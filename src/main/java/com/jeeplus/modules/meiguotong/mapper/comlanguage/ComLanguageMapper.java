/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.mapper.comlanguage;

import java.util.List;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.meiguotong.entity.comlanguage.ComLanguage;

/**
 * 语言表MAPPER接口
 * @author cdq
 * @version 2018-07-27
 */
@MyBatisMapper
public interface ComLanguageMapper extends BaseMapper<ComLanguage> {
   //查找语言
	List<ComLanguage> findLanguage();
    //更改状体
	void status(ComLanguage comLanguage);
	/**
	 * 获取语言列表接口
	 * @param comLanguage
	 * @return
	 */
	List<ComLanguage> findLanguageList(ComLanguage comLanguage);
	/**
	 * 获取语言接口
	 * @return
	 */
	public List<ComLanguage> getLanguage();
	
}