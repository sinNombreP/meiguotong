/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.web.comFeedback;

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
import com.jeeplus.modules.sys.entity.comFeedback.ComFeedback;
import com.jeeplus.modules.sys.service.comFeedback.ComFeedbackService;

/**
 * 意见反馈Controller
 * @author laiyanxin
 * @version 2018-03-05
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/comFeedback")
public class ComFeedbackController extends BaseController {

	@Autowired
	private ComFeedbackService comFeedbackService;
	
	@ModelAttribute
	public ComFeedback get(@RequestParam(required=false) String id) {
		ComFeedback entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = comFeedbackService.get(id);
		}
		if (entity == null){
			entity = new ComFeedback();
		}
		return entity;
	}
	
	/**
	 * 意见反馈列表页面
	 */
	@RequiresPermissions("sys:comFeedback:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/sys/comFeedback/comFeedbackList";
	}
	
		/**
	 * 意见反馈列表数据
	 */
	@ResponseBody
	@RequiresPermissions("sys:comFeedback:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(ComFeedback comFeedback, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ComFeedback> page = comFeedbackService.findPage(new Page<ComFeedback>(request, response), comFeedback); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑意见反馈表单页面
	 */
	@RequiresPermissions(value={"sys:comFeedback:view","sys:comFeedback:add","sys:comFeedback:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(ComFeedback comFeedback, Model model) {
		model.addAttribute("comFeedback", comFeedback);
		return "modules/sys/comFeedback/comFeedbackForm";
	}

	/**
	 * 保存意见反馈
	 */
	@ResponseBody
	@RequiresPermissions(value={"sys:comFeedback:add","sys:comFeedback:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(ComFeedback comFeedback, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, comFeedback)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		comFeedbackService.save(comFeedback);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存意见反馈成功");
		return j;
	}
	
	/**
	 * 删除意见反馈
	 */
	@ResponseBody
	@RequiresPermissions("sys:comFeedback:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(ComFeedback comFeedback, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		comFeedbackService.delete(comFeedback);
		j.setMsg("删除意见反馈成功");
		return j;
	}
	
	/**
	 * 批量删除意见反馈
	 */
	@ResponseBody
	@RequiresPermissions("sys:comFeedback:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			comFeedbackService.delete(comFeedbackService.get(id));
		}
		j.setMsg("删除意见反馈成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("sys:comFeedback:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(ComFeedback comFeedback, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "意见反馈"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<ComFeedback> page = comFeedbackService.findPage(new Page<ComFeedback>(request, response, -1), comFeedback);
    		new ExportExcel("意见反馈", ComFeedback.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出意见反馈记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("sys:comFeedback:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<ComFeedback> list = ei.getDataList(ComFeedback.class);
			for (ComFeedback comFeedback : list){
				try{
					comFeedbackService.save(comFeedback);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条意见反馈记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条意见反馈记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入意见反馈失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/comFeedback/?repage";
    }
	
	/**
	 * 下载导入意见反馈数据模板
	 */
	@RequiresPermissions("sys:comFeedback:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "意见反馈数据导入模板.xlsx";
    		List<ComFeedback> list = Lists.newArrayList(); 
    		new ExportExcel("意见反馈数据", ComFeedback.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/comFeedback/?repage";
    }

}