package com.jeeplus.modules.base.pay.controller;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.modules.base.pay.PayModel;
import com.jeeplus.modules.base.pay.alipay.AlipayConfig;
import com.jeeplus.modules.base.pay.wxpay.PayInfo;


@Controller
@RequestMapping(value = "${adminPath}/pay/alipay")
public class AlipayController {
//	@Autowired
//	private KaolaOrderService kaolaOrderService;

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * 支付宝支付
	 * @param out_trade_no 商户订单号 必传
	 * @param total_amount 付款金额  必传
	 * @param subject 订单名称 必填
	 * @param body 商品描述
	 * @url http://localhost:8088/xsh_sys/a/sys/alipay/pay?out_trade_no= &total_amount= &subject= &body=
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws AlipayApiException
	 */
	@RequestMapping(value = "pay")
	public void pay(PayInfo payInfo, HttpServletRequest request, HttpServletResponse response) throws IOException, AlipayApiException {  
		//获得初始化的AlipayClient
		AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);
		//设置请求参数
		AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
		alipayRequest.setReturnUrl(AlipayConfig.return_url);
		alipayRequest.setNotifyUrl(AlipayConfig.notify_url);
		 String result = "";
		Integer  orderId = payInfo.getOrderId();  //订单ID
        PayModel payModel=null;//kaolaOrderService.findOrderStatusByOrderId(orderId);
        if (payModel.getStatus()>=2) {
        	result = "该订单已经支付";
		}else {
			String total_amount="0.01"; //total_amount 查询订单金额
			String subject="测试";//payModel.getTitle()
			String body="";
			alipayRequest.setBizContent("{\"out_trade_no\":\""+ payModel.getOrderNo() +"\"," 
					+ "\"total_amount\":\""+ total_amount +"\"," 
					+ "\"subject\":\""+ subject +"\"," 
					+ "\"body\":\""+ body +"\"," 
					+ "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
			result = alipayClient.pageExecute(alipayRequest).getBody();
		}
			
		//请求
		response.setContentType("text/html;");
	    PrintWriter out = response.getWriter();
	    out.println(result); 
	    out.flush();  
	    out.close();  
	}  
	
