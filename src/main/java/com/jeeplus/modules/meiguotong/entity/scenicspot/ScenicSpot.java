/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.entity.scenicspot;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeeplus.common.utils.excel.annotation.ExcelField;
import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.modules.meiguotong.entity.settingtitlepro.SettingTitlePro;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 景点表Entity
 * @author cdq
 * @version 2018-08-16
 */
public class ScenicSpot extends DataEntity<ScenicSpot> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 景点名称
	private Date validityDate;		// 有效期
	private Integer status;		// 状态 1审核中2已拒绝3正常4禁用（可以变为启用）5.禁用（后台添加代理商未编辑的情况，不能修改）
	private String imgUrl;		// 景点图片 逗号隔开
	private String address;		// 景点地址
	private Integer supplierId;		// 供应商id
	private Date delDate;		// del_date
	private String delBy;		// del_by
	private Integer languageId;		// 语言id
	private String languageName;		// 语言名称
	private String playerTime;		// 建议游玩时长
	private String season;		// 最佳游玩季节
	private String content;		// 景点详情
	private String recommend;		// 经理推荐
	private String introduce;		// 景点介绍
	private String reserveInfo;		// 预定须知
	private Integer cityId;		// 城市id
	private String sceniciCompany; //景点供应商
	private String labelAttrid;    //属性id多个用，隔开
	private Integer lookNum;		// 查看数量
	private Integer collectionNum;		// 收藏数量
	private Integer commentNum;		// 评论数量
	private Integer finishNum;		// 成交数量
	private String star;		// 平均星级
	private String  tagContent;  //属性名称多个用，隔开
	private String cityName;		// 城市名称
	private String countryName;     //国家名称
	private String countryId;  //国家ID
	private String scenicIds;  //选择的景点Id
	private Integer checkFlag;  //判断是否选择
	private String scenicFlag;  //判断是加载下拉还是模态框
	private String memberid;  //接口用于判断用户是否关注
	private String priceInfor; //价格说明
	private Integer scenicSpotid;
	private Integer orderByType;
	
	private BigDecimal price;  //价格

	private List<String> attrList;// 标签属性
	
	private Integer type;  //1景点 2商务（前端使用）
	private String busiInfo;  //景点名称（前端使用）
	private String ids;   //模块选择的ID
	
	private Integer loginAgentid; //登陆的用户agentid
	private String scenicids;		//景点ID集合
	
	private List<String> imgList;// 图片列表

	private String scenicpotid;  //景点ID（定制传参字段）

	private List<SettingTitlePro> settingTitleProList;

	public String getScenicpotid() {
		return scenicpotid;
	}

	public void setScenicpotid(String scenicpotid) {
		this.scenicpotid = scenicpotid;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getLanguageName() {
		return languageName;
	}

	public void setLanguageName(String languageName) {
		this.languageName = languageName;
	}

	public ScenicSpot() {
		super();
		this.setIdType(IDTYPE_AUTO);
	}

	public String getCountryId() {
		return countryId;
	}

	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getBusiInfo() {
		return busiInfo;
	}

	public void setBusiInfo(String busiInfo) {
		this.busiInfo = busiInfo;
	}

	public String getScenicFlag() {
		return scenicFlag;
	}

	public void setScenicFlag(String scenicFlag) {
		this.scenicFlag = scenicFlag;
	}

	public String getScenicIds() {
		return scenicIds;
	}

	public void setScenicIds(String scenicIds) {
		this.scenicIds = scenicIds;
	}

	public Integer getCheckFlag() {
		return checkFlag;
	}

	public void setCheckFlag(Integer checkFlag) {
		this.checkFlag = checkFlag;
	}

	public ScenicSpot(String id){
		super(id);
	}

	public String getSceniciCompany() {
		return sceniciCompany;
	}

	public void setSceniciCompany(String sceniciCompany) {
		this.sceniciCompany = sceniciCompany;
	}

	@ExcelField(title="景点名称", align=2, sort=1)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="有效期", align=2, sort=2)
	public Date getValidityDate() {
		return validityDate;
	}

	public void setValidityDate(Date validityDate) {
		this.validityDate = validityDate;
	}
	
	@ExcelField(title="状态 0 禁用  1 启用", align=2, sort=3)
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@ExcelField(title="景点图片 逗号隔开", align=2, sort=4)
	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	
	@ExcelField(title="景点地址", align=2, sort=5)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	@ExcelField(title="供应商id", align=2, sort=6)
	public Integer getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="del_date", align=2, sort=11)
	public Date getDelDate() {
		return delDate;
	}

	public void setDelDate(Date delDate) {
		this.delDate = delDate;
	}
	
	@ExcelField(title="del_by", align=2, sort=12)
	public String getDelBy() {
		return delBy;
	}

	public void setDelBy(String delBy) {
		this.delBy = delBy;
	}
	
	@ExcelField(title="语言id", align=2, sort=14)
	public Integer getLanguageId() {
		return languageId;
	}

	public void setLanguageId(Integer languageId) {
		this.languageId = languageId;
	}
	
	@ExcelField(title="建议游玩时长", align=2, sort=15)
	public String getPlayerTime() {
		return playerTime;
	}

	public void setPlayerTime(String playerTime) {
		this.playerTime = playerTime;
	}
	
	@ExcelField(title="最佳游玩季节", align=2, sort=16)
	public String getSeason() {
		return season;
	}

	public void setSeason(String season) {
		this.season = season;
	}
	
	@ExcelField(title="景点详情", align=2, sort=17)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@ExcelField(title="经理推荐", align=2, sort=18)
	public String getRecommend() {
		return recommend;
	}

	public void setRecommend(String recommend) {
		this.recommend = recommend;
	}
	
	@ExcelField(title="景点介绍", align=2, sort=19)
	public String getIntroduce() {
		return introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}
	
	@ExcelField(title="预定须知", align=2, sort=20)
	public String getReserveInfo() {
		return reserveInfo;
	}

	public void setReserveInfo(String reserveInfo) {
		this.reserveInfo = reserveInfo;
	}
	
	@ExcelField(title="城市id", align=2, sort=21)
	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	public Integer getScenicSpotid() {
		return scenicSpotid;
	}

	public void setScenicSpotid(Integer scenicSpotid) {
		this.scenicSpotid = scenicSpotid;
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

	public String getLabelAttrid() {
		return labelAttrid;
	}

	public void setLabelAttrid(String labelAttrid) {
		this.labelAttrid = labelAttrid;
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


	public String getTagContent() {
		return tagContent;
	}

	public void setTagContent(String tagContent) {
		this.tagContent = tagContent;
	}

	public Integer getOrderByType() {
		return orderByType;
	}

	public void setOrderByType(Integer orderByType) {
		this.orderByType = orderByType;
	}

	public String getMemberid() {
		return memberid;
	}

	public void setMemberid(String memberid) {
		this.memberid = memberid;
	}

	public List<String> getAttrList() {
		return attrList;
	}

	public void setAttrList(List<String> attrList) {
		this.attrList = attrList;
	}

	public Integer getLoginAgentid() {
		return loginAgentid;
	}

	public void setLoginAgentid(Integer loginAgentid) {
		this.loginAgentid = loginAgentid;
	}

	public String getScenicids() {
		return scenicids;
	}

	public void setScenicids(String scenicids) {
		this.scenicids = scenicids;
	}

	public List<String> getImgList() {
		return imgList;
	}

	public void setImgList(List<String> imgList) {
		this.imgList = imgList;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public List<SettingTitlePro> getSettingTitleProList() {
		return settingTitleProList;
	}

	public void setSettingTitleProList(List<SettingTitlePro> settingTitleProList) {
		this.settingTitleProList = settingTitleProList;
	}

	public String getPriceInfor() {
		return priceInfor;
	}

	public void setPriceInfor(String priceInfor) {
		this.priceInfor = priceInfor;
	}
	
	
}