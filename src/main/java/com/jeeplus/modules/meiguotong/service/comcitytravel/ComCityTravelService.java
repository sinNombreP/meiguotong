/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.service.comcitytravel;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.meiguotong.entity.comcitytravel.ComCityTravel;
import com.jeeplus.modules.meiguotong.mapper.comcitytravel.ComCityTravelMapper;

/**
 * 目的地关联旅游定制Service
 * @author dong
 * @version 2019-03-22
 */
@Service
@Transactional(readOnly = true)
public class ComCityTravelService extends CrudService<ComCityTravelMapper, ComCityTravel> {

	@Autowired
	private ComCityTravelMapper comCityTravelMapper;
	
	public ComCityTravel get(String id) {
		return super.get(id);
	}
	
	public List<ComCityTravel> findList(ComCityTravel comCityTravel) {
		return super.findList(comCityTravel);
	}
	
	public Page<ComCityTravel> findPage(Page<ComCityTravel> page, ComCityTravel comCityTravel) {
		return super.findPage(page, comCityTravel);
	}
	
	@Transactional(readOnly = false)
	public void save(ComCityTravel comCityTravel) {
		super.save(comCityTravel);
	}
	
	@Transactional(readOnly = false)
	public void delete(ComCityTravel comCityTravel) {
		super.delete(comCityTravel);
	}
	
	/*根据城市ID删除定制*/
	public void deleteComCityTravelByCityId(Integer cityid){
		comCityTravelMapper.deleteComCityTravelByCityId(cityid);
	}
	/*根据城市ID查询定制id*/
	public List<ComCityTravel> findComCityTravelByCityId(Integer cityid){
		return comCityTravelMapper.findComCityTravelByCityId(cityid);
	}
	/*根据城市ID查询定制列表*/
	public List<ComCityTravel> findComCityTravelByCityId2(Integer cityid){
		return comCityTravelMapper.findComCityTravelByCityId2(cityid);
	}
	/*根据城市ID查询定制列表*/
	public List<ComCityTravel> findComCityTravelByCityId3(Integer cityid){
		return comCityTravelMapper.findComCityTravelByCityId3(cityid);
	}
}