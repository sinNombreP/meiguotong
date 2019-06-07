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
import com.jeeplus.modules.meiguotong.entity.draft.ProductDraftDetails;
import com.jeeplus.modules.meiguotong.service.draft.ProductDraftDetailsService;

/**
 * 草稿详情Controller
 * @author dong
 * @version 2018-09-14
 */
@Controller
@RequestMapping(value = "${adminPath}/meiguotong/draft/productDraftDetails")
public class ProductDraftDetailsController extends BaseController {

	@Autowired
	private ProductDraftDetailsService productDraftDetailsService;
	
	@ModelAttribute
	public ProductDraftDetails get(@RequestParam(required=false) String id) {
		ProductDraftDetails entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = productDraftDetailsService.get(id);
		}
		if (entity == null){
			entity = new ProductDraftDetails();
		}
		return entity;
	}
	
	/**
	 * 草稿详情列表页面
	 */
	@RequiresPermissions("meiguotong:draft:productDraftDetails:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/meiguotong/draft/productDraftDetailsList";
	}
	
		/**
	 * 草稿详情列表数据
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:draft:productDraftDetails:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(ProductDraftDetails productDraftDetails, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ProductDraftDetails> page = productDraftDetailsService.findPage(new Page<ProductDraftDetails>(request, response), productDraftDetails); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑草稿详情表单页面
	 */
	@RequiresPermissions(value={"meiguotong:draft:productDraftDetails:view","meiguotong:draft:productDraftDetails:add","meiguotong:draft:productDraftDetails:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(ProductDraftDetails productDraftDetails, Model model) {
		model.addAttribute("productDraftDetails", productDraftDetails);
		return "modules/meiguotong/draft/productDraftDetailsForm";
	}

	/**
	 * 保存草稿详情
	 */
	@ResponseBody
	@RequiresPermissions(value={"meiguotong:draft:productDraftDetails:add","meiguotong:draft:productDraftDetails:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(ProductDraftDetails productDraftDetails, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, productDraftDetails)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		productDraftDetailsService.save(productDraftDetails);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存草稿详情成功");
		return j;
	}
	
	/**
	 * 删除草稿详情
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:draft:productDraftDetails:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(ProductDraftDetails productDraftDetails, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		productDraftDetailsService.delete(productDraftDetails);
		j.setMsg("删除草稿详情成功");
		return j;
	}
	
	/**
	 * 批量删除草稿详情
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:draft:productDraftDetails:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			productDraftDetailsService.delete(productDraftDetailsService.get(id));
		}
		j.setMsg("删除草稿详情成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:draft:productDraftDetails:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(ProductDraftDetails productDraftDetails, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "草稿详情"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<ProductDraftDetails> page = productDraftDetailsService.findPage(new Page<ProductDraftDetails>(request, response, -1), productDraftDetails);
    		new ExportExcel("草稿详情", ProductDraftDetails.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出草稿详情记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("meiguotong:draft:productDraftDetails:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<ProductDraftDetails> list = ei.getDataList(ProductDraftDetails.class);
			for (ProductDraftDetails productDraftDetails : list){
				try{
					productDraftDetailsService.save(productDraftDetails);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条草稿详情记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条草稿详情记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入草稿详情失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/draft/productDraftDetails/?repage";
    }
	
	/**
	 * 下载导入草稿详情数据模板
	 */
	@RequiresPermissions("meiguotong:draft:productDraftDetails:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "草稿详情数据导入模板.xlsx";
    		List<ProductDraftDetails> list = Lists.newArrayList(); 
    		new ExportExcel("草稿详情数据", ProductDraftDetails.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/draft/productDraftDetails/?repage";
    }

}