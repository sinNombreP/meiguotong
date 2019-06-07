/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.mapper.scenicspot;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.meiguotong.entity.product.Route;
import com.jeeplus.modules.meiguotong.entity.scenicspot.ScenicSpot;
import com.jeeplus.modules.meiguotong.entity.travel.OrderTravelDetails;
import com.jeeplus.modules.sys.entity.sysUser.SysUser;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * 景点表MAPPER接口
 * @author cdq
 * @version 2018-08-16
 */
@MyBatisMapper
public interface ScenicSpotMapper extends BaseMapper<ScenicSpot> {
  /**
   * 修改状态
   * @param scenicSpot
   */
	void status(ScenicSpot scenicSpot);

	/** 
	* @Title: getTripScenic 
	* @Description: 获取景点
	* @author 彭善智
	 * @return 
	* @date 2018年8月24日上午10:16:00
	*/ 
	List<ScenicSpot> getTripScenic(ScenicSpot scenicSpot);
	/**
	 * 获取路线途经景点数据
	 * @param scenicSpot
	 * @return
	 */
	public List<ScenicSpot> getRouteScenic(ScenicSpot scenicSpot);
	
	/**
	 * 根据城市获取景点
	 * @param scenicSpot
	 * @return
	 */
	public List<ScenicSpot> getCityScenic(ScenicSpot scenicSpot);
	/**
	 * 景点名称关键字匹配搜索列表
	 * @param scenicSpot
	 * @return
	 */
	public List<ScenicSpot> selectScenicTitle(ScenicSpot scenicSpot);
	
	/**
	 * 景点列表搜索
	 * @param scenicSpot
	 * @return
	 */
	public List<ScenicSpot> selectScenicList(ScenicSpot scenicSpot);
	/**
	 * 景点详情接口
	 * @param id
	 * @return
	 */
	public ScenicSpot getScenicDetails(ScenicSpot scenicSpot);
	/**
	 * 获取景点名称，id
	 * @param id
	 * @return
	 */
	public ScenicSpot getScenicSpotName(String id);
	
	/**
	 * 定制景点关键字搜索
	 * @param scenicSpot
	 * @return
	 */
	public List<ScenicSpot> getScenicByTitle(ScenicSpot scenicSpot);
	/**
	 * 定制景点介绍
	 * @param scenicSpot
	 * @return
	 */
	public ScenicSpot getScenic(ScenicSpot scenicSpot);

	/**
	* @Title: getCitySpot
	* @Description: 获取城市景点
	* @author  彭善智
	* @Data 2018年11月13日上午11:57:10
	*/
	List<ScenicSpot> getCitySpot(ScenicSpot scenicSpot);

	/**
	* @Title: getScenicSpotList
	* @Description: 获取景点列表
	* @author  彭善智
	* @Data 2018年12月5日上午11:33:53
	*/
	List<ScenicSpot> getScenicSpotList(ScenicSpot scenicSpot);

	 /**
	   * @method: findListByCityId
	   * @Description:  获取景点列表
	   * @Author:   彭善智
	   * @Date:     2018/12/28 10:43
	   */
	List<ScenicSpot> findListByCityId(ScenicSpot scenicSpot);
	
	/**
	 * 获取所有景点供应商
	 * @return
	 */
	public List<SysUser> getUser(SysUser sysUser);
	
	/**
	 * 配置景点供应商
	 * @return
	 */
	public void updateUser(ScenicSpot scenicSpot);
	/**
	 * 根据语言获取景点
	 * @param scenicSpot
	 * @return
	 */
	public List<ScenicSpot> getScenicSpot(ScenicSpot scenicSpot);
	
	
	/**
	 * 根据景点名称模糊查询景点
	 * @param scenicSpot
	 * @return
	 */
	public List<ScenicSpot> findScenicsByName(ScenicSpot scenicSpot);
	
	/**
	 * 根据景点id查询景点
	 * @param scenics
	 * @return
	 */
	public List<ScenicSpot> findScenicsByscenicTop(@Param("scenicTop")String scenics);
	
	/**
	 * 根据景点id查询景点并排序
	 * @param scenics
	 * @return
	 */
	public List<ScenicSpot> findScenicsByscenicTop2(Map<String, Object> param);
	
	/*根据定制ID和城市id查询景点*/
	public List<ScenicSpot> findScenicByCityId(OrderTravelDetails oTravelDetails);
	/**
	 * 获取常规路线/当地参团的途径景点
	 * @param route
	 * @return
	 */
	public List<ScenicSpot> getScenicSpotRoute(Route route);
}