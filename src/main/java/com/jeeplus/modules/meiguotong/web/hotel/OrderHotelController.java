/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.web.hotel;

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
import com.jeeplus.modules.meiguotong.entity.orderhotelroom.OrderHotelRoom;
import com.jeeplus.modules.meiguotong.entity.ordermember.OrderMember;
import com.jeeplus.modules.meiguotong.service.hotel.OrderHotelService;
import com.jeeplus.modules.meiguotong.service.order.OrderSysService;
import com.jeeplus.modules.meiguotong.service.orderhotelroom.OrderHotelRoomService;
import com.jeeplus.modules.meiguotong.service.ordermember.OrderMemberService;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.entity.sysUser.SysUser;
import com.jeeplus.modules.sys.service.sysUser.SysUserService;
import com.jeeplus.modules.sys.utils.UserUtils;

/**
 * 酒店订单Controller
 * @author psz
 * @version 2018-08-23
 */
@Controller
@RequestMapping(value = "${adminPath}/meiguotong/hotel/orderHotel")
public class OrderHotelController extends BaseController {

	@Autowired
	private OrderHotelService orderHotelService;
	@Autowired
	private OrderSysService orderSysService;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private OrderMemberService orderMemberService;
	@Autowired
	private OrderHotelRoomService orderHotelRoomService;
	
	@ModelAttribute
	public OrderHotel get(@RequestParam(required=false) String id) {
		OrderHotel entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = orderHotelService.get(id);
		}
		if (entity == null){
			entity = new OrderHotel();
		}
		return entity;
	}
	
	/**
	 * 酒店订单列表页面
	 */
	@RequiresPermissions("meiguotong:hotel:orderHotel:list")
	@RequestMapping(value = {"list", ""})
	public String list(OrderHotel orderHotel, Model model) {
		//供应商
		List<SysUser> sysUserList = sysUserService.getNameByType(6);
		model.addAttribute("sysUserList",sysUserList);
		model.addAttribute("userType",UserUtils.getUser().getUserType());
		return "modules/meiguotong/hotel/orderHotelList";
	}
	
		/**
	 * 酒店订单列表数据
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:hotel:orderHotel:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(OrderHotel orderHotel, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<OrderHotel> page = orderHotelService.findPage(new Page<OrderHotel>(request, response), orderHotel); 
		User user = UserUtils.getUser();
		if(user.getUserType()==2) {
			orderHotel.setAgentid(user.getAgentid());
		}
		return getBootstrapData(page);
	}

	
	/** 
	* @Title: updateStatus 
	* @Description: 确定订单
	* @author 彭善智
	* @date 2018年8月17日下午5:59:55
	*/ 
	@ResponseBody
	@RequestMapping(value = "sureOrder")
	public AjaxJson sureOrder(OrderHotel orderHotel, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		orderHotel.setStatus(3);
		orderHotelService.save(orderHotel);
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
	public AjaxJson cancelOrder(OrderHotel orderHotel, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		orderHotel.setStatus(6);
		orderHotelService.save(orderHotel);
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
	public AjaxJson refundOrder(OrderHotel orderHotel, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		orderHotel.setStatus(7);
		orderHotelService.save(orderHotel);
		j.setMsg("已成功申请退款");
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
	public AjaxJson agreeOrder(OrderHotel orderHotel, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		orderHotel.setStatus(8);
		orderHotelService.save(orderHotel);
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
	public AjaxJson confirmorder(OrderHotel orderHotel, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		orderHotel.setStatus(9);
		orderHotelService.save(orderHotel);
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
	public AjaxJson refuseOrder(OrderHotel orderHotel, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		orderHotel.setStatus(10);
		orderHotelService.save(orderHotel);
		j.setMsg("退款失败");
		return j;
	}
	
	/**
	 * 查看，增加，编辑酒店订单表单页面
	 */
	//@RequiresPermissions(value={"meiguotong:hotel:orderHotel:view","meiguotong:hotel:orderHotel:add","meiguotong:hotel:orderHotel:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(OrderHotel orderHotel, Model model) {
		model.addAttribute("orderHotel", orderHotel);
		//出游人信息
		OrderMember orderMember = new OrderMember();
		orderMember.setTypeId(Integer.parseInt(orderHotel.getOrderSys1().toString()));
		List<OrderHotelRoom> rooms =orderHotelRoomService.findByOrderhotelID(Integer.valueOf(orderHotel.getId()));
		model.addAttribute("rooms", rooms);
		model.addAttribute("orderMemberList", orderMemberService.findList(orderMember));
		return "modules/meiguotong/hotel/orderHotelForm";
	}

	
	/**
	 * 保存酒店订单
	 */
	@RequiresPermissions(value={"meiguotong:hotel:orderHotel:add","meiguotong:hotel:orderHotel:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(OrderHotel orderHotel, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, orderHotel)){
			return form(orderHotel, model);
		}
		//新增或编辑表单保存
		orderHotelService.save(orderHotel);//保存
		addMessage(redirectAttributes, "保存酒店订单成功");
		return "redirect:"+Global.getAdminPath()+"/meiguotong/hotel/orderHotel/?repage";
	}
	
	/**
	 * 删除酒店订单
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:hotel:orderHotel:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(OrderHotel orderHotel, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		orderHotelService.delete(orderHotel);
		j.setMsg("删除酒店订单成功");
		return j;
	}
	
	/**
	 * 批量删除酒店订单
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:hotel:orderHotel:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			orderHotelService.delete(orderHotelService.get(id));
		}
		j.setMsg("删除酒店订单成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:hotel:orderHotel:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(OrderHotel orderHotel, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "酒店订单"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<OrderHotel> page = orderHotelService.findPage(new Page<OrderHotel>(request, response, -1), orderHotel);
    		new ExportExcel("酒店订单", OrderHotel.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出酒店订单记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("meiguotong:hotel:orderHotel:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<OrderHotel> list = ei.getDataList(OrderHotel.class);
			for (OrderHotel orderHotel : list){
				try{
					orderHotelService.save(orderHotel);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条酒店订单记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条酒店订单记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入酒店订单失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/hotel/orderHotel/?repage";
    }
	
	/**
	 * 下载导入酒店订单数据模板
	 */
	@RequiresPermissions("meiguotong:hotel:orderHotel:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "酒店订单数据导入模板.xlsx";
    		List<OrderHotel> list = Lists.newArrayList(); 
    		new ExportExcel("酒店订单数据", OrderHotel.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/hotel/orderHotel/?repage";
    }

}