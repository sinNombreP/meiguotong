/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.web.travel;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.OrderBy;
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
import com.jeeplus.modules.meiguotong.entity.compush.ComPush;
import com.jeeplus.modules.meiguotong.entity.insurance.OrderInsurance;
import com.jeeplus.modules.meiguotong.entity.orderguide.OrderGuide;
import com.jeeplus.modules.meiguotong.entity.travel.OrderTravelBusiness;
import com.jeeplus.modules.meiguotong.entity.travel.OrderTravelDetails;
import com.jeeplus.modules.meiguotong.service.insurance.InsuranceService;
import com.jeeplus.modules.meiguotong.service.insurance.OrderInsuranceService;
import com.jeeplus.modules.meiguotong.service.ordercar.OrderCarInfoService;
import com.jeeplus.modules.meiguotong.service.ordercar.OrderCarService;
import com.jeeplus.modules.meiguotong.service.orderguide.OrderGuideService;
import com.jeeplus.modules.meiguotong.service.travel.OrderTravelBusinessService;
import com.jeeplus.modules.meiguotong.service.travel.OrderTravelDetailsService;
import com.jeeplus.modules.sys.service.member.MemberInformationService;

/**
 * 定制订单Controller
 * @author psz
 * @version 2018-08-29
 */
@Controller
@RequestMapping(value = "${adminPath}/meiguotong/travel/orderTravelBusiness")
public class OrderTravelBusinessController extends BaseController {

	@Autowired
	private OrderTravelBusinessService orderTravelBusinessService;
	@Autowired
	private InsuranceService insuranceService;
	@Autowired
	private OrderTravelDetailsService orderTravelDetailsService;
	@Autowired
	private OrderCarInfoService orderCarInfoService;
	@Autowired
	private MemberInformationService memberInformationService;
	@Autowired
	private OrderInsuranceService orderInsuranceService;
	@Autowired
	private OrderCarService orderCarService;
	@Autowired 
	private OrderGuideService orderGuideService;
	
	@ModelAttribute
	public OrderTravelBusiness get(@RequestParam(required=false) String id) {
		OrderTravelBusiness entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = orderTravelBusinessService.get(id);
		}
		if (entity == null){
			entity = new OrderTravelBusiness();
		}
		return entity;
	}
	
	/**
	 * 定制订单列表页面
	 */
	@RequiresPermissions("meiguotong:travel:orderTravelBusiness:list")
	@RequestMapping(value = {"list", ""})
	public String list(OrderTravelBusiness orderTravelBusiness, Model model) {
		model.addAttribute("orderTravelBusiness", orderTravelBusiness);
		return "modules/meiguotong/travel/orderTravelBusinessList";
	}
	
		/**
	 * 定制订单列表数据
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:travel:orderTravelBusiness:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(OrderTravelBusiness orderTravelBusiness, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<OrderTravelBusiness> page = orderTravelBusinessService.findPage(new Page<OrderTravelBusiness>(request, response), orderTravelBusiness); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑定制订单表单页面
	 */
