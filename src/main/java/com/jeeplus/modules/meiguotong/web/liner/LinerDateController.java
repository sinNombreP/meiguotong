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
import com.jeeplus.modules.meiguotong.entity.liner.LinerDate;
import com.jeeplus.modules.meiguotong.service.liner.LinerDateService;

/**
 * 游轮路线日期价格Controller
 * @author dong
 * @version 2018-10-26
 */
@Controller
@RequestMapping(value = "${adminPath}/meiguotong/liner/linerDate")
public class LinerDateController extends BaseController {

	@Autowired
	private LinerDateService linerDateService;
	
	@ModelAttribute
	public LinerDate get(@RequestParam(required=false) String id) {
		LinerDate entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = linerDateService.get(id);
		}
		if (entity == null){
			entity = new LinerDate();
		}
		return entity;
	}
	
	/**
	 * 游轮路线日期价格列表页面
	 */
	@RequiresPermissions("meiguotong:liner:linerDate:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/meiguotong/liner/linerDateList";
	}
	
		/**
	 * 游轮路线日期价格列表数据
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:liner:linerDate:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(LinerDate linerDate, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<LinerDate> page = linerDateService.findPage(new Page<LinerDate>(request, response), linerDate); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑游轮路线日期价格表单页面
	 */
	@RequiresPermissions(value={"meiguotong:liner:linerDate:view","meiguotong:liner:linerDate:add","meiguotong:liner:linerDate:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(LinerDate linerDate, Model model) {
		model.addAttribute("linerDate", linerDate);
		if(StringUtils.isBlank(linerDate.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/meiguotong/liner/linerDateForm";
	}

	/**
	 * 保存游轮路线日期价格
	 */
	@RequiresPermissions(value={"meiguotong:liner:linerDate:add","meiguotong:liner:linerDate:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(LinerDate linerDate, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, linerDate)){
			return form(linerDate, model);
		}
		//新增或编辑表单保存
		linerDateService.save(linerDate);//保存
		addMessage(redirectAttributes, "保存游轮路线日期价格成功");
		return "redirect:"+Global.getAdminPath()+"/meiguotong/liner/linerDate/?repage";
	}
	
	/**
	 * 删除游轮路线日期价格
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:liner:linerDate:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(LinerDate linerDate, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		linerDateService.delete(linerDate);
		j.setMsg("删除游轮路线日期价格成功");
		return j;
	}
	
	/**
	 * 批量删除游轮路线日期价格
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:liner:linerDate:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			linerDateService.delete(linerDateService.get(id));
		}
		j.setMsg("删除游轮路线日期价格成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:liner:linerDate:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(LinerDate linerDate, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "游轮路线日期价格"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<LinerDate> page = linerDateService.findPage(new Page<LinerDate>(request, response, -1), linerDate);
    		new ExportExcel("游轮路线日期价格", LinerDate.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出游轮路线日期价格记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("meiguotong:liner:linerDate:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<LinerDate> list = ei.getDataList(LinerDate.class);
			for (LinerDate linerDate : list){
				try{
					linerDateService.save(linerDate);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条游轮路线日期价格记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条游轮路线日期价格记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入游轮路线日期价格失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/liner/linerDate/?repage";
    }
	
	/**
	 * 下载导入游轮路线日期价格数据模板
	 */
	@RequiresPermissions("meiguotong:liner:linerDate:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "游轮路线日期价格数据导入模板.xlsx";
    		List<LinerDate> list = Lists.newArrayList(); 
    		new ExportExcel("游轮路线日期价格数据", LinerDate.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/liner/linerDate/?repage";
    }

}