package com.ctgtmo.sshr.repository;

import java.util.List;

import com.ctgtmo.sshr.model.response.EmployResponse;

/**  
 * @Title: EmployDao.java   
 * @Company: 北京易才博普奥管理顾问有限公司
 * @Package: com.ctgtmo.sshr.repository   
 * @Description:员工服务
 * @author: 王共亮     
 * @date: 2020年6月2日 上午9:47:06   
 */
public interface EmployDao {
  /** 
  * @Title: queryEmployList 
  * @Description: 根据公司主键查询员工
  * @param companyId公司主键
  * @return List<Map<String,Object>> 
  * @author 王共亮
  * @date 2020年6月2日上午9:48:46
  */
  List<EmployResponse> queryEmployList(int companyId);
}
