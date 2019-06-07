/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.entity.order;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeeplus.common.utils.excel.annotation.ExcelField;
import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.modules.meiguotong.entity.hotel.OrderHotel;
import com.jeeplus.modules.meiguotong.entity.insurance.OrderInsurance;
import com.jeeplus.modules.meiguotong.entity.ordercar.OrderCar;
import com.jeeplus.modules.meiguotong.entity.ordercar.OrderCarDate;
import com.jeeplus.modules.meiguotong.entity.orderguide.OrderGuide;
import com.jeeplus.modules.meiguotong.entity.orderhotelroom.OrderHotelRoom;
import com.jeeplus.modules.meiguotong.entity.orderliner.OrderLiner;
import com.jeeplus.modules.meiguotong.entity.orderlinerroom.OrderLinerRoom;
import com.jeeplus.modules.meiguotong.entity.ordermember.OrderMember;
import com.jeeplus.modules.meiguotong.entity.orderscenicspot.OrderScenicSpot;
import com.jeeplus.modules.meiguotong.entity.travel.OrderTravelBusiness;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.xerces.impl.dv.xs.DecimalDV;

/**
 * 订单关联表Entity
 * @author PSZ
 * @version 2018-08-17
 */
public class OrderSys extends DataEntity<OrderSys> {
	
	private static final long serialVersionUID = 1L;

	private Integer type;		// 订单类型1.包车租车2.短程接送3.接送机4常规路线5.当地参团6.游轮7.景点门票8.当地玩家/1导游9.酒店10.保险11.旅游定制13.商务定制14.商务旅游',
	private Integer fatherid;		// 父id
	private Integer orderid;		// 订单ID
	private Integer memberid;      //下单人
	private Integer languageid;    //语言id
	private BigDecimal price;          //总价
	private String contactsName;	//联系人姓名
	private String contactsMobile;	//联系人电话
	private String remark;			//备注
	private Integer payWay;			//支付方式1.支付宝2.微信3.银联4.paypal5.线下
	private Date payDate;			//支付时间
	private String payNo;			//支付流水号
	private Integer peopleNum;		//总人数
	private Integer bagNum;			//包裹数
	private String title;			//订单标题
	private Integer status;      	//订单状态1.待付款2.待确定，3.待出行，4.待评价 5.已完成6.取消订单7.申请退款8.退款中9退款成功10.退款失败
	private Integer adultNum;		// 大人数量
	private Integer childNum;		// 孩子数量

	private Date beginDate;       //开始时间
	private Date endDate;		  //结束时间

	private Integer oneNum;		// 单人间数量
	private Integer twoNum;		// 双人间数量
	private Integer threeNum;		// 三人间数量
	private Integer fourNum;		// 四人间数量
	private Integer arrangeNum;		// 配房数量
	
	private String ids;   //模块选择的ID

	private String nickName;  //会员名称
	private String photo;  //会员头像
	private String image;  //图片
	private String orderNo;//订单号
	private String payOrderNo;//支付订单号
	private String afterNo;//售后单号
	private String agent;//供应商
	
	private Date finishDate;//完成时间
	private Integer extract;//提现状态1未提现2提现中3已提现
	
	private String hotelName;//酒店名称
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
	
	private Integer carNum;  //包车租车订单数量
	private Integer sortNum;//短程接送订单数量
	private Integer airNum;	//接送机订单数量
	private Integer normalNum;//常规路线订单数量
	private Integer localNum;//当地参团订单数量
	private Integer linerNum;//邮轮订单数量
	private Integer scenicNum;//景点订单数量
	private Integer guideNum;//导游订单数量
	private Integer hotelNum;//酒店订单数量
	private Integer insuranceNum;//保险订单数量
	private Integer businessNum;//商务定制订单数量
	private Integer travelNum;//商务旅游订单数量
	private Integer cusNum;//定制租车数量
	
	private BigDecimal carPrice;  //包车租车订单数量
	private BigDecimal sortPrice;//短程接送订单数量
	private BigDecimal airPrice;	//接送机订单数量
	private BigDecimal normalPrice;//常规路线订单数量
	private BigDecimal localPrice;//当地参团订单数量
	private BigDecimal linerPrice;//邮轮订单数量
	private BigDecimal scenicPrice;//景点订单数量
	private BigDecimal guidePrice;//导游订单数量
	private BigDecimal hotelPrice;//酒店订单数量
	private BigDecimal insurancePrice;//保险订单数量
	private BigDecimal businessPrice;//商务定制订单数量
	private BigDecimal travelPrice;//商务旅游订单数量
	private BigDecimal cusPrice;//定制租车
	
