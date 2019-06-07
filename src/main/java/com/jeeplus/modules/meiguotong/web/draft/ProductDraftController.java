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
import com.jeeplus.modules.meiguotong.entity.draft.ProductDraft;
import com.jeeplus.modules.meiguotong.service.draft.ProductDraftService;

/**
 * 草稿Controller
 * @author dong
 * @version 2018-09-14
 */
@Controller
@RequestMapping(value = "${adminPath}/meiguotong/draft/productDraft")
public class ProductDraftController extends BaseController {

	@Autowired
	private ProductDraftService productDraftService;
	
	@ModelAttribute
	public ProductDraft get(@RequestParam(required=false) String id) {
		ProductDraft entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = productDraftService.get(id);
		}
		if (entity == null){
			entity = new ProductDraft();
		}
		return entity;
	}
	
	/**
	 * 草稿列表页面
	 */
	@RequiresPermissions("meiguotong:draft:productDraft:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/meiguotong/draft/productDraftList";
	}
	
		/**
	 * 草稿列表数据
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:draft:productDraft:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(ProductDraft productDraft, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ProductDraft> page = productDraftService.findPage(new Page<ProductDraft>(request, response), productDraft); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑草稿表单页面
	 */
	@RequiresPermissions(value={"meiguotong:draft:productDraft:view","meiguotong:draft:productDraft:add","meiguotong:draft:productDraft:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(ProductDraft productDraft, Model model) {
		model.addAttribute("productDraft", productDraft);
		return "modules/meiguotong/draft/productDraftForm";
	}

	/**
	 * 保存草稿
	 */
	@ResponseBody
	@RequiresPermissions(value={"meiguotong:draft:productDraft:add","meiguotong:draft:productDraft:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(ProductDraft productDraft, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, productDraft)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		productDraftService.save(productDraft);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存草稿成功");
		return j;
	}
	
	/**
	 * 删除草稿
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:draft:productDraft:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(ProductDraft productDraft, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		productDraftService.delete(productDraft);
		j.setMsg("删除草稿成功");
		return j;
	}
	
	/**
	 * 批量删除草稿
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:draft:productDraft:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			productDraftService.delete(productDraftService.get(id));
		}
		j.setMsg("删除草稿成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:draft:productDraft:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(ProductDraft productDraft, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "草稿"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<ProductDraft> page = productDraftService.findPage(new Page<ProductDraft>(request, response, -1), productDraft);
    		new ExportExcel("草稿", ProductDraft.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出草稿记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("meiguotong:draft:productDraft:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<ProductDraft> list = ei.getDataList(ProductDraft.class);
			for (ProductDraft productDraft : list){
				try{
					productDraftService.save(productDraft);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条草稿记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条草稿记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入草稿失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/draft/productDraft/?repage";
    }
	
	/**
	 * 下载导入草稿数据模板
	 */
	@RequiresPermissions("meiguotong:draft:productDraft:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "草稿数据导入模板.xlsx";
    		List<ProductDraft> list = Lists.newArrayList(); 
    		new ExportExcel("草稿数据", ProductDraft.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/draft/productDraft/?repage";
    }

}