/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.mapper.common;

import java.util.List;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.sys.entity.common.ComTripartiteLogin;



/**
 * 三方登录MAPPER接口
 * @author xudemo
 * @version 2018-01-16
 */
@MyBatisMapper
public interface ComTripartiteLoginMapper extends BaseMapper<ComTripartiteLogin> {
	ComTripartiteLogin findAPI(ComTripartiteLogin comTripartiteLogin);
	
	void updateBang(ComTripartiteLogin comTripartiteLogin);

	List<ComTripartiteLogin> findByMember(ComTripartiteLogin comTripartiteLogin);
}