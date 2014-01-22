package com.htlab.mis.dao;

import com.htlab.mis.entity.Supplier;



public interface SupplierDao extends BaseDao<Supplier, Integer> {
	public void removeAll();
}
