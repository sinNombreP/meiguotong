/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.web.member;

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
import com.jeeplus.modules.sys.entity.member.SysUserType;
import com.jeeplus.modules.sys.service.member.SysUserTypeService;

/**
 * 供应商类型Controller
 * @author psz
 * @version 2018-08-09
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/member/sysUserType")
public class SysUserTypeController extends BaseController {

	@Autowired
	private SysUserTypeService sysUserTypeService;
	
	@ModelAttribute
	public SysUserType get(@RequestParam(required=false) String id) {
		SysUserType entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = sysUserTypeService.get(id);
		}
		if (entity == null){
			entity = new SysUserType();
		}
		return entity;
	}

	 /**
	   * @method: addRole
	   * @Description:  启用供应商类型
	   * @Author:   彭善智
	   * @Date:     2018/12/20 20:31
	   */
	@ResponseBody
	@RequestMapping(value = "addRole")
	public AjaxJson addRole(SysUserType sysUserType) {
		AjaxJson j = new AjaxJson();
		sysUserTypeService.addRole(sysUserType);
		j.setMsg("修改供应商类型成功");
		return j;
	}


	/**
	 * 供应商类型列表页面
	 */
	@RequiresPermissions("sys:member:sysUserType:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/sys/member/sysUserTypeList";
	}
	
		/**
	 * 供应商类型列表数据
	 */
	@ResponseBody
	@RequiresPermissions("sys:member:sysUserType:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(SysUserType sysUserType, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SysUserType> page = sysUserTypeService.findPage(new Page<SysUserType>(request, response), sysUserType); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑供应商类型表单页面
	 */
	@RequiresPermissions(value={"sys:member:sysUserType:view","sys:member:sysUserType:add","sys:member:sysUserType:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(SysUserType sysUserType, Model model) {
		model.addAttribute("sysUserType", sysUserType);
		if(StringUtils.isBlank(sysUserType.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/sys/member/sysUserTypeForm";
	}

	/**
	 * 保存供应商类型
	 */
	@RequiresPermissions(value={"sys:member:sysUserType:add","sys:member:sysUserType:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(SysUserType sysUserType, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, sysUserType)){
			return form(sysUserType, model);
		}
		//新增或编辑表单保存
		sysUserTypeService.save(sysUserType);//保存
		addMessage(redirectAttributes, "保存供应商类型成功");
		return "redirect:"+Global.getAdminPath()+"/sys/member/sysUserType/?repage";
	}
	
	/**
	 * 删除供应商类型
	 */
	@ResponseBody
	@RequiresPermissions("sys:member:sysUserType:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(SysUserType sysUserType, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		sysUserTypeService.delete(sysUserType);
		j.setMsg("删除供应商类型成功");
		return j;
	}
	
	/**
	 * 批量删除供应商类型
	 */
	@ResponseBody
	@RequiresPermissions("sys:member:sysUserType:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			sysUserTypeService.delete(sysUserTypeService.get(id));
		}
		j.setMsg("删除供应商类型成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("sys:member:sysUserType:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(SysUserType sysUserType, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "供应商类型"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<SysUserType> page = sysUserTypeService.findPage(new Page<SysUserType>(request, response, -1), sysUserType);
    		new ExportExcel("供应商类型", SysUserType.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出供应商类型记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("sys:member:sysUserType:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<SysUserType> list = ei.getDataList(SysUserType.class);
			for (SysUserType sysUserType : list){
				try{
					sysUserTypeService.save(sysUserType);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条供应商类型记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条供应商类型记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入供应商类型失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/member/sysUserType/?repage";
    }
	
	/**
	 * 下载导入供应商类型数据模板
	 */
	@RequiresPermissions("sys:member:sysUserType:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "供应商类型数据导入模板.xlsx";
    		List<SysUserType> list = Lists.newArrayList(); 
    		new ExportExcel("供应商类型数据", SysUserType.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/member/sysUserType/?repage";
    }

}