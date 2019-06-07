/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.entity.linertrip;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.jeeplus.core.persistence.DataEntity;

import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 行程表Entity
 * @author cdq
 * @version 2018-08-14
 */
public class LinerTrip extends DataEntity<LinerTrip> {
	
	private static final long serialVersionUID = 1L;
	private String tripDate;		// 行程时间
	private Integer tripType;		// 行程类型 1 吃饭  2 游玩 3 其他
	private Integer scenicSpotId;		// 景点id
	private String scenicName;		// 景点名称
	private String scenicSpotInfor;		// 景点详情
	private String title;		// 标题
	private String content;		// 详情
	private String imgUrl;		// 图片
	private Integer linerLineId;		// 路线id
	private Date delDate;		// del_date
	private String delBy;		// del_by
	private Integer languageId;		// 语言id
	private String dayCount;//旅行天数
	private List<LinerTrip> contentList; 
	private List<LinerTrip> linerTrip;
	
	public String getDayCount() {
		return dayCount;
	}

	public void setDayCount(String dayCount) {
		this.dayCount = dayCount;
	}

	public List<LinerTrip> getLinerTrip() {
		return linerTrip;
	}

	public void setLinerTrip(List<LinerTrip> linerTrip) {
		this.linerTrip = linerTrip;
	}

	public LinerTrip() {
		super();
		this.setIdType(IDTYPE_AUTO);
	}

	public LinerTrip(String id){
		super(id);
	}

	@JsonFormat(pattern = "HH:mm:ss")
	@ExcelField(title="行程时间", align=2, sort=1)
	public String getTripDate() {
		return tripDate;
	}

	public void setTripDate(String tripDate) {
		this.tripDate = tripDate;
	}
	
	@ExcelField(title="行程类型 1 吃饭  2 游玩 3 其他", align=2, sort=2)
	public Integer getTripType() {
		return tripType;
	}

	public void setTripType(Integer tripType) {
		this.tripType = tripType;
	}
	
	@ExcelField(title="景点id", align=2, sort=3)
	public Integer getScenicSpotId() {
		return scenicSpotId;
	}

	public void setScenicSpotId(Integer scenicSpotId) {
		this.scenicSpotId = scenicSpotId;
	}
	
	@ExcelField(title="景点详情", align=2, sort=4)
	public String getScenicSpotInfor() {
		return scenicSpotInfor;
	}

	public void setScenicSpotInfor(String scenicSpotInfor) {
		this.scenicSpotInfor = scenicSpotInfor;
	}
	
	@ExcelField(title="标题", align=2, sort=5)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@ExcelField(title="详情", align=2, sort=6)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@ExcelField(title="图片", align=2, sort=7)
	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	
	@ExcelField(title="路线id", align=2, sort=8)
	public Integer getLinerLineId() {
		return linerLineId;
	}

	public void setLinerLineId(Integer linerLineId) {
		this.linerLineId = linerLineId;
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
	public String getDelBy() {
		return delBy;
	}

	public void setDelBy(String delBy) {
		this.delBy = delBy;
	}
	
	@ExcelField(title="语言id", align=2, sort=16)
	public Integer getLanguageId() {
		return languageId;
	}

	public void setLanguageId(Integer languageId) {
		this.languageId = languageId;
	}

	public String getScenicName() {
		return scenicName;
	}

	public List<LinerTrip> getContentList() {
		return contentList;
	}

	public void setScenicName(String scenicName) {
		this.scenicName = scenicName;
	}

	public void setContentList(List<LinerTrip> contentList) {
		this.contentList = contentList;
	}
	
}