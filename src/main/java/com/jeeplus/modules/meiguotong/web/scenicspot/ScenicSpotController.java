/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.web.scenicspot;

import com.google.common.collect.Lists;
import com.jeeplus.common.config.Global;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.common.utils.*;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.modules.meiguotong.entity.comcity.ComCity;
import com.jeeplus.modules.meiguotong.entity.comlanguage.ComLanguage;
import com.jeeplus.modules.meiguotong.entity.comtag.ComTag;
import com.jeeplus.modules.meiguotong.entity.liner_line.LinerLine;
import com.jeeplus.modules.meiguotong.entity.scenicspot.ScenicSpot;
import com.jeeplus.modules.meiguotong.entity.settingtitle.SettingTitle;
import com.jeeplus.modules.meiguotong.service.comcity.ComCityService;
import com.jeeplus.modules.meiguotong.service.comlanguage.ComLanguageService;
import com.jeeplus.modules.meiguotong.service.comtag.ComTagService;
import com.jeeplus.modules.meiguotong.service.scenicspot.ScenicSpotService;
import com.jeeplus.modules.meiguotong.service.settingtitle.SettingTitleService;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.entity.sysUser.SysUser;
import com.jeeplus.modules.sys.utils.CodeGenUtils;
import com.jeeplus.modules.sys.utils.UserUtils;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 景点表Controller
 * @author cdq
 * @version 2018-08-16
 */
@Controller
@RequestMapping(value = "${adminPath}/meiguotong/scenicspot/scenicSpot")
public class ScenicSpotController extends BaseController {

	@Autowired
	private ScenicSpotService scenicSpotService;
	@Autowired
	private ComLanguageService comLanguageService;
	@Autowired
	private ComCityService comCityService;
	@Autowired
	private ComTagService comTagService;
	@Autowired
	private SettingTitleService settingTitleService;
	
