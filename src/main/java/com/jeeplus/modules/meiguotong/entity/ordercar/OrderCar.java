/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.entity.ordercar;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeeplus.common.utils.excel.annotation.ExcelField;
import com.jeeplus.core.persistence.DataEntity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 车辆订单表Entity
 * @author cdq
 * @version 2018-08-23
 */
public class OrderCar extends DataEntity<OrderCar> {
	
	private static final long serialVersionUID = 1L;
	private Integer type;		// 订单类型1.包车租车2.短程接送3.接送机4.定制
	private Integer adultNum;		// 大人数量
	private Integer childNum;		// 孩子数量
	private Integer bagNum;		// 包裹数量
	private String startCity;		// 出发城市
	private String name;		// 订单标题
	private String startAddress;		// 出发详细地址
	private String endCity;		// 到达城市
	private String endAddress;		// 到达详细地址
	private Date beginDate;		// 上车时间
	private Date endDate;		// 结束时间
	private String airType;		// 接送机1.接机2.送机
	private String airNo;		// 航班号
	private String sendAirport;		// 飞机起飞机场
	private Date sendDate;		// 飞机起飞时间
	private String reachAirport;		// 到达机场
	private Date reachDate;		// 到达时间
	private String contactsName;		// 联系人姓名
	private String contactsMobile;		// 联系人电话
	private String remark;		// 备注
	private BigDecimal price;		// 金额
	private String carid;		// 多个用,隔开
	private String carName;		// 汽车名称
	private Integer payWay;		// 1.支付宝2.微信3.银联4.paypal5.线下
	private Date payDate;		// 支付时间
	private Integer status;		// 订单状态1.待付款2.待确定，3.待出行，4.待评价 5.已完成6.取消订单7.申请退款8退款通过9.退款不通过
	private Date delDate;		// del_date
	private String delBy;		// del_by
	private Integer languageId;		// 语言
	private String refundInfo;		// 退款说明
	private String refundReason;		// 退款原因
	private String orderNo;		// 订单号
	private String memberId;		// 下单人ID
	private Date useDate;		// 使用时间
	private String nickName;//真实姓名
	private String mobile;//手机号码
	private String payNo; //支付流水号
	private String afterNo;//售后订单号
	private String carDistance;//行程行驶距离
	private String carTime;//行程行驶时间
	private Integer seatNum;
	private String comfort;
	private Integer carBagNum;
	private Integer orderSys1;//主订单id
	private Integer orderSys2;//对应业务的订单id
	
	private Integer ordertype;		//订单类型（1为订单列表，2为售后）
	private Integer dataFlag;			//今天订单
	private String searchContent;			//搜索（订单号/联系人/车辆名称）
	private String companyName;		//供应商
	private Integer memberType;		//下单人类型
	
	private String orderRemark;		//订单备注
	
	private String typeName;//类型名称
	private String date;//时间
	private Date date2;
	
	private List<OrderCar> orderCarList;
	
	private List<OrderCarDate>  orderCarDates;
	
	public String getAfterNo() {
		return afterNo;
	}

	public void setAfterNo(String afterNo) {
		this.afterNo = afterNo;
	}

	public String getPayNo() {
		return payNo;
	}

	public void setPayNo(String payNo) {
		this.payNo = payNo;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public OrderCar() {
		super();
		this.setIdType(IDTYPE_AUTO);
	}

	public OrderCar(String id){
		super(id);
	}

	@ExcelField(title="订单类型1.包车租车2.短程接送3.接送机4.旅游定制5.商务定制", align=2, sort=1)
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
	@ExcelField(title="大人数量", align=2, sort=2)
	public Integer getAdultNum() {
		return adultNum;
	}

	public void setAdultNum(Integer adultNum) {
		this.adultNum = adultNum;
	}
	
	@ExcelField(title="孩子数量", align=2, sort=3)
	public Integer getChildNum() {
		return childNum;
	}

	public void setChildNum(Integer childNum) {
		this.childNum = childNum;
	}
	
	@ExcelField(title="包裹数量", align=2, sort=4)
	public Integer getBagNum() {
		return bagNum;
	}

	public void setBagNum(Integer bagNum) {
		this.bagNum = bagNum;
	}
	
	@ExcelField(title="出发城市", align=2, sort=5)
	public String getStartCity() {
		return startCity;
	}

	public void setStartCity(String startCity) {
		this.startCity = startCity;
	}
	
	@ExcelField(title="出发详细地址", align=2, sort=6)
	public String getStartAddress() {
		return startAddress;
	}

	public void setStartAddress(String startAddress) {
		this.startAddress = startAddress;
	}
	
	@ExcelField(title="到达城市", align=2, sort=7)
	public String getEndCity() {
		return endCity;
	}

	public void setEndCity(String endCity) {
		this.endCity = endCity;
	}
	
	@ExcelField(title="到达详细地址", align=2, sort=8)
	public String getEndAddress() {
		return endAddress;
	}

	public void setEndAddress(String endAddress) {
		this.endAddress = endAddress;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="上车时间", align=2, sort=9)
	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="结束时间", align=2, sort=10)
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	@ExcelField(title="接送机1.接机2.送机", align=2, sort=11)
	public String getAirType() {
		return airType;
	}

	public void setAirType(String airType) {
		this.airType = airType;
	}
	
