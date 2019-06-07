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
import com.jeeplus.common.config.Global;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.modules.sys.entity.member.Member;
import com.jeeplus.modules.sys.entity.member.MemberDiscount;
import com.jeeplus.modules.sys.entity.member.MemberTravel;
import com.jeeplus.modules.sys.service.member.MemberDiscountService;
import com.jeeplus.modules.sys.service.member.MemberTravelService;

/**
 * 旅行社Controller
 * @author psz
 * @version 2018-08-07
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/member/memberTravel")
public class MemberTravelController extends BaseController {

	@Autowired
	private MemberTravelService memberTravelService;
	@Autowired
	private MemberDiscountService memberDiscountService;
	
	@ModelAttribute
	public MemberTravel get(@RequestParam(required=false) String id) {
		MemberTravel entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = memberTravelService.get(id);
		}
		if (entity == null){
			entity = new MemberTravel();
		}
		return entity;
	}
	
	/**
	 * 旅行社列表页面
	 */
	@RequiresPermissions("sys:member:memberTravel:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/sys/member/memberTravelList";
	}
	
		/**
	 * 旅行社列表数据
	 */
	@ResponseBody
	@RequiresPermissions("sys:member:memberTravel:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(MemberTravel memberTravel, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<MemberTravel> page = memberTravelService.findPage(new Page<MemberTravel>(request, response), memberTravel); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑旅行社表单页面
	 */
//	@RequiresPermissions(value={"sys:member:memberTravel:view","sys:member:memberTravel:add","sys:member:memberTravel:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(MemberTravel memberTravel, Model model) {
		model.addAttribute("memberTravel", memberTravel);
		//获取子账号
		List<Member> memberList = memberTravelService.getSon(memberTravel);
		model.addAttribute("memberList",memberList);
		model.addAttribute("number",memberList.size());
		MemberDiscount memberDiscount = new MemberDiscount();
		memberDiscount.setMemberid(Integer.parseInt(memberTravel.getId()));
		model.addAttribute("memberDiscountList",memberDiscountService.findList(memberDiscount));
		if(StringUtils.isBlank(memberTravel.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/sys/member/memberTravelForm";
	}

	/**
	 * 保存旅行社
	 */
//	@RequiresPermissions(value={"sys:member:memberTravel:add","sys:member:memberTravel:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(MemberTravel memberTravel, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, memberTravel)){
			return form(memberTravel, model);
		}
		//新增或编辑表单保存
		memberTravelService.save(memberTravel);//保存
		addMessage(redirectAttributes, "保存旅行社成功");
		return "redirect:"+Global.getAdminPath()+"/sys/member/memberTravel/?repage";
	}
	
	/**
	 * 删除旅行社
	 */
	@ResponseBody
	@RequiresPermissions("sys:member:memberTravel:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(MemberTravel memberTravel, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		memberTravelService.delete(memberTravel);
		j.setMsg("删除旅行社成功");
		return j;
	}
	
	/** 
	* @Title: changeAudit 
	* @Description: 修改审核状态
	* @author 彭善智
	* @date 2018年8月7日下午8:22:51
	*/ 
	@ResponseBody
	@RequestMapping(value = "changeAudit")
	public AjaxJson changeAudit(MemberTravel memberTravel, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		memberTravelService.changeAudit(memberTravel);
		j.setMsg("修改状态成功");
		return j;
	}
	
	/**
	 * 批量删除旅行社
	 */
	@ResponseBody
	@RequiresPermissions("sys:member:memberTravel:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			memberTravelService.delete(memberTravelService.get(id));
		}
		j.setMsg("删除旅行社成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("sys:member:memberTravel:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(MemberTravel memberTravel, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "旅行社"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<MemberTravel> page = memberTravelService.findPage(new Page<MemberTravel>(request, response, -1), memberTravel);
    		new ExportExcel("旅行社", MemberTravel.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出旅行社记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("sys:member:memberTravel:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<MemberTravel> list = ei.getDataList(MemberTravel.class);
			for (MemberTravel memberTravel : list){
				try{
					memberTravelService.save(memberTravel);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条旅行社记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条旅行社记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入旅行社失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/member/memberTravel/?repage";
    }
	
	/**
	 * 下载导入旅行社数据模板
	 */
	@RequiresPermissions("sys:member:memberTravel:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "旅行社数据导入模板.xlsx";
    		List<MemberTravel> list = Lists.newArrayList(); 
    		new ExportExcel("旅行社数据", MemberTravel.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/member/memberTravel/?repage";
    }

}