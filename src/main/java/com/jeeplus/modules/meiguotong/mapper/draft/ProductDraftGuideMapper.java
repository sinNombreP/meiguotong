/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.mapper.draft;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.meiguotong.entity.draft.ProductDraft;
import com.jeeplus.modules.meiguotong.entity.draft.ProductDraftGuide;

/**
 * 草稿导游MAPPER接口
 * @author dong
 * @version 2018-09-14
 */
@MyBatisMapper
public interface ProductDraftGuideMapper extends BaseMapper<ProductDraftGuide> {
	public void deleteByDraftId(ProductDraft productDraft);
}