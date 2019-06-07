/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.web.score;

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
import com.jeeplus.modules.sys.entity.score.SorceLog;
import com.jeeplus.modules.sys.service.score.SorceLogService;

/**
 * 积分管理Controller
 * @author psz
 * @version 2018-03-07
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/score/sorceLog")
public class SorceLogController extends BaseController {

	@Autowired
	private SorceLogService sorceLogService;
	
	@ModelAttribute
	public SorceLog get(@RequestParam(required=false) String id) {
		SorceLog entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = sorceLogService.get(id);
		}
		if (entity == null){
			entity = new SorceLog();
		}
		return entity;
	}
	
	/**
	 * 积分管理列表页面
	 */
//	@RequiresPermissions("sys:score:sorceLog:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/sys/score/sorceLogList";
	}
	
	/**
	 * 我的积分列表页面
	 */
	
	@RequestMapping(value = {"myList"})
	public String myList(SorceLog sorceLog, Model model) {
		model.addAttribute("memberid", sorceLog.getMemberId());
		sorceLog.setWay(1);
		//收入积分
		Integer incomeNum = sorceLogService.getMyScore(sorceLog);
		if(incomeNum==null){
			incomeNum=0;
		}
		model.addAttribute("incomeNum",incomeNum);
		//支出积分
		sorceLog.setWay(2);
		Integer payNum	= sorceLogService.getMyScore(sorceLog);
		if(payNum==null){
			payNum=0;
		}
		sorceLog.setWay(null);
		model.addAttribute("payNum",payNum);
		return "modules/sys/score/sorceLogMyList";
	}
	
		/**
	 * 积分管理列表数据
	 */
	@ResponseBody
//	@RequiresPermissions("sys:score:sorceLog:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(SorceLog sorceLog, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SorceLog> page = sorceLogService.findPage(new Page<SorceLog>(request, response), sorceLog); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑积分管理表单页面
	 */
//	@RequiresPermissions(value={"sys:score:sorceLog:view","sys:score:sorceLog:add","sys:score:sorceLog:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(SorceLog sorceLog, Model model) {
		model.addAttribute("sorceLog", sorceLog);
		if(StringUtils.isBlank(sorceLog.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/sys/score/sorceLogForm";
	}

	/**
	 * 保存积分管理
	 */
//	@RequiresPermissions(value={"sys:score:sorceLog:add","sys:score:sorceLog:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(SorceLog sorceLog, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, sorceLog)){
			return form(sorceLog, model);
		}
		//新增或编辑表单保存
		sorceLogService.save(sorceLog);//保存
		addMessage(redirectAttributes, "保存积分管理成功");
		return "redirect:"+Global.getAdminPath()+"/sys/score/sorceLog/?repage";
	}
	
	/**
	 * 删除积分管理
	 */
	@ResponseBody
//	@RequiresPermissions("sys:score:sorceLog:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(SorceLog sorceLog, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		sorceLogService.delete(sorceLog);
		j.setMsg("删除积分管理成功");
		return j;
	}
	
	/**
	 * 批量删除积分管理
	 */
	@ResponseBody
//	@RequiresPermissions("sys:score:sorceLog:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			sorceLogService.delete(sorceLogService.get(id));
		}
		j.setMsg("删除积分管理成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
//	@RequiresPermissions("sys:score:sorceLog:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(SorceLog sorceLog, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "积分管理"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<SorceLog> page = sorceLogService.findPage(new Page<SorceLog>(request, response, -1), sorceLog);
    		new ExportExcel("积分管理", SorceLog.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出积分管理记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
//	@RequiresPermissions("sys:score:sorceLog:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<SorceLog> list = ei.getDataList(SorceLog.class);
			for (SorceLog sorceLog : list){
				try{
					sorceLogService.save(sorceLog);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条积分管理记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条积分管理记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入积分管理失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/score/sorceLog/?repage";
    }
	
	/**
	 * 下载导入积分管理数据模板
	 */
//	@RequiresPermissions("sys:score:sorceLog:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "积分管理数据导入模板.xlsx";
    		List<SorceLog> list = Lists.newArrayList(); 
    		new ExportExcel("积分管理数据", SorceLog.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/score/sorceLog/?repage";
    }

}