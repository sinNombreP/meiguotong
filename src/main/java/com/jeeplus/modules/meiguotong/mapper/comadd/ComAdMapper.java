/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.mapper.comadd;

import java.util.List;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.meiguotong.entity.comadd.ComAd;

/**
 * 广告设置MAPPER接口
 * @author cdq
 * @version 2018-07-27
 */
@MyBatisMapper
public interface ComAdMapper extends BaseMapper<ComAd> {

   /**
    * 广告列表接口
    * @param comAd
    * @return
    */
	List<ComAd> findComAdList(ComAd comAd);
	/**
	 * 广告详情接口
	 * @param comAd
	 * @return
	 */
    ComAd getComAd(ComAd comAd);
    /**
	 * 根据语言获取广告
	 * @param comAd
	 * @return
	 */
	public List<ComAd> getAdvertList(ComAd comAd);
	/**
	 * 根据id获取广告
	 * @param comAd
	 * @return
	 */
	public List<ComAd> getAdvertListById(String ids);

}