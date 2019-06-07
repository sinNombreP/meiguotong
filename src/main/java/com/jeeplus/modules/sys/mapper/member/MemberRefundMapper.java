/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.mapper.member;

import java.util.List;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.sys.entity.member.MemberRefund;

/**
 * 退款设置表MAPPER接口
 * @author psz
 * @version 2018-08-09
 */
@MyBatisMapper
public interface MemberRefundMapper extends BaseMapper<MemberRefund> {

	/** 
	* @Title: findListByAgentid 
	* @Description: 根据供应商ID查询供应商退款说明
	* @author 彭善智
	* @date 2018年8月9日下午4:37:59
	*/ 
	List<MemberRefund> findListByAgentid(MemberRefund memberRefund);

	/** 
	* @Title: deleteByTypeid 
	* @Description: 根据typeid删除退款说明
	* @author 彭善智
	* @date 2018年8月12日上午12:28:25
	*/ 
	void deleteByTypeid(MemberRefund memberRefund);
	
	/**
	 * 获取导游退款信息（接口）
	 * @param memberRefund
	 * @return
	 */
	public List<MemberRefund> getGuideRefund(MemberRefund memberRefund);
	/**
	 * 获取退款说明
	 * @param memberRefund
	 * @return
	 */
	public List<MemberRefund> getRefundInfor(MemberRefund memberRefund);
	/**
	 * 根据导游id的删除退款说明
	 * @param typeid
	 */
	public void deleteGuideRefund(Integer typeid);
	
	public List<MemberRefund> findMemberRefund(MemberRefund memberRefund);
}