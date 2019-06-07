/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.mapper.settingtitle;

import java.util.List;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.meiguotong.entity.settingtitle.SettingTitle;

/**
 * 详情表MAPPER接口
 * @author cdq
 * @version 2018-08-06
 */
@MyBatisMapper
public interface SettingTitleMapper extends BaseMapper<SettingTitle> {
    //修改状态
	void status(SettingTitle settingTitle);
	/**
	 * 获取最大的isAdd
	 * @param settingTitle
	 * @return
	 */
	public Integer getMaxIsAdd(SettingTitle settingTitle);
   /**
    * 邮轮详情标题接口
    * @return
    */
	List<SettingTitle> cruiseList();
	/**
	 *  获取产品菜单接口
	 * @param settingTitle
	 * @return
	 */
	public List<SettingTitle> getProMenu(SettingTitle settingTitle);
	
	/**
	 * 获取各类型新增的标题
	 * @param settingTitle
	 * @return
	 */
	public List<SettingTitle> getAddTitle(SettingTitle settingTitle);
	
}