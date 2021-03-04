package com.cdutcm.tcms.biz.mapper;

import com.cdutcm.tcms.biz.model.SymptomMenu;
import java.util.List;

/**
 * 〈一句话功能简述〉<br> 〈SymptomMenuMapper〉
 *
 * @author weihao
 * @create 2018/10/23
 * @since 1.0.0
 */
public interface SymptomMenuMapper {

  List<SymptomMenu> findSymptomMenus();
}
