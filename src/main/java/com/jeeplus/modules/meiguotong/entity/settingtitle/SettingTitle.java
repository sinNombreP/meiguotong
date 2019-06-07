/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.entity.settingtitle;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 详情表Entity
 * @author cdq
 * @version 2018-08-06
 */
public class SettingTitle extends DataEntity<SettingTitle> {
	
	private static final long serialVersionUID = 1L;
	private Integer sort;		// sort
	private String title;		// 标题
	private Integer status;		// 状态 1 启用2禁用 
	private Integer type;		// 类型 1 常规线路  2 当地参团 3 景点门票 4 邮轮
	private Date delDate;		// del_date
	private String delBy;		// del_by
	private Integer languageId;		// 语言id
	private String languageName;		// 语言名称
	private String isadd;       //0固定1.新增

	private Integer proid;		// 产品id
	private String content;		// 内容
	private Integer titleProid; //产品内容id
	
	public SettingTitle() {
		super();
		this.setIdType(IDTYPE_AUTO);
	}

	public SettingTitle(String id){
		super(id);
	}

	@ExcelField(title="sort", align=2, sort=1)
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
	
	@ExcelField(title="标题", align=2, sort=2)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@ExcelField(title="状态 0 禁用 1 启用", align=2, sort=3)
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@ExcelField(title="类型 1 常规线路  2 当地参团 3 景点门票 4 邮轮", align=2, sort=4)
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="del_date", align=2, sort=9)
	public Date getDelDate() {
		return delDate;
	}

	public void setDelDate(Date delDate) {
		this.delDate = delDate;
	}
	
	@ExcelField(title="del_by", align=2, sort=10)
	public String getDelBy() {
		return delBy;
	}

	public void setDelBy(String delBy) {
		this.delBy = delBy;
	}
	
	@ExcelField(title="语言id", align=2, sort=12)
	public Integer getLanguageId() {
		return languageId;
	}

	public void setLanguageId(Integer languageId) {
		this.languageId = languageId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getProid() {
		return proid;
	}

	public void setProid(Integer proid) {
		this.proid = proid;
	}

	public String getLanguageName() {
		return languageName;
	}

	public void setLanguageName(String languageName) {
		this.languageName = languageName;
	}

	public String getIsadd() {
		return isadd;
	}

	public void setIsadd(String isadd) {
		this.isadd = isadd;
	}

	public Integer getTitleProid() {
		return titleProid;
	}

	public void setTitleProid(Integer titleProid) {
		this.titleProid = titleProid;
	}
	
}