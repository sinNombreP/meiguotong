/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.service.settingtitle;

import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.meiguotong.entity.settingtitle.SettingTitle;
import com.jeeplus.modules.meiguotong.entity.settingtitlepro.SettingTitlePro;
import com.jeeplus.modules.meiguotong.mapper.settingtitle.SettingTitleMapper;
import com.jeeplus.modules.meiguotong.mapper.settingtitlepro.SettingTitleProMapper;

/**
 * 详情表Service
 * @author cdq
 * @version 2018-08-06
 */
@Service
@Transactional(readOnly = true)
public class SettingTitleService extends CrudService<SettingTitleMapper, SettingTitle> {
	@Autowired
	private SettingTitleProMapper settingTitleProMapper;
	
	public SettingTitle get(String id) {
		return super.get(id);
	}
	/**
	 *  获取产品菜单接口
	 * @param settingTitle
	 * @return
	 */
	public List<SettingTitle> getProMenu(SettingTitle settingTitle) {
		List<SettingTitle> list = mapper.getProMenu(settingTitle);
		if(list!=null&&list.size()>0){
			for(SettingTitle a:list){
				if(Integer.parseInt(a.getIsadd())!=1&&Integer.parseInt(a.getIsadd())!=2
						&&Integer.parseInt(a.getIsadd())!=3&&Integer.parseInt(a.getIsadd())!=4){
					a.setContent(StringEscapeUtils.unescapeHtml(a.getContent()));
				}
			}
		}
		return list;
	}
	/**
	 * 获取各类型新增的标题
	 * @param settingTitle
	 * @return
	 */
	public List<SettingTitle> getAddTitle(SettingTitle settingTitle) {
		return mapper.getAddTitle(settingTitle);
	}
	
	public List<SettingTitle> findList(SettingTitle settingTitle) {
		return super.findList(settingTitle);
	}
	
	public Page<SettingTitle> findPage(Page<SettingTitle> page, SettingTitle settingTitle) {
		return super.findPage(page, settingTitle);
	}
	
	@Transactional(readOnly = false)
	public void save(SettingTitle settingTitle) {
		if(settingTitle.getIsadd()==null){
			//查询isadd的值
			Integer count = mapper.getMaxIsAdd(settingTitle);
			Integer count1=count+1;
			settingTitle.setIsadd(count1.toString());
		}
		super.save(settingTitle);
	}
	
	@Transactional(readOnly = false)
	public void delete(SettingTitle settingTitle) {
		super.delete(settingTitle);
		//删除产品的详情信息
		SettingTitlePro settingTitlePro = new SettingTitlePro();
		settingTitlePro.setTitleid(Integer.parseInt(settingTitle.getId())); 
		settingTitleProMapper.deleteByTitleid(settingTitlePro);
	}
	/**
	 * 修改状态
	 * @param settingTitle
	 */
	@Transactional(readOnly = false)
	public void status(SettingTitle settingTitle) {
		mapper.status(settingTitle);
	}
   /**
    * 邮轮详情标题接口
    * @return
    */
	public List<SettingTitle> cruiseList() {
		return mapper.cruiseList();
	}
	
}