/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.entity.travel;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeeplus.common.utils.excel.annotation.ExcelField;
import com.jeeplus.core.persistence.DataEntity;

/**
 * 定制订单Entity
 * @author psz
 * @version 2018-08-29
 */
public class OrderTravelBusiness extends DataEntity<OrderTravelBusiness> {
	
	private static final long serialVersionUID = 1L;
	private Integer languageId;		// 语言
	private String orderNo;		// 订单号
	private String name;		// 订单名称
	private String afterNo;		// 售后订单号
	private Integer type;		// 1.旅游定制2.商务定制3.旅游商务定制
	private Integer city;		// 出发城市
	private String address;		// 出发地址
	private Date startDate;		// 开始时间
	private Date endDate;		// 结束时间
	private Integer adultNum;		// 大人数量
	private Integer childNum;		// 孩子数量
	private Integer bagNum;		// 包裹数量
	private Integer dayNum;		// 旅游天数
	private Integer payWay;		// 1.支付宝2.微信3.银联4.paypal5.线下
	private Date payDate;		// 支付时间
	private Integer status;		// 订单状态1.待付款2.待确定，3.待出行，4.待评价 5.已完成6.取消订单7.申请退款8退款通过9.退款不通过
	private Date delDate;		// del_date
	private String delBy;		// del_by
	private String refundInfo;		// 退款说明
	private String refundReason;		// 退款原因
	private String contactsName;		// 联系人姓名
	private String contactsMobile;		// 联系人电话
	private String remark;		// 备注
	private Integer memberId;		// 下单人id
	private Date useDate;		// 使用时间
	private BigDecimal price;		// 总价
	private String payNo;		// 支付流水号
	private Date refundDate;  //确定退款时间
	private Integer orderSys1;//主订单id
	private Integer orderSys2;//对应业务的订单id
	
	private Integer orderType;  //1订单 2售后订单
	private Integer dataFlag; //1 今日订单 2今日出行
	private String searchContent;  //搜索订单
	private String cityName;  //出发城市名称
	private String mobile;  //下单人手机号
	private String nickName;  //下单人昵称
	
	private Integer memberType;		//下单人类型
	private Date beginDate;			//使用开始时间
	
	private String orderRemark;//订单备注
	
	private String business;//业务名称
	
	private List<OrderTravelDetails> orderTravelDetails;
	
	
	public OrderTravelBusiness() {
		super();
		this.setIdType(IDTYPE_AUTO);
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

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public Date getRefundDate() {
		return refundDate;
	}

	public void setRefundDate(Date refundDate) {
		this.refundDate = refundDate;
	}

	public String getSearchContent() {
		return searchContent;
	}

	public void setSearchContent(String searchContent) {
		this.searchContent = searchContent;
	}

	public Integer getDataFlag() {
		return dataFlag;
	}

	public void setDataFlag(Integer dataFlag) {
		this.dataFlag = dataFlag;
	}

	public Integer getOrderType() {
		return orderType;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

	public OrderTravelBusiness(String id){
		super(id);
	}

	@ExcelField(title="语言", align=2, sort=1)
	public Integer getLanguageId() {
		return languageId;
	}

	public void setLanguageId(Integer languageId) {
		this.languageId = languageId;
	}
	
	@ExcelField(title="订单号", align=2, sort=2)
	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
	@ExcelField(title="名称", align=2, sort=3)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ExcelField(title="售后订单号", align=2, sort=4)
	public String getAfterNo() {
		return afterNo;
	}

	public void setAfterNo(String afterNo) {
		this.afterNo = afterNo;
	}
	
	@ExcelField(title="1.旅游定制2.商务定制3.旅游商务定制", align=2, sort=5)
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
	@ExcelField(title="出发城市", align=2, sort=6)
	public Integer getCity() {
		return city;
	}

	public void setCity(Integer city) {
		this.city = city;
	}
	
	@ExcelField(title="出发地址", align=2, sort=7)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="开始时间", align=2, sort=8)
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="结束时间", align=2, sort=9)
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	@ExcelField(title="大人数量", align=2, sort=10)
	public Integer getAdultNum() {
		return adultNum;
	}

