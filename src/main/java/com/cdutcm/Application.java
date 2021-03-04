package com.cdutcm;

import java.util.HashMap;
import java.util.Map;


import org.beetl.core.resource.ClasspathResourceLoader;
import org.beetl.ext.spring.BeetlGroupUtilConfiguration;
import org.beetl.ext.spring.BeetlSpringViewResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.scheduling.annotation.EnableScheduling;
import com.cdutcm.tcms.shiro.ShiroExt;
@SpringBootApplication
@EnableScheduling
@ComponentScan("com.cdutcm")
public class Application extends SpringBootServletInitializer {

	private final static Logger log= LoggerFactory
			.getLogger(Application.class);

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		log.info("【v0.2程序启动】");
	}
	 @Bean
	 public EmbeddedServletContainerCustomizer containerCustomizer(){
	        return new EmbeddedServletContainerCustomizer() {
	        	
	            @Override
	            public void customize(ConfigurableEmbeddedServletContainer container) {
	                 container.setSessionTimeout(1000000);//单位为S
	           }
	     };
	 }

	@Override
	protected SpringApplicationBuilder configure(
			SpringApplicationBuilder applicationBuilder) {
		return applicationBuilder.sources(Application.class);
	}

	@Bean(initMethod = "init", name = "beetlConfig")
	public BeetlGroupUtilConfiguration getBeetlGroupUtilConfiguration() {

		BeetlGroupUtilConfiguration beetlGroupUtilConfiguration = new BeetlGroupUtilConfiguration();
		ResourcePatternResolver patternResolver = ResourcePatternUtils
				.getResourcePatternResolver(new DefaultResourceLoader());
		try {
			ClasspathResourceLoader cploder = new ClasspathResourceLoader(
					"templates/");
			beetlGroupUtilConfiguration.setResourceLoader(cploder);

			beetlGroupUtilConfiguration.setConfigFileResource(patternResolver
					.getResource("classpath:beetl.properties"));
			Map<String, Object> functionPackages=new HashMap<String, Object>();
  			functionPackages.put("so", new ShiroExt());
  			beetlGroupUtilConfiguration.setFunctionPackages(functionPackages);
			return beetlGroupUtilConfiguration;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}


	@Bean(name = "beetlViewResolver")
	public BeetlSpringViewResolver getBeetlSpringViewResolver(
			@Qualifier("beetlConfig") BeetlGroupUtilConfiguration beetlGroupUtilConfiguration) {
		BeetlSpringViewResolver beetlSpringViewResolver = new BeetlSpringViewResolver();
		beetlSpringViewResolver.setContentType("text/html;charset=UTF-8");
		beetlSpringViewResolver.setOrder(0);
		beetlSpringViewResolver.setViewNames("*.html");
		beetlSpringViewResolver.setConfig(beetlGroupUtilConfiguration);
		return beetlSpringViewResolver;
	}
}
