package com.htlab.mis.dao;

import com.htlab.mis.entity.Irr;



public interface IrrDao extends BaseDao<Irr, Integer> {
	public void removeAll();
}
