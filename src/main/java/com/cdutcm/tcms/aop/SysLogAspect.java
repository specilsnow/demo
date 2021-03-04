package com.cdutcm.tcms.aop;

import java.lang.reflect.Method;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.cdutcm.core.util.Const;
import com.cdutcm.core.util.IdWorker;
import com.cdutcm.tcms.biz.model.SysLog;
import com.cdutcm.tcms.biz.service.SysLogService;
import com.cdutcm.tcms.log.SysControllerLog;
import com.cdutcm.tcms.log.SysServiceLog;
import com.cdutcm.tcms.sys.entity.User;
import com.google.gson.Gson;


/**
 * @author       zhufj
 * @Description  系统切点类
 * @Date         2016-10-20
 */
@Aspect   
@Component
public class SysLogAspect {

	//http://blog.csdn.net/czmchen/article/details/42392985
	//注入Service用于把日志保存数据库    
    //    @Resource   
    //    private SysLogService logService;
	@Autowired
	private SysLogService sysLogService;
    
	private Gson gson = new Gson();
	
    private static final Logger logger = LoggerFactory.getLogger(SysLogAspect.class);
    /**
     * 系统日志唯一变量
     */
    private  ThreadLocal<SysLog> syslog= new ThreadLocal<SysLog>(); 
    
    //Service切点
    @Pointcut("@annotation(com.cdutcm.tcms.log.SysServiceLog)")
    public void serviceAspect(){}
    
    //Controller切点
    @Pointcut("@annotation(com.cdutcm.tcms.log.SysControllerLog)")
    public void controllerAspect(){}
    
    /**
     *切入点controller层前 
     * @param joinPoint
     */
    @Before("controllerAspect()")
    public void controllerBefore(JoinPoint joinPoint){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String url_info = request.getScheme() + "://" + request.getServerName()+
                ":" +request.getServerPort() + request.getContextPath()+request.getServletPath();
//        +"?"+(request.getQueryString());//用户请求数据，可不加
        HttpSession session = request.getSession();
        String ip = request.getRemoteAddr();
        logger.info("用户ip={}，访问链接{}",ip,url_info);
    }
    /**
     * controller层完成切入点
     * @param joinPoint
     */
    @AfterReturning(returning = "o",pointcut = "controllerAspect()")
    public void controllerafterreturn(Object o){
    	try {
      	syslog.get().setLastupdate(new Date());
    	syslog.get().setReturnvalue(gson.toJson(o));
    	syslog.get().setCtype("正常");
    	sysLogService.insert(syslog.get());
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	}
    
    /**  
     * 异常通知 用于拦截controller层记录异常日志  
     *  
     * @param joinPoint  
     * @param e  
     */    
     @AfterThrowing(pointcut="controllerAspect()", throwing = "e")    
     public void controllerThrowing(JoinPoint joinPoint, Throwable e) {    
       
         try {
        	
        	syslog.get().setExceptionlinenumber(e.getStackTrace()[0].getLineNumber());
        	syslog.get().setCtype("异常");
        	syslog.get().setExceptionmessage("异常类型："+e.getClass().getName()+"--异常描述："+e.getMessage());
        	syslog.get().setLastupdate(new Date());
        	syslog.get().setName(getControllerMethodName(joinPoint));
        	sysLogService.insert(syslog.get());
        }  catch (Exception ex) {    
            //记录本地异常日志    
            logger.error("==异常通知异常==");    
            logger.error("异常信息:{}", ex.getMessage());    
        }    
         /*==========记录本地异常日志==========*/    
//        logger.error("异常方法:{}异常代码:{}异常信息:{}参数:{}", joinPoint.getTarget().getClass().getName() + joinPoint.getSignature().getName(), e.getClass().getName(), e.getMessage(), params);    
    
    }
    /**
     * 用于收集sevice层返回数据
     * 
     */
     
     @AfterReturning(returning = "o",pointcut = "serviceAspect())")
     public void serviceafterreturn(Object o){
     	try {
   
     	} catch (Exception e) {
     		e.printStackTrace();
     	}
     	}
    /**  
     * 异常通知 用于拦截service层记录异常日志  
     *  
     * @param joinPoint  
     * @param e  
     */    
     @AfterThrowing(pointcut="serviceAspect()", throwing = "e")    
     public void doAfterThrowing(JoinPoint joinPoint, Throwable e) {    
      
         try {   
        	if(syslog.get().getCtype()!=""){ 
        	syslog.get().setExceptionlinenumber(e.getStackTrace()[0].getLineNumber());
         	syslog.get().setCtype("异常");
         	syslog.get().setExceptionmessage("异常类型："+e.getClass().getName()+"--异常描述："+e.getMessage()); 
         	syslog.get().setLastupdate(new Date());
         	syslog.get().setName(getControllerMethodName(joinPoint));
         	sysLogService.insert(syslog.get());}
        }  catch (Exception ex) {    
            //记录本地异常日志    
            logger.error("==异常通知异常==");    
            logger.error("异常信息:{}", ex.getMessage());    
        }    
         /*==========记录本地异常日志==========*/    
//        logger.error("异常方法:{}异常代码:{}异常信息:{}参数:{}", joinPoint.getTarget().getClass().getName() + joinPoint.getSignature().getName(), e.getClass().getName(), e.getMessage(), params);    
    
    }
    
    /**  
     * 获取注解中对方法的描述信息 用于service层注解  
     *  
     * @param joinPoint 切点  
     * @return 方法描述  
     * @throws Exception  
     */    
     @SuppressWarnings("rawtypes")
     public  static String getServiceMthodDescription(JoinPoint joinPoint)    
             throws Exception {    
        String targetName = joinPoint.getTarget().getClass().getName();    
        String methodName = joinPoint.getSignature().getName();    
        Object[] arguments = joinPoint.getArgs();    
        Class targetClass = Class.forName(targetName);    
        Method[] methods = targetClass.getMethods();    
        String description = "";    
         for (Method method : methods) {    
             if (method.getName().equals(methodName)) {    
                Class[] clazzs = method.getParameterTypes();    
                 if (clazzs.length == arguments.length) {    
                    description = method.getAnnotation(SysServiceLog.class).description();    
                     break;    
                }    
            }    
        }    
         return description;    
    } 
     
    /**  
     * 获取注解中对方法的描述信息 用于Controller层注解  
     *  
     * @param joinPoint 切点  
     * @return 方法描述  
     * @throws Exception  
     */    
 	public  static String getControllerMethodName(JoinPoint joinPoint)  throws Exception {    
        String targetName = joinPoint.getTarget().getClass().getName();    
        String methodName = joinPoint.getSignature().getName();
        int index = targetName.indexOf(".controller")+12;
        targetName = targetName.substring(index, targetName.length());
        return targetName + "." + methodName ;
    }
    @SuppressWarnings("rawtypes")
   	public static String getControllerinfos(JoinPoint joinPoint)  throws Exception {    
           String targetName = joinPoint.getTarget().getClass().getName();    
           String methodName = joinPoint.getSignature().getName();    
           Object[] arguments = joinPoint.getArgs();    
           Class targetClass = Class.forName(targetName);    
           Method[] methods = targetClass.getMethods();    
           String description = "";
           String ctype = "";
            for (Method method : methods) {    
                if (method.getName().equals(methodName)) {    
                   Class[] clazzs = method.getParameterTypes();    
                    if (clazzs.length == arguments.length) {    
                   	description = method.getAnnotation(SysControllerLog.class).description();   
                   	ctype = method.getAnnotation(SysControllerLog.class).ctype();
                       break;    
                   }    
               }    
           }    
           return ctype + ":" + description ;
       }
}
