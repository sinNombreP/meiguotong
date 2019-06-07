/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.web.comAppstart;

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
import com.jeeplus.modules.sys.entity.comAppstart.ComAppstart;
import com.jeeplus.modules.sys.service.comAppstart.ComAppstartService;
import com.jeeplus.modules.sys.utils.CodeGenUtils;

/**
 * APP启动界面Controller
 * @author laiyanxin
 * @version 2018-03-05
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/comAppstart")
public class ComAppstartController extends BaseController {

	@Autowired
	private ComAppstartService comAppstartService;
	
	@ModelAttribute
	public ComAppstart get(@RequestParam(required=false) String id) {
		ComAppstart entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = comAppstartService.get(id);
		}
		if (entity == null){
			entity = new ComAppstart();
		}
		return entity;
	}
	
	/**
	 * APP启动界面列表页面
	 */
	@RequiresPermissions("sys:comAppstart:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/sys/comAppstart/comAppstartList";
	}
	
		/**
	 * APP启动界面列表数据
	 */
	@ResponseBody
	@RequiresPermissions("sys:comAppstart:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(ComAppstart comAppstart, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ComAppstart> page = comAppstartService.findPage(new Page<ComAppstart>(request, response), comAppstart); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑APP启动界面表单页面
	 */
	@RequiresPermissions(value={"sys:comAppstart:view","sys:comAppstart:add","sys:comAppstart:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(ComAppstart comAppstart, Model model) {
		ComAppstart comAppstart2 = comAppstartService.get("1");
		model.addAttribute("comAppstart", comAppstart2);
		if(StringUtils.isBlank(comAppstart.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/sys/comAppstart/comAppstartForm";
	}

	/**
	 * 保存APP启动界面
	 */
	@RequiresPermissions(value={"sys:comAppstart:add","sys:comAppstart:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(ComAppstart comAppstart, Model model, RedirectAttributes redirectAttributes,
			@RequestParam(value="imageFrontFile") MultipartFile imageFrontFile) throws Exception{
		if (!beanValidator(model, comAppstart)){
			return form(comAppstart, model);
		}
		//上传图片
				String path="common/"+CodeGenUtils.getYear()+"/"+CodeGenUtils.getMonth()+"/"+CodeGenUtils.getDay();
				String message="";
				try {
					if(imageFrontFile != null && imageFrontFile.getSize()>0) {
						String key = path +"-"+CodeGenUtils.getPicId()+".jpg";
						QiniuUtils.uploadFile(imageFrontFile.getInputStream(), key);
						comAppstart.setImg(QiniuUtils.QiniuUrl+ key);
					}
				}catch (Exception e) {
					message = "上传图片失败";
					addMessage(model, message);
					return form(comAppstart, model);
				}
		//新增或编辑表单保存
		comAppstartService.save(comAppstart);//保存
		addMessage(redirectAttributes, "保存APP启动界面成功");
		return "redirect:"+Global.getAdminPath()+"/sys/comAppstart/form";
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
	 * 删除APP启动界面
	 */
	@ResponseBody
	@RequiresPermissions("sys:comAppstart:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(ComAppstart comAppstart, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		comAppstartService.delete(comAppstart);
		j.setMsg("删除APP启动界面成功");
		return j;
	}
	
	/**
	 * 批量删除APP启动界面
	 */
	@ResponseBody
	@RequiresPermissions("sys:comAppstart:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			comAppstartService.delete(comAppstartService.get(id));
		}
		j.setMsg("删除APP启动界面成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("sys:comAppstart:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(ComAppstart comAppstart, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "APP启动界面"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<ComAppstart> page = comAppstartService.findPage(new Page<ComAppstart>(request, response, -1), comAppstart);
    		new ExportExcel("APP启动界面", ComAppstart.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出APP启动界面记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("sys:comAppstart:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<ComAppstart> list = ei.getDataList(ComAppstart.class);
			for (ComAppstart comAppstart : list){
				try{
					comAppstartService.save(comAppstart);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条APP启动界面记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条APP启动界面记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入APP启动界面失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/comAppstart/?repage";
    }
	
	/**
	 * 下载导入APP启动界面数据模板
	 */
	@RequiresPermissions("sys:comAppstart:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "APP启动界面数据导入模板.xlsx";
    		List<ComAppstart> list = Lists.newArrayList(); 
    		new ExportExcel("APP启动界面数据", ComAppstart.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/comAppstart/?repage";
    }

}