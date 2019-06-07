/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.mapper.member;

import java.util.List;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.sys.entity.member.SysUserType;
import com.jeeplus.modules.sys.entity.sysUser.SysUser;

/**
 * 供应商类型MAPPER接口
 * @author psz
 * @version 2018-08-09
 */
@MyBatisMapper
public interface SysUserTypeMapper extends BaseMapper<SysUserType> {

	/** 
	* @Title: findListByAgentid 
	* @Description:  根据供应商ID查询供应商类型
	* @author 彭善智
	* @date 2018年8月9日下午4:29:22
	*/ 
	List<SysUserType> findListByAgentid(SysUser sysUser);
    //修改抽成比例
	void uPdate(SysUserType sysUserType);
	 /**
	   * @method: getUserType
	   * @Description:  查询是否有这个供应商类型
	   * @Author:   彭善智
	   * @Date:     2018/12/20 20:40
	   */
	SysUserType getUserType(SysUserType sysUserType);
	 /**
	   * @method: delRole
	   * @Description:  删除供应商抽成
	   * @Author:   彭善智
	   * @Date:     2018/12/21 9:59
	   */
    void delRole(SysUser sysUser);
}