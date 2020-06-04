package com.ctgtmo.sshr.utils;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * @ClassName: DateUtils 
 * @author: 王共亮
 * @Company: 北京易才博普奥管理咨询有限公司
 * @Description:时间计算类
 * @date: 2020年1月10日 下午3:37:54
 */

public class DateUtils {

  /**
   * 秒
   */
  private static final long ONE_MINUTE = 60;

  /**
   * 时
   */
  private static final long ONE_HOUR = 3600;

  /**
   * 天
   */
  private static final long ONE_DAY = 86400;

  //月
  //private static final long ONE_MONTH = 2592000;

  /**
   * 年
   */
  private static final long ONE_YEAR = 31104000;

  /**
   * 7天
   */
  private static final long SENVER_DAY = 604800;

  public static Calendar calendar = Calendar.getInstance();

  /**
   * @return 返回 yyyy-mm-dd格式
   */
  public static String getDate() {
    return getYear() + "-" + getMonth() + "-" + getDay();
  }

  /**
   * @param format
   * yyyy年MM月dd HH:mm 
   * MM-dd HH:mm 2012-12-25
   * 
   */
  public static String getDate(String format) {
    SimpleDateFormat simple = new SimpleDateFormat(format);
    return simple.format(calendar.getTime());
  }

  /**
   * 
   * @return yyyy-MM-dd HH:mm 
   * 2012-12-29 23:47
   */
  public static String getDateAndMinute() {
    SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    return simple.format(calendar.getTime());
  }

  /**
   * 
   * @return
   *  yyyy-MM-dd HH:mm:ss 
   *  2012-12-29 23:47:36
   */
  public static String getFullDate() {
    SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    return simple.format(calendar.getTime());
  }

  /**
   * 距离今天多久
   * @param date
   * @return 
   * 
   */
  public static String fromToday(Date date) {
    //时间转换
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    long time = date.getTime() / 1000;
    long now = System.currentTimeMillis() / 1000;
    long ago = now - time;
    if (ago <= ONE_HOUR) {
      return ago / ONE_MINUTE + "分钟前";
    } else if (ago <= ONE_DAY) {
      return ago / ONE_HOUR + "小时前";
    } else if (ago >= ONE_DAY && ago <= SENVER_DAY) {
      long day = ago / ONE_DAY;
      return day + "天前";
    } else {
      //返回具体时间
      SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd");
      return simple.format(date);
    }

  }

  /**
   * @Title:betweenDate  
   * @Description: 计算两个时间差
   * @param startTime
   * @param endTime
   * @return 1年以下为空，1-2之间返回1年，以此类推
   * @throws Exception 
   */
  public static String betweenDate(String startTime, String endTime) throws Exception {
    //格式化时间
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    //判断不为空的情况下
    if (StringUtils.isNotBlank(startTime) && StringUtils.isNoneBlank(endTime)) {
      Date startDate = format.parse(startTime);
      Date endDate = format.parse(endTime);
      //计算两个时间之间相差的毫秒数
      long time = endDate.getTime() / 1000 - startDate.getTime() / 1000;
      if (time < ONE_YEAR) {
        return "";
      } else {
        long year = time / ONE_YEAR;
        return year + "年";
      }
    }
    return "";
  }

  /**
   * 距离截止日期还有多长时间
   * 
   * @param date
   * @return
   */
  public static String fromDeadline(Date date) {
    long deadline = date.getTime() / 1000;
    long now = System.currentTimeMillis() / 1000;
    long remain = deadline - now;
    if (remain <= ONE_HOUR) {
      return "只剩下" + remain / ONE_MINUTE + "分钟";
    } else if (remain <= ONE_DAY) {
      return "只剩下" + remain / ONE_HOUR + "小时" + (remain % ONE_HOUR / ONE_MINUTE) + "分钟";
    } else {
      long day = remain / ONE_DAY;
      long hour = remain % ONE_DAY / ONE_HOUR;
      long minute = remain % ONE_DAY % ONE_HOUR / ONE_MINUTE;
      return "只剩下" + day + "天" + hour + "小时" + minute + "分钟";
    }

  }

