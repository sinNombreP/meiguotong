package com.jeeplus.modules.base.pay.controller;


import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayUtil;
import com.jeeplus.common.config.Global;
import com.jeeplus.modules.base.pay.PayModel;
import com.jeeplus.modules.base.pay.wxpay.MyConfig;
import com.jeeplus.modules.base.pay.wxpay.ObjectUtils;
import com.jeeplus.modules.base.pay.wxpay.PayCommonUtil;
import com.jeeplus.modules.base.pay.wxpay.PayInfo;
import com.jeeplus.modules.base.pay.wxpay.PayResult;


@Controller
@RequestMapping(value = "${adminPath}/sys/wxpay")
public class WeixinPayController{
	
//	@Autowired
//	private KaolaOrderService kaolaOrderService;
//	@Autowired
//	private IncomeLogService incomeLogService;
	
	private static Logger logger = LoggerFactory.getLogger(WeixinPayController.class);
	/**
	 * 统一下单接口
	 * @param payInfo
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/unifiedorder") 
	@ResponseBody 
	public Map<String, Object> unifiedorder(PayInfo payInfo, HttpServletRequest request,HttpServletResponse response) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
        PayResult result = new PayResult();
        try {
    	//1.获取相应信息、配置信息
		MyConfig config = new MyConfig();
        WXPay wxpay = new WXPay(config);
        //2.判断订单是否已经支付
        Integer  orderId = payInfo.getOrderId();  //订单ID
        //修改1
        PayModel payModel= null;//kaolaOrderService.findOrderStatusByOrderId(orderId);
        if (payModel.getStatus()>=2) {
        	resultMap.put("code","400");
            resultMap.put("msg","该订单已经支付");
            
        	return resultMap;
		}
//        Double price=payModel.getPrice()*100;
//        Double price=1.00;
        //3.调用统一下单接口
        Map<String, String> data = new HashMap<String, String>();
        data.put("body", payModel.getTitle());//订单描述
        data.put("out_trade_no", payModel.getOrderNo());
//        data.put("total_fee",Math.round(price)+"");//订单价格
        data.put("total_fee","1");//订单价格
        data.put("spbill_create_ip", "47.100.17.86");//终端Ip
        data.put("notify_url", Global.getPayConfig("notify_url"));
        //JSAPI--公众号支付、NATIVE--原生扫码支付、APP--app支付，统一下单接口trade_type的传参可参
        data.put("trade_type", "APP");  // 此处指定为扫码支付
//          {nonce_str=pBw3DykIPdZ3xvBw, appid=wx72d1f00acbb04f91, sign=C3E2BDE1F0836407F9BEE0BEB143C962, 
//    		trade_type=APP, return_msg=OK, result_code=SUCCESS, 
//    		mch_id=1494343272, return_code=SUCCESS, prepay_id=wx20171229194416ef410da8c60118788005}
            Map<String, String> resp = wxpay.unifiedOrder(data);
            result = (PayResult)ObjectUtils.mapToObject(resp, PayResult.class);
            SortedMap<Object, Object> parameterMap2 = new TreeMap<Object, Object>();  
            parameterMap2.put("appid", config.getAppID());  
            parameterMap2.put("partnerid", config.getMchID());  
            parameterMap2.put("prepayid", result.getPrepay_id()); 
            parameterMap2.put("package", "Sign=WXPay");  
            parameterMap2.put("noncestr", PayCommonUtil.CreateNoncestr());  
            //本来生成的时间戳是13位，但是ios必须是10位，所以截取了一下
            parameterMap2.put("timestamp", Long.parseLong(String.valueOf(System.currentTimeMillis()).toString().substring(0,10))); 
            String sign2 = PayCommonUtil.createSign("UTF-8",parameterMap2);
            parameterMap2.put("sign", sign2);  
            resultMap.put("code","0");
            resultMap.put("msg",parameterMap2);
            System.out.println(resp);
        } catch (Exception e) {
        	System.out.println(e.getMessage());
        	resultMap.put("code","-1");
            resultMap.put("msg","ERROR");
           // e.printStackTrace();
        }
        //4.返回prepay_id 以及相应信息
        return resultMap;
	}
	
	/**
	 * 支付回调
	 * @param payInfo
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/notify") 
	public void notify(PayInfo payInfo, HttpServletRequest request,HttpServletResponse response) throws Exception{
        MyConfig config = new MyConfig();
        WXPay wxpay = new WXPay(config);
        String resXml = "";
        //Map<String, String> backxml = new HashMap<String, String>();
        BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
        InputStream inStream;
        try {
            inStream = request.getInputStream();
            ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];int len = 0;
            while ((len = inStream.read(buffer)) != -1) {
                outSteam.write(buffer, 0, len);
            }
            outSteam.close();
            inStream.close();
            String result = new String(outSteam.toByteArray(), "utf-8");// 获取微信调用我们notify_url的返回信息
            Map<String, String> notifyMap = WXPayUtil.xmlToMap(result);
            System.out.println("notifyMap:"+notifyMap);
            if (wxpay.isPayResultNotifySignatureValid(notifyMap)) {
            	 System.out.println("签名正确");
            	 // 签名正确
            	//backxml.put("return_code", "SUCCESS");
                //backxml.put("return_msg", "OK");
                //String toXml = WXPayUtil.mapToXml(backxml);
                //response.getWriter().write(toXml);
//                resXml = "<xml>" 
//                		+ "<return_code><![CDATA[SUCCESS]]></return_code>"
//                		+ "<return_msg><![CDATA[OK]]></return_msg>" 
//                		+ "</xml> ";
            	resXml = setXml("SUCCESS","OK");
                //业务处理开始          注意特殊情况：订单已经退款，但收到了支付结果成功的通知，不应把商户侧订单状态从退款改成支付成功        
            	  //修改订单状态
            	System.out.println("开始改变状态");
            	String orderNo = notifyMap.get("out_trade_no");
            	String tradeNo = notifyMap.get("trade_no");
            	System.out.println(orderNo);
            	PayModel payModel=null;//kaolaOrderService.findOrderStatusByOrderNo(orderNo);
            	 
            	 
            	 //修改2
//            	 KaolaOrder kaolaOrder=new KaolaOrder();
//                 Integer orderId = payModel.getOrderId();
//                 kaolaOrder.setId(orderId+"");
//                 kaolaOrder.setPriceType(2);//付款类型
//                 kaolaOrder.setPrice(payModel.getPrice());//订单总价
//                 Date date=new Date();
//                 kaolaOrder.setPayDate(date);
//                 kaolaOrder.setUpdateDate(date);
//                 kaolaOrder.setStatusd(2);// 1.待付款  2.待发邮件 3.待确认 4.已确认 5.待评价 6.已完成  7.已取消 8.退款 9.已退订',
//                 kaolaOrder.setIsLook(2);//设置未看
//                 kaolaOrder.setTradeNo(tradeNo);
//                 Integer orderType=2;
//                 kaolaOrderService.updateOrderInfoByOrderId(kaolaOrder,orderId,payModel.getPrice()+"",orderType);
                System.out.println("结束状态");
                //业务处理结束                
            }else {
            	resXml = setXml("FAIL","验签失败");
            }
        }catch (Exception e) {
        	resXml = setXml("FAIL","异常");
		}finally {
			out.write(resXml.getBytes());
			out.flush();
			out.close();
		}
	}
	
	public  String setXml(String return_code,String return_msg){
		return "<xml><return_code><![CDATA["+return_code+"]]></return_code><return_msg><![CDATA["+return_msg+"]]></return_msg></xml>";
	}
	
	/**
	 * 订单查询接口
	 * @param payInfo
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/orderQuery") 
	@ResponseBody 
	public String orderQuery(PayInfo payInfo, HttpServletRequest request,HttpServletResponse response) throws Exception{
		String result ="";
		MyConfig config = new MyConfig();
	    WXPay wxpay = new WXPay(config);
	    
	    //修改3
        PayModel payModel=null;//kaolaOrderService.findOrderStatusByOrderId(payInfo.getOrderId());
	    Map<String, String> data = new HashMap<String, String>();
	    data.put("out_trade_no",payModel.getOrderNo());

	    try {
	        Map<String, String> resp = wxpay.orderQuery(data);
	        result=resp.get("trade_state");
	        if("SUCCESS".equals(resp.get("trade_state"))) {
	        	//支付成功 订单业务操作
	      	  //修改订单状态
//                KaolaOrder kaolaOrder=new KaolaOrder();
//                Integer orderId = payInfo.getOrderId();  //订单ID
//                kaolaOrder.setId(orderId+"");
//                kaolaOrder.setPriceType(2);//付款类型
//                kaolaOrder.setPrice(payModel.getPrice());//订单总价
//                Date date=new Date();
//                kaolaOrder.setPayDate(date);
//                kaolaOrder.setUpdateDate(date);
//                kaolaOrder.setStatusd(2);// 1.待付款  2.待发邮件 3.待确认 4.已确认 5.待评价 6.已完成  7.已取消 8.退款 9.已退订',
//                kaolaOrder.setIsLook(2);//设置未看
//                kaolaOrderService.updateOrderInfoByOrderId(kaolaOrder,orderId,payModel.getPrice()+"",1);
               
	        	
	        }
	        
	    } catch (Exception e) {
	    	result="ERROR";
	    }
	   
	    return result;
	}
	
	/**
	 * 退款操作
	 * @param payInfo
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/doRefund") 
	@ResponseBody 
	public String doRefund(PayInfo payInfo, HttpServletRequest request,HttpServletResponse response) throws Exception{
		String result ="";
		MyConfig config = new MyConfig();
		WXPay wxpay = new WXPay(config);
		
		//修改4
		 PayModel payModel=null;//kaolaOrderService.findOrderStatusByOrderId(payInfo.getOrderId());
		HashMap<String, String> data = new HashMap<String, String>();
        data.put("out_trade_no", payModel.getOrderNo());
        data.put("out_refund_no", payInfo.getOut_trade_no());
        data.put("total_fee", payModel.getPrice()*100+"");
        data.put("refund_fee", payInfo.getTotal_fee()+"");
        data.put("refund_fee_type", "CNY");
        data.put("op_user_id", config.getMchID());
        try {
            Map<String, String> r = wxpay.refund(data);
            if ("SUCCESS".equals(r.get("return_code"))) {
				//订单退款操作
        	
			}
            result = r.get("return_code");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  result;
	}

    /**
     * 查询退款
     * out_refund_no
     */
	@RequestMapping(value = "/doRefundQuery") 
	@ResponseBody 
	public String doRefundQuery(PayInfo payInfo, HttpServletRequest request,HttpServletResponse response) throws Exception{
		String result ="";
		MyConfig config = new MyConfig();
		WXPay wxpay = new WXPay(config);
		HashMap<String, String> data = new HashMap<String, String>();
        data.put("out_refund_no", payInfo.getOut_trade_no());
        try {
            Map<String, String> r = wxpay.refundQuery(data);
            if ("SUCCESS".equals(r.get("return_code"))) {
				//订单退款操作
            
			}
            result = r.get("return_code");
        } catch (Exception e) {
            e.printStackTrace();
        }
       
        return  result;
	}
	
}
