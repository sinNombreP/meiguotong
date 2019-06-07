/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.entity.hotelroomdevice;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 酒店管理Entity
 * @author cdq
 * @version 2018-08-27
 */
public class HotelRoomDevice extends DataEntity<HotelRoomDevice> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 设施名称
	private String icon;		// 设施图标
	private Integer status;		// 状态  1 启用2 禁用
	private String languageId;		// 语言id
	private String delDate;		// 删除时间
	private String delBy;		// 删除人
	
	private Integer ifTrue; //判断酒店是否存在该设施1.是    2.否
	private String ids; //酒店选择的设施ID
	private Integer idFlag; //判断是否选中该设施
	
	public HotelRoomDevice() {
		super();
		this.setIdType(IDTYPE_AUTO);
	}

	public Integer getIdFlag() {
		return idFlag;
	}

	public void setIdFlag(Integer idFlag) {
		this.idFlag = idFlag;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public HotelRoomDevice(String id){
		super(id);
	}

	@ExcelField(title="设施名称", align=2, sort=1)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ExcelField(title="设施图标", align=2, sort=2)
	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	@ExcelField(title="状态 0 禁用 1 启用", align=2, sort=3)
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@ExcelField(title="语言id", align=2, sort=4)
	public String getLanguageId() {
		return languageId;
	}

	public void setLanguageId(String languageId) {
		this.languageId = languageId;
	}
	
	@ExcelField(title="删除时间", align=2, sort=10)
	public String getDelDate() {
		return delDate;
	}

	public void setDelDate(String delDate) {
		this.delDate = delDate;
	}
	
	@ExcelField(title="删除人", align=2, sort=11)
	public String getDelBy() {
		return delBy;
	}

	public void setDelBy(String delBy) {
		this.delBy = delBy;
	}

	public Integer getIfTrue() {
		return ifTrue;
	}

	public void setIfTrue(Integer ifTrue) {
		this.ifTrue = ifTrue;
	}
	
}