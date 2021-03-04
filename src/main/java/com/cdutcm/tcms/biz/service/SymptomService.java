package com.cdutcm.tcms.biz.service;

import com.cdutcm.tcms.biz.model.Symptom;
import java.util.List;

/**
 * 〈一句话功能简述〉<br> 〈SymptomService〉
 *
 * @author weihao
 * @create 2018/10/23
 * @since 1.0.0
 */
public interface SymptomService {

  List<Symptom> findSymptomByMenuId(Integer id);
  void insertSymptom(Long menuId,String symptomName);
}
