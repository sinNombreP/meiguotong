/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.service.travel;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.meiguotong.entity.scenicspot.ScenicSpot;
import com.jeeplus.modules.meiguotong.entity.travel.TravelDetails;
import com.jeeplus.modules.meiguotong.mapper.scenicspot.ScenicSpotMapper;
import com.jeeplus.modules.meiguotong.mapper.travel.TravelDetailsMapper;
import com.jeeplus.modules.meiguotong.service.scenicspot.ScenicSpotService;

/**
 * 旅游定制详情Service
 * @author psz
 * @version 2018-08-27
 */
@Service
@Transactional(readOnly = true)
public class TravelDetailsService extends CrudService<TravelDetailsMapper, TravelDetails> {
    @Autowired
	private ScenicSpotMapper scenicSpotMapper;
    
	public TravelDetails get(String id) {
		return super.get(id);
	}
	
	public List<TravelDetails> findList(TravelDetails travelDetails) {
		return super.findList(travelDetails);
	}
	
	public Page<TravelDetails> findPage(Page<TravelDetails> page, TravelDetails travelDetails) {
		return super.findPage(page, travelDetails);
	}
	
	@Transactional(readOnly = false)
	public void save(TravelDetails travelDetails) {
		super.save(travelDetails);
	}
	
	@Transactional(readOnly = false)
	public void delete(TravelDetails travelDetails) {
		super.delete(travelDetails);
	}

	/** 
	* @Title: getDataBYTravelId 
	* @Description: 根据定制ID查询详情
	* @author 彭善智
	* @date 2018年8月28日上午11:28:48
	*/ 
	public List<TravelDetails> getDataByTravelid(String travelid) {
		List<TravelDetails> list= mapper.getDataByTravelid(travelid);
		for(TravelDetails t : list) {
			t.setTravelDetailsList(mapper.getDateByDay(t));
		}
		return list;
	}
	/**
	 * 获取旅游定制的详细信息
	 * @param travelid
	 * @return
	 */
	public List<TravelDetails> getTravelDetails(String travelid) {
		List<TravelDetails> list= mapper.getTravelDetails(travelid);
		for(TravelDetails a:list){
			List<TravelDetails> list1 = a.getTravelDetails();
			for(TravelDetails b:list1){
				List<ScenicSpot> scenicSpotList = new ArrayList<>();
				String scenic = b.getScenic();
				if(StringUtils.isNotBlank(scenic)){
					String[] scenics = scenic.split(",");
					for(String c:scenics){
						ScenicSpot scenicSpot = scenicSpotMapper.getScenicSpotName(c);
						scenicSpotList.add(scenicSpot);
					}
				}
				b.setScenicSpot(scenicSpotList);
			}
		}
		return list;
	}
}