/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.entity.draft;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 草稿Entity
 * @author dong
 * @version 2018-09-14
 */
public class ProductDraft extends DataEntity<ProductDraft> {
	
	private static final long serialVersionUID = 1L;
	private Integer memberid;		// 用户id
	private Integer languageid;		// 语言id
	private String title;		// 草稿标题
	private Integer cityid;		// 出发城市id
	private String address;		// 出发详细地址
	private Integer dayNum;		// 旅游天数
	private Integer adultNum;		// 大人数量
	private Integer childNum;		// 孩子数量
	private Integer bagNum;		// 行李箱数量
	private Date beginDate;		// 开始时间
	private Date endDate;		// 结束时间
	private String ids;		// 草稿id(可以传多个，逗号分隔)
	
	private Integer flag;  //0未选中  1选中  （前端处理数据默认值0） 
	
	public ProductDraft() {
		super();
		this.setIdType(IDTYPE_AUTO);
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public ProductDraft(String id){
		super(id);
	}

	@ExcelField(title="用户id", align=2, sort=1)
	public Integer getMemberid() {
		return memberid;
	}

	public void setMemberid(Integer memberid) {
		this.memberid = memberid;
	}
	
	@ExcelField(title="语言id", align=2, sort=2)
	public Integer getLanguageid() {
		return languageid;
	}

	public void setLanguageid(Integer languageid) {
		this.languageid = languageid;
	}
	
	@ExcelField(title="草稿标题", align=2, sort=3)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@ExcelField(title="出发城市id", align=2, sort=4)
	public Integer getCityid() {
		return cityid;
	}

	public void setCityid(Integer cityid) {
		this.cityid = cityid;
	}
	
	@ExcelField(title="出发详细地址", align=2, sort=5)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	@ExcelField(title="旅游天数", align=2, sort=6)
	public Integer getDayNum() {
		return dayNum;
	}

	public void setDayNum(Integer dayNum) {
		this.dayNum = dayNum;
	}
	
	@ExcelField(title="大人数量", align=2, sort=7)
	public Integer getAdultNum() {
		return adultNum;
	}

	public void setAdultNum(Integer adultNum) {
		this.adultNum = adultNum;
	}
	
	@ExcelField(title="孩子数量", align=2, sort=8)
	public Integer getChildNum() {
		return childNum;
	}

	public void setChildNum(Integer childNum) {
		this.childNum = childNum;
	}
	
	@ExcelField(title="行李箱数量", align=2, sort=9)
	public Integer getBagNum() {
		return bagNum;
	}

	public void setBagNum(Integer bagNum) {
		this.bagNum = bagNum;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="开始时间", align=2, sort=10)
	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="结束时间", align=2, sort=11)
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}
	
}