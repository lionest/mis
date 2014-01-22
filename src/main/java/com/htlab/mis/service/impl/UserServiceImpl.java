package com.htlab.mis.service.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.htlab.mis.dao.UserDao;
import com.htlab.mis.entity.User;
import com.htlab.mis.service.UserService;
import com.htlab.mis.util.PageInfo;
@Service
public class UserServiceImpl extends BaseServiceImpl<User, Integer> implements UserService {

	@Autowired
	private UserDao userDao;

	@Autowired
	public void setBaseDao(){
		super.baseDao=userDao;
	}

	
	public User validateUser(String username, String password) {
		return userDao.validateUser(username, password);
	}

	
	public PageInfo findPagerByMap(int pageIndex,int pageSize,Map<String,Object> properties){
		String hql = "from User where 1=1 and status=0";
		
		if(properties.containsKey("nickname")){
			hql+=" and nickname like :nickname";
		}
		if(properties.containsKey("username")){
			hql+=" and username like :username";
		}
		return userDao.findPageInfoByHql(pageIndex, pageSize, hql, properties);
	}
	
	public boolean checkExist(String username,Integer notThisUserId){
		Map<String,Object> properties = new HashMap<String,Object>();
		String hql = "select count(*) from User where 1=1 ";
		hql+=" and username =:username and status=1";
		properties.put("username", username);
		if(notThisUserId > 0){
			hql+=" and id != :userId";
			properties.put("userId", notThisUserId);
		}
		Long count = (Long)userDao.getUniqueBeanResult(hql, properties);
		if(count > 0){
			return true;
		}
		return false;
	}
	
	public List findByRoleCode(String code){
		String hql = "from User u where u.role.code =:code and u.status=0";
		Map<String,Object> properties = new HashMap<String,Object>();
		properties.put("code", code);
		return userDao.findByHql(hql, properties);
	}
}
