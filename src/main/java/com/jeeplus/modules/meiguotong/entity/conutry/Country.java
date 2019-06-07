/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.entity.conutry;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeeplus.common.utils.excel.annotation.ExcelField;
import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.modules.meiguotong.entity.comcity.ComCity;

import java.util.Date;
import java.util.List;

/**
 * 国家表Entity
 * @author cdq
 * @version 2018-08-09
 */
public class Country extends DataEntity<Country> {
	
	private static final long serialVersionUID = 1L;
	private Integer languageId;		// 关联 语言表主键id
	private Integer status;		// 状态  1 启用 2禁用
	private Date delDate;		// del_date
	private String delBy;		// del_by
	private String title;		// 目的地
	private String titleEn;		// 拼音
	
	private Integer countryid;
	private String countryName;
	private List<ComCity> cityList;		// 拼音
	private String languageName;     //语言名称

	private String value;  //国家ID
	private String label;   //国家名称
	private List<ComCity> children;		// 城市列表

	public Country() {
		super();
		this.setIdType(IDTYPE_AUTO);
	}

	public List<ComCity> getChildren() {
		return children;
	}

	public void setChildren(List<ComCity> children) {
		this.children = children;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Country(String id){
		super(id);
	}

	@ExcelField(title="关联 语言表主键id", align=2, sort=1)
	public Integer getLanguageId() {
		return languageId;
	}

	public void setLanguageId(Integer languageId) {
		this.languageId = languageId;
	}
	
	@ExcelField(title="状态  0 禁用 1 启用", align=2, sort=2)
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="del_date", align=2, sort=8)
	public Date getDelDate() {
		return delDate;
	}

	public void setDelDate(Date delDate) {
		this.delDate = delDate;
	}
	
	@ExcelField(title="del_by", align=2, sort=9)
	public String getDelBy() {
		return delBy;
	}

	public void setDelBy(String delBy) {
		this.delBy = delBy;
	}
	
	@ExcelField(title="目的地", align=2, sort=10)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@ExcelField(title="拼音", align=2, sort=11)
	public String getTitleEn() {
		return titleEn;
	}

	public void setTitleEn(String titleEn) {
		this.titleEn = titleEn;
	}

	public List<ComCity> getCityList() {
		return cityList;
	}

	public void setCityList(List<ComCity> cityList) {
		this.cityList = cityList;
	}

	public Integer getCountryid() {
		return countryid;
	}

	public void setCountryid(Integer countryid) {
		this.countryid = countryid;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getLanguageName() {
		return languageName;
	}

	public void setLanguageName(String languageName) {
		this.languageName = languageName;
	}
	
}