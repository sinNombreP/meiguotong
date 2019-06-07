/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.web.car;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import com.jeeplus.modules.meiguotong.entity.comlanguage.ComLanguage;
import com.jeeplus.modules.meiguotong.entity.ordercar.OrderCar;
import com.jeeplus.modules.meiguotong.service.comlanguage.ComLanguageService;
import com.jeeplus.modules.meiguotong.service.ordercar.OrderCarService;

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

import com.fasterxml.jackson.databind.JsonSerializable;
import com.google.common.collect.Lists;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.config.Global;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.jeeplus.modules.meiguotong.entity.car.CarService;
import com.jeeplus.modules.meiguotong.service.car.CarServiceService;

/**
 * 汽车标题表Controller
 * @author psz
 * @version 2018-08-31
 */
@Controller
@RequestMapping(value = "${adminPath}/meiguotong/car/carService")
public class CarServiceController extends BaseController {

	@Autowired
	private CarServiceService carServiceService;
	@Autowired
	private ComLanguageService comLanguageService;
	@Autowired
	private OrderCarService orderCarService;

	@ModelAttribute
	public CarService get(@RequestParam(required=false) String id) {
		CarService entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = carServiceService.get(id);
		}
		if (entity == null){
			entity = new CarService();
		}
		return entity;
	}
	
	/**
	 * 汽车标题表列表页面
	 */
	@RequiresPermissions("meiguotong:car:carService:list")
	@RequestMapping(value = {"list", ""})
	public String list(Model model) {
		//语言
		List<ComLanguage> comLanguageList = comLanguageService.findList(new ComLanguage());
		model.addAttribute("comLanguageList",comLanguageList);
		return "modules/meiguotong/car/carServiceList";
	}
	
	@RequestMapping(value = "calander")
	public String calander(Model model,String id) {
		model.addAttribute("id", id);
		return "modules/meiguotong/car/calander";
	}
	
	@RequestMapping(value = "orderCarInfo")
	public String orderCarInfo(OrderCar orderCar) {
		return "redirect:"+Global.getAdminPath()+"/meiguotong/ordercar/orderCar/form?id="+orderCar.getId();
	}
	/**
	 * @param carService
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "JourneyOfCar")
	public AjaxJson findJourneyByCarid(OrderCar orderCar, HttpServletRequest request, HttpServletResponse response, Model model) {
		AjaxJson j = new AjaxJson();
		orderCar.setDate(orderCar.getDate()+"-01");
		List<OrderCar> orderCarsDate=orderCarService.findJourneyByCarid(orderCar);
		/*for (OrderCar o:orderCarsDate) {
			o.setCarid(orderCar.getCarid());
			o.setDate(orderCar.getDate());
			List<OrderCar> orderCars=orderCarService.findJourneyByCarid(o);
			o.setOrderCarList(orderCars);
		}*/
		j.getBody().put("result",orderCarsDate);
		return j;
	}
	//j.body:{{"":"","":"",},{}}
		/**
	 * 汽车标题表列表数据
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:car:carService:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(CarService carService, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CarService> page = carServiceService.findPage(new Page<CarService>(request, response), carService); 
		return getBootstrapData(page);
	}
	
	
	/** 
	* @Title: getTitleData 
	* @Description: 模态框获取标题数据
	* @author 彭善智
	* @date 2018年9月4日上午11:53:41
	*/ 
	@ResponseBody
	@RequestMapping(value = "getTitleData")
	public AjaxJson getTitleData(CarService carService, HttpServletRequest request, HttpServletResponse response, Model model) {
		AjaxJson j = new AjaxJson();
		try {
			List<CarService> carServiceList = carServiceService.getTitleData(carService);
			j.getBody().put("carServiceList", carServiceList);
			j.setMsg("获取标题数据成功");
		}catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("获取标题数据失败");
		}
		return j;
	}

	
	
	/**
	 * 查看，增加，编辑汽车标题表表单页面
	 */
	//@RequiresPermissions(value={"meiguotong:car:carService:view","meiguotong:car:carService:add","meiguotong:car:carService:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(CarService carService, Model model) {
		model.addAttribute("carService", carService);
		if(StringUtils.isBlank(carService.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		//语言
		List<ComLanguage> comLanguageList = comLanguageService.findList(new ComLanguage());
		model.addAttribute("comLanguageList",comLanguageList);
		return "modules/meiguotong/car/carServiceForm";
		
	}

	/**
	 * 保存汽车标题表
	 */
	//@RequiresPermissions(value={"meiguotong:car:carService:add","meiguotong:car:carService:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(CarService carService, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, carService)){
			return form(carService, model);
		}
		//新增或编辑表单保存
		carServiceService.save(carService);//保存
		addMessage(redirectAttributes, "保存汽车标题表成功");
		return "redirect:"+Global.getAdminPath()+"/meiguotong/car/carService/?repage";
	}
	
	/**
	 * 删除汽车标题表
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:car:carService:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(CarService carService, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		carServiceService.delete(carService);
		j.setMsg("删除汽车标题表成功");
		return j;
	}
	
	/**
	 * 批量删除汽车标题表
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:car:carService:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			carServiceService.delete(carServiceService.get(id));
		}
		j.setMsg("删除汽车标题表成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:car:carService:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(CarService carService, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "汽车标题表"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<CarService> page = carServiceService.findPage(new Page<CarService>(request, response, -1), carService);
    		new ExportExcel("汽车标题表", CarService.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出汽车标题表记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("meiguotong:car:carService:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<CarService> list = ei.getDataList(CarService.class);
			for (CarService carService : list){
				try{
					carServiceService.save(carService);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条汽车标题表记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条汽车标题表记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入汽车标题表失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/car/carService/?repage";
    }
	
	/**
	 * 下载导入汽车标题表数据模板
	 */
	@RequiresPermissions("meiguotong:car:carService:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "汽车标题表数据导入模板.xlsx";
    		List<CarService> list = Lists.newArrayList(); 
    		new ExportExcel("汽车标题表数据", CarService.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/car/carService/?repage";
    }

}