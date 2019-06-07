/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.entity.comcity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeeplus.common.utils.excel.annotation.ExcelField;
import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.modules.meiguotong.entity.comcitytravel.ComCityTravel;
import com.jeeplus.modules.meiguotong.entity.scenicspot.ScenicSpot;
import com.jeeplus.modules.meiguotong.entity.travel.OrderTravelDetails;

import java.util.Date;
import java.util.List;

/**
 * 城市表Entity
 * @author cdq
 * @version 2018-08-02
 */
public class ComCity extends DataEntity<ComCity> {
	
	private static final long serialVersionUID = 1L;
	private Integer number;		// 城市编码 城市ID
	private Integer provinceId;		// 关键国家表主键ID
	private String cityDestination;//目的地
	private String namePinyin;		// 拼音
	private Integer status;		// 状态   1 启用2禁用 
	private String languageId;		// 关联 语言表主键id
	private String photoUrl;		// 图片 可以上传多张图
	private String videoUrl;		// 城市介绍 视频
	private String nearCityNumber;		// 附近城市编号
	private Integer isCar;		// 是否开启租车模块  0 关闭 1 开启
	private Integer isTourism;		// 是否启用旅游定制模块 0 禁用  1 启用
	private Integer isOffered;		// 当地参团模块  0 禁用 1 启用
	private Integer isScenic;		// 景点模块  0 禁用 1 启用
	private Integer isPlayer;		// 当地玩家模块 0 禁用 1启用
	private String offerenTop;		// 当地参团 置顶 编号  逗号隔开
	private Integer scenicSort;		// 景点排序 0 综合排序  1  时间排序  2 订单量排序
	private Integer offeredSort;		// 当地参团 排序  0 综合排序 1 时间排序  2 订单量排序
	private String scenicTop;		// 景点置顶编号 逗号隔开
	private Integer playerSort;		// 当地玩家排序  0 综合排序 1 时间排序 2 订单量排序
	private String playerTop;		// 当地玩家 置顶编号  逗号隔开
	private Integer isCharter;		// 是否显示在包车租车模块  0 不显示 1 显示
	private Integer isTransfer;		// 是否显示在接送机模块  0 不显示 1 显示
	private Integer isSortDistance;		// 是否显示在短程接送模块  0 不显示 1 显示
	private String  cityName;//城市名称
	private  String  cityRemark;//城市备注
	private  String  cityDetails;//城市详情	
	private Date delDate;		// del_date
	private String delBy;		// del_by

	private List<ComCityTravel> list; //定制旅游集合
	private String endCity;  //已选择的城市ID
	private Integer endCityFlag;  //是否选择 //0 未选择 1 已选择
	
	private List<OrderTravelDetails> orderTravelDetails;//定制详情
	
	private List<OrderTravelDetails> orderbusness;//定制详情

	private Integer num;  //城市下的景点数量

	private Integer cityid;		// 城市ID(接口)
	
	List<ComCity> comCity;

	private List<String> photoList;

	private String languageName;     //语言名称

	private String countryName;  //国家名称
	private String ids;   //模块选择的ID
	
	private Integer commonNum; //评论数量
	
	private Double star; //星级
	
	private String cityids;   //城市ID集合
	
	private List<ScenicSpot> scenics;//旅游定制的城市景点集合

	private String value;  //城市ID
	private String label;   //城市名称

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Integer getCommonNum() {
		return commonNum;
	}

	public List<String> getPhotoList() {
		return photoList;
	}

	public void setPhotoList(List<String> photoList) {
		this.photoList = photoList;
	}

	public void setCommonNum(Integer commonNum) {
		this.commonNum = commonNum;
	}

	public Double getStar() {
		return star;
	}

	public void setStar(Double star) {
		this.star = star;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public Integer getEndCityFlag() {
		return endCityFlag;
	}

	public void setEndCityFlag(Integer endCityFlag) {
		this.endCityFlag = endCityFlag;
	}

	public String getEndCity() {
		return endCity;
	}

	public void setEndCity(String endCity) {
		this.endCity = endCity;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCityRemark() {
		return cityRemark;
	}

	public void setCityRemark(String cityRemark) {
		this.cityRemark = cityRemark;
	}

	public String getCityDetails() {
		return cityDetails;
	}

	public void setCityDetails(String cityDetails) {
		this.cityDetails = cityDetails;
	}

	public String getCityDestination() {
		return cityDestination;
	}

	public void setCityDestination(String cityDestination) {
		this.cityDestination = cityDestination;
	}

	public List<ComCity> getComCity() {
		return comCity;
	}

	public void setComCity(List<ComCity> comCity) {
		this.comCity = comCity;
	}

	public ComCity() {
		super();
		this.setIdType(IDTYPE_AUTO);
	}

	public ComCity(String id){
		super(id);
	}

	@ExcelField(title="城市编码 城市ID", align=2, sort=1)
	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}
	
	@ExcelField(title="关键国家表主键ID", align=2, sort=2)
	public Integer getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(Integer provinceId) {
		this.provinceId = provinceId;
	}
	
	@ExcelField(title="拼音", align=2, sort=4)
	public String getNamePinyin() {
		return namePinyin;
	}

	public void setNamePinyin(String namePinyin) {
		this.namePinyin = namePinyin;
	}
	

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@ExcelField(title="关联 语言表主键id", align=2, sort=6)
	public String getLanguageId() {
		return languageId;
	}

	public void setLanguageId(String languageId) {
		this.languageId = languageId;
	}
	
	@ExcelField(title="图片 可以上传多张图", align=2, sort=7)
	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}
	
