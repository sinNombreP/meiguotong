/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.mapper.guide;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.meiguotong.entity.comtag.ComTag;
import com.jeeplus.modules.meiguotong.entity.guide.Guide;

/**
 * 导游表MAPPER接口
 * @author cdq
 * @version 2018-08-20
 */
/**
 * @author LG
 *
 */
@MyBatisMapper
public interface GuideMapper extends BaseMapper<Guide> {
   //更改状态
	void status(Guide guide);
   //获取抽成比例
	Guide findDiscount();

	/**
	 * 导游筛选接口
	 * @param guide
	 * @return
	 */
	List<Guide> findGuideScreenList(Guide guide);
	
	/**
	 * 获取玩家认证信息接口
	 * @param memberid
	 * @return
	 */
	public Guide getGuide(String memberid);
	/**
	 * 接口获取导游详情
	 * @param memberid
	 * @return
	 */
	public Guide getGuideInfo(Guide guide);

	/**
	 * 根据memberid获取导游信息
	 * @param memberid
	 * @return
	 */
	public Guide findGuide(String memberid);
	/**
	 * 根据搜索条件获取导游列表
	 * @param guide
	 * @return
	 */
	public List<Guide> getGuideByCity(Guide guide);
	/**
	* @Title: getGuideList
	* @Description: 获取导游列表
	* @author  彭善智
	* @Data 2018年12月5日下午2:48:52
	*/
	List<Guide> getGuideList(Guide guide);
	/**
	 * 根据搜索条件模糊搜索导游列表
	 * @param guide
	 * @return
	 */
	List<Guide> findguidesByName(Guide guide);
	
	//根据当地玩家ID获取玩家
	List<Guide> findguidesByPlayerId(@Param("playerTop") String guides);
	
	//获取当地玩家列表
	List<Guide> getPlayerList(Map<String, Object> param);
	
	//获取导游列表
	List<Guide> findGuideList();
	
	//购物车获取导游信息
	public Guide productCar_findGuide(@Param("id") Integer id);
}