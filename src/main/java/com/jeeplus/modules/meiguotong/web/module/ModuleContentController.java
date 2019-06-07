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
import com.jeeplus.modules.meiguotong.entity.module.ModuleContent;
import com.jeeplus.modules.meiguotong.service.module.ModuleContentService;

/**
 * 模块内容Controller
 * @author psz
 * @version 2018-12-03
 */
@Controller
@RequestMapping(value = "${adminPath}/meiguotong/module/moduleContent")
public class ModuleContentController extends BaseController {

	@Autowired
	private ModuleContentService moduleContentService;
	
	@ModelAttribute
	public ModuleContent get(@RequestParam(required=false) String id) {
		ModuleContent entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = moduleContentService.get(id);
		}
		if (entity == null){
			entity = new ModuleContent();
		}
		return entity;
	}
	
	/**
	 * 模块内容列表页面
	 */
	@RequiresPermissions("meiguotong:module:moduleContent:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/meiguotong/module/moduleContentList";
	}
	
		/**
	 * 模块内容列表数据
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:module:moduleContent:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(ModuleContent moduleContent, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ModuleContent> page = moduleContentService.findPage(new Page<ModuleContent>(request, response), moduleContent); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑模块内容表单页面
	 */
	@RequiresPermissions(value={"meiguotong:module:moduleContent:view","meiguotong:module:moduleContent:add","meiguotong:module:moduleContent:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(ModuleContent moduleContent, Model model) {
		model.addAttribute("moduleContent", moduleContent);
		if(StringUtils.isBlank(moduleContent.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/meiguotong/module/moduleContentForm";
	}

	/**
	 * 保存模块内容
	 */
	@RequiresPermissions(value={"meiguotong:module:moduleContent:add","meiguotong:module:moduleContent:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(ModuleContent moduleContent, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, moduleContent)){
			return form(moduleContent, model);
		}
		//新增或编辑表单保存
		moduleContentService.save(moduleContent);//保存
		addMessage(redirectAttributes, "保存模块内容成功");
		return "redirect:"+Global.getAdminPath()+"/meiguotong/module/moduleContent/?repage";
	}
	
	/**
	 * 删除模块内容
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:module:moduleContent:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(ModuleContent moduleContent, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		moduleContentService.delete(moduleContent);
		j.setMsg("删除模块内容成功");
		return j;
	}
	
	/**
	 * 批量删除模块内容
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:module:moduleContent:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			moduleContentService.delete(moduleContentService.get(id));
		}
		j.setMsg("删除模块内容成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:module:moduleContent:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(ModuleContent moduleContent, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "模块内容"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<ModuleContent> page = moduleContentService.findPage(new Page<ModuleContent>(request, response, -1), moduleContent);
    		new ExportExcel("模块内容", ModuleContent.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出模块内容记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("meiguotong:module:moduleContent:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<ModuleContent> list = ei.getDataList(ModuleContent.class);
			for (ModuleContent moduleContent : list){
				try{
					moduleContentService.save(moduleContent);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条模块内容记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条模块内容记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模块内容失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/module/moduleContent/?repage";
    }
	
	/**
	 * 下载导入模块内容数据模板
	 */
	@RequiresPermissions("meiguotong:module:moduleContent:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "模块内容数据导入模板.xlsx";
    		List<ModuleContent> list = Lists.newArrayList(); 
    		new ExportExcel("模块内容数据", ModuleContent.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/module/moduleContent/?repage";
    }

}