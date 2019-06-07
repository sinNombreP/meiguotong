/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.entity.module;


import java.util.List;

import com.jeeplus.common.utils.excel.annotation.ExcelField;
import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.modules.meiguotong.entity.car.CarInfo;
import com.jeeplus.modules.meiguotong.entity.citystrategy.CityStrategy;
import com.jeeplus.modules.meiguotong.entity.comadd.ComAd;
import com.jeeplus.modules.meiguotong.entity.comarticle.ComArticle;
import com.jeeplus.modules.meiguotong.entity.comcity.ComCity;
import com.jeeplus.modules.meiguotong.entity.comcomment.ComComment;
import com.jeeplus.modules.meiguotong.entity.guide.Guide;
import com.jeeplus.modules.meiguotong.entity.guideroute.GuideRoute;
import com.jeeplus.modules.meiguotong.entity.liner_line.LinerLine;
import com.jeeplus.modules.meiguotong.entity.order.OrderSys;
import com.jeeplus.modules.meiguotong.entity.product.Route;
import com.jeeplus.modules.meiguotong.entity.scenicspot.ScenicSpot;
import com.jeeplus.modules.meiguotong.entity.scenicspotticket.ScenicSpotTicket;
import com.jeeplus.modules.meiguotong.entity.travel.TravelCustomization;
import com.jeeplus.modules.sys.entity.sysUser.SysUser;

/**
 * 模块内容Entity
 * @author psz
 * @version 2018-12-03
 */
