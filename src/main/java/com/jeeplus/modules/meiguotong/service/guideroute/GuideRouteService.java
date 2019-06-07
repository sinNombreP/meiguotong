/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.service.guideroute;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.meiguotong.entity.guideroute.GuideRoute;
import com.jeeplus.modules.meiguotong.entity.guideroute.GuideRouteDate;
import com.jeeplus.modules.meiguotong.mapper.guideroute.GuideRouteDateMapper;
import com.jeeplus.modules.meiguotong.mapper.guideroute.GuideRouteMapper;
import com.jeeplus.modules.meiguotong.mapper.guideroute.GuideRouteTimeMapper;

/**
 * 导游路线表Service
 * @author cdq
 * @version 2018-09-04
 */
@Service
@Transactional(readOnly = true)
public class GuideRouteService extends CrudService<GuideRouteMapper, GuideRoute> {
	@Autowired
	private GuideRouteTimeMapper guideRouteTimeMapper;
	@Autowired
	private GuideRouteDateMapper guideRouteDateMapper;
	
	public GuideRoute get(String id) {
		return super.get(id);
	}
	/**
	 * 获取导游的路线规划详情接口
	 * @param id
	 * @return
	 */
	public GuideRoute getGuideRouteDetail(String id) {
		return mapper.getGuideRouteDetail(id);
	}
	/**
	 * 获取导游添加的路线规划接口
	 * @param guideRoute
	 * @return
	 */
	public Page<GuideRoute> getGuideRoute(Page<GuideRoute> page, GuideRoute guideRoute) {
		dataRuleFilter(guideRoute);
		guideRoute.setPage(page);
		page.setList(mapper.getGuideRoute(guideRoute));
		return page;
	}
	public List<GuideRoute> findList(GuideRoute guideRoute) {
		return super.findList(guideRoute);
	}
	
	public Page<GuideRoute> findPage(Page<GuideRoute> page, GuideRoute guideRoute) {
		return super.findPage(page, guideRoute);
	}
	/**
	 *  删除路线规划接口
	 * @param guideRoute
	 */
	@Transactional(readOnly = false)
	public void deleteGuideRoute(GuideRoute guideRoute) {
		mapper.delete(guideRoute);
	}
	/**
	 * 添加编辑导游路线规划
	 * @param guideRoute
	 */
	@Transactional(readOnly = false)
	public void saveGuideRoute(GuideRoute guideRoute) {
		if (guideRoute.getIsNewRecord()){
			mapper.insert(guideRoute);
			guideRouteTimeMapper.insertRoute(guideRoute);
			GuideRouteDate guideRouteDate = new GuideRouteDate();
			guideRouteDate.setRouteid(Integer.parseInt(guideRoute.getId()));		// 导游路线id
			guideRouteDate.setPrice(guideRoute.getPrice());
			String[] weekList = null;
			String[] dayList = null;
			if(guideRoute.getDateType()==2){
				weekList=guideRoute.getWeekDate().split(",");
			}else if(guideRoute.getDateType()==3){
				dayList=guideRoute.getDayDate().split(",");
			}
			
			//添加具体日期价格
			Calendar cal = Calendar.getInstance(); // 获得一个日历
			Integer day= null;
			Integer week= null;
			for(long k =guideRoute.getBeginDate().getTime();k<=guideRoute.getEndDate().getTime();k+=86400000) {
				cal.setTimeInMillis(k);
				if(guideRoute.getDateType() == 1) {
					guideRouteDate.setPriceDate(new Date(k));
					guideRouteDateMapper.insert(guideRouteDate);
				}
				else if (guideRoute.getDateType() == 2 && weekList != null && weekList.length > 0) {
						for (int i = 0; i <weekList.length; i++) {
							week = Integer.parseInt(weekList[i]);
							if(cal.get(Calendar.DAY_OF_WEEK)==week) {
								guideRouteDate.setPriceDate(new Date(k));
								guideRouteDateMapper.insert(guideRouteDate);
							}
						}
				}
				else if (guideRoute.getDateType() == 3 && dayList != null && dayList.length > 0 ) {
						for (int i = 0; i <dayList.length; i++) {
							day = Integer.parseInt(dayList[i]);
							if(cal.get(Calendar.DAY_OF_MONTH)==day) {
								guideRouteDate.setPriceDate(new Date(k));
								guideRouteDateMapper.insert(guideRouteDate);
							}
						}
				}
			}
		}else{
			 mapper.update(guideRoute);
			 guideRouteTimeMapper.updateRoute(guideRoute);
			 //删除旧的日期价格
			 guideRouteDateMapper.deleteOld(guideRoute);
			//添加新的日期价格
			 GuideRouteDate guideRouteDate = new GuideRouteDate();
			guideRouteDate.setRouteid(Integer.parseInt(guideRoute.getId()));		// 导游路线id
			guideRouteDate.setPrice(guideRoute.getPrice());
			String[] weekList = null;
			String[] dayList = null;
			if(guideRoute.getDateType()==2){
				weekList=guideRoute.getWeekDate().split(",");
			}else if(guideRoute.getDateType()==3){
				dayList=guideRoute.getDayDate().split(",");
			}
			
			Calendar cal = Calendar.getInstance(); // 获得一个日历
			Integer day= null;
			Integer week= null;
			for(long k =guideRoute.getBeginDate().getTime();k<=guideRoute.getEndDate().getTime();k+=86400000) {
				cal.setTimeInMillis(k);
				if(guideRoute.getDateType() == 1) {
					guideRouteDate.setPriceDate(new Date(k));
					guideRouteDateMapper.insert(guideRouteDate);
				}
				else if (guideRoute.getDateType() == 2 && weekList != null && weekList.length > 0) {
						for (int i = 0; i <weekList.length; i++) {
							week = Integer.parseInt(weekList[i]);
							if(cal.get(Calendar.DAY_OF_WEEK)==week) {
								guideRouteDate.setPriceDate(new Date(k));
								guideRouteDateMapper.insert(guideRouteDate);
							}
						}
				}
				else if (guideRoute.getDateType() == 3 && dayList != null && dayList.length > 0 ) {
						for (int i = 0; i <dayList.length; i++) {
							day = Integer.parseInt(dayList[i]);
							if(cal.get(Calendar.DAY_OF_MONTH)==day) {
								guideRouteDate.setPriceDate(new Date(k));
								guideRouteDateMapper.insert(guideRouteDate);
							}
						}
				}
			}
		}
	}
	@Transactional(readOnly = false)
	public void save(GuideRoute guideRoute) {
		super.save(guideRoute);
	}
	
	@Transactional(readOnly = false)
	public void delete(GuideRoute guideRoute) {
		super.delete(guideRoute);
	}
  /**
   * 导游推荐路线接口
   * @param page
   * @param guideRoute
   * @return
   */
	public Page<GuideRoute> findGuideRouteList(Page<GuideRoute> page, GuideRoute guideRoute) {
		dataRuleFilter(guideRoute);
		guideRoute.setPage(page);
		page.setList(mapper.findGuideRouteList(guideRoute));
		return page;
	}
	
	//购物车获取导游线路信息
	public GuideRoute productCar_findGuideRoute(@Param("id")Integer id){
		return mapper.productCar_findGuideRoute(id);
	};
	
}