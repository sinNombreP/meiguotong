/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.web.comlanguage;

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
import com.jeeplus.modules.meiguotong.entity.comcomment.ComComment;
import com.jeeplus.modules.meiguotong.entity.comlanguage.ComLanguage;
import com.jeeplus.modules.meiguotong.service.comlanguage.ComLanguageService;

/**
 * 语言表Controller
 * @author cdq
 * @version 2018-07-27
 */
@Controller
@RequestMapping(value = "${adminPath}/meiguotong/comlanguage/comLanguage")
public class ComLanguageController extends BaseController {

	@Autowired
	private ComLanguageService comLanguageService;
	
	@ModelAttribute
	public ComLanguage get(@RequestParam(required=false) String id) {
		ComLanguage entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = comLanguageService.get(id);
		}
		if (entity == null){
			entity = new ComLanguage();
		}
		return entity;
	}
	
	/**
	 * 语言表列表页面
	 */
	@RequiresPermissions("meiguotong:comlanguage:comLanguage:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/meiguotong/comlanguage/comLanguageList";
	}
	
		/**
	 * 语言表列表数据
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:comlanguage:comLanguage:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(ComLanguage comLanguage, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ComLanguage> page = comLanguageService.findPage(new Page<ComLanguage>(request, response), comLanguage); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑语言表表单页面
	 */
	@RequiresPermissions(value={"meiguotong:comlanguage:comLanguage:view","meiguotong:comlanguage:comLanguage:add","meiguotong:comlanguage:comLanguage:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(ComLanguage comLanguage, Model model) {
		model.addAttribute("comLanguage", comLanguage);
		if(StringUtils.isBlank(comLanguage.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/meiguotong/comlanguage/comLanguageForm";
	}

	/**
	 * 保存语言表
	 */
	@RequiresPermissions(value={"meiguotong:comlanguage:comLanguage:add","meiguotong:comlanguage:comLanguage:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(ComLanguage comLanguage, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, comLanguage)){
			return form(comLanguage, model);
		}
		//新增或编辑表单保存
		comLanguageService.save(comLanguage);//保存
		addMessage(redirectAttributes, "保存语言表成功");
		return "redirect:"+Global.getAdminPath()+"/meiguotong/comlanguage/comLanguage/?repage";
	}
	   /**
	   *更改状态
	   */
		@ResponseBody
		@RequestMapping(value = "status")
		public AjaxJson status(ComLanguage comLanguage, RedirectAttributes redirectAttributes) {
			AjaxJson j = new AjaxJson();
			if(comLanguage.getStatus()==2){
				comLanguage.setStatus(1);
			}else{
				comLanguage.setStatus(2);
			}
			comLanguageService.status(comLanguage);
			j.setMsg("更改成功");
			return j;
		}
	/**
	 * 删除语言表
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:comlanguage:comLanguage:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(ComLanguage comLanguage, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		comLanguageService.delete(comLanguage);
		j.setMsg("删除语言表成功");
		return j;
	}
	
	/**
	 * 批量删除语言表
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:comlanguage:comLanguage:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			comLanguageService.delete(comLanguageService.get(id));
		}
		j.setMsg("删除语言表成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:comlanguage:comLanguage:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(ComLanguage comLanguage, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "语言表"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<ComLanguage> page = comLanguageService.findPage(new Page<ComLanguage>(request, response, -1), comLanguage);
    		new ExportExcel("语言表", ComLanguage.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出语言表记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("meiguotong:comlanguage:comLanguage:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<ComLanguage> list = ei.getDataList(ComLanguage.class);
			for (ComLanguage comLanguage : list){
				try{
					comLanguageService.save(comLanguage);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条语言表记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条语言表记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入语言表失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/comlanguage/comLanguage/?repage";
    }
	
	/**
	 * 下载导入语言表数据模板
	 */
	@RequiresPermissions("meiguotong:comlanguage:comLanguage:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "语言表数据导入模板.xlsx";
    		List<ComLanguage> list = Lists.newArrayList(); 
    		new ExportExcel("语言表数据", ComLanguage.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/comlanguage/comLanguage/?repage";
    }

}