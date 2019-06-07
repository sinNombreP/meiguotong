/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.web.comconsult;

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
import com.jeeplus.modules.meiguotong.entity.comconsult.ComConsult;
import com.jeeplus.modules.meiguotong.entity.comlanguage.ComLanguage;
import com.jeeplus.modules.meiguotong.service.comconsult.ComConsultService;
import com.jeeplus.modules.meiguotong.service.comlanguage.ComLanguageService;

/**
 * 用户咨询Controller
 * @author psz
 * @version 2018-08-16
 */
@Controller
@RequestMapping(value = "${adminPath}/meiguotong/comconsult/comConsult")
public class ComConsultController extends BaseController {

	@Autowired
	private ComConsultService comConsultService;
	@Autowired
	private ComLanguageService comLanguageService;
	
	@ModelAttribute
	public ComConsult get(@RequestParam(required=false) String id) {
		ComConsult entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = comConsultService.get(id);
		}
		if (entity == null){
			entity = new ComConsult();
		}
		return entity;
	}
	
	/**
	 * 用户咨询列表页面
	 */
	@RequestMapping(value = {"list", ""})
	public String list(ComConsult comConsult, Model model) {
		//语言
		List<ComLanguage> comLanguageList = comLanguageService.findList(new ComLanguage());
		model.addAttribute("comLanguageList",comLanguageList);
		model.addAttribute("comConsult", comConsult);
		return "modules/meiguotong/product/comConsultList";
	}
	/**
	 * 用户咨询列表页面
	 */
	@RequestMapping(value = {"list1"})
	public String list1(ComConsult comConsult, Model model) {
		//语言
		List<ComLanguage> comLanguageList = comLanguageService.findList(new ComLanguage());
		model.addAttribute("comLanguageList",comLanguageList);
		model.addAttribute("comConsult", comConsult);
		return "modules/meiguotong/product/comConsultList1";
	}
		/**
	 * 用户咨询列表数据
	 */
	@ResponseBody
	@RequestMapping(value = "data")
	public Map<String, Object> data(ComConsult comConsult, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ComConsult> page = comConsultService.findPage(new Page<ComConsult>(request, response), comConsult); 
		return getBootstrapData(page);
	}

	/** 
	* @Title: updateStatus 
	* @Description: 启用禁用
	* @author 彭善智
	* @date 2018年8月16日下午5:47:25
	*/ 
	@ResponseBody
	@RequestMapping(value = "updateStatus")
	public AjaxJson updateStatus(ComConsult comConsult, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		comConsultService.updateStatus(comConsult);
		j.setMsg("更改状态成功");
		return j;
	}
	
	
	/** 
	* @Title: updateContent 
	* @Description: 回复
	* @author 彭善智
	* @date 2018年8月16日下午5:52:59
	*/ 
	@ResponseBody
	@RequestMapping(value = "updateContent")
	public AjaxJson updateContent(ComConsult comConsult, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		comConsultService.updateContent(comConsult);
		j.setMsg("回复成功");
		return j;
	}
	
	/**
	 * 查看，增加，编辑用户咨询表单页面
	 */
	@RequiresPermissions(value={"meiguotong:comconsult:comConsult:view","meiguotong:comconsult:comConsult:add","meiguotong:comconsult:comConsult:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(ComConsult comConsult, Model model) {
		model.addAttribute("comConsult", comConsult);
		if(StringUtils.isBlank(comConsult.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/meiguotong/comconsult/comConsultForm";
	}

	/**
	 * 保存用户咨询
	 */
	@RequiresPermissions(value={"meiguotong:comconsult:comConsult:add","meiguotong:comconsult:comConsult:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(ComConsult comConsult, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, comConsult)){
			return form(comConsult, model);
		}
		//新增或编辑表单保存
		comConsultService.save(comConsult);//保存
		addMessage(redirectAttributes, "保存用户咨询成功");
		return "redirect:"+Global.getAdminPath()+"/meiguotong/comconsult/comConsult/?repage";
	}
	
	/**
	 * 删除用户咨询
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:comconsult:comConsult:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(ComConsult comConsult, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		comConsultService.delete(comConsult);
		j.setMsg("删除用户咨询成功");
		return j;
	}
	
	/**
	 * 批量删除用户咨询
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:comconsult:comConsult:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			comConsultService.delete(comConsultService.get(id));
		}
		j.setMsg("删除用户咨询成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(ComConsult comConsult, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "用户咨询"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<ComConsult> page = comConsultService.findPage(new Page<ComConsult>(request, response, -1), comConsult);
    		new ExportExcel("用户咨询", ComConsult.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出用户咨询记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes,ComConsult c) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<ComConsult> list = ei.getDataList(ComConsult.class);
			for (ComConsult comConsult : list){
				try{
					comConsult.setType(c.getType());
					comConsult.setTypeId(c.getTypeId());
					comConsult.setLanguageId(c.getLanguageId());
					comConsult.setStatus(1);
					comConsultService.save(comConsult);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条用户咨询记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条用户咨询记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入用户咨询失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/comconsult/comConsult/list1?repage&type="+c.getType()+"&typeId="+c.getTypeId()+"&languageId="+c.getLanguageId();
    }
	/**
	 * 下载导入用户咨询数据模板
	 */
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "用户咨询数据导入模板.xlsx";
    		List<ComConsult> list = Lists.newArrayList(); 
    		new ExportExcel("用户咨询数据", ComConsult.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/comconsult/comConsult/?repage";
    }

}