/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.web.comPoster;

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
import com.jeeplus.modules.sys.entity.comPoster.ComPoster;
import com.jeeplus.modules.sys.service.comPoster.ComPosterService;
import com.jeeplus.modules.sys.utils.CodeGenUtils;

/**
 * 广告管理Controller
 * @author laiyanxin
 * @version 2018-03-06
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/comPoster")
public class ComPosterController extends BaseController {

	@Autowired
	private ComPosterService comPosterService;
	
	@ModelAttribute
	public ComPoster get(@RequestParam(required=false) String id) {
		ComPoster entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = comPosterService.get(id);
		}
		if (entity == null){
			entity = new ComPoster();
		}
		return entity;
	}
	
	/**
	 * 广告管理列表页面
	 */
	@RequiresPermissions("sys:comPoster:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/sys/comPoster/comPosterList";
	}
	
		/**
	 * 广告管理列表数据
	 */
	@ResponseBody
	@RequiresPermissions("sys:comPoster:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(ComPoster comPoster, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ComPoster> page = comPosterService.findPage(new Page<ComPoster>(request, response), comPoster); 
		return getBootstrapData(page);
	}

	/**
	 * 查看广告管理表单页面
	 */
	@RequiresPermissions(value={"sys:comPoster:view","sys:comPoster:add","sys:comPoster:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(ComPoster comPoster, Model model) {
		model.addAttribute("comPoster", comPoster);
		if(StringUtils.isBlank(comPoster.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/sys/comPoster/comPosterForm";
	}
	
	/**
	 * 增加，编辑广告管理表单页面
	 */
	@RequiresPermissions(value={"sys:comPoster:view","sys:comPoster:add","sys:comPoster:edit"},logical=Logical.OR)
	@RequestMapping(value = "form2")
	public String form2(ComPoster comPoster, Model model) {
		model.addAttribute("comPoster", comPoster);
		if(StringUtils.isBlank(comPoster.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/sys/comPoster/comPosterForm2";
	}

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
	 * 保存广告管理
	 */
	@RequiresPermissions(value={"sys:comPoster:add","sys:comPoster:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(ComPoster comPoster, Model model, RedirectAttributes redirectAttributes,
			@RequestParam(value="imageFrontFile") MultipartFile imageFrontFile) throws Exception{
		if (!beanValidator(model, comPoster)){
			return form(comPoster, model);
		}
		//上传图片
		String path="common/"+CodeGenUtils.getYear()+"/"+CodeGenUtils.getMonth()+"/"+CodeGenUtils.getDay();
		String message="";
		try {
			if(imageFrontFile != null && imageFrontFile.getSize()>0) {
				String key = path +"-"+CodeGenUtils.getPicId()+".jpg";
				QiniuUtils.uploadFile(imageFrontFile.getInputStream(), key);
				comPoster.setImg(QiniuUtils.QiniuUrl+ key);
			}
		}catch (Exception e) {
			message = "上传图片失败";
			addMessage(model, message);
			return form(comPoster, model);
		}
		//新增或编辑表单保存
		
		comPosterService.save(comPoster);//保存
		addMessage(redirectAttributes, "保存广告管理成功");
		return "redirect:"+Global.getAdminPath()+"/sys/comPoster/?repage";
	}
	
	/**
	 * 删除广告管理
	 */
	@ResponseBody
	@RequiresPermissions("sys:comPoster:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(ComPoster comPoster, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		comPosterService.delete(comPoster);
		j.setMsg("删除广告管理成功");
		return j;
	}
	
	/**
	 * 批量删除广告管理
	 */
	@ResponseBody
	@RequiresPermissions("sys:comPoster:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			comPosterService.delete(comPosterService.get(id));
		}
		j.setMsg("删除广告管理成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("sys:comPoster:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(ComPoster comPoster, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "广告管理"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<ComPoster> page = comPosterService.findPage(new Page<ComPoster>(request, response, -1), comPoster);
    		new ExportExcel("广告管理", ComPoster.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出广告管理记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("sys:comPoster:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<ComPoster> list = ei.getDataList(ComPoster.class);
			for (ComPoster comPoster : list){
				try{
					comPosterService.save(comPoster);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条广告管理记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条广告管理记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入广告管理失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/comPoster/?repage";
    }
	
	/**
	 * 下载导入广告管理数据模板
	 */
	@RequiresPermissions("sys:comPoster:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "广告管理数据导入模板.xlsx";
    		List<ComPoster> list = Lists.newArrayList(); 
    		new ExportExcel("广告管理数据", ComPoster.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/comPoster/?repage";
    }

}