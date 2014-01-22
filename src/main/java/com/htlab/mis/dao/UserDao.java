package com.htlab.mis.dao;

import java.util.Map;

import com.htlab.mis.entity.User;
import com.htlab.mis.util.PageInfo;

public interface UserDao extends BaseDao<User, Integer> {
	
	/**
	 * 判断用户是否是有效用户
	 * @param code
	 * @param password
	 * @return
	 */
	public User validateUser(String code, String password);
	
	
}
