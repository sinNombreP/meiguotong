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
import com.jeeplus.modules.meiguotong.entity.insurance.InsuranceRelationMod;
import com.jeeplus.modules.meiguotong.mapper.insurance.InsuranceMapper;
import com.jeeplus.modules.meiguotong.service.comlanguage.ComLanguageService;
import com.jeeplus.modules.meiguotong.service.insurance.InsuranceRelationModService;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.utils.UserUtils;

/**
 * 保险项目关联Controller
 * @author PSZ
 * @version 2018-08-20
 */
@Controller
@RequestMapping(value = "${adminPath}/meiguotong/insurance/insuranceRelationMod")
public class InsuranceRelationModController extends BaseController {

	@Autowired
	private InsuranceRelationModService insuranceRelationModService;
	@Autowired
	private ComLanguageService comLanguageService;
	@Autowired
	private InsuranceMapper insuranceMapper;
	
	@ModelAttribute
	public InsuranceRelationMod get(@RequestParam(required=false) String id) {
		InsuranceRelationMod entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = insuranceRelationModService.get(id);
		}
		if (entity == null){
			entity = new InsuranceRelationMod();
		}
		return entity;
	}
	
	/**
	 * 保险项目关联列表页面
	 */
	@RequiresPermissions("meiguotong:insurance:insuranceRelationMod:list")
	@RequestMapping(value = {"list", ""})
	public String list(InsuranceRelationMod insuranceRelationMod, Model model) {
		//语言
		List<ComLanguage> comLanguageList = comLanguageService.findList(new ComLanguage());
		model.addAttribute("comLanguageList",comLanguageList);
		model.addAttribute("userType",UserUtils.getUser().getUserType());
		return "modules/meiguotong/insurance/insuranceRelationModList";
	}
	
		/**
	 * 保险项目关联列表数据
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:insurance:insuranceRelationMod:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(InsuranceRelationMod insuranceRelationMod, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<InsuranceRelationMod> page = insuranceRelationModService.findPage(new Page<InsuranceRelationMod>(request, response), insuranceRelationMod); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑保险项目关联表单页面
	 */
//	@RequiresPermissions(value={"meiguotong:insurance:insuranceRelationMod:view","meiguotong:insurance:insuranceRelationMod:add","meiguotong:insurance:insuranceRelationMod:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(InsuranceRelationMod insuranceRelationMod, Model model) {
    	switch (insuranceRelationMod.getType()) {
		case 1:insuranceRelationMod.setName("包车租车"); break;
		case 2:insuranceRelationMod.setName("短程接送"); break;
		case 3:insuranceRelationMod.setName("接送机"); break;
		case 4:insuranceRelationMod.setName("常规路线"); break;
		case 5:insuranceRelationMod.setName("当地参团"); break;
		case 6:insuranceRelationMod.setName("游轮"); break;
		case 7:insuranceRelationMod.setName("景点门票"); break;
		case 8:insuranceRelationMod.setName("当地玩家"); break;
		case 9:insuranceRelationMod.setName("旅游定制"); break;
		}
		model.addAttribute("insuranceRelationMod", insuranceRelationMod);
		if(insuranceRelationMod.getId()!=null){
			//获取可选择的保险
			insuranceRelationMod.preFind();
			List<Insurance> list = insuranceMapper.getInsuranceRelationMod(insuranceRelationMod);
			model.addAttribute("insuranceList", list);
		}
		return "modules/meiguotong/insurance/insuranceRelationModForm";
	}

	/**
	 * 保存保险项目关联
	 */
	@ResponseBody
//	@RequiresPermissions(value={"meiguotong:insurance:insuranceRelationMod:add","meiguotong:insurance:insuranceRelationMod:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(InsuranceRelationMod insuranceRelationMod, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, insuranceRelationMod)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		insuranceRelationModService.save(insuranceRelationMod);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存保险项目关联成功");
		return j;
	}
	
	
	/**
	 * 删除保险项目关联
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:insurance:insuranceRelationMod:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(InsuranceRelationMod insuranceRelationMod, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		insuranceRelationModService.delete(insuranceRelationMod);
		j.setMsg("删除保险项目关联成功");
		return j;
	}
	
	/**
	 * 批量删除保险项目关联
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:insurance:insuranceRelationMod:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			insuranceRelationModService.delete(insuranceRelationModService.get(id));
		}
		j.setMsg("删除保险项目关联成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:insurance:insuranceRelationMod:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(InsuranceRelationMod insuranceRelationMod, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "保险项目关联"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<InsuranceRelationMod> page = insuranceRelationModService.findPage(new Page<InsuranceRelationMod>(request, response, -1), insuranceRelationMod);
    		new ExportExcel("保险项目关联", InsuranceRelationMod.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出保险项目关联记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("meiguotong:insurance:insuranceRelationMod:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<InsuranceRelationMod> list = ei.getDataList(InsuranceRelationMod.class);
			for (InsuranceRelationMod insuranceRelationMod : list){
				try{
					insuranceRelationModService.save(insuranceRelationMod);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条保险项目关联记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条保险项目关联记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入保险项目关联失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/insurance/insuranceRelationMod/?repage";
    }
	
	/**
	 * 下载导入保险项目关联数据模板
	 */
	@RequiresPermissions("meiguotong:insurance:insuranceRelationMod:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "保险项目关联数据导入模板.xlsx";
    		List<InsuranceRelationMod> list = Lists.newArrayList(); 
    		new ExportExcel("保险项目关联数据", InsuranceRelationMod.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/insurance/insuranceRelationMod/?repage";
    }

}