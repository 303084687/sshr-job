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
  public static final String QUERY_GROUP_SPECIAL = "SELECT shifts_id,group_id,special_day,is_workday FROM attend_group_special WHERE group_id IN(:groupIds) AND special_day=:workDay";

  //@Fields QUERY_HOLIDAY:查询假期数据
  public static final String QUERY_HOLIDAY = "SELECT type,holiday FROM holiday WHERE holiday=:workDay AND type=1";

  //@Fields ATTEND_SHIFT:根据班次主键查询信息
  public static final String ATTEND_SHIFT = "SELECT s.id,s.company_id,s.`name`,s.rest_time,s.card_interval,t.work_on,t.work_on_begin,t.work_on_end,t.work_out,t.work_out_begin,"
  + "t.work_out_end FROM attend_shifts s INNER JOIN attend_shifts_time t ON s.id=t.shifts_id WHERE s.id IN(:shiftIds) ORDER BY t.work_on";

  //@Fields QUERY_ATTEND_SCHEDUL:根据考勤组和日期查询排班详情
  public static final String QUERY_ATTEND_SCHEDUL = "SELECT group_id,employ_id,attend_time,company_id FROM attend_schedul WHERE group_id IN(:groupIds) AND attend_day=:workDay";

  //@Fields ADD_EMPLOY_CLOCK:批量插入员工打卡数据
  public static final String ADD_EMPLOY_CLOCK = "INSERT INTO attend_clock_copy (id,group_type,group_id,employ_id,company_id,work_date,shift_id,shift_name,color,rest_time,workstation_id,"
  + "work_on,work_off,work_on_state,work_off_state,is_rest_day,is_default,create_time,work_on_late,work_off_early,work_on_is_field,work_off_is_field,work_on_fix_state,work_off_fix_state)VALUES"
  + "(:id,:groupType,:groupId,:employId,:companyId,:workDate,:shiftId,:shiftName,:color,:restTime,:workstationId,:workOn,:workOff,:workOnState,:workOffState,:isRestDay,1,now(),'0','0',1,1,0,0)";
}
