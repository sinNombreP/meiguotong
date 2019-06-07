/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.web.insurance;

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
import com.jeeplus.common.config.Global;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.modules.meiguotong.entity.hotel.OrderHotel;
import com.jeeplus.modules.meiguotong.entity.insurance.OrderInsurance;
import com.jeeplus.modules.meiguotong.entity.order.OrderSys;
import com.jeeplus.modules.meiguotong.entity.orderhotelroom.OrderHotelRoom;
import com.jeeplus.modules.meiguotong.entity.ordermember.OrderMember;
import com.jeeplus.modules.meiguotong.service.hotel.OrderHotelService;
import com.jeeplus.modules.meiguotong.service.insurance.OrderInsuranceService;
import com.jeeplus.modules.meiguotong.service.order.OrderSysService;
import com.jeeplus.modules.meiguotong.service.ordermember.OrderMemberService;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.entity.sysUser.SysUser;
import com.jeeplus.modules.sys.service.sysUser.SysUserService;
import com.jeeplus.modules.sys.utils.UserUtils;

/**
 * 保险订单Controller
 * @author PSZ
 * @version 2018-08-20
 */
@Controller
@RequestMapping(value = "${adminPath}/meiguotong/insurance/orderInsurance")
public class OrderInsuranceController extends BaseController {

	@Autowired
	private OrderInsuranceService orderInsuranceService;
	@Autowired
	private OrderSysService orderSysService;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private OrderHotelService orderHotelService;
	@Autowired
	private OrderMemberService orderMemberService;
	