  /**
   * 距离今天的绝对时间
   * @param date
   * @return
   * @throws ParseException 
   */
  public static String toToday(String endTime) throws ParseException {
    //返回的字符串
    StringBuffer returnStr = new StringBuffer();
    //格式化时间
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    Date date = format.parse(endTime);
    String str = format.format(date);
    if (StringUtils.isNotBlank(str)) {
      //格式化时间
      LocalDate today = LocalDate.now();
      String[] strs = str.split("-");
      LocalDate birthDate = LocalDate.of(Integer.parseInt(strs[0]), Integer.parseInt(strs[1]), Integer.parseInt(strs[2]));
      Period p = Period.between(birthDate, today);
      //年
      int year = p.getYears();
      //月
      int month = p.getMonths();
      //日
      int day = p.getDays();
      //拼接返回结果
      if (year > 0) {
        returnStr.append(year + "年");
      }
      if (month > 0) {
        returnStr.append(month + "个月");
      }
      if (day > 0) {
        returnStr.append(day + "天");
      }
    }
    return returnStr.toString();
  }

  /**
  * @Title: betweenDay 
  * @Description:计算两个时间差
  * @param startTime
  * @param endTime
  * @throws ParseException String
  * @author 王共亮
  * @date 2020年1月10日下午3:40:55
   */
  public static String betweenDay(String startTime, String endTime) throws ParseException {
    //返回的字符串
    StringBuffer returnStr = new StringBuffer();
    //格式化时间
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    Date date1 = format.parse(startTime);
    Date date2 = format.parse(endTime);
    String str1 = format.format(date1);
    String str2 = format.format(date2);
    if (StringUtils.isNotBlank(str1) && StringUtils.isNotBlank(str2)) {
      //格式化时间
      String[] strs1 = str1.split("-");
      String[] strs2 = str2.split("-");
      LocalDate startDate = LocalDate.of(Integer.parseInt(strs1[0]), Integer.parseInt(strs1[1]), Integer.parseInt(strs1[2]));
      LocalDate endDate = LocalDate.of(Integer.parseInt(strs2[0]), Integer.parseInt(strs2[1]), Integer.parseInt(strs2[2]));
      Period p = Period.between(startDate, endDate);
      //年
      int year = p.getYears();
      //月
      int month = p.getMonths();
      //日
      int day = p.getDays();
      //拼接返回结果
      if (year > 0) {
        returnStr.append(year + "年");
      }
      if (month > 0) {
        returnStr.append(month + "个月");
      }
      if (day > 0) {
        returnStr.append(day + "天");
      }
    }
    return returnStr.toString();
  }

  public static String getYear() {
    return calendar.get(Calendar.YEAR) + "";
  }

  public static String getMonth() {
    int month = calendar.get(Calendar.MONTH) + 1;
    return month + "";
  }

  public static String getDay() {
    return calendar.get(Calendar.DATE) + "";
  }

  public static String get24Hour() {
    return calendar.get(Calendar.HOUR_OF_DAY) + "";
  }

  public static String getMinute() {
    return calendar.get(Calendar.MINUTE) + "";
  }

  public static String getSecond() {
    return calendar.get(Calendar.SECOND) + "";
  }

  /**
   * 取指定时间的第二天
   * @param sourceDate
   * @return
   * @throws ParseException
   */
  public static String getScondDay(String sourceDate) throws ParseException {
    SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd");
    Date date = simple.parse(sourceDate);
    //时间转换
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.add(Calendar.DAY_OF_MONTH, 1);
    date = calendar.getTime();
    return simple.format(date);
  }

  /**
   * 取指定时间的后几天，或者前几天
   * @param sourceDate
   * @return
   * @throws ParseException
   */
  public static String getDay(String sourceDate, int n) throws ParseException {
    SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd");
    Date date = simple.parse(sourceDate);
    //时间转换
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.add(Calendar.DAY_OF_MONTH, n);
    date = calendar.getTime();
    return simple.format(date);
  }

