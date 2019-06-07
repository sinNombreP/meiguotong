/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.mapper.draft;

import java.util.List;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.meiguotong.entity.draft.ProductDraft;

/**
 * 草稿MAPPER接口
 * @author dong
 * @version 2018-09-14
 */
@MyBatisMapper
public interface ProductDraftMapper extends BaseMapper<ProductDraft> {
	/**
	 * 获取我的草稿列表接口
	 * @param productDraft
	 * @return
	 */
	public List<ProductDraft> myDraft(ProductDraft productDraft);
}