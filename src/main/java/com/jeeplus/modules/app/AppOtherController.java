package com.jeeplus.modules.app;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.modules.meiguotong.entity.comadd.ComAd;
import com.jeeplus.modules.meiguotong.entity.comarticle.ComArticle;
import com.jeeplus.modules.meiguotong.entity.comlanguage.ComLanguage;
import com.jeeplus.modules.meiguotong.entity.comnavigation.ComNavigation;
import com.jeeplus.modules.meiguotong.service.comadd.ComAdService;
import com.jeeplus.modules.meiguotong.service.comarticle.ComArticleService;
import com.jeeplus.modules.meiguotong.service.comlanguage.ComLanguageService;
import com.jeeplus.modules.meiguotong.service.comnavigation.ComNavigationService;
import com.jeeplus.modules.sys.entity.comFeedback.ComFeedback;
import com.jeeplus.modules.sys.entity.member.Member;
import com.jeeplus.modules.sys.entity.score.SorceLog;
import com.jeeplus.modules.sys.service.member.MemberService;

@Controller
@RequestMapping(value = "${adminPath}/interface/other")
public class AppOtherController {
	@Autowired
	private ComArticleService comArticleService;
	@Autowired
	private ComAdService comAdService;
	@Autowired
	private ComNavigationService comNavigationService;
	@Autowired
	private ComLanguageService comLanguageService;
	@Autowired
	private AppUtils appUtils;
	
