package com.cdutcm.core.interceptor;

import java.net.URLDecoder;
import java.util.Arrays;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.cdutcm.core.util.StringUtil;
import com.cdutcm.tcms.sys.entity.User;


@Slf4j
public class URLInterceptor implements HandlerInterceptor {



	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		String url = request.getRequestURL().toString();
		String param = null;
		param = request.getQueryString();
		if (StringUtil.notEmpty(param)) {
			param = URLDecoder.decode(param, "UTF-8");
		} else {
			param = "";
		}
		// logger.info("afterCompletion IP：【" + request.getRemoteAddr() + "】" +
		// ",URL:【" + url + "/" + param + "】");
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		String url = request.getRequestURL().toString();

		Map<String, String[]> map = (Map<String, String[]>) request.getParameterMap();
		StringBuffer buffer = new StringBuffer();
		for (String name : map.keySet()) {
			String[] values = map.get(name);
			buffer.append(name).append("=").append(Arrays.toString(values)).append(",");
		}
		/*if (StringUtil.notEmpty(buffer.toString())) {
			Subject subject = SecurityUtils.getSubject();
			if (subject.isAuthenticated()) {
				User user = (User) subject.getPrincipal();
				logger.warn("用户：【" + user.getUsername() + "," + user.getAccount() + "】,IP：【" + request.getRemoteAddr()
						+ "】" + ",URL:【" + url + "/" + "】" + ",参数：【" + buffer.toString() + "】");
			}
		}*/
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {

		String url = request.getRequestURL().toString();
		if(url.indexOf("login")!=-1){
			String param = null;
			param = request.getQueryString();
			if (StringUtil.notEmpty(param)) {
				param = URLDecoder.decode(param, "UTF-8");
			} else {
				param = "";
			}
			Subject subject = SecurityUtils.getSubject();
			if (!subject.isAuthenticated()) {
				User user = (User) subject.getPrincipal();
				if(user!=null){
					log.info("【用户:{},IP:{},请求地址:{},请求参数:{}】",user.getAccount(),request.getRemoteAddr(),url,param);
				}
			}
		}
		return true;
	}

}
