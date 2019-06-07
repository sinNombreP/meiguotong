/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.entity.compage;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 分页展示数量设置Entity
 * @author dong
 * @version 2018-10-16
 */
public class ComPageNo extends DataEntity<ComPageNo> {
	
	private static final long serialVersionUID = 1L;
	private Integer number;		// 列表分页数
	
	public ComPageNo() {
		super();
		this.setIdType(IDTYPE_AUTO);
	}

	public ComPageNo(String id){
		super(id);
	}

	@ExcelField(title="列表分页数", align=2, sort=1)
	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}
	
}