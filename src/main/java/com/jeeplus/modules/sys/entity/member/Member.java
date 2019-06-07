/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.entity.member;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 会员列表Entity
 * @author psz
 * @version 2018-08-07
 */
public class Member extends DataEntity<Member> {
	
	private static final long serialVersionUID = 1L;
	private Integer fatherId;		// 父ID（没有则为0）
	private String code;		// 区号
	private String mobile;		// 手机号
	private String password;		// 密码
	private String viewPassword;  //子账号显示密码
	private Integer status;		// 状态：1.正常 2.禁用
	private Integer audit;		// 审核状态 ： 1待认证2.申请认证3.已认证 4.认证失败
	private Date delDate;		// 删除时间
	private String delBy;		// 删除人
	private Date lastloginDate;		// 上一次登录时间
	private String lastloginIp;		// 上一次登陆IP
	private String loginDeyid;		// 设备号
	private Integer createType;		// 注册类型：1.手机号  2.邮箱
	private String email;		// 邮箱
	private String loginx;		// loginx
	private String loginy;		// loginy
	private Integer type;		// 1 普通用户  2 旅行社
	private String qqId;		// QQ ID
	private String weixinId;		// 微信ID
	private String weiboId;		// 微博ID

	private Integer memberid;		// 用户id
	
	private Integer days;	//天数
	private String beginDate;//开始时间
	private String currentDate;//当前月份
	private Integer num; //人数
	
	public Member() {
		super();
		this.setIdType(IDTYPE_AUTO);
	}

	public Member(String id){
		super(id);
	}

	@ExcelField(title="父ID（没有则为0）", align=2, sort=1)
	public Integer getFatherId() {
		return fatherId;
	}

	public void setFatherId(Integer fatherId) {
		this.fatherId = fatherId;
	}
	
	@ExcelField(title="区号", align=2, sort=2)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@ExcelField(title="手机号", align=2, sort=3)
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	@ExcelField(title="密码", align=2, sort=4)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	@ExcelField(title="状态：1.正常 2.禁用", align=2, sort=5)
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@ExcelField(title="审核状态 ： 1待认证2.申请认证3.已认证 4.认证失败", align=2, sort=6)
	public Integer getAudit() {
		return audit;
	}

	public void setAudit(Integer audit) {
		this.audit = audit;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="删除时间", align=2, sort=11)
	public Date getDelDate() {
		return delDate;
	}

	public void setDelDate(Date delDate) {
		this.delDate = delDate;
	}
	
	@ExcelField(title="删除人", align=2, sort=12)
	public String getDelBy() {
		return delBy;
	}

	public void setDelBy(String delBy) {
		this.delBy = delBy;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getLastloginDate() {
		return lastloginDate;
	}

	public void setLastloginDate(Date lastloginDate) {
		this.lastloginDate = lastloginDate;
	}
	
	@ExcelField(title="上一次登陆IP", align=2, sort=14)
	public String getLastloginIp() {
		return lastloginIp;
	}

	public void setLastloginIp(String lastloginIp) {
		this.lastloginIp = lastloginIp;
	}
	
	@ExcelField(title="设备号", align=2, sort=15)
	public String getLoginDeyid() {
		return loginDeyid;
	}

	public void setLoginDeyid(String loginDeyid) {
		this.loginDeyid = loginDeyid;
	}
	
	@ExcelField(title="注册类型：1.手机号  2.邮箱", align=2, sort=16)
	public Integer getCreateType() {
		return createType;
	}

	public void setCreateType(Integer createType) {
		this.createType = createType;
	}
	
	@ExcelField(title="邮箱", align=2, sort=17)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	@ExcelField(title="loginx", align=2, sort=18)
	public String getLoginx() {
		return loginx;
	}

	public void setLoginx(String loginx) {
		this.loginx = loginx;
	}
	
	@ExcelField(title="loginy", align=2, sort=19)
	public String getLoginy() {
		return loginy;
	}

	public void setLoginy(String loginy) {
		this.loginy = loginy;
	}
	
	@ExcelField(title="1 普通用户  2 旅行社", align=2, sort=20)
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
	@ExcelField(title="QQ ID", align=2, sort=21)
	public String getQqId() {
		return qqId;
	}

	public void setQqId(String qqId) {
		this.qqId = qqId;
	}
	
	@ExcelField(title="微信ID", align=2, sort=22)
	public String getWeixinId() {
		return weixinId;
	}

	public void setWeixinId(String weixinId) {
		this.weixinId = weixinId;
	}
	
	@ExcelField(title="微博ID", align=2, sort=23)
	public String getWeiboId() {
		return weiboId;
	}

	public void setWeiboId(String weiboId) {
		this.weiboId = weiboId;
	}

	public Integer getMemberid() {
		return memberid;
	}

	public void setMemberid(Integer memberid) {
		this.memberid = memberid;
	}

	public String getViewPassword() {
		return viewPassword;
	}

	public void setViewPassword(String viewPassword) {
		this.viewPassword = viewPassword;
	}

	public Integer getDays() {
		return days;
	}

	public void setDays(Integer days) {
		this.days = days;
	}

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getCurrentDate() {
		return currentDate;
	}

	public void setCurrentDate(String currentDate) {
		this.currentDate = currentDate;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}
	
}