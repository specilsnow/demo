package com.cdutcm.tcms.sys.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cdutcm.tcms.sys.entity.User;
import com.cdutcm.tcms.sys.entity.UserInfo;
import org.springframework.stereotype.Repository;


@Repository
public interface UserMapper  {
	
	List<User> listAllUser();
	
	User getUserById(Long userId);

	User getUserByAccount(String account);
	
	void insertUser(User user);
	void insertUserInfo(User user);

	void updateUser(User user);

	void updateUserPwd(User user);

	User getUserInfo(User user);
	
	int updateUserBaseInfo(User user);
	
	void updateUserRights(User user);
	
	int getCountByName(User user);
	
	void deleteUser(Long userId);
	
	int getCount(User user);
	
	List<User> listPageUser(User user);
	
	User getUserAndRoleById(Integer userId);
	
	void updateLastLogin(User user);

	User getUserAndRoleById(Long userId);

	/**
	 *  @param user
	 *  @return   
	 */
	String getClinic(User user);

	/**
	 *  @return   
	 */
	List<UserInfo> listPageUserInfo(UserInfo userInfo);

	List<UserInfo> listPageUserInfoByNameOrSpecialty(UserInfo userInfo);

	/**
	 *  @return   
	 */
	UserInfo getUserInfoByAccount(String account);

  User getUserByOpenid(String openid);

  /**
   * 在线医生
   * @param userInfo
   * @return
   */
  List<UserInfo> listPageOnlineUser(UserInfo userInfo);

  UserInfo getUserInfoByTel(@Param("telphone") String telphone);

  int countAllUser();

  List<User>selectLimitUser(@Param("start") int start,@Param("num") int num);

  List<String>getRoles(@Param("userId")Long userId);


}
