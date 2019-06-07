/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.entity.score;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 积分设置Entity
 * @author psz
 * @version 2018-03-06
 */
public class Score extends DataEntity<Score> {
	
	private static final long serialVersionUID = 1L;
	private String getWay;		// 获取职分的方式，枚举值
	private String cricle;		// 周期
	private Integer num;		// 每一个周期可以获得的次数
	private Integer sorce;		// 积分数
	
	public Score() {
		super();
		this.setIdType(IDTYPE_AUTO);
	}

	public Score(String id){
		super(id);
	}

	@ExcelField(title="获取职分的方式，枚举值", dictType="", align=2, sort=1)
	public String getGetWay() {
		return getWay;
	}

	public void setGetWay(String getWay) {
		this.getWay = getWay;
	}
	
	@ExcelField(title="周期", align=2, sort=2)
	public String getCricle() {
		return cricle;
	}

	public void setCricle(String cricle) {
		this.cricle = cricle;
	}
	
	@ExcelField(title="每一个周期可以获得的次数", align=2, sort=3)
	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}
	
	@ExcelField(title="积分数", align=2, sort=4)
	public Integer getSorce() {
		return sorce;
	}

	public void setSorce(Integer sorce) {
		this.sorce = sorce;
	}
	
}