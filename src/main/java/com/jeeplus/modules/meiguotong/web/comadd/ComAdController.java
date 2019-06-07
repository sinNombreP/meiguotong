/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.web.comadd;

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
import com.jeeplus.common.utils.QiniuUtils;
import com.jeeplus.common.config.Global;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.UploadHelper;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.jeeplus.modules.meiguotong.entity.comadd.ComAd;
import com.jeeplus.modules.meiguotong.entity.comlanguage.ComLanguage;
import com.jeeplus.modules.meiguotong.service.comadd.ComAdService;
import com.jeeplus.modules.meiguotong.service.comlanguage.ComLanguageService;
import com.jeeplus.modules.sys.utils.CodeGenUtils;

/**
 * 广告设置Controller
 * @author cdq
 * @version 2018-07-27
 */
@Controller
@RequestMapping(value = "${adminPath}/meiguotong/comadd/comAd")
public class ComAdController extends BaseController {

	@Autowired
	private ComAdService comAdService;
	@Autowired
	private ComLanguageService comLanguageService;
	
	@ModelAttribute
	public ComAd get(@RequestParam(required=false) String id) {
		ComAd entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = comAdService.get(id);
		}
		if (entity == null){
			entity = new ComAd();
		}
		return entity;
	}
	
	/**
	 * 广告设置列表页面
	 */
	@RequiresPermissions("meiguotong:comadd:comAd:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/meiguotong/comadd/comAdList";
	}
	
	
		/**
	 * 广告设置列表数据
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:comadd:comAd:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(ComAd comAd, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ComAd> page = comAdService.findPage(new Page<ComAd>(request, response), comAd); 
		return getBootstrapData(page);
	}
	

	/**
	 * 查看，增加，编辑广告设置表单页面
	 */
	@RequiresPermissions(value={"meiguotong:comadd:comAd:view","meiguotong:comadd:comAd:add","meiguotong:comadd:comAd:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(ComAd comAd, Model model) {
		model.addAttribute("comAd", comAd);
		List<ComLanguage> languageList =comLanguageService.findLanguage();
		model.addAttribute("languageList", languageList);
		if(StringUtils.isBlank(comAd.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/meiguotong/comadd/comAdForm";
	}
	/**
	 * 修改其他语言的页面
	 * @param comAd
	 * @param model
	 * @return
	 */
	@RequiresPermissions(value={"meiguotong:comadd:comAd:view","meiguotong:comadd:comAd:add","meiguotong:comadd:comAd:edit"},logical=Logical.OR)
	@RequestMapping(value = "Edit")
	public String Edit(ComAd comAd, Model model) {
		model.addAttribute("comAd", comAd);
		if(StringUtils.isBlank(comAd.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/meiguotong/comadd/comAdEditForm";
	}
	/**
	 * 保存广告设置
	 */
	@ResponseBody
	@RequiresPermissions(value={"meiguotong:comadd:comAd:add","meiguotong:comadd:comAd:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(ComAd comAd, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, comAd)){
			j.setSuccess(false);
			j.setMsg("非法参数");
			return j;
		}		
		comAdService.save(comAd);
		j.setSuccess(true);
		j.setMsg("保存成功");
		return j;	
	}
	
	/**
	 * 删除广告设置
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:comadd:comAd:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(ComAd comAd, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		comAdService.delete(comAd);
		j.setMsg("删除广告设置成功");
		return j;
	}
	
	/**
	 * 批量删除广告设置
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:comadd:comAd:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			comAdService.delete(comAdService.get(id));
		}
		j.setMsg("删除广告设置成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:comadd:comAd:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(ComAd comAd, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "广告设置"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<ComAd> page = comAdService.findPage(new Page<ComAd>(request, response, -1), comAd);
    		new ExportExcel("广告设置", ComAd.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出广告设置记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("meiguotong:comadd:comAd:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<ComAd> list = ei.getDataList(ComAd.class);
			for (ComAd comAd : list){
				try{
					comAdService.save(comAd);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条广告设置记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条广告设置记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入广告设置失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/comadd/comAd/?repage";
    }
	
	/**
	 * 下载导入广告设置数据模板
	 */
	@RequiresPermissions("meiguotong:comadd:comAd:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "广告设置数据导入模板.xlsx";
    		List<ComAd> list = Lists.newArrayList(); 
    		new ExportExcel("广告设置数据", ComAd.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/comadd/comAd/?repage";
    }
	/**
	 * 表单页面的图片上传
	 */
	@ResponseBody
	@RequestMapping(value = "uploadFile")
	public AjaxJson uploadFile(HttpServletRequest request, HttpServletResponse response, Model model) {

		AjaxJson ajaxJson = new AjaxJson();

		try {
			List<MultipartFile> multipartFiles = UploadHelper.getMultipartFileList(request);
			if (multipartFiles.size() == 0) {
				// 给出提示,不允许没选择文件点击上传
				ajaxJson.setSuccess(false);
				ajaxJson.setMsg("上传图片为空");
				return ajaxJson;
			}
			MultipartFile multipartFile = multipartFiles.get(0);
			String imgPath="label/"+CodeGenUtils.getYear()+"/"+CodeGenUtils.getMonth()+"/"+CodeGenUtils.getDay();

			if(multipartFile != null && multipartFile.getSize()>0) {
				String key =imgPath +"/"+CodeGenUtils.getPicId()+".jpg";
				String filePath=QiniuUtils.uploadFile(multipartFile.getInputStream(), key);
				if(filePath.equals("")){
					ajaxJson.setSuccess(false);
					ajaxJson.setMsg("图片上传失败");
				}else{
					ajaxJson.setSuccess(true);
					ajaxJson.setMsg("图片上传成功");
					filePath=QiniuUtils.QiniuUrl+ key;
					ajaxJson.put("filePath", filePath);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			ajaxJson.setSuccess(false);
			ajaxJson.setMsg("因未知原因失败");
		}
		return ajaxJson;
	}
}