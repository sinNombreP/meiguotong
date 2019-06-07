/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.service.draft;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.meiguotong.entity.draft.ProductDraft;
import com.jeeplus.modules.meiguotong.entity.draft.ProductDraftCar;
import com.jeeplus.modules.meiguotong.entity.draft.ProductDraftDetails;
import com.jeeplus.modules.meiguotong.entity.draft.ProductDraftGuide;
import com.jeeplus.modules.meiguotong.entity.draft.ProductDraftHotel;
import com.jeeplus.modules.meiguotong.mapper.draft.ProductDraftCarMapper;
import com.jeeplus.modules.meiguotong.mapper.draft.ProductDraftDetailsMapper;
import com.jeeplus.modules.meiguotong.mapper.draft.ProductDraftGuideMapper;
import com.jeeplus.modules.meiguotong.mapper.draft.ProductDraftHotelMapper;
import com.jeeplus.modules.meiguotong.mapper.draft.ProductDraftMapper;

/**
 * 草稿Service
 * @author dong
 * @version 2018-09-14
 */
@Service
@Transactional(readOnly = true)
public class ProductDraftService extends CrudService<ProductDraftMapper, ProductDraft> {
	@Autowired
	private ProductDraftCarMapper productDraftCarMapper;
	@Autowired
	private ProductDraftDetailsMapper productDraftDetailsMapper;
	@Autowired
	private ProductDraftGuideMapper productDraftGuideMapper;
	@Autowired
	private ProductDraftHotelMapper productDraftHotelMapper;
	
	public ProductDraft get(String id) {
		return super.get(id);
	}
	/**
	 * 获取我的草稿列表接口
	 * @param productDraft
	 * @return
	 */
	public Page<ProductDraft> myDraft(Page<ProductDraft> page, ProductDraft productDraft) {
		dataRuleFilter(productDraft);
		productDraft.setPage(page);
		page.setList(mapper.myDraft(productDraft));
		return page;
	}
	
	public List<ProductDraft> findList(ProductDraft productDraft) {
		return super.findList(productDraft);
	}
	
	public Page<ProductDraft> findPage(Page<ProductDraft> page, ProductDraft productDraft) {
		return super.findPage(page, productDraft);
	}
	/**
	 * 删除我的草稿接口
	 * @param productDraft
	 */
	@Transactional(readOnly = false)
	public void deleteMyDraft(ProductDraft productDraft) {
		String[] ids = productDraft.getIds().split(",");
		for(String a:ids){
			productDraft.setId(a);
			mapper.delete(productDraft);
			productDraftCarMapper.deleteByDraftId(productDraft);
			productDraftDetailsMapper.deleteByDraftId(productDraft);
			productDraftGuideMapper.deleteByDraftId(productDraft);
			productDraftHotelMapper.deleteByDraftId(productDraft);
		}
	}
	@Transactional(readOnly = false)
	public void save(ProductDraft productDraft) {
		super.save(productDraft);
	}
	
	@Transactional(readOnly = false)
	public void delete(ProductDraft productDraft) {
		super.delete(productDraft);
	}
	
	@Transactional(readOnly = false)
	public void saveProductDraft(ProductDraft productDraft,List<ProductDraftCar> carList,
			List<ProductDraftDetails> detailsList,ProductDraftGuide productDraftGuide,
			List<ProductDraftHotel> hotelList) {
		
		super.save(productDraft);
		if(carList!=null&&carList.size()>0){
			for(ProductDraftCar a:carList){
				a.setDraftid(Integer.parseInt(productDraft.getId()));
				productDraftCarMapper.insert(a);
			}
		}
		if(detailsList!=null&&detailsList.size()>0){
			for(ProductDraftDetails a:detailsList){
				a.setDraftid(Integer.parseInt(productDraft.getId()));
				productDraftDetailsMapper.insert(a);
			}
		}
		if(productDraftGuide!=null){
			productDraftGuideMapper.insert(productDraftGuide);
		}
		if(hotelList!=null&&hotelList.size()>0){
			for(ProductDraftHotel a:hotelList){
				a.setDraftid(Integer.parseInt(productDraft.getId()));
				productDraftHotelMapper.insert(a);
			}
		}
	}
	
}