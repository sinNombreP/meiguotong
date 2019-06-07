/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.mapper.member;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.sys.entity.member.Member;

import java.util.List;

/**
 * 会员列表MAPPER接口
 * @author psz
 * @version 2018-08-07
 */
@MyBatisMapper
public interface MemberMapper extends BaseMapper<Member> {
   //修改member数据
	void uPdate(Member member);
     //查找手机是否注册账号
	Member ifRegisterByPhone(Member member);
	//查找邮箱是否注册
	Member ifRegisterByEmail(Member member);
	
	/**
	 * 获取旅行社子账号
	 * @param member
	 * @return
	 */
	public List<Member> getChildMember(String memberid);
	/**
	 * 删除旅行社子账号
	 * @param member
	 */
	public void deleteMember(Member member);
	
	/**
	 * 登录验证
	 * @param member
	 * @return
	 */
	public Member login(Member member);
	/**
	 * 验证公司子账号是否存在
	 * @param name
	 * @return
	 */
	public Integer getValidateCount(String name);
	/**
	* @Title: ifRegister
	* @Description: 登录判断是否注册
	* @author  彭善智
	* @Data 2018年12月24日下午7:58:36
	*/
	Member ifRegister(Member member);
	
	//统计某月份的会员数
	List<Member> statisticsMemberNum(Member member);

	/**
	  * @method: updatePassword
	  * @Description: 修改用户密码
	  * @Author: 彭善智
	  * @Date: 2019/5/16 20:50
	  **/
    void updatePassword(Member member);
    
    public void updateMobile(Member member);
    
}