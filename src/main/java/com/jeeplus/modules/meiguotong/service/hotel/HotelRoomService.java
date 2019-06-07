/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.service.hotel;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.jeeplus.common.utils.StringUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.meiguotong.entity.hotel.HotelRoom;
import com.jeeplus.modules.meiguotong.entity.hotel.HotelRoomDate;
import com.jeeplus.modules.meiguotong.entity.hotel.HotelRoomTime;
import com.jeeplus.modules.meiguotong.mapper.hotel.HotelRoomDateMapper;
import com.jeeplus.modules.meiguotong.mapper.hotel.HotelRoomMapper;
import com.jeeplus.modules.meiguotong.mapper.hotel.HotelRoomTimeMapper;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.utils.UserUtils;

/**
 * 酒店管理Service
 * @author PSZ
 * @version 2018-08-21
 */
@Service
@Transactional(readOnly = true)
public class HotelRoomService extends CrudService<HotelRoomMapper, HotelRoom> {
	
	@Autowired
	private HotelRoomDateMapper hotelRoomDateMapper;
	@Autowired
	private HotelRoomTimeMapper hotelRoomTimeMapper;

	public HotelRoom get(String id) {
		return super.get(id);
	}
	/**
	 * 接口获取酒店房间详情
	 * @param hotelRoom
	 * @return
	 */
	public HotelRoom getRoomDetails(HotelRoom hotelRoom) {
		hotelRoom = mapper.getRoomDetails(hotelRoom);
		if(StringUtils.isNotBlank(hotelRoom.getOtherInfo())){
			hotelRoom.setOtherInfo(StringEscapeUtils.unescapeHtml(hotelRoom.getOtherInfo()));
		}
		return hotelRoom;
	}
	/**
	 * 获取酒店房间列表
	 * @param page 
	 * @param hotelRoom
	 * @return
	 */
	public Page<HotelRoom> getHotelRoom(Page<HotelRoom> page, HotelRoom hotelRoom) {
		dataRuleFilter(hotelRoom);
		hotelRoom.setPage(page);
		page.setList(mapper.getHotelRoom(hotelRoom));
		return page;
	}
	public List<HotelRoom> findList(HotelRoom hotelRoom) {
		return super.findList(hotelRoom);
	}
	
	public Page<HotelRoom> findPage(Page<HotelRoom> page, HotelRoom hotelRoom) {
		return super.findPage(page, hotelRoom);
	}
	
	@Transactional(readOnly = false)
	public void save(HotelRoom hotelRoom) {
		super.save(hotelRoom);
	}
	
	@Transactional(readOnly = false)
	public void delete(HotelRoom hotelRoom) {
		super.delete(hotelRoom);
	}

	/** 
	* @Title: findListByAgentid 
	* @Description:根据agentid获取城市
	* @author 彭善智
	* @date 2018年8月21日下午4:11:58
	*/ 
	public List<HotelRoom> findListByAgentid(HotelRoom hotelRoom) {
		return mapper.findListByAgentid(hotelRoom);
	}
	
