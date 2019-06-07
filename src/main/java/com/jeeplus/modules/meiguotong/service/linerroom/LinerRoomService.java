/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.service.linerroom;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.meiguotong.entity.insurance.InsuranceRelationMod;
import com.jeeplus.modules.meiguotong.entity.linerroom.LinerRoom;
import com.jeeplus.modules.meiguotong.mapper.linerroom.LinerRoomMapper;

/**
 * 邮轮房间表Service
 * @author cdq
 * @version 2018-08-14
 */
@Service
@Transactional(readOnly = true)
public class LinerRoomService extends CrudService<LinerRoomMapper, LinerRoom> {

	public LinerRoom get(String id) {
		return super.get(id);
	}
	/**
	 * 获取邮轮房间列表
	 * @param linerRoom
	 * @return
	 */
	public List<LinerRoom> linerRoomList(LinerRoom linerRoom) {
		return mapper.linerRoomList(linerRoom);
	}
	public List<LinerRoom> findList(LinerRoom linerRoom) {
		return super.findList(linerRoom);
	}
	
	public Page<LinerRoom> findPage(Page<LinerRoom> page, LinerRoom linerRoom) {
		return super.findPage(page, linerRoom);
	}
	
	@Transactional(readOnly = false)
	public void save(LinerRoom linerRoom) {
		super.save(linerRoom);
	}
	
	@Transactional(readOnly = false)
	public void delete(LinerRoom linerRoom) {
		super.delete(linerRoom);
	}
    /**
     * 查找房间的数据
     * @param id
     * @return
     */
	public List<LinerRoom> getLinerRoomList(LinerRoom linerRoom2) {
		return mapper.getLinerRoomList(linerRoom2);
	}
/**
 * 邮轮房间列表接口
 * @param page
 * @param linerRoom
 * @return
 */
	public Page<LinerRoom> findLinerRoomList(Page<LinerRoom> page, LinerRoom linerRoom) {
		dataRuleFilter(linerRoom);
		linerRoom.setPage(page);
		page.setList(mapper.findLinerRoomList(linerRoom));
		return page;
	}

	//购物车获取邮轮房间信息
	public List<LinerRoom> productCar_findLinerRoom(String id){
		return mapper.productCar_findLinerRoom(id);
	};
	
}