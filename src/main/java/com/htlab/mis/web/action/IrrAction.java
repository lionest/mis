package com.htlab.mis.web.action;

import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Vector;

import javax.mail.internet.MimeUtility;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts2.json.JSONException;
import org.apache.struts2.json.JSONUtil;
import org.apache.struts2.json.annotations.JSON;
import org.springframework.beans.factory.annotation.Autowired;

import com.htlab.mis.common.Constants;
import com.htlab.mis.common.RetCode;
import com.htlab.mis.entity.GlobalConfig;
import com.htlab.mis.entity.Irr;
import com.htlab.mis.entity.Part;
import com.htlab.mis.entity.Supplier;
import com.htlab.mis.entity.User;
import com.htlab.mis.service.GlobalConfigService;
import com.htlab.mis.service.IrrService;
import com.htlab.mis.service.PartService;
import com.htlab.mis.service.SupplierService;
import com.htlab.mis.service.UserService;
import com.htlab.mis.util.DateUtil;
import com.htlab.mis.util.FileOperatorUtil;
import com.htlab.mis.util.JxlsUtil;
import com.htlab.mis.util.MailUtil;
import com.htlab.mis.util.PropertiesLoader;
import com.htlab.mis.util.StringUtil;

/** 拒收报告 */
public class IrrAction extends BaseAction {
	@Autowired
	private IrrService irrService;
	@Autowired
	private SupplierService supplierService;
	@Autowired
	private UserService userService;
	@Autowired
	private GlobalConfigService globalConfigService;

	public void setParts(List<Part> parts) {
		this.parts = parts;
	}
	@Autowired
	private PartService partService;

	private Irr model;


	private String startTime;
	private String endTime;
	private String jsonData;

	public String getJsonData() {
		return jsonData;
	}

