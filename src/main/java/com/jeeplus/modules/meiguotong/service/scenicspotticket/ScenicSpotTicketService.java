/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.service.scenicspotticket;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import com.jeeplus.modules.meiguotong.entity.scenicspot.ScenicSpot;
import com.jeeplus.modules.meiguotong.entity.scenicspotticket.ScenicSpotTicket;
import com.jeeplus.modules.meiguotong.mapper.scenicspot.ScenicSpotMapper;
import com.jeeplus.modules.meiguotong.mapper.scenicspotticket.ScenicSpotTicketMapper;

/**
 * 景点门票表Service
 * @author cdq
 * @version 2018-08-16
 */
@Service
@Transactional(readOnly = true)
public class ScenicSpotTicketService extends CrudService<ScenicSpotTicketMapper, ScenicSpotTicket> {
	
	@Autowired
	private ScenicSpotMapper scenicSpotMapper;
	
	public ScenicSpotTicket get(String id) {
		return super.get(id);
	}
	
	/**
	 * 查询景点的定制门票
	 * @param scenicSpotTicket
	 * @return
	 */
	public ScenicSpotTicket getTicket(ScenicSpotTicket scenicSpotTicket) {
		return mapper.getTicket(scenicSpotTicket);
	}
	/**
	 * 查询景点的门票
	 * @param scenicSpotTicket
	 * @return
	 */
	public List<ScenicSpotTicket> getList(ScenicSpotTicket scenicSpotTicket) {
		return mapper.getList(scenicSpotTicket);
	}
	public List<ScenicSpotTicket> findList(ScenicSpotTicket scenicSpotTicket) {
		return super.findList(scenicSpotTicket);
	}
	
	public Page<ScenicSpotTicket> findPage(Page<ScenicSpotTicket> page, ScenicSpotTicket scenicSpotTicket) {
		return super.findPage(page, scenicSpotTicket);
	}
	
	@Transactional(readOnly = false)
	public void save(ScenicSpotTicket scenicSpotTicket) {
		ScenicSpot scenicSpot = scenicSpotMapper.get(scenicSpotTicket.getScenicid().toString());
		scenicSpotTicket.setLanguageId(scenicSpot.getLanguageId()); 
		super.save(scenicSpotTicket);
	}
	
	@Transactional(readOnly = false)
	public void delete(ScenicSpotTicket scenicSpotTicket) {
		super.delete(scenicSpotTicket);
	}
	 /**
	   *修改状态
	   */
	@Transactional(readOnly = false)
	public void status(ScenicSpotTicket scenicSpotTicket) {
		mapper.status(scenicSpotTicket);
	}
	/**
	* @Title: getScenicNum
	* @Description: 获取景点门票数量
	* @author  彭善智
	 * @param month2 
	* @Data 2018年10月19日下午4:21:30
	*/
	public List<ScenicSpotTicket> getScenicNum (String scenicSpotTicketId, String scenicDate) throws Exception {
		List<ScenicSpotTicket> list = new ArrayList<ScenicSpotTicket>();
		ScenicSpotTicket scenicSpotTicket = mapper.get(scenicSpotTicketId);
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DAY_OF_MONTH,+scenicSpotTicket.getRule());
		
		Calendar calendar1 = Calendar.getInstance();
		calendar1.setTime(new SimpleDateFormat("yyyy-MM").parse(scenicDate));
		calendar1.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天   
		
		Calendar calendar2 = Calendar.getInstance();
		calendar2.setTime(new SimpleDateFormat("yyyy-MM").parse(scenicDate));
		calendar2.set(Calendar.DAY_OF_MONTH, calendar2.getActualMaximum(Calendar.DAY_OF_MONTH));  
		
		for(long k = calendar1.getTimeInMillis(); k <= calendar2.getTimeInMillis(); k+=86400000) {
			if(k > calendar.getTimeInMillis() && k < scenicSpotTicket.getValidityDate().getTime()) {
				ScenicSpotTicket sc  = new ScenicSpotTicket();
				sc.setPrice(scenicSpotTicket.getPrice());
				sc.setDate(new Date(k));
				list.add(sc);
			}
		}
		return list;
	}
	/**
	* @Title: getScenicSpotTicketList
	* @Description: 获取景点门票列表
	* @author  彭善智
	* @Data 2018年12月5日下午4:25:53
	*/
	public List<ScenicSpotTicket> getScenicSpotTicketList(ScenicSpotTicket scenicSpotTicket) {
		return mapper.getScenicSpotTicketList(scenicSpotTicket);
	}
	
	//查询是否超期
	public ScenicSpotTicket checkDateByScenicid(Integer id){
		return mapper.checkDateByScenicid(id);
	};
	
	//购物车获取景点门票信息
	public ScenicSpotTicket productCar_findScenice(Integer id){
			return mapper.productCar_findScenice(id);
		};
}