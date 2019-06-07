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

import com.google.common.collect.Lists;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.config.Global;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.jeeplus.modules.meiguotong.entity.compush.ComPushPeople;
import com.jeeplus.modules.meiguotong.service.compush.ComPushPeopleService;

/**
 * 推送人员列表Controller
 * @author dong
 * @version 2018-09-17
 */
@Controller
@RequestMapping(value = "${adminPath}/meiguotong/compush/comPushPeople")
public class ComPushPeopleController extends BaseController {

	@Autowired
	private ComPushPeopleService comPushPeopleService;
	
	@ModelAttribute
	public ComPushPeople get(@RequestParam(required=false) String id) {
		ComPushPeople entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = comPushPeopleService.get(id);
		}
		if (entity == null){
			entity = new ComPushPeople();
		}
		return entity;
	}
	
	/**
	 * 推送人员列表列表页面
	 */
	@RequiresPermissions("meiguotong:compush:comPushPeople:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/meiguotong/compush/comPushPeopleList";
	}
	
		/**
	 * 推送人员列表列表数据
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:compush:comPushPeople:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(ComPushPeople comPushPeople, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ComPushPeople> page = comPushPeopleService.findPage(new Page<ComPushPeople>(request, response), comPushPeople); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑推送人员列表表单页面
	 */
	@RequiresPermissions(value={"meiguotong:compush:comPushPeople:view","meiguotong:compush:comPushPeople:add","meiguotong:compush:comPushPeople:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(ComPushPeople comPushPeople, Model model) {
		model.addAttribute("comPushPeople", comPushPeople);
		if(StringUtils.isBlank(comPushPeople.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/meiguotong/compush/comPushPeopleForm";
	}

	/**
	 * 保存推送人员列表
	 */
	@RequiresPermissions(value={"meiguotong:compush:comPushPeople:add","meiguotong:compush:comPushPeople:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(ComPushPeople comPushPeople, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, comPushPeople)){
			return form(comPushPeople, model);
		}
		//新增或编辑表单保存
		comPushPeopleService.save(comPushPeople);//保存
		addMessage(redirectAttributes, "保存推送人员列表成功");
		return "redirect:"+Global.getAdminPath()+"/meiguotong/compush/comPushPeople/?repage";
	}
	
	/**
	 * 删除推送人员列表
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:compush:comPushPeople:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(ComPushPeople comPushPeople, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		comPushPeopleService.delete(comPushPeople);
		j.setMsg("删除推送人员列表成功");
		return j;
	}
	
	/**
	 * 批量删除推送人员列表
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:compush:comPushPeople:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			comPushPeopleService.delete(comPushPeopleService.get(id));
		}
		j.setMsg("删除推送人员列表成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:compush:comPushPeople:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(ComPushPeople comPushPeople, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "推送人员列表"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<ComPushPeople> page = comPushPeopleService.findPage(new Page<ComPushPeople>(request, response, -1), comPushPeople);
    		new ExportExcel("推送人员列表", ComPushPeople.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出推送人员列表记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("meiguotong:compush:comPushPeople:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<ComPushPeople> list = ei.getDataList(ComPushPeople.class);
			for (ComPushPeople comPushPeople : list){
				try{
					comPushPeopleService.save(comPushPeople);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条推送人员列表记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条推送人员列表记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入推送人员列表失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/compush/comPushPeople/?repage";
    }
	
	/**
	 * 下载导入推送人员列表数据模板
	 */
	@RequiresPermissions("meiguotong:compush:comPushPeople:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "推送人员列表数据导入模板.xlsx";
    		List<ComPushPeople> list = Lists.newArrayList(); 
    		new ExportExcel("推送人员列表数据", ComPushPeople.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/compush/comPushPeople/?repage";
    }

}