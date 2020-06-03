package com.ctgtmo.sshr.repository.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Component;

import com.ctgtmo.sshr.config.EmploySqlDao;
import com.ctgtmo.sshr.mapper.EmployMapper;
import com.ctgtmo.sshr.model.response.EmployResponse;
import com.ctgtmo.sshr.repository.EmployDao;

/**  
 * @Title: EmployDaoImpl.java   
 * @Company: 北京易才博普奥管理顾问有限公司
 * @Package: com.ctgtmo.sshr.repository.impl   
 * @Description:员工服务实现
 * @author: 王共亮     
 * @date: 2020年6月2日 上午10:03:08   
 */
@Component
public class EmployDaoImpl implements EmployDao {

  @Autowired
  private EmploySqlDao employSqlDao;

  /** 
  * @Title: queryEmployList 
  * @Description: 根据公司主键查询员工信息
  * @param companyId公司主键
  * @author 王共亮
  * @date 2020年6月2日上午10:04:45
  */
  public List<EmployResponse> queryEmployList(int companyId) {
    MapSqlParameterSource paramSource = new MapSqlParameterSource();
    paramSource.addValue("companyId", companyId);
    return employSqlDao.getNamedParamterDao().query(EmployMapper.QUERY_EMPLOY_COMPANY, paramSource, new BeanPropertyRowMapper<EmployResponse>(
    EmployResponse.class));
  }
}
