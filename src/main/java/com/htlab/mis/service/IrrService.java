package com.htlab.mis.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.htlab.mis.entity.Irr;
import com.htlab.mis.util.PageInfo;


public interface IrrService extends BaseService<Irr, Integer> {
	public PageInfo findPagerByMap(int pageIndex,int pageSize,Map<String,Object> properties);
	public List statsSupplier(Date startTime,Date endTime);
	public List statsIrr(Date startTime,Date endTime);
	
	/**周日到周四检查QE未处理的已提交状态的IRR报告，发送提醒邮件*/
	public void checkQEIrrState();
	public void deleteAll();
}
