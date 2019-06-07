/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.entity.buycar;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 购物车Entity
 * @author dong
 * @version 2018-03-08
 */
public class BuyCar extends DataEntity<BuyCar> {
	
	private static final long serialVersionUID = 1L;
	private Integer memberid;		// 会员ID
	private Integer productid;		// 商品ID
	private Integer num;		// 商品数量
	private Double price;		// 单价
	private Double allprice;		// 总价
	
	public BuyCar() {
		super();
		this.setIdType(IDTYPE_AUTO);
	}

	public BuyCar(String id){
		super(id);
	}

	@ExcelField(title="会员ID", align=2, sort=1)
	public Integer getMemberid() {
		return memberid;
	}

	public void setMemberid(Integer memberid) {
		this.memberid = memberid;
	}
	
	@ExcelField(title="商品ID", align=2, sort=2)
	public Integer getProductid() {
		return productid;
	}

	public void setProductid(Integer productid) {
		this.productid = productid;
	}
	
	@ExcelField(title="商品数量", align=2, sort=3)
	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}
	
	@ExcelField(title="单价", align=2, sort=4)
	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
	
	@ExcelField(title="总价", align=2, sort=5)
	public Double getAllprice() {
		return allprice;
	}

	public void setAllprice(Double allprice) {
		this.allprice = allprice;
	}
	
}