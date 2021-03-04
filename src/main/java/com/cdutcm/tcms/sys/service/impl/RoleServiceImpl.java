package com.cdutcm.tcms.sys.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdutcm.tcms.sys.entity.Role;
import com.cdutcm.tcms.sys.mapper.RoleMapper;
import com.cdutcm.tcms.sys.service.RoleService;
@Service
public class RoleServiceImpl implements RoleService{
    @Autowired
	private RoleMapper roleMapper;
	
	@Override
  public List<Role> listAllRoles() {
		// TODO Auto-generated method stub
		return roleMapper.listAllRoles();
	}

	@Override
  public void deleteRoleById(long roleId) {
		// TODO Auto-generated method stub
		roleMapper.deleteRoleById(roleId);
	}

	@Override
  public Role getRoleById(long roleId) {
		// TODO Auto-generated method stub
		return roleMapper.getRoleById(roleId);
	}

	@Override
  public boolean insertRole(Role role) {
		// TODO Auto-generated method stub
		if(roleMapper.getCountByName(role)>0) {
      return false;
    } else{
			roleMapper.insertRole(role);
			return true;
		}
	}

	@Override
  public boolean updateRoleBaseInfo(Role role) {
		// TODO Auto-generated method stub
		if(roleMapper.getCountByName(role)>0) {
      return false;
    } else{
			roleMapper.updateRoleBaseInfo(role);
			return true;
		}
	}
	
	@Override
  public void updateRoleRights(Role role) {
		// TODO Auto-generated method stub
		roleMapper.updateRoleRights(role);
	}

	public RoleMapper getRoleMapper() {
		return roleMapper;
	}

	public void setRoleMapper(RoleMapper roleMapper) {
		this.roleMapper = roleMapper;
	}

	@Override
	public Role getIdByName(Role role) {
		// TODO Auto-generated method stub
		return roleMapper.getIdByName(role);
	}

	@Override
	public List<Role> getRoleByAccount(String account) {
		return roleMapper.getRoleByAccount(account);
	}

	@Override
	public List<Role> listPageRoles(Role role) {
		// TODO Auto-generated method stub
		return roleMapper.listPageRoles(role);
	}



}
