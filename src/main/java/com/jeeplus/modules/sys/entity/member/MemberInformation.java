/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.entity.member;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 会员详情Entity
 * @author psz
 * @version 2018-08-07
 */
public class MemberInformation extends DataEntity<MemberInformation> {
	
	private static final long serialVersionUID = 1L;
	private String memberid;		// 会员ID
	private String photo;		// 会员头像
	private Integer sex;		// 性别：1.男 2女
	private Date birthday;		// 生日
	private String nickName;		// 昵称
	private String countryid;		// 国家
	private String cityid;		// 地址（省市区，用/区分）
	private String address;		// 详细地址
	private String realName;		// 真实姓名
	private Integer certType;		// 证件类型
	private String certNo;		// 证件号

	//Member
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
	private Integer memberType;		// 1 普通用户  2 旅行社  （前端用）
	private String qqId;		// QQ ID
	private String weixinId;		// 微信ID
	private String weiboId;		// 微博ID
	
	private String searchContent;  //搜索内容
	private Integer lately;  //1.一周内 2.一月内  3.一年内
	private Integer age;   //年龄
	
	private String phone;		// 联系方式
	
	private Integer companyType;		// 公司类型 1.个人 2 公司
	private String legalPerson;		// 法人代表
	private String companyLogo;		// 公司logo
	private String companyName;		// 公司名称
	private String companyImg;		// 营业执照
	private String companyEmail;		// 电子邮箱
	private String cardsImg;		// 法人身份证
	private String companyContent;		// 公司简介

	private String countryName;  //国家名称
	private String cityName;  //城市名称

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public MemberInformation() {
		super();
		this.setIdType(IDTYPE_AUTO);
	}

	public Integer getMemberType() {
		return memberType;
	}

	public void setMemberType(Integer memberType) {
		this.memberType = memberType;
	}

	public Integer getLately() {
		return lately;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public void setLately(Integer lately) {
		this.lately = lately;
	}

	public String getSearchContent() {
		return searchContent;
	}

	public void setSearchContent(String searchContent) {
		this.searchContent = searchContent;
	}

	public Integer getCertType() {
		return certType;
	}

	public void setCertType(Integer certType) {
		this.certType = certType;
	}

	public String getCertNo() {
		return certNo;
	}

	public void setCertNo(String certNo) {
		this.certNo = certNo;
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

	public MemberInformation(String id){
		super(id);
	}

	@ExcelField(title="会员ID", align=2, sort=1)
	public String getMemberid() {
		return memberid;
	}

	public void setMemberid(String memberid) {
		this.memberid = memberid;
	}
	
	@ExcelField(title="会员头像", align=2, sort=2)
	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}
	
	@ExcelField(title="性别：1.男 2女", dictType="", align=2, sort=3)
	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@ExcelField(title="生日", align=2, sort=4)
	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	
	@ExcelField(title="昵称", align=2, sort=5)
	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	
	
	
	public String getCountryid() {
		return countryid;
	}

	public String getCityid() {
		return cityid;
	}

	public void setCountryid(String countryid) {
		this.countryid = countryid;
	}

	public void setCityid(String cityid) {
		this.cityid = cityid;
	}
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@ExcelField(title="真实姓名", align=2, sort=9)
	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getCompanyType() {
		return companyType;
	}

	public String getLegalPerson() {
		return legalPerson;
	}

	public String getCompanyLogo() {
		return companyLogo;
	}

	public String getCompanyName() {
		return companyName;
	}

	public String getCompanyImg() {
		return companyImg;
	}

	public String getCompanyEmail() {
		return companyEmail;
	}

	public String getCardsImg() {
		return cardsImg;
	}

	public String getCompanyContent() {
		return companyContent;
	}

	public void setCompanyType(Integer companyType) {
		this.companyType = companyType;
	}

	public void setLegalPerson(String legalPerson) {
		this.legalPerson = legalPerson;
	}

	public void setCompanyLogo(String companyLogo) {
		this.companyLogo = companyLogo;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public void setCompanyImg(String companyImg) {
		this.companyImg = companyImg;
	}

	public void setCompanyEmail(String companyEmail) {
		this.companyEmail = companyEmail;
	}

	public void setCardsImg(String cardsImg) {
		this.cardsImg = cardsImg;
	}

	public void setCompanyContent(String companyContent) {
		this.companyContent = companyContent;
	}
	
}