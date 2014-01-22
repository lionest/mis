package com.htlab.mis.web.action;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.htlab.mis.common.RetCode;
import com.htlab.mis.entity.Part;
import com.htlab.mis.entity.Supplier;
import com.htlab.mis.service.PartService;
import com.htlab.mis.service.SupplierService;
import com.htlab.mis.util.StringUtil;

/** 零件 */
public class PartAction extends BaseAction {

	@Autowired
	private PartService partService;

	@Autowired
	private SupplierService supplierService;
	
	private Part model;
	private Integer supId;

	private String pids;
	public String getPids() {
		return pids;
	}

	public void setPids(String pids) {
		this.pids = pids;
	}

	public Integer getSupId() {
		return supId;
	}

	public void setSupId(Integer supId) {
		this.supId = supId;
	}

	private List<Supplier> spList;
	private File upload;
	private String uploadFileName;
	private String uploadContentType;
	
	//查询出所有的供应商
	public List getSpList(){
		return supplierService.getAllSuppliers();
	}

	public String list() {
		try {
			Map<String, Object> properties = new HashMap<String, Object>();
			if(model !=null){
//				if((model.getSupplier().getId()>0)){
//					properties.put("supplier", "%"+model.getSupplier().getId()+"%");
//				}
				if((model.getName()!=null)){
					properties.put("name", "%"+model.getName()+"%");
				}
			}
			pageInfo = partService.findPagerByMap(pageIndex, pageSize, properties);
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
			model = partService.get(model.getId());
		} catch (Exception e) {
			log.error("edit error", e);
		}
		return EDIT;
	}

	public String update() {
		try {
			if(supId>0){
				model.setSupplier(supplierService.get(supId));
			}
			Part au = partService.get(model.getId());
			PropertyUtils.copyProperties(au, model);
			partService.update(au);

			resultMap.put("retcode", RetCode.SUCCESS);
			resultMap.put("retmsg", "更新成功");
		} catch (Exception e) {
			log.error("add error", e);
			resultMap.put("retcode", RetCode.FAIL);
			resultMap.put("retmsg", "更新失败");
		}

		return EDIT;
	}

	public String add() {
		try {
			if(supId>0){
				model.setSupplier(supplierService.get(supId));
			}
			partService.save(model);
			
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

	public String delete() {
		try {
			model=partService.get(model.getId());
			model.setStatus(1);
			partService.update(model);
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
	//批量删除
		public String deletes(){
			if(pids==null||pids.length()==0){
				resultMap.put("retcode", RetCode.FAIL);
				resultMap.put("retmsg", "删除失败，没有数据可以删除");
				return JSON;
			}
			String[] suds=pids.split(",");
			for(String sid:suds){
				if(sid.trim().length()==0)continue;
				model=partService.get(Integer.parseInt(sid));
				model.setStatus(1);
				partService.update(model);
			}
			resultMap.put("retcode", RetCode.SUCCESS);
			resultMap.put("retmsg", "批量删除成功");
			log.info("batch delete model success,pids="+pids);
			return JSON;
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
				List<Part> list = new ArrayList<Part>();
				StringBuffer supsnames=new StringBuffer();
				for (int i = rowStart; i < rowNum; i++) {
					Cell[] cells = sheet.getRow(i);

					if (cells.length < 4) {
						log.warn("列数不够，忽略");
						continue;
					}

					String name = StringUtil.replaceSpecialChar(cells[0]
							.getContents());
					String code = StringUtil.replaceSpecialChar(cells[2]
							.getContents());
					String supplier = StringUtil.replaceSpecialChar(cells[3]
							.getContents());
					
					if(!StringUtil.isNotEmpty(supplier)){
						continue;
					}
					
					if (StringUtil.isNotEmpty(name) && !keySet.contains(name)) {
						Part sp = new Part();
						sp.setName(name);
						sp.setNum(code);
						List<Supplier> sups=supplierService.findByProperty("name", supplier.trim());
						if(sups==null||sups.size()==0){
							log.error("导入时"+supplier+"供应商不存在");
							supsnames.append(supplier+"  ");
							continue;
						}
						sp.setSupplier(sups.get(0));
						keySet.add(name);
						list.add(sp);
						count++;
					}
				}

				if (count == 0) {
					getRequest().setAttribute("retMsg", "没有合格的数据");
					return "import";
				}

				String result = partService.saveImportData((List<Part>) list);
				
				if(supsnames.toString().length()>0){
					getRequest().setAttribute("retMsg", result+",以下供应商不存在:"+supsnames);
				}else{
					getRequest().setAttribute("retMsg", result);
				}
				
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

	public File getUpload() {
			return upload;
		}

		public void setUpload(File upload) {
			this.upload = upload;
		}

	public Part getModel() {
		return model;
	}

	public void setModel(Part model) {
		this.model = model;
	}

}
