/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.entity.settingtitlepro;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 产品新增详情标题Entity
 * @author dong
 * @version 2019-04-28
 */
public class SettingTitlePro extends DataEntity<SettingTitlePro> {
	
	private static final long serialVersionUID = 1L;
	private Integer titleid;		// 标题id
	private Integer proid;		// 产品id
	private String content;		// 内容
	
	public SettingTitlePro() {
		super();
		this.setIdType(IDTYPE_AUTO);
	}

	public SettingTitlePro(String id){
		super(id);
	}

	@ExcelField(title="标题id", align=2, sort=1)
	public Integer getTitleid() {
		return titleid;
	}

	public void setTitleid(Integer titleid) {
		this.titleid = titleid;
	}
	
	@ExcelField(title="产品id", align=2, sort=2)
	public Integer getProid() {
		return proid;
	}

	public void setProid(Integer proid) {
		this.proid = proid;
	}
	
	@ExcelField(title="内容", align=2, sort=3)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
}