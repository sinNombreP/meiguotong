/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.entity.compush;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 推送管理Entity
 * @author dong
 * @version 2018-09-17
 */
public class ComPush extends DataEntity<ComPush> {
	
	private static final long serialVersionUID = 1L;
	private Integer pushid;		//推送Id
	private String title;		// 推送标题
	private String content;		// 内容
	private Integer send;		// 1.所有用户 2.部分用户
	private String sendType;	//用户类型 1.当地玩家2.直客3.旅行社
	private Integer memberid;	//用户id
	private String nickName;	//用户昵称
	private String number;		//账号
	private String memberids;	//用户id集合
	private String strSendType;	//用户类型名称
	
	public ComPush() {
		super();
		this.setIdType(IDTYPE_AUTO);
	}

	public ComPush(String id){
		super(id);
	}

	@ExcelField(title="推送标题", align=2, sort=1)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@ExcelField(title="内容", align=2, sort=2)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	


	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	@ExcelField(title="1.所有用户 2.部分用户", align=2, sort=3)
	public Integer getSend() {
		return send;
	}

	public void setSend(Integer send) {
		this.send = send;
	}
	
	@ExcelField(title="1.当地玩家2.直客3.旅行社", align=2, sort=4)
	public String getSendType() {
		return sendType;
	}

	public void setSendType(String sendType) {
		this.sendType = sendType;
	}

	public Integer getMemberid() {
		return memberid;
	}

	public void setMemberid(Integer memberid) {
		this.memberid = memberid;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getMemberids() {
		return memberids;
	}

	public void setMemberids(String memberids) {
		this.memberids = memberids;
	}

	public Integer getPushid() {
		return pushid;
	}

	public void setPushid(Integer pushid) {
		this.pushid = pushid;
	}

	public String getStrSendType() {
		return strSendType;
	}

	public void setStrSendType(String strSendType) {
		this.strSendType = strSendType;
	}
	
	
}