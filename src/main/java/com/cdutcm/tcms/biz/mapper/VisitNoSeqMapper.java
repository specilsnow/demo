package com.cdutcm.tcms.biz.mapper;

import org.apache.ibatis.annotations.Select;
import org.mapstruct.Mapper;

@Mapper
public interface VisitNoSeqMapper {

	/**
	 * @return 最新的序列号
	 */
	@Select("SELECT visitno_seq()")
	String getVisitNoSeq();
}
