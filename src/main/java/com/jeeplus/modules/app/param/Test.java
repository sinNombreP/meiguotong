package com.jeeplus.modules.app.param;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;

public class Test {
    String chineseName ;
    String englishName ;
    String certType ;
    String certNo ;
    String certValidDate;
    String birthday ;
    String area ;
	String mobile ;
    String orderMemberType ;
    
    String type ;
    public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getChineseName() {
		return chineseName;
	}
	public void setChineseName(String chineseName) {
		this.chineseName = chineseName;
	}
	public String getEnglishName() {
		return englishName;
	}
	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}
	public String getCertType() {
		return certType;
	}
	public void setCertType(String certType) {
		this.certType = certType;
	}
	public String getCertNo() {
		return certNo;
	}
	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}
	public String getCertValidDate() {
		return certValidDate;
	}
	public void setCertValidDate(String certValidDate) {
		this.certValidDate = certValidDate;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getOrderMemberType() {
		return orderMemberType;
	}
	public void setOrderMemberType(String orderMemberType) {
		this.orderMemberType = orderMemberType;
	}

	public static void main(String[] args) {
		List<Test> Tests=new ArrayList();
		Test Test=new Test();
		Test.setArea("广州");
		Test.setBirthday("2019-05-05");
		Test.setCertNo("1234569");
		Test.setCertValidDate("2018-05-05");
		Test.setChineseName("中文名");
		Test.setEnglishName("EnglishName");
		Test.setMobile("13000000000");
		Test.setOrderMemberType("1");
		Tests.add(Test);
		Tests.add(Test);
        JSONArray jsonArray = JSONArray.fromObject(Tests);
        System.err.println(jsonArray.toString());
	}
}
