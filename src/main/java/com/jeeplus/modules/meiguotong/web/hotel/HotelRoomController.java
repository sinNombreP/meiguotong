/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.web.hotel;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import com.jeeplus.modules.meiguotong.entity.hotelroomdevice.HotelRoomDevice;
import com.jeeplus.modules.meiguotong.entity.product.WeekDate;
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
import com.jeeplus.common.config.Global;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.modules.meiguotong.entity.comcity.ComCity;
import com.jeeplus.modules.meiguotong.entity.comlanguage.ComLanguage;
import com.jeeplus.modules.meiguotong.entity.hotel.HotelRoom;
import com.jeeplus.modules.meiguotong.service.comlanguage.ComLanguageService;
import com.jeeplus.modules.meiguotong.service.hotel.HotelRoomService;
import com.jeeplus.modules.meiguotong.service.product.RouteService;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.utils.UserUtils;

/**
 * 酒店管理Controller
 * @author PSZ
 * @version 2018-08-21
 */
@Controller
@RequestMapping(value = "${adminPath}/meiguotong/hotel/hotelRoom")
public class HotelRoomController extends BaseController {

	@Autowired
	private HotelRoomService hotelRoomService;
	@Autowired
	private RouteService routeService;
	@Autowired
	private ComLanguageService comLanguageService;
	
