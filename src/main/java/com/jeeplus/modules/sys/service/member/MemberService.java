/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.service.member;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.sys.entity.member.Member;
import com.jeeplus.modules.sys.entity.member.MemberInformation;
import com.jeeplus.modules.sys.entity.member.MemberTravel;
import com.jeeplus.modules.sys.mapper.member.MemberMapper;
import com.jeeplus.modules.sys.mapper.member.MemberTravelMapper;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 会员列表Service
 * @author psz
 * @version 2018-08-07
 */
@Service
@Transactional(readOnly = true)
public class MemberService extends CrudService<MemberMapper, Member> {
	@Autowired
	private MemberInformationService memberInformationService;
	@Autowired
	private MemberTravelMapper memberTravelMapper;
	@Autowired
	private MemberMapper memberMapper;
	
	public Member get(String id) {
		return super.get(id);
	}
	/**
	 * 获取旅行社子账号
	 * @param member
	 * @return
	 */
	public List<Member> getChildMember(String memberid) {
		return mapper.getChildMember(memberid);
	}
	public List<Member> findList(Member member) {
		return super.findList(member);
	}
	
	public Page<Member> findPage(Page<Member> page, Member member) {
		return super.findPage(page, member);
	}
	/**
	 * 修改旅行社子账号
	 * @param list
	 */
	@Transactional(readOnly = false)
	public void updateCompanyInfor(List<Member> list,String uid) {
		//获取子账号
		List<Member> memberList = mapper.getChildMember(uid);
		for(Member a:memberList){
			int num = 0;
			for(Member b:list){
				if(a.getMemberid()==b.getMemberid()){
					num = 1;
				}
			}
			if(num==0){//账号不存在，删除
				mapper.deleteMember(a);
			}
//			if(num==1){//账号存在，编辑
//				
//			}
		}
		for(Member c:list){
			if(c.getMemberid()==null){
				//添加新的子账号
				Member member = new Member();
				member.setType(2);
				member.setFatherId(Integer.parseInt(uid));
				member.setMobile(c.getMobile());
				member.setPassword(c.getPassword());
				member.setViewPassword(c.getViewPassword());
				member.setStatus(1);
				member.setAudit(1);
				member.setCreateDate(new Date());
				member.setDelFlag("0");
				mapper.insert(member);
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void save(Member member) {
		super.save(member);
	}
	
	@Transactional(readOnly = false)
	public void delete(Member member) {
		super.delete(member);
	}
    /**
     * 查找手机是否注册
     * @param member
     * @return
     */
	public Member ifRegisterByPhone(Member member) {
		return mapper.ifRegisterByPhone(member);
	}
	/**
	 * 保存注册
	 * @param member
	 * @param memberInformation
	 */
	@Transactional(readOnly = false)
	public void register(Member member, MemberInformation memberInformation) {
		member.setStatus(1);
		member.setAudit(3);
		mapper.insert(member);	
		memberInformation.setMemberid(member.getId().toString());// 插入memberinfomation
		memberInformation.setPhone(member.getMobile());
		String nickName="";
		if(member.getCreateType()==1){
			nickName = member.getMobile();
		}else if(member.getCreateType()==2){
			nickName = member.getEmail();
		}
		memberInformation.setNickName(nickName);
		memberInformationService.save(memberInformation);
		
	}
   /**
    * 查看邮箱是否注册
    * @param member
    * @return
    */
	public Member ifRegisterByEmail(Member member) {
		return mapper.ifRegisterByEmail(member);
	}
   /**
    * 保存商家注册的个人注册信息
    * @param member
    * @param memberInformation
    * @param memberTravel
    */
    @Transactional(readOnly = false)
	public void BusinessRegister(Member member, MemberInformation memberInformation, MemberTravel memberTravel) {
	     mapper.insert(member);	
//		memberInformation.setMemberid(member.getId().toString());// 插入memberinfomation
//		memberInformationService.save(memberInformation);
		memberTravel.setMemberid(Integer.parseInt(member.getId()));
		memberTravel.preInsert();
		memberTravelMapper.insert(memberTravel);
}
    /**
     * 保存商家注册的公司注册信息
     * @param member
     * @param memberTravel
     */
	@Transactional(readOnly = false)
public void BusinessRegister2(Member member, MemberTravel memberTravel) {
		mapper.insert(member);
		memberTravel.setMemberid(Integer.parseInt(member.getId()));
		memberTravel.preInsert();
		memberTravelMapper.insert(memberTravel);
	
}


	/**
	 * 登录验证
	 * @param member
	 * @return
	 */
	public Member login(Member member) {
		return mapper.login(member);
	}

	/**
	* @Title: myInfor
	* @Description: 获取个人信息
	* @author  彭善智
	* @Data 2018年9月25日下午3:56:59
	*/
	public MemberInformation myInfor(String uid) {
		Member member1 = mapper.get(uid);
		int memberType = member1.getType();
		MemberInformation member = new MemberInformation();
		if(memberType==1){//普通用户
			member = memberInformationService.getMember(uid);
			member.setMemberType(1);
		}else{//旅行社用户
			member = memberInformationService.getMemberTravel(uid);
			member.setMemberType(2);
		}
		return member;
	}

	/**
	 * 验证公司子账号是否存在
	 * @param name
	 * @return
	 */
	public Integer getValidateCount(String name) {
		return mapper.getValidateCount(name);
	}
	/**
	* @Title: ifRegister
	* @Description: 登录判断是否注册过
	* @author  彭善智
	* @Data 2018年12月24日下午7:57:51
	*/
	public Member ifRegister(Member member) {
		return mapper.ifRegister(member);
	}
	
	//统计某月份的会员数
	public List<Member> statisticsMemberNum(Member member){
			return memberMapper.statisticsMemberNum(member);
		};

	/**
	  * @method: updatePassword
	  * @Description: 修改用户密码
	  * @Author: 彭善智
	  * @Date: 2019/5/16 20:49
	  **/
	@Transactional(readOnly = false)
	public void updatePassword(Member member) {
		mapper.updatePassword(member);
	}
	/**
	 * 修改旅行社
	 * @param member
	 * @param memberTravel
	 */
	@Transactional(readOnly = false)
	public void updateTravel(Member member,MemberTravel memberTravel) {
		if(StringUtils.isNotBlank(member.getMobile())){
			mapper.updateMobile(member);
		}
        memberTravelMapper.update(memberTravel);
	}
	
}