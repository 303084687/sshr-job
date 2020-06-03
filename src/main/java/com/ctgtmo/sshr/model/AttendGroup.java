package com.ctgtmo.sshr.model;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>Company: 乾通互联(北京)科技有限公司</p>
 * <p>Description: 考勤组实体类</p>
 * <p>author feikanghui</p>
 * <p>2019年07月01日 13:35:36</p>
 */
public class AttendGroup implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer id;// 考勤组id
	
	private Integer companyId;// 公司id

	private String name;// 考勤组名称
	
	private Integer isDefault;// 是否默认(0:否 1:是)
	
	private Integer type;// 类型：1：固定 2：排班 3：自由
	
	private String digest;// 摘要(部门/员工)
	
	private boolean holidayIsopen;// 法定节假日自动排休状态 0：关闭 1：开启
	
	private boolean fieldIsClock;// 允许外勤打卡0：不允许 1：允许
	
	private boolean fieldIsRemark;// 外勤打卡是否备注0：关闭 1：开启
	
	private boolean fieldIsPhoto;// 外勤打卡是否拍照0：关闭 1：开启
	
	private String personality; // 个性设置 isOpen：是否开启个人设置1开启2关闭，isLateEarly：是否开始宽松上下班1开启2关闭，isStayArrive：是否开启晚走晚到1开启2关闭， {"isOpen": 1,"isLateEarly": {"isOpen": 1,"late": 5,"early": 5},"isStayArrive": {"isOpen": 1,"stay": 1,"arrive": 1}}
    
    private String absenteeism; // 旷工设置 isOpen：是否开启旷工设置1开启2关闭，late：迟到多少分钟算旷工，early：早退多少分钟算旷工，{"isOpen": 1,"late":30,"early":30}
   
	private Date createTime;// 创建时间
	
	private Date updateTime;// 更新时间

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Integer isDefault) {
		this.isDefault = isDefault;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getDigest() {
		return digest;
	}

	public void setDigest(String digest) {
		this.digest = digest;
	}

	public boolean isHolidayIsopen() {
		return holidayIsopen;
	}

	public void setHolidayIsopen(boolean holidayIsopen) {
		this.holidayIsopen = holidayIsopen;
	}

	public boolean isFieldIsClock() {
		return fieldIsClock;
	}

	public void setFieldIsClock(boolean fieldIsClock) {
		this.fieldIsClock = fieldIsClock;
	}

	public boolean isFieldIsRemark() {
		return fieldIsRemark;
	}

	public void setFieldIsRemark(boolean fieldIsRemark) {
		this.fieldIsRemark = fieldIsRemark;
	}

	public boolean isFieldIsPhoto() {
		return fieldIsPhoto;
	}

	public void setFieldIsPhoto(boolean fieldIsPhoto) {
		this.fieldIsPhoto = fieldIsPhoto;
	}

	public String getPersonality() {
		return personality;
	}

	public void setPersonality(String personality) {
		this.personality = personality;
	}

	public String getAbsenteeism() {
		return absenteeism;
	}

	public void setAbsenteeism(String absenteeism) {
		this.absenteeism = absenteeism;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
