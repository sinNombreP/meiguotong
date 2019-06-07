/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.web.car;

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
import com.jeeplus.modules.meiguotong.entity.car.CarBusiness;
import com.jeeplus.modules.meiguotong.service.car.CarBusinessService;

/**
 * 汽车业务表Controller
 * @author psz
 * @version 2018-08-31
 */
@Controller
@RequestMapping(value = "${adminPath}/meiguotong/car/carBusiness")
public class CarBusinessController extends BaseController {

	@Autowired
	private CarBusinessService carBusinessService;
	
	@ModelAttribute
	public CarBusiness get(@RequestParam(required=false) String id) {
		CarBusiness entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = carBusinessService.get(id);
		}
		if (entity == null){
			entity = new CarBusiness();
		}
		return entity;
	}
	
	/**
	 * 汽车业务表列表页面
	 */
	@RequiresPermissions("meiguotong:car:carBusiness:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/meiguotong/car/carBusinessList";
	}
	
		/**
	 * 汽车业务表列表数据
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:car:carBusiness:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(CarBusiness carBusiness, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CarBusiness> page = carBusinessService.findPage(new Page<CarBusiness>(request, response), carBusiness); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑汽车业务表表单页面
	 */
	@RequiresPermissions(value={"meiguotong:car:carBusiness:view","meiguotong:car:carBusiness:add","meiguotong:car:carBusiness:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(CarBusiness carBusiness, Model model) {
		model.addAttribute("carBusiness", carBusiness);
		if(StringUtils.isBlank(carBusiness.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/meiguotong/car/carBusinessForm";
	}

	/**
	 * 保存汽车业务表
	 */
	@RequiresPermissions(value={"meiguotong:car:carBusiness:add","meiguotong:car:carBusiness:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(CarBusiness carBusiness, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, carBusiness)){
			return form(carBusiness, model);
		}
		//新增或编辑表单保存
		carBusinessService.save(carBusiness);//保存
		addMessage(redirectAttributes, "保存汽车业务表成功");
		return "redirect:"+Global.getAdminPath()+"/meiguotong/car/carBusiness/?repage";
	}
	
	/**
	 * 删除汽车业务表
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:car:carBusiness:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(CarBusiness carBusiness, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		carBusinessService.delete(carBusiness);
		j.setMsg("删除汽车业务表成功");
		return j;
	}
	
	/**
	 * 批量删除汽车业务表
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:car:carBusiness:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			carBusinessService.delete(carBusinessService.get(id));
		}
		j.setMsg("删除汽车业务表成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:car:carBusiness:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(CarBusiness carBusiness, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "汽车业务表"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<CarBusiness> page = carBusinessService.findPage(new Page<CarBusiness>(request, response, -1), carBusiness);
    		new ExportExcel("汽车业务表", CarBusiness.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出汽车业务表记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("meiguotong:car:carBusiness:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<CarBusiness> list = ei.getDataList(CarBusiness.class);
			for (CarBusiness carBusiness : list){
				try{
					carBusinessService.save(carBusiness);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条汽车业务表记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条汽车业务表记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入汽车业务表失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/car/carBusiness/?repage";
    }
	
	/**
	 * 下载导入汽车业务表数据模板
	 */
	@RequiresPermissions("meiguotong:car:carBusiness:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "汽车业务表数据导入模板.xlsx";
    		List<CarBusiness> list = Lists.newArrayList(); 
    		new ExportExcel("汽车业务表数据", CarBusiness.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/car/carBusiness/?repage";
    }

}