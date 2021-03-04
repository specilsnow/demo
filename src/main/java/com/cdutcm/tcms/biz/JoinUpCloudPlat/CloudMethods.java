package com.cdutcm.tcms.biz.JoinUpCloudPlat;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.cdutcm.core.util.JSONUtil;
import com.cdutcm.core.util.StringUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.*;

/**
 * @author wonder
 * @date 2019/10/29
 */
public class CloudMethods {
    private CloudMethods() {
    }
    private static class SingleMethods {
        private static final CloudMethods COCOS_MANGER = new CloudMethods();
    }
    public static final CloudMethods getInstance() {
        return SingleMethods.COCOS_MANGER;
    }

    public List<CloudUserClinic> getDoctorOrgList(String token){
        String s = HttpUtils.sendGetWithToken(ApiConstant.DoctorOrgList, null, token);
        List<CloudUserClinic> list = new ArrayList<>();
        JSONObject jsonObject = JSONObject.parseObject(s);
        if (jsonObject.containsKey("Success") & (boolean)jsonObject.get("Success")){
            list = JSON.parseObject(jsonObject.getString("Data"),new TypeReference<List<CloudUserClinic>>(){});
        }
        return list;
    }

    public List<CloudOrganization> getOrganization(Integer currentPage,Integer pageSize,String name,String token){
        Map<String,String> map = new HashMap<>();
        if (Objects.nonNull(pageSize)){
            map.put("PageSize",pageSize.toString());
        }
        if (Objects.nonNull(currentPage)){
            map.put("Page",currentPage.toString());
        }
        if (Objects.nonNull(currentPage)){
            map.put("Name",name);
        }
        Gson gson = new Gson();
        String s1 = gson.toJson(map);
        String s = HttpUtils.sendPostWithToken(ApiConstant.OrganizationList, s1, token);
        List<CloudOrganization> list = new ArrayList<>();
        JSONObject jsonObject = JSONObject.parseObject(s);
        if (jsonObject.containsKey("Rows")){
            list = JSON.parseObject(jsonObject.getString("Rows"),new TypeReference<List<CloudOrganization>>(){});
        }
        return list;
    }

    public CloudOrganization saveOrganization(String name,String token){
        Map<String,String> map = new HashMap<>();
        map.put("Name",name);
        Gson gson = new Gson();
        String s1 = gson.toJson(map);
        String s = HttpUtils.sendPostWithToken(ApiConstant.OrganizationList, s1, token);
        JSONObject jsonObject = JSONObject.parseObject(s);
        CloudOrganization cloudOrganization = new CloudOrganization();
        if (jsonObject.containsKey("Success") & (boolean)jsonObject.get("Success")){
            cloudOrganization = JSON.parseObject(jsonObject.getString("Data"),CloudOrganization.class);
        }
        return cloudOrganization;
    }

