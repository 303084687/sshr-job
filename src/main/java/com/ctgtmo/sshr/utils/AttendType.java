package com.ctgtmo.sshr.utils;

/**  
 * @Title: AttendType.java   
 * @Company: 北京易才博普奥管理顾问有限公司
 * @Package: com.ctgtmo.sshr.utils   
 * @Description:考勤组类型type
 * @author: 王共亮     
 * @date: 2020年6月3日 下午3:28:50   
 */
public enum AttendType {
  GROUP_FIXED_TYPE("fixed", 1), GROUP_SCHEDUL_TYPE("schedul", 2), GROUP_FREE_TYPE("free", 3);

  // 成员变量  
  private String name;

  private int index;

  // 构造方法  
  private AttendType(String name, int index) {
    this.name = name;
    this.index = index;
  }

  //覆盖方法  
  @Override
  public String toString() {
    return this.index + "_" + this.name;
  }

  public static AttendType fromAttendType(int attendType) {
    for (AttendType type : AttendType.values()) {
      if (type.getIndex() == attendType) {
        return type;
      }
    }
    return null;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getIndex() {
    return index;
  }

  public void setIndex(int index) {
    this.index = index;
  }

}
