/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.web.ordermember;

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
import com.jeeplus.modules.meiguotong.entity.ordermember.OrderMember;
import com.jeeplus.modules.meiguotong.service.ordermember.OrderMemberService;

/**
 * 各出行人信息表Controller
 * @author cdq
 * @version 2018-08-15
 */
@Controller
@RequestMapping(value = "${adminPath}/meiguotong/ordermember/orderMember")
public class OrderMemberController extends BaseController {

	@Autowired
	private OrderMemberService orderMemberService;
	
	@ModelAttribute
	public OrderMember get(@RequestParam(required=false) String id) {
		OrderMember entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = orderMemberService.get(id);
		}
		if (entity == null){
			entity = new OrderMember();
		}
		return entity;
	}
	
	/**
	 * 各出行人信息表列表页面
	 */
	@RequiresPermissions("meiguotong:ordermember:orderMember:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/meiguotong/ordermember/orderMemberList";
	}
	
		/**
	 * 各出行人信息表列表数据
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:ordermember:orderMember:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(OrderMember orderMember, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<OrderMember> page = orderMemberService.findPage(new Page<OrderMember>(request, response), orderMember); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑各出行人信息表表单页面
	 */
	@RequiresPermissions(value={"meiguotong:ordermember:orderMember:view","meiguotong:ordermember:orderMember:add","meiguotong:ordermember:orderMember:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(OrderMember orderMember, Model model) {
		model.addAttribute("orderMember", orderMember);
		if(StringUtils.isBlank(orderMember.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/meiguotong/ordermember/orderMemberForm";
	}

	/**
	 * 保存各出行人信息表
	 */
	@RequiresPermissions(value={"meiguotong:ordermember:orderMember:add","meiguotong:ordermember:orderMember:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(OrderMember orderMember, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, orderMember)){
			return form(orderMember, model);
		}
		//新增或编辑表单保存
		orderMemberService.save(orderMember);//保存
		addMessage(redirectAttributes, "保存各出行人信息表成功");
		return "redirect:"+Global.getAdminPath()+"/meiguotong/ordermember/orderMember/?repage";
	}
	
	/**
	 * 删除各出行人信息表
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:ordermember:orderMember:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(OrderMember orderMember, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		orderMemberService.delete(orderMember);
		j.setMsg("删除各出行人信息表成功");
		return j;
	}
	
	/**
	 * 批量删除各出行人信息表
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:ordermember:orderMember:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			orderMemberService.delete(orderMemberService.get(id));
		}
		j.setMsg("删除各出行人信息表成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:ordermember:orderMember:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(OrderMember orderMember, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "各出行人信息表"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<OrderMember> page = orderMemberService.findPage(new Page<OrderMember>(request, response, -1), orderMember);
    		new ExportExcel("各出行人信息表", OrderMember.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出各出行人信息表记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("meiguotong:ordermember:orderMember:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<OrderMember> list = ei.getDataList(OrderMember.class);
			for (OrderMember orderMember : list){
				try{
					orderMemberService.save(orderMember);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条各出行人信息表记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条各出行人信息表记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入各出行人信息表失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/ordermember/orderMember/?repage";
    }
	
	/**
	 * 下载导入各出行人信息表数据模板
	 */
	@RequiresPermissions("meiguotong:ordermember:orderMember:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "各出行人信息表数据导入模板.xlsx";
    		List<OrderMember> list = Lists.newArrayList(); 
    		new ExportExcel("各出行人信息表数据", OrderMember.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/ordermember/orderMember/?repage";
    }

}