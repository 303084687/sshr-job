package com.ctgtmo.sshr.service.attend;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ctgtmo.sshr.model.AttendGroup;
import com.ctgtmo.sshr.model.response.AttendOrgResponse;
import com.ctgtmo.sshr.model.response.EmployResponse;
import com.ctgtmo.sshr.repository.AttendRecordDao;
import com.ctgtmo.sshr.repository.EmployDao;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import com.xxl.job.core.log.XxlJobLogger;

/**  
 * @Title: AttendClockJob.java   
 * @Company: 北京易才博普奥管理顾问有限公司
 * @Package: com.ctgtmo.sshr.service.attend   
 * @Description:生成打卡记录定时任务
 * @author: 王共亮     
 * @date: 2020年6月2日 上午10:49:29   
 */
@Component
public class AttendClockJob {
  /**
   * 员工dao
   */
  @Autowired
  private EmployDao employDao;

  /**
   * 考勤dao
   */
  @Autowired
  private AttendRecordDao attendRecordDao;

  /** 
  * @Title: addAttendClockDay 
  * @Description: 根据公司进行分线程执行定时插入打卡记录,每天0点10分执行
  * @param param corn=0 10 0 * * ?
  * @throws Exception ReturnT<String> 
  * @author 王共亮
  * @date 2020年6月2日上午11:01:57
  */
  @XxlJob("addAttendClockDayJob")
  public ReturnT<String> addAttendClockDay(String param) throws Exception {
    XxlJobLogger.log("每天凌晨执行增加打卡记录定时任务");
    //查询默认考勤组数据确定公司数量
    List<Map<String, Object>> defaultcompanyGroupList = attendRecordDao.queryDefaultCompanyList();
    List<Integer> companyList = new ArrayList<>();
    companyList.add(320);
    if (CollectionUtils.isNotEmpty(defaultcompanyGroupList)) {
      for (int i = 0; i < defaultcompanyGroupList.size(); i++) {
        //companyList.add(Integer.parseInt(defaultcompanyGroupList.get(i).get("company_id").toString()));
      }
    }
    //判断公司有考勤组的情况下才走线程任务
    if (companyList != null && companyList.size() > 0) {
      //生成对应线程数量
      ExecutorService executor = Executors.newFixedThreadPool(3);
      //批量任务
      for (int i = 0; i < companyList.size(); i++) {
        //获取公司主键
        final int companyId = companyList.get(i);
        executor.execute(new Runnable() {
          @Override
          public void run() {
            try {
              //执行插入数据的任务
              addAttendClock(companyId);
            } catch (Exception e) {
              e.printStackTrace();
            }
          }
        });
      }
      //多线程任务执行完毕，关闭线程池
      executor.shutdown();
    }
    return ReturnT.SUCCESS;
  }

