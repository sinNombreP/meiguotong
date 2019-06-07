/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.entity.liner_line;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeeplus.common.utils.excel.annotation.ExcelField;
import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.modules.meiguotong.entity.insurance.Insurance;
import com.jeeplus.modules.meiguotong.entity.linerroom.LinerRoom;
import com.jeeplus.modules.meiguotong.entity.linertrip.LinerTrip;
import com.jeeplus.modules.meiguotong.entity.settingtitlepro.SettingTitlePro;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 邮轮航线管理Entity
 * @author cdq
 * @version 2018-08-13
 */
public class LinerLine extends DataEntity<LinerLine> {
	
	private static final long serialVersionUID = 1L;
	private Integer linerId;		// 邮轮id
	private String lineNo;        //游轮路线编号
	private Date delDate;		// del_date
	private String delBy;		// del_by
	private Integer languageId;		// 语言id
	private String imgUrl;		// 邮轮图片  多张逗号隔开
	private String upPort;		// 上船港口
	private String upPortName;		// 上船港口名称
	private String route;		// route
	private String startCity;		// 出发城市
	private String startCityName;		// 出发城市名称
	private String downPort;		// 下船港口
	private String downPortName;// 下船港口名称
	private String tagId;		// 对应的标签属性id  多选 逗号隔开
	private String travelDay;		// 几天
	private String travelNight;//几晚
	private Integer advanceNum;		// 提前天数
	private BigDecimal price;		// 市场价格
	private String name;		// 路线名称
	private String infor;		// 路线简介
	private String path;		// 途径地
	private String recommend;		// 推荐
	private String content;		// 详细信息
	private String company;//所属公司；
	private Integer status;		// 状态 1审核中2已拒绝3正常4禁用
	private String lineName;//邮轮名称
	private String companyName; //公司名称
	private Integer lookNum;		// 查看数量
	private Integer collectionNum;		// 收藏数量
	private Integer commentNum;		// 评论数量
	private Integer finishNum;		// 成交数量
	private String star;		// 平均星级
	private String cityName; //城市名称
	private String portid; //出发港口
	private  Date startDate; //出航时间
	private String tagContent; //标签属性
	
	private Integer orderByType; //1.销量2.价格降序3.价格升序4好评
	private List<Date> dateList;  //查询选择时间

	private Integer linerCompanyId; //邮轮公司id
	private String linerCompanyName; //邮轮公司名称
	private String memberid;  //查询条件，收藏人id
	private List<String> attrList;//筛选条件，属性集合
	private Integer type;  //搜索接口用 1.游轮2.航线3.目的地
	private Integer typeid;  //搜索接口用 type对应的id
	private String ids;   //模块选择的ID

	private Integer dateType; //时间类型
	private  Date beginDate; //日期范围开始时间
	private  Date endDate; //日期范围结束时间
	private String weekList;   //日期范围选择星期
	private String dayList;   //日期范围选择号数
	private List<LinerRoom> linerRoomList;//房间
	private List<LinerTrip> linerTripList;//行程
	private String scenicContent; //行程景点
	private String routeName;//航线
	
	private Integer cityid;//城市ID
	
	private List<SettingTitlePro> settingTitleProList;
	
	private List<Insurance> insurances;
	
	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	

	public String getTravelDay() {
		return travelDay;
	}

	public void setTravelDay(String travelDay) {
		this.travelDay = travelDay;
	}

	public String getTravelNight() {
		return travelNight;
	}

	public void setTravelNight(String travelNight) {
		this.travelNight = travelNight;
	}


	public List<LinerRoom> getLinerRoomList() {
		return linerRoomList;
	}
	public void setLinerRoomList(List<LinerRoom> linerRoomList) {
		this.linerRoomList = linerRoomList;
	}
	public LinerLine() {
		super();
		this.setIdType(IDTYPE_AUTO);
	}

	public LinerLine(String id){
		super(id);
	}

	@ExcelField(title="邮轮公司id", align=2, sort=1)
	public Integer getLinerId() {
		return linerId;
	}