    //获取token
    public HashMap<Object, Object> getToken(String username,String password){
        HashMap<Object, Object> hashMap = new HashMap<>();
        Map<String, String> map = new HashMap<>();
        map.put("client_id", "msc.psychiatry");
        map.put("client_secret", "psychiatry");
        map.put("grant_type", "password");
        map.put("username", username);
        map.put("password", password);
        String rs = HttpUtils.sendPost(ApiConstant.AuthToken, map);
        TokenObj tokenObj = null;
        //将结果转为json对象
        try {
            tokenObj = JSONUtil.toBean(rs, TokenObj.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (StringUtil.objIsEmpty(tokenObj) || StringUtil.isEmpty(tokenObj.getAccess_token())) {
            hashMap.put("code", false);
        } else {
            hashMap.put("code", true);
            hashMap.put("data", tokenObj);
        }
        return hashMap;
    }
    //短信的登录验证
    public HashMap<Object, Object> getSmsToken(String username,String password,String codekey){
        HashMap<Object, Object> hashMap = new HashMap<>();
        Map<String, String> map = new HashMap<>();
        map.put("client_id", "msc.bd5bllqdpsbpd");
        map.put("client_secret", "secretcases");
        map.put("grant_type", "password");
        map.put("logintype", "2");
        map.put("username", username);
        map.put("password", password);
        map.put("codekey", codekey);
        String rs = HttpUtils.sendPost(ApiConstant.AuthToken, map);
        System.out.println(rs);
        TokenObj tokenObj = null;
        //将结果转为json对象
        try {
            tokenObj = JSONUtil.toBean(rs, TokenObj.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (StringUtil.objIsEmpty(tokenObj) || StringUtil.isEmpty(tokenObj.getAccess_token())) {
            hashMap.put("code", false);
        } else {
            hashMap.put("code", true);
            hashMap.put("data", tokenObj);
        }
        return hashMap;
    }
    public CloudUser getUserInfo(String token){
        String json = "{\"UserId\":null}";
        String s1 = HttpUtils.sendPostWithToken(ApiConstant.GetUserInfo, json,token);
        net.sf.json.JSONObject jsonObject = net.sf.json.JSONObject.fromObject(s1);
        CloudUser cloudUser = new CloudUser();
        try {
            Boolean success =(Boolean)jsonObject.get("Success");
            if(success){
                cloudUser =  JSONUtil.toBean(jsonObject.get("Data").toString(), CloudUser.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cloudUser;
    }
    public HashMap<String,String> addOrder(Double orderMoney,String orderCode,String token){
        String json = "{\n" +
                "  \"orderMoney\": "+orderMoney+", \n" +
                "  \"orderType\": 5, \n" +
                "  \"subject\": \"数据下载\", \n" +
                "  \"body\": \"数据开放平台数据下载\",  \n" +
                "  \"orderCode\": \""+orderCode+"\", \n" +
                "  \"payType\": 7, \n" +
                "  \"webCallbackUrl\": \"http://dsj.cdutcm.edu.cn/opindex\" \n" +
                "}";
        String s = HttpUtils.sendPostWithToken(ApiConstant.AddOrder, json, token);
        Gson gson = new Gson();
        HashMap<String, String> map = gson.fromJson(s, new TypeToken<HashMap<String,String>>(){}.getType());
        return map;
    }
    public HashMap SendSms(String phone,String clientToken){
        HashMap hashMap = new HashMap();
        //1.拿到客户端认证
        //2.构造发送实体
        String json = "{\n" +
                "\t\"TemplateNo\": \"1\",\n" +
                "\t\"access_token\": \""+clientToken+"\",\n" +
                "\t\"mobile\": \""+phone+"\",\n" +
                "\t\"token_ type\": \"Bearer\"\n" +
                "}";
        System.out.println(json);
        String s = HttpUtils.sendPostWithToken(ApiConstant.PostSMS, json, clientToken);
        System.out.println(s);
        net.sf.json.JSONObject jsonObject = net.sf.json.JSONObject.fromObject(s);
        Boolean aBoolean=(Boolean)jsonObject.get("Success");
        if(aBoolean){
            hashMap.put("code",true);
            net.sf.json.JSONObject data = jsonObject.getJSONObject("Data");
            String  codekey = data.get("Id").toString();
            System.out.println("codekey="+codekey);
            hashMap.put("codekey",codekey);
        }else {
            hashMap.put("code",false);
        }
        return hashMap;
    }
    /**
     * 云平台的客户端认证
     * @return
     */
    public String getClientToken(){
        Map<String, String> map = new HashMap<>();
        map.put("client_id", "client.dashuju");
        map.put("client_secret", "dashuju@123");
        map.put("grant_type", "client_credentials");
        String rs = HttpUtils.sendPost(ApiConstant.AuthToken, map);
        net.sf.json.JSONObject jsonObject = net.sf.json.JSONObject.fromObject(rs);
        return jsonObject.get("access_token").toString();
    }
    public List<DiseaseVO> getGoodAtDisease(String token){
        String json = "{\"name\":\"\"}";
//        String clientToken = getClientToken();
        String s = HttpUtils.sendPostWithToken(ApiConstant.GetGoodAtDisease, json, token);
        net.sf.json.JSONObject jsonObject = net.sf.json.JSONObject.fromObject(s);
        List<DiseaseVO> diseaseVOS = new ArrayList<>();
        try {
            String rows = jsonObject.get("Rows").toString();
            if(StringUtil.notEmpty(rows)){
                diseaseVOS = JSONUtil.toList(rows, DiseaseVO.class);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return  diseaseVOS;
    }
    public String authDocotor(String json,String token){
        String s1 = HttpUtils.sendPostWithToken(ApiConstant.AuthDoctor, json,token);
        return s1;
    }
    public String uploadData(String json){
        Map map = new HashMap();
        map.put("Name",json);
        return HttpUtils.sendPost(ApiConstant.PostData, map);
    }

    public String updatePassword(String OldPwd,String NewPwd,String token) {
        Map<String,Object> map = new HashMap<>();
        map.put("OldPwd",OldPwd);
        map.put("NewPwd",NewPwd);
        map.put("UserId","");
        Gson gson = new Gson();
        String json = gson.toJson(map);
        return HttpUtils.sendPostWithToken(ApiConstant.UpdatePassword, json,token);
    }

    public String pushWxMsg(String json,String token){
        String s = HttpUtils.sendPostWithToken(ApiConstant.PushWxMsg, json, token);
        return s;
    }

    public String RetrievePassword(String phone, String NewPwd, String accessToken) {
        Map map = new HashMap();
        map.put("LoginCode", phone);
        map.put("LoginPwd", NewPwd);
        String s = HttpUtils.sendPostWithToken(ApiConstant.RetrievePassword, JSONUtil.toJson(map), accessToken);
        return s;
    }

    public HashMap<Object, Object> getWxToken(String openid){
        HashMap<Object, Object> hashMap = new HashMap<>();
        Map<String, String> map = new HashMap<>();
        map.put("client_id", "msc.psychiatry");
        map.put("client_secret", "psychiatry");
        map.put("grant_type", "password");
        map.put("username", "0");
        map.put("password", "0");
        map.put("logintype","4");
        String json = "{\n" +
                "\t\"openid\": \""+openid+"\",\n" +
                "\t\"nickname\": \"\",\n" +
                "\t\"sex\": 1,\n" +
                "\t\"city\": \"\",\n" +
                "\t\"province\": \"\",\n" +
                "\t\"country\": \"\",\n" +
                "\t\"headimgurl\": \"\",\n" +
                "\t\"privilege\": [],\n" +
                "\t\"unionid\": \"ohLxKty4EPNl8\",\n" +
                "\t\"appid\": \"wx283826bb47a11f95\"\n" +
                "}";
        map.put("quick",json);
        String rs = HttpUtils.sendPost(ApiConstant.AuthToken, map);
        TokenObj tokenObj = null;
        //将结果转为json对象
        try {
            tokenObj = JSONUtil.toBean(rs, TokenObj.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (StringUtil.objIsEmpty(tokenObj) || StringUtil.isEmpty(tokenObj.getAccess_token())) {
            hashMap.put("code", false);
        } else {
            hashMap.put("code", true);
            hashMap.put("data", tokenObj);
        }
        return hashMap;
    }



    public static void main(String[] args) {
//        CloudMethods cloudMethods = new CloudMethods();
//        HashMap<Object, Object> hashMap = cloudMethods.getWxToken("oMuWYYVeq4ibU");
        HashMap<Object, Object> hashMap = new HashMap<>();
        hashMap.put("OpenId","ouM5m06F9fs3BrNmRKGxI22SMhUY");
        hashMap.put("First",getHash("亲：今天是2020年1月6日，本日节气为大寒！","#000000"));
        hashMap.put("Keyword1",getHash("","#000000"));
        hashMap.put("Keyword2",getHash("小明","#000000"));
        hashMap.put("Keyword3",getHash("小明","#000000"));
        hashMap.put("Keyword4",getHash("小明","#000000"));
        hashMap.put("Keyword5",getHash("小明","#000000"));
        hashMap.put("Remark",getHash("为了更准确地采集您的健康数据，方便给您提供更科学的健康指导，请于本日内进行体质辨识；","red"));
        hashMap.put("Url","http://tzbs.cdutcm.edu.cn/user/info");
        hashMap.put("TemplateId","SWtA1KpYpuU-BEQ2zrlRTFG0mUZ57IlgB78iuMEZIIQ");
        String json = net.sf.json.JSONObject.fromObject(hashMap).toString();
        String clientToken = getInstance().getClientToken();
        System.out.println(clientToken);
        String s = getInstance().pushWxMsg(json, clientToken);
        System.out.println(s);
    }

    public static HashMap getHash(String value,String color){
        HashMap<Object, Object> hashMap = new HashMap<>();
        hashMap.put("Value",value);
        hashMap.put("Color",color);
        return hashMap;
    }
}