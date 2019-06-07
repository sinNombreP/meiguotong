/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.entity.liner;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeeplus.modules.sys.entity.User;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 游轮公司表Entity
 * @author dong
 * @version 2018-10-29
 */
public class LinerCompany extends DataEntity<LinerCompany> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 邮轮公司名称
	private Integer status;		// 状态  1 正常2 禁用
	private Date delDate;		// del_date
	private User delBy;		// del_by
	private Integer languageId;		// 语言id
	private String languageName;		// 语言名称
	
	public LinerCompany() {
		super();
		this.setIdType(IDTYPE_AUTO);
	}

	public LinerCompany(String id){
		super(id);
	}

	@ExcelField(title="邮轮公司名称", align=2, sort=1)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ExcelField(title="状态 0 禁用 1 正常", align=2, sort=2)
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="del_date", align=2, sort=7)
	public Date getDelDate() {
		return delDate;
	}

	public void setDelDate(Date delDate) {
		this.delDate = delDate;
	}
	
	@ExcelField(title="del_by", align=2, sort=8)
	public User getDelBy() {
		return delBy;
	}

	public void setDelBy(User delBy) {
		this.delBy = delBy;
	}
	
	@ExcelField(title="语言id", dictType="", align=2, sort=10)
	public Integer getLanguageId() {
		return languageId;
	}

	public void setLanguageId(Integer languageId) {
		this.languageId = languageId;
	}

	public String getLanguageName() {
		return languageName;
	}

	public void setLanguageName(String languageName) {
		this.languageName = languageName;
	}
	
}