  /**
   * @Title:getMonthDayList  
   * @Description:根据传入月份算出当月月份集合
   * @param month
   * @return
   */
  public static List<String> getMonthDayList(String month) {
    //补全是为了格式化不报错
    month += "-01";
    LocalDate date = LocalDate.parse(month);
    //返回的月份数据集合
    List<String> monthDayList = new ArrayList<>();
    int days = date.lengthOfMonth();
    for (int i = 1; i <= days; i++) {
      String day = String.valueOf(i);
      //10以下补0
      if (i < 10) {
        day = String.format("%02d", i);
      }
      monthDayList.add(month.substring(0, 8) + day);
    }
    return monthDayList;
  }

  /**
   * @Title:getMonth  
   * @Description:根据传入日期计算距离当月相差天数
   * @param startTime 开始时间
   * @param today true包含当天 false不包含
   * @return
   */
  public static List<String> getMonth(String startTime, boolean today) {
    //计算当月天数
    LocalDate date = LocalDate.parse(startTime);
    //返回的月份数据集合
    List<String> monthDayList = new ArrayList<>();
    int days = date.lengthOfMonth();
    //计算startTime距离月末还剩几天
    int start = date.getDayOfMonth();
    if (!today) {
      start += 1;
    }
    for (int i = start; i <= days; i++) {
      String day = String.valueOf(i);
      //10以下补0
      if (i < 10) {
        day = String.format("%02d", i);
      }
      monthDayList.add(startTime.substring(0, 8) + day);
    }
    return monthDayList;
  }

  /**
   * @Title:getMonthDay  
   * @Description:根据传入月份算出当月多少天
   * @param month
   * @return
   */
  public static int getMonthDay(String month) {
    //补全是为了格式化不报错
    month += "-01";
    LocalDate date = LocalDate.parse(month);
    int days = date.lengthOfMonth();
    return days;
  }

  //计算当天距离月初的时间--包含当天
  public static List<String> getDayOfMonth() {
    List<String> dayList = new ArrayList<>();
    //获取当前天数
    LocalDate nowDate = LocalDate.now();
    int count = nowDate.getDayOfMonth();
    for (int i = 1; i <= count; i++) {
      String day = String.valueOf(i);
      //10以下补0
      if (i < 10) {
        day = String.format("%02d", i);
      }
      dayList.add(nowDate.toString().substring(0, 8) + day);
    }
    return dayList;
  }

  /**
   * @Title:getDayOfTheWeek  
   * @Description:获取当天是星期几
   * @return
   */
  public static String getDayOfTheWeek() {
    String[][] strArray = { { "MONDAY", "一" }, { "TUESDAY", "二" }, { "WEDNESDAY", "三" }, { "THURSDAY", "四" }, { "FRIDAY", "五" }, { "SATURDAY", "六" }, { "SUNDAY", "日" } };
    LocalDate currentDate = LocalDate.now();
    String k = String.valueOf(currentDate.getDayOfWeek());
    //获取行数
    for (int i = 0; i < strArray.length; i++) {
      if (k.equals(strArray[i][0])) {
        k = strArray[i][1];
        break;
      }
    }
    return "星期" + k;
  }

  //根据传入分钟转换小时-分钟
  public static String convertHour(String fen) {
    String result = "";
    if (StringUtils.isNotBlank(fen)) {
      int time = Integer.parseInt(fen);
      int hours = (int) Math.floor(time / 60);
      int minute = time % 60;
      if (hours > 0) {
        result += hours + "小时";
      }
      if (minute > 0) {
        result += minute + "分钟";
      }
    }
    return result;
  }

  //计算两个数相除
  public static String division(int a, int b) {
    String result = "";
    float num = (float) a / b;
    DecimalFormat df = new DecimalFormat("0.00");
    result = df.format(num);
    return subString(result);
  }

  //计算字符串去掉0
  public static String subString(String str) {
    //计算类似0.70,1.00,1.05这样的数据
    if (StringUtils.isNotBlank(str) && str.length() == 4) {
      //判断位数,第4位为0减1位
      if (str.substring(2, 4).equals("00")) {
        str = str.substring(0, 1);
      }
    }
    return str;
  }