	public void setLinerId(Integer linerId) {
		this.linerId = linerId;
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
	
	@ExcelField(title="语言id", align=2, sort=9)
	public Integer getLanguageId() {
		return languageId;
	}

	public void setLanguageId(Integer languageId) {
		this.languageId = languageId;
	}
	
	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@ExcelField(title="邮轮图片  多张逗号隔开", align=2, sort=10)
	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	
	@ExcelField(title="上船港口", align=2, sort=11)
	public String getUpPort() {
		return upPort;
	}

	public void setUpPort(String upPort) {
		this.upPort = upPort;
	}
	
	@ExcelField(title="route", align=2, sort=12)
	public String getRoute() {
		return route;
	}

	public void setRoute(String route) {
		this.route = route;
	}
	
	@ExcelField(title="出发城市", align=2, sort=13)
	public String getStartCity() {
		return startCity;
	}

	public void setStartCity(String startCity) {
		this.startCity = startCity;
	}
	
	@ExcelField(title="下船港口", align=2, sort=14)
	public String getDownPort() {
		return downPort;
	}

	public void setDownPort(String downPort) {
		this.downPort = downPort;
	}
	
	@ExcelField(title="对应的标签属性id  多选 逗号隔开", align=2, sort=15)
	public String getTagId() {
		return tagId;
	}

	public void setTagId(String tagId) {
		this.tagId = tagId;
	}

	
	@ExcelField(title="提前天数", align=2, sort=17)
	public Integer getAdvanceNum() {
		return advanceNum;
	}

	public void setAdvanceNum(Integer advanceNum) {
		this.advanceNum = advanceNum;
	}
	
	@ExcelField(title="市场价格", align=2, sort=18)
	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
	@ExcelField(title="路线名称", align=2, sort=19)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ExcelField(title="路线简介", align=2, sort=20)
	public String getInfor() {
		return infor;
	}

	public void setInfor(String infor) {
		this.infor = infor;
	}
	
	@ExcelField(title="途径地", align=2, sort=21)
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	@ExcelField(title="推荐", align=2, sort=22)
	public String getRecommend() {
		return recommend;
	}

	public void setRecommend(String recommend) {
		this.recommend = recommend;
	}
	
	@ExcelField(title="详细信息", align=2, sort=23)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	

	public Integer getLookNum() {
		return lookNum;
	}

	public Integer getCollectionNum() {
		return collectionNum;
	}

	public Integer getCommentNum() {
		return commentNum;
	}

	public Integer getFinishNum() {
		return finishNum;
	}

	public String getStar() {
		return star;
	}

	public void setLookNum(Integer lookNum) {
		this.lookNum = lookNum;
	}

	public void setCollectionNum(Integer collectionNum) {
		this.collectionNum = collectionNum;
	}

	public void setCommentNum(Integer commentNum) {
		this.commentNum = commentNum;
	}

	public void setFinishNum(Integer finishNum) {
		this.finishNum = finishNum;
	}

	public void setStar(String star) {
		this.star = star;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}


	public String getTagContent() {
		return tagContent;
	}


	public void setTagContent(String tagContent) {
		this.tagContent = tagContent;
	}

	public String getLineName() {
		return lineName;
	}

	public void setLineName(String lineName) {
		this.lineName = lineName;
	}

	public List<Date> getDateList() {
		return dateList;
	}

	public void setDateList(List<Date> dateList) {
		this.dateList = dateList;
	}

	public Integer getOrderByType() {
		return orderByType;
	}

	public void setOrderByType(Integer orderByType) {
		this.orderByType = orderByType;
	}

	public String getLineNo() {
		return lineNo;
	}

	public void setLineNo(String lineNo) {
		this.lineNo = lineNo;
	}

	public String getMemberid() {
		return memberid;
	}

	public void setMemberid(String memberid) {
		this.memberid = memberid;
	}

	public List<String> getAttrList() {
		return attrList;
	}

	public void setAttrList(List<String> attrList) {
		this.attrList = attrList;
	}

	public Integer getType() {
		return type;
	}

	public Integer getTypeid() {
		return typeid;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public void setTypeid(Integer typeid) {
		this.typeid = typeid;
	}

	public String getPortid() {
		return portid;
	}

	public void setPortid(String portid) {
		this.portid = portid;
	}

	public String getUpPortName() {
		return upPortName;
	}

	public void setUpPortName(String upPortName) {
		this.upPortName = upPortName;
	}

	public String getStartCityName() {
		return startCityName;
	}

	public String getDownPortName() {
		return downPortName;
	}

	public void setStartCityName(String startCityName) {
		this.startCityName = startCityName;
	}

	public void setDownPortName(String downPortName) {
		this.downPortName = downPortName;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Integer getLinerCompanyId() {
		return linerCompanyId;
	}

	public String getLinerCompanyName() {
		return linerCompanyName;
	}

	public void setLinerCompanyId(Integer linerCompanyId) {
		this.linerCompanyId = linerCompanyId;
	}

	public void setLinerCompanyName(String linerCompanyName) {
		this.linerCompanyName = linerCompanyName;
	}

	public Integer getDateType() {
		return dateType;
	}

	public void setDateType(Integer dateType) {
		this.dateType = dateType;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public String getWeekList() {
		return weekList;
	}

	public String getDayList() {
		return dayList;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public void setWeekList(String weekList) {
		this.weekList = weekList;
	}

	public void setDayList(String dayList) {
		this.dayList = dayList;
	}

	public List<LinerTrip> getLinerTripList() {
		return linerTripList;
	}

	public void setLinerTripList(List<LinerTrip> linerTripList) {
		this.linerTripList = linerTripList;
	}

	public List<SettingTitlePro> getSettingTitleProList() {
		return settingTitleProList;
	}

	public void setSettingTitleProList(List<SettingTitlePro> settingTitleProList) {
		this.settingTitleProList = settingTitleProList;
	}

	public String getScenicContent() {	
		return scenicContent;
	}

	public void setScenicContent(String scenicContent) {
		this.scenicContent = scenicContent;
	}

	public List<Insurance> getInsurances() {
		return insurances;
	}

	public void setInsurances(List<Insurance> insurances) {
		this.insurances = insurances;
	}

	public String getRouteName() {
		return routeName;
	}

	public void setRouteName(String routeName) {	
		this.routeName = routeName;
	}

	public Integer getCityid() {
		return cityid;
	}

	public void setCityid(Integer cityid) {
		this.cityid = cityid;
	}
	
}