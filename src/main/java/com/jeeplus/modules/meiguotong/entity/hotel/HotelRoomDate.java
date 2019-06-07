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
 * 酒店时间设置Entity
 * @author psz
 * @version 2018-08-21
 */
public class HotelRoomDate extends DataEntity<HotelRoomDate> {
	
	private static final long serialVersionUID = 1L;
	private Date priceDate;		// 设置价格日期
	private BigDecimal price;		// 价格
	private Integer stockNum;		// 库存
	private Integer hotelRoomId;		// 酒店房间id
	
	public HotelRoomDate() {
		super();
		this.setIdType(IDTYPE_AUTO);
	}

	public HotelRoomDate(String id){
		super(id);
	}

	public Integer getHotelRoomId() {
		return hotelRoomId;
	}

	public void setHotelRoomId(Integer hotelRoomId) {
		this.hotelRoomId = hotelRoomId;
	}

	@JsonFormat(pattern = "yyyy-MM-dd")
	@ExcelField(title="设置价格日期", align=2, sort=1)
	public Date getPriceDate() {
		return priceDate;
	}

	public void setPriceDate(Date priceDate) {
		this.priceDate = priceDate;
	}
	
	@ExcelField(title="价格", align=2, sort=2)
	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
	@ExcelField(title="库存", align=2, sort=3)
	public Integer getStockNum() {
		return stockNum;
	}

	public void setStockNum(Integer stockNum) {
		this.stockNum = stockNum;
	}
	
}