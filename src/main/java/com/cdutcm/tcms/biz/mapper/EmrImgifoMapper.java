package com.cdutcm.tcms.biz.mapper;

import com.cdutcm.tcms.biz.model.EmrImgifo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: mxq
 * @date: 2019/10/12 10:35
 * @desc:
 */
@Repository
public interface EmrImgifoMapper {
    int insert(EmrImgifo emrImgifo);
    List<EmrImgifo>getByVisitNo(String visitNo);
    int plInsert(List<EmrImgifo>emrImgifos);
}
