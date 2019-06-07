/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.mapper.member;

import java.util.List;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.sys.entity.member.MemberDiscount;

/**
 * 会员优惠MAPPER接口
 * @author psz
 * @version 2018-08-08
 */
@MyBatisMapper
public interface MemberDiscountMapper extends BaseMapper<MemberDiscount> {
	/**
	 * 获取账号优惠信息
	 * @param memberid
	 * @return
	 */
	public List<MemberDiscount> getDiscountInfo(String memberid);
}