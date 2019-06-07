package com.jeeplus.modules.base.task;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import com.jeeplus.common.utils.CacheUtils;
import com.jeeplus.modules.meiguotong.entity.comlanguage.ComLanguage;
import com.jeeplus.modules.meiguotong.entity.comnavigation.ComNavigation;
import com.jeeplus.modules.meiguotong.entity.module.ModuleContent;
import com.jeeplus.modules.meiguotong.entity.module.ModuleHtml;
import com.jeeplus.modules.meiguotong.mapper.comlanguage.ComLanguageMapper;
import com.jeeplus.modules.meiguotong.mapper.module.ModuleContentMapper;
import com.jeeplus.modules.meiguotong.mapper.module.ModuleHtmlMapper;
import com.jeeplus.modules.meiguotong.service.comnavigation.ComNavigationService;
import com.jeeplus.modules.meiguotong.service.module.ModuleHtmlService;
import com.jeeplus.modules.sys.service.SystemService;

import net.oschina.j2cache.redis.client.RedisClient;
import net.oschina.j2cache.redis.support.RedisClientFactory;


@Service
public class StartupListener implements ApplicationListener<ContextRefreshedEvent> {
	@Autowired
	private ComLanguageMapper comLanguageMapper;
	@Autowired
	private ComNavigationService comNavigationService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private ModuleHtmlMapper moduleHtmlMapper;
	@Autowired
	private ModuleContentMapper moduleContentMapper;
	@Autowired
	private RedisClientFactory redisClientFactory;
	
	private static final Logger log =  LoggerFactory.getLogger(StartupListener.class);

	private static final String APP_Home = "appHome";
	private static final String CACHE_NAV_LIST = "navList";
	private static final String CACHE_CONTENT_LIST = "contentList";
	private static final String CACHE_HOTCITY_LIST = "hotCityList";
	private static final String CACHE_ADVERT_LIST = "advertList";
	
	
    @Override
    public void onApplicationEvent(ContextRefreshedEvent evt) {
    	log.debug(">>>>>>>>>>>>系统启动完成，onApplicationEvent()");
        if (evt.getApplicationContext().getParent() == null) {
            return;
        }
        RedisClient client=redisClientFactory.getResource();
       //获取所有语言
        List<ComLanguage> languageList = comLanguageMapper.findLanguageList(new ComLanguage());
        if(languageList!=null&&languageList.size()>0){
        	for(ComLanguage a:languageList){
        		 //获取首页导航
	              ComNavigation comNavigation = new ComNavigation();
	              comNavigation.setLanguageId(Integer.parseInt(a.getId()));
	              List<ComNavigation> list = comNavigationService.getComNavigation(comNavigation);
	              //CacheUtils.put(APP_Home, CACHE_NAV_LIST+"_"+a.getId(), list);
	              String aa = CACHE_NAV_LIST+"_"+a.getId();
	              client.set(aa.getBytes(), SerializeUtil.serialize(list));
	              //首页热门目的地
	              ComNavigation comNavigation1 = new ComNavigation();
	              comNavigation1.setLanguageId(Integer.parseInt(a.getId()));
	              List<ComNavigation> list2 = comNavigationService.getHotCity(comNavigation);
	              //CacheUtils.put(APP_Home, CACHE_HOTCITY_LIST+"_"+a.getId(), list2);
	              String bb = CACHE_HOTCITY_LIST+"_"+a.getId();
	              client.set(bb.getBytes(), SerializeUtil.serialize(list2));
	              //模块数据
	              //获取所有网站页面
	              //判断语言有没有数据
	              Integer count = moduleContentMapper.getCount(a.getId());
	              if(count>0){
	            	  List<ModuleHtml> list1 = moduleHtmlMapper.findList(new ModuleHtml());
		              if(list1!=null&&list1.size()>0){
		            	  for(ModuleHtml b:list1){
		            		  if("内容模块".equals(b.gethtmlSeal())){
		            			  List<ModuleContent> moduleContentList = systemService.getHomeContent( b.getId(), a.getId());
			            		  //CacheUtils.put(APP_Home, CACHE_CONTENT_LIST+"_"+b.getHtmlName()+"_"+b.gethtmlSeal()+"_"+a.getId(), moduleContentList);
			            		  String cc = CACHE_CONTENT_LIST+"_"+b.getHtmlName()+"_"+b.gethtmlSeal()+"_"+a.getId();
			    	              client.set(cc.getBytes(), SerializeUtil.serialize(moduleContentList));
		            		  }else if("广告模块".equals(b.gethtmlSeal())){
		            			  List<ModuleContent> moduleContentList = systemService.getAdvertContent( b.getId(), a.getId());
			            		  //CacheUtils.put(APP_Home, CACHE_ADVERT_LIST+"_"+b.getHtmlName()+"_"+b.gethtmlSeal()+"_"+a.getId(), moduleContentList);
			            		  String cc = CACHE_ADVERT_LIST+"_"+b.getHtmlName()+"_"+b.gethtmlSeal()+"_"+a.getId();
			    	              client.set(cc.getBytes(), SerializeUtil.serialize(moduleContentList));
		            		  }
		            	  }
		              }
	              } 
            }
        }
        try {
			client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}