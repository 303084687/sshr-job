package com.ctgtmo.sshr.mapper;

/**  
 * @Title: EmployMapper.java   
 * @Company: 北京易才博普奥管理顾问有限公司
 * @Package: com.ctgtmo.sshr.mapper   
 * @Description:员工服务sql
 * @author: 王共亮     
 * @date: 2020年6月2日 上午10:28:37   
 */
public class EmployMapper {

  //@Fields QUERY_EMPLOY_COMPANY:根据公司主键查询所有在职员工
  public static final String QUERY_EMPLOY_COMPANY = "SELECT employ_id,org_id FROM employ WHERE state IN(2,3,4) AND company_id=:companyId";
}
