/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.entity.common;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 验证码Entity
 * @author laiyanxin
 * @version 2018-02-26
 */
public class ComSmsLog extends DataEntity<ComSmsLog> {
	
	private static final long serialVersionUID = 1L;
	private String area;		// are
	private String phone;		// phone
	private String code;		// code
	private Integer type;		// 类型1.注册验证码 2.忘记密码 3.验证原手机 4.绑定新手机 5.新帐号绑定手机 6.已有帐号绑定手机
	private String content;		// content
	private Integer source;		// 1 网页 2手机端
	private String email;		// email
	private Integer sendType;// 1.手机发送验证码 2.邮箱发送验证码;
	
	
	
	public ComSmsLog() {
		super();
		this.setIdType(IDTYPE_AUTO);
	}

	public ComSmsLog(String id){
		super(id);
	}

	
	
	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public Integer getSendType() {
		return sendType;
	}

	public void setSendType(Integer sendType) {
		this.sendType = sendType;
	}

	@ExcelField(title="phone", align=2, sort=2)
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	
	
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@ExcelField(title="类型1.注册验证码 2.忘记密码 3.验证原手机 4.绑定新手机 5.新帐号绑定手机 6.已有帐号绑定手机", align=2, sort=4)
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
	@ExcelField(title="content", align=2, sort=6)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@ExcelField(title="1.安卓 2.苹果 3.网页", align=2, sort=7)
	public Integer getSource() {
		return source;
	}

	public void setSource(Integer source) {
		this.source = source;
	}
	
	@ExcelField(title="email", align=2, sort=8)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
}