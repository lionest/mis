package com.htlab.mis.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.htlab.mis.common.BaseObject;
import com.htlab.mis.util.PropertiesLoader;


@Entity
@Table(name="irr_file")
public class IrrFile extends BaseObject {

	private static final long serialVersionUID = 481864645777L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	@Column(name="filename")  
	private String filename;
	
	@Column
	private String type; //qe mrb
	
	@Column
	private String path;//存储路径
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_time")
	private Date createTime=new Date();  //创建时间
	
	@ManyToOne( cascade={CascadeType.PERSIST, CascadeType.MERGE})
	@NotFound(action = NotFoundAction.IGNORE)
	@JoinColumn(name="irr_id")
	private Irr irr;
	
	@Column
	private Integer status=0; //状态备用
	
	@Transient
	private String downUrl;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Irr getIrr() {
		return irr;
	}

	public void setIrr(Irr irr) {
		this.irr = irr;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getDownUrl() {
		return PropertiesLoader.getInstance().getProperty("file.url.prefix")+path;
	}

	public void setDownUrl(String downUrl) {
		this.downUrl = downUrl;
	}

	
}
