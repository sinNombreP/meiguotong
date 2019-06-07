/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.web.orderliner;

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
import com.jeeplus.modules.meiguotong.entity.liner_line.LinerLine;
import com.jeeplus.modules.meiguotong.entity.linerroom.LinerRoom;
import com.jeeplus.modules.meiguotong.entity.linertrip.LinerTrip;
import com.jeeplus.modules.meiguotong.entity.orderliner.OrderLiner;
import com.jeeplus.modules.meiguotong.entity.orderlinerroom.OrderLinerRoom;
import com.jeeplus.modules.meiguotong.entity.ordermember.OrderMember;
import com.jeeplus.modules.meiguotong.service.insurance.InsuranceService;
import com.jeeplus.modules.meiguotong.service.liner_line.LinerLineService;
import com.jeeplus.modules.meiguotong.service.linerroom.LinerRoomService;
import com.jeeplus.modules.meiguotong.service.linertrip.LinerTripService;
import com.jeeplus.modules.meiguotong.service.orderliner.OrderLinerService;
import com.jeeplus.modules.meiguotong.service.orderlinerroom.OrderLinerRoomService;
import com.jeeplus.modules.meiguotong.service.ordermember.OrderMemberService;
import com.jeeplus.modules.sys.entity.member.Member;
import com.jeeplus.modules.sys.entity.member.MemberInformation;
import com.jeeplus.modules.sys.entity.memberincome.MemberIncome;
import com.jeeplus.modules.sys.entity.sysUser.SysUser;
import com.jeeplus.modules.sys.service.member.MemberInformationService;
import com.jeeplus.modules.sys.service.member.MemberService;
import com.jeeplus.modules.sys.service.memberincome.MemberIncomeService;
import com.jeeplus.modules.sys.service.sysUser.SysUserService;
import com.jeeplus.modules.sys.utils.UserUtils;

/**
 * 邮轮订单表Controller
 * @author cdq
 * @version 2018-08-15
 */
@Controller
@RequestMapping(value = "${adminPath}/meiguotong/orderliner/orderLiner")
public class OrderLinerController extends BaseController {

	@Autowired
	private OrderLinerService orderLinerService;
	@Autowired
	private MemberService memberService;
	@Autowired
	private MemberInformationService memberInformationService;
	@Autowired
	private LinerLineService linerLineService;
	@Autowired
	private LinerRoomService linerRoomService;
	@Autowired
	private InsuranceService insuranceService;
	@Autowired
	private OrderLinerRoomService orderLinerRoomService;
	@Autowired
	private OrderMemberService orderMemberService;
	@Autowired
	private MemberIncomeService memberIncomeService;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private LinerTripService linerTripService;
	@Autowired
	
	
	@ModelAttribute
	public OrderLiner get(@RequestParam(required=false) String id) {
		OrderLiner entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = orderLinerService.get(id);
		}
		if (entity == null){
			entity = new OrderLiner();
		}
		return entity;
	}
	
	/**
	 * 邮轮订单表列表页面
	 */
	@RequiresPermissions("meiguotong:orderliner:orderLiner:list")
	@RequestMapping(value = {"list", ""})
	public String list(Model model) {
		List<SysUser> sysUserList = sysUserService.getNameByType(4);
		model.addAttribute("sysUserList",sysUserList);
		model.addAttribute("userType",UserUtils.getUser().getUserType());
		return "modules/meiguotong/orderliner/orderLinerList";
	}
	/**
	 * 邮轮订单表列表页面
	 */
	@RequiresPermissions("meiguotong:orderliner:orderLiner:list")
	@RequestMapping(value = {"AfterSale"})
	public String AfterSale(Model model) {
		List<SysUser> sysUserList = sysUserService.getNameByType(4);
		model.addAttribute("sysUserList",sysUserList);
		model.addAttribute("userType",UserUtils.getUser().getUserType());
		return "modules/meiguotong/orderliner/orderLinerAfterSaleList";
	}
	
		/**
	 * 邮轮订单表列表数据
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:orderliner:orderLiner:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(OrderLiner orderLiner, HttpServletRequest request, HttpServletResponse response, Model model) {
		orderLiner.preFind();
		Page<OrderLiner> page = orderLinerService.findPage(new Page<OrderLiner>(request, response), orderLiner); 
		return getBootstrapData(page);
	}
	/**
 * 邮轮售后订单表列表数据
 */
