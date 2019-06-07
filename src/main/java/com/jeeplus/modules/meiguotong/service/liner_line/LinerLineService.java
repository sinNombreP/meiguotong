/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.service.liner_line;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.meiguotong.entity.comcity.ComCity;
import com.jeeplus.modules.meiguotong.entity.liner.LinerDate;
import com.jeeplus.modules.meiguotong.entity.liner.LinerTime;
import com.jeeplus.modules.meiguotong.entity.liner_line.LinerLine;
import com.jeeplus.modules.meiguotong.entity.linerroom.LinerRoom;
import com.jeeplus.modules.meiguotong.entity.linertrip.LinerTrip;
import com.jeeplus.modules.meiguotong.entity.product.RouteDate;
import com.jeeplus.modules.meiguotong.entity.settingtitlepro.SettingTitlePro;
import com.jeeplus.modules.meiguotong.mapper.liner.LinerDateMapper;
import com.jeeplus.modules.meiguotong.mapper.liner.LinerTimeMapper;
import com.jeeplus.modules.meiguotong.mapper.liner_line.LinerLineMapper;
import com.jeeplus.modules.meiguotong.mapper.linerroom.LinerRoomMapper;
import com.jeeplus.modules.meiguotong.mapper.linertrip.LinerTripMapper;
import com.jeeplus.modules.meiguotong.mapper.settingtitlepro.SettingTitleProMapper;

/**
 * 邮轮航线管理Service
 * @author cdq
 * @version 2018-08-13
 */
@Service
@Transactional(readOnly = true)
public class LinerLineService extends CrudService<LinerLineMapper, LinerLine> {
	@Autowired
	private LinerRoomMapper linerRoomMapper;
	@Autowired
	private LinerTripMapper linerTripMapper;
	@Autowired
	private LinerTimeMapper linerTimeMapper;
	@Autowired
	private LinerDateMapper linerDateMapper;
	@Autowired
	private SettingTitleProMapper settingTitleProMapper;
	
	
	public LinerLine get(String id) {
		return super.get(id);
	}
	/**
	 * 接口获取游轮路线信息
	 * @param linerline
	 * @return
	 */
	public LinerLine getLinerline(LinerLine linerline) {
		return mapper.getLinerline(linerline);
	}
	public List<LinerLine> findList(LinerLine linerLine) {
		return super.findList(linerLine);
	}
	
	public Page<LinerLine> findPage(Page<LinerLine> page, LinerLine linerLine) {
		return super.findPage(page, linerLine);
	}
	
