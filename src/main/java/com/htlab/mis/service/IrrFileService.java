package com.htlab.mis.service;

import java.util.List;

import com.htlab.mis.entity.IrrFile;


public interface IrrFileService extends BaseService<IrrFile, Integer> {
	public List findByIrrAndType(Integer irrId,String type);
	public void deleteAll();
}
