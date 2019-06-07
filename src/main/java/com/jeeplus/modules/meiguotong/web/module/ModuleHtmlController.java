/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.web.module;

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
import com.jeeplus.modules.meiguotong.entity.module.ModuleHtml;
import com.jeeplus.modules.meiguotong.service.module.ModuleHtmlService;

/**
 * 网站页面Controller
 * @author psz
 * @version 2018-12-03
 */
@Controller
@RequestMapping(value = "${adminPath}/meiguotong/module/moduleHtml")
public class ModuleHtmlController extends BaseController {

	@Autowired
	private ModuleHtmlService moduleHtmlService;
	
	@ModelAttribute
	public ModuleHtml get(@RequestParam(required=false) String id) {
		ModuleHtml entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = moduleHtmlService.get(id);
		}
		if (entity == null){
			entity = new ModuleHtml();
		}
		return entity;
	}
	
	/**
	 * 网站页面列表页面
	 */
	@RequiresPermissions("meiguotong:module:moduleHtml:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/meiguotong/module/moduleHtmlList";
	}
	
		/**
	 * 网站页面列表数据
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:module:moduleHtml:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(ModuleHtml moduleHtml, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ModuleHtml> page = moduleHtmlService.findPage(new Page<ModuleHtml>(request, response), moduleHtml); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑网站页面表单页面
	 */
	@RequiresPermissions(value={"meiguotong:module:moduleHtml:view","meiguotong:module:moduleHtml:add","meiguotong:module:moduleHtml:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(ModuleHtml moduleHtml, Model model) {
		model.addAttribute("moduleHtml", moduleHtml);
		if(StringUtils.isBlank(moduleHtml.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/meiguotong/module/moduleHtmlForm";
	}

	/**
	 * 保存网站页面
	 */
	@RequiresPermissions(value={"meiguotong:module:moduleHtml:add","meiguotong:module:moduleHtml:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(ModuleHtml moduleHtml, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, moduleHtml)){
			return form(moduleHtml, model);
		}
		//新增或编辑表单保存
		moduleHtmlService.save(moduleHtml);//保存
		addMessage(redirectAttributes, "保存网站页面成功");
		return "redirect:"+Global.getAdminPath()+"/meiguotong/module/moduleHtml/?repage";
	}
	
	/**
	 * 删除网站页面
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:module:moduleHtml:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(ModuleHtml moduleHtml, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		moduleHtmlService.delete(moduleHtml);
		j.setMsg("删除网站页面成功");
		return j;
	}
	
	/**
	 * 批量删除网站页面
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:module:moduleHtml:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			moduleHtmlService.delete(moduleHtmlService.get(id));
		}
		j.setMsg("删除网站页面成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:module:moduleHtml:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(ModuleHtml moduleHtml, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "网站页面"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<ModuleHtml> page = moduleHtmlService.findPage(new Page<ModuleHtml>(request, response, -1), moduleHtml);
    		new ExportExcel("网站页面", ModuleHtml.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出网站页面记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("meiguotong:module:moduleHtml:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<ModuleHtml> list = ei.getDataList(ModuleHtml.class);
			for (ModuleHtml moduleHtml : list){
				try{
					moduleHtmlService.save(moduleHtml);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条网站页面记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条网站页面记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入网站页面失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/module/moduleHtml/?repage";
    }
	
	/**
	 * 下载导入网站页面数据模板
	 */
	@RequiresPermissions("meiguotong:module:moduleHtml:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "网站页面数据导入模板.xlsx";
    		List<ModuleHtml> list = Lists.newArrayList(); 
    		new ExportExcel("网站页面数据", ModuleHtml.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/module/moduleHtml/?repage";
    }

}