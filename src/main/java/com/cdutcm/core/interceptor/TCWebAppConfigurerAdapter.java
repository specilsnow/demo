package com.cdutcm.core.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author zhufj web 拦截器
 * 
 */
@Configuration
public class TCWebAppConfigurerAdapter extends WebMvcConfigurerAdapter {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// registry.addInterceptor(new PagePlugin()).addPathPatterns("/**"); //
		// 分页插件
		// super.addInterceptors(registry);
		registry.addInterceptor(new URLInterceptor());
//		registry.addInterceptor(new LoginHandlerInterceptor());
		super.addInterceptors(registry);
	}
}
