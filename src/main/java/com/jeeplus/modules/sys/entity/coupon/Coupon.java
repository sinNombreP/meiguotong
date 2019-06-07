/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.entity.coupon;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 优惠卷管理Entity
 * @author xudemo
 * @version 2018-03-08
 */
public class Coupon extends DataEntity<Coupon> {
	
	private static final long serialVersionUID = 1L;
	private String title;		// 标题
	private String descrption;		// 描述
	private Double stock;		// 门槛 
	private Double price;		// 优惠价格
	private Integer status;		// 1启用2禁用
	private String category;		// 使用类别，多个类别用逗号隔开
	private String categoryName;		// category_name
	private Integer useType;		// 使用类别限定1无限制，2限制使用分类
	private Date beginDate;		// 开始时间
	private Date endDate;		// 结束时间
	private Integer type;		// 1.所有人 2.有条件发放 3.二维码获取
	private Integer typeWay;		// 选择条件后的条件，条件根据实际情况而定
	private Integer allNum;		// 最大份数
	private Integer useNum;		// 使用的人数量
	private Integer haveNum;		// 有多少张没发放
	private String delFlg;		// del_flg
	private Date delDate;		// del_date
	private String delBy;		// del_by
	private Date beginCreateDate;		// 开始 create_date
	private Date endCreateDate;		// 结束 create_date
	private String QRcode;			//二维码
	private Integer memberId;		//用于接收memberId
	private Integer isMe;			//用于判断本人是否拥有
	private Integer isHave;         //用于判断该优惠卷是否还有剩余
	
	public Integer getIsMe() {
		return isMe;
	}

	public void setIsMe(Integer isMe) {
		this.isMe = isMe;
	}

	public Integer getIsHave() {
		return isHave;
	}

	public void setIsHave(Integer isHave) {
		this.isHave = isHave;
	}

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public String getQRcode() {
		return QRcode;
	}

	public void setQRcode(String qRcode) {
		QRcode = qRcode;
	}

	public Coupon() {
		super();
		this.setIdType(IDTYPE_AUTO);
	}

	public Coupon(String id){
		super(id);
	}

	@ExcelField(title="标题", align=2, sort=1)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@ExcelField(title="描述", align=2, sort=2)
	public String getDescrption() {
		return descrption;
	}

	public void setDescrption(String descrption) {
		this.descrption = descrption;
	}
	
	@ExcelField(title="门槛 ", align=2, sort=3)
	public Double getStock() {
		return stock;
	}

	public void setStock(Double stock) {
		this.stock = stock;
	}
	
	@ExcelField(title="优惠价格", align=2, sort=4)
	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
	
	@ExcelField(title="1启用2禁用", align=2, sort=5)
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@ExcelField(title="使用类别，多个类别用逗号隔开", align=2, sort=6)
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	
	@ExcelField(title="category_name", align=2, sort=7)
	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	
	@ExcelField(title="使用类别限定1无限制，2限制使用分类", align=2, sort=8)
	public Integer getUseType() {
		return useType;
	}

	public void setUseType(Integer useType) {
		this.useType = useType;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="开始时间", align=2, sort=9)
	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="结束时间", align=2, sort=10)
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	@ExcelField(title="1.所有人 2.有条件发放 3.二维码获取", align=2, sort=11)
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
	@ExcelField(title="选择条件后的条件，条件根据实际情况而定", align=2, sort=12)
	public Integer getTypeWay() {
		return typeWay;
	}

	public void setTypeWay(Integer typeWay) {
		this.typeWay = typeWay;
	}
	
	@ExcelField(title="最大份数", align=2, sort=13)
	public Integer getAllNum() {
		return allNum;
	}

	public void setAllNum(Integer allNum) {
		this.allNum = allNum;
	}
	
	@ExcelField(title="使用的人数量", align=2, sort=14)
	public Integer getUseNum() {
		return useNum;
	}

	public void setUseNum(Integer useNum) {
		this.useNum = useNum;
	}
	
	@ExcelField(title="有多少张没发放", align=2, sort=15)
	public Integer getHaveNum() {
		return haveNum;
	}

	public void setHaveNum(Integer haveNum) {
		this.haveNum = haveNum;
	}
	
	@ExcelField(title="del_flg", align=2, sort=20)
	public String getDelFlg() {
		return delFlg;
	}

	public void setDelFlg(String delFlg) {
		this.delFlg = delFlg;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="del_date", align=2, sort=21)
	public Date getDelDate() {
		return delDate;
	}

	public void setDelDate(Date delDate) {
		this.delDate = delDate;
	}
	
	@ExcelField(title="del_by", align=2, sort=22)
	public String getDelBy() {
		return delBy;
	}

	public void setDelBy(String delBy) {
		this.delBy = delBy;
	}
	
	public Date getBeginCreateDate() {
		return beginCreateDate;
	}

	public void setBeginCreateDate(Date beginCreateDate) {
		this.beginCreateDate = beginCreateDate;
	}
	
	public Date getEndCreateDate() {
		return endCreateDate;
	}

	public void setEndCreateDate(Date endCreateDate) {
		this.endCreateDate = endCreateDate;
	}
		
}