package com.cdutcm.core.util;

import com.cdutcm.core.config.UrlConfig;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Hashtable;

/**
 * @Auther: Mxq
 * @Date: 2018/7/24 14:49
 * @description: 二维码工具类
 */
public class ZxingQRCode {

//    //生成二维码
//    public static String encode(String contents, HttpServletRequest request, UrlConfig config) {
//
//        String urlName = RandomUtil.generate(12)+".png";
//
//        String imgPath2 = request.getSession().getServletContext().getRealPath(urlName); //文件存储位置
//        //修改访问路径
//        String url2 = request.getScheme() + "://" + request.getServerName() +
//                ":" + request.getServerPort() + request.getContextPath() +"/"+urlName;//访问路径
//        String url3 = request.getScheme() + "://" + config.getServerName()+
//        		":" + config.getServerPort() + request.getContextPath() +"/"+urlName;//访问路径
//        //一维码生成路径
//        String imgPath = "D:/File/barcode/"+urlName;
//        //一维码服务器访问路径
//        String url = request.getScheme() + "://" + request.getServerName() +
//                ":" + request.getServerPort() + request.getContextPath()+"/image/"+urlName;
//
//        int width=400;//二维码的宽度
//        int height=400;//二维码的高度
//
//        Hashtable<EncodeHintType, Object> hints = new Hashtable();
//        // 指定纠错等级
//        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
//        // 指定显示格式为GBK
//        hints.put(EncodeHintType.CHARACTER_SET, "GBK");
//        try {
//            BitMatrix bitMatrix = new MultiFormatWriter().encode(contents,
//                    BarcodeFormat.QR_CODE, width, height, hints);
//            //生成png格式的图片保存到imgPath路径位置
//            MatrixToImageWriter.writeToStream(bitMatrix, "png",
//                    new FileOutputStream(imgPath2));
////            System.out.println("QR Code encode sucsess! the img's path is "+imgPath);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return  imgPath2;
//    }

    //生成二维码

    public static String encode(String contents, HttpServletRequest request, UrlConfig config) {

        String urlName = RandomUtil.generate(12)+".png";
        //修改访问路径
        String url2 = request.getScheme() + "://" + request.getServerName() +
                ":" + request.getServerPort() + request.getContextPath() +"/QrImg/"+urlName;//访问路径
        int width=400;//二维码的宽度
        int height=400;//二维码的高度
        String src  ="/opt/cdutcm/jenkins/ybm/data/"+urlName;
//        System.out.println(src);
        Hashtable<EncodeHintType, Object> hints = new Hashtable();
        // 指定纠错等级
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        // 指定显示格式为GBK
        hints.put(EncodeHintType.CHARACTER_SET, "GBK");
        try {
            BitMatrix bitMatrix = new MultiFormatWriter().encode(contents,


                    BarcodeFormat.QR_CODE, width, height, hints);
            //生成png格式的图片保存到imgPath路径位置
            MatrixToImageWriter.writeToStream(bitMatrix, "png",
                    new FileOutputStream(src));
//            System.out.println("QR Code encode sucsess! the img's path is "+imgPath);
        } catch (Exception e) {

            e.printStackTrace();
            return "生成失败"+src;
        }
        return  url2;
    }




}