	@ExcelField(title="城市介绍 视频", align=2, sort=8)
	public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}
	
	@ExcelField(title="附近城市编号", align=2, sort=9)
	public String getNearCityNumber() {
		return nearCityNumber;
	}

	public void setNearCityNumber(String nearCityNumber) {
		this.nearCityNumber = nearCityNumber;
	}
	
	@ExcelField(title="是否开启租车模块  0 关闭 1 开启", align=2, sort=10)
	public Integer getIsCar() {
		return isCar;
	}

	public void setIsCar(Integer isCar) {
		this.isCar = isCar;
	}
	
	@ExcelField(title="是否启用旅游定制模块 0 禁用  1 启用", align=2, sort=11)
	public Integer getIsTourism() {
		return isTourism;
	}

	public void setIsTourism(Integer isTourism) {
		this.isTourism = isTourism;
	}
	
	@ExcelField(title="当地参团模块  0 禁用 1 启用", align=2, sort=12)
	public Integer getIsOffered() {
		return isOffered;
	}

	public void setIsOffered(Integer isOffered) {
		this.isOffered = isOffered;
	}
	
	@ExcelField(title="景点模块  0 禁用 1 启用", align=2, sort=13)
	public Integer getIsScenic() {
		return isScenic;
	}

	public void setIsScenic(Integer isScenic) {
		this.isScenic = isScenic;
	}
	
	@ExcelField(title="当地玩家模块 0 禁用 1启用", align=2, sort=14)
	public Integer getIsPlayer() {
		return isPlayer;
	}

	public void setIsPlayer(Integer isPlayer) {
		this.isPlayer = isPlayer;
	}
	
	@ExcelField(title="当地参团 置顶 编号  逗号隔开", align=2, sort=15)
	public String getOfferenTop() {
		return offerenTop;
	}

	public void setOfferenTop(String offerenTop) {
		this.offerenTop = offerenTop;
	}
	
	@ExcelField(title="景点排序 0 综合排序  1  时间排序  2 订单量排序", align=2, sort=16)
	public Integer getScenicSort() {
		return scenicSort;
	}

	public void setScenicSort(Integer scenicSort) {
		this.scenicSort = scenicSort;
	}
	
	@ExcelField(title="当地参团 排序  0 综合排序 1 时间排序  2 订单量排序", align=2, sort=17)
	public Integer getOfferedSort() {
		return offeredSort;
	}

	public void setOfferedSort(Integer offeredSort) {
		this.offeredSort = offeredSort;
	}
	
	@ExcelField(title="景点置顶编号 逗号隔开", align=2, sort=18)
	public String getScenicTop() {
		return scenicTop;
	}

	public void setScenicTop(String scenicTop) {
		this.scenicTop = scenicTop;
	}
	
	@ExcelField(title="当地玩家排序  0 综合排序 1 时间排序 2 订单量排序", align=2, sort=19)
	public Integer getPlayerSort() {
		return playerSort;
	}

	public void setPlayerSort(Integer playerSort) {
		this.playerSort = playerSort;
	}
	
	@ExcelField(title="当地玩家 置顶编号  逗号隔开", align=2, sort=20)
	public String getPlayerTop() {
		return playerTop;
	}

	public void setPlayerTop(String playerTop) {
		this.playerTop = playerTop;
	}
	
	@ExcelField(title="是否显示在包车租车模块  0 不显示 1 显示", align=2, sort=21)
	public Integer getIsCharter() {
		return isCharter;
	}

	public void setIsCharter(Integer isCharter) {
		this.isCharter = isCharter;
	}
	
	@ExcelField(title="是否显示在接送机模块  0 不显示 1 显示", align=2, sort=22)
	public Integer getIsTransfer() {
		return isTransfer;
	}

	public void setIsTransfer(Integer isTransfer) {
		this.isTransfer = isTransfer;
	}
	
	@ExcelField(title="是否显示在短程接送模块  0 不显示 1 显示", align=2, sort=23)
	public Integer getIsSortDistance() {
		return isSortDistance;
	}

	public void setIsSortDistance(Integer isSortDistance) {
		this.isSortDistance = isSortDistance;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="del_date", align=2, sort=28)
	public Date getDelDate() {
		return delDate;
	}

	public void setDelDate(Date delDate) {
		this.delDate = delDate;
	}
	
	@ExcelField(title="del_by", align=2, sort=29)
	public String getDelBy() {
		return delBy;
	}

	public void setDelBy(String delBy) {
		this.delBy = delBy;
	}

	public Integer getCityid() {
		return cityid;
	}

	public void setCityid(Integer cityid) {
		this.cityid = cityid;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public String getLanguageName() {
		return languageName;
	}

	public void setLanguageName(String languageName) {
		this.languageName = languageName;
	}

	public String getCityids() {
		return cityids;
	}

	public void setCityids(String cityids) {
		this.cityids = cityids;
	}

	public List<ComCityTravel> getList() {
		return list;
	}

	public void setList(List<ComCityTravel> list) {
		this.list = list;
	}

	public List<ScenicSpot> getScenics() {
		return scenics;
	}

	public void setScenics(List<ScenicSpot> scenics) {
		this.scenics = scenics;
	}

	public List<OrderTravelDetails> getOrderTravelDetails() {
		return orderTravelDetails;
	}

	public void setOrderTravelDetails(List<OrderTravelDetails> orderTravelDetails) {
		this.orderTravelDetails = orderTravelDetails;
	}

	public List<OrderTravelDetails> getOrderbusness() {
		return orderbusness;
	}

	public void setOrderbusness(List<OrderTravelDetails> orderbusness) {
		this.orderbusness = orderbusness;
	}
	
	
}