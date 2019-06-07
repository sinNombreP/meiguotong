/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.web.income;

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
import com.jeeplus.modules.sys.entity.income.Income;
import com.jeeplus.modules.sys.service.income.IncomeService;

/**
 * 平台流水管理Controller
 * @author dong
 * @version 2019-04-09
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/income/income")
public class IncomeController extends BaseController {

	@Autowired
	private IncomeService incomeService;
	
	@ModelAttribute
	public Income get(@RequestParam(required=false) String id) {
		Income entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = incomeService.get(id);
		}
		if (entity == null){
			entity = new Income();
		}
		return entity;
	}
	
	/**
	 * 平台流水管理列表页面
	 */
	@RequiresPermissions("sys:income:income:list")
	@RequestMapping(value = {"list", ""})
	public String list(Model model) {
		model.addAttribute("income", incomeService.findIncomeSale());
		return "modules/sys/income/incomeList";
	}
	
		/**
	 * 平台流水管理列表数据
	 */
	@ResponseBody
	@RequiresPermissions("sys:income:income:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Income income, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Income> page = incomeService.findIncomeListByMoreParameter(new Page<Income>(request, response), income); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑平台流水管理表单页面
	 */
	//@RequiresPermissions(value={"sys:income:income:view","sys:income:income:add","sys:income:income:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Income income, Model model) {
		model.addAttribute("income", income);
		if(StringUtils.isBlank(income.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/sys/income/incomeForm";
	}

	/**
	 * 保存平台流水管理
	 */
	@RequiresPermissions(value={"sys:income:income:add","sys:income:income:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(Income income, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, income)){
			return form(income, model);
		}
		//新增或编辑表单保存
		incomeService.save(income);//保存
		addMessage(redirectAttributes, "保存平台流水管理成功");
		return "redirect:"+Global.getAdminPath()+"/sys/income/income/?repage";
	}
	
	/**
	 * 删除平台流水管理
	 */
	@ResponseBody
	@RequiresPermissions("sys:income:income:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(Income income, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		incomeService.delete(income);
		j.setMsg("删除平台流水管理成功");
		return j;
	}
	
	/**
	 * 批量删除平台流水管理
	 */
	@ResponseBody
	@RequiresPermissions("sys:income:income:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			incomeService.delete(incomeService.get(id));
		}
		j.setMsg("删除平台流水管理成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("sys:income:income:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(Income income, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "平台流水管理"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Income> page = incomeService.findPage(new Page<Income>(request, response, -1), income);
    		new ExportExcel("平台流水管理", Income.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出平台流水管理记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("sys:income:income:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Income> list = ei.getDataList(Income.class);
			for (Income income : list){
				try{
					incomeService.save(income);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条平台流水管理记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条平台流水管理记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入平台流水管理失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/income/income/?repage";
    }
	
	/**
	 * 下载导入平台流水管理数据模板
	 */
	@RequiresPermissions("sys:income:income:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "平台流水管理数据导入模板.xlsx";
    		List<Income> list = Lists.newArrayList(); 
    		new ExportExcel("平台流水管理数据", Income.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/income/income/?repage";
    }

}