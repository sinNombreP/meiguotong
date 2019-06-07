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
import com.jeeplus.modules.meiguotong.entity.module.ModuleHtmlName;
import com.jeeplus.modules.meiguotong.service.module.ModuleHtmlNameService;

/**
 * 模块关联表Controller
 * @author psz
 * @version 2018-12-03
 */
@Controller
@RequestMapping(value = "${adminPath}/meiguotong/module/moduleHtmlName")
public class ModuleHtmlNameController extends BaseController {

	@Autowired
	private ModuleHtmlNameService moduleHtmlNameService;
	
	@ModelAttribute
	public ModuleHtmlName get(@RequestParam(required=false) String id) {
		ModuleHtmlName entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = moduleHtmlNameService.get(id);
		}
		if (entity == null){
			entity = new ModuleHtmlName();
		}
		return entity;
	}
	
	/**
	 * 模块关联表列表页面
	 */
	@RequiresPermissions("meiguotong:module:moduleHtmlName:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/meiguotong/module/moduleHtmlNameList";
	}
	
		/**
	 * 模块关联表列表数据
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:module:moduleHtmlName:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(ModuleHtmlName moduleHtmlName, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ModuleHtmlName> page = moduleHtmlNameService.findPage(new Page<ModuleHtmlName>(request, response), moduleHtmlName); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑模块关联表表单页面
	 */
	@RequiresPermissions(value={"meiguotong:module:moduleHtmlName:view","meiguotong:module:moduleHtmlName:add","meiguotong:module:moduleHtmlName:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(ModuleHtmlName moduleHtmlName, Model model) {
		model.addAttribute("moduleHtmlName", moduleHtmlName);
		if(StringUtils.isBlank(moduleHtmlName.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/meiguotong/module/moduleHtmlNameForm";
	}

	/**
	 * 保存模块关联表
	 */
	@RequiresPermissions(value={"meiguotong:module:moduleHtmlName:add","meiguotong:module:moduleHtmlName:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(ModuleHtmlName moduleHtmlName, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, moduleHtmlName)){
			return form(moduleHtmlName, model);
		}
		//新增或编辑表单保存
		moduleHtmlNameService.save(moduleHtmlName);//保存
		addMessage(redirectAttributes, "保存模块关联表成功");
		return "redirect:"+Global.getAdminPath()+"/meiguotong/module/moduleHtmlName/?repage";
	}
	
	/**
	 * 删除模块关联表
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:module:moduleHtmlName:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(ModuleHtmlName moduleHtmlName, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		moduleHtmlNameService.delete(moduleHtmlName);
		j.setMsg("删除模块关联表成功");
		return j;
	}
	
	/**
	 * 批量删除模块关联表
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:module:moduleHtmlName:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			moduleHtmlNameService.delete(moduleHtmlNameService.get(id));
		}
		j.setMsg("删除模块关联表成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:module:moduleHtmlName:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(ModuleHtmlName moduleHtmlName, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "模块关联表"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<ModuleHtmlName> page = moduleHtmlNameService.findPage(new Page<ModuleHtmlName>(request, response, -1), moduleHtmlName);
    		new ExportExcel("模块关联表", ModuleHtmlName.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出模块关联表记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("meiguotong:module:moduleHtmlName:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<ModuleHtmlName> list = ei.getDataList(ModuleHtmlName.class);
			for (ModuleHtmlName moduleHtmlName : list){
				try{
					moduleHtmlNameService.save(moduleHtmlName);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条模块关联表记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条模块关联表记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模块关联表失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/module/moduleHtmlName/?repage";
    }
	
	/**
	 * 下载导入模块关联表数据模板
	 */
	@RequiresPermissions("meiguotong:module:moduleHtmlName:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "模块关联表数据导入模板.xlsx";
    		List<ModuleHtmlName> list = Lists.newArrayList(); 
    		new ExportExcel("模块关联表数据", ModuleHtmlName.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/module/moduleHtmlName/?repage";
    }

}