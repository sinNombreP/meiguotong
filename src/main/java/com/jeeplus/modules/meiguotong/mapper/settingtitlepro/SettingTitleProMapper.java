/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.mapper.settingtitlepro;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.meiguotong.entity.settingtitlepro.SettingTitlePro;

/**
 * 产品新增详情标题MAPPER接口
 * @author dong
 * @version 2019-04-28
 */
@MyBatisMapper
public interface SettingTitleProMapper extends BaseMapper<SettingTitlePro> {
	public void deleteByTitleid(SettingTitlePro settingTitlePro);
}