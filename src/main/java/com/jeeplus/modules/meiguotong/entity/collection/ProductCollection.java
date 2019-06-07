/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.entity.collection;


import com.jeeplus.common.utils.excel.annotation.ExcelField;
import com.jeeplus.core.persistence.DataEntity;

import java.math.BigDecimal;

/**
 * 我的收藏Entity
 * @author dong
 * @version 2018-09-14
 */
public class ProductCollection extends DataEntity<ProductCollection> {
	
	private static final long serialVersionUID = 1L;
	private Integer type;		// 1.常规路线2.当地参团3.当地玩家4.游轮5.景点
	private Integer typeid;		// 收藏产品id
	private Integer memberid;		// 用户id
	private Integer languageid;		// 语言id
	
	private Integer collectionType;		// 1.常规路线2.当地参团3.当地玩家4.游轮5.景点
	
	private String name;
	private String img;
	private Integer lookNum;		// 查看数量
	private Integer collectionNum;		// 收藏数量
	private Integer commentNum;		// 评论数量
	private Integer finishNum;		// 成交数量
	private String star;		// 平均星级
	private BigDecimal price;
	private String tagContent;
	
	private Integer collectionid;
	private Integer routeid;		// 路线/参团id
	private Integer linerid;		// 游轮路线id
	private Integer guideid;		// 导游id
	private Integer scenicid;		// 景点id

	private String introduction;   //导游简介
	private String cityName;   //城市名称
	private String countryName; //国家名称
	
	public ProductCollection() {
		super();
		this.setIdType(IDTYPE_AUTO);
	}

	public ProductCollection(String id){
		super(id);
	}

	@ExcelField(title="1.常规路线2.当地参团3.当地玩家4.游轮5.景点", align=2, sort=1)
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
	@ExcelField(title="收藏产品id", align=2, sort=2)
	public Integer getTypeid() {
		return typeid;
	}

	public void setTypeid(Integer typeid) {
		this.typeid = typeid;
	}
	
	@ExcelField(title="用户id", align=2, sort=3)
	public Integer getMemberid() {
		return memberid;
	}

	public void setMemberid(Integer memberid) {
		this.memberid = memberid;
	}
	
	@ExcelField(title="语言id", align=2, sort=4)
	public Integer getLanguageid() {
		return languageid;
	}

	public void setLanguageid(Integer languageid) {
		this.languageid = languageid;
	}

	public Integer getCollectionType() {
		return collectionType;
	}

	public void setCollectionType(Integer collectionType) {
		this.collectionType = collectionType;
	}

	public String getName() {
		return name;
	}

	public String getImg() {
		return img;
	}

	public Integer getLookNum() {
		return lookNum;
	}

	public Integer getCollectionNum() {
		return collectionNum;
	}

	public Integer getCommentNum() {
		return commentNum;
	}

	public Integer getFinishNum() {
		return finishNum;
	}

	public String getStar() {
		return star;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public String getTagContent() {
		return tagContent;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public void setLookNum(Integer lookNum) {
		this.lookNum = lookNum;
	}

	public void setCollectionNum(Integer collectionNum) {
		this.collectionNum = collectionNum;
	}

	public void setCommentNum(Integer commentNum) {
		this.commentNum = commentNum;
	}

	public void setFinishNum(Integer finishNum) {
		this.finishNum = finishNum;
	}

	public void setStar(String star) {
		this.star = star;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public void setTagContent(String tagContent) {
		this.tagContent = tagContent;
	}

	public Integer getRouteid() {
		return routeid;
	}

	public Integer getLinerid() {
		return linerid;
	}

	public Integer getGuideid() {
		return guideid;
	}

	public Integer getScenicid() {
		return scenicid;
	}

	public void setRouteid(Integer routeid) {
		this.routeid = routeid;
	}

	public void setLinerid(Integer linerid) {
		this.linerid = linerid;
	}

	public void setGuideid(Integer guideid) {
		this.guideid = guideid;
	}

	public void setScenicid(Integer scenicid) {
		this.scenicid = scenicid;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public Integer getCollectionid() {
		return collectionid;
	}

	public void setCollectionid(Integer collectionid) {
		this.collectionid = collectionid;
	}

	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
}