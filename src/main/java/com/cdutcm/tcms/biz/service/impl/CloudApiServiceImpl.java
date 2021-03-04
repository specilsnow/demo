package com.cdutcm.tcms.biz.service.impl;

import com.cdutcm.tcms.biz.JoinUpCloudPlat.CloudMethods;
import com.cdutcm.tcms.biz.JoinUpCloudPlat.CloudUserClinic;
import com.cdutcm.tcms.biz.model.UserClinic;
import com.cdutcm.tcms.biz.service.CloudApiService;
import com.cdutcm.tcms.biz.service.UserClinicService;
import com.cdutcm.tcms.sys.entity.Clinic;
import com.cdutcm.tcms.sys.service.ClinicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * @author wonder
 * @date 2019/10/30
 */
@Service
public class CloudApiServiceImpl implements CloudApiService {

    @Autowired
    private UserClinicService userClinicService;

    @Autowired
    private ClinicService clinicService;

    @Override
    public void updateUserClinic(String account,String token) {
        List<CloudUserClinic> doctorOrgList = CloudMethods.getInstance().getDoctorOrgList(token);
        userClinicService.deleteByAccount(account);
        ListIterator<CloudUserClinic> cloudUserClinicListIterator = doctorOrgList.listIterator();
        List<UserClinic> userClinicList = new ArrayList<>();
        List<Clinic> clinicList =new ArrayList<>();
        while (cloudUserClinicListIterator.hasNext()){
            CloudUserClinic cloudUserClinic = cloudUserClinicListIterator.next();
            UserClinic userClinic = new UserClinic(account,cloudUserClinic.getOrganizationId());
            userClinicList.add(userClinic);
            if (Objects.isNull(clinicService.findByClinicId(userClinic.getClinicId()))){
                String organizationName = cloudUserClinic.getOrganizationName();
                String organizationId = cloudUserClinic.getOrganizationId();
                String address = cloudUserClinic.getAddress();
                Clinic clinic = new Clinic(organizationName,organizationId,address,new Date());
                clinicList.add(clinic);
            }
        }
        if (!CollectionUtils.isEmpty(userClinicList)){
            userClinicService.insertList(userClinicList);
        }
        if (!CollectionUtils.isEmpty(clinicList)){
            clinicService.insertList(clinicList);
        }
    }
}