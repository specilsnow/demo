package com.cdutcm.core.util;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * @author mxq
 * @date: 2018/11/13 17:28
 * @desc:
 */
public class HttpUtil {
    /**
     * 发送 get请求
     */
    public static String httpClientGet(String url) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        String result = null;
        try {
            // 创建httpget.
            HttpGet httpget = new HttpGet(url);
            // System.out.println("executing request " + httpget.getURI());
            // 执行get请求.
            CloseableHttpResponse response = httpclient.execute(httpget);
            try {
                // 获取响应实体
                HttpEntity entity = response.getEntity();
                // 打印响应状态
                // System.out.println(response.getStatusLine());
                if (entity != null) {
                    // 打印响应内容长度
                    // System.out.println("Response content length: " +
                    // entity.getContentLength());
                    // 打印响应内容
                    result = EntityUtils.toString(entity);
                    // System.out.println("Response content: " + result);
                }
            } finally {
                response.close();
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭连接,释放资源
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
