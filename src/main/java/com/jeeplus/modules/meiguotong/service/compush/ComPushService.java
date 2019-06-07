/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.service.compush;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.meiguotong.entity.compush.ComPush;
import com.jeeplus.modules.meiguotong.mapper.compush.ComPushMapper;

/**
 * 推送管理Service
 * @author dong
 * @version 2018-09-17
 */
@Service("ComPushMapper")
@Transactional(readOnly = true)
public class ComPushService extends CrudService<ComPushMapper, ComPush> {

	@Autowired
	private ComPushMapper comPushMapper;
	
	public ComPush get(String id) {
		return super.get(id);
	}
	
	public List<ComPush> findList(ComPush comPush) {
		return super.findList(comPush);
	}
	
	public Page<ComPush> findPage(Page<ComPush> page, ComPush comPush) {
		return super.findPage(page, comPush);
	}
	
	@Transactional(readOnly = false)
	public void save(ComPush comPush) {
		super.save(comPush);
		
		//推送给全部用户
		if (comPush.getSend()==1) {
			comPush.setSendType("1,2,3");
			List<Integer> memberids=comPushMapper.findAllUserId();
			for (int i = 0; i < memberids.size(); i++) {
				comPush.setMemberid(memberids.get(i));
				//保存推送的用户
				comPushMapper.insertPushPeople(comPush);
			}
		}else {
			//推送给部分用户
			//筛选用户类型
			String[] sendtype=comPush.getSendType().trim().split(",");
			List list =Arrays.asList(sendtype);
			Set set=new HashSet(list);
			comPush.setSendType(String.join("", set));
			String[] memberid=comPush.getMemberids().split(",");
			for (int i = 0; i < memberid.length; i++) {
				comPush.setMemberid(Integer.valueOf(memberid[i]));
				//保存推送的用户
				comPushMapper.insertPushPeople(comPush);
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(ComPush comPush) {
		super.delete(comPush);
	}
	
	public List<ComPush> findnumber(ComPush comPush){
		return this.comPushMapper.findnumber(comPush);
	}
	
	@Transactional(readOnly = false)
	public Integer insertPushPeople(ComPush comPush){
		return comPushMapper.insertPushPeople(comPush);
	}
	
	/*public List<Integer> findAllUserId(){
		return comPushMapper.findAllUserId();
	}*/
	
	public List<ComPush> findComPushPeople(String id){
		return comPushMapper.findComPushPeople(id);
	}
}