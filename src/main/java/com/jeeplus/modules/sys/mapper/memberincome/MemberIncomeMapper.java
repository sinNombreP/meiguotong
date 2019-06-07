/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.mapper.memberincome;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.sys.entity.memberincome.MemberIncome;

/**
 * 直客流水记录MAPPER接口
 * @author dong
 * @version 2019-04-09
 */
@MyBatisMapper
public interface MemberIncomeMapper extends BaseMapper<MemberIncome> {
	/**
	 * 帐户流水接口数据
	 * @param memberIncome
	 * @return
	 */
	public List<MemberIncome> findMemberIncomePage(MemberIncome memberIncome);
	
	/*获取会员提现流水*/
	public List<MemberIncome> findTiXianList(MemberIncome memberIncome);
	
	/*查询直客流水*/
	List<MemberIncome> findMemberIncomeZhiKeList(MemberIncome memberIncome);
	
	/*获取会员提现详情*/
	public MemberIncome findMemberIncomeTiXianById(@Param("id")Integer id);
}