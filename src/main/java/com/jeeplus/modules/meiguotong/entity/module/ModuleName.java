/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.entity.module;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 模块名称Entity
 * @author psz
 * @version 2018-12-03
 */
public class ModuleName extends DataEntity<ModuleName> {
	
	private static final long serialVersionUID = 1L;
	private String moduleName;		// 模块名称
	private Integer moduleType;		// 1.左侧广告2.右侧广告3.内容4.页脚
	private String moduleSize;		// 模板规格
	private Integer type;		// 对于的类型：1广告表
	
	public ModuleName() {
		super();
		this.setIdType(IDTYPE_AUTO);
	}

	public ModuleName(String id){
		super(id);
	}

	@ExcelField(title="模块名称", align=2, sort=1)
	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	
	@ExcelField(title="1.左侧广告2.右侧广告3.内容4.页脚", align=2, sort=2)
	public Integer getModuleType() {
		return moduleType;
	}

	public void setModuleType(Integer moduleType) {
		this.moduleType = moduleType;
	}
	
	@ExcelField(title="1.1*1，2.1+23.1+1+3，4.1*3，5.1*4，6.1*5，7.1*6，8.2+3，9.2*3，10.2*4，11.3*1，12.3+4，12.4*2，", align=2, sort=3)
	public String getModuleSize() {
		return moduleSize;
	}

	public void setModuleSize(String moduleSize) {
		this.moduleSize = moduleSize;
	}
	
	@ExcelField(title="对于的类型：1广告表", align=2, sort=4)
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
}