/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.web.orderhotelroom;

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
import com.jeeplus.modules.meiguotong.entity.orderhotelroom.OrderHotelRoom;
import com.jeeplus.modules.meiguotong.service.orderhotelroom.OrderHotelRoomService;

/**
 * 酒店订单房间Controller
 * @author dong
 * @version 2018-10-16
 */
@Controller
@RequestMapping(value = "${adminPath}/meiguotong/orderhotelroom/orderHotelRoom")
public class OrderHotelRoomController extends BaseController {

	@Autowired
	private OrderHotelRoomService orderHotelRoomService;
	
	@ModelAttribute
	public OrderHotelRoom get(@RequestParam(required=false) String id) {
		OrderHotelRoom entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = orderHotelRoomService.get(id);
		}
		if (entity == null){
			entity = new OrderHotelRoom();
		}
		return entity;
	}
	
	/**
	 * 酒店订单房间列表页面
	 */
	@RequiresPermissions("meiguotong:orderhotelroom:orderHotelRoom:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/meiguotong/orderhotelroom/orderHotelRoomList";
	}
	
		/**
	 * 酒店订单房间列表数据
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:orderhotelroom:orderHotelRoom:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(OrderHotelRoom orderHotelRoom, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<OrderHotelRoom> page = orderHotelRoomService.findPage(new Page<OrderHotelRoom>(request, response), orderHotelRoom); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑酒店订单房间表单页面
	 */
	@RequiresPermissions(value={"meiguotong:orderhotelroom:orderHotelRoom:view","meiguotong:orderhotelroom:orderHotelRoom:add","meiguotong:orderhotelroom:orderHotelRoom:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(OrderHotelRoom orderHotelRoom, Model model) {
		model.addAttribute("orderHotelRoom", orderHotelRoom);
		if(StringUtils.isBlank(orderHotelRoom.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/meiguotong/orderhotelroom/orderHotelRoomForm";
	}

	/**
	 * 保存酒店订单房间
	 */
	@RequiresPermissions(value={"meiguotong:orderhotelroom:orderHotelRoom:add","meiguotong:orderhotelroom:orderHotelRoom:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(OrderHotelRoom orderHotelRoom, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, orderHotelRoom)){
			return form(orderHotelRoom, model);
		}
		//新增或编辑表单保存
		orderHotelRoomService.save(orderHotelRoom);//保存
		addMessage(redirectAttributes, "保存酒店订单房间成功");
		return "redirect:"+Global.getAdminPath()+"/meiguotong/orderhotelroom/orderHotelRoom/?repage";
	}
	
	/**
	 * 删除酒店订单房间
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:orderhotelroom:orderHotelRoom:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(OrderHotelRoom orderHotelRoom, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		orderHotelRoomService.delete(orderHotelRoom);
		j.setMsg("删除酒店订单房间成功");
		return j;
	}
	
	/**
	 * 批量删除酒店订单房间
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:orderhotelroom:orderHotelRoom:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			orderHotelRoomService.delete(orderHotelRoomService.get(id));
		}
		j.setMsg("删除酒店订单房间成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:orderhotelroom:orderHotelRoom:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(OrderHotelRoom orderHotelRoom, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "酒店订单房间"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<OrderHotelRoom> page = orderHotelRoomService.findPage(new Page<OrderHotelRoom>(request, response, -1), orderHotelRoom);
    		new ExportExcel("酒店订单房间", OrderHotelRoom.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出酒店订单房间记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("meiguotong:orderhotelroom:orderHotelRoom:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<OrderHotelRoom> list = ei.getDataList(OrderHotelRoom.class);
			for (OrderHotelRoom orderHotelRoom : list){
				try{
					orderHotelRoomService.save(orderHotelRoom);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条酒店订单房间记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条酒店订单房间记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入酒店订单房间失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/orderhotelroom/orderHotelRoom/?repage";
    }
	
	/**
	 * 下载导入酒店订单房间数据模板
	 */
	@RequiresPermissions("meiguotong:orderhotelroom:orderHotelRoom:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "酒店订单房间数据导入模板.xlsx";
    		List<OrderHotelRoom> list = Lists.newArrayList(); 
    		new ExportExcel("酒店订单房间数据", OrderHotelRoom.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/orderhotelroom/orderHotelRoom/?repage";
    }

}