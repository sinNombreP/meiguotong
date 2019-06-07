/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.service.product;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.meiguotong.entity.product.Route;
import com.jeeplus.modules.meiguotong.entity.product.RouteContent;
import com.jeeplus.modules.meiguotong.entity.product.RouteDate;
import com.jeeplus.modules.meiguotong.entity.product.RouteTime;
import com.jeeplus.modules.meiguotong.entity.product.WeekDate;
import com.jeeplus.modules.meiguotong.entity.settingtitlepro.SettingTitlePro;
import com.jeeplus.modules.meiguotong.mapper.product.RouteContentMapper;
import com.jeeplus.modules.meiguotong.mapper.product.RouteDateMapper;
import com.jeeplus.modules.meiguotong.mapper.product.RouteMapper;
import com.jeeplus.modules.meiguotong.mapper.product.RouteTimeMapper;
import com.jeeplus.modules.meiguotong.mapper.settingtitlepro.SettingTitleProMapper;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.utils.UserUtils;

/**
 * 当地参团Service
 * @author psz
 * @version 2018-08-13
 */
@Service
@Transactional(readOnly = true)
public class RouteService extends CrudService<RouteMapper, Route> {

	@Autowired
	private RouteDateMapper routeDateMapper;
	@Autowired
	private RouteTimeMapper routeTimeMapper;
	@Autowired
	private RouteContentMapper routeContentMapper;
	@Autowired
	private RouteMapper routeMapper;
	@Autowired
	private SettingTitleProMapper settingTitleProMapper;

	public Route get(String id) {
		return super.get(id);
	}
	/**
	 * 获取路线详情接口
	 * @param route
	 * @return
	 */
	public Route getRoute(Route route) {
		return mapper.getRoute(route);
	}
	/**
	 * 获取参团详情接口
	 * @param route
	 * @return
	 */
	public Route getCityRoute(Route route) {
		return mapper.getCityRoute(route);
	}
	public List<Route> findList(Route route) {
		return super.findList(route);
	}
	/**
	 * 常规路线搜索接口
	 * @param page
	 * @param route
	 * @return
	 */
	public Page<Route> findRoutePage(Page<Route> page, Route route) {
		dataRuleFilter(route);
		route.setPage(page);
		page.setList(mapper.findRouteList(route));
		return page;
	}
	/**
	 * 当地参团搜索接口
	 * @param page
	 * @param route
	 * @return
	 */
	public Page<Route> findCityRoutePage(Page<Route> page, Route route) {
		dataRuleFilter(route);
		route.setPage(page);
		List<Route> list = mapper.findCityRouteList(route);
		if(list!=null&&list.size()>0){
			for(Route a:list){
				if(a.getDateType()==2){
					a.setWeekList(a.getWeekDate().split(","));
				}else if(a.getDateType()==3){
					a.setDayList(a.getDayDate().split(","));
				}
			}
		}
		page.setList(list);
		return page;
	}
	public Page<Route> findPage(Page<Route> page, Route route) {
		return super.findPage(page, route);
	}
	
	@Transactional(readOnly = false)
	public void save(Route route) {
		super.save(route);
	}
	
	@Transactional(readOnly = false)
	public void delete(Route route) {
		super.delete(route);
	}

	/** 
	* @Title: updateStatus 
	* @Description: 修改审核状态
	* @author 彭善智
	* @date 2018年8月13日下午3:20:29
	*/ 
	@Transactional(readOnly = false)
	public void updateStatus(Route route) {
		mapper.updateStatus(route);
	}

	/** 
	* @Title: getWeekAll 
	* @Description: 获取所有的星期
	* @author 彭善智
	* @date 2018年8月14日下午12:00:49
	*/ 
	public List<WeekDate> getWeekAll() {
		List<WeekDate> lists = new ArrayList<WeekDate>();
		WeekDate w = new WeekDate();
		w = new WeekDate();
		w.setId("1");
		w.setName("周日");
		lists.add(w);
		w = new WeekDate();
		w.setId("2");
		w.setName("周一");
		lists.add(w);
	    w = new WeekDate();
		w.setId("3");
		w.setName("周二");
		lists.add(w);
		w = new WeekDate();
		w.setId("4");
		w.setName("周三");
		lists.add(w);
		w = new WeekDate();
		w.setId("5");
		w.setName("周四");
		lists.add(w);
		w = new WeekDate();
		w.setId("6");
		w.setName("周五");
		lists.add(w);
		w = new WeekDate();
		w.setId("7");
		w.setName("周六");
		lists.add(w);
		return lists;
	}

