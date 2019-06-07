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
import com.jeeplus.modules.sys.entity.member.MemberInformation;
import com.jeeplus.modules.sys.service.member.MemberInformationService;

/**
 * 会员详情Controller
 * @author psz
 * @version 2018-08-07
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/member/memberInformation")
public class MemberInformationController extends BaseController {

	@Autowired
	private MemberInformationService memberInformationService;
	
	@ModelAttribute
	public MemberInformation get(@RequestParam(required=false) String id) {
		MemberInformation entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = memberInformationService.get(id);
		}
		if (entity == null){
			entity = new MemberInformation();
		}
		return entity;
	}
	
	/**
	 * 会员详情列表页面
	 */
	@RequiresPermissions("sys:member:memberInformation:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/sys/member/memberInformationList";
	}
	
		/**
	 * 会员详情列表数据
	 */
	@ResponseBody
	@RequiresPermissions("sys:member:memberInformation:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(MemberInformation memberInformation, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<MemberInformation> page = memberInformationService.findPage(new Page<MemberInformation>(request, response), memberInformation); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑会员详情表单页面
	 */
//	@RequiresPermissions(value={"sys:member:memberInformation:view","sys:member:memberInformation:add","sys:member:memberInformation:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(MemberInformation memberInformation, Model model) {
		model.addAttribute("memberInformation", memberInformation);
		if(StringUtils.isBlank(memberInformation.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/sys/member/memberInformationForm";
	}

	/**
	 * 保存会员详情
	 */
//	@RequiresPermissions(value={"sys:member:memberInformation:add","sys:member:memberInformation:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(MemberInformation memberInformation, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, memberInformation)){
			return form(memberInformation, model);
		}
		//新增或编辑表单保存
		memberInformationService.save(memberInformation);//保存
		addMessage(redirectAttributes, "保存会员详情成功");
		return "redirect:"+Global.getAdminPath()+"/sys/member/memberInformation/?repage";
	}
	
	/**
	 * 删除会员详情
	 */
	@ResponseBody
	@RequiresPermissions("sys:member:memberInformation:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(MemberInformation memberInformation, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		memberInformationService.delete(memberInformation);
		j.setMsg("删除会员详情成功");
		return j;
	}
	
	
	/** 
	* @Title: changeStatus 
	* @Description: 修改会员状态
	* @author 彭善智
	* @date 2018年8月7日下午2:42:45
	*/ 
	@ResponseBody
	@RequestMapping(value = "changeStatus")
	public AjaxJson changeStatus(MemberInformation memberInformation, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
			memberInformationService.changeStatus(memberInformation);
			j.setMsg("修改会员状态成功");
		}catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("修改会员状态失败");
		}
		return j;
	}
	
	/**
	 * 批量删除会员详情
	 */
	@ResponseBody
	@RequiresPermissions("sys:member:memberInformation:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			memberInformationService.delete(memberInformationService.get(id));
		}
		j.setMsg("删除会员详情成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("sys:member:memberInformation:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(MemberInformation memberInformation, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "会员详情"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<MemberInformation> page = memberInformationService.findPage(new Page<MemberInformation>(request, response, -1), memberInformation);
    		new ExportExcel("会员详情", MemberInformation.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出会员详情记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("sys:member:memberInformation:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<MemberInformation> list = ei.getDataList(MemberInformation.class);
			for (MemberInformation memberInformation : list){
				try{
					memberInformationService.save(memberInformation);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条会员详情记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条会员详情记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入会员详情失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/member/memberInformation/?repage";
    }
	
	/**
	 * 下载导入会员详情数据模板
	 */
	@RequiresPermissions("sys:member:memberInformation:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "会员详情数据导入模板.xlsx";
    		List<MemberInformation> list = Lists.newArrayList(); 
    		new ExportExcel("会员详情数据", MemberInformation.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/member/memberInformation/?repage";
    }

}