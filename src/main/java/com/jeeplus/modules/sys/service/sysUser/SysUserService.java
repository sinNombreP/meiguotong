/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.service.sysUser;

import java.math.BigDecimal;
import java.util.List;

import com.jeeplus.modules.sys.entity.member.SysUserType;
import com.jeeplus.modules.sys.mapper.member.SysUserTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.utils.CacheUtils;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.core.service.ServiceException;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.entity.sysUser.Role;
import com.jeeplus.modules.sys.entity.sysUser.SysUser;
import com.jeeplus.modules.sys.mapper.sysUser.RoleMapper;
import com.jeeplus.modules.sys.mapper.sysUser.SysUserMapper;
import com.jeeplus.modules.sys.utils.UserUtils;

/**
 * 后台账号管理Service
 * @author xudemo
 * @version 2018-01-03
 */
@Service
@Transactional(readOnly = true)
public class SysUserService extends CrudService<SysUserMapper, SysUser> {

	@Autowired
	private RoleMapper roleMapper;
	@Autowired
	private SysUserTypeMapper sysUserTypeMapper;
	@Autowired
	private SysUserMapper sysUserMapper;
	
	
	public SysUser get(String id) {
		SysUser sysUser = super.get(id);
		User user = new User();
		user.setId(id);
		//根据id获取对应的权限
		sysUser.setRoleList(roleMapper.findList(new Role(user)));
		return sysUser;
	}
	
	public List<SysUser> findList(SysUser sysUser) {
		return super.findList(sysUser);
	}
	
	public Page<SysUser> findPage(Page<SysUser> page, SysUser sysUser) {
		return super.findPage(page, sysUser);
	}
	
	public SysUser getSysUserByName(String name) {
		SysUser r = new SysUser();
		r.setLoginName(name);
		return mapper.getByName(r);
	}
	
	@Transactional(readOnly = false)
	public void save(SysUser sysUser) {
		/*super.save(sysUser);*/
		if (StringUtils.isBlank(sysUser.getId())){
			sysUser.preInsert();
			mapper.insert(sysUser);
		}else{
			// 清除原用户机构用户缓存
		//	SysUser oldUser = mapper.get(sysUser.getId());
			/*if (oldUser.getOffice() != null && oldUser.getOffice().getId() != null){
				CacheUtils.remove(UserUtils.USER_CACHE, UserUtils.USER_CACHE_LIST_BY_OFFICE_ID_ + oldUser.getOffice().getId());
			}*/
			// 更新用户数据
			sysUser.preUpdate();
			mapper.update(sysUser);
		}
		if (StringUtils.isNotBlank(sysUser.getId())){
			// 更新用户与角色关联
			mapper.deleteUserRole(sysUser);
			if (sysUser.getRoleList() != null && sysUser.getRoleList().size() > 0){
				mapper.insertUserRole(sysUser);
			}else{
				throw new ServiceException(sysUser.getLoginName() + "没有设置角色！");
			}
			// 清除用户缓存
			User user = new User();
			user.setId(sysUser.getId());
			user.setLoginName(sysUser.getLoginName());
			UserUtils.clearCache(user);
			// 清除权限缓存
		//	systemRealm.clearAllCachedAuthorizationInfo();
		}
	}

