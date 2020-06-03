package com.ctgtmo.sshr.repository;

import java.util.List;
import java.util.Map;

import com.ctgtmo.sshr.model.AttendGroup;
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
}