  //计算当前月份和传递的月份大小
  public static int compareMonth(String month) {
    //获取当前月份
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
    String nowMonth = formatter.format(LocalDate.now());
    return nowMonth.compareTo(month);
  }

  /**
   * 根据年月 获取当月的天数
   * @param year
   * @param month
   * @return
   */

  public static int getDaysByYearMonth(int year, int month) {
    Calendar a = Calendar.getInstance();
    a.set(Calendar.YEAR, year);
    a.set(Calendar.MONTH, month - 1);
    a.set(Calendar.DATE, 1);
    a.roll(Calendar.DATE, -1);
    int maxDate = a.get(Calendar.DATE);
    return maxDate;
  }

  //计算两个时间差(HH:MM)，list(hour,minute)
  public static List<Integer> diffByTime(String start, String end) throws ParseException {
    List<Integer> list = new ArrayList<>();
    DateFormat sdff = new SimpleDateFormat("HH:mm");
    long a = sdff.parse(start).getTime();
    long b = sdff.parse(end).getTime();
    long c = b - a;
    long hour = c / (3600 * 1000);
    long minute = (c - hour * 3600 * 1000) / (1000 * 60);
    list.add(Integer.parseInt(String.valueOf(hour)));
    list.add(Integer.parseInt(String.valueOf(minute)));
    return list;
  }

  /*
   * 参数格式2019-01-01 根据 日期计算星期几
   */
  public static String dateToWeek(String datetime) throws java.text.ParseException {
    SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
    String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
    // 获得一个日历
    Calendar cal = Calendar.getInstance();
    Date datet = null;
    datet = f.parse(datetime);
    cal.setTime(datet);
    // 指示一个星期中的某天。
    int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
    if (w < 0) {
      w = 0;
    }
    return weekDays[w];
  }

  /**
  * @Title: getMinute 
  * @Description:根据开始时间,结束时间,休息时间计算相隔的分钟
  * @param startTime开始时间
  * @param endTime结束时间
  * @param restTime 休息时间
  * @author 王共亮
  * @date 2020年1月10日下午3:42:11
   */
  public static int getMinute(String startTime, String endTime, String restTime) throws Exception {
    //上班有次日的情况
    if (StringUtils.isNotBlank(startTime)) {
      if (startTime.contains("次日")) {
        startTime = startTime.substring(startTime.length() - 5, startTime.length());
        Integer i = Integer.valueOf(startTime.split(":")[0]) + 24;
        startTime = i.toString() + ":" + (Integer.valueOf(startTime.split(":")[1]).toString());
      }
    }
    //下班有次日的情况
    if (StringUtils.isNotBlank(endTime)) {
      if (endTime.contains("次日")) {
        endTime = endTime.substring(endTime.length() - 5, endTime.length());
        Integer i = Integer.valueOf(endTime.split(":")[0]) + 24;
        endTime = i.toString() + ":" + (Integer.valueOf(endTime.split(":")[1]).toString());
      }
    }
    //先判断有没有休息时间
    int restMinute = 0;
    if (StringUtils.isNotBlank(restTime)) {
      String[] time = restTime.split("-");
      //计算分钟数
      restMinute = getMin(time[0], time[1]);
    }
    //计算startTime和endTime的分钟数
    int minute = 0;
    if (StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(endTime)) {
      minute = getMin(startTime, endTime);
    }
    return minute - restMinute;
  }

  //计算两个时间相差的分钟数
  public static int getMinute1(String startTime, String endTime) throws Exception {
    //计算startTime和endTime的分钟数
    int minute = 0;
    if (StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(endTime)) {
      //判断是不是同一天
      if (startTime.substring(0, 10).equals(endTime.substring(0, 10))) {
        minute = getMin(startTime.substring(11, 16), endTime.substring(11, 16));
      } else {
        //处理结束时间
        String end = endTime.substring(0, 10) + " ";
        endTime = endTime.substring(endTime.length() - 8, endTime.length());
        Integer i = Integer.valueOf(endTime.split(":")[0]) + 24;
        endTime = end + i.toString() + (endTime.substring(endTime.length() - 6, endTime.length()));
        minute = getMin(startTime.substring(11, 16), endTime.substring(11, 16));
      }
    }
    return minute;
  }

