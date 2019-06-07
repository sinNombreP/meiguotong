/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.web.liner_line;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
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
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.QiniuUtils;
import com.jeeplus.common.config.Global;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.UploadHelper;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.jeeplus.modules.meiguotong.entity.comcity.ComCity;
import com.jeeplus.modules.meiguotong.entity.comlanguage.ComLanguage;
import com.jeeplus.modules.meiguotong.entity.comtag.ComTag;
import com.jeeplus.modules.meiguotong.entity.liner.Liner;
import com.jeeplus.modules.meiguotong.entity.liner.LinerCourse;
import com.jeeplus.modules.meiguotong.entity.liner.LinerPort;
import com.jeeplus.modules.meiguotong.entity.liner.LinerTime;
import com.jeeplus.modules.meiguotong.entity.liner_line.LinerLine;
import com.jeeplus.modules.meiguotong.entity.linerroom.LinerRoom;
import com.jeeplus.modules.meiguotong.entity.linertrip.LinerTrip;
import com.jeeplus.modules.meiguotong.entity.product.WeekDate;
import com.jeeplus.modules.meiguotong.entity.scenicspot.ScenicSpot;
import com.jeeplus.modules.meiguotong.entity.settingtitle.SettingTitle;
import com.jeeplus.modules.meiguotong.service.comcity.ComCityService;
import com.jeeplus.modules.meiguotong.service.comlanguage.ComLanguageService;
import com.jeeplus.modules.meiguotong.service.comtag.ComTagService;
import com.jeeplus.modules.meiguotong.service.liner.LinerCourseService;
import com.jeeplus.modules.meiguotong.service.liner.LinerPortService;
import com.jeeplus.modules.meiguotong.service.liner.LinerService;
import com.jeeplus.modules.meiguotong.service.liner.LinerTimeService;
import com.jeeplus.modules.meiguotong.service.liner_line.LinerLineService;
import com.jeeplus.modules.meiguotong.service.linerroom.LinerRoomService;
import com.jeeplus.modules.meiguotong.service.linertrip.LinerTripService;
import com.jeeplus.modules.meiguotong.service.product.RouteService;
import com.jeeplus.modules.meiguotong.service.scenicspot.ScenicSpotService;
import com.jeeplus.modules.meiguotong.service.settingtitle.SettingTitleService;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.utils.CodeGenUtils;
import com.jeeplus.modules.sys.utils.UserUtils;

/**
 * 邮轮航线管理Controller
 * @author cdq
 * @version 2018-08-13
 */
@Controller
@RequestMapping(value = "${adminPath}/meiguotong/liner_line/linerLine")
public class LinerLineController extends BaseController {

