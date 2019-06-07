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
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.jeeplus.modules.sys.entity.common.ComLoginLog;
import com.jeeplus.modules.sys.service.common.ComLoginLogService;

/**
 * 会员登录管理Controller
 * @author xudemo
 * @version 2018-03-07
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/comLoginLog")
public class ComLoginLogController extends BaseController {

	@Autowired
	private ComLoginLogService comLoginLogService;
	
	@ModelAttribute
	public ComLoginLog get(@RequestParam(required=false) String id) {
		ComLoginLog entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = comLoginLogService.get(id);
		}
		if (entity == null){
			entity = new ComLoginLog();
		}
		return entity;
	}
	
	/**
	 * 会员登录管理列表页面
	 */
	@RequiresPermissions("sys:comLoginLog:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/sys/comLoginLog/comLoginLogList";
	}
	
		/**
	 * 会员登录管理列表数据
	 */
	@ResponseBody
	@RequiresPermissions("sys:comLoginLog:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(ComLoginLog comLoginLog, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ComLoginLog> page = comLoginLogService.findPage(new Page<ComLoginLog>(request, response), comLoginLog); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑会员登录管理表单页面
	 */
	@RequiresPermissions(value={"sys:comLoginLog:view","sys:comLoginLog:add","sys:comLoginLog:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(ComLoginLog comLoginLog, Model model) {
		model.addAttribute("comLoginLog", comLoginLog);
		if(StringUtils.isBlank(comLoginLog.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/sys/comLoginLog/comLoginLogForm";
	}

	/**
	 * 保存会员登录管理
	 */
	@RequiresPermissions(value={"sys:comLoginLog:add","sys:comLoginLog:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(ComLoginLog comLoginLog, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, comLoginLog)){
			return form(comLoginLog, model);
		}
		//新增或编辑表单保存
		comLoginLogService.save(comLoginLog);//保存
		addMessage(redirectAttributes, "保存会员登录管理成功");
		return "redirect:"+Global.getAdminPath()+"/sys/comLoginLog/?repage";
	}
	
	/**
	 * 删除会员登录管理
	 */
	@ResponseBody
	@RequiresPermissions("sys:comLoginLog:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(ComLoginLog comLoginLog, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		comLoginLogService.delete(comLoginLog);
		j.setMsg("删除会员登录管理成功");
		return j;
	}
	
	/**
	 * 批量删除会员登录管理
	 */
	@ResponseBody
	@RequiresPermissions("sys:comLoginLog:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			comLoginLogService.delete(comLoginLogService.get(id));
		}
		j.setMsg("删除会员登录管理成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("sys:comLoginLog:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(ComLoginLog comLoginLog, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "会员登录管理"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<ComLoginLog> page = comLoginLogService.findPage(new Page<ComLoginLog>(request, response, -1), comLoginLog);
    		new ExportExcel("会员登录管理", ComLoginLog.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出会员登录管理记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("sys:comLoginLog:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<ComLoginLog> list = ei.getDataList(ComLoginLog.class);
			for (ComLoginLog comLoginLog : list){
				try{
					comLoginLogService.save(comLoginLog);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条会员登录管理记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条会员登录管理记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入会员登录管理失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/comLoginLog/?repage";
    }
	
	/**
	 * 下载导入会员登录管理数据模板
	 */
	@RequiresPermissions("sys:comLoginLog:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "会员登录管理数据导入模板.xlsx";
    		List<ComLoginLog> list = Lists.newArrayList(); 
    		new ExportExcel("会员登录管理数据", ComLoginLog.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/comLoginLog/?repage";
    }

}