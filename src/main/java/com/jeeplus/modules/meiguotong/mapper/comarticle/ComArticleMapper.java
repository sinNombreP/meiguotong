/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.mapper.comarticle;

import java.util.List;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.meiguotong.entity.comarticle.ComArticle;

/**
 * 文章管理MAPPER接口
 * @author cdq
 * @version 2018-07-30
 */
@MyBatisMapper
public interface ComArticleMapper extends BaseMapper<ComArticle> {
  //资讯管理页面
	List<ComArticle> information(ComArticle comArticle);
   //修改状态
	void status(ComArticle comArticle);
   /**
    * 网站文章列表接口
    * @param comArticle
    * @return
    */
	List<ComArticle> findComArticleList(ComArticle comArticle);
	/**
	 * 获取文章详情接口
	 * @param comArticle
	 * @return
	 */
     ComArticle getComArticle(ComArticle comArticle);
     /**
      * 公司新闻列表接口
      * @param comArticle
      * @return
      */
	List<ComArticle> findCompanyNewList(ComArticle comArticle);
	
	/**
	 * 获取关于我们信息接口
	 * @return
	 */
	public List<ComArticle> getArticle(ComArticle comArticle);
	
	public List<ComArticle> findUsList(ComArticle comArticle);
	/**
	* @Title: getComArticleList
	* @Description: 获取资讯列表
	* @author  彭善智
	* @Data 2018年12月5日下午3:44:19
	*/
	List<ComArticle> getComArticleList(ComArticle comArticle);
	
	//前端获取网站文章
	List<ComArticle> findListByLanguageid(ComArticle comArticle);
	
	//根据文章类型获取 
	ComArticle findListByContentType(ComArticle comArticle);
	
	//根据类型获取列表
	List<ComArticle> findArticleByType1();
	
	//根据日期获取列表
	List<ComArticle> findListByCreateDate(ComArticle comArticle);
	
	//根据语言获取置顶列表
	List<ComArticle> findListByTop (ComArticle comArticle);
}