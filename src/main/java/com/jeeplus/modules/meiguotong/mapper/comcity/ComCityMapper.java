/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.mapper.comcity;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.meiguotong.entity.comcity.ComCity;

/**
 * 城市表MAPPER接口
 * @author cdq
 * @version 2018-08-02
 */
@MyBatisMapper
public interface ComCityMapper extends BaseMapper<ComCity> {
     /**
      * 修改状态
      * @param comCity
      */
	void status(ComCity comCity);

	void insert(List<ComCity> comCityList);

	/** 
	* @Title: getData 
	* @Description: 获取城市
	* @author 彭善智
	* @date 2018年8月24日下午9:26:30
	*/ 
	List<ComCity> getData(ComCity comCity);
	/**
	 * 根据语言获取城市
	 * @param comCity
	 * @return
	 */
	public List<ComCity> getCityList(ComCity comCity);
	/**
	 * 根据语言获取城市和城市景点数量
	 * @param comCity
	 * @return
	 */
	public List<ComCity> getCityScenicNum(ComCity comCity);
	/**
	 * 城市关键字搜索
	 * @param comCity
	 * @return
	 */
	public List<ComCity> getCityByTitle(ComCity comCity);
	/**
	 * 定制城市介绍信息
	 * @param comCity
	 * @return
	 */
	public ComCity getCity(ComCity comCity);

	/**
	* @Title: getNearbyCity
	* @Description: 获取附近城市
	* @author  彭善智
	* @Data 2018年11月13日上午10:39:13
	*/
	List<ComCity> getNearbyCity(String cityid);
	
	/**
	 * 景点详情接口
	 * @param id
	 * @return
	 */
	public ComCity getCityDetails(ComCity comCity);

	
	//根据语言ID，国家ID，查找城市
	List<ComCity> findCityBylanguageid(ComCity comCity);
	
	//根据附近城市ID，查找城市
	List<ComCity> findCitynearCityid(@Param("nearCityNumber")String nearCity);

}