/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.entity.comcitytravel;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.modules.meiguotong.entity.travel.TravelCustomization;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 目的地关联旅游定制Entity
 * @author dong
 * @version 2019-03-22
 */
public class ComCityTravel extends DataEntity<ComCityTravel> {
	
	private static final long serialVersionUID = 1L;
	private Integer cityid;		// 城市id
	private String classname;		// 分类名称
	private String travelid;		// 旅游定制ID
	private Date creatDate;		// 时间
	
	private List<TravelCustomization> travel;//定制集合
	
	public ComCityTravel() {
		super();
		this.setIdType(IDTYPE_AUTO);
	}

	public ComCityTravel(String id){
		super(id);
	}

	@ExcelField(title="城市id", align=2, sort=1)
	public Integer getCityid() {
		return cityid;
	}

	public void setCityid(Integer cityid) {
		this.cityid = cityid;
	}
	
	@ExcelField(title="分类名称", align=2, sort=2)
	public String getClassname() {
		return classname;
	}

	public void setClassname(String classname) {
		this.classname = classname;
	}
	
	@ExcelField(title="旅游定制ID", align=2, sort=3)
	public String getTravelid() {
		return travelid;
	}

	public void setTravelid(String travelid) {
		this.travelid = travelid;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="时间", align=2, sort=4)
	public Date getCreatDate() {
		return creatDate;
	}

	public void setCreatDate(Date creatDate) {
		this.creatDate = creatDate;
	}

	public List<TravelCustomization> getTravel() {
		return travel;
	}

	public void setTravel(List<TravelCustomization> travel) {
		this.travel = travel;
	}
	
}