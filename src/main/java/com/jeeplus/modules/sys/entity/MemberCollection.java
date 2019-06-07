/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.entity;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 个人收藏Entity
 * @author xudemo
 * @version 2018-02-27
 */
public class MemberCollection extends DataEntity<MemberCollection> {
	
	private static final long serialVersionUID = 1L;
	private Integer memberid;		// memberid
	private Integer type;		// 相应的枚举值
	private Integer typeid;		// ID值
	private String typeids;		// 多个ID值
	
	
	public MemberCollection() {
		super();
		this.setIdType(IDTYPE_AUTO);
	}

	public MemberCollection(String id){
		super(id);
	}

	@ExcelField(title="memberid", align=2, sort=1)
	public Integer getMemberid() {
		return memberid;
	}

	public void setMemberid(Integer memberid) {
		this.memberid = memberid;
	}
	
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getTypeid() {
		return typeid;
	}

	public void setTypeid(Integer typeid) {
		this.typeid = typeid;
	}

	public String getTypeids() {
		return typeids;
	}

	public void setTypeids(String typeids) {
		this.typeids = typeids;
	}

	
}