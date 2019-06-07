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
import com.jeeplus.modules.meiguotong.entity.liner.LinerTime;
import com.jeeplus.modules.meiguotong.service.liner.LinerTimeService;

/**
 * 游轮路线日期设置Controller
 * @author dong
 * @version 2018-10-26
 */
@Controller
@RequestMapping(value = "${adminPath}/meiguotong/liner/linerTime")
public class LinerTimeController extends BaseController {

	@Autowired
	private LinerTimeService linerTimeService;
	
	@ModelAttribute
	public LinerTime get(@RequestParam(required=false) String id) {
		LinerTime entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = linerTimeService.get(id);
		}
		if (entity == null){
			entity = new LinerTime();
		}
		return entity;
	}
	
	/**
	 * 游轮路线日期设置列表页面
	 */
	@RequiresPermissions("meiguotong:liner:linerTime:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/meiguotong/liner/linerTimeList";
	}
	
		/**
	 * 游轮路线日期设置列表数据
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:liner:linerTime:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(LinerTime linerTime, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<LinerTime> page = linerTimeService.findPage(new Page<LinerTime>(request, response), linerTime); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑游轮路线日期设置表单页面
	 */
	@RequiresPermissions(value={"meiguotong:liner:linerTime:view","meiguotong:liner:linerTime:add","meiguotong:liner:linerTime:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(LinerTime linerTime, Model model) {
		model.addAttribute("linerTime", linerTime);
		if(StringUtils.isBlank(linerTime.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/meiguotong/liner/linerTimeForm";
	}

	/**
	 * 保存游轮路线日期设置
	 */
	@RequiresPermissions(value={"meiguotong:liner:linerTime:add","meiguotong:liner:linerTime:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(LinerTime linerTime, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, linerTime)){
			return form(linerTime, model);
		}
		//新增或编辑表单保存
		linerTimeService.save(linerTime);//保存
		addMessage(redirectAttributes, "保存游轮路线日期设置成功");
		return "redirect:"+Global.getAdminPath()+"/meiguotong/liner/linerTime/?repage";
	}
	
	/**
	 * 删除游轮路线日期设置
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:liner:linerTime:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(LinerTime linerTime, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		linerTimeService.delete(linerTime);
		j.setMsg("删除游轮路线日期设置成功");
		return j;
	}
	
	/**
	 * 批量删除游轮路线日期设置
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:liner:linerTime:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			linerTimeService.delete(linerTimeService.get(id));
		}
		j.setMsg("删除游轮路线日期设置成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:liner:linerTime:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(LinerTime linerTime, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "游轮路线日期设置"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<LinerTime> page = linerTimeService.findPage(new Page<LinerTime>(request, response, -1), linerTime);
    		new ExportExcel("游轮路线日期设置", LinerTime.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出游轮路线日期设置记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("meiguotong:liner:linerTime:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<LinerTime> list = ei.getDataList(LinerTime.class);
			for (LinerTime linerTime : list){
				try{
					linerTimeService.save(linerTime);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条游轮路线日期设置记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条游轮路线日期设置记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入游轮路线日期设置失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/liner/linerTime/?repage";
    }
	
	/**
	 * 下载导入游轮路线日期设置数据模板
	 */
	@RequiresPermissions("meiguotong:liner:linerTime:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "游轮路线日期设置数据导入模板.xlsx";
    		List<LinerTime> list = Lists.newArrayList(); 
    		new ExportExcel("游轮路线日期设置数据", LinerTime.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/liner/linerTime/?repage";
    }

}