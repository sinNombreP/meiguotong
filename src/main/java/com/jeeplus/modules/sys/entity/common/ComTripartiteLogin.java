/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.entity.common;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 三方登录Entity
 * @author xudemo
 * @version 2018-01-16
 */
public class ComTripartiteLogin extends DataEntity<ComTripartiteLogin> {
	
	private static final long serialVersionUID = 1L;
	private String type;		// weixin,QQ,weibo,facebook
	private String typeId;		// 对应的ID
	private String supplement;		// 参数补充
	private Date loginDate;		// 登陆时间
	private Integer loginType;		// 1.安卓 2.苹果3.网页
	private String loginName;		// 登陆姓名
	private String loginPhoto;		// 登陆照片
	private String loginIp;		// 登陆时间
	private Integer isBang;		// 1.已绑定 2.未绑定
	private Integer memberid;	//会员id
	
	public Integer getMemberid() {
		return memberid;
	}

	public void setMemberid(Integer memberid) {
		this.memberid = memberid;
	}

	public ComTripartiteLogin() {
		super();
		this.setIdType(IDTYPE_AUTO);
	}

	public ComTripartiteLogin(String id){
		super(id);
	}

	@ExcelField(title="weixin,QQ,weibo,facebook", align=2, sort=1)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@ExcelField(title="对应的ID", align=2, sort=2)
	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}
	
	@ExcelField(title="参数补充", align=2, sort=3)
	public String getSupplement() {
		return supplement;
	}

	public void setSupplement(String supplement) {
		this.supplement = supplement;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="登陆时间", align=2, sort=4)
	public Date getLoginDate() {
		return loginDate;
	}

	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}
	
	@ExcelField(title="1.安卓 2.苹果3.网页", align=2, sort=5)
	public Integer getLoginType() {
		return loginType;
	}

	public void setLoginType(Integer loginType) {
		this.loginType = loginType;
	}
	
	@ExcelField(title="登陆姓名", align=2, sort=6)
	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	
	@ExcelField(title="登陆照片", align=2, sort=7)
	public String getLoginPhoto() {
		return loginPhoto;
	}

	public void setLoginPhoto(String loginPhoto) {
		this.loginPhoto = loginPhoto;
	}
	
	@ExcelField(title="登陆时间", align=2, sort=8)
	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}
	
	@ExcelField(title="1.已绑定 2.未绑定", align=2, sort=9)
	public Integer getIsBang() {
		return isBang;
	}

	public void setIsBang(Integer isBang) {
		this.isBang = isBang;
	}
	
}