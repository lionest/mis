package com.htlab.mis.service;

import java.util.List;
import java.util.Map;

import com.htlab.mis.entity.User;
import com.htlab.mis.util.PageInfo;

public interface UserService extends BaseService<User, Integer> {

	/**
	 * 查看登录用户是否是有效的用户
	 * 
	 * @param code
	 * @param password
	 * @return
	 */
	public User validateUser(String username, String password);
	
	
	public PageInfo findPagerByMap(int pageIndex,int pageSize,Map<String,Object> properties);
	
	public boolean checkExist(String username,Integer notThisUserId);
	
	public List findByRoleCode(String code);
}
