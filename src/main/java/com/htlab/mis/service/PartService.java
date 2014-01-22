package com.htlab.mis.service;

import java.util.List;
import java.util.Map;

import com.htlab.mis.entity.Part;
import com.htlab.mis.entity.Supplier;
import com.htlab.mis.util.PageInfo;

public interface PartService extends BaseService<Part, Integer> {
	public PageInfo findPagerByMap(int pageIndex, int pageSize,
			Map<String, Object> properties);
	public List<Part> getAllParts();
	public String saveImportData(List<Part> list);
	public void deleteAll();
}
