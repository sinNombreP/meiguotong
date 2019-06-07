package com.jeeplus.modules.base.pay;

import com.jeeplus.core.persistence.DataEntity;

public class PayModel extends DataEntity<PayModel> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer  status;//订单状态
	private String title; //订单标题
	private Double price; //订单价格
	private String orderNo;//订单号
	private Integer orderId;//订单id
	private String trade_status; //订单支付状态
	private boolean signVerified;//验签状态
	private String tradeNo;
	
	
	public String getTradeNo() {
		return tradeNo;
	}
	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}
	public boolean isSignVerified() {
		return signVerified;
	}
	public void setSignVerified(boolean signVerified) {
		this.signVerified = signVerified;
	}
	public String getTrade_status() {
		return trade_status;
	}
	public void setTrade_status(String trade_status) {
		this.trade_status = trade_status;
	}
	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	
	

}
