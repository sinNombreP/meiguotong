package com.jeeplus.modules.base.sms;

import java.io.IOException;
import java.util.UUID;

//import com.dahantc.api.sms.json.JSONHttpClient;


//import com.dahantc.api.sms.json.JSONHttpClient;
//import com.jeeplus.common.utils.JsonUtils;


public class SMSUtil {

	private static String account1 = "dh14016";// 用户名（必填）
	private static String password1 = "W1z8p4hf";// 密码（必填）
	
	private static String account = "dh14015";// 用户名（必填）
	private static String password= "6DiRQV82";// 密码（必填）
	//private static String phone = "13922786086"; // 手机号码（必填,多条以英文逗号隔开）
	public static String sign = "【圈途旅行】"; // 短信签名（必填）
	public static String subcode = ""; // 子号码（可选）
	public static String msgid = UUID.randomUUID().toString().replace("-", ""); // 短信id，查询短信状态报告时需要，（可选）
	public static String sendtime = ""; // 定时发送时间（可选）
	
	public static String  send(String phone, String content) throws IOException {
		String result="";
		try {
//			String content = "您的验证码是:"+content;// 短信内容（必填）
//			System.out.println(content);
//			JSONHttpClient jsonHttpClient = new JSONHttpClient(sendtime)  ;//new JSONHttpClient("http://www.dh3t.com");
//			jsonHttpClient.setRetryCount(1);
//			String sendhRes = jsonHttpClient.sendSms(account, password, phone, content, sign, subcode);
//			System.out.println(sendhRes);
//			return sendhRes;
		} catch (Exception e) {

		}
		return result;
	}
	
	
	
	public static String  send1(String phone, String nums) throws IOException {
		String result="";
		try {
//			String content = "您的验证码是:"+nums;// 短信内容（必填）
//			System.out.println(content);
//			JSONHttpClient jsonHttpClient = new JSONHttpClient("http://www.dh3t.com");
//			jsonHttpClient.setRetryCount(1);
//			String sendhRes = jsonHttpClient.sendSms(account1, password1, phone, content, sign, subcode);
//			return sendhRes;
//			SMSEntity entity = (SMSEntity)JsonUtils.getInstanceByJson(SMSEntity.class, sendhRes);
//			if("0".equals(entity.getResult())) {
//				result = true;
//			}

			//
			// List<SmsData> list = new ArrayList<SmsData>();
			// list.add(new
			// SmsData("11111111,15711666133,1738786465,44554545",
			// content, msgid, sign, subcode, sendtime));
			// list.add(new SmsData("15711777134", content, msgid, sign,
			// subcode, sendtime));
			// String sendBatchRes = jsonHttpClient.sendBatchSms(account,
			// password, list);
			// LOG.info("提交批量短信响应：" + sendBatchRes);
			//
			// String reportRes = jsonHttpClient.getReport(account, password);
			// LOG.info("获取状态报告响应：" + reportRes);
			//
			// String smsRes = jsonHttpClient.getSms(account, password);
			// LOG.info("获取上行短信响应：" + smsRes);
			//
			// String balanceRes = jsonHttpClient.getBalance(account, password);
			// LOG.info("获取余额短信响应：" + balanceRes);

		} catch (Exception e) {

		}
		return result;
	}

	
	
	public static String  send2(String phone, String nums) throws IOException {
		String result="";
		try {
//			String content = "您的验证码是:"+nums;// 短信内容（必填）
//			System.out.println(content);
//			JSONHttpClient jsonHttpClient = new JSONHttpClient("http://www.dh3t.com");
//			jsonHttpClient.setRetryCount(1);
//			String sendhRes = jsonHttpClient.sendSms(account1, password1, phone, content, sign, subcode);
//			return sendhRes;
		} catch (Exception e) {

		}
		return result;
	}
	
	public static void main(String[] args) throws IOException {
		String send = send1("13922786086", "123456");
		System.out.println(send);
	}

}