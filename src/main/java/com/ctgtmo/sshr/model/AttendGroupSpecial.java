package com.ctgtmo.sshr.model;

/**
 * @Title: AttendGroupSpecial.java   
 * @Company: 北京易才博普奥管理顾问有限公司
 * @Package: com.ctgtmo.sshr.model   
 * @Description:特殊日期实体
 * @author: 王共亮     
 * @date: 2020年6月3日 下午1:41:55
 */
public class AttendGroupSpecial {
  //考勤组id
  private int groupId;

  //班次id
  private int shiftsId;

  //特殊日期-年月日
  private String specialDay;

  //是否上班 1：上班 2：休息（固定和自由考勤组用）
  private int isWorkday;

  public int getGroupId() {
    return groupId;
  }

  public void setGroupId(int groupId) {
    this.groupId = groupId;
  }

  public int getShiftsId() {
    return shiftsId;
  }

  public void setShiftsId(int shiftsId) {
    this.shiftsId = shiftsId;
  }

  public String getSpecialDay() {
    return specialDay;
  }

  public void setSpecialDay(String specialDay) {
    this.specialDay = specialDay;
  }

  public int getIsWorkday() {
    return isWorkday;
  }

  public void setIsWorkday(int isWorkday) {
    this.isWorkday = isWorkday;
  }

}
