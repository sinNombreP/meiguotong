/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.mapper.comtag;

import java.util.List;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.meiguotong.entity.comlanguage.ComLanguage;
import com.jeeplus.modules.meiguotong.entity.comtag.ComTag;
import com.jeeplus.modules.meiguotong.entity.guide.Guide;

/**
 * 各项管理MAPPER接口
 * @author cdq
 * @version 2018-07-31
 */
@MyBatisMapper
public interface ComTagMapper extends BaseMapper<ComTag> {
 
	/**
	 * 修改状态 
	 * @param comTag
	 */
	void status(ComTag comTag);
	/**
	 * 当地玩家列表数据
	 * @param comTag
	 * @return
	 */
	List<ComTag> PlayerList(ComTag comTag);
	/** 
	* @Title: getNameByType 
	* @Description: 根据type查询属性
	* @author 彭善智
	* @date 2018年8月13日下午4:06:02
	*/ 
	List<ComTag> getNameByType(ComTag comTag);
	/**
	 * 获取某个类型还未添加的属性
	 * @param comTag
	 * @return
	 */
	public List<ComTag> getTagByType(ComTag comTag);
	/**
	 * 查找当地玩家所有属性名称
	 * @return
	 */
	List<ComTag> getList();
	/**
	 * 查找当地玩家已有属性
	 * @return
	 */
	public List<ComTag> getGuideTagList(Guide guide);
	//修改当地属性名称
	void uPdate(ComTag comTag);
	
	/**
	 * 获取玩家擅长属性
	 * @return
	 */
	public List<ComTag> getGuideLabel(Integer languageId);
	/**
	 * 获取各类型标签属性(类型1234)
	 * @return
	 */
	public List<ComTag> getRouteLabel(ComTag comTag);
	/**
	 * 获取各类型标签属性(类型56)
	 * @return
	 */
	public List<ComTag> getLabel(ComTag comTag);
	/**
	 * 根据语言获取标签
	 * @return
	 */
	public List<ComTag> getLabelByLanguage(ComTag comTag);
}