/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.entity.compush;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 推送人员列表Entity
 * @author dong
 * @version 2018-09-17
 */
public class ComPushPeople extends DataEntity<ComPushPeople> {
	
	private static final long serialVersionUID = 1L;
	private Integer memberid;		// 会员ID
	private Integer pushid;		// 推送ID
	private Integer isLook;		// 是否查看  1.未查看  2.已查看
	private String title;		// 标题
	private String content;		// 推送内容
	
	public ComPushPeople() {
		super();
		this.setIdType(IDTYPE_AUTO);
	}

	public ComPushPeople(String id){
		super(id);
	}

	@ExcelField(title="会员ID", align=2, sort=1)
	public Integer getMemberid() {
		return memberid;
	}

	public void setMemberid(Integer memberid) {
		this.memberid = memberid;
	}
	
	@ExcelField(title="推送ID", align=2, sort=2)
	public Integer getPushid() {
		return pushid;
	}

	public void setPushid(Integer pushid) {
		this.pushid = pushid;
	}
	
	@ExcelField(title="是否查看  1.未查看  2.已查看", align=2, sort=3)
	public Integer getIsLook() {
		return isLook;
	}

	public void setIsLook(Integer isLook) {
		this.isLook = isLook;
	}
	
	@ExcelField(title="标题", align=2, sort=4)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@ExcelField(title="推送内容", align=2, sort=5)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
}