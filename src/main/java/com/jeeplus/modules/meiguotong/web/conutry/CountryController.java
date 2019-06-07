/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.web.conutry;

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
import com.jeeplus.modules.meiguotong.entity.conutry.Country;
import com.jeeplus.modules.meiguotong.service.comlanguage.ComLanguageService;
import com.jeeplus.modules.meiguotong.service.conutry.CountryService;

/**
 * 国家表Controller
 * @author cdq
 * @version 2018-08-09
 */
@Controller
@RequestMapping(value = "${adminPath}/meiguotong/conutry/country")
public class CountryController extends BaseController {

	@Autowired
	private CountryService countryService;
	@Autowired
	private ComLanguageService comLanguageService;
	@ModelAttribute
	public Country get(@RequestParam(required=false) String id) {
		Country entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = countryService.get(id);
		}
		if (entity == null){
			entity = new Country();
		}
		return entity;
	}
	
	/**
	 * 国家表列表页面
	 */
	@RequiresPermissions("meiguotong:conutry:country:list")
	@RequestMapping(value = {"list", ""})
	public String list(Model model) {
		//语言
		List<ComLanguage> comLanguageList = comLanguageService.findList(new ComLanguage());
		model.addAttribute("comLanguageList",comLanguageList);
		return "modules/meiguotong/conutry/countryList";
	}
	
		/**
	 * 国家表列表数据
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:conutry:country:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Country country, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Country> page = countryService.findPage(new Page<Country>(request, response), country); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑国家表表单页面
	 */
	@RequiresPermissions(value={"meiguotong:conutry:country:view","meiguotong:conutry:country:add","meiguotong:conutry:country:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Country country, Model model) {
		List<ComLanguage> comLanguageList =comLanguageService.findLanguage();
		model.addAttribute("comLanguageList", comLanguageList);
		model.addAttribute("country", country);
		if(StringUtils.isBlank(country.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/meiguotong/conutry/countryForm";
	}
	/**
	 * 查看，增加，编辑国家表表单页面
	 */
	@RequiresPermissions(value={"meiguotong:conutry:country:view","meiguotong:conutry:country:add","meiguotong:conutry:country:edit"},logical=Logical.OR)
	@RequestMapping(value = "AddForm")
	public String AddForm(Country country, Model model) {
		List<ComLanguage> comLanguageList =comLanguageService.findLanguage();
		model.addAttribute("comLanguageList", comLanguageList);
		model.addAttribute("country", country);
		if(StringUtils.isBlank(country.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/meiguotong/conutry/countryAddForm";
	}

	/**
	 * 保存国家表
	 */
	@ResponseBody
	@RequiresPermissions(value={"meiguotong:conutry:country:add","meiguotong:conutry:country:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(Country country, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, country)){
			j.setSuccess(false);
			j.setMsg("非法参数");
			return j;
		}
		//新增或者编辑表单保存
		countryService.save(country);//保存
		j.setSuccess(true);
		j.setMsg("保存成功");
		return j;	
	}
	 /**
	   *修改状态
	   */
		@ResponseBody
		@RequestMapping(value = "status")
		public AjaxJson status(Country country, RedirectAttributes redirectAttributes) {
			AjaxJson j = new AjaxJson();
			if(country.getStatus()==1){
				country.setStatus(2);
			}else{
				country.setStatus(1);
			}
			countryService.status(country);
			
			j.setMsg("修改状态成功");
			return j;
		}
	/**
	 * 删除国家表
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:conutry:country:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(Country country, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		countryService.delete(country);
		j.setMsg("删除国家表成功");
		return j;
	}
	
	/**
	 * 批量删除国家表
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:conutry:country:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			countryService.delete(countryService.get(id));
		}
		j.setMsg("删除国家表成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:conutry:country:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(Country country, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "国家表"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Country> page = countryService.findPage(new Page<Country>(request, response, -1), country);
    		new ExportExcel("国家表", Country.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出国家表记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据
	 */
	@RequiresPermissions("meiguotong:conutry:country:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Country> list = ei.getDataList(Country.class);
			for (Country country : list){
				try{
					countryService.save(country);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条国家表记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条国家表记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入国家表失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/conutry/country/?repage";
    }
	
	/**
	 * 下载导入国家表数据模板
	 */
	@RequiresPermissions("meiguotong:conutry:country:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "国家表数据导入模板.xlsx";
    		List<Country> list = Lists.newArrayList(); 
    		new ExportExcel("国家表数据", Country.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/conutry/country/?repage";
    }

}