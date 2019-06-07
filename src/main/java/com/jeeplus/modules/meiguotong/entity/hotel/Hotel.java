/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.entity.hotel;


import com.jeeplus.common.utils.excel.annotation.ExcelField;
import com.jeeplus.core.persistence.DataEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 酒店管理Entity
 * @author PSZ
 * @version 2018-08-20
 */
public class Hotel extends DataEntity<Hotel> {
	
	private static final long serialVersionUID = 1L;
	private String hotelNo;		// 编号
	private String imgUrl;		// 图片
	private Integer level;		// 酒店星级
	private Integer cityId;		// 城市id
	private String cityName;		// 城市名称
	private String longitude;		// 经度
	private String latitude;		// 纬度
	private Integer status;		// 状态 1审核中2已拒绝3正常4禁用
	private String address;		// 详细地址
	private String name;		// 酒店名称
	private Integer languageId;		// 语言id
	private String delDate;		// 删除时间
	private String delBy;		// 删除人
	
	private String stars;    //星级，接口用，多个用，隔开
	private Integer oneNum;		// 单人间
	private Integer twoNum;		// 双人间
	private Integer threeNum;		// 三人间
	private Integer fourNum;		// 四人间
	private Date date;		// 日期，搜索接口用

	private BigDecimal price;    //价格
	
	public Hotel() {
		super();
		this.setIdType(IDTYPE_AUTO);
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getHotelNo() {
		return hotelNo;
	}

	public void setHotelNo(String hotelNo) {
		this.hotelNo = hotelNo;
	}

	public Hotel(String id){
		super(id);
	}

	@ExcelField(title="图片", align=2, sort=1)
	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	
	@ExcelField(title="酒店星级", align=2, sort=2)
	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}
	
	@ExcelField(title="城市id", align=2, sort=3)
	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}
	
	@ExcelField(title="经度", align=2, sort=4)
	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	
	@ExcelField(title="纬度", align=2, sort=5)
	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	
	@ExcelField(title="状态 0 禁用 1 正常  2 审核中 4 已拒绝", align=2, sort=6)
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@ExcelField(title="详细地址", align=2, sort=7)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	@ExcelField(title="酒店名称", align=2, sort=8)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ExcelField(title="语言id", align=2, sort=9)
	public Integer getLanguageId() {
		return languageId;
	}

	public void setLanguageId(Integer languageId) {
		this.languageId = languageId;
	}
	
	@ExcelField(title="删除时间", align=2, sort=15)
	public String getDelDate() {
		return delDate;
	}

	public void setDelDate(String delDate) {
		this.delDate = delDate;
	}
	
	@ExcelField(title="删除人", align=2, sort=16)
	public String getDelBy() {
		return delBy;
	}

	public void setDelBy(String delBy) {
		this.delBy = delBy;
	}

	public Integer getOneNum() {
		return oneNum;
	}

	public Integer getTwoNum() {
		return twoNum;
	}

	public Integer getThreeNum() {
		return threeNum;
	}

	public Integer getFourNum() {
		return fourNum;
	}

	public void setOneNum(Integer oneNum) {
		this.oneNum = oneNum;
	}

	public void setTwoNum(Integer twoNum) {
		this.twoNum = twoNum;
	}

	public void setThreeNum(Integer threeNum) {
		this.threeNum = threeNum;
	}

	public void setFourNum(Integer fourNum) {
		this.fourNum = fourNum;
	}

	public String getStars() {
		return stars;
	}

	public void setStars(String stars) {
		this.stars = stars;
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

	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
}