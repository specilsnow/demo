package com.cdutcm.tcms.sys.service;


import java.util.List;
import com.cdutcm.core.message.SysMsg;
import com.cdutcm.core.util.ResultVO;
import com.cdutcm.tcms.sys.entity.User;
import com.cdutcm.tcms.sys.entity.UserInfo;

public interface UserService {
	User getUserById(Long userId);

	User getUserByAccount(String account);
	
	boolean insertUser(User user);
	
	void updateUser(User user);

	void updateUserPwd(User user);
	
	User getUserByNameAndPwd(String username,String password);
	
	public SysMsg updateUserBaseInfo(User user);
	
	void updateUserRights(User user);
	
	void deleteUser(Long userId);
	
	List<User> listPageUser(User user);

	void updateLastLogin(User user);
	
	User getUserAndRoleById(Long userId);
	
	List<User> listAllUser();

	/**
	 *  @param user
	 *  @return   
	 */
	String getClinic(User user);
	
	List<UserInfo> listAllUserInfo(UserInfo userInfo);

  List<UserInfo> listPageUserInfoByNameOrSpecialty(UserInfo userInfo);
	/**
	 *  @param account
	 *  @return   
	 */
	UserInfo getUserInfoByAccount(String account);

	User getUserByOpenid(String openid);

	/**
	 * 在线医生
	 * @param userInfo
	 * @param accounts
	 * @return
	 */
	List<UserInfo> onlineUser(UserInfo userInfo,List<String> accounts);
	/**
	 * 通过电话号码查找个人信息
	 */
	UserInfo getUserInfoByTel(String telphone);


	void storageData(User user);

	void authCloudPlat(User user);

	ResultVO updateUser(String phone, String password, String code);

	User authHulian(String phone);

}
