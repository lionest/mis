package com.htlab.mis.service;

import java.util.Map;

import com.htlab.mis.entity.Role;
import com.htlab.mis.util.PageInfo;


public interface RoleService extends BaseService<Role, Integer> {
	public PageInfo findPagerByMap(int pageIndex,int pageSize,Map<String,Object> properties);
	public boolean checkExist(String username,Integer notThisUserId);
}
