/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.web.ordercar;

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
import com.jeeplus.modules.meiguotong.entity.ordercar.OrderCarDate;
import com.jeeplus.modules.meiguotong.service.ordercar.OrderCarDateService;

/**
 * 包车订单日期详情Controller
 * @author dong
 * @version 2019-05-08
 */
@Controller
@RequestMapping(value = "${adminPath}/meiguotong/ordercar/orderCarDate")
public class OrderCarDateController extends BaseController {

	@Autowired
	private OrderCarDateService orderCarDateService;
	
	@ModelAttribute
	public OrderCarDate get(@RequestParam(required=false) String id) {
		OrderCarDate entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = orderCarDateService.get(id);
		}
		if (entity == null){
			entity = new OrderCarDate();
		}
		return entity;
	}
	
	/**
	 * 包车订单日期详情列表页面
	 */
	@RequiresPermissions("meiguotong:ordercar:orderCarDate:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/meiguotong/ordercar/orderCarDateList";
	}
	
		/**
	 * 包车订单日期详情列表数据
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:ordercar:orderCarDate:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(OrderCarDate orderCarDate, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<OrderCarDate> page = orderCarDateService.findPage(new Page<OrderCarDate>(request, response), orderCarDate); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑包车订单日期详情表单页面
	 */
	@RequiresPermissions(value={"meiguotong:ordercar:orderCarDate:view","meiguotong:ordercar:orderCarDate:add","meiguotong:ordercar:orderCarDate:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(OrderCarDate orderCarDate, Model model) {
		model.addAttribute("orderCarDate", orderCarDate);
		if(StringUtils.isBlank(orderCarDate.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/meiguotong/ordercar/orderCarDateForm";
	}

	/**
	 * 保存包车订单日期详情
	 */
	@RequiresPermissions(value={"meiguotong:ordercar:orderCarDate:add","meiguotong:ordercar:orderCarDate:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(OrderCarDate orderCarDate, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, orderCarDate)){
			return form(orderCarDate, model);
		}
		//新增或编辑表单保存
		orderCarDateService.save(orderCarDate);//保存
		addMessage(redirectAttributes, "保存包车订单日期详情成功");
		return "redirect:"+Global.getAdminPath()+"/meiguotong/ordercar/orderCarDate/?repage";
	}
	
	/**
	 * 删除包车订单日期详情
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:ordercar:orderCarDate:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(OrderCarDate orderCarDate, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		orderCarDateService.delete(orderCarDate);
		j.setMsg("删除包车订单日期详情成功");
		return j;
	}
	
	/**
	 * 批量删除包车订单日期详情
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:ordercar:orderCarDate:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			orderCarDateService.delete(orderCarDateService.get(id));
		}
		j.setMsg("删除包车订单日期详情成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:ordercar:orderCarDate:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(OrderCarDate orderCarDate, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "包车订单日期详情"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<OrderCarDate> page = orderCarDateService.findPage(new Page<OrderCarDate>(request, response, -1), orderCarDate);
    		new ExportExcel("包车订单日期详情", OrderCarDate.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出包车订单日期详情记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("meiguotong:ordercar:orderCarDate:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<OrderCarDate> list = ei.getDataList(OrderCarDate.class);
			for (OrderCarDate orderCarDate : list){
				try{
					orderCarDateService.save(orderCarDate);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条包车订单日期详情记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条包车订单日期详情记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入包车订单日期详情失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/ordercar/orderCarDate/?repage";
    }
	
	/**
	 * 下载导入包车订单日期详情数据模板
	 */
	@RequiresPermissions("meiguotong:ordercar:orderCarDate:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "包车订单日期详情数据导入模板.xlsx";
    		List<OrderCarDate> list = Lists.newArrayList(); 
    		new ExportExcel("包车订单日期详情数据", OrderCarDate.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/ordercar/orderCarDate/?repage";
    }

}