package com.ctgtmo.sshr.model;

/**
 * <p>Company: 乾通互联(北京)科技有限公司</p>
 * <p>Description: 考勤组实体类</p>
 * <p>author feikanghui</p>
 * <p>2019年07月01日 13:35:36</p>
 */
public class AttendGroup {

  private int id;// 考勤组id

  private int companyId;// 公司id

  private int isDefault;// 是否默认(0:否 1:是)

  private int type;// 类型：1：固定 2：排班 3：自由

  private boolean holidayIsopen;// 法定节假日自动排休状态 0：关闭 1：开启

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getCompanyId() {
    return companyId;
  }

  public void setCompanyId(int companyId) {
    this.companyId = companyId;
  }

  public int getIsDefault() {
    return isDefault;
  }

  public void setIsDefault(int isDefault) {
    this.isDefault = isDefault;
  }

  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }

  public boolean isHolidayIsopen() {
    return holidayIsopen;
  }

  public void setHolidayIsopen(boolean holidayIsopen) {
    this.holidayIsopen = holidayIsopen;
  }

}
