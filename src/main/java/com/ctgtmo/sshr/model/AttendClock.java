package com.ctgtmo.sshr.model;

import java.util.UUID;

/**  
 * @Title: AttendClock.java   
 * @Company: 北京易才博普奥管理顾问有限公司
 * @Package: com.ctgtmo.sshr.model   
 * @Description:考勤打卡实体
 * @author: 王共亮     
 * @date: 2020年6月3日 上午9:46:07   
 */
public class AttendClock {
  //打卡考勤id
  private String id;

  //考勤组类型 1：固定 2：排班 3：自由
  private int groupType;

  //考勤组id
  private int groupId;

  //员工id
  private String employId;

  //公司id
  private int companyId;

  //上班日期,年月日
  private String workDate;

  //班次id
  private int shiftId;

  //班次名称
  private String shiftName;

  //班次颜色
  private String color;

  //班次的休息时间（一天一次上下班时有值）
  private String restTime;

  //工作站id
  private int workstationId;

  //上班时间
  private String workOn;

  //上班状态
  private int workOnState;

  //下班时间
  private String workOff;

  //下班状态
  private int workOffState;

  //'是否休息日 1上班2休息3节假日'
  private int isRestDay = 1;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public int getGroupType() {
    return groupType;
  }

  public void setGroupType(int groupType) {
    this.groupType = groupType;
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

  public int getCompanyId() {
    return companyId;
  }

  public void setCompanyId(int companyId) {
    this.companyId = companyId;
  }

  public String getWorkDate() {
    return workDate;
  }

  public void setWorkDate(String workDate) {
    this.workDate = workDate;
  }

  public int getShiftId() {
    return shiftId;
  }

  public void setShiftId(int shiftId) {
    this.shiftId = shiftId;
  }

  public String getShiftName() {
    return shiftName;
  }

  public void setShiftName(String shiftName) {
    this.shiftName = shiftName;
  }

  public String getColor() {
    return color;
  }

  public void setColor(String color) {
    this.color = color;
  }

  public String getRestTime() {
    return restTime;
  }

  public void setRestTime(String restTime) {
    this.restTime = restTime;
  }

  public String getWorkOn() {
    return workOn;
  }

  public int getWorkstationId() {
    return workstationId;
  }

  public void setWorkstationId(int workstationId) {
    this.workstationId = workstationId;
  }

  public void setWorkOn(String workOn) {
    this.workOn = workOn;
  }

  public int getWorkOnState() {
    return workOnState;
  }

  public void setWorkOnState(int workOnState) {
    this.workOnState = workOnState;
  }

  public String getWorkOff() {
    return workOff;
  }

  public void setWorkOff(String workOff) {
    this.workOff = workOff;
  }

  public int getWorkOffState() {
    return workOffState;
  }

  public void setWorkOffState(int workOffState) {
    this.workOffState = workOffState;
  }

  public int getIsRestDay() {
    return isRestDay;
  }

  public void setIsRestDay(int isRestDay) {
    this.isRestDay = isRestDay;
  }

  public AttendClock(String id, int groupType, int groupId, String employId, int companyId, String workDate, int shiftId, String shiftName, String color,
  String restTime, int workstationId, String workOn, int workOnState, String workOff, int workOffState, int isRestDay) {
    this.id = UUID.randomUUID().toString().replace("-", "");
    this.groupType = groupType;
    this.groupId = groupId;
    this.employId = employId;
    this.companyId = companyId;
    this.workDate = workDate;
    this.shiftId = shiftId;
    this.shiftName = shiftName;
    this.color = color;
    this.restTime = restTime;
    this.workstationId = workstationId;
    this.workOn = workOn;
    this.workOnState = workOnState;
    this.workOff = workOff;
    this.workOffState = workOffState;
    this.isRestDay = isRestDay;
  }

  public AttendClock() {
    super();
  }

}
