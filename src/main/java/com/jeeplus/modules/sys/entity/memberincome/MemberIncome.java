/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.entity.memberincome;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

import java.math.BigDecimal;

/**
 * 直客流水记录Entity
 *
 * @author dong
 * @version 2019-04-09
 */
public class MemberIncome extends DataEntity<MemberIncome> {

    private static final long serialVersionUID = 1L;
    private String no;        // 流水号 时间搓+4位随机
    private Integer type;        // 订单类型：1.包车租车2.短程接送3.接送机4常规路线5.当地参团6.游轮7.景点门票8.当地玩家/导游9.酒店10.保险11.旅游定制13.商务定制14.商务旅游
    private Integer income;        // 1.收入 2.支出
    private Integer orderId;        // 订单Id(关联订单表)
    private Integer memberid;        // 会员ID
    private String remark;        // 备注
    private BigDecimal incomePrice;        // 金额
    
	private Integer status;		// 1.收入  2.支出
	private String number;		//账号/订单号
	private String nickName;	//客户昵称
	private Integer checks;//状态1.待审核2.审核通过3.审核不通过4已打款
	
	private String people;//开户人
	private String pay ;//账户
	private String rank;//银行
	
	private Integer payType;//支付方式
	
    
    

    public MemberIncome() {
        super();
        this.setIdType(IDTYPE_AUTO);
    }

    public MemberIncome(String id) {
        super(id);
    }

    @ExcelField(title = "流水号 时间搓+4位随机", align = 2, sort = 1)
    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    @ExcelField(title = "订单类型：1.包车租车2.短程接送3.接送机4常规路线5.当地参团6.游轮7.景点门票8.当地玩家/导游9.酒店10.保险11.旅游定制13.商务定制14.商务旅游", align = 2, sort = 2)
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @ExcelField(title = "1.收入 2.支出", align = 2, sort = 3)
    public Integer getIncome() {
        return income;
    }

    public void setIncome(Integer income) {
        this.income = income;
    }

    @ExcelField(title = "订单Id(关联订单表)", align = 2, sort = 4)
    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    @ExcelField(title = "会员ID", align = 2, sort = 6)
    public Integer getMemberid() {
        return memberid;
    }

    public void setMemberid(Integer memberid) {
        this.memberid = memberid;
    }

    @ExcelField(title = "备注", align = 2, sort = 7)
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @ExcelField(title = "金额", align = 2, sort = 8)
    public BigDecimal getIncomePrice() {
        return incomePrice;
    }

    public void setIncomePrice(BigDecimal incomePrice) {
        this.incomePrice = incomePrice;
    }

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public Integer getChecks() {
		return checks;
	}

	public void setChecks(Integer checks) {
		this.checks = checks;
	}

	public String getPeople() {
		return people;
	}

	public void setPeople(String people) {
		this.people = people;
	}

	public String getPay() {
		return pay;
	}

	public void setPay(String pay) {
		this.pay = pay;
	}

	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}

	public Integer getPayType() {
		return payType;
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
	}
	
    
}