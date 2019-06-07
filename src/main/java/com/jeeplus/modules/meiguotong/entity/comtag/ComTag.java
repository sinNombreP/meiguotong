/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.entity.comtag;


import java.util.List;

import com.jeeplus.common.utils.excel.annotation.ExcelField;
import com.jeeplus.core.persistence.DataEntity;

/**
 * 各项管理Entity
 * @author cdq
 * @version 2018-07-31
 */
public class ComTag extends DataEntity<ComTag> {
	
	private static final long serialVersionUID = 1L;
	private String tagNum;		// 标签编号
	private Integer type;		// 1  常规线路  2 当地参团  3 邮轮  4 景点 5 当地玩家  6 商务定制
	private Integer fatherId;		// 父标签id
	private Integer status;		// 状态 1 启用2 禁用
	private Integer languageId;		// 语言id
	private String delDate;		// 删除时间
	private String delBy;		// 删除人
	private String fatherContent; 
	private String content;   //内容

	private Integer tagid;		// 属性id
	
	private String labelAttrid;  //选择的标签ID
	private Integer checkFlag;  //判断是否选中
	private List<ComTag> comTagList ;//常规路线集合数据
	private List<ComTag> comTagLocalList;//当地参团集合数据
	private List<ComTag> comTagCruiseList;//邮轮集合数据
   private List<ComTag> comTagAttractionsList;//景点集合数据
   
	public List<ComTag> getComTagAttractionsList() {
	return comTagAttractionsList;
}

public void setComTagAttractionsList(List<ComTag> comTagAttractionsList) {
	this.comTagAttractionsList = comTagAttractionsList;
}

	public List<ComTag> getComTagCruiseList() {
		return comTagCruiseList;
	}

	public void setComTagCruiseList(List<ComTag> comTagCruiseList) {
		this.comTagCruiseList = comTagCruiseList;
	}

	public List<ComTag> getComTagLocalList() {
		return comTagLocalList;
	}

	public void setComTagLocalList(List<ComTag> comTagLocalList) {
		this.comTagLocalList = comTagLocalList;
	}

	public List<ComTag> getComTagList() {
		return comTagList;
	}

	public void setComTagList(List<ComTag> comTagList) {
		this.comTagList = comTagList;
	}

	public ComTag() {
		super();
		this.setIdType(IDTYPE_AUTO);
	}

	public Integer getCheckFlag() {
		return checkFlag;
	}

	public void setCheckFlag(Integer checkFlag) {
		this.checkFlag = checkFlag;
	}

	public String getLabelAttrid() {
		return labelAttrid;
	}

	public void setLabelAttrid(String labelAttrid) {
		this.labelAttrid = labelAttrid;
	}

	public ComTag(String id){
		super(id);
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@ExcelField(title="标签编号", align=2, sort=1)
	public String getTagNum() {
		return tagNum;
	}

	public void setTagNum(String tagNum) {
		this.tagNum = tagNum;
	}
	
	@ExcelField(title="1  常规线路  2 当地参团  3 邮轮  4 景点 5 当地玩家  6 商务定制", align=2, sort=2)
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
	@ExcelField(title="父标签id", align=2, sort=3)
	public Integer getFatherId() {
		return fatherId;
	}

	public void setFatherId(Integer fatherId) {
		this.fatherId = fatherId;
	}
	
	@ExcelField(title="状态 0 禁用 1 启用", align=2, sort=4)
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@ExcelField(title="语言id", align=2, sort=5)
	public Integer getLanguageId() {
		return languageId;
	}

	public void setLanguageId(Integer languageId) {
		this.languageId = languageId;
	}
	
	@ExcelField(title="删除时间", align=2, sort=11)
	public String getDelDate() {
		return delDate;
	}

	public void setDelDate(String delDate) {
		this.delDate = delDate;
	}
	
	@ExcelField(title="删除人", align=2, sort=12)
	public String getDelBy() {
		return delBy;
	}

	public void setDelBy(String delBy) {
		this.delBy = delBy;
	}

	public Integer getTagid() {
		return tagid;
	}

	public void setTagid(Integer tagid) {
		this.tagid = tagid;
	}

	public String getFatherContent() {
		return fatherContent;
	}

	public void setFatherContent(String fatherContent) {
		this.fatherContent = fatherContent;
	}
	
}