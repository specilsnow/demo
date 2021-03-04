package com.cdutcm.tcms.biz.mapper;

import com.cdutcm.tcms.biz.model.Symptom;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * 〈一句话功能简述〉<br> 〈SymptomMapper〉
 *
 * @author weihao
 * @create 2018/10/23
 * @since 1.0.0
 */
public interface SymptomMapper {

  List<Symptom> findSymptomByMenuId(Integer id);

  void insertSymptom(@Param("menuId") Long menuId,@Param("symptomName") String symptomName);
}
