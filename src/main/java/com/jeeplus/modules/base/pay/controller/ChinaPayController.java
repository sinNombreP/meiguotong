//package com.jeeplus.modules.base.pay.controller;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.net.URLDecoder;
//import java.text.SimpleDateFormat;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.Enumeration;
//import java.util.HashMap;
//import java.util.Map;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.http.client.methods.CloseableHttpResponse;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import com.alipay.api.AlipayApiException;
//import com.jeeplus.common.json.AjaxJson;
//import com.jeeplus.modules.base.pay.alipay.AlipayConfig;
//import com.jeeplus.modules.base.pay.chinapay.entity.PayRequest;
//import com.jeeplus.modules.base.pay.chinapay.utils.HttpSendUtil;
//import com.jeeplus.modules.base.pay.chinapay.utils.PathUtil;
//import com.jeeplus.modules.base.pay.chinapay.utils.SignUtil;
//import com.jeeplus.modules.base.pay.chinapay.utils.StringUtil;
//
//@Controller
//@RequestMapping(value = "${adminPath}/sys/chinapay")
//public class ChinaPayController {
//	
//	private static final String transResvered = "trans_";
//	private static final String cardResvered = "card_"; 
//	private static final String transResveredKey = "TranReserved";
//	private static final String cardResveredKey = "CardTranData"; 
//	private static final String signatureField = "Signature";
//	private static Logger logger = LoggerFactory.getLogger(ChinaPayController.class);
//	
//	
////	@Autowired
////	private SysOrderRalationDao sysOrderRalationDao;
//	
//	/**
//	 * 银联支付
//	 * @param orderNo 订单号
//	 * @param orderAmt  付款金额
//	 * @param subject 订单名称
//	 * @param body 商品描述
//	 * @url http://localhost:8088/xsh_sys/a/sys/alipay/pay?out_trade_no= &total_amount= &subject= &body=
//	 * @param request
//	 * @param response
//	 * @throws IOException
//	 * @throws AlipayApiException
//	 */
//	@RequestMapping(value = "pay")
//	public void pay(String out_trade_no,String total_amount, HttpServletRequest request, HttpServletResponse response) throws IOException {  		
//		Map<String, Object> sendMap = new HashMap<String,Object>();
//		
//		try {
//		//	SysOrderRalation sysOrderRalation = sysOrderRalationService.findUniqueByProperty("order_header_id", out_trade_no);
////			int money = (int)(sysOrderRalation.getTotalMoney()*100);
////			money =1;
//			
//			//修改
//			
//			
//			//SysOrderRalation sysOrderRalation = sysOrderRalationDao.get(out_trade_no);
//	    	 total_amount = "1";//String.valueOf(sysOrderRalation.getTotalMoney()*100);
//	    	 total_amount = total_amount.substring(0,total_amount.indexOf("."));
//			
//			sendMap.put("OrderAmt", total_amount);
//			sendMap.put("MerOrderNo", out_trade_no);
//
//			Calendar calendar = Calendar.getInstance();
//			Date date = calendar.getTime();
//			String tranDate = StringUtil.getRelevantDate(date);
//			String tranTime = StringUtil.getRelevantTime(date);
//			sendMap.put("TranDate", tranDate);
//			sendMap.put("TranTime",tranTime);
//			sendMap.put("MerPageUrl", PathUtil.getValue("MerPageUrl"));
//			sendMap.put("MerBgUrl", PathUtil.getValue("MerBgUrl"));
//			
//			//sendMap.put("Host", request.getRemoteAddr());
//			sendMap.put("TranType", "0001");
//			sendMap.put("AcqCode", "000000000000014");
//			sendMap.put("BusiType", "0001");
//			sendMap.put("Version", "20140728");
//			sendMap.put("MerId", "481611711290001");
//			sendMap.put("AccessType", "0");
//			sendMap.put("MerResv", "MerResv");
//			sendMap.put("CurryNo", "CNY");
//
//			//商户签名
//			String signature = SignUtil.sign(sendMap);
//			sendMap.put(signatureField, signature);
//			System.out.println(signature);
//			String result = "<form name=\"payment\" action=\""+PathUtil.getValue("pay_url")+"\" method=\"POST\">";
//			
//			for(Map.Entry<String, Object> entry:sendMap.entrySet()){
//				request.setAttribute(entry.getKey(), entry.getValue());
//				result+="<input type=\"hidden\" name = '"+entry.getKey()+"' value ='"+entry.getValue()+"'/>\r\n";
//			}
//			
//			result+="<input type=\"submit\" value=\"立即支付\" style=\"display:none\" ></form>"
//					+ "<script>document.forms[0].submit();</script>";	
//
//			response.setContentType("text/html;charset=utf-8");
//		    PrintWriter out = response.getWriter();
//		    out.println(result); 
//		    out.flush();  
//		    out.close(); 
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}  
////	@Autowired
////	private SysOrderRalationService sysOrderRalationService;
//	
//	@RequestMapping(value = "bgReturnUrl")
//	public void bgReturnUrl(PayRequest payRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, AlipayApiException {  
//		request.setCharacterEncoding("utf-8");
//		response.setCharacterEncoding("utf-8");
//		//解析 返回报文
//		Enumeration<String> requestNames = request.getParameterNames();
//		Map<String, String> resultMap = new HashMap<String, String>();
//		while(requestNames.hasMoreElements()){
//			String name = requestNames.nextElement();
//			String value = request.getParameter(name);
//			value = URLDecoder.decode(value, "UTF-8");
//			resultMap.put(name, value);
//		}
//		System.out.println("*************bgReturnUrl result:"+resultMap);
//		//验证签名
//		if(SignUtil.verify(resultMap)){
//			if("0000".equals(resultMap.get("OrderStatus"))) {
//				String MerOrderNo=resultMap.get("MerOrderNo");
//				String TranDate=resultMap.get("TranDate");
//				String TranTime=resultMap.get("TranTime");
//				//String OrderAmt=resultMap.get("OrderAmt");
////				SysOrderRalation sysOrderRalation = sysOrderRalationService.findUniqueByProperty("order_header_id", MerOrderNo);
////				int payState = sysOrderRalation.getPayState();
////				if(payState==0) {
////					try {
////						Date strToDate = DateUtils.StrToDate(TranDate+TranTime);
////						sysOrderRalation.setCreateDate(strToDate);
////						//sysOrderRalation.setCreateDate(createDate);
////						sysOrderRalationService.updateChinaPayOrder(sysOrderRalation);
////						logger.info(MerOrderNo+":"+MerOrderNo+":交易完成");
////					} catch (Exception e) {
////						System.out.println(MerOrderNo+":"+MerOrderNo+":"+resultMap.get("OrderStatus")+"交易异常");
////						response.getWriter().write("fail");
////						return;
////					}
////				}
////				System.out.println(MerOrderNo+":"+MerOrderNo+":"+resultMap.get("OrderStatus")+"交易完成");
//			}
//			logger.info("银联验证成功");
//			response.getWriter().write("success");
//		}else{
//			response.getWriter().write("fail");
//		}
//	}  
//	
//	@RequestMapping(value = "pageReturnUrl")
//	public String pageReturnUrl(PayRequest payRequest, HttpServletRequest request, HttpServletResponse response) throws IOException {  
//		request.setCharacterEncoding("utf-8");
//		response.setCharacterEncoding("utf-8");
//		//解析 返回报文
//		Enumeration<String> requestNames = request.getParameterNames();
//		Map<String, String> resultMap = new HashMap<String, String>();
//		while(requestNames.hasMoreElements()){
//			String name = requestNames.nextElement();
//			String value = request.getParameter(name);
////			value = URLDecoder.decode(value, "UTF-8");
//			resultMap.put(name, value);
//		}
//		System.out.println("*************pageReturnUrl result:"+resultMap);
//		//验证签名
//		if(SignUtil.verify(resultMap)){
//			return "redirect:"+AlipayConfig.returnPath+"?palyState=1";
//		}else{
//			return "redirect:"+AlipayConfig.returnPath+"?palyState=2";
//		}
//	}  
//	
//	@RequestMapping(value = "refund")
//	@ResponseBody
//	public AjaxJson refund(String out_trade_no, String refund_amount, HttpServletRequest request,
//			HttpServletResponse response) throws IOException {
//		AjaxJson ajaxJson = new AjaxJson();
//		Map<String, Object> queryMap = new HashMap<String, Object>();
////		SysOrderRalation sysOrderRalation = new SysOrderRalation();
////		sysOrderRalation.setOrderHeaderId(out_trade_no);
////		SysOrderRalation entity = sysOrderRalationService.getByOrderNo(sysOrderRalation);
////		if (entity == null) {
////			ajaxJson.setSuccess(false);
////			ajaxJson.setMsg("订单不存在");
////			return ajaxJson;
////		}
////		Date payDate = entity.getPayDate();
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
//
//		// 解析报文
//        Double refundMoney =Double.parseDouble(refund_amount)*100;
//        refund_amount = refundMoney.toString();
//		refund_amount = refund_amount.substring(0,refund_amount.indexOf("."));
//		queryMap.put("RefundAmt", refund_amount);
////		queryMap.put("MerOrderNo", entity.getId());
//		queryMap.put("OriOrderNo", out_trade_no);
////		queryMap.put("OriTranDate", sdf.format(payDate));
//
//		Calendar calendar = Calendar.getInstance();
//		Date date = calendar.getTime();
//		String tranDate = StringUtil.getRelevantDate(date);
//		String tranTime = StringUtil.getRelevantTime(date);
//		queryMap.put("TranDate", tranDate);
//		queryMap.put("TranTime", tranTime);
//		queryMap.put("MerBgUrl", PathUtil.getValue("BgRefundUrl"));
//		queryMap.put("TranType", "0401");
//		queryMap.put("AcqCode", "000000000000014");
//		queryMap.put("BusiType", "0001");
//		queryMap.put("Version", "20140728");
//		queryMap.put("MerId", "481611711290001");
//		queryMap.put("AccessType", "0");
//		queryMap.put("MerResv", "MerResv");
//		queryMap.put("CurryNo", "CNY");
//		
//		String signature = SignUtil.sign(queryMap);
//		queryMap.put(signatureField, signature);
//
//		String query_url = PathUtil.getValue("refund_url");
//
//		CloseableHttpResponse httpResonse = HttpSendUtil.sendToOtherServer(query_url, queryMap);
//		if (null == httpResonse) {
//			ajaxJson.setSuccess(false);
//			ajaxJson.setMsg("退款路径不存在");
//			return ajaxJson;
//		} else {
//			String respStr = StringUtil.parseResponseToStr(httpResonse);
//			System.out.println(respStr);
//			if (!respStr.contains("=")) {
//				response.getWriter().write(respStr);
//				ajaxJson.setSuccess(false);
//				ajaxJson.setMsg("参数不正确");
//				return ajaxJson;
//			}
//			Map<String, String> resultMap = StringUtil.paserStrtoMap(respStr);
//
//			for (Map.Entry<String, String> entry : resultMap.entrySet()) {
//				request.setAttribute(entry.getKey(), entry.getValue());
//			}
//			System.out.println(resultMap);
//			// 验证签名
//			String respCode = resultMap.get("respCode");
//			String respMsg = resultMap.get("respMsg");
//			if ("1003".equals(respCode) || "0000".equals(respCode)) {
//				if (SignUtil.verify(resultMap)) {
//					ajaxJson.setSuccess(true);
//					ajaxJson.setMsg("退款成功");
//					return ajaxJson;
//				} else {
//					ajaxJson.setSuccess(false);
//					ajaxJson.setMsg("退款异常" + respMsg);
//					return ajaxJson;
//				}
//			}
//			ajaxJson.setSuccess(false);
//			ajaxJson.setMsg("退款异常" + respMsg);
//			return ajaxJson;
//		}
//	}
//	
////	@RequestMapping(value = "bgRefundUrl")
////	public void bgRefundUrl(PayRequest payRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, AlipayApiException {  
////		request.setCharacterEncoding("utf-8");
////		response.setCharacterEncoding("utf-8");
////		//解析 返回报文
////		Enumeration<String> requestNames = request.getParameterNames();
////		Map<String, String> resultMap = new HashMap<String, String>();
////		while(requestNames.hasMoreElements()){
////			String name = requestNames.nextElement();
////			String value = request.getParameter(name);
////			value = URLDecoder.decode(value, "UTF-8");
////			resultMap.put(name, value);
////		}
////		System.out.println("*************bgReturnUrl result:"+resultMap);
////		//验证签名
////		if(SignUtil.verify(resultMap)){
////			if("0000".equals(resultMap.get("OrderStatus"))) {
////				String MerOrderNo=resultMap.get("MerOrderNo");
////				String TranDate=resultMap.get("TranDate");
////				String TranTime=resultMap.get("TranTime");
////				String OrderAmt=resultMap.get("OrderAmt");
////				SysOrderRalation sysOrderRalation = sysOrderRalationService.findUniqueByProperty("order_header_id", MerOrderNo);
////				int payState = sysOrderRalation.getPayState();
////				if(payState==0) {
////					try {
////						Date strToDate = DateUtils.StrToDate(TranDate+TranTime);
////						sysOrderRalation.setCreateDate(strToDate);
////						//sysOrderRalation.setCreateDate(createDate);
////						sysOrderRalationService.updateChinaPayOrder(sysOrderRalation);
////						logger.info(MerOrderNo+":"+MerOrderNo+":交易完成");
////					} catch (Exception e) {
////						System.out.println(MerOrderNo+":"+MerOrderNo+":"+resultMap.get("OrderStatus")+"交易异常");
////						response.getWriter().write("fail");
////						return;
////					}
////				}
////				System.out.println(MerOrderNo+":"+MerOrderNo+":"+resultMap.get("OrderStatus")+"交易完成");
////			}
////			logger.info("银联验证成功");
////			response.getWriter().write("success");
////		}else{
////			response.getWriter().write("fail");
////		}
////	}  
//	
//	@RequestMapping(value = "bgRefundUrl")
//	public void bgRefundUrl(PayRequest payRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, AlipayApiException {  
//		request.setCharacterEncoding("utf-8");
//		response.setCharacterEncoding("utf-8");
//		//解析 返回报文
//		Enumeration<String> requestNames = request.getParameterNames();
//		Map<String, String> resultMap = new HashMap<String, String>();
//		while(requestNames.hasMoreElements()){
//			String name = requestNames.nextElement();
//			String value = request.getParameter(name);
//			value = URLDecoder.decode(value, "UTF-8");
//			resultMap.put(name, value);
//		}
//		System.out.println("*************bgRefundUrl result:"+resultMap);
//		//验证签名
//		if(SignUtil.verify(resultMap)){
//			if("0000".equals(resultMap.get("OrderStatus"))) {
//				String MerOrderNo=resultMap.get("MerOrderNo");
//				String TranDate=resultMap.get("TranDate");
//				String TranTime=resultMap.get("TranTime");
//				String OrderAmt=resultMap.get("OrderAmt");
//				
//				//退款业务
//				
////				SysOrderRalation sysOrderRalation = sysOrderRalationService.findUniqueByProperty("order_header_id", MerOrderNo);
////				int payState = sysOrderRalation.getPayState();
////				if(payState==0) {
////					try {
////						Date strToDate = DateUtils.StrToDate(TranDate+TranTime);
////						sysOrderRalation.setCreateDate(strToDate);
////						//sysOrderRalation.setCreateDate(createDate);
////						sysOrderRalationService.updateChinaPayOrder(sysOrderRalation);
////						logger.info(MerOrderNo+":"+MerOrderNo+":交易完成");
////					} catch (Exception e) {
////						System.out.println(MerOrderNo+":"+MerOrderNo+":"+resultMap.get("OrderStatus")+"交易异常");
////						response.getWriter().write("fail");
////						return;
////					}
////				}
//				System.out.println(MerOrderNo+":"+MerOrderNo+":"+resultMap.get("OrderStatus")+"退款完成");
//			}
//			logger.info("银联退款验证成功");
//			response.getWriter().write("success");
//		}else{
//			response.getWriter().write("fail");
//		}
//	}  
//	
//}
