package com.cdutcm.core.exception;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cdutcm.core.message.Header;
import com.cdutcm.tcms.aop.SysLogAspect;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	 private static final Logger logger = LoggerFactory.getLogger(SysLogAspect.class);

	@ExceptionHandler(RuntimeException.class)
	@ResponseBody
	public Header exceptionHandler(RuntimeException re){
		Header header = new Header();
		header.setResultCode("0");
		header.setResultInfo(re.getMessage());
		re.printStackTrace();
		logger.error("【打印抛出的异常信息】{}",re.getMessage());
		return header;
	}
 }
