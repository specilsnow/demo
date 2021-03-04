package com.cdutcm.tcms.sys.mapper;

import java.util.List;

import com.cdutcm.tcms.sys.entity.Dieasename;
import com.cdutcm.tcms.sys.entity.Sysbasefield;

public interface CheckdiseaseMapper {
  List<Sysbasefield> searchsysbasefield(Sysbasefield sysbasefield);
  public List<Dieasename> searchdieasename(Dieasename dieasename);
}
