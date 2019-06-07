/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.mapper.compush;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.meiguotong.entity.compush.ComPush;

/**
 * 推送管理MAPPER接口
 * @author dong
 * @version 2018-09-17
 */
@MyBatisMapper
public interface ComPushMapper extends BaseMapper<ComPush> {
	
	/**
	 *  推送管理_查询用户
	 */
	List<ComPush>  findnumber(ComPush compush);
	
	/**
	 * 保存被推送用户
	 */
	
	Integer insertPushPeople(ComPush compush);
	
	/**
	 * 查询所有用户id
	 */
	List<Integer> findAllUserId();
	
	/**
	 * 查询被推送用户
	 */
	List<ComPush> findComPushPeople(@Param("pushid") String id);
}