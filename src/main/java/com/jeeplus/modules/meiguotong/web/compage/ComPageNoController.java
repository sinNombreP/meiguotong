/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.web.compage;

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
import com.jeeplus.modules.meiguotong.entity.compage.ComPageNo;
import com.jeeplus.modules.meiguotong.service.compage.ComPageNoService;

/**
 * 分页展示数量设置Controller
 * @author dong
 * @version 2018-10-16
 */
@Controller
@RequestMapping(value = "${adminPath}/meiguotong/compage/comPageNo")
public class ComPageNoController extends BaseController {

	@Autowired
	private ComPageNoService comPageNoService;
	
	@ModelAttribute
	public ComPageNo get(@RequestParam(required=false) String id) {
		ComPageNo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = comPageNoService.get(id);
		}
		if (entity == null){
			entity = new ComPageNo();
		}
		return entity;
	}
	
	/**
	 * 分页展示数量设置列表页面
	 */
	@RequiresPermissions("meiguotong:compage:comPageNo:list")
	@RequestMapping(value = {"list", ""})
	public String list(ComPageNo comPageNo, Model model) {
		comPageNo = comPageNoService.get(comPageNo);
		if(comPageNo==null){
			comPageNo = new ComPageNo();
		}
		model.addAttribute("comPageNo", comPageNo);
		return "modules/meiguotong/compage/comPageNoForm";
	}
	
		/**
	 * 分页展示数量设置列表数据
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:compage:comPageNo:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(ComPageNo comPageNo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ComPageNo> page = comPageNoService.findPage(new Page<ComPageNo>(request, response), comPageNo); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑分页展示数量设置表单页面
	 */
	@RequiresPermissions(value={"meiguotong:compage:comPageNo:view","meiguotong:compage:comPageNo:add","meiguotong:compage:comPageNo:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(ComPageNo comPageNo, Model model) {
		comPageNo = comPageNoService.get(comPageNo);
		if(comPageNo==null){
			comPageNo = new ComPageNo();
		}
		model.addAttribute("comPageNo", comPageNo);
		if(StringUtils.isBlank(comPageNo.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/meiguotong/compage/comPageNoForm";
	}

	/**
	 * 保存分页展示数量设置
	 */
	@RequiresPermissions(value={"meiguotong:compage:comPageNo:add","meiguotong:compage:comPageNo:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(ComPageNo comPageNo, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, comPageNo)){
			return form(comPageNo, model);
		}
		//新增或编辑表单保存
		comPageNoService.save(comPageNo);//保存
		addMessage(redirectAttributes, "保存分页展示数量设置成功");
		return "redirect:"+Global.getAdminPath()+"/meiguotong/compage/comPageNo/?repage";
	}
	
	/**
	 * 删除分页展示数量设置
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:compage:comPageNo:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(ComPageNo comPageNo, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		comPageNoService.delete(comPageNo);
		j.setMsg("删除分页展示数量设置成功");
		return j;
	}
	
	/**
	 * 批量删除分页展示数量设置
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:compage:comPageNo:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			comPageNoService.delete(comPageNoService.get(id));
		}
		j.setMsg("删除分页展示数量设置成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:compage:comPageNo:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(ComPageNo comPageNo, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "分页展示数量设置"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<ComPageNo> page = comPageNoService.findPage(new Page<ComPageNo>(request, response, -1), comPageNo);
    		new ExportExcel("分页展示数量设置", ComPageNo.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出分页展示数量设置记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("meiguotong:compage:comPageNo:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<ComPageNo> list = ei.getDataList(ComPageNo.class);
			for (ComPageNo comPageNo : list){
				try{
					comPageNoService.save(comPageNo);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条分页展示数量设置记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条分页展示数量设置记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入分页展示数量设置失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/compage/comPageNo/?repage";
    }
	
	/**
	 * 下载导入分页展示数量设置数据模板
	 */
	@RequiresPermissions("meiguotong:compage:comPageNo:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "分页展示数量设置数据导入模板.xlsx";
    		List<ComPageNo> list = Lists.newArrayList(); 
    		new ExportExcel("分页展示数量设置数据", ComPageNo.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/compage/comPageNo/?repage";
    }

}