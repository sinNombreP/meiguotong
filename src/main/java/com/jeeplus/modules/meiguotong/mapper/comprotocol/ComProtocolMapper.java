/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.mapper.comprotocol;

import java.util.List;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.meiguotong.entity.comprotocol.ComProtocol;

/**
 * 设置管理MAPPER接口
 * @author cdq
 * @version 2018-07-30
 */
@MyBatisMapper
public interface ComProtocolMapper extends BaseMapper<ComProtocol> {
  
	
	/**
	 * 根据语言获取基本参数
	 * @param comProtocol
	 * @return
	 */
	public ComProtocol getProtocol(ComProtocol comProtocol);
	/**
	 * 获取一条数据
	 * @param comProtocol
	 * @return
	 */
	public ComProtocol getComProtocol(ComProtocol comProtocol);
	/**
	 * 修改logo设置
	 * @param comProtocol
	 * @return
	 */
	public void update1(ComProtocol comProtocol);
	/**
	 * 修改品牌设置
	 * @param comProtocol
	 * @return
	 */
	public void update2(ComProtocol comProtocol);
	/**
	 * 修改底部文字
	 * @param comProtocol
	 * @return
	 */
	public void update3(ComProtocol comProtocol);
	
}