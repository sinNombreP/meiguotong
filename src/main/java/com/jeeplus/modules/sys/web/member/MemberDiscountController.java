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
import com.jeeplus.modules.sys.entity.member.MemberDiscount;
import com.jeeplus.modules.sys.service.member.MemberDiscountService;

/**
 * 会员优惠Controller
 * @author psz
 * @version 2018-08-08
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/member/memberDiscount")
public class MemberDiscountController extends BaseController {

	@Autowired
	private MemberDiscountService memberDiscountService;
	
	@ModelAttribute
	public MemberDiscount get(@RequestParam(required=false) String id) {
		MemberDiscount entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = memberDiscountService.get(id);
		}
		if (entity == null){
			entity = new MemberDiscount();
		}
		return entity;
	}
	
	/**
	 * 会员优惠列表页面
	 */
	@RequiresPermissions("sys:member:memberDiscount:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/sys/member/memberDiscountList";
	}
	
		/**
	 * 会员优惠列表数据
	 */
	@ResponseBody
	@RequiresPermissions("sys:member:memberDiscount:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(MemberDiscount memberDiscount, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<MemberDiscount> page = memberDiscountService.findPage(new Page<MemberDiscount>(request, response), memberDiscount); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑会员优惠表单页面
	 */
	@RequiresPermissions(value={"sys:member:memberDiscount:view","sys:member:memberDiscount:add","sys:member:memberDiscount:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(MemberDiscount memberDiscount, Model model) {
		model.addAttribute("memberDiscount", memberDiscount);
		return "modules/sys/member/memberDiscountForm";
	}

	/**
	 * 保存会员优惠
	 */
	@ResponseBody
	@RequiresPermissions(value={"sys:member:memberDiscount:add","sys:member:memberDiscount:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(MemberDiscount memberDiscount, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, memberDiscount)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		memberDiscountService.save(memberDiscount);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存会员优惠成功");
		return j;
	}
	
	/**
	 * 删除会员优惠
	 */
	@ResponseBody
	@RequiresPermissions("sys:member:memberDiscount:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(MemberDiscount memberDiscount, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		memberDiscountService.delete(memberDiscount);
		j.setMsg("删除会员优惠成功");
		return j;
	}
	
	/**
	 * 批量删除会员优惠
	 */
	@ResponseBody
	@RequiresPermissions("sys:member:memberDiscount:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			memberDiscountService.delete(memberDiscountService.get(id));
		}
		j.setMsg("删除会员优惠成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("sys:member:memberDiscount:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(MemberDiscount memberDiscount, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "会员优惠"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<MemberDiscount> page = memberDiscountService.findPage(new Page<MemberDiscount>(request, response, -1), memberDiscount);
    		new ExportExcel("会员优惠", MemberDiscount.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出会员优惠记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("sys:member:memberDiscount:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<MemberDiscount> list = ei.getDataList(MemberDiscount.class);
			for (MemberDiscount memberDiscount : list){
				try{
					memberDiscountService.save(memberDiscount);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条会员优惠记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条会员优惠记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入会员优惠失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/member/memberDiscount/?repage";
    }
	
	/**
	 * 下载导入会员优惠数据模板
	 */
	@RequiresPermissions("sys:member:memberDiscount:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "会员优惠数据导入模板.xlsx";
    		List<MemberDiscount> list = Lists.newArrayList(); 
    		new ExportExcel("会员优惠数据", MemberDiscount.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/member/memberDiscount/?repage";
    }

}