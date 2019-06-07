/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.sys.entity.MemberFans;
import com.jeeplus.modules.sys.entity.member.MemberInformation;
import com.jeeplus.modules.sys.mapper.MemberFansMapper;
import com.jeeplus.modules.sys.mapper.member.MemberInformationMapper;

/**
 * 好友列表Service
 * @author xudemo
 * @version 2018-03-08
 */
@Service
@Transactional(readOnly = true)
public class MemberFansService extends CrudService<MemberFansMapper, MemberFans> {

	@Autowired
	private MemberInformationMapper memberInformationMapper;
	
	public MemberFans get(String id) {
		return super.get(id);
	}
	
	public List<MemberFans> findList(MemberFans memberFans) {
		return super.findList(memberFans);
	}
	
	public Page<MemberFans> findPage(Page<MemberFans> page, MemberFans memberFans) {
		return super.findPage(page, memberFans);
	}
	
	@Transactional(readOnly = false)
	public void save(MemberFans memberFans) {
		super.save(memberFans);
	}
	
	@Transactional(readOnly = false)
	public void delete(MemberFans memberFans) {
		super.delete(memberFans);
	}
	
	/**
	 * 查询是否关注
	 * @return
	 */
	public MemberFans findIsFans(MemberFans memberFans){
		return mapper.findIsFans(memberFans);
	}
	
	/**
	 * 加入关注
	 * @param memberFans
	 * @param memberInformation
	 * @param memberInformation2
	 */
	@Transactional(readOnly = false)
	public void addFans(MemberFans memberFans,MemberInformation memberInformation, MemberInformation memberInformation2) {
		mapper.insert(memberFans);
		memberInformationMapper.update(memberInformation);
		memberInformationMapper.update(memberInformation2);
	}

	/**
	 * 取消关注
	 * @param memberFans
	 * @param memberInformation
	 * @param memberInformation2
	 */
	@Transactional(readOnly = false)
	public void delFans(MemberFans memberFans,MemberInformation memberInformation, MemberInformation memberInformation2) {
		mapper.delete(memberFans);
		memberInformationMapper.update(memberInformation);
		memberInformationMapper.update(memberInformation2);
	}
	
}