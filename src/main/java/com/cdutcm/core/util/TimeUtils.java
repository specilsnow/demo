package com.cdutcm.core.util;



import org.apache.commons.lang.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class TimeUtils {

    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 获取当前时间
     * @return 时间 yyyy-MM-dd HH:ss:mm
     */
    public static String getNowDateTime(){
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(DATETIME_FORMAT);
        String dateString = formatter.format(currentTime);
        return dateString;
    }


    /**
     * 获取当前日期
     * @return 日期 yyyy-MM-dd
     */
    public static String getNowDate(){
        Date today = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
        String dateString = formatter.format(today);
        return dateString;
    }

    /**
     * 得到当前毫秒时间戳
     * @return 毫秒时间戳
     */
    public static long getCurrentTimestamp(){
        return System.currentTimeMillis();
    }

    /**
     * 获取当前秒时间戳
     * @return 秒时间戳
     */
    public static int getSecondTimestamp(){
        return (int) (System.currentTimeMillis() / 1000);
    }


    /**
     * 获取指定日期的时间戳
     * @param date 日期 yyyy-MM-dd
     * @return 时间戳（秒）
     */
    public static int getSecondTimestamp(String dateStr){
        if(StringUtils.isEmpty(dateStr)){
            return 0;
        }
        try {
            Date date = new SimpleDateFormat(DATE_FORMAT).parse(dateStr);
            String timestamp = String.valueOf(date.getTime() / 1000);
            return Integer.valueOf(timestamp);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取未来xx天后现在的时间
     * @param day 未来xx天
     * @return 时间 yyyy-MM-dd HH:ss:mm
     */
    public static String getFutureDateTime(int day) {
        Date date = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DATE, day);
        date = calendar.getTime();
        SimpleDateFormat formatter = new SimpleDateFormat(DATETIME_FORMAT);
        String dateString = formatter.format(date);
        return dateString;
    }

    /**
     * 获取未来xx天的日期
     * @param day xx天
     * @return 日期 yyyy-MM-dd
     */
    public static String getFutureDate(int day) {
        Date date = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DATE, day);
        date = calendar.getTime();
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
        String dateString = formatter.format(date);
        return dateString;
    }


    /**
     * 获取距今天前xx天的日期
     * @param day 前xx天
     * @return 日期：yyyy-MM-dd
     */
    public static String getBeforeDate(int day) {
        day = day - 1;
        Calendar now = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        now.set(Calendar.DATE, now.get(Calendar.DATE) - day - 1);
        now.set(Calendar.HOUR_OF_DAY, 0);
        now.set(Calendar.MINUTE, 0);
        now.set(Calendar.SECOND, 0);
        return sdf.format(now.getTime());
    }

    /**
     * 获取两个日期之间相差的天数
     * @param startDate 开始日期 yyyy-MM-dd
     * @param endDate 结束日期 yyyy-MM-dd
     * @return 相差天数
     */
    public static int getCompareDate(String startDate, String endDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        try {
            Date tempDate1 = dateFormat.parse(startDate);
            Date tempDate2 = dateFormat.parse(endDate);
            long l = tempDate2.getTime() - tempDate1.getTime();
            long day = l / (24 * 60 * 60 * 1000);
            return (int) day;
        } catch (ParseException e) {
            throw new RuntimeException("String转换成java.util.Date时异常");
        }
    }

    /**
     * 得到某个日期是星期几
     * @param dateStr 日期 yyyy-MM-dd
     * @return 1=星期一,2=星期二,3=星期三,4=星期四,5=星期五,6=星期六,7=星期天
     */
    public static int getWeekDate(String dateStr) {
        SimpleDateFormat df = new SimpleDateFormat(DATE_FORMAT);
        Date date= null;
        try {
            date = df.parse(dateStr);
        } catch (ParseException e) {
            throw new RuntimeException("String转换成java.util.Date时异常");
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        if(c.get(Calendar.DAY_OF_WEEK) - 1 == 0){
            return 7;
        }else {
            return c.get(Calendar.DAY_OF_WEEK) - 1;
        }
    }

    /**
     * 得到当前周周几的日期(按照中国人的习惯,周一是每周的第一天,周日是每周的最后一天)
     * @param index 周几：1-7 (1星期一,2星期二,2星期三,4星期四,5星期五,6星期六,7星期天)
     * @return 日期：yyyy-MM-dd
     */
    public static String getDateByWeekday(int index){
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());//设置当前日期

        //判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
        if(1 == dayWeek){
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        //设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        //获得当前日期是一个星期的第几天
        int weekIndex = cal.get(Calendar.DAY_OF_WEEK);
        //根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek()-weekIndex);
        //根据传参得到周几的日期
        cal.add(Calendar.DATE, (index-1));
        String dayStr = sdf.format(cal.getTime());
        return dayStr;
    }


}
