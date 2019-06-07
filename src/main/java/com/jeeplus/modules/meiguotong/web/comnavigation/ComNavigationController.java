/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.web.comnavigation;

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
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.config.Global;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.jeeplus.modules.meiguotong.entity.comlanguage.ComLanguage;
import com.jeeplus.modules.meiguotong.entity.comnavigation.ComNavigation;
import com.jeeplus.modules.meiguotong.service.comlanguage.ComLanguageService;
import com.jeeplus.modules.meiguotong.service.comnavigation.ComNavigationService;

/**
 * 主导航Controller
 * @author cdq
 * @version 2018-07-27
 */
@Controller
@RequestMapping(value = "${adminPath}/meiguotong/comnavigation/comNavigation")
public class ComNavigationController extends BaseController {

	@Autowired
	private ComNavigationService comNavigationService;
	@Autowired
	private ComLanguageService comLanguageService;
	
	@ModelAttribute
	public ComNavigation get(@RequestParam(required=false) String id) {
		ComNavigation entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = comNavigationService.get(id);
		}
		if (entity == null){
			entity = new ComNavigation();
		}
		return entity;
	}
	
	/**
	 * 主导航列表页面
	 */
	@RequiresPermissions("meiguotong:comnavigation:comNavigation:list")
	@RequestMapping(value = {"list", ""})
	public String list(Model model) {
		List<ComLanguage> languageList =comLanguageService.findLanguage();
		model.addAttribute("languageList", languageList);
		return "modules/meiguotong/comnavigation/comNavigationList";
	}
	/**
	 * 热门城市列表页面
	 */
	@RequiresPermissions("meiguotong:comnavigation:comNavigation:HotCity")
	@RequestMapping(value = {"HotCity"})
	public String HotCity(Model model) {
		List<ComLanguage> languageList =comLanguageService.findLanguage();
		model.addAttribute("languageList", languageList);
		return "modules/meiguotong/comnavigation/comNavigationHotCityList";
	}
	
		/**
	 * 主导航列表数据
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:comnavigation:comNavigation:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(ComNavigation comNavigation, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ComNavigation> page = comNavigationService.findPage(new Page<ComNavigation>(request, response), comNavigation); 
		return getBootstrapData(page);
	}
    /**
     * 热门城市列表数据
     */
	@ResponseBody
	@RequiresPermissions("meiguotong:comnavigation:comNavigation:HotCity")
	@RequestMapping(value = "HotCityDate")
	public Map<String, Object> HotCityDate(ComNavigation comNavigation, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ComNavigation> page = comNavigationService.HotCity(new Page<ComNavigation>(request, response), comNavigation); 
		return getBootstrapData(page);
	}
	/**
	 * 热门城市的查看，增加，编辑主导航表单页面
	 */
	@RequiresPermissions(value={"meiguotong:comnavigation:comNavigation:view","meiguotong:comnavigation:comNavigation:add","meiguotong:comnavigation:comNavigation:edit"},logical=Logical.OR)
	@RequestMapping(value = "HotCityform")
	public String HotCityform(ComNavigation comNavigation, Model model) {
		//查找语言
		List<ComLanguage> languageList=comLanguageService.findLanguage();
		model.addAttribute("languageList", languageList);
		model.addAttribute("comNavigation", comNavigation);
		return "modules/meiguotong/comnavigation/comNavigationHotCityForm";
	}
	/**
	 * 主导航的查看，增加，编辑主导航表单页面
	 */
	@RequiresPermissions(value={"meiguotong:comnavigation:comNavigation:view","meiguotong:comnavigation:comNavigation:add","meiguotong:comnavigation:comNavigation:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(ComNavigation comNavigation, Model model) {
		//查找语言
		List<ComLanguage> languageList=comLanguageService.findLanguage();
		model.addAttribute("languageList", languageList);
		model.addAttribute("comNavigation", comNavigation);
		return "modules/meiguotong/comnavigation/comNavigationForm";
	}
	

	/**
	 * 保存主导航
	 */
	@ResponseBody
	@RequiresPermissions(value={"meiguotong:comnavigation:comNavigation:add","meiguotong:comnavigation:comNavigation:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(ComNavigation comNavigation, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, comNavigation)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		comNavigationService.save(comNavigation);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存成功");
		return j;
	}
	
	/**
	 * 删除主导航
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:comnavigation:comNavigation:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(ComNavigation comNavigation, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		comNavigationService.delete(comNavigation);
		j.setMsg("删除成功");
		return j;
	}
	
	/**
	 * 批量删除主导航
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:comnavigation:comNavigation:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			comNavigationService.delete(comNavigationService.get(id));
		}
		j.setMsg("删除成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:comnavigation:comNavigation:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(ComNavigation comNavigation, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "主导航"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<ComNavigation> page = comNavigationService.findPage(new Page<ComNavigation>(request, response, -1), comNavigation);
    		new ExportExcel("主导航", ComNavigation.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出主导航记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("meiguotong:comnavigation:comNavigation:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<ComNavigation> list = ei.getDataList(ComNavigation.class);
			for (ComNavigation comNavigation : list){
				try{
					comNavigationService.save(comNavigation);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条主导航记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条主导航记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入主导航失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/comnavigation/comNavigation/?repage";
    }
	
	/**
	 * 下载导入主导航数据模板
	 */
	@RequiresPermissions("meiguotong:comnavigation:comNavigation:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "主导航数据导入模板.xlsx";
    		List<ComNavigation> list = Lists.newArrayList(); 
    		new ExportExcel("主导航数据", ComNavigation.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/comnavigation/comNavigation/?repage";
    }

}