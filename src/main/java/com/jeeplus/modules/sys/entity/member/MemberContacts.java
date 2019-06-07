/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.entity.member;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 常用联系人Entity
 * @author psz
 * @version 2018-08-07
 */
public class MemberContacts extends DataEntity<MemberContacts> {
	
	private static final long serialVersionUID = 1L;
	private Integer memberid;		// 用户id
	private String chineseName;		// 中文姓名
	private String englishName;		// 英文姓名
	private Integer certType;		// 证件类型  1.身份证2.护照
	private String certNo;		// 证件号
	private Date certValidDate;		// 有效期
	private Date birthday;		// 出生年月
	private String area;		// 区号
	private String mobile;		// 手机号
	private Date delDate;		// del_date
	private String delBy;		// del_by
	private String sur;		// 英文姓
	private String name;		// 英文名

	private Integer contactid; //联系人id
	
	public MemberContacts() {
		super();
		this.setIdType(IDTYPE_AUTO);
	}

	public String getSur() {
		return sur;
	}

	public void setSur(String sur) {
		this.sur = sur;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public MemberContacts(String id){
		super(id);
	}

	@ExcelField(title="用户id", align=2, sort=1)
	public Integer getMemberid() {
		return memberid;
	}

	public void setMemberid(Integer memberid) {
		this.memberid = memberid;
	}
	
	@ExcelField(title="中文姓名", align=2, sort=2)
	public String getChineseName() {
		return chineseName;
	}

	public void setChineseName(String chineseName) {
		this.chineseName = chineseName;
	}
	
	@ExcelField(title="英文姓名", align=2, sort=3)
	public String getEnglishName() {
		return englishName;
	}

	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}
	
	@ExcelField(title="证件类型", align=2, sort=4)
	public Integer getCertType() {
		return certType;
	}

	public void setCertType(Integer certType) {
		this.certType = certType;
	}
	
	@ExcelField(title="证件号", align=2, sort=5)
	public String getCertNo() {
		return certNo;
	}

	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@ExcelField(title="有效期", align=2, sort=6)
	public Date getCertValidDate() {
		return certValidDate;
	}

	public void setCertValidDate(Date certValidDate) {
		this.certValidDate = certValidDate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@ExcelField(title="出生年月", align=2, sort=7)
	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	
	@ExcelField(title="区号", align=2, sort=8)
	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}
	
	@ExcelField(title="手机号", align=2, sort=9)
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="del_date", align=2, sort=14)
	public Date getDelDate() {
		return delDate;
	}

	public void setDelDate(Date delDate) {
		this.delDate = delDate;
	}
	
	@ExcelField(title="del_by", align=2, sort=15)
	public String getDelBy() {
		return delBy;
	}

	public void setDelBy(String delBy) {
		this.delBy = delBy;
	}

	public Integer getContactid() {
		return contactid;
	}

	public void setContactid(Integer contactid) {
		this.contactid = contactid;
	}

	
	
}