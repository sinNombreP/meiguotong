/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.entity.citystrategy;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeeplus.modules.meiguotong.entity.citystrategyson.CityStrategySon;
import com.jeeplus.modules.sys.entity.User;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 城市攻略表Entity
 * @author cdq
 * @version 2018-08-01
 */
public class CityStrategy extends DataEntity<CityStrategy> {
	
	private static final long serialVersionUID = 1L;
	private String title;		// 标题
	private String content;		// 内容
	private String imgUrl;		// 攻略封面
	private Integer memberId;		// 发布人id  （发布人账号  发布人昵称 关联获取）
	private Integer status;		// 状态 0 禁用 1启用
	private Integer isEssence;		// 是否为精华  0 不是 1 是
	private Integer languageId;		// 语言id
	private Date delDate;		// del_date
	private User delBy;		// del_by
	private Integer cityId;		// 城市主键id
	private String secnicInfo;//景点
	private String nickName;  //昵称
	private String photo;   //会员头像
	private String mobile;  //手机号
	private String ids;   //模块选择的ID
	private Integer digNum;		// 点赞数
	private Integer commentNum;		// 评论数
	private String star;		// 星级
	private Integer digFlag;		// 是否点赞1.已点赞2.未点赞
	
	private List<CityStrategySon> cityStrategySonList;
	
	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public CityStrategy() {
		super();
		this.setIdType(IDTYPE_AUTO);
	}

	public CityStrategy(String id){
		super(id);
	}

	@ExcelField(title="标题", align=2, sort=1)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getSecnicInfo() {
		return secnicInfo;
	}

	public void setSecnicInfo(String secnicInfo) {
		this.secnicInfo = secnicInfo;
	}

	@ExcelField(title="内容", align=2, sort=2)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@ExcelField(title="攻略封面", align=2, sort=3)
	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	
	@ExcelField(title="发布人id  （发布人账号  发布人昵称 关联获取）", align=2, sort=4)
	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}
	
	@ExcelField(title="状态 0 禁用 1启用", align=2, sort=5)
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@ExcelField(title="是否为精华  0 不是 1 是", align=2, sort=6)
	public Integer getIsEssence() {
		return isEssence;
	}

	public void setIsEssence(Integer isEssence) {
		this.isEssence = isEssence;
	}
	
	@ExcelField(title="语言id", align=2, sort=7)
	public Integer getLanguageId() {
		return languageId;
	}

	public void setLanguageId(Integer languageId) {
		this.languageId = languageId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="del_date", align=2, sort=12)
	public Date getDelDate() {
		return delDate;
	}

	public void setDelDate(Date delDate) {
		this.delDate = delDate;
	}
	
	@ExcelField(title="del_by", align=2, sort=13)
	public User getDelBy() {
		return delBy;
	}

	public void setDelBy(User delBy) {
		this.delBy = delBy;
	}
	
	@ExcelField(title="城市主键id", align=2, sort=15)
	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	public List<CityStrategySon> getCityStrategySonList() {
		return cityStrategySonList;
	}

	public void setCityStrategySonList(List<CityStrategySon> cityStrategySonList) {
		this.cityStrategySonList = cityStrategySonList;
	}

	public Integer getDigNum() {
		return digNum;
	}

	public Integer getCommentNum() {
		return commentNum;
	}

	public Integer getDigFlag() {
		return digFlag;
	}

	public void setDigNum(Integer digNum) {
		this.digNum = digNum;
	}

	public void setCommentNum(Integer commentNum) {
		this.commentNum = commentNum;
	}

	public void setDigFlag(Integer digFlag) {
		this.digFlag = digFlag;
	}

	public String getStar() {
		return star;
	}

	public void setStar(String star) {
		this.star = star;
	}
	
	
}