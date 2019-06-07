/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.entity.module;


import java.util.List;

import com.jeeplus.common.utils.excel.annotation.ExcelField;
import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.modules.meiguotong.entity.car.CarInfo;
import com.jeeplus.modules.meiguotong.entity.citystrategy.CityStrategy;
import com.jeeplus.modules.meiguotong.entity.comarticle.ComArticle;
import com.jeeplus.modules.meiguotong.entity.comcity.ComCity;
import com.jeeplus.modules.meiguotong.entity.comcomment.ComComment;
import com.jeeplus.modules.meiguotong.entity.guide.Guide;
import com.jeeplus.modules.meiguotong.entity.guideroute.GuideRoute;
import com.jeeplus.modules.meiguotong.entity.liner.Liner;
import com.jeeplus.modules.meiguotong.entity.liner_line.LinerLine;
import com.jeeplus.modules.meiguotong.entity.order.OrderSys;
import com.jeeplus.modules.meiguotong.entity.product.Route;
import com.jeeplus.modules.meiguotong.entity.scenicspot.ScenicSpot;
import com.jeeplus.modules.meiguotong.entity.scenicspotticket.ScenicSpotTicket;
import com.jeeplus.modules.meiguotong.entity.travel.TravelCustomization;
import com.jeeplus.modules.sys.entity.sysUser.SysUser;

/**
 * 模块详情Entity
 * @author psz
 * @version 2018-12-03
 */
public class ModuleDetails extends DataEntity<ModuleDetails> {
	
	private static final long serialVersionUID = 1L;
	private Integer moduleContentId;		// module_content的主键ID
	private String cityid;		// 地区限制（城市ID：多个用逗号区分）
	private String tagid;		// 标签属性（多个用逗号区分）
	private String desid;		// 对于的类型ID（多个用逗号区分）
	private String firstTypeContent;		// 一级分类名称（页脚）
	private String title;		// 标题
	private String link;		// 链接
	
	private List<CarInfo> carInfoList;  //评论列表
	private List<ComComment> comCommentList;  //评论列表
	private List<ComCity> comCityList;  //城市列表
	private List<ScenicSpot> scenicSpotList;  //景点列表
	private List<LinerLine> linerLineList;  //游轮列表
	private List<OrderSys> orderSysList;  //订单列表
	private List<TravelCustomization> travelCustomizationList;  //定制列表
	private List<CityStrategy> CityStrategyList;  //攻略列表
	private List<Guide> guideList;  //导游列表
	private List<GuideRoute> guideRouteList;  //导游路线列表
	private List<Route> routeList;  //常规路线/当地参团列表
	private List<ComArticle> comArticleList;  //网站文章/资讯列表
	private List<SysUser> SysUserList;  //供应商列表
	private List<ScenicSpotTicket> scenicSpotTicketList;  //景点门票列表
	
	public ModuleDetails() {
		super();
		this.setIdType(IDTYPE_AUTO);
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

	public ModuleDetails(String id){
		super(id);
	}

	@ExcelField(title="module_content的主键ID", align=2, sort=1)
	public Integer getModuleContentId() {
		return moduleContentId;
	}

	public void setModuleContentId(Integer moduleContentId) {
		this.moduleContentId = moduleContentId;
	}
	
	@ExcelField(title="地区限制（城市ID：多个用逗号区分）", align=2, sort=2)
	public String getCityid() {
		return cityid;
	}

	public void setCityid(String cityid) {
		this.cityid = cityid;
	}
	
	@ExcelField(title="标签属性（多个用逗号区分）", align=2, sort=3)
	public String getTagid() {
		return tagid;
	}

	public void setTagid(String tagid) {
		this.tagid = tagid;
	}
	
	public String getDesid() {
		return desid;
	}

	public void setDesid(String desid) {
		this.desid = desid;
	}

	@ExcelField(title="一级分类名称（页脚）", align=2, sort=5)
	public String getFirstTypeContent() {
		return firstTypeContent;
	}

	public void setFirstTypeContent(String firstTypeContent) {
		this.firstTypeContent = firstTypeContent;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}
	
}