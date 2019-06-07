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
import com.jeeplus.modules.meiguotong.entity.liner.LinerCompany;
import com.jeeplus.modules.meiguotong.service.comlanguage.ComLanguageService;
import com.jeeplus.modules.meiguotong.service.liner.LinerCompanyService;

/**
 * 游轮公司表Controller
 * @author dong
 * @version 2018-10-29
 */
@Controller
@RequestMapping(value = "${adminPath}/meiguotong/liner/linerCompany")
public class LinerCompanyController extends BaseController {

	@Autowired
	private LinerCompanyService linerCompanyService;
	@Autowired
	private ComLanguageService comLanguageService;
	
	@ModelAttribute
	public LinerCompany get(@RequestParam(required=false) String id) {
		LinerCompany entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = linerCompanyService.get(id);
		}
		if (entity == null){
			entity = new LinerCompany();
		}
		return entity;
	}
	
	/**
	 * 游轮公司表列表页面
	 */
	@RequiresPermissions("meiguotong:liner:linerCompany:list")
	@RequestMapping(value = {"list", ""})
	public String list(Model model) {
		//获取所有语言
		List<ComLanguage> list = comLanguageService.getLanguage();
		model.addAttribute("languageList", list);
		return "modules/meiguotong/liner/linerCompanyList";
	}
	
		/**
	 * 游轮公司表列表数据
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:liner:linerCompany:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(LinerCompany linerCompany, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<LinerCompany> page = linerCompanyService.findPage(new Page<LinerCompany>(request, response), linerCompany); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑游轮公司表表单页面
	 */
	@RequiresPermissions(value={"meiguotong:liner:linerCompany:view","meiguotong:liner:linerCompany:add","meiguotong:liner:linerCompany:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(LinerCompany linerCompany, Model model) {
		//获取所有语言
		List<ComLanguage> list = comLanguageService.getLanguage();
		model.addAttribute("languageList", list);
		model.addAttribute("linerCompany", linerCompany);
		return "modules/meiguotong/liner/linerCompanyForm";
	}

	/**
	 * 保存游轮公司表
	 */
	@ResponseBody
	@RequiresPermissions(value={"meiguotong:liner:linerCompany:add","meiguotong:liner:linerCompany:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(LinerCompany linerCompany, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, linerCompany)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		linerCompanyService.save(linerCompany);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存游轮公司表成功");
		return j;
	}
	
	/**
	 * 删除游轮公司表
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:liner:linerCompany:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(LinerCompany linerCompany, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		//判断公司下是否有游轮
		Integer count = linerCompanyService.getCount(linerCompany);
		if(count>0){
			j.setSuccess(false);
			j.setMsg("该游轮公司下有游轮，不能删除该公司");
		}else{
			linerCompanyService.delete(linerCompany);
			j.setMsg("删除游轮公司成功");
		}
		return j;
	}
	
	/**
	 * 批量删除游轮公司表
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:liner:linerCompany:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			linerCompanyService.delete(linerCompanyService.get(id));
		}
		j.setMsg("删除游轮公司表成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:liner:linerCompany:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(LinerCompany linerCompany, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "游轮公司表"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<LinerCompany> page = linerCompanyService.findPage(new Page<LinerCompany>(request, response, -1), linerCompany);
    		new ExportExcel("游轮公司表", LinerCompany.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出游轮公司表记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("meiguotong:liner:linerCompany:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<LinerCompany> list = ei.getDataList(LinerCompany.class);
			for (LinerCompany linerCompany : list){
				try{
					linerCompanyService.save(linerCompany);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条游轮公司表记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条游轮公司表记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入游轮公司表失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/liner/linerCompany/?repage";
    }
	
	/**
	 * 下载导入游轮公司表数据模板
	 */
	@RequiresPermissions("meiguotong:liner:linerCompany:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "游轮公司表数据导入模板.xlsx";
    		List<LinerCompany> list = Lists.newArrayList(); 
    		new ExportExcel("游轮公司表数据", LinerCompany.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/liner/linerCompany/?repage";
    }

}