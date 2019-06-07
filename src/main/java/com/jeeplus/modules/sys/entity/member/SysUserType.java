/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.entity.member;


import com.jeeplus.core.persistence.DataEntity;

import java.math.BigDecimal;

import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 供应商类型Entity
 * @author psz
 * @version 2018-08-09
 */
public class SysUserType extends DataEntity<SysUserType> {
	
	private static final long serialVersionUID = 1L;
	private String content;		// 类型名称
	private Integer type;		// 类型
	private BigDecimal discount;		// 抽成
	private Integer ststus;		// 1 正常 2禁用
	private Integer userType;  //1 供应商 2 导游
	private String roleid;  //角色ID
	private String roleName;  //角色名称
	private String userid;  //用户ID
	public SysUserType() {
		super();
		this.setIdType(IDTYPE_AUTO);
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleid() {
		return roleid;
	}

	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	public SysUserType(String id){
		super(id);
	}

	@ExcelField(title="类型名称", align=2, sort=1)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@ExcelField(title="类型", align=2, sort=2)
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
	
	
	public BigDecimal getDiscount() {
		return discount;
	}

	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}

	@ExcelField(title="1 正常 2禁用", align=2, sort=7)
	public Integer getStstus() {
		return ststus;
	}

	public void setStstus(Integer ststus) {
		this.ststus = ststus;
	}
	
}