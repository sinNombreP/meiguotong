/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.service.comcity;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.meiguotong.entity.comcity.ComCity;
import com.jeeplus.modules.meiguotong.entity.comcitytravel.ComCityTravel;

import com.jeeplus.modules.meiguotong.mapper.comcity.ComCityMapper;
import com.jeeplus.modules.meiguotong.service.comcitytravel.ComCityTravelService;

/**
 * 城市表Service
 * @author cdq
 * @version 2018-08-02
 */
/**
 * @author LG
 *
 */
/**
 * @author LG
 *
 */
/**
 * @author LG
 *
 */
/**
 * @author LG
 *
 */
/**
 * @author LG
 *
 */
/**
 * @author LG
 *
 */
/**
 * @author LG
 *
 */
/**
 * @author LG
 *
 */
/**
 * @author LG
 *
 */
@Service
@Transactional(readOnly = true)
public class ComCityService extends CrudService<ComCityMapper, ComCity> {

	@Autowired
	private ComCityMapper comCityMapper;
	
	@Autowired
	private ComCityTravelService comCityTravelService;
	
	public ComCity get(String id) {
		return super.get(id);
	}
	/**
	 * 定制城市介绍信息
	 * @param comCity
	 * @return
	 */
	public ComCity getCity(ComCity comCity) {
		return mapper.getCity(comCity);
	}
	/**
	 * 城市关键字搜索
	 * @param comCity
	 * @return
	 */
	public List<ComCity> getCityByTitle(ComCity comCity) {
		return mapper.getCityByTitle(comCity);
	}
	/**
	 * 根据语言获取城市
	 * @param comCity
	 * @return
	 */
	public List<ComCity> getCityList(ComCity comCity) {
		return mapper.getCityList(comCity);
	}
	/**
	 * 根据语言获取城市和城市景点数量
	 * @param comCity
	 * @return
	 */
	public List<ComCity> getCityScenicNum(ComCity comCity) {
		return mapper.getCityScenicNum(comCity);
	}
	public List<ComCity> findList(ComCity comCity) {
		return super.findList(comCity);
	}
	
	public Page<ComCity> findPage(Page<ComCity> page, ComCity comCity) {
		return super.findPage(page, comCity);
	}
	
	@Transactional(readOnly = false)
	public void save(ComCity comCity) {	
		//判断复选框是否勾选
		if (comCity.getIsCar()==null) {
			comCity.setIsCar(2);
		}
		if (comCity.getIsOffered()==null) {
			comCity.setIsOffered(2);
		}
		if (comCity.getIsPlayer()==null) {
			comCity.setIsPlayer(2);
		}
		if (comCity.getIsScenic()==null) {
			comCity.setIsScenic(2);
		}
		if (comCity.getIsTourism()==null) {
			comCity.setIsTourism(2);
		}
		super.save(comCity);	//保存城市
		if(!comCity.getIsNewRecord()){
			//删除已有的定制旅游
			comCityTravelService.deleteComCityTravelByCityId(Integer.valueOf(comCity.getId()));
			List<ComCityTravel> travels=comCity.getList();
			for (int i = 0; i < travels.size(); i++) {
				if (!StringUtils.isBlank(travels.get(i).getClassname())) {
					ComCityTravel comCityTravel=new ComCityTravel();
					comCityTravel.setCityid(Integer.valueOf(comCity.getId()));
					comCityTravel.setClassname(travels.get(i).getClassname());
					comCityTravel.setTravelid(travels.get(i).getTravelid());
					comCityTravelService.save(comCityTravel);//保存定制旅游
				}
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(ComCity comCity) {
		super.delete(comCity);
	}
   /**
    * 修改状态
    * @param comCity
    */
	@Transactional(readOnly = false)
	public void status(ComCity comCity) {
		mapper.status(comCity);
		
	}

	/** 
	* @Title: getData 
	* @Description: 获取城市
	* @author 彭善智
	* @date 2018年8月24日下午9:25:55
	*/ 
	public List<ComCity> getData(ComCity comCity) {
		return mapper.getData(comCity);
	}
	/**
	* @Title: getNearbyCity
	* @Description: 获取附近城市
	* @author  彭善智
	* @Data 2018年11月13日上午10:38:21
	*/
	public List<ComCity> getNearbyCity(String cityid) {
		return mapper.getNearbyCity(cityid);
	}

	/**
	 * 城市名称关键字匹配搜索列表
	 * @param page
	 * @param ComCity
	 * @return
	 */
	public Page<ComCity> selectCityTitle(Page<ComCity> page, ComCity comCity) {
		dataRuleFilter(comCity);
		comCity.setPage(page);
		page.setList(mapper.getCityByTitle(comCity));
		return page;
	}
	/**
	 * 城市详情接口
	 * @param comCity
	 * @return
	 */
	public ComCity getCityDetails(ComCity comCity) {
		comCity = mapper.getCityDetails(comCity);
		comCity.setCityDetails(StringEscapeUtils.unescapeHtml(comCity.getCityDetails()));
		return comCity;
	}
	
//	根据国家ID，语言获取城市
	public List<ComCity> findCityBylanguageid(ComCity comCity){
		return comCityMapper.findCityBylanguageid(comCity);
	}
//	根据附近城市ID获取城市
	public List<ComCity> findCitynearCityid(String nearCity){
		return comCityMapper.findCitynearCityid(nearCity);
	}

}