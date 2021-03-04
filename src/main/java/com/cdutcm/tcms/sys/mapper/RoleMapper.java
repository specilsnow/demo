package com.cdutcm.tcms.sys.mapper;

import java.util.List;

import com.cdutcm.tcms.sys.entity.Role;

public interface RoleMapper {
	List<Role> listAllRoles();
	Role getRoleById(long roleId);
	void insertRole(Role role);
	void updateRoleBaseInfo(Role role);
	void deleteRoleById(long roleId);
	int getCountByName(Role role);
	void updateRoleRights(Role role);
	List<Role> listPageRoles(Role role);
	Role getIdByName(Role role);

	List<Role> getRoleByAccount(String account);
}
