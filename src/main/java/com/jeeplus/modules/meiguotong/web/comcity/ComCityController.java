/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.web.comcity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
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

import com.alibaba.druid.sql.visitor.functions.If;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.QiniuUtils;
import com.jeeplus.common.config.Global;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.UploadHelper;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.jeeplus.modules.meiguotong.entity.comcity.ComCity;
import com.jeeplus.modules.meiguotong.entity.comcity.ComCity2;
import com.jeeplus.modules.meiguotong.entity.comcity.ComCityModel;
import com.jeeplus.modules.meiguotong.entity.comcitytravel.ComCityTravel;
import com.jeeplus.modules.meiguotong.entity.comlanguage.ComLanguage;
import com.jeeplus.modules.meiguotong.entity.conutry.Country;
import com.jeeplus.modules.meiguotong.entity.guide.Guide;
import com.jeeplus.modules.meiguotong.entity.product.Route;
import com.jeeplus.modules.meiguotong.entity.scenicspot.ScenicSpot;
import com.jeeplus.modules.meiguotong.entity.travel.TravelCustomization;
import com.jeeplus.modules.meiguotong.service.comcity.ComCityService;
import com.jeeplus.modules.meiguotong.service.comcitytravel.ComCityTravelService;
import com.jeeplus.modules.meiguotong.service.comlanguage.ComLanguageService;
import com.jeeplus.modules.meiguotong.service.conutry.CountryService;
import com.jeeplus.modules.meiguotong.service.guide.GuideService;
import com.jeeplus.modules.meiguotong.service.product.RouteService;
import com.jeeplus.modules.meiguotong.service.scenicspot.ScenicSpotService;
import com.jeeplus.modules.meiguotong.service.travel.TravelCustomizationService;
import com.jeeplus.modules.sys.utils.CodeGenUtils;

/**
 * 城市表Controller
 * 
 * @author cdq
 * @version 2018-08-02
 */
/**
 * @author LG
 *
 */
/**
 * @author LG
 *
 */
/**
 * @author LG
 *
 */
/**
 * @author LG
 *
 */
/**
 * @author LG
 *
 */
@Controller
@RequestMapping(value = "${adminPath}/meiguotong/comcity/comCity")
public class ComCityController extends BaseController {

	@Autowired
	private ComCityService comCityService;
	@Autowired
	private ComLanguageService comLanguageService;
	@Autowired
	private CountryService countryService;
	@Autowired
	private RouteService routeService;
	@Autowired
	private ScenicSpotService ScenicSpotService;
	@Autowired
	private GuideService guideService;
	@Autowired
	private TravelCustomizationService travelCustomizationService;
	@Autowired 
	private ComCityTravelService comCityTravelService;

