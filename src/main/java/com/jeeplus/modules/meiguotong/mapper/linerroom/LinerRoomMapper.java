/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.mapper.linerroom;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.meiguotong.entity.insurance.InsuranceRelationMod;
import com.jeeplus.modules.meiguotong.entity.liner.LinerDate;
import com.jeeplus.modules.meiguotong.entity.linerroom.LinerRoom;

/**
 * 邮轮房间表MAPPER接口
 * @author cdq
 * @version 2018-08-14
 */
@MyBatisMapper
public interface LinerRoomMapper extends BaseMapper<LinerRoom> {
  /**
   * 查找房间的数据
   * @param linerRoom2
   * @return
   */
	List<LinerRoom> getLinerRoomList(LinerRoom linerRoom2);
	/**
	 * 修改房间数据
	 * @param linerRoom
	 */
	void uPdate(LinerRoom linerRoom);
	/**
	 * 邮轮房间列表接口
	 * @param linerRoom
	 * @return
	 */
	List<LinerRoom> findLinerRoomList(LinerRoom linerRoom);
	/**
	 * 获取邮轮房间列表
	 * @param linerRoom
	 * @return
	 */
	public List<LinerRoom> linerRoomList(LinerRoom linerRoom);
	/**
	 * 删除游轮路线房间
	 * @param linerRoom
	 */
	public void deleteByLinerLineId(LinerRoom linerRoom);

	//购物车获取邮轮房间信息
	public List<LinerRoom> productCar_findLinerRoom(@Param("id")String id);
}