@ResponseBody
@RequiresPermissions("meiguotong:orderliner:orderLiner:list")
@RequestMapping(value = "AfterSaleData")
public Map<String, Object> AfterSaleData(OrderLiner orderLiner, HttpServletRequest request, HttpServletResponse response, Model model) {
	orderLiner.preFind();
	Page<OrderLiner> page = orderLinerService.AfterSale(new Page<OrderLiner>(request, response), orderLiner); 
	return getBootstrapData(page);
}

	/**
	 * 查看，增加，编辑邮轮订单表表单页面
	 */
	@RequiresPermissions(value={"meiguotong:orderliner:orderLiner:view","meiguotong:orderliner:orderLiner:add","meiguotong:orderliner:orderLiner:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(OrderLiner orderLiner, Model model) {
		//Member表的数据
		Member getMember=new Member();
		getMember.setId(orderLiner.getMemberId());
		Member member=memberService.get(getMember);
		model.addAttribute("member", member);
		//MemberInformation表的数据
		MemberInformation getMemberInformation=new MemberInformation();
		getMemberInformation.setMemberid(orderLiner.getMemberId());
		MemberInformation memberInformation =memberInformationService.findByMemberid(getMemberInformation);
		model.addAttribute("memberInformation", memberInformation);
		//linerLine(航线表)的数据
		List<LinerTrip> trips =linerTripService.findTripByLinerLineId(orderLiner.getLinerLineId());
		model.addAttribute("trips", trips);
//		LinerLine getlinerLine =new LinerLine();
//		getlinerLine.setMemberId(Integer.parseInt(orderLiner.getMemberId())); 
//		LinerLine linerLine=linerLineService.findByMemberid(getlinerLine);
//		model.addAttribute("linerLine", linerLine);
		//邮轮房间的数据
		model.addAttribute("linerRoom", orderLinerRoomService.findRoomInfoByOrderId(Integer.valueOf(orderLiner.getId())));
		/*LinerRoom getlinerRoom =new LinerRoom();
		getlinerRoom.setLinerLineId(orderLiner.getLinerLineId());
		List<LinerRoom> linerRoom=linerRoomService.getLinerRoomList(getlinerRoom);
		model.addAttribute("linerRoom", linerRoom);
		OrderLinerRoom getorderLinerRoom=new OrderLinerRoom();
		try{
		getorderLinerRoom.setRoomId(Integer.parseInt(linerRoom.getId()));
		List<OrderLinerRoom> orderlinerRoomList=orderLinerRoomService.findListById(getorderLinerRoom);
		model.addAttribute("orderlinerRoomList", orderlinerRoomList);
		}catch(Exception e){			
		}*/
		//邮轮订单出行人信息
		OrderMember orderMember=new OrderMember();
		orderMember.setTypeId(Integer.parseInt(orderLiner.getId()));
		List<OrderMember> orderMemberList=orderMemberService.findListByTyid(orderMember);
		model.addAttribute("orderMemberList", orderMemberList);
		
//		//支付信息数据
//		MemberIncome getMemberIncom =new MemberIncome();
//		getMemberIncom.setOrderId(Integer.parseInt(orderLiner.getId()));
//		MemberIncome memberIncome =memberIncomeService.findByOrderid(getMemberIncom);
//		model.addAttribute("memberIncome", memberIncome);
		model.addAttribute("orderLiner", orderLiner);
		if(StringUtils.isBlank(orderLiner.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/meiguotong/orderliner/orderLinerForm";
	}

	/**
	 * 保存邮轮订单表
	 */
	@RequiresPermissions(value={"meiguotong:orderliner:orderLiner:add","meiguotong:orderliner:orderLiner:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(OrderLiner orderLiner, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, orderLiner)){
			return form(orderLiner, model);
		}
		//新增或编辑表单保存
		orderLinerService.save(orderLiner);//保存
		addMessage(redirectAttributes, "保存邮轮订单表成功");
		return "redirect:"+Global.getAdminPath()+"/meiguotong/orderliner/orderLiner/?repage";
	}
	/**
	 * 更改状态
	 * @param orderLiner
	 * @param redirectAttributes
	 * @return
	 */
	    @ResponseBody
		@RequestMapping(value = "status")
		public AjaxJson status(OrderLiner orderLiner, RedirectAttributes redirectAttributes) {
			AjaxJson j = new AjaxJson();
			try {
				orderLinerService.status(orderLiner);
				 j.setSuccess(true);
				 switch (orderLiner.getStatus()) {
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
	 * 删除邮轮订单表
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:orderliner:orderLiner:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(OrderLiner orderLiner, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		orderLinerService.delete(orderLiner);
		j.setMsg("删除邮轮订单表成功");
		return j;
	}
	
	/**
	 * 批量删除邮轮订单表
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:orderliner:orderLiner:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			orderLinerService.delete(orderLinerService.get(id));
		}
		j.setMsg("删除邮轮订单表成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:orderliner:orderLiner:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(OrderLiner orderLiner, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "邮轮订单表"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<OrderLiner> page = orderLinerService.findPage(new Page<OrderLiner>(request, response, -1), orderLiner);
    		new ExportExcel("邮轮订单表", OrderLiner.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出邮轮订单表记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("meiguotong:orderliner:orderLiner:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<OrderLiner> list = ei.getDataList(OrderLiner.class);
			for (OrderLiner orderLiner : list){
				try{
					orderLinerService.save(orderLiner);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条邮轮订单表记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条邮轮订单表记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入邮轮订单表失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/orderliner/orderLiner/?repage";
    }
	
	/**
	 * 下载导入邮轮订单表数据模板
	 */
	@RequiresPermissions("meiguotong:orderliner:orderLiner:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "邮轮订单表数据导入模板.xlsx";
    		List<OrderLiner> list = Lists.newArrayList(); 
    		new ExportExcel("邮轮订单表数据", OrderLiner.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/orderliner/orderLiner/?repage";
    }

}