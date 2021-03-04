package com.cdutcm.tcms.biz.service.impl;

import com.cdutcm.tcms.biz.mapper.SymptomMapper;
import com.cdutcm.tcms.biz.model.Symptom;
import com.cdutcm.tcms.biz.service.SymptomService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 〈一句话功能简述〉<br> 〈〉
 *
 * @author weihao
 * @create 2018/10/23
 * @since 1.0.0
 */
@Service
public class SymptomServiceImpl implements SymptomService {

  @Autowired
  private SymptomMapper symptomMapper;

  @Override
  public List<Symptom> findSymptomByMenuId(Integer id) {
    return symptomMapper.findSymptomByMenuId(id);
  }

  @Override
  public void insertSymptom(Long menuId, String symptomName) {
    symptomMapper.insertSymptom(menuId,symptomName);
  }
}