  //计算两个时间相差的分钟数
  public static int getMin(String sdate, String edate) throws Exception {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
    //判断是否包含次日的情况
    if (!sdate.contains("次日") && edate.contains("次日")) {
      edate = edate.replaceAll("次日", "");
      //小时加上24
      Integer i = Integer.valueOf(edate.split(":")[0]) + 24;
      edate = i.toString() + ":" + (Integer.valueOf(edate.split(":")[1]).toString());
    } else {
      sdate = sdate.replaceAll("次日", "");
      edate = edate.replaceAll("次日", "");
    }
    Date start = simpleDateFormat.parse(sdate);
    Date end = simpleDateFormat.parse(edate);
    long between = (end.getTime() - start.getTime()) / 1000;
    long min = between / 60;
    return new Long(min).intValue();
  }

  //两个数相除
  public static int bs(int a, int b) {
    return (int) ((new BigDecimal((float) a / b).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()) * 100);
  }

  public static final int daysBetween(String early, String late) throws Exception {
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    Date earlydate = format.parse(early);
    Date latedate = format.parse(late);
    java.util.Calendar calst = java.util.Calendar.getInstance();
    java.util.Calendar caled = java.util.Calendar.getInstance();
    calst.setTime(earlydate);
    caled.setTime(latedate);
    //设置时间为0时   
    calst.set(java.util.Calendar.HOUR_OF_DAY, 0);
    calst.set(java.util.Calendar.MINUTE, 0);
    calst.set(java.util.Calendar.SECOND, 0);
    caled.set(java.util.Calendar.HOUR_OF_DAY, 0);
    caled.set(java.util.Calendar.MINUTE, 0);
    caled.set(java.util.Calendar.SECOND, 0);
    //得到两个日期相差的天数   
    int days = ((int) (caled.getTime().getTime() / 1000) - (int) (calst.getTime().getTime() / 1000)) / 3600 / 24;

    return days;
  }

  /**
   * Description: 比较时间差
   * @throws ParseException
   */
  public static List<Integer> diffByTimeMayNextDay(String begin, String end) throws ParseException {
    if (end.contains("次日")) {
      end = end.substring(end.length() - 5, end.length());
      Integer i = Integer.valueOf(end.split(":")[0]) + 24;
      end = i.toString() + ":" + (Integer.valueOf(end.split(":")[1]).toString());
    }
    if (begin.contains("次日")) {//
      begin = begin.substring(begin.length() - 5, begin.length());
      Integer j = Integer.valueOf(begin.split(":")[0]) + 24;
      begin = j.toString() + ":" + (Integer.valueOf(begin.split(":")[1]).toString());
    }

    String format = "HH:mm";

    Date nowDate = new SimpleDateFormat(format).parse(begin);
    Date endDate = new SimpleDateFormat(format).parse(end);

    long nd = 1000 * 24 * 60 * 60;
    long nh = 1000 * 60 * 60;
    long nm = 1000 * 60;
    List<Integer> list = new ArrayList<>();
    // long ns = 1000;
    // 获得两个时间的毫秒时间差异
    long diff = endDate.getTime() - nowDate.getTime();
    // 计算差多少天
    // long day = diff / nd;
    // 计算差多少小时
    long hour = diff % nd / nh;
    // 计算差多少分钟
    long min = diff % nd % nh / nm;
    list.add(Integer.parseInt(String.valueOf(hour)));
    list.add(Integer.parseInt(String.valueOf(min)));
    return list;
  }

