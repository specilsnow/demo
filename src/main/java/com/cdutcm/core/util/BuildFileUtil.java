package com.cdutcm.core.util;


import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Range;

import java.io.*;
import java.util.Map;

/**
 * @Auther: Mxq
 * @Date: 2018/8/9 15:53
 * @description: 导出word文档
 */
public class BuildFileUtil {

    public static void build(File tmpFile, Map<String, String> contentMap, String exportFile) throws Exception {
        FileInputStream tempFileInputStream = new FileInputStream(tmpFile);
        HWPFDocument document = new HWPFDocument(tempFileInputStream);
        // 读取文本内容
        Range bodyRange = document.getRange();
        // 替换内容
        for (Map.Entry<String, String> entry : contentMap.entrySet()) {
            bodyRange.replaceText("${" + entry.getKey() + "}", entry.getValue());
        }

        //导出到文件
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        document.write(byteArrayOutputStream);
        OutputStream outputStream = new FileOutputStream(exportFile);
        outputStream.write(byteArrayOutputStream.toByteArray());
        outputStream.close();
    }
}
