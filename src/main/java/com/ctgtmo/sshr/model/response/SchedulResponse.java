package com.ctgtmo.sshr.model.response;

import java.util.List;

/**  
 * @Title: SchedulResponse.java   
 * @Company: 北京易才博普奥管理顾问有限公司
 * @Package: com.ctgtmo.sshr.model.response   
 * @Description:排班中详细排班数据
 * @author: 王共亮     
 * @date: 2020年6月3日 下午5:02:29   
 */
public class SchedulResponse {
  private int id;

  private int isShifts;

  private String beginTime;

  private String endTime;

  private String sleepBegin;

  private String sleepEnd;

  private String shiftsName;

  private String workstationName;

  private int workstationId;

  private String color;

  private String workOnScope;

  private String workOutScope;

  private List<SchedulShiftTime> shiftsTimes;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getIsShifts() {
    return isShifts;
  }

  public void setIsShifts(int isShifts) {
    this.isShifts = isShifts;
  }

  public int getWorkstationId() {
    return workstationId;
  }

  public void setWorkstationId(int workstationId) {
    this.workstationId = workstationId;
  }

  public String getBeginTime() {
    return beginTime;
  }

  public void setBeginTime(String beginTime) {
    this.beginTime = beginTime;
  }

  public String getEndTime() {
    return endTime;
  }

  public void setEndTime(String endTime) {
    this.endTime = endTime;
  }

  public String getSleepBegin() {
    return sleepBegin;
  }

  public void setSleepBegin(String sleepBegin) {
    this.sleepBegin = sleepBegin;
  }

  public String getSleepEnd() {
    return sleepEnd;
  }

  public void setSleepEnd(String sleepEnd) {
    this.sleepEnd = sleepEnd;
  }

  public String getShiftsName() {
    return shiftsName;
  }

  public void setShiftsName(String shiftsName) {
    this.shiftsName = shiftsName;
  }

  public String getWorkstationName() {
    return workstationName;
  }

  public void setWorkstationName(String workstationName) {
    this.workstationName = workstationName;
  }

  public String getColor() {
    return color;
  }

  public void setColor(String color) {
    this.color = color;
  }

  public List<SchedulShiftTime> getShiftsTimes() {
    return shiftsTimes;
  }

  public void setShiftsTimes(List<SchedulShiftTime> shiftsTimes) {
    this.shiftsTimes = shiftsTimes;
  }

  public String getWorkOnScope() {
    return workOnScope;
  }

  public void setWorkOnScope(String workOnScope) {
    this.workOnScope = workOnScope;
  }

  public String getWorkOutScope() {
    return workOutScope;
  }

  public void setWorkOutScope(String workOutScope) {
    this.workOutScope = workOutScope;
  }

}
