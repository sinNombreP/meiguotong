/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.service.scenicspot;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.meiguotong.entity.scenicspot.ScenicSpot;
import com.jeeplus.modules.meiguotong.entity.settingtitlepro.SettingTitlePro;
import com.jeeplus.modules.meiguotong.entity.travel.OrderTravelDetails;
import com.jeeplus.modules.meiguotong.mapper.scenicspot.ScenicSpotMapper;
import com.jeeplus.modules.meiguotong.mapper.settingtitlepro.SettingTitleProMapper;
import com.jeeplus.modules.sys.entity.sysUser.SysUser;

/**
 * 景点表Service
 * @author cdq
 * @version 2018-08-16
 */
@Service
@Transactional(readOnly = true)
public class ScenicSpotService extends CrudService<ScenicSpotMapper, ScenicSpot> {

	@Autowired
	private ScenicSpotMapper scenicSpotMapper;
	@Autowired
	private SettingTitleProMapper settingTitleProMapper;
	
	public ScenicSpot get(String id) {
		return super.get(id);
	}
	/**
	 * 定制景点介绍
	 * @param scenicSpot
	 * @return
	 */
	public ScenicSpot getScenic(ScenicSpot scenicSpot) {
		return mapper.getScenic(scenicSpot);
	}
	/**
	 * 景点详情接口
	 * @param id
	 * @return
	 */
	public ScenicSpot getScenicDetails(ScenicSpot scenicSpot) {
		scenicSpot = mapper.getScenicDetails(scenicSpot);
		scenicSpot.setIntroduce(StringEscapeUtils.unescapeHtml(scenicSpot.getIntroduce()));
		scenicSpot.setReserveInfo(StringEscapeUtils.unescapeHtml(scenicSpot.getReserveInfo()));
		scenicSpot.setRecommend(StringEscapeUtils.unescapeHtml(scenicSpot.getRecommend()));
		return scenicSpot;
	}
	
	/**
	 * 定制景点关键字搜索
	 * @param scenicSpot
	 * @return
	 */
	public List<ScenicSpot> getScenicByTitle(ScenicSpot scenicSpot) {
		return mapper.getScenicByTitle(scenicSpot);
	}
	/**
	 * 根据城市获取景点
	 * @param scenicSpot
	 * @return
	 */
	public List<ScenicSpot> getCityScenic(ScenicSpot scenicSpot) {
		return mapper.getCityScenic(scenicSpot);
	}
	public List<ScenicSpot> findList(ScenicSpot scenicSpot) {
		return super.findList(scenicSpot);
	}
	/**
	 * 景点名称关键字匹配搜索列表
	 * @param page
	 * @param scenicSpot
	 * @return
	 */
	public Page<ScenicSpot> selectScenicTitle(Page<ScenicSpot> page, ScenicSpot scenicSpot) {
		dataRuleFilter(scenicSpot);
		scenicSpot.setPage(page);
		page.setList(mapper.selectScenicTitle(scenicSpot));
		return page;
	}
	/**
	 * 景点列表搜索
	 * @param page
	 * @param scenicSpot
	 * @return
	 */
	public Page<ScenicSpot> selectScenicList(Page<ScenicSpot> page, ScenicSpot scenicSpot) {
		dataRuleFilter(scenicSpot);
		scenicSpot.setPage(page);
		page.setList(mapper.selectScenicList(scenicSpot));
		return page;
	}
	
	public Page<ScenicSpot> findPage(Page<ScenicSpot> page, ScenicSpot scenicSpot) {
		scenicSpot.preFind();
		return super.findPage(page, scenicSpot);
	}
	
