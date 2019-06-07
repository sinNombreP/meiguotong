/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.mapper.agentextract;

import java.util.List;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.meiguotong.entity.agentextract.AgentExtract;

/**
 * 代理商提现MAPPER接口
 * @author dong
 * @version 2019-05-17
 */
@MyBatisMapper
public interface AgentExtractMapper extends BaseMapper<AgentExtract> {
	
	//获取供应商提现列表
	public List<AgentExtract> findAgentExtractByAgentId(AgentExtract agentExtract);
}