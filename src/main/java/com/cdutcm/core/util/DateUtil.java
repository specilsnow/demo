package com.cdutcm.core.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @Description:日期工具类
 * @ClassName :com.cdutcm.common.util.DateUtil.java
 * @Author :zhufj
 */
public class DateUtil {
	// 日期格式
	public final static String YMD = "yyyy-MM-dd";
	public final static String YMDHMS = "yyyy-MM-dd HH:mm:ss";
	public final static String YMDHMS_ = "yyyy/MM/dd HH:mm:ss";
	public static final String YMDHM = "yyyy-MM-dd HH:mm";
	public static final String YYYYMMDDHHMMSS = "yyyyMMddhh:mm:ss";
	public static final String YYYYMMDD = "yyyyMMdd";
	public static final String YYYY_MM_DD = "yyyy-MM-dd";
	public static final String YYYYMMDD_HHMMSS = "yyyy-MM-dd-hh.mm.ss";

	public final static int ERA = 0;
	public final static int YEAR = 1;
	public final static int MONTH = 2;
	public final static int WEEK_OF_YEAR = 3;
	public final static int WEEK_OF_MONTH = 4;
	public final static int DATE = 5;
	public final static int DAY_OF_MONTH = 5;
	public final static int DAY_OF_YEAR = 6;
	public final static int DAY_OF_WEEK = 7;
	public final static int DAY_OF_WEEK_IN_MONTH = 8;
	public final static int HOUR = 10;
	public final static int HOUR_OF_DAY = 11;
	public final static int MINUTE = 12;
	public final static int SECOND = 13;
	public final static int MILLISECOND = 14;

	/**
	 * 将日期转换为 yyyy-MM-dd 格式的字符串
	 * @param date 需要转换的日期
	 * @return 字符串
	 */
	public static String format(Date date) {
		return DateUtil.format(date, DateUtil.YMD);
	}
	/**
	 * @Description:将日期转换为字符串，格式为"yyyy-MM-dd"
	 * @MethodName :format
	 * @Author :zhufj
	 * @param date 需要转换的日期
	 * @return 日期字符串
	 */
	public static String formatF(Date date) {
		return DateUtil.format(date, DateUtil.YMDHMS_);
	}
	
	public static Date getDate(Date date,String pattern){
		return parse(format(date, pattern));
	}
	
	/**
	 * @param date
	 * @return
	 * @Description:db2的日期转换
	 */
	public static String formatDB2(Date date) {
		return DateUtil.format(date, DateUtil.YYYYMMDD_HHMMSS);
	}
	
	/**
	 * @param date
	 * @return
	 * @Description:db2的日期转换
	 */
	public static String formatYYMMDD(Date date) {
		return DateUtil.format(date, DateUtil.YYYY_MM_DD);
	}
	/**
	 * @param date
	 * @return
	 * @Description:转成YMDHM格式
	 */
	public static String formatYMDHM(Date date) {
		return DateUtil.format(date, DateUtil.YMDHM);
	}
	/**
	 * @param date
	 * @return
	 * @Description:转成YMDHM格式
	 */
	public static String parseFormatYMDHM(String date) {
		Date d = DateUtil.parse(date, DateUtil.YMDHM);
		return DateUtil.format(d, DateUtil.YMDHM);
	}
	
