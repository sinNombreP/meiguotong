/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.entity.orderliner;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeeplus.common.utils.excel.annotation.ExcelField;
import com.jeeplus.core.persistence.DataEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 邮轮订单表Entity
 * @author cdq
 * @version 2018-08-15
 */
public class OrderLiner extends DataEntity<OrderLiner> {
	
	private static final long serialVersionUID = 1L;
	private String orderNo;		// 订单号
	private Integer linerLineId;		// 邮轮路线id
	private String productName;		// 订单产品名称
	private String memberId;		// 下单人id
	private Date useDate;		// 使用时间
	private BigDecimal price;		// 订单价格
	private Integer status;		// 订单状态  1 待付款 2 待确认 3 待出行 4 待点评 5 已完成 6 已取消 7 申请退款 8 退款成功 9 退款失败
	private String contacts;		// 联系人
	private String contactMobile;		// 联系电话
	private String contactRemark;		// 备注
	private Date delDate;		// del_date
	private String delBy;		// del_by
	private Integer languageId;		// 语言id
	private Integer payWay;		// 支付类型  1 支付宝 2 微信 3 银联 4 paypal 5 线下
	private Date payDate;		// 支付时间
	private String refundInfo;		// 退款说明
	private String refundReason;		// 退款原因
	private Date startDate;				//开始时间
	private  String curiseCompany; //邮轮公司
	private String mobile;//手机号
	private  String  nickName;//昵称
	private  String  name;//订单标题
	private Integer cityid; //'出发城市id'
	private  String  cityName; //'出发城市名称'
	private Integer portid; //'出发港口id'
	private  String  portName; //'出发港口名称'
	private  String  lineNo; //'路线编号'
	private Integer courseid; //'邮轮航区id'
	private  String  courseName;//'邮轮航区名称'
	private Integer downPortid;    //'下船港口',
	private  String downPortName; //'下船港口名称',
	/** 主订单id */
	private Integer orderSys1;
	/** 对应业务的订单id */
	private Integer orderSys2;
	private String payNo; 
	

	private Integer dataFlag;		//今日订单
	private Integer memberType;	//下单人类型
	private Date beginDate;//使用开始时间
	private Date endDate;//使用结束时间
	
	

	public String getPayNo() {
		return payNo;
	}

	public void setPayNo(String payNo) {
		this.payNo = payNo;
	}

	public Integer getOrderSys1() {
		return orderSys1;
	}

	public void setOrderSys1(Integer orderSys1) {
		this.orderSys1 = orderSys1;
	}

	public Integer getOrderSys2() {
		return orderSys2;
	}

	public void setOrderSys2(Integer orderSys2) {
		this.orderSys2 = orderSys2;
	}


	
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public OrderLiner() {
		super();
		this.setIdType(IDTYPE_AUTO);
	}

	public OrderLiner(String id){
		super(id);
	}

	public String getCuriseCompany() {
		return curiseCompany;
	}

	public void setCuriseCompany(String curiseCompany) {
		this.curiseCompany = curiseCompany;
	}

	@ExcelField(title="订单号", align=2, sort=1)
	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
	@ExcelField(title="邮轮路线id", align=2, sort=2)
	public Integer getLinerLineId() {
		return linerLineId;
	}

	public void setLinerLineId(Integer linerLineId) {
		this.linerLineId = linerLineId;
	}
	
	@ExcelField(title="订单产品名称", align=2, sort=3)
	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	@ExcelField(title="下单人id", align=2, sort=4)
	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="使用时间", align=2, sort=5)
	public Date getUseDate() {
		return useDate;
	}

	public void setUseDate(Date useDate) {
		this.useDate = useDate;
	}
	
	@ExcelField(title="订单价格", align=2, sort=6)
	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
	@ExcelField(title="订单状态  1 待付款 2 待确认 3 待出行 4 待点评 5 已完成 6 已取消 7 申请退款 8 退款成功 9 退款失败", align=2, sort=7)
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@ExcelField(title="联系人", align=2, sort=8)
	public String getContacts() {
		return contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}
	
	@ExcelField(title="联系电话", align=2, sort=9)
	public String getContactMobile() {
		return contactMobile;
	}

	public void setContactMobile(String contactMobile) {
		this.contactMobile = contactMobile;
	}
	
	@ExcelField(title="备注", align=2, sort=10)
	public String getContactRemark() {
		return contactRemark;
	}

	public void setContactRemark(String contactRemark) {
		this.contactRemark = contactRemark;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="del_date", align=2, sort=15)
	public Date getDelDate() {
		return delDate;
	}

	public void setDelDate(Date delDate) {
		this.delDate = delDate;
	}
	
	@ExcelField(title="del_by", align=2, sort=16)
	public String getDelBy() {
		return delBy;
	}

	public void setDelBy(String delBy) {
		this.delBy = delBy;
	}
	
	@ExcelField(title="语言id", align=2, sort=18)
	public Integer getLanguageId() {
		return languageId;
	}

	public void setLanguageId(Integer languageId) {
		this.languageId = languageId;
	}
	
	@ExcelField(title="支付类型  1 支付宝 2 微信 3 银联 4 paypal 5 线下", align=2, sort=19)
	public Integer getPayWay() {
		return payWay;
	}

	public void setPayWay(Integer payWay) {
		this.payWay = payWay;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="支付时间", align=2, sort=20)
	public Date getPayDate() {
		return payDate;
	}

	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}
	
	@ExcelField(title="退款说明", align=2, sort=21)
	public String getRefundInfo() {
		return refundInfo;
	}

	public void setRefundInfo(String refundInfo) {
		this.refundInfo = refundInfo;
	}
	
	@ExcelField(title="退款原因", align=2, sort=22)
	public String getRefundReason() {
		return refundReason;
	}

	public void setRefundReason(String refundReason) {
		this.refundReason = refundReason;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getCityid() {
		return cityid;
	}

	public String getCityName() {
		return cityName;
	}

	public Integer getPortid() {
		return portid;
	}

	public String getPortName() {
		return portName;
	}

	public String getLineNo() {
		return lineNo;
	}

	public Integer getCourseid() {
		return courseid;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCityid(Integer cityid) {
		this.cityid = cityid;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public void setPortid(Integer portid) {
		this.portid = portid;
	}

	public void setPortName(String portName) {
		this.portName = portName;
	}

	public void setLineNo(String lineNo) {
		this.lineNo = lineNo;
	}

	public void setCourseid(Integer courseid) {
		this.courseid = courseid;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public Integer getDownPortid() {
		return downPortid;
	}

	public String getDownPortName() {
		return downPortName;
	}

	public void setDownPortid(Integer downPortid) {
		this.downPortid = downPortid;
	}

	public void setDownPortName(String downPortName) {
		this.downPortName = downPortName;
	}

	

	public Integer getDataFlag() {
		return dataFlag;
	}

	public void setDataFlag(Integer dataFlag) {
		this.dataFlag = dataFlag;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Integer getMemberType() {
		return memberType;
	}

	public void setMemberType(Integer memberType) {
		this.memberType = memberType;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
}