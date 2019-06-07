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
import com.jeeplus.modules.meiguotong.entity.product.RouteContent;
import com.jeeplus.modules.meiguotong.service.product.RouteContentService;

/**
 * 参团内容Controller
 * @author psz
 * @version 2018-08-14
 */
@Controller
@RequestMapping(value = "${adminPath}/meiguotong/product/routeContent")
public class RouteContentController extends BaseController {

	@Autowired
	private RouteContentService routeContentService;
	
	@ModelAttribute
	public RouteContent get(@RequestParam(required=false) String id) {
		RouteContent entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = routeContentService.get(id);
		}
		if (entity == null){
			entity = new RouteContent();
		}
		return entity;
	}
	
	/**
	 * 参团内容列表页面
	 */
	@RequiresPermissions("meiguotong:product:routeContent:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/meiguotong/product/routeContentList";
	}
	
		/**
	 * 参团内容列表数据
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:product:routeContent:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(RouteContent routeContent, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<RouteContent> page = routeContentService.findPage(new Page<RouteContent>(request, response), routeContent); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑参团内容表单页面
	 */
	@RequiresPermissions(value={"meiguotong:product:routeContent:view","meiguotong:product:routeContent:add","meiguotong:product:routeContent:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(RouteContent routeContent, Model model) {
		model.addAttribute("routeContent", routeContent);
		if(StringUtils.isBlank(routeContent.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/meiguotong/product/routeContentForm";
	}

	/**
	 * 保存参团内容
	 */
	@RequiresPermissions(value={"meiguotong:product:routeContent:add","meiguotong:product:routeContent:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(RouteContent routeContent, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, routeContent)){
			return form(routeContent, model);
		}
		//新增或编辑表单保存
		routeContentService.save(routeContent);//保存
		addMessage(redirectAttributes, "保存参团内容成功");
		return "redirect:"+Global.getAdminPath()+"/meiguotong/product/routeContent/?repage";
	}
	
	/**
	 * 删除参团内容
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:product:routeContent:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(RouteContent routeContent, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		routeContentService.delete(routeContent);
		j.setMsg("删除参团内容成功");
		return j;
	}
	
	/**
	 * 批量删除参团内容
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:product:routeContent:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			routeContentService.delete(routeContentService.get(id));
		}
		j.setMsg("删除参团内容成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:product:routeContent:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(RouteContent routeContent, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "参团内容"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<RouteContent> page = routeContentService.findPage(new Page<RouteContent>(request, response, -1), routeContent);
    		new ExportExcel("参团内容", RouteContent.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出参团内容记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("meiguotong:product:routeContent:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<RouteContent> list = ei.getDataList(RouteContent.class);
			for (RouteContent routeContent : list){
				try{
					routeContentService.save(routeContent);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条参团内容记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条参团内容记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入参团内容失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/product/routeContent/?repage";
    }
	
	/**
	 * 下载导入参团内容数据模板
	 */
	@RequiresPermissions("meiguotong:product:routeContent:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "参团内容数据导入模板.xlsx";
    		List<RouteContent> list = Lists.newArrayList(); 
    		new ExportExcel("参团内容数据", RouteContent.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/product/routeContent/?repage";
    }

}