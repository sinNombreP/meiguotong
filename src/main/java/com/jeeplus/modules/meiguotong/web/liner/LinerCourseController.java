/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.web.liner;

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
import com.jeeplus.modules.meiguotong.entity.liner.LinerCourse;
import com.jeeplus.modules.meiguotong.service.comlanguage.ComLanguageService;
import com.jeeplus.modules.meiguotong.service.liner.LinerCourseService;

/**
 * 邮轮航区设置Controller
 * @author dong
 * @version 2018-10-29
 */
@Controller
@RequestMapping(value = "${adminPath}/meiguotong/liner/linerCourse")
public class LinerCourseController extends BaseController {

	@Autowired
	private LinerCourseService linerCourseService;
	@Autowired
	private ComLanguageService comLanguageService;
	
	@ModelAttribute
	public LinerCourse get(@RequestParam(required=false) String id) {
		LinerCourse entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = linerCourseService.get(id);
		}
		if (entity == null){
			entity = new LinerCourse();
		}
		return entity;
	}
	
	/**
	 * 邮轮航区设置列表页面
	 */
	@RequiresPermissions("meiguotong:liner:linerCourse:list")
	@RequestMapping(value = {"list", ""})
	public String list(Model model) {
		//获取所有语言
		List<ComLanguage> list = comLanguageService.getLanguage();
		model.addAttribute("languageList", list);
		return "modules/meiguotong/liner/linerCourseList";
	}
	
		/**
	 * 邮轮航区设置列表数据
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:liner:linerCourse:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(LinerCourse linerCourse, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<LinerCourse> page = linerCourseService.findPage(new Page<LinerCourse>(request, response), linerCourse); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑邮轮航区设置表单页面
	 */
	@RequiresPermissions(value={"meiguotong:liner:linerCourse:view","meiguotong:liner:linerCourse:add","meiguotong:liner:linerCourse:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(LinerCourse linerCourse, Model model) {
		//获取所有语言
		List<ComLanguage> list = comLanguageService.getLanguage();
		model.addAttribute("languageList", list);
		model.addAttribute("linerCourse", linerCourse);
		return "modules/meiguotong/liner/linerCourseForm";
	}

	/**
	 * 保存邮轮航区设置
	 */
	@ResponseBody
	@RequiresPermissions(value={"meiguotong:liner:linerCourse:add","meiguotong:liner:linerCourse:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(LinerCourse linerCourse, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, linerCourse)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		linerCourseService.save(linerCourse);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存邮轮航区设置成功");
		return j;
	}
	
	/**
	 * 删除邮轮航区设置
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:liner:linerCourse:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(LinerCourse linerCourse, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		linerCourseService.delete(linerCourse);
		j.setMsg("删除邮轮航区设置成功");
		return j;
	}
	
	/**
	 * 批量删除邮轮航区设置
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:liner:linerCourse:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			linerCourseService.delete(linerCourseService.get(id));
		}
		j.setMsg("删除邮轮航区设置成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:liner:linerCourse:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(LinerCourse linerCourse, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "邮轮航区设置"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<LinerCourse> page = linerCourseService.findPage(new Page<LinerCourse>(request, response, -1), linerCourse);
    		new ExportExcel("邮轮航区设置", LinerCourse.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出邮轮航区设置记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("meiguotong:liner:linerCourse:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<LinerCourse> list = ei.getDataList(LinerCourse.class);
			for (LinerCourse linerCourse : list){
				try{
					linerCourseService.save(linerCourse);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条邮轮航区设置记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条邮轮航区设置记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入邮轮航区设置失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/liner/linerCourse/?repage";
    }
	
	/**
	 * 下载导入邮轮航区设置数据模板
	 */
	@RequiresPermissions("meiguotong:liner:linerCourse:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "邮轮航区设置数据导入模板.xlsx";
    		List<LinerCourse> list = Lists.newArrayList(); 
    		new ExportExcel("邮轮航区设置数据", LinerCourse.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/liner/linerCourse/?repage";
    }

}