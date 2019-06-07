/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.service.member;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.sys.entity.member.MemberContacts;
import com.jeeplus.modules.sys.mapper.member.MemberContactsMapper;

/**
 * 常用联系人Service
 * @author psz
 * @version 2018-08-07
 */
@Service
@Transactional(readOnly = true)
public class MemberContactsService extends CrudService<MemberContactsMapper, MemberContacts> {

	public MemberContacts get(String id) {
		return super.get(id);
	}
	/**
	 * 获取常用联系人信息列表接口
	 * @param memberid
	 * @return
	 */
	public Page<MemberContacts> getContactMember(Page<MemberContacts> page,MemberContacts memberContacts) {
		dataRuleFilter(memberContacts);
		memberContacts.setPage(page);
		page.setList(mapper.getContactMember(memberContacts));
		return page;
	}
	/**
	 * 获取所有常用联系人
	 * @param memberContacts
	 * @return
	 */
	public List<MemberContacts> getMemberContacts(MemberContacts memberContacts) {
		return mapper.getContactMember(memberContacts);
	}
	
	public List<MemberContacts> findList(MemberContacts memberContacts) {
		return super.findList(memberContacts);
	}
	
	public Page<MemberContacts> findPage(Page<MemberContacts> page, MemberContacts memberContacts) {
		return super.findPage(page, memberContacts);
	}
	
	@Transactional(readOnly = false)
	public void save(MemberContacts memberContacts) {
		super.save(memberContacts);
	}
	
	@Transactional(readOnly = false)
	public void delete(MemberContacts memberContacts) {
		super.delete(memberContacts);
	}
	
}