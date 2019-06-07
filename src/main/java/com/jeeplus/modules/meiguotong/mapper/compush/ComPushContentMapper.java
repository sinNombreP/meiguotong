/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.mapper.compush;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.meiguotong.entity.compush.ComPushContent;

/**
 * 自动推送模板MAPPER接口
 * @author dong
 * @version 2019-03-13
 */
@MyBatisMapper
public interface ComPushContentMapper extends BaseMapper<ComPushContent> {
	
	List<ComPushContent> findComPushContentByLanguageid(@Param("languageid") Integer id);
}