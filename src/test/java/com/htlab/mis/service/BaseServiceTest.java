package com.htlab.mis.service;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

@ContextConfiguration(locations={"classpath:/spring/applicationContext*.xml"})
public class BaseServiceTest extends AbstractJUnit4SpringContextTests {

}
