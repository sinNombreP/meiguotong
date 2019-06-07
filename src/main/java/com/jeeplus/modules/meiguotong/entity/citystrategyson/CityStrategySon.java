/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.entity.citystrategyson;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 攻略子类表Entity
 * @author cdq
 * @version 2018-08-01
 */
public class CityStrategySon extends DataEntity<CityStrategySon> {
	
	private static final long serialVersionUID = 1L;
	private String secnicInfo;		// 游玩景点
	private Date playTime;		// 游玩日期
	private String weekDay;		// 周几
	private Date createTime;		// 创建日期
	private String hotelInfo;		// 酒店名称  多个用逗号隔开
	private Integer strategyId;		// 主表 主键id
	private String cityInfo;		// 城市名称
	private Integer languageId;		// 语言id
	private Integer status;		// 状态  0 禁用 1 启用
	private Date delDate;		// del_date
	private String delBy;		// del_by
	
	public CityStrategySon() {
		super();
	}

	public CityStrategySon(String id){
		super(id);
	}

	@ExcelField(title="游玩景点", align=2, sort=1)
	public String getSecnicInfo() {
		return secnicInfo;
	}

	public void setSecnicInfo(String secnicInfo) {
		this.secnicInfo = secnicInfo;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@ExcelField(title="游玩日期", align=2, sort=2)
	public Date getPlayTime() {
		return playTime;
	}

	public void setPlayTime(Date playTime) {
		this.playTime = playTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="创建日期", align=2, sort=3)
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@ExcelField(title="酒店名称  多个用逗号隔开", align=2, sort=4)
	public String getHotelInfo() {
		return hotelInfo;
	}

	public void setHotelInfo(String hotelInfo) {
		this.hotelInfo = hotelInfo;
	}
	
	@ExcelField(title="主表 主键id", align=2, sort=5)
	public Integer getStrategyId() {
		return strategyId;
	}

	public void setStrategyId(Integer strategyId) {
		this.strategyId = strategyId;
	}
	
	@ExcelField(title="城市名称", align=2, sort=6)
	public String getCityInfo() {
		return cityInfo;
	}

	public void setCityInfo(String cityInfo) {
		this.cityInfo = cityInfo;
	}
	
	@ExcelField(title="语言id", align=2, sort=7)
	public Integer getLanguageId() {
		return languageId;
	}

	public void setLanguageId(Integer languageId) {
		this.languageId = languageId;
	}
	
	@ExcelField(title="状态  0 禁用 1 启用", align=2, sort=8)
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="del_date", align=2, sort=13)
	public Date getDelDate() {
		return delDate;
	}

	public void setDelDate(Date delDate) {
		this.delDate = delDate;
	}
	
	@ExcelField(title="del_by", align=2, sort=14)
	public String getDelBy() {
		return delBy;
	}

	public void setDelBy(String delBy) {
		this.delBy = delBy;
	}

	public String getWeekDay() {
		return weekDay;
	}

	public void setWeekDay(String weekDay) {
		this.weekDay = weekDay;
	}
	
}