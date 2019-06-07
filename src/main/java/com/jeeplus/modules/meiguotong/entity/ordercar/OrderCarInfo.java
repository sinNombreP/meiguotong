/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.entity.ordercar;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 车辆订单车辆详情Entity
 * @author psz
 * @version 2018-08-30
 */
public class OrderCarInfo extends DataEntity<OrderCarInfo> {
	
	private static final long serialVersionUID = 1L;
	private String orderid;		// 车辆订单ID
	private String carName;		// 汽车名称
	private Integer seatNum;		// 座位数
	private String comfort;		// 舒适度
	private Integer bagNum;  //包裹数量
	
	public OrderCarInfo() {
		super();
		this.setIdType(IDTYPE_AUTO);
	}

	public Integer getBagNum() {
		return bagNum;
	}

	public void setBagNum(Integer bagNum) {
		this.bagNum = bagNum;
	}

	public OrderCarInfo(String id){
		super(id);
	}

	@ExcelField(title="车辆订单ID", align=2, sort=1)
	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}
	
	@ExcelField(title="汽车名称", align=2, sort=2)
	public String getCarName() {
		return carName;
	}

	public void setCarName(String carName) {
		this.carName = carName;
	}
	
	@ExcelField(title="座位数", align=2, sort=3)
	public Integer getSeatNum() {
		return seatNum;
	}

	public void setSeatNum(Integer seatNum) {
		this.seatNum = seatNum;
	}
	
	@ExcelField(title="舒适度", align=2, sort=4)
	public String getComfort() {
		return comfort;
	}

	public void setComfort(String comfort) {
		this.comfort = comfort;
	}
	
}