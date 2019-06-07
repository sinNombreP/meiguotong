/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.web.guide;

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
import com.jeeplus.modules.meiguotong.entity.guide.GuideDate;
import com.jeeplus.modules.meiguotong.service.guide.GuideDateService;

/**
 * 导游日期价格设置Controller
 * @author dong
 * @version 2018-09-12
 */
@Controller
@RequestMapping(value = "${adminPath}/meiguotong/guide/guideDate")
public class GuideDateController extends BaseController {

	@Autowired
	private GuideDateService guideDateService;
	
	@ModelAttribute
	public GuideDate get(@RequestParam(required=false) String id) {
		GuideDate entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = guideDateService.get(id);
		}
		if (entity == null){
			entity = new GuideDate();
		}
		return entity;
	}
	
	/**
	 * 导游日期价格设置列表页面
	 */
	@RequiresPermissions("meiguotong:guide:guideDate:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/meiguotong/guide/guideDateList";
	}
	
		/**
	 * 导游日期价格设置列表数据
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:guide:guideDate:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(GuideDate guideDate, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<GuideDate> page = guideDateService.findPage(new Page<GuideDate>(request, response), guideDate); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑导游日期价格设置表单页面
	 */
	@RequiresPermissions(value={"meiguotong:guide:guideDate:view","meiguotong:guide:guideDate:add","meiguotong:guide:guideDate:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(GuideDate guideDate, Model model) {
		model.addAttribute("guideDate", guideDate);
		return "modules/meiguotong/guide/guideDateForm";
	}

	/**
	 * 保存导游日期价格设置
	 */
	@ResponseBody
	@RequiresPermissions(value={"meiguotong:guide:guideDate:add","meiguotong:guide:guideDate:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(GuideDate guideDate, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, guideDate)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		guideDateService.save(guideDate);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存导游日期价格设置成功");
		return j;
	}
	
	/**
	 * 删除导游日期价格设置
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:guide:guideDate:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(GuideDate guideDate, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		guideDateService.delete(guideDate);
		j.setMsg("删除导游日期价格设置成功");
		return j;
	}
	
	/**
	 * 批量删除导游日期价格设置
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:guide:guideDate:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			guideDateService.delete(guideDateService.get(id));
		}
		j.setMsg("删除导游日期价格设置成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:guide:guideDate:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(GuideDate guideDate, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "导游日期价格设置"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<GuideDate> page = guideDateService.findPage(new Page<GuideDate>(request, response, -1), guideDate);
    		new ExportExcel("导游日期价格设置", GuideDate.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出导游日期价格设置记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("meiguotong:guide:guideDate:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<GuideDate> list = ei.getDataList(GuideDate.class);
			for (GuideDate guideDate : list){
				try{
					guideDateService.save(guideDate);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条导游日期价格设置记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条导游日期价格设置记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入导游日期价格设置失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/guide/guideDate/?repage";
    }
	
	/**
	 * 下载导入导游日期价格设置数据模板
	 */
	@RequiresPermissions("meiguotong:guide:guideDate:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "导游日期价格设置数据导入模板.xlsx";
    		List<GuideDate> list = Lists.newArrayList(); 
    		new ExportExcel("导游日期价格设置数据", GuideDate.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/guide/guideDate/?repage";
    }

}