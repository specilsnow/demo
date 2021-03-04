package com.cdutcm.core.util;

import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;

public class UrlUtil {
    private static final String KEY = "youngyield";

    public static void main(String[] args) throws Exception {
        String source = "/f/g/6427346923.xlsx";
        System.out.println("原文:" + source);

        String result = enCryptAndEncode(source);
        System.out.println("加密后:" + result);
        String source_2 = deCryptAndDecode(result);
        System.out.println("解密后:" + source_2);

    }


    /**
     * Base64URL加密
     * @param content
     * @return
     */
    public static String enCryptAndEncode(String content){
        if(content == null || content.length() == 0){
            return null;
        }
        byte[] sourceBytes = new byte[0];
        try {
            sourceBytes = enCryptAndEncode(content, KEY);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Base64.encodeBase64URLSafeString(sourceBytes);

    }
    /**
     * 加密函数
     *
     * @param content 加密的内容
     * @param strKey  密钥
     * @return 返回二进制字符数组
     * @throws Exception
     */
    private static byte[] enCryptAndEncode(String content, String strKey) throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        /*keyGenerator.init(128, new SecureRandom(strKey.getBytes()));*/
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG" );
        secureRandom.setSeed(strKey.getBytes());
        keyGenerator.init(128, secureRandom);

        SecretKey desKey = keyGenerator.generateKey();
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, desKey);
        return cipher.doFinal(content.getBytes("UTF-8"));
    }
    /**
     * Base64URL解密
     * @param content
     * @return
     * @throws Exception
     */
    public static String deCryptAndDecode(String content) throws Exception {
        if(content == null || content.length() == 0){
            return null;
        }
        byte[] targetBytes = Base64.decodeBase64(content);
        return deCryptAndDecode(targetBytes, KEY);
    }


    /**
     * 解密函数
     *
     * @param src    加密过的二进制字符数组
     * @param strKey 密钥
     * @return
     * @throws Exception
     */
    private static String deCryptAndDecode(byte[] src, String strKey) throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG" );
        secureRandom.setSeed(strKey.getBytes());
        keyGenerator.init(128, secureRandom);
        SecretKey desKey = keyGenerator.generateKey();
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, desKey);
        byte[] cByte = cipher.doFinal(src);
        return new String(cByte, "UTF-8");
    }

    private final static String ENCODE = "UTF-8";
    /**
     * URL 解码
     */
    public static String getURLDecoderString(String str) {
        String result = "";
        if (null == str) {
            return "";
        }
        try {
            result = java.net.URLDecoder.decode(str, ENCODE);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }
    /**
     * URL 转码
     */
    public static String getURLEncoderString(String str) {
        String result = "";
        if (null == str) {
            return "";
        }
        try {
            result = java.net.URLEncoder.encode(str, ENCODE);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }
}
