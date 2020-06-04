package com.ctgtmo.sshr.model;

/**
 * @Title: AttendGroupWorkday.java   
 * @Company: 北京易才博普奥管理顾问有限公司
 * @Package: com.ctgtmo.sshr.model   
 * @Description:工作日实体类
 * @author: 王共亮     
 * @date: 2020年6月3日 上午11:36:14
 */
public class AttendGroupWorkday {

  //考勤组id
  private int groupId;

  //班次id
  private int shiftsId;

  //星期
  private String weekday;

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

  public String getWeekday() {
    return weekday;
  }

  public void setWeekday(String weekday) {
    this.weekday = weekday;
  }

  public int getIsWorkday() {
    return isWorkday;
  }

  public void setIsWorkday(int isWorkday) {
    this.isWorkday = isWorkday;
  }

}
