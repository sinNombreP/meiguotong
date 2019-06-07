/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.entity.product;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeeplus.common.utils.excel.annotation.ExcelField;
import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.modules.meiguotong.entity.comcity.ComCity;
import com.jeeplus.modules.meiguotong.entity.comtag.ComTag;
import com.jeeplus.modules.meiguotong.entity.insurance.Insurance;
import com.jeeplus.modules.meiguotong.entity.settingtitlepro.SettingTitlePro;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 当地参团Entity
 * @author psz
 * @version 2018-08-13
 */
public class Route extends DataEntity<Route> {
	
	private static final long serialVersionUID = 1L;
	private String no;		// 编号
	private Integer status;		// 1审核中2已拒绝3正常4禁用
	private Integer language;		// 语言
	private Integer type;		// 类型1.常规路线2.当地参团
	private String title;		// 标题
	private String subtitle;		// 副标题
	private String infor;		// 路线详情
	private String priceInfor;		// 价格描述
	private String recommend;		// 产品经理推荐
	
	private String content;		// 资料
	private String carImg;		// 汽车图片，多个用，隔开
	private Integer startCity;		// 出发城市
	private Integer endCity;		// 到达城市
	
	private Integer dayNum;		// 几个白天
	private Integer nightNum;		// 几晚
	private Integer advanceDay;		// 提前天数
	private BigDecimal price;		// 价格
	private Date delDate;		// del_date
	private String delBy;		// 删除人

	private Date routeDate;		// 
	private String day;       //行程天数
	private String minPrice;       //最小价格
	private String maxPrice;       //最大价格
	private String scenic;		// 途径景点，多个id用，隔开
	private String labelAttrid;		// 标签属性id
	private List<String> scenicList;// 途径景点
	private List<String> attrList;// 标签属性
	
	private String companyName;		// 公司名称
	private String langContent;  //语言名称
	private String tagContent;  //标签名称
	private String scenicContent;  //景点名称
	private String startCityContent;		// 出发城市
	private String endCityContent;		// 到达城市
	
	private Integer lookNum;		// 查看数量
	private Integer collectionNum;		// 收藏数量
	private Integer commentNum;		// 评论数量
	private Integer finishNum;		// 成交数量
	private String star;		// 平均星级
	private String remark;		// 备注
	private String routeids;	//参团ID集合

	//RouteDate
	private Integer routeid;		// 路线/参团id
	private Date priceDate;		// 设置价格日期
	private BigDecimal oneProfit;		// 单房利润
	private BigDecimal twoProfit;		// 双人房利润
	private BigDecimal threeProfit;		// 三人房利润
	private BigDecimal fourProfit;		// 四人房利润
	private BigDecimal arrangeProfit;		// 配房利润
	private BigDecimal houseOne;		// 单房差
	private String priceInfo;		// 价格描述
	private Integer stock;		// 库存（-1为无限）
	private BigDecimal oneCost;		// 单房成本
	private BigDecimal twoCost;		// 双人房成本
	private BigDecimal threeCost;		// 三人房成本
	private BigDecimal fourCost;		// 四人房成本
	private BigDecimal arrangeCost;		// 配房成本
	
	//RouteTime
	private Integer dateType;		// 日期类型1.所有日期2.按星期3.按号数
	private Date beginDate;		// 选择所有日期的开始时间
	private Date endDate;		// 选择所有日期的结束时间
	private String weekDate;		// 选择的星期
	private String dayDate;		// 选择的号数
	
	private ComTag comTag;  //选择的标签  id  content
	private ComCity comCity;  //选择的目的地  id  cityName
	
	private String[] weekList;  //星期
	private String[] dayList; //天数
	private String[] imgSrc;//图片
	private List<RouteContent> routeContentList;  //行程内容
	private List<Date> dateLIst;  //查询选择时间

	private Integer memberid;		//用戶id
	private Integer ifcollection;   //是否收藏
	private String ids;   //模块选择的ID
	private Integer cityId; //城市ID
	private List<String> imgLIst;  //查询选择时间
	
	private Integer num; //已售数量
	
	private String cityName;//城市名称
	private Integer cityid;//城市ID
	
	private List<Insurance> insurances;
	
