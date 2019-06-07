package com.jeeplus.modules.meiguotong.entity.order;

import java.util.Date;

public class OrderMemberApp {
	private String chineseName;		// 中文姓名
	private String englishName;		// 英文姓名
	private Integer certType;		// 证件类型：1.身份证 2.护照 ,
	private String certNo;		// 证件号
	private Date birthday;		// 出生年月
	private String mobile;		// 手机号
	private Integer languageId;		// 语言id
	private String id;		// id
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getChineseName() {
		return chineseName;
	}
	public void setChineseName(String chineseName) {
		this.chineseName = chineseName;
	}
	public String getEnglishName() {
		return englishName;
	}
	public void setEnglishName(String englishName) {
		this.englishName = englishName;
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
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public Integer getLanguageId() {
		return languageId;
	}
	public void setLanguageId(Integer languageId) {
		this.languageId = languageId;
	}

}
