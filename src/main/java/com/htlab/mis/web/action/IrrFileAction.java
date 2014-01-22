package com.htlab.mis.web.action;

import java.io.File;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.htlab.mis.entity.Irr;
import com.htlab.mis.entity.IrrFile;
import com.htlab.mis.service.IrrFileService;
import com.htlab.mis.service.IrrService;
import com.htlab.mis.util.FileOperatorUtil;
import com.htlab.mis.util.PropertiesLoader;

/** 拒收报告附件 */
public class IrrFileAction extends BaseAction {

	@Autowired
	private IrrService irrService;
	@Autowired
	private IrrFileService irrFileService;

	private Irr model;

	private String type;// 文件类型
	private Integer cldType;// 处理单类型
	private java.util.List<File> uploads;
	private java.util.List<String> uploadsFileName;
	private java.util.List<String> uploadsContentType;

	public String upload() {

		try {
			if (uploads == null || uploads.size() == 0) {
				request.setAttribute("retMsg", "请选择要上传的文件");
				return EDIT;
			}
			Irr irr = irrService.get(model.getId());

			if ("mbr".equals(type)) {
				irr.setMbrFile(1);// 标记已上传mbr附件
				irr.setMbrTime(new Date());
			}

			if ("sqe".equals(type)) {
				irr.setHandleTime(new Date());
				if (0 >= cldType) {
					request.setAttribute("retMsg", "请选择处理单类型");
					return EDIT;
				}

				irr.setSqeFile(1);// 标记已上传sqe附件
				irr.setCldType(cldType);// 处理单类型
			}
			for (int i = 0; i < uploads.size(); i++) {
				if (uploads.get(i).length() > 20048000) {
					request.setAttribute("retMsg", "文件太大，超过了20M");
					return EDIT;
				}

				String fileName = uploadsFileName.get(i);
				String ext = fileName.substring(fileName.lastIndexOf("."));

				if (ext.toLowerCase().contains("exe")) {
					request.setAttribute("retMsg", "不可以上传exe文件");
					return EDIT;
				}
				//评审单只可以上传PDF格式
				if ("mbr".equals(type)) {
					if (ext.toLowerCase().contains("pdf")) {
					}else{
						request.setAttribute("retMsg", "只可以上传PDF格式");
						return EDIT;
					}
					//是否需要发送邮件通知
					
				}
				String seperatorPath = irr.getCode() + File.separator;
				String newFileName = seperatorPath + irr.getCode()
						+ type.toUpperCase() + "_" + System.currentTimeMillis()
						+ ext;
				String dstPath = PropertiesLoader.getInstance().getProperty(
						"uploaded.file.path");

				File dstDirPath = new File(dstPath + seperatorPath);
				if (!dstDirPath.exists()) {
					dstDirPath.mkdirs();
				}

				File dst = new File(dstPath + newFileName);

				FileOperatorUtil.copy(uploads.get(i), dst);

				IrrFile entity = new IrrFile();
				entity.setFilename(uploadsFileName.get(i));
				entity.setIrr(irr);
				entity.setType(type);
				entity.setPath(newFileName.replaceAll("\\\\", "/"));
				irrFileService.save(entity);
			}

			irrService.update(irr);
			request.setAttribute("retMsg", "上传成功");
		} catch (Exception e) {
			log.error("上传失败", e);
			request.setAttribute("retMsg", "上传失败");
			return EDIT;
		}

		return EDIT;
	}

	public String gotoUpload() {
		try {

		} catch (Exception e) {

		}
		return EDIT;
	}

	public String listByIrr() {
		List list = irrFileService.findByIrrAndType(model.getId(), type);
		resultMap.put("list", list);
		return JSON;
	}

	public Irr getModel() {
		return model;
	}

	public void setModel(Irr model) {
		this.model = model;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public java.util.List<File> getUploads() {
		return uploads;
	}

	public void setUploads(java.util.List<File> uploads) {
		this.uploads = uploads;
	}

	public java.util.List<String> getUploadsFileName() {
		return uploadsFileName;
	}

	public void setUploadsFileName(java.util.List<String> uploadsFileName) {
		this.uploadsFileName = uploadsFileName;
	}

	public java.util.List<String> getUploadsContentType() {
		return uploadsContentType;
	}

	public void setUploadsContentType(java.util.List<String> uploadsContentType) {
		this.uploadsContentType = uploadsContentType;
	}

	public Integer getCldType() {
		return cldType;
	}

	public void setCldType(Integer cldType) {
		this.cldType = cldType;
	}

}
