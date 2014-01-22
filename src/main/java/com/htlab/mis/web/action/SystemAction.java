package com.htlab.mis.web.action;

import java.io.IOException;

import org.apache.struts2.json.JSONException;
import org.apache.struts2.json.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;

import com.htlab.mis.common.RetCode;
import com.htlab.mis.service.IrrFileService;
import com.htlab.mis.service.IrrService;
import com.htlab.mis.service.PartService;
import com.htlab.mis.service.SupplierService;
import com.htlab.mis.service.UserService;

public class SystemAction extends BaseAction {

	@Autowired
	private IrrService irrService;
	@Autowired
	private IrrFileService irrFileService;
	@Autowired
	private PartService partService;
	@Autowired
	private SupplierService supplierService;
	@Autowired
	private UserService userService;

	public void clean() {
		try {
			// 删除数据
			 partService.deleteAll();
			 supplierService.deleteAll();
			irrService.deleteAll();
			irrFileService.deleteAll();
		} catch (Exception e) {
			e.printStackTrace();
			str2Resp("<script>alert('数据清除失败');</script>");
			return;
		}
		str2Resp("<script>alert('数据清除成功');</script>");
	}

}
