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
import com.jeeplus.modules.meiguotong.entity.ordercar.OrderCarInfo;
import com.jeeplus.modules.meiguotong.service.ordercar.OrderCarInfoService;

/**
 * 车辆订单车辆详情Controller
 * @author psz
 * @version 2018-08-30
 */
@Controller
@RequestMapping(value = "${adminPath}/meiguotong/ordercar/orderCarInfo")
public class OrderCarInfoController extends BaseController {

	@Autowired
	private OrderCarInfoService orderCarInfoService;
	
	@ModelAttribute
	public OrderCarInfo get(@RequestParam(required=false) String id) {
		OrderCarInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = orderCarInfoService.get(id);
		}
		if (entity == null){
			entity = new OrderCarInfo();
		}
		return entity;
	}
	
	/**
	 * 车辆订单车辆详情列表页面
	 */
	@RequiresPermissions("meiguotong:ordercar:orderCarInfo:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/meiguotong/ordercar/orderCarInfoList";
	}
	
		/**
	 * 车辆订单车辆详情列表数据
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:ordercar:orderCarInfo:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(OrderCarInfo orderCarInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<OrderCarInfo> page = orderCarInfoService.findPage(new Page<OrderCarInfo>(request, response), orderCarInfo); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑车辆订单车辆详情表单页面
	 */
	@RequiresPermissions(value={"meiguotong:ordercar:orderCarInfo:view","meiguotong:ordercar:orderCarInfo:add","meiguotong:ordercar:orderCarInfo:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(OrderCarInfo orderCarInfo, Model model) {
		model.addAttribute("orderCarInfo", orderCarInfo);
		if(StringUtils.isBlank(orderCarInfo.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/meiguotong/ordercar/orderCarInfoForm";
	}

	/**
	 * 保存车辆订单车辆详情
	 */
	@RequiresPermissions(value={"meiguotong:ordercar:orderCarInfo:add","meiguotong:ordercar:orderCarInfo:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(OrderCarInfo orderCarInfo, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, orderCarInfo)){
			return form(orderCarInfo, model);
		}
		//新增或编辑表单保存
		orderCarInfoService.save(orderCarInfo);//保存
		addMessage(redirectAttributes, "保存车辆订单车辆详情成功");
		return "redirect:"+Global.getAdminPath()+"/meiguotong/ordercar/orderCarInfo/?repage";
	}
	
	/**
	 * 删除车辆订单车辆详情
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:ordercar:orderCarInfo:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(OrderCarInfo orderCarInfo, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		orderCarInfoService.delete(orderCarInfo);
		j.setMsg("删除车辆订单车辆详情成功");
		return j;
	}
	
	/**
	 * 批量删除车辆订单车辆详情
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:ordercar:orderCarInfo:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			orderCarInfoService.delete(orderCarInfoService.get(id));
		}
		j.setMsg("删除车辆订单车辆详情成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:ordercar:orderCarInfo:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(OrderCarInfo orderCarInfo, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "车辆订单车辆详情"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<OrderCarInfo> page = orderCarInfoService.findPage(new Page<OrderCarInfo>(request, response, -1), orderCarInfo);
    		new ExportExcel("车辆订单车辆详情", OrderCarInfo.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出车辆订单车辆详情记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("meiguotong:ordercar:orderCarInfo:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<OrderCarInfo> list = ei.getDataList(OrderCarInfo.class);
			for (OrderCarInfo orderCarInfo : list){
				try{
					orderCarInfoService.save(orderCarInfo);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条车辆订单车辆详情记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条车辆订单车辆详情记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入车辆订单车辆详情失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/ordercar/orderCarInfo/?repage";
    }
	
	/**
	 * 下载导入车辆订单车辆详情数据模板
	 */
	@RequiresPermissions("meiguotong:ordercar:orderCarInfo:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "车辆订单车辆详情数据导入模板.xlsx";
    		List<OrderCarInfo> list = Lists.newArrayList(); 
    		new ExportExcel("车辆订单车辆详情数据", OrderCarInfo.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/ordercar/orderCarInfo/?repage";
    }

}