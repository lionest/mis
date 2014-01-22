package com.htlab.mis.web.action;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.htlab.mis.common.RetCode;
import com.htlab.mis.entity.Role;
import com.htlab.mis.service.RoleService;
import com.htlab.mis.util.StringUtil;

public class RoleAction extends BaseAction {

	private static final long serialVersionUID = 813116148656670072L;
	@Autowired
	private RoleService roleService;

	private Role model;
	private String code;
	private String name;

	public String list() {
		try {
			Map<String, Object> properties = new HashMap<String, Object>();
			if (StringUtil.isNotEmpty(name)) {
				properties.put("name", "%" + name + "%");
			}
			if (StringUtil.isNotEmpty(code)) {
				properties.put("code", "%" + code + "%");
			}
			pageInfo = roleService.findPagerByMap(pageIndex, pageSize,
					properties);

		} catch (Exception e) {
			log.error("list error", e);
		}
		return LIST;
	}

	public String gotoAdd() {
		try {

		} catch (Exception e) {

		}
		return EDIT;
	}

	public String edit() {
		try {
			if(model.getId()!=null){
				model=roleService.get(model.getId());
			}
		} catch (Exception e) {
			log.error("edit error", e);
		}
		return EDIT;
	}

	public String add() {
		try {
			if (StringUtil.isEmpty(model.getName())) {
				resultMap.put("retcode", RetCode.FAIL);
				resultMap.put("retmsg", "请设置名称");
				return EDIT;
			}
			if (StringUtil.isEmpty(model.getCode())) {
				resultMap.put("retcode", RetCode.FAIL);
				resultMap.put("retmsg", "请设置代码");
				return EDIT;
			}
			boolean existed = roleService.checkExist(model.getName(), 0);
			if (existed) {
				resultMap.put("retcode", RetCode.FAIL);
				resultMap.put("retmsg", "账号已存在");
				return EDIT;
			}
			roleService.save(model);

			resultMap.put("retcode", RetCode.SUCCESS);
			resultMap.put("retmsg", "保存成功");
		} catch (Exception e) {
			log.error("add error", e);
			resultMap.put("retcode", RetCode.FAIL);
			resultMap.put("retmsg", "保存失败");
			return EDIT;
		}

		return DOLIST;
	}

	public String update() {
		try {
			boolean existed = roleService.checkExist(model.getName(),
					model.getId());
			if (existed) {
				resultMap.put("retcode", RetCode.FAIL);
				resultMap.put("retmsg", "角色已存在");
				return EDIT;
			}

			Role au = roleService.get(model.getId());
			PropertyUtils.copyProperties(au, model);
			roleService.update(au);

			resultMap.put("retcode", RetCode.SUCCESS);
			resultMap.put("retmsg", "更新成功");
		} catch (Exception e) {
			log.error("add error", e);
			resultMap.put("retcode", RetCode.FAIL);
			resultMap.put("retmsg", "更新失败");
		}

		return EDIT;
	}

	public String delete() {
		try {
			roleService.delete(model);
			resultMap.put("retcode", RetCode.SUCCESS);
			resultMap.put("retmsg", "删除成功");
			log.info("delete model success,id=" + model.getId());
		} catch (Exception e) {
			log.error("delete model error:id=" + model.getId(), e);
			resultMap.put("retcode", RetCode.FAIL);
			resultMap.put("retmsg", "删除失败，该信息存在关联数据，不能删除");
		}
		return JSON;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Role getModel() {
		return model;
	}

	public void setModel(Role model) {
		this.model = model;
	}

}
