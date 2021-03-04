package com.cdutcm.tcms.sys.controller;



import com.cdutcm.core.page.Page;
import com.cdutcm.core.util.StringUtil;
import com.cdutcm.tcms.biz.model.Assist;
import com.cdutcm.tcms.biz.service.WebSocket;
import com.cdutcm.tcms.redis.service.IRedisService;
import com.cdutcm.tcms.sys.entity.MqttData;
import java.io.PrintWriter;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;


import net.sf.json.JSONObject;
import org.apache.shiro.SecurityUtils;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SimpleSession;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.crazycake.shiro.RedisCache;
import org.crazycake.shiro.RedisManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.cdutcm.core.message.SysMsg;
import com.cdutcm.core.util.IdWorker;
import com.cdutcm.tcms.sys.entity.Role;
import com.cdutcm.tcms.sys.entity.User;
import com.cdutcm.tcms.sys.entity.UserInfo;
import com.cdutcm.tcms.sys.service.RoleService;
import com.cdutcm.tcms.sys.service.UserService;
import com.cdutcm.tcms.sys.view.UserExcelView;

@Controller
@RestController
@RequestMapping(value="/user")
public class UserController {
	private static final Logger log = LoggerFactory.getLogger(UserController.class);
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private WebSocket webSocket;
	@Autowired
	private IRedisService iRedisService;
	@Autowired
	private RedisManager redisManager;


	/**
	 * 显示用户列表
	 * @param user
	 * @return
	 */
	@RequestMapping(value="/listPageUsers")
	public ModelAndView listPageUsers(User user){
		//List<User> userList = userService.listAllUser(page);
		List<User> userList = userService.listPageUser(user);
		List<Role> roleList = roleService.listAllRoles();
		ModelAndView mv = new ModelAndView();
		mv.addObject("userList", userList);
		mv.addObject("roleList", roleList);
		mv.addObject("user", user);
		mv.setViewName("/admin/user_list.html");
		UserInfo userInfo = new UserInfo();
		return mv;
	}

	/**
	 * 请求新增用户页面
	 * @return
	 */
	@RequestMapping(value="/add")
	public ModelAndView toAdd(){
		ModelAndView mv = new ModelAndView();
		List<Role> roleList = roleService.listAllRoles();
		User user=new User();
		mv.addObject("user", user);
		mv.addObject("roleList",roleList);
		mv.setViewName("/admin/user_info.html");
		return mv;
	}
	
	/**
	 * 保存用户信息
	 * @param user
	 * @return
	 */
	//@SysControllerLog(name=Const.SAVE_USER)
	@RequestMapping(value="/save",method=RequestMethod.POST)
	@ResponseBody
	public SysMsg saveUser(@ModelAttribute User user){
		SysMsg sysMsg=new SysMsg();	
		if(user.getUserId()==0){
			user.setUserId(new IdWorker().nextId());
			user.setCreattime(new Date());
			if(userService.insertUser(user)==true){
				//mv.setViewName("redirect:/user/listPageUsers");
				sysMsg.setContent("新增用户成功");
				sysMsg.setStatus("T");
				return sysMsg;
			}else{
				sysMsg.setContent("失败，用户名重复");
				sysMsg.setStatus("F");
				return sysMsg;
			}
		}else{
		    sysMsg=userService.updateUserBaseInfo(user);
			return sysMsg;
		}
		
	}
	
	/**
	 * 请求编辑用户页面
	 * @param userId
	 * @return
	 */
	@RequestMapping(value="/edit")
	public ModelAndView toEdit(@RequestParam Long userId){
		ModelAndView mv = new ModelAndView();
		User user = userService.getUserAndRoleById(userId);
		List<Role> roleList = roleService.listAllRoles();
	
		mv.addObject("user", user);
		mv.addObject("roleList", roleList);
		mv.setViewName("/admin/user_info.html");
		return mv;
	}
	
	/**
	 * 删除某个用户
	 * @param userId
	 * @param out
	 */
	//@SysControllerLog(name=Const.DELETE_USER)
	@RequestMapping(value="/delete")
	public void deleteUser(@RequestParam Long userId,PrintWriter out){
		userService.deleteUser(userId);
		out.write("success");
		out.close();
	}
	
