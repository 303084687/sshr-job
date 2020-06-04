package com.ctgtmo.sshr.model;

/**
 * @ClassName: Holiday.java
 * @Company: 北京易才博普奥管理咨询有限公司
 * @Description:假期表
 * @author wanggongliang
 * @date 2019年7月16日 下午1:58:07
 */
public class Holiday {

  private int type;//1法定节假日2节假日调休

  private String holiday;//假期

  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }

  public String getHoliday() {
    return holiday;
  }

  public void setHoliday(String holiday) {
    this.holiday = holiday;
  }

}
