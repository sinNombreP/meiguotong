/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.web.coupon;

import java.util.ArrayList;
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
import com.jeeplus.modules.sys.entity.coupon.Coupon;
import com.jeeplus.modules.sys.service.coupon.CouponService;

/**
 * 优惠卷管理Controller
 * @author xudemo
 * @version 2018-03-08
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/coupon")
public class CouponController extends BaseController {

	@Autowired
	private CouponService couponService;
	
	@ModelAttribute
	public Coupon get(@RequestParam(required=false) String id) {
		Coupon entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = couponService.get(id);
		}
		if (entity == null){
			entity = new Coupon();
		}
		return entity;
	}
	
	/**
	 * 优惠卷管理列表页面
	 */
	@RequiresPermissions("sys:coupon:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/sys/coupon/couponList";
	}
	
		/**
	 * 优惠卷管理列表数据
	 */
	@ResponseBody
	@RequiresPermissions("sys:coupon:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Coupon coupon, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Coupon> page = couponService.findPage(new Page<Coupon>(request, response), coupon); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑优惠卷管理表单页面
	 */
	@RequiresPermissions(value={"sys:coupon:view","sys:coupon:add","sys:coupon:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Coupon coupon,String view, Model model) {
		//获取优惠券可使用的类别
		String label=coupon.getCategory();
		/*List<ProductLabel> labelUseList=new ArrayList<>();
		String[] labels=null;
		if(label!=null&&!"".equals(label)){
			labels=label.split(",");
			if(labels!=null){
				for(String id:labels){
					labelUseList.add(productLabelMapper.get(id));
				}
			}
		}
		//获取优惠券未使用的类别
		List<ProductLabel> labelList = productLabelMapper.getLabelList(labels);*/
		model.addAttribute("coupon", coupon);
		/*model.addAttribute("labelUseList", labelUseList);
		model.addAttribute("labelList", labelList);*/
		model.addAttribute("labelUseList", null);
		model.addAttribute("labelList", null);
		if(StringUtils.isBlank(coupon.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		if(view.equals("1")){
			model.addAttribute("isAdd", false);
		}
		if(view.equals("0")){
			model.addAttribute("isAdd", true);
		}
		return "modules/sys/coupon/couponForm";
	}


	/**
	 * 保存优惠卷管理
	 */
	@RequiresPermissions(value={"sys:coupon:add","sys:coupon:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(Coupon coupon, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, coupon)){
			return form(coupon,"0",model);
		}
		//新增或编辑表单保存
		couponService.save(coupon);//保存
		addMessage(redirectAttributes, "保存优惠卷管理成功");
		return "redirect:"+Global.getAdminPath()+"/sys/coupon/?repage";
	}
	
	/**
	 * 删除优惠卷管理
	 */
	@ResponseBody
	@RequiresPermissions("sys:coupon:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(Coupon coupon, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		couponService.delete(coupon);
		j.setMsg("删除优惠卷管理成功");
		return j;
	}
	
	/**
	 * 更改优惠券状态
	 */
	@ResponseBody
	@RequestMapping(value = "status")
	public AjaxJson status(Coupon coupon, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		if(coupon.getStatus()==1){
			coupon.setStatus(2);
		}else if(coupon.getStatus()==2){
			coupon.setStatus(1);
		}
		coupon.preUpdate();
		couponService.status(coupon);
		j.setMsg("更改状态成功");
		return j;
	}
	
	/**
	 * 批量删除优惠卷管理
	 */
	@ResponseBody
	@RequiresPermissions("sys:coupon:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			couponService.delete(couponService.get(id));
		}
		j.setMsg("删除优惠卷管理成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("sys:coupon:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(Coupon coupon, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "优惠卷管理"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Coupon> page = couponService.findPage(new Page<Coupon>(request, response, -1), coupon);
    		new ExportExcel("优惠卷管理", Coupon.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出优惠卷管理记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("sys:coupon:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Coupon> list = ei.getDataList(Coupon.class);
			for (Coupon coupon : list){
				try{
					couponService.save(coupon);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条优惠卷管理记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条优惠卷管理记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入优惠卷管理失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/coupon/?repage";
    }
	
	/**
	 * 下载导入优惠卷管理数据模板
	 */
	@RequiresPermissions("sys:coupon:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "优惠卷管理数据导入模板.xlsx";
    		List<Coupon> list = Lists.newArrayList(); 
    		new ExportExcel("优惠卷管理数据", Coupon.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/coupon/?repage";
    }

}