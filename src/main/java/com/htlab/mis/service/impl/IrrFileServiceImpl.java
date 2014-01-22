package com.htlab.mis.service.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.htlab.mis.dao.IrrFileDao;
import com.htlab.mis.entity.IrrFile;
import com.htlab.mis.service.IrrFileService;
@Service
public class IrrFileServiceImpl extends BaseServiceImpl<IrrFile, Integer> implements IrrFileService {

	@Autowired
	private IrrFileDao irrFileDao;

	@Autowired
	public void setBaseDao(){
		super.baseDao=irrFileDao;
	}

	public List findByIrrAndType(Integer irrId,String type){

		String hql = " from IrrFile irrf where irrf.irr.id=:irrId and  irrf.type = :type";
		Map<String,Object> properties = new HashMap<String,Object>();
		properties.put("irrId", irrId);
			properties.put("type", type);
		
		List list = irrFileDao.findByHql(hql,10, properties);
		return list;
	
	}

	@Override
	public void deleteAll() {
		irrFileDao.removeAll();

	}
}
