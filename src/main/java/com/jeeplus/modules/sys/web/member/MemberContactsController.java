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
import com.jeeplus.modules.sys.entity.member.MemberContacts;
import com.jeeplus.modules.sys.service.member.MemberContactsService;

/**
 * 常用联系人Controller
 * @author psz
 * @version 2018-08-07
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/member/memberContacts")
public class MemberContactsController extends BaseController {

	@Autowired
	private MemberContactsService memberContactsService;
	
	@ModelAttribute
	public MemberContacts get(@RequestParam(required=false) String id) {
		MemberContacts entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = memberContactsService.get(id);
		}
		if (entity == null){
			entity = new MemberContacts();
		}
		return entity;
	}
	
	/**
	 * 常用联系人列表页面
	 */
	@RequiresPermissions("sys:member:memberContacts:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/sys/member/memberContactsList";
	}
	
		/**
	 * 常用联系人列表数据
	 */
	@ResponseBody
	@RequiresPermissions("sys:member:memberContacts:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(MemberContacts memberContacts, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<MemberContacts> page = memberContactsService.findPage(new Page<MemberContacts>(request, response), memberContacts); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑常用联系人表单页面
	 */
	@RequestMapping(value = "form")
	public String form(MemberContacts memberContacts, Model model) {
		model.addAttribute("memberContactsList", memberContactsService.findList(memberContacts));
		return "modules/sys/member/memberContactsForm";
	}

	/**
	 * 保存常用联系人
	 */
	@RequiresPermissions(value={"sys:member:memberContacts:add","sys:member:memberContacts:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(MemberContacts memberContacts, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, memberContacts)){
			return form(memberContacts, model);
		}
		//新增或编辑表单保存
		memberContactsService.save(memberContacts);//保存
		addMessage(redirectAttributes, "保存常用联系人成功");
		return "redirect:"+Global.getAdminPath()+"/sys/member/memberContacts/?repage";
	}
	
	/**
	 * 删除常用联系人
	 */
	@ResponseBody
	@RequiresPermissions("sys:member:memberContacts:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(MemberContacts memberContacts, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		memberContactsService.delete(memberContacts);
		j.setMsg("删除常用联系人成功");
		return j;
	}
	
	/**
	 * 批量删除常用联系人
	 */
	@ResponseBody
	@RequiresPermissions("sys:member:memberContacts:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			memberContactsService.delete(memberContactsService.get(id));
		}
		j.setMsg("删除常用联系人成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("sys:member:memberContacts:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(MemberContacts memberContacts, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "常用联系人"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<MemberContacts> page = memberContactsService.findPage(new Page<MemberContacts>(request, response, -1), memberContacts);
    		new ExportExcel("常用联系人", MemberContacts.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出常用联系人记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("sys:member:memberContacts:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<MemberContacts> list = ei.getDataList(MemberContacts.class);
			for (MemberContacts memberContacts : list){
				try{
					memberContactsService.save(memberContacts);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条常用联系人记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条常用联系人记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入常用联系人失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/member/memberContacts/?repage";
    }
	
	/**
	 * 下载导入常用联系人数据模板
	 */
	@RequiresPermissions("sys:member:memberContacts:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "常用联系人数据导入模板.xlsx";
    		List<MemberContacts> list = Lists.newArrayList(); 
    		new ExportExcel("常用联系人数据", MemberContacts.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/member/memberContacts/?repage";
    }

}