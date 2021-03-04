package com.cdutcm.core.util;


import com.cdutcm.tcms.biz.model.AnalyzeEntity;
import com.cdutcm.tcms.biz.model.EmrImgifo;
import com.cdutcm.tcms.biz.model.StaticsEmr;
import com.cdutcm.tcms.biz.model.UserInfo;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MapperUtils {

    private static Pattern humpPattern = Pattern.compile("[A-Z]");
    /**
     * 生成ResultMap的方法
     */
    public static String getResultMap(Class<?> clazz){

        Object obj = null;
        try{
            obj = clazz.newInstance();
        }catch (Exception e){
            return "#Exception.反射生成实体异常#";
        }

        String clazzName = clazz.getSimpleName();
        String resultMapId = Character.toLowerCase(clazzName.charAt(0))+clazzName.substring(1)+"Map";
        String pkgName = clazz.getName();

        StringBuilder resultMap = new StringBuilder();
        resultMap.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" +
                "<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\" >\n");
        resultMap.append("<mapper namespace=\""+getNameSpace(pkgName)+"\">\n");
        resultMap.append("<resultMap id=\"");
        resultMap.append(resultMapId);
        resultMap.append("\" type=\"");
        resultMap.append(pkgName);
        resultMap.append("\">\n");

        Field[] fields = clazz.getDeclaredFields();
        for(Field f : fields){
            String property = f.getName();
            String javaType = f.getType().getName();
            if("serialVersionUID".equals(property)){
                continue;//忽略掉这个属性
            }
            resultMap.append("    <result column=\"");
            resultMap.append(property2Column(property).toLowerCase());
            resultMap.append("\" jdbcType=\"");
            resultMap.append(javaType2jdbcType(javaType.toLowerCase()));
            resultMap.append("\" property=\"");
            resultMap.append(property);
            resultMap.append("\" />\n");
        }
        resultMap.append("</resultMap>\n");
        resultMap.append(genInsertMap(clazz));
        resultMap.append(genUpdateMap(clazz));
        resultMap.append("</mapper>\n");
        return resultMap.toString();
    }
    /**
     * 获取命名空间
     */
    public static String getNameSpace(String pkName){
        //1.截取包名
        //2.替换对象包为mapper
        //3.在对象后添加mapper
        int end =  pkName.lastIndexOf(".");
        int start = pkName.lastIndexOf(".",end-1)+1;
        return pkName.replace(pkName.substring(start,end),"mapper")+"Mapper";
    }

    /**
     * 获取列
     * @param property
     * @return
     */
    private static String property2Column(String property){
        Matcher matcher = humpPattern.matcher(property);
        StringBuffer sb = new StringBuffer();
        while(matcher.find()){
            matcher.appendReplacement(sb, "_"+matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        String column = sb.toString();
        if(column.substring(0,1).equals("_")){
            return column.substring(1,column.length());
        }
        return sb.toString();
    }
    /**
     * java类型和数据库类型互转
     * @param javaType
     * @return
     */
    private static String javaType2jdbcType(String javaType){
        if(javaType.contains("string")){
            return "VARCHAR";
        }else if(javaType.contains("boolean")){
            return "BIT";
        }else if(javaType.contains("byte")){
            return "TINYINT";
        }else if(javaType.contains("short")){
            return "SMALLINT";
        }else if(javaType.contains("int")){
            return "INTEGER";
        }else if(javaType.contains("long")){
            return "BIGINT";
        }else if(javaType.contains("double")){
            return "DOUBLE";
        }else if(javaType.contains("float")){
            return "REAL";
        }else if(javaType.contains("date")){
            return "DATE";
        }else if(javaType.contains("timestamp")){
            return "TIMESTAMP";
        }else if(javaType.contains("time")){
            return "TIME";
        }else if(javaType.contains("bigdecimal")){
            return "DECIMAL";
        }else{
            return "未知类型";
        }
    }
    /**
     * 生成插入语句
     *
     */
    public static String genInsertMap(Class<?> clazz){
        StringBuffer sb = new StringBuffer();
        String pkgName = clazz.getName();
        String tableName = getTabNameByPkName(pkgName);
        sb.append("<insert id=\"insert\" parameterType=\""+pkgName+"\">\r\n");
        HashMap hashMap = getValuesAppend(clazz);
        sb.append("  insert into "+tableName+"("+hashMap.get("columns")+") values("+hashMap.get("values")+")\r\n");
        sb.append("</insert>\r\n");
        return sb.toString();
    }
    //通过包名获取表名
    public static String getTabNameByPkName(String pkgName){
        String tableName = pkgName.substring(pkgName.lastIndexOf('.')+1,pkgName.length());
        return property2Column(tableName);
    }
    /**
     * 生成更新语句
     */
    public static String genUpdateMap(Class<?> clazz){
        Field[] fields = clazz.getDeclaredFields();
        StringBuffer sb = new StringBuffer();
        String pkgName = clazz.getName();
        String tableName = getTabNameByPkName(pkgName);
        sb.append("<update id=\"update\" parameterType=\""+pkgName+"\">\r\n");
        StringBuffer setAppend = new StringBuffer();
        sb.append("  update "+tableName+" set\r\n");
        int length = fields.length;
        for (int i = 1; i < length; i++) {
            Field field = fields[i];
            String property = fields[i].getName();
            if(i==length-1){
                sb.append("    "+property2Column(fields[i].getName()).toLowerCase() +"="+"#{"+fields[i].getName()+",jdbcType="+javaType2jdbcType(fields[i].getType().getName().toLowerCase())+"}\r\n");
            }else {
                sb.append("    "+property2Column(fields[i].getName()).toLowerCase() +"="+"#{"+fields[i].getName()+",jdbcType="+javaType2jdbcType(fields[i].getType().getName().toLowerCase())+"},\r\n");
            }

        }
        sb.append("  where "+property2Column(fields[0].getName()).toLowerCase() +"="+"#{"+fields[0].getName()+",jdbcType="+javaType2jdbcType(fields[0].getType().getName().toLowerCase())+"}\r\n");
        sb.append("</update>\r\n");
        return sb.toString();
    }


    /**
     * 获取列和传入数据的字符串
     * @param clazz
     * @return
     */
    public static HashMap getValuesAppend(Class<?>clazz){
        HashMap hashMap = new HashMap();
        Field[] fields = clazz.getDeclaredFields();
        StringBuffer columnAppend = new StringBuffer();
        StringBuffer valuesAppend = new StringBuffer();
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            String property = field.getName();
            if(i<fields.length-1){
                columnAppend.append(property2Column(property).toLowerCase()+",");
                valuesAppend.append("#{");
                valuesAppend.append(property);
                valuesAppend.append("}");
                valuesAppend.append(",");
            }else {
                columnAppend.append(property2Column(property).toLowerCase());
                valuesAppend.append("#{");
                valuesAppend.append(property);
                valuesAppend.append("}");
            }
        }
        hashMap.put("columns",columnAppend);
        hashMap.put("values",valuesAppend);
        return hashMap;
    }
    public static void main(String[] args) {
        Class clazz  =  StaticsEmr.class;
////        System.out.println(genInsertMap(clazz));
        System.out.println(getResultMap(clazz));
//        System.out.println(getNameSpace(clazz.getName()));
    }

}
