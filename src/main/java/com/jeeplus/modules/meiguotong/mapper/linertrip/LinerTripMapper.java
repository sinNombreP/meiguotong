/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.mapper.linertrip;

import java.util.List;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.meiguotong.entity.linerroom.LinerRoom;
import com.jeeplus.modules.meiguotong.entity.linertrip.LinerTrip;

/**
 * 行程表MAPPER接口
 * @author cdq
 * @version 2018-08-14
 */
@MyBatisMapper
public interface LinerTripMapper extends BaseMapper<LinerTrip> {
    /**
     * 修改行程数据
     * @param linerTrip
     */
	void uPdate(LinerTrip linerTrip);
   /**
    * 查找行程数据
    */
	List<LinerTrip> getLinerTrip(LinerTrip linerTrip1);
	/**
	 * 查找行程列表信息接口
	 * @param linertrip
	 * @return
	 */
    List<LinerTrip> findTripList(LinerTrip linertrip);
    /**
     * 邮轮行程
     * @param linerTrip
     * @return
     */
	List<LinerTrip> findTrip(LinerTrip linerTrip);
	
	/**
	 * 邮轮航线行程信息
	 * @param linerTrip
	 * @return
	 */
	public List<LinerTrip> getTripList(LinerTrip linerTrip);

	/**
	 * 删除游轮路线行程
	 * @param linerTrip
	 */
	public void deleteByLinerLineId(LinerTrip linerTrip);
	
	/*获取邮轮行程信息*/
	List<LinerTrip> findTripByLinerLineId(Integer linnerlineid);
}