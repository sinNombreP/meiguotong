/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.service.guide;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.meiguotong.entity.comtag.ComTag;
import com.jeeplus.modules.meiguotong.entity.guide.Guide;
import com.jeeplus.modules.meiguotong.entity.guide.GuideDate;
import com.jeeplus.modules.meiguotong.entity.guide.GuideTime;
import com.jeeplus.modules.meiguotong.entity.guideroute.GuideRouteDate;
import com.jeeplus.modules.meiguotong.mapper.comtag.ComTagMapper;
import com.jeeplus.modules.meiguotong.mapper.guide.GuideDateMapper;
import com.jeeplus.modules.meiguotong.mapper.guide.GuideMapper;
import com.jeeplus.modules.meiguotong.mapper.guide.GuideTimeMapper;
import com.jeeplus.modules.sys.entity.member.Member;
import com.jeeplus.modules.sys.entity.member.MemberInformation;
import com.jeeplus.modules.sys.entity.member.MemberRefund;
import com.jeeplus.modules.sys.entity.member.SysUserType;
import com.jeeplus.modules.sys.mapper.member.MemberInformationMapper;
import com.jeeplus.modules.sys.mapper.member.MemberMapper;
import com.jeeplus.modules.sys.mapper.member.MemberRefundMapper;
import com.jeeplus.modules.sys.mapper.member.SysUserTypeMapper;

/**
 * 导游表Service
 * @author cdq
 * @version 2018-08-20
 */
@Service
@Transactional(readOnly = true)
public class GuideService extends CrudService<GuideMapper, Guide> {
	@Autowired
	private ComTagMapper comTagMapper;
	@Autowired
	private MemberMapper memberMapper;
	@Autowired
	private MemberInformationMapper memberInformationMapper;
	@Autowired
	private SysUserTypeMapper sysUserTypeMapper;
	@Autowired
	private GuideTimeMapper guideTimeMapper;
	@Autowired
	private MemberRefundMapper memberRefundMapper;
	@Autowired
	private GuideDateMapper guideDateMapper;
	@Autowired
	private GuideMapper guideMapper;
	/**
	 * 接口获取导游详情
	 * @param memberid
	 * @return
	 */
	public Guide getGuideInfo(Guide guide1) {
		Guide guide =  mapper.getGuideInfo(guide1);
		guide.setDeltails(StringEscapeUtils.unescapeHtml(guide.getDeltails()));
		return guide;
	}
	/**
	 * 获取玩家认证信息接口
	 * @param memberid
	 * @return
	 */
	public Guide getGuide(String memberid) {
		Guide guide =  mapper.getGuide(memberid);
		if(guide!=null&&guide.getDeltails()!=null){
			guide.setDeltails(StringEscapeUtils.unescapeHtml(guide.getDeltails()));
		}
		return guide;
	}
	/**
	 * 根据memberid获取导游信息
	 * @param memberid
	 * @return
	 */
	public Guide findGuide(String memberid) {
		return mapper.findGuide(memberid);
	}
	public Guide get(String id) {
		return super.get(id);
	}
	
	public List<Guide> findList(Guide guide) {
		return super.findList(guide);
	}
	
	/**
	 * 根据搜索条件获取导游列表
	 * @param page
	 * @param guide
	 * @return
	 */
	public Page<Guide> getGuideByCity(Page<Guide> page, Guide guide) {
		dataRuleFilter(guide);
		guide.setPage(page);
		List<Guide> list = mapper.getGuideByCity(guide);
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy"); 
		if(list!=null&&list.size()>0){
			for(Guide a:list){
				if(null != a.getBirthday()){
					String date = sdf.format(a.getBirthday());
					String date1=sdf.format(new Date());
					a.setAge(Integer.parseInt(date1)-Integer.parseInt(date));
				}
			}
		}
		page.setList(list);
		return page;
	}
	public Page<Guide> findPage(Page<Guide> page, Guide guide) {
		return super.findPage(page, guide);
	}
	
