/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.sys.entity.MemberCollection;
import com.jeeplus.modules.sys.entity.MemberDigg;
import com.jeeplus.modules.sys.mapper.MemberCollectionMapper;

/**
 * 个人收藏Service
 * @author xudemo
 * @version 2018-02-27
 */
@Service
@Transactional(readOnly = true)
public class MemberCollectionService extends CrudService<MemberCollectionMapper, MemberCollection> {

	public MemberCollection get(String id) {
		return super.get(id);
	}
	
	public List<MemberCollection> findList(MemberCollection memberCollection) {
		return super.findList(memberCollection);
	}
	
	public Page<MemberCollection> findPage(Page<MemberCollection> page, MemberCollection memberCollection) {
		return super.findPage(page, memberCollection);
	}
	
	public MemberCollection findOne(MemberCollection memberCollection){
		return mapper.findOne(memberCollection);
	}
	
	@Transactional(readOnly = false)
	public void save(MemberCollection memberCollection) {
		super.save(memberCollection);
	}
	
	@Transactional(readOnly = false)
	public void delete(MemberCollection memberCollection) {
		super.delete(memberCollection);
	}
	
	@Transactional(readOnly = false)
	public void deleteCollection(MemberCollection memberCollection) {
		mapper.delete(memberCollection);
	}
	/**
	 * 判断是否收藏过（>0收藏过）
	 * @param memberCollection
	 * @return
	 */
	public Integer getCount(MemberCollection memberCollection) {
		Integer count = mapper.getCount(memberCollection);
		return count;
	}
	/**
	 * 添加收藏信息（接口）
	 * @param memberDigg
	 */
	@Transactional(readOnly = false)
	public void insertCollection(MemberCollection memberCollection) {
		mapper.insert(memberCollection);
	}
	/**
	 * 取消收藏（接口  单个取消）
	 * @param memberDigg
	 */
	@Transactional(readOnly = false)
	public void delCollectionOne(MemberCollection memberCollection) {
		mapper.delete(memberCollection);
	}
	/**
	 * 取消收藏（接口 多个一起取消）
	 * @param memberDigg
	 */
	@Transactional(readOnly = false)
	public void delCollection(MemberCollection memberCollection) {
		String typeids=memberCollection.getTypeids();
		String[] ids=typeids.split(",");
		for(String id:ids){
			memberCollection.setTypeid(Integer.parseInt(id)); 
			mapper.delete(memberCollection);
		}
	}
}