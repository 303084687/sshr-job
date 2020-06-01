package com.ctgtmo.sshr.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ctgtmo.sshr.config.AttendSqlDao;
import com.ctgtmo.sshr.config.PayrollSqlDao;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import com.xxl.job.core.log.XxlJobLogger;

/**  
 * @Title: TestJob.java   
 * @Company: 北京易才博普奥管理顾问有限公司
 * @Package: com.ctgtmo.sshr.service   
 * @Description:
 * @author: 王共亮     
 * @date: 2020年6月1日 下午4:51:51   
 */
@Component
public class TestJob {
  @Autowired
  private AttendSqlDao attendSqlDao;

  @Autowired
  private PayrollSqlDao PayrollSqlDao;

  //考勤数据库测试
  @XxlJob("attendJob")
  public ReturnT<String> attendJob(String param) throws Exception {
    XxlJobLogger.log("XXL-JOB, Hello attend..............");
    System.out.println("我开始执行查询考勤数据库任务了========");
    List<Map<String, Object>> map = attendSqlDao.queryForList("SELECT * FROM holiday");
    System.out.println("假期的数量为：" + map.size());
    return ReturnT.SUCCESS;
  }

  //薪资数据库测试
  @XxlJob("payrollJob")
  public ReturnT<String> payrollJob(String param) throws Exception {
    XxlJobLogger.log("XXL-JOB, Hello payroll............");
    System.out.println("我开始执行查询薪资数据库任务了========");
    List<Map<String, Object>> map = PayrollSqlDao.queryForList("SELECT * FROM pay_subject  WHERE company_id=90");
    System.out.println("假期的数量为：" + map.size());
    return ReturnT.SUCCESS;
  }
}
