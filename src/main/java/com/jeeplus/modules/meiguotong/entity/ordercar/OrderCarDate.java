/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.entity.ordercar;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 包车订单日期详情Entity
 * @author dong
 * @version 2019-05-08
 */
public class OrderCarDate extends DataEntity<OrderCarDate> {
	
	private static final long serialVersionUID = 1L;
	private Integer orderid;		// 订单id
	private Integer serviceid;		// 业务id
	private String title;		// 业务名称
	private Integer cityid;		// 出发城市id
	private String city;		// 出发城市名称
	private Integer  endCityid; //到达城市id
	private String endCity;		//到达城市名称
	private String airNum;		// 航班号
	private String sendAirport;		// 飞机起飞机场
	private Date sendDate;		// 飞机起飞时间
	private String reachAirport;		// 到达机场
	private Date reachDate;		// 到达时间
	private Integer day;		// 第几天
	private Date dayTime;		// 日期
	private Integer range; //行程类型1.接机2.本地城市3.附近城市
	
	private List<OrderCarDate> orderCarDates;
	
	public OrderCarDate() {
		super();
		this.setIdType(IDTYPE_AUTO);
	}

	public OrderCarDate(String id){
		super(id);
	}

	@ExcelField(title="订单id", align=2, sort=1)
	public Integer getOrderid() {
		return orderid;
	}

	public void setOrderid(Integer orderid) {
		this.orderid = orderid;
	}
	
	@ExcelField(title="业务id", align=2, sort=2)
	public Integer getServiceid() {
		return serviceid;
	}

	public void setServiceid(Integer serviceid) {
		this.serviceid = serviceid;
	}
	
	@ExcelField(title="业务名称", align=2, sort=3)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@ExcelField(title="城市id", align=2, sort=4)
	public Integer getCityid() {
		return cityid;
	}

	public void setCityid(Integer cityid) {
		this.cityid = cityid;
	}
	
	@ExcelField(title="城市名称", align=2, sort=5)
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	@ExcelField(title="航班号", align=2, sort=6)
	public String getAirNum() {
		return airNum;
	}

	public void setAirNum(String airNum) {
		this.airNum = airNum;
	}
	
	@ExcelField(title="飞机起飞机场", align=2, sort=7)
	public String getSendAirport() {
		return sendAirport;
	}

	public void setSendAirport(String sendAirport) {
		this.sendAirport = sendAirport;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="飞机起飞时间", align=2, sort=8)
	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}
	
	@ExcelField(title="到达机场", align=2, sort=9)
	public String getReachAirport() {
		return reachAirport;
	}

	public void setReachAirport(String reachAirport) {
		this.reachAirport = reachAirport;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="到达时间", align=2, sort=10)
	public Date getReachDate() {
		return reachDate;
	}

	public void setReachDate(Date reachDate) {
		this.reachDate = reachDate;
	}
	
	@ExcelField(title="第几天", align=2, sort=11)
	public Integer getDay() {
		return day;
	}

	public void setDay(Integer day) {
		this.day = day;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="日期", align=2, sort=12)
	public Date getDayTime() {
		return dayTime;
	}

	public void setDayTime(Date dayTime) {
		this.dayTime = dayTime;
	}

	public List<OrderCarDate> getOrderCarDates() {
		return orderCarDates;
	}

	public void setOrderCarDates(List<OrderCarDate> orderCarDates) {
		this.orderCarDates = orderCarDates;
	}

	public Integer getEndCityid() {
		return endCityid;
	}

	public String getEndCity() {
		return endCity;
	}

	public void setEndCityid(Integer endCityid) {
		this.endCityid = endCityid;
	}

	public void setEndCity(String endCity) {
		this.endCity = endCity;
	}

	public Integer getRange() {
		return range;
	}

	public void setRange(Integer range) {
		this.range = range;
	}
	
}