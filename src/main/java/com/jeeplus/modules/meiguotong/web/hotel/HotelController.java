/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.web.hotel;

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
import com.jeeplus.modules.meiguotong.entity.comcity.ComCity;
import com.jeeplus.modules.meiguotong.entity.comlanguage.ComLanguage;
import com.jeeplus.modules.meiguotong.entity.hotel.Hotel;
import com.jeeplus.modules.meiguotong.service.comcity.ComCityService;
import com.jeeplus.modules.meiguotong.service.comlanguage.ComLanguageService;
import com.jeeplus.modules.meiguotong.service.hotel.HotelService;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.utils.UserUtils;

/**
 * 酒店管理Controller
 * @author PSZ
 * @version 2018-08-20
 */
@Controller
@RequestMapping(value = "${adminPath}/meiguotong/hotel/hotel")
public class HotelController extends BaseController {

	@Autowired
	private HotelService hotelService;
	@Autowired
	private ComLanguageService comLanguageService;
	@Autowired
	private ComCityService comCityService;
	
	@ModelAttribute
	public Hotel get(@RequestParam(required=false) String id) {
		Hotel entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = hotelService.get(id);
		}
		if (entity == null){
			entity = new Hotel();
		}
		return entity;
	}
	
	/**
	 * 酒店管理列表页面
	 */
	@RequiresPermissions("meiguotong:hotel:hotel:list")
	@RequestMapping(value = {"list", ""})
	public String list(Hotel hotel, Model model) {
		User user = UserUtils.getUser();
		if(user!=null){
			model.addAttribute("agentid",user.getAgentid());
		}
		//语言
		List<ComLanguage> comLanguageList = comLanguageService.findList(new ComLanguage());
		model.addAttribute("comLanguageList",comLanguageList);
		return "modules/meiguotong/hotel/hotelList";
	}
	
		/**
	 * 酒店管理列表数据
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:hotel:hotel:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Hotel hotel, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		if(user.getUserType()==2) {
			hotel.setAgentid(user.getAgentid());
		}
		Page<Hotel> page = hotelService.findPage(new Page<Hotel>(request, response), hotel); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑酒店管理表单页面
	 */
	@RequiresPermissions(value={"meiguotong:hotel:hotel:view","meiguotong:hotel:hotel:add","meiguotong:hotel:hotel:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Hotel hotel, Model model) {
		//语言
		List<ComLanguage> comLanguageList = comLanguageService.findList(new ComLanguage());
		model.addAttribute("comLanguageList",comLanguageList);
		if(!hotel.getIsNewRecord()){
			ComCity comCity = new ComCity();
			comCity.setLanguageId(hotel.getLanguageId().toString());
			List<ComCity> cityList = comCityService.getCityList(comCity);
			model.addAttribute("cityList",cityList);
		}
		model.addAttribute("hotel", hotel);
		return "modules/meiguotong/hotel/hotelForm";
	}

	/**
	 * 保存酒店管理
	 */
	@ResponseBody
	@RequiresPermissions(value={"meiguotong:hotel:hotel:add","meiguotong:hotel:hotel:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(Hotel hotel, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, hotel)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		hotelService.save(hotel);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存酒店管理成功");
		return j;
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
	public AjaxJson updateStatus(Hotel hotel, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		hotelService.save(hotel);
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
	public AjaxJson reviewed(Hotel hotel, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		hotelService.save(hotel);
		j.setMsg("更改状态成功");
		return j;
	}
	
	
	/**
	 * 根据语言获取城市
	 */
	@ResponseBody
	@RequestMapping(value = "getCity")
	public AjaxJson getCity(ComCity comCity, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		List<ComCity> list = comCityService.getCityList(comCity);
		j.getBody().put("list", list);
		return j;
	}
	
	/**
	 * 删除酒店管理
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:hotel:hotel:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(Hotel hotel, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		hotelService.delete(hotel);
		j.setMsg("删除酒店管理成功");
		return j;
	}
	
	/**
	 * 批量删除酒店管理
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:hotel:hotel:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			hotelService.delete(hotelService.get(id));
		}
		j.setMsg("删除酒店管理成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:hotel:hotel:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(Hotel hotel, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "酒店管理"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Hotel> page = hotelService.findPage(new Page<Hotel>(request, response, -1), hotel);
    		new ExportExcel("酒店管理", Hotel.class).setDataList(page.getList()).write(response, fileName).dispose();
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
	@RequiresPermissions("meiguotong:hotel:hotel:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Hotel> list = ei.getDataList(Hotel.class);
			for (Hotel hotel : list){
				try{
					hotelService.save(hotel);
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
		return "redirect:"+Global.getAdminPath()+"/meiguotong/hotel/hotel/?repage";
    }
	
	/**
	 * 下载导入酒店管理数据模板
	 */
	@RequiresPermissions("meiguotong:hotel:hotel:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "酒店管理数据导入模板.xlsx";
    		List<Hotel> list = Lists.newArrayList(); 
    		new ExportExcel("酒店管理数据", Hotel.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/hotel/hotel/?repage";
    }

}