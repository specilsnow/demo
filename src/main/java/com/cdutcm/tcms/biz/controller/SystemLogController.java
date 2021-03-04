package com.cdutcm.tcms.biz.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.cdutcm.core.util.DateUtil;
import com.cdutcm.tcms.biz.model.SysLog;
import com.cdutcm.tcms.biz.service.impl.SysLogServiceImpl;

@RestController
public class SystemLogController {

	@Autowired
	private SysLogServiceImpl sysLogServiceImpl;
	
	/**
	 * 分页显示日志记录
	 */

	@RequestMapping (value="/listPageSyslogs")
	public ModelAndView listPageSyslogs(@ModelAttribute SysLog sysLog,String lastupdateStr,String starttimeStr){
		if(lastupdateStr!=""&&lastupdateStr!=null){
		sysLog.setLastupdate(DateUtil.parseTime(lastupdateStr));
//		System.out.println(DateUtil.parseTime(lastupdateStr));
		}
        if(starttimeStr!=""&&starttimeStr!=null){
			sysLog.setStarttime(DateUtil.parseTime(starttimeStr));
//			System.out.println(DateUtil.parseTime(starttimeStr));
		}
		ModelAndView mv = new ModelAndView();
		List<SysLog> sysLogs = sysLogServiceImpl.listPageSysLog(sysLog);
		mv.addObject("sysLogs", sysLogs);
		mv.setViewName("/admin/sysLog_list.html");
		return mv;	
	}
	/**
	 * 跳转日志页面
	 */
	@RequestMapping (value="/listPageSyslogsinfo")
	public ModelAndView turnsyslogs(){
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/admin/sysLog_info.html");
		return mv;	
	}
	/**
	 * 根据ID查询参数和返回值
	 */
	@RequestMapping (value="/listPageSyslogsbyid")
	public List<SysLog> listPageSyslogsbyid(SysLog sysLog){
	
		
		List<SysLog> sysLogs = sysLogServiceImpl.listPageSysLog(sysLog);
	
		return sysLogs;	
	}
}
