/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.mapper.comnavigation;

import java.util.List;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.meiguotong.entity.comnavigation.ComNavigation;

/**
 * 主导航MAPPER接口
 * @author cdq
 * @version 2018-07-27
 */
@MyBatisMapper
public interface ComNavigationMapper extends BaseMapper<ComNavigation> {

	ComNavigation findNumber(ComNavigation comNavigation);
   /**
    * 热门城市的列表
    * @param comNavigation
    * @return
    */
	List<ComNavigation> findHotCityList(ComNavigation comNavigation);
	/**
	 * 获取主导航列表接口
	 * @param comNavigation
	 * @return
	 */
    List<ComNavigation> findNavigationList(ComNavigation comNavigation);
    /**
	 * 根据语言获取热门目的地
	 * @param comNavigation
	 * @return
	 */
	public List<ComNavigation> getHotCity(ComNavigation comNavigation);
	 /**
	   * @method: getComNavigation
	   * @Description:  获取首页导航栏
	   * @Author:   彭善智
	   * @Date:     2019/1/4 14:44
	   */
	List<ComNavigation> getComNavigation(ComNavigation comNavigation);
}