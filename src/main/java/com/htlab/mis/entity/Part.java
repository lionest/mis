package com.htlab.mis.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.htlab.mis.common.BaseObject;

@Entity
@Table(name = "part")
public class Part extends BaseObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7898622026887850097L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(name = "name", length = 50)
	private String name;
	@Column(name = "num", length = 100)
	private String num;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}
	@Column(name = "status", length = 50)
	private Integer status=0 ;//状态备用
	

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@NotFound(action = NotFoundAction.IGNORE)
	@JoinColumn(name = "supplier")
	private Supplier supplier;
}
