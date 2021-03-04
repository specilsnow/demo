package com.cdutcm.core.exception;

import java.io.IOException;
import java.io.Writer;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.beetl.core.ConsoleErrorHandler;
import org.beetl.core.Resource;
import org.beetl.core.exception.BeetlException;
import org.beetl.core.exception.ErrorInfo;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.cdutcm.core.util.Const;
import com.cdutcm.core.util.IdWorker;
import com.cdutcm.core.util.SpringUtil;
import com.cdutcm.tcms.biz.mapper.SysLogMapper;
import com.cdutcm.tcms.biz.model.SysLog;
import com.cdutcm.tcms.sys.entity.User;

/**
 * @author       zhufj
 * @Description  重载页面报错处理类
 * @Date         2018-3-21
 */
@Component
public class ReThrowConsoleErrorHandler extends ConsoleErrorHandler{
	
    @Override
    public void processExcption(BeetlException ex, Writer writer){
    	SysLog sysLog=new SysLog();
    	sysLog.setStarttime(new Date());
    	SysLogMapper logMapper = (SysLogMapper) SpringUtil.getBean("sysLogMapper");
    	ErrorInfo error = new ErrorInfo(ex);
    	HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    	HttpSession session = request.getSession();    
        User user = (User)session.getAttribute(Const.SESSION_USER);
        String ip = request.getRemoteAddr();
    	sysLog.setCreateor(user.getUsername());
    	sysLog.setCtype("异常");
    	sysLog.setId(new IdWorker().nextId());
    	sysLog.setExceptionlinenumber(error.getErrorTokenLine());
    	sysLog.setName("模板页面渲染异常");
    	sysLog.setIp(ip);
    	sysLog.setExceptionmessage(error.getType()+":"+error.getErrorTokenText()+"位于"+error.getErrorTokenLine()+"行 资源："+getResourceName(ex.resource.getId()));
        sysLog.setDescription("");
        sysLog.setLastupdate(new Date());
       
        super.processExcption(ex, writer);
        String content = null;
        int line = error.getErrorTokenLine();
    	String sb="";
		try
		{
			
			Resource res = ex.resource;
			//鏄剧ず鍓嶅悗涓夎鐨勫唴瀹�
			int[] range = this.getRange(line);
			content = res.getContent(range[0], range[1]);
			if (content != null)
			{
				String[] strs = content.split(ex.cr);
				int lineNumber = range[0];
				for (int i = 0; i < strs.length; i++)
				{
					sb+="-" + lineNumber+"|"+strs[i]+"|";
				
					lineNumber++;
				}

			}
		}
		catch (IOException e)
		{

			//ingore

		}
		sysLog.setDescription("异常代码见详情");
		sysLog.setReturnvalue(sb);
		logMapper.insert(sysLog);
        throw ex;
    }
}