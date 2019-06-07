/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.web.product;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.jeeplus.common.config.Global;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.modules.meiguotong.entity.comcity.ComCity;
import com.jeeplus.modules.meiguotong.entity.comlanguage.ComLanguage;
import com.jeeplus.modules.meiguotong.entity.comtag.ComTag;
import com.jeeplus.modules.meiguotong.entity.product.Route;
import com.jeeplus.modules.meiguotong.entity.product.RouteContent;
import com.jeeplus.modules.meiguotong.entity.product.WeekDate;
import com.jeeplus.modules.meiguotong.entity.scenicspot.ScenicSpot;
import com.jeeplus.modules.meiguotong.entity.settingtitle.SettingTitle;
import com.jeeplus.modules.meiguotong.mapper.scenicspot.ScenicSpotMapper;
import com.jeeplus.modules.meiguotong.service.comcity.ComCityService;
import com.jeeplus.modules.meiguotong.service.comlanguage.ComLanguageService;
import com.jeeplus.modules.meiguotong.service.comtag.ComTagService;
import com.jeeplus.modules.meiguotong.service.product.RouteContentService;
import com.jeeplus.modules.meiguotong.service.product.RouteDateService;
import com.jeeplus.modules.meiguotong.service.product.RouteService;
import com.jeeplus.modules.meiguotong.service.product.RouteTimeService;
import com.jeeplus.modules.meiguotong.service.settingtitle.SettingTitleService;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.entity.sysUser.SysUser;
import com.jeeplus.modules.sys.service.sysUser.SysUserService;
import com.jeeplus.modules.sys.utils.UserUtils;

/**
 * 当地参团Controller
 * @author psz
 * @version 2018-08-13
 */
@Controller
@RequestMapping(value = "${adminPath}/meiguotong/product/route")
public class RouteController extends BaseController {

	@Autowired
	private RouteService routeService;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private ComTagService comTagService;
	@Autowired
	private ComLanguageService comLanguageService;
	@Autowired
	private ComCityService comCityService;
	@Autowired
	private RouteDateService routeDateService;
	@Autowired
	private RouteTimeService routeTimeService;
	@Autowired
	private RouteContentService routeContentService;
	@Autowired
	private SettingTitleService settingTitleService;
	@Autowired
	private ScenicSpotMapper scenicSpotMapper;
	
	@ModelAttribute
	public Route get(@RequestParam(required=false) String id) {
		Route entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = routeService.get(id);
		}
		if (entity == null){
			entity = new Route();
		}
		return entity;
	}
	
	/**
	 * 当地参团列表页面
	 */
	@RequiresPermissions("meiguotong:product:route:list")
	@RequestMapping(value = {"list", ""})
	public String list(Model model,Route route) {
		//供应商
		List<SysUser> sysUserList = sysUserService.getNameByType(route.getType()+1);
		model.addAttribute("sysUserList",sysUserList);
		//账号类型
		model.addAttribute("userType",UserUtils.getUser().getUserType());
		//语言
		List<ComLanguage> comLanguageList = comLanguageService.findList(new ComLanguage());
		model.addAttribute("comLanguageList",comLanguageList);
		//属性
		ComTag comTag = new ComTag();
		comTag.setType(route.getType());
		List<ComTag> comTagList = comTagService.getNameByType(comTag);
		model.addAttribute("comTagList",comTagList);
		//城市
		ComCity comCity = new ComCity();
		List<ComCity> comCityList = comCityService.getData(comCity);
		model.addAttribute("comCityList",comCityList);
		return "modules/meiguotong/product/routeList";
	}
	
		/**
	 * 当地参团列表数据
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:product:route:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Route route, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		if(user.getUserType()==2) {
			route.setAgentid(user.getAgentid());
		}
		Page<Route> page = routeService.findPage(new Page<Route>(request, response), route); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑当地参团表单页面
	 */
	@RequestMapping(value = "form")
