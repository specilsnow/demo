package com.cdutcm.tcms.sys.mapper;


import com.cdutcm.tcms.sys.entity.UserInfo;
import org.springframework.stereotype.Repository;

/**
 * @author: mxq
 * @date: 2019/10/15 09:24
 * @desc:
 */
@Repository
public interface SysUserInfoMapper {

    int insert(UserInfo userInfo);
    int update(UserInfo userInfo);
}
