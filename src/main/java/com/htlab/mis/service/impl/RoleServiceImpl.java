package com.htlab.mis.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.htlab.mis.dao.RoleDao;
import com.htlab.mis.entity.Role;
import com.htlab.mis.service.RoleService;
import com.htlab.mis.util.PageInfo;

@Service
public class RoleServiceImpl extends BaseServiceImpl<Role, Integer> implements
		RoleService {

	@Autowired
	private RoleDao roleDao;

	@Autowired
	public void setBaseDao() {
		super.baseDao = roleDao;
	}

	
	public PageInfo findPagerByMap(int pageIndex, int pageSize,
			Map<String, Object> properties) {
		String hql = "from Role where 1=1 ";

		if (properties.containsKey("name")) {
			hql += " and name like :name";
		}
		if (properties.containsKey("code")) {
			hql += " and code like :code";
		}
		return roleDao.findPageInfoByHql(pageIndex, pageSize, hql, properties);
	}

	public boolean checkExist(String role, Integer notThisUserId) {
		Map<String, Object> properties = new HashMap<String, Object>();
		String hql = "select count(*) from Role where 1=1 ";
		hql += " and name =:name";
		properties.put("name", role);
		if (notThisUserId > 0) {
			hql += " and id != :roleId";
			properties.put("roleId", notThisUserId);
		}
		Long count = (Long) roleDao.getUniqueBeanResult(hql, properties);
		if (count > 0) {
			return true;
		}
		return false;
	}

}
