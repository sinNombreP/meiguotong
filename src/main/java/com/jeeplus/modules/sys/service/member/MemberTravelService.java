/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.service.member;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.sys.entity.member.Member;
import com.jeeplus.modules.sys.entity.member.MemberTravel;
import com.jeeplus.modules.sys.mapper.member.MemberTravelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 旅行社Service
 * @author psz
 * @version 2018-08-07
 */
@Service
@Transactional(readOnly = true)
public class MemberTravelService extends CrudService<MemberTravelMapper, MemberTravel> {

	public MemberTravel get(String id) {
		return super.get(id);
	}
	
	public List<MemberTravel> findList(MemberTravel memberTravel) {
		return super.findList(memberTravel);
	}
	
	public Page<MemberTravel> findPage(Page<MemberTravel> page, MemberTravel memberTravel) {
	     Calendar c = Calendar.getInstance();
	    if(null != memberTravel.getLately()) {
			if(1 == memberTravel.getLately()) {  //一周内
		        c.setTime(new Date());
		        c.add(Calendar.DATE, - 7);
				Date d = c.getTime();
				memberTravel.setLastloginDate(d);
			}else if(2 == memberTravel.getLately()) {  //一月内
		        c.setTime(new Date());
		        c.add(Calendar.MONTH, -1);
		        Date m = c.getTime();
		        memberTravel.setLastloginDate(m);
			}else if(3 == memberTravel.getLately()) {  //一年内
		        c.setTime(new Date());
		        c.add(Calendar.YEAR, -1);
		        Date y = c.getTime();
		        memberTravel.setLastloginDate(y);
			}
	    }
		return super.findPage(page, memberTravel);
	}
	
//	@Transactional(readOnly = false)
//	public void save(MemberTravel memberTravel) {
//		memberTravel.preUpdate();
//		mapper.update(memberTravel);
//	}
	
	@Transactional(readOnly = false)
	public void delete(MemberTravel memberTravel) {
		super.delete(memberTravel);
	}

	/** 
	* @Title: changeAudit 
	* @Description: 修改审核状态
	* @author 彭善智
	* @date 2018年8月7日下午8:23:45
	*/ 
	@Transactional(readOnly = false)
	public void changeAudit(MemberTravel memberTravel) {
		mapper.changeAudit(memberTravel);
	}

	/** 
	* @Title: getSon 
	* @Description: 获取子账号
	* @author 彭善智
	* @date 2018年8月8日上午11:15:09
	*/ 
	public List<Member> getSon(MemberTravel memberTravel) {
		return mapper.getSon(memberTravel);
	}
	
}