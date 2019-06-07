/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.entity.productcar;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.modules.meiguotong.entity.guide.Guide;
import com.jeeplus.modules.meiguotong.entity.guideroute.GuideRoute;
import com.jeeplus.modules.meiguotong.entity.insurance.Insurance;
import com.jeeplus.modules.meiguotong.entity.liner_line.LinerLine;
import com.jeeplus.modules.meiguotong.entity.linerroom.LinerRoom;
import com.jeeplus.modules.meiguotong.entity.product.Route;
import com.jeeplus.modules.meiguotong.entity.scenicspotticket.ScenicSpotTicket;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 购物车Entity
 * @author dong
 * @version 2018-09-17
 */
public class ProductCar extends DataEntity<ProductCar> {
	
	private static final long serialVersionUID = 1L;
	private Integer memberid;		// 用户id
	private Integer type;		// 1.常规路线2.当地参团3.当地玩家4.游轮5.景点门票6导游路线
	private Integer typeid;		// 产品id
	private Integer adultNum;		// 大人数量
	private Integer childNum;		// 孩子数量
	private Integer oneNum;		// 单人间数量
	private Integer twoNum;		// 双人间数量
	private Integer threeNum;		// 三人间数量
	private Integer fourNum;		// 四人间数量
	private Integer arrangeNum;		// 配房数量
	private Date beginDate;		// 出发时间
	private Date endDate;		// 结束时间
	private Integer languageid;		// 语言id
	private BigDecimal price;		// 价格
	private String roomids;		// 游轮房间
	
	private Integer status; //订单状态
	private Integer num;//数量
	private String title;//标题
	private String img;//图片
	
	private Route route;
	private	Guide guide;
	private LinerLine linerLine;
	private LinerRoom linerRoom;
	private ScenicSpotTicket scenicSpotTicket;
	private GuideRoute guideRoute;
	
	private List<Insurance> insuranceList;

	private Integer cityid;  //城市ID
	private String cityName;  //城市名称


	public ProductCar() {
		super();
		this.setIdType(IDTYPE_AUTO);
	}

	public Integer getCityid() {
		return cityid;
	}

	public void setCityid(Integer cityid) {
		this.cityid = cityid;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public ProductCar(String id){
		super(id);
	}

	@ExcelField(title="用户id", align=2, sort=1)
	public Integer getMemberid() {
		return memberid;
	}

	public void setMemberid(Integer memberid) {
		this.memberid = memberid;
	}
	
	@ExcelField(title="1.常规路线2.当地参团3.当地玩家4.游轮5.景点门票", align=2, sort=2)
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
	@ExcelField(title="产品id", align=2, sort=3)
	public Integer getTypeid() {
		return typeid;
	}

	public void setTypeid(Integer typeid) {
		this.typeid = typeid;
	}
	
	@ExcelField(title="大人数量", align=2, sort=4)
	public Integer getAdultNum() {
		return adultNum;
	}

	public void setAdultNum(Integer adultNum) {
		this.adultNum = adultNum;
	}
	
	@ExcelField(title="孩子数量", align=2, sort=5)
	public Integer getChildNum() {
		return childNum;
	}

	public void setChildNum(Integer childNum) {
		this.childNum = childNum;
	}
	
	@ExcelField(title="单人间数量", align=2, sort=6)
	public Integer getOneNum() {
		return oneNum;
	}

	public void setOneNum(Integer oneNum) {
		this.oneNum = oneNum;
	}
	
	@ExcelField(title="双人间数量", align=2, sort=7)
	public Integer getTwoNum() {
		return twoNum;
	}

	public void setTwoNum(Integer twoNum) {
		this.twoNum = twoNum;
	}
	
	@ExcelField(title="三人间数量", align=2, sort=8)
	public Integer getThreeNum() {
		return threeNum;
	}

	public void setThreeNum(Integer threeNum) {
		this.threeNum = threeNum;
	}
	
	@ExcelField(title="四人间数量", align=2, sort=9)
	public Integer getFourNum() {
		return fourNum;
	}

	public void setFourNum(Integer fourNum) {
		this.fourNum = fourNum;
	}
	
	@ExcelField(title="配房数量", align=2, sort=10)
	public Integer getArrangeNum() {
		return arrangeNum;
	}

	public void setArrangeNum(Integer arrangeNum) {
		this.arrangeNum = arrangeNum;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@ExcelField(title="出发时间", align=2, sort=11)
	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@ExcelField(title="语言id", align=2, sort=12)
	public Integer getLanguageid() {
		return languageid;
	}

	public void setLanguageid(Integer languageid) {
		this.languageid = languageid;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getRoomids() {
		return roomids;
	}

	public void setRoomids(String roomids) {
		this.roomids = roomids;
	}


	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public Route getRoute() {
		return route;
	}

	public void setRoute(Route route) {
		this.route = route;
	}

	public Guide getGuide() {
		return guide;
	}

	public void setGuide(Guide guide) {
		this.guide = guide;
	}

	public LinerLine getLinerLine() {
		return linerLine;
	}

	public void setLinerLine(LinerLine linerLine) {
		this.linerLine = linerLine;
	}

	public LinerRoom getLinerRoom() {
		return linerRoom;
	}

	public void setLinerRoom(LinerRoom linerRoom) {
		this.linerRoom = linerRoom;
	}

	public ScenicSpotTicket getScenicSpotTicket() {
		return scenicSpotTicket;
	}

	public void setScenicSpotTicket(ScenicSpotTicket scenicSpotTicket) {
		this.scenicSpotTicket = scenicSpotTicket;
	}

	public GuideRoute getGuideRoute() {
		return guideRoute;
	}

	public void setGuideRoute(GuideRoute guideRoute) {
		this.guideRoute = guideRoute;
	}

	public List<Insurance> getInsuranceList() {
		return insuranceList;
	}

	public void setInsuranceList(List<Insurance> insuranceList) {
		this.insuranceList = insuranceList;
	}
	
}