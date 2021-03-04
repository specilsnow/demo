package com.cdutcm.tcms.junqu.mapper;

import java.util.List;

import com.cdutcm.tcms.junqu.model.PatientView;

public interface PatientViewMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table V_ZYY_CLINIC_MASTER
     *
     * @mbggenerated Tue May 08 14:16:07 CST 2018
     */
    int deleteByPrimaryKey(Short visitNo);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table V_ZYY_CLINIC_MASTER
     *
     * @mbggenerated Tue May 08 14:16:07 CST 2018
     */
    int insert(PatientView record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table V_ZYY_CLINIC_MASTER
     *
     * @mbggenerated Tue May 08 14:16:07 CST 2018
     */
    int insertSelective(PatientView record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table V_ZYY_CLINIC_MASTER
     *
     * @mbggenerated Tue May 08 14:16:07 CST 2018
     */
    PatientView selectByPrimaryKey(Short visitNo);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table V_ZYY_CLINIC_MASTER
     *
     * @mbggenerated Tue May 08 14:16:07 CST 2018
     */
    int updateByPrimaryKeySelective(PatientView record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table V_ZYY_CLINIC_MASTER
     *
     * @mbggenerated Tue May 08 14:16:07 CST 2018
     */
    int updateByPrimaryKey(PatientView record);
    
    List<PatientView> findPatientByDateAndNo (String date ,String visitNo);
}