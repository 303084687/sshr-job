package com.ctgtmo.sshr.repository;

import java.util.List;
import java.util.Map;

import com.ctgtmo.sshr.model.AttendClock;
import com.ctgtmo.sshr.model.AttendGroup;
import com.ctgtmo.sshr.model.AttendGroupSpecial;
import com.ctgtmo.sshr.model.AttendGroupWorkday;
import com.ctgtmo.sshr.model.AttendSchedul;
import com.ctgtmo.sshr.model.AttendShift;
import com.ctgtmo.sshr.model.Holiday;
import com.ctgtmo.sshr.model.response.AttendOrgResponse;

/**  
 * @Title: AttendRecordDao.java   
 * @Company: 北京易才博普奥管理顾问有限公司
 * @Package: com.ctgtmo.sshr.repository   
 * @Description:考勤打卡记录
 * @author: 王共亮     
 * @date: 2020年6月2日 上午9:29:19   
 */
public interface AttendRecordDao {

  /** 
  * @Title: queryDefaultCompanyList 
  * @Description: 查询默认考勤组公司数量
  * @return List<Map<String,Object>> 
  * @author 王共亮
  * @date 2020年6月2日上午9:33:28
  */
  List<Map<String, Object>> queryDefaultCompanyList();

  /** 
  * @Title: queryCompanyGroupList 
  * @Description:根据公司查询考勤组
  * @param companyId公司主键
  * @return List<AttendGroup> 
  * @author 王共亮
  * @date 2020年6月2日上午9:38:05
  */
  List<AttendGroup> queryCompanyGroupList(int companyId);

  /** 
  * @Title: queryGroupOrgList 
  * @Description: 根据考勤组查询对应的部门/人员信息
  * @param groupId考勤组主键
  * @return List<Map<String,Object>> 
  * @author 王共亮
  * @date 2020年6月2日上午9:44:50
  */
  List<AttendOrgResponse> queryGroupOrgList(List<Integer> groupIdList);

  /** 
  * @Title: queryGroupWorkList 
  * @Description: 根据日期和考勤组查询工作时间(固定/自由类型)
  * @param workDay日期
  * @param groupIdList考勤组ids
  * @return List<AttendGroupWorkday> 
  * @author 王共亮
  * @date 2020年6月3日上午11:39:48
  */
  List<AttendGroupWorkday> queryGroupWorkList(String workDay, List<Integer> groupIdList);

  /** 
  * @Title: queryGroupSpecialList 
  * @Description: 根据日期和考勤组查询特殊工作时间(固定/自由类型)
  * @param workDay日期
  * @param groupIdList考勤组ids
  * @return List<AttendGroupSpecial> 
  * @author 王共亮
  * @date 2020年6月3日下午1:44:23
  */
  List<AttendGroupSpecial> queryGroupSpecialList(String workDay, List<Integer> groupIdList);

  /** 
  * @Title: queryHolidayList 
  * @Description:根据日期查询是否节假日(固定/自由类型)
  * @param workDay 日期
  * @return List<Holiday> 
  * @author 王共亮
  * @date 2020年6月3日下午1:50:29
  */
  List<Holiday> queryHolidayList(String workDay);

  /** 
  * @Title: querySchedulList 
  * @Description: 根据日期和考勤组ids查询排班详情
  * @param workDay日期
  * @param groupIdList考勤组ids
  * @return List<AttendSchedul> 
  * @author 王共亮
  * @date 2020年6月3日下午1:55:30
  */
  List<AttendSchedul> querySchedulList(String workDay, List<Integer> groupIdList);

  /** 
  * @Title: queryShiftList 
  * @Description: 根据班次主键查询
  * @param shiftList单个/多个班次id
  * @return List<AttendShift> 
  * @author 王共亮
  * @date 2020年6月3日下午2:02:48
  */
  List<AttendShift> queryShiftList(List<Integer> shiftList);

  /** 
  * @Title: batchAddEmployClock 
  * @Description: 
  * @param employList void  
  * @author 王共亮
  * @date 2020年6月5日上午9:40:44
  */
  void batchAddEmployClock(List<AttendClock> employList);
}
