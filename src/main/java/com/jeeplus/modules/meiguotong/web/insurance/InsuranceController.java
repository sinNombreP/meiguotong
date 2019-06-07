/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.web.insurance;

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
import com.jeeplus.modules.meiguotong.entity.insurance.Insurance;
import com.jeeplus.modules.meiguotong.service.comlanguage.ComLanguageService;
import com.jeeplus.modules.meiguotong.service.insurance.InsuranceService;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.utils.UserUtils;

/**
 * 保险表Controller
 * @author cdq
 * @version 2018-08-15
 */
@Controller
@RequestMapping(value = "${adminPath}/meiguotong/insurance/insurance")
public class InsuranceController extends BaseController {

	
	@Autowired
	private InsuranceService insuranceService;
	@Autowired
	private ComLanguageService comLanguageService;
	
	
	@ModelAttribute
	public Insurance get(@RequestParam(required=false) String id) {
		Insurance entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = insuranceService.get(id);
		}
		if (entity == null){
			entity = new Insurance();
		}
		return entity;
	}
	
	/**
	 * 保险表列表页面
	 */
	@RequiresPermissions("meiguotong:insurance:insurance:list")
	@RequestMapping(value = {"list", ""})
	public String list(Insurance insurance, Model model) {
		//语言
		List<ComLanguage> comLanguageList = comLanguageService.findList(new ComLanguage());
		model.addAttribute("comLanguageList",comLanguageList);
		model.addAttribute("userType",UserUtils.getUser().getUserType());
		return "modules/meiguotong/insurance/insuranceList";
	}
	
		/**
	 * 保险表列表数据
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:insurance:insurance:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Insurance insurance, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		if(user.getUserType()==2) {
			insurance.setAgentid(user.getAgentid());
		}
		Page<Insurance> page = insuranceService.findPage(new Page<Insurance>(request, response), insurance); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑保险表表单页面
	 */
//	@RequiresPermissions(value={"meiguotong:insurance:insurance:view","meiguotong:insurance:insurance:add","meiguotong:insurance:insurance:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Insurance insurance, Model model) {
		model.addAttribute("insurance", insurance);
		if(StringUtils.isBlank(insurance.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		//语言
		List<ComLanguage> comLanguageList = comLanguageService.findList(new ComLanguage());
		model.addAttribute("comLanguageList",comLanguageList);
		return "modules/meiguotong/insurance/insuranceForm";
	}

	/**
	 * 保存保险表
	 */
//	@RequiresPermissions(value={"meiguotong:insurance:insurance:add","meiguotong:insurance:insurance:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(Insurance insurance, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, insurance)){
			return form(insurance, model);
		}
		//新增或编辑表单保存
		insuranceService.save(insurance);//保存
		addMessage(redirectAttributes, "保存保险表成功");
		return "redirect:"+Global.getAdminPath()+"/meiguotong/insurance/insurance/?repage";
	}
	
	/**
	 * 删除保险表
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:insurance:insurance:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(Insurance insurance, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		insuranceService.delete(insurance);
		j.setMsg("删除保险表成功");
		return j;
	}
	
	@ResponseBody
	@RequiresPermissions("meiguotong:insurance:insurance:updateStatus")
	@RequestMapping(value = "updateStatus")
	public AjaxJson updateStatus(Insurance insurance, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		insuranceService.save(insurance);
		j.setMsg("修改状态成功");
		return j;
	}
	
	/**
	 * 批量删除保险表
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:insurance:insurance:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			insuranceService.delete(insuranceService.get(id));
		}
		j.setMsg("删除保险表成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:insurance:insurance:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(Insurance insurance, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "保险表"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Insurance> page = insuranceService.findPage(new Page<Insurance>(request, response, -1), insurance);
    		new ExportExcel("保险表", Insurance.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出保险表记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("meiguotong:insurance:insurance:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Insurance> list = ei.getDataList(Insurance.class);
			for (Insurance insurance : list){
				try{
					insuranceService.save(insurance);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条保险表记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条保险表记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入保险表失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/insurance/insurance/?repage";
    }
	
	/**
	 * 下载导入保险表数据模板
	 */
	@RequiresPermissions("meiguotong:insurance:insurance:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "保险表数据导入模板.xlsx";
    		List<Insurance> list = Lists.newArrayList(); 
    		new ExportExcel("保险表数据", Insurance.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/insurance/insurance/?repage";
    }

}