/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.web.car;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

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
import com.jeeplus.modules.meiguotong.entity.car.CarBusiness;
import com.jeeplus.modules.meiguotong.entity.car.CarInfo;
import com.jeeplus.modules.meiguotong.entity.car.CarService;
import com.jeeplus.modules.meiguotong.entity.comlanguage.ComLanguage;
import com.jeeplus.modules.meiguotong.entity.product.WeekDate;
import com.jeeplus.modules.meiguotong.service.car.CarBusinessService;
import com.jeeplus.modules.meiguotong.service.car.CarInfoService;
import com.jeeplus.modules.meiguotong.service.car.CarServiceService;
import com.jeeplus.modules.meiguotong.service.comlanguage.ComLanguageService;
import com.jeeplus.modules.meiguotong.service.product.RouteService;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.entity.sysUser.SysUser;
import com.jeeplus.modules.sys.service.sysUser.SysUserService;
import com.jeeplus.modules.sys.utils.UserUtils;

/**
 * 租车管理Controller
 * @author psz
 * @version 2018-08-31
 */
@Controller
@RequestMapping(value = "${adminPath}/meiguotong/car/car")
public class CarInfoController extends BaseController {

	@Autowired
	private CarInfoService carService;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private ComLanguageService comLanguageService;
	@Autowired
	private CarBusinessService carBusinessService;
	@Autowired
	private CarServiceService carServiceService;
	@Autowired
	private RouteService routeService;
	
	@ModelAttribute
	public CarInfo get(@RequestParam(required=false) String id) {
		CarInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = carService.get(id);
		}
		if (entity == null){
			entity = new CarInfo();
		}
		return entity;
	}
	
	/**
	 * 租车管理列表页面
	 */
	@RequiresPermissions("meiguotong:car:car:list")
	@RequestMapping(value = {"list", ""})
	public String list(Model model, CarInfo carInfo) {
		//供应商
		List<SysUser> sysUserList = sysUserService.getNameByType(1);
		model.addAttribute("sysUserList",sysUserList);
		//账号类型
		model.addAttribute("userType",UserUtils.getUser().getUserType());
		//语言
		List<ComLanguage> comLanguageList = comLanguageService.findList(new ComLanguage());
		model.addAttribute("comLanguageList",comLanguageList);
		model.addAttribute("carInfo",carInfo);
		return "modules/meiguotong/car/carList";
	}
	
		/**
	 * 租车管理列表数据
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:car:car:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(CarInfo car, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		if(user.getUserType()==2) {
			car.setAgentid(user.getAgentid());
		}
		Page<CarInfo> page = carService.findPage(new Page<CarInfo>(request, response), car); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑租车管理表单页面
	 */
//	@RequiresPermissions(value={"meiguotong:car:car:view","meiguotong:car:car:add","meiguotong:car:car:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(CarInfo car, Model model) {
		//账号类型
		model.addAttribute("userType",UserUtils.getUser().getUserType());
		//语言
		List<ComLanguage> comLanguageList = comLanguageService.findList(new ComLanguage());
		model.addAttribute("comLanguageList",comLanguageList);
		//包车租车标题
		List<CarService> carServiceList = carServiceService.findList(new CarService());
		model.addAttribute("carServiceList",carServiceList);
		//汽车业务
		List<CarBusiness> carBusinessList = carBusinessService.findListByCarid(car);
		for(CarBusiness a : carBusinessList){
			List<WeekDate> list1 = routeService.getDayAll();
			if(a.getDayDate()!=null){
				String[] dayDate= a.getDayDate().split(",");
				for(String day:dayDate){
					for(WeekDate b:list1){
						if(day.equals(b.getId())){
							b.setDigFlag(1);
							break;
						}
					}
				}
			}
			a.setDayList1(list1);
		}
		model.addAttribute("carBusinessList", carBusinessList);
		//获取所有的星期
		model.addAttribute("weekDateList",routeService.getWeekAll());
		//获取所有的小时
		model.addAttribute("hourList",routeService.getHourAll());
		//后台账号类型
		model.addAttribute("userType",UserUtils.getUser().getUserType());
		User user = UserUtils.getUser();
		if(user.getUserType() == 2) {
			car.setAgentid(user.getAgentid());
		}
		if(StringUtils.isBlank(car.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		model.addAttribute("car", car);
		return "modules/meiguotong/car/carForm";
	}



	/** 
	* @Title: save 
	* @Description: 保存租车管理
	* @author 彭善智
	* @date 2018年9月4日下午4:33:34
	*/ 
//	@RequiresPermissions(value={"meiguotong:car:car:add","meiguotong:car:car:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(CarInfo car, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, car)){
			return form(car, model);
		}
		//新增或编辑表单保存
		carService.addUpdate(car);//保存
		addMessage(redirectAttributes, "保存租车管理成功");
		return "redirect:"+Global.getAdminPath()+"/meiguotong/car/car/?repage";
	}
	
	/** 
	* @Title: updateStatus 
	* @Description: 修改审核状态
	* @author 彭善智
	* @date 2018年9月3日下午3:19:32
	*/ 
	@ResponseBody
	@RequestMapping(value = "updateStatus")
	public AjaxJson updateStatus(CarInfo car, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		carService.save(car);
		j.setMsg("修改审核状态成功");
		return j;
	}
	
	/**
	 * 删除租车管理
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:car:car:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(CarInfo car, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		carService.delete(car);
		j.setMsg("删除租车管理成功");
		return j;
	}
	
	/**
	 * 批量删除租车管理
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:car:car:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			carService.delete(carService.get(id));
		}
		j.setMsg("删除租车管理成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:car:car:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(CarInfo car, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "租车管理"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<CarInfo> page = carService.findPage(new Page<CarInfo>(request, response, -1), car);
    		new ExportExcel("租车管理", CarInfo.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出租车管理记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("meiguotong:car:car:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<CarInfo> list = ei.getDataList(CarInfo.class);
			for (CarInfo car : list){
				try{
					carService.save(car);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条租车管理记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条租车管理记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入租车管理失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/car/car/?repage";
    }
	
	/**
	 * 下载导入租车管理数据模板
	 */
	@RequiresPermissions("meiguotong:car:car:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "租车管理数据导入模板.xlsx";
    		List<CarInfo> list = Lists.newArrayList(); 
    		new ExportExcel("租车管理数据", CarInfo.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/car/car/?repage";
    }

}