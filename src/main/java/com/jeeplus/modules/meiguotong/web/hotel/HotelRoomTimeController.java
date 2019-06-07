/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.web.hotel;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import com.jeeplus.modules.meiguotong.service.hotelroomdevice.HotelRoomDeviceService;
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
import com.jeeplus.modules.meiguotong.entity.hotel.HotelRoomTime;
import com.jeeplus.modules.meiguotong.service.hotel.HotelRoomTimeService;

/**
 * 酒店类型设置Controller
 * @author psz
 * @version 2018-08-21
 */
@Controller
@RequestMapping(value = "${adminPath}/meiguotong/hotel/hotelRoomTime")
public class HotelRoomTimeController extends BaseController {

	@Autowired
	private HotelRoomTimeService hotelRoomTimeService;

	
	@ModelAttribute
	public HotelRoomTime get(@RequestParam(required=false) String id) {
		HotelRoomTime entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = hotelRoomTimeService.get(id);
		}
		if (entity == null){
			entity = new HotelRoomTime();
		}
		return entity;
	}
	
	/**
	 * 酒店类型设置列表页面
	 */
	@RequiresPermissions("meiguotong:hotel:hotelRoomTime:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/meiguotong/hotel/hotelRoomTimeList";
	}
	
		/**
	 * 酒店类型设置列表数据
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:hotel:hotelRoomTime:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(HotelRoomTime hotelRoomTime, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<HotelRoomTime> page = hotelRoomTimeService.findPage(new Page<HotelRoomTime>(request, response), hotelRoomTime); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑酒店类型设置表单页面
	 */
	@RequiresPermissions(value={"meiguotong:hotel:hotelRoomTime:view","meiguotong:hotel:hotelRoomTime:add","meiguotong:hotel:hotelRoomTime:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(HotelRoomTime hotelRoomTime, Model model) {
		model.addAttribute("hotelRoomTime", hotelRoomTime);
		if(StringUtils.isBlank(hotelRoomTime.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/meiguotong/hotel/hotelRoomTimeForm";
	}

	/**
	 * 保存酒店类型设置
	 */
	@RequiresPermissions(value={"meiguotong:hotel:hotelRoomTime:add","meiguotong:hotel:hotelRoomTime:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(HotelRoomTime hotelRoomTime, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, hotelRoomTime)){
			return form(hotelRoomTime, model);
		}
		//新增或编辑表单保存
		hotelRoomTimeService.save(hotelRoomTime);//保存
		addMessage(redirectAttributes, "保存酒店类型设置成功");
		return "redirect:"+Global.getAdminPath()+"/meiguotong/hotel/hotelRoomTime/?repage";
	}
	
	/**
	 * 删除酒店类型设置
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:hotel:hotelRoomTime:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(HotelRoomTime hotelRoomTime, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		hotelRoomTimeService.delete(hotelRoomTime);
		j.setMsg("删除酒店类型设置成功");
		return j;
	}
	
	/**
	 * 批量删除酒店类型设置
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:hotel:hotelRoomTime:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			hotelRoomTimeService.delete(hotelRoomTimeService.get(id));
		}
		j.setMsg("删除酒店类型设置成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:hotel:hotelRoomTime:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(HotelRoomTime hotelRoomTime, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "酒店类型设置"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<HotelRoomTime> page = hotelRoomTimeService.findPage(new Page<HotelRoomTime>(request, response, -1), hotelRoomTime);
    		new ExportExcel("酒店类型设置", HotelRoomTime.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出酒店类型设置记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("meiguotong:hotel:hotelRoomTime:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<HotelRoomTime> list = ei.getDataList(HotelRoomTime.class);
			for (HotelRoomTime hotelRoomTime : list){
				try{
					hotelRoomTimeService.save(hotelRoomTime);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条酒店类型设置记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条酒店类型设置记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入酒店类型设置失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/hotel/hotelRoomTime/?repage";
    }
	
	/**
	 * 下载导入酒店类型设置数据模板
	 */
	@RequiresPermissions("meiguotong:hotel:hotelRoomTime:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "酒店类型设置数据导入模板.xlsx";
    		List<HotelRoomTime> list = Lists.newArrayList(); 
    		new ExportExcel("酒店类型设置数据", HotelRoomTime.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/hotel/hotelRoomTime/?repage";
    }

}