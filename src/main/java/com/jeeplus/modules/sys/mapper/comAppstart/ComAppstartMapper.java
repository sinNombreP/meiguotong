/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.mapper.comAppstart;

import org.apache.ibatis.annotations.Param;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.sys.entity.comAppstart.ComAppstart;
import com.jeeplus.modules.sys.entity.comVersion.ComVersion;

/**
 * APP启动界面MAPPER接口
 * @author laiyanxin
 * @version 2018-03-05
 */
@MyBatisMapper
public interface ComAppstartMapper extends BaseMapper<ComAppstart> {

	
	ComVersion findVersion(@Param("version")String version, @Param("source")Integer source);

	ComVersion findNewVersion(Integer source);

	
}