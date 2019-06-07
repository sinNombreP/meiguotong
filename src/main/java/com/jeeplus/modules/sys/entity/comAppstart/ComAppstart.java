/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.entity.comAppstart;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * APP启动界面Entity
 * @author laiyanxin
 * @version 2018-03-05
 */
public class ComAppstart extends DataEntity<ComAppstart> {
	
	private static final long serialVersionUID = 1L;
	private String title;		// 标题
	private String img;		// 图像
	private Integer time;		// 启动显示时间。为0则需要点击跳过，默认为零
	private String link;		// 链接
	private String lastupdateDate;		// lastupdate_date
	private Integer status;		// 状态：1.启用 2.禁用
	private Integer type;		// 1.富文本 2.链接，3.其他ID，更多的增加枚举值
	private Integer typeid;		// typeid
	private String content;		// content
	
	public ComAppstart() {
		super();
		this.setIdType(IDTYPE_AUTO);
	}

	public ComAppstart(String id){
		super(id);
	}

	@ExcelField(title="标题", align=2, sort=1)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@ExcelField(title="图像", align=2, sort=2)
	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}
	
	@ExcelField(title="启动显示时间。为0则需要点击跳过，默认为零", align=2, sort=3)
	public Integer getTime() {
		return time;
	}

	public void setTime(Integer time) {
		this.time = time;
	}
	
	@ExcelField(title="链接", align=2, sort=4)
	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}
	
	@ExcelField(title="lastupdate_date", align=2, sort=7)
	public String getLastupdateDate() {
		return lastupdateDate;
	}

	public void setLastupdateDate(String lastupdateDate) {
		this.lastupdateDate = lastupdateDate;
	}
	
	@ExcelField(title="状态：1.启用 2.禁用", align=2, sort=8)
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@ExcelField(title="1.富文本 2.链接，3.其他ID，更多的增加枚举值", align=2, sort=9)
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
	@ExcelField(title="typeid", align=2, sort=10)
	public Integer getTypeid() {
		return typeid;
	}

	public void setTypeid(Integer typeid) {
		this.typeid = typeid;
	}
	
	@ExcelField(title="content", align=2, sort=11)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
}