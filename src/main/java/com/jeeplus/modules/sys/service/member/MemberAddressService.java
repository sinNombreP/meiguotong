/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.service.member;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.sys.entity.member.MemberAddress;
import com.jeeplus.modules.sys.mapper.member.MemberAddressMapper;

/**
 * 会员地址Service
 * @author xudemo
 * @version 2018-02-24
 */
@Service
@Transactional(readOnly = true)
public class MemberAddressService extends CrudService<MemberAddressMapper, MemberAddress> {

	public MemberAddress get(String id) {
		return super.get(id);
	}
	
	public MemberAddress getTopAddress(MemberAddress memberAddress) {
		return mapper.getTopAddress(memberAddress);
	}
	
	public List<MemberAddress> findList(MemberAddress memberAddress) {
		return super.findList(memberAddress);
	}
	
	public Page<MemberAddress> findPage(Page<MemberAddress> page, MemberAddress memberAddress) {
		return super.findPage(page, memberAddress);
	}
	
	@Transactional(readOnly = false)
	public void save(MemberAddress memberAddress) {
		super.save(memberAddress);
	}
	
	@Transactional(readOnly = false)
	public void delete(MemberAddress memberAddress) {
		super.delete(memberAddress);
	}
	
	@Transactional(readOnly = false)
	public void addAddress(MemberAddress memberAddress) {
		MemberAddress memberAddress2 = new MemberAddress();
		memberAddress2.setMemberId(memberAddress.getMemberId());
		if(this.findList(memberAddress2)==null){
			memberAddress.setIsTop(1);
		}else{
			if(memberAddress.getIsTop()==1){
				for(MemberAddress mAddress:this.findList(memberAddress2)){
					mAddress.setIsTop(0);
					mapper.updateIsFirst(mAddress);
				}
			}
		}
		mapper.insert(memberAddress);
	}
	
	@Transactional(readOnly = false)
	public void updateAddress(MemberAddress memberAddress) {
		MemberAddress mAddress = new MemberAddress();
		mAddress.setMemberId(memberAddress.getMemberId());
		if(this.findList(mAddress).size()==1){
			memberAddress.setIsTop(1);
		}else{
			if(memberAddress.getIsTop()==1){
				for(MemberAddress mAddress2:this.findList(mAddress)){
					mAddress2.setIsTop(0);
					mapper.updateIsFirst(mAddress2);
				}
			}else{
				if(mapper.get(memberAddress).getIsTop()==1){
					MemberAddress mAddress2 = mapper.findNotAddress(memberAddress);
					mAddress2.setIsTop(1);
					mapper.updateIsFirst(mAddress2);
				}
			}
		}
		mapper.update(memberAddress);
	}
	
	@Transactional(readOnly = false)
	public void deleteAddress(MemberAddress memberAddress) {
		MemberAddress memberAddress2 = new MemberAddress();
		memberAddress2.setMemberId(memberAddress.getMemberId());
		if(this.findList(memberAddress2).size()==1){
			mapper.delete(memberAddress);
		}else{
			if(memberAddress.getIsTop()==1){
				MemberAddress mAddress = mapper.findNotAddress(memberAddress);
				mAddress.setIsTop(1);
				mapper.updateIsFirst(mAddress);
			}
			mapper.delete(memberAddress);
		}
	}
	
}