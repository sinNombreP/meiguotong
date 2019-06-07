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
import com.jeeplus.modules.meiguotong.entity.module.ModuleName;
import com.jeeplus.modules.meiguotong.service.module.ModuleNameService;

/**
 * 模块名称Controller
 * @author psz
 * @version 2018-12-03
 */
@Controller
@RequestMapping(value = "${adminPath}/meiguotong/module/moduleName")
public class ModuleNameController extends BaseController {

	@Autowired
	private ModuleNameService moduleNameService;
	
	@ModelAttribute
	public ModuleName get(@RequestParam(required=false) String id) {
		ModuleName entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = moduleNameService.get(id);
		}
		if (entity == null){
			entity = new ModuleName();
		}
		return entity;
	}
	
	/**
	 * 模块名称列表页面
	 */
	@RequiresPermissions("meiguotong:module:moduleName:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/meiguotong/module/moduleNameList";
	}
	
		/**
	 * 模块名称列表数据
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:module:moduleName:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(ModuleName moduleName, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ModuleName> page = moduleNameService.findPage(new Page<ModuleName>(request, response), moduleName); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑模块名称表单页面
	 */
	@RequiresPermissions(value={"meiguotong:module:moduleName:view","meiguotong:module:moduleName:add","meiguotong:module:moduleName:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(ModuleName moduleName, Model model) {
		model.addAttribute("moduleName", moduleName);
		if(StringUtils.isBlank(moduleName.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/meiguotong/module/moduleNameForm";
	}

	/**
	 * 保存模块名称
	 */
	@RequiresPermissions(value={"meiguotong:module:moduleName:add","meiguotong:module:moduleName:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(ModuleName moduleName, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, moduleName)){
			return form(moduleName, model);
		}
		//新增或编辑表单保存
		moduleNameService.save(moduleName);//保存
		addMessage(redirectAttributes, "保存模块名称成功");
		return "redirect:"+Global.getAdminPath()+"/meiguotong/module/moduleName/?repage";
	}
	
	/**
	 * 删除模块名称
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:module:moduleName:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(ModuleName moduleName, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		moduleNameService.delete(moduleName);
		j.setMsg("删除模块名称成功");
		return j;
	}
	
	/**
	 * 批量删除模块名称
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:module:moduleName:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			moduleNameService.delete(moduleNameService.get(id));
		}
		j.setMsg("删除模块名称成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:module:moduleName:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(ModuleName moduleName, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "模块名称"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<ModuleName> page = moduleNameService.findPage(new Page<ModuleName>(request, response, -1), moduleName);
    		new ExportExcel("模块名称", ModuleName.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出模块名称记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("meiguotong:module:moduleName:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<ModuleName> list = ei.getDataList(ModuleName.class);
			for (ModuleName moduleName : list){
				try{
					moduleNameService.save(moduleName);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条模块名称记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条模块名称记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模块名称失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/module/moduleName/?repage";
    }
	
	/**
	 * 下载导入模块名称数据模板
	 */
	@RequiresPermissions("meiguotong:module:moduleName:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "模块名称数据导入模板.xlsx";
    		List<ModuleName> list = Lists.newArrayList(); 
    		new ExportExcel("模块名称数据", ModuleName.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/module/moduleName/?repage";
    }

}