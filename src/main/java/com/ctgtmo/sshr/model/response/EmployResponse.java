package com.ctgtmo.sshr.model.response;

/**  
 * @Title: EmployResponse.java   
 * @Company: 北京易才博普奥管理顾问有限公司
 * @Package: com.ctgtmo.sshr.model.response   
 * @Description:用于根据公司主键查询员工信息
 * @author: 王共亮     
 * @date: 2020年6月2日 上午10:21:13   
 */
public class EmployResponse {
  //员工主键
  private String employId;

  //部门主键
  private String orgId;

  //考勤组主键
  private int groupId;

  public String getEmployId() {
    return employId;
  }

  public void setEmployId(String employId) {
    this.employId = employId;
  }

  public String getOrgId() {
    return orgId;
  }

  public void setOrgId(String orgId) {
    this.orgId = orgId;
  }

  public int getGroupId() {
    return groupId;
  }

  public void setGroupId(int groupId) {
    this.groupId = groupId;
  }

}
