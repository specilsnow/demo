package com.cdutcm.tcms.sys.service;

import java.util.List;

import com.cdutcm.tcms.sys.entity.Countall;
import com.cdutcm.tcms.sys.entity.Countdata;
import com.cdutcm.tcms.sys.entity.EmrCount;

public interface CountdataService {
	
  public List<Countdata> selectcounCountdata(Countdata countdata);
  
  public List<Countall>  selectCountdisease(Countdata countdata);

  
  public List<Countall> selectCountpatient(Countdata countdata);
  
  public List<Countall> selectCountXw(Countdata countdata);
  
  public List<Countall> listPagexw(Countdata coundata);
  
  public List<Countall> listPagebz(Countdata coundata);
  
  public List<Countall> listPageys(Countdata coundata);
  
  public List<EmrCount> seleEmrCount(Countdata coundata);

}
