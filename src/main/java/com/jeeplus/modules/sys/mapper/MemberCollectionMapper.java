/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.mapper;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.sys.entity.MemberCollection;
import com.jeeplus.modules.sys.entity.MemberDigg;

/**
 * 个人收藏MAPPER接口
 * @author xudemo
 * @version 2018-02-27
 */
@MyBatisMapper
public interface MemberCollectionMapper extends BaseMapper<MemberCollection> {
	MemberCollection findOne(MemberCollection memberCollection);
	/**
	 * 判断用户是否已收藏
	 * @param memberCollection
	 * @return
	 */
	public Integer getCount(MemberCollection memberCollection);
}