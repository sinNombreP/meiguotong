/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.web.order;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

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
import com.jeeplus.modules.sys.entity.order.OrderComment;
import com.jeeplus.modules.sys.service.order.OrderCommentService;

/**
 * 订单评价Controller
 * @author psz
 * @version 2018-03-05
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/order/orderComment")
public class OrderCommentController extends BaseController {

	@Autowired
	private OrderCommentService orderCommentService;
	
	@ModelAttribute
	public OrderComment get(@RequestParam(required=false) String id) {
		OrderComment entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = orderCommentService.get(id);
		}
		if (entity == null){
			entity = new OrderComment();
		}
		return entity;
	}
	
	/**
	 * 订单评价列表页面
	 */
//	@RequiresPermissions("sys:order:orderComment:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/sys/order/orderCommentList";
	}
	
		/**
	 * 订单评价列表数据
	 */
	@ResponseBody
//	@RequiresPermissions("sys:order:orderComment:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(OrderComment orderComment, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<OrderComment> page = orderCommentService.findPage(new Page<OrderComment>(request, response), orderComment); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑订单评价表单页面
	 */
//	@RequiresPermissions(value={"sys:order:orderComment:view","sys:order:orderComment:add","sys:order:orderComment:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(OrderComment orderComment, Model model) {
		model.addAttribute("orderComment", orderComment);
		if(StringUtils.isBlank(orderComment.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/sys/order/orderCommentForm";
	}

	/**
	 * 保存订单评价
	 */
//	@RequiresPermissions(value={"sys:order:orderComment:add","sys:order:orderComment:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(OrderComment orderComment, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, orderComment)){
			return form(orderComment, model);
		}
		//新增或编辑表单保存
		orderCommentService.save(orderComment);//保存
		addMessage(redirectAttributes, "保存订单评价成功");
		return "redirect:"+Global.getAdminPath()+"/sys/order/orderComment/?repage";
	}
	
	/**
	 * 删除订单评价
	 */
	@ResponseBody
//	@RequiresPermissions("sys:order:orderComment:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(OrderComment orderComment, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		orderCommentService.delete(orderComment);
		j.setMsg("删除订单评价成功");
		return j;
	}
	
	/**
	 * 批量删除订单评价
	 */
	@ResponseBody
//	@RequiresPermissions("sys:order:orderComment:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			orderCommentService.delete(orderCommentService.get(id));
		}
		j.setMsg("删除订单评价成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
//	@RequiresPermissions("sys:order:orderComment:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(OrderComment orderComment, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "订单评价"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<OrderComment> page = orderCommentService.findPage(new Page<OrderComment>(request, response, -1), orderComment);
    		new ExportExcel("订单评价", OrderComment.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出订单评价记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
//	@RequiresPermissions("sys:order:orderComment:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<OrderComment> list = ei.getDataList(OrderComment.class);
			for (OrderComment orderComment : list){
				try{
					orderCommentService.save(orderComment);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条订单评价记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条订单评价记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入订单评价失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/order/orderComment/?repage";
    }
	
	/**
	 * 下载导入订单评价数据模板
	 */
//	@RequiresPermissions("sys:order:orderComment:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "订单评价数据导入模板.xlsx";
    		List<OrderComment> list = Lists.newArrayList(); 
    		new ExportExcel("订单评价数据", OrderComment.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/order/orderComment/?repage";
    }

}