/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.entity.car;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeeplus.common.utils.excel.annotation.ExcelField;
import com.jeeplus.core.persistence.DataEntity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 租车管理Entity
 * @author psz
 * @version 2018-08-31
 */
public class CarInfo extends DataEntity<CarInfo> {
	
	private static final long serialVersionUID = 1L;
	private Integer language;		// 语言
	private String carNo;		// 汽车编号
	private Integer status;		// 1审核中2已拒绝3正常4禁用
	private Integer city;		// 城市
	private String carImg;		// 汽车图片
	private String carVideo;		// 汽车视频
	private Integer seatNum;		// 座位数
	private Integer adviseNum;		// 建议乘坐人数
	private Integer bagNum;		// 包裹数量
	private Integer comfort;		// 舒适度
	private Integer useYear;		// 使用年限
	private String carName;		// 汽车名称
	private String carTrait;		// 汽车卖点
	private Date delDate;		// del_date
	private String delBy;		// 删除人

	private Integer commentNum;		// 评论数量
	private String star;		// 平均星级
	
	private String cityName;  //城市名称
	private String companyName;  //供应商名称
	private String searchContent;  //搜索内容
	private List<CarBusiness> carBusinessList;  //汽车业务list

	private Date date;		// 日期，搜索接口用
	private String carType;		// 业务类型，搜索接口用
	private BigDecimal price;		// 价格，搜索接口用
	private BigDecimal startPrice;		// 起价小费
	private BigDecimal headPrice;		// 人头小费
	private BigDecimal foodPrice;		// 餐补
	private BigDecimal emptyPrice;		// 空车返回
	
	private String ids;   //模块选择的ID
	private String countryName;  //国家名称

	private String Serviceids;  //汽车标题的id

	public String getServiceids() {
		return Serviceids;
	}

	public void setServiceids(String serviceids) {
		Serviceids = serviceids;
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

	public CarInfo() {
		super();
		this.setIdType(IDTYPE_AUTO);
	}


	public BigDecimal getStartPrice() {
		return startPrice;
	}

	public void setStartPrice(BigDecimal startPrice) {
		this.startPrice = startPrice;
	}

	public BigDecimal getHeadPrice() {
		return headPrice;
	}

	public void setHeadPrice(BigDecimal headPrice) {
		this.headPrice = headPrice;
	}

	public BigDecimal getFoodPrice() {
		return foodPrice;
	}

	public void setFoodPrice(BigDecimal foodPrice) {
		this.foodPrice = foodPrice;
	}

	public BigDecimal getEmptyPrice() {
		return emptyPrice;
	}

	public void setEmptyPrice(BigDecimal emptyPrice) {
		this.emptyPrice = emptyPrice;
	}

	public List<CarBusiness> getCarBusinessList() {
		return carBusinessList;
	}

	public void setCarBusinessList(List<CarBusiness> carBusinessList) {
		this.carBusinessList = carBusinessList;
	}

	public String getSearchContent() {
		return searchContent;
	}

	public void setSearchContent(String searchContent) {
		this.searchContent = searchContent;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public CarInfo(String id){
		super(id);
	}

	@ExcelField(title="语言", align=2, sort=1)
	public Integer getLanguage() {
		return language;
	}

	public void setLanguage(Integer language) {
		this.language = language;
	}
	
	@ExcelField(title="汽车编号", align=2, sort=2)
	public String getCarNo() {
		return carNo;
	}

	public void setCarNo(String carNo) {
		this.carNo = carNo;
	}
	
	@ExcelField(title="城市", align=2, sort=4)
	public Integer getCity() {
		return city;
	}

	public void setCity(Integer city) {
		this.city = city;
	}
	
	@ExcelField(title="汽车图片", align=2, sort=5)
	public String getCarImg() {
		return carImg;
	}

	public void setCarImg(String carImg) {
		this.carImg = carImg;
	}
	
	@ExcelField(title="汽车视频", align=2, sort=6)
	public String getCarVideo() {
		return carVideo;
	}

	public void setCarVideo(String carVideo) {
		this.carVideo = carVideo;
	}
	
	@ExcelField(title="座位数", align=2, sort=7)
	public Integer getSeatNum() {
		return seatNum;
	}

	public void setSeatNum(Integer seatNum) {
		this.seatNum = seatNum;
	}
	
	@ExcelField(title="建议乘坐人数", align=2, sort=8)
	public Integer getAdviseNum() {
		return adviseNum;
	}

	public void setAdviseNum(Integer adviseNum) {
		this.adviseNum = adviseNum;
	}
	
	@ExcelField(title="包裹数量", align=2, sort=9)
	public Integer getBagNum() {
		return bagNum;
	}

	public void setBagNum(Integer bagNum) {
		this.bagNum = bagNum;
	}
	
	@ExcelField(title="舒适度", align=2, sort=10)
	public Integer getComfort() {
		return comfort;
	}

	public void setComfort(Integer comfort) {
		this.comfort = comfort;
	}
	
	@ExcelField(title="使用年限", align=2, sort=11)
	public Integer getUseYear() {
		return useYear;
	}

	public void setUseYear(Integer useYear) {
		this.useYear = useYear;
	}
	
	@ExcelField(title="汽车名称", align=2, sort=12)
	public String getCarName() {
		return carName;
	}

	public void setCarName(String carName) {
		this.carName = carName;
	}
	
	@ExcelField(title="汽车卖点", align=2, sort=13)
	public String getCarTrait() {
		return carTrait;
	}

	public void setCarTrait(String carTrait) {
		this.carTrait = carTrait;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="del_date", align=2, sort=19)
	public Date getDelDate() {
		return delDate;
	}

	public void setDelDate(Date delDate) {
		this.delDate = delDate;
	}
	
	@ExcelField(title="删除人", align=2, sort=20)
	public String getDelBy() {
		return delBy;
	}

	public void setDelBy(String delBy) {
		this.delBy = delBy;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Integer getCommentNum() {
		return commentNum;
	}

	public String getStar() {
		return star;
	}

	public void setCommentNum(Integer commentNum) {
		this.commentNum = commentNum;
	}

	public void setStar(String star) {
		this.star = star;
	}

	public String getCarType() {
		return carType;
	}

	public void setCarType(String carType) {
		this.carType = carType;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
}