	@Autowired
	private LinerLineService linerLineService;
	@Autowired
	private LinerService linerService;
	@Autowired
	private ComLanguageService comLanguageService;
	@Autowired
	private LinerRoomService linerRoomService;
	@Autowired
	private LinerTripService linerTripService;
	@Autowired
	private RouteService routeService;
	@Autowired
	private LinerCourseService linerCourseService;
	@Autowired
	private ComCityService comCityService;
	@Autowired
	private LinerPortService linerPortService;
	@Autowired
	private ComTagService comTagService;
	@Autowired
	private ScenicSpotService scenicSpotService;
	@Autowired
	private LinerTimeService linerTimeService;
	@Autowired
	private SettingTitleService settingTitleService;
	
	
	@ModelAttribute
	public LinerLine get(@RequestParam(required=false) String id) {
		LinerLine entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = linerLineService.get(id);
		}
		if (entity == null){
			entity = new LinerLine();
		}
		return entity;
	}
	
	/**
	 * 邮轮航线管理列表页面
	 */
	@RequiresPermissions("meiguotong:liner_line:linerLine:list")
	@RequestMapping(value = {"list", ""})
	public String list(Model model) {
		User user = UserUtils.getUser();
		if(user!=null){
			if(user.getAgentid()==null){
				model.addAttribute("sysType",1);
			}else{
				model.addAttribute("sysType",2);
			}
		}
		return "modules/meiguotong/liner_line/linerLineList";
	}
	
		/**
	 * 邮轮航线管理列表数据
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:liner_line:linerLine:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(LinerLine linerLine, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<LinerLine> page = linerLineService.findPage(new Page<LinerLine>(request, response), linerLine); 
		return getBootstrapData(page);
	}

	/**
	 * 增加邮轮航线管理表单页面
	 */
	@RequiresPermissions(value={"meiguotong:liner_line:linerLine:view","meiguotong:liner_line:linerLine:add","meiguotong:liner_line:linerLine:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(LinerLine linerLine, Model model) {
		List<ComLanguage> languageList=comLanguageService.findLanguage();
		model.addAttribute("linerLine", linerLine);
		model.addAttribute("languageList", languageList);
		//获取所有的星期
		model.addAttribute("weekDateList",routeService.getWeekAll());
		//获取所有的天数
		model.addAttribute("dayList",routeService.getDayAll());
		if(StringUtils.isBlank(linerLine.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/meiguotong/liner_line/linerLineForm";
	}
	/**
	 * 编辑/查看邮轮航线管理表单页面
	 */
	@RequiresPermissions(value={"meiguotong:liner_line:linerLine:view","meiguotong:liner_line:linerLine:add","meiguotong:liner_line:linerLine:edit"},logical=Logical.OR)
	@RequestMapping(value = "ViewForm")
	public String ViewForm(LinerLine linerLine, Model model) {
		List<ComLanguage> languageList=comLanguageService.findLanguage();
		String tagids=linerLine.getTagId();
		List<ComTag> tagList=new ArrayList<>();
		if(StringUtils.isNotBlank(tagids)){
			String[] tags= tagids.split(",");
			for(String a:tags){
				ComTag tag=comTagService.get(a);
				tagList.add(tag);
			}
		}
		model.addAttribute("tagList",tagList);		//属性
		//航区
		LinerCourse linerCourse = new LinerCourse();
		linerCourse.setLanguageId(linerLine.getLanguageId());
		List<LinerCourse> linerCourseList = linerCourseService.findCourseList(linerCourse);
		//获取城市
		ComCity comCity = new ComCity();
		comCity.setLanguageId(linerLine.getLanguageId().toString());
		List<ComCity> cityList = comCityService.getCityList(comCity);
		//获取城市下的港口
		LinerPort linerPort = new LinerPort();
		linerPort.setLanguageId(linerLine.getLanguageId());
		linerPort.setCityid(Integer.parseInt(linerLine.getStartCity()));
		List<LinerPort> portList = linerPortService.getPortByCity(linerPort);
		//获取游轮
		Liner liner = new Liner();
		liner.setLanguageId(linerLine.getLanguageId());
		liner.setAgentid(linerLine.getAgentid());
		List<Liner> linerList = linerService.getLinerBylanguage(liner);
		//获取景点
		ScenicSpot scenicSpot = new ScenicSpot();
		scenicSpot.setLanguageId(linerLine.getLanguageId());
		List<ScenicSpot> scenicSpotlist = scenicSpotService.getScenicSpot(scenicSpot);
		
		model.addAttribute("linerCourseList", linerCourseList);   //航区
		model.addAttribute("linerList", linerList);                //邮轮名称
		model.addAttribute("cityList", cityList);                   //城市
		model.addAttribute("portList", portList);                   //港口
		model.addAttribute("scenicSpotlist", scenicSpotlist);       //景点
		model.addAttribute("linerLine", linerLine);
		model.addAttribute("languageList", languageList);
		//获取所有的星期
		model.addAttribute("weekDateList",routeService.getWeekAll());
		
		//获取游轮航线日期设置
		LinerTime linerTime = linerTimeService.getLinerTime(Integer.parseInt(linerLine.getId()));
		model.addAttribute("linerTime",linerTime);
		//获取所有的天数
				List<WeekDate> list1 = routeService.getDayAll();
				if(linerTime.getDayDate()!=null){
					String[] dayDate= linerTime.getDayDate().split(",");
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
		//房间的数据
		LinerRoom linerRoom=new LinerRoom();
		linerRoom.setLinerLineId(Integer.parseInt(linerLine.getId()));
		List<LinerRoom> linerRoomList=linerRoomService.getLinerRoomList(linerRoom);
		model.addAttribute("linerRoomList",linerRoomList);		
		//邮轮行程
		LinerTrip linerTrip =new LinerTrip();
		linerTrip.setLinerLineId(Integer.parseInt(linerLine.getId()));
		List<LinerTrip> linerTripList=linerTripService.getTripList(linerTrip);
		model.addAttribute("linerTripList", linerTripList);
		
		User user = UserUtils.getUser();
		if(user!=null){
			if(user.getAgentid()==null){
				model.addAttribute("sysType",1);
			}else{
				model.addAttribute("sysType",2);
			}
		}
		//获取新增的详情标题
		SettingTitle settingTitle = new SettingTitle();
		settingTitle.setLanguageId(linerLine.getLanguageId());
		settingTitle.setType(4);
		if(linerLine.getId()!=null){
			settingTitle.setProid(Integer.parseInt(linerLine.getId()));
		}
		List<SettingTitle> listTitle = settingTitleService.getAddTitle(settingTitle);
		model.addAttribute("listTitle",listTitle);
		return "modules/meiguotong/liner_line/linerLineViewForm";
	}

	/**
	 * 保存邮轮航线管理
	 */
	@RequiresPermissions(value={"meiguotong:liner_line:linerLine:add","meiguotong:liner_line:linerLine:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(LinerLine linerLine, Model model,RedirectAttributes redirectAttributes){
		if (!beanValidator(model, linerLine)){
			return form(linerLine,model);
		}
		//新增或编辑表单保存
		linerLineService.save(linerLine);//保存
		addMessage(redirectAttributes, "保存邮轮航线管理成功");
		return "redirect:"+Global.getAdminPath()+"/meiguotong/liner_line/linerLine/?repage";
	}
	/**
	 * 修改状态
	 * @param orderSys1
	 * @param redirectAttributes
	 * @return
	 */
	 @ResponseBody
		@RequestMapping(value = "status")
		public AjaxJson status(LinerLine linerLine, RedirectAttributes redirectAttributes) {
			AjaxJson j = new AjaxJson();
			try {
				linerLineService.status(linerLine);
				 j.setSuccess(true);
				 j.setMsg("更新状态成功");
			} catch (Exception e) {
				j.setSuccess(false);
				j.setMsg("更新状态失败");
			}
			return j;
		}
	/**
	 * 根据语言获取游轮航区
	 */
	@ResponseBody
	@RequestMapping(value = "getLinerCourse")
	public AjaxJson getLineCourse(LinerLine linerLine, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		LinerCourse linerCourse = new LinerCourse();
		linerCourse.setLanguageId(linerLine.getLanguageId());
		List<LinerCourse> list = linerCourseService.findCourseList(linerCourse);
		j.getBody().put("list", list);
		return j;
	}
	/**
	 * 根据语言获取城市
	 */
	@ResponseBody
	@RequestMapping(value = "getCity")
	public AjaxJson getCity(LinerLine linerLine, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		ComCity comCity = new ComCity();
		comCity.setLanguageId(linerLine.getLanguageId().toString());
		List<ComCity> list = comCityService.getCityList(comCity);
		j.getBody().put("list", list);
		return j;
	}
	/**
	 * 根据语言获取港口
	 */
	@ResponseBody
	@RequestMapping(value = "getPort")
	public AjaxJson getPort(LinerLine linerLine, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		LinerPort linerPort = new LinerPort();
		linerPort.setLanguageId(linerLine.getLanguageId());
		linerPort.setCityid(Integer.parseInt(linerLine.getStartCity()));
		List<LinerPort> list = linerPortService.getPortByCity(linerPort);
		j.getBody().put("list", list);
		return j;
	}
	/**
	 * 根据语言获取游轮
	 */
	@ResponseBody
	@RequestMapping(value = "getLiner")
	public AjaxJson getLiner(LinerLine linerLine, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		Liner liner = new Liner();
		liner.setLanguageId(linerLine.getLanguageId());
		liner.preFind();
		List<Liner> list = linerService.getLinerBylanguage(liner);
		j.getBody().put("list", list);
		return j;
	}
	/**
	 * 根据语言获取游轮标签属性
	 */
	@ResponseBody
	@RequestMapping(value = "getTag")
	public AjaxJson getTag(LinerLine linerLine, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		ComTag comTag = new ComTag();
		comTag.setLanguageId(linerLine.getLanguageId());
		comTag.setType(3);
		comTag.setContent(linerLine.getName());
		comTag.setLabelAttrid(linerLine.getTagId());
		List<ComTag> list = comTagService.getTagByType(comTag);
		j.getBody().put("list", list);
		return j;
	}
	/**
	 * 根据语言获取景点
	 */
	@ResponseBody
	@RequestMapping(value = "getScenicSpot")
	public AjaxJson getScenicSpot(LinerLine linerLine, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		ScenicSpot scenicSpot = new ScenicSpot();
		scenicSpot.setLanguageId(linerLine.getLanguageId());
		List<ScenicSpot> list = scenicSpotService.getScenicSpot(scenicSpot);
		j.getBody().put("list", list);
		return j;
	}
	
	/**
	 * 删除邮轮航线管理
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:liner_line:linerLine:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(LinerLine linerLine, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		linerLineService.delete(linerLine);
		j.setMsg("删除邮轮航线管理成功");
		return j;
	}
	
	/**
	 * 批量删除邮轮航线管理
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:liner_line:linerLine:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			linerLineService.delete(linerLineService.get(id));
		}
		j.setMsg("删除邮轮航线管理成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:liner_line:linerLine:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(LinerLine linerLine, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "邮轮航线管理"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<LinerLine> page = linerLineService.findPage(new Page<LinerLine>(request, response, -1), linerLine);
    		new ExportExcel("邮轮航线管理", LinerLine.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出邮轮航线管理记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("meiguotong:liner_line:linerLine:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<LinerLine> list = ei.getDataList(LinerLine.class);
			for (LinerLine linerLine : list){
				try{
					linerLineService.save(linerLine);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条邮轮航线管理记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条邮轮航线管理记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入邮轮航线管理失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/liner_line/linerLine/?repage";
    }
	
	/**
	 * 下载导入邮轮航线管理数据模板
	 */
	@RequiresPermissions("meiguotong:liner_line:linerLine:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "邮轮航线管理数据导入模板.xlsx";
    		List<LinerLine> list = Lists.newArrayList(); 
    		new ExportExcel("邮轮航线管理数据", LinerLine.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/liner_line/linerLine/?repage";
    }
	// 上传图片
				@ResponseBody
				@RequestMapping(value = "uploadFile")
				public AjaxJson uploadFile(HttpServletRequest request, HttpServletResponse response, Model model) {

					AjaxJson ajaxJson = new AjaxJson();

					try {
						List<MultipartFile> multipartFiles = UploadHelper.getMultipartFileList(request);
						if (multipartFiles.size() == 0) {
							// 给出提示,不允许没选择文件点击上传
							ajaxJson.setSuccess(false);
							ajaxJson.setMsg("上传图片为空");
							return ajaxJson;
						}
						MultipartFile multipartFile = multipartFiles.get(0);
						String imgPath="meiguotong/"+CodeGenUtils.getYear()+"/"+CodeGenUtils.getMonth()+"/"+CodeGenUtils.getDay();

						if(multipartFile != null && multipartFile.getSize()>0) {
							String key =imgPath +"/"+CodeGenUtils.getPicId()+".jpg";
							String filePath=QiniuUtils.uploadFile(multipartFile.getInputStream(), key);
							if(filePath.equals("")){
								ajaxJson.setSuccess(false);
								ajaxJson.setMsg("图片上传失败");
							}else{
								ajaxJson.setSuccess(true);
								ajaxJson.setMsg("图片上传成功");
								filePath=QiniuUtils.QiniuUrl+ key;
								ajaxJson.put("filePath", filePath);
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
						ajaxJson.setSuccess(false);
						ajaxJson.setMsg("因未知原因失败");
					}
					return ajaxJson;
				}
}