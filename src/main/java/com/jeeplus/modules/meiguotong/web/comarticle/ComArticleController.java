/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.web.comarticle;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.apache.poi.ss.formula.functions.T;
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
import com.jeeplus.modules.meiguotong.entity.comarticle.ComArticle;
import com.jeeplus.modules.meiguotong.entity.comlanguage.ComLanguage;
import com.jeeplus.modules.meiguotong.service.comarticle.ComArticleService;
import com.jeeplus.modules.meiguotong.service.comlanguage.ComLanguageService;
import com.jeeplus.modules.sys.utils.CodeGenUtils;

/**
 * 文章管理Controller
 * @author cdq
 * @version 2018-07-30
 */
@Controller
@RequestMapping(value = "${adminPath}/meiguotong/comarticle/comArticle")
public class ComArticleController extends BaseController {

	@Autowired
	private ComArticleService comArticleService;
	@Autowired
	private ComLanguageService comLanguageService;
	
	@ModelAttribute
	public ComArticle get(@RequestParam(required=false) String id) {
		ComArticle entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = comArticleService.get(id);
		}
		if (entity == null){
			entity = new ComArticle();
		}
		return entity;
	}
	
	/**
	 * 文章管理列表页面
	 */
	@RequiresPermissions("meiguotong:comarticle:comArticle:list")
	@RequestMapping(value = {"list", ""})
	public String list(Model model) {
		List<ComLanguage> languageList=comLanguageService.findLanguage();
		model.addAttribute("languageList", languageList);
		return "modules/meiguotong/comarticle/comArticleList";
	}
	/**
	 * 资讯管理列表页面
	 */
	@RequiresPermissions("meiguotong:comarticle:comArticle:list")
	@RequestMapping(value = {"information"})
	public String information(Model model) {
		List<ComLanguage> languageList=comLanguageService.findLanguage();
		model.addAttribute("languageList", languageList);
		return "modules/meiguotong/comarticle/comArticleInformationList";
	}	
	/**
	 * 关于我们列表页面
	 */
	@RequiresPermissions("meiguotong:comarticle:comArticle:list")
	@RequestMapping(value = {"aboutUs"})
	public String aboutUs(Model model) {
		List<ComLanguage> languageList=comLanguageService.findLanguage();
		model.addAttribute("languageList", languageList);
		return "modules/meiguotong/comarticle/comArticleAboutUsList";
	}
	/**
	 * 文章管理列表数据
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:comarticle:comArticle:list")
	@RequestMapping(value = "dataUs")
	public Map<String, Object> dataUs(ComArticle comArticle, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ComArticle> page = comArticleService.findUsPage(new Page<ComArticle>(request, response), comArticle); 
		return getBootstrapData(page);
	}
	/**
	 * 文章管理列表数据
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:comarticle:comArticle:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(ComArticle comArticle, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ComArticle> page = comArticleService.findPage(new Page<ComArticle>(request, response), comArticle); 
		return getBootstrapData(page);
	}
	/**
 * 资讯管理列表数据
 */
