/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.service.comlanguage;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.meiguotong.entity.comarticle.ComArticle;
import com.jeeplus.modules.meiguotong.entity.comlanguage.ComLanguage;
import com.jeeplus.modules.meiguotong.entity.settingtitle.SettingTitle;
import com.jeeplus.modules.meiguotong.mapper.comarticle.ComArticleMapper;
import com.jeeplus.modules.meiguotong.mapper.comlanguage.ComLanguageMapper;
import com.jeeplus.modules.meiguotong.service.comarticle.ComArticleService;
import com.jeeplus.modules.meiguotong.service.settingtitle.SettingTitleService;

/**
 * 语言表Service
 * @author cdq
 * @version 2018-07-27
 */
@Service
@Transactional(readOnly = true)
public class ComLanguageService extends CrudService<ComLanguageMapper, ComLanguage> {
	@Autowired
	private SettingTitleService settingTitleService;
	@Autowired
	private ComArticleMapper comArticleMapper;
	
	public ComLanguage get(String id) {
		return super.get(id);
	}
	/**
	 * 获取语言接口
	 * @return
	 */
	public List<ComLanguage> getLanguage() {
		return mapper.getLanguage();
	}
	public List<ComLanguage> findList(ComLanguage comLanguage) {
		return super.findList(comLanguage);
	}
	
	public Page<ComLanguage> findPage(Page<ComLanguage> page, ComLanguage comLanguage) {
		return super.findPage(page, comLanguage);
	}
	
	@Transactional(readOnly = false)
	public void save(ComLanguage comLanguage) {
		if (comLanguage.getIsNewRecord()){
			comLanguage.preInsert();
			mapper.insert(comLanguage);
			//添加默认固定的详情标题
			SettingTitle settingTitle = new SettingTitle();
			settingTitle.setDelFlag("0"); //删除状态 0正常1 删除
			settingTitle.setLanguageId(Integer.parseInt(comLanguage.getId())); //语言id
			settingTitle.setStatus(1); //状态 1 启用 2 禁用
			for(int i=1;i<5;i++){
				if(i==3){
					settingTitle.setType(i); //类型 1 常规线路  2 当地参团 3 景点门票 4 邮轮
					settingTitle.setSort(2);
					settingTitle.setIsadd("2");//是否新增1.新增的2.行程内容3.评价4.用户咨询'
					settingTitle.setTitle("预订须知"); //标题
					settingTitleService.save(settingTitle);
					settingTitle.setSort(3);
					settingTitle.setIsadd("3");//是否新增1.新增的2.行程内容3.评价4.用户咨询'
					settingTitle.setTitle("景点介绍"); //标题
					settingTitleService.save(settingTitle);
					settingTitle.setSort(4);
					settingTitle.setIsadd("4");//是否新增1.新增的2.行程内容3.评价4.用户咨询'
					settingTitle.setTitle("用户点评"); //标题
					settingTitleService.save(settingTitle);
				}else{
					settingTitle.setType(i); //类型 1 常规线路  2 当地参团 3 景点门票 4 邮轮
					settingTitle.setSort(2);
					settingTitle.setIsadd("2");//是否新增1.新增的2.行程内容3.评价4.用户咨询'
					settingTitle.setTitle("行程内容"); //标题
					settingTitleService.save(settingTitle);
					settingTitle.setSort(3);
					settingTitle.setIsadd("3");//是否新增1.新增的2.行程内容3.评价4.用户咨询'
					settingTitle.setTitle("评价"); //标题
					settingTitleService.save(settingTitle);
					settingTitle.setSort(4);
					settingTitle.setIsadd("4");//是否新增1.新增的2.行程内容3.评价4.用户咨询'
					settingTitle.setTitle("用户咨询"); //标题
					settingTitleService.save(settingTitle);
				}
			}
			//根据语言添加协议
			List<ComArticle> comArticles=comArticleMapper.findArticleByType1();
			Date date=new Date();
			for (ComArticle c:comArticles) {
				c.setId(null);
				c.setLanguageId(Integer.valueOf(comLanguage.getId()));
				c.setCreateDate(date);
				comArticleMapper.insert(c);
			}
		}else{
			comLanguage.preUpdate();
			 mapper.update(comLanguage);
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(ComLanguage comLanguage) {
		super.delete(comLanguage);
	}
   //查找语言
	public List<ComLanguage> findLanguage() {
		return mapper.findLanguage();
	}
  //更改状态
	@Transactional(readOnly = false)
	public void status(ComLanguage comLanguage) {
		mapper.status(comLanguage);
	}
     /**
      * 获取语言列表接口
      * @param page
      * @param comLanguage
      * @return
      */
	public Page<ComLanguage> findLanguageList(Page<ComLanguage> page, ComLanguage comLanguage) {
		dataRuleFilter(comLanguage);
		comLanguage.setPage(page);
		page.setList(mapper.findLanguageList(comLanguage));  
		return page;
	}
	
}