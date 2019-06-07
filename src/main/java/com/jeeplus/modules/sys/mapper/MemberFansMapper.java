/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.mapper;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.sys.entity.MemberFans;

/**
 * 好友列表MAPPER接口
 * @author xudemo
 * @version 2018-03-08
 */
@MyBatisMapper
public interface MemberFansMapper extends BaseMapper<MemberFans> {
	/**
	 * 查询是否是关注
	 * @param memberFans
	 * @return
	 */
	MemberFans findIsFans(MemberFans memberFans);
}