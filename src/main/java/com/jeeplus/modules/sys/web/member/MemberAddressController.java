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
import com.jeeplus.modules.sys.entity.member.MemberAddress;
import com.jeeplus.modules.sys.service.member.MemberAddressService;

/**
 * 会员地址Controller
 * @author xudemo
 * @version 2018-02-24
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/memberAddress")
public class MemberAddressController extends BaseController {

	@Autowired
	private MemberAddressService memberAddressService;
	
	@ModelAttribute
	public MemberAddress get(@RequestParam(required=false) String id) {
		MemberAddress entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = memberAddressService.get(id);
		}
		if (entity == null){
			entity = new MemberAddress();
		}
		return entity;
	}
	
	/**
	 * 会员地址列表页面
	 */
	@RequiresPermissions("sys:memberAddress:list")
	@RequestMapping(value = {"list", ""})
	public String list(MemberAddress memberAddress, Model model) {
		model.addAttribute("memberid", memberAddress.getMemberId());
		return "modules/sys/memberAddress/memberAddressList";
	}
	
		/**
	 * 会员地址列表数据
	 */
	@ResponseBody
	@RequiresPermissions("sys:memberAddress:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(MemberAddress memberAddress, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<MemberAddress> page = memberAddressService.findPage(new Page<MemberAddress>(request, response), memberAddress); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑会员地址表单页面
	 */
	@RequiresPermissions(value={"sys:memberAddress:view","sys:memberAddress:add","sys:memberAddress:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(MemberAddress memberAddress, Model model) {
		model.addAttribute("memberAddress", memberAddress);
		if(StringUtils.isBlank(memberAddress.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/sys/memberAddress/memberAddressForm";
	}

	/**
	 * 保存会员地址
	 */
	@RequiresPermissions(value={"sys:memberAddress:add","sys:memberAddress:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(MemberAddress memberAddress, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, memberAddress)){
			return form(memberAddress, model);
		}
		//新增或编辑表单保存
		memberAddressService.save(memberAddress);//保存
		addMessage(redirectAttributes, "保存会员地址成功");
		return "redirect:"+Global.getAdminPath()+"/sys/memberAddress/?repage";
	}
	
	/**
	 * 删除会员地址
	 */
	@ResponseBody
	@RequiresPermissions("sys:memberAddress:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(MemberAddress memberAddress, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		memberAddressService.delete(memberAddress);
		j.setMsg("删除会员地址成功");
		return j;
	}
	
	/**
	 * 批量删除会员地址
	 */
	@ResponseBody
	@RequiresPermissions("sys:memberAddress:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			memberAddressService.delete(memberAddressService.get(id));
		}
		j.setMsg("删除会员地址成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("sys:memberAddress:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(MemberAddress memberAddress, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "会员地址"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<MemberAddress> page = memberAddressService.findPage(new Page<MemberAddress>(request, response, -1), memberAddress);
    		new ExportExcel("会员地址", MemberAddress.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出会员地址记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("sys:memberAddress:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<MemberAddress> list = ei.getDataList(MemberAddress.class);
			for (MemberAddress memberAddress : list){
				try{
					memberAddressService.save(memberAddress);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条会员地址记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条会员地址记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入会员地址失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/memberAddress/?repage";
    }
	
	/**
	 * 下载导入会员地址数据模板
	 */
	@RequiresPermissions("sys:memberAddress:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "会员地址数据导入模板.xlsx";
    		List<MemberAddress> list = Lists.newArrayList(); 
    		new ExportExcel("会员地址数据", MemberAddress.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/memberAddress/?repage";
    }

}