	private List<SettingTitlePro> settingTitleProList;
	
	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}
	public Route() {
		super();
		this.setIdType(IDTYPE_AUTO);
	}

	public List<RouteContent> getRouteContentList() {
		return routeContentList;
	}

	public void setRouteContentList(List<RouteContent> routeContentList) {
		this.routeContentList = routeContentList;
	}

	public ComCity getComCity() {
		return comCity;
	}

	public void setComCity(ComCity comCity) {
		this.comCity = comCity;
	}

	public ComTag getComTag() {
		return comTag;
	}

	public void setComTag(ComTag comTag) {
		this.comTag = comTag;
	}

	public String getPriceInfo() {
		return priceInfo;
	}

	public void setPriceInfo(String priceInfo) {
		this.priceInfo = priceInfo;
	}

	public Integer getRouteid() {
		return routeid;
	}

	public void setRouteid(Integer routeid) {
		this.routeid = routeid;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getPriceDate() {
		return priceDate;
	}

	public void setPriceDate(Date priceDate) {
		this.priceDate = priceDate;
	}

	public BigDecimal getOneProfit() {
		return oneProfit;
	}

	public void setOneProfit(BigDecimal oneProfit) {
		this.oneProfit = oneProfit;
	}

	public BigDecimal getTwoProfit() {
		return twoProfit;
	}

	public void setTwoProfit(BigDecimal twoProfit) {
		this.twoProfit = twoProfit;
	}

	public BigDecimal getThreeProfit() {
		return threeProfit;
	}

	public void setThreeProfit(BigDecimal threeProfit) {
		this.threeProfit = threeProfit;
	}

	public BigDecimal getFourProfit() {
		return fourProfit;
	}

	public void setFourProfit(BigDecimal fourProfit) {
		this.fourProfit = fourProfit;
	}

	public BigDecimal getArrangeProfit() {
		return arrangeProfit;
	}

	public void setArrangeProfit(BigDecimal arrangeProfit) {
		this.arrangeProfit = arrangeProfit;
	}

	public BigDecimal getHouseOne() {
		return houseOne;
	}

	public void setHouseOne(BigDecimal houseOne) {
		this.houseOne = houseOne;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public BigDecimal getOneCost() {
		return oneCost;
	}

	public void setOneCost(BigDecimal oneCost) {
		this.oneCost = oneCost;
	}

	public BigDecimal getTwoCost() {
		return twoCost;
	}

	public void setTwoCost(BigDecimal twoCost) {
		this.twoCost = twoCost;
	}

	public BigDecimal getThreeCost() {
		return threeCost;
	}

	public void setThreeCost(BigDecimal threeCost) {
		this.threeCost = threeCost;
	}

	public BigDecimal getFourCost() {
		return fourCost;
	}

	public void setFourCost(BigDecimal fourCost) {
		this.fourCost = fourCost;
	}

	public BigDecimal getArrangeCost() {
		return arrangeCost;
	}

	public void setArrangeCost(BigDecimal arrangeCost) {
		this.arrangeCost = arrangeCost;
	}

	public Integer getDateType() {
		return dateType;
	}

	public void setDateType(Integer dateType) {
		this.dateType = dateType;
	}

	@JsonFormat(pattern = "yyyy-MM-dd")
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

	public String getWeekDate() {
		return weekDate;
	}

	public void setWeekDate(String weekDate) {
		this.weekDate = weekDate;
	}

	public String getDayDate() {
		return dayDate;
	}

	public void setDayDate(String dayDate) {
		this.dayDate = dayDate;
	}

	public String[] getWeekList() {
		return weekList;
	}

	public void setWeekList(String[] weekList) {
		this.weekList = weekList;
	}

	public String[] getDayList() {
		return dayList;
	}

	public void setDayList(String[] dayList) {
		this.dayList = dayList;
	}

	public String[] getImgSrc() {
		return imgSrc;
	}

	public void setImgSrc(String[] imgSrc) {
		this.imgSrc = imgSrc;
	}

	public String getStartCityContent() {
		return startCityContent;
	}

	public void setStartCityContent(String startCityContent) {
		this.startCityContent = startCityContent;
	}

	public String getEndCityContent() {
		return endCityContent;
	}

	public void setEndCityContent(String endCityContent) {
		this.endCityContent = endCityContent;
	}

	public String getScenicContent() {
		return scenicContent;
	}

	public void setScenicContent(String scenicContent) {
		this.scenicContent = scenicContent;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getLangContent() {
		return langContent;
	}

	public void setLangContent(String langContent) {
		this.langContent = langContent;
	}

	public String getTagContent() {
		return tagContent;
	}

	public void setTagContent(String tagContent) {
		this.tagContent = tagContent;
	}

	public Route(String id){
		super(id);
	}

	
	@ExcelField(title="编号", dictType="", align=2, sort=2)
	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}
	
	@ExcelField(title="1审核中2已拒绝3正常4禁用", dictType="", align=2, sort=3)
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@ExcelField(title="语言", dictType="", align=2, sort=4)
	public Integer getLanguage() {
		return language;
	}

	public void setLanguage(Integer language) {
		this.language = language;
	}
	
	@ExcelField(title="类型1.常规路线2.当地参团", align=2, sort=5)
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
	@ExcelField(title="标题", align=2, sort=6)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@ExcelField(title="副标题", align=2, sort=7)
	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}
	
	@ExcelField(title="路线详情", align=2, sort=8)
	public String getInfor() {
		return infor;
	}

	public void setInfor(String infor) {
		this.infor = infor;
	}
	
	@ExcelField(title="价格描述", align=2, sort=9)
	public String getPriceInfor() {
		return priceInfor;
	}

	public void setPriceInfor(String priceInfor) {
		this.priceInfor = priceInfor;
	}
	
	@ExcelField(title="产品经理推荐", align=2, sort=10)
	public String getRecommend() {
		return recommend;
	}

	public void setRecommend(String recommend) {
		this.recommend = recommend;
	}
	
	@ExcelField(title="途径景点，多个id用，隔开", align=2, sort=11)
	public String getScenic() {
		return scenic;
	}

	public void setScenic(String scenic) {
		this.scenic = scenic;
	}
	
	@ExcelField(title="资料", align=2, sort=12)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@ExcelField(title="汽车图片，多个用，隔开", align=2, sort=13)
	public String getCarImg() {
		return carImg;
	}

	public void setCarImg(String carImg) {
		this.carImg = carImg;
	}
	
	@ExcelField(title="出发城市", align=2, sort=14)
	public Integer getStartCity() {
		return startCity;
	}

	public void setStartCity(Integer startCity) {
		this.startCity = startCity;
	}
	
	@ExcelField(title="到达城市", dictType="", align=2, sort=15)
	public Integer getEndCity() {
		return endCity;
	}

	public void setEndCity(Integer endCity) {
		this.endCity = endCity;
	}
	
	@ExcelField(title="标签属性id", dictType="", align=2, sort=16)
	public String getLabelAttrid() {
		return labelAttrid;
	}

	public void setLabelAttrid(String labelAttrid) {
		this.labelAttrid = labelAttrid;
	}
	
	@ExcelField(title="几个白天", align=2, sort=17)
	public Integer getDayNum() {
		return dayNum;
	}

	public void setDayNum(Integer dayNum) {
		this.dayNum = dayNum;
	}
	
	@ExcelField(title="几晚", align=2, sort=18)
	public Integer getNightNum() {
		return nightNum;
	}

	public void setNightNum(Integer nightNum) {
		this.nightNum = nightNum;
	}
	
	@ExcelField(title="提前天数", align=2, sort=19)
	public Integer getAdvanceDay() {
		return advanceDay;
	}

	public void setAdvanceDay(Integer advanceDay) {
		this.advanceDay = advanceDay;
	}
	
	@ExcelField(title="价格", align=2, sort=20)
	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="del_date", align=2, sort=26)
	public Date getDelDate() {
		return delDate;
	}

	public void setDelDate(Date delDate) {
		this.delDate = delDate;
	}
	
	@ExcelField(title="删除人", align=2, sort=27)
	public String getDelBy() {
		return delBy;
	}

	public void setDelBy(String delBy) {
		this.delBy = delBy;
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

	public String getDay() {
		return day;
	}

	public String getMinPrice() {
		return minPrice;
	}

	public String getMaxPrice() {
		return maxPrice;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public void setMinPrice(String minPrice) {
		this.minPrice = minPrice;
	}

	public void setMaxPrice(String maxPrice) {
		this.maxPrice = maxPrice;
	}

	public List<Date> getDateLIst() {
		return dateLIst;
	}

	public void setDateLIst(List<Date> dateLIst) {
		this.dateLIst = dateLIst;
	}

	public Integer getMemberid() {
		return memberid;
	}

	public void setMemberid(Integer memberid) {
		this.memberid = memberid;
	}

	public Integer getIfcollection() {
		return ifcollection;
	}

	public void setIfcollection(Integer ifcollection) {
		this.ifcollection = ifcollection;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public List<String> getScenicList() {
		return scenicList;
	}

	public List<String> getAttrList() {
		return attrList;
	}

	public void setScenicList(List<String> scenicList) {
		this.scenicList = scenicList;
	}

	public void setAttrList(List<String> attrList) {
		this.attrList = attrList;
	}
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getRouteDate() {
		return routeDate;
	}

	public void setRouteDate(Date routeDate) {
		this.routeDate = routeDate;
	}

	public String getRouteids() {
		return routeids;
	}

	public void setRouteids(String routeids) {
		this.routeids = routeids;
	}

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	public List<String> getImgLIst() {
		return imgLIst;
	}

	public void setImgLIst(List<String> imgLIst) {
		this.imgLIst = imgLIst;
	}

	public List<SettingTitlePro> getSettingTitleProList() {
		return settingTitleProList;
	}

	public void setSettingTitleProList(List<SettingTitlePro> settingTitleProList) {
		this.settingTitleProList = settingTitleProList;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public List<Insurance> getInsurances() {
		return insurances;
	}

	public void setInsurances(List<Insurance> insurances) {
		this.insurances = insurances;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public Integer getCityid() {
		return cityid;
	}

	public void setCityid(Integer cityid) {
		this.cityid = cityid;
	}
	
}