package com.cdutcm.core.excel;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public final class ClassUtil {

	public static Field[] getClassProperty(Object obj) {
		return obj.getClass().getDeclaredFields();
	}

	/**
	 * 通过反射调用方法
	 * 
	 * @param bean
	 *            需要操作的实体对象
	 * @param property
	 *            方法名
	 * @param value
	 *            方法参数
	 * @throws Exception
	 */
	public static void methodInvoke(Object bean, String property, Object value)
			throws Exception {
		for (Field field : getClassProperty(bean)) {
			if (field.getName().equals(property)) {
				// 强制访问私有属性
				field.setAccessible(true);
				field.set(bean, value);
			}
		}
	}

	public static Type getGenericSuperclass(Object obj) {
		return ((ParameterizedType) obj.getClass().getGenericSuperclass())
				.getActualTypeArguments()[0];
	}
}
