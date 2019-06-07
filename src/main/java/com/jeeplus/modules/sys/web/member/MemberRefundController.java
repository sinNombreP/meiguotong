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
import com.jeeplus.modules.sys.entity.member.MemberRefund;
import com.jeeplus.modules.sys.service.member.MemberRefundService;
import com.jeeplus.modules.sys.utils.UserUtils;

/**
 * 退款设置表Controller
 * @author psz
 * @version 2018-08-09
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/member/memberRefund")
public class MemberRefundController extends BaseController {

	@Autowired
	private MemberRefundService memberRefundService;
	
	@ModelAttribute
	public MemberRefund get(@RequestParam(required=false) String id) {
		MemberRefund entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = memberRefundService.get(id);
		}
		if (entity == null){
			entity = new MemberRefund();
		}
		return entity;
	}
	
	
	
	/** 
	* @Title: findList 
	* @Description: 退款说明页面
	* @author 彭善智
	* @date 2018年8月11日下午6:22:42
	*/ 
	@RequiresPermissions("sys:member:memberRefund:findList")
	@RequestMapping(value = {"findList"})
	public String findList(Model model,MemberRefund memberRefund) {
		Integer userType = UserUtils.getUser().getUserType();
		model.addAttribute("userType", userType); //供应商类型
		if(userType == 2) {
			if(StringUtils.isNotBlank(memberRefund.getFlag())) {
				memberRefundService.update(memberRefund);
			}
			memberRefund.setTypeId(UserUtils.getUser().getAgentid());
			memberRefund.setType(2);
			List<MemberRefund> memberRefundList = memberRefundService.findListByAgentid(memberRefund);
			if(memberRefundList!=null&&memberRefundList.size()>0){
				int carflag=0;
				int agentid=0;
				for(MemberRefund a:memberRefundList){
					if(a.getRefundType()==1){
						carflag=1;
						agentid=a.getAgentid();
					}else if(a.getRefundType()==2){
						a.setRefundType(4);
					}else if(a.getRefundType()==3){
						a.setRefundType(5);
					}else if(a.getRefundType()==4){
						a.setRefundType(6);
					}else if(a.getRefundType()==5){
						a.setRefundType(7);
					}else if(a.getRefundType()==6){
						a.setRefundType(9);
					}else if(a.getRefundType()==7){
						a.setRefundType(10);
					}
				}
				if(carflag==1){//有租车权限
					MemberRefund memberRefund1 = new MemberRefund();
					memberRefund1.setAgentid(agentid);
					memberRefund1.setRefundType(2);
					memberRefund1.setRefundContent("短程接送");
					List<MemberRefund> list1 = memberRefundService.findMemberRefund(memberRefund1);
					memberRefund1.setMemberRefundList(list1);
					memberRefundList.add(memberRefund1);
					
					MemberRefund memberRefund2 = new MemberRefund();
					memberRefund2.setAgentid(agentid);
					memberRefund2.setRefundType(3);
					memberRefund2.setRefundContent("接送机");
					List<MemberRefund> list2 = memberRefundService.findMemberRefund(memberRefund2);
					memberRefund2.setMemberRefundList(list2);
					memberRefundList.add(memberRefund2);
				}
				//memberRefundList按照RefundType排序
				
			}
			model.addAttribute("memberRefundList", memberRefundList); //供应商退款说明
		}
		return "modules/sys/sysUser/sysUserFormRefund";
	}
	
	

	/** 
	* @Title: update 
	* @Description: 更新退款说明页面
	* @author 彭善智
	* @date 2018年8月12日上午12:16:39
	*/ 
	@RequiresPermissions("sys:member:memberRefund:findList")
	@RequestMapping(value = {"update"})
	public String update(Model model,MemberRefund memberRefund) {
		memberRefund.setTypeId(UserUtils.getUser().getAgentid());
		memberRefund.setType(2);
		model.addAttribute("memberRefundList", memberRefundService.findListByAgentid(memberRefund)); //供应商退款说明
		return "modules/sys/sysUser/sysUserFormRefund";
	}
	
	
	
	
	/**
	 * 退款设置表列表页面
	 */
	@RequiresPermissions("sys:member:memberRefund:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/sys/member/memberRefundList";
	}
	
	
		/**
	 * 退款设置表列表数据
	 */
	@ResponseBody
	@RequiresPermissions("sys:member:memberRefund:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(MemberRefund memberRefund, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<MemberRefund> page = memberRefundService.findPage(new Page<MemberRefund>(request, response), memberRefund); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑退款设置表表单页面
	 */
	@RequiresPermissions(value={"sys:member:memberRefund:view","sys:member:memberRefund:add","sys:member:memberRefund:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(MemberRefund memberRefund, Model model) {
		model.addAttribute("memberRefund", memberRefund);
		if(StringUtils.isBlank(memberRefund.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/sys/member/memberRefundForm";
	}

	/**
	 * 保存退款设置表
	 */
	@RequiresPermissions(value={"sys:member:memberRefund:add","sys:member:memberRefund:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(MemberRefund memberRefund, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, memberRefund)){
			return form(memberRefund, model);
		}
		//新增或编辑表单保存
		memberRefundService.save(memberRefund);//保存
		addMessage(redirectAttributes, "保存退款设置表成功");
		return "redirect:"+Global.getAdminPath()+"/sys/member/memberRefund/?repage";
	}
	
	/**
	 * 删除退款设置表
	 */
	@ResponseBody
	@RequiresPermissions("sys:member:memberRefund:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(MemberRefund memberRefund, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		memberRefundService.delete(memberRefund);
		j.setMsg("删除退款设置表成功");
		return j;
	}
	
	/**
	 * 批量删除退款设置表
	 */
	@ResponseBody
	@RequiresPermissions("sys:member:memberRefund:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			memberRefundService.delete(memberRefundService.get(id));
		}
		j.setMsg("删除退款设置表成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("sys:member:memberRefund:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(MemberRefund memberRefund, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "退款设置表"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<MemberRefund> page = memberRefundService.findPage(new Page<MemberRefund>(request, response, -1), memberRefund);
    		new ExportExcel("退款设置表", MemberRefund.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出退款设置表记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("sys:member:memberRefund:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<MemberRefund> list = ei.getDataList(MemberRefund.class);
			for (MemberRefund memberRefund : list){
				try{
					memberRefundService.save(memberRefund);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条退款设置表记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条退款设置表记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入退款设置表失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/member/memberRefund/?repage";
    }
	
	/**
	 * 下载导入退款设置表数据模板
	 */
	@RequiresPermissions("sys:member:memberRefund:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "退款设置表数据导入模板.xlsx";
    		List<MemberRefund> list = Lists.newArrayList(); 
    		new ExportExcel("退款设置表数据", MemberRefund.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/member/memberRefund/?repage";
    }

}