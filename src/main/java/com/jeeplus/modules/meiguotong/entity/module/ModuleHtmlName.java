/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.entity.module;

import javax.validation.constraints.NotNull;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 模块关联表Entity
 * @author psz
 * @version 2018-12-03
 */
public class ModuleHtmlName extends DataEntity<ModuleHtmlName> {
	
	private static final long serialVersionUID = 1L;
	private Integer moduleHtmlId;		// 页面ID
	private Integer moduleNameId;		// 模块ID
	private Integer languageId;		// 语言ID
	private Integer sort;		// 排序（升序）
	
	private String moduleName;		// 模块名称
	private Integer moduleType;		// 1.左侧广告2.右侧广告3.内容4.页脚
	private Integer moduleSize;		// 1.1*1，2.1+23.1+1+3，4.1*3，5.1*4，6.1*5，7.1*6，8.2+3，9.2*3，10.2*4，11.3*1，12.3+4，12.4*2，
	private Integer type;		// 对于的类型：1广告表
	
	public ModuleHtmlName() {
		super();
		this.setIdType(IDTYPE_AUTO);
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public Integer getModuleType() {
		return moduleType;
	}

	public void setModuleType(Integer moduleType) {
		this.moduleType = moduleType;
	}

	public Integer getModuleSize() {
		return moduleSize;
	}

	public void setModuleSize(Integer moduleSize) {
		this.moduleSize = moduleSize;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public ModuleHtmlName(String id){
		super(id);
	}

	@NotNull(message="页面ID不能为空")
	@ExcelField(title="页面ID", align=2, sort=1)
	public Integer getModuleHtmlId() {
		return moduleHtmlId;
	}

	public void setModuleHtmlId(Integer moduleHtmlId) {
		this.moduleHtmlId = moduleHtmlId;
	}
	
	@NotNull(message="模块ID不能为空")
	@ExcelField(title="模块ID", align=2, sort=2)
	public Integer getModuleNameId() {
		return moduleNameId;
	}

	public void setModuleNameId(Integer moduleNameId) {
		this.moduleNameId = moduleNameId;
	}
	
	@ExcelField(title="语言ID", align=2, sort=3)
	public Integer getLanguageId() {
		return languageId;
	}

	public void setLanguageId(Integer languageId) {
		this.languageId = languageId;
	}
	
	@ExcelField(title="排序（升序）", align=2, sort=4)
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
	
}