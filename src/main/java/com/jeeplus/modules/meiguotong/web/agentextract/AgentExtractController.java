/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.web.agentextract;

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
import com.jeeplus.modules.meiguotong.entity.agentextract.AgentExtract;
import com.jeeplus.modules.meiguotong.entity.hotel.OrderHotel;
import com.jeeplus.modules.meiguotong.entity.insurance.OrderInsurance;
import com.jeeplus.modules.meiguotong.entity.order.OrderRoute;
import com.jeeplus.modules.meiguotong.entity.order.OrderSys;
import com.jeeplus.modules.meiguotong.entity.ordercar.OrderCar;
import com.jeeplus.modules.meiguotong.entity.orderguide.OrderGuide;
import com.jeeplus.modules.meiguotong.entity.orderliner.OrderLiner;
import com.jeeplus.modules.meiguotong.entity.ordermember.OrderMember;
import com.jeeplus.modules.meiguotong.entity.orderscenicspot.OrderScenicSpot;
import com.jeeplus.modules.meiguotong.entity.scenicspot.ScenicSpot;
import com.jeeplus.modules.meiguotong.service.agentextract.AgentExtractService;
import com.jeeplus.modules.meiguotong.service.hotel.OrderHotelService;
import com.jeeplus.modules.meiguotong.service.insurance.OrderInsuranceService;
import com.jeeplus.modules.meiguotong.service.order.OrderRouteService;
import com.jeeplus.modules.meiguotong.service.order.OrderSysService;
import com.jeeplus.modules.meiguotong.service.ordercar.OrderCarService;
import com.jeeplus.modules.meiguotong.service.orderguide.OrderGuideService;
import com.jeeplus.modules.meiguotong.service.orderliner.OrderLinerService;
import com.jeeplus.modules.meiguotong.service.orderlinerroom.OrderLinerRoomService;
import com.jeeplus.modules.meiguotong.service.ordermember.OrderMemberService;
import com.jeeplus.modules.meiguotong.service.orderscenicspot.OrderScenicSpotService;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.utils.CodeGenUtils;
import com.jeeplus.modules.sys.utils.UserUtils;


/**
 * 代理商提现Controller
 * @author dong
 * @version 2019-05-17
 */
@Controller
@RequestMapping(value = "${adminPath}/meiguotong/agentextract/agentExtract")
public class AgentExtractController extends BaseController {

	@Autowired
	private AgentExtractService agentExtractService;
	@Autowired
	private OrderSysService orderSysService;
	@Autowired
	private OrderCarService orderCarService;
	@Autowired
    private OrderRouteService orderRouteService;
    @Autowired 
    private OrderLinerService orderLinerService;
    @Autowired
    private OrderLinerRoomService orderLinerRoomService;
    @Autowired 
    private OrderScenicSpotService orderScenicSpotService;
    @Autowired
    private OrderGuideService orderGuideService;
    @Autowired
    private OrderInsuranceService orderInsuranceService;
    @Autowired
    private OrderMemberService orderMemberService;
    @Autowired
    private OrderHotelService orderHotelService;
	