	public static String parseFormatYYYYMMDDHHMMSS(String date){
		Date d = DateUtil.parse(date, DateUtil.YYYYMMDDHHMMSS);
		return DateUtil.format(d,DateUtil.YMDHM);
	}
	/**
	 * @Description:将日期转换为字符串，格式为"yyyy-MM-dd HH:mm:ss"
	 * @MethodName :formatTime
	 * @Author :zhufj
	 * @param date 需要转换的日期
	 * @return 日期字符串
	 */
	public static String formatTime(Date date) {
		return DateUtil.format(date, DateUtil.YMDHMS);
	}
	/**
	 * @Description:将日期转换为字符串，可自定义格式
	 * @MethodName :format
	 * @Author :zhufj
	 * @param date 需要转换的日期
	 * @param pattern 转换格式
	 * @return 日期字符串
	 */
	public static String format(Date date, String pattern) {
		DateFormat df = new SimpleDateFormat(pattern);
		if(date != null) {
			return df.format(date);
		}
		return null;
	}
	/**
	 * @Description:将字符串转换为日期，格式为"yyyy-MM-dd"
	 * @MethodName :parse
	 * @Author :zhufj
	 * @param strDate
	 * @return
	 */
	public static Date parse(String strDate) {
		return parse(strDate, DateUtil.YMD);
	}
	/**
	 * @Description:将字符串转换为日期，格式为"yyyy-MM-dd HH:mm:ss"
	 * @MethodName :parseTime
	 * @Author :zhufj
	 * @param strDate
	 * @return
	 */
	public static Date parseTime(String strDate) {
		return parse(strDate, DateUtil.YMDHMS);
	}
	/**
	 * @Description:将字符串转换为日期，自定义格式
	 * @MethodName :parse
	 * @Author :zhufj
	 * @param strDate
	 * @param pattern
	 * @return
	 */
	public static Date parse(String strDate, String pattern) {
		DateFormat df = new SimpleDateFormat(pattern);
		try {
			return df.parse(strDate);
		} catch(ParseException e) {
			return null;
		}
	}
	/**
	 * @Description:判断 d1是否早于d2
	 * @MethodName :before
	 * @Author :zhufj
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static boolean before(Date d1, Date d2) {
		return d1.before(d2);
	}
	/**
	 * @Description:判断 d1是否晚于d2
	 * @MethodName :after
	 * @Author :zhufj
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static boolean after(Date d1, Date d2) {
		return d1.after(d2);
	}
	/**
	 * @Description:设置日期某一部分的值
	 * @MethodName :set
	 * @Author :zhufj
	 * @param date 日期
	 * @param field 日期改变部分,如DateUtil.YEAR,DateUtil.MONTH。。。
	 * @param value 设置的值
	 * @return
	 */
	public static Date set(Date date, int field, int value) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(field, value);
		return cal.getTime();
	}
	/**
	 * @Description:增加/减少日期某一部分的值
	 * @MethodName :add
	 * @Author :zhufj
	 * @param date 日期
	 * @param field 日期改变部分,如DateUtil.YEAR,DateUtil.MONTH。。。
	 * @param amount 增加/减少的值:正数为增加，负数为减少
	 * @return
	 */
	public static Date add(Date date, int field, int amount) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(field, amount);
		return cal.getTime();
	}
	/**
	 * @Description:取得日期某一部分最大可能的数值（如某一月的最大天数）
	 * @MethodName :getMaximum
	 * @Author :zhufj
	 * @param date 日期
	 * @param field 日期部分，如DateUtil.YEAR,DateUtil.MONTH。。。
	 * @return
	 */
	public static int getMaximum(Date date, int field) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.getMaximum(field);
	}
	/**
	 * @Description:通过设置年月日来得到日期
	 * @MethodName :getDate
	 * @Author :zhufj
	 * @param year
	 * @param month
	 * @param date
	 * @return
	 */
	public static Date getDate(int year, int month, int date) {
		return getDate(year, month, date, 0, 0, 0);
	}
	/**
	 * @Description:通过设置年,月,日,小时,分来得到日期
	 * @MethodName :getDate
	 * @Author :zhufj
	 * @param year
	 * @param month
	 * @param date
	 * @param hourOfDay
	 * @param minute
	 * @return
	 */
	public static Date getDate(int year, int month, int date, int hourOfDay, int minute) {
		return getDate(year, month, date, hourOfDay, minute, 0);
	}
	/**
	 * @Description:通过设置年,月,日,小时,分，秒来得到日期
	 * @MethodName :getDate
	 * @Author :zhufj
	 * @param year
	 * @param month
	 * @param date
	 * @param hourOfDay
	 * @param minute
	 * @param second
	 * @return 日期
	 */
	public static Date getDate(int year, int month, int date, int hourOfDay, int minute, int second) {
		Calendar cal = Calendar.getInstance();
		cal.clear();
		cal.set(year, month, date, hourOfDay, minute, second);
		return cal.getTime();
	}
	/**
	 * @Description:得到星期
	 * @MethodName :getWeek
	 * @Author :zhufj
	 * @param date
	 * @return
	 */
	public static Integer getWeek(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int i = cal.get(Calendar.DAY_OF_WEEK);
		if(i != 1) {
			i--;
		} else {
			i = 7;
		}
		return i;
	}
	/**
	 * 获取前几天或者后几天的时间
	 * @param num 0.当前时间，小于0过去时间，大于0未来时间
	 * @return 日期
	 */
	public static Date getLastOrNextDate(int num) {
		Date result = null;
		if(num == 0) {
			result = new Date();
		} else {
			Calendar calendar = Calendar.getInstance();
			calendar.add(DAY_OF_MONTH, num);
			result = calendar.getTime();
		}
		return result;
	}
	/**
	 * 获取指定时间年份
	 * @param times 日期对象或日期字符串，若不传参，则获取当前时间年份
	 * @return 年份
	 */
	public static int getYear(Object... times) {
		return getCalendar(times).get(Calendar.YEAR);
	}
	/**
	 * 获取指定时间月份
	 * @param times 日期对象或日期字符串，若不传参，则获取当前时间月份
	 * @return 月份
	 */
	public static int getMonth(Object... times) {
		return getCalendar(times).get(Calendar.MONTH);
	}
	/**
	 * 获取指定时间当月第几日
	 * @param times 日期对象或日期字符串，若不传参，则获取当前时间月份对应号数
	 * @return 号数
	 */
	public static int getDayOfMonth(Object... times) {
		return getCalendar(times).get(Calendar.DAY_OF_MONTH);
	}
	public static Date getNextYear() {
		return null;
	}
	public static Date getNextMonth() {
		return null;
	}
	/**
	 * 获取指定日期当月当日的下一天
	 * @param times 日期对象或日期字符串，若不传参，则获取当前时间月份当日下一天
	 * @return 号数
	 */
	public static Date getNextDayOfMonth(Object... times) {
		Calendar cal = Calendar.getInstance();
		if(times != null && times.length > 0) {
			Object time = times[0];
			if(time instanceof Date) {
				cal.setTime((Date)time);
			} else if(time instanceof String) {
				cal.setTime(parse((String)time));
			} else {
				cal.setTime(new Date());
			}
		} else {
			cal.setTime(new Date());
		}
		int day = cal.get(Calendar.DATE);
		cal.set(Calendar.DATE, day + 1);
		return cal.getTime();
	}
	/**
	 * 通过传入的生日得到具体年龄
	 * @param birthday 生日
	 * @return 年龄
	 */
	public static Integer getAge(Date birthday) {
		Integer result = 0;
		Calendar calendar = Calendar.getInstance();
		if(birthday == null) { return 0; }
		if(calendar.getTime().after(birthday)) {
			// 获取系统年、月、日
			int sysYear  = calendar.get(Calendar.YEAR);
			int sysMonth = calendar.get(Calendar.MONTH) + 1;
			int sysDay   = calendar.get(Calendar.DAY_OF_MONTH);
			// 获取用户生日年、月、日
			calendar.setTime(birthday);
			int userYear  = calendar.get(Calendar.YEAR);
			int userMonth = calendar.get(Calendar.MONTH) + 1;
			int userDay   = calendar.get(Calendar.DAY_OF_MONTH);
			int age = sysYear - userYear;
			// 计算年龄
			if(sysMonth <= userMonth) {
	            if(sysMonth == userMonth) {
	                if(sysDay < userDay) {
	                    age--;
	                }
	            } else {
	                age--;
	            }
	        }
			result = age;
		}
		return result;
	}
	/**
	 * 通过年龄计算默认出生日期
	 * @param age 年龄
	 * @param agedw 年龄单位： 1-年 2-month 3-day 4-hour
	 * @return
	 */
	public static String getBirthdayDate(int age, int agedw) {
		// 获取现在时间
		Calendar calendar = Calendar.getInstance();
		// 获取年份
		int year = calendar.get(Calendar.YEAR);
		// 获取月份
		int month = calendar.get(Calendar.MONTH) + 1;
		// 获取日期
		int day = calendar.get(Calendar.DATE);
		// 获取小时
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		// 获取分钟
		int minute = calendar.get(Calendar.MINUTE);
		// 获取秒钟
		int second = calendar.get(Calendar.SECOND);
		String date = null;
		if (agedw == 1) {
			year = year - age;
		}
		if (agedw == 2) {
			month = month - age;
		}
		if (agedw == 3) {
			day = day - age;
		}
		if (agedw == 4) {
			hour = hour - age;
		}
		// 拼接日期
		date = year + "-" + (month < 10 ? "0" + month : month) + "-" + (day < 10 ? "0" + day : day);
		// 拼接时间
		String time = (hour < 10 ? "0" + hour : hour) + ":"
					+ (minute < 10 ? "0" + minute : minute) + ":"
					+ (second < 10 ? "0" + second : second);
		// 拼接日期和时间
		String dateTime = date + " " + time;
		return dateTime;
	}

	private static Calendar getCalendar(Object... times) {
		Calendar cal = Calendar.getInstance();
		if(times != null && times.length > 0) {
			Object time = times[0];
			if(time instanceof Date) {
				cal.setTime((Date)time);
			} else if(time instanceof String) {
				cal.setTime(parse((String)time));
			} else {
				cal.setTime(new Date());
			}
		} else {
			cal.setTime(new Date());
		}
		return cal;
	}
}
