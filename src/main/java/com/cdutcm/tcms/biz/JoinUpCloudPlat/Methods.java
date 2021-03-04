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







}
