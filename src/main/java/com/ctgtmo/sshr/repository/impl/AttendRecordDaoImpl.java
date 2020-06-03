package com.ctgtmo.sshr.repository.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Component;

import com.ctgtmo.sshr.config.AttendSqlDao;
import com.ctgtmo.sshr.mapper.AttendClockMapper;
import com.ctgtmo.sshr.model.AttendGroup;
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
  public List<AttendGroup> queryCompanyGroupList(int companyId) {
    //根据公司主键查询考勤组的数量
    MapSqlParameterSource paramSource = new MapSqlParameterSource();
    paramSource.addValue("companyId", companyId);
    return attendSqlDao.getNamedParamterDao().query(AttendClockMapper.QUERY_COMPANY_GROUP, paramSource, new BeanPropertyRowMapper<AttendGroup>(
    AttendGroup.class));
  }

  /** 
  * @Title: queryGroupOrgList 
  * @Description: 根据考勤组主键查询对应组织/人员信息
  * @param groupIdList考勤组list
  * @author 王共亮
  * @date 2020年6月2日上午10:56:52
  */
  public List<AttendOrgResponse> queryGroupOrgList(List<Integer> groupIdList) {
    MapSqlParameterSource paramSource = new MapSqlParameterSource();
    paramSource.addValue("groupIds", groupIdList);
    return attendSqlDao.getNamedParamterDao().query(AttendClockMapper.QUERY_GROUP_ORG, paramSource, new BeanPropertyRowMapper<AttendOrgResponse>(
    AttendOrgResponse.class));
  }

}
