package com.cdutcm.core.util;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;

import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zhufj
 * @Created on 2014-12-10
 * @Description XML和对象相互转换
 */
@SuppressWarnings("unchecked")
public class JAXBUtil<T> {

	private static final Logger errorlogger = LoggerFactory.getLogger("error");

	/**
	 * @param str
	 * @param t
	 * @return
	 * @throws JAXBException
	 * @throws FileNotFoundException
	 * @throws UnsupportedEncodingException
	 * @Description: 将xml字符串转成对象
	 */
	public T JAXBunmarshal(String str, T t) throws JAXBException,
			FileNotFoundException, UnsupportedEncodingException {
		InputStream inputStream = new ByteArrayInputStream(
				str.getBytes("UTF-8"));
		return (T) JAXB.unmarshal(inputStream, t.getClass());
	}

	/**
	 * @param t
	 * @encoding 编码
	 * @return
	 * @Description: 将对象转成xml
	 */
	public String JAXBmarshal(T t, String encoding) {
		try {
			JAXBContext context = JAXBContext.newInstance(t.getClass());
			Marshaller marshaller = context.createMarshaller();
			// 是否省略xml头声明信息
			marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, false);
			marshaller.setProperty(Marshaller.JAXB_ENCODING, encoding);
			StringWriter writer = new StringWriter();
			// ByteArrayOutputStream os = new ByteArrayOutputStream();
			marshaller.marshal(t, writer);
			return writer.toString();
		} catch (Exception e) {
			errorlogger.error(e.toString());
			return "";
		}
		// OutputStream outputStream = new ByteArrayOutputStream();
		// JAXB.marshal(t, outputStream);
		// return outputStream.toString();
	}
}
