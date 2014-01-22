package com.htlab.mis.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.htlab.mis.dao.SupplierDao;
import com.htlab.mis.entity.Supplier;
import com.htlab.mis.service.SupplierService;
import com.htlab.mis.util.PageInfo;
import com.htlab.mis.util.StringUtil;

@Service
public class SupplierServiceImpl extends BaseServiceImpl<Supplier, Integer>
		implements SupplierService {

	@Autowired
	private SupplierDao supplierDao;

	@Autowired
	public void setBaseDao() {
		super.baseDao = supplierDao;
	}

	public PageInfo findPagerByModel(int pageIndex, int pageSize,
			Supplier supplier) {
		String hql = "from Supplier where 1=1 and status=0 ";
		Map<String, Object> properties = new HashMap<String, Object>();
		if (supplier != null) {
			if (StringUtil.isNotEmpty(supplier.getName())) {
				hql += " and name like :name";
				properties.put("name", "%" + supplier.getName() + "%");
			}
			if (StringUtil.isNotEmpty(supplier.getCode())) {
				hql += " and code like :code";
				properties.put("code", "%" + supplier.getCode() + "%");
			}
			if (StringUtil.isNotEmpty(supplier.getSqe())) {
				hql += " and sqe like :sqe";
				properties.put("sqe", "%" + supplier.getSqe() + "%");
			}
		}
		return supplierDao.findPageInfoByHql(pageIndex, pageSize, hql,
				properties);
	}

	public boolean checkExist(String name, Integer notThisId) {
		Map<String, Object> properties = new HashMap<String, Object>();
		String hql = "select count(*) from Supplier where 1=1 and status=0 ";
		hql += " and name =:name";
		properties.put("name", name);
		if (notThisId > 0) {
			hql += " and id != :id";
			properties.put("id", notThisId);
		}
		Long count = (Long) supplierDao.getUniqueBeanResult(hql, properties);
		if (count > 0) {
			return true;
		}
		return false;
	}

	public String saveImportData(List<Supplier> list) {

		String hql = "from Supplier where name = :name and status=0";
		Map<String, Object> properties = new HashMap<String, Object>();
		int updateCount = 0;
		int addCount = 0;
		for (int i = 0; i < list.size(); i++) {
			Supplier sp = list.get(i);
			properties.put("name", sp.getName());
			Supplier persist = (Supplier) supplierDao.getUniqueBeanResult(hql,
					properties);
			if (persist != null) {
				try {
					sp.setId(persist.getId());
					PropertyUtils.copyProperties(persist, sp);
					persist.setModifyTime(new Date());
					supplierDao.update(persist);
					updateCount++;
				} catch (Exception e) {
					log.error(
							"saveImportData PropertyUtils.copyProperties error ",
							e);
				}
			} else {
				supplierDao.save(sp);
				addCount++;
			}
		}
		return "共导入" + list.size() + "条,其中新增" + addCount + "条,更新" + updateCount
				+ "条";
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Supplier> getAllSuppliers() {
		return supplierDao
				.findByHql("from Supplier where status=0 order by name");
	}

	@Override
	public void deleteAll() {
		supplierDao.removeAll();

	}
}
