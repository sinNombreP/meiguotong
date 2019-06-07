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
import com.jeeplus.modules.meiguotong.entity.draft.ProductDraftHotel;
import com.jeeplus.modules.meiguotong.service.draft.ProductDraftHotelService;

/**
 * 草稿酒店Controller
 * @author dong
 * @version 2018-09-14
 */
@Controller
@RequestMapping(value = "${adminPath}/meiguotong/draft/productDraftHotel")
public class ProductDraftHotelController extends BaseController {

	@Autowired
	private ProductDraftHotelService productDraftHotelService;
	
	@ModelAttribute
	public ProductDraftHotel get(@RequestParam(required=false) String id) {
		ProductDraftHotel entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = productDraftHotelService.get(id);
		}
		if (entity == null){
			entity = new ProductDraftHotel();
		}
		return entity;
	}
	
	/**
	 * 草稿酒店列表页面
	 */
	@RequiresPermissions("meiguotong:draft:productDraftHotel:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/meiguotong/draft/productDraftHotelList";
	}
	
		/**
	 * 草稿酒店列表数据
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:draft:productDraftHotel:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(ProductDraftHotel productDraftHotel, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ProductDraftHotel> page = productDraftHotelService.findPage(new Page<ProductDraftHotel>(request, response), productDraftHotel); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑草稿酒店表单页面
	 */
	@RequiresPermissions(value={"meiguotong:draft:productDraftHotel:view","meiguotong:draft:productDraftHotel:add","meiguotong:draft:productDraftHotel:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(ProductDraftHotel productDraftHotel, Model model) {
		model.addAttribute("productDraftHotel", productDraftHotel);
		return "modules/meiguotong/draft/productDraftHotelForm";
	}

	/**
	 * 保存草稿酒店
	 */
	@ResponseBody
	@RequiresPermissions(value={"meiguotong:draft:productDraftHotel:add","meiguotong:draft:productDraftHotel:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(ProductDraftHotel productDraftHotel, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, productDraftHotel)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		productDraftHotelService.save(productDraftHotel);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存草稿酒店成功");
		return j;
	}
	
	/**
	 * 删除草稿酒店
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:draft:productDraftHotel:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(ProductDraftHotel productDraftHotel, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		productDraftHotelService.delete(productDraftHotel);
		j.setMsg("删除草稿酒店成功");
		return j;
	}
	
	/**
	 * 批量删除草稿酒店
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:draft:productDraftHotel:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			productDraftHotelService.delete(productDraftHotelService.get(id));
		}
		j.setMsg("删除草稿酒店成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:draft:productDraftHotel:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(ProductDraftHotel productDraftHotel, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "草稿酒店"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<ProductDraftHotel> page = productDraftHotelService.findPage(new Page<ProductDraftHotel>(request, response, -1), productDraftHotel);
    		new ExportExcel("草稿酒店", ProductDraftHotel.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出草稿酒店记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("meiguotong:draft:productDraftHotel:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<ProductDraftHotel> list = ei.getDataList(ProductDraftHotel.class);
			for (ProductDraftHotel productDraftHotel : list){
				try{
					productDraftHotelService.save(productDraftHotel);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条草稿酒店记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条草稿酒店记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入草稿酒店失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/draft/productDraftHotel/?repage";
    }
	
	/**
	 * 下载导入草稿酒店数据模板
	 */
	@RequiresPermissions("meiguotong:draft:productDraftHotel:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "草稿酒店数据导入模板.xlsx";
    		List<ProductDraftHotel> list = Lists.newArrayList(); 
    		new ExportExcel("草稿酒店数据", ProductDraftHotel.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/draft/productDraftHotel/?repage";
    }

}