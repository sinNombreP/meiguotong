/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.web.orderscenicspot;

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
import com.jeeplus.modules.meiguotong.entity.insurance.Insurance;
import com.jeeplus.modules.meiguotong.entity.insurance.OrderInsurance;
import com.jeeplus.modules.meiguotong.entity.order.OrderSys;
import com.jeeplus.modules.meiguotong.entity.orderhotelroom.OrderHotelRoom;
import com.jeeplus.modules.meiguotong.entity.orderliner.OrderLiner;
import com.jeeplus.modules.meiguotong.entity.ordermember.OrderMember;
import com.jeeplus.modules.meiguotong.entity.orderscenicspot.OrderScenicSpot;
import com.jeeplus.modules.meiguotong.service.insurance.InsuranceService;
import com.jeeplus.modules.meiguotong.service.insurance.OrderInsuranceService;
import com.jeeplus.modules.meiguotong.service.order.OrderSysService;
import com.jeeplus.modules.meiguotong.service.ordermember.OrderMemberService;
import com.jeeplus.modules.meiguotong.service.orderscenicspot.OrderScenicSpotService;
import com.jeeplus.modules.sys.entity.member.Member;
import com.jeeplus.modules.sys.entity.member.MemberInformation;
import com.jeeplus.modules.sys.entity.sysUser.SysUser;
import com.jeeplus.modules.sys.service.member.MemberInformationService;
import com.jeeplus.modules.sys.service.member.MemberService;
import com.jeeplus.modules.sys.service.sysUser.SysUserService;
import com.jeeplus.modules.sys.utils.UserUtils;

/**
 * 景点评论Controller
 * @author cdq
 * @version 2018-08-17
 */
@Controller
@RequestMapping(value = "${adminPath}/meiguotong/orderscenicspot/orderScenicSpot")
public class OrderScenicSpotController extends BaseController {

	@Autowired
	private OrderScenicSpotService orderScenicSpotService;
	@Autowired
	private MemberService memberService;
	@Autowired
	private MemberInformationService memberInformationService;
	@Autowired
	private OrderSysService orderSysService;
	@Autowired
	private InsuranceService insuranceService;
	@Autowired
	private OrderInsuranceService orderInsuranceService;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private OrderMemberService orderMemberService;
	
	@ModelAttribute
	public OrderScenicSpot get(@RequestParam(required=false) String id) {
		OrderScenicSpot entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = orderScenicSpotService.get(id);
		}
		if (entity == null){
			entity = new OrderScenicSpot();
		}
		return entity;
	}
	
	/**
	 * 景点评论列表页面
	 */
	@RequiresPermissions("meiguotong:orderscenicspot:orderScenicSpot:list")
	@RequestMapping(value = {"list", ""})
	public String list(Model model) {
		List<SysUser> agent=sysUserService.getNameByType(5);
		model.addAttribute("agent", agent);
		model.addAttribute("userType",UserUtils.getUser().getUserType());
		return "modules/meiguotong/orderscenicspot/orderScenicSpotList";
	}
	/**
	 * 售后景点评论列表页面
	 */
	@RequiresPermissions("meiguotong:orderscenicspot:orderScenicSpot:list")
	@RequestMapping(value = {"AfterSaleOrderScenicSpot"})
	public String AfterSaleOrderScenicSpot(Model model) {
		List<SysUser> agent=sysUserService.getNameByType(5);
		model.addAttribute("agent", agent);
		model.addAttribute("userType",UserUtils.getUser().getUserType());
		return "modules/meiguotong/orderscenicspot/AfterSaleOrderScenicSpotList";
	}
		/**
	 * 景点评论列表数据
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:orderscenicspot:orderScenicSpot:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(OrderScenicSpot orderScenicSpot, HttpServletRequest request, HttpServletResponse response, Model model) {
		orderScenicSpot.preFind();
		Page<OrderScenicSpot> page = orderScenicSpotService.findPage(new Page<OrderScenicSpot>(request, response), orderScenicSpot); 
		return getBootstrapData(page);
	}
	/**
 * 售后景点评论列表数据
 */
