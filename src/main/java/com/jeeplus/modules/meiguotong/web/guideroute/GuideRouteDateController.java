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
import com.jeeplus.modules.meiguotong.entity.guideroute.GuideRouteDate;
import com.jeeplus.modules.meiguotong.service.guideroute.GuideRouteDateService;

/**
 * 导游路线日期价格设置Controller
 * @author dong
 * @version 2018-09-13
 */
@Controller
@RequestMapping(value = "${adminPath}/meiguotong/guideroute/guideRouteDate")
public class GuideRouteDateController extends BaseController {

	@Autowired
	private GuideRouteDateService guideRouteDateService;
	
	@ModelAttribute
	public GuideRouteDate get(@RequestParam(required=false) String id) {
		GuideRouteDate entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = guideRouteDateService.get(id);
		}
		if (entity == null){
			entity = new GuideRouteDate();
		}
		return entity;
	}
	
	/**
	 * 导游路线日期价格设置列表页面
	 */
	@RequiresPermissions("meiguotong:guideroute:guideRouteDate:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/meiguotong/guideroute/guideRouteDateList";
	}
	
		/**
	 * 导游路线日期价格设置列表数据
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:guideroute:guideRouteDate:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(GuideRouteDate guideRouteDate, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<GuideRouteDate> page = guideRouteDateService.findPage(new Page<GuideRouteDate>(request, response), guideRouteDate); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑导游路线日期价格设置表单页面
	 */
	@RequiresPermissions(value={"meiguotong:guideroute:guideRouteDate:view","meiguotong:guideroute:guideRouteDate:add","meiguotong:guideroute:guideRouteDate:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(GuideRouteDate guideRouteDate, Model model) {
		model.addAttribute("guideRouteDate", guideRouteDate);
		return "modules/meiguotong/guideroute/guideRouteDateForm";
	}

	/**
	 * 保存导游路线日期价格设置
	 */
	@ResponseBody
	@RequiresPermissions(value={"meiguotong:guideroute:guideRouteDate:add","meiguotong:guideroute:guideRouteDate:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(GuideRouteDate guideRouteDate, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, guideRouteDate)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		guideRouteDateService.save(guideRouteDate);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存导游路线日期价格设置成功");
		return j;
	}
	
	/**
	 * 删除导游路线日期价格设置
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:guideroute:guideRouteDate:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(GuideRouteDate guideRouteDate, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		guideRouteDateService.delete(guideRouteDate);
		j.setMsg("删除导游路线日期价格设置成功");
		return j;
	}
	
	/**
	 * 批量删除导游路线日期价格设置
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:guideroute:guideRouteDate:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			guideRouteDateService.delete(guideRouteDateService.get(id));
		}
		j.setMsg("删除导游路线日期价格设置成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:guideroute:guideRouteDate:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(GuideRouteDate guideRouteDate, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "导游路线日期价格设置"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<GuideRouteDate> page = guideRouteDateService.findPage(new Page<GuideRouteDate>(request, response, -1), guideRouteDate);
    		new ExportExcel("导游路线日期价格设置", GuideRouteDate.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出导游路线日期价格设置记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("meiguotong:guideroute:guideRouteDate:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<GuideRouteDate> list = ei.getDataList(GuideRouteDate.class);
			for (GuideRouteDate guideRouteDate : list){
				try{
					guideRouteDateService.save(guideRouteDate);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条导游路线日期价格设置记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条导游路线日期价格设置记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入导游路线日期价格设置失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/guideroute/guideRouteDate/?repage";
    }
	
	/**
	 * 下载导入导游路线日期价格设置数据模板
	 */
	@RequiresPermissions("meiguotong:guideroute:guideRouteDate:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "导游路线日期价格设置数据导入模板.xlsx";
    		List<GuideRouteDate> list = Lists.newArrayList(); 
    		new ExportExcel("导游路线日期价格设置数据", GuideRouteDate.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/guideroute/guideRouteDate/?repage";
    }

}