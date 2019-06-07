/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.mapper.member;

import java.util.List;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.sys.entity.member.MemberAddress;

/**
 * 会员地址MAPPER接口
 * @author xudemo
 * @version 2018-02-24
 */
@MyBatisMapper
public interface MemberAddressMapper extends BaseMapper<MemberAddress> {
	void updateIsFirst(MemberAddress memberAddress);
	MemberAddress findNotAddress(MemberAddress memberAddress);
	/**
	 * 获取我的默认地址
	 */
	MemberAddress getTopAddress(MemberAddress memberAddress);
}