package com.cdutcm.tcms.biz.JoinUpCloudPlat;

/**
 * @author: mxq
 * @date: 2019/10/9 15:58
 * @desc:
 */
import com.cdutcm.core.util.JSONUtil;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.CharsetUtils;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class HttpUtils {
    /**
     * 向指定URL发送GET方法的请求
     *
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 携带token的GET请求
     * @param url
     * @param param
     * @return
     */
    public static String sendGetWithToken(String url, String param, String token) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url;
            if (Objects.nonNull(param)){
                urlNameString = url + "?" + param;
            }
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.setRequestProperty("authorization","Bearer "+ token);
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }
    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url
     *            发送请求的 URL
     * @param
     *             =value1&name2=value2 的形式,传入map会进行转换
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, Map<String,String>map) {
        String param = getUrlParamsFromMap(map);
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }
    public static String sendPostWithToken(String url,String json,String token){
        CloseableHttpClient httpclient = HttpClients.createDefault();
        String responseInfo = null;
        System.out.println(json);
        System.out.println(token);
        try {
            HttpPost httpPost = new HttpPost(url);
            httpPost.addHeader("Content-Type", "application/json;charset=UTF-8");
            if(token!=null){
                httpPost.addHeader("authorization","Bearer "+"  "+token);
            }
            ContentType contentType = ContentType.create("application/json", CharsetUtils.get("UTF-8"));
            httpPost.setEntity(new StringEntity(json, contentType));
            CloseableHttpResponse response = httpclient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            int status = response.getStatusLine().getStatusCode();
            if (status >= 200 && status < 300) {
                if (null != entity) {
                    responseInfo = EntityUtils.toString(entity);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return responseInfo;
    }
    /**
     * description:将map转换成url参数格式: name1=value1&name2=value2
     *
     * @param map
     * @return
     */
    public static String getUrlParamsFromMap(Map<String, String> map) {
        try {
            if (null != map) {
                StringBuilder stringBuilder = new StringBuilder();
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    stringBuilder.append(URLEncoder.encode(entry.getKey(), "UTF-8"))
                            .append("=")
                            .append(URLEncoder.encode(entry.getValue(), "UTF-8"))
                            .append("&");
                }
                String content = stringBuilder.toString();
                if (content.endsWith("&")) {
                    content = content.substring(0, content.length() - 1);
                }
                return content;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("map数据异常！" + e);
        }
        return "";
    }


    public static void main(String[] args) {
        String url = "http://iauth.cddmi.cn/connect/token";
        Map<String,String>map = new HashMap<>();
        map.put("client_id","msc.psychiatry");
        map.put("client_secret","psychiatry");
        map.put("grant_type","password");
        map.put("username","18382473437");
        map.put("password","123456");
        String param1 = getUrlParamsFromMap(map);
        System.out.println(param1);
        System.out.println("param1="+param1);
        String param = "client_id=msc.psychiatry&client_secret=psychiatry&grant_type=password&username=18382473437&password=123456";
        System.out.println("param="+param);
        String s = sendPost(url, map);
        TokenObj tokenObj = JSONUtil.toBean(s,TokenObj.class);
        //获取用户信息
        System.out.println(s);
        String json = "{\"UserId\":null}";
        String s1 = sendPostWithToken(ApiConstant.GetUserInfo, json, tokenObj.getAccess_token());
        System.out.println(s1);
    }
}
