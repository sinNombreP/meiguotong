/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.web.linertrip;

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
import com.jeeplus.modules.meiguotong.entity.linertrip.LinerTrip;
import com.jeeplus.modules.meiguotong.service.linertrip.LinerTripService;

/**
 * 行程表Controller
 * @author cdq
 * @version 2018-08-14
 */
@Controller
@RequestMapping(value = "${adminPath}/meiguotong/linertrip/linerTrip")
public class LinerTripController extends BaseController {

	@Autowired
	private LinerTripService linerTripService;
	
	@ModelAttribute
	public LinerTrip get(@RequestParam(required=false) String id) {
		LinerTrip entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = linerTripService.get(id);
		}
		if (entity == null){
			entity = new LinerTrip();
		}
		return entity;
	}
	
	/**
	 * 行程表列表页面
	 */
	@RequiresPermissions("meiguotong:linertrip:linerTrip:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/meiguotong/linertrip/linerTripList";
	}
	
		/**
	 * 行程表列表数据
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:linertrip:linerTrip:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(LinerTrip linerTrip, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<LinerTrip> page = linerTripService.findPage(new Page<LinerTrip>(request, response), linerTrip); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑行程表表单页面
	 */
	@RequiresPermissions(value={"meiguotong:linertrip:linerTrip:view","meiguotong:linertrip:linerTrip:add","meiguotong:linertrip:linerTrip:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(LinerTrip linerTrip, Model model) {
		model.addAttribute("linerTrip", linerTrip);
		if(StringUtils.isBlank(linerTrip.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/meiguotong/linertrip/linerTripForm";
	}

	/**
	 * 保存行程表
	 */
	@RequiresPermissions(value={"meiguotong:linertrip:linerTrip:add","meiguotong:linertrip:linerTrip:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(LinerTrip linerTrip, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, linerTrip)){
			return form(linerTrip, model);
		}
		//新增或编辑表单保存
		linerTripService.save(linerTrip);//保存
		addMessage(redirectAttributes, "保存行程表成功");
		return "redirect:"+Global.getAdminPath()+"/meiguotong/linertrip/linerTrip/?repage";
	}
	
	/**
	 * 删除行程表
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:linertrip:linerTrip:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(LinerTrip linerTrip, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		linerTripService.delete(linerTrip);
		j.setMsg("删除行程表成功");
		return j;
	}
	
	/**
	 * 批量删除行程表
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:linertrip:linerTrip:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			linerTripService.delete(linerTripService.get(id));
		}
		j.setMsg("删除行程表成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:linertrip:linerTrip:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(LinerTrip linerTrip, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "行程表"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<LinerTrip> page = linerTripService.findPage(new Page<LinerTrip>(request, response, -1), linerTrip);
    		new ExportExcel("行程表", LinerTrip.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出行程表记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("meiguotong:linertrip:linerTrip:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<LinerTrip> list = ei.getDataList(LinerTrip.class);
			for (LinerTrip linerTrip : list){
				try{
					linerTripService.save(linerTrip);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条行程表记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条行程表记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入行程表失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/linertrip/linerTrip/?repage";
    }
	
	/**
	 * 下载导入行程表数据模板
	 */
	@RequiresPermissions("meiguotong:linertrip:linerTrip:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "行程表数据导入模板.xlsx";
    		List<LinerTrip> list = Lists.newArrayList(); 
    		new ExportExcel("行程表数据", LinerTrip.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/linertrip/linerTrip/?repage";
    }

}