/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.entity.travel;


import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeeplus.common.utils.excel.annotation.ExcelField;
import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.modules.meiguotong.entity.comcity.ComCity;
import com.jeeplus.modules.meiguotong.entity.hotel.OrderHotel;
import com.jeeplus.modules.meiguotong.entity.order.OrderSys;
import com.jeeplus.modules.meiguotong.entity.orderhotelroom.OrderHotelRoom;
import com.jeeplus.modules.meiguotong.entity.orderscenicspot.OrderScenicSpot;

/**
 * 定制订单详情Entity
 * @author psz
 * @version 2018-08-29
 */
public class OrderTravelDetails extends DataEntity<OrderTravelDetails> {
	
	private static final long serialVersionUID = 1L;
	private Integer orderTrvelId;		// 旅游定制订单id
	private Integer day;		// 第几天
	private Integer citySort;   //城市排序
	private Integer sort;		// 城市行程排序（当天排序）
	private Integer city;		// 城市id
	private String scenic;		// 景点id
	private String img;		// 城市图片
	private Date useDate;  //使用时间
	private Integer type; //'类型1.旅游2.商务'
	private String busiInfo; //'商务信息（地址+leixing+时间段）'
	private String busiAddress; //'商务详细地址'
	private Date busiBeginDate; //'商务开始时间'
	private Date busiEndDate; //'商务结束时间'
	private String busiItemId; //'商务定制项目id'
	private String busiItem; //'商务定制项目'
	private Integer busiAdultNum; //'商务大人数量'
	private Integer busiChildNum; //'商务孩子数量'
	private String busiContent; //'商务详细'
	
	private Integer orderScenicId; //景点订单
	private BigDecimal price;		//价格
	
	private String cityName; //城市名称
	private String scenicContent; //景点名称
	private List<OrderTravelDetails> orderTravelDetailsList;  //每一天的具体行程
	private OrderHotel orderHotel;  //每天的酒店信息
	private OrderScenicSpot orderScenicSpot;  //景点订单
	
	private List<ComCity> citys;//每天的城市
	
	private List<OrderHotelRoom> hotels;//旅游定制酒店
	
	public OrderHotel getOrderHotel() {
		return orderHotel;
	}

	public void setOrderHotel(OrderHotel orderHotel) {
		this.orderHotel = orderHotel;
	}

	public List<OrderTravelDetails> getOrderTravelDetailsList() {
		return orderTravelDetailsList;
	}

	public void setOrderTravelDetailsList(List<OrderTravelDetails> orderTravelDetailsList) {
		this.orderTravelDetailsList = orderTravelDetailsList;
	}

	public OrderTravelDetails() {
		super();
		this.setIdType(IDTYPE_AUTO);
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getScenicContent() {
		return scenicContent;
	}

	public void setScenicContent(String scenicContent) {
		this.scenicContent = scenicContent;
	}

	public OrderTravelDetails(String id){
		super(id);
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getUseDate() {
		return useDate;
	}

	public void setUseDate(Date useDate) {
		this.useDate = useDate;
	}

	@ExcelField(title="旅游定制订单id", align=2, sort=1)
	public Integer getOrderTrvelId() {
		return orderTrvelId;
	}

	public void setOrderTrvelId(Integer orderTrvelId) {
		this.orderTrvelId = orderTrvelId;
	}
	
	@ExcelField(title="第几天", align=2, sort=2)
	public Integer getDay() {
		return day;
	}

	public void setDay(Integer day) {
		this.day = day;
	}
	
	@ExcelField(title="排序（当天排序）", align=2, sort=3)
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
	
	@ExcelField(title="城市id", align=2, sort=4)
	public Integer getCity() {
		return city;
	}

	public void setCity(Integer city) {
		this.city = city;
	}
	
	@ExcelField(title="景点id", align=2, sort=5)
	public String getScenic() {
		return scenic;
	}

	public void setScenic(String scenic) {
		this.scenic = scenic;
	}
	
	@ExcelField(title="城市图片", align=2, sort=6)
	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public Integer getType() {
		return type;
	}

	public String getBusiInfo() {
		return busiInfo;
	}

	public String getBusiAddress() {
		return busiAddress;
	}

	public Date getBusiBeginDate() {
		return busiBeginDate;
	}

	public Date getBusiEndDate() {
		return busiEndDate;
	}

	public String getBusiItemId() {
		return busiItemId;
	}

	public String getBusiItem() {
		return busiItem;
	}

	public Integer getBusiAdultNum() {
		return busiAdultNum;
	}

	public Integer getBusiChildNum() {
		return busiChildNum;
	}

	public String getBusiContent() {
		return busiContent;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public void setBusiInfo(String busiInfo) {
		this.busiInfo = busiInfo;
	}

	public void setBusiAddress(String busiAddress) {
		this.busiAddress = busiAddress;
	}

	public void setBusiBeginDate(Date busiBeginDate) {
		this.busiBeginDate = busiBeginDate;
	}

	public void setBusiEndDate(Date busiEndDate) {
		this.busiEndDate = busiEndDate;
	}

	public void setBusiItemId(String busiItemId) {
		this.busiItemId = busiItemId;
	}

	public void setBusiItem(String busiItem) {
		this.busiItem = busiItem;
	}

	public void setBusiAdultNum(Integer busiAdultNum) {
		this.busiAdultNum = busiAdultNum;
	}

	public void setBusiChildNum(Integer busiChildNum) {
		this.busiChildNum = busiChildNum;
	}

	public void setBusiContent(String busiContent) {
		this.busiContent = busiContent;
	}

	public Integer getCitySort() {
		return citySort;
	}

	public void setCitySort(Integer citySort) {
		this.citySort = citySort;
	}

	public Integer getOrderScenicId() {
		return orderScenicId;
	}

	public void setOrderScenicId(Integer orderScenicId) {
		this.orderScenicId = orderScenicId;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public OrderScenicSpot getOrderScenicSpot() {
		return orderScenicSpot;
	}

	public void setOrderScenicSpot(OrderScenicSpot orderScenicSpot) {
		this.orderScenicSpot = orderScenicSpot;
	}

	public List<ComCity> getCitys() {
		return citys;
	}

	public void setCitys(List<ComCity> citys) {
		this.citys = citys;
	}

	public List<OrderHotelRoom> getHotels() {
		return hotels;
	}

	public void setHotels(List<OrderHotelRoom> hotels) {
		this.hotels = hotels;
	}
	
	
	
	
}