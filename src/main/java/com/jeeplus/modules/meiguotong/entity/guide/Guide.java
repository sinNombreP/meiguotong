/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.entity.guide;


import com.jeeplus.common.utils.excel.annotation.ExcelField;
import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.modules.meiguotong.entity.insurance.Insurance;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 导游表Entity
 * @author cdq
 * @version 2018-08-20
 */
public class Guide extends DataEntity<Guide> {
	
	private static final long serialVersionUID = 1L;
	private String realName;		// 正实姓名
	private String address;		// 地址
	private Integer status;		// 状态 1显示 2禁用
	private String introduction;		// 简介
	private String deltails;		// 详情
	private Integer type;		// 审核状态  1待审核2 通过3拒绝
	private Integer codeType;		// 证件类型 
	private String code;		// 证件对应的号码
	private String photoUrl;		// 图片 可以上传多张图
	private String img;		// 介绍图片
	private String memberid;
	private BigDecimal price;//价格
	private String  goodAt;//擅长
	private String tagId;//对应的标签表Id
	private Integer status1;//审核状态  0审核中 1已通过 2已拒绝
	private String memberPhoto; //会员头像

	private List<String> tagIds;//对应的标签表Id
	private Integer lookNum;		// 查看数量
	private Integer collectionNum;		// 收藏数量
	private Integer commentNum;		// 评论数量
	private Integer finishNum;		// 成交数量
	private String star;		// 平均星级
	private String ifcollection;		//是否收藏  1已收藏  2未收藏
	
	private Integer guideid; //导游id
	private Integer countryid;//国家id
	private Integer cityid;  //城市id
	private String cityName;  //城市名称
	private String tagContent;  //属性名称
	private Date birthday;  //生日
	private String countryName; //国家名称
	
	private String mobile;//账号
	private String nickName;//昵称
	private String lastLoginDate;//最后登陆时间
	private String photo;//会员头像
	private String email;//邮箱
	private BigDecimal discount;;//抽成比例
	private  String  sex;//性别
	private String content; 
	private  Integer age;//年龄
	private Date date;		// 日期，搜索接口用
	private String guideType;//搜索接口用
	private String guideAge;//搜索接口用
	private String guideSex;//搜索接口用
	private String minPrice;//搜索接口用
	private String maxPrice;//搜索接口用
	private String ids;   //模块选择的ID
	private String sexName;//性别名称
	private String guideids;//导游ID集合
	private Integer cityId; //城市ID
	
	private List<Insurance> insurances;
	
	
	private List<String> photoUrlList;

	public String getMemberPhoto() {
		return memberPhoto;
	}

	public void setMemberPhoto(String memberPhoto) {
		this.memberPhoto = memberPhoto;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}
	
	public String getIfcollection() {
		return ifcollection;
	}

	public void setIfcollection(String ifcollection) {
		this.ifcollection = ifcollection;
	}

	public Integer getStatus1() {
		return status1;
	}

	public void setStatus1(Integer status1) {
		this.status1 = status1;
	}

	public String getTagId() {
		return tagId;
	}

	public void setTagId(String tagId) {
		this.tagId = tagId;
	}


	public String getGoodAt() {
		return goodAt;
	}

	public void setGoodAt(String goodAt) {
		this.goodAt = goodAt;
	}
	
   	
	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public BigDecimal getDiscount() {
		return discount;
	}

	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Guide() {
		super();
		this.setIdType(IDTYPE_AUTO);
	}

	public Guide(String id){
		super(id);
	}

	@ExcelField(title="正实姓名", align=2, sort=1)
	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}
	


	public String getMemberid() {
		return memberid;
	}

	public void setMemberid(String memberid) {
		this.memberid = memberid;
	}

	
	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(String lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	
	
	@ExcelField(title="地址", align=2, sort=5)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	
	@ExcelField(title="状态 0显示 1禁用", align=2, sort=7)
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@ExcelField(title="简介", align=2, sort=8)
	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	
	@ExcelField(title="详情", align=2, sort=9)
	public String getDeltails() {
		return deltails;
	}

	public void setDeltails(String deltails) {
		this.deltails = deltails;
	}
	
	@ExcelField(title="审核状态  1待审核2 通过3拒绝", align=2, sort=10)
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
	@ExcelField(title="证件类型 ", align=2, sort=11)
	public Integer getCodeType() {
		return codeType;
	}

	public void setCodeType(Integer codeType) {
		this.codeType = codeType;
	}
	
	@ExcelField(title="证件对应的号码", align=2, sort=12)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@ExcelField(title="图片 可以上传多张图", align=2, sort=13)
	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	public Integer getGuideid() {
		return guideid;
	}

	public void setGuideid(Integer guideid) {
		this.guideid = guideid;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Integer getCountryid() {
		return countryid;
	}

	public Integer getCityid() {
		return cityid;
	}

	public void setCountryid(Integer countryid) {
		this.countryid = countryid;
	}

	public void setCityid(Integer cityid) {
		this.cityid = cityid;
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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getGuideType() {
		return guideType;
	}

	public String getGuideAge() {
		return guideAge;
	}

	public String getGuideSex() {
		return guideSex;
	}

	public String getMinPrice() {
		return minPrice;
	}

	public String getMaxPrice() {
		return maxPrice;
	}


	public void setGuideType(String guideType) {
		this.guideType = guideType;
	}

	public void setGuideAge(String guideAge) {
		this.guideAge = guideAge;
	}

	public void setGuideSex(String guideSex) {
		this.guideSex = guideSex;
	}

	public void setMinPrice(String minPrice) {
		this.minPrice = minPrice;
	}

	public void setMaxPrice(String maxPrice) {
		this.maxPrice = maxPrice;
	}

	public List<String> getTagIds() {
		return tagIds;
	}

	public void setTagIds(List<String> tagIds) {
		this.tagIds = tagIds;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getTagContent() {
		return tagContent;
	}

	public void setTagContent(String tagContent) {
		this.tagContent = tagContent;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getSexName() {
		return sexName;
	}

	public void setSexName(String sexName) {
		this.sexName = sexName;
	}

	public String getGuideids() {
		return guideids;
	}

	public void setGuideids(String guideids) {
		this.guideids = guideids;
	}

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	public List<String> getPhotoUrlList() {
		return photoUrlList;
	}

	public void setPhotoUrlList(List<String> photoUrlList) {
		this.photoUrlList = photoUrlList;
	}

	public List<Insurance> getInsurances() {
		return insurances;
	}

	public void setInsurances(List<Insurance> insurances) {
		this.insurances = insurances;
	}
	
	

	
}