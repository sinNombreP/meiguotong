/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.entity.member;


import com.jeeplus.core.persistence.DataEntity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 旅行社Entity
 * @author psz
 * @version 2018-08-07
 */
public class MemberTravel extends DataEntity<MemberTravel> {
	
	private static final long serialVersionUID = 1L;
	private Integer memberid;		// 会员ID
	private String photo;		// 会员头像
	private Integer companyType;		// 公司类型 1.个人 2 公司
	private String address;		// 地址（省市区，用/区分）
	private String legalPerson;		// 法人代表
	private String companyLogo;		// 公司logo
	private String companyName;		// 公司名称
	private String companyImg;		// 营业执照
	private String companyEmail;		// 电子邮箱
	private String cardsImg;		// 法人身份证
	private String companyContent;		// 公司简介
	private String nickName;		// 昵称
	private String countryid;		// 国家
	private String cityid;		// 地址（省市区，用/区分）
	private String phone;		// 联系方式
	//member
	private Integer fatherId;		// 父ID（没有则为0）
	private String code;		// 区号
	private String mobile;		// 手机号
	private String password;		// 密码
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
	
	private String searchContent;  //搜索内容
	private Integer lately;  //1.一周内 2.一月内  3.一年内
	
	public MemberTravel() {
		super();
		this.setIdType(IDTYPE_AUTO);
	}

	public String getCompanyEmail() {
		return companyEmail;
	}

	public void setCompanyEmail(String companyEmail) {
		this.companyEmail = companyEmail;
	}

	public String getCompanyLogo() {
		return companyLogo;
	}

	public void setCompanyLogo(String companyLogo) {
		this.companyLogo = companyLogo;
	}

	public String getSearchContent() {
		return searchContent;
	}

	public void setSearchContent(String searchContent) {
		this.searchContent = searchContent;
	}

	public Integer getLately() {
		return lately;
	}

	public void setLately(Integer lately) {
		this.lately = lately;
	}

	public Integer getFatherId() {
		return fatherId;
	}

	public void setFatherId(Integer fatherId) {
		this.fatherId = fatherId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getAudit() {
		return audit;
	}

	public void setAudit(Integer audit) {
		this.audit = audit;
	}

	public Date getDelDate() {
		return delDate;
	}

	public void setDelDate(Date delDate) {
		this.delDate = delDate;
	}

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

	public String getLastloginIp() {
		return lastloginIp;
	}

	public void setLastloginIp(String lastloginIp) {
		this.lastloginIp = lastloginIp;
	}

	public String getLoginDeyid() {
		return loginDeyid;
	}

	public void setLoginDeyid(String loginDeyid) {
		this.loginDeyid = loginDeyid;
	}

	public Integer getCreateType() {
		return createType;
	}

	public void setCreateType(Integer createType) {
		this.createType = createType;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLoginx() {
		return loginx;
	}

	public void setLoginx(String loginx) {
		this.loginx = loginx;
	}

	public String getLoginy() {
		return loginy;
	}

	public void setLoginy(String loginy) {
		this.loginy = loginy;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getQqId() {
		return qqId;
	}

	public void setQqId(String qqId) {
		this.qqId = qqId;
	}

	public String getWeixinId() {
		return weixinId;
	}

	public void setWeixinId(String weixinId) {
		this.weixinId = weixinId;
	}

	public String getWeiboId() {
		return weiboId;
	}

	public void setWeiboId(String weiboId) {
		this.weiboId = weiboId;
	}

	public MemberTravel(String id){
		super(id);
	}

	@ExcelField(title="会员ID", align=2, sort=1)
	public Integer getMemberid() {
		return memberid;
	}

	public void setMemberid(Integer memberid) {
		this.memberid = memberid;
	}
	
	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	@ExcelField(title="公司类型 1.个人 2 公司", align=2, sort=3)
	public Integer getCompanyType() {
		return companyType;
	}

	public void setCompanyType(Integer companyType) {
		this.companyType = companyType;
	}
	
	@ExcelField(title="地址（省市区，用/区分）", align=2, sort=4)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	
	@ExcelField(title="法人代表", align=2, sort=6)
	public String getLegalPerson() {
		return legalPerson;
	}

	public void setLegalPerson(String legalPerson) {
		this.legalPerson = legalPerson;
	}
	
	@ExcelField(title="公司名称", align=2, sort=7)
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	@ExcelField(title="营业执照", align=2, sort=8)
	public String getCompanyImg() {
		return companyImg;
	}

	public void setCompanyImg(String companyImg) {
		this.companyImg = companyImg;
	}
	
	@ExcelField(title="法人身份证", align=2, sort=9)
	public String getCardsImg() {
		return cardsImg;
	}

	public void setCardsImg(String cardsImg) {
		this.cardsImg = cardsImg;
	}
	
	@ExcelField(title="公司简介", align=2, sort=10)
	public String getCompanyContent() {
		return companyContent;
	}

	public void setCompanyContent(String companyContent) {
		this.companyContent = companyContent;
	}

	public String getCountryid() {
		return countryid;
	}

	public String getCityid() {
		return cityid;
	}

	public String getPhone() {
		return phone;
	}

	public void setCountryid(String countryid) {
		this.countryid = countryid;
	}

	public void setCityid(String cityid) {
		this.cityid = cityid;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	
}