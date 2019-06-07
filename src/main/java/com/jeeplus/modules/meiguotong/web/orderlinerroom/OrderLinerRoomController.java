/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.web.orderlinerroom;

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
import com.jeeplus.modules.meiguotong.entity.orderlinerroom.OrderLinerRoom;
import com.jeeplus.modules.meiguotong.service.orderlinerroom.OrderLinerRoomService;

/**
 * 评价房间表Controller
 * @author cdq
 * @version 2018-08-15
 */
@Controller
@RequestMapping(value = "${adminPath}/meiguotong/orderlinerroom/orderLinerRoom")
public class OrderLinerRoomController extends BaseController {

	@Autowired
	private OrderLinerRoomService orderLinerRoomService;
	
	@ModelAttribute
	public OrderLinerRoom get(@RequestParam(required=false) String id) {
		OrderLinerRoom entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = orderLinerRoomService.get(id);
		}
		if (entity == null){
			entity = new OrderLinerRoom();
		}
		return entity;
	}
	
	/**
	 * 评价房间表列表页面
	 */
	@RequiresPermissions("meiguotong:orderlinerroom:orderLinerRoom:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/meiguotong/orderlinerroom/orderLinerRoomList";
	}
	
		/**
	 * 评价房间表列表数据
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:orderlinerroom:orderLinerRoom:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(OrderLinerRoom orderLinerRoom, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<OrderLinerRoom> page = orderLinerRoomService.findPage(new Page<OrderLinerRoom>(request, response), orderLinerRoom); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑评价房间表表单页面
	 */
	@RequiresPermissions(value={"meiguotong:orderlinerroom:orderLinerRoom:view","meiguotong:orderlinerroom:orderLinerRoom:add","meiguotong:orderlinerroom:orderLinerRoom:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(OrderLinerRoom orderLinerRoom, Model model) {
		model.addAttribute("orderLinerRoom", orderLinerRoom);
		if(StringUtils.isBlank(orderLinerRoom.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/meiguotong/orderlinerroom/orderLinerRoomForm";
	}

	/**
	 * 保存评价房间表
	 */
	@RequiresPermissions(value={"meiguotong:orderlinerroom:orderLinerRoom:add","meiguotong:orderlinerroom:orderLinerRoom:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(OrderLinerRoom orderLinerRoom, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, orderLinerRoom)){
			return form(orderLinerRoom, model);
		}
		//新增或编辑表单保存
		orderLinerRoomService.save(orderLinerRoom);//保存
		addMessage(redirectAttributes, "保存评价房间表成功");
		return "redirect:"+Global.getAdminPath()+"/meiguotong/orderlinerroom/orderLinerRoom/?repage";
	}
	
	/**
	 * 删除评价房间表
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:orderlinerroom:orderLinerRoom:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(OrderLinerRoom orderLinerRoom, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		orderLinerRoomService.delete(orderLinerRoom);
		j.setMsg("删除评价房间表成功");
		return j;
	}
	
	/**
	 * 批量删除评价房间表
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:orderlinerroom:orderLinerRoom:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			orderLinerRoomService.delete(orderLinerRoomService.get(id));
		}
		j.setMsg("删除评价房间表成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:orderlinerroom:orderLinerRoom:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(OrderLinerRoom orderLinerRoom, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "评价房间表"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<OrderLinerRoom> page = orderLinerRoomService.findPage(new Page<OrderLinerRoom>(request, response, -1), orderLinerRoom);
    		new ExportExcel("评价房间表", OrderLinerRoom.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出评价房间表记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("meiguotong:orderlinerroom:orderLinerRoom:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<OrderLinerRoom> list = ei.getDataList(OrderLinerRoom.class);
			for (OrderLinerRoom orderLinerRoom : list){
				try{
					orderLinerRoomService.save(orderLinerRoom);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条评价房间表记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条评价房间表记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入评价房间表失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/orderlinerroom/orderLinerRoom/?repage";
    }
	
	/**
	 * 下载导入评价房间表数据模板
	 */
	@RequiresPermissions("meiguotong:orderlinerroom:orderLinerRoom:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "评价房间表数据导入模板.xlsx";
    		List<OrderLinerRoom> list = Lists.newArrayList(); 
    		new ExportExcel("评价房间表数据", OrderLinerRoom.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/orderlinerroom/orderLinerRoom/?repage";
    }

}