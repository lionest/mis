package com.htlab.mis.web.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.htlab.mis.common.Constants;
import com.htlab.mis.common.RetCode;
import com.htlab.mis.entity.Role;
import com.htlab.mis.entity.User;
import com.htlab.mis.service.RoleService;
import com.htlab.mis.service.UserService;
import com.htlab.mis.util.MD5Generator;
import com.htlab.mis.util.StringUtil;



public class UserAction extends BaseAction{
	
	private static final long serialVersionUID = 813116148656670072L;
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	
	private User model;
	private String checkCode;
	private String newPwd;
	private String reptPwd;
	private String username;
	private String nickname;
	private String password;
	private Integer roleId;
	private String uids;
	public String getUids() {
		return uids;
	}

	public void setUids(String uids) {
		this.uids = uids;
	}

	private List roleList;
	
	public List getRoleList(){
		return roleService.getAll();
	}
	
	public String login(){
		
		log.debug("checkCode:"+getSession().get(Constants.CHECK_CODE));
		log.debug("req checkcode= "+checkCode);
		
		if(!checkCode.equalsIgnoreCase((String)getSession().get(Constants.CHECK_CODE))){
			resultMap.put("retcode", RetCode.FAIL);
			resultMap.put("retmsg", "验证码错误");
		}else{
			model = userService.validateUser(model.getUsername(), MD5Generator.getMD5Value(model.getPassword()));
			if(model!=null){
				
				if(model.getStatus()==1){
					resultMap.put("retcode", RetCode.FAIL);
					resultMap.put("retmsg", "用户不存在");
				}else{
					getSession().put(Constants.SESSION_ADMIN_USER, model);
					resultMap.put("retcode", RetCode.SUCCESS);
					resultMap.put("retmsg", "登陆成功");
				}
			}else{
				resultMap.put("retcode", RetCode.FAIL);
				resultMap.put("retmsg", "帐号或密码不正确");
			}
		}

		getSession().remove(Constants.CHECK_CODE);
		return JSON;
	}
	
	public String logout(){
		this.getSession().clear();
		return SUCCESS;
	}
	
	public String list()
	{
		try {
			Map<String,Object> properties = new HashMap<String,Object>();
			if(StringUtil.isNotEmpty(username)){
				properties.put("username", "%"+username+"%");
			}
			if(StringUtil.isNotEmpty(nickname)){
				properties.put("nickname", "%"+nickname+"%");
			}
			
			pageInfo = userService.findPagerByMap(pageIndex, pageSize,properties );
			
		} catch (Exception e) {
			log.error("list error",e);
		}
		return LIST;
	}
	
	public String gotoAdd(){
		try{
			
		}catch(Exception e){
			
		}
		return EDIT;
	}
	
	
	public String edit()
	{
		try {
			model = userService.get(model.getId());
		} catch (Exception e) {
			log.error("edit error",e);
		}
		return EDIT;
	}

	public String add()
	{
		try {
			if(!StringUtil.isEmail(model.getEmail())){
				resultMap.put("retcode", RetCode.FAIL);
				resultMap.put("retmsg", "邮箱格式不正确");
				return EDIT;
			}
			if(StringUtil.isEmpty(model.getPassword()) ){
				resultMap.put("retcode", RetCode.FAIL);
				resultMap.put("retmsg", "请设置密码");
				return EDIT;
			}
			if(StringUtil.isEmpty(model.getNickname()) ){
				resultMap.put("retcode", RetCode.FAIL);
				resultMap.put("retmsg", "请设置姓名");
				return EDIT;
			}
			boolean existed = userService.checkExist(model.getUsername(),0);
			if(existed){
				resultMap.put("retcode", RetCode.FAIL);
				resultMap.put("retmsg", "账号已存在");
				return EDIT;
			}
			if(roleId > 0){
				Role role = roleService.get(roleId);
				model.setRole(role);
			}
			
			model.setPassword(MD5Generator.getMD5Value(model.getPassword()));
			userService.save(model);
			
			resultMap.put("retcode", RetCode.SUCCESS);
			resultMap.put("retmsg", "保存成功");
		}catch(Exception e){
			log.error("add error",e);
			resultMap.put("retcode", RetCode.FAIL);
			resultMap.put("retmsg", "保存失败");
			return EDIT;
		}
		
		return DOLIST;
	}
	
	
	public String update()
	{
		try {
			boolean existed = userService.checkExist(model.getUsername(), model.getId());
			if(existed){
				resultMap.put("retcode", RetCode.FAIL);
				resultMap.put("retmsg", "账号已存在");
				return EDIT;
			}
			
			User au = userService.get(model.getId());
			if( ! StringUtil.isEmpty(model.getPassword())){
				model.setPassword(MD5Generator.getMD5Value(model.getPassword()));
			}else{
				model.setPassword(au.getPassword());
			}
			if(roleId > 0){
				Role role = roleService.get(roleId);
				model.setRole(role);
			}
			model.setCreateTime(au.getCreateTime());
			
			PropertyUtils.copyProperties(au, model);
			userService.update(au);
			
			resultMap.put("retcode", RetCode.SUCCESS);
			resultMap.put("retmsg", "更新成功");
		}catch(Exception e){
			log.error("add error",e);
			resultMap.put("retcode", RetCode.FAIL);
			resultMap.put("retmsg", "更新失败");
		}
		
		return EDIT;
	}
	
