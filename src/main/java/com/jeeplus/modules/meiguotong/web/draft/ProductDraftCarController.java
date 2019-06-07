/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.web.draft;

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
import com.jeeplus.modules.meiguotong.entity.draft.ProductDraftCar;
import com.jeeplus.modules.meiguotong.service.draft.ProductDraftCarService;

/**
 * 草稿租车Controller
 * @author dong
 * @version 2018-09-14
 */
@Controller
@RequestMapping(value = "${adminPath}/meiguotong/draft/productDraftCar")
public class ProductDraftCarController extends BaseController {

	@Autowired
	private ProductDraftCarService productDraftCarService;
	
	@ModelAttribute
	public ProductDraftCar get(@RequestParam(required=false) String id) {
		ProductDraftCar entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = productDraftCarService.get(id);
		}
		if (entity == null){
			entity = new ProductDraftCar();
		}
		return entity;
	}
	
	/**
	 * 草稿租车列表页面
	 */
	@RequiresPermissions("meiguotong:draft:productDraftCar:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/meiguotong/draft/productDraftCarList";
	}
	
		/**
	 * 草稿租车列表数据
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:draft:productDraftCar:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(ProductDraftCar productDraftCar, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ProductDraftCar> page = productDraftCarService.findPage(new Page<ProductDraftCar>(request, response), productDraftCar); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑草稿租车表单页面
	 */
	@RequiresPermissions(value={"meiguotong:draft:productDraftCar:view","meiguotong:draft:productDraftCar:add","meiguotong:draft:productDraftCar:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(ProductDraftCar productDraftCar, Model model) {
		model.addAttribute("productDraftCar", productDraftCar);
		return "modules/meiguotong/draft/productDraftCarForm";
	}

	/**
	 * 保存草稿租车
	 */
	@ResponseBody
	@RequiresPermissions(value={"meiguotong:draft:productDraftCar:add","meiguotong:draft:productDraftCar:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(ProductDraftCar productDraftCar, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, productDraftCar)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		productDraftCarService.save(productDraftCar);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存草稿租车成功");
		return j;
	}
	
	/**
	 * 删除草稿租车
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:draft:productDraftCar:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(ProductDraftCar productDraftCar, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		productDraftCarService.delete(productDraftCar);
		j.setMsg("删除草稿租车成功");
		return j;
	}
	
	/**
	 * 批量删除草稿租车
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:draft:productDraftCar:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			productDraftCarService.delete(productDraftCarService.get(id));
		}
		j.setMsg("删除草稿租车成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:draft:productDraftCar:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(ProductDraftCar productDraftCar, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "草稿租车"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<ProductDraftCar> page = productDraftCarService.findPage(new Page<ProductDraftCar>(request, response, -1), productDraftCar);
    		new ExportExcel("草稿租车", ProductDraftCar.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出草稿租车记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("meiguotong:draft:productDraftCar:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<ProductDraftCar> list = ei.getDataList(ProductDraftCar.class);
			for (ProductDraftCar productDraftCar : list){
				try{
					productDraftCarService.save(productDraftCar);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条草稿租车记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条草稿租车记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入草稿租车失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/draft/productDraftCar/?repage";
    }
	
	/**
	 * 下载导入草稿租车数据模板
	 */
	@RequiresPermissions("meiguotong:draft:productDraftCar:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "草稿租车数据导入模板.xlsx";
    		List<ProductDraftCar> list = Lists.newArrayList(); 
    		new ExportExcel("草稿租车数据", ProductDraftCar.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/draft/productDraftCar/?repage";
    }

}