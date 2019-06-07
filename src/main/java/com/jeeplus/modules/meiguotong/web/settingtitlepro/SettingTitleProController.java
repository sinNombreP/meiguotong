/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.web.settingtitlepro;

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
import com.jeeplus.modules.meiguotong.entity.settingtitlepro.SettingTitlePro;
import com.jeeplus.modules.meiguotong.service.settingtitlepro.SettingTitleProService;

/**
 * 产品新增详情标题Controller
 * @author dong
 * @version 2019-04-28
 */
@Controller
@RequestMapping(value = "${adminPath}/meiguotong/settingtitlepro/settingTitlePro")
public class SettingTitleProController extends BaseController {

	@Autowired
	private SettingTitleProService settingTitleProService;
	
	@ModelAttribute
	public SettingTitlePro get(@RequestParam(required=false) String id) {
		SettingTitlePro entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = settingTitleProService.get(id);
		}
		if (entity == null){
			entity = new SettingTitlePro();
		}
		return entity;
	}
	
	/**
	 * 产品新增详情标题列表页面
	 */
	@RequiresPermissions("meiguotong:settingtitlepro:settingTitlePro:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/meiguotong/settingtitlepro/settingTitleProList";
	}
	
		/**
	 * 产品新增详情标题列表数据
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:settingtitlepro:settingTitlePro:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(SettingTitlePro settingTitlePro, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SettingTitlePro> page = settingTitleProService.findPage(new Page<SettingTitlePro>(request, response), settingTitlePro); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑产品新增详情标题表单页面
	 */
	@RequiresPermissions(value={"meiguotong:settingtitlepro:settingTitlePro:view","meiguotong:settingtitlepro:settingTitlePro:add","meiguotong:settingtitlepro:settingTitlePro:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(SettingTitlePro settingTitlePro, Model model) {
		model.addAttribute("settingTitlePro", settingTitlePro);
		if(StringUtils.isBlank(settingTitlePro.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/meiguotong/settingtitlepro/settingTitleProForm";
	}

	/**
	 * 保存产品新增详情标题
	 */
	@RequiresPermissions(value={"meiguotong:settingtitlepro:settingTitlePro:add","meiguotong:settingtitlepro:settingTitlePro:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(SettingTitlePro settingTitlePro, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, settingTitlePro)){
			return form(settingTitlePro, model);
		}
		//新增或编辑表单保存
		settingTitleProService.save(settingTitlePro);//保存
		addMessage(redirectAttributes, "保存产品新增详情标题成功");
		return "redirect:"+Global.getAdminPath()+"/meiguotong/settingtitlepro/settingTitlePro/?repage";
	}
	
	/**
	 * 删除产品新增详情标题
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:settingtitlepro:settingTitlePro:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(SettingTitlePro settingTitlePro, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		settingTitleProService.delete(settingTitlePro);
		j.setMsg("删除产品新增详情标题成功");
		return j;
	}
	
	/**
	 * 批量删除产品新增详情标题
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:settingtitlepro:settingTitlePro:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			settingTitleProService.delete(settingTitleProService.get(id));
		}
		j.setMsg("删除产品新增详情标题成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:settingtitlepro:settingTitlePro:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(SettingTitlePro settingTitlePro, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "产品新增详情标题"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<SettingTitlePro> page = settingTitleProService.findPage(new Page<SettingTitlePro>(request, response, -1), settingTitlePro);
    		new ExportExcel("产品新增详情标题", SettingTitlePro.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出产品新增详情标题记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("meiguotong:settingtitlepro:settingTitlePro:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<SettingTitlePro> list = ei.getDataList(SettingTitlePro.class);
			for (SettingTitlePro settingTitlePro : list){
				try{
					settingTitleProService.save(settingTitlePro);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条产品新增详情标题记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条产品新增详情标题记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入产品新增详情标题失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/settingtitlepro/settingTitlePro/?repage";
    }
	
	/**
	 * 下载导入产品新增详情标题数据模板
	 */
	@RequiresPermissions("meiguotong:settingtitlepro:settingTitlePro:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "产品新增详情标题数据导入模板.xlsx";
    		List<SettingTitlePro> list = Lists.newArrayList(); 
    		new ExportExcel("产品新增详情标题数据", SettingTitlePro.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/settingtitlepro/settingTitlePro/?repage";
    }

}