package com.cdutcm.tcms.sys.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdutcm.tcms.sys.entity.Dieasename;
import com.cdutcm.tcms.sys.entity.Sysbasefield;
import com.cdutcm.tcms.sys.mapper.CheckdiseaseMapper;
import com.cdutcm.tcms.sys.service.CheckdiseaseService;
@Service
public class CheckdiseaseServiceImpl implements CheckdiseaseService {
    @Autowired
    private CheckdiseaseMapper checkdiseaseMapper;
	@Override
	public List<Sysbasefield> searchsysbasefield(Sysbasefield sysbasefield) {
		// TODO Auto-generated method stub
		return checkdiseaseMapper.searchsysbasefield(sysbasefield);
	}
	@Override
	public List<Dieasename> searchdieasename(Dieasename dieasename) {
		// TODO Auto-generated method stub
		return checkdiseaseMapper.searchdieasename(dieasename);
	}

}