	@Transactional(readOnly = false)
	public void save(LinerLine linerLine) {
		linerLine.setStatus(1);
		if (linerLine.getIsNewRecord()){
			linerLine.preInsert();
			mapper.insert(linerLine);
			String str = "YL-";
			str+=linerLine.getAgentid()+"-";
			str+=linerLine.getTravelDay()+"-";
			str+=linerLine.getLinerId()+"-";
			str+=linerLine.getId();
			linerLine.setLineNo(str);
			mapper.updateLineNo(linerLine);
			//添加时间
			LinerTime linerTime = new LinerTime();
			linerTime.setLineid(Integer.parseInt(linerLine.getId()));
			linerTime.setBeginDate(linerLine.getBeginDate());
			linerTime.setEndDate(linerLine.getEndDate());
			linerTime.setDateType(linerLine.getDateType());
			
			LinerDate linerDate = new LinerDate();
			linerDate.setLineid(Integer.parseInt(linerLine.getId()));
			linerDate.setPrice(linerLine.getPrice());
			
			if(linerLine.getDateType()==1){//全部
				linerTimeMapper.insert(linerTime);
				Calendar cal = Calendar.getInstance(); // 获得一个日历
				for(long k =linerLine.getBeginDate().getTime();k<=linerLine.getEndDate().getTime();k+=86400000) {
					cal.setTimeInMillis(k);
					linerDate.setStartDate(new Date(k));
					linerDateMapper.insert(linerDate);
				}
			}else if(linerLine.getDateType()==2){//按星期
				linerTime.setWeekDate(linerLine.getWeekList());
				linerTimeMapper.insert(linerTime);
				String[] weeks = linerLine.getWeekList().split(",");
				Calendar cal = Calendar.getInstance(); // 获得一个日历
				for(long k =linerLine.getBeginDate().getTime();k<=linerLine.getEndDate().getTime();k+=86400000) {
					cal.setTimeInMillis(k);
					for (int i = 0; i <weeks.length; i++) {
						Integer day = Integer.parseInt(weeks[i]);
						if(cal.get(Calendar.DAY_OF_WEEK)==day) {
							linerDate.setStartDate(new Date(k));
							linerDateMapper.insert(linerDate);
						}
					}
				}
			}else if(linerLine.getDateType()==3){//按号数
				linerTime.setDayDate(linerLine.getDayList());
				linerTimeMapper.insert(linerTime);
				String[] days = linerLine.getDayList().split(",");
				Calendar cal = Calendar.getInstance(); // 获得一个日历
				for(long k =linerLine.getBeginDate().getTime();k<=linerLine.getEndDate().getTime();k+=86400000) {
					cal.setTimeInMillis(k);
					for (int i = 0; i <days.length; i++) {
						Integer day = Integer.parseInt(days[i]);
						if(cal.get(Calendar.DAY_OF_MONTH)==day) {
							linerDate.setStartDate(new Date(k));
							linerDateMapper.insert(linerDate);
						}
					}
				}
			}
			//添加房间
			List<LinerRoom> linerRoomList =linerLine.getLinerRoomList();
			if(linerRoomList!=null&&linerRoomList.size()>0){
		      for(LinerRoom a:linerRoomList){
	    		  a.setLinerLineId(Integer.parseInt(linerLine.getId()));
		    	  a.setLanguageId(linerLine.getLanguageId());
		    	  if(a.getCheckInType()==null){
		    		  a.setCheckInType(2);
		    	  }
		    	  a.preInsert();
		    	  linerRoomMapper.insert(a);
		      }
		    }
			
			//添加行程
			List <LinerTrip> linerTripList=linerLine.getLinerTripList();
			if(linerTripList!=null&&linerTripList.size()>0){
				for(LinerTrip a:linerTripList){
					 List <LinerTrip> contentList=a.getContentList();
					 if(contentList!=null&&contentList.size()>0){
						 for(LinerTrip b:contentList){
							 b.setLinerLineId(Integer.parseInt(linerLine.getId()));
					    	 b.setLanguageId(linerLine.getLanguageId());
					    	 b.setDayCount(a.getDayCount());
					    	 b.preInsert();
					    	 linerTripMapper.insert(b);	
						 }
					 }
				}
			}
			
			//添加编辑新增标题内容
			List<SettingTitlePro> settingTitleProList = linerLine.getSettingTitleProList();
			if(settingTitleProList!=null&&settingTitleProList.size()>0){
				for(SettingTitlePro a:settingTitleProList){
					if (a.getIsNewRecord()){
						a.setProid(Integer.parseInt(linerLine.getId()));
						settingTitleProMapper.insert(a);
					}else{
						a.setProid(Integer.parseInt(linerLine.getId()));
						settingTitleProMapper.update(a);
					}
				}
			}
		}else{
			linerLine.preUpdate();
			 mapper.update(linerLine);
			 
			 //删除时间，删除房间删除行程
			 LinerTime linerTime1 = new LinerTime();
			 linerTime1.setLineid(Integer.parseInt(linerLine.getId()));
			 linerTimeMapper.deleteByLinerLineId(linerTime1);
			 LinerDate linerDate1 = new LinerDate();
			 linerDate1.setLineid(Integer.parseInt(linerLine.getId()));
			 linerDateMapper.deleteByLinerLineId(linerDate1);
			 LinerRoom linerRoom1 = new LinerRoom();
			 linerRoom1.setLinerLineId(Integer.parseInt(linerLine.getId()));
			 linerRoomMapper.deleteByLinerLineId(linerRoom1);
			 LinerTrip LinerTrip1 = new LinerTrip();
			 LinerTrip1.setLinerLineId(Integer.parseInt(linerLine.getId()));
			 linerTripMapper.deleteByLinerLineId(LinerTrip1);
			//添加时间
				LinerTime linerTime = new LinerTime();
				linerTime.setLineid(Integer.parseInt(linerLine.getId()));
				linerTime.setBeginDate(linerLine.getBeginDate());
				linerTime.setEndDate(linerLine.getEndDate());
				linerTime.setDateType(linerLine.getDateType());
				
				LinerDate linerDate = new LinerDate();
				linerDate.setLineid(Integer.parseInt(linerLine.getId()));
				linerDate.setPrice(linerLine.getPrice());
				
				if(linerLine.getDateType()==1){//全部
					linerTimeMapper.insert(linerTime);
					Calendar cal = Calendar.getInstance(); // 获得一个日历
					for(long k =linerLine.getBeginDate().getTime();k<=linerLine.getEndDate().getTime();k+=86400000) {
						cal.setTimeInMillis(k);
						linerDate.setStartDate(new Date(k));
						linerDateMapper.insert(linerDate);
					}
				}else if(linerLine.getDateType()==2){//按星期
					linerTime.setWeekDate(linerLine.getWeekList());
					linerTimeMapper.insert(linerTime);
					String[] weeks = linerLine.getWeekList().split(",");
					Calendar cal = Calendar.getInstance(); // 获得一个日历
					for(long k =linerLine.getBeginDate().getTime();k<=linerLine.getEndDate().getTime();k+=86400000) {
						cal.setTimeInMillis(k);
						for (int i = 0; i <weeks.length; i++) {
							Integer day = Integer.parseInt(weeks[i]);
							if(cal.get(Calendar.DAY_OF_WEEK)==day) {
								linerDate.setStartDate(new Date(k));
								linerDateMapper.insert(linerDate);
							}
						}
					}
				}else if(linerLine.getDateType()==3){//按号数
					linerTime.setDayDate(linerLine.getDayList());
					linerTimeMapper.insert(linerTime);
					String[] days = linerLine.getDayList().split(",");
					Calendar cal = Calendar.getInstance(); // 获得一个日历
					for(long k =linerLine.getBeginDate().getTime();k<=linerLine.getEndDate().getTime();k+=86400000) {
						cal.setTimeInMillis(k);
						for (int i = 0; i <days.length; i++) {
							Integer day = Integer.parseInt(days[i]);
							if(cal.get(Calendar.DAY_OF_MONTH)==day) {
								linerDate.setStartDate(new Date(k));
								linerDateMapper.insert(linerDate);
							}
						}
					}
				}
				//添加房间
				List<LinerRoom> linerRoomList =linerLine.getLinerRoomList();
				if(linerRoomList!=null&&linerRoomList.size()>0){
			      for(LinerRoom a:linerRoomList){
			    	  if(a.getName()!=null){
				    	  a.setLinerLineId(Integer.parseInt(linerLine.getId()));
				    	  a.setLanguageId(linerLine.getLanguageId());
				    	  if(a.getCheckInType()==null){
				    		  a.setCheckInType(2);
				    	  }
				    	  a.preInsert();
				    	  linerRoomMapper.insert(a);
			    	  }
			      }
			    }
				
				//添加行程
				List <LinerTrip> linerTripList=linerLine.getLinerTripList();
				if(linerTripList!=null&&linerTripList.size()>0){
					for(LinerTrip a:linerTripList){
						 List <LinerTrip> contentList=a.getContentList();
						 if(contentList!=null&&contentList.size()>0){
							 for(LinerTrip b:contentList){
								 b.setLinerLineId(Integer.parseInt(linerLine.getId()));
						    	 b.setLanguageId(linerLine.getLanguageId());
						    	 b.setDayCount(a.getDayCount());
						    	 b.preInsert();
						    	 linerTripMapper.insert(b);	
							 }
						 }
					}
				}
				//添加编辑新增标题内容
				List<SettingTitlePro> settingTitleProList = linerLine.getSettingTitleProList();
				if(settingTitleProList!=null&&settingTitleProList.size()>0){
					for(SettingTitlePro a:settingTitleProList){
						if (a.getIsNewRecord()){
							a.setProid(Integer.parseInt(linerLine.getId()));
							settingTitleProMapper.insert(a);
						}else{
							a.setProid(Integer.parseInt(linerLine.getId()));
							settingTitleProMapper.update(a);
						}
					}
				}
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(LinerLine linerLine) {
		super.delete(linerLine);
	}
    /**
     * 修改状态
     * @param linerLine
     */
	@Transactional(readOnly = false)
	public void status(LinerLine linerLine) {
		mapper.status(linerLine);
	}
	
	/**
    * 获取游轮出发港口
    * @param getlinerLine
    * @return
    */
	public List<ComCity> getStartCity(LinerLine linerLine) {
		return mapper.getStartCity(linerLine);
	}
   
   /**
    * 邮轮搜索接口
    * @param linerline
    * @return
    */
	public Page<LinerLine> findCruiseList(Page<LinerLine> page, LinerLine linerline) {
		dataRuleFilter(linerline);
		linerline.setPage(page);
		page.setList(mapper.findCruiseList(linerline));
		return page;
	}
	/**
	 * 邮轮筛选接口
	 * @param page
	 * @param linerline
	 * @return
	 */
	public Page<LinerLine> findCruiseScreenList(Page<LinerLine> page, LinerLine linerline) {
		dataRuleFilter(linerline);
		linerline.setPage(page);
		page.setList(mapper.findCruiseScreenList(linerline));
		return page;
	}


	
}