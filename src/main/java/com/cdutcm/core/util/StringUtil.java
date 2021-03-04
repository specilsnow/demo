package com.cdutcm.core.util;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sourceforge.pinyin4j.PinyinHelper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StringUtil {

	private static final Logger errorlogger = LoggerFactory.getLogger("error");

	private static Pattern NUMBER_PATTERN = Pattern.compile("[0-9]*");
	// 对码处理
	public static String replaceSomeAlp(String str) {
		String result = "";
		String[] arr = new String[] { "1", "2", "3", "4", "5", "6", "7", "8",
				"9", "0", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
				"K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
				"W", "X", "Y", "Z", "a", "b", "c", "d", "e", "f", "g", "h",
				"i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t",
				"u", "v", "w", "x", "y", "z", "（", "小", "包", "装", "）", "颗",
				"粒", "(", ")", "饮", "片", " ", "炒", "制", "袋", "[", "]", "精",
				"品", "*", "#" };
		for (int i = 0; i < str.length(); i++) {
			boolean found = false;
			char item = str.charAt(i);
			for (int j = 0; j < arr.length; j++) {
				if (arr[j].equals(item + "")) {
					found = true;
					break;
				}
			}
			if (!found) {
				result += item;
			}
		}
		return result;
	}

	/**
	 * 检测字符串是否不为空(null,"","null")
	 * 
	 * @param s
	 * @return 不为空则返回true，否则返回false
	 */
	public static boolean notEmpty(String s) {
		return s != null && !"".equals(s) && !"null".equals(s.toLowerCase());
	}

	/**
	 * 检测字符串是否为空(null,"","null")
	 * 
	 * @param s
	 * @return 为空则返回true，不否则返回false
	 */
	public static boolean isEmpty(String s) {
		return s == null || "".equals(s) || "null".equals(s.toLowerCase());
	}

	/**
	 * 字符串转换为字符串数组
	 * 
	 * @param str
	 *            字符串
	 * @param splitRegex
	 *            分隔符
	 * @return
	 */
	public static String[] str2StrArray(String str, String splitRegex) {
		return isEmpty(str) ? null : str.split(splitRegex);
	}

	/**
	 * 用默认的分隔符(,)将字符串转换为字符串数组
	 * 
	 * @param str
	 *            字符串
	 * @return
	 */
	public static String[] str2StrArray(String str) {
		return str2StrArray(str, ",\\s*");
	}

	/**
	 * 按照yyyy-MM-dd HH:mm:ss的格式，日期转字符串
	 * 
	 * @param date
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static String date2Str(Date date) {
		return date2Str(date, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 判断是否是空集合
	 * 
	 * @param list
	 * @return
	 */
	public static boolean isEmptyList(List<?> list) {
		return list == null || list.size() <= 0;
	}

	/**
	 * 按照yyyy-MM-dd HH:mm:ss的格式，字符串转日期
	 * 
	 * @param date
	 * @return
	 */
	public static Date str2Date(String date) {
		if (notEmpty(date)) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				return sdf.parse(date);
			} catch (ParseException e) {
				errorlogger.error(e.toString());
			}
			return new Date();
		} else {
			return null;
		}
	}

	/**
	 * 按照参数format的格式，日期转字符串
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static String date2Str(Date date, String format) {
		if (date != null) {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return sdf.format(date);
		} else {
			return "";
		}
	}

	public static Boolean objIsEmpty(Object str) {
		return str == null;
	}

	/** 获取当前时间戳返回字符串 */
	public static String currentTimeMillis() {
		Long Time = System.currentTimeMillis();
		String currentTimeMillis = Time.toString();
		return currentTimeMillis;
	}

	// 拼音生成，返回中文的首字母
	public static String getPinYinHeadChar(String str) {
		String convert = "";
		for (int j = 0; j < str.length(); j++) {
			char word = str.charAt(j);
			String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
			if (pinyinArray != null) {
				convert += pinyinArray[0].charAt(0);
			} else {
				convert += word;
			}
		}
		return convert;
	}

	/**
	 * 判断是否为数字
	 * 
	 * @param obj
	 *            需要验证的值
	 * @return true/false
	 */
	public static boolean isNumber(Object obj) {
		boolean result = false;
		if (obj instanceof String) {
			String str = (String) obj;
//			Pattern pattern = Pattern.compile("[0-9]*");

			Matcher isNum = NUMBER_PATTERN.matcher(str);
			if (isNum.matches()) {
				result = true;
			}
		} else if (obj instanceof Integer || obj instanceof Long
				|| obj instanceof Double || obj instanceof Float) {
			result = true;
		}
		return result;
	}

	public static String StringaddSpace(String str) {
		String sb = "";
		int length = str.length();
		for (int i = 0; i < length; i++) {
			sb += str.charAt(i) + " ";
		}

		return sb.toString();
	}

	/** 根据中文unicode判断输入的是不是中文 */
	public static String chineseToUnicode(String str) {
		String result = "";
		for (int i = 0; i < str.length(); i++) {
			int chr1 = (char) str.charAt(i);
			if (chr1 >= 19968 && chr1 <= 171941 && str.charAt(i) != '，'
					&& str.charAt(i) != '。' && str.charAt(i) != '（'
					&& str.charAt(i) != '）' && str.charAt(i) != '！'
					&& str.charAt(i) != '；' && str.charAt(i) != '、') {// 汉字范围
																		// \u4e00-\u9fa5
																		// (中文)
				result += str.charAt(i);
			} else {
				result += "";
			}
		}
		return result;
	}

	/** 判断最后一位字符是否是中文，不是中文就去掉 */
	public static String chineseToUnicodeTwo(String str) {
		String result = "";
		for (int i = 0; i < str.length(); i++) {
			int chr1 = (char) str.charAt(i);
			if (i == str.length() - 1) {
				if (chr1 >= 19968 && chr1 <= 171941 && str.charAt(i) != '，'
						&& str.charAt(i) != '。' && str.charAt(i) != '！'
						&& str.charAt(i) != '；' && str.charAt(i) != '、'
						&& str.charAt(i) != '（' && str.charAt(i) != '）'
						&& str.charAt(i) != ')') {// 汉字范围 \u4e00-\u9fa5 (中文)
					result += str.charAt(i);
				} else {
					result += "";
				}
			} else {
				result += str.charAt(i);
			}
		}
		return result;
	}

	/** 字符串中如果含有(),[]号，就把他和括号里面的字符截取了 */
	public static String chineseToUnicodeThere(String str) {
		String result = "";
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) == '（' || str.charAt(i) == '('
					|| str.charAt(i) == '[' || str.charAt(i) == '【'
					|| str.charAt(i) == '{') {
				break;
			} else {
				result += str.charAt(i);
			}
		}
		return result;
	}
	
	/**
	 * @param d	源数据
	 * @param digit 位数，默认为2位，	digit=4,则返回4位
	 * @return
	 */
	public static Double parseDouble(Double d,Integer...digit){
		DecimalFormat df = null;
		if(digit != null && digit[0] == 4){
			df = new DecimalFormat("##0.0000");
		} else if(digit[0] == 2){
			df = new DecimalFormat("##0.00");
		}
		return Double.parseDouble(df.format(d));
	}
	
	/**
	 * @param d
	 * @return 将double转成int类型
	 */
	public static Integer double2Int(Double d){
		long round = Math.round(d);
		return new Long(round).intValue();
	}

	/**
	 * 获取字符串真实长度
	 * @param str
	 * @return
	 */
	public static int getRealLength(String str) {
		int m = 0;
		char arr[] = str.toCharArray();
		for (int i = 0; i < arr.length; i++) {
			char c = arr[i];
			// 中文字符(根据Unicode范围判断),中文字符长度为2
			if ((c >= 0x0391 && c <= 0xFFE5)) {
				m = m + 2;
			} else if ((c >= 0x0000 && c <= 0x00FF)) // 英文字符
			{
				m = m + 1;
			}
		}
		return m;
	}
	/**
	 * 判断对象是否为空，且对象的所有属性都为空
	 * ps: boolean类型会有默认值false 判断结果不会为null 会影响判断结果
	 *     序列化的默认值也会影响判断结果
	 * @param object
	 * @return
	 */
	public static boolean objCheckIsNull(Object object){
		Class clazz = (Class)object.getClass(); // 得到类对象
		Field fields[] = clazz.getDeclaredFields(); // 得到所有属性
		boolean flag = true; //定义返回结果，默认为true
		for(Field field : fields){
			field.setAccessible(true);
			Object fieldValue = null;
			try {
				fieldValue = field.get(object); //得到属性值
				Type fieldType =field.getGenericType();//得到属性类型
				String fieldName = field.getName(); // 得到属性名
//				System.out.println("属性类型："+fieldType+",属性名："+fieldName+",属性值："+fieldValue);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			if(fieldValue != null){  //只要有一个属性值不为null 就返回false 表示对象不为null
				flag = false;
				break;
			}
		}
		return flag;
	}
}
