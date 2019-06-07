/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.entity.car;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeeplus.common.utils.excel.annotation.ExcelField;
import com.jeeplus.core.persistence.DataEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 汽车时间表Entity
 * @author psz
 * @version 2018-08-31
 */
public class CarTimePrice extends DataEntity<CarTimePrice> {
	
	private static final long serialVersionUID = 1L;
	private Integer type;		// 语言
	private Integer language;		// 语言
	private Integer businessid;		// 汽车业务id
	private Date busiDate;		// 日期
	private BigDecimal price;		// 价格
	private BigDecimal startPrice;		// 起价小费
	private BigDecimal headPrice;		// 人头小费
	private BigDecimal foodPrice;		// 餐补
	private BigDecimal emptyPrice;		// 空车返回
	private Date beginTime;		// 起始时间
	private Date endTime;		// 结束时间
	private String serviceid;		// 包车租车业务id
	private Integer range;		// 范围1.接机2.本地城市3.附近城市4.距离半径
	private Integer radius;		// 半径距离（km）

	private String title;  //包车租车标题
	private String carid;  //汽车id
	private String businessType;//汽车业务类型
	
	public CarTimePrice() {
		super();
		this.setIdType(IDTYPE_AUTO);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public CarTimePrice(String id){
		super(id);
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@ExcelField(title="语言", align=2, sort=1)
	public Integer getLanguage() {
		return language;
	}

	public void setLanguage(Integer language) {
		this.language = language;
	}
	
	@ExcelField(title="汽车业务id", align=2, sort=2)
	public Integer getBusinessid() {
		return businessid;
	}

	public void setBusinessid(Integer businessid) {
		this.businessid = businessid;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="日期", align=2, sort=3)
	public Date getBusiDate() {
		return busiDate;
	}

	public void setBusiDate(Date busiDate) {
		this.busiDate = busiDate;
	}
	
	@ExcelField(title="价格", align=2, sort=4)
	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
	@ExcelField(title="起价小费", align=2, sort=5)
	public BigDecimal getStartPrice() {
		return startPrice;
	}

	public void setStartPrice(BigDecimal startPrice) {
		this.startPrice = startPrice;
	}
	
	@ExcelField(title="人头小费", align=2, sort=6)
	public BigDecimal getHeadPrice() {
		return headPrice;
	}

	public void setHeadPrice(BigDecimal headPrice) {
		this.headPrice = headPrice;
	}
	
	@ExcelField(title="餐补", align=2, sort=7)
	public BigDecimal getFoodPrice() {
		return foodPrice;
	}

	public void setFoodPrice(BigDecimal foodPrice) {
		this.foodPrice = foodPrice;
	}
	
	@ExcelField(title="空车返回", align=2, sort=8)
	public BigDecimal getEmptyPrice() {
		return emptyPrice;
	}

	public void setEmptyPrice(BigDecimal emptyPrice) {
		this.emptyPrice = emptyPrice;
	}
	
	@JsonFormat(pattern = "HH:mm:ss")
	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	
	@JsonFormat(pattern = "HH:mm:ss")
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	@ExcelField(title="包车租车业务id", align=2, sort=11)
	public String getServiceid() {
		return serviceid;
	}

	public void setServiceid(String serviceid) {
		this.serviceid = serviceid;
	}
	
	@ExcelField(title="范围1.接机2.本地城市3.附近城市4.距离半径", align=2, sort=12)
	public Integer getRange() {
		return range;
	}

	public void setRange(Integer range) {
		this.range = range;
	}
	
	@ExcelField(title="半径距离（km）", align=2, sort=13)
	public Integer getRadius() {
		return radius;
	}

	public void setRadius(Integer radius) {
		this.radius = radius;
	}

	public String getCarid() {
		return carid;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setCarid(String carid) {
		this.carid = carid;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}
	
}