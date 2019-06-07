/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.entity.comcurrency;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 汇率表Entity
 * @author cdq
 * @version 2018-08-01
 */
public class ComCurrency extends DataEntity<ComCurrency> {
	
	private static final long serialVersionUID = 1L;
	private Date delDate;		// del_date
	private String delBy;		// del_by
	private Integer status;		// 状态 0 禁用 1启用
	private BigDecimal rate;		// 汇率 以人民币为基准
	private String currency;		// 货币
	private String sign;		// 符号

	private Integer currencyid;		// 货币id
	private Integer languageid;		// 语言id
	
	public ComCurrency() {
		super();
		this.setIdType(IDTYPE_AUTO);
	}

	public ComCurrency(String id){
		super(id);
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="del_date", align=2, sort=5)
	public Date getDelDate() {
		return delDate;
	}

	public void setDelDate(Date delDate) {
		this.delDate = delDate;
	}
	
	@ExcelField(title="del_by", align=2, sort=6)
	public String getDelBy() {
		return delBy;
	}

	public void setDelBy(String delBy) {
		this.delBy = delBy;
	}
	
	@ExcelField(title="状态 0 禁用 1启用", align=2, sort=8)
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@ExcelField(title="汇率 以人民币为基准", align=2, sort=9)
	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}
	
	@ExcelField(title="货币", align=2, sort=10)
	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public Integer getCurrencyid() {
		return currencyid;
	}

	public void setCurrencyid(Integer currencyid) {
		this.currencyid = currencyid;
	}

	public Integer getLanguageid() {
		return languageid;
	}

	public void setLanguageid(Integer languageid) {
		this.languageid = languageid;
	}
	
}