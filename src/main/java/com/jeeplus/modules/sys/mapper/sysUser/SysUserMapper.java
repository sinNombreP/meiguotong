/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.mapper.sysUser;

import java.util.List;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.sys.entity.member.SysUserType;
import com.jeeplus.modules.sys.entity.sysUser.Role;
import com.jeeplus.modules.sys.entity.sysUser.SysUser;

/**
 * 后台账号管理MAPPER接口
 * @author xudemo
 * @version 2018-01-03
 */
@MyBatisMapper
public interface SysUserMapper extends BaseMapper<SysUser> {
	
	/** 
	* @Title: changeState 
	* @Description: 启用禁用
	* @author 彭善智
	* @date 2018年8月10日上午9:48:02
	*/ 
	void changeState(SysUser sysUser);
	
	void enable(SysUser sysUser);
	
	void disable(SysUser sysUser);
	
	SysUser getByName(SysUser sysUser);

	void updateAudit(SysUser sysUser);
	
	/**
	 * 删除用户角色关联数据
	 * @param user
	 * @return
	 */
	public int deleteUserRole(SysUser sysUser);
	/**
	 * 插入用户角色关联数据
	 * @param user
	 * @return
	 */
	public int insertUserRole(SysUser sysUser);

	/** 
	* @Title: findListByfathid 
	* @Description: 查询供应商子账号
	* @author 彭善智
	* @date 2018年8月9日下午3:35:05
	*/ 
	List<SysUser> findListByfathid(SysUser sysUser);

	/** 
	* @Title: findByAgentId 
	* @Description: 根据agentid查询用户信息
	* @author 彭善智
	* @date 2018年8月10日下午6:37:15
	*/ 
	SysUser findByAgentId(Integer agentid);

	/** 
	* @Title: getMaxAgentid 
	* @Description: 获取最大的agentid
	* @author 彭善智
	* @date 2018年8月11日下午2:53:28
	*/ 
	Integer getMaxAgentid();

	/** 
	* @Title: getNameByType 
	* @Description: 根据type查询该类型的供应商信息 
	* @author 彭善智
	* @date 2018年8月13日下午3:53:37
	*/ 
	List<SysUser> getNameByType(Integer type);
	 /**
	   * @method: delRole
	   * @Description:  删除供应商类型
	   * @Author:   彭善智
	   * @Date:     2018/12/20 21:08
	   */
    void delRole(SysUserType sysUserType);
	 /**
	   * @method: addRole
	   * @Description:  添加供应商类型
	   * @Author:   彭善智
	   * @Date:     2018/12/20 21:11
	   */
	void addRole(SysUserType sysUserType);
	
	/*获取某月的供应商注册数量*/
	public List<SysUser> statisticsAgent(SysUser sysUser);

}