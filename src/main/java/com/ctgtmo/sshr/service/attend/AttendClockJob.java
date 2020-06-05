package com.ctgtmo.sshr.service.attend;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.ctgtmo.sshr.config.RedisHelper;
import com.ctgtmo.sshr.model.AttendClock;
import com.ctgtmo.sshr.model.AttendGroup;
import com.ctgtmo.sshr.model.AttendGroupSpecial;
import com.ctgtmo.sshr.model.AttendGroupWorkday;
import com.ctgtmo.sshr.model.AttendSchedul;
import com.ctgtmo.sshr.model.AttendShift;
import com.ctgtmo.sshr.model.Holiday;
import com.ctgtmo.sshr.model.response.AttendOrgResponse;
import com.ctgtmo.sshr.model.response.EmployResponse;
import com.ctgtmo.sshr.model.response.SchedulResponse;
import com.ctgtmo.sshr.model.response.SchedulShiftTime;
import com.ctgtmo.sshr.repository.AttendRecordDao;
import com.ctgtmo.sshr.repository.EmployDao;
import com.ctgtmo.sshr.utils.AttendType;
import com.ctgtmo.sshr.utils.DateUtils;
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
   * redis缓存
   */
  @Autowired
  private RedisHelper redisHelper;

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
    if (CollectionUtils.isNotEmpty(defaultcompanyGroupList)) {
      for (int i = 0; i < defaultcompanyGroupList.size(); i++) {
        companyList.add(Integer.parseInt(defaultcompanyGroupList.get(i).get("company_id").toString()));
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
      //考勤组是否包含排班
      boolean scheduFlag = false;
      //考勤组是否包含自由和固定
      boolean fixedFlag = false;
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
          //判断是否包含排班数据
          if (groupList.get(i).getType() == 2) {
            scheduFlag = true;
          } else {
            fixedFlag = true;
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
          //获取当前查询日期
          String workDay = LocalDate.now().toString();
          //从redis中查询在生成记录之前已经生成的员工打卡数据,redis的key=yyyyMMdd+companyId+employId(20200603_12_xxxx),value值为employId
          String redisKey = workDay.replace("-", "") + "_" + companyId + "_";
          Set<String> keys = redisHelper.Keys(redisKey);
          List<EmployResponse> resultEmployList = new ArrayList<>();
          if (CollectionUtils.isNotEmpty(keys)) {
            //redis中已经生成打卡数据的员工
            List<EmployResponse> redisEmployList = new ArrayList<>();
            //把已生成的员工打卡数据中删掉
            for (String key : keys) {
              EmployResponse employ = new EmployResponse();
              employ.setEmployId(key.replace(redisKey, ""));
              redisEmployList.add(employ);
            }
            //去掉重复员工,得到剩下员工集合
            resultEmployList = realOrgEmployList.stream().filter(item -> !redisEmployList.stream().map(e -> e.getEmployId()).collect(Collectors.toList())
            .contains(item.getEmployId())).collect(Collectors.toList());
          }
          //获取每个员工的排班数据
          List<AttendClock> employShiftList = getEmployClock(companyId, groupList, resultEmployList, groupIdList, scheduFlag, fixedFlag, workDay);
          //批量添加数据库
          attendRecordDao.batchAddEmployClock(employShiftList);
        }
      }
    }
  }

  /** 
  * @Title: getEmployClock 
  * @Description: 生成员工打卡班次时间
  * @param companyId 公司主键
  * @param groupList 考勤组信息
  * @param employList 员工集合
  * @param groupIdList 考勤组ids-包含默认考勤组
  * @param scheduFlag true包含排班信息
  * @param fixedFlag true包含固定和自由班次信息
  * @return List<AttendClock> 每个员工详细的班次信息
  * @author 王共亮
  * @date 2020年6月3日上午10:02:36
  */
  public List<AttendClock> getEmployClock(int companyId, List<AttendGroup> groupList, List<EmployResponse> employList, List<Integer> groupIdList,
  boolean scheduFlag, boolean fixedFlag, String workDay) throws ParseException {
    //定义返回的员工排班信息
    List<AttendClock> resultEmployList = new ArrayList<>();
    //循环员工和考勤组数据赋值考勤组属性
    for (int a = 0; a < employList.size(); a++) {
      for (int b = 0; b < groupList.size(); b++) {
        //相同考勤组的才赋值
        if (employList.get(a).getGroupId() == groupList.get(b).getId()) {
          employList.get(a).setGroupType(groupList.get(b).getType());
          //true开启false关闭
          employList.get(a).setHolidayRest(groupList.get(b).isHolidayIsopen());
        }
      }
    }
    List<AttendSchedul> schedulList = new ArrayList<>();
    //scheduFlag true包含排班
    if (scheduFlag) {
      //根据日期和考勤组ids查询排班详情
      schedulList = attendRecordDao.querySchedulList(workDay, groupIdList);
    }
    List<AttendGroupWorkday> groupWorkList = new ArrayList<>();
    List<AttendGroupSpecial> groupSpecialList = new ArrayList<>();
    List<Holiday> holidayList = new ArrayList<>();
    List<AttendShift> shiftList = new ArrayList<>();
    //fixedFlag true包含固定和自由班次
    if (fixedFlag) {
      //根据日期和考勤组查询工作时间(固定/自由类型)
      groupWorkList = attendRecordDao.queryGroupWorkList(DateUtils.dateToWeek(workDay), groupIdList);
      //根据日期和考勤组查询特殊工作时间(固定/自由类型)
      groupSpecialList = attendRecordDao.queryGroupSpecialList(workDay, groupIdList);
      //根据日期查询是否节假日(固定/自由类型)
      holidayList = attendRecordDao.queryHolidayList(workDay);
      //获取需要查询的班次id
      List<Integer> shiftIdList = new ArrayList<>();
      if (CollectionUtils.isNotEmpty(groupWorkList)) {
        for (int i = 0; i < groupWorkList.size(); i++) {
          shiftIdList.add(groupWorkList.get(i).getShiftsId());
        }
      }
      if (CollectionUtils.isNotEmpty(groupSpecialList)) {
        for (int i = 0; i < groupSpecialList.size(); i++) {
          shiftIdList.add(groupSpecialList.get(i).getShiftsId());
        }
      }
      //根据班次主键查询详情
      shiftList = attendRecordDao.queryShiftList(shiftIdList);
    }
    //循环员工集合生成对应的打卡数据
    for (int i = 0; i < employList.size(); i++) {
      //获取员工主键
      String employId = employList.get(i).getEmployId();
      //获取员工考勤组主键
      int groupId = employList.get(i).getGroupId();
      //获取员工所在考勤组类型
      int groupType = employList.get(i).getGroupType();
      //获取员工所在考勤组是否开启节假日平排休
      boolean holidayRest = employList.get(i).isHolidayRest();
      AttendType attendType = AttendType.fromAttendType(groupType);
      AttendClock clock = new AttendClock();
      switch (attendType) {
        //1为固定类型
        case GROUP_FIXED_TYPE:
          //判断当天是否是特殊日期,如果是直接读取班次信息
          if (CollectionUtils.isNotEmpty(groupSpecialList)) {
            //是否包含特殊日期标志
            boolean specialFlag = true;
            //因为是查询多个考勤组的特殊日期,有可能当前的考勤组没有特殊日期
            for (int j = 0; j < groupSpecialList.size(); j++) {
              //特殊日期的考勤组主键
              int specialGroupId = groupSpecialList.get(j).getGroupId();
              //只有相等的情况下说明今天是特殊日期直接读取班次信息
              if (groupId == specialGroupId) {
                //判断特殊日期是休息日2/工作日1
                if (groupSpecialList.get(j).getIsWorkday() == 1) {
                  //获取特殊日期中的shiftId
                  int specialShiftId = groupSpecialList.get(j).getShiftsId();
                  for (int a = 0; a < shiftList.size(); a++) {
                    //比较相同的shiftId
                    int shiftId = shiftList.get(a).getId();
                    //休息时间
                    String restTime = StringUtils.isNotBlank(shiftList.get(a).getRestTime()) ? shiftList.get(a).getRestTime() : "";
                    //班次名称
                    String shiftName = shiftList.get(a).getName();
                    if (shiftId == specialShiftId) {
                      //生成固定工作日的打卡数据实体
                      clock = new AttendClock("", groupType, specialGroupId, employId, companyId, workDay, shiftId, shiftName, "#4196f6", restTime, 0, shiftList
                      .get(a).getWorkOn(), 3, shiftList.get(a).getWorkOut(), 3, 1);
                    }
                  }
                } else {
                  //生成固定休息日的打卡数据
                  clock = new AttendClock("", groupType, groupId, employId, companyId, workDay, 0, "", "#ffffff", "", 0, "", 1, "", 1, 2);
                }
                //说明已经生成特殊日期打卡数据
                specialFlag = false;
                break;
              }
            }
            //没有特殊日期的情况,需要考虑节假日是否安排休息
            if (specialFlag) {
              clock = getFixedGroup(groupWorkList, holidayList, shiftList, groupId, holidayRest, groupType, employId, companyId, workDay);
            }
          } else {
            //按照正常的流程,没有特殊日期的情况,需要考虑节假日是否安排休息
            clock = getFixedGroup(groupWorkList, holidayList, shiftList, groupId, holidayRest, groupType, employId, companyId, workDay);
          }
          resultEmployList.add(clock);
          break;
        //2为排班类型
        case GROUP_SCHEDUL_TYPE:
          //排版中是否包含此员工,默认不包含
          boolean isHaveEmploy = false;
          //查询schedulList,如果查询不到则员工默认为休息,员工会存在多条排班记录
          for (int j = 0; j < schedulList.size(); j++) {
            //排班中有员工
            String scheduEmployId = schedulList.get(j).getEmployId();
            //比较相同的员工
            if (employId.equals(scheduEmployId)) {
              //分解员工排班字符串json,排班分为两种情况1是有班次的2是直接拖取的时间(没有班次信息的)
              StringBuffer json = new StringBuffer();
              json.append("[");
              //详细班次信息
              json.append(schedulList.get(j).getAttendTime());
              json.append("]");
              List<SchedulResponse> shift = JSONArray.parseArray(json.toString(), SchedulResponse.class);
              //查看有多少个班次信息
              if (CollectionUtils.isNotEmpty(shift)) {
                //获取shiftsTimes的值,数量大于0说明是排班
                List<SchedulShiftTime> shiftTime = shift.get(0).getShiftsTimes();
                SchedulResponse schedul = shift.get(0);
                //休息时间拼接
                String restTime = StringUtils.isNotBlank(schedul.getSleepBegin()) ? (schedul.getSleepBegin() + "-" + schedul.getSleepEnd()) : "";
                if (shiftTime.size() > 0) {
                  //循环看有多少班次上下班时间
                  for (int m = 0; m < shiftTime.size(); m++) {
                    //员工排班正常班次实体
                    clock = new AttendClock("", groupType, groupId, employId, companyId, workDay, schedul.getId(), schedul.getShiftsName(), schedul.getColor(),
                    restTime, schedul.getWorkstationId(), shiftTime.get(m).getBegin(), 3, shiftTime.get(m).getEnd(), 3, 1);
                  }
                } else {
                  //说明是拉取的时间不包含班次信息只有上下班时间
                  clock = new AttendClock("", groupType, groupId, employId, companyId, workDay, schedul.getId(), schedul.getShiftsName(), schedul.getColor(),
                  "", schedul.getWorkstationId(), schedul.getBeginTime(), 3, schedul.getEndTime(), 3, 1);
                }
              }
              //设置为已经查询到此员工的排班信息
              isHaveEmploy = true;
            }
          }
          //如果排班中查询不到这个员工的排班信息则默认为当天休息
          if (!isHaveEmploy) {
            //员工排班休息班次实体
            clock = new AttendClock("", groupType, groupId, employId, companyId, workDay, 0, "", "#ffffff", "", 0, "", 1, "", 1, 2);
          }
          resultEmployList.add(clock);
          break;
        //3为自由类型
        case GROUP_FREE_TYPE:
          //判断当天是否是特殊日期,如果是直接读取班次信息
          if (CollectionUtils.isNotEmpty(groupSpecialList)) {
            //是否包含特殊日期标志
            boolean specialFlag = true;
            //因为是查询多个考勤组的特殊日期,有可能当前的考勤组没有特殊日期
            for (int j = 0; j < groupSpecialList.size(); j++) {
              //特殊日期的考勤组主键
              int specialGroupId = groupSpecialList.get(j).getGroupId();
              //只有相等的情况下说明今天是特殊日期直接读取班次信息
              if (groupId == specialGroupId) {
                //判断特殊日期是休息日2/工作日1
                if (groupSpecialList.get(j).getIsWorkday() == 1) {
                  //生成工作日的打卡数据实体
                  clock = new AttendClock("", groupType, groupId, employId, companyId, workDay, 0, "", "#4196f6", "", 0, "", 3, "", 3, 1);
                } else {
                  //生成休息日的打卡数据实体
                  clock = new AttendClock("", groupType, groupId, employId, companyId, workDay, 0, "", "#ffffff", "", 0, "", 1, "", 1, 2);
                }
                //说明已经生成特殊日期打卡数据
                specialFlag = false;
                break;
              }
            }
            //没有特殊日期的情况,需要考虑节假日是否安排休息
            if (specialFlag) {
              clock = getFreeGroup(groupWorkList, holidayList, groupId, holidayRest, groupType, employId, companyId, workDay);
            }
          } else {
            //按照正常的流程,没有特殊日期的情况,需要考虑节假日是否安排休息
            clock = getFreeGroup(groupWorkList, holidayList, groupId, holidayRest, groupType, employId, companyId, workDay);
          }
          resultEmployList.add(clock);
          break;
      }
    }
    return resultEmployList;
  }

  /** 
  * @Title: getFreeGroup 
  * @Description: 自由考勤组正常的流程
  * @param groupWorkList 工作日班次集合
  * @param holidayList 节假日list
  * @param groupId 考勤组id
  * @param holidayRest 考勤组是否开启节假日排休
  * @param groupType 考勤组类型
  * @param employId 员工主键
  * @param companyId 公司主键
  * @param workDay 工作日
  * @return AttendClock 
  * @author 王共亮
  * @date 2020年6月4日上午9:52:32
  */
  public AttendClock getFreeGroup(List<AttendGroupWorkday> groupWorkList, List<Holiday> holidayList, int groupId, boolean holidayRest, int groupType,
  String employId, int companyId, String workDay) {
    AttendClock clock = new AttendClock();
    //用groupWorkList中的数据获取具体班次信息,正常情况下一个考勤组周几对应一条数据
    if (CollectionUtils.isNotEmpty(groupWorkList)) {
      for (int j = 0; j < groupWorkList.size(); j++) {
        int workGroupId = groupWorkList.get(j).getGroupId();
        if (groupId == workGroupId) {
          //对比考勤组当天是工作日1/休息日2
          if (groupWorkList.get(j).getIsWorkday() == 1) {
            //判断是否开启法定节假日休息true开启false关闭
            if (holidayRest) {
              //判断当天是不会法定节假日
              if (CollectionUtils.isNotEmpty(holidayList)) {
                //生成休息日的打卡数据实体
                clock = new AttendClock("", groupType, groupId, employId, companyId, workDay, 0, "", "#ffffff", "", 0, "", 1, "", 1, 2);
              } else {
                //按照自由班次生成打卡数据
                clock = new AttendClock("", groupType, groupId, employId, companyId, workDay, 0, "", "#4196f6", "", 0, "", 3, "", 3, 1);
              }
            } else {
              //按照自由班次生成打卡数据
              clock = new AttendClock("", groupType, groupId, employId, companyId, workDay, 0, "", "#4196f6", "", 0, "", 3, "", 3, 1);
            }
          } else {
            //生成休息日的打卡数据实体
            clock = new AttendClock("", groupType, groupId, employId, companyId, workDay, 0, "", "#ffffff", "", 0, "", 1, "", 1, 2);
          }
          break;
        }
      }
    }
    return clock;
  }

  /** 
  * @Title: getFixedGroup 
  * @Description: 生成固定考勤组的打卡数据
  * @param groupWorkList 工作日班次集合
  * @param holidayList 节假日list
  * @param shiftList 班次信息list
  * @param groupId 考勤组主键
  * @param holidayRest 考勤组是否开启节假日排休
  * @param groupType 考勤组类型
  * @param employId 员工主键
  * @param companyId 公司主键
  * @param workDay 工作日
  * @return AttendClock 
  * @author 王共亮
  * @date 2020年6月4日下午3:11:13
  */
  public AttendClock getFixedGroup(List<AttendGroupWorkday> groupWorkList, List<Holiday> holidayList, List<AttendShift> shiftList, int groupId,
  boolean holidayRest, int groupType, String employId, int companyId, String workDay) {
    AttendClock clock = new AttendClock();
    //需要根据工作日排班数据得到具体的班次信息
    if (CollectionUtils.isNotEmpty(groupWorkList)) {
      //正常情况下一个考勤组周几对应一条数据
      for (int a = 0; a < groupWorkList.size(); a++) {
        //获取固定排班中的考勤组id
        int workGroupId = groupWorkList.get(a).getGroupId();
        int workShiftId = groupWorkList.get(a).getShiftsId();
        if (workGroupId == groupId) {
          //判断当天是工作日1/休息日2
          if (groupWorkList.get(a).getIsWorkday() == 1) {
            //判断是否开启了节假日自动排休
            if (holidayRest) {
              //查询当天是不是法定节假日
              if (CollectionUtils.isNotEmpty(holidayList)) {
                //生成固定休息日的打卡数据
                clock = new AttendClock("", groupType, groupId, employId, companyId, workDay, 0, "", "#ffffff", "", 0, "", 1, "", 1, 2);
              } else {
                //正常的固定排班
                for (int b = 0; b < shiftList.size(); b++) {
                  //比较相同的shiftId
                  int shiftId = shiftList.get(b).getId();
                  //休息时间
                  String restTime = StringUtils.isNotBlank(shiftList.get(b).getRestTime()) ? shiftList.get(b).getRestTime() : "";
                  //班次名称
                  String shiftName = shiftList.get(b).getName();
                  if (shiftId == workShiftId) {
                    //生成固定工作日的打卡数据实体
                    clock = new AttendClock("", groupType, groupId, employId, companyId, workDay, shiftId, shiftName, "#4196f6", restTime, 0, shiftList.get(b)
                    .getWorkOn(), 3, shiftList.get(b).getWorkOut(), 3, 1);
                  }
                }
              }
            } else {
              //正常的固定排班
              for (int b = 0; b < shiftList.size(); b++) {
                //比较相同的shiftId
                int shiftId = shiftList.get(b).getId();
                //休息时间
                String restTime = StringUtils.isNotBlank(shiftList.get(b).getRestTime()) ? shiftList.get(b).getRestTime() : "";
                //班次名称
                String shiftName = shiftList.get(b).getName();
                if (shiftId == workShiftId) {
                  //生成固定工作日的打卡数据实体
                  clock = new AttendClock("", groupType, groupId, employId, companyId, workDay, shiftId, shiftName, "#4196f6", restTime, 0, shiftList.get(b)
                  .getWorkOn(), 3, shiftList.get(b).getWorkOut(), 3, 1);
                }
              }
            }
          } else {
            //生成固定休息日的打卡数据
            clock = new AttendClock("", groupType, groupId, employId, companyId, workDay, 0, "", "#ffffff", "", 0, "", 1, "", 1, 2);
          }
          break;
        }
      }
    }
    return clock;
  }

}
