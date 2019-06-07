/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.entity.member;


import java.util.List;

import com.jeeplus.common.utils.excel.annotation.ExcelField;
import com.jeeplus.core.persistence.DataEntity;

/**
 * 退款设置表Entity
 * @author psz
 * @version 2018-08-09
 */
public class MemberRefund extends DataEntity<MemberRefund> {
	
	private static final long serialVersionUID = 1L;
	private Integer type;		// 账号类型1.导游2.供应商
	private Integer typeId;		// 相应的类型ID
	private String refundContent;		// 类型文字
	private Integer refundType;		// 退款类型1.包车租车2.短程接送3.接送机4常规路线5.当地参团6.游轮
										//7.景点门票9.酒店10.保险11.当地玩家
										//12.旅游定制-导游13.旅游定制-司兼导14包车/租车-导游15包车/租车-司兼导
	private Integer refundDay;		// 退款天数
	private Double refundNum;		// 退款比例(n/100)
	private Integer fathflag;  //区分是否添加了这个类型
	private List<MemberRefund> memberRefundList;
	private String flag;
	
	public MemberRefund() {
		super();
		this.setIdType(IDTYPE_AUTO);
	}

	public List<MemberRefund> getMemberRefundList() {
		return memberRefundList;
	}

	public void setMemberRefundList(List<MemberRefund> memberRefundList) {
		this.memberRefundList = memberRefundList;
	}

	public Integer getFathflag() {
		return fathflag;
	}

	public void setFathflag(Integer fathflag) {
		this.fathflag = fathflag;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public MemberRefund(String id){
		super(id);
	}

	@ExcelField(title="账号类型1.导游2.供应商", dictType="", align=2, sort=1)
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
	@ExcelField(title="相应的类型ID", align=2, sort=2)
	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}
	
	@ExcelField(title="类型文字", align=2, sort=3)
	public String getRefundContent() {
		return refundContent;
	}

	public void setRefundContent(String refundContent) {
		this.refundContent = refundContent;
	}
	
	@ExcelField(title="退款类型1.包车租车2.短程接送", dictType="", align=2, sort=4)
	public Integer getRefundType() {
		return refundType;
	}

	public void setRefundType(Integer refundType) {
		this.refundType = refundType;
	}
	
	@ExcelField(title="退款天数", align=2, sort=5)
	public Integer getRefundDay() {
		return refundDay;
	}

	public void setRefundDay(Integer refundDay) {
		this.refundDay = refundDay;
	}
	
	@ExcelField(title="退款比例(n/100)", align=2, sort=6)
	public Double getRefundNum() {
		return refundNum;
	}

	public void setRefundNum(Double refundNum) {
		this.refundNum = refundNum;
	}
	
}