/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.service.agentextract;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.meiguotong.entity.agentextract.AgentExtract;
import com.jeeplus.modules.meiguotong.mapper.agentextract.AgentExtractMapper;
import com.jeeplus.modules.meiguotong.service.order.OrderSysService;

/**
 * 代理商提现Service
 * @author dong
 * @version 2019-05-17
 */
@Service
@Transactional(readOnly = true)
public class AgentExtractService extends CrudService<AgentExtractMapper, AgentExtract> {

	@Autowired
	private OrderSysService orderSysService;
	
	public AgentExtract get(String id) {
		return super.get(id);
	}
	
	public List<AgentExtract> findList(AgentExtract agentExtract) {
		return super.findList(agentExtract);
	}
	
	public Page<AgentExtract> findPage(Page<AgentExtract> page, AgentExtract agentExtract) {
		return super.findPage(page, agentExtract);
	}
	
	@Transactional(readOnly = false)
	public void save(AgentExtract agentExtract) {
		if (agentExtract.getId()==null) {
			orderSysService.updateExtract(2, agentExtract.getOrderids());
		}
		super.save(agentExtract);
	}
	
	@Transactional(readOnly = false)
	public void delete(AgentExtract agentExtract) {
		super.delete(agentExtract);
	}
	
	//获取供应商提现列表
	public Page<AgentExtract> findAgentExtractByAgentId(Page<AgentExtract> page,AgentExtract agentExtract){
		dataRuleFilter(agentExtract);
		agentExtract.setPage(page);
		page.setList(mapper.findAgentExtractByAgentId(agentExtract));
		return page;
	};
	//提现状态
	@Transactional(readOnly = false)
	public void changeStatus (AgentExtract agentExtract){
		save(agentExtract);
		if (agentExtract.getStatus()==3) {
			orderSysService.updateExtract(1, agentExtract.getOrderids());
		}else if (agentExtract.getStatus()==4) {
			orderSysService.updateExtract(3, agentExtract.getOrderids());
		}
	}
}