package com.cdutcm.tcms.biz.mapper;


import com.cdutcm.tcms.biz.model.UserClinic;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author wonder
 * @date 2019/10/29
 */
@Repository
public interface UserClinicMapper {

    Integer insert(UserClinic userClinic);

    Integer deleteByAccount(String account);

    Integer insertList(List<UserClinic> userClinicList);

    UserClinic findByClinicIdAndAccount(@Param("clinicId") String clinicId, @Param("account") String account);
}
