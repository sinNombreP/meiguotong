/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.service.liner;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.meiguotong.entity.liner.LinerPort;
import com.jeeplus.modules.meiguotong.mapper.liner.LinerPortMapper;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.utils.UserUtils;

/**
 * 邮轮港口设置Service
 * @author dong
 * @version 2018-10-29
 */
@Service
@Transactional(readOnly = true)
public class LinerPortService extends CrudService<LinerPortMapper, LinerPort> {

	public LinerPort get(String id) {
		return super.get(id);
	}
	/**
	 * 根据语言获取港口
	 * @param linerPort
	 * @return
	 */
	public List<LinerPort> getPortList(LinerPort linerPort) {
		return mapper.getPortList(linerPort);
	}
	/**
	 * 根据语言城市获取港口
	 * @param linerPort
	 * @return
	 */
	public List<LinerPort> getPortByCity(LinerPort linerPort) {
		return mapper.getPortByCity(linerPort);
	}
	public List<LinerPort> findList(LinerPort linerPort) {
		return super.findList(linerPort);
	}
	
	public Page<LinerPort> findPage(Page<LinerPort> page, LinerPort linerPort) {
		return super.findPage(page, linerPort);
	}
	
	@Transactional(readOnly = false)
	public void save(LinerPort linerPort) {
		super.save(linerPort);
	}
	
	@Transactional(readOnly = false)
	public void delete(LinerPort linerPort) {
		User user = UserUtils.getUser();
		if (StringUtils.isNotBlank(user.getId())){
			linerPort.setDelBy(user);
			linerPort.setDelDate(new Date());
		}
		super.delete(linerPort);
	}
	
}