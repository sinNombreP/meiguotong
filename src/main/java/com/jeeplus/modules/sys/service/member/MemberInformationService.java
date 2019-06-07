/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.service.member;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.meiguotong.entity.travel.OrderTravelBusiness;
import com.jeeplus.modules.sys.entity.member.MemberInformation;
import com.jeeplus.modules.sys.mapper.member.MemberInformationMapper;

/**
 * 会员详情Service
 * @author psz
 * @version 2018-08-07
 */
@Service
@Transactional(readOnly = true)
public class MemberInformationService extends CrudService<MemberInformationMapper, MemberInformation> {

	public MemberInformation get(String id) {
		return super.get(id);
	}
	/**
	 * 获取会员个人信息（接口）
	 * @param memberid
	 * @return
	 */
	public MemberInformation getMember(String memberid) {
		return mapper.getMember(memberid);
	}
	/**
	 * 获取会员个人信息(旅行社)（接口）
	 * @param memberid
	 * @return
	 */
	public MemberInformation getMemberTravel(String memberid) {
		return mapper.getMemberTravel(memberid);
	}
	public List<MemberInformation> findList(MemberInformation memberInformation) {
		return super.findList(memberInformation);
	}
	
	public Page<MemberInformation> findPage(Page<MemberInformation> page, MemberInformation memberInformation) {
	     Calendar c = Calendar.getInstance();
	    if(null != memberInformation.getLately()) {
			if(1 == memberInformation.getLately()) {  //一周内
		        c.setTime(new Date());
		        c.add(Calendar.DATE, - 7);
				Date d = c.getTime();
				memberInformation.setLastloginDate(d);
			}else if(2 == memberInformation.getLately()) {  //一月内
		        c.setTime(new Date());
		        c.add(Calendar.MONTH, -1);
		        Date m = c.getTime();
				memberInformation.setLastloginDate(m);
			}else if(3 == memberInformation.getLately()) {  //一年内
		        c.setTime(new Date());
		        c.add(Calendar.YEAR, -1);
		        Date y = c.getTime();
				memberInformation.setLastloginDate(y);
			}
	    }
		return super.findPage(page, memberInformation);
	}
	
	@Transactional(readOnly = false)
	public void save(MemberInformation memberInformation) {
		super.save(memberInformation);
	}
	
	@Transactional(readOnly = false)
	public void delete(MemberInformation memberInformation) {
		super.delete(memberInformation);
	}

	/** 
	* @Title: changeStatus 
	* @Description: 修改会员状态
	* @author 彭善智
	* @date 2018年8月7日下午2:44:27
	*/ 
	@Transactional(readOnly = false)
	public void changeStatus(MemberInformation memberInformation) {
		mapper.changeStatus(memberInformation);
	}
	/**
	 * 修改会员信息
	 * @param memberInformation
	 */
	@Transactional(readOnly = false)
	public void updateMember(MemberInformation memberInformation) {
		mapper.updateMember(memberInformation);
	}
	/**
	 * 修改会员信息(旅行社)
	 * @param memberInformation
	 */
	@Transactional(readOnly = false)
	public void updateMemberTravel(MemberInformation memberInformation) {
		mapper.updateMemberTravel(memberInformation);
		//修改用户审核状态
		mapper.updateMemberAudit(memberInformation);
	}
	
	public static void main(String[] args) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DATE, - 7);
        Date d = c.getTime();
        String day = format.format(d);
        System.out.println("过去七天："+day);
        c.setTime(new Date());
        c.add(Calendar.MONTH, -1);
        Date m = c.getTime();
        String mon = format.format(m);
        System.out.println("过去一个月："+mon);
        c.setTime(new Date());
        c.add(Calendar.MONTH, -3);
        Date m3 = c.getTime();
        String mon3 = format.format(m3);
        System.out.println("过去三个月："+mon3);
        c.setTime(new Date());
        c.add(Calendar.YEAR, -1);
        Date y = c.getTime();
        String year = format.format(y);
        System.out.println("过去一年："+year);
	}
    /**
     * 根据memberid查找数据
     * @param getMemberInformation
     * @return
     */
	public MemberInformation findByMemberid(MemberInformation getMemberInformation) {
		return mapper.findByMemberid(getMemberInformation);
	}

	/** 
	* @Title: getInfo 
	* @Description: 获取导游信息
	* @author 彭善智
	* @date 2018年8月30日下午3:25:37
	*/ 
	public MemberInformation getInfo(OrderTravelBusiness orderTravelBusiness) {
		return mapper.getInfo(orderTravelBusiness);
	}
	
}