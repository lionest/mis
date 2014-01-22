package com.htlab.mis.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.htlab.mis.dao.GlobalConfigDao;
import com.htlab.mis.entity.GlobalConfig;
import com.htlab.mis.service.GlobalConfigService;

@Service
public class GlobalConfigServiceImpl extends BaseServiceImpl<GlobalConfig, Long> implements GlobalConfigService {
	
	@Autowired
	private GlobalConfigDao globalConfigDao;

	@Autowired
	public void setBaseDao(GlobalConfigDao globalConfigDao) {
		super.baseDao = globalConfigDao;
	}

	
	public GlobalConfig getByDataCode(String dataCode) {

		String hql = "from GlobalConfig where dataCode = :dataCode";
		Map<String,Object> properties = new HashMap<String,Object>();
		properties.put("dataCode", dataCode);
		List<GlobalConfig> list = globalConfigDao.findByHql(hql, properties);
		if(list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	
}