public class ModuleContent extends DataEntity<ModuleContent> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 页面名称
	private String typeid;		// 对于的类型ID（多个用逗号区分）
	private Integer orderType;		// 排序类型
	private Integer oneFooterShow;		// 页脚是否显示
	private String oneFooter;		// 页脚图片（多个用逗号区分）
	private Integer twoFooterShow;		// two_footer_show
	private String twoFooter;		// two_footer
	private Integer threeFooterShow;		// three_footer_show
	private String threeFooter;		// three_footer
	private String img;		// 图片（多个用逗号隔开）
	private String url;		// 链接
	private Integer moduleHtmlId;		// 页面ID
	private Integer moduleNameId;		// 模块ID
	private Integer languageId;		// 语言ID
	private Integer sort;		// 排序（升序）
	private Integer index;		
	
	private String moduleName;		// 模块名称
	private Integer moduleType;		// 1.左侧广告2.右侧广告3.内容4.页脚 5.中间广告
	private String moduleSize;		// 1.1*1，2.1+23.1+1+3，4.1*3，5.1*4，6.1*5，7.1*6，8.2+3，9.2*3，10.2*4，11.3*1，12.3+4，12.4*2，
	private Integer type;		// 对于的类型：1广告表
	
	private List<ModuleDetails> moduleDetails;  //模块详情
	private List<CarInfo> carInfoList;  //评论列表
	private List<ComComment> comCommentList;  //评论列表
	private List<ComCity> comCityList;  //城市列表
	private List<ScenicSpot> scenicSpotList;  //景点列表
	private List<LinerLine> linerLineList;  //游轮路线列表
	private List<OrderSys> orderSysList;  //订单列表
	private List<TravelCustomization> travelCustomizationList;  //定制列表
	private List<CityStrategy> CityStrategyList;  //攻略列表
	private List<Guide> guideList;  //导游列表
	private List<GuideRoute> guideRouteList;  //导游路线列表
	private List<Route> routeList;  //常规路线/当地参团列表
	private List<ComArticle> comArticleList;  //网站文章/资讯列表
	private List<SysUser> SysUserList;  //供应商列表
	private List<ScenicSpotTicket> scenicSpotTicketList;  //景点门票列表
	private List<ComAd> advertList;  //广告列表
	
	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public List<CarInfo> getCarInfoList() {
		return carInfoList;
	}

	public void setCarInfoList(List<CarInfo> carInfoList) {
		this.carInfoList = carInfoList;
	}

	public List<ComComment> getComCommentList() {
		return comCommentList;
	}

	public void setComCommentList(List<ComComment> comCommentList) {
		this.comCommentList = comCommentList;
	}

	public List<ComCity> getComCityList() {
		return comCityList;
	}

	public void setComCityList(List<ComCity> comCityList) {
		this.comCityList = comCityList;
	}

	public List<ScenicSpot> getScenicSpotList() {
		return scenicSpotList;
	}

	public void setScenicSpotList(List<ScenicSpot> scenicSpotList) {
		this.scenicSpotList = scenicSpotList;
	}

	public List<LinerLine> getLinerLineList() {
		return linerLineList;
	}

	public void setLinerLineList(List<LinerLine> linerLineList) {
		this.linerLineList = linerLineList;
	}

	public List<OrderSys> getOrderSysList() {
		return orderSysList;
	}

	public void setOrderSysList(List<OrderSys> orderSysList) {
		this.orderSysList = orderSysList;
	}

	public List<TravelCustomization> getTravelCustomizationList() {
		return travelCustomizationList;
	}

	public void setTravelCustomizationList(List<TravelCustomization> travelCustomizationList) {
		this.travelCustomizationList = travelCustomizationList;
	}

	public List<CityStrategy> getCityStrategyList() {
		return CityStrategyList;
	}

	public void setCityStrategyList(List<CityStrategy> cityStrategyList) {
		CityStrategyList = cityStrategyList;
	}

	public List<Guide> getGuideList() {
		return guideList;
	}

	public void setGuideList(List<Guide> guideList) {
		this.guideList = guideList;
	}

	public List<GuideRoute> getGuideRouteList() {
		return guideRouteList;
	}

	public void setGuideRouteList(List<GuideRoute> guideRouteList) {
		this.guideRouteList = guideRouteList;
	}

	public List<Route> getRouteList() {
		return routeList;
	}

	public void setRouteList(List<Route> routeList) {
		this.routeList = routeList;
	}

	public List<ComArticle> getComArticleList() {
		return comArticleList;
	}

	public void setComArticleList(List<ComArticle> comArticleList) {
		this.comArticleList = comArticleList;
	}

	public List<SysUser> getSysUserList() {
		return SysUserList;
	}

	public void setSysUserList(List<SysUser> sysUserList) {
		SysUserList = sysUserList;
	}

	public List<ScenicSpotTicket> getScenicSpotTicketList() {
		return scenicSpotTicketList;
	}

	public void setScenicSpotTicketList(List<ScenicSpotTicket> scenicSpotTicketList) {
		this.scenicSpotTicketList = scenicSpotTicketList;
	}

	public List<ModuleDetails> getModuleDetails() {
		return moduleDetails;
	}

	public void setModuleDetails(List<ModuleDetails> moduleDetails) {
		this.moduleDetails = moduleDetails;
	}

	public Integer getModuleHtmlId() {
		return moduleHtmlId;
	}

	public void setModuleHtmlId(Integer moduleHtmlId) {
		this.moduleHtmlId = moduleHtmlId;
	}

	public Integer getModuleNameId() {
		return moduleNameId;
	}

	public void setModuleNameId(Integer moduleNameId) {
		this.moduleNameId = moduleNameId;
	}

	public Integer getLanguageId() {
		return languageId;
	}

	public void setLanguageId(Integer languageId) {
		this.languageId = languageId;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public Integer getModuleType() {
		return moduleType;
	}

	public void setModuleType(Integer moduleType) {
		this.moduleType = moduleType;
	}

	public String getModuleSize() {
		return moduleSize;
	}

	public void setModuleSize(String moduleSize) {
		this.moduleSize = moduleSize;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public ModuleContent() {
		super();
		this.setIdType(IDTYPE_AUTO);
	}

	public ModuleContent(String id){
		super(id);
	}

	@ExcelField(title="页面名称", align=2, sort=1)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ExcelField(title="对于的类型ID（多个用逗号区分）", align=2, sort=2)
	public String getTypeid() {
		return typeid;
	}

	public void setTypeid(String typeid) {
		this.typeid = typeid;
	}
	
	@ExcelField(title="排序类型", align=2, sort=3)
	public Integer getOrderType() {
		return orderType;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}
	
	@ExcelField(title="页脚是否显示", align=2, sort=4)
	public Integer getOneFooterShow() {
		return oneFooterShow;
	}

	public void setOneFooterShow(Integer oneFooterShow) {
		this.oneFooterShow = oneFooterShow;
	}
	
	@ExcelField(title="页脚图片（多个用逗号区分）", align=2, sort=5)
	public String getOneFooter() {
		return oneFooter;
	}

	public void setOneFooter(String oneFooter) {
		this.oneFooter = oneFooter;
	}
	
	@ExcelField(title="two_footer_show", align=2, sort=6)
	public Integer getTwoFooterShow() {
		return twoFooterShow;
	}

	public void setTwoFooterShow(Integer twoFooterShow) {
		this.twoFooterShow = twoFooterShow;
	}
	
	@ExcelField(title="two_footer", align=2, sort=7)
	public String getTwoFooter() {
		return twoFooter;
	}

	public void setTwoFooter(String twoFooter) {
		this.twoFooter = twoFooter;
	}
	
	@ExcelField(title="three_footer_show", align=2, sort=8)
	public Integer getThreeFooterShow() {
		return threeFooterShow;
	}

	public void setThreeFooterShow(Integer threeFooterShow) {
		this.threeFooterShow = threeFooterShow;
	}
	
	@ExcelField(title="three_footer", align=2, sort=9)
	public String getThreeFooter() {
		return threeFooter;
	}

	public void setThreeFooter(String threeFooter) {
		this.threeFooter = threeFooter;
	}
	
	@ExcelField(title="图片（多个用逗号隔开）", align=2, sort=10)
	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}
	
	@ExcelField(title="链接", align=2, sort=11)
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<ComAd> getAdvertList() {
		return advertList;
	}

	public void setAdvertList(List<ComAd> advertList) {
		this.advertList = advertList;
	}
	
}