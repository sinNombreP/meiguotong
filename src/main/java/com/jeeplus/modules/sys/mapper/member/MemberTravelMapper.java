/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.mapper.member;

import java.util.List;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.sys.entity.member.Member;
import com.jeeplus.modules.sys.entity.member.MemberTravel;

/**
 * 旅行社MAPPER接口
 * @author psz
 * @version 2018-08-07
 */
@MyBatisMapper
public interface MemberTravelMapper extends BaseMapper<MemberTravel> {

	/** 
	* @Title: changeAudit 
	* @Description: 修改审核状态
	* @author 彭善智
	* @date 2018年8月7日下午8:24:11
	*/ 
	void changeAudit(MemberTravel memberTravel);

	/** 
	* @Title: getSon 
	* @Description: 获取子账号
	* @author 彭善智
	* @date 2018年8月8日上午11:16:52
	*/ 
	List<Member> getSon(MemberTravel memberTravel);
	
}