	@ModelAttribute
	public OrderInsurance get(@RequestParam(required=false) String id) {
		OrderInsurance entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = orderInsuranceService.get(id);
		}
		if (entity == null){
			entity = new OrderInsurance();
		}
		return entity;
	}
	
	/**
	 * 保险订单列表页面
	 */
	@RequiresPermissions("meiguotong:insurance:orderInsurance:list")
	@RequestMapping(value = {"list", ""})
	public String list(OrderInsurance orderInsurance, Model model) {
		//供应商
		List<SysUser> agent=sysUserService.getNameByType(7);
		model.addAttribute("agent", agent);
		model.addAttribute("userType",UserUtils.getUser().getUserType());
		model.addAttribute("orderInsurance", orderInsurance);
		return "modules/meiguotong/insurance/orderInsuranceList";
	}
	
		/**
	 * 保险订单列表数据
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:insurance:orderInsurance:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(OrderInsurance orderInsurance, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		if(user.getUserType()==2) {
			orderInsurance.setAgentid(user.getAgentid());
		}
		Page<OrderInsurance> page = orderInsuranceService.findPage(new Page<OrderInsurance>(request, response), orderInsurance); 
		return getBootstrapData(page);
	}

	//@RequiresPermissions(value={"meiguotong:insurance:orderInsurance:view","meiguotong:insurance:orderInsurance:add","meiguotong:insurance:orderInsurance:edit"},logical=Logical.OR)
	/** 
	* @Title: form 
	* @Description: 查看订单详情
	* @author 彭善智
	* @param id 订单ID
	* @param type 订单type
	* @date 2018年8月22日上午9:51:32
	*/ 
	@RequestMapping(value = "form")
	public String form(OrderInsurance orderInsurance, Model model) {
		String param ="";
		switch (orderInsurance.getType()) {
		case 9:
			param = "&status="+orderInsurance.getStatus()+"&createDate="+orderInsurance.getCreateDate()
					+"&orderNo="+orderInsurance.getOrderNo()+"&price="+orderInsurance.getPrice();
			break;
		case 10:
			OrderHotel orderHotel = new OrderHotel();
			param = "&status="+orderHotel.getStatus()+"&createDate="+orderHotel.getCreateDate()
					+"&orderNo="+orderHotel.getOrderNo()+"&price="+orderHotel.getPrice();
			break;
		}
		OrderSys orderSys= orderSysService.getFathType(orderInsurance);
		String url = "";
		switch (orderSys.getType()) {
		case 4:
			url= "/meiguotong/order/orderRoute/form?type=1&id="+orderSys.getOrderid();
			break;
		case 5:
			url= "/meiguotong/order/orderRoute/form?type=2&id="+orderSys.getOrderid();
			break;
		case 11:
			url= "/meiguotong/travel/orderTravelBusiness/form?id="+orderSys.getOrderid();
			break;
		default:
			break;
		}
		return "redirect:"+Global.getAdminPath()+url+param;
	}
	
	@RequestMapping(value = "form2")
	public String form2(OrderInsurance orderInsurance, Model model) {
		model.addAttribute("orderInsurance", orderInsurance);
		//出游人信息
		OrderMember orderMember = new OrderMember();
		orderMember.setTypeId(Integer.parseInt(orderInsurance.getOrderSys1().toString()));
		model.addAttribute("orderMemberList", orderMemberService.findList(orderMember));
		return "modules/meiguotong/insurance/orderInsuranceForm";
	}
	/**
	 * 保存保险订单
	 */
	@RequiresPermissions(value={"meiguotong:insurance:orderInsurance:add","meiguotong:insurance:orderInsurance:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(OrderInsurance orderInsurance, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, orderInsurance)){
			return form(orderInsurance, model);
		}
		//新增或编辑表单保存
		orderInsuranceService.save(orderInsurance);//保存
		addMessage(redirectAttributes, "保存保险订单成功");
		return "redirect:"+Global.getAdminPath()+"/meiguotong/insurance/orderInsurance/?repage";
	}
	
	/** 
	* @Title: updateStatus 
	* @Description: 确定订单
	* @author 彭善智
	* @date 2018年8月17日下午5:59:55
	*/ 
	@ResponseBody
	@RequestMapping(value = "sureOrder")
	public AjaxJson sureOrder(OrderInsurance orderInsurance, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		orderInsurance.setStatus(3);
		orderInsuranceService.save(orderInsurance);
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
	public AjaxJson cancelOrder(OrderInsurance orderInsurance, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		orderInsurance.setStatus(6);
		orderInsuranceService.save(orderInsurance);
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
	public AjaxJson refundOrder(OrderInsurance orderInsurance, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		orderInsurance.setStatus(7);
		orderInsuranceService.save(orderInsurance);
		j.setMsg("申请退款成功");
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
	public AjaxJson agreeOrder(OrderInsurance orderInsurance, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		orderInsurance.setStatus(8);
		orderInsuranceService.save(orderInsurance);
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
	public AjaxJson confirmorder(OrderInsurance orderInsurance, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		orderInsurance.setStatus(9);
		orderInsuranceService.save(orderInsurance);
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
	public AjaxJson refuseOrder(OrderInsurance orderInsurance, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		orderInsurance.setStatus(10);
		orderInsuranceService.save(orderInsurance);
		j.setMsg("退款失败");
		return j;
	}
	
	/**
	 * 删除保险订单
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:insurance:orderInsurance:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(OrderInsurance orderInsurance, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		orderInsuranceService.delete(orderInsurance);
		j.setMsg("删除保险订单成功");
		return j;
	}
	
	/**
	 * 批量删除保险订单
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:insurance:orderInsurance:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			orderInsuranceService.delete(orderInsuranceService.get(id));
		}
		j.setMsg("删除保险订单成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:insurance:orderInsurance:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(OrderInsurance orderInsurance, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "保险订单"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<OrderInsurance> page = orderInsuranceService.findPage(new Page<OrderInsurance>(request, response, -1), orderInsurance);
    		new ExportExcel("保险订单", OrderInsurance.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出保险订单记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("meiguotong:insurance:orderInsurance:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<OrderInsurance> list = ei.getDataList(OrderInsurance.class);
			for (OrderInsurance orderInsurance : list){
				try{
					orderInsuranceService.save(orderInsurance);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条保险订单记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条保险订单记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入保险订单失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/insurance/orderInsurance/?repage";
    }
	
	/**
	 * 下载导入保险订单数据模板
	 */
	@RequiresPermissions("meiguotong:insurance:orderInsurance:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "保险订单数据导入模板.xlsx";
    		List<OrderInsurance> list = Lists.newArrayList(); 
    		new ExportExcel("保险订单数据", OrderInsurance.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/insurance/orderInsurance/?repage";
    }

}