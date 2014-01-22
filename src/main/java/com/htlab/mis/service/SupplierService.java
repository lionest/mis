package com.htlab.mis.service;

import java.util.List;

import com.htlab.mis.entity.Supplier;
import com.htlab.mis.util.PageInfo;


public interface SupplierService extends BaseService<Supplier, Integer> {
	
	public PageInfo findPagerByModel(int pageIndex,int pageSize,Supplier supplier);
	public List<Supplier> getAllSuppliers();
	
	public boolean checkExist(String name,Integer notThisId);
	
	public String saveImportData(List<Supplier> list);
	public void deleteAll();
}