	/**
	 * 网站文章列表接口
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "comArticleList")
	@ResponseBody
	public AjaxJson comArticleList(HttpServletRequest request, HttpServletResponse response, Model model) {
		
		AjaxJson ajaxJson = new AjaxJson();
		String type = request.getParameter("type");      //1.游客 2.会员
		String uid = request.getParameter("uid");      //会员ID，登陆后返回
		String time = request.getParameter("time");		//时间，登陆后返回
		String key = request.getParameter("key");       //密钥，登陆后返回
		String pageNo = request.getParameter("pageNo");//页码
		
		if (StringUtils.isBlank(type)) {
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_2);
			ajaxJson.setMsg("type传参为空");
			return ajaxJson;
		}
		if(Integer.parseInt(type)!=2){
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_2);
			ajaxJson.setMsg("type传参错误");
			return ajaxJson;
		}
		if (StringUtils.isBlank(uid)) {
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_2);
			ajaxJson.setMsg("uid传参为空");
			return ajaxJson;
		}
		if (StringUtils.isBlank(time)) {
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_2);
			ajaxJson.setMsg("time传参为空");
			return ajaxJson;
		}
		if (StringUtils.isBlank(key)) {
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_2);
			ajaxJson.setMsg("key传参为空");
			return ajaxJson;
		}
		Integer memberStatus = appUtils.keyIsTrue(uid, time, key);
		if (memberStatus != 0) {
			ajaxJson.getBody().put("memberStatus", memberStatus);
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_7);
			ajaxJson.setMsg(AppErrorUtils.error_7_desc);
			return ajaxJson;
		}
		if (StringUtils.isBlank(pageNo)) {
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_2);
			ajaxJson.setMsg("pageNo传参为空");
			return ajaxJson;
		}
		
		try {
			String pageSize1 = request.getParameter("pageSize");       //每页数
			Integer pageSize;
			if(StringUtils.isBlank(pageSize1)){
				pageSize = appUtils.getPageSize();
			}else{
				pageSize = Integer.parseInt(pageSize1);
			}
			
			ComArticle comArticle=new ComArticle();
			comArticle.setType(1);//1代表网站文章（type区分）
			Page<ComArticle> page=new Page<ComArticle>();
			page.setPageNo(Integer.parseInt(pageNo));
			page.setPageSize1(pageSize);
			
			Page<ComArticle> page1 = comArticleService.findComArticleList(page, comArticle);
		
			List<ComArticle> comArticleList=page1.getList();
			
			int totalPage=page1.getTotalPage();
			if(Integer.parseInt(pageNo)>totalPage){
				ajaxJson.getBody().put("body_list", null);
			}else{
				ajaxJson.getBody().put("body_list", comArticleList);
			}
			ajaxJson.getBody().put("body_total", totalPage);
			
			ajaxJson.setSuccess(true);
			ajaxJson.setErrorCode("0");
			ajaxJson.setMsg("获取网站文章列表成功");  
			return ajaxJson;
		} catch (Exception e) {
			e.printStackTrace();
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_3);
			ajaxJson.setMsg(AppErrorUtils.error_3_desc);
			return ajaxJson;
		}
	}
    /**
    *网站文章详情
    * @param request
    * @param response
    * @param model
    * @return
    */
	@ResponseBody
	@RequestMapping(value = "getComArticle")
	public AjaxJson getComArticle(HttpServletRequest request, HttpServletResponse response, Model model) {
		AjaxJson ajaxJson = new AjaxJson();
		String type = request.getParameter("type");//1.游客 2.会员
		String uid = request.getParameter("uid");
		String time = request.getParameter("time");
		String key = request.getParameter("key");
		String articleId=request.getParameter("articleId");//文章详情ID
		if (StringUtils.isBlank(type)) {
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_2);
			ajaxJson.setMsg("type传参为空");
			return ajaxJson;
		}
		if(Integer.parseInt(type)!=2){
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_2);
			ajaxJson.setMsg("type传参错误");
			return ajaxJson;
		}
		if (StringUtils.isBlank(uid)) {
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_2);
			ajaxJson.setMsg("uid传参为空");
			return ajaxJson;
		}
		if (StringUtils.isBlank(time)) {
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_2);
			ajaxJson.setMsg("time传参为空");
			return ajaxJson;
		}
		if (StringUtils.isBlank(key)) {
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_2);
			ajaxJson.setMsg("key传参为空");
			return ajaxJson;
		}
		if (StringUtils.isBlank(articleId)) {
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_2);
			ajaxJson.setMsg("articleId传参为空");
			return ajaxJson;
		}
		Integer memberStatus = appUtils.keyIsTrue(uid, time, key);
		if (memberStatus != 0) {
			ajaxJson.getBody().put("memberStatus", memberStatus);
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_7);
			ajaxJson.setMsg(AppErrorUtils.error_7_desc);
			return ajaxJson;
		}
		try {
			ComArticle comArticle=new ComArticle();
			comArticle.setType(1);
			comArticle.setId(articleId);
			ComArticle comArticle1=comArticleService.getComArtilce(comArticle);
			ajaxJson.getBody().put("comPosterList", comArticle1);
			ajaxJson.setSuccess(true);
			ajaxJson.setErrorCode("0");
			ajaxJson.setMsg("获取网站文章详情成功");
		} catch (Exception e) {
			e.printStackTrace();
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_3);
			ajaxJson.setMsg(AppErrorUtils.error_3_desc);
		}
		return ajaxJson;
	}
	/**
	 * 广告列表
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "comAddList")
	@ResponseBody
	public AjaxJson comAddList(HttpServletRequest request, HttpServletResponse response, Model model) {
		
		AjaxJson ajaxJson = new AjaxJson();
		String type = request.getParameter("type");      //1.游客 2.会员
		String uid = request.getParameter("uid");      //会员ID，登陆后返回
		String time = request.getParameter("time");		//时间，登陆后返回
		String key = request.getParameter("key");       //密钥，登陆后返回
		String pageNo = request.getParameter("pageNo");//页码
		
		if (StringUtils.isBlank(type)) {
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_2);
			ajaxJson.setMsg("type传参为空");
			return ajaxJson;
		}
		if(Integer.parseInt(type)!=2){
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_2);
			ajaxJson.setMsg("type传参错误");
			return ajaxJson;
		}
		if (StringUtils.isBlank(uid)) {
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_2);
			ajaxJson.setMsg("uid传参为空");
			return ajaxJson;
		}
		if (StringUtils.isBlank(time)) {
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_2);
			ajaxJson.setMsg("time传参为空");
			return ajaxJson;
		}
		if (StringUtils.isBlank(key)) {
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_2);
			ajaxJson.setMsg("key传参为空");
			return ajaxJson;
		}
		Integer memberStatus = appUtils.keyIsTrue(uid, time, key);
		if (memberStatus != 0) {
			ajaxJson.getBody().put("memberStatus", memberStatus);
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_7);
			ajaxJson.setMsg(AppErrorUtils.error_7_desc);
			return ajaxJson;
		}
		if (StringUtils.isBlank(pageNo)) {
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_2);
			ajaxJson.setMsg("pageNo传参为空");
			return ajaxJson;
		}
		try {
			String pageSize1 = request.getParameter("pageSize");       //每页数
			Integer pageSize;
			if(StringUtils.isBlank(pageSize1)){
				pageSize = appUtils.getPageSize();
			}else{
				pageSize = Integer.parseInt(pageSize1);
			}
			
			ComAd comAd=new ComAd();
			Page<ComAd> page=new Page<ComAd>();
			page.setPageNo(Integer.parseInt(pageNo));
			page.setPageSize1(pageSize);		
			Page<ComAd> page1 = comAdService.findComAdList(page, comAd);		
			List<ComAd> comAdList=page1.getList();			
			int totalPage=page1.getTotalPage();
			if(Integer.parseInt(pageNo)>totalPage){
				ajaxJson.getBody().put("body_list", null);
			}else{
				ajaxJson.getBody().put("body_list", comAdList);
			}
			ajaxJson.getBody().put("body_total", totalPage);
			
			ajaxJson.setSuccess(true);
			ajaxJson.setErrorCode("0");
			ajaxJson.setMsg("获取网站文章列表成功");  
			return ajaxJson;
		} catch (Exception e) {
			e.printStackTrace();
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_3);
			ajaxJson.setMsg(AppErrorUtils.error_3_desc);
			return ajaxJson;
		}
	}
	  /**
	    *网站文章详情
	    * @param request
	    * @param response
	    * @param model
	    * @return
	    */
		@ResponseBody
		@RequestMapping(value = "getComAdd")
		public AjaxJson getComAd(HttpServletRequest request, HttpServletResponse response, Model model) {
			AjaxJson ajaxJson = new AjaxJson();
			String type = request.getParameter("type");//1.游客 2.会员
			String uid = request.getParameter("uid");
			String time = request.getParameter("time");
			String key = request.getParameter("key");
			String comAdId=request.getParameter("comAdId");//广告详情ID
			if (StringUtils.isBlank(type)) {
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_2);
				ajaxJson.setMsg("type传参为空");
				return ajaxJson;
			}
			if(Integer.parseInt(type)!=2){
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_2);
				ajaxJson.setMsg("type传参错误");
				return ajaxJson;
			}
			if (StringUtils.isBlank(uid)) {
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_2);
				ajaxJson.setMsg("uid传参为空");
				return ajaxJson;
			}
			if (StringUtils.isBlank(time)) {
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_2);
				ajaxJson.setMsg("time传参为空");
				return ajaxJson;
			}
			if (StringUtils.isBlank(key)) {
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_2);
				ajaxJson.setMsg("key传参为空");
				return ajaxJson;
			}
			if (StringUtils.isBlank(comAdId)) {
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_2);
				ajaxJson.setMsg("comAdId传参为空");
				return ajaxJson;
			}
			Integer memberStatus = appUtils.keyIsTrue(uid, time, key);
			if (memberStatus != 0) {
				ajaxJson.getBody().put("memberStatus", memberStatus);
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_7);
				ajaxJson.setMsg(AppErrorUtils.error_7_desc);
				return ajaxJson;
			}
			try {
				ComAd comAd=new ComAd();
				comAd.setId(comAdId);
				ComAd comAd1=comAdService.getComAd(comAd);
				ajaxJson.getBody().put("comPosterList", comAd1);
				ajaxJson.setSuccess(true);
				ajaxJson.setErrorCode("0");
				ajaxJson.setMsg("获取广告详情成功");
			} catch (Exception e) {
				e.printStackTrace();
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_3);
				ajaxJson.setMsg(AppErrorUtils.error_3_desc);
			}
			return ajaxJson;
		}
		/**
		 * 主导航列表接口
		 * @param request
		 * @param response
		 * @param model
		 * @return
		 */
		@RequestMapping(value = "comNavigationList")
		@ResponseBody
		public AjaxJson comNavigationList(HttpServletRequest request, HttpServletResponse response, Model model) {
			
			AjaxJson ajaxJson = new AjaxJson();
			String type = request.getParameter("type");      //1.游客 2.会员
			String uid = request.getParameter("uid");      //会员ID，登陆后返回
			String time = request.getParameter("time");		//时间，登陆后返回
			String key = request.getParameter("key");       //密钥，登陆后返回
			String pageNo = request.getParameter("pageNo");//页码
			
			if (StringUtils.isBlank(type)) {
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_2);
				ajaxJson.setMsg("type传参为空");
				return ajaxJson;
			}
			if(Integer.parseInt(type)!=2){
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_2);
				ajaxJson.setMsg("type传参错误");
				return ajaxJson;
			}
			if (StringUtils.isBlank(uid)) {
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_2);
				ajaxJson.setMsg("uid传参为空");
				return ajaxJson;
			}
			if (StringUtils.isBlank(time)) {
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_2);
				ajaxJson.setMsg("time传参为空");
				return ajaxJson;
			}
			if (StringUtils.isBlank(key)) {
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_2);
				ajaxJson.setMsg("key传参为空");
				return ajaxJson;
			}
			Integer memberStatus = appUtils.keyIsTrue(uid, time, key);
			if (memberStatus != 0) {
				ajaxJson.getBody().put("memberStatus", memberStatus);
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_7);
				ajaxJson.setMsg(AppErrorUtils.error_7_desc);
				return ajaxJson;
			}
			if (StringUtils.isBlank(pageNo)) {
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_2);
				ajaxJson.setMsg("pageNo传参为空");
				return ajaxJson;
			}
			try {
				String pageSize1 = request.getParameter("pageSize");       //每页数
				Integer pageSize;
				if(StringUtils.isBlank(pageSize1)){
					pageSize = appUtils.getPageSize();
				}else{
					pageSize = Integer.parseInt(pageSize1);
				}
				
				ComNavigation comNavigation=new ComNavigation();
				comNavigation.setType(1);
				Page<ComNavigation> page=new Page<ComNavigation>();
				page.setPageNo(Integer.parseInt(pageNo));
				page.setPageSize1(pageSize);		
				Page<ComNavigation> page1 = comNavigationService.findNavigationList(page, comNavigation);		
				List<ComNavigation> comNavigationList=page1.getList();			
				int totalPage=page1.getTotalPage();
				if(Integer.parseInt(pageNo)>totalPage){
					ajaxJson.getBody().put("body_list", null);
				}else{
					ajaxJson.getBody().put("body_list", comNavigationList);
				}
				ajaxJson.getBody().put("body_total", totalPage);
				
				ajaxJson.setSuccess(true);
				ajaxJson.setErrorCode("0");
				ajaxJson.setMsg("获取主导航列表成功");  
				return ajaxJson;
			} catch (Exception e) {
				e.printStackTrace();
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_3);
				ajaxJson.setMsg(AppErrorUtils.error_3_desc);
				return ajaxJson;
			}
		}
		/**
		 *  热门城市列表接口
		 * @param request
		 * @param response
		 * @param model
		 * @return
		 */
		@RequestMapping(value = "HotCityList")
		@ResponseBody
		public AjaxJson HotCityList(HttpServletRequest request, HttpServletResponse response, Model model) {
			
			AjaxJson ajaxJson = new AjaxJson();
			String type = request.getParameter("type");      //1.游客 2.会员
			String uid = request.getParameter("uid");      //会员ID，登陆后返回
			String time = request.getParameter("time");		//时间，登陆后返回
			String key = request.getParameter("key");       //密钥，登陆后返回
			String pageNo = request.getParameter("pageNo");//页码

			Integer memberStatus = appUtils.keyIsTrue(uid, time, key);
			if (memberStatus != 0) {
				ajaxJson.getBody().put("memberStatus", memberStatus);
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_7);
				ajaxJson.setMsg(AppErrorUtils.error_7_desc);
				return ajaxJson;
			}
			if (StringUtils.isBlank(pageNo)) {
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_2);
				ajaxJson.setMsg("pageNo传参为空");
				return ajaxJson;
			}
			
			try {
				String pageSize1 = request.getParameter("pageSize");       //每页数
				Integer pageSize;
				if(StringUtils.isBlank(pageSize1)){
					pageSize = appUtils.getPageSize();
				}else{
					pageSize = Integer.parseInt(pageSize1);
				}
				
				ComNavigation comNavigation=new ComNavigation();
				comNavigation.setType(2);
				Page<ComNavigation> page=new Page<ComNavigation>();
				page.setPageNo(Integer.parseInt(pageNo));
				page.setPageSize1(pageSize);		
				Page<ComNavigation> page1 = comNavigationService.findNavigationList(page, comNavigation);		
				List<ComNavigation> comNavigationList=page1.getList();			
				int totalPage=page1.getTotalPage();
				if(Integer.parseInt(pageNo)>totalPage){
					ajaxJson.getBody().put("body_list", null);
				}else{
					ajaxJson.getBody().put("body_list", comNavigationList);
				}
				ajaxJson.getBody().put("body_total", totalPage);
				
				ajaxJson.setSuccess(true);
				ajaxJson.setErrorCode("0");
				ajaxJson.setMsg("获取热门城市列表成功");  
				return ajaxJson;
			} catch (Exception e) {
				e.printStackTrace();
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_3);
				ajaxJson.setMsg(AppErrorUtils.error_3_desc);
				return ajaxJson;
			}
		}
		/**
		 *  语言列表接口
		 * @param request
		 * @param response
		 * @param model
		 * @return
		 */
		@RequestMapping(value = "LanguageList")
		@ResponseBody
		public AjaxJson LanguageList(HttpServletRequest request, HttpServletResponse response, Model model) {
			
			AjaxJson ajaxJson = new AjaxJson();
			String type = request.getParameter("type");      //1.游客 2.会员
			String uid = request.getParameter("uid");      //会员ID，登陆后返回
			String time = request.getParameter("time");		//时间，登陆后返回
			String key = request.getParameter("key");       //密钥，登陆后返回
			String pageNo = request.getParameter("pageNo");//页码
			
			if (StringUtils.isBlank(type)) {
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_2);
				ajaxJson.setMsg("type传参为空");
				return ajaxJson;
			}
			if(Integer.parseInt(type)!=2){
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_2);
				ajaxJson.setMsg("type传参错误");
				return ajaxJson;
			}
			if (StringUtils.isBlank(uid)) {
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_2);
				ajaxJson.setMsg("uid传参为空");
				return ajaxJson;
			}
			if (StringUtils.isBlank(time)) {
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_2);
				ajaxJson.setMsg("time传参为空");
				return ajaxJson;
			}
			if (StringUtils.isBlank(key)) {
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_2);
				ajaxJson.setMsg("key传参为空");
				return ajaxJson;
			}
			Integer memberStatus = appUtils.keyIsTrue(uid, time, key);
			if (memberStatus != 0) {
				ajaxJson.getBody().put("memberStatus", memberStatus);
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_7);
				ajaxJson.setMsg(AppErrorUtils.error_7_desc);
				return ajaxJson;
			}
			if (StringUtils.isBlank(pageNo)) {
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_2);
				ajaxJson.setMsg("pageNo传参为空");
				return ajaxJson;
			}
			
			try {
				String pageSize1 = request.getParameter("pageSize");       //每页数
				Integer pageSize;
				if(StringUtils.isBlank(pageSize1)){
					pageSize = appUtils.getPageSize();
				}else{
					pageSize = Integer.parseInt(pageSize1);
				}
				
				ComLanguage comLanguage=new ComLanguage();
				Page<ComLanguage> page=new Page<ComLanguage>();
				page.setPageNo(Integer.parseInt(pageNo));
				page.setPageSize1(pageSize);		
				Page<ComLanguage> page1 = comLanguageService.findLanguageList(page, comLanguage);		
				List<ComLanguage> comLanguageList=page1.getList();			
				int totalPage=page1.getTotalPage();
				if(Integer.parseInt(pageNo)>totalPage){
					ajaxJson.getBody().put("body_list", null);
				}else{
					ajaxJson.getBody().put("body_list", comLanguageList);
				}
				ajaxJson.getBody().put("body_total", totalPage);
				
				ajaxJson.setSuccess(true);
				ajaxJson.setErrorCode("0");
				ajaxJson.setMsg("获取语言列表成功");  
				return ajaxJson;
			} catch (Exception e) {
				e.printStackTrace();
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_3);
				ajaxJson.setMsg(AppErrorUtils.error_3_desc);
				return ajaxJson;
			}
		}
		/**
		 *  公司新闻接口
		 * @param request
		 * @param response
		 * @param model
		 * @return
		 */
		@RequestMapping(value = "companyNewList")
		@ResponseBody
		public AjaxJson companyNewList(HttpServletRequest request, HttpServletResponse response, Model model) {
			
			AjaxJson ajaxJson = new AjaxJson();
			String type = request.getParameter("type");      //1.游客 2.会员
			String uid = request.getParameter("uid");      //会员ID，登陆后返回
			String time = request.getParameter("time");		//时间，登陆后返回
			String key = request.getParameter("key");       //密钥，登陆后返回
			String pageNo = request.getParameter("pageNo");//页码
			
			if (StringUtils.isBlank(type)) {
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_2);
				ajaxJson.setMsg("type传参为空");
				return ajaxJson;
			}
			if(Integer.parseInt(type)!=2){
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_2);
				ajaxJson.setMsg("type传参错误");
				return ajaxJson;
			}
			if (StringUtils.isBlank(uid)) {
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_2);
				ajaxJson.setMsg("uid传参为空");
				return ajaxJson;
			}
			if (StringUtils.isBlank(time)) {
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_2);
				ajaxJson.setMsg("time传参为空");
				return ajaxJson;
			}
			if (StringUtils.isBlank(key)) {
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_2);
				ajaxJson.setMsg("key传参为空");
				return ajaxJson;
			}
			Integer memberStatus = appUtils.keyIsTrue(uid, time, key);
			if (memberStatus != 0) {
				ajaxJson.getBody().put("memberStatus", memberStatus);
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_7);
				ajaxJson.setMsg(AppErrorUtils.error_7_desc);
				return ajaxJson;
			}
			if (StringUtils.isBlank(pageNo)) {
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_2);
				ajaxJson.setMsg("pageNo传参为空");
				return ajaxJson;
			}
			
			try {
				String pageSize1 = request.getParameter("pageSize");       //每页数
				Integer pageSize;
				if(StringUtils.isBlank(pageSize1)){
					pageSize = appUtils.getPageSize();
				}else{
					pageSize = Integer.parseInt(pageSize1);
				}
				
				ComArticle comArticle=new ComArticle();
				comArticle.setType(2);
				Page<ComArticle> page=new Page<ComArticle>();
				page.setPageNo(Integer.parseInt(pageNo));
				page.setPageSize1(pageSize);		
				Page<ComArticle> page1 = comArticleService.findCompanyNewList(page, comArticle);		
				List<ComArticle> comArticleList=page1.getList();			
				int totalPage=page1.getTotalPage();
				if(Integer.parseInt(pageNo)>totalPage){
					ajaxJson.getBody().put("body_list", null);
				}else{
					ajaxJson.getBody().put("body_list", comArticleList);
				}
				ajaxJson.getBody().put("body_total", totalPage);
				
				ajaxJson.setSuccess(true);
				ajaxJson.setErrorCode("0");
				ajaxJson.setMsg("获取公司新闻列表成功");  
				return ajaxJson;
			} catch (Exception e) {
				e.printStackTrace();
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_3);
				ajaxJson.setMsg(AppErrorUtils.error_3_desc);
				return ajaxJson;
			}
		}
		  /**
		    *网站文章详情
		    * @param request
		    * @param response
		    * @param model
		    * @return
		    */
			@ResponseBody
			@RequestMapping(value = "companyDetails")
			public AjaxJson companyDetails(HttpServletRequest request, HttpServletResponse response, Model model) {
				AjaxJson ajaxJson = new AjaxJson();
				String type = request.getParameter("type");//1.游客 2.会员
				String uid = request.getParameter("uid");
				String time = request.getParameter("time");
				String key = request.getParameter("key");
				String articleId=request.getParameter("articleId");//公司新闻Id
				if (StringUtils.isBlank(type)) {
					ajaxJson.setSuccess(false);
					ajaxJson.setErrorCode(AppErrorUtils.error_2);
					ajaxJson.setMsg("type传参为空");
					return ajaxJson;
				}
				if(Integer.parseInt(type)!=2){
					ajaxJson.setSuccess(false);
					ajaxJson.setErrorCode(AppErrorUtils.error_2);
					ajaxJson.setMsg("type传参错误");
					return ajaxJson;
				}
				if (StringUtils.isBlank(uid)) {
					ajaxJson.setSuccess(false);
					ajaxJson.setErrorCode(AppErrorUtils.error_2);
					ajaxJson.setMsg("uid传参为空");
					return ajaxJson;
				}
				if (StringUtils.isBlank(time)) {
					ajaxJson.setSuccess(false);
					ajaxJson.setErrorCode(AppErrorUtils.error_2);
					ajaxJson.setMsg("time传参为空");
					return ajaxJson;
				}
				if (StringUtils.isBlank(key)) {
					ajaxJson.setSuccess(false);
					ajaxJson.setErrorCode(AppErrorUtils.error_2);
					ajaxJson.setMsg("key传参为空");
					return ajaxJson;
				}
				if (StringUtils.isBlank(articleId)) {
					ajaxJson.setSuccess(false);
					ajaxJson.setErrorCode(AppErrorUtils.error_2);
					ajaxJson.setMsg("articleId传参为空");
					return ajaxJson;
				}
				Integer memberStatus = appUtils.keyIsTrue(uid, time, key);
				if (memberStatus != 0) {
					ajaxJson.getBody().put("memberStatus", memberStatus);
					ajaxJson.setSuccess(false);
					ajaxJson.setErrorCode(AppErrorUtils.error_7);
					ajaxJson.setMsg(AppErrorUtils.error_7_desc);
					return ajaxJson;
				}
				try {
					ComArticle comArticle=new ComArticle();
					comArticle.setType(2);
					comArticle.setId(articleId);
					ComArticle comArticle1=comArticleService.getComArtilce(comArticle);
					ajaxJson.getBody().put("comPosterList", comArticle1);
					ajaxJson.setSuccess(true);
					ajaxJson.setErrorCode("0");
					ajaxJson.setMsg("获取公司新闻详情成功");
				} catch (Exception e) {
					e.printStackTrace();
					ajaxJson.setSuccess(false);
					ajaxJson.setErrorCode(AppErrorUtils.error_3);
					ajaxJson.setMsg(AppErrorUtils.error_3_desc);
				}
				return ajaxJson;
			}
}
