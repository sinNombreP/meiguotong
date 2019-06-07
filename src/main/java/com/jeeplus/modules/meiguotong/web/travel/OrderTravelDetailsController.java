/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.web.travel;

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
import com.jeeplus.modules.meiguotong.entity.travel.OrderTravelDetails;
import com.jeeplus.modules.meiguotong.service.travel.OrderTravelDetailsService;

/**
 * 定制订单详情Controller
 * @author psz
 * @version 2018-08-29
 */
@Controller
@RequestMapping(value = "${adminPath}/meiguotong/travel/orderTravelDetails")
public class OrderTravelDetailsController extends BaseController {

	@Autowired
	private OrderTravelDetailsService orderTravelDetailsService;
	
	@ModelAttribute
	public OrderTravelDetails get(@RequestParam(required=false) String id) {
		OrderTravelDetails entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = orderTravelDetailsService.get(id);
		}
		if (entity == null){
			entity = new OrderTravelDetails();
		}
		return entity;
	}
	
	/**
	 * 定制订单详情列表页面
	 */
	@RequiresPermissions("meiguotong:travel:orderTravelDetails:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/meiguotong/travel/orderTravelDetailsList";
	}
	
		/**
	 * 定制订单详情列表数据
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:travel:orderTravelDetails:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(OrderTravelDetails orderTravelDetails, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<OrderTravelDetails> page = orderTravelDetailsService.findPage(new Page<OrderTravelDetails>(request, response), orderTravelDetails); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑定制订单详情表单页面
	 */
	@RequiresPermissions(value={"meiguotong:travel:orderTravelDetails:view","meiguotong:travel:orderTravelDetails:add","meiguotong:travel:orderTravelDetails:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(OrderTravelDetails orderTravelDetails, Model model) {
		model.addAttribute("orderTravelDetails", orderTravelDetails);
		if(StringUtils.isBlank(orderTravelDetails.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/meiguotong/travel/orderTravelDetailsForm";
	}

	/**
	 * 保存定制订单详情
	 */
	@RequiresPermissions(value={"meiguotong:travel:orderTravelDetails:add","meiguotong:travel:orderTravelDetails:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(OrderTravelDetails orderTravelDetails, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, orderTravelDetails)){
			return form(orderTravelDetails, model);
		}
		//新增或编辑表单保存
		orderTravelDetailsService.save(orderTravelDetails);//保存
		addMessage(redirectAttributes, "保存定制订单详情成功");
		return "redirect:"+Global.getAdminPath()+"/meiguotong/travel/orderTravelDetails/?repage";
	}
	
	/**
	 * 删除定制订单详情
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:travel:orderTravelDetails:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(OrderTravelDetails orderTravelDetails, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		orderTravelDetailsService.delete(orderTravelDetails);
		j.setMsg("删除定制订单详情成功");
		return j;
	}
	
	/**
	 * 批量删除定制订单详情
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:travel:orderTravelDetails:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			orderTravelDetailsService.delete(orderTravelDetailsService.get(id));
		}
		j.setMsg("删除定制订单详情成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:travel:orderTravelDetails:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(OrderTravelDetails orderTravelDetails, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "定制订单详情"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<OrderTravelDetails> page = orderTravelDetailsService.findPage(new Page<OrderTravelDetails>(request, response, -1), orderTravelDetails);
    		new ExportExcel("定制订单详情", OrderTravelDetails.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出定制订单详情记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("meiguotong:travel:orderTravelDetails:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<OrderTravelDetails> list = ei.getDataList(OrderTravelDetails.class);
			for (OrderTravelDetails orderTravelDetails : list){
				try{
					orderTravelDetailsService.save(orderTravelDetails);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条定制订单详情记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条定制订单详情记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入定制订单详情失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/travel/orderTravelDetails/?repage";
    }
	
	/**
	 * 下载导入定制订单详情数据模板
	 */
	@RequiresPermissions("meiguotong:travel:orderTravelDetails:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "定制订单详情数据导入模板.xlsx";
    		List<OrderTravelDetails> list = Lists.newArrayList(); 
    		new ExportExcel("定制订单详情数据", OrderTravelDetails.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/travel/orderTravelDetails/?repage";
    }

}