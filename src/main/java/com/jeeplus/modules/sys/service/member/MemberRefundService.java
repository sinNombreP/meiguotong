/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.service.member;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.sys.entity.member.MemberRefund;
import com.jeeplus.modules.sys.mapper.member.MemberRefundMapper;
import com.jeeplus.modules.sys.utils.UserUtils;

/**
 * 退款设置表Service
 * @author psz
 * @version 2018-08-09
 */
/** 
 *@ClassName:  MemberRefundService   
* @Description: 
* @author 彭善智
* @date 2018年8月9日下午4:35:34
*/ 
@Service
@Transactional(readOnly = true)
public class MemberRefundService extends CrudService<MemberRefundMapper, MemberRefund> {

	public MemberRefund get(String id) {
		return super.get(id);
	}
	/**
	 * 获取导游退款信息（接口）
	 * @param memberRefund
	 * @return
	 */
	public List<MemberRefund> getGuideRefund(MemberRefund memberRefund) {
		return mapper.getGuideRefund(memberRefund);
	}
	
	/**
	 * 获取退款说明
	 * @param memberRefund
	 * @return
	 */
	public List<MemberRefund> getRefundInfor(MemberRefund memberRefund) {
		return mapper.getRefundInfor(memberRefund);
	}
	public List<MemberRefund> findList(MemberRefund memberRefund) {
		return super.findList(memberRefund);
	}
	
	public Page<MemberRefund> findPage(Page<MemberRefund> page, MemberRefund memberRefund) {
		return super.findPage(page, memberRefund);
	}
	
	public List<MemberRefund> findMemberRefund(MemberRefund memberRefund) {
		return mapper.findMemberRefund(memberRefund);
	}
	@Transactional(readOnly = false)
	public void save(MemberRefund memberRefund) {
		super.save(memberRefund);
	}
	
	@Transactional(readOnly = false)
	public void delete(MemberRefund memberRefund) {
		super.delete(memberRefund);
	}

	
	/** 
	* @Title: findListByAgentid 
	* @Description: 根据供应商ID查询供应商退款说明
	* @author 彭善智
	* @date 2018年8月9日下午4:35:37
	*/ 
	public List<MemberRefund>  findListByAgentid(MemberRefund memberRefund) {
		List<MemberRefund> MemberRefundList = mapper.findListByAgentid(memberRefund);
//		for(MemberRefund m :MemberRefundList) {
//			m.setMemberRefundList(mapper.findList(m));
//		}
		return MemberRefundList;
	}

	/** 
	* @Title: update 
	* @Description: 更新退款数据
	* @author 彭善智
	* @date 2018年8月12日上午12:26:35
	*/ 
	@Transactional(readOnly = false)
	public void update(MemberRefund memberRefundList) {
		MemberRefund memberRefund = new MemberRefund();
		memberRefund.setTypeId(UserUtils.getUser().getAgentid());
		memberRefund.setType(2);
		mapper.deleteByTypeid(memberRefund);
		memberRefund.setCreateDate(new Date());
		memberRefund.setFathflag(1);
		for(MemberRefund m1 :memberRefundList.getMemberRefundList()) {
			if(m1 == null || null == m1.getMemberRefundList() || m1.getMemberRefundList().size()==0) {
				continue;
			}
			memberRefund.setRefundType(m1.getRefundType());
			memberRefund.setRefundContent(m1.getRefundContent());
			for(MemberRefund m2 : m1.getMemberRefundList()) {
				if(m2 == null || null == m2.getRefundDay() || null == m2.getRefundNum()) {
					continue;
				}
				memberRefund.setRefundDay(m2.getRefundDay());
				memberRefund.setRefundNum(m2.getRefundNum());
				mapper.insert(memberRefund);
			}
		}
	}


}