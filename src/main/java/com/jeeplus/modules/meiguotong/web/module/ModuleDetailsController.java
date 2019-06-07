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
import com.jeeplus.modules.meiguotong.entity.module.ModuleDetails;
import com.jeeplus.modules.meiguotong.service.module.ModuleDetailsService;

/**
 * 模块详情Controller
 * @author psz
 * @version 2018-12-03
 */
@Controller
@RequestMapping(value = "${adminPath}/meiguotong/module/moduleDetails")
public class ModuleDetailsController extends BaseController {

	@Autowired
	private ModuleDetailsService moduleDetailsService;
	
	@ModelAttribute
	public ModuleDetails get(@RequestParam(required=false) String id) {
		ModuleDetails entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = moduleDetailsService.get(id);
		}
		if (entity == null){
			entity = new ModuleDetails();
		}
		return entity;
	}
	
	/**
	 * 模块详情列表页面
	 */
	@RequiresPermissions("meiguotong:module:moduleDetails:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/meiguotong/module/moduleDetailsList";
	}
	
		/**
	 * 模块详情列表数据
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:module:moduleDetails:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(ModuleDetails moduleDetails, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ModuleDetails> page = moduleDetailsService.findPage(new Page<ModuleDetails>(request, response), moduleDetails); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑模块详情表单页面
	 */
	@RequiresPermissions(value={"meiguotong:module:moduleDetails:view","meiguotong:module:moduleDetails:add","meiguotong:module:moduleDetails:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(ModuleDetails moduleDetails, Model model) {
		model.addAttribute("moduleDetails", moduleDetails);
		if(StringUtils.isBlank(moduleDetails.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/meiguotong/module/moduleDetailsForm";
	}

	/**
	 * 保存模块详情
	 */
	@RequiresPermissions(value={"meiguotong:module:moduleDetails:add","meiguotong:module:moduleDetails:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(ModuleDetails moduleDetails, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, moduleDetails)){
			return form(moduleDetails, model);
		}
		//新增或编辑表单保存
		moduleDetailsService.save(moduleDetails);//保存
		addMessage(redirectAttributes, "保存模块详情成功");
		return "redirect:"+Global.getAdminPath()+"/meiguotong/module/moduleDetails/?repage";
	}
	
	/**
	 * 删除模块详情
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:module:moduleDetails:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(ModuleDetails moduleDetails, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		moduleDetailsService.delete(moduleDetails);
		j.setMsg("删除模块详情成功");
		return j;
	}
	
	/**
	 * 批量删除模块详情
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:module:moduleDetails:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			moduleDetailsService.delete(moduleDetailsService.get(id));
		}
		j.setMsg("删除模块详情成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:module:moduleDetails:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(ModuleDetails moduleDetails, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "模块详情"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<ModuleDetails> page = moduleDetailsService.findPage(new Page<ModuleDetails>(request, response, -1), moduleDetails);
    		new ExportExcel("模块详情", ModuleDetails.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出模块详情记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("meiguotong:module:moduleDetails:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<ModuleDetails> list = ei.getDataList(ModuleDetails.class);
			for (ModuleDetails moduleDetails : list){
				try{
					moduleDetailsService.save(moduleDetails);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条模块详情记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条模块详情记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模块详情失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/module/moduleDetails/?repage";
    }
	
	/**
	 * 下载导入模块详情数据模板
	 */
	@RequiresPermissions("meiguotong:module:moduleDetails:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "模块详情数据导入模板.xlsx";
    		List<ModuleDetails> list = Lists.newArrayList(); 
    		new ExportExcel("模块详情数据", ModuleDetails.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/module/moduleDetails/?repage";
    }

}