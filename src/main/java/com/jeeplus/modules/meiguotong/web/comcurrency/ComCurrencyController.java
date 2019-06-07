/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.web.comcurrency;

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
import com.jeeplus.modules.meiguotong.entity.comcurrency.ComCurrency;
import com.jeeplus.modules.meiguotong.entity.comlanguage.ComLanguage;
import com.jeeplus.modules.meiguotong.service.comcurrency.ComCurrencyService;

/**
 * 汇率表Controller
 * @author cdq
 * @version 2018-08-01
 */
@Controller
@RequestMapping(value = "${adminPath}/meiguotong/comcurrency/comCurrency")
public class ComCurrencyController extends BaseController {

	@Autowired
	private ComCurrencyService comCurrencyService;
	
	@ModelAttribute
	public ComCurrency get(@RequestParam(required=false) String id) {
		ComCurrency entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = comCurrencyService.get(id);
		}
		if (entity == null){
			entity = new ComCurrency();
		}
		return entity;
	}
	
	/**
	 * 汇率表列表页面
	 */
	@RequiresPermissions("meiguotong:comcurrency:comCurrency:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/meiguotong/comcurrency/comCurrencyList";
	}
	
		/**
	 * 汇率表列表数据
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:comcurrency:comCurrency:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(ComCurrency comCurrency, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ComCurrency> page = comCurrencyService.findPage(new Page<ComCurrency>(request, response), comCurrency); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑汇率表表单页面
	 */
	@RequiresPermissions(value={"meiguotong:comcurrency:comCurrency:view","meiguotong:comcurrency:comCurrency:add","meiguotong:comcurrency:comCurrency:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(ComCurrency comCurrency, Model model) {
		model.addAttribute("comCurrency", comCurrency);
		if(StringUtils.isBlank(comCurrency.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/meiguotong/comcurrency/comCurrencyForm";
	}
	  /**
	   *更改状态
	   */
		@ResponseBody
		@RequestMapping(value = "status")
		public AjaxJson status(ComCurrency comCurrency, RedirectAttributes redirectAttributes) {
			AjaxJson j = new AjaxJson();
			if(comCurrency.getStatus()==2){
				comCurrency.setStatus(1);
			}else{
				comCurrency.setStatus(2);
			}
			comCurrencyService.status(comCurrency);
			j.setMsg("更改成功");
			return j;
		}
	/**
	 * 保存汇率表
	 */
	@RequiresPermissions(value={"meiguotong:comcurrency:comCurrency:add","meiguotong:comcurrency:comCurrency:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(ComCurrency comCurrency, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, comCurrency)){
			return form(comCurrency, model);
		}
		//新增或编辑表单保存
		comCurrencyService.save(comCurrency);//保存
		addMessage(redirectAttributes, "保存汇率表成功");
		return "redirect:"+Global.getAdminPath()+"/meiguotong/comcurrency/comCurrency/?repage";
	}
	
	/**
	 * 删除汇率表
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:comcurrency:comCurrency:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(ComCurrency comCurrency, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		comCurrencyService.delete(comCurrency);
		j.setMsg("删除汇率表成功");
		return j;
	}
	
	/**
	 * 批量删除汇率表
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:comcurrency:comCurrency:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			comCurrencyService.delete(comCurrencyService.get(id));
		}
		j.setMsg("删除汇率表成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:comcurrency:comCurrency:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(ComCurrency comCurrency, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "汇率表"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<ComCurrency> page = comCurrencyService.findPage(new Page<ComCurrency>(request, response, -1), comCurrency);
    		new ExportExcel("汇率表", ComCurrency.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出汇率表记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("meiguotong:comcurrency:comCurrency:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<ComCurrency> list = ei.getDataList(ComCurrency.class);
			for (ComCurrency comCurrency : list){
				try{
					comCurrencyService.save(comCurrency);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条汇率表记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条汇率表记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入汇率表失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/comcurrency/comCurrency/?repage";
    }
	
	/**
	 * 下载导入汇率表数据模板
	 */
	@RequiresPermissions("meiguotong:comcurrency:comCurrency:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "汇率表数据导入模板.xlsx";
    		List<ComCurrency> list = Lists.newArrayList(); 
    		new ExportExcel("汇率表数据", ComCurrency.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/comcurrency/comCurrency/?repage";
    }

}