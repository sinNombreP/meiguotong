/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.entity.comment;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 评论列表Entity
 * @author laiyanxin
 * @version 2018-03-07
 */
public class Comment extends DataEntity<Comment> {
	
	private static final long serialVersionUID = 1L;
	private Integer replyid;		// 回复ID
	private Integer type;		// type参数，没有多个不同时，1为默认数
	private Integer typeid;		// 对应的类型ID
	private String content;		// 评论内容
	private Integer memberid;		// 会员ID
	private String memberName;		// 评论人名称
	private Integer replyMember;		// 被评论人ID
	private String replyName;		// 被评论人姓名
	private Date commentDate;		// 评论时间
	private Integer replyNum;		// 回复数量
	private Integer status;		// 用户删除状态1.正常 2.删除 
	private Date delDate;		// del_date
	private String delBy;		// del_by
	private String title;		// 所评论数据的标题，回复的话，标题继承最原始的
	private String typename;		// typename
	private Date beginCommentDate;		// 开始 评论时间
	private Date endCommentDate;		// 结束 评论时间
	
	private List<Comment> twoCommentList;  //二级评论集合
	 private String memberPhoto; //评论人头像
	 private String replyMemberphoto;//被评论人头像
	 
	 
	 
	 
	
	
	
	public String getMemberPhoto() {
		return memberPhoto;
	}

	public void setMemberPhoto(String memberPhoto) {
		this.memberPhoto = memberPhoto;
	}

	public String getReplyMemberphoto() {
		return replyMemberphoto;
	}

	public void setReplyMemberphoto(String replyMemberphoto) {
		this.replyMemberphoto = replyMemberphoto;
	}

	public List<Comment> getTwoCommentList() {
		return twoCommentList;
	}

	public void setTwoCommentList(List<Comment> twoCommentList) {
		this.twoCommentList = twoCommentList;
	}

	public Comment() {
		super();
		this.setIdType(IDTYPE_AUTO);
	}

	public Comment(String id){
		super(id);
	}

	@ExcelField(title="回复ID", align=2, sort=1)
	public Integer getReplyid() {
		return replyid;
	}

	public void setReplyid(Integer replyid) {
		this.replyid = replyid;
	}
	
	@ExcelField(title="type参数，没有多个不同时，1为默认数", dictType="", align=2, sort=2)
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
	@ExcelField(title="对应的类型ID", align=2, sort=3)
	public Integer getTypeid() {
		return typeid;
	}

	public void setTypeid(Integer typeid) {
		this.typeid = typeid;
	}
	
	@ExcelField(title="评论内容", align=2, sort=4)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@ExcelField(title="会员ID", align=2, sort=5)
	public Integer getMemberid() {
		return memberid;
	}

	public void setMemberid(Integer memberid) {
		this.memberid = memberid;
	}
	
	@ExcelField(title="评论人名称", align=2, sort=6)
	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	
	@ExcelField(title="被评论人ID", align=2, sort=7)
	public Integer getReplyMember() {
		return replyMember;
	}

	public void setReplyMember(Integer replyMember) {
		this.replyMember = replyMember;
	}
	
	@ExcelField(title="被评论人姓名", align=2, sort=8)
	public String getReplyName() {
		return replyName;
	}

	public void setReplyName(String replyName) {
		this.replyName = replyName;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="评论时间", align=2, sort=9)
	public Date getCommentDate() {
		return commentDate;
	}

	public void setCommentDate(Date commentDate) {
		this.commentDate = commentDate;
	}
	
	@ExcelField(title="回复数量", align=2, sort=10)
	public Integer getReplyNum() {
		return replyNum;
	}

	public void setReplyNum(Integer replyNum) {
		this.replyNum = replyNum;
	}
	
	@ExcelField(title="用户删除状态1.正常 2.删除 ", align=2, sort=11)
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="del_date", align=2, sort=13)
	public Date getDelDate() {
		return delDate;
	}

	public void setDelDate(Date delDate) {
		this.delDate = delDate;
	}
	
	@ExcelField(title="del_by", align=2, sort=14)
	public String getDelBy() {
		return delBy;
	}

	public void setDelBy(String delBy) {
		this.delBy = delBy;
	}
	
	@ExcelField(title="所评论数据的标题，回复的话，标题继承最原始的", align=2, sort=15)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@ExcelField(title="typename", align=2, sort=16)
	public String getTypename() {
		return typename;
	}

	public void setTypename(String typename) {
		this.typename = typename;
	}
	
	public Date getBeginCommentDate() {
		return beginCommentDate;
	}

	public void setBeginCommentDate(Date beginCommentDate) {
		this.beginCommentDate = beginCommentDate;
	}
	
	public Date getEndCommentDate() {
		return endCommentDate;
	}

	public void setEndCommentDate(Date endCommentDate) {
		this.endCommentDate = endCommentDate;
	}
		
}