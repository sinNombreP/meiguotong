/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.web.order;

import java.util.Date;
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
import com.jeeplus.modules.meiguotong.entity.order.OrderRoute;
import com.jeeplus.modules.meiguotong.entity.ordermember.OrderMember;
import com.jeeplus.modules.meiguotong.entity.product.Route;
import com.jeeplus.modules.meiguotong.service.insurance.InsuranceService;
import com.jeeplus.modules.meiguotong.service.order.OrderRouteService;
import com.jeeplus.modules.meiguotong.service.ordermember.OrderMemberService;
import com.jeeplus.modules.meiguotong.service.travel.OrderTravelDetailsService;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.entity.sysUser.SysUser;
import com.jeeplus.modules.sys.service.sysUser.SysUserService;
import com.jeeplus.modules.sys.utils.UserUtils;

/**
 * 参团订单Controller
 * @author PSZ
 * @version 2018-08-17
 */
@Controller
@RequestMapping(value = "${adminPath}/meiguotong/order/orderRoute")
public class OrderRouteController extends BaseController {

	@Autowired
	private OrderRouteService orderRouteService;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private OrderMemberService orderMemberService;
	@Autowired
	private InsuranceService insuranceService;
	
	@ModelAttribute
	public OrderRoute get(@RequestParam(required=false) String id) {
		OrderRoute entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = orderRouteService.get(id);
		}
		if (entity == null){
			entity = new OrderRoute();
		}
		return entity;
	}
	
	/**
	 * 参团订单列表页面
	 */
	@RequiresPermissions("meiguotong:order:orderRoute:list")
	@RequestMapping(value = {"list", ""})
	public String list(Model model,OrderRoute orderRoute) {
		//供应商
		List<SysUser> sysUserList = sysUserService.getNameByType(orderRoute.getType()+1);
		model.addAttribute("sysUserList",sysUserList);
		model.addAttribute("userType",UserUtils.getUser().getUserType());
		model.addAttribute("orderRoute",orderRoute);
		return "modules/meiguotong/product/orderRouteList";
	}
	
		/**
	 * 参团订单列表数据
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:order:orderRoute:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(OrderRoute orderRoute, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		if(user.getUserType()==2) {
			orderRoute.setAgentid(user.getAgentid());
		}
		Page<OrderRoute> page = orderRouteService.findPage(new Page<OrderRoute>(request, response), orderRoute); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑参团订单表单页面
	 */
//	@RequiresPermissions(value={"meiguotong:order:orderRoute:view","meiguotong:order:orderRoute:add","meiguotong:order:orderRoute:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(OrderRoute orderRoute, Model model) {
		model.addAttribute("orderRoute", orderRoute);
		//出游人信息
		OrderMember orderMember = new OrderMember();
		orderMember.setTypeId(Integer.parseInt(orderRoute.getOrderSys1().toString()));
		model.addAttribute("orderMemberList", orderMemberService.findList(orderMember));
		//保险信息
		model.addAttribute("insuranceList", insuranceService.getInfo(orderRoute.getType()+3,Integer.parseInt(orderRoute.getId()),10));
		return "modules/meiguotong/product/orderRouteForm";
	}

	
	/** 
	* @Title: updateStatus 
	* @Description: 确定订单
	* @author 彭善智
	* @date 2018年8月17日下午5:59:55
	*/ 
	@ResponseBody
	@RequestMapping(value = "sureOrder")
	public AjaxJson sureOrder(OrderRoute orderRoute, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		orderRoute.setStatus(3);
		orderRouteService.save(orderRoute);
		j.setMsg("确定订单成功");
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
	public AjaxJson refundOrder(OrderRoute orderRoute, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		orderRoute.setStatus(7);
		orderRoute.setRefundDate(new Date());
		orderRouteService.save(orderRoute);
		j.setMsg("已成功申请退款");
		return j;
	}
	
	@ResponseBody
	@RequestMapping(value = "cancelOrder")
	public AjaxJson cancelOrder(OrderRoute orderRoute, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		orderRoute.setStatus(6);
		orderRoute.setRefundDate(new Date());
		orderRouteService.save(orderRoute);
		j.setMsg("取消成功");
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
	public AjaxJson agreeOrder(OrderRoute orderRoute, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		orderRoute.setStatus(8);
		orderRouteService.save(orderRoute);
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
	public AjaxJson confirmorder(OrderRoute orderRoute, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		orderRoute.setStatus(9);
		orderRoute.setRefundDate(new Date());
		orderRouteService.save(orderRoute);
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
	public AjaxJson refuseOrder(OrderRoute orderRoute, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		orderRoute.setStatus(10);
		orderRouteService.save(orderRoute);
		j.setMsg("拒绝退款成功");
		return j;
	}
	
	
	/**
	 * 保存参团订单
	 */
	@RequiresPermissions(value={"meiguotong:order:orderRoute:add","meiguotong:order:orderRoute:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(OrderRoute orderRoute, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, orderRoute)){
			return form(orderRoute, model);
		}
		//新增或编辑表单保存
		orderRouteService.save(orderRoute);//保存
		addMessage(redirectAttributes, "保存参团订单成功");
		return "redirect:"+Global.getAdminPath()+"/meiguotong/order/orderRoute/?repage";
	}
	
	/**
	 * 删除参团订单
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:order:orderRoute:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(OrderRoute orderRoute, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		orderRouteService.delete(orderRoute);
		j.setMsg("删除参团订单成功");
		return j;
	}
	
	/**
	 * 批量删除参团订单
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:order:orderRoute:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			orderRouteService.delete(orderRouteService.get(id));
		}
		j.setMsg("删除参团订单成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:order:orderRoute:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(OrderRoute orderRoute, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "参团订单"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<OrderRoute> page = orderRouteService.findPage(new Page<OrderRoute>(request, response, -1), orderRoute);
    		new ExportExcel("参团订单", OrderRoute.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出参团订单记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("meiguotong:order:orderRoute:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<OrderRoute> list = ei.getDataList(OrderRoute.class);
			for (OrderRoute orderRoute : list){
				try{
					orderRouteService.save(orderRoute);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条参团订单记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条参团订单记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入参团订单失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/order/orderRoute/?repage";
    }
	
	/**
	 * 下载导入参团订单数据模板
	 */
	@RequiresPermissions("meiguotong:order:orderRoute:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "参团订单数据导入模板.xlsx";
    		List<OrderRoute> list = Lists.newArrayList(); 
    		new ExportExcel("参团订单数据", OrderRoute.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/order/orderRoute/?repage";
    }

}