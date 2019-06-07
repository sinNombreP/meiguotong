/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.entity.car;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeeplus.common.utils.excel.annotation.ExcelField;
import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.modules.meiguotong.entity.product.WeekDate;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 汽车业务表Entity
 * @author psz
 * @version 2018-08-31
 */
public class CarBusiness extends DataEntity<CarBusiness> {
	
	private static final long serialVersionUID = 1L;
	private Integer carid;		// 汽车id
	private Integer businessType;		// 1.包车租车2.短程接送3.接送机4.旅游定制5.商务定制
	private Integer status;		// 状态1启用2禁用
	private Integer dateType;		// 日期类型1.所有日期2.按星期3.按号数
	private Date beginDate;		// 选择所有日期的开始时间
	private Date endDate;		// 选择所有日期的结束时间
	private String weekDate;		// 选择的星期
	private String dayDate;		// 选择的号数
	private String hourDate;   //选择的时间
	private Integer language;		// 语言
	
	private List<CarTimePrice> carTimePriceList;  //汽车时间表list
	private BigDecimal price;		// 价格
	private BigDecimal startPrice;		// 起价小费
	private BigDecimal headPrice;		// 人头小费
	private BigDecimal foodPrice;		// 餐补
	private BigDecimal emptyPrice;		// 空车返回
	
	private String titleids;  //选择的标题
	private String[] weekList;  //星期
	private String[] dayList; //天数
	private String[] hourList;  //星期

	private List<WeekDate> dayList1;  //天数是否选中的集合
	
	public CarBusiness() {
		super();
		this.setIdType(IDTYPE_AUTO);
	}

	public String[] getWeekList() {
		return weekList;
	}

	public BigDecimal getEmptyPrice() {
		return emptyPrice;
	}

	public void setEmptyPrice(BigDecimal emptyPrice) {
		this.emptyPrice = emptyPrice;
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

	public String[] getHourList() {
		return hourList;
	}

	public void setHourList(String[] hourList) {
		this.hourList = hourList;
	}

	public String getTitleids() {
		return titleids;
	}

	public void setTitleids(String titleids) {
		this.titleids = titleids;
	}

	public String getHourDate() {
		return hourDate;
	}

	public void setHourDate(String hourDate) {
		this.hourDate = hourDate;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
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

	public List<CarTimePrice> getCarTimePriceList() {
		return carTimePriceList;
	}

	public void setCarTimePriceList(List<CarTimePrice> carTimePriceList) {
		this.carTimePriceList = carTimePriceList;
	}

	public CarBusiness(String id){
		super(id);
	}

	@ExcelField(title="汽车id", align=2, sort=1)
	public Integer getCarid() {
		return carid;
	}

	public void setCarid(Integer carid) {
		this.carid = carid;
	}
	
	@ExcelField(title="1.包车租车2.短程接送3.接送机4.旅游定制5.商务定制", align=2, sort=2)
	public Integer getBusinessType() {
		return businessType;
	}

	public void setBusinessType(Integer businessType) {
		this.businessType = businessType;
	}
	
	@ExcelField(title="状态1启用2禁用", align=2, sort=3)
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@ExcelField(title="日期类型1.所有日期2.按星期3.按号数", align=2, sort=4)
	public Integer getDateType() {
		return dateType;
	}

	public void setDateType(Integer dateType) {
		this.dateType = dateType;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="选择所有日期的开始时间", align=2, sort=5)
	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="选择所有日期的结束时间", align=2, sort=6)
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	@ExcelField(title="选择的星期", align=2, sort=7)
	public String getWeekDate() {
		return weekDate;
	}

	public void setWeekDate(String weekDate) {
		this.weekDate = weekDate;
	}
	
	@ExcelField(title="选择的号数", align=2, sort=8)
	public String getDayDate() {
		return dayDate;
	}

	public void setDayDate(String dayDate) {
		this.dayDate = dayDate;
	}
	
	@ExcelField(title="语言", align=2, sort=9)
	public Integer getLanguage() {
		return language;
	}

	public void setLanguage(Integer language) {
		this.language = language;
	}

	public List<WeekDate> getDayList1() {
		return dayList1;
	}

	public void setDayList1(List<WeekDate> dayList1) {
		this.dayList1 = dayList1;
	}
	
}