	/**
	 * 请求用户授权页面
	 * @param userId
	 * @param model
	 * @return
	 */
	/*@RequestMapping(value="/auth")
	public String auth(@RequestParam int userId,Model model){
		List<Menu> menuList = menuService.listAllMenu();
		User user = userService.getUserById(userId);
		String userRights = user.getRights();
		if(StringUtil.notEmpty(userRights)){
			for(Menu menu : menuList){
				menu.setHasMenu(RightsHelper.testRights(userRights, menu.getMenuId()));
				if(menu.isHasMenu()){
					List<Menu> subRightsList = menu.getSubMenu();
					for(Menu sub : subRightsList){
						sub.setHasMenu(RightsHelper.testRights(userRights, sub.getMenuId()));
					}
				}
			}
		}
		JSONArray arr = JSONArray.fromObject(menuList);
		String json = arr.toString();
		json = json.replaceAll("menuId", "id").replaceAll("menuName", "name").replaceAll("subMenu", "nodes").replaceAll("hasMenu", "checked");
		model.addAttribute("zTreeNodes", json);
		model.addAttribute("userId", userId);
		return "authorization";
	}*/
	
/*	*//**
	 * 保存用户权限
	 * @param userId
	 * @param menuIds
	 * @param out
	 *//*
	//@SysControllerLog(name=Const.SAVE_User_PERMISSIONS)
	@RequestMapping(value="/auth/save")
	public void saveAuth(@RequestParam int userId,@RequestParam String menuIds,PrintWriter out){
		BigInteger rights = RightsHelper.sumRights(StringUtil.str2StrArray(menuIds));
		User user = userService.getUserById(userId);
		user.setRights(rights.toString());
		userService.updateUserRights(user);
		out.write("success");
		out.close();
	}*/
	
	/**
	 * 导出用户信息到excel
	 * @return
	 */
	//@SysControllerLog(name=Const.EXPORT_USER_TOXML)
	@RequestMapping(value="/excel")
	public ModelAndView export2Excel(){
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("用户名");
		titles.add("名称");
		titles.add("角色");
		titles.add("最近登录");
		dataMap.put("titles", titles);
		List<User> userList = userService.listAllUser();
		dataMap.put("userList", userList);
		UserExcelView erv = new UserExcelView();
		ModelAndView mv = new ModelAndView(erv,dataMap);
		return mv;
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
	
	/**
	 * 名医列表
	 *  @return
	 */
	@RequestMapping(value="/doctorList")
	public ModelAndView listAllUserInfo(UserInfo userInfo) {
		ModelAndView mv = new ModelAndView();
		ArrayList<String> accounts = new ArrayList<>();
		Map<String, WebSocket> webSocketMapUser = WebSocket.getWebSocketMapUser();
		for (String account : webSocketMapUser.keySet()) {
			accounts.add(account);
		log.info("在线医生:"+account);
		}
		if (userInfo.getPage()==null){
         Page p = new Page();
         userInfo.setPage(p);
       }
    userInfo.getPage().setShowCount(5);
		List<UserInfo> userInfos = userService.onlineUser(userInfo, accounts);
//		List<UserInfo> listAllUserInfo = userService.listAllUserInfo(userInfo);
		mv.addObject("users", userInfos);
		mv.setViewName("/recipel/doctorList.html");
		return mv;
	}

  /**
   *	根据姓名，特长 进行模糊查询医生
   * @param userInfo
   * @return
   */
  @RequestMapping(value="/listPageDoctorModal")
	public ModelAndView listPageUserInfoByNameOrSpecialty(UserInfo userInfo){
     ModelAndView mv = new ModelAndView();
    List<UserInfo> users = userService.listPageUserInfoByNameOrSpecialty(userInfo);
    if (StringUtil.isEmptyList(users)){
      return null;
    }else {
      mv.addObject("users", users);
      mv.setViewName("/recipel/doctorList.html");
    }
    return mv;
  }
	/**
	 * 名医信息
	 *  @return
	 */
	@RequestMapping(value="/userInfo")
	public ModelAndView userInfo(String account) {
		ModelAndView mv = new ModelAndView();
		UserInfo userInfo = userService.getUserInfoByAccount(account);
		mv.addObject("userInfo", userInfo);
    Subject subject = SecurityUtils.getSubject();
    User user = (User) subject.getPrincipal();
    mv.addObject("user",user);
		mv.setViewName("/recipel/userInfo.html");
		return mv;
	}

  /**
   * 远程
   * @param assist
   * @return
   */
  @RequestMapping("/assist")
  public SysMsg assist(Assist assist) {
    SysMsg msg = new SysMsg();
		User user = userService.getUserByAccount(assist.getProposerId());
		String appleid = user.getAppleid();
		if ("" == appleid || null ==appleid){
			msg.setStatus("F");
      msg.setContent("您暂时无法使用此功能");
			log.info("需要先绑定FaceTime;后天管理做");
		}else {
      MqttData<Object> data = new MqttData<>();
      assist.setProposerAppleId(appleid);
      data.setTopic("doctorHelp");
      data.setData(assist);
			String str = JSONObject.fromObject(data).toString();
//			ServerMQTT.publish(str,assist.getProposerId(),assist.getHelper());
			msg.setStatus("T");
			msg.setContent("预约成功");
			/*给专家发消息*/
			log.info(str);
		}
    return msg;
  }

}
