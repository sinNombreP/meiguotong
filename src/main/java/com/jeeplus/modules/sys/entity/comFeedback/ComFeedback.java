/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.entity.comFeedback;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 意见反馈Entity
 * @author laiyanxin
 * @version 2018-03-05
 */
public class ComFeedback extends DataEntity<ComFeedback> {
	
	private static final long serialVersionUID = 1L;
	private Integer memberid;		// 会员ID
	private String membername;		// 会员昵称
	private String mobile;		// 联系电话
	private String email;		// 邮箱
	private String content;		// 反馈内容
	private Integer isDeal;		// 是否处理：0为未处理 1.已处理
	private String remark;		// 回复/备注
	private Date dealDate;		// 处理时间
	private String dealBy;		// 处理人
	private Date delDate;		// 删除时间
	private String delBy;		// 删除人
	private Integer isWay;		// 1.会员 2.游客
	
	public ComFeedback() {
		super();
		this.setIdType(IDTYPE_AUTO);
	}

	public ComFeedback(String id){
		super(id);
	}

	@ExcelField(title="会员ID", align=2, sort=1)
	public Integer getMemberid() {
		return memberid;
	}

	public void setMemberid(Integer memberid) {
		this.memberid = memberid;
	}
	
	@ExcelField(title="会员昵称", align=2, sort=2)
	public String getMembername() {
		return membername;
	}

	public void setMembername(String membername) {
		this.membername = membername;
	}
	
	@ExcelField(title="联系电话", align=2, sort=3)
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	@ExcelField(title="邮箱", align=2, sort=4)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	@ExcelField(title="反馈内容", align=2, sort=5)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@ExcelField(title="是否处理：0为未处理 1.已处理", align=2, sort=6)
	public Integer getIsDeal() {
		return isDeal;
	}

	public void setIsDeal(Integer isDeal) {
		this.isDeal = isDeal;
	}
	
	@ExcelField(title="回复/备注", align=2, sort=7)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="处理时间", align=2, sort=9)
	public Date getDealDate() {
		return dealDate;
	}

	public void setDealDate(Date dealDate) {
		this.dealDate = dealDate;
	}
	
	@ExcelField(title="处理人", align=2, sort=10)
	public String getDealBy() {
		return dealBy;
	}

	public void setDealBy(String dealBy) {
		this.dealBy = dealBy;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="删除时间", align=2, sort=11)
	public Date getDelDate() {
		return delDate;
	}

	public void setDelDate(Date delDate) {
		this.delDate = delDate;
	}
	
	@ExcelField(title="删除人", align=2, sort=12)
	public String getDelBy() {
		return delBy;
	}

	public void setDelBy(String delBy) {
		this.delBy = delBy;
	}
	
	@ExcelField(title="1.会员 2.游客", align=2, sort=14)
	public Integer getIsWay() {
		return isWay;
	}

	public void setIsWay(Integer isWay) {
		this.isWay = isWay;
	}
	
}