/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.entity.module;


import java.util.List;

import com.jeeplus.common.utils.excel.annotation.ExcelField;
import com.jeeplus.core.persistence.DataEntity;

/**
 * 网站页面Entity
 * @author psz
 * @version 2018-12-03
 */
public class ModuleHtml extends DataEntity<ModuleHtml> {
	
	private static final long serialVersionUID = 1L;
	private String htmlName;		// 页面名称
	private String htmlSeal;		// 页面位置（内容模块，底部栏，广告模块，搜索模块）
	private List<ModuleHtml> moduleHtmlList;  //子页面List
	
	public ModuleHtml() {
		super();
		this.setIdType(IDTYPE_AUTO);
	}

	public List<ModuleHtml> getModuleHtmlList() {
		return moduleHtmlList;
	}

	public void setModuleHtmlList(List<ModuleHtml> moduleHtmlList) {
		this.moduleHtmlList = moduleHtmlList;
	}

	public ModuleHtml(String id){
		super(id);
	}

	@ExcelField(title="页面名称", align=2, sort=1)
	public String getHtmlName() {
		return htmlName;
	}

	public void setHtmlName(String htmlName) {
		this.htmlName = htmlName;
	}
	
	@ExcelField(title="页面位置（内容模块，底部栏，广告模块，搜索模块）", align=2, sort=2)
	public String gethtmlSeal() {
		return htmlSeal;
	}

	public void sethtmlSeal(String htmlSeal) {
		this.htmlSeal = htmlSeal;
	}
	
}