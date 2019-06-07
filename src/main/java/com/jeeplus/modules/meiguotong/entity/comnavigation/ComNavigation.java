/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.entity.comnavigation;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.utils.UserUtils;
import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 主导航Entity
 * @author cdq
 * @version 2018-07-27
 */
public class ComNavigation extends DataEntity<ComNavigation> {
	
	private static final long serialVersionUID = 1L;
	private Date delDate;		// 删除时间
	private User delBy;		// 删除人
	private Integer type;		// 1  主导航链接信息  2 热门城市链接信息
	private Integer sort;		// 排序
	private Integer typeid;		// 根据type不同对应不同id 
	private Integer status;		// 状态1.启用2.禁用
	private String name;		// 根据type不同对应不同的名称
	private String link;		// 外链地址
	private String linkExplain;		// 链接说明
	private Integer languageId;		// 语言id
	private String language; //语言
	
	private Integer cityid; //城市id
	private String cityName; //城市名称
	private String photoUrl; //城市图片
	
	public ComNavigation() {
		super();
		this.setIdType(IDTYPE_AUTO);
	}

	public ComNavigation(String id){
		super(id);
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="删除时间", align=2, sort=6)
	public Date getDelDate() {
		return delDate;
	}

	public void setDelDate(Date delDate) {
		this.delDate = delDate;
	}
	
	@ExcelField(title="删除人", align=2, sort=7)
	public User getDelBy() {
		return delBy;
	}

	public void setDelBy(User delBy) {
		this.delBy = delBy;
	}
	
	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	@ExcelField(title="1  主导航链接信息  2 热门城市链接信息", align=2, sort=8)
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
	@ExcelField(title="排序", align=2, sort=9)
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
	
	@ExcelField(title="根据type不同对应不同的名称", align=2, sort=10)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ExcelField(title="外链地址", align=2, sort=11)
	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}
	
	@ExcelField(title="链接说明", align=2, sort=12)
	public String getLinkExplain() {
		return linkExplain;
	}

	public void setLinkExplain(String linkExplain) {
		this.linkExplain = linkExplain;
	}
	
	@ExcelField(title="语言id", align=2, sort=13)
	public Integer getLanguageId() {
		return languageId;
	}

	public void setLanguageId(Integer languageId) {
		this.languageId = languageId;
	}

	public Integer getTypeid() {
		return typeid;
	}

	public Integer getStatus() {
		return status;
	}

	public void setTypeid(Integer typeid) {
		this.typeid = typeid;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getCityid() {
		return cityid;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityid(Integer cityid) {
		this.cityid = cityid;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}
	/**
	 * 删除之前执行方法，需要手动调用
	 */
	public void preDel(){
		User user = UserUtils.getUser();
		if (StringUtils.isNotBlank(user.getId())){
			this.delBy = user;
		}
		this.delDate = new Date();
	}
}