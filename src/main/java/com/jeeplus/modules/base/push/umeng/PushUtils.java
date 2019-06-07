package com.jeeplus.modules.base.push.umeng;

import com.jeeplus.common.config.Global;
import com.jeeplus.modules.base.push.umeng.android.AndroidBroadcast;
import com.jeeplus.modules.base.push.umeng.android.AndroidUnicast;
import com.jeeplus.modules.base.push.umeng.ios.IOSBroadcast;
import com.jeeplus.modules.base.push.umeng.ios.IOSUnicast;

public class PushUtils {
	public  String appkey =  Global.getConfig("um_appkey"); 
	public  String appMasterSecret =  Global.getConfig("um_appMasterSecret"); 
	
	private String appIOSkey = Global.getConfig("um_appIOSkey");
	private String appIOSMasterSecret = Global.getConfig("um_appIOSMasterSecret"); 
	
	private String timestamp = null;
	private PushClient client = new PushClient();
	
	/**
	 * Android 广播
	 * @param ticker 必填 通知栏提示文字
	 * @param title 必填 通知标题
	 * @param text 必填 通知文字描述 
	 * @param extraField 扩展字段
	 * @param expireTime 过期时间戳
	 * @throws Exception
	 */
	public  void sendAndroidBroadcast(String ticker,String title,String text,String extraField,String expireTime) throws Exception {
		AndroidBroadcast broadcast = new AndroidBroadcast(appkey,appMasterSecret);
		broadcast.setExpireTime(expireTime);
		broadcast.setTicker(ticker);
		broadcast.setTitle( title);
		broadcast.setText( text);
		broadcast.goAppAfterOpen();
		broadcast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
		// TODO Set 'production_mode' to 'false' if it's a test device. 
		// For how to register a test device, please see the developer doc.
		broadcast.setProductionMode();
		// Set customized fields
		broadcast.setExtraField("extraField",extraField);
		client.send(broadcast);
	}
	
	/**
	 * Android单播
	 * @param deviceToken 设备token
	 * @param ticker 必填 通知栏提示文字
	 * @param title 必填 通知标题
	 * @param text 必填 通知文字描述 
	 * @param extraField 扩展字段
	 * @throws Exception
	 */
/*	public  void sendAndroidUnicast(String deviceToken,String ticker,String title,String text,String extraField,String expireTime) throws Exception {
		AndroidUnicast unicast = new AndroidUnicast(appkey,appMasterSecret);
		// TODO Set your device token
		unicast.setExpireTime(expireTime);
		unicast.setDeviceToken(deviceToken);
		unicast.setTicker(ticker);
		unicast.setTitle(title);
		unicast.setText(text);
		unicast.goAppAfterOpen();
		unicast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
		// TODO Set 'production_mode' to 'false' if it's a test device. 
		// For how to register a test device, please see the developer doc.
		unicast.setProductionMode();
		// Set customized fields
		unicast.setExtraField("extraField", extraField); 
		unicast.setCustomField(text);
		client.send(unicast);
	}*/
	
	    public void sendAndroidUnicast(String deviceToken, String ticker, String title, String text, String json, String extraField, String expireTime) throws Exception {
	        //这里需要一个type，就是区分是要跳转页面还是打开APP
	        AndroidUnicast unicast = new AndroidUnicast(appkey, appMasterSecret);
	        // TODO Set your device token
	        unicast.setExpireTime(expireTime);
	        unicast.setDeviceToken(deviceToken);
	        unicast.setTicker(ticker);
	        unicast.setTitle(title);
	        unicast.setText(text);
	        unicast.goActivityAfterOpen("cn.ewhale.quantu.ui.notice.NoticeActivity");
	        unicast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
	        // TODO Set 'production_mode' to 'false' if it's a test device.
	        // For how to register a test device, please see the developer doc.
	        unicast.setProductionMode();
	        // Set customized fields
	        unicast.setExtraField("extraField", extraField);
	        unicast.setCustomField(json);
	        client.send(unicast);
	    }
	    
	public void sendIOSBroadcast(String text,String extraField,String expireTime) throws Exception {
		IOSBroadcast broadcast = new IOSBroadcast(appIOSkey,appIOSMasterSecret);
		broadcast.setExpireTime(expireTime);
		broadcast.setAlert(text);
		broadcast.setBadge( 0);
		broadcast.setSound( "default");
		// TODO set 'production_mode' to 'true' if your app is under production mode
		broadcast.setProductionMode();
		// Set customized fields
		broadcast.setCustomizedField("extraField", extraField);
		client.send(broadcast);
	}
	
	/**
	 * ios单播
	 * @param deviceToken 设备token
	 * @param alert 必填 通知文字描述  
	 * @param extraField 扩展字段
	 * @throws Exception
	 */
	public void sendIOSUnicast(String deviceToken, String alert,String extraField,String expireTime) throws Exception {
		 // "alert":{           
        //  "title":"title",
        //  "subtitle":"subtitle",
        //  "body":"body",
		//}                   
		IOSUnicast unicast = new IOSUnicast(appIOSkey,appIOSMasterSecret);
		// TODO Set your device token
		unicast.setExpireTime(expireTime);
		unicast.setDeviceToken(deviceToken);
		unicast.setAlert(alert);
		unicast.setBadge( 0);
		unicast.setSound( "default");
		// TODO set 'production_mode' to 'true' if your app is under production mode
		unicast.setProductionMode();
		// Set customized fields
		unicast.setCustomizedField("extraField", extraField);
		client.send(unicast);
	}
	
	
	public static void main(String[] args) {
		// TODO set your appkey and master secret here
		
		try {
			PushUtils  demo =new PushUtils();
			demo.sendIOSBroadcast("测试", "1111", "12122");
//			demo.sendAndroidUnicast("deviceToken", "ticker", "title", "text", "extraField", "expireTime");
			/* TODO these methods are all available, just fill in some fields and do the test
			 * demo.sendAndroidCustomizedcastFile();
			 * demo.sendAndroidBroadcast();
			 * demo.sendAndroidGroupcast();
			 * demo.sendAndroidCustomizedcast();
			 * demo.sendAndroidFilecast();
			 * 
			 * demo.sendIOSBroadcast();
			 * demo.sendIOSUnicast();
			 * demo.sendIOSGroupcast();
			 * demo.sendIOSCustomizedcast();
			 * demo.sendIOSFilecast();
			 */
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	

}