	private String startCity; //出发城市',
	private String startAddress; //出发详细地址',
	private String  endCity; //到达城市',
	private String endAddress; //到达详细地址',
	private String  portName; //出发港口名称',
	private String downPortName; //下船港口名称
	
	List<OrderCar> carlist;
	List<OrderRoute> routelist;
	List<OrderGuide> guilist;
	List<OrderInsurance> insurancelist;
	List<OrderHotel> hotellist;
	List<OrderLiner> linerlist;
	List<OrderScenicSpot> scenicSpotlist;
	List<OrderTravelBusiness> travelBusinesseslist;
	List<OrderCarDate> orderCarDates;
	List<OrderLinerRoom> orderLinerRooms;
	List<OrderHotelRoom> orderHotelRooms;
	List<OrderMember> orderMembers;

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

	public String getAgent() {
		return agent;
	}

	public void setAgent(String agent) {
		this.agent = agent;
	}


	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}


	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public OrderSys() {
		super();
		this.setIdType(IDTYPE_AUTO);
	}

	public OrderSys(String id){
		super(id);
	}

	@ExcelField(title="订单类型1.旅游定制2.商务定制3.旅游商务定制4.汽车订单5.常规路线6.当地参团7.游轮8.景点9.导游10.保险", align=2, sort=1)
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
	@ExcelField(title="父id", align=2, sort=2)
	public Integer getFatherid() {
		return fatherid;
	}

	public void setFatherid(Integer fatherid) {
		this.fatherid = fatherid;
	}
	
	@ExcelField(title="订单ID", align=2, sort=3)
	public Integer getOrderid() {
		return orderid;
	}

	public void setOrderid(Integer orderid) {
		this.orderid = orderid;
	}

	public Integer getMemberid() {
		return memberid;
	}

	public void setMemberid(Integer memberid) {
		this.memberid = memberid;
	}

	public Integer getLanguageid() {
		return languageid;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public String getContactsName() {
		return contactsName;
	}

	public String getContactsMobile() {
		return contactsMobile;
	}

	public String getRemark() {
		return remark;
	}

	public Integer getPayWay() {
		return payWay;
	}

	public Date getPayDate() {
		return payDate;
	}

	public Integer getPeopleNum() {
		return peopleNum;
	}

	public Integer getBagNum() {
		return bagNum;
	}

	public String getTitle() {
		return title;
	}

	public void setLanguageid(Integer languageid) {
		this.languageid = languageid;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public void setContactsName(String contactsName) {
		this.contactsName = contactsName;
	}

	public void setContactsMobile(String contactsMobile) {
		this.contactsMobile = contactsMobile;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setPayWay(Integer payWay) {
		this.payWay = payWay;
	}

	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}

	public void setPeopleNum(Integer peopleNum) {
		this.peopleNum = peopleNum;
	}

	public void setBagNum(Integer bagNum) {
		this.bagNum = bagNum;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getAdultNum() {
		return adultNum;
	}

	public Integer getChildNum() {
		return childNum;
	}
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getBeginDate() {
		return beginDate;
	}
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getEndDate() {
		return endDate;
	}

	public Integer getOneNum() {
		return oneNum;
	}

	public Integer getTwoNum() {
		return twoNum;
	}

	public Integer getThreeNum() {
		return threeNum;
	}

	public Integer getFourNum() {
		return fourNum;
	}

	public Integer getArrangeNum() {
		return arrangeNum;
	}

	public void setAdultNum(Integer adultNum) {
		this.adultNum = adultNum;
	}

	public void setChildNum(Integer childNum) {
		this.childNum = childNum;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public void setOneNum(Integer oneNum) {
		this.oneNum = oneNum;
	}

	public void setTwoNum(Integer twoNum) {
		this.twoNum = twoNum;
	}

	public void setThreeNum(Integer threeNum) {
		this.threeNum = threeNum;
	}

	public void setFourNum(Integer fourNum) {
		this.fourNum = fourNum;
	}

	public void setArrangeNum(Integer arrangeNum) {
		this.arrangeNum = arrangeNum;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getHotelName() {
		return hotelName;
	}

	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}

	public Integer getCarNum() {
		return carNum;
	}

	public void setCarNum(Integer carNum) {
		this.carNum = carNum;
	}

	public Integer getSortNum() {
		return sortNum;
	}

	public void setSortNum(Integer sortNum) {
		this.sortNum = sortNum;
	}

	public Integer getAirNum() {
		return airNum;
	}

	public void setAirNum(Integer airNum) {
		this.airNum = airNum;
	}

	public Integer getNormalNum() {
		return normalNum;
	}

	public void setNormalNum(Integer normalNum) {
		this.normalNum = normalNum;
	}

	public Integer getLocalNum() {
		return localNum;
	}

	public void setLocalNum(Integer localNum) {
		this.localNum = localNum;
	}

	public Integer getLinerNum() {
		return linerNum;
	}

	public void setLinerNum(Integer linerNum) {
		this.linerNum = linerNum;
	}

	public Integer getScenicNum() {
		return scenicNum;
	}

	public void setScenicNum(Integer scenicNum) {
		this.scenicNum = scenicNum;
	}

	public Integer getGuideNum() {
		return guideNum;
	}

	public void setGuideNum(Integer guideNum) {
		this.guideNum = guideNum;
	}

	public Integer getHotelNum() {
		return hotelNum;
	}

	public void setHotelNum(Integer hotelNum) {
		this.hotelNum = hotelNum;
	}

	public Integer getInsuranceNum() {
		return insuranceNum;
	}

	public void setInsuranceNum(Integer insuranceNum) {
		this.insuranceNum = insuranceNum;
	}

	public Integer getBusinessNum() {
		return businessNum;
	}

	public void setBusinessNum(Integer businessNum) {
		this.businessNum = businessNum;
	}

	public Integer getTravelNum() {
		return travelNum;
	}

	public void setTravelNum(Integer travelNum) {
		this.travelNum = travelNum;
	}

	public Integer getCusNum() {
		return cusNum;
	}

	public void setCusNum(Integer cusNum) {
		this.cusNum = cusNum;
	}

	public BigDecimal getCarPrice() {
		return carPrice;
	}

	public void setCarPrice(BigDecimal carPrice) {
		this.carPrice = carPrice;
	}

	public BigDecimal getSortPrice() {
		return sortPrice;
	}

	public void setSortPrice(BigDecimal sortPrice) {
		this.sortPrice = sortPrice;
	}

	public BigDecimal getAirPrice() {
		return airPrice;
	}

	public void setAirPrice(BigDecimal airPrice) {
		this.airPrice = airPrice;
	}

	public BigDecimal getNormalPrice() {
		return normalPrice;
	}

	public void setNormalPrice(BigDecimal normalPrice) {
		this.normalPrice = normalPrice;
	}

	public BigDecimal getLocalPrice() {
		return localPrice;
	}

	public void setLocalPrice(BigDecimal localPrice) {
		this.localPrice = localPrice;
	}

	public BigDecimal getLinerPrice() {
		return linerPrice;
	}

	public void setLinerPrice(BigDecimal linerPrice) {
		this.linerPrice = linerPrice;
	}

	public BigDecimal getScenicPrice() {
		return scenicPrice;
	}

	public void setScenicPrice(BigDecimal scenicPrice) {
		this.scenicPrice = scenicPrice;
	}

	public BigDecimal getGuidePrice() {
		return guidePrice;
	}

	public void setGuidePrice(BigDecimal guidePrice) {
		this.guidePrice = guidePrice;
	}

	public BigDecimal getHotelPrice() {
		return hotelPrice;
	}

	public void setHotelPrice(BigDecimal hotelPrice) {
		this.hotelPrice = hotelPrice;
	}

	public BigDecimal getInsurancePrice() {
		return insurancePrice;
	}

	public void setInsurancePrice(BigDecimal insurancePrice) {
		this.insurancePrice = insurancePrice;
	}

	public BigDecimal getBusinessPrice() {
		return businessPrice;
	}

	public void setBusinessPrice(BigDecimal businessPrice) {
		this.businessPrice = businessPrice;
	}

	public BigDecimal getTravelPrice() {
		return travelPrice;
	}

	public void setTravelPrice(BigDecimal travelPrice) {
		this.travelPrice = travelPrice;
	}

	public BigDecimal getCusPrice() {
		return cusPrice;
	}

	public void setCusPrice(BigDecimal cusPrice) {
		this.cusPrice = cusPrice;
	}

	public List<OrderCar> getCarlist() {
		return carlist;
	}

	public void setCarlist(List<OrderCar> carlist) {
		this.carlist = carlist;
	}

	public List<OrderRoute> getRoutelist() {
		return routelist;
	}

	public void setRoutelist(List<OrderRoute> routelist) {
		this.routelist = routelist;
	}

	public List<OrderGuide> getGuilist() {
		return guilist;
	}

	public void setGuilist(List<OrderGuide> guilist) {
		this.guilist = guilist;
	}

	public List<OrderInsurance> getInsurancelist() {
		return insurancelist;
	}

	public void setInsurancelist(List<OrderInsurance> insurancelist) {
		this.insurancelist = insurancelist;
	}

	public List<OrderHotel> getHotellist() {
		return hotellist;
	}

	public void setHotellist(List<OrderHotel> hotellist) {
		this.hotellist = hotellist;
	}

	public List<OrderLiner> getLinerlist() {
		return linerlist;
	}

	public void setLinerlist(List<OrderLiner> linerlist) {
		this.linerlist = linerlist;
	}

	public List<OrderScenicSpot> getScenicSpotlist() {
		return scenicSpotlist;
	}

	public void setScenicSpotlist(List<OrderScenicSpot> scenicSpotlist) {
		this.scenicSpotlist = scenicSpotlist;
	}

	public List<OrderTravelBusiness> getTravelBusinesseslist() {
		return travelBusinesseslist;
	}

	public void setTravelBusinesseslist(List<OrderTravelBusiness> travelBusinesseslist) {
		this.travelBusinesseslist = travelBusinesseslist;
	}

	public String getStartCity() {
		return startCity;
	}

	public void setStartCity(String startCity) {
		this.startCity = startCity;
	}

	public String getStartAddress() {
		return startAddress;
	}

	public void setStartAddress(String startAddress) {
		this.startAddress = startAddress;
	}

	public String getEndCity() {
		return endCity;
	}

	public void setEndCity(String endCity) {
		this.endCity = endCity;
	}

	public String getEndAddress() {
		return endAddress;
	}

	public void setEndAddress(String endAddress) {
		this.endAddress = endAddress;
	}

	public String getPortName() {
		return portName;
	}

	public void setPortName(String portName) {
		this.portName = portName;
	}

	public String getDownPortName() {
		return downPortName;
	}

	public void setDownPortName(String downPortName) {
		this.downPortName = downPortName;
	}

	public List<OrderCarDate> getOrderCarDates() {
		return orderCarDates;
	}

	public void setOrderCarDates(List<OrderCarDate> orderCarDates) {
		this.orderCarDates = orderCarDates;
	}

	public List<OrderLinerRoom> getOrderLinerRooms() {
		return orderLinerRooms;
	}

	public void setOrderLinerRooms(List<OrderLinerRoom> orderLinerRooms) {
		this.orderLinerRooms = orderLinerRooms;
	}

	public List<OrderHotelRoom> getOrderHotelRooms() {
		return orderHotelRooms;
	}

	public void setOrderHotelRooms(List<OrderHotelRoom> orderHotelRooms) {
		this.orderHotelRooms = orderHotelRooms;
	}

	public List<OrderMember> getOrderMembers() {
		return orderMembers;
	}

	public void setOrderMembers(List<OrderMember> orderMembers) {
		this.orderMembers = orderMembers;
	}

	public Date getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}

	public Integer getExtract() {
		return extract;
	}

	public void setExtract(Integer extract) {
		this.extract = extract;
	}

	public String getPayNo() {
		return payNo;
	}

	public void setPayNo(String payNo) {
		this.payNo = payNo;
	}

	public String getPayOrderNo() {
		return payOrderNo;
	}

	public void setPayOrderNo(String payOrderNo) {
		this.payOrderNo = payOrderNo;
	}
	
}