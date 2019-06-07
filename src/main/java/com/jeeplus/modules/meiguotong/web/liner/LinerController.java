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
import com.jeeplus.modules.meiguotong.entity.liner.Liner;
import com.jeeplus.modules.meiguotong.entity.liner.LinerCompany;
import com.jeeplus.modules.meiguotong.service.comlanguage.ComLanguageService;
import com.jeeplus.modules.meiguotong.service.liner.LinerService;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.utils.UserUtils;

/**
 * 邮轮管理表Controller
 * @author cdq
 * @version 2018-08-13
 */
@Controller
@RequestMapping(value = "${adminPath}/meiguotong/liner/liner")
public class LinerController extends BaseController {

	@Autowired
	private LinerService linerService;
	@Autowired
	private ComLanguageService comLanguageService;
	@ModelAttribute
	public Liner get(@RequestParam(required=false) String id) {
		Liner entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = linerService.get(id);
		}
		if (entity == null){
			entity = new Liner();
		}
		return entity;
	}
	
	/**
	 * 邮轮管理表列表页面
	 */
	@RequiresPermissions("meiguotong:liner:liner:list")
	@RequestMapping(value = {"list", ""})
	public String list(Liner liner,Model model) {
		User user = UserUtils.getUser();
		if (StringUtils.isNotBlank(user.getId())){
			liner.setLoginAgentid(user.getAgentid());
		}
		model.addAttribute("liner", liner);
		List<ComLanguage> languageList =comLanguageService.findLanguage();
		model.addAttribute("languageList", languageList);
		return "modules/meiguotong/liner/linerList";
	}
	
	/**
	 * 邮轮管理表列表数据
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:liner:liner:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Liner liner, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Liner> page = linerService.findPage(new Page<Liner>(request, response), liner); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑邮轮管理表表单页面
	 */
	@RequiresPermissions(value={"meiguotong:liner:liner:view","meiguotong:liner:liner:add","meiguotong:liner:liner:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Liner liner, Model model) {
		model.addAttribute("liner", liner);
		//获取所有语言
		List<ComLanguage> list = comLanguageService.getLanguage();
		model.addAttribute("languageList", list);
		if(liner.getId()!=null){
			LinerCompany linerCompany = new LinerCompany();
			linerCompany.setLanguageId(liner.getLanguageId());
			//根据语言获取未删除邮轮公司
			List<LinerCompany> companyList = linerService.getCompanyNoDel(linerCompany);
			model.addAttribute("companyList", companyList);
		}
		return "modules/meiguotong/liner/linerForm";
	}

	/**
	 * 保存邮轮管理表
	 */
	@ResponseBody
	@RequiresPermissions(value={"meiguotong:liner:liner:add","meiguotong:liner:liner:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(Liner liner, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, liner)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		linerService.save(liner);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存邮轮管理表成功");
		return j;
	}
	 /**
	   *修改状态
	   */
		@ResponseBody
		@RequestMapping(value = "status")
		public AjaxJson status(Liner liner, RedirectAttributes redirectAttributes) {
			AjaxJson j = new AjaxJson();
			if(liner.getStatus()==4){
				liner.setStatus(3);
			}else{
				liner.setStatus(4);
			}
			linerService.status(liner);
			j.setMsg("修改状态成功");
			return j;
		}
		
	/**
	 * 根据语言获取可用邮轮公司（未删除，未禁用）
	 */
	@ResponseBody
	@RequestMapping(value = "getCompany")
	public AjaxJson getCompany(LinerCompany linerCompany, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		List<LinerCompany> list = linerService.getCompany(linerCompany);
		j.getBody().put("list", list);
		return j;
	}
	/**
	 * 删除邮轮管理表
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:liner:liner:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(Liner liner, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		linerService.delete(liner);
		j.setMsg("删除邮轮管理表成功");
		return j;
	}
	
	/**
	 * 批量删除邮轮管理表
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:liner:liner:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			linerService.delete(linerService.get(id));
		}
		j.setMsg("删除邮轮管理表成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:liner:liner:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(Liner liner, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "邮轮管理表"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Liner> page = linerService.findPage(new Page<Liner>(request, response, -1), liner);
    		new ExportExcel("邮轮管理表", Liner.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出邮轮管理表记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("meiguotong:liner:liner:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Liner> list = ei.getDataList(Liner.class);
			for (Liner liner : list){
				try{
					linerService.save(liner);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条邮轮管理表记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条邮轮管理表记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入邮轮管理表失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/liner/liner/?repage";
    }
	
	/**
	 * 下载导入邮轮管理表数据模板
	 */
	@RequiresPermissions("meiguotong:liner:liner:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "邮轮管理表数据导入模板.xlsx";
    		List<Liner> list = Lists.newArrayList(); 
    		new ExportExcel("邮轮管理表数据", Liner.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/liner/liner/?repage";
    }

}