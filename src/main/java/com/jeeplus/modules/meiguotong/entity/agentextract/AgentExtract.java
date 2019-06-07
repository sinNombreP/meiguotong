/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.entity.agentextract;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 代理商提现Entity
 * @author dong
 * @version 2019-05-17
 */
public class AgentExtract extends DataEntity<AgentExtract> {
	
	private static final long serialVersionUID = 1L;
	private String no;		// 流水账号
	private Double count;		// 订单总金额
	private Double extract;		// 提现金额
	private String people;		// 开户人
	private String pay;		// 开户账号
	private String rank;		// 开户银行
	private Integer status;		// 状态1已申请2同意申请3拒绝申请4已完成
	private String orderids;		// 订单ID，多个
	private String agentName;		// 供应商名称
	
	
	public AgentExtract() {
		super();
		this.setIdType(IDTYPE_AUTO);
	}

	public AgentExtract(String id){
		super(id);
	}

	@ExcelField(title="流水账号", align=2, sort=1)
	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}
	
	@ExcelField(title="订单总金额", align=2, sort=2)
	public Double getCount() {
		return count;
	}

	public void setCount(Double count) {
		this.count = count;
	}
	
	@ExcelField(title="提现金额", align=2, sort=3)
	public Double getExtract() {
		return extract;
	}

	public void setExtract(Double extract) {
		this.extract = extract;
	}
	
	@ExcelField(title="开户人", align=2, sort=4)
	public String getPeople() {
		return people;
	}

	public void setPeople(String people) {
		this.people = people;
	}
	
	@ExcelField(title="开户账号", align=2, sort=5)
	public String getPay() {
		return pay;
	}

	public void setPay(String pay) {
		this.pay = pay;
	}
	
	@ExcelField(title="开户银行", align=2, sort=6)
	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}
	
	@ExcelField(title="状态1已申请2同意申请3拒绝申请4已完成", align=2, sort=8)
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@ExcelField(title="订单ID，多个", align=2, sort=9)
	public String getOrderids() {
		return orderids;
	}

	public void setOrderids(String orderids) {
		this.orderids = orderids;
	}
	
	@ExcelField(title="供应商名称", align=2, sort=11)
	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}
	
}