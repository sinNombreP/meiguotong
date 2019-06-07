/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.web.compush;

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

import com.alibaba.fastjson.JSON;
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
import com.jeeplus.modules.meiguotong.entity.compush.ComPushContent;
import com.jeeplus.modules.meiguotong.service.comlanguage.ComLanguageService;
import com.jeeplus.modules.meiguotong.service.compush.ComPushContentService;

/**
 * 自动推送模板Controller
 * @author dong
 * @version 2019-03-13
 */
@Controller
@RequestMapping(value = "${adminPath}/meiguotong/compush/comPushContent")
public class ComPushContentController extends BaseController {

	@Autowired
	private ComPushContentService comPushContentService;
	
	@Autowired
	private ComLanguageService comLanguageService;
	
	@ModelAttribute
	public ComPushContent get(@RequestParam(required=false) String id) {
		ComPushContent entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = comPushContentService.get(id);
		}
		if (entity == null){
			entity = new ComPushContent();
		}
		return entity;
	}
	
	/**
	 * 自动推送模板列表页面
	 */
	@RequiresPermissions("meiguotong:compush:comPushContent:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/meiguotong/compush/comPushContentList";
	}
	
		/**
	 * 自动推送模板列表数据
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:compush:comPushContent:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(ComPushContent comPushContent, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ComPushContent> page = comPushContentService.findPage(new Page<ComPushContent>(request, response), comPushContent); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑自动推送模板表单页面
	 */
	@RequiresPermissions(value={"meiguotong:compush:comPushContent:view","meiguotong:compush:comPushContent:add","meiguotong:compush:comPushContent:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(ComPushContent comPushContent, Model model) {
		List<ComLanguage> languages=comLanguageService.findLanguage();
		model.addAttribute("comlanguage", languages);
		return "modules/meiguotong/compush/comPushContentForm";
	}

	/**
	 * 保存自动推送模板
	 */
	@RequiresPermissions(value={"meiguotong:compush:comPushContent:add","meiguotong:compush:comPushContent:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(ComPushContent comPushContent, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, comPushContent)){
			return form(comPushContent, model);
		}
		//新增或编辑表单保存
		comPushContentService.save(comPushContent);//保存
		addMessage(redirectAttributes, "保存自动推送模板成功");
		return "redirect:"+Global.getAdminPath()+"/meiguotong/compush/comPushContent/form";
	}
	
	/**
	 * 根据语言ID查询推送模板
	 */
	@ResponseBody
	@RequestMapping(value="search")
	public AjaxJson findcomPushContent(Integer id){
		List<ComPushContent> comPushContents=comPushContentService.findComPushContentByLanguageid(id);
		AjaxJson j=new AjaxJson();
		j.getBody().put("list", comPushContents);
		return j;
	}
	
	/**
	 * 删除自动推送模板
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:compush:comPushContent:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(ComPushContent comPushContent, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		comPushContentService.delete(comPushContent);
		j.setMsg("删除自动推送模板成功");
		return j;
	}
	
	/**
	 * 批量删除自动推送模板
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:compush:comPushContent:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			comPushContentService.delete(comPushContentService.get(id));
		}
		j.setMsg("删除自动推送模板成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:compush:comPushContent:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(ComPushContent comPushContent, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "自动推送模板"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<ComPushContent> page = comPushContentService.findPage(new Page<ComPushContent>(request, response, -1), comPushContent);
    		new ExportExcel("自动推送模板", ComPushContent.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出自动推送模板记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("meiguotong:compush:comPushContent:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<ComPushContent> list = ei.getDataList(ComPushContent.class);
			for (ComPushContent comPushContent : list){
				try{
					comPushContentService.save(comPushContent);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条自动推送模板记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条自动推送模板记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入自动推送模板失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/compush/comPushContent/?repage";
    }
	
	/**
	 * 下载导入自动推送模板数据模板
	 */
	@RequiresPermissions("meiguotong:compush:comPushContent:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "自动推送模板数据导入模板.xlsx";
    		List<ComPushContent> list = Lists.newArrayList(); 
    		new ExportExcel("自动推送模板数据", ComPushContent.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/compush/comPushContent/?repage";
    }

}