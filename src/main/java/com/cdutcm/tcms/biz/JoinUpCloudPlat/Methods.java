package com.cdutcm.tcms.biz.JoinUpCloudPlat;

import com.cdutcm.core.util.JSONUtil;
import com.cdutcm.core.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: mxq
 * @date: 2019/10/10 17:55
 * @desc:
 */
@Slf4j
public class Methods {

    private Methods() {
    }

    private static class SingleMethods {

        private static final Methods COCOS_MANGER = new Methods();
    }

    public static final Methods getInstance() {
        return SingleMethods.COCOS_MANGER;
    }

    //获取token
    public HashMap<Object, Object> getToken(String username, String password) {
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
    public HashMap<Object, Object> getSmsToken(String username, String password, String codekey) {
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

    public CloudUser getUserInfo(String token) {
        String json = "{\"UserId\":null}";
        String s1 = HttpUtils.sendPostWithToken(ApiConstant.GetUserInfo, json, token);
        JSONObject jsonObject = JSONObject.fromObject(s1);
        CloudUser cloudUser = new CloudUser();
        try {
            Boolean success = (Boolean) jsonObject.get("Success");
            if (success) {
                cloudUser = JSONUtil.toBean(jsonObject.get("Data").toString(), CloudUser.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cloudUser;
    }

    public HashMap SendSms(String phone) {
        HashMap hashMap = new HashMap();
        //1.拿到客户端认证
        String clientToken = getClientToken();
        //2.构造发送实体
        String json = "{\n" +
                "\t\"TemplateNo\": \"1\",\n" +
                "\t\"access_token\": \"" + clientToken + "\",\n" +
                "\t\"mobile\": \"" + phone + "\",\n" +
                "\t\"token_ type\": \"Bearer\"\n" +
                "}";
        System.out.println(json);
        String s = HttpUtils.sendPostWithToken(ApiConstant.PostSMS, json, clientToken);
        System.out.println(s);
        JSONObject jsonObject = JSONObject.fromObject(s);
        Boolean aBoolean = (Boolean) jsonObject.get("Success");
        if (aBoolean) {
            hashMap.put("code", true);
            JSONObject data = jsonObject.getJSONObject("Data");
            String codekey = data.get("Id").toString();
            System.out.println("codekey=" + codekey);
            hashMap.put("codekey", codekey);
        } else {
            hashMap.put("code", false);
        }
        return hashMap;
    }

    /**
     * 云平台的客户端认证
     *
     * @return
     */
    public String getClientToken() {
        Map<String, String> map = new HashMap<>();
        map.put("client_id", "client.dashuju");
        map.put("client_secret", "dashuju@123");
        map.put("grant_type", "client_credentials");
        String rs = HttpUtils.sendPost(ApiConstant.AuthToken, map);
        JSONObject jsonObject = JSONObject.fromObject(rs);
        return jsonObject.get("access_token").toString();
    }

    public List<DiseaseVO> getGoodAtDisease(String token) {
        String json = "{\"name\":\"\"}";
//        String clientToken = getClientToken();
        String s = HttpUtils.sendPostWithToken(ApiConstant.GetGoodAtDisease, json, token);
        JSONObject jsonObject = JSONObject.fromObject(s);
        List<DiseaseVO> diseaseVOS = new ArrayList<>();
        try {
            String rows = jsonObject.get("Rows").toString();
            if (StringUtil.notEmpty(rows)) {
                diseaseVOS = JSONUtil.toList(rows, DiseaseVO.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return diseaseVOS;
    }

    public String authDocotor(String json, String token) {
        String s1 = HttpUtils.sendPostWithToken(ApiConstant.AuthDoctor, json, token);
        return s1;
    }

    public String uploadData(String json) {
        Map map = new HashMap();
        map.put("Name", json);
        //分开去提交一个报错了 提交另外一个
        JSONObject jsonObject = JSONObject.fromObject(map);
        String s = HttpUtils.sendPostWithToken(ApiConstant.PostDataWithI, jsonObject.toString(), null);
        if (StringUtil.objIsEmpty(s)) {
            JSONObject jsonObject1 = JSONObject.fromObject(s);
            Boolean success = (Boolean) jsonObject1.get("Success");
            if (!success) {
                log.info("【用了带i的那个提交失败了，换不带i的】");
                s = HttpUtils.sendPost(ApiConstant.PostData, map);
                JSONObject jsonObject2 = JSONObject.fromObject(s);
                if(StringUtil.objIsEmpty(jsonObject2)){
                    log.info("【用了不带i的那个提交失败了，喊林杨改bug】");
                }else {
                    Boolean success2 = (Boolean) jsonObject2.get("Success");
                    if(!success2){
                        log.info("【用了不带i的那个提交失败了，喊林杨改bug】");
                    }
                }

            }
        }
        return s;
    }

    public static void main(String[] args) {
//        String s = Methods.getInstance().uploadData();
//        List<DiseaseVO> diseaseVOS  = Methods.getInstance().getGoodAtDisease();
//        List<DiseaseVO> diseaseVOS = JSONUtil.toList(goodAtDisease, DiseaseVO.class);
        String json = "{\"zz\":\"口干咽燥\",\"yjs\":\"\",\"bxdata\":\"[6, 8, 3, 7, 7, 4, 9, 9, 2, 5, 5, 4, 0, 0, 0, 9, 8, 6, 4, 9, 0, 6, 2, 4, 4, 3, 5, 1, 8, 5, 0, 2, 2, 8, 2, 2, 6, 2, 1, 1, 1, 9, 8, 9, 2, 0]\",\"jws\":\"\",\"xbs\":\"\",\"bwdata\":\"[1, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]\",\"js\":\"\",\"gms\":\"\",\"fs\":\"3\",\"adviceName\":\"\",\"doctorName\":\"何洁林\",\"bw\":\"心,肺,肾,\",\"bx\":\"气虚,阳虚,\",\"tgjc\":\"\",\"yz\":\"忌辛辣\",\"disease\":\"口干\",\"drugs\":[{\"dosage\":\"15\",\"name\":\"车前子\"},{\"dosage\":\"12\",\"name\":\"法半夏\"},{\"dosage\":\"15\",\"name\":\"茯苓\"},{\"dosage\":\"15\",\"name\":\"黄柏\"},{\"dosage\":\"12\",\"name\":\"黄连\"},{\"dosage\":\"12\",\"name\":\"黄芩\"},{\"dosage\":\"15\",\"name\":\"牡丹皮\"},{\"dosage\":\"15\",\"name\":\"青蒿\"},{\"dosage\":\"20\",\"name\":\"山药\"},{\"dosage\":\"15\",\"name\":\"山茱萸\"},{\"dosage\":\"30\",\"name\":\"石膏\"},{\"dosage\":\"30\",\"name\":\"熟地黄\"},{\"dosage\":\"15\",\"name\":\"泽泻\"},{\"dosage\":\"12\",\"name\":\"知母\"},{\"dosage\":\"12\",\"name\":\"枳壳\"},{\"dosage\":\"15\",\"name\":\"竹茹\"}],\"userphone\":\"15281020238\",\"sex\":\"男\",\"end_time\":\"2019-12-31\",\"grs\":\"\",\"dept\":\"脾胃科\",\"jff\":\"水煎服，一日三次，一次150ml\",\"ZF\":\"活血祛瘀\",\"doctorPhone\":\"18382473437\",\"symptom\":\"口干咽燥头晕,声低息微\",\"zlyj\":\"多喝水呀\",\"je\":0,\"fzjc\":\"\",\"age\":\"027岁\",\"zx\":\"燥热伤肺证\",\"jzlsh\":\"20191231445028\",\"username\":\"浮浮\"}";
        HashMap<Object, Object> hashMap = new HashMap<>();
        hashMap.put("Name", json);
        JSONObject jsonObject = JSONObject.fromObject(hashMap);
        System.out.println(jsonObject.toString());
//        String json1 = "{" +
//                "\"Name\":" +
//                "{\"zz\":\"口干咽燥\",\"yjs\":\"\",\"bxdata\":\"[6, 8, 3, 7, 7, 4, 9, 9, 2, 5, 5, 4, 0, 0, 0, 9, 8, 6, 4, 9, 0, 6, 2, 4, 4, 3, 5, 1, 8, 5, 0, 2, 2, 8, 2, 2, 6, 2, 1, 1, 1, 9, 8, 9, 2, 0]\",\"jws\":\"\",\"xbs\":\"\",\"bwdata\":\"[1, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]\",\"js\":\"\",\"gms\":\"\",\"fs\":\"3\",\"adviceName\":\"\",\"doctorName\":\"何洁林\",\"bw\":\"心,肺,肾,\",\"bx\":\"气虚,阳虚,\",\"tgjc\":\"\",\"yz\":\"忌辛辣\",\"disease\":\"口干\",\"drugs\":[{\"dosage\":\"15\",\"name\":\"车前子\"},{\"dosage\":\"12\",\"name\":\"法半夏\"},{\"dosage\":\"15\",\"name\":\"茯苓\"},{\"dosage\":\"15\",\"name\":\"黄柏\"},{\"dosage\":\"12\",\"name\":\"黄连\"},{\"dosage\":\"12\",\"name\":\"黄芩\"},{\"dosage\":\"15\",\"name\":\"牡丹皮\"},{\"dosage\":\"15\",\"name\":\"青蒿\"},{\"dosage\":\"20\",\"name\":\"山药\"},{\"dosage\":\"15\",\"name\":\"山茱萸\"},{\"dosage\":\"30\",\"name\":\"石膏\"},{\"dosage\":\"30\",\"name\":\"熟地黄\"},{\"dosage\":\"15\",\"name\":\"泽泻\"},{\"dosage\":\"12\",\"name\":\"知母\"},{\"dosage\":\"12\",\"name\":\"枳壳\"},{\"dosage\":\"15\",\"name\":\"竹茹\"}],\"userphone\":\"15281020238\",\"sex\":\"男\",\"end_time\":\"2019-12-31\",\"grs\":\"\",\"dept\":\"脾胃科\",\"jff\":\"水煎服，一日三次，一次150ml\",\"ZF\":\"活血祛瘀\",\"doctorPhone\":\"18382473437\",\"symptom\":\"口干咽燥头晕,声低息微\",\"zlyj\":\"多喝水呀\",\"je\":0,\"fzjc\":\"\",\"age\":\"027岁\",\"zx\":\"燥热伤肺证\",\"jzlsh\":\"20191231445028\",\"username\":\"浮浮\"}" +
//                "}";
//        jsonObject.put("Name",json);
        System.out.println(jsonObject.toString());
        String s = HttpUtils.sendPostWithToken(ApiConstant.PostDataWithI, jsonObject.toString(), null);
        System.out.println("返回格式：" + s);
    }
}
