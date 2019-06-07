/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.entity.travel;




import java.util.List;

import com.jeeplus.common.utils.excel.annotation.ExcelField;
import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.modules.meiguotong.entity.scenicspot.ScenicSpot;

/**
 * 旅游定制详情Entity
 * @author psz
 * @version 2018-08-27
 */
public class TravelDetails extends DataEntity<TravelDetails> {
	
	private static final long serialVersionUID = 1L;
	private Integer travelid;		// 旅游定制id
	private Integer sort;		// 排序
	private Integer day;		// 第几天
	private Integer city;		// 城市id
	private String scenic;		// 景点id
	private String img;		// 图片

	private String cityid;		// 城市名称
	private String cityName;		// 城市名称
	private String cityImg;		// 城市图片
	private String scenicContent;		// 景点名称

	private List<TravelDetails> travelDetailsList;
	private List<TravelDetails> travelDetails;
	private List<ScenicSpot> scenicSpotList;
	private List<ScenicSpot> scenicSpot;
	
	public TravelDetails() {
		super();
		this.setIdType(IDTYPE_AUTO);
	}

	public String getCityid() {
		return cityid;
	}

	public void setCityid(String cityid) {
		this.cityid = cityid;
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

	public List<TravelDetails> getTravelDetailsList() {
		return travelDetailsList;
	}

	public void setTravelDetailsList(List<TravelDetails> travelDetailsList) {
		this.travelDetailsList = travelDetailsList;
	}

	public TravelDetails(String id){
		super(id);
	}

	@ExcelField(title="旅游定制id", align=2, sort=1)
	public Integer getTravelid() {
		return travelid;
	}

	public void setTravelid(Integer travelid) {
		this.travelid = travelid;
	}
	
	@ExcelField(title="排序", align=2, sort=2)
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
	
	public Integer getDay() {
		return day;
	}

	public void setDay(Integer day) {
		this.day = day;
	}

	@ExcelField(title="城市id", align=2, sort=3)
	public Integer getCity() {
		return city;
	}

	public void setCity(Integer city) {
		this.city = city;
	}
	
	@ExcelField(title="景点id", align=2, sort=4)
	public String getScenic() {
		return scenic;
	}

	public void setScenic(String scenic) {
		this.scenic = scenic;
	}
	
	@ExcelField(title="图片", align=2, sort=5)
	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getCityImg() {
		return cityImg;
	}

	public List<ScenicSpot> getScenicSpotList() {
		return scenicSpotList;
	}

	public void setCityImg(String cityImg) {
		this.cityImg = cityImg;
	}

	public void setScenicSpotList(List<ScenicSpot> scenicSpotList) {
		this.scenicSpotList = scenicSpotList;
	}

	public List<ScenicSpot> getScenicSpot() {
		return scenicSpot;
	}

	public void setScenicSpot(List<ScenicSpot> scenicSpot) {
		this.scenicSpot = scenicSpot;
	}

	public List<TravelDetails> getTravelDetails() {
		return travelDetails;
	}

	public void setTravelDetails(List<TravelDetails> travelDetails) {
		this.travelDetails = travelDetails;
	}
	
}