	@ModelAttribute
	public AgentExtract get(@RequestParam(required=false) String id) {
		AgentExtract entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = agentExtractService.get(id);
		}
		if (entity == null){
			entity = new AgentExtract();
		}
		return entity;
	}
	
	/**
	 * 代理商提现列表页面
	 */
	@RequiresPermissions("meiguotong:agentextract:agentExtract:list")
	@RequestMapping(value = {"list", ""})
	public String list(Model model) {
		User user=UserUtils.getUser();
		model.addAttribute("userType", user.getUserType());
		return "modules/meiguotong/agentextract/agentExtractList";
	}
	
		/**
	 * 代理商提现列表数据
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:agentextract:agentExtract:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(AgentExtract agentExtract, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user=UserUtils.getUser();
		agentExtract.setAgentid(user.getAgentid());//供应商ID
		Page<AgentExtract> page = agentExtractService.findAgentExtractByAgentId(new Page<AgentExtract>(request, response), agentExtract); 
		return getBootstrapData(page);
	}
	
	
	@RequestMapping(value = "UnExtract")
	public String agentExtractUnExtract() {
		return "modules/meiguotong/agentextract/agentExtractUnExtract";
	}
	@RequestMapping(value = "Compare")
	public String agentExtractCompare() {
		return "modules/meiguotong/agentextract/agentExtractForm1";
	}
	
	
	@ResponseBody
	@RequestMapping(value = "findOrderSysByExtract")
	public Map<String, Object> findOrderSysByExtract(OrderSys orderSys, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user=UserUtils.getUser();
		orderSys.setAgentid(user.getAgentid());
		Page<OrderSys> page = orderSysService.findOrderSysByExtract(new Page<OrderSys>(request, response), orderSys); 
		return getBootstrapData(page);
	}
	
	
	@RequestMapping(value = "findOrderSysById_Extract")
	public String findOrderSysById_Extract(AgentExtract agentExtract, Model model){
		model.addAttribute("agentExtract", agentExtract);
		List<OrderSys> orderSys=orderSysService.findOrderSysById_Extract(agentExtract.getOrderids());
		model.addAttribute("orderSys", orderSys);
		return "modules/meiguotong/agentextract/agentExtractForm1";
	}

	/**
	 * 查看，增加，编辑代理商提现表单页面
	 */
	//@RequiresPermissions(value={"meiguotong:agentextract:agentExtract:view","meiguotong:agentextract:agentExtract:add","meiguotong:agentextract:agentExtract:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(AgentExtract agentExtract, Model model) {
		model.addAttribute("agentExtract", agentExtract);
		return "modules/meiguotong/agentextract/agentExtractForm";
	}

	/**
	 * 保存代理商提现
	 */
	@RequestMapping(value = "save")
	public String save(AgentExtract agentExtract, Model model, RedirectAttributes redirectAttributes) throws Exception{
		
		agentExtract.setNo(CodeGenUtils.getNowDate());
		User user=UserUtils.getUser();
		agentExtract.setAgentid(user.getAgentid());
		agentExtract.setAgentName(user.getCompanyName());
		OrderSys orderSys=orderSysService.findSumPriceById(agentExtract.getOrderids());
		agentExtract.setCount(orderSys.getPrice().doubleValue());
		agentExtract.setExtract(orderSys.getPrice().doubleValue());
		
		agentExtractService.save(agentExtract);//新建或者编辑保存
		
		return "redirect:"+Global.getAdminPath()+"/meiguotong/agentextract/agentExtract/list";
	}
	
	@ResponseBody
	@RequestMapping(value = "changeStatus")
	public AjaxJson changeStatus(AgentExtract agentExtract, Model model) throws Exception{
		agentExtractService.changeStatus(agentExtract);//新建或者编辑保存
		AjaxJson j=new AjaxJson();
		if (agentExtract.getStatus()==2) {
			j.setMsg("同意申请成功");
		}else if (agentExtract.getStatus()==3) {
			j.setMsg("拒绝申请成功");
		}else if (agentExtract.getStatus()==4) {
			j.setMsg("完成提现");
		}
		return j;
	}
	
	@RequestMapping(value = "viewOrder")
	public String viewOrder(OrderSys orderSys, Model model,RedirectAttributes redirect) {
		OrderSys orderSys2=orderSysService.get(orderSys);
		int type=orderSys2.getType();
		OrderCar orderCar=new OrderCar();
		String page="";
		if(type==1) {
			orderCar=orderCarService.findCarInfoByOrderSys2(1,Integer.valueOf(orderSys.getId()));
			page="redirect:"+Global.getAdminPath()+"/meiguotong/ordercar/orderCar/form?id="+orderCar.getId();
		}else if(type==2) {
			orderCar=orderCarService.findCarInfoByOrderSys2(2,Integer.valueOf(orderSys.getId()));
			page="redirect:"+Global.getAdminPath()+"/meiguotong/ordercar/orderCar/form?id="+orderCar.getId();
		}else if(type==3) {
			orderCar=orderCarService.findCarInfoByOrderSys2(3,Integer.valueOf(orderSys.getId()));
			page="redirect:"+Global.getAdminPath()+"/meiguotong/ordercar/orderCar/form?id="+orderCar.getId();
		}else if(type==15) {
			orderCar=orderCarService.findCarInfoByOrderSys2(4,Integer.valueOf(orderSys.getId()));
			page="redirect:"+Global.getAdminPath()+"/meiguotong/ordercar/orderCar/form?id="+orderCar.getId();
		}else if(type==4 || type==5) {
			OrderRoute orderRoute=orderRouteService.findRouteDetailByOrderSys2(Integer.valueOf(orderSys.getId()));
			page="redirect:"+Global.getAdminPath()+"/meiguotong/order/orderRoute/ViewForm?id="+orderRoute.getId();
		}else if(type==6) {
			OrderLiner orderLiner=orderLinerService.findLinerDetailByOrderSys2(Integer.valueOf(orderSys.getId()));
			page="redirect:"+Global.getAdminPath()+"/meiguotong/orderliner/orderLiner/form?id="+orderLiner.getId();
		}else if(type==7) {
			OrderScenicSpot orderScenicSpot=orderScenicSpotService.findScenicSpotDetailByOrderid(Integer.valueOf(orderSys.getId()));
			page="redirect:"+Global.getAdminPath()+"/meiguotong/orderscenicspot/orderScenicSpot/form?id="+orderScenicSpot.getId();
		}else if(type==8) {
			OrderGuide orderGuide=orderGuideService.findGuideDetailByOrderSys2(Integer.valueOf(orderSys.getId()));
			page="redirect:"+Global.getAdminPath()+"/meiguotong/orderguide/orderGuide/ViewForm?id="+orderGuide.getId();
		}else if(type==9) {
			OrderHotel orderHotel=orderHotelService.findhotelDetailByOrderSys2(Integer.valueOf(orderSys.getId()));
			page="redirect:"+Global.getAdminPath()+"/meiguotong/hotel/orderHotel/form?id="+orderHotel.getId();
		}else if(type==10) {
			OrderInsurance orderInsurance=orderInsuranceService.findInsuranceByOrderSys2(Integer.valueOf(orderSys.getId()));
			page="redirect:"+Global.getAdminPath()+"/meiguotong/insurance/orderInsurance/form2?id="+orderInsurance.getId();
		}
		return page;
	}
	
	/**
	 * 删除代理商提现
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:agentextract:agentExtract:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(AgentExtract agentExtract, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		agentExtractService.delete(agentExtract);
		j.setMsg("删除代理商提现成功");
		return j;
	}
	
	/**
	 * 批量删除代理商提现
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:agentextract:agentExtract:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			agentExtractService.delete(agentExtractService.get(id));
		}
		j.setMsg("删除代理商提现成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:agentextract:agentExtract:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(AgentExtract agentExtract, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "代理商提现"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<AgentExtract> page = agentExtractService.findPage(new Page<AgentExtract>(request, response, -1), agentExtract);
    		new ExportExcel("代理商提现", AgentExtract.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出代理商提现记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("meiguotong:agentextract:agentExtract:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<AgentExtract> list = ei.getDataList(AgentExtract.class);
			for (AgentExtract agentExtract : list){
				try{
					agentExtractService.save(agentExtract);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条代理商提现记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条代理商提现记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入代理商提现失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/agentextract/agentExtract/?repage";
    }
	
	/**
	 * 下载导入代理商提现数据模板
	 */
	@RequiresPermissions("meiguotong:agentextract:agentExtract:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "代理商提现数据导入模板.xlsx";
    		List<AgentExtract> list = Lists.newArrayList(); 
    		new ExportExcel("代理商提现数据", AgentExtract.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/agentextract/agentExtract/?repage";
    }

}