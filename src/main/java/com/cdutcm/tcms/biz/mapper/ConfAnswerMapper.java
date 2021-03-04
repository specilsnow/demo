package com.cdutcm.tcms.biz.mapper;

import com.cdutcm.tcms.biz.model.ConfAnswer;
import org.apache.ibatis.annotations.Param;
import org.mapstruct.Mapper;

@Mapper
public interface ConfAnswerMapper {

    ConfAnswer findByUserIdAndChoiceId(@Param("userId") String userId, @Param("choiceId") String choiceId);

    ConfAnswer findByChoiceIdAndCtype(@Param("choiceId") String choiceId,@Param("ctype") String ctype);
}
