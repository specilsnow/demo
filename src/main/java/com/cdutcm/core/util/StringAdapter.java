package com.cdutcm.core.util;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * @author zhufj
 *  
 *  解析xml适配空字符串为空节点
 *
 */
public class StringAdapter extends XmlAdapter<String, String>{

	@Override
	public String unmarshal(String string) throws Exception {
		if("".equals(string)){
			return null;
		}
		return string;
	}

	@Override
	public String marshal(String string) throws Exception {
		if(null == string){
			return "";
		}
		return string;
	}

}