	@Transactional(readOnly = false)
	public void save(Guide guide,ComTag comTag,Member member,
			MemberInformation memberInformation,SysUserType sysUserType) {
		super.save(guide);
		comTagMapper.uPdate(comTag);
		memberMapper.uPdate(member);
		memberInformationMapper.uPdate(memberInformation);
		sysUserTypeMapper.uPdate(sysUserType);
		
	}
	@Transactional(readOnly = false)
	public void updateGuidePrice(List<GuideTime> list,List<MemberRefund> list2 ,Guide guide) {
		//删除旧的日期设置
		guideTimeMapper.deleteByGuideid(guide.getId());
		//删除旧的价格设置
		guideDateMapper.deleteByGuideid(guide.getId());
		//删除旧的导游退款设置
		memberRefundMapper.deleteGuideRefund(Integer.parseInt(guide.getId()));
		for(GuideTime a:list){
//			if (a.getIsNewRecord()){
				guideTimeMapper.insert(a);
				
				GuideDate guideDate = new GuideDate();
				guideDate.setGuideid(a.getGuideid());		// 导游id
				guideDate.setPrice(a.getPrice());		// 价格
				guideDate.setType(a.getType());		// 1.当地玩家2.定制旅游-导游3.定制旅游-司兼导4.包车/租车
				
				String[] weekList = null;
				String[] dayList = null;
				if(a.getDateType()==2){
					weekList=a.getWeekDate().split(",");
				}else if(a.getDateType()==3){
					dayList=a.getDayDate().split(",");
				}
				//添加具体日期价格
				Calendar cal = Calendar.getInstance(); // 获得一个日历
				Integer day= null;
				Integer week= null;
				for(long k =a.getBeginDate().getTime();k<a.getEndDate().getTime();k+=86400000) {
					cal.setTimeInMillis(k);
					if(a.getDateType() == 1) {
						guideDate.setPriceDate(new Date(k));
						guideDateMapper.insert(guideDate);
					}
					else if (a.getDateType() == 2 && weekList != null && weekList.length > 0) {
							for (int i = 0; i <weekList.length; i++) {
								week = Integer.parseInt(weekList[i]);
								if(cal.get(Calendar.DAY_OF_WEEK)==week) {
									guideDate.setPriceDate(new Date(k));
									guideDateMapper.insert(guideDate);
								}
							}
					}
					else if (a.getDateType() == 3 && dayList != null && dayList.length > 0 ) {
							for (int i = 0; i <dayList.length; i++) {
								day = Integer.parseInt(dayList[i]);
								if(cal.get(Calendar.DAY_OF_MONTH)==day) {
									guideDate.setPriceDate(new Date(k));
									guideDateMapper.insert(guideDate);
								}
							}
					}
				}
//			}else{
//				guideTimeMapper.update(a);
//				//删除旧的价格设置
//				guideDateMapper.deleteOld(a);
//				//添加新的价格设置
//				GuideDate guideDate = new GuideDate();
//				guideDate.setGuideid(a.getGuideid());		// 导游id
//				guideDate.setPrice(a.getPrice());		// 价格
//				guideDate.setType(a.getType());		// 1.当地玩家2.定制旅游-导游3.定制旅游-司兼导4.包车/租车
//
//				String[] weekList = null;
//				String[] dayList = null;
//				if(a.getDateType()==2){
//					weekList=a.getWeekDate().split(",");
//				}else if(a.getDateType()==3){
//					dayList=a.getDayDate().split(",");
//				}
//				//添加具体日期价格
//				Calendar cal = Calendar.getInstance(); // 获得一个日历
//				Integer day= null;
//				Integer week= null;
//				for(long k =a.getBeginDate().getTime();k<a.getEndDate().getTime();k+=86400000) {
//					cal.setTimeInMillis(k);
//					if(a.getDateType() == 1) {
//						guideDate.setPriceDate(new Date(k));
//						guideDateMapper.insert(guideDate);
//					}
//					else if (a.getDateType() == 2 && weekList != null && weekList.length > 0) {
//							for (int i = 0; i <weekList.length; i++) {
//								week = Integer.parseInt(weekList[i]);
//								if(cal.get(Calendar.DAY_OF_WEEK)==week) {
//									guideDate.setPriceDate(new Date(k));
//									guideDateMapper.insert(guideDate);
//								}
//							}
//					}
//					else if (a.getDateType() == 3 && dayList != null && dayList.length > 0 ) {
//							for (int i = 0; i <dayList.length; i++) {
//								day = Integer.parseInt(dayList[i]);
//								if(cal.get(Calendar.DAY_OF_MONTH)==day) {
//									guideDate.setPriceDate(new Date(k));
//									guideDateMapper.insert(guideDate);
//								}
//							}
//					}
//				}
//			}
		}
		if(list2!=null&&list2.size()>0){
			//添加新的导游退款设置
			for(MemberRefund a:list2){
				memberRefundMapper.insert(a);
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(Guide guide) {
		super.delete(guide);
	}
/**
 * 更改状态
 * @param guide
 */
	@Transactional(readOnly = false)
	public void status(Guide guide) {
		mapper.status(guide);
	}
//获取抽成比例
public Guide findDiscount() {
	return mapper.findDiscount();
}

/**
 * 导游筛选接口
 * @param page
 * @param guide
 * @return
 */
public Page<Guide> findGuideScreenList(Page<Guide> page, Guide guide) {
	dataRuleFilter(guide);
	guide.setPage(page);
	page.setList(mapper.findGuideScreenList(guide));
	return page;
}
/**
* @Title: getGuideList
* @Description: 获取导游列表
* @author  彭善智
* @Data 2018年12月5日下午2:41:36
*/
public List<Guide> getGuideList(Guide guide) {
	return mapper.getGuideList(guide);
}
	/**
	 * 根据搜索条件模糊搜索导游
	 * */
	public List<Guide> findguidesByName(Guide guide) {
		return guideMapper.findguidesByName(guide);
	}
	/**
	 * 根据当地玩家ID获取导游
	 * */
	public List<Guide> findguidesByPlayerId(String guides) {
		return guideMapper.findguidesByPlayerId(guides);
	}
	
	//获取当地玩家列表
	public List<Guide> getPlayerList(Map<String, Object> param){
		List<Guide> guideList = guideMapper.getPlayerList(param);
		for (Guide guide : guideList) {
			guide.setDeltails(StringEscapeUtils.unescapeHtml(guide.getDeltails()));
		}
		return guideList;
	}
	
	//获取导游列表
	public List<Guide> findGuideList(){
		return guideMapper.findGuideList();
	};
	
	//购物车获取导游信息
	public Guide productCar_findGuide(Integer id){
		return mapper.productCar_findGuide(id);
	};
}