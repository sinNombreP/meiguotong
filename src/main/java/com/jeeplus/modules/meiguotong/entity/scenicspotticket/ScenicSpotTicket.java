/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.entity.scenicspotticket;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.modules.meiguotong.entity.insurance.Insurance;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 景点门票表Entity
 * @author cdq
 * @version 2018-08-16
 */
public class ScenicSpotTicket extends DataEntity<ScenicSpotTicket> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 门票名称
	private Integer rule;		// 预定规则
	private BigDecimal price;		// 价格
	private BigDecimal marketPrice;
	private Date validityDate;		// 有效期
	private Integer status;		// 状态   1 启用，2 禁用
	private Date delDate;		// del_date
	private String delBy;		// del_by
	private Integer languageId;		// 语言id
	private Integer scenicid;		// 景点ID
	private String sceniciCompany;		// 景点供应商
	private String imgUrl;  //景点门票
	private Integer ifCustom; //是否定制门票1.否2.是

	private Date date;
	private String ids;   //模块选择的ID

	private Integer loginAgentid; //登陆的用户agentid
	
	private Integer scenicSpotId ;//景点ID
	private String sceniceName;//景点名称
	private String address;//地址
	private String content;//特色
	
	private Integer cityid;//城市id
	private String cityName;//城市名称
	
	private List<Insurance> insurances;
	
	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}
	public ScenicSpotTicket() {
		super();
		this.setIdType(IDTYPE_AUTO);
	}
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public ScenicSpotTicket(String id){
		super(id);
	}

	@ExcelField(title="门票名称", align=2, sort=1)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ExcelField(title="预定规则", align=2, sort=2)
	public Integer getRule() {
		return rule;
	}

	public void setRule(Integer rule) {
		this.rule = rule;
	}
	

	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="有效期", align=2, sort=4)
	public Date getValidityDate() {
		return validityDate;
	}

	public void setValidityDate(Date validityDate) {
		this.validityDate = validityDate;
	}
	
	@ExcelField(title="状态  0 禁用 1 启用", align=2, sort=5)
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="del_date", align=2, sort=10)
	public Date getDelDate() {
		return delDate;
	}

	public void setDelDate(Date delDate) {
		this.delDate = delDate;
	}
	
	@ExcelField(title="del_by", align=2, sort=11)
	public String getDelBy() {
		return delBy;
	}

	public void setDelBy(String delBy) {
		this.delBy = delBy;
	}
	
	@ExcelField(title="语言id", align=2, sort=13)
	public Integer getLanguageId() {
		return languageId;
	}

	public void setLanguageId(Integer languageId) {
		this.languageId = languageId;
	}
	
	@ExcelField(title="景点ID", align=2, sort=14)
	public Integer getScenicid() {
		return scenicid;
	}

	public void setScenicid(Integer scenicid) {
		this.scenicid = scenicid;
	}
	
	@ExcelField(title="景点供应商", align=2, sort=15)
	public String getSceniciCompany() {
		return sceniciCompany;
	}

	public void setSceniciCompany(String sceniciCompany) {
		this.sceniciCompany = sceniciCompany;
	}

	
	public Integer getLoginAgentid() {
		return loginAgentid;
	}

	public void setLoginAgentid(Integer loginAgentid) {
		this.loginAgentid = loginAgentid;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public BigDecimal getMarketPrice() {
		return marketPrice;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public void setMarketPrice(BigDecimal marketPrice) {
		this.marketPrice = marketPrice;
	}

	public Integer getIfCustom() {
		return ifCustom;
	}

	public void setIfCustom(Integer ifCustom) {
		this.ifCustom = ifCustom;
	}

	public Integer getScenicSpotId() {
		return scenicSpotId;
	}

	public void setScenicSpotId(Integer scenicSpotId) {
		this.scenicSpotId = scenicSpotId;
	}

	public String getSceniceName() {
		return sceniceName;
	}

	public void setSceniceName(String sceniceName) {
		this.sceniceName = sceniceName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public List<Insurance> getInsurances() {
		return insurances;
	}

	public void setInsurances(List<Insurance> insurances) {
		this.insurances = insurances;
	}

	public Integer getCityid() {
		return cityid;
	}

	public void setCityid(Integer cityid) {
		this.cityid = cityid;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	
}