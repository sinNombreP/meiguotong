/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.entity.travel;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 旅游定制Entity
 * @author psz
 * @version 2018-08-27
 */
public class TravelCustomization extends DataEntity<TravelCustomization> {
	
	private static final long serialVersionUID = 1L;
	private Integer language;		// 语言
	private Integer city;		// 城市id
	private String img;		// 图片
	private String name;		// 产品名称
	private Integer dayNum;		// 旅游天数
	private Integer status;		// 状态 1 正常 2禁用
	private Date delDate;		// del_date
	private String delBy;		// 删除人

	private String cityName;  //城市名称
	private String searchContent;  //搜索内容
	private Integer cityNum;  //城市个数
	private Integer scenicNum;  //景点个数
	private Integer lookNum;		// 查看数量
	private Integer collectionNum;		// 收藏数量
	private String travelIds;		//定制旅游id集合
	private String className;		//分类名称
	
	private List<TravelDetails> travelDetailsList;  //旅游定制详情
	private String ids;   //模块选择的ID
	
	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}
	public TravelCustomization() {
		super();
		this.setIdType(IDTYPE_AUTO);
	}

	public Integer getLookNum() {
		return lookNum;
	}

	public void setLookNum(Integer lookNum) {
		this.lookNum = lookNum;
	}

	public Integer getCollectionNum() {
		return collectionNum;
	}

	public void setCollectionNum(Integer collectionNum) {
		this.collectionNum = collectionNum;
	}

	public List<TravelDetails> getTravelDetailsList() {
		return travelDetailsList;
	}

	public void setTravelDetailsList(List<TravelDetails> travelDetailsList) {
		this.travelDetailsList = travelDetailsList;
	}

	public Integer getCityNum() {
		return cityNum;
	}

	public void setCityNum(Integer cityNum) {
		this.cityNum = cityNum;
	}

	public Integer getScenicNum() {
		return scenicNum;
	}

	public void setScenicNum(Integer scenicNum) {
		this.scenicNum = scenicNum;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSearchContent() {
		return searchContent;
	}

	public void setSearchContent(String searchContent) {
		this.searchContent = searchContent;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public TravelCustomization(String id){
		super(id);
	}

	@ExcelField(title="语言", align=2, sort=1)
	public Integer getLanguage() {
		return language;
	}

	public void setLanguage(Integer language) {
		this.language = language;
	}
	
	@ExcelField(title="城市", align=2, sort=2)
	public Integer getCity() {
		return city;
	}

	public void setCity(Integer city) {
		this.city = city;
	}
	
	@ExcelField(title="图片", align=2, sort=3)
	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}
	
	@ExcelField(title="旅游天数", align=2, sort=4)
	public Integer getDayNum() {
		return dayNum;
	}

	public void setDayNum(Integer dayNum) {
		this.dayNum = dayNum;
	}
	
	@ExcelField(title="状态 1 正常 2禁用", align=2, sort=5)
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="del_date", align=2, sort=11)
	public Date getDelDate() {
		return delDate;
	}

	public void setDelDate(Date delDate) {
		this.delDate = delDate;
	}
	
	@ExcelField(title="删除人", align=2, sort=12)
	public String getDelBy() {
		return delBy;
	}

	public void setDelBy(String delBy) {
		this.delBy = delBy;
	}

	public String getTravelIds() {
		return travelIds;
	}

	public void setTravelIds(String travelIds) {
		this.travelIds = travelIds;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	
	
}