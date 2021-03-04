package com.cdutcm.tcms.itf.mapper;

import com.cdutcm.tcms.itf.model.RegistInfo;
import org.apache.ibatis.annotations.Param;

/**
 * @author : weihao
 * @version V1.0
 * @Description: TODO
 * @date Date : 2019年04月30日 16:26
 */
public interface RegistInfoMapper {

    int insert(RegistInfo info);

    RegistInfo getByVisitNo(@Param("visitNo") String visitNo);
}