	@Transactional(readOnly = false)
	public void save(ScenicSpot scenicSpot) {
		scenicSpot.preFind();
		if(scenicSpot.getAgentid()!=null){
			scenicSpot.setStatus(1);
		}
		super.save(scenicSpot);
		
		//添加编辑新增标题内容
		List<SettingTitlePro> settingTitleProList = scenicSpot.getSettingTitleProList();
		if(settingTitleProList!=null&&settingTitleProList.size()>0){
			for(SettingTitlePro a:settingTitleProList){
				if (a.getIsNewRecord()){
					a.setProid(Integer.parseInt(scenicSpot.getId()));
					settingTitleProMapper.insert(a);
				}else{
					a.setProid(Integer.parseInt(scenicSpot.getId()));
					settingTitleProMapper.update(a);
				}
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(ScenicSpot scenicSpot) {
		super.delete(scenicSpot);
	}
	/**
	 * 修改状态
	 * @param scenicSpot
	 */
	@Transactional(readOnly = false)
	public void status(ScenicSpot scenicSpot) {
		mapper.status(scenicSpot);
	}

	/** 
	* @Title: getTripScenic 
	* @Description: 获取景点
	* @author 彭善智
	* @date 2018年8月24日上午10:15:39
	*/ 
	public List<ScenicSpot> getTripScenic(ScenicSpot scenicSpot) {
		return mapper.getTripScenic(scenicSpot);
	}
	/** 
	 * 获取路线途经景点数据 
	 * @param scenicSpot
	 * @return
	 */
	public List<ScenicSpot> getRouteScenic(ScenicSpot scenicSpot) {
		return mapper.getRouteScenic(scenicSpot);
	}
	/**
	* @Title: getCitySpot
	* @Description: 获取城市景点
	* @author  彭善智
	* @Data 2018年11月13日上午11:56:36
	*/
	public List<ScenicSpot> getCitySpot(ScenicSpot scenicSpot) {
		return mapper.getCitySpot(scenicSpot);
	}
	/**
	* @Title: getScenicSpotList
	* @Description: 获取景点列表
	* @author  彭善智
	* @Data 2018年12月5日上午11:32:03
	*/
	public List<ScenicSpot> getScenicSpotList(ScenicSpot scenicSpot) {
		return mapper.getScenicSpotList(scenicSpot);
	}
	 /**
	   * @method: findListByCityId
	   * @Description:  根据城市获取景点
	   * @Author:   彭善智
	   * @Date:     2018/12/28 10:39
	   */
	public Page<ScenicSpot> findListByCityId(Page<ScenicSpot> page, ScenicSpot scenicSpot) {
		scenicSpot.setPage(page);
		page.setList(mapper.findListByCityId(scenicSpot));
		return page;
	}
	/**
	 * 获取所有景点供应商
	 * @return
	 */
	public List<SysUser> getUser(SysUser sysUser) {
		return mapper.getUser(sysUser);
	}
	/**
	 * 配置景点供应商
	 * @return
	 */
	@Transactional(readOnly = false)
	public void updateUser(ScenicSpot scenicSpot) {
		mapper.updateUser(scenicSpot);
	}
	/**
	 * 根据语言获取景点
	 * @param scenicSpot
	 * @return
	 */
	public List<ScenicSpot> getScenicSpot(ScenicSpot scenicSpot) {
		return mapper.getScenicSpot(scenicSpot);
	}
	/**
	 * 根据景点模糊搜索景点
	 * @param scenicSpot
	 * @return
	 */
	public List<ScenicSpot> findScenicsByName(ScenicSpot scenicSpot) {
		return scenicSpotMapper.findScenicsByName(scenicSpot);
	}
	
	/**
	 * 根据景点id搜索景点
	 * @param scenicSpot
	 * @return
	 */
	public List<ScenicSpot> findScenicsByscenicTop(String scenics) {
		return scenicSpotMapper.findScenicsByscenicTop(scenics);
	}
	
	/**
	 * 根据景点id查询景点并排序
	 * @param scenics
	 * @return
	 */
	public List<ScenicSpot> findScenicsByscenicTop2(Map<String, Object> param){
		return scenicSpotMapper.findScenicsByscenicTop2(param);
	}
	
	
	/*根据定制ID和城市id查询景点*/
	public List<ScenicSpot> findScenicByCityId(OrderTravelDetails oTravelDetails){
		return scenicSpotMapper.findScenicByCityId(oTravelDetails);
	};
}