	/** 
	* @Title: getDayAll 
	* @Description: 获取所有的天数
	* @author 彭善智
	* @date 2018年8月14日下午12:02:24
	*/ 
	public List<WeekDate> getDayAll() {
		List<WeekDate> lists = new ArrayList<WeekDate>();
		for (Integer i=1 ; i<32 ;i++) {
			WeekDate w = new WeekDate();
			w.setName(i.toString());
			w.setId(i.toString());
			w.setDigFlag(2);
			lists.add(w);
		}
		return lists;
	}
	
	
	/** 
	* @Title: getHourAll 
	* @Description: 获取所有小时
	* @author 彭善智
	* @date 2018年9月3日下午8:55:02
	*/ 
	public List<WeekDate> getHourAll() {
		List<WeekDate> lists = new ArrayList<WeekDate>();
		WeekDate weekDate = new WeekDate();
		weekDate.setId("-1");
		weekDate.setName("全部");
		lists.add(weekDate);
		for (Integer i=0 ; i<48 ;i++) {
			WeekDate w = new WeekDate();
			String p = i/2+":"+(i%2==0?"00":"30");
			w.setId(p);
			w.setName(p);
			lists.add(w);
		}
		return lists;
	}

	
	
	/** 
	* @Title: updateAdd 
	* @Description: 保存新增参团/常规路线
	* @author 彭善智
	 * @throws ParseException 
	* @date 2018年8月26日下午9:54:32
	*/ 
	@Transactional(readOnly = false)
	public void updateAdd(Route route) throws ParseException {
		String imgUrl="";
		if (route.getImgSrc()!= null && route.getImgSrc().length>0) {
			for (int i = 0; i < route.getImgSrc().length; i++) {
				imgUrl+=route.getImgSrc()[i]+",";
			}
			imgUrl=imgUrl.length()>0 ? imgUrl.substring(0,imgUrl.lastIndexOf(",")):"";
			route.setCarImg(imgUrl);
		}
		String weekDate = "";
		if (route.getWeekList()!= null && route.getWeekList().length>0) {
			for (int i = 0; i < route.getWeekList().length; i++) {
				weekDate+=route.getWeekList()[i]+",";
			}
			weekDate=weekDate.length()>0 ? weekDate.substring(0,weekDate.lastIndexOf(",")):"";
		}
		String dayDate = "";
		if (route.getDayList()!= null && route.getDayList().length>0) {
			for (int i = 0; i < route.getDayList().length; i++) {
				dayDate+=route.getDayList()[i]+",";
			}
			dayDate=dayDate.length()>0 ? dayDate.substring(0,dayDate.lastIndexOf(",")):"";
		}
		
		RouteTime routeTime = route.getIsNewRecord()?new RouteTime():routeTimeMapper.get(route.getId().toString());
		routeTime.setDateType(route.getDateType());
		routeTime.setBeginDate(route.getBeginDate());
		routeTime.setEndDate(route.getEndDate());
		if(route.getDateType() == 2) {
			routeTime.setWeekDate(weekDate);
			routeTime.setDayDate(null);
		} else if(route.getDateType() == 3) {
			routeTime.setWeekDate(null);
			routeTime.setDayDate(dayDate);
		}
		if (route.getIsNewRecord()){
			route.preInsert();
			mapper.insert(route);
			route.setNo("CG-"+route.getAgentid()+"-"+route.getDayNum()+"-"+route.getId());
			mapper.updateNo(route);
			routeTime.setRouteid(Integer.parseInt(route.getId()));
			routeTime.preInsert();
			routeTimeMapper.insert(routeTime);
			
		}else{
			route.preUpdate();
			User user = UserUtils.getUser();
			if(user!=null&&user.getAgentid()!=null){
				route.setStatus(1);
			}
			 mapper.update(route);
			 routeTime.preUpdate();
			 routeTimeMapper.update(routeTime);
		}
		RouteDate routeDate = new RouteDate();
		routeDate.preInsert();
		routeDate.setRouteid(Integer.parseInt(route.getId()));
		routeDate.setOneProfit(route.getOneProfit());
		routeDate.setTwoProfit(route.getTwoProfit());
		routeDate.setThreeProfit(route.getThreeProfit());
		routeDate.setFourProfit(route.getFourProfit());
		routeDate.setArrangeProfit(route.getArrangeProfit());
		routeDate.setPriceInfo(route.getPriceInfo());
		routeDate.setHouseOne(route.getHouseOne());
		routeDate.setStock(route.getStock());
		routeDate.setOneCost(route.getOneCost());
		routeDate.setTwoCost(route.getTwoCost());
		routeDate.setThreeCost(route.getThreeCost());
		routeDate.setFourCost(route.getFourCost());
		routeDate.setArrangeCost(route.getArrangeCost());
		routeDate.setFathid(1);
		
		Calendar cal = Calendar.getInstance(); // 获得一个日历
		Integer day= null;
		Integer week= null;
		List<RouteDate> list = new ArrayList<>();
		for(long k =route.getBeginDate().getTime();k<=route.getEndDate().getTime();k+=86400000) {
			cal.setTimeInMillis(k);
			if(route.getDateType() == 1) {
				RouteDate routeDate1 = new RouteDate();
				BeanUtils.copyProperties(routeDate, routeDate1); 
				routeDate1.setPriceDate(new Date(k));
				list.add(routeDate1);
			}
			else if (route.getDateType() == 2 && route.getWeekList() != null && route.getWeekList().length > 0) {
					for (int i = 0; i <route.getWeekList().length; i++) {
						day = Integer.parseInt(route.getWeekList()[i]);
						if(cal.get(Calendar.DAY_OF_WEEK)==day) {
							RouteDate routeDate1 = new RouteDate();
							BeanUtils.copyProperties(routeDate, routeDate1); 
							routeDate1.setPriceDate(new Date(k));
							list.add(routeDate1);
						}
					}
			}
			else if (route.getDateType() == 3 && route.getDayList() != null && route.getDayList().length > 0 ) {
					for (int i = 0; i <route.getDayList().length; i++) {
						week = Integer.parseInt(route.getDayList()[i]);
						if(cal.get(Calendar.DAY_OF_MONTH)==week) {
							RouteDate routeDate1 = new RouteDate();
							BeanUtils.copyProperties(routeDate, routeDate1); 
							routeDate1.setPriceDate(new Date(k));
							list.add(routeDate1);
						}
					}
			}
		}
		//获取旧的日期数据
		List<RouteDate> dateList = routeDateMapper.getAllDateList(routeDate);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if(list!=null&&list.size()>0){
			for(RouteDate a:list){
				if(dateList!=null&&dateList.size()>0){
					for(RouteDate b:dateList){
						String at=sdf.format(a.getPriceDate());
						String bt=sdf.format(b.getPriceDate());
						Date att=sdf.parse(at);
						Date btt=sdf.parse(bt);
						if(att.compareTo(btt)==0){
							a.setId(b.getId());
							a.setNum(b.getNum());
							break;
						}
					}
				}
			}
		}
		//编辑保存日期
		if(list!=null&&list.size()>0){
			for(RouteDate a:list){
				if (StringUtils.isBlank(a.getId())){
					routeDateMapper.insert(a);
				}else{
					a.setDelFlag("0");
					routeDateMapper.update(a);
				}
			}
		}
		//删除旧的不存在日期（逻辑删除）
		if(dateList!=null&&dateList.size()>0){
			for(RouteDate b:dateList){
				if(list!=null&&list.size()>0){
					int num =0;
					for(RouteDate a:list){
						String at=sdf.format(a.getPriceDate());
						String bt=sdf.format(b.getPriceDate());
						Date att=sdf.parse(at);
						Date btt=sdf.parse(bt);
						if(att.compareTo(btt)==0){
							num=1;
							break;
						}
					}
					if(num==0){
						//删除
						if(b.getNum()!=0){
							routeDateMapper.deleteByLogic(b);
						}else{
							routeDateMapper.delete(b);
						}
						
					}
				}else{
					//删除
					if(b.getNum()!=0){
						routeDateMapper.deleteByLogic(b);
					}else{
						routeDateMapper.delete(b);
					}
				}
			}
		}
		
		routeDate.setPriceDate(null);
		routeDate.setFathid(0);
		//查询是否存在
		RouteDate routeDate2 = routeDateMapper.getByFathid(routeDate);
		if(routeDate2!=null){
			routeDate.setId(routeDate2.getId());
			routeDate.setDelFlag("0"); 
			routeDateMapper.update(routeDate);
		}else{
			routeDateMapper.insert(routeDate);
		}
		
		
		routeContentMapper.deleteByRouteId(route.getId());
		for(RouteContent r : route.getRouteContentList()) {
			for(RouteContent r1 : r.getContentList()){
				r1.setRouteid(Integer.parseInt(route.getId()));
				r1.setDayCount(r.getDayCount());
				routeContentMapper.insert(r1);
			}
		}
		//添加编辑新增标题内容
		List<SettingTitlePro> settingTitleProList = route.getSettingTitleProList();
		if(settingTitleProList!=null&&settingTitleProList.size()>0){
			for(SettingTitlePro a:settingTitleProList){
				if (a.getIsNewRecord()){
					a.setProid(Integer.parseInt(route.getId()));
					settingTitleProMapper.insert(a);
				}else{
					a.setProid(Integer.parseInt(route.getId()));
					settingTitleProMapper.update(a);
				}
			}
		}
	}
	
	//根据title获取参团列表
	public List<Route> findRrouteByTitle(Route route){
		return routeMapper.findRrouteByTitle(route);
	}
	
	//根据当地参团ID获取参团列表
	public List<Route> findRrouteByOfferenTop(String routes){
		return routeMapper.findRrouteByOfferenTop(routes);
	}
	
	/**
	 * 目的地获取当地参团列表
	 * @param param
	 * @return
	 */
	public List<Route> findOfferedList(Map<String, Object> param){
		return routeMapper.findOfferedList(param);
	}
	/**
	 * 目的地获取当地参团数量
	 * @param param
	 * @return
	 */
	public Integer countOffered(Map<String, Object> param){
		return routeMapper.countOffered(param);
	}
	
	//购物车查库存
		public Route findRouteStockByDate(Integer id,Date date){
			return mapper.findRouteStockByDate(id, date);
		};
	
//	public static void main(String[] args) {
//		for (Integer i=0 ; i<48 ;i++) {
//			int j = i%2;
//			int k =i/2;
//			System.out.println(k+":"+(j==0?"00":"30"));
//		
//		}
//	}
	

}