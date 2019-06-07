/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.entity.orderguide;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 导游订单Entity
 * @author cdq
 * @version 2018-08-21
 */
public class OrderGuide extends DataEntity<OrderGuide> {
	
	private static final long serialVersionUID = 1L;
	private Integer status;		// 订单状态1.待付款2.待确定，3.待出行，4.待评价 5.已完成6.取消订单7.申请退款8退款通过9.退款不通过
	private Date delDate;		// del_date
	private String delBy;		// del_by
	private BigDecimal price;		// 金额
	private String guideRouteId;		// 导游路线id
	private Date beginDate;		// 开始时间
	private Date endDate;		// 结束时间
	private Integer type; //主订单的导游类型
	private Integer typeid;//对应的导游ID
	private  String contactsName;//联系人姓名
	private  String  contactsMobile;//联系人电话
	private   String remark;//备注
	private   String  orderNo;//订单号
	private  String   afterNo;//售后订单号
	private String adultNum;//成人数量
	private String childNum;//小孩数量
	private String endCity;//目的地
	private String memberId;//下单人ID
	private Integer payWay;		// 支付类型  1 支付宝 2 微信 3 银联 4 paypal 5 线下
	private Date payDate;		// 支付时间
	private  String refundReason;   //退款原因
	private  String refundInfo;//退款说明
    private String payNo;//支付流水号
    private  BigDecimal refundPrice;//退款金额
    private  Integer languageid;//语言id

	private String name;		// 订单标题
	private Integer orderMemberId;  //下单人ID
	private Date orderCreateDate;   //下单时间
	private Date useDate;  //使用时间
	private Integer orderStatus;//订单状态
    private  BigDecimal orderPrice; //订单金额
    private Integer guideid;//导游id
    private  Integer orderType;//主订单的类型
    private Integer orderSysid;//主订单ID
    private String title;
    private  String scenice;
    private String sex;
    private String age;
    private String area;
    private String realName;
    private String deltails;
    private String mobile;
    private String nickName;
	private Integer orderSys1;//主订单id
	private Integer orderSys2;//对应业务的订单id
    
    private Integer dataFlag;  //当天订单
    private String searchContent;//搜索内容
    private Integer memberType;//下单人类型
    private String guideNickName;//导游昵称
    
    private String guideTag;//导游标签
    private String countryName;//国家名称
    private String cityName;//城市名称
    
    private String orderMark;  //订单备注
    
    private String introduction;//导游简介
    
    private BigDecimal price2;//天/单价
    private Integer day;//单价
    
    
	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public Integer getPayWay() {
		return payWay;
	}

	public void setPayWay(Integer payWay) {
		this.payWay = payWay;
	}

	public Date getPayDate() {
		return payDate;
	}

	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}

	public String getPayNo() {
		return payNo;
	}

	public void setPayNo(String payNo) {
		this.payNo = payNo;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getScenice() {
		return scenice;
	}

	public void setScenice(String scenice) {
		this.scenice = scenice;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getDeltails() {
		return deltails;
	}

	public void setDeltails(String deltails) {
		this.deltails = deltails;
	}

	public String getAdultNum() {
		return adultNum;
	}

	public void setAdultNum(String adultNum) {
		this.adultNum = adultNum;
	}

	public String getChildNum() {
		return childNum;
	}

	public void setChildNum(String childNum) {
		this.childNum = childNum;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getEndCity() {
		return endCity;
	}

	public void setEndCity(String endCity) {
		this.endCity = endCity;
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

	public String getAfterNo() {
		return afterNo;
	}

	public void setAfterNo(String afterNo) {
		this.afterNo = afterNo;
	}

	public String getRefundReason() {
		return refundReason;
	}

	public void setRefundReason(String refundReason) {
		this.refundReason = refundReason;
	}

	public String getRefundInfo() {
		return refundInfo;
	}

	public void setRefundInfo(String refundInfo) {
		this.refundInfo = refundInfo;
	}

	public Integer getTypeid() {
		return typeid;
	}

	public void setTypeid(Integer typeid) {
		this.typeid = typeid;
	}

	public Integer getOrderSysid() {
		return orderSysid;
	}

	public void setOrderSysid(Integer orderSysid) {
		this.orderSysid = orderSysid;
	}

	public Integer getOrderType() {
		return orderType;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getGuideid() {
		return guideid;
	}

	public void setGuideid(Integer guideid) {
		this.guideid = guideid;
	}

	public Integer getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}


	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getOrderCreateDate() {
		return orderCreateDate;
	}

	public void setOrderCreateDate(Date orderCreateDate) {
		this.orderCreateDate = orderCreateDate;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getUseDate() {
		return useDate;
	}

	public void setUseDate(Date useDate) {
		this.useDate = useDate;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
	public String getGuideRouteId() {
		return guideRouteId;
	}

	public void setGuideRouteId(String guideRouteId) {
		this.guideRouteId = guideRouteId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getOrderMemberId() {
		return orderMemberId;
	}

	public void setOrderMemberId(Integer orderMemberId) {
		this.orderMemberId = orderMemberId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public OrderGuide() {
		super();
		this.setIdType(IDTYPE_AUTO);
	}

	public OrderGuide(String id){
		super(id);
	}

	@ExcelField(title="订单状态1.待付款2.待确定，3.待出行，4.待评价 5.已完成6.取消订单7.申请退款8退款通过9.退款不通过", align=2, sort=1)
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="del_date", align=2, sort=6)
	public Date getDelDate() {
		return delDate;
	}

	public void setDelDate(Date delDate) {
		this.delDate = delDate;
	}
	
	@ExcelField(title="del_by", align=2, sort=7)
	public String getDelBy() {
		return delBy;
	}

	public void setDelBy(String delBy) {
		this.delBy = delBy;
	}
	
	
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="开始时间", align=2, sort=11)
	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="结束时间", align=2, sort=12)
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getLanguageid() {
		return languageid;
	}

	public void setLanguageid(Integer languageid) {
		this.languageid = languageid;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public BigDecimal getRefundPrice() {
		return refundPrice;
	}

	public BigDecimal getOrderPrice() {
		return orderPrice;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public void setRefundPrice(BigDecimal refundPrice) {
		this.refundPrice = refundPrice;
	}

	public void setOrderPrice(BigDecimal orderPrice) {
		this.orderPrice = orderPrice;
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

	public String getGuideNickName() {
		return guideNickName;
	}

	public void setGuideNickName(String guideNickName) {
		this.guideNickName = guideNickName;
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

	public String getOrderMark() {
		return orderMark;
	}

	public void setOrderMark(String orderMark) {
		this.orderMark = orderMark;
	}

	public String getGuideTag() {
		return guideTag;
	}

	public void setGuideTag(String guideTag) {
		this.guideTag = guideTag;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public BigDecimal getPrice2() {
		return price2;
	}

	public void setPrice2(BigDecimal price2) {
		this.price2 = price2;
	}

	public Integer getDay() {
		return day;
	}

	public void setDay(Integer day) {
		this.day = day;
	}
	
}