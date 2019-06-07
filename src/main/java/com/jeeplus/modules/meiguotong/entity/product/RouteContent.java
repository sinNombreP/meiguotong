/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.entity.product;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 参团内容Entity
 * @author psz
 * @version 2018-08-14
 */
public class RouteContent extends DataEntity<RouteContent> {
	
	private static final long serialVersionUID = 1L;
	private Integer routeid;		// 路线/参团id
	private Integer dayCount;		// 第几天
	private String tripDate;		// 行程时间
	private String tripType;		// 行程类型
	private String tripScenic;		// 行程景点
	private String title;		// 标题
	private String infor;		// 详情
	private String img;		// 图片
	
	private List<RouteContent> contentList; //每天行程集合

	private String scenicName;		// 行程景点名称
	private String scenicInfor;		// 行程景点详情
	
	public RouteContent() {
		super();
		this.setIdType(IDTYPE_AUTO);
	}

	public RouteContent(String id){
		super(id);
	}

	@ExcelField(title="路线/参团id", align=2, sort=1)
	public Integer getRouteid() {
		return routeid;
	}

	public void setRouteid(Integer routeid) {
		this.routeid = routeid;
	}
	
	@ExcelField(title="第几天", align=2, sort=2)
	public Integer getDayCount() {
		return dayCount;
	}

	public void setDayCount(Integer dayCount) {
		this.dayCount = dayCount;
	}
	
//	@JsonFormat(pattern = "HH:mm:ss")
	@ExcelField(title="行程时间", align=2, sort=3)
	public String getTripDate() {
		return tripDate;
	}

	public void setTripDate(String tripDate) {
		this.tripDate = tripDate;
	}
	
	@ExcelField(title="行程类型", align=2, sort=4)
	public String getTripType() {
		return tripType;
	}

	public void setTripType(String tripType) {
		this.tripType = tripType;
	}
	
	@ExcelField(title="行程景点", align=2, sort=5)
	public String getTripScenic() {
		return tripScenic;
	}

	public void setTripScenic(String tripScenic) {
		this.tripScenic = tripScenic;
	}
	
	@ExcelField(title="标题", align=2, sort=6)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@ExcelField(title="详情", align=2, sort=7)
	public String getInfor() {
		return infor;
	}

	public void setInfor(String infor) {
		this.infor = infor;
	}
	
	@ExcelField(title="图片", align=2, sort=8)
	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public List<RouteContent> getContentList() {
		return contentList;
	}

	public void setContentList(List<RouteContent> contentList) {
		this.contentList = contentList;
	}

	public String getScenicName() {
		return scenicName;
	}

	public String getScenicInfor() {
		return scenicInfor;
	}

	public void setScenicName(String scenicName) {
		this.scenicName = scenicName;
	}

	public void setScenicInfor(String scenicInfor) {
		this.scenicInfor = scenicInfor;
	}
	
}