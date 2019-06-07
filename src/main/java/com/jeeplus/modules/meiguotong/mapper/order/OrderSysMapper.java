/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.mapper.order;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.meiguotong.entity.insurance.OrderInsurance;
import com.jeeplus.modules.meiguotong.entity.order.OrderSys;
import com.jeeplus.modules.meiguotong.entity.orderguide.OrderGuide;
import com.jeeplus.modules.meiguotong.entity.travel.OrderTravelDetails;

/**
 * 订单关联表MAPPER接口
 * @author PSZ
 * @version 2018-08-17
 */
@MyBatisMapper
public interface OrderSysMapper extends BaseMapper<OrderSys> {

 /**
  * 查找OrderId
  * @param getOrderSys
  * @return
  */
	List<OrderSys> findOrderId(OrderSys getOrderSys);


	/** 
	* @Title: getFathType 
	* @Description: 获取父ID类型
	* @author 彭善智
	* @date 2018年8月20日下午8:06:07
	*/ 
	OrderSys getFathType(OrderInsurance orderInsurance);
	  /**
     * 获取父类ID类型
     * @param orderGuide
     * @return
     */
	OrderSys getFatherType(OrderGuide orderGuide);

    /**
     * 查找保险订单数据
     * @param orderSys
     * @return
     */
	OrderSys findInsurance(OrderSys orderSys);

    /**
     * 查找保险订单数据
     * @param orderSys
     * @return
     */
	OrderSys findInsurance1(OrderSys orderSys2);
	/**
	 * 获取我的订单接口
	 * @param getOrderSys
	 * @return
	 */
	public List<OrderSys> myOrder(OrderSys getOrderSys);
	/**
	 * 获取我的售后订单接口
	 * @param getOrderSys
	 * @return
	 */
	public List<OrderSys> myOrderAfter(OrderSys getOrderSys);
	/**
	 * 获取我的非定制订单接口
	 * @param page
	 * @param orderSys
	 * @return
	 */
	public List<OrderSys> getOrderSys(OrderSys orderSys);
	/**
	 * 获取我的定制订单接口
	 * @param page
	 * @param orderSys
	 * @return
	 */
	public List<OrderSys> getOrderSysList(OrderSys orderSys);
	
	/*获取未处理订单数量*/
	public OrderSys findOrderNum(@Param("agentid")Integer id);
	
	/*获取明日出行订单数量 */
	public OrderSys findToTravelNum(@Param("agentid")Integer id);
	
	//统计某时间段订单数量 
	public OrderSys findOrderNumByDate(OrderSys orderSys);
	
	//统计某时间段订单金额 
	public OrderSys findOrderEDuByDate(OrderSys orderSys);
	
	/*获取子订单*/
	public List<OrderSys> findDetailById(@Param("id")Integer id);
	
	//获取未提现订单
	public List<OrderSys> findOrderSysByExtract(OrderSys orderSys);
	
	//获取对账单
	public List<OrderSys> findOrderSysById_Extract(@Param("orderIds")String orderIds);
	
	//修改提现状态
	public void updateExtract(Integer extract,String orderIds);
	
	//获取提现金额
	public OrderSys findSumPriceById(@Param("orderIds")String orderIds);
	
	/**
     * 根据支付订单号获取支付列表
     * @param orderSys
     * @return
     */
    public List<OrderSys> getListByPayOrderNo(OrderSys orderSys);
	
}