/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.mapper.score;

import java.util.List;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.sys.entity.score.SorceLog;

/**
 * 积分管理MAPPER接口
 * @author psz
 * @version 2018-03-07
 */
@MyBatisMapper
public interface SorceLogMapper extends BaseMapper<SorceLog> {
	/**
	 * 获取个人使用积分数量
	 * @param memberid
	 * @return
	 */
	Integer getMyUseScore(String memberid);
	/**
	 * 获取个人积分
	 * @param sorceLog
	 * @return
	 */
	Integer getMyScore(SorceLog sorceLog);
	
	
	public List<SorceLog> findSorceLogList(SorceLog sorceLog);
}