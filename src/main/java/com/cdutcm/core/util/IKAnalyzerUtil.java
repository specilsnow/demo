//package com.cdutcm.core.util;
//
//import java.io.ByteArrayInputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.io.Reader;
//
//import org.wltea.analyzer.core.IKSegmenter;
//import org.wltea.analyzer.core.Lexeme;
//
///**
// * 分词器工具类
// *
// * @author fw
// *
// */
//public class IKAnalyzerUtil {
//	/**
//	 * 对中文进行分词
//	 *
//	 * @param str
//	 * @return
//	 */
//	public static String IKAnalysis(String str) {
//		StringBuffer sb = new StringBuffer();
//		try {
//			byte[] bt = str.getBytes();
//			InputStream ip = new ByteArrayInputStream(bt);
//			Reader read = new InputStreamReader(ip);
//			IKSegmenter iks = new IKSegmenter(read, true);
//			Lexeme t;
//			while ((t = iks.next()) != null) {
//				sb.append(t.getLexemeText() + "-");
//			}
//			// sb.delete(sb.length() - 1, sb.length());
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return sb.toString().substring(0, sb.length() - 1);
//	}
//}
