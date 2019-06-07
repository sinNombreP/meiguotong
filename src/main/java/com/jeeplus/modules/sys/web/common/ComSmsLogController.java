/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.web.common;

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
import com.jeeplus.modules.sys.entity.common.ComSmsLog;
import com.jeeplus.modules.sys.service.common.ComSmsLogService;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;

/**
 * 验证码Controller
 * @author laiyanxin
 * @version 2018-02-26
 */
@Controller
@RequestMapping(value = "${adminPath}/common/comSmsLog")
public class ComSmsLogController extends BaseController {

	@Autowired
	private ComSmsLogService comSmsLogService;
	
	@ModelAttribute
	public ComSmsLog get(@RequestParam(required=false) String id) {
		ComSmsLog entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = comSmsLogService.get(id);
		}
		if (entity == null){
			entity = new ComSmsLog();
		}
		return entity;
	}
	
	/**
	 * 验证码列表页面
	 */
	@RequiresPermissions("common:comSmsLog:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/common/comSmsLogList";
	}
	
		/**
	 * 验证码列表数据
	 */
	@ResponseBody
	@RequiresPermissions("common:comSmsLog:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(ComSmsLog comSmsLog, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ComSmsLog> page = comSmsLogService.findPage(new Page<ComSmsLog>(request, response), comSmsLog); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑验证码表单页面
	 */
	@RequiresPermissions(value={"common:comSmsLog:view","common:comSmsLog:add","common:comSmsLog:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(ComSmsLog comSmsLog, Model model) {
		model.addAttribute("comSmsLog", comSmsLog);
		if(StringUtils.isBlank(comSmsLog.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/common/comSmsLogForm";
	}

	/**
	 * 保存验证码
	 */
	@RequiresPermissions(value={"common:comSmsLog:add","common:comSmsLog:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(ComSmsLog comSmsLog, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, comSmsLog)){
			return form(comSmsLog, model);
		}
		//新增或编辑表单保存
		comSmsLogService.save(comSmsLog);//保存
		addMessage(redirectAttributes, "保存验证码成功");
		return "redirect:"+Global.getAdminPath()+"/common/comSmsLog/?repage";
	}
	
	/**
	 * 删除验证码
	 */
	@ResponseBody
	@RequiresPermissions("common:comSmsLog:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(ComSmsLog comSmsLog, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		comSmsLogService.delete(comSmsLog);
		j.setMsg("删除验证码成功");
		return j;
	}
	
	/**
	 * 批量删除验证码
	 */
	@ResponseBody
	@RequiresPermissions("common:comSmsLog:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			comSmsLogService.delete(comSmsLogService.get(id));
		}
		j.setMsg("删除验证码成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("common:comSmsLog:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(ComSmsLog comSmsLog, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "验证码"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<ComSmsLog> page = comSmsLogService.findPage(new Page<ComSmsLog>(request, response, -1), comSmsLog);
    		new ExportExcel("验证码", ComSmsLog.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出验证码记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("common:comSmsLog:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<ComSmsLog> list = ei.getDataList(ComSmsLog.class);
			for (ComSmsLog comSmsLog : list){
				try{
					comSmsLogService.save(comSmsLog);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条验证码记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条验证码记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入验证码失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/common/comSmsLog/?repage";
    }
	
	/**
	 * 下载导入验证码数据模板
	 */
	@RequiresPermissions("common:comSmsLog:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "验证码数据导入模板.xlsx";
    		List<ComSmsLog> list = Lists.newArrayList(); 
    		new ExportExcel("验证码数据", ComSmsLog.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/common/comSmsLog/?repage";
    }

}