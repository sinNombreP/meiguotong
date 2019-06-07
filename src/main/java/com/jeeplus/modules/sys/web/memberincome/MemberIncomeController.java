/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.web.memberincome;

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

import com.alibaba.druid.sql.visitor.functions.If;
import com.google.common.collect.Lists;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.config.Global;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.jeeplus.modules.sys.entity.memberincome.MemberIncome;
import com.jeeplus.modules.sys.service.memberincome.MemberIncomeService;

/**
 * 直客流水记录Controller
 * @author dong
 * @version 2019-04-09
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/memberincome/memberIncome")
public class MemberIncomeController extends BaseController {

	@Autowired
	private MemberIncomeService memberIncomeService;
	
	@ModelAttribute
	public MemberIncome get(@RequestParam(required=false) String id) {
		MemberIncome entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = memberIncomeService.get(id);
		}
		if (entity == null){
			entity = new MemberIncome();
		}
		return entity;
	}
	
	/**
	 * 直客流水记录列表页面
	 */
	@RequiresPermissions("sys:memberincome:memberIncome:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/sys/memberincome/memberIncomeList";
	}
	
		/**
	 * 直客流水记录列表数据
	 */
	@ResponseBody
	@RequiresPermissions("sys:memberincome:memberIncome:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(MemberIncome memberIncome, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<MemberIncome> page = memberIncomeService.findTiXianList(new Page<MemberIncome>(request, response), memberIncome); 
		return getBootstrapData(page);
	}

	@RequestMapping(value = {"ZhiKelist", ""})
	public String ZhiKelist(MemberIncome memberIncome, Model model) {
		return "modules/sys/memberincome/memberIncomeZhiKe";
	} 
	
	
	/**
	 * 直客流水列表数据
	 */
	@ResponseBody
	@RequestMapping(value = "ZhiKeData")
	public Map<String, Object> findMemberIncomeZhiKeList(MemberIncome memberIncome, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<MemberIncome> page = memberIncomeService.findZhiKePage(new Page<MemberIncome>(request, response), memberIncome); 
		return getBootstrapData(page);
	}
	
	//修改状态
	@ResponseBody
	@RequestMapping(value = "UpdateChecks")
	public AjaxJson UpdateChecks(MemberIncome memberIncome,RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		memberIncomeService.save(memberIncome);
		if (memberIncome.getChecks()==2) {
			j.setMsg("通过成功");
		}else if (memberIncome.getChecks()==3) {
			j.setMsg("不通过成功");
		}else if (memberIncome.getChecks()==3) {
			j.setMsg("确认打款成功");
		}
		return j;
	}
	
	
	/**
	 * 查看，增加，编辑直客流水记录表单页面
	 */
//	@RequiresPermissions(value={"sys:memberincome:memberIncome:view","sys:memberincome:memberIncome:add","sys:memberincome:memberIncome:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(MemberIncome memberIncome, Model model) {
		model.addAttribute("memberIncome", memberIncomeService.findMemberIncomeTiXianById(Integer.valueOf(memberIncome.getId())));
		if(StringUtils.isBlank(memberIncome.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/sys/memberincome/memberIncomeForm";
	}
	
	
	@RequestMapping(value = "ZhiKeform")
	public String ZhiKeform(MemberIncome memberIncome, Model model) {
		model.addAttribute("memberIncome", memberIncomeService.get(memberIncome));
		if(StringUtils.isBlank(memberIncome.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/sys/memberincome/memberIncomeZhiKeForm";
	}

	/**
	 * 保存直客流水记录
	 */
	@RequiresPermissions(value={"sys:memberincome:memberIncome:add","sys:memberincome:memberIncome:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(MemberIncome memberIncome, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, memberIncome)){
			return form(memberIncome, model);
		}
		//新增或编辑表单保存
		memberIncomeService.save(memberIncome);//保存
		addMessage(redirectAttributes, "保存直客流水记录成功");
		return "redirect:"+Global.getAdminPath()+"/sys/memberincome/memberIncome/?repage";
	}
	
	/**
	 * 删除直客流水记录
	 */
	@ResponseBody
	@RequiresPermissions("sys:memberincome:memberIncome:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(MemberIncome memberIncome, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		memberIncomeService.delete(memberIncome);
		j.setMsg("删除直客流水记录成功");
		return j;
	}
	
	/**
	 * 批量删除直客流水记录
	 */
	@ResponseBody
	@RequiresPermissions("sys:memberincome:memberIncome:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			memberIncomeService.delete(memberIncomeService.get(id));
		}
		j.setMsg("删除直客流水记录成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("sys:memberincome:memberIncome:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(MemberIncome memberIncome, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "直客流水记录"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<MemberIncome> page = memberIncomeService.findPage(new Page<MemberIncome>(request, response, -1), memberIncome);
    		new ExportExcel("直客流水记录", MemberIncome.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出直客流水记录记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("sys:memberincome:memberIncome:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<MemberIncome> list = ei.getDataList(MemberIncome.class);
			for (MemberIncome memberIncome : list){
				try{
					memberIncomeService.save(memberIncome);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条直客流水记录记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条直客流水记录记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入直客流水记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/memberincome/memberIncome/?repage";
    }
	
	/**
	 * 下载导入直客流水记录数据模板
	 */
	@RequiresPermissions("sys:memberincome:memberIncome:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "直客流水记录数据导入模板.xlsx";
    		List<MemberIncome> list = Lists.newArrayList(); 
    		new ExportExcel("直客流水记录数据", MemberIncome.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/memberincome/memberIncome/?repage";
    }

}