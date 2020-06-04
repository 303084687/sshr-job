package com.ctgtmo.sshr.model;

/**
 * @Title: AttendSchedul.java   
 * @Company: 北京易才博普奥管理顾问有限公司
 * @Package: com.ctgtmo.sshr.model   
 * @Description:排班详情
 * @author: 王共亮     
 * @date: 2020年6月3日 下午1:54:01
 */
public class AttendSchedul {

  //排班详情id
  private int id;

  //考勤组id
  private int groupId;

  //员工id
  private String employId;

  //排班时间（年月日）
  private String attendDay;

  //排班时间（"8：00-12：00":"工作站id"，……）
  private String attendTime;

  private String beginTime;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getGroupId() {
    return groupId;
  }

  public void setGroupId(int groupId) {
    this.groupId = groupId;
  }

  public String getEmployId() {
    return employId;
  }

  public void setEmployId(String employId) {
    this.employId = employId;
  }

  public String getAttendDay() {
    return attendDay;
  }

  public void setAttendDay(String attendDay) {
    this.attendDay = attendDay;
  }

  public String getAttendTime() {
    return attendTime;
  }

  public void setAttendTime(String attendTime) {
    this.attendTime = attendTime;
  }

  public String getBeginTime() {
    return beginTime;
  }

  public void setBeginTime(String beginTime) {
    this.beginTime = beginTime;
  }

}
