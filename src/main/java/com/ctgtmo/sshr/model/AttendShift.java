package com.ctgtmo.sshr.model;

/**
 * @ClassName: AttendShift.java
 * @Company: 北京易才博普奥管理咨询有限公司
 * @Description:班次返回实体
 * @author 王共亮
 * @date 2019年7月18日 上午11:00:01
 */
public class AttendShift {
  //班次id
  private int id;

  //公司id
  private int companyId;

  //班次名称
  private String name;

  //打卡时段设置 1.启用，2.禁用
  private int cardInterval;

  //休息时间（有值显示：12：00-13：00；为空则没有休息时间）
  private String restTime;

  //上班时间
  private String workOn;

  //上班打卡开始时间
  private String workOnBegin;

  //上班打卡结束时间
  private String workOnEnd;

  //下班时间
  private String workOut;

  //下班打卡开始时间
  private String workOutBegin;

  //下班打卡结束时间
  private String workOutEnd;

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

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getCardInterval() {
    return cardInterval;
  }

  public void setCardInterval(int cardInterval) {
    this.cardInterval = cardInterval;
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

  public void setWorkOn(String workOn) {
    this.workOn = workOn;
  }

  public String getWorkOnBegin() {
    return workOnBegin;
  }

  public void setWorkOnBegin(String workOnBegin) {
    this.workOnBegin = workOnBegin;
  }

  public String getWorkOnEnd() {
    return workOnEnd;
  }

  public void setWorkOnEnd(String workOnEnd) {
    this.workOnEnd = workOnEnd;
  }

  public String getWorkOut() {
    return workOut;
  }

  public void setWorkOut(String workOut) {
    this.workOut = workOut;
  }

  public String getWorkOutBegin() {
    return workOutBegin;
  }

  public void setWorkOutBegin(String workOutBegin) {
    this.workOutBegin = workOutBegin;
  }

  public String getWorkOutEnd() {
    return workOutEnd;
  }

  public void setWorkOutEnd(String workOutEnd) {
    this.workOutEnd = workOutEnd;
  }

}
