package com.jeeplus.modules.base.pay.chinapay.entity;

public class PayRequest {
	private String merId; //商户ID
	private String orderAmt;//订单金额
	private String  tranDate;//交易日期
	private String  tranTime;//交易时间
	private String  orderNo;//订单号
	private String  version;//支付版本号
	private String  host;//地址
	private String  pageReturnUrl;//前台接收地址
	private String  bgReturnUrl;//前台接收地址
	private String  CurryNo;// 交易币种(3位，默认为CNY 人民币)
	private String  AccessType;//接入类型(1位数字，默认:0,表示接入类型[0:以商户身份接入；1:以机构身份接入])
	private String  AcqCode;//收单机构号&nbsp;(15位数字 )"000000000000014"
	private String  MerResv;//商户私有域"MerResv"(英文或数字，不超过60字节，ChinaPay将原样返回给商户系统该字段填入的数据)
	private String  BusiType;//业务类型(4位数字，固定值：0001)
	public String getMerId() {
		return merId;
	}
	public void setMerId(String merId) {
		this.merId = merId;
	}
	public String getOrderAmt() {
		return orderAmt;
	}
	public void setOrderAmt(String orderAmt) {
		this.orderAmt = orderAmt;
	}
	public String getTranDate() {
		return tranDate;
	}
	public void setTranDate(String tranDate) {
		this.tranDate = tranDate;
	}
	public String getTranTime() {
		return tranTime;
	}
	public void setTranTime(String tranTime) {
		this.tranTime = tranTime;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getPageReturnUrl() {
		return pageReturnUrl;
	}
	public void setPageReturnUrl(String pageReturnUrl) {
		this.pageReturnUrl = pageReturnUrl;
	}
	public String getBgReturnUrl() {
		return bgReturnUrl;
	}
	public void setBgReturnUrl(String bgReturnUrl) {
		this.bgReturnUrl = bgReturnUrl;
	}
	public String getCurryNo() {
		return CurryNo;
	}
	public void setCurryNo(String curryNo) {
		CurryNo = curryNo;
	}
	public String getAccessType() {
		return AccessType;
	}
	public void setAccessType(String accessType) {
		AccessType = accessType;
	}
	public String getAcqCode() {
		return AcqCode;
	}
	public void setAcqCode(String acqCode) {
		AcqCode = acqCode;
	}
	public String getMerResv() {
		return MerResv;
	}
	public void setMerResv(String merResv) {
		MerResv = merResv;
	}
	public String getBusiType() {
		return BusiType;
	}
	public void setBusiType(String busiType) {
		BusiType = busiType;
	}
}