  //判断一个时间(当前班次的上下班)是否在另一个时间范围内
  public static boolean regoin1(String startTime, String workOnTime, String workOffTime, String endTime) {
    //设置日期格式
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    Date workOn = null;
    Date workOff = null;
    Date beginTime = null;
    Date endTime1 = null;
    //如果middleTime为空直接返回true
    if (StringUtils.isBlank(workOnTime) && StringUtils.isBlank(workOffTime)) {
      return true;
    } else {
      //处理上班次日的情况
      if (workOnTime.contains("次日")) {
        workOnTime = workOnTime.replaceAll("次日", "");
        String s = workOnTime.substring(0, 10);
        String e = workOnTime.substring(11);
        //开始日期加一天
        LocalDate date = LocalDate.parse(s).plusDays(1L);
        workOnTime = date.toString() + " " + e;
      }
      //处理下班次日的情况
      if (workOffTime.contains("次日")) {
        workOffTime = workOffTime.replaceAll("次日", "");
        String s = workOffTime.substring(0, 10);
        String e = workOffTime.substring(11);
        //开始日期加一天
        LocalDate date = LocalDate.parse(s).plusDays(1L);
        workOffTime = date.toString() + " " + e;
      }
      try {
        workOn = df.parse(workOnTime);
        workOff = df.parse(workOffTime);
        beginTime = df.parse(startTime);
        endTime1 = df.parse(endTime);
      } catch (Exception e) {
        e.printStackTrace();
      }
      long start = beginTime.getTime();
      long workOnClock = workOn.getTime();
      long workOffClock = workOff.getTime();
      long end = endTime1.getTime();
      if ((workOnClock - start >= 0) && (end - workOffClock >= 0)) {
        return true;
      }
    }
    return false;
  }

  //判断一个时间是否在另一个时间范围内
  public static boolean regoin(String startTime, String middleTime, String endTime) {
    //设置日期格式
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    Date now = null;
    Date beginTime = null;
    Date endTime1 = null;
    //如果middleTime为空直接返回true
    if (StringUtils.isBlank(middleTime)) {
      return true;
    } else {
      //处理次日的情况
      if (middleTime.contains("次日")) {
        middleTime = middleTime.replaceAll("次日", "");
        String s = middleTime.substring(0, 10);
        String e = middleTime.substring(11);
        //开始日期加一天
        LocalDate date = LocalDate.parse(s).plusDays(1L);
        middleTime = date.toString() + " " + e;
      }
      try {
        now = df.parse(middleTime);
        beginTime = df.parse(startTime);
        endTime1 = df.parse(endTime);
      } catch (Exception e) {
        e.printStackTrace();
      }
      long start = beginTime.getTime();
      long middle = now.getTime();
      long end = endTime1.getTime();
      if ((middle - start >= 0) && (end - middle >= 0)) {
        return true;
      }
    }
    return false;
  }

  /**
  * @Title: compare_date 
  * @Description:比较两个日期的大小
  * @author 王共亮
  * @date 2020年1月10日下午3:43:35
   */
  public static int compare_date(String DATE1, String DATE2) {
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    try {
      Date dt1 = df.parse(DATE1);
      Date dt2 = df.parse(DATE2);
      if (dt1.getTime() > dt2.getTime()) {
        return -1;
      } else if (dt1.getTime() < dt2.getTime()) {
        return 1;
      } else {
        return 0;
      }
    } catch (Exception exception) {
      exception.printStackTrace();
    }
    return 0;
  }

  public static int compare_dateTime(String DATE1, String DATE2) {
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    try {
      Date dt1 = df.parse(DATE1);
      Date dt2 = df.parse(DATE2);
      if (dt1.getTime() > dt2.getTime()) {
        return -1;
      } else if (dt1.getTime() < dt2.getTime()) {
        return 1;
      } else {
        return 0;
      }
    } catch (Exception exception) {
      exception.printStackTrace();
    }
    return 0;
  }

  public static int compare_Time(String DATE1, String DATE2) {
    DateFormat df = new SimpleDateFormat("HH:mm");
    try {
      Date dt1 = df.parse(DATE1);
      Date dt2 = df.parse(DATE2);
      if (dt1.getTime() > dt2.getTime()) {
        return -1;
      } else if (dt1.getTime() < dt2.getTime()) {
        return 1;
      } else {
        return 0;
      }
    } catch (Exception exception) {
      exception.printStackTrace();
    }
    return 0;
  }

}
