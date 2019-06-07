/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.service.travel;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.meiguotong.entity.travel.TravelCustomization;
import com.jeeplus.modules.meiguotong.entity.travel.TravelDetails;
import com.jeeplus.modules.meiguotong.mapper.travel.TravelCustomizationMapper;
import com.jeeplus.modules.meiguotong.mapper.travel.TravelDetailsMapper;

/**
 * 旅游定制Service
 * @author psz
 * @version 2018-08-27
 */
@Service
@Transactional(readOnly = true)
public class TravelCustomizationService extends CrudService<TravelCustomizationMapper, TravelCustomization> {

	@Autowired
	private TravelDetailsMapper travelDetailsMapper;
	@Autowired
	private TravelCustomizationMapper travelCustomizationMapper;
	
	public TravelCustomization get(String id) {
		return super.get(id);
	}
	/**
	 *  获取后台配置的旅游定制的天数列表
	 * @param travelCustomization
	 * @return
	 */
	public List<Integer> getTravelDay(TravelCustomization travelCustomization) {
		return mapper.getTravelDay(travelCustomization);
	}
	public List<TravelCustomization> findList(TravelCustomization travelCustomization) {
		return super.findList(travelCustomization);
	}
	
	/**
	 * 根据旅游天数获取后台旅游定制列表
	 * @param page
	 * @param travelCustomization
	 * @return
	 */
	public Page<TravelCustomization> getTravelByDay(Page<TravelCustomization> page, TravelCustomization travelCustomization) {
		dataRuleFilter(travelCustomization);
		travelCustomization.setPage(page);
		page.setList(mapper.getTravelByDay(travelCustomization));
		return page;
	}
	public Page<TravelCustomization> findPage(Page<TravelCustomization> page, TravelCustomization travelCustomization) {
		return super.findPage(page, travelCustomization);
	}
	
	@Transactional(readOnly = false)
	public void save(TravelCustomization travelCustomization) {
		super.save(travelCustomization);
	}
	
	@Transactional(readOnly = false)
	public void delete(TravelCustomization travelCustomization) {
		super.delete(travelCustomization);
	}

	/** 
	* @Title: updateAdd 
	* @Description: 保存
	* @author 彭善智
	* @date 2018年8月29日下午2:46:12
	*/ 
	@Transactional(readOnly = false)
	public void updateAdd(TravelCustomization travelCustomization) {
		int scenicNum = 0;
		int cityNum = 0;
		String name = "";
		for(TravelDetails k : travelCustomization.getTravelDetailsList()) {
			for(TravelDetails t :k.getTravelDetailsList()) {
				cityNum ++;
				scenicNum += t.getScenic().split(",").length;
				if(StringUtils.isNotBlank(t.getCityName())) {
					name += t.getCityName()+",";
				}
				
			}
		}
		travelCustomization.setScenicNum(scenicNum);
		travelCustomization.setCityNum(cityNum);
		travelCustomization.setName(name.substring(0, name.length()-1)+travelCustomization.getTravelDetailsList().size()+"日游,("+travelCustomization.getCityName()+"出发)");
		if (travelCustomization.getIsNewRecord()){
			travelCustomization.preInsert();
			mapper.insert(travelCustomization);
		}else{
			travelCustomization.preUpdate();
			 mapper.update(travelCustomization);
		}
		travelDetailsMapper.deleteByTravelCustomizationId(travelCustomization);
		for(TravelDetails k : travelCustomization.getTravelDetailsList()) {
			int sort = 0;
			for(TravelDetails t :k.getTravelDetailsList()) {
				t.setSort(sort);
				t.setDay(k.getDay());
				t.setTravelid(Integer.parseInt(travelCustomization.getId()));
				travelDetailsMapper.insert(t);
				sort ++;
			}
		}
	}
	/*根据城市id获取定制列表*/
	public List<TravelCustomization> findTravelByCityId(TravelCustomization travelCustomization){
		return travelCustomizationMapper.findTravelByCityId(travelCustomization);
	}
	
	/*根据城市旅游定制获取定制列表*/
	public List<TravelCustomization> findTravelByComCityTravels(String travelid){
		return travelCustomizationMapper.findTravelByComCityTravels(travelid);
	}
}