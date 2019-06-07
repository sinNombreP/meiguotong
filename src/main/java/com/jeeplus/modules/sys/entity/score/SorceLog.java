/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.entity.score;

import java.util.Date;

import javax.validation.constraints.NotNull;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 积分管理Entity
 * @author psz
 * @version 2018-03-07
 */
public class SorceLog extends DataEntity<SorceLog> {
	
	private static final long serialVersionUID = 1L;
	private Integer memberId;		// 会员ID
	private Integer type;		// 获取和消费积分的方式,实际根据当时type值而定
	private Integer way;		// 1.收入  2.支出
	private Integer score;		// 积分
	private Date  beginCreateDate;		//开始时间
	private Date  endCreateDate;		//结束时间
	
	private Integer scoreType;
	private Integer status;
	
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

	public SorceLog() {
		super();
		this.setIdType(IDTYPE_AUTO);
	}

	public SorceLog(String id){
		super(id);
	}

	@NotNull(message="会员ID不能为空")
	@ExcelField(title="会员ID", align=2, sort=1)
	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}
	
	@NotNull(message="获取和消费积分的方式,实际根据当时type值而定不能为空")
	@ExcelField(title="获取和消费积分的方式,实际根据当时type值而定", dictType="", align=2, sort=2)
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	

	@ExcelField(title="1.收入  2.支出", align=2, sort=3)
	public Integer getWay() {
		return way;
	}

	public void setWay(Integer way) {
		this.way = way;
	}
	
	@NotNull(message="积分不能为空")
	@ExcelField(title="积分", align=2, sort=4)
	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public Integer getScoreType() {
		return scoreType;
	}

	public void setScoreType(Integer scoreType) {
		this.scoreType = scoreType;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
}