package com.htlab.mis.service;

import com.htlab.mis.entity.GlobalConfig;

public interface GlobalConfigService extends BaseService<GlobalConfig, Long> {
	public GlobalConfig getByDataCode(String dataCode) ;
}
