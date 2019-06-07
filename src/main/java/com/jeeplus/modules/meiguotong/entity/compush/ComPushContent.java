/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.entity.compush;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeeplus.modules.sys.entity.User;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 自动推送模板Entity
 * @author dong
 * @version 2019-03-13
 */
public class ComPushContent extends DataEntity<ComPushContent> {
	
	private static final long serialVersionUID = 1L;
	private Integer type;		// 1.注册成功2.未付款订单3.下单成功4.后台确认5.开始订单6.取消订单7.注册验证码8.忘记密码
	private String content;		// 内容
	private Integer languageid;		// 语言
	private Integer status;		// 状态1.启用2.禁用
	private Date delDate;		// 删除时间
	private User delBy;		// 删除人
	private String statuss; //状态组
	
	private List<ComPushContent> list;
	
	public ComPushContent() {
		super();
		this.setIdType(IDTYPE_AUTO);
	}

	public ComPushContent(String id){
		super(id);
	}

	@ExcelField(title="1.注册成功2.未付款订单3.下单成功4.后台确认5.开始订单6.取消订单7.注册验证码8.忘记密码", align=2, sort=1)
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
	@ExcelField(title="内容", align=2, sort=2)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@ExcelField(title="语言", dictType="", align=2, sort=3)
	public Integer getLanguageid() {
		return languageid;
	}

	public void setLanguageid(Integer languageid) {
		this.languageid = languageid;
	}
	
	@ExcelField(title="状态1.启用2.禁用", align=2, sort=4)
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="删除时间", align=2, sort=10)
	public Date getDelDate() {
		return delDate;
	}

	public void setDelDate(Date delDate) {
		this.delDate = delDate;
	}
	
	@ExcelField(title="删除人", align=2, sort=11)
	public User getDelBy() {
		return delBy;
	}

	public void setDelBy(User delBy) {
		this.delBy = delBy;
	}

	public String getStatuss() {
		return statuss;
	}

	public void setStatuss(String statuss) {
		this.statuss = statuss;
	}

	public List<ComPushContent> getList() {
		return list;
	}

	public void setList(List<ComPushContent> list) {
		this.list = list;
	}
	
	
}