/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.entity.comPoster;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 广告管理Entity
 * @author laiyanxin
 * @version 2018-03-06
 */
public class ComPoster extends DataEntity<ComPoster> {
	
	private static final long serialVersionUID = 1L;
	private String title;		// 标题
	private String link;		// 链接
	private Integer positionType;		// 位置属性：1.首页  2.其他根据项目所需要的枚举而定
	private Integer type;		// 类型ID1.链接 2.富文本3.其他类型ID
	private Integer typeid;		// 对应的typeID
	private String content;		// 富文本
	private Integer carousel;		// 轮播
	private String img;		// 图片
	private Date delDate;		// 删除日期
	private String delBy;		// 删除人
	private Integer isAuto;		// 是否自动播放 1.不自动播放  2.自动播放
	private Integer time;		// 自动播放切换时间  默认为零
	private Date beginCreateDate;		// 开始 创建日期
	private Date endCreateDate;		// 结束 创建日期
	
	public ComPoster() {
		super();
		this.setIdType(IDTYPE_AUTO);
	}

	public ComPoster(String id){
		super(id);
	}

	@ExcelField(title="标题", align=2, sort=1)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@ExcelField(title="链接", align=2, sort=2)
	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}
	
	@ExcelField(title="位置属性：1.首页  2.其他根据项目所需要的枚举而定", align=2, sort=3)
	public Integer getPositionType() {
		return positionType;
	}

	public void setPositionType(Integer positionType) {
		this.positionType = positionType;
	}
	
	@ExcelField(title="类型ID1.链接 2.富文本3.其他类型ID", align=2, sort=4)
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
	@ExcelField(title="对应的typeID", align=2, sort=5)
	public Integer getTypeid() {
		return typeid;
	}

	public void setTypeid(Integer typeid) {
		this.typeid = typeid;
	}
	
	@ExcelField(title="富文本", align=2, sort=6)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@ExcelField(title="轮播", align=2, sort=7)
	public Integer getCarousel() {
		return carousel;
	}

	public void setCarousel(Integer carousel) {
		this.carousel = carousel;
	}
	
	@ExcelField(title="图片", align=2, sort=8)
	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="删除日期", align=2, sort=14)
	public Date getDelDate() {
		return delDate;
	}

	public void setDelDate(Date delDate) {
		this.delDate = delDate;
	}
	
	@ExcelField(title="删除人", align=2, sort=15)
	public String getDelBy() {
		return delBy;
	}

	public void setDelBy(String delBy) {
		this.delBy = delBy;
	}
	
	@ExcelField(title="是否自动播放 1.不自动播放  2.自动播放", align=2, sort=16)
	public Integer getIsAuto() {
		return isAuto;
	}

	public void setIsAuto(Integer isAuto) {
		this.isAuto = isAuto;
	}
	
	@ExcelField(title="自动播放切换时间  默认为零", align=2, sort=17)
	public Integer getTime() {
		return time;
	}

	public void setTime(Integer time) {
		this.time = time;
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