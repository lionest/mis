package com.htlab.mis.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.htlab.mis.common.BaseObject;


@Entity
@Table(name="supplier")
public class Supplier extends BaseObject {

	private static final long serialVersionUID = 64577317009972L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	@Column(name="name")  
	private String name;   
	
	@Column(name="code" )
	private String code;
	
	@Column(length=500)
	private String email; 
	
	@Column
	private String sqe; 
	
	@Column()
	private String sqeEmail;
	
	@Column()
	private String type;

	@Column()
	private Date createTime = new Date();

	@Column()
	private Date modifyTime = new Date();
	
	@Column
	private Integer status=0; //状态备用
	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSqe() {
		return sqe;
	}

	public void setSqe(String sqe) {
		this.sqe = sqe;
	}

	public String getSqeEmail() {
		return sqeEmail;
	}

	public void setSqeEmail(String sqeEmail) {
		this.sqeEmail = sqeEmail;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	} 
	
}
