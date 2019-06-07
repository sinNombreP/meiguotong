/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.web.collection;

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
import com.jeeplus.modules.meiguotong.entity.collection.ProductCollection;
import com.jeeplus.modules.meiguotong.service.collection.ProductCollectionService;

/**
 * 我的收藏Controller
 * @author dong
 * @version 2018-09-14
 */
@Controller
@RequestMapping(value = "${adminPath}/meiguotong/collection/productCollection")
public class ProductCollectionController extends BaseController {

	@Autowired
	private ProductCollectionService productCollectionService;
	
	@ModelAttribute
	public ProductCollection get(@RequestParam(required=false) String id) {
		ProductCollection entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = productCollectionService.get(id);
		}
		if (entity == null){
			entity = new ProductCollection();
		}
		return entity;
	}
	
	/**
	 * 我的收藏列表页面
	 */
	@RequiresPermissions("meiguotong:collection:productCollection:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/meiguotong/collection/productCollectionList";
	}
	
		/**
	 * 我的收藏列表数据
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:collection:productCollection:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(ProductCollection productCollection, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ProductCollection> page = productCollectionService.findPage(new Page<ProductCollection>(request, response), productCollection); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑我的收藏表单页面
	 */
	@RequiresPermissions(value={"meiguotong:collection:productCollection:view","meiguotong:collection:productCollection:add","meiguotong:collection:productCollection:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(ProductCollection productCollection, Model model) {
		model.addAttribute("productCollection", productCollection);
		return "modules/meiguotong/collection/productCollectionForm";
	}

	/**
	 * 保存我的收藏
	 */
	@ResponseBody
	@RequiresPermissions(value={"meiguotong:collection:productCollection:add","meiguotong:collection:productCollection:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(ProductCollection productCollection, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, productCollection)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		productCollectionService.save(productCollection);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存我的收藏成功");
		return j;
	}
	
	/**
	 * 删除我的收藏
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:collection:productCollection:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(ProductCollection productCollection, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		productCollectionService.delete(productCollection);
		j.setMsg("删除我的收藏成功");
		return j;
	}
	
	/**
	 * 批量删除我的收藏
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:collection:productCollection:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			productCollectionService.delete(productCollectionService.get(id));
		}
		j.setMsg("删除我的收藏成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:collection:productCollection:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(ProductCollection productCollection, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "我的收藏"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<ProductCollection> page = productCollectionService.findPage(new Page<ProductCollection>(request, response, -1), productCollection);
    		new ExportExcel("我的收藏", ProductCollection.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出我的收藏记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("meiguotong:collection:productCollection:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<ProductCollection> list = ei.getDataList(ProductCollection.class);
			for (ProductCollection productCollection : list){
				try{
					productCollectionService.save(productCollection);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条我的收藏记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条我的收藏记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入我的收藏失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/collection/productCollection/?repage";
    }
	
	/**
	 * 下载导入我的收藏数据模板
	 */
	@RequiresPermissions("meiguotong:collection:productCollection:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "我的收藏数据导入模板.xlsx";
    		List<ProductCollection> list = Lists.newArrayList(); 
    		new ExportExcel("我的收藏数据", ProductCollection.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/collection/productCollection/?repage";
    }

}