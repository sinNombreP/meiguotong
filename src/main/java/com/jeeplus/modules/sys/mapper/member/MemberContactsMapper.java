/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.mapper.member;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.sys.entity.member.MemberContacts;

/**
 * 常用联系人MAPPER接口
 * @author psz
 * @version 2018-08-07
 */
@MyBatisMapper
public interface MemberContactsMapper extends BaseMapper<MemberContacts> {
	/**
	 * 获取常用联系人信息列表接口
	 * @param memberid
	 * @return
	 */
	public List<MemberContacts> getContactMember(MemberContacts memberContacts);
}