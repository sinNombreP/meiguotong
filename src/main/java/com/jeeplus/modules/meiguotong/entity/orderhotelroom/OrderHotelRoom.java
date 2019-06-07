/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.entity.orderhotelroom;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeeplus.modules.sys.entity.User;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 酒店订单房间Entity
 * @author dong
 * @version 2018-10-16
 */
public class OrderHotelRoom extends DataEntity<OrderHotelRoom> {
	
	private static final long serialVersionUID = 1L;
	private Integer orderHotelId;		// 酒店订单id
	private Integer roomId;		// 房间id
	private String roomName;		// 房间名称
	private Date roomDate;		// 日期
	private Integer roomDay;		// 定制行程第几天
	private BigDecimal price;		// 价格
	private Integer hotelId;		// 酒店id
	private String name;		// 酒店名称
	private Integer people;		// 最大可入住人数
	private String imgUrl;		// 酒店房间照片
	private String content;		// 详情介绍
	private Date delDate;		// del_date
	private User delBy;		// del_by
	private Integer languageId;		// 语言id
	private Integer num;		// 房间数量
	
	private String hotelName; //酒店名称
	
	List<OrderHotelRoom> orderHotelRooms;
	
	public OrderHotelRoom() {
		super();
		this.setIdType(IDTYPE_AUTO);
	}

	public OrderHotelRoom(String id){
		super(id);
	}

	@ExcelField(title="酒店订单id", align=2, sort=1)
	public Integer getOrderHotelId() {
		return orderHotelId;
	}

	public void setOrderHotelId(Integer orderHotelId) {
		this.orderHotelId = orderHotelId;
	}
	
	@ExcelField(title="房间id", align=2, sort=2)
	public Integer getRoomId() {
		return roomId;
	}

	public void setRoomId(Integer roomId) {
		this.roomId = roomId;
	}
	
	@ExcelField(title="房间名称", align=2, sort=3)
	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
	
	@ExcelField(title="酒店id", align=2, sort=4)
	public Integer getHotelId() {
		return hotelId;
	}

	public void setHotelId(Integer hotelId) {
		this.hotelId = hotelId;
	}
	
	@ExcelField(title="酒店名称", align=2, sort=5)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ExcelField(title="最大可入住人数", align=2, sort=6)
	public Integer getPeople() {
		return people;
	}

	public void setPeople(Integer people) {
		this.people = people;
	}
	
	@ExcelField(title="酒店房间照片", align=2, sort=7)
	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	
	@ExcelField(title="详情介绍", align=2, sort=8)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="del_date", align=2, sort=13)
	public Date getDelDate() {
		return delDate;
	}

	public void setDelDate(Date delDate) {
		this.delDate = delDate;
	}
	
	@ExcelField(title="del_by", align=2, sort=14)
	public User getDelBy() {
		return delBy;
	}

	public void setDelBy(User delBy) {
		this.delBy = delBy;
	}
	
	@ExcelField(title="语言id", align=2, sort=16)
	public Integer getLanguageId() {
		return languageId;
	}

	public void setLanguageId(Integer languageId) {
		this.languageId = languageId;
	}
	
	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Date getRoomDate() {
		return roomDate;
	}

	public Integer getRoomDay() {
		return roomDay;
	}

	public void setRoomDate(Date roomDate) {
		this.roomDate = roomDate;
	}

	public void setRoomDay(Integer roomDay) {
		this.roomDay = roomDay;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getHotelName() {
		return hotelName;
	}

	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}

	public List<OrderHotelRoom> getOrderHotelRooms() {
		return orderHotelRooms;
	}

	public void setOrderHotelRooms(List<OrderHotelRoom> orderHotelRooms) {
		this.orderHotelRooms = orderHotelRooms;
	}
	
	
}