	public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
	}
	private int spId;
	private int qeId;
	private String isShow;

	private File upload;

	public List<Part> getParts() {
		parts= partService.getAllParts();
		return parts;

	}

	private String uploadFileName;
	private String uploadContentType;
	private List<Part> parts;

	// 查询出所有的供应商
	public List getSpList() {
		return supplierService.getAllSuppliers();
	}

	// 查询出所有的QE和QEZG
	public List getQeList() {
		List<User> qe=userService.findByRoleCode("QE");
		List<User> qe1=userService.findByRoleCode("QEZG");
		qe.addAll(qe1);
		return qe;
	}

	// 报告一览
	public String listReport() {
		try {

			Map<String, Object> properties = new HashMap<String, Object>();
			if (model != null) {
				if (StringUtil.isNotEmpty(model.getCode())) {
					properties.put("code", "%" + model.getCode() + "%");
				}
				if (StringUtil.isNotEmpty(model.getLinJianMingCheng())) {
					properties.put("linJianMingCheng",
							"%" + model.getLinJianMingCheng() + "%");
				}
				if (StringUtil.isNotEmpty(model.getLinJianHao())) {
					properties.put("linJianHao", "%" + model.getLinJianHao()
							+ "%");
				}
				if (StringUtil.isNotEmpty(model.getPiCiHao())) {
					properties.put("piCiHao", "%" + model.getPiCiHao() + "%");
				}
				if (spId > 0) {
					properties.put("spId", spId);
				}
				if (model.getState() != null && model.getState() > 0) {
					properties.put("state", model.getState());
				}
				if (StringUtil.isNotEmpty(startTime)
						&& StringUtil.isNotEmpty(endTime)) {
					String pattern = "yyyy-MM-dd HH:mm";
					properties.put("startTime",
							DateUtil.parseDate(startTime, pattern));
					properties.put("endTime",
							DateUtil.parseDate(endTime, pattern));
				}
			}
			pageSize = 50;
			pageInfo = irrService.findPagerByMap(pageIndex, pageSize,
					properties);
		} catch (Exception e) {
			log.error("reportList error", e);
		}
		return "listReport";
	}

	public String list() {
		try {

			User user = (User) session.get(Constants.SESSION_ADMIN_USER);
			Map<String, Object> properties = new HashMap<String, Object>();
			if (user != null && user.getRole() != null) {
				if ("QA".equalsIgnoreCase(user.getRole().getCode())) {
					properties.put("qaId", user.getId());
				} else if ("QE".equalsIgnoreCase(user.getRole().getCode())) {
					properties.put("qeId", user.getId());
				}else if("OD".equalsIgnoreCase(user.getRole().getCode())){
					properties.put("role", "OD");
				}else if("GM".equalsIgnoreCase(user.getRole().getCode())){
					properties.put("role", "GM");
				}else if("CG".equalsIgnoreCase(user.getRole().getCode())){
					properties.put("role", "CG");
				}else if("SQE".equalsIgnoreCase(user.getRole().getCode())){
					properties.put("role", "SQE");
					properties.put("sqe",user.getNickname());
				}
			}
			if (model != null) {
				if (StringUtil.isNotEmpty(model.getCode())) {
					properties.put("code", "%" + model.getCode() + "%");
				}
				if (StringUtil.isNotEmpty(model.getLinJianMingCheng())) {
					properties.put("linJianMingCheng",
							"%" + model.getLinJianMingCheng() + "%");
				}
				if (StringUtil.isNotEmpty(model.getLinJianHao())) {
					properties.put("linJianHao", "%" + model.getLinJianHao()
							+ "%");
				}
				if (StringUtil.isNotEmpty(model.getPiCiHao())) {
					properties.put("piCiHao", "%" + model.getPiCiHao() + "%");
				}
				if (spId > 0) {
					properties.put("spId", spId);
				}
				if (model.getState() != null && model.getState() > 0) {
					properties.put("state", model.getState());
				}
				if (StringUtil.isNotEmpty(startTime)
						&& StringUtil.isNotEmpty(endTime)) {
					String pattern = "yyyy-MM-dd HH:mm";
					properties.put("startTime",
							DateUtil.parseDate(startTime, pattern));
					properties.put("endTime",
							DateUtil.parseDate(endTime, pattern));
				}
			}
			pageInfo = irrService.findPagerByMap(pageIndex, pageSize,
					properties);
		} catch (Exception e) {
			log.error("list error", e);
		}
		return LIST;
	}
	
	private void fillJsonData(){
		StringBuilder sb=new StringBuilder();
		sb.append("[");
		List<Part> parts=getParts();
		int i=0;
		for(Part p:parts){
			sb.append("{");
			sb.append("'name':'"+p.getName()+"','spId':'"+p.getSupplier().getId()+"','id':'"+p.getId()+"','partNum':'"+p.getNum()+"'");
			if(i<parts.size()-1){
				sb.append("},");	
			}else{
				sb.append("}");
			}
			i++;
		}
		sb.append("]");
		jsonData=sb.toString();
	}

	public String gotoAdd() {
		try {
			User qa = (User) session.get(Constants.SESSION_ADMIN_USER);

			if (!"QA".equalsIgnoreCase(qa.getRole().getCode())) {
				return NOPRIVILEGE;
			}
			model = new Irr();
			model.setState(0);
			fillJsonData();
			
		} catch (Exception e) {

		}
		return EDIT;
	}

	public String edit() {
		try {
			model = irrService.get(model.getId());
			fillJsonData();
		} catch (Exception e) {
			log.error("edit error", e);
		}
		return EDIT;
	}
	// 暂存后 IRR可删除
	public String delete() {
		try {
			User qa = (User) session.get(Constants.SESSION_ADMIN_USER);
			response.setContentType("text/html");
			if (!"QA".equalsIgnoreCase(qa.getRole().getCode())) {
				response.getWriter().print("{'retcode':'没有权限'}");
				return DOLIST;
			}
			if(model.getId()==null){
				response.getWriter().print("{'retcode':'没有IRR可删除'}");
				return DOLIST;
			}
			irrService.removeById(model.getId());

			resultMap.put("retcode", RetCode.SUCCESS);
			resultMap.put("retmsg", "删除成功");
			response.getWriter().print(JSONUtil.serialize(resultMap));
			return DOLIST;
		} catch (Exception e) {
			log.error("add error", e);
			resultMap.put("retcode", RetCode.FAIL);
			resultMap.put("retmsg", "删除失败");
			try {
				response.getWriter().print(JSONUtil.serialize(resultMap));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return DOLIST;
		}

	}

	// 暂存报告
	public void add() {
		try {
			User qa = (User) session.get(Constants.SESSION_ADMIN_USER);
			response.setContentType("text/html");
			if (!"QA".equalsIgnoreCase(qa.getRole().getCode())) {
				response.getWriter().print("{'retcode':'没有权限'}");
				return;
			}

			qa = generateIrrCode(qa.getId());

			// 保存报告图片附件
			String retsult = saveUpload();
			if (StringUtil.isNotEmpty(retsult)) {
				resultMap.put("retcode", RetCode.FAIL);
				resultMap.put("retmsg", "图片保存失败," + retsult);
				response.getWriter().print(JSONUtil.serialize(resultMap));
				return;
			}

			User qe = userService.get(qeId);
			model.setQe(qe);
			model.setQa(qa);
			Supplier sp = supplierService.get(spId);
			model.setSp(sp);
			model.setModifyTime(new Date());
			model.setState(Irr.STATE_SAVE);
			irrService.save(model);
			// 更新该用户的报告编号信息
			userService.update(qa);

			resultMap.put("retcode", RetCode.SUCCESS);
			resultMap.put("retmsg", "保存成功");
			response.getWriter().print(JSONUtil.serialize(resultMap));
			return;
		} catch (Exception e) {
			log.error("add error", e);
			resultMap.put("retcode", RetCode.FAIL);
			resultMap.put("retmsg", "保存失败");
			try {
				response.getWriter().print(JSONUtil.serialize(resultMap));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return ;
		}

	}

	// 生成IRR编号
	private User generateIrrCode(Integer qaId) {

		User qa = userService.get(qaId);

		String irrYear = DateUtil.formatDate(new Date(), "yy");
		String irrCode = "IRR" + irrYear + qa.getId();
		if (!irrYear.equalsIgnoreCase(qa.getIrrYear())) {
			qa.setIrrYear(irrYear);
			qa.setIrrSeqnum(1);
		} else {
			qa.setIrrSeqnum(qa.getIrrSeqnum() + 1);
		}
		Integer irrSeqnum = qa.getIrrSeqnum();
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMinimumIntegerDigits(4);
		nf.setGroupingUsed(false);
		String irrSeqnumStr = nf.format(irrSeqnum);
		irrCode += irrSeqnumStr;
		log.debug("irrCode = " + irrCode);
		model.setCode(irrCode);
		return qa;
	}

	// 保存上传的报告图片附件
	private String saveUpload() {
		if (upload == null) {
			return null;
		}
		if (upload.length() > 3004800) {
			return "图片太大，超过了3M";
		}

		String ext = uploadFileName
				.substring(uploadFileName.lastIndexOf(".") + 1);
		if (!imgFileExtSet.contains(ext.toLowerCase())) {
			return "文件类型不正确";
		}

		String seperatorPath = model.getCode() + File.separator;
		String newFileName = seperatorPath + model.getCode() + "img_"
				+ System.currentTimeMillis() + "." + ext;
		String dstPath = PropertiesLoader.getInstance().getProperty(
				"uploaded.file.path");

		File dstDirPath = new File(dstPath + seperatorPath);
		if (!dstDirPath.exists()) {
			dstDirPath.mkdirs();
		}

		File dst = new File(dstPath + newFileName);

		try {
			FileOperatorUtil.copy(upload, dst);
		} catch (Exception e) {
			log.error("save irr img error", e);
		}

		model.setImg(newFileName.replaceAll("\\\\", "/"));
		return null;
	}

	// 更新暂存报告
	public void update() {
		try {
			response.setContentType("text/html");
			User qa = (User) session.get(Constants.SESSION_ADMIN_USER);

			model.setQa(qa);
			User qe = userService.get(qeId);
			model.setQe(qe);
			Supplier sp = supplierService.get(spId);
			model.setSp(sp);
			model.setState(Irr.STATE_SAVE);

			Irr old = irrService.get(model.getId());

			model.setCode(old.getCode());
			model.setVersion(old.getVersion());

			if (upload != null) {
				// 保存报告图片附件
				String retsult = saveUpload();
				if (StringUtil.isNotEmpty(retsult)) {
					resultMap.put("retcode", RetCode.FAIL);
					resultMap.put("retmsg", "图片保存失败," + retsult);
					response.getWriter().print(JSONUtil.serialize(resultMap));
					return;
				}
			} else {
				model.setImg(old.getImg());
			}

			PropertyUtils.copyProperties(old, model);
			irrService.update(old);

			resultMap.put("retcode", RetCode.SUCCESS);
			resultMap.put("retmsg", "更新成功");
			response.getWriter().print(JSONUtil.serialize(resultMap));
			return;
		} catch (Exception e) {
			log.error("update error", e);
			resultMap.put("retcode", RetCode.FAIL);
			resultMap.put("retmsg", "更新失败");
			try {
				response.getWriter().print(JSONUtil.serialize(resultMap));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return;
		}

	}

	// 提交报告
	public void submit() {
		try {
			response.setContentType("text/html");
			User qa = (User) session.get(Constants.SESSION_ADMIN_USER);

			model.setQa(qa);
			User qe = userService.get(qeId);
			model.setQe(qe);
			Supplier sp = supplierService.get(spId);
			model.setSp(sp);
			log.debug("submit id = " + model.getId());
			if (null == model.getId() || model.getId() == 0) {// 直接提交报告
				qa = generateIrrCode(qa.getId());

				// 保存报告图片附件
				String retsult = saveUpload();
				if (StringUtil.isNotEmpty(retsult)) {
					resultMap.put("retcode", RetCode.FAIL);
					resultMap.put("retmsg", "图片保存失败," + retsult);
					response.getWriter().print(JSONUtil.serialize(resultMap));
					return;
				}

				model.setState(Irr.STATE_SAVE);
				irrService.save(model);
				// 更新该用户的报告编号信息
				userService.update(qa);
			} else {// 将暂存报告提交
				if (upload != null) {
					// 保存报告图片附件
					String retsult = saveUpload();
					if (StringUtil.isNotEmpty(retsult)) {
						resultMap.put("retcode", RetCode.FAIL);
						resultMap.put("retmsg", "图片保存失败," + retsult);
						response.getWriter().print(JSONUtil.serialize(resultMap));
						return;
					}
				}
			}

			log.debug("irr id = " + model.getId());
			Irr old = irrService.get(model.getId());

			if (StringUtil.isEmpty(model.getImg())) {
				model.setImg(old.getImg());
			}
			model.setCode(old.getCode());
			model.setVersion(old.getVersion());
			model.setState(Irr.STATE_SUBMIT);
			model.setModifyTime(new Date());
			PropertyUtils.copyProperties(old, model);

			// 生成报告附件
			String reportFile = generateIrrReport(model);

			// 给QE发送邮件通知
			Properties prop = PropertiesLoader.getInstance();
			MailUtil sm = new MailUtil(prop.getProperty("mail.server"),
					prop.getProperty("mail.user"),
					prop.getProperty("mail.password"));
			String mailFrom = prop.getProperty("mail.user");
			String mailTo = MimeUtility.encodeText(qe.getNickname()) + "<"
					+ qe.getEmail() + ">";
			//抄送给QA,QE主管
			StringBuffer mailbccTo = new StringBuffer(qa.getEmail());
			List<User> qezg=userService.findByRoleCode("QEZG");
			if(qezg!=null){
				for(User u:qezg){
					String em=u.getEmail();
					if (StringUtil.isNotEmpty(em)
							&& StringUtil.isEmail(em)) {
						mailbccTo.append(",").append(u.getEmail());
					}
				}
			}
			//抄送给SQE主管
			List<User> sqezg=userService.findByRoleCode("SQEZG");
			if(sqezg!=null){
				for(User u:sqezg){
					String em=u.getEmail();
					if (StringUtil.isNotEmpty(em)
							&& StringUtil.isEmail(em)) {
						mailbccTo.append(",").append(u.getEmail());
					}
				}
			}
			
			String subject = qa.getNickname() + "  关于" + sp.getName() + "的拒收报告";
			StringBuffer msgContent = new StringBuffer();
			msgContent = msgContent
					.append(qe.getNickname())
					.append("，你好:<br/><br/>")
					.append("&nbsp;&nbsp;&nbsp;&nbsp;关于&nbsp;")
					.append(sp.getName())
					.append(old.getLinJianMingCheng())
					.append("&nbsp;的拒收报告见附件。<br/><br/>(该邮件由质检系统自动发出)<br/>&nbsp;")
					.append(qa.getNickname());
			Vector attachedFilePathList = new Vector();
			attachedFilePathList.add(reportFile);
			int result = sm.sendMail(mailTo, mailFrom, subject,
					msgContent.toString(), attachedFilePathList, mailbccTo.toString(), "");

			if (result != 0) {
				resultMap.put("retcode", RetCode.FAIL);
				resultMap.put("retmsg", "提交失败，邮件发送出现了问题");
				response.getWriter().print(JSONUtil.serialize(resultMap));
				return;
			} else {
				resultMap.put("retcode", RetCode.SUCCESS);
				irrService.update(old);
				resultMap.put("retmsg", "提交成功");
				response.getWriter().print(JSONUtil.serialize(resultMap));
			}
			// 删除临时文件
			File f = new File(reportFile);
			f.delete();
		} catch (Exception e) {
			log.error("update error", e);
			resultMap.put("retcode", RetCode.FAIL);
			resultMap.put("retmsg", "提交失败");
			try {
				response.getWriter().print(JSONUtil.serialize(resultMap));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return;
		}

	}

	// 同意报告
	public void agree() {
		try {
			response.setContentType("text/html");
			Irr old = irrService.get(model.getId());

			old.setState(Irr.STATE_AGREE);
			old.setShenHeYiJian(model.getShenHeYiJian());
			old.setModifyTime(new Date());
			old.setAgreeTime(new Date());
			// 生成报告附件
			String reportFile = generateIrrReport(old);

			User qe = old.getQe();
			User qa = old.getQa();
			Supplier sp = old.getSp();

			// 给QA，SQE，和供应商发送邮件通知
			Properties prop = PropertiesLoader.getInstance();
			MailUtil sm = new MailUtil(prop.getProperty("mail.server"),
					prop.getProperty("mail.user"),
					prop.getProperty("mail.password"));
			String mailFrom = prop.getProperty("mail.user");
			String mailTo = MimeUtility.encodeText(qa.getNickname()) + "<"
					+ qa.getEmail() + ">";
			StringBuffer mailccTo = new StringBuffer();
			if(sp!=null){
				String speqmail=sp.getSqeEmail();
				if(StringUtil.isNotEmpty(speqmail)){
					String[] sems=speqmail.split(";");
					if(sems.length>1){
						mailccTo = mailccTo.append(MimeUtility.encodeText(sp.getSqe()))
								.append("<").append(sems[0]).append(">,"+sems[1]);
					}else{
						mailccTo = mailccTo.append(MimeUtility.encodeText(sp.getSqe()))
								.append("<").append(sp.getSqeEmail()).append(">");
					}
				}
			}

 //保留功能 发给供应商
//			String email = sp.getEmail();
//			if (StringUtil.isNotEmpty(email)) {
//				email.replaceAll("；", ";");
//				String[] array = email.split(";");
//				for (int k = 0; k < array.length; k++) {
//					if (StringUtil.isNotEmpty(array[k])
//							&& StringUtil.isEmail(array[k])) {
//						mailccTo.append(",").append(sp.getSqeEmail());
//					}
//				}
//			}
			//根据供应商的属性发送邮件，国产，进口，型钢
			Supplier supplier=old.getSp();
			if(supplier!=null){
				if(supplier.getType().equals("国产件")){
					//订单中心
					@SuppressWarnings("unchecked")
					List<User> dingdanzhongxin=userService.findByRoleCode("OD");
					if(dingdanzhongxin!=null){
						for(User u:dingdanzhongxin){
							String em=u.getEmail();
							if(em!=null){
								String[] ems1=em.split(";");
								for (int k = 0; k < ems1.length; k++) {
									if (StringUtil.isNotEmpty(ems1[k])
											&& StringUtil.isEmail(ems1[k])) {
										mailccTo.append(",").append(ems1[k]);
									}
								}
							}
						}
					}
					
				}else if(supplier.getType().equals("进口件")){
					//国贸 一个账户俩个邮箱 ;分割
					@SuppressWarnings("unchecked")
					List<User> guomao=userService.findByRoleCode("GM");
					if(guomao!=null){
						for(User u:guomao){
							String ems=u.getEmail();
							if(ems!=null){
								String[] emss=ems.split(";");
								for (int k = 0; k < emss.length; k++) {
									if (StringUtil.isNotEmpty(emss[k])
											&& StringUtil.isEmail(emss[k])) {
										mailccTo.append(",").append(emss[k]);
									}
								}
							}
							break;
						}
					}
				}else if(supplier.getType().equals("型钢件")){
					//型钢
					//采购
					List<User> cg=userService.findByRoleCode("CG");
					if(cg!=null){
						for(User u:cg){
							String em=u.getEmail();
							if (StringUtil.isNotEmpty(em)
									&& StringUtil.isEmail(em)) {
								mailccTo.append(",").append(u.getEmail());
							}
						}
					}
					
				}else{
					System.out.println("------------选择的供应商类型有问题-------------");
				}
			}
			

//			List<String> ccMailList = getAgreeCCMail(sp.getType());
//
//			for (String str : ccMailList) {
//				mailccTo.append(",").append(str);
//			}

			String mailbccTo = qe.getEmail();
			String subject = qa.getNickname() + "  关于" + sp.getName()
					+ "的拒收报告已审批";
			StringBuffer msgContent = new StringBuffer();
			msgContent = msgContent
					.append(qa.getNickname())
					.append("，你好:<br/><br/>")
					.append("&nbsp;&nbsp;&nbsp;&nbsp;关于&nbsp;")
					.append(sp.getName())
					.append(old.getLinJianMingCheng())
					.append("&nbsp;的拒收报告已审批，详见附件，请及时召集评审。<br/><br/>(该邮件由质检系统自动发出)<br/>&nbsp;")
					.append(qa.getNickname());
			Vector attachedFilePathList = new Vector();
			attachedFilePathList.add(reportFile);

			int result = sm.sendMail(mailTo, mailFrom, subject,
					msgContent.toString(), attachedFilePathList, mailbccTo,
					mailccTo.toString());

			if (result != 0) {
				resultMap.put("retcode", RetCode.FAIL);
				resultMap.put("retmsg", "审核失败，邮件发送出现了问题");
				response.getWriter().print(JSONUtil.serialize(resultMap));
				return;
			} else {
				resultMap.put("retcode", RetCode.SUCCESS);
				irrService.update(old);
				resultMap.put("retmsg", "审核成功");
				response.getWriter().print(JSONUtil.serialize(resultMap));
			}
			// 删除临时文件
			File f = new File(reportFile);
			f.delete();
		} catch (Exception e) {
			log.error("update error", e);
			resultMap.put("retcode", RetCode.FAIL);
			resultMap.put("retmsg", "审核失败");
			try {
				response.getWriter().print(JSONUtil.serialize(resultMap));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return;
		}

	}
	

	/** 查询同意发布的报告应抄送的邮箱 */
	private List<String> getAgreeCCMail(String spType) {
		List<String> list = new ArrayList<String>();

		List<User> userList = new ArrayList<User>();
		if ("进口".equals(spType)) {
			userList = userService.findByRoleCode("GM");
		} else {
			userList = userService.findByRoleCode("OD");
		}

		for (User u : userList) {
			list.add(u.getEmail());
		}

		GlobalConfig cfg = globalConfigService.getByDataCode("se_email");
		if (cfg != null) {
			list.add(cfg.getDataValue());
		}
		return list;
	}

	// 撤销报告
	public void reject() {
		try {
			response.setContentType("text/html");
			Irr old = irrService.get(model.getId());
			User op = (User) session.get(Constants.SESSION_ADMIN_USER);
			old.setState(Irr.STATE_REJECT);
			old.setShenHeYiJian(model.getShenHeYiJian());
			old.setModifyTime(new Date());
			old.setRejectTime(new Date());

			// 生成报告附件
			String reportFile = generateIrrReport(old);

			User qe = old.getQe();
			User qa = old.getQa();
			Supplier sp = old.getSp();

			// 给QA发送邮件通知
			Properties prop = PropertiesLoader.getInstance();
			MailUtil sm = new MailUtil(prop.getProperty("mail.server"),
					prop.getProperty("mail.user"),
					prop.getProperty("mail.password"));
			String mailFrom = prop.getProperty("mail.user");
			String mailTo = MimeUtility.encodeText(qa.getNickname()) + "<"
					+ qa.getEmail() + ">";

			String mailbccTo = qe.getEmail();
			String subject = qa.getNickname() + "  关于" + sp.getName()
					+ "的拒收报告已撤销";
			StringBuffer msgContent = new StringBuffer();
			msgContent = msgContent.append(qa.getNickname())
					.append("，你好:<br/><br/>")
					.append("&nbsp;&nbsp;&nbsp;&nbsp;关于&nbsp;")
					.append(sp.getName()).append(old.getLinJianMingCheng())
					.append("&nbsp;的拒收报告已被"+op.getUsername()+"拒绝")
					.append("<br>详见附件。<br/><br/>(该邮件由质检系统自动发出)<br/>&nbsp;")
					.append(qa.getNickname());
			Vector attachedFilePathList = new Vector();
			attachedFilePathList.add(reportFile);

			int result = sm.sendMail(mailTo, mailFrom, subject,
					msgContent.toString(), attachedFilePathList, mailbccTo,
					null);

			if (result != 0) {
				resultMap.put("retcode", RetCode.FAIL);
				resultMap.put("retmsg", "撤销失败，邮件发送出现了问题");
				response.getWriter().print(JSONUtil.serialize(resultMap));
				return;
			} else {
				resultMap.put("retcode", RetCode.SUCCESS);
				old.setOperator(op);
				irrService.update(old);
				resultMap.put("retmsg", "撤销成功");
				response.getWriter().print(JSONUtil.serialize(resultMap));
			}
			// 删除临时文件
			File f = new File(reportFile);
			f.delete();
		} catch (Exception e) {
			log.error("update error", e);
			resultMap.put("retcode", RetCode.FAIL);
			resultMap.put("retmsg", "撤销失败");
			try {
				response.getWriter().print(JSONUtil.serialize(resultMap));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return;
		}

	}

	
	// 查看时间轴
		public String time() {
			try {
				int r=0;
				Irr old = irrService.get(model.getId());
				//编写时间轴参数
				if(old.getCreateTime()!=null){
					r=0;
				}
				if(old.getModifyTime()!=null){
					r=1;
				}
				if(old.getCancelTime()!=null){
					r=2;
				}
				if(old.getAgreeTime()!=null){
					r=3;
				}
				if(old.getRejectTime()!=null){
					r=4;
				}
				
				if(old.getMbrTime()!=null){
					r=5;
				}
				if(old.getHandleTime()!=null){
					r=6;
				}
				if(old.getCloseTime()!=null){
					r=7;
				}
				
				request.setAttribute("r", r);
				request.setAttribute("model", old);
			} catch (Exception e) {
				log.error("update error", e);
				resultMap.put("retcode", RetCode.FAIL);
				resultMap.put("retmsg", "查看失败");
			}

			return "time";
		}

	// 关闭报告
	public void close() {
		try {
			response.setContentType("text/html");
			Irr old = irrService.get(model.getId());
			old.setClosed(1);
			old.setModifyTime(new Date());
			old.setCloseTime(new Date());
			irrService.update(old);

			resultMap.put("retcode", RetCode.SUCCESS);
			resultMap.put("retmsg", "关闭成功");
			response.getWriter().print(JSONUtil.serialize(resultMap));
		} catch (Exception e) {
			log.error("update error", e);
			resultMap.put("retcode", RetCode.FAIL);
			resultMap.put("retmsg", "关闭失败");
			try {
				response.getWriter().print(JSONUtil.serialize(resultMap));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return ;
	}
	// 取消报告
	public String cancel() {
		try {

			Irr old = irrService.get(model.getId());
			old.setClosed(1);
			old.setModifyTime(new Date());
			old.setState(5);
			old.setCancelTime(new Date());
			irrService.update(old);

			resultMap.put("retcode", RetCode.SUCCESS);
			resultMap.put("retmsg", "取消成功");
			response.getWriter().print(JSONUtil.serialize(resultMap));
		} catch (Exception e) {
			log.error("update error", e);
			resultMap.put("retcode", RetCode.FAIL);
			resultMap.put("retmsg", "取消失败");
			try {
				response.getWriter().print(JSONUtil.serialize(resultMap));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		return DOLIST;
	}

	// 延迟 报告
	public void delay() {
		try {
			response.setContentType("text/html");
			Irr old = irrService.get(model.getId());
			old.setDelay(model.getDelay());
			String re=model.getDelayreason();
			if(re.length()>100){
				resultMap.put("retcode", RetCode.FAIL);
				resultMap.put("retmsg", "申请延迟失败，原因不要超过100个字");
				response.getWriter().print(JSONUtil.serialize(resultMap));
				return;
			}else{
				old.setDelayreason(re);
			}
			old.setDelaytime(new Date());
			irrService.update(old);
			User qa = old.getQa();
			Supplier sp = old.getSp();
			
			// 给QA，SQE发送邮件通知
			Properties prop = PropertiesLoader.getInstance();
			MailUtil sm = new MailUtil(prop.getProperty("mail.server"),
					prop.getProperty("mail.user"),
					prop.getProperty("mail.password"));
			String mailFrom = prop.getProperty("mail.user");
			String mailTo = MimeUtility.encodeText(qa.getNickname()) + "<"
					+ qa.getEmail() + ">";
			StringBuffer mailccTo = new StringBuffer();
			
			String speqmail=sp.getSqeEmail();
			if(StringUtil.isNotEmpty(speqmail)){
				String[] sems=speqmail.split(";");
				if(sems.length>1){
					mailccTo = mailccTo.append(MimeUtility.encodeText(sp.getSqe()))
							.append("<").append(sems[0]).append(">,"+sems[1]);
				}else{
					mailccTo = mailccTo.append(MimeUtility.encodeText(sp.getSqe()))
							.append("<").append(sp.getSqeEmail()).append(">");
				}
			}

			String email = sp.getEmail();
			if (StringUtil.isNotEmpty(email)) {
				email.replaceAll("；", ";");
				String[] array = email.split(";");
				for (int k = 0; k < array.length; k++) {
					if (StringUtil.isNotEmpty(array[k])
							&& StringUtil.isEmail(array[k])) {
						mailccTo.append(",").append(array[k]);
					}
				}
			}

//			List<String> ccMailList = getAgreeCCMail(sp.getType());
//
//			for (String str : ccMailList) {
//				mailccTo.append(",").append(str);
//			}

			String subject = qa.getNickname() + "  关于" + sp.getName()
					+ "的拒收报告已申请延迟";
			StringBuffer msgContent = new StringBuffer();
			msgContent = msgContent
					.append(qa.getNickname())
					.append("，你好:<br/><br/>")
					.append("&nbsp;&nbsp;&nbsp;&nbsp;关于&nbsp;")
					.append(sp.getName())
					.append(old.getLinJianMingCheng())
					.append("&nbsp;的拒收报告申请延迟"+model.getDelay()+"小时,原因是"+model.getDelayreason()+"。<br/><br/>(该邮件由质检系统自动发出)<br/>&nbsp;")
					.append(qa.getNickname());
			Vector attachedFilePathList = new Vector();
			int result = sm.sendMail(mailTo, mailFrom, subject,
					msgContent.toString(), attachedFilePathList, null,
					mailccTo.toString());

			if (result != 0) {
				resultMap.put("retcode", RetCode.FAIL);
				resultMap.put("retmsg", "申请延迟失败，邮件发送出现了问题");
				response.getWriter().print(JSONUtil.serialize(resultMap));
				return;
			} else {
				resultMap.put("retcode", RetCode.SUCCESS);
				irrService.update(old);
				resultMap.put("retmsg", "申请延迟成功");
				response.getWriter().print(JSONUtil.serialize(resultMap));
			}
		} catch (Exception e) {
			log.error("update error", e);
			resultMap.put("retcode", RetCode.FAIL);
			resultMap.put("retmsg", "延迟失败");
			try {
				response.getWriter().print(JSONUtil.serialize(resultMap));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return;
		}

	}
	// 生成报告附件
	private String generateIrrReport(Irr irr) throws IOException {
		Properties prop = PropertiesLoader.getInstance();
		String dstXlsFile = getRequest().getSession().getServletContext()
				.getRealPath("/")
				+ "/" + irr.getCode() + ".xls";
		Map beans = new HashMap();
		beans.put("model", irr);
		String templateFile = "/templates/template.xls";
		log.debug("excel templateFile =" + templateFile);
		String imgPath = null;
		if (StringUtil.isNotEmpty(irr.getImg())) {
			imgPath = PropertiesLoader.getInstance().getProperty(
					"uploaded.file.path")
					+ irr.getImg();
			log.debug("imgPath= " + imgPath);
		}
		JxlsUtil.export(templateFile, beans, dstXlsFile, imgPath);
		return dstXlsFile;
	}

	public Irr getModel() {
		return model;
	}

	public void setModel(Irr model) {
		this.model = model;
	}

	public IrrService getIrrService() {
		return irrService;
	}

	public void setIrrService(IrrService irrService) {
		this.irrService = irrService;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public int getSpId() {
		return spId;
	}

	public void setSpId(int spId) {
		this.spId = spId;
	}

	public int getQeId() {
		return qeId;
	}

	public void setQeId(int qeId) {
		this.qeId = qeId;
	}

	public String getIsShow() {
		return isShow;
	}

	public void setIsShow(String isShow) {
		this.isShow = isShow;
	}

	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public String getUploadContentType() {
		return uploadContentType;
	}

	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}

}
