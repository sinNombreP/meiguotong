/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.service.settingtitlepro;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.meiguotong.entity.settingtitlepro.SettingTitlePro;
import com.jeeplus.modules.meiguotong.mapper.settingtitlepro.SettingTitleProMapper;

/**
 * 产品新增详情标题Service
 * @author dong
 * @version 2019-04-28
 */
@Service
@Transactional(readOnly = true)
public class SettingTitleProService extends CrudService<SettingTitleProMapper, SettingTitlePro> {

	public SettingTitlePro get(String id) {
		return super.get(id);
	}
	
	public List<SettingTitlePro> findList(SettingTitlePro settingTitlePro) {
		return super.findList(settingTitlePro);
	}
	
	public Page<SettingTitlePro> findPage(Page<SettingTitlePro> page, SettingTitlePro settingTitlePro) {
		return super.findPage(page, settingTitlePro);
	}
	
	@Transactional(readOnly = false)
	public void save(SettingTitlePro settingTitlePro) {
		super.save(settingTitlePro);
	}
	
	@Transactional(readOnly = false)
	public void delete(SettingTitlePro settingTitlePro) {
		super.delete(settingTitlePro);
	}
	
}