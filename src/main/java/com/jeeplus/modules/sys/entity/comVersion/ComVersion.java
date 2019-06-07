/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.entity.comVersion;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 版本管理Entity
 * @author laiyanxin
 * @version 2018-03-05
 */
public class ComVersion extends DataEntity<ComVersion> {
	
	private static final long serialVersionUID = 1L;
	private Integer terminal;		// 终端：1-android  ，2-ios
	private String version;		// 版本号
	private String downloadurl;		// 下载地址
	private String remark;		// 升级说明和备注
	private Integer froce;		// 是否强制升级：1.否 2.是
	private Date lastupdateDate;		// 上次更新时间
	
	public ComVersion() {
		super();
		this.setIdType(IDTYPE_AUTO);
	}

	public ComVersion(String id){
		super(id);
	}

	@ExcelField(title="终端：1-android  ，2-ios", align=2, sort=1)
	public Integer getTerminal() {
		return terminal;
	}

	public void setTerminal(Integer terminal) {
		this.terminal = terminal;
	}
	
	@ExcelField(title="版本号", align=2, sort=2)
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
	
	@ExcelField(title="下载地址", align=2, sort=3)
	public String getDownloadurl() {
		return downloadurl;
	}

	public void setDownloadurl(String downloadurl) {
		this.downloadurl = downloadurl;
	}
	
	@ExcelField(title="升级说明和备注", align=2, sort=4)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@ExcelField(title="是否强制升级：1.否 2.是", align=2, sort=5)
	public Integer getFroce() {
		return froce;
	}

	public void setFroce(Integer froce) {
		this.froce = froce;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="上次更新时间", align=2, sort=8)
	public Date getLastupdateDate() {
		return lastupdateDate;
	}

	public void setLastupdateDate(Date lastupdateDate) {
		this.lastupdateDate = lastupdateDate;
	}
	
}