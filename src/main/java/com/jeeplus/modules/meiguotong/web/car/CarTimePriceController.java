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
import com.jeeplus.modules.meiguotong.entity.car.CarTimePrice;
import com.jeeplus.modules.meiguotong.service.car.CarTimePriceService;

/**
 * 汽车时间表Controller
 * @author psz
 * @version 2018-08-31
 */
@Controller
@RequestMapping(value = "${adminPath}/meiguotong/car/carTimePrice")
public class CarTimePriceController extends BaseController {

	@Autowired
	private CarTimePriceService carTimePriceService;
	
	@ModelAttribute
	public CarTimePrice get(@RequestParam(required=false) String id) {
		CarTimePrice entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = carTimePriceService.get(id);
		}
		if (entity == null){
			entity = new CarTimePrice();
		}
		return entity;
	}
	
	/**
	 * 汽车时间表列表页面
	 */
	@RequiresPermissions("meiguotong:car:carTimePrice:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/meiguotong/car/carTimePriceList";
	}
	
		/**
	 * 汽车时间表列表数据
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:car:carTimePrice:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(CarTimePrice carTimePrice, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CarTimePrice> page = carTimePriceService.findPage(new Page<CarTimePrice>(request, response), carTimePrice); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑汽车时间表表单页面
	 */
	@RequiresPermissions(value={"meiguotong:car:carTimePrice:view","meiguotong:car:carTimePrice:add","meiguotong:car:carTimePrice:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(CarTimePrice carTimePrice, Model model) {
		model.addAttribute("carTimePrice", carTimePrice);
		if(StringUtils.isBlank(carTimePrice.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/meiguotong/car/carTimePriceForm";
	}

	/**
	 * 保存汽车时间表
	 */
	@RequiresPermissions(value={"meiguotong:car:carTimePrice:add","meiguotong:car:carTimePrice:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(CarTimePrice carTimePrice, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, carTimePrice)){
			return form(carTimePrice, model);
		}
		//新增或编辑表单保存
		carTimePriceService.save(carTimePrice);//保存
		addMessage(redirectAttributes, "保存汽车时间表成功");
		return "redirect:"+Global.getAdminPath()+"/meiguotong/car/carTimePrice/?repage";
	}
	
	/**
	 * 删除汽车时间表
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:car:carTimePrice:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(CarTimePrice carTimePrice, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		carTimePriceService.delete(carTimePrice);
		j.setMsg("删除汽车时间表成功");
		return j;
	}
	
	/**
	 * 批量删除汽车时间表
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:car:carTimePrice:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			carTimePriceService.delete(carTimePriceService.get(id));
		}
		j.setMsg("删除汽车时间表成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:car:carTimePrice:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(CarTimePrice carTimePrice, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "汽车时间表"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<CarTimePrice> page = carTimePriceService.findPage(new Page<CarTimePrice>(request, response, -1), carTimePrice);
    		new ExportExcel("汽车时间表", CarTimePrice.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出汽车时间表记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("meiguotong:car:carTimePrice:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<CarTimePrice> list = ei.getDataList(CarTimePrice.class);
			for (CarTimePrice carTimePrice : list){
				try{
					carTimePriceService.save(carTimePrice);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条汽车时间表记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条汽车时间表记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入汽车时间表失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/car/carTimePrice/?repage";
    }
	
	/**
	 * 下载导入汽车时间表数据模板
	 */
	@RequiresPermissions("meiguotong:car:carTimePrice:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "汽车时间表数据导入模板.xlsx";
    		List<CarTimePrice> list = Lists.newArrayList(); 
    		new ExportExcel("汽车时间表数据", CarTimePrice.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/car/carTimePrice/?repage";
    }

}