	@ExcelField(title="航班号", align=2, sort=12)
	public String getAirNo() {
		return airNo;
	}

	public void setAirNo(String airNo) {
		this.airNo = airNo;
	}
	
	@ExcelField(title="飞机起飞机场", align=2, sort=13)
	public String getSendAirport() {
		return sendAirport;
	}

	public void setSendAirport(String sendAirport) {
		this.sendAirport = sendAirport;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="飞机起飞时间", align=2, sort=14)
	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}
	
	@ExcelField(title="到达机场", align=2, sort=15)
	public String getReachAirport() {
		return reachAirport;
	}

	public void setReachAirport(String reachAirport) {
		this.reachAirport = reachAirport;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="到达时间", align=2, sort=16)
	public Date getReachDate() {
		return reachDate;
	}

	public void setReachDate(Date reachDate) {
		this.reachDate = reachDate;
	}
	
	@ExcelField(title="联系人姓名", align=2, sort=17)
	public String getContactsName() {
		return contactsName;
	}

	public void setContactsName(String contactsName) {
		this.contactsName = contactsName;
	}
	
	@ExcelField(title="联系人电话", align=2, sort=18)
	public String getContactsMobile() {
		return contactsMobile;
	}

	public void setContactsMobile(String contactsMobile) {
		this.contactsMobile = contactsMobile;
	}
	
	@ExcelField(title="备注", align=2, sort=19)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@ExcelField(title="金额", align=2, sort=20)
	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
	@ExcelField(title="多个用,隔开", align=2, sort=21)
	public String getCarid() {
		return carid;
	}

	public void setCarid(String carid) {
		this.carid = carid;
	}
	
	@ExcelField(title="1.支付宝2.微信3.银联4.paypal5.线下", align=2, sort=22)
	public Integer getPayWay() {
		return payWay;
	}

	public void setPayWay(Integer payWay) {
		this.payWay = payWay;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="支付时间", align=2, sort=23)
	public Date getPayDate() {
		return payDate;
	}

	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}
	

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="del_date", align=2, sort=29)
	public Date getDelDate() {
		return delDate;
	}

	public void setDelDate(Date delDate) {
		this.delDate = delDate;
	}
	
	@ExcelField(title="del_by", align=2, sort=30)
	public String getDelBy() {
		return delBy;
	}

	public void setDelBy(String delBy) {
		this.delBy = delBy;
	}
	
	@ExcelField(title="语言", align=2, sort=32)
	public Integer getLanguageId() {
		return languageId;
	}

	public void setLanguageId(Integer languageId) {
		this.languageId = languageId;
	}
	
	@ExcelField(title="退款说明", align=2, sort=33)
	public String getRefundInfo() {
		return refundInfo;
	}

	public void setRefundInfo(String refundInfo) {
		this.refundInfo = refundInfo;
	}
	
	@ExcelField(title="退款原因", align=2, sort=34)
	public String getRefundReason() {
		return refundReason;
	}

	public void setRefundReason(String refundReason) {
		this.refundReason = refundReason;
	}
	
	@ExcelField(title="订单号", align=2, sort=35)
	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
	@ExcelField(title="下单人ID", align=2, sort=36)
	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="使用时间", align=2, sort=37)
	public Date getUseDate() {
		return useDate;
	}

	public void setUseDate(Date useDate) {
		this.useDate = useDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCarName() {
		return carName;
	}

	public void setCarName(String carName) {
		this.carName = carName;
	}

	public String getCarDistance() {
		return carDistance;
	}

	public String getCarTime() {
		return carTime;
	}

	public void setCarDistance(String carDistance) {
		this.carDistance = carDistance;
	}

	public void setCarTime(String carTime) {
		this.carTime = carTime;
	}

	public Integer getSeatNum() {
		return seatNum;
	}


	public Integer getCarBagNum() {
		return carBagNum;
	}

	public void setSeatNum(Integer seatNum) {
		this.seatNum = seatNum;
	}

	public String getComfort() {
		return comfort;
	}

	public void setComfort(String comfort) {
		this.comfort = comfort;
	}

	public void setCarBagNum(Integer carBagNum) {
		this.carBagNum = carBagNum;
	}

	public Integer getOrdertype() {
		return ordertype;
	}

	public void setOrdertype(Integer ordertype) {
		this.ordertype = ordertype;
	}

	

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Integer getDataFlag() {
		return dataFlag;
	}

	public void setDataFlag(Integer dataFlag) {
		this.dataFlag = dataFlag;
	}

	public String getSearchContent() {
		return searchContent;
	}

	public void setSearchContent(String searchContent) {
		this.searchContent = searchContent;
	}

	public Integer getMemberType() {
		return memberType;
	}

	public void setMemberType(Integer memberType) {
		this.memberType = memberType;
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

	public List<OrderCarDate> getOrderCarDates() {
		return orderCarDates;
	}

	public void setOrderCarDates(List<OrderCarDate> orderCarDates) {
		this.orderCarDates = orderCarDates;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getDate2() {
		return date2;
	}

	public void setDate2(Date date2) {
		this.date2 = date2;
	}

	public List<OrderCar> getOrderCarList() {
		return orderCarList;
	}

	public void setOrderCarList(List<OrderCar> orderCarList) {
		this.orderCarList = orderCarList;
	}
	
	
}