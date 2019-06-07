/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.web.compush;

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
import com.jeeplus.common.config.Global;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.modules.meiguotong.entity.compush.ComPush;
import com.jeeplus.modules.meiguotong.service.compush.ComPushService;

/**
 * 推送管理Controller
 * @author dong
 * @version 2018-09-17
 */
@Controller
@RequestMapping(value = "${adminPath}/meiguotong/compush/comPush")
public class ComPushController extends BaseController {

	@Autowired
	private ComPushService comPushService;
	
	@ModelAttribute
	public ComPush get(@RequestParam(required=false) String id) {
		ComPush entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = comPushService.get(id);
		}
		if (entity == null){
			entity = new ComPush();
		}
		return entity;
	}
	
	/**
	 * 推送管理列表页面
	 */
	@RequiresPermissions("meiguotong:compush:comPush:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/meiguotong/compush/comPushList";
	}
	
		/**
	 * 推送管理列表数据
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:compush:comPush:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(ComPush comPush, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ComPush> page = comPushService.findPage(new Page<ComPush>(request, response), comPush); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑推送管理表单页面
	 */
	@RequiresPermissions(value={"meiguotong:compush:comPush:view","meiguotong:compush:comPush:add","meiguotong:compush:comPush:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(ComPush comPush, Model model) {
		model.addAttribute("comPush", comPush);
		if (comPush.getId()!=null || comPush.getId()!="") {
			List<ComPush> conpushpeople=comPushService.findComPushPeople(comPush.getId());
			model.addAttribute("comPushPeople",conpushpeople);
		}
		if(StringUtils.isBlank(comPush.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/meiguotong/compush/comPushForm";
	}

	/**
	 * 查询用户
	 * */
	@ResponseBody
	@RequestMapping(value = "member")
	public AjaxJson seach(ComPush comPush, Model model){
		
		List<ComPush> comPushs= comPushService.findnumber(comPush);
		AjaxJson j = new AjaxJson();
		j.getBody().put("list", comPushs);
		return j;
	}
	
	/**
	 * 保存推送管理
	 */
	@RequiresPermissions(value={"meiguotong:compush:comPush:add","meiguotong:compush:comPush:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(ComPush comPush, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, comPush)){
			return form(comPush, model);
		}
		if(StringUtils.isBlank(comPush.getContent())){
			addMessage(redirectAttributes, "推送内容为空，请重新添加！");
			return "redirect:"+Global.getAdminPath()+"/meiguotong/compush/comPush/form";
		}
		if (comPush.getSend()==1) {
			comPush.setSendType("1,2,3");
		}
		//新增或编辑表单保存
		comPushService.save(comPush);//保存
		addMessage(redirectAttributes, "保存推送管理成功");
		return "redirect:"+Global.getAdminPath()+"/meiguotong/compush/comPush/?repage";
	}
	
	/**
	 * 删除推送管理
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:compush:comPush:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(ComPush comPush, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		comPushService.delete(comPush);
		j.setMsg("删除推送管理成功");
		return j;
	}
	
	/**
	 * 批量删除推送管理
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:compush:comPush:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			comPushService.delete(comPushService.get(id));
		}
		j.setMsg("删除推送管理成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:compush:comPush:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(ComPush comPush, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "推送管理"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<ComPush> page = comPushService.findPage(new Page<ComPush>(request, response, -1), comPush);
    		new ExportExcel("推送管理", ComPush.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出推送管理记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("meiguotong:compush:comPush:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<ComPush> list = ei.getDataList(ComPush.class);
			for (ComPush comPush : list){
				try{
					comPushService.save(comPush);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条推送管理记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条推送管理记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入推送管理失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/compush/comPush/?repage";
    }
	
	/**
	 * 下载导入推送管理数据模板
	 */
	@RequiresPermissions("meiguotong:compush:comPush:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "推送管理数据导入模板.xlsx";
    		List<ComPush> list = Lists.newArrayList(); 
    		new ExportExcel("推送管理数据", ComPush.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/compush/comPush/?repage";
    }
	@RequestMapping(value = "inform")
	public String inform() {
		return "modules/meiguotong/compush/comPushList_inform";
	}
}