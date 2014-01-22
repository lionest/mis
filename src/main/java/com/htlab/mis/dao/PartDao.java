package com.htlab.mis.dao;

import com.htlab.mis.entity.Part;

public interface PartDao extends BaseDao<Part, Integer>{
	public void removeAll();
}
