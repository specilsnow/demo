package com.cdutcm.tcms.biz.mapper;


import com.cdutcm.tcms.biz.model.TreatData;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: mxq
 * @date: 2019/12/3 11:08
 * @desc:
 */
@Repository
public interface TreatDataMapper {

    TreatData findByVisitNo(String visitNo);
    int insert(TreatData treatData);
    int update(TreatData treatData);
    List<TreatData>findByAccount(String account);
    int deleteByVisitNo(String visitNo);

    int updateBeforeData(TreatData treatData);
    int updateAfterData(TreatData treatData);
}
