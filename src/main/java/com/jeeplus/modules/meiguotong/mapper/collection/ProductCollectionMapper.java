/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.mapper.collection;

import java.util.List;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.meiguotong.entity.collection.ProductCollection;

/**
 * 我的收藏MAPPER接口
 * @author dong
 * @version 2018-09-14
 */
@MyBatisMapper
public interface ProductCollectionMapper extends BaseMapper<ProductCollection> {
	/**
	 *  获取我的路线/参团收藏列表接口
	 * @param productCollection
	 * @return
	 */
	public List<ProductCollection> myCollectionRoute(ProductCollection productCollection);
	/**
	 *  获取我的玩家收藏列表接口
	 * @param productCollection
	 * @return
	 */
	public List<ProductCollection> myCollectionGuide(ProductCollection productCollection);
	/**
	 *  获取我的游轮收藏列表接口
	 * @param productCollection
	 * @return
	 */
	public List<ProductCollection> myCollectionLiner(ProductCollection productCollection);
	/**
	 *  获取我的景点收藏列表接口
	 * @param productCollection
	 * @return
	 */
	public List<ProductCollection> myCollectionScenic(ProductCollection productCollection);
	/**
	 * 判断是否被收藏过
	 * @param productCollection
	 * @return
	 */
	public Integer judgeCollection(ProductCollection productCollection);
	/**
	 * 取消收藏(单个)
	 * @param productCollection
	 */
	public void deleteCollection(ProductCollection productCollection);
}