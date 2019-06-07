/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.service.draft;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.meiguotong.entity.draft.ProductDraftHotel;
import com.jeeplus.modules.meiguotong.mapper.draft.ProductDraftHotelMapper;

/**
 * 草稿酒店Service
 * @author dong
 * @version 2018-09-14
 */
@Service
@Transactional(readOnly = true)
public class ProductDraftHotelService extends CrudService<ProductDraftHotelMapper, ProductDraftHotel> {

	public ProductDraftHotel get(String id) {
		return super.get(id);
	}
	
	public List<ProductDraftHotel> findList(ProductDraftHotel productDraftHotel) {
		return super.findList(productDraftHotel);
	}
	
	public Page<ProductDraftHotel> findPage(Page<ProductDraftHotel> page, ProductDraftHotel productDraftHotel) {
		return super.findPage(page, productDraftHotel);
	}
	
	@Transactional(readOnly = false)
	public void save(ProductDraftHotel productDraftHotel) {
		super.save(productDraftHotel);
	}
	
	@Transactional(readOnly = false)
	public void delete(ProductDraftHotel productDraftHotel) {
		super.delete(productDraftHotel);
	}
	
}