/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.web.comcitytravel;

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
import com.jeeplus.modules.meiguotong.entity.comcitytravel.ComCityTravel;
import com.jeeplus.modules.meiguotong.service.comcitytravel.ComCityTravelService;

/**
 * 目的地关联旅游定制Controller
 * @author dong
 * @version 2019-03-22
 */
@Controller
@RequestMapping(value = "${adminPath}/meiguotong/comcitytravel/comCityTravel")
public class ComCityTravelController extends BaseController {

	@Autowired
	private ComCityTravelService comCityTravelService;
	
	@ModelAttribute
	public ComCityTravel get(@RequestParam(required=false) String id) {
		ComCityTravel entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = comCityTravelService.get(id);
		}
		if (entity == null){
			entity = new ComCityTravel();
		}
		return entity;
	}
	
	/**
	 * 目的地关联旅游定制列表页面
	 */
	@RequiresPermissions("meiguotong:comcitytravel:comCityTravel:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/meiguotong/comcitytravel/comCityTravelList";
	}
	
		/**
	 * 目的地关联旅游定制列表数据
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:comcitytravel:comCityTravel:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(ComCityTravel comCityTravel, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ComCityTravel> page = comCityTravelService.findPage(new Page<ComCityTravel>(request, response), comCityTravel); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑目的地关联旅游定制表单页面
	 */
	@RequiresPermissions(value={"meiguotong:comcitytravel:comCityTravel:view","meiguotong:comcitytravel:comCityTravel:add","meiguotong:comcitytravel:comCityTravel:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(ComCityTravel comCityTravel, Model model) {
		model.addAttribute("comCityTravel", comCityTravel);
		if(StringUtils.isBlank(comCityTravel.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/meiguotong/comcitytravel/comCityTravelForm";
	}

	/**
	 * 保存目的地关联旅游定制
	 */
	@RequiresPermissions(value={"meiguotong:comcitytravel:comCityTravel:add","meiguotong:comcitytravel:comCityTravel:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(ComCityTravel comCityTravel, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, comCityTravel)){
			return form(comCityTravel, model);
		}
		//新增或编辑表单保存
		comCityTravelService.save(comCityTravel);//保存
		addMessage(redirectAttributes, "保存目的地关联旅游定制成功");
		return "redirect:"+Global.getAdminPath()+"/meiguotong/comcitytravel/comCityTravel/?repage";
	}
	
	/**
	 * 删除目的地关联旅游定制
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:comcitytravel:comCityTravel:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(ComCityTravel comCityTravel, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		comCityTravelService.delete(comCityTravel);
		j.setMsg("删除目的地关联旅游定制成功");
		return j;
	}
	
	/**
	 * 批量删除目的地关联旅游定制
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:comcitytravel:comCityTravel:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			comCityTravelService.delete(comCityTravelService.get(id));
		}
		j.setMsg("删除目的地关联旅游定制成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:comcitytravel:comCityTravel:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(ComCityTravel comCityTravel, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "目的地关联旅游定制"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<ComCityTravel> page = comCityTravelService.findPage(new Page<ComCityTravel>(request, response, -1), comCityTravel);
    		new ExportExcel("目的地关联旅游定制", ComCityTravel.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出目的地关联旅游定制记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("meiguotong:comcitytravel:comCityTravel:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<ComCityTravel> list = ei.getDataList(ComCityTravel.class);
			for (ComCityTravel comCityTravel : list){
				try{
					comCityTravelService.save(comCityTravel);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条目的地关联旅游定制记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条目的地关联旅游定制记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入目的地关联旅游定制失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/comcitytravel/comCityTravel/?repage";
    }
	
	/**
	 * 下载导入目的地关联旅游定制数据模板
	 */
	@RequiresPermissions("meiguotong:comcitytravel:comCityTravel:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "目的地关联旅游定制数据导入模板.xlsx";
    		List<ComCityTravel> list = Lists.newArrayList(); 
    		new ExportExcel("目的地关联旅游定制数据", ComCityTravel.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/comcitytravel/comCityTravel/?repage";
    }

}