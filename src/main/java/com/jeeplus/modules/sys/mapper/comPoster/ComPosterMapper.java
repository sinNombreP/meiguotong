/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.mapper.comPoster;

import java.util.List;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.sys.entity.comPoster.ComPoster;

/**
 * 广告管理MAPPER接口
 * @author laiyanxin
 * @version 2018-03-06
 */
@MyBatisMapper
public interface ComPosterMapper extends BaseMapper<ComPoster> {

	List<ComPoster> findPosterByType(String positionType);
	
}