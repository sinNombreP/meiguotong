/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.entity.product;

/**
 * 当地参团Entity
 * @author psz
 * @version 2018-08-13
 */
public class WeekDate{
	private String name;
	private String id;
	private Integer digFlag;//是否选中1.是2.否
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getDigFlag() {
		return digFlag;
	}
	public void setDigFlag(Integer digFlag) {
		this.digFlag = digFlag;
	}
	

}