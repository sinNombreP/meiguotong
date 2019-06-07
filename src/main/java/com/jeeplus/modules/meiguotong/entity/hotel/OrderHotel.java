/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.entity.hotel;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeeplus.common.utils.excel.annotation.ExcelField;
import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.modules.meiguotong.entity.orderhotelroom.OrderHotelRoom;

import net.bytebuddy.dynamic.scaffold.inline.MethodNameTransformer.Prefixing;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.management.loading.PrivateClassLoader;

/**
 * 酒店订单Entity
 * @author psz
 * @version 2018-08-23
 */
public class OrderHotel extends DataEntity<OrderHotel> {
	
	private static final long serialVersionUID = 1L;
	private Integer hotelId;		// hotel_id
	private String afterNo;		// 售后订单号
	private String orderNo;		// 订单号
	private String productName;		// 酒店名称
	private Integer memberId;		// 下单人id
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
	private Integer city;				//城市
	private String cityName;			//城市名称
	private Integer orderSys1;		//主订单ID
	private Integer orderSys2;		//对应业务的订单ID
	private String payNo;			//支付流水号
	private Date useDate;		// 使用时间
	private Date startDate;		//开始时间
	private BigDecimal refundPrice;		// 退款金额
	private String companyName;		// 供应商名称
	private Integer type;		// 1订单 2售后订单
	private Integer dataFlag;		// 1今日下单2今日出行
	private String searchContent;		// 搜索内容

	private String name;		// 订单标题
	private String hotelName;  //酒店名称
	
	private Integer memberType; //下单人类型
	private String nickName;	//下单人
	private Date beginDate;		//开始时间
	private Date endDate;		//结束时间
	
	private String mobile;		//下单人电话
	private Integer adultNum;// 成人数量
	private Integer childNum;//儿童数量
	private String remark;		//订单备注
	private String hotelNo;    //酒店编号
	private String hotelImgUrl; //酒店图片
	private String hotelAddress;//酒店地址
	
	private List<OrderHotelRoom> orderHotelRoomList;  //酒店订单房间集合
	
	public OrderHotel() {
		super();
		this.setIdType(IDTYPE_AUTO);
	}

	public String getHotelName() {
		return hotelName;
	}

	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
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

	public OrderHotel(String id){
		super(id);
	}

	@ExcelField(title="hotel_id", align=2, sort=1)
	public Integer getHotelId() {
		return hotelId;
	}

	public void setHotelId(Integer hotelId) {
		this.hotelId = hotelId;
	}
	
	@ExcelField(title="售后订单号", align=2, sort=2)
	public String getAfterNo() {
		return afterNo;
	}

	public void setAfterNo(String afterNo) {
		this.afterNo = afterNo;
	}
	
	@ExcelField(title="订单号", align=2, sort=3)
	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
	@ExcelField(title="订单产品名称", align=2, sort=4)
	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	@ExcelField(title="下单人id", align=2, sort=5)
	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
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
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="使用时间", align=2, sort=23)
	public Date getUseDate() {
		return useDate;
	}

	public void setUseDate(Date useDate) {
		this.useDate = useDate;
	}
	
	@ExcelField(title="退款金额", align=2, sort=25)
	public BigDecimal getRefundPrice() {
		return refundPrice;
	}

	public void setRefundPrice(BigDecimal refundPrice) {
		this.refundPrice = refundPrice;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<OrderHotelRoom> getOrderHotelRoomList() {
		return orderHotelRoomList;
	}

	public void setOrderHotelRoomList(List<OrderHotelRoom> orderHotelRoomList) {
		this.orderHotelRoomList = orderHotelRoomList;
	}

	public Integer getMemberType() {
		return memberType;
	}

	public void setMemberType(Integer memberType) {
		this.memberType = memberType;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
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

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Integer getCity() {
		return city;
	}

	public void setCity(Integer city) {
		this.city = city;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Integer getAdultNum() {
		return adultNum;
	}

	public void setAdultNum(Integer adultNum) {
		this.adultNum = adultNum;
	}

	public Integer getChildNum() {
		return childNum;
	}

	public void setChildNum(Integer childNum) {
		this.childNum = childNum;
	}

	public String getRemark() {
		return remark;
	}

	public String getHotelNo() {
		return hotelNo;
	}

	public void setHotelNo(String hotelNo) {
		this.hotelNo = hotelNo;
	}

	public String getHotelImgUrl() {
		return hotelImgUrl;
	}

	public void setHotelImgUrl(String hotelImgUrl) {
		this.hotelImgUrl = hotelImgUrl;
	}

	public String getHotelAddress() {
		return hotelAddress;
	}

	public void setHotelAddress(String hotelAddress) {
		this.hotelAddress = hotelAddress;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}


	public String getPayNo() {
		return payNo;
	}

	public void setPayNo(String payNo) {
		this.payNo = payNo;
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
	
	
	
	
}