@ResponseBody
@RequiresPermissions("meiguotong:comarticle:comArticle:list")
@RequestMapping(value = "InformationData")
public Map<String, Object> InformationData(ComArticle comArticle, HttpServletRequest request, HttpServletResponse response, Model model) {
	Page<ComArticle> page = comArticleService.information(new Page<ComArticle>(request, response), comArticle); 
	return getBootstrapData(page);
}
	/**
	 * 文章管理查看，增加，编辑表单页面
	 */
	@RequiresPermissions(value={"meiguotong:comarticle:comArticle:view","meiguotong:comarticle:comArticle:add","meiguotong:comarticle:comArticle:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(ComArticle comArticle, Model model) {
		//查找语言
	  List<ComLanguage> languageList=comLanguageService.findLanguage();
	  model.addAttribute("languageList", languageList);
		model.addAttribute("comArticle", comArticle);
		if(StringUtils.isBlank(comArticle.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/meiguotong/comarticle/comArticleForm";
	}
	/**
	 * 资讯管理查看，增加，编辑文章管理表单页面
	 */
	@RequiresPermissions(value={"meiguotong:comarticle:comArticle:view","meiguotong:comarticle:comArticle:add","meiguotong:comarticle:comArticle:edit"},logical=Logical.OR)
	@RequestMapping(value = "Informationform")
	public String Informationform(ComArticle comArticle, Model model) {
		//查找语言
	  List<ComLanguage> languageList=comLanguageService.findLanguage();
	  model.addAttribute("languageList", languageList);
		model.addAttribute("comArticle", comArticle);
		if(StringUtils.isBlank(comArticle.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/meiguotong/comarticle/comArticleInformationForm";
	}
	/**
	 * 关于我们编辑查看页面
	 */
	@RequiresPermissions(value={"meiguotong:comarticle:comArticle:view","meiguotong:comarticle:comArticle:add","meiguotong:comarticle:comArticle:edit"},logical=Logical.OR)
	@RequestMapping(value = "aboutUsForm")
	public String aboutUsForm(ComArticle comArticle, Model model) {
		//查找语言
	  List<ComLanguage> languageList=comLanguageService.findLanguage();
	  model.addAttribute("languageList", languageList);
		model.addAttribute("comArticle", comArticle);
		return "modules/meiguotong/comarticle/comArticleAboutUsForm";
	}
	/**
	 * 保存文章管理
	 */
	@RequiresPermissions(value={"meiguotong:comarticle:comArticle:add","meiguotong:comarticle:comArticle:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(ComArticle comArticle, Model model, RedirectAttributes redirectAttributes
			) throws Exception{
		if (!beanValidator(model, comArticle)){
			return form(comArticle, model);
		}
		//新增或编辑表单保存
		comArticleService.save(comArticle);//保存
		addMessage(redirectAttributes, "保存文章管理成功");
		return "redirect:"+Global.getAdminPath()+"/meiguotong/comarticle/comArticle/?repage";
	}
	/**
	 * 保存文章管理
	 */
	@RequiresPermissions(value={"meiguotong:comarticle:comArticle:add","meiguotong:comarticle:comArticle:edit"},logical=Logical.OR)
	@RequestMapping(value = "save1")
	public String save1(ComArticle comArticle, Model model, RedirectAttributes redirectAttributes
		) throws Exception{
		if (!beanValidator(model, comArticle)){
			return form(comArticle, model);
		}
		//新增或编辑表单保存
		comArticleService.save(comArticle);//保存
		addMessage(redirectAttributes, "保存资讯管理成功");
		return "redirect:"+Global.getAdminPath()+"/meiguotong/comarticle/comArticle/information";
	}
	/**
	 * 保存关于我们
	 */
	@RequiresPermissions(value={"meiguotong:comarticle:comArticle:add","meiguotong:comarticle:comArticle:edit"},logical=Logical.OR)
	@RequestMapping(value = "saveUs")
	public String saveUs(ComArticle comArticle, Model model, RedirectAttributes redirectAttributes
			) throws Exception{
		if (!beanValidator(model, comArticle)){
			comArticle.setFlag(1); 
			return aboutUsForm(comArticle, model);
		}
		//新增或编辑表单保存
		comArticleService.save(comArticle);//保存
		addMessage(redirectAttributes, "保存文章管理成功");
		return "redirect:"+Global.getAdminPath()+"/meiguotong/comarticle/comArticle/aboutUs?repage";
	}
	/**
	 *  富文本编辑器的图片上传功能
	 */
		@ResponseBody
		@RequestMapping(value = "uploadIMG")
		public void upload(HttpServletRequest request, HttpServletResponse response,
				@RequestParam(value="upfile") MultipartFile upfile)  
	            throws Exception {  

	        request.setCharacterEncoding("utf-8");  
	        response.setContentType("text/html");  
	        response.setCharacterEncoding("utf-8");  
	        String path="common/"+CodeGenUtils.getYear()+"/"+CodeGenUtils.getMonth()+"/"+CodeGenUtils.getDay();
			String url ="";
			String result ="";
			//身份证正面
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
	 * 删除文章管理
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:comarticle:comArticle:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(ComArticle comArticle, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		comArticleService.delete(comArticle);
		j.setMsg("删除成功");
		return j;
	}
	   /**
	   *修改状态
	   */
		@ResponseBody
		@RequestMapping(value = "status")
		public AjaxJson status(ComArticle comArticle, RedirectAttributes redirectAttributes) {
			AjaxJson j = new AjaxJson();
			if(comArticle.getStatus()==2){
				comArticle.setStatus(1);
			}else{
				comArticle.setStatus(2);
			}
			comArticleService.status(comArticle);
			j.setMsg("修改状态成功");
			return j;
		}	
		//修改置顶状态
		@ResponseBody
		@RequestMapping(value = "top")
		public AjaxJson top(ComArticle comArticle, RedirectAttributes redirectAttributes) {
			AjaxJson j = new AjaxJson();
			if(comArticle.getIsTop()==2){
				comArticle.setIsTop(1);
			}else{
				comArticle.setIsTop(2);
			}
			comArticleService.save(comArticle);
			if(comArticle.getIsTop()==1){
				j.setMsg("置顶成功");
			}else{
				j.setMsg("取消置顶成功");
			}
			return j;
		}	
	/**
	 * 批量删除文章管理
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:comarticle:comArticle:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			comArticleService.delete(comArticleService.get(id));
		}
		j.setMsg("删除文章管理成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:comarticle:comArticle:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(ComArticle comArticle, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "文章管理"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<ComArticle> page = comArticleService.findPage(new Page<ComArticle>(request, response, -1), comArticle);
    		new ExportExcel("文章管理", ComArticle.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出文章管理记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("meiguotong:comarticle:comArticle:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<ComArticle> list = ei.getDataList(ComArticle.class);
			for (ComArticle comArticle : list){
				try{
					comArticleService.save(comArticle);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条文章管理记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条文章管理记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入文章管理失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/comarticle/comArticle/?repage";
    }
	
	/**
	 * 下载导入文章管理数据模板
	 */
	@RequiresPermissions("meiguotong:comarticle:comArticle:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "文章管理数据导入模板.xlsx";
    		List<ComArticle> list = Lists.newArrayList(); 
    		new ExportExcel("文章管理数据", ComArticle.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/comarticle/comArticle/?repage";
    }
}