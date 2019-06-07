/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.common.utils;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

/**
 * 用户工具类
 * @author jeeplus
 * @version 2013-12-05
 */
public class EmailUtils {

	public static Integer sendEmail(String supplierEmail,String title,String msg){
		int status=0;
		// 不要使用SimpleEmail,会出现乱码问题
        HtmlEmail email = new HtmlEmail();
        try {
         // 这里是SMTP发送服务器的名字：，普通qq号只能是smtp.qq.com ；smtp.exmail.qq.com没测试成功
         email.setHostName("smtp.qq.com");
         //设置需要鉴权端口
         email.setSmtpPort(465);
        //开启 SSL 加密
         email.setSSL(true);
         // 字符编码集的设置
         email.setCharset("utf-8");
         // 收件人的邮箱
         email.addTo(supplierEmail);
         // 发送人的邮箱
         email.setFrom("781456213@qq.com", "无名之人");
         // 如果需要认证信息的话，设置认证：用户名-密码。分别为发件人在邮件服务器上的注册名称和得到的授权码
         email.setAuthentication("781456213@qq.com", "edbxeapfmymvbbij");
         email.setSubject(title);
         // 要发送的信息，由于使用了HtmlEmail，可以在邮件内容中使用HTML标签
         email.setMsg(msg);
         // 发送
         email.send();
         //System.out.println ( "邮件发送成功!" );
         status=0;
         return status;
        } catch (EmailException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
         //System.out.println ( "邮件发送失败!" );
         status=1;
         return status;
        }
	}
}