  /** 
  * @Title: addAttendClock 
  * @Description: 定时生成的根据考勤组生成打卡数据
  * @param companyId 公司主键
  * @throws Exception void 
  * @author 王共亮
  * @date 2020年6月2日上午11:03:27
  */
  @Transactional
  public void addAttendClock(int companyId) throws Exception {
    //根据公司主键查询所有员工
    List<EmployResponse> employList = employDao.queryEmployList(companyId);
    //根据公司主键查询考勤组
    List<AttendGroup> groupList = attendRecordDao.queryCompanyGroupList(companyId);
    //根据考勤组数据查询关联的部门/人员信息
    if (CollectionUtils.isNotEmpty(groupList) && CollectionUtils.isNotEmpty(employList)) {
      //公司默认的考勤组id
      int defaultGroupId = 0;
      int groupSize = groupList.size();
      //如果数量为1说明这个公司使用的是默认考勤组
      if (groupSize == 1) {
        //循环员工添加考勤组id
        for (int i = 0; i < employList.size(); i++) {
          employList.get(i).setGroupId(groupList.get(0).getId());
        }
        //当考勤组大于1说明有多个考勤组,默认考勤组不会出现在attend_group_org的表中
      } else {
        //需要查询的考勤组id
        List<Integer> groupIdList = new ArrayList<>();
        for (int i = 0; i < groupSize; i++) {
          groupIdList.add(groupList.get(i).getId());
          //赋值默认考勤组id
          if (groupList.get(i).getIsDefault() == 1) {
            defaultGroupId = groupList.get(i).getId();
          }
        }
        //查询相关联的组织/人员信息
        List<AttendOrgResponse> groupOrgList = attendRecordDao.queryGroupOrgList(groupIdList);
        if (CollectionUtils.isNotEmpty(groupOrgList)) {
          //参与其他考勤组的员工
          List<EmployResponse> otherGroupList = new ArrayList<>();
          //无需参与考勤的员工,有可能参与别的部门考勤/默认考勤组
          List<EmployResponse> noGroupList = new ArrayList<>();
          //参与部门考勤的员工
          List<EmployResponse> departGroupList = new ArrayList<>();
          //参与考勤的部门
          List<AttendOrgResponse> orgGroupList = new ArrayList<>();
          //1：部门 2：无需考勤人员 3：参与考勤人员
          for (int i = 0; i < groupOrgList.size(); i++) {
            //员工参与其他考勤组type==3
            if (groupOrgList.get(i).getType() == 3) {
              EmployResponse employ = new EmployResponse();
              employ.setEmployId(groupOrgList.get(i).getRelationId());
              employ.setOrgId(groupOrgList.get(i).getOrgId());
              employ.setGroupId(groupOrgList.get(i).getGroupId());
              otherGroupList.add(employ);
            }
            //无需参与考勤的人员
            if (groupOrgList.get(i).getType() == 2) {
              EmployResponse employ = new EmployResponse();
              employ.setEmployId(groupOrgList.get(i).getRelationId());
              employ.setOrgId(groupOrgList.get(i).getOrgId());
              employ.setGroupId(groupOrgList.get(i).getGroupId());
              noGroupList.add(employ);
            }
            //需要参与考勤的部门
            if (groupOrgList.get(i).getType() == 1) {
              orgGroupList.add(groupOrgList.get(i));
            }
          }
          //处理参与考勤的部门得到具体的人员考勤组
          if (CollectionUtils.isNotEmpty(orgGroupList)) {
            for (int a = 0; a < orgGroupList.size(); a++) {
              for (int b = 0; b < employList.size(); b++) {
                //根据相同的部门id确定所属的员工
                if (orgGroupList.get(a).getRelationId().equals(employList.get(b).getOrgId())) {
                  EmployResponse employ = new EmployResponse();
                  employ.setEmployId(employList.get(b).getEmployId());
                  employ.setOrgId(employList.get(b).getOrgId());
                  employ.setGroupId(orgGroupList.get(a).getGroupId());
                  departGroupList.add(employ);
                }
              }
            }
          }
          //得到真正参与部门考勤的人员departGroupList-otherGroupList-noGroupList
          List<EmployResponse> realOrgEmployList = departGroupList.stream().filter(item -> !otherGroupList.stream().map(e -> e.getEmployId()).collect(Collectors
          .toList()).contains(item.getEmployId())).filter(item -> !noGroupList.stream().map(e -> e.getEmployId()).collect(Collectors.toList()).contains(item
          .getEmployId())).collect(Collectors.toList());
          //得到默认的参与考勤人员employList-realOrgEmployList-otherGroupList
          List<EmployResponse> defaultEmployList = employList.stream().filter(item -> !otherGroupList.stream().map(e -> e.getEmployId()).collect(Collectors
          .toList()).contains(item.getEmployId())).filter(item -> !realOrgEmployList.stream().map(e -> e.getEmployId()).collect(Collectors.toList()).contains(
          item.getEmployId())).collect(Collectors.toList());
          //给默认考勤组员工赋值考勤组id
          for (int i = 0; i < defaultEmployList.size(); i++) {
            defaultEmployList.get(i).setGroupId(defaultGroupId);
          }
          //合并获取全公司人员考勤信息-包含没有部门的员工
          realOrgEmployList.addAll(otherGroupList);
          realOrgEmployList.addAll(defaultEmployList);

        }
      }
    }
  }
}
