/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.mapper.member;

import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.meiguotong.entity.travel.OrderTravelBusiness;
import com.jeeplus.modules.sys.entity.member.MemberInformation;

/**
 * 会员详情MAPPER接口
 * @author psz
 * @version 2018-08-07
 */
@MyBatisMapper
public interface MemberInformationMapper extends BaseMapper<MemberInformation> {

	/** 
	* @Title: changeStatus 
	* @Description: 修改会员状态
	* @author 彭善智
	* @date 2018年8月7日下午2:45:03
	*/ 
	void changeStatus(MemberInformation memberInformation);
   /**
    * 根据memberid查找数据
    * @param getMemberInformation
    * @return
    */
	MemberInformation findByMemberid(MemberInformation getMemberInformation);
    void uPdate(MemberInformation memberInformation);
	/** 
	* @Title: getInfo 
	* @Description: 获取导游信息
	* @author 彭善智
	* @date 2018年8月30日下午3:26:34
	*/ 
	MemberInformation getInfo(OrderTravelBusiness orderTravelBusiness);
	/**
	 * 获取会员个人信息（接口）
	 * @param memberid
	 * @return
	 */
	public MemberInformation getMember(String memberid);
	/**
	 * 获取会员个人信息(旅行社)（接口）
	 * @param memberid
	 * @return
	 */
	public MemberInformation getMemberTravel(String memberid);
	/**
	 * 修改会员信息
	 * @param memberInformation
	 */
	public void updateMember(MemberInformation memberInformation);
	/**
	 * 修改会员信息(邮箱)
	 * @param memberInformation
	 */
	public void updateMemberEmail(MemberInformation memberInformation);
	/**
	 * 修改会员信息(旅行社)
	 * @param memberInformation
	 */
	public void updateMemberTravel(MemberInformation memberInformation);
	/**
	 * 修改会员信息(旅行社审核状态)
	 * @param memberInformation
	 */
	public void updateMemberAudit(MemberInformation memberInformation);
		
}