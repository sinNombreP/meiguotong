/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.service.draft;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.meiguotong.entity.draft.ProductDraftDetails;
import com.jeeplus.modules.meiguotong.mapper.draft.ProductDraftDetailsMapper;

/**
 * 草稿详情Service
 * @author dong
 * @version 2018-09-14
 */
@Service
@Transactional(readOnly = true)
public class ProductDraftDetailsService extends CrudService<ProductDraftDetailsMapper, ProductDraftDetails> {

	public ProductDraftDetails get(String id) {
		return super.get(id);
	}
	
	public List<ProductDraftDetails> findList(ProductDraftDetails productDraftDetails) {
		return super.findList(productDraftDetails);
	}
	
	public Page<ProductDraftDetails> findPage(Page<ProductDraftDetails> page, ProductDraftDetails productDraftDetails) {
		return super.findPage(page, productDraftDetails);
	}
	
	@Transactional(readOnly = false)
	public void save(ProductDraftDetails productDraftDetails) {
		super.save(productDraftDetails);
	}
	
	@Transactional(readOnly = false)
	public void delete(ProductDraftDetails productDraftDetails) {
		super.delete(productDraftDetails);
	}
	
}