	/**
	 * APP支付调用生成订单号
	 * @param payInfo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/unifiedorder") 
	@ResponseBody 
	public Map<String, Object> unifiedorder(PayInfo payInfo) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
	        //2.判断订单是否已经支付
	        Integer  orderId = payInfo.getOrderId();  //订单ID
	        PayModel payModel=null;//kaolaOrderService.findOrderStatusByOrderId(orderId);
	        if (payModel.getStatus()>=2) {
	        	resultMap.put("code","400");
	            resultMap.put("msg","该订单已经支付");
	            
	        	return resultMap;
			}
	        
	    	//实例化客户端
	        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);
	    	//实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
	    	AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
	    	//SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
	    	AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
	    	model.setBody("");//可空
	    	model.setSubject("App支付测试Java");//payModel.getTitle()
	    	model.setOutTradeNo(payModel.getOrderNo());
	    	model.setTimeoutExpress("30m");
	    	model.setTotalAmount("0.01");
	    	model.setProductCode("QUICK_MSECURITY_PAY");
	    	request.setBizModel(model);
	    	request.setNotifyUrl(AlipayConfig.notify_url);
	        //这里和普通的接口调用不同，使用的是sdkExecute
	        AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
	        logger.error("************APP生成交易订单成："+response.getBody());
	        System.out.println(response.getBody());//就是orderString 可以直接给客户端请求，无需再做处理。
	        resultMap.put("msg",response.getBody());
        } catch (AlipayApiException e) {
        		logger.error("************APP生成交易订单失败："+payInfo.getOrderId());
    	    	resultMap.put("code","-1");
                resultMap.put("msg","ERROR");
    	 }
    	
        return resultMap;
	}
	
	/*
	*************************页面功能说明*************************
	 * 创建该页面文件时，请留心该页面文件中无任何HTML代码及空格。
	 * 该页面不能在本机电脑测试，请到服务器上做测试。请确保外部可以访问该页面。
	 * 如果没有收到该页面返回的 success 
	 * 建议该页面只做支付成功的业务逻辑处理，退款的处理请以调用退款查询接口的结果为准。
	 */
	@RequestMapping(value = "notifyUrl")
	public void notify(HttpServletRequest request, HttpServletResponse response ) throws Exception {  
		//获取支付宝POST过来反馈信息
		Map<String,String> params = new HashMap<String,String>();
		Map<String,String[]> requestParams = request.getParameterMap();
		for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			//乱码解决，这段代码在出现乱码时使用
//			valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
			params.put(name, valueStr);
		}
		System.out.println(params);
		boolean signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key, AlipayConfig.charset, AlipayConfig.sign_type); //调用SDK验证签名
		response.setContentType("text/html;charset=utf-8");
	    PrintWriter out = response.getWriter();
	    
		/* 实际验证过程建议商户务必添加以下校验：
		1、需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号，
		2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额），
		3、校验通知中的seller_id（或者seller_email) 是否为out_trade_no这笔单据的对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email）
		4、验证app_id是否为该商户本身。
		*/
		if(signVerified) {//验证成功
			//商户订单号
			String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
		
			//支付宝交易号
			String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");
			String tradeNo = trade_no;
			String orderNo = out_trade_no;

			//交易状态
			String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");
			
			PayModel payModel=null;//kaolaOrderService.findOrderStatusByOrderNo(out_trade_no);
			if (trade_status.equals("TRADE_FINISHED") && payModel.getStatus() < 2) {
				// 业务处理开始 注意特殊情况：订单已经退款，但收到了支付结果成功的通知，不应把商户侧订单状态从退款改成支付成功
				// 修改订单状态
				System.out.println(orderNo);
//				KaolaOrder kaolaOrder = new KaolaOrder();
//				Integer orderId = payModel.getOrderId();
//				kaolaOrder.setId(orderId + "");
//				kaolaOrder.setPriceType(1);// 付款类型
//				kaolaOrder.setPrice(payModel.getPrice());// 订单总价
//				Date date = new Date();
//				kaolaOrder.setPayDate(date);
//				kaolaOrder.setUpdateDate(date);
//				kaolaOrder.setStatusd(2);// 1.待付款 2.待发邮件 3.待确认 4.已确认 5.待评价 6.已完成 7.已取消 8.退款 9.已退订',
//				kaolaOrder.setIsLook(2);// 设置未看
//				System.out.println("1"+tradeNo);
//				kaolaOrder.setTradeNo(tradeNo);
//				System.out.println("2"+tradeNo);
//				Integer orderType = 1;
				//kaolaOrderService.updateOrderInfoByOrderId(kaolaOrder, orderId, payModel.getPrice() + "", orderType);
				logger.info("************交易成功："+orderNo);

			}else if (trade_status.equals("TRADE_SUCCESS")){
				//判断该笔订单是否在商户网站中已经做过处理
				if(payModel.getStatus()<2) {
					// 业务处理开始 注意特殊情况：订单已经退款，但收到了支付结果成功的通知，不应把商户侧订单状态从退款改成支付成功
					// 修改订单状态
//					KaolaOrder kaolaOrder = new KaolaOrder();
//					Integer orderId = payModel.getOrderId();
//					kaolaOrder.setId(orderId + "");
//					kaolaOrder.setPriceType(1);// 付款类型
//					kaolaOrder.setPrice(payModel.getPrice());// 订单总价
//					Date date = new Date();
//					kaolaOrder.setPayDate(date);
//					kaolaOrder.setUpdateDate(date);
//					kaolaOrder.setStatusd(2);// 1.待付款 2.待发邮件 3.待确认 4.已确认 5.待评价 6.已完成 7.已取消 8.退款 9.已退订',
//					kaolaOrder.setIsLook(2);// 设置未看
//					kaolaOrder.setTradeNo(tradeNo);
//					Integer orderType = 1;
//					kaolaOrderService.updateOrderInfoByOrderId(kaolaOrder, orderId, payModel.getPrice() + "", orderType);
					logger.info("************交易成功："+orderNo);
				}
				out.println(out_trade_no+":"+trade_no+":"+trade_status+"付款成功");

			}
			out.println("success");
			
		}else {//验证失败
			logger.error("************交易失败订单号："+request.getParameter("out_trade_no"));
			out.println("fail");
		}
		out.flush();  
	    out.close();
	}
	
	@RequestMapping(value = "returnUrl")
	public String return_url(HttpServletRequest request, HttpServletResponse response,RedirectAttributes redirectAttributes) throws IOException, AlipayApiException {  
	    //获取支付宝GET过来反馈信息
		Map<String,String> params = new HashMap<String,String>();
		Map<String,String[]> requestParams = request.getParameterMap();
		for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			//乱码解决，这段代码在出现乱码时使用
			//valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
			params.put(name, valueStr);
		}
		
		boolean signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key, AlipayConfig.charset, AlipayConfig.sign_type); //调用SDK验证签名
	    
		//——请在这里编写您的程序（以下代码仅作参考）——
	    if(signVerified) {
			//商户订单号
			String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
			
			return "redirect:https://www.xiaoniutingche.com/kaola/f/web/pay/success?orderid="+out_trade_no;
		}else {
			return "";
		}
	}
	
	/**
	 * 支付宝退款操作
	 * @param out_trade_no 商户订单号 201711221474311
	 * @param trade_no 支付宝交易号   2017112221001104680214721909
	 * @param refund_amount 需要退款的金额
	 * @param refund_reason 退款的原因说明
	 * @param out_request_no  标识一次退款请求，同一笔交易多次退款需要保证唯一，如需部分退款，则此参数必传
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws AlipayApiException
	 */
	@RequestMapping(value = "refund")
	@ResponseBody
	public AjaxJson refund(String out_trade_no,String trade_no,String refund_amount,String refund_reason, String out_request_no, HttpServletRequest request, HttpServletResponse response) throws IOException, AlipayApiException {  
		AjaxJson ajaxJson = new AjaxJson();

		//获得初始化的AlipayClient
		AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);
		AlipayTradeRefundRequest alipaRequest = new AlipayTradeRefundRequest();
//		 "\"out_trade_no\":\""+ out_trade_no +"\"," +
		alipaRequest.setBizContent("{" +
		 "\"trade_no\":\""+ trade_no +"\"," +
		 "\"refund_amount\":\""+ refund_amount +"\"," +
		 "\"refund_reason\":\""+ refund_reason +"\"," +
		 "\"out_request_no\":\""+ out_request_no +"\""
		 +"}");

		AlipayTradeRefundResponse aliResponse = alipayClient.execute(alipaRequest);
		if(aliResponse.isSuccess()){
			ajaxJson.setSuccess(true);
			ajaxJson.setMsg("退款成功");
			logger.info("************退款成功："+trade_no);
		} else {
			ajaxJson.setSuccess(false);
			ajaxJson.setMsg("退款失败");
			logger.error("************退款失败："+trade_no);
		}
		return ajaxJson;
	}  
}
