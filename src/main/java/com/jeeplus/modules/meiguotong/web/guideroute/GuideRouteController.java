/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.web.guideroute;

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
import com.jeeplus.modules.meiguotong.entity.guideroute.GuideRoute;
import com.jeeplus.modules.meiguotong.service.guideroute.GuideRouteService;

/**
 * 导游路线表Controller
 * @author cdq
 * @version 2018-09-04
 */
@Controller
@RequestMapping(value = "${adminPath}/meiguotong/guideroute/guideRoute")
public class GuideRouteController extends BaseController {

	@Autowired
	private GuideRouteService guideRouteService;
	
	@ModelAttribute
	public GuideRoute get(@RequestParam(required=false) String id) {
		GuideRoute entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = guideRouteService.get(id);
		}
		if (entity == null){
			entity = new GuideRoute();
		}
		return entity;
	}
	
	/**
	 * 导游路线表列表页面
	 */
	@RequiresPermissions("meiguotong:guideroute:guideRoute:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/meiguotong/guideroute/guideRouteList";
	}
	
		/**
	 * 导游路线表列表数据
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:guideroute:guideRoute:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(GuideRoute guideRoute, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<GuideRoute> page = guideRouteService.findPage(new Page<GuideRoute>(request, response), guideRoute); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑导游路线表表单页面
	 */
	@RequiresPermissions(value={"meiguotong:guideroute:guideRoute:view","meiguotong:guideroute:guideRoute:add","meiguotong:guideroute:guideRoute:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(GuideRoute guideRoute, Model model) {
		model.addAttribute("guideRoute", guideRoute);
		if(StringUtils.isBlank(guideRoute.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/meiguotong/guideroute/guideRouteForm";
	}

	/**
	 * 保存导游路线表
	 */
	@RequiresPermissions(value={"meiguotong:guideroute:guideRoute:add","meiguotong:guideroute:guideRoute:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(GuideRoute guideRoute, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, guideRoute)){
			return form(guideRoute, model);
		}
		//新增或编辑表单保存
		guideRouteService.save(guideRoute);//保存
		addMessage(redirectAttributes, "保存导游路线表成功");
		return "redirect:"+Global.getAdminPath()+"/meiguotong/guideroute/guideRoute/?repage";
	}
	
	/**
	 * 删除导游路线表
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:guideroute:guideRoute:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(GuideRoute guideRoute, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		guideRouteService.delete(guideRoute);
		j.setMsg("删除导游路线表成功");
		return j;
	}
	
	/**
	 * 批量删除导游路线表
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:guideroute:guideRoute:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			guideRouteService.delete(guideRouteService.get(id));
		}
		j.setMsg("删除导游路线表成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:guideroute:guideRoute:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(GuideRoute guideRoute, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "导游路线表"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<GuideRoute> page = guideRouteService.findPage(new Page<GuideRoute>(request, response, -1), guideRoute);
    		new ExportExcel("导游路线表", GuideRoute.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出导游路线表记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("meiguotong:guideroute:guideRoute:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<GuideRoute> list = ei.getDataList(GuideRoute.class);
			for (GuideRoute guideRoute : list){
				try{
					guideRouteService.save(guideRoute);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条导游路线表记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条导游路线表记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入导游路线表失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/guideroute/guideRoute/?repage";
    }
	
	/**
	 * 下载导入导游路线表数据模板
	 */
	@RequiresPermissions("meiguotong:guideroute:guideRoute:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "导游路线表数据导入模板.xlsx";
    		List<GuideRoute> list = Lists.newArrayList(); 
    		new ExportExcel("导游路线表数据", GuideRoute.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/guideroute/guideRoute/?repage";
    }

}