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
import com.jeeplus.modules.meiguotong.entity.ordercar.OrderCar;
import com.jeeplus.modules.meiguotong.entity.orderhotelroom.OrderHotelRoom;
import com.jeeplus.modules.meiguotong.entity.ordermember.OrderMember;
import com.jeeplus.modules.meiguotong.service.ordercar.OrderCarService;
import com.jeeplus.modules.meiguotong.service.ordermember.OrderMemberService;
import com.jeeplus.modules.sys.entity.sysUser.SysUser;
import com.jeeplus.modules.sys.utils.UserUtils;

/**
 * 车辆订单表Controller
 * @author cdq
 * @version 2018-08-23
 */
@Controller
@RequestMapping(value = "${adminPath}/meiguotong/ordercar/orderCar")
public class OrderCarController extends BaseController {

	@Autowired
	private OrderCarService orderCarService;
	@Autowired
	private OrderMemberService orderMemberService;
	
	@ModelAttribute
	public OrderCar get(@RequestParam(required=false) String id) {
		OrderCar entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = orderCarService.get(id);
		}
		if (entity == null){
			entity = new OrderCar();
		}
		return entity;
	}
	
	/**
	 * 车辆订单表列表页面
	 */
	@RequiresPermissions("meiguotong:ordercar:orderCar:list")
	@RequestMapping(value = {"list", ""})
	public String list(Model model,Integer type,Integer ordertype) {
		List<SysUser> agent=orderCarService.findagentlist();
		model.addAttribute("agent", agent);
		model.addAttribute("type", type);
		model.addAttribute("ordertype", ordertype);
		model.addAttribute("userType",UserUtils.getUser().getUserType());
		return "modules/meiguotong/ordercar/orderCarList";
	}
	
		/**
	 * 车辆订单表列表数据
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:ordercar:orderCar:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(OrderCar orderCar, HttpServletRequest request, HttpServletResponse response, Model model) {
		orderCar.preFind();
		Page<OrderCar> page = orderCarService.findOrderCarList(new Page<OrderCar>(request, response), orderCar); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑车辆订单表表单页面
	 */
	//@RequiresPermissions(value={"meiguotong:ordercar:orderCar:view","meiguotong:ordercar:orderCar:add","meiguotong:ordercar:orderCar:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(OrderCar orderCar, Model model) {
		model.addAttribute("orderCar", orderCar);
		//出游人信息
		OrderMember orderMember = new OrderMember();
		orderMember.setTypeId(Integer.parseInt(orderCar.getOrderSys1().toString()));
		model.addAttribute("orderMemberList", orderMemberService.findList(orderMember));
		return "modules/meiguotong/ordercar/orderCarForm";
	}

	/**
	 * 保存车辆订单表
	 */
	@RequiresPermissions(value={"meiguotong:ordercar:orderCar:add","meiguotong:ordercar:orderCar:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(OrderCar orderCar, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, orderCar)){
			return form(orderCar, model);
		}
		//新增或编辑表单保存
		orderCarService.save(orderCar);//保存
		addMessage(redirectAttributes, "保存车辆订单表成功");
		return "redirect:"+Global.getAdminPath()+"/meiguotong/ordercar/orderCar/?repage";
	}
	
	@ResponseBody
	@RequestMapping(value = "changStatus")
	public AjaxJson changStatus(OrderCar orderCar, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		try {
			orderCarService.save(orderCar);
			 j.setSuccess(true);
			 switch (orderCar.getStatus()) {
			// 1.待付款2.待确定，3.待出行，4.待评价 5.已完成6.取消订单7.申请退款8退款中9退款通过10.退款不通过
				case 2:
					 j.setMsg("确认成功");
					break;
				case 6:
					 j.setMsg("取消订单成功");
					break;
				case 7:
					 j.setMsg("申请退款成功");
					break;
				case 9:
					 j.setMsg("退款成功");
					break;
				case 10:
					 j.setMsg("退款失败");
					break;
				default:
					break;
			}
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("更新状态失败");
		}
		return j;
	}
	/**
	 * 删除车辆订单表
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:ordercar:orderCar:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(OrderCar orderCar, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		orderCarService.delete(orderCar);
		j.setMsg("删除车辆订单表成功");
		return j;
	}
	
	/**
	 * 批量删除车辆订单表
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:ordercar:orderCar:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			orderCarService.delete(orderCarService.get(id));
		}
		j.setMsg("删除车辆订单表成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:ordercar:orderCar:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(OrderCar orderCar, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "车辆订单表"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<OrderCar> page = orderCarService.findPage(new Page<OrderCar>(request, response, -1), orderCar);
    		new ExportExcel("车辆订单表", OrderCar.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出车辆订单表记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("meiguotong:ordercar:orderCar:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<OrderCar> list = ei.getDataList(OrderCar.class);
			for (OrderCar orderCar : list){
				try{
					orderCarService.save(orderCar);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条车辆订单表记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条车辆订单表记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入车辆订单表失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/ordercar/orderCar/?repage";
    }
	
	/**
	 * 下载导入车辆订单表数据模板
	 */
	@RequiresPermissions("meiguotong:ordercar:orderCar:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "车辆订单表数据导入模板.xlsx";
    		List<OrderCar> list = Lists.newArrayList(); 
    		new ExportExcel("车辆订单表数据", OrderCar.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/ordercar/orderCar/?repage";
    }

}