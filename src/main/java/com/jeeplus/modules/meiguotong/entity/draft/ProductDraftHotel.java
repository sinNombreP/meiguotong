/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.entity.draft;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 草稿酒店Entity
 * @author dong
 * @version 2018-09-14
 */
public class ProductDraftHotel extends DataEntity<ProductDraftHotel> {
	
	private static final long serialVersionUID = 1L;
	private Integer day;		// 第几天
	private Integer hotelid;		// 酒店id
	private Integer roomid;		// 放间id
	private Integer num;		// 房间数量
	private Integer draftid;		// 草稿id
	
	public ProductDraftHotel() {
		super();
		this.setIdType(IDTYPE_AUTO);
	}

	public ProductDraftHotel(String id){
		super(id);
	}

	@ExcelField(title="酒店id", align=2, sort=1)
	public Integer getHotelid() {
		return hotelid;
	}

	public void setHotelid(Integer hotelid) {
		this.hotelid = hotelid;
	}
	
	@ExcelField(title="第几天", align=2, sort=2)
	public Integer getDay() {
		return day;
	}

	public void setDay(Integer day) {
		this.day = day;
	}
	
	
	
	@ExcelField(title="草稿id", align=2, sort=7)
	public Integer getDraftid() {
		return draftid;
	}

	public void setDraftid(Integer draftid) {
		this.draftid = draftid;
	}

	public Integer getRoomid() {
		return roomid;
	}

	public Integer getNum() {
		return num;
	}

	public void setRoomid(Integer roomid) {
		this.roomid = roomid;
	}

	public void setNum(Integer num) {
		this.num = num;
	}
	
	
}