//	@RequiresPermissions(value={"meiguotong:travel:orderTravelBusiness:view","meiguotong:travel:orderTravelBusiness:add","meiguotong:travel:orderTravelBusiness:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(OrderTravelBusiness orderTravelBusiness, Model model) {
		model.addAttribute("orderTravelBusiness", orderTravelBusiness);
		if(StringUtils.isBlank(orderTravelBusiness.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		//定制信息
		List<OrderTravelDetails> orderTravelDetailsList  = orderTravelDetailsService.getInfo(orderTravelBusiness);
		model.addAttribute("orderTravelDetailsList",orderTravelDetailsList);
		//保险信息
		model.addAttribute("insurances", orderInsuranceService.findOrderInsuranceByOrderSys(orderTravelBusiness.getOrderSys1()));
		/*model.addAttribute("insuranceList", insuranceService.getInfo(11,Integer.parseInt(orderTravelBusiness.getId()),10));*/
		//汽车信息
		model.addAttribute("orderCar", orderCarService.findOrderCarByOrderSys(orderTravelBusiness.getOrderSys1()));
		/*model.addAttribute("orderCarInfoList", orderCarInfoService.getInfo(orderTravelBusiness));*/
		//导游信息
		model.addAttribute("guide", orderGuideService.findGuideByOrderSys(orderTravelBusiness.getOrderSys1()));
		/*model.addAttribute("memberInformation", memberInformationService.getInfo(orderTravelBusiness));*/
		return "modules/meiguotong/travel/orderTravelBusinessForm";
	}

	/**
	 * 保存定制订单
	 */
	@RequiresPermissions(value={"meiguotong:travel:orderTravelBusiness:add","meiguotong:travel:orderTravelBusiness:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(OrderTravelBusiness orderTravelBusiness, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, orderTravelBusiness)){
			return form(orderTravelBusiness, model);
		}
		//新增或编辑表单保存
		orderTravelBusinessService.save(orderTravelBusiness);//保存
		addMessage(redirectAttributes, "保存定制订单成功");
		return "redirect:"+Global.getAdminPath()+"/meiguotong/travel/orderTravelBusiness/?repage";
	}
	
	/**
	 * 子订单确认状态
	 * */
	@ResponseBody
	@RequestMapping(value = "orderList")
	public AjaxJson orderList(OrderTravelBusiness orderTravelBusiness, Model model){
		
		List<OrderTravelBusiness> orderlist=orderTravelBusinessService.findAffirm(orderTravelBusiness.getOrderSys1());
		AjaxJson j = new AjaxJson();
		j.getBody().put("list", orderlist);
		return j;
	}
	
	/** 
	* @Title: updateStatus 
	* @Description: 确定订单
	* @author 彭善智
	* @date 2018年8月17日下午5:59:55
	*/ 
	@ResponseBody
	@RequestMapping(value = "sureOrder")
	public AjaxJson sureOrder(OrderTravelBusiness orderTravelBusiness, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		orderTravelBusiness.setStatus(3);
		orderTravelBusinessService.save(orderTravelBusiness);
		j.setMsg("确定订单成功");
		return j;
	}
	
	/** 
	* @Title: cancelOrder 
	* @Description: 取消订单
	* @author 彭善智
	* @date 2018年8月17日下午6:04:59
	*/ 
	@ResponseBody
	@RequestMapping(value = "cancelOrder")
	public AjaxJson cancelOrder(OrderTravelBusiness orderTravelBusiness, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		orderTravelBusinessService.batchUpdateStatus(orderTravelBusiness.getOrderSys1());
		j.setMsg("取消订单成功");
		return j;
	}
	
	/** 
	* @Title: refundOrder 
	* @Description: 退款
	* @author 彭善智
	* @date 2018年8月17日下午6:05:13
	*/ 
	@ResponseBody
	@RequestMapping(value = "refundOrder")
	public AjaxJson refundOrder(OrderTravelBusiness orderTravelBusiness, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		orderTravelBusiness.setStatus(7);
		orderTravelBusiness.setRefundDate(new Date());
		orderTravelBusinessService.save(orderTravelBusiness);
		j.setMsg("退款成功");
		return j;
	}
	
	
	/** 
	* @Title: agreeOrder 
	* @Description: 同意退款
	* @author 彭善智
	* @date 2018年8月20日上午12:26:11
	*/ 
	@ResponseBody
	@RequestMapping(value = "agreeOrder")
	public AjaxJson agreeOrder(OrderTravelBusiness orderTravelBusiness, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		orderTravelBusiness.setStatus(8);
		orderTravelBusinessService.save(orderTravelBusiness);
		j.setMsg("同意退款成功");
		return j;
	}
	
	
	/** 
	* @Title: confirmorder 
	* @Description: 确定退款
	* @author 彭善智
	* @date 2018年8月20日上午12:27:14
	*/ 
	@ResponseBody
	@RequestMapping(value = "confirmorder")
	public AjaxJson confirmorder(OrderTravelBusiness orderTravelBusiness, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		orderTravelBusiness.setStatus(9);
		orderTravelBusiness.setRefundDate(new Date());
		orderTravelBusinessService.save(orderTravelBusiness);
		j.setMsg("退款成功");
		return j;
	}
	
	/** 
	* @Title: refuseOrder 
	* @Description: 拒绝退款
	* @author 彭善智
	* @date 2018年8月20日上午12:27:50
	*/ 
	@ResponseBody
	@RequestMapping(value = "refuseOrder")
	public AjaxJson refuseOrder(OrderTravelBusiness orderTravelBusiness, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		orderTravelBusiness.setStatus(10);
		orderTravelBusinessService.save(orderTravelBusiness);
		j.setMsg("退款成功");
		return j;
	}
	
	
	/**
	 * 删除定制订单
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:travel:orderTravelBusiness:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(OrderTravelBusiness orderTravelBusiness, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		orderTravelBusinessService.delete(orderTravelBusiness);
		j.setMsg("删除定制订单成功");
		return j;
	}
	
	/**
	 * 批量删除定制订单
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:travel:orderTravelBusiness:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			orderTravelBusinessService.delete(orderTravelBusinessService.get(id));
		}
		j.setMsg("删除定制订单成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:travel:orderTravelBusiness:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(OrderTravelBusiness orderTravelBusiness, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "定制订单"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<OrderTravelBusiness> page = orderTravelBusinessService.findPage(new Page<OrderTravelBusiness>(request, response, -1), orderTravelBusiness);
    		new ExportExcel("定制订单", OrderTravelBusiness.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出定制订单记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("meiguotong:travel:orderTravelBusiness:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<OrderTravelBusiness> list = ei.getDataList(OrderTravelBusiness.class);
			for (OrderTravelBusiness orderTravelBusiness : list){
				try{
					orderTravelBusinessService.save(orderTravelBusiness);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条定制订单记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条定制订单记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入定制订单失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/travel/orderTravelBusiness/?repage";
    }
	
	/**
	 * 下载导入定制订单数据模板
	 */
	@RequiresPermissions("meiguotong:travel:orderTravelBusiness:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "定制订单数据导入模板.xlsx";
    		List<OrderTravelBusiness> list = Lists.newArrayList(); 
    		new ExportExcel("定制订单数据", OrderTravelBusiness.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/travel/orderTravelBusiness/?repage";
    }

}