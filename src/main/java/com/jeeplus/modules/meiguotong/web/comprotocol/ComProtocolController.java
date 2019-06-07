/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.web.comprotocol;

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
import com.jeeplus.common.utils.CodeGen;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.QiniuUtils;
import com.jeeplus.common.config.Global;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.jeeplus.modules.meiguotong.entity.comlanguage.ComLanguage;
import com.jeeplus.modules.meiguotong.entity.comprotocol.ComProtocol;
import com.jeeplus.modules.meiguotong.service.comlanguage.ComLanguageService;
import com.jeeplus.modules.meiguotong.service.comprotocol.ComProtocolService;
import com.jeeplus.modules.sys.utils.CodeGenUtils;

/**
 * 设置管理Controller
 * @author cdq
 * @version 2018-07-30
 */
@Controller
@RequestMapping(value = "${adminPath}/meiguotong/comprotocol/comProtocol")
public class ComProtocolController extends BaseController {

	@Autowired
	private ComProtocolService comProtocolService;
	@Autowired
	private ComLanguageService comLanguageService;
	
	@ModelAttribute
	public ComProtocol get(@RequestParam(required=false) String id) {
		ComProtocol entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = comProtocolService.get(id);
		}
		if (entity == null){
			entity = new ComProtocol();
		}
		return entity;
	}
	
	/**
	 * 设置管理列表页面
	 */
	@RequiresPermissions("meiguotong:comprotocol:comProtocol:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/meiguotong/comprotocol/comProtocolList";
	}
	
		/**
	 * Logo设置管理列表数据
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:comprotocol:comProtocol:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(ComProtocol comProtocol, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ComProtocol> page = comProtocolService.findPage(new Page<ComProtocol>(request, response), comProtocol); 
		return getBootstrapData(page);
	}

	/**
	 * Logo查看，增加，编辑设置管理表单页面
	 */
	@RequiresPermissions(value={"meiguotong:comprotocol:comProtocol:view","meiguotong:comprotocol:comProtocol:add","meiguotong:comprotocol:comProtocol:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(ComProtocol comProtocol, Model model) {
		List<ComLanguage> comLanguageList =comLanguageService.findLanguage();
		model.addAttribute("comLanguageList", comLanguageList);
		comProtocol = comProtocolService.getComProtocol(comProtocol);
		if(comProtocol==null){
			comProtocol = new ComProtocol();
		}
		model.addAttribute("comProtocol", comProtocol);		
		return "modules/meiguotong/comprotocol/comProtocolForm";
	}
	/**
	 * 根据语言获取基本设置
	 */
	@ResponseBody
	@RequestMapping(value = "getComProtocol")
	public AjaxJson getComProtocol(ComProtocol comProtocol, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		comProtocol = comProtocolService.getComProtocol(comProtocol);
		j.getBody().put("comProtocol", comProtocol);
		j.setMsg("获取设置成功");
		return j;
	}
	
	/**
	 * 品牌设置 查看，增加，编辑设置管理表单页面
	 */
	@RequiresPermissions(value={"meiguotong:comprotocol:comProtocol:view","meiguotong:comprotocol:comProtocol:add","meiguotong:comprotocol:comProtocol:edit"},logical=Logical.OR)
	@RequestMapping(value = "BrandForm")
	public String BrandForm(ComProtocol comProtocol, Model model) {
		List<ComLanguage> comLanguageList =comLanguageService.findLanguage();
		model.addAttribute("comLanguageList", comLanguageList);
		comProtocol = comProtocolService.getComProtocol(comProtocol);
		if(comProtocol==null){
			comProtocol = new ComProtocol();
		}
		model.addAttribute("comProtocol", comProtocol);		
		return "modules/meiguotong/comprotocol/comProtocolBrandForm";
	}
	/**
	 * 底部文字 设置 查看，增加，编辑设置管理表单页面
	 */
	@RequiresPermissions(value={"meiguotong:comprotocol:comProtocol:view","meiguotong:comprotocol:comProtocol:add","meiguotong:comprotocol:comProtocol:edit"},logical=Logical.OR)
	@RequestMapping(value = "TextForm")
	public String TextForm(ComProtocol comProtocol, Model model) {
		List<ComLanguage> comLanguageList =comLanguageService.findLanguage();
		comProtocol = comProtocolService.getComProtocol(comProtocol);
		if(comProtocol==null){
			comProtocol = new ComProtocol();
		}
		model.addAttribute("comProtocol", comProtocol);	
		model.addAttribute("comLanguageList", comLanguageList);
		return "modules/meiguotong/comprotocol/comProtocolTextForm";
	}
	
	/**
	 * Logo页面保存设置管理
	 */
	@RequiresPermissions(value={"meiguotong:comprotocol:comProtocol:add","meiguotong:comprotocol:comProtocol:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(ComProtocol comProtocol, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, comProtocol)){
			return form(comProtocol, model);
		}
		//新增或编辑表单保存
		comProtocolService.save(comProtocol);//保存
		addMessage(redirectAttributes, "保存设置管理成功");
		return "redirect:"+Global.getAdminPath()+"/meiguotong/comprotocol/comProtocol/form";
	}
	/**
	 * 品牌设置页面保存设置管理
	 */
	@RequiresPermissions(value={"meiguotong:comprotocol:comProtocol:add","meiguotong:comprotocol:comProtocol:edit"},logical=Logical.OR)
	@RequestMapping(value = "BrandSave")
	public String BrandSave(ComProtocol comProtocol, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, comProtocol)){
			return form(comProtocol, model);
		}
		//新增或编辑表单保存
		comProtocolService.save2(comProtocol);//保存
		addMessage(redirectAttributes, "保存设置管理成功");
		return "redirect:"+Global.getAdminPath()+"/meiguotong/comprotocol/comProtocol/BrandForm";
	}
	
	/**
	 * 底部文字 设置页面保存设置管理
	 */
	@RequiresPermissions(value={"meiguotong:comprotocol:comProtocol:add","meiguotong:comprotocol:comProtocol:edit"},logical=Logical.OR)
	@RequestMapping(value = "TextSave")
	public String TextSave(ComProtocol comProtocol, Model model, RedirectAttributes redirectAttributes
			) throws Exception{
		if (!beanValidator(model, comProtocol)){
			return form(comProtocol, model);
		}
	
		//新增或编辑表单保存
		comProtocolService.save3(comProtocol);//保存
		addMessage(redirectAttributes, "保存设置管理成功");
		return "redirect:"+Global.getAdminPath()+"/meiguotong/comprotocol/comProtocol/TextForm";
	}
	/**
	 *  富文本图片上传
	 */
		@ResponseBody
		@RequestMapping(value = "uploadIMG")
		public void upload(HttpServletRequest request, HttpServletResponse response,
				@RequestParam(value="upfile") MultipartFile upfile)  
	            throws Exception {  

	        request.setCharacterEncoding("utf-8");  
	        response.setContentType("text/html");  
	        response.setCharacterEncoding("utf-8");  
	        String path="meiguotong/"+CodeGenUtils.getYear()+"/"+CodeGenUtils.getMonth()+"/"+CodeGenUtils.getDay();
			String url ="";
			String result ="";
			//韬唤璇佹闈�
			try {
				if(upfile != null) {
					String key = path +"-"+CodeGen.getPicId()+".jpg";
					QiniuUtils.uploadFile(upfile.getInputStream(), key);
					url = QiniuUtils.QiniuUrl+ key;
					result = "{ \"state\": \""  
						      + "SUCCESS" + "\", \"type\": \"" + "jpg" 
						      + "\", \"url\": \"" + url + "\"}";  
				}
			}catch (Exception e) {
				result = "{ \"state\": \""  
					      + "ERROR" + "\", \"type\": \"" + "jpg" 
					      + "\", \"url\": \"" + url + "\"}";  
			}
	  
	       result = result.replaceAll("\\\\", "\\\\");  
	       response.getWriter().print(result);  
	} 
	/**
	 * 删除设置管理
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:comprotocol:comProtocol:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(ComProtocol comProtocol, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		comProtocolService.delete(comProtocol);
		j.setMsg("删除设置管理成功");
		return j;
	}
	
	/**
	 * 批量删除设置管理
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:comprotocol:comProtocol:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			comProtocolService.delete(comProtocolService.get(id));
		}
		j.setMsg("删除设置管理成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:comprotocol:comProtocol:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(ComProtocol comProtocol, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "设置管理"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<ComProtocol> page = comProtocolService.findPage(new Page<ComProtocol>(request, response, -1), comProtocol);
    		new ExportExcel("设置管理", ComProtocol.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出设置管理记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("meiguotong:comprotocol:comProtocol:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<ComProtocol> list = ei.getDataList(ComProtocol.class);
			for (ComProtocol comProtocol : list){
				try{
					comProtocolService.save(comProtocol);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条设置管理记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条设置管理记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入设置管理失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/comprotocol/comProtocol/?repage";
    }
	
	/**
	 * 下载导入设置管理数据模板
	 */
	@RequiresPermissions("meiguotong:comprotocol:comProtocol:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "设置管理数据导入模板.xlsx";
    		List<ComProtocol> list = Lists.newArrayList(); 
    		new ExportExcel("设置管理数据", ComProtocol.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/comprotocol/comProtocol/?repage";
    }

}