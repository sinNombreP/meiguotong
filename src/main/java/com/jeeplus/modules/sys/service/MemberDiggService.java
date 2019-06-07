/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.sys.entity.MemberDigg;
import com.jeeplus.modules.sys.mapper.MemberDiggMapper;

/**
 * 点赞Service
 * @author dong
 * @version 2018-03-07
 */
@Service
@Transactional(readOnly = true)
public class MemberDiggService extends CrudService<MemberDiggMapper, MemberDigg> {

	public MemberDigg get(String id) {
		return super.get(id);
	}
	
	public List<MemberDigg> findList(MemberDigg memberDigg) {
		return super.findList(memberDigg);
	}
	
	public Page<MemberDigg> findPage(Page<MemberDigg> page, MemberDigg memberDigg) {
		return super.findPage(page, memberDigg);
	}
	
	@Transactional(readOnly = false)
	public void save(MemberDigg memberDigg) {
		super.save(memberDigg);
	}
	
	@Transactional(readOnly = false)
	public void delete(MemberDigg memberDigg) {
		super.delete(memberDigg);
	}
	/**
	 * 添加点赞信息（接口）
	 * @param memberDigg
	 */
	@Transactional(readOnly = false)
	public Integer insertDig(MemberDigg memberDigg) {
		Integer count=0;
		//判断用户id存在则添加
		if((memberDigg.getMemberid())!=null){
			//判断用户是否点赞过
			count = mapper.getCount(memberDigg);
			if(count==0){
				mapper.insert(memberDigg);
			}
		}
		return count;
	}
	/**
	 * 取消点赞（接口）
	 * @param memberDigg
	 */
	@Transactional(readOnly = false)
	public void delDig(MemberDigg memberDigg) {
		//判断用户id存在则取消
		if((memberDigg.getMemberid())!=null){
			mapper.delete(memberDigg);
		}
	}
	
}