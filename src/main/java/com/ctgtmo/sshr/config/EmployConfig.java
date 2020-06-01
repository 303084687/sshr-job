package com.ctgtmo.sshr.config;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.alibaba.druid.pool.DruidDataSource;

/**
 * @Title: AttendConfig.java   
 * @Company: 北京易才博普奥管理顾问有限公司
 * @Package: com.ctgtmo.sshr.config   
 * @Description:员工数据库配置
 * @author: 王共亮     
 * @date: 2020年6月1日 下午4:40:26
 */
@Configuration
@SuppressWarnings("all")
public class EmployConfig {
  private final Logger logger = LoggerFactory.getLogger(EmployConfig.class);

  @Value("${spring.employSource.url}")
  private String dbUrl;

  @Value("${spring.employSource.username}")
  private String username;

  @Value("${spring.employSource.password}")
  private String password;

  @Value("${spring.employSource.driverClass}")
  private String driverClassName;

  @Value("${spring.datasource.initialSize}")
  private int initialSize;

  @Value("${spring.datasource.minIdle}")
  private int minIdle;

  @Value("${spring.datasource.maxActive}")
  private int maxActive;

  @Value("${spring.datasource.maxWait}")
  private int maxWait;

  @Value("${spring.datasource.timeBetweenEvictionRunsMillis}")
  private int timeBetweenEvictionRunsMillis;

  @Value("${spring.datasource.minEvictableIdleTimeMillis}")
  private int minEvictableIdleTimeMillis;

  @Value("${spring.datasource.validationQuery}")
  private String validationQuery;

  @Value("${spring.datasource.testWhileIdle}")
  private boolean testWhileIdle;

  @Value("${spring.datasource.testOnBorrow}")
  private boolean testOnBorrow;

  @Value("${spring.datasource.testOnReturn}")
  private boolean testOnReturn;

  @Value("${spring.datasource.poolPreparedStatements}")
  private boolean poolPreparedStatements;

  @Value("${spring.datasource.filters}")
  private String filters;

  @Bean(name = "employSource")
  public DataSource dataSource() {
    DruidDataSource datasource = new DruidDataSource();
    datasource.setUrl(dbUrl);
    datasource.setUsername(username);
    datasource.setPassword(password);
    datasource.setDriverClassName(driverClassName);
    datasource.setInitialSize(initialSize);
    datasource.setMinIdle(minIdle);
    datasource.setMaxActive(maxActive);
    datasource.setMaxWait(maxWait);
    datasource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
    datasource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
    datasource.setValidationQuery(validationQuery);
    datasource.setTestWhileIdle(testWhileIdle);
    datasource.setTestOnBorrow(testOnBorrow);
    datasource.setTestOnReturn(testOnReturn);
    datasource.setPoolPreparedStatements(poolPreparedStatements);
    try {
      datasource.setFilters(filters);
    } catch (SQLException e) {
      logger.error("druid configuration initialization filter", e);
    }
    return datasource;
  }

  @Bean(name = "employTransactionManager")
  public DataSourceTransactionManager employTransactionManager(@Qualifier("employSource") DataSource dataSource) {
    return new DataSourceTransactionManager(dataSource);
  }

}
