package com.cdutcm.core.util;

/**
 * @Auther: Mxq
 * @Date: 2018/7/24 11:16
 * @description: 生成随机数的工具类
 */
public class RandomUtil {

    //生成六位字母+数字的随机数
    public static String generate(int k) {

        char[] letters = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
                'K', 'L', 'M', 'N', 'O', 'P','Q', 'R', 'S', 'T', 'U', 'V',
                'W', 'X', 'Y', 'Z','0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f','g','h','i',
                'j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','r',
        };
        boolean[] flags = new boolean[letters.length];
        char[] chs = new char[k];
        for (int i = 0; i < chs.length; i++) {
            int index;
            do {
                index = (int) (Math.random() * (letters.length));
            } while (flags[index]);// 判断生成的字符是否重复
            chs[i] = letters[index];
            flags[index] = true;
        }
        return String.valueOf(chs);
    }

}
