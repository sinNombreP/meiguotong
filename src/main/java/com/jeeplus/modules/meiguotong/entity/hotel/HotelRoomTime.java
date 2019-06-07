/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.entity.hotel;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeeplus.common.utils.excel.annotation.ExcelField;
import com.jeeplus.core.persistence.DataEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 酒店类型设置Entity
 * @author psz
 * @version 2018-08-21
 */
public class HotelRoomTime extends DataEntity<HotelRoomTime> {
	
	private static final long serialVersionUID = 1L;
	private Integer stockAll;		// 库存
	private BigDecimal money;		// 价格
	private Integer hotelRoomId;		// 酒店房间id
	private Integer dateType;		// 日期类型1.所有日期2.按星期3.按号数
	private Date beginDate;		// 选择所有日期的开始时间
	private Date endDate;		// 选择所有日期的结束时间
	private String weekDate;		// 选择的星期
	private String dayDate;		// 选择的号数
	private String delDate;		// 删除时间
	private String delBy;		// 删除人
	
	public HotelRoomTime() {
		super();
		this.setIdType(IDTYPE_AUTO);
	}

	public HotelRoomTime(String id){
		super(id);
	}

	@ExcelField(title="库存", align=2, sort=1)
	public Integer getStockAll() {
		return stockAll;
	}

	public void setStockAll(Integer stockAll) {
		this.stockAll = stockAll;
	}
	
	@ExcelField(title="价格", align=2, sort=2)
	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}
	
	@ExcelField(title="酒店id", align=2, sort=3)
	public Integer getHotelRoomId() {
		return hotelRoomId;
	}

	public void setHotelRoomId(Integer hotelRoomId) {
		this.hotelRoomId = hotelRoomId;
	}
	
	@ExcelField(title="日期类型1.所有日期2.按星期3.按号数", align=2, sort=4)
	public Integer getDateType() {
		return dateType;
	}

	public void setDateType(Integer dateType) {
		this.dateType = dateType;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@ExcelField(title="选择所有日期的开始时间", align=2, sort=5)
	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@ExcelField(title="选择所有日期的结束时间", align=2, sort=6)
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	@ExcelField(title="选择的星期", align=2, sort=7)
	public String getWeekDate() {
		return weekDate;
	}

	public void setWeekDate(String weekDate) {
		this.weekDate = weekDate;
	}
	
	@ExcelField(title="选择的号数", align=2, sort=8)
	public String getDayDate() {
		return dayDate;
	}

	public void setDayDate(String dayDate) {
		this.dayDate = dayDate;
	}
	
	@ExcelField(title="删除时间", align=2, sort=14)
	public String getDelDate() {
		return delDate;
	}

	public void setDelDate(String delDate) {
		this.delDate = delDate;
	}
	
	@ExcelField(title="删除人", align=2, sort=15)
	public String getDelBy() {
		return delBy;
	}

	public void setDelBy(String delBy) {
		this.delBy = delBy;
	}
	
}