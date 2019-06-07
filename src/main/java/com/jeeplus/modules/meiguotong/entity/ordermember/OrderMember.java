/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.entity.ordermember;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 各出行人信息表Entity
 * @author cdq
 * @version 2018-08-15
 */
public class OrderMember extends DataEntity<OrderMember> {
	
	private static final long serialVersionUID = 1L;
	private String chineseName;		// 中文姓名
	private String englishName;		// 英文姓名
	private Integer certType;		// 证件类型：1.身份证 2.护照 ,
	private String certNo;		// 证件号
	private Date certValidDate;		// 有效期
	private Date birthday;		// 出生年月
	private String area;		// 区号
	private String mobile;		// 手机号
	private Date delDate;		// del_date
	private String delBy;		// del_by
	private Integer languageId;		// 语言id
	private Integer type;		// 1.租车2.常规路线3.当地参团4.游轮5.景点门票6.当地玩家7.酒店8.保险9.定制10导游'
	private Integer typeId;		// 根据type 对应不同表的 orderid
	
	private Boolean saveType;		// 是否保存常用联系人1.是2.否
	
	public OrderMember() {
		super();
		this.setIdType(IDTYPE_AUTO);
	}

	public OrderMember(String id){
		super(id);
	}

	@ExcelField(title="中文姓名", align=2, sort=1)
	public String getChineseName() {
		return chineseName;
	}

	public void setChineseName(String chineseName) {
		this.chineseName = chineseName;
	}
	
	@ExcelField(title="英文姓名", align=2, sort=2)
	public String getEnglishName() {
		return englishName;
	}

	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}
	
	@ExcelField(title="证件类型", align=2, sort=3)
	public Integer getCertType() {
		return certType;
	}

	public void setCertType(Integer certType) {
		this.certType = certType;
	}
	
	@ExcelField(title="证件号", align=2, sort=4)
	public String getCertNo() {
		return certNo;
	}

	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="有效期", align=2, sort=5)
	public Date getCertValidDate() {
		return certValidDate;
	}

	public void setCertValidDate(Date certValidDate) {
		this.certValidDate = certValidDate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="出生年月", align=2, sort=6)
	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	
	@ExcelField(title="区号", align=2, sort=7)
	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}
	
	@ExcelField(title="手机号", align=2, sort=8)
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="del_date", align=2, sort=13)
	public Date getDelDate() {
		return delDate;
	}

	public void setDelDate(Date delDate) {
		this.delDate = delDate;
	}
	
	@ExcelField(title="del_by", align=2, sort=14)
	public String getDelBy() {
		return delBy;
	}

	public void setDelBy(String delBy) {
		this.delBy = delBy;
	}
	
	@ExcelField(title="语言id", align=2, sort=16)
	public Integer getLanguageId() {
		return languageId;
	}

	public void setLanguageId(Integer languageId) {
		this.languageId = languageId;
	}
	
	@ExcelField(title="1 邮轮订单出行人信息  2 景点门票出行人信息3.常规路线出行人信息", align=2, sort=17)
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
	@ExcelField(title="根据type 对应不同表的 orderid", align=2, sort=18)
	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public Boolean getSaveType() {
		return saveType;
	}

	public void setSaveType(Boolean saveType) {
		this.saveType = saveType;
	}
	
}