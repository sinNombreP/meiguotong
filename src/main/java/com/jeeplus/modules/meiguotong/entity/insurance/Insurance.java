/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.entity.insurance;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 保险表Entity
 * @author cdq
 * @version 2018-08-15
 */
public class Insurance extends DataEntity<Insurance> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 保险名称
	private BigDecimal price;		// 价格
	private Integer status;		// 状态 0 禁用 1启用
	private Date delDate;		// del_date
	private String delBy;		// del_by
	private Integer languageId;		// 语言id
	private String content;		// content
	private Integer num;		// 预定数量
	private Integer memberId; //下单人id
	private Integer checkedFlag; //是否关联1.是2.否
	
	public Insurance() {
		super();
		this.setIdType(IDTYPE_AUTO);
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Insurance(String id){
		super(id);
	}

	@ExcelField(title="保险名称", align=2, sort=1)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ExcelField(title="价格", align=2, sort=2)
	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
	@ExcelField(title="状态 0 禁用 1启用", align=2, sort=3)
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
	
	@ExcelField(title="content", align=2, sort=12)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public Integer getCheckedFlag() {
		return checkedFlag;
	}

	public void setCheckedFlag(Integer checkedFlag) {
		this.checkedFlag = checkedFlag;
	}
	
}