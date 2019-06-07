/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.entity.comadd;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 广告设置Entity
 * @author cdq
 * @version 2018-07-27
 */
public class ComAd extends DataEntity<ComAd> {
	
	private static final long serialVersionUID = 1L;
	private Date delDate;		// del_date
	private String delBy;		// del_by
	private Integer displayType;		// 显示位置   1 电脑端  2 手机网页
	private Integer positionType;		// 所在位置  1 网页头部 2 网页尾部
	private String imgUrl;		// 广告图片
	private String title;		// 广告标题
	private String link;		// 广告外链
	private String languageId;		// 语言主键id
	private Integer sort;		// 排序
	private String languageName; 
	public ComAd() {
		super();
		this.setIdType(IDTYPE_AUTO);
	}

	public ComAd(String id){
		super(id);
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="del_date", align=2, sort=5)
	public Date getDelDate() {
		return delDate;
	}

	public void setDelDate(Date delDate) {
		this.delDate = delDate;
	}
	
	@ExcelField(title="del_by", align=2, sort=6)
	public String getDelBy() {
		return delBy;
	}

	public void setDelBy(String delBy) {
		this.delBy = delBy;
	}
	
	@ExcelField(title="显示位置   1 电脑端  2 手机网页", align=2, sort=8)
	public Integer getDisplayType() {
		return displayType;
	}

	public void setDisplayType(Integer displayType) {
		this.displayType = displayType;
	}
	
	@ExcelField(title="所在位置  1 网页头部 2 网页尾部", align=2, sort=9)
	public Integer getPositionType() {
		return positionType;
	}

	public void setPositionType(Integer positionType) {
		this.positionType = positionType;
	}
	
	@ExcelField(title="广告图片", align=2, sort=10)
	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	
	@ExcelField(title="广告标题", align=2, sort=11)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@ExcelField(title="广告外链", align=2, sort=12)
	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}
	
	@ExcelField(title="语言主键id", align=2, sort=13)
	public String getLanguageId() {
		return languageId;
	}

	public void setLanguageId(String languageId) {
		this.languageId = languageId;
	}
	
	@ExcelField(title="排序", align=2, sort=14)
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getLanguageName() {
		return languageName;
	}

	public void setLanguageName(String languageName) {
		this.languageName = languageName;
	}
	
}