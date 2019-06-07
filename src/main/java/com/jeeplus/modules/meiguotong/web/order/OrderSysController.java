/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.web.order;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

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
import com.jeeplus.modules.meiguotong.entity.order.OrderSys;
import com.jeeplus.modules.meiguotong.service.order.OrderSysService;

import cn.jpush.api.report.MessagesResult.Android;

/**
 * 订单关联表Controller
 * @author PSZ
 * @version 2018-08-17
 */
@Controller
@RequestMapping(value = "${adminPath}/meiguotong/order/orderSys")
public class OrderSysController extends BaseController {

	@Autowired
	private OrderSysService orderSysService;
	
	@ModelAttribute
	public OrderSys get(@RequestParam(required=false) String id) {
		OrderSys entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = orderSysService.get(id);
		}
		if (entity == null){
			entity = new OrderSys();
		}
		return entity;
	}
	
	/**
	 * 订单关联表列表页面
	 */
	@RequiresPermissions("meiguotong:order:orderSys:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/meiguotong/order/orderSysList";
	}
	
		/**
	 * 订单关联表列表数据
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:order:orderSys:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(OrderSys orderSys, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<OrderSys> page = orderSysService.findPage(new Page<OrderSys>(request, response), orderSys); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑订单关联表表单页面
	 */
	@RequiresPermissions(value={"meiguotong:order:orderSys:view","meiguotong:order:orderSys:add","meiguotong:order:orderSys:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(OrderSys orderSys, Model model) {
		model.addAttribute("orderSys", orderSys);
		if(StringUtils.isBlank(orderSys.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/meiguotong/order/orderSysForm";
	}

	/**
	 * 保存订单关联表
	 */
	@RequiresPermissions(value={"meiguotong:order:orderSys:add","meiguotong:order:orderSys:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(OrderSys orderSys, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, orderSys)){
			return form(orderSys, model);
		}
		//新增或编辑表单保存
		orderSysService.save(orderSys);//保存
		addMessage(redirectAttributes, "保存订单关联表成功");
		return "redirect:"+Global.getAdminPath()+"/meiguotong/order/orderSys/?repage";
	}
	
	/**
	 * 删除订单关联表
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:order:orderSys:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(OrderSys orderSys, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		orderSysService.delete(orderSys);
		j.setMsg("删除订单关联表成功");
		return j;
	}
	
	/**
	 * 批量删除订单关联表
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:order:orderSys:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			orderSysService.delete(orderSysService.get(id));
		}
		j.setMsg("删除订单关联表成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:order:orderSys:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(OrderSys orderSys, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "订单关联表"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<OrderSys> page = orderSysService.findPage(new Page<OrderSys>(request, response, -1), orderSys);
    		new ExportExcel("订单关联表", OrderSys.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出订单关联表记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("meiguotong:order:orderSys:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<OrderSys> list = ei.getDataList(OrderSys.class);
			for (OrderSys orderSys : list){
				try{
					orderSysService.save(orderSys);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条订单关联表记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条订单关联表记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入订单关联表失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/order/orderSys/?repage";
    }
	
	/**
	 * 下载导入订单关联表数据模板
	 */
	@RequiresPermissions("meiguotong:order:orderSys:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "订单关联表数据导入模板.xlsx";
    		List<OrderSys> list = Lists.newArrayList(); 
    		new ExportExcel("订单关联表数据", OrderSys.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/order/orderSys/?repage";
    }
	
	

}