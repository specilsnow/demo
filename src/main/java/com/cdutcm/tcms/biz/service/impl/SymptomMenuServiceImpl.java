package com.cdutcm.tcms.biz.service.impl;

import com.cdutcm.tcms.biz.mapper.SymptomMenuMapper;
import com.cdutcm.tcms.biz.model.SymptomMenu;
import com.cdutcm.tcms.biz.service.SymptomMenuService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 〈一句话功能简述〉<br> 〈SymptomMenuService实现〉
 *
 * @author weihao
 * @create 2018/10/23
 * @since 1.0.0
 */
@Service
public class SymptomMenuServiceImpl implements SymptomMenuService {

  @Autowired
  private  SymptomMenuMapper menuMapper;
  @Override
  public List<SymptomMenu> findSymptomMenus() {
    return menuMapper.findSymptomMenus();
  }
}
