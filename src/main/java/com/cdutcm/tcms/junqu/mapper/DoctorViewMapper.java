package com.cdutcm.tcms.junqu.mapper;

import java.util.List;

import com.cdutcm.tcms.biz.model.UserInfo;
import com.cdutcm.tcms.junqu.model.DoctorView;

public interface DoctorViewMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table V_ZYY_DOCTOR
     *
     * @mbggenerated Tue May 08 14:14:13 CST 2018
     */
    int deleteByPrimaryKey(String userId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table V_ZYY_DOCTOR
     *
     * @mbggenerated Tue May 08 14:14:13 CST 2018
     */
    int insert(DoctorView record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table V_ZYY_DOCTOR
     *
     * @mbggenerated Tue May 08 14:14:13 CST 2018
     */
    int insertSelective(DoctorView record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table V_ZYY_DOCTOR
     *
     * @mbggenerated Tue May 08 14:14:13 CST 2018
     */
    DoctorView selectByPrimaryKey(String userId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table V_ZYY_DOCTOR
     *
     * @mbggenerated Tue May 08 14:14:13 CST 2018
     */
    int updateByPrimaryKeySelective(DoctorView record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table V_ZYY_DOCTOR
     *
     * @mbggenerated Tue May 08 14:14:13 CST 2018
     */
    int updateByPrimaryKey(DoctorView record);
    
    List<UserInfo> findAll();
}