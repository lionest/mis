package com.htlab.mis.service;


import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.htlab.mis.entity.User;

public class TestUserService extends BaseServiceTest {
	@Autowired
	private UserService userService;
	
	@Test
	@Ignore
	public void testValidateUser(){
		User user=userService.validateUser("zxm", "pass");
//		System.out.println(user.getPass()+":"+user.getCode());
//		assertThat(user.getCode(),Matchers.equalTo("zxm"));
		
	}
	
	@Test
	public void testFindPageInfoBySql(){
	/*//	PageInfo pageInfo=userService.findUsersBySql(1, 10, null);
		System.out.println(pageInfo);
		System.out.println(pageInfo.getRecordeCount());
		User user=(User) pageInfo.getPageData().get(0);
		System.out.println(user.getName());*/
	}
	
	
	@Before
	public void testBefore(){
		System.out.println(System.currentTimeMillis());
	}
	
	@After
	public void testAfter(){
		System.out.println(System.currentTimeMillis());
	}


}
