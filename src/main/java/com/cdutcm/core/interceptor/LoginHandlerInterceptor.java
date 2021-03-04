package com.cdutcm.core.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.cdutcm.core.util.Const;
import com.cdutcm.tcms.sys.entity.User;

/**
 * @author       zhufj
 * @Description  登录拦截器
 * @Date         2016-9-20
 */
//@Component
public class LoginHandlerInterceptor extends HandlerInterceptorAdapter{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String path = request.getServletPath();
	
		if(path.matches(Const.NO_INTERCEPTOR_PATH)){
			return true;
		}else{
			HttpSession session = request.getSession();
			User user = (User)session.getAttribute(Const.SESSION_USER);
			if(user!=null){
				return true;
			}else{
				String header = request.getHeader("X-Requested-With");  
			    boolean isAjax = "XMLHttpRequest".equals(header) ? true:false;
			    if(isAjax){
//			    	response.getWriter().write("timeout");
			    	response.sendRedirect(request.getContextPath()+"/login");
			    }else{
			    	response.sendRedirect(request.getContextPath()+"/login");
			    }
				
				return false;
			}
		}
	}
	
}
