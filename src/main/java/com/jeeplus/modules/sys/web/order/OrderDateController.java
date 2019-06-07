/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.web.order;

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
import com.jeeplus.modules.sys.entity.order.OrderDate;
import com.jeeplus.modules.sys.service.order.OrderDateService;

/**
 * 订单状态时间Controller
 * @author dong
 * @version 2018-03-08
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/order/orderDate")
public class OrderDateController extends BaseController {

	@Autowired
	private OrderDateService orderDateService;
	
	@ModelAttribute
	public OrderDate get(@RequestParam(required=false) String id) {
		OrderDate entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = orderDateService.get(id);
		}
		if (entity == null){
			entity = new OrderDate();
		}
		return entity;
	}
	
	/**
	 * 订单状态时间列表页面
	 */
	@RequiresPermissions("sys:order:orderDate:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/sys/order/orderDateList";
	}
	
		/**
	 * 订单状态时间列表数据
	 */
	@ResponseBody
	@RequiresPermissions("sys:order:orderDate:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(OrderDate orderDate, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<OrderDate> page = orderDateService.findPage(new Page<OrderDate>(request, response), orderDate); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑订单状态时间表单页面
	 */
	@RequiresPermissions(value={"sys:order:orderDate:view","sys:order:orderDate:add","sys:order:orderDate:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(OrderDate orderDate, Model model) {
		model.addAttribute("orderDate", orderDate);
		return "modules/sys/order/orderDateForm";
	}

	/**
	 * 保存订单状态时间
	 */
	@ResponseBody
	@RequiresPermissions(value={"sys:order:orderDate:add","sys:order:orderDate:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(OrderDate orderDate, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, orderDate)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		orderDateService.save(orderDate);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存订单状态时间成功");
		return j;
	}
	
	/**
	 * 删除订单状态时间
	 */
	@ResponseBody
	@RequiresPermissions("sys:order:orderDate:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(OrderDate orderDate, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		orderDateService.delete(orderDate);
		j.setMsg("删除订单状态时间成功");
		return j;
	}
	
	/**
	 * 批量删除订单状态时间
	 */
	@ResponseBody
	@RequiresPermissions("sys:order:orderDate:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			orderDateService.delete(orderDateService.get(id));
		}
		j.setMsg("删除订单状态时间成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("sys:order:orderDate:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(OrderDate orderDate, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "订单状态时间"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<OrderDate> page = orderDateService.findPage(new Page<OrderDate>(request, response, -1), orderDate);
    		new ExportExcel("订单状态时间", OrderDate.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出订单状态时间记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("sys:order:orderDate:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<OrderDate> list = ei.getDataList(OrderDate.class);
			for (OrderDate orderDate : list){
				try{
					orderDateService.save(orderDate);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条订单状态时间记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条订单状态时间记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入订单状态时间失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/order/orderDate/?repage";
    }
	
	/**
	 * 下载导入订单状态时间数据模板
	 */
	@RequiresPermissions("sys:order:orderDate:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "订单状态时间数据导入模板.xlsx";
    		List<OrderDate> list = Lists.newArrayList(); 
    		new ExportExcel("订单状态时间数据", OrderDate.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/order/orderDate/?repage";
    }

}