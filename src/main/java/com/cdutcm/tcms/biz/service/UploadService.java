package com.cdutcm.tcms.biz.service;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * @author: mxq
 * @date: 2019/8/14 15:10
 * @desc:
 */
public interface UploadService {
    String saveFile(MultipartFile file);
    /**
     * 获取文件访问路径
     */
    void getSavePath(String encodeFile, HttpServletResponse response);
}