	/** 
	* @Title: updateAdd 
	* @Description: 修改新增
	* @author 彭善智
	* @date 2018年8月22日下午5:27:47
	*/
	@Transactional(readOnly = false)
	public void updateAdd(HotelRoom hotelRoom) {
		String imgUrl="";
		if (hotelRoom.getImgSrc()!= null && hotelRoom.getImgSrc().length>0) {
			for (int i = 0; i < hotelRoom.getImgSrc().length; i++) {
				imgUrl+=hotelRoom.getImgSrc()[i]+",";
			}
			imgUrl=imgUrl.length()>0 ? imgUrl.substring(0,imgUrl.lastIndexOf(",")):"";
			hotelRoom.setImgUrl(imgUrl);
		}
		String weekDate = "";
		if (hotelRoom.getWeekList()!= null && hotelRoom.getWeekList().length>0) {
			for (int i = 0; i < hotelRoom.getWeekList().length; i++) {
				weekDate+=hotelRoom.getWeekList()[i]+",";
			}
			weekDate=weekDate.length()>0 ? weekDate.substring(0,weekDate.lastIndexOf(",")):"";
		}
		String dayDate = "";
		if (hotelRoom.getDayList()!= null && hotelRoom.getDayList().length>0) {
			for (int i = 0; i < hotelRoom.getDayList().length; i++) {
				dayDate+=hotelRoom.getDayList()[i]+",";
			}
			dayDate=dayDate.length()>0 ? dayDate.substring(0,dayDate.lastIndexOf(",")):"";
		}
		HotelRoomTime hotelRoomTime = hotelRoom.getIsNewRecord()?new HotelRoomTime():hotelRoomTimeMapper.get(hotelRoom.getId().toString());
		hotelRoomTime.setStockAll(hotelRoom.getStockAll());
		hotelRoomTime.setMoney(hotelRoom.getMoney());
		hotelRoomTime.setDateType(hotelRoom.getDateType());
		hotelRoomTime.setBeginDate(hotelRoom.getBeginDate());
		hotelRoomTime.setEndDate(hotelRoom.getEndDate());
		hotelRoomTime.setWeekDate(null);
		hotelRoomTime.setDayDate(null);
		if(hotelRoom.getDateType() == 2) {
			hotelRoomTime.setWeekDate(weekDate);
		} else if(hotelRoom.getDateType() == 3) {
			hotelRoomTime.setDayDate(dayDate);
		}
		if (hotelRoom.getIsNewRecord()){
			hotelRoom.preInsert();
			mapper.insert(hotelRoom);
			//生成编号
			hotelRoom.setHoteRoomNo("JDFJ-"+hotelRoom.getAgentid()+'-'+hotelRoom.getId());
			mapper.updateHotelNo(hotelRoom);
			hotelRoomTime.setHotelRoomId(Integer.parseInt(hotelRoom.getId()));
			hotelRoomTime.preInsert();
			hotelRoomTimeMapper.insert(hotelRoomTime);
			
		}else{
			User user = UserUtils.getUser();
			if(user.getUserType()==2) {
				hotelRoom.setStatus(1);
			}
			 hotelRoom.preUpdate();
			 mapper.update(hotelRoom);
			 hotelRoomTime.preUpdate();
			 hotelRoomTimeMapper.update(hotelRoomTime);
		}
		HotelRoomDate hotelRoomDate = new HotelRoomDate();
		hotelRoomDate.setStockNum(hotelRoomTime.getStockAll());
		hotelRoomDate.setPrice(hotelRoom.getMoney());
		hotelRoomDate.setHotelRoomId(Integer.parseInt(hotelRoom.getId()));
		hotelRoomDateMapper.deleteByHotelRoomId(hotelRoomDate);
		Calendar cal = Calendar.getInstance(); // 获得一个日历
		Integer day= null;
		Integer week= null;
		for(long k =hotelRoom.getBeginDate().getTime();k<hotelRoom.getEndDate().getTime();k+=86400000) {
			cal.setTimeInMillis(k);
			if(hotelRoom.getDateType() == 1) {
				hotelRoomDate.setPriceDate(new Date(k));
				hotelRoomDateMapper.insert(hotelRoomDate);
			}
			else if (hotelRoom.getDateType() == 2 && hotelRoom.getWeekList()!= null && hotelRoom.getWeekList().length > 0) {
					for (int i = 0; i <hotelRoom.getWeekList().length; i++) {
						day = Integer.parseInt(hotelRoom.getWeekList()[i]);
						if(cal.get(Calendar.DAY_OF_WEEK)==day) {
							hotelRoomDate.setPriceDate(new Date(k));
							hotelRoomDateMapper.insert(hotelRoomDate);
						}
					}
			}
			else if (hotelRoom.getDateType() == 3 && hotelRoom.getDayList()!= null && hotelRoom.getDayList().length > 0) {
						for (int i = 0; i <hotelRoom.getDayList().length; i++) {
							week = Integer.parseInt(hotelRoom.getDayList()[i]);
							if(cal.get(Calendar.DAY_OF_MONTH)==week) {
								hotelRoomDate.setPriceDate(new Date(k));
								hotelRoomDateMapper.insert(hotelRoomDate);
							}
						}
					
			}
		}
		
	}
	
}