package com.cdutcm.core.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

/***
 * 文件处理工具类
 * 
 * @author lixin
 * 
 */
public class FileUtil {

	/***
	 * 以行为单位读取文件
	 * 
	 * @param path
	 *            文件路径
	 * @return 返回文本的内容字符串
	 */
	public static String readFileByLines(String path) {
		StringBuffer stringBuffer = new StringBuffer();
		File file = new File(path);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			// 一次读入一行，直到读入null为文件结束
			while ((tempString = reader.readLine()) != null) {
				// 显示行号
				stringBuffer.append(tempString);
				stringBuffer.append("\n");
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
		return stringBuffer + "";
	}

	/**
	 * 以字符为单位读取文件，常用于读文本，数字等类型的文件
	 */
	public static String readFileByChars(String fileName) {
		StringBuffer stringBuffer = new StringBuffer();
		Reader reader = null;
		try {
			// 一次读多个字符
			char[] tempchars = new char[30];
			int charread = 0;
			reader = new InputStreamReader(new FileInputStream(fileName));
			// 读入多个字符到字符数组中，charread为一次读取字符数
			while ((charread = reader.read(tempchars)) != -1) {
				// 同样屏蔽掉\r不显示
				if ((charread == tempchars.length)
						&& (tempchars[tempchars.length - 1] != '\r')) {
					stringBuffer.append(tempchars);
				} else {
					for (int i = 0; i < charread; i++) {
						if (tempchars[i] == '\r') {
							continue;
						} else {
							stringBuffer.append(tempchars[i]);
						}
					}
				}
			}

		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
		return stringBuffer + "";
	}

	/** 获取指定目录下面的所有文件的文件名 */
	public static List<String> getFile(String path) {
		List<String> files = new ArrayList<String>();
		// 获得指定文件对象
		File file = new File(path);
		// 获得该文件夹内的所有文件
		File[] array = file.listFiles();

		for (int i = 0; i < array.length; i++) {
			if (array[i].isFile())// 如果是文件
			{
				// 只输出文件名字
				// System.out.println(array[i].getName());
				files.add(array[i].getName());
				// 同样输出当前文件的完整路径 大家可以去掉注释 测试一下
				// System.out.println(array[i].getPath());
			}
		}
		return files;
	}
}
