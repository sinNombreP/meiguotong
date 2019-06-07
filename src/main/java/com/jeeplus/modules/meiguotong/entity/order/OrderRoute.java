/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.entity.order;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 参团订单Entity
 * @author PSZ
 * @version 2018-08-17
 */
public class OrderRoute extends DataEntity<OrderRoute> {
	
	private static final long serialVersionUID = 1L;
	private String orderNo;  //订单号
	private String afterNo;  //售后订单号
	private Integer routeid;		// 常规路线/参团id
	private Integer memberid;		// 会员ID
	private Integer memberType;      //下单人类型
	private Integer startCity;		// 出发城市
	private Date startDate;		// 出发时间
	private Integer adultNum;		// 大人数量
	private Integer childNum;		// 孩子数量
	private Integer oneNum;		// 单人间数量
	private Integer twoNum;		// 双人间数量
	private Integer threeNum;		// 三人间数量
	private Integer fourNum;		// 四人间数量
	private Integer arrangeNum;		// 配房数量
	private BigDecimal price;		// 总价
	private String contactsName;		// 联系人姓名
	private String contactsMobile;		// 联系人电话
	private String remark;		// 备注
	private Integer payWay;		// 1.支付宝2.微信3.银联4.paypal5.线下
	private Date payDate;		// 支付时间
	private String payNo;		// 支付流水号
	private Integer status;		// 订单状态1.待付款2.待确定，3.待出行，4.待评价 5.已完成6.取消订单7.申请退款8退款通过9.退款不通过
	private Date delDate;		// del_date
	private String delBy;		// del_by
	private Integer languageId;		// 语言
	private String refundInfo;		// 退款说明
	private String refundReason;		// 退款原因
	private String title;		// 标题
	private String subtitle;		// 副标题
	private String infor;		// 产品详情
	private String no;		// 产品编号
	private String companyName;		// 供应商名称
	private Integer day;		// 订单天数
	private Date refundDate;		// 退款时间
	private Integer type;		// 1常规路线  2参团 
	private Integer orderType;  //1订单 2售后订单
	private Integer dataFlag;		// 1今日下单2今日出行
	private String searchContent;		// 搜索内容
	private String nickName;		// 用户名
	private String mobile;		// 手机号
	private String startCityName;  //出发城市名称
	private String name;//订单标题
	private Integer orderSys1;//主订单id
	private Integer orderSys2;//对应业务的订单id
	private Date 	useDate;
	private Date beginDate;
	private Date endDate;
	
	private Integer num;
	
	/** 1人房间 */
	private java.math.BigDecimal price1;
	/** 2人房间 */
	private java.math.BigDecimal price2;
	/** 3人房间 */
	private java.math.BigDecimal price3;
	/** 4人房间 */
	private java.math.BigDecimal price4;
	/** 配房单价 */
	private java.math.BigDecimal pricea;

	public java.math.BigDecimal getPricea() {
		return pricea;
	}

	public void setPricea(java.math.BigDecimal pricea) {
		this.pricea = pricea;
	}

	public java.math.BigDecimal getPrice1() {
		return price1;
	}

	public void setPrice1(java.math.BigDecimal price1) {
		this.price1 = price1;
	}

	public java.math.BigDecimal getPrice2() {
		return price2;
	}

	public void setPrice2(java.math.BigDecimal price2) {
		this.price2 = price2;
	}

	public java.math.BigDecimal getPrice3() {
		return price3;
	}

	public void setPrice3(java.math.BigDecimal price3) {
		this.price3 = price3;
	}

	public java.math.BigDecimal getPrice4() {
		return price4;
	}

	public void setPrice4(java.math.BigDecimal price4) {
		this.price4 = price4;
	}

	public Date getUseDate() {
		return useDate;
	}

