/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.service.score;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.sys.entity.score.Score;
import com.jeeplus.modules.sys.mapper.score.ScoreMapper;

/**
 * 积分设置Service
 * @author psz
 * @version 2018-03-06
 */
@Service
@Transactional(readOnly = true)
public class ScoreService extends CrudService<ScoreMapper, Score> {

	public Score get(String id) {
		return super.get(id);
	}
	
	public List<Score> findList(Score score) {
		return super.findList(score);
	}
	
	public Page<Score> findPage(Page<Score> page, Score score) {
		return super.findPage(page, score);
	}
	
	@Transactional(readOnly = false)
	public void save(Score score) {
		super.save(score);
	}
	
	@Transactional(readOnly = false)
	public void delete(Score score) {
		super.delete(score);
	}
	
}