	@ModelAttribute
	public ScenicSpot get(@RequestParam(required=false) String id) {
		ScenicSpot entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = scenicSpotService.get(id);
		}
		if (entity == null){
			entity = new ScenicSpot();
		}
		return entity;
	}


	 /**
	   * @method: getscenicSopt
	   * @Description:  城市列表查看景点
	   * @Author:   彭善智
	   * @Date:     2018/12/28 10:14
	   */
	@RequestMapping(value = {"getscenicSopt"})
	public String getscenicSopt(ScenicSpot scenicSpot, Model model) {
		model.addAttribute("scenicSpot", scenicSpot);
		//语言
		List<ComLanguage> comLanguageList = comLanguageService.findList(new ComLanguage());
		model.addAttribute("comLanguageList",comLanguageList);
		return "modules/meiguotong/scenicspot2/scenicSpotList";
	}

	 /**
	   * @method: data2
	   * @Description:  景点列表数据
	   * @Author:   彭善智
	   * @Date:     2018/12/28 10:36
	   */
 	@ResponseBody
	@RequestMapping(value = "data2")
	public Map<String, Object> data2(ScenicSpot scenicSpot, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ScenicSpot> page = scenicSpotService.findListByCityId(new Page<ScenicSpot>(request, response), scenicSpot);
		return getBootstrapData(page);
	}

	/**
	 * 景点表列表页面
	 */
	@RequiresPermissions("meiguotong:scenicspot:scenicSpot:list")
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
		return "modules/meiguotong/scenicspot/scenicSpotList";
	}


		/**
	 * 景点表列表数据
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:scenicspot:scenicSpot:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(ScenicSpot scenicSpot, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ScenicSpot> page = scenicSpotService.findPage(new Page<ScenicSpot>(request, response), scenicSpot); 
		return getBootstrapData(page);
	}




	/**
	 * 查看,编辑景点表表单页面
	 */
	@RequiresPermissions(value={"meiguotong:scenicspot:scenicSpot:view","meiguotong:scenicspot:scenicSpot:add","meiguotong:scenicspot:scenicSpot:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(ScenicSpot scenicSpot, Model model) {
		User user = UserUtils.getUser();
		if (StringUtils.isNotBlank(user.getId())){
			scenicSpot.setLoginAgentid(user.getAgentid());
		}
		model.addAttribute("scenicSpot", scenicSpot);
		if(scenicSpot.getLoginAgentid()!=null){//供应商登录为添加
			model.addAttribute("isAdd", true);
		}
		if(!scenicSpot.getIsNewRecord()){
			String tagids=scenicSpot.getLabelAttrid();
			List<ComTag> tagList=new ArrayList<>();
			if(StringUtils.isNotBlank(tagids)){
				String[] tags= tagids.split(",");
				for(String a:tags){
					ComTag tag=comTagService.get(a);
					tagList.add(tag);
				}
			}
			model.addAttribute("tagList",tagList);		//属性
		}
		//获取新增的详情标题
		SettingTitle settingTitle = new SettingTitle();
		settingTitle.setLanguageId(scenicSpot.getLanguageId());
		settingTitle.setType(3);
		if(scenicSpot.getId()!=null){
			settingTitle.setProid(Integer.parseInt(scenicSpot.getId()));
		}
		List<SettingTitle> listTitle = settingTitleService.getAddTitle(settingTitle);
		model.addAttribute("listTitle",listTitle);
		return "modules/meiguotong/scenicspot/scenicSpotForm";
	}
	/**
	 * 增加景点表表单页面
	 */
	@RequestMapping(value = "AddForm")
	public String AddForm(ScenicSpot scenicSpot, Model model) {
		ComCity city = comCityService.get(scenicSpot.getCityId().toString());
		scenicSpot.setCityId(Integer.parseInt(city.getId()));
		scenicSpot.setCityName(city.getCityName());
		scenicSpot.setLanguageId(Integer.parseInt(city.getLanguageId()));
		scenicSpot.setLanguageName(city.getLanguageName());
		scenicSpot.setCountryId(city.getProvinceId().toString());
		model.addAttribute("scenicSpot", scenicSpot);
		if(StringUtils.isBlank(scenicSpot.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/meiguotong/scenicspot2/scenicSpotForm";
	}
	/**
	 * 增加景点表表单页面
	 */
	@RequestMapping(value = "view")
	public String view(ScenicSpot scenicSpot, Model model) {
		ComCity city = comCityService.get(scenicSpot.getCityId().toString());
		scenicSpot.setCityId(Integer.parseInt(city.getId()));
		scenicSpot.setCityName(city.getCityName());
		scenicSpot.setLanguageId(Integer.parseInt(city.getLanguageId()));
		scenicSpot.setLanguageName(city.getLanguageName());
		scenicSpot.setCountryId(city.getProvinceId().toString());
		User user = UserUtils.getUser();
		if (StringUtils.isNotBlank(user.getId())){
			scenicSpot.setLoginAgentid(user.getAgentid());
		}
		model.addAttribute("scenicSpot", scenicSpot);
		if(!scenicSpot.getIsNewRecord()){
			String tagids=scenicSpot.getLabelAttrid();
			List<ComTag> tagList=new ArrayList<>();
			if(StringUtils.isNotBlank(tagids)){
				String[] tags= tagids.split(",");
				for(String a:tags){
					ComTag tag=comTagService.get(a);
					tagList.add(tag);
				}
			}
			model.addAttribute("tagList",tagList);		//属性
		}
		if(StringUtils.isBlank(scenicSpot.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/meiguotong/scenicspot2/scenicSpotForm2";
	}
	/**
	 * 保存景点表
	 */
	@RequiresPermissions(value={"meiguotong:scenicspot:scenicSpot:add","meiguotong:scenicspot:scenicSpot:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(ScenicSpot scenicSpot, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, scenicSpot)){
			return form(scenicSpot, model);
		}
		//新增或编辑表单保存
		scenicSpotService.save(scenicSpot);//保存
		addMessage(redirectAttributes, "保存景点表成功");
		return "redirect:"+Global.getAdminPath()+"/meiguotong/scenicspot/scenicSpot/?repage";
	}
	/**
	 * 总后台保存景点
	 */
	@RequestMapping(value = "saveScenicSpot")
	public String saveScenicSpot(ScenicSpot scenicSpot, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, scenicSpot)){
			return form(scenicSpot, model);
		}
		//新增或编辑表单保存
		scenicSpot.setStatus(5);
		scenicSpotService.save(scenicSpot);//保存
		addMessage(redirectAttributes, "保存景点表成功");
		return "redirect:"+Global.getAdminPath()+"/meiguotong/scenicspot/scenicSpot/getscenicSopt?cityId="+scenicSpot.getCityId()+"&countryId"+scenicSpot.getCountryId();
	}
	
	 /** 
	* @Title: getTripScenic 
	* @Description: 获取景点
	* @author 彭善智
	* @date 2018年8月24日上午10:12:52
	*/ 
	@ResponseBody
	@RequestMapping(value = "getTripScenic")
	public AjaxJson getTripScenic(ScenicSpot scenicSpot, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
			if(scenicSpot.getSupplierId() == null) {
				scenicSpot.setSupplierId(UserUtils.getUser().getAgentid());
			}
			List<ScenicSpot> scenicSpotList = scenicSpotService.getTripScenic(scenicSpot);
			j.getBody().put("scenicSpotList", scenicSpotList);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("获取景点失败");
		}
		return j;
	}
	 /** 
	  * 获取途经景点数据
	  * @param scenicSpot
	  * @param redirectAttributes
	  * @return
	  */
	@ResponseBody
	@RequestMapping(value = "getScenic")
	public AjaxJson getScenic(ScenicSpot scenicSpot, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
			if(scenicSpot.getSupplierId() == null) {
				scenicSpot.setSupplierId(UserUtils.getUser().getAgentid());
			}
			List<ScenicSpot> scenicSpotList = scenicSpotService.getRouteScenic(scenicSpot);
			j.getBody().put("scenicSpotList", scenicSpotList);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("获取景点失败");
		}
		return j;
	}
	/**
	 * 修改状态
	 * @param linerLine
	 * @param redirectAttributes
	 * @return
	 */
	 @ResponseBody
	@RequestMapping(value = "status")
	public AjaxJson status(ScenicSpot scenicSpot, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
			scenicSpotService.status(scenicSpot);
			 j.setSuccess(true);
			 j.setMsg("更新状态成功");
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("更新状态失败");
		}
		return j;
	}
	 
	/**
	 * 获取所有景点供应商
	 */
	@ResponseBody
	@RequestMapping(value = "getUser")
	public AjaxJson getUser(SysUser sysUser, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		List<SysUser> list = scenicSpotService.getUser(sysUser);
		j.getBody().put("list", list);
		return j;
	}
	/**
	 * 配置景点供应商
	 */
	@ResponseBody
	@RequestMapping(value = "updateUser")
	public AjaxJson updateUser(ScenicSpot scenicSpot, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
			scenicSpotService.updateUser(scenicSpot);
			j.setSuccess(true);
		} catch (Exception e) {
			j.setSuccess(false);
			e.printStackTrace();
		}
		return j;
	}
	
	/**
	 * 删除景点表
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:scenicspot:scenicSpot:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(ScenicSpot scenicSpot, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		scenicSpotService.delete(scenicSpot);
		j.setMsg("删除景点表成功");
		return j;
	}
	
	/**
	 * 批量删除景点表
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:scenicspot:scenicSpot:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			scenicSpotService.delete(scenicSpotService.get(id));
		}
		j.setMsg("删除景点表成功");
		return j;
	}
	/**
	 * 根据语言获取景点标签属性
	 */
	@ResponseBody
	@RequestMapping(value = "getTag")
	public AjaxJson getTag(LinerLine linerLine, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		ComTag comTag = new ComTag();
		comTag.setLanguageId(linerLine.getLanguageId());
		comTag.setType(4);
		comTag.setContent(linerLine.getName());
		comTag.setLabelAttrid(linerLine.getTagId());
		List<ComTag> list = comTagService.getTagByType(comTag);
		j.getBody().put("list", list);
		return j;
	}
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:scenicspot:scenicSpot:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(ScenicSpot scenicSpot, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "景点表"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<ScenicSpot> page = scenicSpotService.findPage(new Page<ScenicSpot>(request, response, -1), scenicSpot);
    		new ExportExcel("景点表", ScenicSpot.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出景点表记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("meiguotong:scenicspot:scenicSpot:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<ScenicSpot> list = ei.getDataList(ScenicSpot.class);
			for (ScenicSpot scenicSpot : list){
				try{
					scenicSpotService.save(scenicSpot);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条景点表记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条景点表记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入景点表失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/scenicspot/scenicSpot/?repage";
    }
	
	/**
	 * 下载导入景点表数据模板
	 */
	@RequiresPermissions("meiguotong:scenicspot:scenicSpot:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "景点表数据导入模板.xlsx";
    		List<ScenicSpot> list = Lists.newArrayList(); 
    		new ExportExcel("景点表数据", ScenicSpot.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/scenicspot/scenicSpot/?repage";
    }
	/**
	 *  富文本图片上传
	 */
		@ResponseBody
		@RequestMapping(value = "uploadIMG")
		public void upload(HttpServletRequest request, HttpServletResponse response,
				@RequestParam(value="upfile") MultipartFile upfile)  
	            throws Exception {  

	        request.setCharacterEncoding("utf-8");  
	        response.setContentType("text/html");  
	        response.setCharacterEncoding("utf-8");  
	        String path="meiguotong/"+CodeGenUtils.getYear()+"/"+CodeGenUtils.getMonth()+"/"+CodeGenUtils.getDay();
			String url ="";
			String result ="";
			try {
				if(upfile != null) {
					String key = path +"-"+CodeGen.getPicId()+".jpg";
					QiniuUtils.uploadFile(upfile.getInputStream(), key);
					url = QiniuUtils.QiniuUrl+ key;
					result = "{ \"state\": \""  
						      + "SUCCESS" + "\", \"type\": \"" + "jpg" 
						      + "\", \"url\": \"" + url + "\"}";  
				}
			}catch (Exception e) {
				result = "{ \"state\": \""  
					      + "ERROR" + "\", \"type\": \"" + "jpg" 
					      + "\", \"url\": \"" + url + "\"}";  
			}
	  
	       result = result.replaceAll("\\\\", "\\\\");  
	       response.getWriter().print(result);  
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