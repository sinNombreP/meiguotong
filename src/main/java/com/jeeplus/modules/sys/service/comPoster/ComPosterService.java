/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.service.comPoster;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.sys.entity.comPoster.ComPoster;
import com.jeeplus.modules.sys.mapper.comPoster.ComPosterMapper;

/**
 * 广告管理Service
 * @author laiyanxin
 * @version 2018-03-06
 */
@Service
@Transactional(readOnly = true)
public class ComPosterService extends CrudService<ComPosterMapper, ComPoster> {

	public ComPoster get(String id) {
		return super.get(id);
	}
	
	public List<ComPoster> findList(ComPoster comPoster) {
		return super.findList(comPoster);
	}
	
	public Page<ComPoster> findPage(Page<ComPoster> page, ComPoster comPoster) {
		return super.findPage(page, comPoster);
	}
	
	@Transactional(readOnly = false)
	public void save(ComPoster comPoster) {
		super.save(comPoster);
	}
	
	@Transactional(readOnly = false)
	public void delete(ComPoster comPoster) {
		super.delete(comPoster);
	}

	public List<ComPoster> findPosterByType(String positionType) {
		return mapper.findPosterByType(positionType);
	}
	
}