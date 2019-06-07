/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.web.travel;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

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
import com.jeeplus.modules.meiguotong.entity.comlanguage.ComLanguage;
import com.jeeplus.modules.meiguotong.entity.travel.TravelCustomization;
import com.jeeplus.modules.meiguotong.entity.travel.TravelDetails;
import com.jeeplus.modules.meiguotong.service.comlanguage.ComLanguageService;
import com.jeeplus.modules.meiguotong.service.travel.TravelCustomizationService;
import com.jeeplus.modules.meiguotong.service.travel.TravelDetailsService;

/**
 * 旅游定制Controller
 * @author psz
 * @version 2018-08-27
 */
@Controller
@RequestMapping(value = "${adminPath}/meiguotong/travel/travelCustomization")
public class TravelCustomizationController extends BaseController {

	@Autowired
	private TravelCustomizationService travelCustomizationService;
	@Autowired
	private ComLanguageService comLanguageService;
	@Autowired
	private TravelDetailsService travelDetailsService;
	
	@ModelAttribute
	public TravelCustomization get(@RequestParam(required=false) String id) {
		TravelCustomization entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = travelCustomizationService.get(id);
		}
		if (entity == null){
			entity = new TravelCustomization();
		}
		return entity;
	}
	
	/**
	 * 旅游定制列表页面
	 */
	@RequiresPermissions("meiguotong:travel:travelCustomization:list")
	@RequestMapping(value = {"list", ""})
	public String list(TravelCustomization travelCustomization, Model model) {
		//语言
		List<ComLanguage> comLanguageList = comLanguageService.findList(new ComLanguage());
		model.addAttribute("comLanguageList",comLanguageList);
		return "modules/meiguotong/travel/travelCustomizationList";
	}
	
		/**
	 * 旅游定制列表数据
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:travel:travelCustomization:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(TravelCustomization travelCustomization, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<TravelCustomization> page = travelCustomizationService.findPage(new Page<TravelCustomization>(request, response), travelCustomization); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑旅游定制表单页面
	 */
//	@RequiresPermissions(value={"meiguotong:travel:travelCustomization:view","meiguotong:travel:travelCustomization:add","meiguotong:travel:travelCustomization:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(TravelCustomization travelCustomization, Model model) {
		model.addAttribute("travelCustomization", travelCustomization);
		//旅游定制详情
		List<TravelDetails> travelDetailsList = travelDetailsService.getDataByTravelid(travelCustomization.getId());
		model.addAttribute("travelDetailsList", travelDetailsList);
		//语言
		List<ComLanguage> comLanguageList = comLanguageService.findList(new ComLanguage());
		model.addAttribute("comLanguageList",comLanguageList);
		return "modules/meiguotong/travel/travelCustomizationForm";
	}

	/**
	 * 保存旅游定制
	 */
//	@RequiresPermissions(value={"meiguotong:travel:travelCustomization:add","meiguotong:travel:travelCustomization:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(TravelCustomization travelCustomization, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, travelCustomization)){
			return form(travelCustomization, model);
		}
		//新增或编辑表单保存
		travelCustomizationService.updateAdd(travelCustomization);//保存
		addMessage(redirectAttributes, "保存旅游定制成功");
		return "redirect:"+Global.getAdminPath()+"/meiguotong/travel/travelCustomization/?repage";
	}
	
	
	

	/** 
	* @Title: updateStatus 
	* @Description: 启用禁用
	* @author 彭善智
	* @date 2018年8月28日上午10:05:19
	*/ 
	@ResponseBody
//	@RequiresPermissions("meiguotong:travel:travelCustomization:updateStatus")
	@RequestMapping(value = "updateStatus")
	public AjaxJson updateStatus(TravelCustomization travelCustomization, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
			travelCustomization.setStatus(travelCustomization.getStatus()==1?2:1);
			travelCustomizationService.save(travelCustomization);
			j.setMsg("修改状态成功");
		}catch (Exception e) {
			j.setMsg("修改状态失败");
			j.setSuccess(false);
		}
		return j;
	}
	
	/**
	 * 删除旅游定制
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:travel:travelCustomization:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(TravelCustomization travelCustomization, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		travelCustomizationService.delete(travelCustomization);
		j.setMsg("删除旅游定制成功");
		return j;
	}
	
	/**
	 * 批量删除旅游定制
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:travel:travelCustomization:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			travelCustomizationService.delete(travelCustomizationService.get(id));
		}
		j.setMsg("删除旅游定制成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:travel:travelCustomization:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(TravelCustomization travelCustomization, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "旅游定制"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<TravelCustomization> page = travelCustomizationService.findPage(new Page<TravelCustomization>(request, response, -1), travelCustomization);
    		new ExportExcel("旅游定制", TravelCustomization.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出旅游定制记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("meiguotong:travel:travelCustomization:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<TravelCustomization> list = ei.getDataList(TravelCustomization.class);
			for (TravelCustomization travelCustomization : list){
				try{
					travelCustomizationService.save(travelCustomization);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条旅游定制记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条旅游定制记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入旅游定制失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/travel/travelCustomization/?repage";
    }
	
	/**
	 * 下载导入旅游定制数据模板
	 */
	@RequiresPermissions("meiguotong:travel:travelCustomization:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "旅游定制数据导入模板.xlsx";
    		List<TravelCustomization> list = Lists.newArrayList(); 
    		new ExportExcel("旅游定制数据", TravelCustomization.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/travel/travelCustomization/?repage";
    }

}