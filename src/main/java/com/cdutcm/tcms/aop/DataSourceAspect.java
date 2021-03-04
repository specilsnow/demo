package com.cdutcm.tcms.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import com.cdutcm.core.database.DataSourceHolder;

/**
 * @author zhufj
 * @Description 数据源自动切换的AOP实现
 * @Date 2017-6-7
 */
@Aspect
@Component
public class DataSourceAspect {

	/**
	 * 监听ServiceImpl所有方法 cws.service： 设置数据源为dsCws kb.service : 设置数据源为dsKb
	 * kbbd.service:设置数据源为dsKbbd
	 * 
	 * @param point
	 */
	@Before("execution(* com.cdutcm.tcms..*ServiceImpl.*(..))")
	public void setDataSource(JoinPoint point) {
		String target = point.getTarget().toString();
		if (target.contains("biz.service")) {
			DataSourceHolder.setDataSource("dsDh");
		}
		if(target.contains("junqu.service")){
			DataSourceHolder.setDataSource("dsHis");
		}
	}

}
