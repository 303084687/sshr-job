package com.ctgtmo.sshr.repository.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ctgtmo.sshr.config.AttendSqlDao;
import com.ctgtmo.sshr.mapper.AttendClockMapper;
import com.ctgtmo.sshr.model.AttendGroup;
import com.ctgtmo.sshr.model.AttendGroupSpecial;
import com.ctgtmo.sshr.model.AttendGroupWorkday;
import com.ctgtmo.sshr.model.AttendSchedul;
import com.ctgtmo.sshr.model.AttendShift;
import com.ctgtmo.sshr.model.Holiday;
import com.ctgtmo.sshr.model.response.AttendOrgResponse;
import com.ctgtmo.sshr.repository.AttendRecordDao;

/**  
 * @Title: AttendRecordDaoImpl.java   
 * @Company: 北京易才博普奥管理顾问有限公司
 * @Package: com.ctgtmo.sshr.repository.impl   
 * @Description:增加打卡记录实现
 * @author: 王共亮     
 * @date: 2020年6月2日 上午10:56:52   
 */
@Component
public class AttendRecordDaoImpl implements AttendRecordDao {
  @Autowired
  private AttendSqlDao attendSqlDao;

  /** 
  * @Title: queryDefaultCompanyList 
  * @Description: 查询默认考勤组确定公司数量
  * @author 王共亮
  * @date 2020年6月2日上午10:56:52
  */
  @Override
  public List<Map<String, Object>> queryDefaultCompanyList() {
    return attendSqlDao.getNamedParamterDao().queryForList(AttendClockMapper.QUERY_COMPANY_DEFAULT_GROUP, new MapSqlParameterSource());
  }

  /** 
  * @Title: queryCompanyGroupList 
  * @Description: 根据公司主键查询考勤组
  * @param companyId公司主键
  * @author 王共亮
  * @date 2020年6月2日上午10:56:52
  */
  @Override
  public List<AttendGroup> queryCompanyGroupList(int companyId) {
    //根据公司主键查询考勤组的数量
    MapSqlParameterSource paramSource = new MapSqlParameterSource();
    paramSource.addValue("companyId", companyId);
    return attendSqlDao.getNamedParamterDao().query(AttendClockMapper.QUERY_COMPANY_GROUP, paramSource, new BeanPropertyRowMapper<>(AttendGroup.class));
  }

  /** 
  * @Title: queryGroupOrgList 
  * @Description: 根据考勤组主键查询对应组织/人员信息
  * @param groupIdList考勤组list
  * @author 王共亮
  * @date 2020年6月2日上午10:56:52
  */
  @Override
  @Transactional
  public List<AttendOrgResponse> queryGroupOrgList(List<Integer> groupIdList) {
    MapSqlParameterSource paramSource = new MapSqlParameterSource();
    paramSource.addValue("groupIds", groupIdList);
    return attendSqlDao.getNamedParamterDao().query(AttendClockMapper.QUERY_GROUP_ORG, paramSource, new BeanPropertyRowMapper<>(AttendOrgResponse.class));
  }

  /** 
   * @Title: queryGroupWorkList 
   * @Description: 根据日期和考勤组查询工作时间(固定/自由类型)
   * @param workDay日期
   * @param groupIdList考勤组ids
   * @return List<AttendGroupWorkday> 
   * @author 王共亮
   * @date 2020年6月3日上午11:39:48
   */
  @Override
  @Transactional
  public List<AttendGroupWorkday> queryGroupWorkList(String workDay, List<Integer> groupIdList) {
    MapSqlParameterSource paramSource = new MapSqlParameterSource();
    paramSource.addValue("workDay", workDay);
    paramSource.addValue("groupIds", groupIdList);
    return attendSqlDao.getNamedParamterDao().query(AttendClockMapper.QUERY_GROUP_WORKDAY, paramSource, new BeanPropertyRowMapper<>(AttendGroupWorkday.class));
  }

  /** 
   * @Title: queryGroupSpecialList 
   * @Description: 根据日期和考勤组查询特殊工作时间(固定/自由类型)
   * @param workDay日期
   * @param groupIdList考勤组ids
   * @return List<AttendGroupSpecial> 
   * @author 王共亮
   * @date 2020年6月3日下午1:44:23
   */
  @Override
  @Transactional
  public List<AttendGroupSpecial> queryGroupSpecialList(String workDay, List<Integer> groupIdList) {
    MapSqlParameterSource paramSource = new MapSqlParameterSource();
    paramSource.addValue("workDay", workDay);
    paramSource.addValue("groupIds", groupIdList);
    return attendSqlDao.getNamedParamterDao().query(AttendClockMapper.QUERY_GROUP_SPECIAL, paramSource, new BeanPropertyRowMapper<>(AttendGroupSpecial.class));
  }

  /** 
   * @Title: queryHolidayList 
   * @Description:根据日期查询是否节假日(固定/自由类型)
   * @param workDay 日期
   * @return List<Holiday> 
   * @author 王共亮
   * @date 2020年6月3日下午1:50:29
   */
  @Override
  @Transactional
  public List<Holiday> queryHolidayList(String workDay) {
    MapSqlParameterSource paramSource = new MapSqlParameterSource();
    paramSource.addValue("workDay", workDay);
    return attendSqlDao.getNamedParamterDao().query(AttendClockMapper.QUERY_HOLIDAY, paramSource, new BeanPropertyRowMapper<>(Holiday.class));
  }

  /** 
   * @Title: querySchedulList 
   * @Description: 根据日期和考勤组ids查询排班详情
   * @param workDay日期
   * @param groupIdList考勤组ids
   * @return List<AttendSchedul> 
   * @author 王共亮
   * @date 2020年6月3日下午1:55:30
   */
  @Override
  @Transactional
  public List<AttendSchedul> querySchedulList(String workDay, List<Integer> groupIdList) {
    MapSqlParameterSource paramSource = new MapSqlParameterSource();
    paramSource.addValue("workDay", workDay);
    paramSource.addValue("groupIds", groupIdList);
    return attendSqlDao.getNamedParamterDao().query(AttendClockMapper.QUERY_ATTEND_SCHEDUL, paramSource, new BeanPropertyRowMapper<>(AttendSchedul.class));
  }

  /** 
   * @Title: queryShiftList 
   * @Description: 根据班次主键查询
   * @param shiftList单个/多个班次id
   * @return List<AttendShift> 
   * @author 王共亮
   * @date 2020年6月3日下午2:02:48
   */
  @Override
  @Transactional
  public List<AttendShift> queryShiftList(List<Integer> shiftList) {
    MapSqlParameterSource paramSource = new MapSqlParameterSource();
    paramSource.addValue("shiftIds", shiftList);
    return attendSqlDao.getNamedParamterDao().query(AttendClockMapper.ATTEND_SHIFT, paramSource, new BeanPropertyRowMapper<>(AttendShift.class));
  }

}
