package com.cdutcm.tcms.biz.service.impl;


import com.cdutcm.core.util.TimeUtils;
import com.cdutcm.core.util.UrlUtil;
import com.cdutcm.tcms.biz.service.UploadService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;

/**
 * @author: mxq
 * @date: 2019/8/14 15:11
 * @desc:
 */
@Service
@Slf4j
public class UploadServiceImpl implements UploadService {
    @Value("${file.image-path}")
    private String imagePath;
    @Override
    public String saveFile(MultipartFile file) {
        System.out.println("保存图片逻辑");
        HashMap<String, String> params = new HashMap<>();
        params.put("file", file.getOriginalFilename());
        // 利用文件的hashCode生成两层目录（防止同个目录文件过多影响读取速率）
        int hashCode =params.hashCode();
        String hex = Integer.toHexString(hashCode);
        File dirFile = new File(imagePath+hex.charAt(0) + "/" + hex.charAt(1));
        //根据路径创建文件夹
        System.out.println("创建文件夹");
        if(!dirFile.exists()){
            dirFile.mkdirs();
        }
        String suffix = ".jpg";
        String saveName = TimeUtils.getCurrentTimestamp() + suffix;
        File filei = new File(dirFile, saveName);
        try {
            FileOutputStream out = new FileOutputStream(filei);
            out.write(file.getBytes());
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
        System.out.println("上传图片的保存逻辑");
        String downloadUrl= getHost()+ "/upload/file/";
        String path = hex.charAt(0) + "/" + hex.charAt(1) + "/" + saveName;
        return downloadUrl+ UrlUtil.enCryptAndEncode(path);
    }

    @Override
    public void getSavePath(String encodeFile, HttpServletResponse response) {
        log.debug("加密文件：{}", encodeFile);
        String filePath = null;
        try {
            filePath = UrlUtil.deCryptAndDecode(encodeFile);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("失败");
        }
        log.debug("解密文件：{}", filePath);
        if(StringUtils.isEmpty(filePath)){
            System.out.println("失败");
        }
        response.setHeader("Content-Type", "application/x-msdownload");
        response.setDateHeader("Expires", 0L);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/jpeg");
        String path = imagePath+ filePath;
        File file = new File(path);
        FileInputStream in = null;
        OutputStream out = null;
        try {
            in = new FileInputStream(file);
            out = response.getOutputStream();
            byte[] bytes = new byte[1024];
            int len = 0;
            while ((len = in.read(bytes)) != -1){
                out.write(bytes, 0, len);
            }
        } catch (FileNotFoundException e) {
            System.out.println("文件不存在");
        } catch (IOException e){
            throw new RuntimeException(e);
        } finally {
            try {
                if(out != null){
                    out.flush();
                    out.close();
                }
                if(in != null){
                    in.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    //获取项目访问路径通用前缀
    String getHost(){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        return request.getScheme() + "://" + request.getServerName() +
                ":" + request.getServerPort();

    }
}
