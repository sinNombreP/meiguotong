/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.entity;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 点赞Entity
 * @author dong
 * @version 2018-03-07
 */
public class MemberDigg extends DataEntity<MemberDigg> {
	
	private static final long serialVersionUID = 1L;
	private Integer memberid;		// 会员ID
	private Integer type;		// 相应的枚举值
	private Integer typeid;		// ID值
	
	public MemberDigg() {
		super();
		this.setIdType(IDTYPE_AUTO);
	}

	public MemberDigg(String id){
		super(id);
	}

	@ExcelField(title="会员ID", align=2, sort=1)
	public Integer getMemberid() {
		return memberid;
	}

	public void setMemberid(Integer memberid) {
		this.memberid = memberid;
	}
	
	@ExcelField(title="相应的枚举值", align=2, sort=2)
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
	@ExcelField(title="ID值", align=2, sort=3)
	public Integer getTypeid() {
		return typeid;
	}

	public void setTypeid(Integer typeid) {
		this.typeid = typeid;
	}
	
}