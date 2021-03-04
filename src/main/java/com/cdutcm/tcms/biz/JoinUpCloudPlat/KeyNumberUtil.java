package com.cdutcm.tcms.biz.JoinUpCloudPlat;

import java.util.Random;

/**
 * @author: mxq
 * @date: 2019/10/17 09:17
 * @desc:
 */
public class KeyNumberUtil {
    //各种新增对象的id
    public static String keyNumber(){
        Random random = new Random();
        //生成6位随机数
        Integer number = random.nextInt(9000000)+1000000;
        //返回当前时间戳+六位随机数 共计20位随机数
        return System.currentTimeMillis() + String.valueOf(number);
    }
    //生成验证码
    public static String SixValidateNumber(){
        Random random = new Random();
        Integer number = random.nextInt(900000)+100000;
        return String.valueOf(number);
    }
}