	@ModelAttribute
	public ComCity get(@RequestParam(required = false) String id) {
		ComCity entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = comCityService.get(id);
		}
		if (entity == null) {
			entity = new ComCity();
		}
		return entity;
	}

	/**
	 * 城市表列表页面
	 */
	@RequiresPermissions("meiguotong:comcity:comCity:list")
	@RequestMapping(value = { "list", "" })
	public String list(Integer provinceId, Model model) {
		model.addAttribute("provinceId", provinceId);
		//语言
		List<ComLanguage> comLanguageList = comLanguageService.findList(new ComLanguage());
		model.addAttribute("comLanguageList",comLanguageList);
		return "modules/meiguotong/comcity/comCityList";
	}

	/**
	 * 城市表列表数据
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:comcity:comCity:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(ComCity comCity, HttpServletRequest request, HttpServletResponse response,
			Model model) {
		Page<ComCity> page = comCityService.findPage(new Page<ComCity>(request, response), comCity);
		return getBootstrapData(page);
	}

	/**
	 * @Title: getData
	 * @Description: 获取城市列表
	 * @author 彭善智
	 * @date 2018年8月23日下午3:33:40
	 */
	@ResponseBody
	@RequestMapping(value = "getData")
	public AjaxJson getData(ComCity comCity, Model model) {
		AjaxJson j = new AjaxJson();
		List<ComCity> comCityList = comCityService.getData(comCity);
		j.getBody().put("comCityList", comCityList);
		return j;
	}

	/**
	 * 根据语言获取城市
	 * 
	 * @param comCity
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getCityList")
	public Map<String, List<ComCity>> getCityList(ComCity comCity, Model model) {
		Map<String, List<ComCity>> map = new HashMap<>();
		List<ComCity> list = comCityService.getCityList(comCity);
		map.put("list", list);
		return map;
	}

	/**
	 * 增加，编辑城市表表单页面
	 */
	@RequiresPermissions(value = { "meiguotong:comcity:comCity:view", "meiguotong:comcity:comCity:add",
			"meiguotong:comcity:comCity:edit" }, logical = Logical.OR)
	@RequestMapping(value = "form")
	public String form(ComCity comCity, Integer provinceId, Model model) {
		List<ComLanguage> comLanguageList = comLanguageService.findLanguage();
		model.addAttribute("comLanguageList", comLanguageList);
		model.addAttribute("comCity", comCity);
		model.addAttribute("provinceId", provinceId);
		Country country = countryService.get(provinceId.toString());
		model.addAttribute("languageid", country.getLanguageId());
		if (StringUtils.isBlank(comCity.getId())) {// 如果ID是空为添加,否则为修改
			model.addAttribute("isAdd", true);
			return "modules/meiguotong/comcity/comCityForm";
		}else{
			model.addAttribute("nearCitys", comCityService.findCitynearCityid(comCity.getNearCityNumber()));//附近城市列表
			model.addAttribute("routes", routeService.findRrouteByOfferenTop(comCity.getOfferenTop()));//参团列表
			model.addAttribute("scenics", ScenicSpotService.findScenicsByscenicTop(comCity.getScenicTop()));//景点列表
			model.addAttribute("guides", guideService.findguidesByPlayerId(comCity.getPlayerTop()));//当地玩家列表
			List<ComCityTravel> comCityTravels=comCityTravelService.findComCityTravelByCityId(Integer.valueOf(comCity.getId()));//根据城市ID获取旅游定制
			List<TravelCustomization> traveldetails=new ArrayList<>();//总得旅游详情列表
//			Iterator<ComCityTravel> t=comCityTravels.iterator();
//			while (t.hasNext()) {
				for (int i = 0; i < comCityTravels.size(); i++) {
					List<TravelCustomization> travels=
							travelCustomizationService.findTravelByComCityTravels(comCityTravels.get(i).getTravelid());//根据定制ID获取定制列表
					for (int j = 0; j < travels.size(); j++) {
						travels.get(j).setClassName(comCityTravels.get(i).getClassname());//为定制旅游设置分类
					}
					traveldetails.addAll(travels);
				}
//			}
			List<ComCityTravel> travelsss=comCityTravelService.findComCityTravelByCityId2(Integer.valueOf(comCity.getId()));
			model.addAttribute("travelsss", travelsss);
			model.addAttribute("comCityTravels", comCityTravels);
			model.addAttribute("traveldetails", traveldetails);
			return "modules/meiguotong/comcity/comCityFormEdit";
		}
	}

	/**
	 * 根据语言查询城市
	 */
	@ResponseBody
	@RequestMapping(value ="findcity")
	public AjaxJson findCityByLanguageid(ComCity comCity,Model model){
		AjaxJson j=new AjaxJson();
		List<ComCity> citys=comCityService.findCityBylanguageid(comCity);
		j.getBody().put("list", citys);
		return j;
	}
	
	/**
	 * 根据title参团列表
	 */
	@ResponseBody
	@RequestMapping(value ="routes")
	public AjaxJson findRouteByTitle(Route route,Model model){
		AjaxJson j=new AjaxJson();
		List<Route> routes=routeService.findRrouteByTitle(route);
		j.getBody().put("list", routes);
		return j;
	}
	/**
	 * 根据景点模糊搜索景点
	 */
	@ResponseBody
	@RequestMapping(value ="scenics")
	public AjaxJson findScenicsByName(ScenicSpot scenicSpot,Model model){
		AjaxJson j=new AjaxJson();
		List<ScenicSpot> scenicSpots=ScenicSpotService.findScenicsByName(scenicSpot);
		j.getBody().put("list", scenicSpots);
		return j;
	}
	
	/**
	 * 根据导游名称模糊搜索导游
	 * @param guide
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value ="guides")
	public AjaxJson findguidesByName(Guide guide,Model model){
		AjaxJson j=new AjaxJson();
		List<Guide> guides=guideService.findguidesByName(guide);
		j.getBody().put("list", guides);
		return j;
	}
	/**
	 * 根据导游名称模糊搜索导游
	 * @param guide
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value ="travels")
	public AjaxJson findguidesByName(TravelCustomization travelCustomization,Model model){
		AjaxJson j=new AjaxJson();
		List<TravelCustomization> travels=travelCustomizationService.findTravelByCityId(travelCustomization);
		j.getBody().put("list", travels);
		return j;
	}
	
	/**
	 * 查看城市表表单页面
	 */
	@RequiresPermissions(value = { "meiguotong:comcity:comCity:view", "meiguotong:comcity:comCity:add",
			"meiguotong:comcity:comCity:edit" }, logical = Logical.OR)
	@RequestMapping(value = "viewForm")
	public String viewForm(ComCity comCity, Integer provinceId, Model model) {
		model.addAttribute("provinceId", provinceId);
		model.addAttribute("comCity", comCity);
		model.addAttribute("nearCitys", comCityService.findCitynearCityid(comCity.getNearCityNumber()));
		model.addAttribute("routes", routeService.findRrouteByOfferenTop(comCity.getOfferenTop()));
		model.addAttribute("scenics", ScenicSpotService.findScenicsByscenicTop(comCity.getScenicTop()));
		model.addAttribute("guides", guideService.findguidesByPlayerId(comCity.getPlayerTop()));
		if (StringUtils.isBlank(comCity.getId())) {// 如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/meiguotong/comcity/comCityviewForm";
	}

	/**
	 * 保存城市表
	 */
	@RequiresPermissions(value = { "meiguotong:comcity:comCity:add",
			"meiguotong:comcity:comCity:edit" }, logical = Logical.OR)
	@RequestMapping(value = "save")
	public String save(ComCity comCity,String nearCityNumber,String offerenTop,String scenicTop,String playerTop, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, comCity)) {
			return form(comCity, comCity.getProvinceId(), model);
		}
		// 新增或编辑表单保存
		Country country = countryService.get(comCity.getProvinceId().toString());
		comCity.setLanguageId(country.getLanguageId().toString());
		comCity.setNearCityNumber(nearCityNumber);
		comCity.setOfferenTop(offerenTop);
		comCity.setScenicTop(scenicTop);
		comCity.setPlayerTop(playerTop);
		comCityService.save(comCity);// 保存
		addMessage(redirectAttributes, "保存城市表成功");
		return "redirect:" + Global.getAdminPath() + "/meiguotong/comcity/comCity/list?provinceId="
				+ comCity.getProvinceId();
	}

	/**
	 * 修改状态
	 */
	@ResponseBody
	@RequestMapping(value = "status")
	public AjaxJson status(ComCity comCity, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		if (comCity.getStatus() == 1) {
			comCity.setStatus(2);
		} else {
			comCity.setStatus(1);
		}
		comCityService.status(comCity);
		j.setMsg("修改状态成功");
		return j;
	}

	/**
	 * 删除城市表
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:comcity:comCity:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(ComCity comCity, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		comCityService.delete(comCity);
		j.setMsg("删除城市表成功");
		return j;
	}

	/**
	 * 批量删除城市表
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:comcity:comCity:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] = ids.split(",");
		for (String id : idArray) {
			comCityService.delete(comCityService.get(id));
		}
		j.setMsg("删除城市表成功");
		return j;
	}

	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:comcity:comCity:export")
	@RequestMapping(value = "export", method = RequestMethod.POST)
	public AjaxJson exportFile(ComCity comCity, HttpServletRequest request, HttpServletResponse response,
			RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
			String fileName = "城市表" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
			Page<ComCity> page = comCityService.findPage(new Page<ComCity>(request, response, -1), comCity);
			new ExportExcel("城市表", ComCity.class).setDataList(page.getList()).write(response, fileName).dispose();
			j.setSuccess(true);
			j.setMsg("导出成功！");
			return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出城市表记录失败！失败信息：" + e.getMessage());
		}
		return j;
	}

	/**
	 * 导入Excel数据
	 * 
	 */
	@RequiresPermissions("meiguotong:comcity:comCity:import")
	@RequestMapping(value = "import", method = RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<ComCity> list = ei.getDataList(ComCity.class);
			for (ComCity comCity : list) {
				try {
					comCityService.save(comCity);
					successNum++;
				} catch (ConstraintViolationException ex) {
					failureNum++;
				} catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum > 0) {
				failureMsg.insert(0, "，失败 " + failureNum + " 条城市表记录。");
			}
			addMessage(redirectAttributes, "已成功导入 " + successNum + " 条城市表记录" + failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入城市表失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + Global.getAdminPath() + "/meiguotong/comcity/comCity/?repage";
	}

	/**
	 * 下载导入城市表数据模板
	 */
	@RequiresPermissions("meiguotong:comcity:comCity:import")
	@RequestMapping(value = "import/template")
	public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
			String fileName = "城市表数据导入模板.xlsx";
			List<ComCity> list = Lists.newArrayList();
			new ExportExcel("城市表数据", ComCity.class, 1).setDataList(list).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + Global.getAdminPath() + "/meiguotong/comcity/comCity/?repage";
	}

	// 上传图片
	@ResponseBody
	@RequestMapping(value = "uploadFile")
	public AjaxJson uploadFile(HttpServletRequest request, HttpServletResponse response, Model model) {

		AjaxJson ajaxJson = new AjaxJson();

		try {
			List<MultipartFile> multipartFiles = UploadHelper.getMultipartFileList(request);
			if (multipartFiles.size() == 0) {
				// 给出提示,不允许没选择文件点击上传
				ajaxJson.setSuccess(false);
				ajaxJson.setMsg("上传图片为空");
				return ajaxJson;
			}
			MultipartFile multipartFile = multipartFiles.get(0);
			String imgPath = "meiguotong/" + CodeGenUtils.getYear() + "/" + CodeGenUtils.getMonth() + "/"
					+ CodeGenUtils.getDay();

			if (multipartFile != null && multipartFile.getSize() > 0) {
				String key = imgPath + "/" + CodeGenUtils.getPicId() + ".jpg";
				String filePath = QiniuUtils.uploadFile(multipartFile.getInputStream(), key);
				if (filePath.equals("")) {
					ajaxJson.setSuccess(false);
					ajaxJson.setMsg("图片上传失败");
				} else {
					ajaxJson.setSuccess(true);
					ajaxJson.setMsg("图片上传成功");
					filePath = QiniuUtils.QiniuUrl + key;
					ajaxJson.put("filePath", filePath);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			ajaxJson.setSuccess(false);
			ajaxJson.setMsg("因未知原因失败");
		}
		return ajaxJson;
	}

	// 上传视频
	@ResponseBody
	@RequestMapping(value = "uploadVideo")
	public AjaxJson uploadVideo(HttpServletRequest request, HttpServletResponse response, Model model) {

		AjaxJson ajaxJson = new AjaxJson();

		try {
			List<MultipartFile> multipartFiles = UploadHelper.getMultipartFileList(request);
			if (multipartFiles.size() == 0) {
				// 给出提示,不允许没选择文件点击上传
				ajaxJson.setSuccess(false);
				ajaxJson.setMsg("上传视频为空");
				return ajaxJson;
			}
			MultipartFile multipartFile = multipartFiles.get(0);
			String imgPath = "meiguotong/" + CodeGenUtils.getYear() + "/" + CodeGenUtils.getMonth() + "/"
					+ CodeGenUtils.getDay();

			if (multipartFile != null && multipartFile.getSize() > 0) {
				String originalFilename = multipartFile.getOriginalFilename();
				//后缀
				String name = originalFilename.substring(originalFilename.indexOf("."),originalFilename.length());
				String key = imgPath + "/" + CodeGenUtils.getPicId() + name;
				String filePath = QiniuUtils.uploadFile(multipartFile.getInputStream(), key);
				if (filePath.equals("")) {
					ajaxJson.setSuccess(false);
					ajaxJson.setMsg("视频上传失败");
				} else {
					ajaxJson.setSuccess(true);
					ajaxJson.setMsg("视频上传成功");
					filePath = QiniuUtils.QiniuUrl + key;
					ajaxJson.put("filePath", filePath);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			ajaxJson.setSuccess(false);
			ajaxJson.setMsg("因未知原因失败");
		}
		return ajaxJson;
	}
}