	public void setAdultNum(Integer adultNum) {
		this.adultNum = adultNum;
	}
	
	@ExcelField(title="孩子数量", align=2, sort=11)
	public Integer getChildNum() {
		return childNum;
	}

	public void setChildNum(Integer childNum) {
		this.childNum = childNum;
	}
	
	@ExcelField(title="包裹数量", align=2, sort=12)
	public Integer getBagNum() {
		return bagNum;
	}

	public void setBagNum(Integer bagNum) {
		this.bagNum = bagNum;
	}
	
	@ExcelField(title="旅游天数", align=2, sort=13)
	public Integer getDayNum() {
		return dayNum;
	}

	public void setDayNum(Integer dayNum) {
		this.dayNum = dayNum;
	}
	
	@ExcelField(title="1.支付宝2.微信3.银联4.paypal5.线下", align=2, sort=14)
	public Integer getPayWay() {
		return payWay;
	}

	public void setPayWay(Integer payWay) {
		this.payWay = payWay;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="支付时间", align=2, sort=15)
	public Date getPayDate() {
		return payDate;
	}

	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}
	
	@ExcelField(title="订单状态1.待付款2.待确定，3.待出行，4.待评价 5.已完成6.取消订单7.申请退款8退款通过9.退款不通过", align=2, sort=16)
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="del_date", align=2, sort=21)
	public Date getDelDate() {
		return delDate;
	}

	public void setDelDate(Date delDate) {
		this.delDate = delDate;
	}
	
	@ExcelField(title="del_by", align=2, sort=22)
	public String getDelBy() {
		return delBy;
	}

	public void setDelBy(String delBy) {
		this.delBy = delBy;
	}
	
	@ExcelField(title="退款说明", align=2, sort=24)
	public String getRefundInfo() {
		return refundInfo;
	}

	public void setRefundInfo(String refundInfo) {
		this.refundInfo = refundInfo;
	}
	
	@ExcelField(title="退款原因", align=2, sort=25)
	public String getRefundReason() {
		return refundReason;
	}

	public void setRefundReason(String refundReason) {
		this.refundReason = refundReason;
	}
	
	@ExcelField(title="联系人姓名", align=2, sort=26)
	public String getContactsName() {
		return contactsName;
	}

	public void setContactsName(String contactsName) {
		this.contactsName = contactsName;
	}
	
	@ExcelField(title="联系人电话", align=2, sort=27)
	public String getContactsMobile() {
		return contactsMobile;
	}

	public void setContactsMobile(String contactsMobile) {
		this.contactsMobile = contactsMobile;
	}
	
	@ExcelField(title="备注", align=2, sort=28)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@ExcelField(title="下单人id", align=2, sort=29)
	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="使用时间", align=2, sort=30)
	public Date getUseDate() {
		return useDate;
	}

	public void setUseDate(Date useDate) {
		this.useDate = useDate;
	}
	
	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
	@ExcelField(title="支付流水号", align=2, sort=32)
	public String getPayNo() {
		return payNo;
	}

	public void setPayNo(String payNo) {
		this.payNo = payNo;
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

	public Integer getOrderSys1() {
		return orderSys1;
	}

	public Integer getOrderSys2() {
		return orderSys2;
	}

	public void setOrderSys1(Integer orderSys1) {
		this.orderSys1 = orderSys1;
	}

	public void setOrderSys2(Integer orderSys2) {
		this.orderSys2 = orderSys2;
	}

	public String getOrderRemark() {
		return orderRemark;
	}

	public void setOrderRemark(String orderRemark) {
		this.orderRemark = orderRemark;
	}

	public String getBusiness() {
		return business;
	}

	public void setBusiness(String business) {
		this.business = business;
	}

	public List<OrderTravelDetails> getOrderTravelDetails() {
		return orderTravelDetails;
	}

	public void setOrderTravelDetails(List<OrderTravelDetails> orderTravelDetails) {
		this.orderTravelDetails = orderTravelDetails;
	}
	
}