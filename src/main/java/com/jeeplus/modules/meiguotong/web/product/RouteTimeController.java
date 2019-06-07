/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.web.product;

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
import com.jeeplus.modules.meiguotong.entity.product.RouteTime;
import com.jeeplus.modules.meiguotong.service.product.RouteTimeService;

/**
 * 参团Controller
 * @author psz
 * @version 2018-08-14
 */
@Controller
@RequestMapping(value = "${adminPath}/meiguotong/product/routeTime")
public class RouteTimeController extends BaseController {

	@Autowired
	private RouteTimeService routeTimeService;
	
	@ModelAttribute
	public RouteTime get(@RequestParam(required=false) String id) {
		RouteTime entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = routeTimeService.get(id);
		}
		if (entity == null){
			entity = new RouteTime();
		}
		return entity;
	}
	
	/**
	 * 参团列表页面
	 */
	@RequiresPermissions("meiguotong:product:routeTime:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/meiguotong/product/routeTimeList";
	}
	
		/**
	 * 参团列表数据
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:product:routeTime:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(RouteTime routeTime, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<RouteTime> page = routeTimeService.findPage(new Page<RouteTime>(request, response), routeTime); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑参团表单页面
	 */
	@RequiresPermissions(value={"meiguotong:product:routeTime:view","meiguotong:product:routeTime:add","meiguotong:product:routeTime:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(RouteTime routeTime, Model model) {
		model.addAttribute("routeTime", routeTime);
		if(StringUtils.isBlank(routeTime.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/meiguotong/product/routeTimeForm";
	}

	/**
	 * 保存参团
	 */
	@RequiresPermissions(value={"meiguotong:product:routeTime:add","meiguotong:product:routeTime:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(RouteTime routeTime, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, routeTime)){
			return form(routeTime, model);
		}
		//新增或编辑表单保存
		routeTimeService.save(routeTime);//保存
		addMessage(redirectAttributes, "保存参团成功");
		return "redirect:"+Global.getAdminPath()+"/meiguotong/product/routeTime/?repage";
	}
	
	/**
	 * 删除参团
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:product:routeTime:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(RouteTime routeTime, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		routeTimeService.delete(routeTime);
		j.setMsg("删除参团成功");
		return j;
	}
	
	/**
	 * 批量删除参团
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:product:routeTime:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			routeTimeService.delete(routeTimeService.get(id));
		}
		j.setMsg("删除参团成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:product:routeTime:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(RouteTime routeTime, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "参团"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<RouteTime> page = routeTimeService.findPage(new Page<RouteTime>(request, response, -1), routeTime);
    		new ExportExcel("参团", RouteTime.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出参团记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("meiguotong:product:routeTime:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<RouteTime> list = ei.getDataList(RouteTime.class);
			for (RouteTime routeTime : list){
				try{
					routeTimeService.save(routeTime);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条参团记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条参团记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入参团失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/product/routeTime/?repage";
    }
	
	/**
	 * 下载导入参团数据模板
	 */
	@RequiresPermissions("meiguotong:product:routeTime:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "参团数据导入模板.xlsx";
    		List<RouteTime> list = Lists.newArrayList(); 
    		new ExportExcel("参团数据", RouteTime.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/product/routeTime/?repage";
    }

}