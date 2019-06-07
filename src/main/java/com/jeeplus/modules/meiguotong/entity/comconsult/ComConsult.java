/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.entity.comconsult;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 用户咨询Entity
 * @author psz
 * @version 2018-08-16
 */
public class ComConsult extends DataEntity<ComConsult> {
	
	private static final long serialVersionUID = 1L;
	private Integer type;		// 类型1.租车2.常规路线3.当地参团4.游轮5.景点门票6.当地玩家7.酒店8.保险9.旅游定制
	private Integer typeId;		// 根据type 去区分 这个字段是主键id
	private String content;		// 咨询内容
	private Integer memberid;		// 用户ID
	private String contentRetply;		// 回复内容
	private Integer status;		// 状态  1正常0禁用
	private Integer languageId;		// 语言id
	private String name;		// 姓名
	private String moblie;		// 电话
	private String nickName;   //评论人昵称
	private String account;   //评论人账号
	public ComConsult() {
		super();
		this.setIdType(IDTYPE_AUTO);
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public ComConsult(String id){
		super(id);
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}
	
	@ExcelField(title="咨询内容", align=2, sort=2)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@ExcelField(title="用户ID", align=2, sort=1)
	public Integer getMemberid() {
		return memberid;
	}

	public void setMemberid(Integer memberid) {
		this.memberid = memberid;
	}
	
	@ExcelField(title="回复内容", align=2, sort=3)
	public String getContentRetply() {
		return contentRetply;
	}

	public void setContentRetply(String contentRetply) {
		this.contentRetply = contentRetply;
	}
	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public Integer getLanguageId() {
		return languageId;
	}

	public void setLanguageId(Integer languageId) {
		this.languageId = languageId;
	}
	
	@ExcelField(title="姓名", align=2, sort=4)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ExcelField(title="电话", align=2, sort=5)
	public String getMoblie() {
		return moblie;
	}

	public void setMoblie(String moblie) {
		this.moblie = moblie;
	}
	
}