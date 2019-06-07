/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.service.comarticle;

import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ser.std.MapProperty;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.meiguotong.entity.comarticle.ComArticle;
import com.jeeplus.modules.meiguotong.mapper.comarticle.ComArticleMapper;

/**
 * 文章管理Service
 * @author cdq
 * @version 2018-07-30
 */
@Service
@Transactional(readOnly = true)
public class ComArticleService extends CrudService<ComArticleMapper, ComArticle> {

	public ComArticle get(String id) {
		return super.get(id);
	}
	/**
	 * 获取关于我们信息接口
	 * @return
	 */
	public List<ComArticle> getArticle(ComArticle comArticle) {
		List<ComArticle> list = mapper.getArticle(comArticle);
		if(list!=null&&list.size()>0){
			for(ComArticle a:list){
				a.setContent(StringEscapeUtils.unescapeHtml(a.getContent()));
			}
		}
		return list;
	}
	
	public List<ComArticle> findList(ComArticle comArticle) {
		return super.findList(comArticle);
	}
	
	public Page<ComArticle> findPage(Page<ComArticle> page, ComArticle comArticle) {
		return super.findPage(page, comArticle);
	}
	public Page<ComArticle> findUsPage(Page<ComArticle> page, ComArticle comArticle) {
		dataRuleFilter(comArticle);
		comArticle.setPage(page);
		page.setList(mapper.findUsList(comArticle));
		return page;
	}
	
	@Transactional(readOnly = false)
	public void save(ComArticle comArticle) {
		super.save(comArticle);
	}
	
	@Transactional(readOnly = false)
	public void delete(ComArticle comArticle) {
		super.delete(comArticle);
	}
   /**
    * 资讯管理页面
    * @param page
    * @param comArticle
    * @return
    */
	public Page<ComArticle> information(Page<ComArticle> page, ComArticle comArticle) {
		dataRuleFilter(comArticle);
		comArticle.setPage(page);
		page.setList(mapper.information(comArticle));
		return page;
	}
      //修改状态
	@Transactional(readOnly = false)
        public void status(ComArticle comArticle) {
	    mapper.status(comArticle);
      }
	
  /**
   * 网站文章列表接口
   * @param page
   * @param comArticle
   * @return
   */
	public Page<ComArticle> findComArticleList(Page<ComArticle> page, ComArticle comArticle) {
		dataRuleFilter(comArticle);
		comArticle.setPage(page);
		page.setList(mapper.findComArticleList(comArticle));
		return page;
	}
/**
 * 获取文章详情接口
 * @param comArticle
 * @return
 */
public ComArticle getComArtilce(ComArticle comArticle) {
	return mapper.getComArticle(comArticle);
}
/**
 * 公司新闻列表接口
 * @param page
 * @param comArticle
 * @return
 */
public Page<ComArticle> findCompanyNewList(Page<ComArticle> page, ComArticle comArticle) {
	dataRuleFilter(comArticle);
	comArticle.setPage(page);
	page.setList(mapper.findCompanyNewList(comArticle));
	return page;
}
	/**
	* @Title: getComArticleList
	* @Description: 获取资讯列表
	* @author  彭善智
	* @Data 2018年12月5日下午3:43:42
	*/
	public List<ComArticle> getComArticleList(ComArticle comArticle) {
		return mapper.getComArticleList(comArticle);
	}
	
	//前端获取网站文章
	public Page<ComArticle> findListByLanguageid(Page<ComArticle> page, ComArticle comArticle) {
		dataRuleFilter(comArticle);
		comArticle.setPage(page);
		List<ComArticle> comArticle2=mapper.findListByLanguageid(comArticle);
		for (ComArticle c: comArticle2) {
			c.setLanguageId(comArticle.getLanguageId());
			c.setComArticleList(mapper.findListByCreateDate(c));
		}
		page.setList(comArticle2);
		return page;
	}
	
	//根据文章类型获取 
	public ComArticle findListByContentType(ComArticle comArticle){
		return mapper.findListByContentType(comArticle);
	};
		
	//根据类型获取列表
	public List<ComArticle> findArticleByType1(ComArticle comArticle){
		return mapper.findArticleByType1();
	};
	
	//根据日期获取列表
	public List<ComArticle> findListByCreateDate(ComArticle comArticle){
		return mapper.findListByCreateDate(comArticle);
	};
		
	//根据语言获取置顶列表
	public List<ComArticle> findListByTop (ComArticle comArticle){
		return mapper.findListByTop(comArticle);
	};
}