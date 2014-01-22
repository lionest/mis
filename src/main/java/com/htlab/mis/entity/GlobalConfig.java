package com.htlab.mis.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.htlab.mis.common.BaseObject;


@Entity
@Table(name = "global_config")
public class GlobalConfig extends BaseObject {

	private static final long serialVersionUID = 5728265723411641476L;
	@Id()
	private Long id;
	@Column(name = "data_code")
	private String dataCode;
	@Column(name = "data_name")
	private String dataName;
	@Column(name = "data_desc")
	private String dataDesc;
	@Column(name = "data_value")
	private String dataValue;
	@Column(name = "remark")
	private String remark;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDataCode() {
		return dataCode;
	}
	public void setDataCode(String dataCode) {
		this.dataCode = dataCode;
	}
	public String getDataName() {
		return dataName;
	}
	public void setDataName(String dataName) {
		this.dataName = dataName;
	}
	public String getDataDesc() {
		return dataDesc;
	}
	public void setDataDesc(String dataDesc) {
		this.dataDesc = dataDesc;
	}
	public String getDataValue() {
		return dataValue;
	}
	public void setDataValue(String dataValue) {
		this.dataValue = dataValue;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
