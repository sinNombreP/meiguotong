/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.service.member;

import java.util.List;

import com.jeeplus.modules.sys.entity.sysUser.Role;
import com.jeeplus.modules.sys.mapper.sysUser.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.sys.entity.member.SysUserType;
import com.jeeplus.modules.sys.entity.sysUser.SysUser;
import com.jeeplus.modules.sys.mapper.member.SysUserTypeMapper;

/**
 * 供应商类型Service
 * @author psz
 * @version 2018-08-09
 */
@Service
@Transactional(readOnly = true)
public class SysUserTypeService extends CrudService<SysUserTypeMapper, SysUserType> {

	@Autowired
	private SysUserMapper sysUserMapper;

	public SysUserType get(String id) {
		return super.get(id);
	}
	
	public List<SysUserType> findList(SysUserType sysUserType) {
		return super.findList(sysUserType);
	}
	
	public Page<SysUserType> findPage(Page<SysUserType> page, SysUserType sysUserType) {
		return super.findPage(page, sysUserType);
	}
	
	@Transactional(readOnly = false)
	public void save(SysUserType sysUserType) {
		super.save(sysUserType);
	}
	
	@Transactional(readOnly = false)
	public void delete(SysUserType sysUserType) {
		super.delete(sysUserType);
	}

	/** 
	* @Title: findListByAgentid 
	* @Description: 根据供应商ID查询供应商类型
	* @author 彭善智
	* @date 2018年8月9日下午4:27:58
	*/ 
	public List<SysUserType> findListByAgentid(SysUser sysUser) {
		return mapper.findListByAgentid(sysUser);
	}

	 /**
	   * @method: addRole
	   * @Description:  修改供应商类型
	   * @Author:   彭善智
	   * @Date:     2018/12/20 20:33
	   */
	 @Transactional(readOnly = false)
	public void addRole(SysUserType sysUserType) {
		SysUser sysUser = sysUserMapper.get(sysUserType.getUserid());
		sysUserType.setAgentid(sysUser.getAgentid());
		SysUserType sysUserTypeFlag = mapper.getUserType(sysUserType);
		//为空则添加  否则删除
		if(sysUserTypeFlag == null){
			sysUserMapper.addRole(sysUserType);
			sysUserType.preInsert();
			sysUserType.setUserType(1);
			sysUserType.setStstus(1);
			sysUserType.setAgentid(sysUser.getAgentid());
			mapper.insert(sysUserType);
		}else{
			sysUserMapper.delRole(sysUserType);
			mapper.delete(sysUserTypeFlag);
		}
	}
}