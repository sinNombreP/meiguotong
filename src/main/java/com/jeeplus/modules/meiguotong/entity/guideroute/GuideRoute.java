/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.entity.guideroute;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeeplus.common.utils.excel.annotation.ExcelField;
import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.modules.meiguotong.entity.insurance.Insurance;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 导游路线表Entity
 * @author cdq
 * @version 2018-09-04
 */
public class GuideRoute extends DataEntity<GuideRoute> {
	
	private static final long serialVersionUID = 1L;
	private Integer guideid;		// guideid
	private Integer memberid;		// memberid
	private String title;		// 标题
	private String photo;		// 图片
	private String scenice;		// 景点
	private String details;		// 详情
	private Integer miniNum;		// 最低人数
	private Integer transactionsNum;		// 成交数量
	private BigDecimal price;		// 价格/天
	private Integer day;		// 行程天数

	private String city;		// 城市
	private Integer cityid;		// 城市id
	private Integer guideRouteid;
	
	private Integer dateType;		// 日期类型1.所有日期2.按星期3.按号数
	private Date beginDate;		// 选择所有日期的开始时间
	private Date endDate;		// 选择所有日期的结束时间
	private String weekDate;		// 选择的星期
	private String dayDate;		// 选择的号数
	private String sceniceName; //景点名称，多个
	private Integer languageid;		// 语言id
	private String ids;   //模块选择的ID
	
	private String tagContent;  //属性名称
	private Integer age;  //年龄
	private String countryName; //国家名称
	private String nickName;//昵称
	private String introduction;//导游简介
	private Integer sex;//性别1男2女
	private List<Insurance> insurances;
	private String realName;//真实姓名
	
	private String cityName;//城市名称
	
	
	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}
	
	public GuideRoute() {
		super();
		this.setIdType(IDTYPE_AUTO);
	}

	public GuideRoute(String id){
		super(id);
	}

	@ExcelField(title="guideid", align=2, sort=1)
	public Integer getGuideid() {
		return guideid;
	}

	public void setGuideid(Integer guideid) {
		this.guideid = guideid;
	}
	
	@ExcelField(title="memberid", align=2, sort=2)
	public Integer getMemberid() {
		return memberid;
	}

	public void setMemberid(Integer memberid) {
		this.memberid = memberid;
	}
	
	@ExcelField(title="标题", align=2, sort=3)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@ExcelField(title="图片", align=2, sort=4)
	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}
	
	@ExcelField(title="景点", align=2, sort=5)
	public String getScenice() {
		return scenice;
	}

	public void setScenice(String scenice) {
		this.scenice = scenice;
	}
	
	@ExcelField(title="详情", align=2, sort=6)
	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}
	
	@ExcelField(title="最低人数", align=2, sort=7)
	public Integer getMiniNum() {
		return miniNum;
	}

	public void setMiniNum(Integer miniNum) {
		this.miniNum = miniNum;
	}
	
	@ExcelField(title="成交数量", align=2, sort=8)
	public Integer getTransactionsNum() {
		return transactionsNum;
	}

	public void setTransactionsNum(Integer transactionsNum) {
		this.transactionsNum = transactionsNum;
	}
	
	@ExcelField(title="价格/天", align=2, sort=9)
	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Integer getGuideRouteid() {
		return guideRouteid;
	}

	public void setGuideRouteid(Integer guideRouteid) {
		this.guideRouteid = guideRouteid;
	}

	public Integer getDateType() {
		return dateType;
	}
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getBeginDate() {
		return beginDate;
	}
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getEndDate() {
		return endDate;
	}

	public String getWeekDate() {
		return weekDate;
	}

	public String getDayDate() {
		return dayDate;
	}

	public void setDateType(Integer dateType) {
		this.dateType = dateType;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public void setWeekDate(String weekDate) {
		this.weekDate = weekDate;
	}

	public void setDayDate(String dayDate) {
		this.dayDate = dayDate;
	}

	public Integer getLanguageid() {
		return languageid;
	}

	public void setLanguageid(Integer languageid) {
		this.languageid = languageid;
	}

	public Integer getCityid() {
		return cityid;
	}

	public void setCityid(Integer cityid) {
		this.cityid = cityid;
	}

	public Integer getDay() {
		return day;
	}

	public void setDay(Integer day) {
		this.day = day;
	}

	public String getSceniceName() {
		return sceniceName;
	}

	public void setSceniceName(String sceniceName) {
		this.sceniceName = sceniceName;
	}

	public String getTagContent() {
		return tagContent;
	}

	public void setTagContent(String tagContent) {
		this.tagContent = tagContent;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public List<Insurance> getInsurances() {
		return insurances;
	}

	public void setInsurances(List<Insurance> insurances) {
		this.insurances = insurances;
	}
	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	
}