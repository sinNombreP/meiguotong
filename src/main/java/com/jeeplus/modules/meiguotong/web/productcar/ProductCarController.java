/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.web.productcar;

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
import com.jeeplus.modules.meiguotong.entity.productcar.ProductCar;
import com.jeeplus.modules.meiguotong.service.productcar.ProductCarService;

/**
 * 购物车Controller
 * @author dong
 * @version 2018-09-17
 */
@Controller
@RequestMapping(value = "${adminPath}/meiguotong/productcar/productCar")
public class ProductCarController extends BaseController {

	@Autowired
	private ProductCarService productCarService;
	
	@ModelAttribute
	public ProductCar get(@RequestParam(required=false) String id) {
		ProductCar entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = productCarService.get(id);
		}
		if (entity == null){
			entity = new ProductCar();
		}
		return entity;
	}
	
	/**
	 * 购物车列表页面
	 */
	@RequiresPermissions("meiguotong:productcar:productCar:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/meiguotong/productcar/productCarList";
	}
	
		/**
	 * 购物车列表数据
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:productcar:productCar:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(ProductCar productCar, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ProductCar> page = productCarService.findPage(new Page<ProductCar>(request, response), productCar); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑购物车表单页面
	 */
	@RequiresPermissions(value={"meiguotong:productcar:productCar:view","meiguotong:productcar:productCar:add","meiguotong:productcar:productCar:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(ProductCar productCar, Model model) {
		model.addAttribute("productCar", productCar);
		return "modules/meiguotong/productcar/productCarForm";
	}

	/**
	 * 保存购物车
	 */
	@ResponseBody
	@RequiresPermissions(value={"meiguotong:productcar:productCar:add","meiguotong:productcar:productCar:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(ProductCar productCar, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, productCar)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		productCarService.save(productCar);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存购物车成功");
		return j;
	}
	
	/**
	 * 删除购物车
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:productcar:productCar:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(ProductCar productCar, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		productCarService.delete(productCar);
		j.setMsg("删除购物车成功");
		return j;
	}
	
	/**
	 * 批量删除购物车
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:productcar:productCar:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			productCarService.delete(productCarService.get(id));
		}
		j.setMsg("删除购物车成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:productcar:productCar:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(ProductCar productCar, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "购物车"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<ProductCar> page = productCarService.findPage(new Page<ProductCar>(request, response, -1), productCar);
    		new ExportExcel("购物车", ProductCar.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出购物车记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("meiguotong:productcar:productCar:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<ProductCar> list = ei.getDataList(ProductCar.class);
			for (ProductCar productCar : list){
				try{
					productCarService.save(productCar);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条购物车记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条购物车记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入购物车失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/productcar/productCar/?repage";
    }
	
	/**
	 * 下载导入购物车数据模板
	 */
	@RequiresPermissions("meiguotong:productcar:productCar:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "购物车数据导入模板.xlsx";
    		List<ProductCar> list = Lists.newArrayList(); 
    		new ExportExcel("购物车数据", ProductCar.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/productcar/productCar/?repage";
    }

}