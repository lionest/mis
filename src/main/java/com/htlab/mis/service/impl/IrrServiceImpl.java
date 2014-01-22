package com.htlab.mis.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Vector;

import javax.mail.internet.MimeUtility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.htlab.mis.common.StatsInfo;
import com.htlab.mis.dao.IrrDao;
import com.htlab.mis.entity.Irr;
import com.htlab.mis.entity.Supplier;
import com.htlab.mis.entity.User;
import com.htlab.mis.service.IrrService;
import com.htlab.mis.util.MailUtil;
import com.htlab.mis.util.PageInfo;
import com.htlab.mis.util.PropertiesLoader;

@Service
public class IrrServiceImpl extends BaseServiceImpl<Irr, Integer> implements
		IrrService {

	@Autowired
	private IrrDao irrDao;

	@Autowired
	public void setBaseDao() {
		super.baseDao = irrDao;
	}

	public PageInfo findPagerByMap(int pageIndex, int pageSize,
			Map<String, Object> properties) {
		String hql = "from Irr irr where 1=1 ";
		if (properties.containsKey("role")) {
			hql += " and irr.state >2";
			String roleCode = properties.get("role").toString();
			if (roleCode.equalsIgnoreCase("OD")) {
				hql += " and irr.sp.type like '国产%'";
			}
			if (roleCode.equalsIgnoreCase("GM")) {
				hql += " and irr.sp.type like '进口%'";
			}
			if (roleCode.equalsIgnoreCase("CG")) {
				hql += " and irr.sp.type like '型钢%'";
			}
			if (properties.containsKey("sqe")) {
				String sqe = properties.get("sqe").toString();
				if (sqe != null) {
					hql += " and irr.sp.sqe =:sqe";
				}
			}
		}

		if (properties.containsKey("qaId")) {
			hql += " and irr.qa.id = :qaId";
		}
		if (properties.containsKey("qeId")) {
			hql += " and irr.qe.id = :qeId";
			hql += " and irr.state >= 2";
		}
		if (properties.containsKey("code")) {
			hql += " and irr.code like :code";
		}
		if (properties.containsKey("linJianMingCheng")) {
			hql += " and irr.linJianMingCheng like :linJianMingCheng";
		}
		if (properties.containsKey("linJianHao")) {
			hql += " and irr.linJianHao like :linJianHao";
		}
		if (properties.containsKey("piCiHao")) {
			hql += " and irr.piCiHao like :piCiHao";
		}
		if (properties.containsKey("spId")) {
			hql += " and irr.sp.id = :spId ";
		}
		if (properties.containsKey("state")) {
			hql += " and irr.state = :state ";
		}
		if (properties.containsKey("startTime")) {
			hql += " and irr.createTime >= :startTime";
		}
		if (properties.containsKey("endTime")) {
			hql += " and irr.createTime <= :endTime";
		}
		hql += " order by modifyTime desc";
		return irrDao.findPageInfoByHql(pageIndex, pageSize, hql, properties);
	}

	/** 统计供应商拒收报告数 */
	public List statsSupplier(Date startTime, Date endTime) {
		String hql = "select irr.sp.name , count(*)   from Irr irr where irr.state = "
				+ Irr.STATE_AGREE;
		Map<String, Object> properties = new HashMap<String, Object>();
		if (startTime != null) {
			hql += " and irr.createTime >= :startTime ";
			properties.put("startTime", startTime);
		}
		if (endTime != null) {
			hql += " and irr.createTime <= :endTime ";
			properties.put("endTime", endTime);
		}
		hql += " group by col_0_0_ order by col_1_0_ desc";

		List list = irrDao.findByHql(hql, 10, properties);
		List<StatsInfo> result = new ArrayList<StatsInfo>();
		for (int i = 0; i < list.size(); i++) {
			Object[] object = (Object[]) list.get(i);
			StatsInfo info = new StatsInfo();
			info.setName("" + object[0]);
			info.setCount("" + object[1]);
			result.add(info);
		}
		return result;
	}

	/** 统计每月的拒收报告数 */
	public List statsIrr(Date startTime, Date endTime) {
		String hql = "select cast(SUBSTR(createTime,1,7) as char(8) ) as mon , count(*) c  from Irr  where state = "
				+ Irr.STATE_AGREE;
		Map<String, Object> properties = new HashMap<String, Object>();
		if (startTime != null) {
			hql += " and createTime >= :startTime ";
			properties.put("startTime", startTime);
		}
		if (endTime != null) {
			hql += " and createTime <= :endTime ";
			properties.put("endTime", endTime);
		}
		hql += " group by mon ";

		List list = irrDao.findBySql(hql, properties);
		List<StatsInfo> result = new ArrayList<StatsInfo>();
		for (int i = 0; i < list.size(); i++) {
			Object[] object = (Object[]) list.get(i);
			StatsInfo info = new StatsInfo();
			info.setName("" + object[0]);
			info.setCount("" + object[1]);
			result.add(info);
		}
		return result;
	}

	@Scheduled(cron = "0 25 7 ? * MON-FRI")
	public void checkQEIrrState() {

		Calendar c = Calendar.getInstance();
		c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH) - 1);

		String hql = "from Irr where state = " + Irr.STATE_SUBMIT
				+ "  and createTime < :createTime";
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put("createTime", c.getTime());

		List<Irr> list = irrDao.findByHql(hql, properties);
		for (Irr irr : list) {
			Supplier sp = irr.getSp();
			User qe = irr.getQe();
			User qa = irr.getQa();

			Properties prop = PropertiesLoader.getInstance();
			MailUtil sm = new MailUtil(prop.getProperty("mail.server"),
					prop.getProperty("mail.user"),
					prop.getProperty("mail.password"));
			String mailFrom = prop.getProperty("mail.user");
			String mailTo = qe.getEmail();

			String subject = "请尽快处理 " + qa.getNickname() + "  关于"
					+ sp.getName() + "的拒收报告,编号" + irr.getCode();
			StringBuffer msgContent = new StringBuffer();
			msgContent = msgContent.append(qa.getNickname())
					.append("，你好:<br/><br/>")
					.append("&nbsp;&nbsp;&nbsp;&nbsp;请尽快处理关于&nbsp;")
					.append(sp.getName()).append(irr.getLinJianMingCheng())
					.append("&nbsp;的拒收报告，编号").append(irr.getCode())
					.append("<br/><br/>(该邮件由质检系统自动发出)<br/>&nbsp;");
			Vector attachedFilePathList = new Vector();

			sm.sendMail(mailTo, mailFrom, subject, msgContent.toString(),
					attachedFilePathList, null, null);

		}
	}

	@Override
	public void deleteAll() {
		irrDao.removeAll();

	}
}