@ResponseBody
@RequiresPermissions("meiguotong:orderscenicspot:orderScenicSpot:list")
@RequestMapping(value = "AfterSaleData")
public Map<String, Object> AfterSaleData(OrderScenicSpot orderScenicSpot, HttpServletRequest request, HttpServletResponse response, Model model) {
	orderScenicSpot.preFind();
	Page<OrderScenicSpot> page = orderScenicSpotService.AfterSale(new Page<OrderScenicSpot>(request, response), orderScenicSpot); 
	return getBootstrapData(page);
}
	/**
	 * 查看景点评论表单页面
	 */
	@RequiresPermissions(value={"meiguotong:orderscenicspot:orderScenicSpot:view","meiguotong:orderscenicspot:orderScenicSpot:add","meiguotong:orderscenicspot:orderScenicSpot:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(OrderScenicSpot orderScenicSpot, Model model) {
		//下单人信息
		Member getMember=new Member();
		getMember.setId(orderScenicSpot.getMemberId().toString());
		Member member=memberService.get(getMember);
		model.addAttribute("member", member);
		MemberInformation getMemberInformation =new MemberInformation();
		getMemberInformation.setMemberid(orderScenicSpot.getMemberId().toString());
		MemberInformation memberInformation=memberInformationService.findByMemberid(getMemberInformation);
		model.addAttribute("memberInformation", memberInformation);
		
		//出游人信息
		OrderMember orderMember = new OrderMember();
		orderMember.setTypeId(Integer.parseInt(orderScenicSpot.getOrderSys1().toString()));
		model.addAttribute("orderMemberList", orderMemberService.findList(orderMember));
		//保险方案信息
		/*OrderSys orderSys=new OrderSys();
		orderSys.setOrderid(Integer.parseInt(orderScenicSpot.getId()));
		orderSys.setType(7);
		OrderSys orderSys1=orderSysService.findInsurance(orderSys);
		OrderSys orderSys2=new OrderSys();
		orderSys2.setFatherid(orderSys1.getFatherid());
		orderSys2.setType(10);
		OrderSys orderSys3=orderSysService.findInsurance1(orderSys2);
		OrderInsurance orderInsurance=new OrderInsurance();
		orderInsurance.setId(orderSys3.getOrderid().toString());
		OrderInsurance orderInsurance1=orderInsuranceService.get(orderInsurance);	
		model.addAttribute("insurance1", orderInsurance1);
		Insurance insurance=new Insurance();
		insurance.setId(orderInsurance1.getInsuranceid().toString());
		Insurance insurance1=insuranceService.get(insurance);
		model.addAttribute("insurance1", insurance1);*/
		
		model.addAttribute("orderScenicSpot", orderScenicSpot);
		if(StringUtils.isBlank(orderScenicSpot.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/meiguotong/orderscenicspot/orderScenicSpotForm";
	}
	/**
	 * 查看售后景点评论表单页面
	 */
	@RequiresPermissions(value={"meiguotong:orderscenicspot:orderScenicSpot:view","meiguotong:orderscenicspot:orderScenicSpot:add","meiguotong:orderscenicspot:orderScenicSpot:edit"},logical=Logical.OR)
	@RequestMapping(value = "AfterSaleForm")
	public String AfterSaleForm(OrderScenicSpot orderScenicSpot, Model model) {
		//下单人信息
		Member getMember=new Member();
		getMember.setId(orderScenicSpot.getMemberId().toString());
		Member member=memberService.get(getMember);
		model.addAttribute("member", member);
		MemberInformation getMemberInformation =new MemberInformation();
		getMemberInformation.setMemberid(orderScenicSpot.getMemberId().toString());
		MemberInformation memberInformation=memberInformationService.findByMemberid(getMemberInformation);
		model.addAttribute("memberInformation", memberInformation);
		//保险方案信息
		OrderScenicSpot   orderInsurance =orderScenicSpotService.getOrderInsurance();
		model.addAttribute("orderInsurance", orderInsurance);
		model.addAttribute("orderScenicSpot", orderScenicSpot);
		if(StringUtils.isBlank(orderScenicSpot.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/meiguotong/orderscenicspot/orderScenicSpotForm";
	}
	/**
	 * 保存景点评论
	 */
	@RequiresPermissions(value={"meiguotong:orderscenicspot:orderScenicSpot:add","meiguotong:orderscenicspot:orderScenicSpot:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(OrderScenicSpot orderScenicSpot, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, orderScenicSpot)){
			return form(orderScenicSpot, model);
		}
		//新增或编辑表单保存
		orderScenicSpotService.save(orderScenicSpot);//保存
		addMessage(redirectAttributes, "保存景点评论成功");
		return "redirect:"+Global.getAdminPath()+"/meiguotong/orderscenicspot/orderScenicSpot/?repage";
	}
	/**
	 * 
	 * @param orderLiner
	 * @param redirectAttributes
	 * @return
	 */
    @ResponseBody
	@RequestMapping(value = "status")
	public AjaxJson status(OrderScenicSpot orderScenicSpot, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
			orderScenicSpotService.status(orderScenicSpot);
			 j.setSuccess(true);
			 switch (orderScenicSpot.getStatus()) {
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
	 * 删除景点评论
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:orderscenicspot:orderScenicSpot:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(OrderScenicSpot orderScenicSpot, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		orderScenicSpotService.delete(orderScenicSpot);
		j.setMsg("删除景点评论成功");
		return j;
	}
	
	/**
	 * 批量删除景点评论
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:orderscenicspot:orderScenicSpot:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			orderScenicSpotService.delete(orderScenicSpotService.get(id));
		}
		j.setMsg("删除景点评论成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:orderscenicspot:orderScenicSpot:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(OrderScenicSpot orderScenicSpot, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "景点评论"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<OrderScenicSpot> page = orderScenicSpotService.findPage(new Page<OrderScenicSpot>(request, response, -1), orderScenicSpot);
    		new ExportExcel("景点评论", OrderScenicSpot.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出景点评论记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("meiguotong:orderscenicspot:orderScenicSpot:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<OrderScenicSpot> list = ei.getDataList(OrderScenicSpot.class);
			for (OrderScenicSpot orderScenicSpot : list){
				try{
					orderScenicSpotService.save(orderScenicSpot);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条景点评论记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条景点评论记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入景点评论失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/orderscenicspot/orderScenicSpot/?repage";
    }
	
	/**
	 * 下载导入景点评论数据模板
	 */
	@RequiresPermissions("meiguotong:orderscenicspot:orderScenicSpot:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "景点评论数据导入模板.xlsx";
    		List<OrderScenicSpot> list = Lists.newArrayList(); 
    		new ExportExcel("景点评论数据", OrderScenicSpot.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/orderscenicspot/orderScenicSpot/?repage";
    }

}