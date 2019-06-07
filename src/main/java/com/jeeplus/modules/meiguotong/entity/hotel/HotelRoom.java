/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.entity.hotel;


import com.jeeplus.common.utils.excel.annotation.ExcelField;
import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.modules.meiguotong.entity.hotelroomdevice.HotelRoomDevice;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 酒店管理Entity
 * @author PSZ
 * @version 2018-08-21
 */
public class HotelRoom extends DataEntity<HotelRoom> {
	
	private static final long serialVersionUID = 1L;
	private String roomName;		// 酒店房间名称
	private Integer hotelId;		// 酒店id
	private Integer people;        //最大可入住人数
	private String imgUrl;		// 酒店房间照片
	private String hotelRoomDeviceId;		// 房间设施id  多个用逗号隔开
	private String hotelRoomDeviceName;		// 房间设施名称  多个用逗号隔开
	private String content;		// 详细介绍
	private String otherInfo;		// 其他设置
	private String delDate;		// 删除时间
	private String delBy;		// 删除人
	private String hoteRoomNo;		// 编号
	private Integer status;		// 状态1审核中2已拒绝3正常4禁用
	
	private String name;		// 酒店名称
	private Integer cityId;		// 城市id
	private String cityName;		// 城市名称
	private Integer level;		// 酒店星级
	
	private BigDecimal money;		// 价格
	private Integer stockAll;		// 库存
	private Integer dateType;		// 日期类型1.所有日期2.按星期3.按号数
	private Date beginDate;		// 选择所有日期的开始时间
	private Date endDate;		// 选择所有日期的结束时间
	private String weekDate;		// 选择的星期
	private String dayDate;		// 选择的号数
	
	private String[] weekList;  //星期
	private String[] dayList; //天数
	private String[] imgSrc;//图片
			
	private List<HotelRoom> hotelRoomList;  //选择城市及城市下面的酒店
	private BigDecimal price;
	private Integer stockNum;  
	
	private Integer languageid;   

	private Date date;		//日期
	private List<HotelRoomDevice> hotelRoomDeviceList; //房间设施集合
	
	
	public String getHotelRoomDeviceName() {
		return hotelRoomDeviceName;
	}

	public void setHotelRoomDeviceName(String hotelRoomDeviceName) {
		this.hotelRoomDeviceName = hotelRoomDeviceName;
	}

	public String[] getWeekList() {
		return weekList;
	}

	public String[] getImgSrc() {
		return imgSrc;
	}

	public void setImgSrc(String[] imgSrc) {
		this.imgSrc = imgSrc;
	}

	public void setWeekList(String[] weekList) {
		this.weekList = weekList;
	}

	public String[] getDayList() {
		return dayList;
	}

	public void setDayList(String[] dayList) {
		this.dayList = dayList;
	}

	public BigDecimal getMoney() {
		return money;
	}

	public Integer getDateType() {
		return dateType;
	}

	public void setDateType(Integer dateType) {
		this.dateType = dateType;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getWeekDate() {
		return weekDate;
	}

	public void setWeekDate(String weekDate) {
		this.weekDate = weekDate;
	}

	public String getDayDate() {
		return dayDate;
	}

	public void setDayDate(String dayDate) {
		this.dayDate = dayDate;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	public Integer getStockAll() {
		return stockAll;
	}

	public void setStockAll(Integer stockAll) {
		this.stockAll = stockAll;
	}

	public HotelRoom() {
		super();
		this.setIdType(IDTYPE_AUTO);
	}

	public List<HotelRoom> getHotelRoomList() {
		return hotelRoomList;
	}

	public void setHotelRoomList(List<HotelRoom> hotelRoomList) {
		this.hotelRoomList = hotelRoomList;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getHoteRoomNo() {
		return hoteRoomNo;
	}

	public void setHoteRoomNo(String hoteRoomNo) {
		this.hoteRoomNo = hoteRoomNo;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public HotelRoom(String id){
		super(id);
	}

	@ExcelField(title="酒店房间名称", align=2, sort=1)
	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
	
	@ExcelField(title="酒店id", align=2, sort=2)
	public Integer getHotelId() {
		return hotelId;
	}

	public void setHotelId(Integer hotelId) {
		this.hotelId = hotelId;
	}
	
	@ExcelField(title="酒店房间照片", align=2, sort=3)
	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	
	@ExcelField(title="房间设施id  多个用逗号隔开", align=2, sort=4)
	public String getHotelRoomDeviceId() {
		return hotelRoomDeviceId;
	}

	public void setHotelRoomDeviceId(String hotelRoomDeviceId) {
		this.hotelRoomDeviceId = hotelRoomDeviceId;
	}
	
	@ExcelField(title="详细介绍", align=2, sort=5)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@ExcelField(title="其他设置", align=2, sort=6)
	public String getOtherInfo() {
		return otherInfo;
	}

	public void setOtherInfo(String otherInfo) {
		this.otherInfo = otherInfo;
	}
	
	@ExcelField(title="删除时间", align=2, sort=13)
	public String getDelDate() {
		return delDate;
	}

	public void setDelDate(String delDate) {
		this.delDate = delDate;
	}
	
	@ExcelField(title="删除人", align=2, sort=14)
	public String getDelBy() {
		return delBy;
	}

	public void setDelBy(String delBy) {
		this.delBy = delBy;
	}

	public Integer getPeople() {
		return people;
	}

	public void setPeople(Integer people) {
		this.people = people;
	}

	public Date getDate() {
		return date;
	}


	public void setDate(Date date) {
		this.date = date;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public Integer getStockNum() {
		return stockNum;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public void setStockNum(Integer stockNum) {
		this.stockNum = stockNum;
	}

	public List<HotelRoomDevice> getHotelRoomDeviceList() {
		return hotelRoomDeviceList;
	}

	public void setHotelRoomDeviceList(List<HotelRoomDevice> hotelRoomDeviceList) {
		this.hotelRoomDeviceList = hotelRoomDeviceList;
	}

	public Integer getLanguageid() {
		return languageid;
	}

	public void setLanguageid(Integer languageid) {
		this.languageid = languageid;
	}
	
}