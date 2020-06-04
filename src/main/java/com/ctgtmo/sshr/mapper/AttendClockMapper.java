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
  public static final String QUERY_COMPANY_GROUP = "SELECT id,company_id,is_default,type,holiday_isopen FROM attend_group WHERE company_id=:companyId";

  //@Fields QUERY_GROUP_ORG:查询考勤组相关联的组织/人员信息
  public static final String QUERY_GROUP_ORG = "SELECT group_id,type,relation_id,org_id FROM attend_group_org WHERE group_id IN(:groupIds)";

  //@Fields QUERY_GROUP_WORKDAY:根据考勤组id和周几来查询固定和自由班次信息
  public static final String QUERY_GROUP_WORKDAY = "SELECT shifts_id,group_id,weekday,is_workday FROM attend_group_workday WHERE group_id IN(:groupIds) AND weekday=:workDay";

  //@Fields QUERY_GROUP_SPECIAL:根据考勤组id和当天日期查询特殊日期排班
  public static final String QUERY_GROUP_SPECIAL = "SELECT shifts_id,group_id,weekday,is_workday FROM attend_group_workday WHERE group_id IN(:groupIds) AND weekday=:workDay";

  //@Fields QUERY_HOLIDAY:查询假期数据
  public static final String QUERY_HOLIDAY = "SELECT type,holiday FROM holiday WHERE holiday=:workDay AND type=1";

  //@Fields ATTEND_SHIFT:根据班次主键查询信息
  public static final String ATTEND_SHIFT = "SELECT s.id,s.company_id,s.`name`,s.rest_time,s.card_interval,t.work_on,t.work_on_begin,t.work_on_end,t.work_out,t.work_out_begin,"
  + "t.work_out_end FROM attend_shifts s INNER JOIN attend_shifts_time t ON s.id=t.shifts_id WHERE s.id IN(:shiftIds) ORDER BY t.work_on";

  //@Fields QUERY_ATTEND_SCHEDUL:根据考勤组和日期查询排班详情
  public static final String QUERY_ATTEND_SCHEDUL = "SELECT group_id,employ_id,attend_time,company_id FROM attend_schedul WHERE group_id IN(:groupIds) AND attend_day=:workDay";
}
