/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.mapper.comconsult;

import java.util.List;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.meiguotong.entity.comconsult.ComConsult;

/**
 * 用户咨询MAPPER接口
 * @author psz
 * @version 2018-08-16
 */
@MyBatisMapper
public interface ComConsultMapper extends BaseMapper<ComConsult> {

	/** 
	* @Title: updateStatus 
	* @Description: 启用禁用
	* @author 彭善智
	* @date 2018年8月16日下午5:48:16
	*/ 
	void updateStatus(ComConsult comConsult);
    /**
     * 用户资讯列表接口
     * @param comConsult
     * @return
     */
	List<ComConsult> findComConsultList(ComConsult comConsult);
	/**
	 * 根据类型获取产品咨询
	 * @param comConsult
	 * @return
	 */
	public List<ComConsult> getRouteConsult(ComConsult comConsult);
	
}