	public void setUseDate(Date useDate) {
		this.useDate = useDate;
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
	public OrderRoute() {
		super();
		this.setIdType(IDTYPE_AUTO);
	}
	
	public Integer getOrderType() {
		return orderType;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

	public String getPayNo() {
		return payNo;
	}

	public void setPayNo(String payNo) {
		this.payNo = payNo;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	public String getInfor() {
		return infor;
	}

	public void setInfor(String infor) {
		this.infor = infor;
	}

	public String getStartCityName() {
		return startCityName;
	}

	public void setStartCityName(String startCityName) {
		this.startCityName = startCityName;
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

	public String getSearchContent() {
		return searchContent;
	}

	public void setSearchContent(String searchContent) {
		this.searchContent = searchContent;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getDataFlag() {
		return dataFlag;
	}

	public void setDataFlag(Integer dataFlag) {
		this.dataFlag = dataFlag;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getRefundDate() {
		return refundDate;
	}

	public void setRefundDate(Date refundDate) {
		this.refundDate = refundDate;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getAfterNo() {
		return afterNo;
	}

	public void setAfterNo(String afterNo) {
		this.afterNo = afterNo;
	}

	public OrderRoute(String id){
		super(id);
	}

	
	
	@ExcelField(title="常规路线/参团id", align=2, sort=2)
	public Integer getRouteid() {
		return routeid;
	}

	public void setRouteid(Integer routeid) {
		this.routeid = routeid;
	}
	
	@ExcelField(title="会员ID", align=2, sort=3)
	public Integer getMemberid() {
		return memberid;
	}

	public void setMemberid(Integer memberid) {
		this.memberid = memberid;
	}
	
	@ExcelField(title="出发城市", align=2, sort=4)
	public Integer getStartCity() {
		return startCity;
	}

	public void setStartCity(Integer startCity) {
		this.startCity = startCity;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="出发时间", align=2, sort=5)
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	@ExcelField(title="大人数量", align=2, sort=6)
	public Integer getAdultNum() {
		return adultNum;
	}

	public void setAdultNum(Integer adultNum) {
		this.adultNum = adultNum;
	}
	
	@ExcelField(title="孩子数量", align=2, sort=7)
	public Integer getChildNum() {
		return childNum;
	}

	public void setChildNum(Integer childNum) {
		this.childNum = childNum;
	}
	
	@ExcelField(title="单人间数量", align=2, sort=8)
	public Integer getOneNum() {
		return oneNum;
	}

	public void setOneNum(Integer oneNum) {
		this.oneNum = oneNum;
	}
	
	@ExcelField(title="双人间数量", align=2, sort=9)
	public Integer getTwoNum() {
		return twoNum;
	}

	public void setTwoNum(Integer twoNum) {
		this.twoNum = twoNum;
	}
	
	@ExcelField(title="三人间数量", align=2, sort=10)
	public Integer getThreeNum() {
		return threeNum;
	}

	public void setThreeNum(Integer threeNum) {
		this.threeNum = threeNum;
	}
	
	@ExcelField(title="四人间数量", align=2, sort=11)
	public Integer getFourNum() {
		return fourNum;
	}

	public void setFourNum(Integer fourNum) {
		this.fourNum = fourNum;
	}
	
	@ExcelField(title="配房数量", align=2, sort=12)
	public Integer getArrangeNum() {
		return arrangeNum;
	}

	public void setArrangeNum(Integer arrangeNum) {
		this.arrangeNum = arrangeNum;
	}
	
	@ExcelField(title="总价", align=2, sort=13)
	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
	@ExcelField(title="联系人姓名", align=2, sort=14)
	public String getContactsName() {
		return contactsName;
	}

	public void setContactsName(String contactsName) {
		this.contactsName = contactsName;
	}
	
	@ExcelField(title="联系人电话", align=2, sort=15)
	public String getContactsMobile() {
		return contactsMobile;
	}

	public void setContactsMobile(String contactsMobile) {
		this.contactsMobile = contactsMobile;
	}
	
	@ExcelField(title="备注", align=2, sort=16)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@ExcelField(title="1.支付宝2.微信3.银联4.paypal5.线下", align=2, sort=17)
	public Integer getPayWay() {
		return payWay;
	}

	public void setPayWay(Integer payWay) {
		this.payWay = payWay;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="支付时间", align=2, sort=18)
	public Date getPayDate() {
		return payDate;
	}

	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}
	
	@ExcelField(title="订单状态1.待付款2.待确定，3.待出行，4.待评价 5.已完成6.取消订单7.申请退款8退款通过9.退款不通过", align=2, sort=19)
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="del_date", align=2, sort=24)
	public Date getDelDate() {
		return delDate;
	}

	public void setDelDate(Date delDate) {
		this.delDate = delDate;
	}
	
	@ExcelField(title="del_by", align=2, sort=25)
	public String getDelBy() {
		return delBy;
	}

	public void setDelBy(String delBy) {
		this.delBy = delBy;
	}
	
	@ExcelField(title="语言", align=2, sort=27)
	public Integer getLanguageId() {
		return languageId;
	}

	public void setLanguageId(Integer languageId) {
		this.languageId = languageId;
	}
	
	@ExcelField(title="退款说明", align=2, sort=28)
	public String getRefundInfo() {
		return refundInfo;
	}

	public void setRefundInfo(String refundInfo) {
		this.refundInfo = refundInfo;
	}
	
	@ExcelField(title="退款原因", align=2, sort=29)
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

	public String getNo() {
		return no;
	}

	public String getCompanyName() {
		return companyName;
	}

	public Integer getDay() {
		return day;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public void setDay(Integer day) {
		this.day = day;
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

	public Date getEndDate() {
		return endDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	
}