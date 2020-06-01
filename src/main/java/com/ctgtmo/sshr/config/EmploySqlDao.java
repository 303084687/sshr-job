package com.ctgtmo.sshr.config;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

/**  
 * @Title: AttendSqlDao.java   
 * @Company: 北京易才博普奥管理顾问有限公司
 * @Package: com.ctgtmo.sshr.config   
 * @Description:员工sqlDao
 * @author: 王共亮     
 * @date: 2020年6月1日 下午4:49:42   
 */
@Service
public class EmploySqlDao extends JdbcTemplate {

  @Override
  @Resource(name = "employSource")
  public void setDataSource(DataSource dataSource) {
    super.setDataSource(dataSource);
  }

  @Override
  public Map<String, Object> queryForMap(String sql, Object... args) throws DataAccessException {
    return queryForObject(sql, args, getColumnMapRowMapper());
  }

  @Override
  public <T> T queryForObject(String sql, Object[] args, Class<T> requiredType) throws DataAccessException {
    return queryForObject(sql, args, getSingleColumnRowMapper(requiredType));
  }

  @Override
  public <T> T queryForObject(String sql, Object[] args, RowMapper<T> rowMapper) throws DataAccessException {
    List<T> results = query(sql, args, new RowMapperResultSetExtractor<T>(rowMapper, 1));

    return requiredSingleResult(results);
  }

  private static <T> T requiredSingleResult(Collection<T> results) throws IncorrectResultSizeDataAccessException {
    int size = (results != null ? results.size() : 0);
    if (size == 0) {
      return null;
    }
    if (results.size() > 1) {
      throw new IncorrectResultSizeDataAccessException(1, size);
    }
    return results.iterator().next();
  }

  public NamedParameterJdbcTemplate getNamedParamterDao() {
    NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(super.getDataSource());
    return jdbcTemplate;
  }
}