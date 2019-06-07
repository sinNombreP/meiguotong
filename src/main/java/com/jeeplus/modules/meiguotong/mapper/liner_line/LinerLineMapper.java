/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.mapper.liner_line;

import java.util.List;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.meiguotong.entity.comcity.ComCity;
import com.jeeplus.modules.meiguotong.entity.liner_line.LinerLine;

/**
 * 邮轮航线管理MAPPER接口
 * @author cdq
 * @version 2018-08-13
 */
@MyBatisMapper
public interface LinerLineMapper extends BaseMapper<LinerLine> {
   /**
    * 修改状态
    * @param linerLine
    */
	void status(LinerLine linerLine);
 
/**
 * 搜索邮轮接口
 * @param linerline
 * @return
 */
List<LinerLine> findCruiseList(LinerLine linerline);
/**
 * 邮轮筛选接口
 * @param linerline
 * @return
 */
List<LinerLine> findCruiseScreenList(LinerLine linerline);

	/**
	 * 获取游轮出发港口
	 * @param getlinerLine
	 * @return
	 */
	public List<ComCity> getStartCity(LinerLine linerLine);
	/**
	 * 接口获取游轮路线信息
	 * @param linerline
	 * @return
	 */
	public LinerLine getLinerline(LinerLine linerline);

	/**
	 * 修改游轮路线编号
	 * @param linerline
	 */
	public void updateLineNo(LinerLine linerline);
	
}