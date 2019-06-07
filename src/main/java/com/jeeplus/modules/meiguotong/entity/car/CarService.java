/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.entity.car;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.modules.meiguotong.entity.comcity.ComCity;
import com.jeeplus.modules.test.entity.treetable.Car;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 汽车标题表Entity
 * @author psz
 * @version 2018-08-31
 */
public class CarService extends DataEntity<CarService> {
	
	private static final long serialVersionUID = 1L;
	private Integer language;		// 语言
	private String title;		// 业务标题
	private Date delDate;		// del_date
	private String delBy;		// 删除人
	private Integer range; //'范围1.接机2.本地城市3.附近城市',
	private Integer radius; //'半径距离（km）',

	private List<ComCity> comCityList;   //附近城市

	private String ids; //选择的标题
	private String languageName;     //语言名称

	public CarService() {
		super();
		this.setIdType(IDTYPE_AUTO);
	}

	public String getLanguageName() {
		return languageName;
	}

	public void setLanguageName(String languageName) {
		this.languageName = languageName;
	}

	public List<ComCity> getComCityList() {
		return comCityList;
	}

	public void setComCityList(List<ComCity> comCityList) {
		this.comCityList = comCityList;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public CarService(String id){
		super(id);
	}

	@ExcelField(title="语言", align=2, sort=1)
	public Integer getLanguage() {
		return language;
	}

	public void setLanguage(Integer language) {
		this.language = language;
	}
	
	@ExcelField(title="业务标题", align=2, sort=2)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="del_date", align=2, sort=8)
	public Date getDelDate() {
		return delDate;
	}

	public void setDelDate(Date delDate) {
		this.delDate = delDate;
	}
	
	@ExcelField(title="删除人", align=2, sort=9)
	public String getDelBy() {
		return delBy;
	}

	public void setDelBy(String delBy) {
		this.delBy = delBy;
	}

	public Integer getRange() {
		return range;
	}

	public Integer getRadius() {
		return radius;
	}

	public void setRange(Integer range) {
		this.range = range;
	}

	public void setRadius(Integer radius) {
		this.radius = radius;
	}

}