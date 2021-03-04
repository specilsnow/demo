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





    public static HashMap getHash(String value,String color){
        HashMap<Object, Object> hashMap = new HashMap<>();
        hashMap.put("Value",value);
        hashMap.put("Color",color);
        return hashMap;
    }
}
