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
import com.jeeplus.modules.meiguotong.entity.liner.LinerPort;
import com.jeeplus.modules.meiguotong.service.comlanguage.ComLanguageService;
import com.jeeplus.modules.meiguotong.service.liner.LinerPortService;

/**
 * 邮轮港口设置Controller
 * @author dong
 * @version 2018-10-29
 */
@Controller
@RequestMapping(value = "${adminPath}/meiguotong/liner/linerPort")
public class LinerPortController extends BaseController {

	@Autowired
	private LinerPortService linerPortService;
	@Autowired
	private ComLanguageService comLanguageService;
	
	@ModelAttribute
	public LinerPort get(@RequestParam(required=false) String id) {
		LinerPort entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = linerPortService.get(id);
		}
		if (entity == null){
			entity = new LinerPort();
		}
		return entity;
	}
	
	/**
	 * 邮轮港口设置列表页面
	 */
	@RequiresPermissions("meiguotong:liner:linerPort:list")
	@RequestMapping(value = {"list", ""})
	public String list(Model model) {
		//获取所有语言
		List<ComLanguage> list = comLanguageService.getLanguage();
		model.addAttribute("languageList", list);
		return "modules/meiguotong/liner/linerPortList";
	}
	
		/**
	 * 邮轮港口设置列表数据
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:liner:linerPort:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(LinerPort linerPort, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<LinerPort> page = linerPortService.findPage(new Page<LinerPort>(request, response), linerPort); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑邮轮港口设置表单页面
	 */
	@RequiresPermissions(value={"meiguotong:liner:linerPort:view","meiguotong:liner:linerPort:add","meiguotong:liner:linerPort:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(LinerPort linerPort, Model model) {
		//获取所有语言
		List<ComLanguage> list = comLanguageService.getLanguage();
		model.addAttribute("languageList", list);
		model.addAttribute("linerPort", linerPort);
		return "modules/meiguotong/liner/linerPortForm";
	}

	/**
	 * 保存邮轮港口设置
	 */
	@ResponseBody
	@RequiresPermissions(value={"meiguotong:liner:linerPort:add","meiguotong:liner:linerPort:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(LinerPort linerPort, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, linerPort)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		linerPortService.save(linerPort);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存邮轮港口设置成功");
		return j;
	}
	
	/**
	 * 删除邮轮港口设置
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:liner:linerPort:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(LinerPort linerPort, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		linerPortService.delete(linerPort);
		j.setMsg("删除邮轮港口设置成功");
		return j;
	}
	
	/**
	 * 批量删除邮轮港口设置
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:liner:linerPort:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			linerPortService.delete(linerPortService.get(id));
		}
		j.setMsg("删除邮轮港口设置成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:liner:linerPort:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(LinerPort linerPort, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "邮轮港口设置"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<LinerPort> page = linerPortService.findPage(new Page<LinerPort>(request, response, -1), linerPort);
    		new ExportExcel("邮轮港口设置", LinerPort.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出邮轮港口设置记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("meiguotong:liner:linerPort:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<LinerPort> list = ei.getDataList(LinerPort.class);
			for (LinerPort linerPort : list){
				try{
					linerPortService.save(linerPort);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条邮轮港口设置记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条邮轮港口设置记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入邮轮港口设置失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/liner/linerPort/?repage";
    }
	
	/**
	 * 下载导入邮轮港口设置数据模板
	 */
	@RequiresPermissions("meiguotong:liner:linerPort:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "邮轮港口设置数据导入模板.xlsx";
    		List<LinerPort> list = Lists.newArrayList(); 
    		new ExportExcel("邮轮港口设置数据", LinerPort.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/liner/linerPort/?repage";
    }

}