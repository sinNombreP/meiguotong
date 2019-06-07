/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.entity.common;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 会员登录管理Entity
 * @author xudemo
 * @version 2018-03-07
 */
public class ComLoginLog extends DataEntity<ComLoginLog> {
	
	private static final long serialVersionUID = 1L;
	private Integer memberid;		// memberid
	private Date loginDate;		// login_date
	private String loginIp;		// login_ip
	private Integer loginType;		// 登录终端：1.安卓  2.苹果  3.网页 4.微信公众号
	private Integer loginWay;		// 1.会员登录2.三方登陆3.游客登陆
	private Integer source;           //'1.安卓 2.苹果 3.网页',
	private String loginPosition;		// 位置
	private Integer isLog;		// 1.会员 2.游客
	private String membername;		// 会员姓名
	private Date beginLoginDate;		// 开始 login_date
	private Date endLoginDate;		// 结束 login_date
	
	public Integer getSource() {
		return source;
	}

	public void setSource(Integer source) {
		this.source = source;
	}

	public ComLoginLog() {
		super();
		this.setIdType(IDTYPE_AUTO);
	}

	public ComLoginLog(String id){
		super(id);
	}

	@ExcelField(title="memberid", align=2, sort=1)
	public Integer getMemberid() {
		return memberid;
	}

	public void setMemberid(Integer memberid) {
		this.memberid = memberid;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="login_date", align=2, sort=2)
	public Date getLoginDate() {
		return loginDate;
	}

	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}
	
	@ExcelField(title="login_ip", align=2, sort=3)
	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}
	
	@ExcelField(title="登录终端：1.安卓  2.苹果  3.网页 4.微信公众号", align=2, sort=4)
	public Integer getLoginType() {
		return loginType;
	}

	public void setLoginType(Integer loginType) {
		this.loginType = loginType;
	}
	
	@ExcelField(title="1.会员登录2.三方登陆3.游客登陆", align=2, sort=5)
	public Integer getLoginWay() {
		return loginWay;
	}

	public void setLoginWay(Integer loginWay) {
		this.loginWay = loginWay;
	}
	
	@ExcelField(title="位置", align=2, sort=6)
	public String getLoginPosition() {
		return loginPosition;
	}

	public void setLoginPosition(String loginPosition) {
		this.loginPosition = loginPosition;
	}
	
	@ExcelField(title="1.会员 2.游客", align=2, sort=7)
	public Integer getIsLog() {
		return isLog;
	}

	public void setIsLog(Integer isLog) {
		this.isLog = isLog;
	}
	
	@ExcelField(title="会员姓名", align=2, sort=8)
	public String getMembername() {
		return membername;
	}

	public void setMembername(String membername) {
		this.membername = membername;
	}
	
	public Date getBeginLoginDate() {
		return beginLoginDate;
	}

	public void setBeginLoginDate(Date beginLoginDate) {
		this.beginLoginDate = beginLoginDate;
	}
	
	public Date getEndLoginDate() {
		return endLoginDate;
	}

	public void setEndLoginDate(Date endLoginDate) {
		this.endLoginDate = endLoginDate;
	}
		
}