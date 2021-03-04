package com.cdutcm.tcms.sys.service;

import java.util.List;

import com.cdutcm.tcms.sys.entity.Role;

public interface RoleService {
	List<Role> listAllRoles();
	
	Role getRoleById(long roleId);
	boolean insertRole(Role role);
	boolean updateRoleBaseInfo(Role role);
	void deleteRoleById(long roleId);
	void updateRoleRights(Role role);
	List<Role> listPageRoles(Role role);
	Role getIdByName(Role role);

	List<Role> getRoleByAccount(String account);
}
