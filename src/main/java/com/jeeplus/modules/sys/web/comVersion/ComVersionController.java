/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.web.comVersion;

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
import com.jeeplus.modules.sys.entity.comVersion.ComVersion;
import com.jeeplus.modules.sys.service.comVersion.ComVersionService;

/**
 * 版本管理Controller
 * @author laiyanxin
 * @version 2018-03-05
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/comVersion")
public class ComVersionController extends BaseController {

	@Autowired
	private ComVersionService comVersionService;
	
	@ModelAttribute
	public ComVersion get(@RequestParam(required=false) String id) {
		ComVersion entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = comVersionService.get(id);
		}
		if (entity == null){
			entity = new ComVersion();
		}
		return entity;
	}
	
	/**
	 * 版本管理列表页面
	 */
	@RequiresPermissions("sys:comVersion:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/sys/comVersion/comVersionList";
	}
	
		/**
	 * 版本管理列表数据
	 */
	@ResponseBody
	@RequiresPermissions("sys:comVersion:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(ComVersion comVersion, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ComVersion> page = comVersionService.findPage(new Page<ComVersion>(request, response), comVersion); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑版本管理表单页面
	 */
	@RequiresPermissions(value={"sys:comVersion:view","sys:comVersion:add","sys:comVersion:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(ComVersion comVersion, Model model) {
		model.addAttribute("comVersion", comVersion);
		return "modules/sys/comVersion/comVersionForm";
	}

	/**
	 * 保存版本管理
	 */
	@ResponseBody
	@RequiresPermissions(value={"sys:comVersion:add","sys:comVersion:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(ComVersion comVersion, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, comVersion)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		comVersionService.save(comVersion);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存版本管理成功");
		return j;
	}
	
	/**
	 * 删除版本管理
	 */
	@ResponseBody
	@RequiresPermissions("sys:comVersion:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(ComVersion comVersion, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		comVersionService.delete(comVersion);
		j.setMsg("删除版本管理成功");
		return j;
	}
	
	/**
	 * 批量删除版本管理
	 */
	@ResponseBody
	@RequiresPermissions("sys:comVersion:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			comVersionService.delete(comVersionService.get(id));
		}
		j.setMsg("删除版本管理成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("sys:comVersion:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(ComVersion comVersion, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "版本管理"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<ComVersion> page = comVersionService.findPage(new Page<ComVersion>(request, response, -1), comVersion);
    		new ExportExcel("版本管理", ComVersion.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出版本管理记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("sys:comVersion:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<ComVersion> list = ei.getDataList(ComVersion.class);
			for (ComVersion comVersion : list){
				try{
					comVersionService.save(comVersion);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条版本管理记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条版本管理记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入版本管理失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/comVersion/?repage";
    }
	
	/**
	 * 下载导入版本管理数据模板
	 */
	@RequiresPermissions("sys:comVersion:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "版本管理数据导入模板.xlsx";
    		List<ComVersion> list = Lists.newArrayList(); 
    		new ExportExcel("版本管理数据", ComVersion.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/comVersion/?repage";
    }

}