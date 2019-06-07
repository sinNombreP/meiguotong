/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.service.module;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.meiguotong.entity.module.ModuleContent;
import com.jeeplus.modules.meiguotong.entity.module.ModuleDetails;
import com.jeeplus.modules.meiguotong.mapper.module.ModuleContentMapper;
import com.jeeplus.modules.meiguotong.mapper.module.ModuleDetailsMapper;

/**
 * 模块内容Service
 * @author psz
 * @version 2018-12-03
 */
@Service
@Transactional(readOnly = true)
public class ModuleContentService extends CrudService<ModuleContentMapper, ModuleContent> {
	
	@Autowired
	private ModuleDetailsMapper moduleDetailsMapper;

	public ModuleContent get(String id) {
		return super.get(id);
	}
	
	public List<ModuleContent> findList(ModuleContent moduleContent) {
		return super.findList(moduleContent);
	}
	
	public Page<ModuleContent> findPage(Page<ModuleContent> page, ModuleContent moduleContent) {
		return super.findPage(page, moduleContent);
	}
	
	@Transactional(readOnly = false)
	public void save(ModuleContent moduleContent) {
		super.save(moduleContent);
	}
	
	@Transactional(readOnly = false)
	public void delete(ModuleContent moduleContent) {
		super.delete(moduleContent);
	}

	/**
	* @Title: getModuleHtmlNameList
	* @Description: 获取关联模块
	* @author  彭善智
	* @Data 2018年12月6日下午7:52:45
	*/
	public List<ModuleContent> getModuleHtmlNameList(ModuleContent moduleContent) {
		return mapper.getModuleHtmlNameList(moduleContent);
	}

	/**
	* @Title: addModuleContent
	* @Description: 保存模板内容
	* @author  彭善智
	* @Data 2018年12月10日下午4:58:40
	*/
	@Transactional(readOnly = false)
	public void addModuleContent(ModuleContent moduleContent) {
		if (moduleContent.getIsNewRecord()){
			moduleContent.setSort(mapper.getMaxSort(moduleContent));
			mapper.insert(moduleContent);
		}else{
			mapper.update(moduleContent);
			moduleDetailsMapper.deleteByModeleContentId(moduleContent.getId());
		}
		if(moduleContent.getModuleDetails() != null)
		for(ModuleDetails m : moduleContent.getModuleDetails()) {
			m.setModuleContentId(Integer.parseInt(moduleContent.getId()));
			moduleDetailsMapper.insert(m);
		}
	}

	/**
	* @Title: updateSort
	* @Description: 排序
	* @author  彭善智
	* @Data 2018年12月11日下午2:48:37
	*/
	@Transactional(readOnly = false)
	public void updateSort(String id, Integer sort){
		ModuleContent moduleContent = mapper.get(id);
		List<ModuleContent> moduleContentList = mapper.getModuleHtmlNameList(moduleContent);
		if(moduleContentList.size() == 1 || moduleContent.getSort() + sort <= 0) return;
		if(moduleContent.getSort() + sort >= mapper.getMaxSort(moduleContent)) return;
		for(ModuleContent m :moduleContentList) {
			if(m.getSort() == moduleContent.getSort()) {
				m.setSort(m.getSort() + sort);
				mapper.update(m);
			}else if(m.getSort() == moduleContent.getSort() + sort && !m.getId().equals(id)) {
				m.setSort(m.getSort() - sort);
				mapper.update(m);
			}
		}	
	}

	/**
	* @Title: getModuleContentById
	* @Description: 获取模块详情
	* @author  彭善智
	* @Data 2018年12月11日下午4:24:59
	*/
	public ModuleContent getModuleContentById(String id) {
		return mapper.getModuleContentById(id);
	}

	/**
	* @Title: getModuleDataInfo
	* @Description: 根据页面获取数据
	* @author  彭善智
	 * @return 
	* @Data 2018年12月12日下午3:11:45
	*/
	public List<ModuleContent> getModuleDataInfo(ModuleContent moduleContent) {
		return mapper.getModuleDataInfo(moduleContent);
	}
	 /**
	   * @method: deleteModule
	   * @Description:  删除模块
	   * @Author:   彭善智
	   * @Date:     2018/12/18 20:51
	   */
	@Transactional(readOnly = false)
    public void deleteModule(String id) {
		mapper.deleteModule(id);
    }
}