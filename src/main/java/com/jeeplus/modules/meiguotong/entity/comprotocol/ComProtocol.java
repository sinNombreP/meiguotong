/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.entity.comprotocol;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 设置管理Entity
 * @author cdq
 * @version 2018-07-30
 */
public class ComProtocol extends DataEntity<ComProtocol> {
	
	private static final long serialVersionUID = 1L;
	private String pcLogo; //'logo'
	private String logoLink; //'logo链接地址'
	private String img; //'服务热线图片'
	private String phone; //'客服电话'
	private String defaultImg; //'网站默认图片'
	private String defaultPhoto; //'默认头像'
	private String footContent; //'底部文字'
	private String title; //'标题'
	private Integer languageid; //'语言'
	private String delDate;		// 删除时间
	private String delBy;		// 删除人
	
	public ComProtocol() {
		super();
		this.setIdType(IDTYPE_AUTO);
	}

	public ComProtocol(String id){
		super(id);
	}

	@ExcelField(title="删除时间", align=2, sort=8)
	public String getDelDate() {
		return delDate;
	}

	public void setDelDate(String delDate) {
		this.delDate = delDate;
	}
	
	@ExcelField(title="删除人", align=2, sort=9)
	public String getDelBy() {
		return delBy;
	}

	public void setDelBy(String delBy) {
		this.delBy = delBy;
	}
	
	@ExcelField(title="标题中文", align=2, sort=10)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@ExcelField(title="img", align=2, sort=11)
	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}
	
	
	@ExcelField(title="客服电话", align=2, sort=16)
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPcLogo() {
		return pcLogo;
	}

	public String getLogoLink() {
		return logoLink;
	}

	public String getDefaultImg() {
		return defaultImg;
	}

	public String getDefaultPhoto() {
		return defaultPhoto;
	}

	public String getFootContent() {
		return footContent;
	}

	public void setPcLogo(String pcLogo) {
		this.pcLogo = pcLogo;
	}

	public void setLogoLink(String logoLink) {
		this.logoLink = logoLink;
	}

	public void setDefaultImg(String defaultImg) {
		this.defaultImg = defaultImg;
	}

	public void setDefaultPhoto(String defaultPhoto) {
		this.defaultPhoto = defaultPhoto;
	}

	public void setFootContent(String footContent) {
		this.footContent = footContent;
	}

	public Integer getLanguageid() {
		return languageid;
	}

	public void setLanguageid(Integer languageid) {
		this.languageid = languageid;
	}
	
}