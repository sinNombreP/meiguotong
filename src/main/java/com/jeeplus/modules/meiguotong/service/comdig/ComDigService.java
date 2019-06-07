/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.service.comdig;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.meiguotong.entity.citystrategy.CityStrategy;
import com.jeeplus.modules.meiguotong.entity.comcomment.ComComment;
import com.jeeplus.modules.meiguotong.entity.comdig.ComDig;
import com.jeeplus.modules.meiguotong.mapper.comdig.ComDigMapper;
import com.jeeplus.modules.meiguotong.service.citystrategy.CityStrategyService;
import com.jeeplus.modules.meiguotong.service.comcomment.ComCommentService;

/**
 * 点赞Service
 * @author dong
 * @version 2018-09-27
 */
@Service
@Transactional(readOnly = true)
public class ComDigService extends CrudService<ComDigMapper, ComDig> {

	@Autowired
	private ComCommentService comCommentService;
	@Autowired
	private CityStrategyService cityStrategyService;
	
	public ComDig get(String id) {
		return super.get(id);
	}
	/**
	 * 查询是否点赞过
	 * @param id
	 * @return
	 */
	public Integer getCount(ComDig comDig) {
		return mapper.getCount(comDig);
	}
	public List<ComDig> findList(ComDig comDig) {
		return super.findList(comDig);
	}
	
	public Page<ComDig> findPage(Page<ComDig> page, ComDig comDig) {
		return super.findPage(page, comDig);
	}
	
	@Transactional(readOnly = false)
	public void save(ComDig comDig) {
		super.save(comDig);
		if (comDig.getType()==1) {
			ComComment comComment =new ComComment();
			comComment.setId(comDig.getTypeId().toString());
			comComment.setDigNum(1);
			comCommentService.changeDigNum(comComment);
		}else if (comDig.getType()==2) {
			CityStrategy cityStrategy =new CityStrategy();
			cityStrategy.setId(comDig.getTypeId().toString());
			cityStrategy.setDigNum(1);
			cityStrategyService.changeDigNum(cityStrategy);
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(ComDig comDig) {
		super.delete(comDig);
	}
	@Transactional(readOnly = false)
	public void deleteDig(ComDig comDig) {
		mapper.deleteDig(comDig);
		if (comDig.getType()==1) {
			ComComment comComment =new ComComment();
			comComment.setId(comDig.getTypeId().toString());
			comComment.setDigNum(-1);
			comCommentService.changeDigNum(comComment);
		}else if (comDig.getType()==2) {
			CityStrategy cityStrategy =new CityStrategy();
			cityStrategy.setId(comDig.getTypeId().toString());
			cityStrategy.setDigNum(-1);
			cityStrategyService.changeDigNum(cityStrategy);
		}
	}
	
}