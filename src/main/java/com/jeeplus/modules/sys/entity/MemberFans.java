/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.entity;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 好友列表Entity
 * @author xudemo
 * @version 2018-03-08
 */
public class MemberFans extends DataEntity<MemberFans> {
	
	private static final long serialVersionUID = 1L;
	private Integer fanId;		// 粉丝的ID
	private Integer memberId;		// 被关注人的ID
	
	public MemberFans() {
		super();
		this.setIdType(IDTYPE_AUTO);
	}

	public MemberFans(String id){
		super(id);
	}

	@ExcelField(title="粉丝的ID", align=2, sort=1)
	public Integer getFanId() {
		return fanId;
	}

	public void setFanId(Integer fanId) {
		this.fanId = fanId;
	}
	
	@ExcelField(title="被关注人的ID", align=2, sort=2)
	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}
	
}