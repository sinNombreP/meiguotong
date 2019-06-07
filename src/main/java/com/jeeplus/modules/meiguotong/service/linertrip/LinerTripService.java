/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.service.linertrip;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.meiguotong.entity.linertrip.LinerTrip;
import com.jeeplus.modules.meiguotong.mapper.linertrip.LinerTripMapper;

/**
 * 行程表Service
 * @author cdq
 * @version 2018-08-14
 */
@Service
@Transactional(readOnly = true)
public class LinerTripService extends CrudService<LinerTripMapper, LinerTrip> {

	@Autowired
	private LinerTripMapper linerTripMapper;
	
	public LinerTrip get(String id) {
		return super.get(id);
	}
	
	public List<LinerTrip> findList(LinerTrip linerTrip) {
		return super.findList(linerTrip);
	}
	
	public Page<LinerTrip> findPage(Page<LinerTrip> page, LinerTrip linerTrip) {
		return super.findPage(page, linerTrip);
	}
	
	@Transactional(readOnly = false)
	public void save(LinerTrip linerTrip) {
		super.save(linerTrip);
	}
	
	@Transactional(readOnly = false)
	public void delete(LinerTrip linerTrip) {
		super.delete(linerTrip);
	}
  /**
   *查找行程内容数据
   * @param linerTrip1
   * @return
   */
	public List<LinerTrip> getLinerTrip(LinerTrip linerTrip1) {
		return mapper.getLinerTrip(linerTrip1);
	}
	
	/**
	 * 邮轮航线行程信息
	 * @param linerTrip
	 * @return
	 */
	public List<LinerTrip> getTripList(LinerTrip linerTrip){
		return mapper.getTripList(linerTrip);
	};
	
  /**
   * 邮轮行程
   * @param linerTrip
   * @return
   */
	public List<LinerTrip> findTrip(LinerTrip linerTrip) {
		return mapper.findTrip(linerTrip);
	}
	
	/*获取邮轮行程信息*/
	public  List<LinerTrip> findTripByLinerLineId(Integer linnerlineid){
		return linerTripMapper.findTripByLinerLineId(linnerlineid);
	};
}