/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.entity.comdig;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 点赞Entity
 * @author dong
 * @version 2018-09-27
 */
public class ComDig extends DataEntity<ComDig> {
	
	private static final long serialVersionUID = 1L;
	private Integer memberId;		// 点赞人id
	private Integer type;		// 点赞类型：1.评论点赞2.攻略点赞
	private Integer typeId;		// 根据type 去区分 这个字段是主键id
	
	public ComDig() {
		super();
		this.setIdType(IDTYPE_AUTO);
	}

	public ComDig(String id){
		super(id);
	}

	@ExcelField(title="点赞人id", align=2, sort=1)
	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}
	
	@ExcelField(title="点赞类型：1.评论点赞", align=2, sort=2)
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
	@ExcelField(title="根据type 去区分 这个字段是主键id", align=2, sort=3)
	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}
	
}