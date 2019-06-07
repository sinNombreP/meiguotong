/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.web.travel;

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
import com.jeeplus.modules.meiguotong.entity.travel.TravelDetails;
import com.jeeplus.modules.meiguotong.service.travel.TravelDetailsService;

/**
 * 旅游定制详情Controller
 * @author psz
 * @version 2018-08-27
 */
@Controller
@RequestMapping(value = "${adminPath}/meiguotong/travel/travelDetails")
public class TravelDetailsController extends BaseController {

	@Autowired
	private TravelDetailsService travelDetailsService;
	
	@ModelAttribute
	public TravelDetails get(@RequestParam(required=false) String id) {
		TravelDetails entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = travelDetailsService.get(id);
		}
		if (entity == null){
			entity = new TravelDetails();
		}
		return entity;
	}
	
	/**
	 * 旅游定制详情列表页面
	 */
	@RequiresPermissions("meiguotong:travel:travelDetails:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/meiguotong/travel/travelDetailsList";
	}
	
		/**
	 * 旅游定制详情列表数据
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:travel:travelDetails:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(TravelDetails travelDetails, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<TravelDetails> page = travelDetailsService.findPage(new Page<TravelDetails>(request, response), travelDetails); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑旅游定制详情表单页面
	 */
	@RequiresPermissions(value={"meiguotong:travel:travelDetails:view","meiguotong:travel:travelDetails:add","meiguotong:travel:travelDetails:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(TravelDetails travelDetails, Model model) {
		model.addAttribute("travelDetails", travelDetails);
		if(StringUtils.isBlank(travelDetails.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/meiguotong/travel/travelDetailsForm";
	}

	/**
	 * 保存旅游定制详情
	 */
	@RequiresPermissions(value={"meiguotong:travel:travelDetails:add","meiguotong:travel:travelDetails:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(TravelDetails travelDetails, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, travelDetails)){
			return form(travelDetails, model);
		}
		//新增或编辑表单保存
		travelDetailsService.save(travelDetails);//保存
		addMessage(redirectAttributes, "保存旅游定制详情成功");
		return "redirect:"+Global.getAdminPath()+"/meiguotong/travel/travelDetails/?repage";
	}
	
	/**
	 * 删除旅游定制详情
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:travel:travelDetails:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(TravelDetails travelDetails, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		travelDetailsService.delete(travelDetails);
		j.setMsg("删除旅游定制详情成功");
		return j;
	}
	
	/**
	 * 批量删除旅游定制详情
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:travel:travelDetails:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			travelDetailsService.delete(travelDetailsService.get(id));
		}
		j.setMsg("删除旅游定制详情成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:travel:travelDetails:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(TravelDetails travelDetails, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "旅游定制详情"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<TravelDetails> page = travelDetailsService.findPage(new Page<TravelDetails>(request, response, -1), travelDetails);
    		new ExportExcel("旅游定制详情", TravelDetails.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出旅游定制详情记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("meiguotong:travel:travelDetails:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<TravelDetails> list = ei.getDataList(TravelDetails.class);
			for (TravelDetails travelDetails : list){
				try{
					travelDetailsService.save(travelDetails);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条旅游定制详情记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条旅游定制详情记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入旅游定制详情失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/travel/travelDetails/?repage";
    }
	
	/**
	 * 下载导入旅游定制详情数据模板
	 */
	@RequiresPermissions("meiguotong:travel:travelDetails:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "旅游定制详情数据导入模板.xlsx";
    		List<TravelDetails> list = Lists.newArrayList(); 
    		new ExportExcel("旅游定制详情数据", TravelDetails.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/travel/travelDetails/?repage";
    }

}