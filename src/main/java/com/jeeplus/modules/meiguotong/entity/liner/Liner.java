/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.entity.liner;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 邮轮管理表Entity
 * @author cdq
 * @version 2018-08-13
 */
public class Liner extends DataEntity<Liner> {
	
	private static final long serialVersionUID = 1L;
	private String linerNo;		// 游轮编号
	private String name;		// name
	private String company;		// 所属公司
	private String companyName; //所属公司名称
	private Integer status;		// 状态 1审核中2已拒绝3正常4禁用
	private Date delDate;		// del_date
	private String delBy;		// del_by
	private Integer languageId;		// 语言id
	private Double length;//长度
	private Double heigth;//高
	private  Double width;//宽
	private  Double weight;//重量
	private   Date startDate;//首航时间
	private  Integer passengersNum;//载客人数
	private String languageName;// 语言名称
	private  Integer loginAgentid;//登录人信息
	
	public String getLinerNo() {
		return linerNo;
	}

	public void setLinerNo(String linerNo) {
		this.linerNo = linerNo;
	}

	public Double getLength() {
		return length;
	}

	public void setLength(Double length) {
		this.length = length;
	}

	public Double getHeigth() {
		return heigth;
	}

	public void setHeigth(Double heigth) {
		this.heigth = heigth;
	}

	public Double getWidth() {
		return width;
	}

	public void setWidth(Double width) {
		this.width = width;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Integer getPassengersNum() {
		return passengersNum;
	}

	public void setPassengersNum(Integer passengersNum) {
		this.passengersNum = passengersNum;
	}

	public Liner() {
		super();
		this.setIdType(IDTYPE_AUTO);
	}

	public Liner(String id){
		super(id);
	}

	@ExcelField(title="name", align=2, sort=1)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ExcelField(title="所属公司", align=2, sort=2)
	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}
	
	@ExcelField(title="状态 0 禁用 1 正常", align=2, sort=3)
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="del_date", align=2, sort=8)
	public Date getDelDate() {
		return delDate;
	}

	public void setDelDate(Date delDate) {
		this.delDate = delDate;
	}
	
	@ExcelField(title="del_by", align=2, sort=9)
	public String getDelBy() {
		return delBy;
	}

	public void setDelBy(String delBy) {
		this.delBy = delBy;
	}
	
	@ExcelField(title="语言id", align=2, sort=11)
	public Integer getLanguageId() {
		return languageId;
	}

	public void setLanguageId(Integer languageId) {
		this.languageId = languageId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getLanguageName() {
		return languageName;
	}

	public void setLanguageName(String languageName) {
		this.languageName = languageName;
	}

	public Integer getLoginAgentid() {
		return loginAgentid;
	}

	public void setLoginAgentid(Integer loginAgentid) {
		this.loginAgentid = loginAgentid;
	}
	
	
}