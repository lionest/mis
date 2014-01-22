package com.htlab.mis.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.htlab.mis.common.Constants;
import com.htlab.mis.dao.UserDao;
import com.htlab.mis.entity.User;
import com.htlab.mis.util.MD5Generator;
import com.htlab.mis.util.PageInfo;
import com.htlab.mis.util.StringUtil;

@Repository
public class UserDaoImpl extends BaseDaoImpl<User, Integer> implements UserDao {

	public User validateUser(String username, String password) {
		String hql="from User user where user.username =:username and user.password=:password";
		Map<String,Object> properties=new HashMap<String,Object>();
		properties.put("username", username);
		properties.put("password",password);
		User user=(User) this.getUniqueBeanResult(hql, properties);
		return user;
	}

//	public static void main(String[] args) {
//		System.out.println(MD5Generator.getMD5Value("admin123"));
//	}
}
