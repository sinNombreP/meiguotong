/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.entity.order;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.jeeplus.modules.sys.entity.member.MemberContacts;

/**
 * 订单关联表Entity
 * @author PSZ
 * @version 2018-08-17
 */
public class OrderDetail{
	private Integer id;    //主订单id
	private Integer type;		// 订单类型1.旅游定制2.商务定制3.旅游商务定制4.汽车订单5.常规路线6.当地参团7.游轮8.景点9.导游10.保险
	private Integer status;      	//订单状态
	private OrderRoute orderRoute;//常规路线
	private List<OrderMemberApp> orderMemberApps;//出游人信息
	private String createDateString;      	//下单时间
	private String orderNo;//订单号
	private String agent;//供应商
	private Integer bagNum;			//包裹数
	private Integer fatherid;		// 父id
	private BigDecimal price;          //总价
	private String contactsName;	//联系人姓名
	private String contactsMobile;	//联系人电话
	private String remark;			//备注
	private  OrderInsuranceApp orderInsuranceApp;//保险信息
	public OrderInsuranceApp getOrderInsuranceApp() {
		return orderInsuranceApp;
	}

	public void setOrderInsuranceApp(OrderInsuranceApp orderInsuranceApp) {
		this.orderInsuranceApp = orderInsuranceApp;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getContactsName() {
		return contactsName;
	}

	public void setContactsName(String contactsName) {
		this.contactsName = contactsName;
	}

	public String getContactsMobile() {
		return contactsMobile;
	}

	public void setContactsMobile(String contactsMobile) {
		this.contactsMobile = contactsMobile;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getBagNum() {
		return bagNum;
	}

	public void setBagNum(Integer bagNum) {
		this.bagNum = bagNum;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getAgent() {
		return agent;
	}

	public void setAgent(String agent) {
		this.agent = agent;
	}

	
	public Integer getFatherid() {
		return fatherid;
	}

	public void setFatherid(Integer fatherid) {
		this.fatherid = fatherid;
	}

	public String getCreateDateString() {
		return createDateString;
	}

	public void setCreateDateString(String createDateString) {
		this.createDateString = createDateString;
	}


	public Integer getType() {
		return type;
	}


	public void setType(Integer type) {
		this.type = type;
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	public OrderRoute getOrderRoute() {
		return orderRoute;
	}

	public void setOrderRoute(OrderRoute orderRoute) {
		this.orderRoute = orderRoute;
	}
	public List<OrderMemberApp> getOrderMemberApps() {
		return orderMemberApps;
	}

	public void setOrderMemberApps(List<OrderMemberApp> orderMemberApps) {
		this.orderMemberApps = orderMemberApps;
	}

}