package com.cdutcm.tcms.biz.mapper;

import java.util.List;

import com.cdutcm.tcms.biz.model.BaseRecipelItem;
import com.cdutcm.tcms.biz.model.RecipelItem;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipelItemMapper {

	List<RecipelItem> findItemByRecipelNo(String recipelNo);

	/** 添加发送的药品信息 */
	public int saveRecipelItem(RecipelItem recipelItem);

	/** 根据recipelId删除 */
	public int delRecipelItemByRecipelId(Long recipelId);

	List<RecipelItem> findItemByRecipelId(Long id);
	
	int saveallRecipelItem(List<RecipelItem> recipelItems);

	List<RecipelItem> findItemByAccount(@Param("account") String account);

}
