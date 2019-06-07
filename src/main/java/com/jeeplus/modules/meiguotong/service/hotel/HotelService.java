/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.service.hotel;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.meiguotong.entity.hotel.Hotel;
import com.jeeplus.modules.meiguotong.mapper.hotel.HotelMapper;

/**
 * 酒店管理Service
 * @author PSZ
 * @version 2018-08-20
 */
@Service
@Transactional(readOnly = true)
public class HotelService extends CrudService<HotelMapper, Hotel> {

	public Hotel get(String id) {
		return super.get(id);
	}
	
	public List<Hotel> findList(Hotel hotel) {
		return super.findList(hotel);
	}
	/**
	 * 根据城市及搜索条件获取酒店
	 * @param page
	 * @param hotel
	 * @return
	 */
	public Page<Hotel> getHotelByCity(Page<Hotel> page, Hotel hotel) {
		dataRuleFilter(hotel);
		hotel.setPage(page);
		page.setList(mapper.getHotelByCity(hotel));
		return page;
	}
	/**
	 * 根据语言件获取酒店
	 * @param hotel
	 * @return
	 */
	public Page<Hotel> getHotelByLanguage(Page<Hotel> page, Hotel hotel) {
		dataRuleFilter(hotel);
		hotel.setPage(page);
		page.setList(mapper.getHotelByLanguage(hotel));
		return page;
	}
	
	public Page<Hotel> findPage(Page<Hotel> page, Hotel hotel) {
		return super.findPage(page, hotel);
	}
	
	@Transactional(readOnly = false)
	public void save(Hotel hotel) {
		if (hotel.getIsNewRecord()){
			hotel.preInsert();
			mapper.insert(hotel);
			//生成编号
			hotel.setHotelNo("JD-"+hotel.getAgentid()+'-'+hotel.getId());
			mapper.updateHotelNo(hotel);
		}else{
			hotel.setStatus(1);
			hotel.preUpdate();
			 mapper.update(hotel);
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(Hotel hotel) {
		super.delete(hotel);
	}
	
}