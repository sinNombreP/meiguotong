/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.web.settingtitle;

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
import com.jeeplus.modules.meiguotong.entity.settingtitle.SettingTitle;
import com.jeeplus.modules.meiguotong.service.comlanguage.ComLanguageService;
import com.jeeplus.modules.meiguotong.service.settingtitle.SettingTitleService;
/**
 * 详情表Controller
 * @author cdq
 * @version 2018-08-06
 */
@Controller
@RequestMapping(value = "${adminPath}/meiguotong/settingtitle/settingTitle")
public class SettingTitleController extends BaseController {

	@Autowired
	private SettingTitleService settingTitleService;
	@Autowired
	private ComLanguageService comLanguageService;
	@ModelAttribute
	public SettingTitle get(@RequestParam(required=false) String id) {
		SettingTitle entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = settingTitleService.get(id);
		}
		if (entity == null){
			entity = new SettingTitle();
		}
		return entity;
	}
	
	/**
	 * 详情表列表页面
	 */
	@RequiresPermissions("meiguotong:settingtitle:settingTitle:list")
	@RequestMapping(value = {"list", ""})
	public String list(Model model) {
		List<ComLanguage> languageList =comLanguageService.findLanguage();
		model.addAttribute("languageList", languageList);
		return "modules/meiguotong/settingtitle/settingTitleList";
	}
	
		/**
	 * 详情表列表数据
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:settingtitle:settingTitle:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(SettingTitle settingTitle, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SettingTitle> page = settingTitleService.findPage(new Page<SettingTitle>(request, response, -1), settingTitle); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑详情表表单页面
	 */
	@RequiresPermissions(value={"meiguotong:settingtitle:settingTitle:view","meiguotong:settingtitle:settingTitle:add","meiguotong:settingtitle:settingTitle:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(SettingTitle settingTitle, Model model) {
		List<ComLanguage> languageList =comLanguageService.findLanguage();
		model.addAttribute("languageList", languageList);
		model.addAttribute("settingTitle", settingTitle);
		if(StringUtils.isBlank(settingTitle.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/meiguotong/settingtitle/settingTitleForm";
	}

	/**
	 * 保存详情表
	 */
	@ResponseBody
	@RequiresPermissions(value={"meiguotong:settingtitle:settingTitle:add","meiguotong:settingtitle:settingTitle:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(SettingTitle settingTitle, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, settingTitle)){
			j.setSuccess(false);
			j.setMsg("非法参数");
			return j;
		}
		//新增或者编辑表单保存
		settingTitleService.save(settingTitle);//保存
		j.setSuccess(true);
		j.setMsg("保存成功");
		return j;	
	}
	   /**
	   *修改状态
	   */
		@ResponseBody
		@RequestMapping(value = "status")
		public AjaxJson status(SettingTitle settingTitle, RedirectAttributes redirectAttributes) {
			AjaxJson j = new AjaxJson();
			if(settingTitle.getStatus()==2){
				settingTitle.setStatus(1);
			}else{
				settingTitle.setStatus(2);
			}
			settingTitleService.status(settingTitle);
			j.setMsg("修改状态成功");
			return j;
		}
	/**
	 * 删除详情表
	 */
	@ResponseBody
	@RequestMapping(value = "delete")
	public AjaxJson delete(SettingTitle settingTitle, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		settingTitleService.delete(settingTitle);
		j.setMsg("删除详情标题成功");
		return j;
	}
	
	/**
	 * 批量删除详情表
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:settingtitle:settingTitle:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			settingTitleService.delete(settingTitleService.get(id));
		}
		j.setMsg("删除详情表成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:settingtitle:settingTitle:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(SettingTitle settingTitle, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "详情表"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<SettingTitle> page = settingTitleService.findPage(new Page<SettingTitle>(request, response, -1), settingTitle);
    		new ExportExcel("详情表", SettingTitle.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出详情表记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("meiguotong:settingtitle:settingTitle:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<SettingTitle> list = ei.getDataList(SettingTitle.class);
			for (SettingTitle settingTitle : list){
				try{
					settingTitleService.save(settingTitle);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条详情表记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条详情表记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入详情表失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/settingtitle/settingTitle/?repage";
    }
	
	/**
	 * 下载导入详情表数据模板
	 */
	@RequiresPermissions("meiguotong:settingtitle:settingTitle:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "详情表数据导入模板.xlsx";
    		List<SettingTitle> list = Lists.newArrayList(); 
    		new ExportExcel("详情表数据", SettingTitle.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/settingtitle/settingTitle/?repage";
    }

}