	 /**
	   * @method: updateAgent
	   * @Description:  添加/修改供应商
	   * @Author:   彭善智
	   * @Date:     2018/12/20 17:02
	   */
     @Transactional(readOnly = false)
	public void updateAgent(SysUser sysUser){
        super.save(sysUser);
        if (StringUtils.isNotBlank(sysUser.getId())){
            // 更新用户与角色关联
            mapper.deleteUserRole(sysUser);
            sysUserTypeMapper.delRole(sysUser);
            if (sysUser.getSysUserTypeList() != null && sysUser.getSysUserTypeList().size() > 0){
                for(SysUserType sysUserType: sysUser.getSysUserTypeList()){
                    sysUserType.setUserid(sysUser.getId());
                    if(sysUserType.getStstus() == 1){
                        if(sysUserType.getDiscount() == null){
                            sysUserType.setDiscount(new BigDecimal("10"));
                        }
                        mapper.addRole(sysUserType);
                        sysUserType.preInsert();
                        sysUserType.setUserType(1);
                        sysUserType.setStstus(1);
                        sysUserType.setAgentid(sysUser.getAgentid());
                        sysUserTypeMapper.insert(sysUserType);
                    }
                }
            }else{
                throw new ServiceException(sysUser.getLoginName() + "没有设置角色！");
            }
            // 清除用户缓存
            User user = new User();
			user.setId(sysUser.getId());
			user.setLoginName(sysUser.getLoginName());
			UserUtils.clearCache(user);
            // 清除权限缓存
            //	systemRealm.clearAllCachedAuthorizationInfo();
        }
	}

	@Transactional(readOnly = false)
	public void delete(SysUser sysUser) {
		super.delete(sysUser);
	}
	
	@Transactional(readOnly = false)
	public void deleteByLogic(SysUser sysUser) {
		mapper.deleteByLogic(sysUser);
	}
	
	@Transactional(readOnly = false)
	public void enable(SysUser sysUser) {
		mapper.enable(sysUser);
		// 清除用户缓存
        User user = new User();
		user.setId(sysUser.getId());
		user.setLoginName(sysUser.getLoginName());
		UserUtils.clearCache(user);
	}
	
	@Transactional(readOnly = false)
	public void disable(SysUser sysUser) {
		mapper.disable(sysUser);
		// 清除用户缓存
        User user = new User();
		user.setId(sysUser.getId());
		user.setLoginName(sysUser.getLoginName());
		UserUtils.clearCache(user);
	}
	
	@Transactional(readOnly = false)
	public void roleDeleteByLogic(Role role) {
		roleMapper.deleteByLogic(role);
	}

	@Transactional(readOnly = false)
	public void updateAudit(SysUser sysUser) {
		mapper.updateAudit(sysUser);
	}

	/** 
	* @Title: findListByfathid 
	* @Description: 查询供应商子账号
	* @author 彭善智
	* @date 2018年8月9日下午3:34:31
	*/ 
	public List<SysUser> findListByfathid(SysUser sysUser) {
		return mapper.findListByfathid(sysUser);
	}
	
	/** 
	* @Title: changeState 
	* @Description: 启用禁用
	* @author 彭善智
	* @date 2018年8月10日上午9:49:55
	*/ 
	@Transactional(readOnly = false)
	public void changeState(SysUser sysUser) {
		mapper.changeState(sysUser);
		  // 清除用户缓存
        User user = new User();
		user.setId(sysUser.getId());
		user.setLoginName(sysUser.getLoginName());
		UserUtils.clearCache(user);
	}

	/** 
	* @Title: findByAgentId 
	* @Description: 根据agentid查询用户信息
	* @author 彭善智
	* @date 2018年8月10日下午6:36:23
	*/ 
	public SysUser findByAgentId(Integer agentid) {
		return 	mapper.findByAgentId(agentid);
	}

	/** 
	* @Title: getMaxAgentid 
	* @Description: 获取最大的agentid
	* @author 彭善智
	* @date 2018年8月11日下午2:52:38
	*/ 
	public Integer getMaxAgentid() {
		return mapper.getMaxAgentid();
	}

	/** 
	* @Title: getNameByType 
	* @Description: 根据type查询该类型的供应商信息 
	* @author 彭善智
	* @date 2018年8月13日下午3:53:06
	*/ 
	public List<SysUser> getNameByType(Integer type) {
		return mapper.getNameByType(type);
	}

	/*获取某月的供应商注册数量*/
	public List<SysUser> statisticsAgent(SysUser sysUser){
		return sysUserMapper.statisticsAgent(sysUser);
	};
}