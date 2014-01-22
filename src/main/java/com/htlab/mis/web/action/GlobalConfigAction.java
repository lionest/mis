package com.htlab.mis.web.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.htlab.mis.common.RetCode;
import com.htlab.mis.entity.GlobalConfig;
import com.htlab.mis.service.GlobalConfigService;

public class GlobalConfigAction  extends BaseAction {
	
	@Autowired
	private GlobalConfigService globalConfigService;
	
	private GlobalConfig globalConfig;
	
	
	public String list(){
		try{
			List<GlobalConfig> list = globalConfigService.getAll();
			
			getRequest().setAttribute("list", list);
		}catch(Exception e){
			log.error("list error ",e);
		}
		return LIST;
	}


	
	public String update(){
		try{
			
			GlobalConfig old =  globalConfigService.getByDataCode(globalConfig.getDataCode());
			old.setDataValue(globalConfig.getDataValue());
			globalConfigService.update(old);
			resultMap.put("retcode", RetCode.SUCCESS);
			resultMap.put("retmsg", "保存成功");
			
		}catch(Exception e){
			log.error("update error ",e);
			resultMap.put("retcode", RetCode.FAIL);
			resultMap.put("retmsg", "保存失败");
		}
		return JSON;
	}


	public GlobalConfig getGlobalConfig() {
		return globalConfig;
	}


	public void setGlobalConfig(GlobalConfig globalConfig) {
		this.globalConfig = globalConfig;
	}
	
}
