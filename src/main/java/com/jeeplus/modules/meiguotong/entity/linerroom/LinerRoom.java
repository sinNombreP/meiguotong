/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.entity.linerroom;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeeplus.common.utils.excel.annotation.ExcelField;
import com.jeeplus.core.persistence.DataEntity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 邮轮房间表Entity
 * @author cdq
 * @version 2018-08-14
 */
public class LinerRoom extends DataEntity<LinerRoom> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 房间类型名称
	private String spec;		// 房间规格
	private String floor;		// 楼层
	private Integer checkInType;		// 入住类型  1 代表可入住  2 代表必须入住
	private Integer minPeopleNumber;		// 最少入住人数
	private Integer maxPeopleNumber;		// 最大入住人数
	private Integer peopleNumber;		// 入住人数
	private BigDecimal price;		// 价格 按照人头计算
	private Integer roomNumber;		// 房间数量
	private String imgUrl;		// 房间照片 多张照片隔开
	private Integer linerLineId;		// 邮轮路线id
	private Date delDate;		// del_date
	private String delBy;		// del_by
	private Integer languageId;		// 语言id
	

	private List<LinerRoom> linerRoomList;
	public List<LinerRoom> getLinerRoomList() {
		return linerRoomList;
	}
	
	public void setLinerRoomList(List<LinerRoom> linerRoomList) {
		this.linerRoomList = linerRoomList;
	}

	public LinerRoom() {
		super();
		this.setIdType(IDTYPE_AUTO);
	}

	public LinerRoom(String id){
		super(id);
	}

	@ExcelField(title="房间类型名称", align=2, sort=1)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ExcelField(title="房间规格", align=2, sort=2)
	public String getSpec() {
		return spec;
	}

	public void setSpec(String spec) {
		this.spec = spec;
	}
	
	@ExcelField(title="楼层", align=2, sort=3)
	public String getFloor() {
		return floor;
	}

	public void setFloor(String floor) {
		this.floor = floor;
	}
	
	@ExcelField(title="入住类型  1 代表可入住  2 代表必须入住", align=2, sort=4)
	public Integer getCheckInType() {
		return checkInType;
	}

	public void setCheckInType(Integer checkInType) {
		this.checkInType = checkInType;
	}
	
	@ExcelField(title="最少入住人数", align=2, sort=5)
	public Integer getMinPeopleNumber() {
		return minPeopleNumber;
	}

	public void setMinPeopleNumber(Integer minPeopleNumber) {
		this.minPeopleNumber = minPeopleNumber;
	}
	
	@ExcelField(title="最大入住人数", align=2, sort=6)
	public Integer getMaxPeopleNumber() {
		return maxPeopleNumber;
	}

	public void setMaxPeopleNumber(Integer maxPeopleNumber) {
		this.maxPeopleNumber = maxPeopleNumber;
	}
	
	@ExcelField(title="入住人数", align=2, sort=7)
	public Integer getPeopleNumber() {
		return peopleNumber;
	}

	public void setPeopleNumber(Integer peopleNumber) {
		this.peopleNumber = peopleNumber;
	}
	
	@ExcelField(title="价格 按照人头计算", align=2, sort=8)
	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
	@ExcelField(title="房间数量", align=2, sort=9)
	public Integer getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(Integer roomNumber) {
		this.roomNumber = roomNumber;
	}
	
	@ExcelField(title="房间照片 多张照片隔开", align=2, sort=10)
	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	
	@ExcelField(title="邮轮路线id", align=2, sort=11)
	public Integer getLinerLineId() {
		return linerLineId;
	}

	public void setLinerLineId(Integer linerLineId) {
		this.linerLineId = linerLineId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="del_date", align=2, sort=16)
	public Date getDelDate() {
		return delDate;
	}

	public void setDelDate(Date delDate) {
		this.delDate = delDate;
	}
	
	@ExcelField(title="del_by", align=2, sort=17)
	public String getDelBy() {
		return delBy;
	}

	public void setDelBy(String delBy) {
		this.delBy = delBy;
	}
	
	@ExcelField(title="语言id", align=2, sort=19)
	public Integer getLanguageId() {
		return languageId;
	}

	public void setLanguageId(Integer languageId) {
		this.languageId = languageId;
	}
	
}