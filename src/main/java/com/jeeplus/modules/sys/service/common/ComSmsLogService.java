/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.service.common;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.sys.entity.common.ComSmsLog;
import com.jeeplus.modules.sys.mapper.common.ComSmsLogMapper;

/**
 * 验证码Service
 * @author laiyanxin
 * @version 2018-02-26
 */
@Service
@Transactional(readOnly = true)
public class ComSmsLogService extends CrudService<ComSmsLogMapper, ComSmsLog> {

	public ComSmsLog get(String id) {
		return super.get(id);
	}
	
	public List<ComSmsLog> findList(ComSmsLog comSmsLog) {
		return super.findList(comSmsLog);
	}
	
	public Page<ComSmsLog> findPage(Page<ComSmsLog> page, ComSmsLog comSmsLog) {
		return super.findPage(page, comSmsLog);
	}
	
	@Transactional(readOnly = false)
	public void save(ComSmsLog comSmsLog) {
		super.save(comSmsLog);
	}
	
	@Transactional(readOnly = false)
	public void delete(ComSmsLog comSmsLog) {
		super.delete(comSmsLog);
	}

     /**
      * 查找验证码
      * @param comSmsLog
      * @return
      */
	public ComSmsLog findCodeByMobile(ComSmsLog comSmsLog) {
		return mapper.findCodeByMobile(comSmsLog);
	}
  /**
   * 查找邮箱注册验证码
   * @param comSmsLog
   * @return
   */
	public ComSmsLog findCodeByEmail(ComSmsLog comSmsLog) {
		return mapper.findCodeByEmail(comSmsLog);
	}
	public ComSmsLog checkEmail(ComSmsLog comSmsLog) {
		return mapper.checkEmail(comSmsLog);
	}
}