	public String updateUser()
	{
		try {
			//当前登录的用户
			User cu = (User)session.get(Constants.SESSION_ADMIN_USER);
			
			
			if( ! StringUtil.isEmpty(model.getPassword())){//更新密码
				
				String oldPwd = MD5Generator.getMD5Value(model.getPassword());
				
				log.debug("cu.getPassword() {}",MD5Generator.getMD5Value(cu.getPassword()));
				log.debug("adminUser.getPassword() {}",MD5Generator.getMD5Value(model.getPassword()));
				log.debug("newPwd {}",MD5Generator.getMD5Value(newPwd));
				
				if(!cu.getPassword().equals(oldPwd)){
					resultMap.put("retcode", RetCode.FAIL);
					resultMap.put("retmsg", "原密码不正确，不能更新密码");
					return JSON;
				}
				
				cu.setPassword(MD5Generator.getMD5Value(newPwd));
			}
			cu.setNickname(model.getNickname());
			cu.setMobile(model.getMobile());
			cu.setEmail(model.getEmail());
			userService.update(cu);
			session.put(Constants.SESSION_ADMIN_USER,cu);
			resultMap.put("retcode", RetCode.SUCCESS);
			resultMap.put("retmsg", "保存成功");
		}catch(Exception e){
			log.error("update error",e);
			resultMap.put("retcode", RetCode.FAIL);
			resultMap.put("retmsg", "保存失败");
		}
		return JSON;
	}
	
	public String delete(){
		try{
			model=userService.get(model.getId());
			model.setStatus(1);
			userService.update(model);
			resultMap.put("retcode", RetCode.SUCCESS);
			resultMap.put("retmsg", "删除成功");
			log.info("delete model success,id="+model.getId());
		}catch(Exception e){
			log.error("delete model error:id="+model.getId(),e);
			resultMap.put("retcode", RetCode.FAIL);
			resultMap.put("retmsg", "删除失败，该信息存在关联数据，不能删除");
		}
		return JSON;
	}
	
	//批量删除
	public String deletes(){
		if(uids==null||uids.length()==0){
			resultMap.put("retcode", RetCode.FAIL);
			resultMap.put("retmsg", "删除失败，没有数据可以删除");
			return JSON;
		}
		String[] uds=uids.split(",");
		for(String uid:uds){
			if(uid.trim().length()==0)continue;
			model=userService.get(Integer.parseInt(uid));
			model.setStatus(1);
			userService.update(model);
		}
		resultMap.put("retcode", RetCode.SUCCESS);
		resultMap.put("retmsg", "批量删除成功");
		log.info("batch delete model success,uids="+uids);
		return JSON;
	}
	
	public String getReptPwd() {
		return reptPwd;
	}

	public void setReptPwd(String reptPwd) {
		this.reptPwd = reptPwd;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCheckCode() {
		return checkCode;
	}

	public void setCheckCode(String checkCode) {
		this.checkCode = checkCode;
	}

	public User getModel() {
		return model;
	}

	public void setModel(User model) {
		this.model = model;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getNewPwd() {
		return newPwd;
	}

	public void setNewPwd(String newPwd) {
		this.newPwd = newPwd;
	}


}
