package com.htlab.mis.service;


import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class TestIrrService extends BaseServiceTest {
	@Autowired
	private IrrService irrService;
	
	@Test
	public void testStatsSupplier(){
		List list = irrService.statsSupplier(null, null);
		System.out.println(list);
	}
	@Test
	public void testStatsIrr(){
		List list = irrService.statsIrr(null, null);
		System.out.println(list);
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
