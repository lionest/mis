package com.htlab.mis.dao;

import com.htlab.mis.entity.IrrFile;


public interface IrrFileDao extends BaseDao<IrrFile, Integer> {
	public void removeAll();
	
}
