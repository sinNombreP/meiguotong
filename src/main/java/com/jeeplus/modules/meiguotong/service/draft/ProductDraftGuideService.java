/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.service.draft;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.meiguotong.entity.draft.ProductDraftGuide;
import com.jeeplus.modules.meiguotong.mapper.draft.ProductDraftGuideMapper;

/**
 * 草稿导游Service
 * @author dong
 * @version 2018-09-14
 */
@Service
@Transactional(readOnly = true)
public class ProductDraftGuideService extends CrudService<ProductDraftGuideMapper, ProductDraftGuide> {

	public ProductDraftGuide get(String id) {
		return super.get(id);
	}
	
	public List<ProductDraftGuide> findList(ProductDraftGuide productDraftGuide) {
		return super.findList(productDraftGuide);
	}
	
	public Page<ProductDraftGuide> findPage(Page<ProductDraftGuide> page, ProductDraftGuide productDraftGuide) {
		return super.findPage(page, productDraftGuide);
	}
	
	@Transactional(readOnly = false)
	public void save(ProductDraftGuide productDraftGuide) {
		super.save(productDraftGuide);
	}
	
	@Transactional(readOnly = false)
	public void delete(ProductDraftGuide productDraftGuide) {
		super.delete(productDraftGuide);
	}
	
}