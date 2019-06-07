/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.entity.draft;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 草稿导游Entity
 * @author dong
 * @version 2018-09-14
 */
public class ProductDraftGuide extends DataEntity<ProductDraftGuide> {
	
	private static final long serialVersionUID = 1L;
	private Integer draftid;		// 草稿id
	private Integer guideid;		// 导游id
	
	public ProductDraftGuide() {
		super();
		this.setIdType(IDTYPE_AUTO);
	}

	public ProductDraftGuide(String id){
		super(id);
	}

	@ExcelField(title="草稿id", align=2, sort=1)
	public Integer getDraftid() {
		return draftid;
	}

	public void setDraftid(Integer draftid) {
		this.draftid = draftid;
	}
	

	
	@ExcelField(title="导游id", align=2, sort=3)
	public Integer getGuideid() {
		return guideid;
	}

	public void setGuideid(Integer guideid) {
		this.guideid = guideid;
	}
	
}