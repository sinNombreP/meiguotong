/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.service.draft;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.meiguotong.entity.draft.ProductDraftCar;
import com.jeeplus.modules.meiguotong.mapper.draft.ProductDraftCarMapper;

/**
 * 草稿租车Service
 * @author dong
 * @version 2018-09-14
 */
@Service
@Transactional(readOnly = true)
public class ProductDraftCarService extends CrudService<ProductDraftCarMapper, ProductDraftCar> {

	public ProductDraftCar get(String id) {
		return super.get(id);
	}
	
	public List<ProductDraftCar> findList(ProductDraftCar productDraftCar) {
		return super.findList(productDraftCar);
	}
	
	public Page<ProductDraftCar> findPage(Page<ProductDraftCar> page, ProductDraftCar productDraftCar) {
		return super.findPage(page, productDraftCar);
	}
	
	@Transactional(readOnly = false)
	public void save(ProductDraftCar productDraftCar) {
		super.save(productDraftCar);
	}
	
	@Transactional(readOnly = false)
	public void delete(ProductDraftCar productDraftCar) {
		super.delete(productDraftCar);
	}
	
}