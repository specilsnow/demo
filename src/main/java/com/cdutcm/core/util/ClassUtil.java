package com.cdutcm.core.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description:类处理工具
 * @ClassName :com.cdutcm.common.util.ClassUtils.java
 * @Author :zhufj
 */
public final class ClassUtil {

	private static final Logger errorlogger = LoggerFactory.getLogger("error");

	/**
	 * @Description:通过反射,获得定义Class声明的父类的范型参数的类型. 如public BookManager extends
	 *                                           GenricManager<Book>
	 * @MethodName :getSuperClassGenricType
	 * @Author :zhufj
	 * @param clazz
	 *            :The class to introspect
	 * @return :the first generic declaration, or <code>Object.class</code> if
	 *         cannot be determined
	 */
	@SuppressWarnings("rawtypes")
	public static Class getSuperClassGenricType(Class clazz) {
		return getSuperClassGenricType(clazz, 0);
	}

	/**
	 * @Description:通过反射,获得定义Class声明的父类的范型参数的类型. 如public BookManager extends
	 *                                           GenricManager<Book>
	 * @MethodName :getSuperClassGenricType
	 * @Author :zhufj
	 * @param clazz
	 *            :The class to introspect
	 * @param index
	 *            :the Index of the generic ddeclaration,start from 0.
	 * @return the index generic declaration, or <code>Object.class</code> if
	 *         cannot be determined
	 */
	@SuppressWarnings("rawtypes")
	public static Class getSuperClassGenricType(Class clazz, int index) {
		/** 获取父类类型 */
		Type genType = clazz.getGenericSuperclass();
		/** 如果父类类型不是参数化的类型 */
		if (!(genType instanceof ParameterizedType)) {
			errorlogger.warn(clazz.getSimpleName()
					+ "'s superclass not ParameterizedType");
			return Object.class;
		}
		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
		if (index >= params.length || index < 0) {
			errorlogger.warn("Index: " + index + ", Size of "
					+ clazz.getSimpleName() + "'s Parameterized Type: "
					+ params.length);
			return Object.class;
		}
		if (!(params[index] instanceof Class)) {
			errorlogger
					.warn(clazz.getSimpleName()
							+ " not set the actual class on superclass generic parameter");
			return Object.class;
		}
		return (Class) params[index];
	}

	/**
	 * @Description:得到属性的get方法的方法名
	 * @MethodName :getGetMethodName
	 * @Author :zhufj
	 * @param fieldname
	 * @return
	 */
	public static String getGetMethodName(String fieldname) {
		if (fieldname == null) {
			return null;
		} else {
			return "get" + fieldname.substring(0, 1).toUpperCase()
					+ fieldname.substring(1);
		}
	}

	/**
	 * @Description:得到属性的set方法的方法名
	 * @MethodName :getSetMethodName
	 * @Author :zhufj
	 * @param fieldname
	 * @return
	 */
	public static String getSetMethodName(String fieldname) {
		if (fieldname == null) {
			return null;
		} else {
			return "set" + fieldname.substring(0, 1).toUpperCase()
					+ fieldname.substring(1);
		}
	}

	/**
	 * @Description:获取给定类中指定的方法
	 * @MethodName :getMethodByName
	 * @Author :zhufj
	 * @param clazz
	 * @param methodName
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Method getMethodByName(Class clazz, String methodName) {
		Method[] methods = clazz.getMethods();
		for (Method method : methods) {
			if (method.getName().equals(methodName)) {
				return method;
			}
		}
		return null;
	}

	/**
	 * 获取指定对象属性值
	 * 
	 * @param obj
	 *            需要获取属性的对象
	 * @param fieldName
	 *            属性的名字
	 * @return 值
	 */
	public static Object getFieldValue(Object obj, String fieldName) {
		Field field = getAccessibleField(obj, fieldName);
		if (field == null) {
			throw new IllegalArgumentException("在实体 [" + obj + "] 中不能找到属性 ["
					+ fieldName + "]");
		}
		Object result = null;
		try {
			result = field.get(obj);
		} catch (IllegalAccessException e) {
			errorlogger.error(e.toString());
		}
		return result;
	}

	/**
	 * 给指定对象属性赋值
	 * 
	 * @param obj
	 *            需要赋值的对象
	 * @param fieldName
	 *            需要赋值的属性名称
	 * @param value
	 *            值
	 */
	public static void setFieldValue(Object obj, String fieldName, Object value) {
		Field field = getAccessibleField(obj, fieldName);
		if (field == null) {
			throw new IllegalArgumentException("Could not find field ["
					+ fieldName + "] on target [" + obj + "]");
		}
		try {
			field.set(obj, value);
		} catch (IllegalAccessException e) {
			errorlogger.error(e.toString());
		}
	}

	/**
	 * 循环向上转型, 获取对象的DeclaredField, 并强制设置为可访问. 如向上转型到Object仍无法找到, 返回null.
	 */
	public static Field getAccessibleField(Object obj, String fieldName) {
		Field result = null;
		if (null != obj && null != fieldName) {
			for (Class<?> superClass = obj.getClass(); superClass != Object.class; superClass = superClass
					.getSuperclass()) {
				try {
					Field field = superClass.getDeclaredField(fieldName);
					field.setAccessible(true);
					result = field;
					break;
				} catch (NoSuchFieldException e) {

				}
			}
		}
		return result;
	}

	/**
	 * @param oldObj
	 * @param newObj
	 * @return
	 * @throws Exception
	 *             Description：复制对象，支持对象之间的继承
	 */
	public static Object copyObject(Object oldObj, Object newObj)
			throws Exception {
		List<Method> lists = new ArrayList<Method>();
		for (Class<?> clazz = oldObj.getClass(); clazz != Object.class; clazz = clazz
				.getSuperclass()) {
			lists.addAll(Arrays.asList(clazz.getDeclaredMethods()));
		}
		for (Class<?> clazz = newObj.getClass(); clazz != Object.class; clazz = clazz
				.getSuperclass()) {
			Method[] method = clazz.getDeclaredMethods();
			for (Method m : method) {
				String mName = m.getName();
				if (mName.startsWith("set")) {
					for (Method emrMethod : lists) {
						String getMethod = "g".concat(mName.substring(1,
								mName.length()));
						if (getMethod.equals(emrMethod.getName())) {
							m.invoke(newObj, emrMethod.invoke(oldObj));
							break;
						}
					}
				}
			}
		}
		return newObj;
	}

	/**
	 * @param oldObj
	 * @param newObj
	 * @return
	 * @throws Exception
	 *             Description：复制对象，支持对象之间的继承
	 */
	public static Object copyObjectWithOutId(Object oldObj, Object newObj)
			throws Exception {
		List<Method> lists = new ArrayList<Method>();
		for (Class<?> clazz = oldObj.getClass(); clazz != Object.class; clazz = clazz
				.getSuperclass()) {
			lists.addAll(Arrays.asList(clazz.getDeclaredMethods()));
		}
		for (Class<?> clazz = newObj.getClass(); clazz != Object.class; clazz = clazz
				.getSuperclass()) {
			Method[] method = clazz.getDeclaredMethods();
			for (Method m : method) {
				String mName = m.getName();
				if (mName.startsWith("set")) {
					for (Method emrMethod : lists) {
						String getMethod = "g".concat(mName.substring(1,
								mName.length()));
						if (getMethod.equals(emrMethod.getName())) {
							if (!"getId".equals(getMethod)) {
								m.invoke(newObj, emrMethod.invoke(oldObj));
								break;
							}
						}
					}
				}
			}
		}
		return newObj;
	}
}
