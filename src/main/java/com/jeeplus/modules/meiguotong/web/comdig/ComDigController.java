/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.web.comdig;

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
import com.jeeplus.modules.meiguotong.entity.comdig.ComDig;
import com.jeeplus.modules.meiguotong.service.comdig.ComDigService;

/**
 * 点赞Controller
 * @author dong
 * @version 2018-09-27
 */
@Controller
@RequestMapping(value = "${adminPath}/meiguotong/comdig/comDig")
public class ComDigController extends BaseController {

	@Autowired
	private ComDigService comDigService;
	
	@ModelAttribute
	public ComDig get(@RequestParam(required=false) String id) {
		ComDig entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = comDigService.get(id);
		}
		if (entity == null){
			entity = new ComDig();
		}
		return entity;
	}
	
	/**
	 * 点赞列表页面
	 */
	@RequiresPermissions("meiguotong:comdig:comDig:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/meiguotong/comdig/comDigList";
	}
	
		/**
	 * 点赞列表数据
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:comdig:comDig:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(ComDig comDig, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ComDig> page = comDigService.findPage(new Page<ComDig>(request, response), comDig); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑点赞表单页面
	 */
	@RequiresPermissions(value={"meiguotong:comdig:comDig:view","meiguotong:comdig:comDig:add","meiguotong:comdig:comDig:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(ComDig comDig, Model model) {
		model.addAttribute("comDig", comDig);
		return "modules/meiguotong/comdig/comDigForm";
	}

	/**
	 * 保存点赞
	 */
	@ResponseBody
	@RequiresPermissions(value={"meiguotong:comdig:comDig:add","meiguotong:comdig:comDig:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(ComDig comDig, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, comDig)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		comDigService.save(comDig);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存点赞成功");
		return j;
	}
	
	/**
	 * 删除点赞
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:comdig:comDig:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(ComDig comDig, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		comDigService.delete(comDig);
		j.setMsg("删除点赞成功");
		return j;
	}
	
	/**
	 * 批量删除点赞
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:comdig:comDig:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			comDigService.delete(comDigService.get(id));
		}
		j.setMsg("删除点赞成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:comdig:comDig:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(ComDig comDig, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "点赞"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<ComDig> page = comDigService.findPage(new Page<ComDig>(request, response, -1), comDig);
    		new ExportExcel("点赞", ComDig.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出点赞记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("meiguotong:comdig:comDig:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<ComDig> list = ei.getDataList(ComDig.class);
			for (ComDig comDig : list){
				try{
					comDigService.save(comDig);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条点赞记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条点赞记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入点赞失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/comdig/comDig/?repage";
    }
	
	/**
	 * 下载导入点赞数据模板
	 */
	@RequiresPermissions("meiguotong:comdig:comDig:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "点赞数据导入模板.xlsx";
    		List<ComDig> list = Lists.newArrayList(); 
    		new ExportExcel("点赞数据", ComDig.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/comdig/comDig/?repage";
    }

}