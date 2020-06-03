package com.ctgtmo.sshr.model.response;

/**
 * @Title: AttendOrgResponse.java   
 * @Company: 北京易才博普奥管理顾问有限公司
 * @Package: com.ctgtmo.sshr.model.response   
 * @Description:考勤组关联的组织/人员信息
 * @author: 王共亮     
 * @date: 2020年6月2日 下午3:42:08
 */
public class AttendOrgResponse {
  //考勤组id
  private int groupId;

  //类型：1：部门 2：无需考勤人员 3：参与考勤人员
  private int type;

  //关联id（部门id/人员id）
  private String relationId;

  //直属部门id
  private String orgId;

  public int getGroupId() {
    return groupId;
  }

  public void setGroupId(int groupId) {
    this.groupId = groupId;
  }

  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }

  public String getRelationId() {
    return relationId;
  }

  public void setRelationId(String relationId) {
    this.relationId = relationId;
  }

  public String getOrgId() {
    return orgId;
  }

  public void setOrgId(String orgId) {
    this.orgId = orgId;
  }

}
