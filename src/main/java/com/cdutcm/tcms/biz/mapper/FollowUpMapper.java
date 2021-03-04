package com.cdutcm.tcms.biz.mapper;

import com.cdutcm.tcms.biz.model.FollowUp;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author : weihao
 * @version V1.0
 * @Description: TODO
 * @date Date : 2019年05月07日 15:51
 */
public interface FollowUpMapper {

    int insert(FollowUp followUp);

    int delete(Long id);

    List<FollowUp> getByAccount(@Param("account") String account);

    int updataUse(FollowUp followUp);

    int useInit(@Param("account") String account);

    void choose(@Param("id") Long id);
}
