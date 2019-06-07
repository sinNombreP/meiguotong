package com.jeeplus.modules.app.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "LoginParam", description = "登录")
public class LoginParam {

	    @ApiModelProperty(value="手机号/邮箱",name="phone")
		private String phone;
		@ApiModelProperty(value="密码",name="passWord")
		private String passWord;
	    @ApiModelProperty(value="设备号",name="devid")
		private String devid;
	    @ApiModelProperty(value="经度",name="loginX")
		private Long loginX;
	    @ApiModelProperty(value="纬度",name="loginY")
		private String loginY;
	    @ApiModelProperty(value="登陆IP",name="loginIp")
		private Long loginIp;

	    public String getPhone() {
			return phone;
		}
		public void setPhone(String phone) {
			this.phone = phone;
		}
		public String getPassWord() {
			return passWord;
		}
		public void setPassWord(String passWord) {
			this.passWord = passWord;
		}
		public String getDevid() {
			return devid;
		}
		public void setDevid(String devid) {
			this.devid = devid;
		}
		public Long getLoginX() {
			return loginX;
		}
		public void setLoginX(Long loginX) {
			this.loginX = loginX;
		}
		public String getLoginY() {
			return loginY;
		}
		public void setLoginY(String loginY) {
			this.loginY = loginY;
		}
		public Long getLoginIp() {
			return loginIp;
		}
		public void setLoginIp(Long loginIp) {
			this.loginIp = loginIp;
		}

}
