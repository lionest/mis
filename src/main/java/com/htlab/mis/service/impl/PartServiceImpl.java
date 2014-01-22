package com.htlab.mis.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.htlab.mis.dao.PartDao;
import com.htlab.mis.entity.Part;
import com.htlab.mis.entity.Supplier;
import com.htlab.mis.service.PartService;
import com.htlab.mis.util.PageInfo;

@Service
public class PartServiceImpl extends BaseServiceImpl<Part, Integer> implements
		PartService {

	@Autowired
	private PartDao partDao;

	@Autowired
	public void setBaseDao() {
		super.baseDao = partDao;
	}

	public PageInfo findPagerByMap(int pageIndex, int pageSize,
			Map<String, Object> properties) {
		String hql = "from Part part  where 1=1 and status=0 ";

		if (properties.containsKey("supplier")) {
			hql += " and part.supplier.id = :supplier";
		}
		if (properties.containsKey("name")) {
			hql += " and part.name like :name";
		}
		hql += " order by name desc";
		return partDao.findPageInfoByHql(pageIndex, pageSize, hql, properties);
	}

	
public String saveImportData(List<Part> list){
		
		String hql = "from Part where name = :name and status=0";
		Map<String,Object> properties = new HashMap<String,Object>();
		int updateCount = 0;
		int addCount = 0;
		for(int i=0;i<list.size();i++){
			Part sp = list.get(i);
			properties.put("name", sp.getName());
			Part persist = (Part)partDao.getUniqueBeanResult(hql, properties);
			if(persist!=null){
				try {
					sp.setId(persist.getId());
					PropertyUtils.copyProperties(persist, sp);
					partDao.update(persist);
					updateCount++;
				} catch (Exception e) {
					log.error("saveImportData PropertyUtils.copyProperties error ",e);
				}
			}else{
				partDao.save(sp);
				addCount++;
			}
		}
		return "共导入"+list.size()+"条,其中新增"+addCount+"条,更新"+updateCount+"条";
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Part> getAllParts() {
		return partDao.findByHql("from Part where status=0 order by name");
	}

	
	@Override
	public void deleteAll() {
		partDao.removeAll();

	}
}