//	@RequiresPermissions(value={"meiguotong:product:route:view","meiguotong:product:route:add","meiguotong:product:route:edit"},logical=Logical.OR)
	public String form(Route route, Model model) {
		if(StringUtils.isBlank(route.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		model.addAttribute("userType",UserUtils.getUser().getUserType());
		//语言
		List<ComLanguage> comLanguageList = comLanguageService.findList(new ComLanguage());
		model.addAttribute("comLanguageList",comLanguageList);
		//获取所有的星期
		model.addAttribute("weekDateList",routeService.getWeekAll());
		//获取所有的天数
		List<WeekDate> list1 = routeService.getDayAll();
		if(route.getDayDate()!=null){
			String[] dayDate= route.getDayDate().split(",");
			for(String day:dayDate){
				for(WeekDate b:list1){
					if(day.equals(b.getId())){
						b.setDigFlag(1);
						break;
					}
				}
			}
		}
		model.addAttribute("dayList",list1);
		//后台账号类型
		model.addAttribute("userType",UserUtils.getUser().getUserType());
		User user = UserUtils.getUser();
		if(user.getUserType() == 2) {
			route.setAgentid(user.getAgentid());
		}
		model.addAttribute("route",route);
		//获取所有的行程
		RouteContent routeContent = new RouteContent();
		routeContent.setRouteid(route.getRouteid());
		List<RouteContent> routeContentList = routeContentService.getContentList(routeContent);
		//List<RouteContent> routeContentList = routeContentService.findListByRouteid(route);
		model.addAttribute("routeContentList",routeContentList);
		//获取新增的详情标题
		SettingTitle settingTitle = new SettingTitle();
		settingTitle.setLanguageId(route.getLanguage());
		settingTitle.setType(route.getType());
		if(route.getId()!=null){
			settingTitle.setProid(Integer.parseInt(route.getId()));
		}
		List<SettingTitle> listTitle = settingTitleService.getAddTitle(settingTitle);
		model.addAttribute("listTitle",listTitle);
		//获取所有的途经景点
		List<ScenicSpot> scenicList = scenicSpotMapper.getScenicSpotRoute(route);
		model.addAttribute("scenicList",scenicList);
		return "modules/meiguotong/product/routeForm";
	}


	/**
	* @Title: form2
	* @Description: 查看已设置
	* @author 彭善智
	* @date 2018年8月27日下午8:45:25
	*/
	@RequestMapping(value = "form2")
	public String form2(Route route, Model model) {
		if(route.getDateType() == 2) {
			List<String> strs = new ArrayList<String>();
			for(String s : route.getWeekDate().split(",")) {
				String week = "";
				switch (Integer.parseInt(s)) {
				case 1: week="周日"; break;
				case 2: week="周一"; break;
				case 3: week="周二"; break;
				case 4: week="周三"; break;
				case 5: week="周四"; break;
				case 6: week="周五"; break;
				case 7: week="周六"; break;
				}
				strs.add(week);
			}	
			String weekDate = String.join(",", strs);
			route.setWeekDate(weekDate);
		}
		model.addAttribute("route",route);
		return "modules/meiguotong/product/routeForm2";
	}


	/**
	 * 保存当地参团
	 */
/*	@RequiresPermissions(value={"meiguotong:product:route:add","meiguotong:product:route:edit"},logical=Logical.OR)*/
	@RequestMapping(value = "save")
	public String save(Route route, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, route)){
			return form(route, model);
		}
		//新增或编辑表单保存
		routeService.updateAdd(route);//保存
		if(route.getType()==1){
			addMessage(redirectAttributes, "保存常规路线成功");
		}else{
			addMessage(redirectAttributes, "保存当地参团成功");
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/product/route/?repage&type="+route.getType();
	}
	
	
	/** 
	* @Title: updateStatus 
	* @Description: 修改审核状态
	* @author 彭善智
	* @date 2018年8月13日下午3:19:32
	*/ 
	@ResponseBody
/*	@RequiresPermissions("meiguotong:product:route:updateStatus")*/
	@RequestMapping(value = "updateStatus")
	public AjaxJson updateStatus(Route route, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		routeService.updateStatus(route);
		j.setMsg("修改审核状态成功");
		return j;
	}
	
	/**
	 * 删除当地参团
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:product:route:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(Route route, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		routeService.delete(route);
		j.setMsg("删除当地参团成功");
		return j;
	}
	
	/**
	 * 批量删除当地参团
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:product:route:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			routeService.delete(routeService.get(id));
		}
		j.setMsg("删除当地参团成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:product:route:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(Route route, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "当地参团"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Route> page = routeService.findPage(new Page<Route>(request, response, -1), route);
    		new ExportExcel("当地参团", Route.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出当地参团记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("meiguotong:product:route:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Route> list = ei.getDataList(Route.class);
			for (Route route : list){
				try{
					routeService.save(route);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条当地参团记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条当地参团记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入当地参团失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/product/route/?repage";
    }
	
	/**
	 * 下载导入当地参团数据模板
	 */
	@RequiresPermissions("meiguotong:product:route:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "当地参团数据导入模板.xlsx";
    		List<Route> list = Lists.newArrayList(); 
    		new ExportExcel("当地参团数据", Route.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/product/route/?repage";
    }

}