	@ModelAttribute
	public HotelRoom get(@RequestParam(required=false) String id) {
		HotelRoom entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = hotelRoomService.get(id);
		}
		if (entity == null){
			entity = new HotelRoom();
		}
		return entity;
	}
	
	/**
	 * 酒店管理列表页面
	 */
	@RequiresPermissions("meiguotong:hotel:hotelRoom:list")
	@RequestMapping(value = {"list", ""})
	public String list(HotelRoom hotelRoom, Model model) {
		//城市
		HotelRoom h = new HotelRoom();
		User user = UserUtils.getUser();
		if(user.getUserType()==2) {
			hotelRoom.setAgentid(user.getAgentid());
		}
		List<HotelRoom> hotelRoomList = hotelRoomService.findListByAgentid(h);
		model.addAttribute("hotelRoomList",hotelRoomList);
		model.addAttribute("userType",user.getUserType());
		return "modules/meiguotong/hotel/hotelRoomList";
	}
	
		/**
	 * 酒店管理列表数据
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:hotel:hotelRoom:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(HotelRoom hotelRoom, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		if(user.getUserType()==2) {
			hotelRoom.setAgentid(user.getAgentid());
		}
		Page<HotelRoom> page = hotelRoomService.findPage(new Page<HotelRoom>(request, response), hotelRoom); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑酒店管理表单页面
	 */
	@RequiresPermissions(value={"meiguotong:hotel:hotelRoom:view","meiguotong:hotel:hotelRoom:add","meiguotong:hotel:hotelRoom:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(HotelRoom hotelRoom, Model model) {
		//语言
		List<ComLanguage> comLanguageList = comLanguageService.findList(new ComLanguage());
		model.addAttribute("comLanguageList",comLanguageList);
		//城市
		HotelRoom h = new HotelRoom();
		User user = UserUtils.getUser();
		if(user.getUserType()==2) {
			h.setAgentid(user.getAgentid());
		}
		if(StringUtils.isBlank(hotelRoom.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}else {
			h.setAgentid(hotelRoom.getAgentid());
		}
		List<HotelRoom> hotelRoomList = hotelRoomService.findListByAgentid(h);
		model.addAttribute("hotelRoomList",hotelRoomList);
		model.addAttribute("userType",user.getUserType());
		//获取所有的星期
		model.addAttribute("weekDateList",routeService.getWeekAll());
		//获取所有的天数
		List<WeekDate> list1 = routeService.getDayAll();
		if(hotelRoom.getDayDate()!=null){
			String[] dayDate= hotelRoom.getDayDate().split(",");
			for(String day:dayDate){
				for(WeekDate b:list1){
					if(day.equals(b.getId())){
						b.setDigFlag(1);
						break;
					}
				}
			}
		}
		model.addAttribute("dayList",list1);
		//获取日期选择
		model.addAttribute("hotelRoom",hotelRoom);
		return "modules/meiguotong/hotel/hotelRoomForm";
	}

	/**
	 * 保存酒店管理
	 */
	@RequiresPermissions(value={"meiguotong:hotel:hotelRoom:add","meiguotong:hotel:hotelRoom:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(HotelRoom hotelRoom, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, hotelRoom)){
			return form(hotelRoom, model);
		}
		//新增或编辑表单保存
		hotelRoomService.updateAdd(hotelRoom);//保存
		addMessage(redirectAttributes, "保存酒店管理成功");
		return "redirect:"+Global.getAdminPath()+"/meiguotong/hotel/hotelRoom/?repage";
	}
	

	/** 
	* @Title: updateStatus 
	* @Description: 审核
	* @author 彭善智
	* @date 2018年8月21日上午10:33:51
	*/ 
	@ResponseBody
	@RequiresPermissions("meiguotong:hotel:hotel:updateStatus")
	@RequestMapping(value = "updateStatus")
	public AjaxJson updateStatus(HotelRoom hotelRoom, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		hotelRoomService.save(hotelRoom);
		j.setMsg("更改状态成功");
		return j;
	}
	
	
	/** 
	* @Title: reviewed 
	* @Description: 启用禁用
	* @author 彭善智
	* @date 2018年8月21日上午11:21:15
	*/ 
	@ResponseBody
	@RequiresPermissions("meiguotong:hotel:hotel:reviewed")
	@RequestMapping(value = "reviewed")
	public AjaxJson reviewed(HotelRoom hotelRoom, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		hotelRoomService.save(hotelRoom);
		j.setMsg("更改状态成功");
		return j;
	}
	/**
	 * 根据语言获取城市
	 */
	@ResponseBody
	@RequestMapping(value = "getCity")
	public AjaxJson getCity(HotelRoom hotelRoom, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		//城市
		User user = UserUtils.getUser();
		if(user.getUserType()==2) {
			hotelRoom.setAgentid(user.getAgentid());
		}
		List<HotelRoom> hotelRoomList = hotelRoomService.findListByAgentid(hotelRoom);
		j.getBody().put("list", hotelRoomList);
		return j;
	}
	/**
	 * 删除酒店管理
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:hotel:hotelRoom:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(HotelRoom hotelRoom, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		hotelRoomService.delete(hotelRoom);
		j.setMsg("删除酒店管理成功");
		return j;
	}
	
	/**
	 * 批量删除酒店管理
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:hotel:hotelRoom:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			hotelRoomService.delete(hotelRoomService.get(id));
		}
		j.setMsg("删除酒店管理成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:hotel:hotelRoom:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(HotelRoom hotelRoom, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "酒店管理"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<HotelRoom> page = hotelRoomService.findPage(new Page<HotelRoom>(request, response, -1), hotelRoom);
    		new ExportExcel("酒店管理", HotelRoom.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出酒店管理记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("meiguotong:hotel:hotelRoom:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<HotelRoom> list = ei.getDataList(HotelRoom.class);
			for (HotelRoom hotelRoom : list){
				try{
					hotelRoomService.save(hotelRoom);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条酒店管理记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条酒店管理记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入酒店管理失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/hotel/hotelRoom/?repage";
    }
	
	/**
	 * 下载导入酒店管理数据模板
	 */
	@RequiresPermissions("meiguotong:hotel:hotelRoom:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "酒店管理数据导入模板.xlsx";
    		List<HotelRoom> list = Lists.newArrayList(); 
    		new ExportExcel("酒店管理数据", HotelRoom.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/hotel/hotelRoom/?repage";
    }

}