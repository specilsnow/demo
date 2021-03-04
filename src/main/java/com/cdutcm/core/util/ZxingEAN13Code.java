package com.cdutcm.core.util;

import com.cdutcm.core.config.UrlConfig;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import org.apache.http.HttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;

/**
 * @Auther:  Mxq
 * @Date: 2018/7/24
 * @description:  条形码工具类
 */
public class ZxingEAN13Code {


    //生成条形码
    public  static String encode(String contents, HttpServletRequest request,UrlConfig config) {

        String urlName = contents + RandomUtil.generate(6)+".png";

        /**
         * 修改访问路径为tomcat
         */
        //条形码保存路径
        String imgPath2 = request.getSession().getServletContext().getRealPath(urlName); //文件存储位置
        //修改访问路径
        String url2 = request.getScheme() + "://" + request.getServerName() +
                ":" + request.getServerPort() + request.getContextPath() +"/"+urlName;//访问路径
//        System.out.println(url2);
        String url3 = request.getScheme() + "://" + config.getServerName()+
                ":" + config.getServerPort() + request.getContextPath() +"/"+urlName;//访问路径
        //一维码生成路径
        String imgPath = "D:/File/barcode/"+urlName;
        //一维码服务器访问路径
        String url = request.getScheme() + "://" + request.getServerName() +
                ":" + request.getServerPort() + request.getContextPath()+"/image/"+urlName;
        //保证最小为70*25的大小
        int width=300;//一维码的宽度
        int height=40;//一维码的高度
        int codeWidth = Math.max(70, width);
        int codeHeight = Math.max(25, height);
        try {
            //使用EAN_13编码格式进行编码

            BitMatrix bitMatrix = new MultiFormatWriter().encode(contents,
                    BarcodeFormat.CODE_39, codeWidth, codeHeight, null);
            //生成png格式的图片保存到imgPath路径
            MatrixToImageWriter.writeToStream(bitMatrix, "png",
                    new FileOutputStream(imgPath2));
//            System.out.println("encode success! the img's path is "+imgPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return url3;
    }
    //解析条形码
    public static String decode(String imgPath) {
        BufferedImage image = null;
        Result result = null;
        try {
            image = ImageIO.read(new File(imgPath));
            if (image == null) {
//                System.out.println("the decode image may be not exit.");
            }
            LuminanceSource source = new BufferedImageLuminanceSource(image);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

            result = new MultiFormatReader().decode(bitmap, null);
            return result.getText();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
