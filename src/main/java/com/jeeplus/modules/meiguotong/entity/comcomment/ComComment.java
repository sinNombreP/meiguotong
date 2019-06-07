/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.entity.comcomment;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeeplus.common.utils.excel.annotation.ExcelField;
import com.jeeplus.core.persistence.DataEntity;

/**
 * 评论表Entity
 * @author cdq
 * @version 2018-08-01
 */
public class ComComment extends DataEntity<ComComment> {
	
	private static final long serialVersionUID = 1L;
	private Integer fatherId;		// 父评论id
	private String content;		// 评论内容
	private Integer memberId;		// 评论人id
	private Integer status;		// 状态  0禁用 1启用
	private Integer type;		// 1 攻略评论  2 城市评论 3 邮轮评论 4 景点门票  5酒店
	private Integer typeId;		// 根据type 去区分 这个字段是主键id
	private Date delDate;		// del_date
	private String delBy;		// del_by
	private Integer languageId;		// 语言id
	private Integer level;		// 评论星级
	private String nickName;   //评论人昵称
	private String account;   //评论人账号
	private List<ComComment> comCommentList;  //子评论列表
	private List<ComComment> cityCommentList;   //城市子评论列表
	private  List<ComComment> strategyCommentList;//攻略评论列表
	private  List<ComComment> playerCommentList;//当地玩家评论列表
	private  List<ComComment> sceniceCommentList;//景点评论列表
	private Integer digNum;      //点赞数量
	private Integer childNum;		//子评论数量
	private String commentid;		// 评论id
	private String memberName;  //评论人昵称
	private String memberPhoto;//评论人头像
	private String fatherComment;//父评论内容
	private Integer ifdig; //是否点赞
	private String ids; 
	private String scenicSpot; //游玩景点（攻略评论存在）
	
	private Integer cityid; 
	
	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public List<ComComment> getSceniceCommentList() {
		return sceniceCommentList;
	}

	public void setSceniceCommentList(List<ComComment> sceniceCommentList) {
		this.sceniceCommentList = sceniceCommentList;
	}

	public List<ComComment> getPlayerCommentList() {
		return playerCommentList;
	}

	public void setPlayerCommentList(List<ComComment> playerCommentList) {
		this.playerCommentList = playerCommentList;
	}

	public List<ComComment> getStrategyCommentList() {
		return strategyCommentList;
	}

	public void setStrategyCommentList(List<ComComment> strategyCommentList) {
		this.strategyCommentList = strategyCommentList;
	}

	public List<ComComment> getCityCommentList() {
		return cityCommentList;
	}

	public void setCityCommentList(List<ComComment> cityCommentList) {
		this.cityCommentList = cityCommentList;
	}

	public ComComment() {
		super();
		this.setIdType(IDTYPE_AUTO);
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public List<ComComment> getComCommentList() {
		return comCommentList;
	}

	public void setComCommentList(List<ComComment> comCommentList) {
		this.comCommentList = comCommentList;
	}

	public ComComment(String id){
		super(id);
	}

	@ExcelField(title="父评论id", align=2, sort=1)
	public Integer getFatherId() {
		return fatherId;
	}

	public void setFatherId(Integer fatherId) {
		this.fatherId = fatherId;
	}
	
	@ExcelField(title="评论内容", align=2, sort=2)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@ExcelField(title="评论人id", align=2, sort=3)
	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}
	
	@ExcelField(title="状态  0禁用 1启用", align=2, sort=4)
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@ExcelField(title="1 攻略评论  2 城市评论 3 邮轮评论 4 景点门票  5酒店", align=2, sort=5)
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
	@ExcelField(title="根据type 去区分 这个字段是主键id", align=2, sort=6)
	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="del_date", align=2, sort=11)
	public Date getDelDate() {
		return delDate;
	}

	public void setDelDate(Date delDate) {
		this.delDate = delDate;
	}
	
	@ExcelField(title="del_by", align=2, sort=12)
	public String getDelBy() {
		return delBy;
	}

	public void setDelBy(String delBy) {
		this.delBy = delBy;
	}
	
	@ExcelField(title="语言id", align=2, sort=14)
	public Integer getLanguageId() {
		return languageId;
	}

	public void setLanguageId(Integer languageId) {
		this.languageId = languageId;
	}
	
	@ExcelField(title="评论星级", align=2, sort=15)
	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getMemberName() {
		return memberName;
	}

	public String getMemberPhoto() {
		return memberPhoto;
	}

	public String getFatherComment() {
		return fatherComment;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public void setMemberPhoto(String memberPhoto) {
		this.memberPhoto = memberPhoto;
	}

	public void setFatherComment(String fatherComment) {
		this.fatherComment = fatherComment;
	}

	public String getCommentid() {
		return commentid;
	}

	public void setCommentid(String commentid) {
		this.commentid = commentid;
	}

	public Integer getDigNum() {
		return digNum;
	}

	public Integer getChildNum() {
		return childNum;
	}

	public void setDigNum(Integer digNum) {
		this.digNum = digNum;
	}

	public void setChildNum(Integer childNum) {
		this.childNum = childNum;
	}

	public Integer getIfdig() {
		return ifdig;
	}

	public void setIfdig(Integer ifdig) {
		this.ifdig = ifdig;
	}

	public Integer getCityid() {
		return cityid;
	}

	public void setCityid(Integer cityid) {
		this.cityid = cityid;
	}

	public String getScenicSpot() {
		return scenicSpot;
	}

	public void setScenicSpot(String scenicSpot) {
		this.scenicSpot = scenicSpot;
	}
	
}