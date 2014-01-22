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

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.htlab.mis.common.BaseObject;


@Entity
@Table(name="user")
public class User extends BaseObject {

	private static final long serialVersionUID = 4818646457731700997L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	@Column(name="username", length=50)  
	private String username; 		// 用户登录账号   
	
	@Column(name="password", length=50 )
	private String password;	//密码
	
	@Column
	private String email;  // 邮箱
	
	@Column
	private String nickname;  // 用户姓名
	
	@Column(length=13)
	private String mobile;  //手机号
	
	@Column
	private String irrYear;

	@Column
	private Integer irrSeqnum;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="last_login_time")
	private Date lastLoginTime=new Date(); //最后登录时间
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_time")
	private Date createTime=new Date();  //创建时间
	
	@ManyToOne( cascade={CascadeType.PERSIST, CascadeType.MERGE})
	@NotFound(action = NotFoundAction.IGNORE)
	@JoinColumn(name="role_id")
	private Role role;
	
	@Column
	private Integer status=0; //状态备用
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Integer getIrrSeqnum() {
		return irrSeqnum;
	}

	public void setIrrSeqnum(Integer irrSeqnum) {
		this.irrSeqnum = irrSeqnum;
	}

	public String getIrrYear() {
		return irrYear;
	}

	public void setIrrYear(String irrYear) {
		this.irrYear = irrYear;
	}
	
}
