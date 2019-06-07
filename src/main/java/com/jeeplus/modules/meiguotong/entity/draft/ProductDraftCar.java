/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.entity.draft;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 草稿租车Entity
 * @author dong
 * @version 2018-09-14
 */
public class ProductDraftCar extends DataEntity<ProductDraftCar> {
	
	private static final long serialVersionUID = 1L;
	private Integer carid;		// 车辆id
	private Integer draftid;		// 草稿id
	
	public ProductDraftCar() {
		super();
		this.setIdType(IDTYPE_AUTO);
	}

	public ProductDraftCar(String id){
		super(id);
	}

	@ExcelField(title="车辆id", align=2, sort=1)
	public Integer getCarid() {
		return carid;
	}

	public void setCarid(Integer carid) {
		this.carid = carid;
	}
	
	
	
	@ExcelField(title="草稿id", align=2, sort=3)
	public Integer getDraftid() {
		return draftid;
	}

	public void setDraftid(Integer draftid) {
		this.draftid = draftid;
	}
	
}