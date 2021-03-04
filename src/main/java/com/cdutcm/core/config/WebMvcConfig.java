package com.cdutcm.core.config;

import com.cdutcm.core.PathConstant;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @Auther: Mxq
 * @Date: 2018/7/24 10:51
 * @description: 图片绝对地址与虚拟地址的映射
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Value("${webimg.src}")
    private  String src;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/file/**").addResourceLocations("classpath:/file/");
        registry.addResourceHandler("/emrfile/**").addResourceLocations("file:/home/file/");
        registry.addResourceHandler("/QrImg/**").addResourceLocations("file:/opt/cdutcm/jenkins/ybm/data/");
        registry.addResourceHandler("/uploadImg/**").addResourceLocations("file:/opt/cdutcm/jenkins/ybm/images/");
//        registry.addResourceHandler("/uploadImg/**").addResourceLocations("file:d:/images/");
        registry.addResourceHandler("/excel/**").addResourceLocations("file:d:/yb/");
//        registry.addResourceHandler("/uploadImg/**").addResourceLocations("file:".concat(PathConstant.IMG_PATH));
    }
}
