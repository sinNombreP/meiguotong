/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.entity.draft;


import com.jeeplus.core.persistence.DataEntity;

import java.util.Date;

import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 草稿详情Entity
 * @author dong
 * @version 2018-09-14
 */
public class ProductDraftDetails extends DataEntity<ProductDraftDetails> {
	
	private static final long serialVersionUID = 1L;
	private Integer draftid;		// 草稿id
	private Integer day;		// 第几天
	private Integer sort;		// 排序（当天排序）
	private Integer city;		// 城市id
	private String scenic;		// 景点id
	private Integer citySort;   //城市排序
	private Date useDate;  //使用时间
	private Integer type; //'类型1.旅游2.商务'
	private String busiAddress; //'商务详细地址'
	private Date busiBeginDate; //'商务开始时间'
	private Date busiEndDate; //'商务结束时间'
	private String busiItemId; //'商务定制项目id'
	private String busiItem; //'商务定制项目'
	private Integer busiAdultNum; //'商务大人数量'
	private Integer busiChildNum; //'商务孩子数量'
	private String busiContent; //'商务详细'
	
	public ProductDraftDetails() {
		super();
		this.setIdType(IDTYPE_AUTO);
	}

	public ProductDraftDetails(String id){
		super(id);
	}

	@ExcelField(title="草稿id", align=2, sort=1)
	public Integer getDraftid() {
		return draftid;
	}

	public void setDraftid(Integer draftid) {
		this.draftid = draftid;
	}
	
	@ExcelField(title="第几天", align=2, sort=2)
	public Integer getDay() {
		return day;
	}

	public void setDay(Integer day) {
		this.day = day;
	}
	
	@ExcelField(title="排序（当天排序）", align=2, sort=3)
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
	
	@ExcelField(title="城市id", align=2, sort=4)
	public Integer getCity() {
		return city;
	}

	public void setCity(Integer city) {
		this.city = city;
	}
	
	@ExcelField(title="景点id", align=2, sort=5)
	public String getScenic() {
		return scenic;
	}

	public void setScenic(String scenic) {
		this.scenic = scenic;
	}

	public Integer getCitySort() {
		return citySort;
	}

	public Date getUseDate() {
		return useDate;
	}

	public Integer getType() {
		return type;
	}

	public String getBusiAddress() {
		return busiAddress;
	}

	public Date getBusiBeginDate() {
		return busiBeginDate;
	}

	public Date getBusiEndDate() {
		return busiEndDate;
	}

	public String getBusiItemId() {
		return busiItemId;
	}

	public String getBusiItem() {
		return busiItem;
	}

	public Integer getBusiAdultNum() {
		return busiAdultNum;
	}

	public Integer getBusiChildNum() {
		return busiChildNum;
	}

	public String getBusiContent() {
		return busiContent;
	}

	public void setCitySort(Integer citySort) {
		this.citySort = citySort;
	}

	public void setUseDate(Date useDate) {
		this.useDate = useDate;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public void setBusiAddress(String busiAddress) {
		this.busiAddress = busiAddress;
	}

	public void setBusiBeginDate(Date busiBeginDate) {
		this.busiBeginDate = busiBeginDate;
	}

	public void setBusiEndDate(Date busiEndDate) {
		this.busiEndDate = busiEndDate;
	}

	public void setBusiItemId(String busiItemId) {
		this.busiItemId = busiItemId;
	}

	public void setBusiItem(String busiItem) {
		this.busiItem = busiItem;
	}

	public void setBusiAdultNum(Integer busiAdultNum) {
		this.busiAdultNum = busiAdultNum;
	}

	public void setBusiChildNum(Integer busiChildNum) {
		this.busiChildNum = busiChildNum;
	}

	public void setBusiContent(String busiContent) {
		this.busiContent = busiContent;
	}
	
}