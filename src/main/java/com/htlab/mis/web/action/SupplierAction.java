package com.htlab.mis.web.action;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.htlab.mis.common.RetCode;
import com.htlab.mis.entity.Part;
import com.htlab.mis.entity.Supplier;
import com.htlab.mis.service.PartService;
import com.htlab.mis.service.SupplierService;
import com.htlab.mis.util.StringUtil;

/** 供应商 */
public class SupplierAction extends BaseAction {

	@Autowired
	private SupplierService supplierService;
	
	@Autowired
	private PartService partService;

	private File upload;
	private Supplier model;
	private String sids;

	public String getSids() {
		return sids;
	}

	public void setSids(String sids) {
		this.sids = sids;
	}

	public String importData() {
		// 导入分组模版
		Workbook book = null;
		long start = System.currentTimeMillis();
		try {

			WorkbookSettings setting = new WorkbookSettings();
			java.util.Locale locale = new java.util.Locale("zh ", "CN ");
			setting.setLocale(locale);
			book = Workbook.getWorkbook(this.upload);

			// 获得第一个工作表对象
			Sheet sheet = book.getSheet(0);
			// 得到第一列第一行的单元格
			int rowNum = sheet.getRows();
			// 循环读入每一行的每一列
			int rowStart = 1; // 定义开始的一行 一般是从第二行开始的！

			int count = 0;
			Set<String> keySet = new HashSet<String>();
			List<Supplier> list = new ArrayList<Supplier>();
			for (int i = rowStart; i < rowNum; i++) {
				Cell[] cells = sheet.getRow(i);

				if (cells.length < 6) {
					log.warn("列数不够，忽略");
					continue;
				}

				String name = StringUtil.replaceSpecialChar(cells[0]
						.getContents());
				String code = StringUtil.replaceSpecialChar(cells[1]
						.getContents());
				String email = StringUtil.replaceSpecialChar(cells[2]
						.getContents());
				String sqe = StringUtil.replaceSpecialChar(cells[3]
						.getContents());
				String sqeEmail = StringUtil.replaceSpecialChar(cells[4]
						.getContents());
				String type = StringUtil.replaceSpecialChar(cells[5]
						.getContents());
				
				if(StringUtil.isNotEmpty(email)){
					String[] array = email.split(";");
					for(int k=0;k<array.length;k++){
						if(StringUtil.isNotEmpty(array[k]) && !StringUtil.isEmail(array[k])){
							log.debug(name+" : "+cells[2].getContents());
							log.debug(name+" : "+email);
							getRequest().setAttribute("retMsg", name+",多个邮箱请用分号;分隔");
							return "import";
						}
					}
				}
				
				if (StringUtil.isNotEmpty(name) && !keySet.contains(name)) {
					Supplier sp = new Supplier();
					sp.setName(name);
					sp.setCode(code);
					sp.setEmail(email);
					sp.setSqe(sqe);
					sp.setSqeEmail(sqeEmail);
					sp.setType(type);
					keySet.add(name);
					list.add(sp);
					count++;
				}
			}

			if (count == 0) {
				getRequest().setAttribute("retMsg", "没有合格的数据");
				return "import";
			}

			String result = supplierService.saveImportData((List<Supplier>) list);

			getRequest().setAttribute("retMsg", result);
		} catch (jxl.read.biff.BiffException jxl) {
			log.error("导入失败, 文件格式不正确。");
			getRequest().setAttribute("retMsg", "导入失败, 文件格式不正确。");
			return "import";
		} catch (Exception e) {
			log.error("导入数据报错 ", e);
			getRequest().setAttribute("retMsg", "导入失败, 请稍后重试或联系系统管理员。");
			return "import";
		} finally {
			if (null != book) {
				book.close();
			}
			log.debug("导入共耗时{}秒",
					(System.currentTimeMillis() - start) / 1000);
		}
		return "import";
	}

	public String list() {
		try {
			pageInfo = supplierService.findPagerByModel(pageIndex, pageSize,
					model);
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
	
	public String gotoImport() {
		try {

		} catch (Exception e) {

		}
		return "import";
	}

	public String edit() {
		try {
			model = supplierService.get(model.getId());
		} catch (Exception e) {
			log.error("edit error", e);
		}
		return EDIT;
	}

	public String add() {
		try {
			boolean existed = supplierService.checkExist(model.getName(), 0);
			if (existed) {
				resultMap.put("retcode", RetCode.FAIL);
				resultMap.put("retmsg", "供应商已存在");
				return EDIT;
			}
			supplierService.save(model);

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
			boolean existed = supplierService.checkExist(model.getName(),
					model.getId());
			if (existed) {
				resultMap.put("retcode", RetCode.FAIL);
				resultMap.put("retmsg", "供应商已存在");
				return EDIT;
			}

			Supplier au = supplierService.get(model.getId());
			PropertyUtils.copyProperties(au, model);

			supplierService.update(au);

			resultMap.put("retcode", RetCode.SUCCESS);
			resultMap.put("retmsg", "更新成功");
		} catch (Exception e) {
			log.error("add error", e);
			resultMap.put("retcode", RetCode.FAIL);
			resultMap.put("retmsg", "更新失败");
		}

		return EDIT;
	}
	@Transactional( rollbackFor={Exception.class}) 
	public String delete(){
		try{
			
			model=supplierService.get(model.getId());
			model.setStatus(1);
			supplierService.update(model);
			//删除关联零件
			List<Part> parts=partService.findByProperty("supplier.id", model.getId());
			for(Part p:parts){
				p.setStatus(1);
				partService.update(p);
			}
			
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
	@Transactional( rollbackFor={Exception.class}) 
	public String deletes(){
		if(sids==null||sids.length()==0){
			resultMap.put("retcode", RetCode.FAIL);
			resultMap.put("retmsg", "删除失败，没有数据可以删除");
			return JSON;
		}
		String[] suds=sids.split(",");
		for(String sid:suds){
			if(sid.trim().length()==0)continue;
			model=supplierService.get(Integer.parseInt(sid));
			model.setStatus(1);
			supplierService.update(model);
			
			//删除关联零件
			List<Part> parts=partService.findByProperty("supplier.id", model.getId());
			for(Part p:parts){
				p.setStatus(1);
				partService.update(p);
			}
		}
		resultMap.put("retcode", RetCode.SUCCESS);
		resultMap.put("retmsg", "批量删除成功");
		log.info("batch delete model success,uids="+sids);
		return JSON;
	}
	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public Supplier getModel() {
		return model;
	}

	public void setModel(Supplier model) {
		this.model = model;
	}

}
