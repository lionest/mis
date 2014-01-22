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
import javax.persistence.Transient;
import javax.persistence.Version;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.htlab.mis.common.BaseObject;
import com.htlab.mis.util.DateUtil;
import com.htlab.mis.util.PropertiesLoader;


@Entity
@Table(name="irr")
public class Irr extends BaseObject {

	private static final long serialVersionUID = 645773L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	@Column(name="code" ,nullable=false,unique=true)
	private String code;
	
	@Column
	private String linJianMingCheng; 
	
	@Column()
	private String linJianHao;
	
	@Column()
	private String piCiHao;
	
	@Column()
	private String shuLiang;
	@Column()
	private String img; //不合格相关的图片
	
	@Column(length=1000)
	private String miaoShu;
	
	@Column()
	private String category;//不合格类别1 尺寸，2材料、3性能、4其他
	
	@Column(length=1000)
	private String shenHeYiJian;
	
	@Column()
	private Integer state=1; //1暂存 2提交审核  3审核通过  4审核不通过
	
	@Column()
	private Integer mbrFile=0; //1MBR已上传MBR评审报告
	
	@Column()
	private Integer sqeFile=0;//1 SQE已上传处理单
	@Column()
	private Integer closed=0;//1 质保经理是否关闭该报告
	@Column()
	private Integer cldType;//处理单类型：1 偏差 2筛选 3返工 4退货 5报废6.试装
	
	@Column()
	private Date createTime ;
	
	@Column()
	private Date delaytime;
	
	@Column()
	private Date cancelTime;
	
	@Column()
	private Date agreeTime;
	@Column()
	private Date handleTime;
	@Column()
	private Date mbrTime ;
	
	@Column()
	private Date rejectTime;
	
	public Date getRejectTime() {
		return rejectTime;
	}

	public void setRejectTime(Date rejectTime) {
		this.rejectTime = rejectTime;
	}

	@Column()
	private Date closeTime;
	
	public Date getCancelTime() {
		return cancelTime;
	}

	public void setCancelTime(Date cancelTime) {
		this.cancelTime = cancelTime;
	}

	public Date getAgreeTime() {
		return agreeTime;
	}

	public void setAgreeTime(Date agreeTime) {
		this.agreeTime = agreeTime;
	}

	public Date getHandleTime() {
		return handleTime;
	}

	public void setHandleTime(Date handleTime) {
		this.handleTime = handleTime;
	}

	public Date getMbrTime() {
		return mbrTime;
	}

	public void setMbrTime(Date mbrTime) {
		this.mbrTime = mbrTime;
	}

	public Date getCloseTime() {
		return closeTime;
	}

	public void setCloseTime(Date closeTime) {
		this.closeTime = closeTime;
	}

	public Date getDelaytime() {
		return delaytime;
	}

	public void setDelaytime(Date delaytime) {
		this.delaytime = delaytime;
	}

	@Column()
	private Date modifyTime ;
	
	@ManyToOne( cascade={CascadeType.PERSIST, CascadeType.MERGE})
	@NotFound(action = NotFoundAction.IGNORE)
	@JoinColumn(name="qa_id")
	private User qa;
	
	@ManyToOne( cascade={CascadeType.PERSIST, CascadeType.MERGE})
	@NotFound(action = NotFoundAction.IGNORE)
	@JoinColumn(name="qe_id")
	private User qe;
	
	@ManyToOne( cascade={CascadeType.PERSIST, CascadeType.MERGE})
	@NotFound(action = NotFoundAction.IGNORE)
	@JoinColumn(name="operator")
	private User operator;
	
	public User getOperator() {
		return operator;
	}

	public void setOperator(User operator) {
		this.operator = operator;
	}

	@ManyToOne( cascade={CascadeType.PERSIST, CascadeType.MERGE})
	@NotFound(action = NotFoundAction.IGNORE)
	@JoinColumn(name="sp_id")
	private Supplier sp;
	
	@Version
    @Column
	private Integer version;

	@Transient
	private String imgUrl;
	@Transient
	private String createTimeStr;
	@Transient
	public static int STATE_SAVE = 1;
	@Transient
	public static int STATE_SUBMIT = 2;
	@Transient
	public static int STATE_AGREE = 3;
	@Transient
	public static int STATE_REJECT = 4;
	
	@Transient
	public static int STATE_CANCEL = 5;
	
	@Transient
	public static int STATE_DELAY = 6;
	

	@Column
	private Integer delay;
	
	@Column
	private String delayreason;
	
	public String getDelayreason() {
		return delayreason;
	}

	public void setDelayreason(String delayreason) {
		this.delayreason = delayreason;
	}

	public Integer getDelay() {
		return delay;
	}

	public void setDelay(Integer delay) {
		this.delay = delay;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getLinJianMingCheng() {
		return linJianMingCheng;
	}

	public void setLinJianMingCheng(String linJianMingCheng) {
		this.linJianMingCheng = linJianMingCheng;
	}

	public String getLinJianHao() {
		return linJianHao;
	}

	public void setLinJianHao(String linJianHao) {
		this.linJianHao = linJianHao;
	}

	public String getPiCiHao() {
		return piCiHao;
	}

	public void setPiCiHao(String piCiHao) {
		this.piCiHao = piCiHao;
	}

	public String getShuLiang() {
		return shuLiang;
	}

	public void setShuLiang(String shuLiang) {
		this.shuLiang = shuLiang;
	}

	public String getMiaoShu() {
		return miaoShu;
	}

	public void setMiaoShu(String miaoShu) {
		this.miaoShu = miaoShu;
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

	public User getQa() {
		return qa;
	}

	public void setQa(User qa) {
		this.qa = qa;
	}

	public User getQe() {
		return qe;
	}

	public void setQe(User qe) {
		this.qe = qe;
	}

	public Supplier getSp() {
		return sp;
	}

	public void setSp(Supplier sp) {
		this.sp = sp;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getShenHeYiJian() {
		return shenHeYiJian;
	}

	public void setShenHeYiJian(String shenHeYiJian) {
		this.shenHeYiJian = shenHeYiJian;
	}

	public String getCreateTimeStr() {
		if(createTime == null){
			return "";
		}
		return DateUtil.formatDate(createTime, "yyyy-MM-dd");
	}

	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}

	public Integer getMbrFile() {
		return mbrFile;
	}

	public void setMbrFile(Integer mbrFile) {
		this.mbrFile = mbrFile;
	}

	public Integer getSqeFile() {
		return sqeFile;
	}

	public void setSqeFile(Integer sqeFile) {
		this.sqeFile = sqeFile;
	}

	public Integer getClosed() {
		return closed;
	}

	public void setClosed(Integer closed) {
		this.closed = closed;
	}


	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}
	
	public String getImgUrl() {
		return PropertiesLoader.getInstance().getProperty("file.url.prefix")+img;
	}

	public Integer getCldType() {
		return cldType;
	}

	public void setCldType(Integer cldType) {
		this.cldType = cldType;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	
}
