/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.mapper.comcomment;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.meiguotong.entity.comcomment.ComComment;

/**
 * 评论表MAPPER接口
 * @author cdq
 * @version 2018-08-01
 */
@MyBatisMapper
public interface ComCommentMapper extends BaseMapper<ComComment> {
   /**
    * 修改状态
    * @param comComment
    */
	
	void status(ComComment comComment);
    /**
     * 攻略评论表数据
     * @param comComment
     * @return
     */
List<ComComment> strategyComment(ComComment comComment);
     /**
      * 城市评论
      * @param comComment
      * @return
      */
List<ComComment> cityComment(ComComment comComment);
	/**
	 * 邮轮评论表
	 * @param comComment
	 * @return
	 */
	List<ComComment> CruiseComment(ComComment comComment);
	/**
	 * 邮轮子类评论表
	 * @param comComment
	 * @return
	 */
	List<ComComment> CruiseChildComment(ComComment comComment);
	/** 
	* @Title: getCommentDate 
	* @Description: 列表数据
	* @author 彭善智
	* @date 2018年8月16日上午10:01:21
	*/ 
	List<ComComment> getCommentDate(ComComment comComment);
	/** 
	* @Title: updateStatus 
	* @Description: 启用禁用
	* @author 彭善智
	* @date 2018年8月16日下午2:50:40
	*/ 
	void updateStatus(ComComment comComment);
	/**
	 * 景点评论列表
	 * @param comComment
	 * @return
	 */
	List<ComComment> SceniceComment(ComComment comComment);
	/**
	 * 当地玩家评论表列表
	 * @param comComment
	 * @return
	 */
	List<ComComment> playerComment(ComComment comComment);
	/**
	 * 邮轮评论列表接口
	 * @param comComment
	 * @return
	 */
	List<ComComment> findCruiseCommentList(ComComment comComment);
	/**
	 * 评论导游接口
	 * @param comComment
	 * @return
	 */
	List<ComComment> findCommentList(ComComment comComment);
	
	/**
	 * 获取消息中心评论消息接口
	 * @param comComment
	 * @return
	 */
	public List<ComComment> getCommentList(ComComment comComment);
	/**
	 *  获取各类型产品的评论
	 * @param comComment
	 * @return
	 */
	public List<ComComment> selectRouteComment(ComComment comComment);
	/**
	 *  获取评论的子评论
	 * @param comComment
	 * @return
	 */
	public List<ComComment> selectChildComment(ComComment comComment);
	
}