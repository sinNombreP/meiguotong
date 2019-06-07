/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.service.comprotocol;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.meiguotong.entity.comprotocol.ComProtocol;
import com.jeeplus.modules.meiguotong.mapper.comprotocol.ComProtocolMapper;

/**
 * 设置管理Service
 * @author cdq
 * @version 2018-07-30
 */
@Service
@Transactional(readOnly = true)
public class ComProtocolService extends CrudService<ComProtocolMapper, ComProtocol> {

	public ComProtocol get(String id) {
		return super.get(id);
	}
	/**
	 * 获取一条数据
	 * @param comProtocol
	 * @return
	 */
	public ComProtocol getComProtocol(ComProtocol comProtocol) {
		return mapper.getComProtocol(comProtocol);
	}
	
	/**
	 * 根据语言获取基本参数
	 * @param comProtocol
	 * @return
	 */
	public ComProtocol getProtocol(ComProtocol comProtocol) {
		return mapper.getProtocol(comProtocol);
	}
	public List<ComProtocol> findList(ComProtocol comProtocol) {
		return super.findList(comProtocol);
	}
	
	public Page<ComProtocol> findPage(Page<ComProtocol> page, ComProtocol comProtocol) {
		return super.findPage(page, comProtocol);
	}
	
	@Transactional(readOnly = false)
	public void save(ComProtocol comProtocol) {
		if (comProtocol.getIsNewRecord()){
			comProtocol.preInsert();
			mapper.insert(comProtocol);
		}else{
			comProtocol.preUpdate();
			mapper.update1(comProtocol);
		}
	}
	@Transactional(readOnly = false)
	public void save2(ComProtocol comProtocol) {
		if (comProtocol.getIsNewRecord()){
			comProtocol.preInsert();
			mapper.insert(comProtocol);
		}else{
			comProtocol.preUpdate();
			mapper.update2(comProtocol);
		}
	}
	@Transactional(readOnly = false)
	public void save3(ComProtocol comProtocol) {
		if (comProtocol.getIsNewRecord()){
			comProtocol.preInsert();
			mapper.insert(comProtocol);
		}else{
			comProtocol.preUpdate();
			mapper.update3(comProtocol);
		}
	}
	@Transactional(readOnly = false)
	public void delete(ComProtocol comProtocol) {
		super.delete(comProtocol);
	}

	
}