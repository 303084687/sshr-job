package com.ctgtmo.sshr.mapper;

/**  
 * @Title: AttendClockMapper.java   
 * @Company: 北京易才博普奥管理顾问有限公司
 * @Package: com.ctgtmo.sshr.mapper   
 * @Description:生成考勤打卡记录
 * @author: 王共亮     
 * @date: 2020年6月2日 上午10:47:50   
 */
public class AttendClockMapper {

  //@Fields QUERY_COMPANY_DEFAULT_GROUP:查询默认的考勤组
  public static final String QUERY_COMPANY_DEFAULT_GROUP = "SELECT id,company_id FROM attend_group WHERE is_default=1";

  //@Fields QUERY_COMPANY_GROUP:查询公司所有的考勤组
  public static final String QUERY_COMPANY_GROUP = "SELECT * FROM attend_group WHERE company_id=:companyId";

  //@Fields QUERY_GROUP_ORG:查询考勤组相关联的组织/人员信息
  public static final String QUERY_GROUP_ORG = "SELECT group_id,type,relation_id,org_id FROM attend_group_org WHERE group_id IN(:groupIds)";
}
