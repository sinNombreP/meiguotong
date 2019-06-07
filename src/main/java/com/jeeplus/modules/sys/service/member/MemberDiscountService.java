/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.service.member;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.sys.entity.member.MemberDiscount;
import com.jeeplus.modules.sys.mapper.member.MemberDiscountMapper;

/**
 * 会员优惠Service
 * @author psz
 * @version 2018-08-08
 */
@Service
@Transactional(readOnly = true)
public class MemberDiscountService extends CrudService<MemberDiscountMapper, MemberDiscount> {

	public MemberDiscount get(String id) {
		return super.get(id);
	}
	/**
	 * 获取账号优惠信息
	 * @param memberid
	 * @return
	 */
	public List<MemberDiscount> getDiscountInfo(String memberid) {
		return mapper.getDiscountInfo(memberid);
	}
	public List<MemberDiscount> findList(MemberDiscount memberDiscount) {
		return super.findList(memberDiscount);
	}
	
	public Page<MemberDiscount> findPage(Page<MemberDiscount> page, MemberDiscount memberDiscount) {
		return super.findPage(page, memberDiscount);
	}
	
	@Transactional(readOnly = false)
	public void save(MemberDiscount memberDiscount) {
		super.save(memberDiscount);
	}
	
	@Transactional(readOnly = false)
	public void delete(MemberDiscount memberDiscount) {
		super.delete(memberDiscount);
	}
	
}