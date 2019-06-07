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
import com.jeeplus.modules.meiguotong.entity.guideroute.GuideRouteTime;
import com.jeeplus.modules.meiguotong.service.guideroute.GuideRouteTimeService;

/**
 * 导游路线日期设置Controller
 * @author dong
 * @version 2018-09-13
 */
@Controller
@RequestMapping(value = "${adminPath}/meiguotong/guideroute/guideRouteTime")
public class GuideRouteTimeController extends BaseController {

	@Autowired
	private GuideRouteTimeService guideRouteTimeService;
	
	@ModelAttribute
	public GuideRouteTime get(@RequestParam(required=false) String id) {
		GuideRouteTime entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = guideRouteTimeService.get(id);
		}
		if (entity == null){
			entity = new GuideRouteTime();
		}
		return entity;
	}
	
	/**
	 * 导游路线日期设置列表页面
	 */
	@RequiresPermissions("meiguotong:guideroute:guideRouteTime:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/meiguotong/guideroute/guideRouteTimeList";
	}
	
		/**
	 * 导游路线日期设置列表数据
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:guideroute:guideRouteTime:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(GuideRouteTime guideRouteTime, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<GuideRouteTime> page = guideRouteTimeService.findPage(new Page<GuideRouteTime>(request, response), guideRouteTime); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑导游路线日期设置表单页面
	 */
	@RequiresPermissions(value={"meiguotong:guideroute:guideRouteTime:view","meiguotong:guideroute:guideRouteTime:add","meiguotong:guideroute:guideRouteTime:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(GuideRouteTime guideRouteTime, Model model) {
		model.addAttribute("guideRouteTime", guideRouteTime);
		return "modules/meiguotong/guideroute/guideRouteTimeForm";
	}

	/**
	 * 保存导游路线日期设置
	 */
	@ResponseBody
	@RequiresPermissions(value={"meiguotong:guideroute:guideRouteTime:add","meiguotong:guideroute:guideRouteTime:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(GuideRouteTime guideRouteTime, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, guideRouteTime)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		guideRouteTimeService.save(guideRouteTime);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存导游路线日期设置成功");
		return j;
	}
	
	/**
	 * 删除导游路线日期设置
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:guideroute:guideRouteTime:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(GuideRouteTime guideRouteTime, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		guideRouteTimeService.delete(guideRouteTime);
		j.setMsg("删除导游路线日期设置成功");
		return j;
	}
	
	/**
	 * 批量删除导游路线日期设置
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:guideroute:guideRouteTime:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			guideRouteTimeService.delete(guideRouteTimeService.get(id));
		}
		j.setMsg("删除导游路线日期设置成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:guideroute:guideRouteTime:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(GuideRouteTime guideRouteTime, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "导游路线日期设置"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<GuideRouteTime> page = guideRouteTimeService.findPage(new Page<GuideRouteTime>(request, response, -1), guideRouteTime);
    		new ExportExcel("导游路线日期设置", GuideRouteTime.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出导游路线日期设置记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("meiguotong:guideroute:guideRouteTime:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<GuideRouteTime> list = ei.getDataList(GuideRouteTime.class);
			for (GuideRouteTime guideRouteTime : list){
				try{
					guideRouteTimeService.save(guideRouteTime);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条导游路线日期设置记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条导游路线日期设置记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入导游路线日期设置失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/guideroute/guideRouteTime/?repage";
    }
	
	/**
	 * 下载导入导游路线日期设置数据模板
	 */
	@RequiresPermissions("meiguotong:guideroute:guideRouteTime:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "导游路线日期设置数据导入模板.xlsx";
    		List<GuideRouteTime> list = Lists.newArrayList(); 
    		new ExportExcel("导游路线日期设置数据", GuideRouteTime.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/guideroute/guideRouteTime/?repage";
    }

}