package com.cdutcm.core.message;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.cdutcm.core.util.StringAdapter;

@XmlRootElement(name="Head") 
@XmlAccessorType(XmlAccessType.FIELD)
@XmlJavaTypeAdapter(value=StringAdapter.class,type=String.class)
public class Header {

	@XmlElement(name="resutlCode")
	private String resultCode;
	
	@XmlElement(name="resutlInfo")
	private String resultInfo;
	
	public Header() {
		super();
	}

	public String getResultCode() {
		return resultCode;
	}

	public Header(String resultCode, String resultInfo) {
		super();
		this.resultCode = resultCode;
		this.resultInfo = resultInfo;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultInfo() {
		return resultInfo;
	}

	public void setResultInfo(String resultInfo) {
		this.resultInfo = resultInfo;
	}

}
