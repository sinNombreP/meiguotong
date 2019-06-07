/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.web.hotelroomdevice;

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
import com.jeeplus.common.utils.QiniuUtils;
import com.jeeplus.common.config.Global;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.UploadHelper;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.jeeplus.modules.meiguotong.entity.comlanguage.ComLanguage;
import com.jeeplus.modules.meiguotong.entity.hotelroomdevice.HotelRoomDevice;
import com.jeeplus.modules.meiguotong.service.comlanguage.ComLanguageService;
import com.jeeplus.modules.meiguotong.service.hotelroomdevice.HotelRoomDeviceService;
import com.jeeplus.modules.sys.utils.CodeGenUtils;

/**
 * 酒店管理Controller
 * @author cdq
 * @version 2018-08-27
 */
@Controller
@RequestMapping(value = "${adminPath}/meiguotong/hotelroomdevice/hotelRoomDevice")
public class HotelRoomDeviceController extends BaseController {

	@Autowired
	private HotelRoomDeviceService hotelRoomDeviceService;
	@Autowired
	private ComLanguageService comLanguageService;
	
	@ModelAttribute
	public HotelRoomDevice get(@RequestParam(required=false) String id) {
		HotelRoomDevice entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = hotelRoomDeviceService.get(id);
		}
		if (entity == null){
			entity = new HotelRoomDevice();
		}
		return entity;
	}

	 /**
	   * @method: getHotelRoomDeviceList
	   * @Description:  获取房间设施列表
	   * @Author:   彭善智
	   * @Date:     2018/12/19 11:19
	   */
	@ResponseBody
	@RequestMapping(value = "getHotelRoomDeviceList")
	public AjaxJson getHotelRoomDeviceList(HotelRoomDevice hotelRoomDevice, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		List<HotelRoomDevice> hotelRoomDeviceList =  hotelRoomDeviceService.findList(hotelRoomDevice);
		j.getBody().put("hotelRoomDeviceList",hotelRoomDeviceList);
		j.setSuccess(true);
		j.setMsg("保存酒店房间设施管理成功");
		return j;
	}

	/**
	 * 酒店管理列表页面
	 */
	@RequiresPermissions("meiguotong:hotelroomdevice:hotelRoomDevice:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/meiguotong/hotelroomdevice/hotelRoomDeviceList";
	}
	
		/**
	 * 酒店管理列表数据
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:hotelroomdevice:hotelRoomDevice:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(HotelRoomDevice hotelRoomDevice, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<HotelRoomDevice> page = hotelRoomDeviceService.findPage(new Page<HotelRoomDevice>(request, response), hotelRoomDevice); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑酒店管理表单页面
	 */
	@RequiresPermissions(value={"meiguotong:hotelroomdevice:hotelRoomDevice:view","meiguotong:hotelroomdevice:hotelRoomDevice:add","meiguotong:hotelroomdevice:hotelRoomDevice:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(HotelRoomDevice hotelRoomDevice, Model model) {
		List<ComLanguage> languageList = comLanguageService.findLanguage();
		model.addAttribute("languageList", languageList);
		model.addAttribute("hotelRoomDevice", hotelRoomDevice);
		return "modules/meiguotong/hotelroomdevice/hotelRoomDeviceForm";
	}

	/**
	 * 保存酒店管理
	 */
	@ResponseBody
	@RequiresPermissions(value={"meiguotong:hotelroomdevice:hotelRoomDevice:add","meiguotong:hotelroomdevice:hotelRoomDevice:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(HotelRoomDevice hotelRoomDevice, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, hotelRoomDevice)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		hotelRoomDeviceService.save(hotelRoomDevice);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存酒店房间设施管理成功");
		return j;
	}
	   /**
	   *修改状态
	   */
		@ResponseBody
		@RequestMapping(value = "status")
		public AjaxJson status(HotelRoomDevice hotelRoomDevice, RedirectAttributes redirectAttributes) {
			AjaxJson j = new AjaxJson();
			if(hotelRoomDevice.getStatus()==2){
				hotelRoomDevice.setStatus(1);
			}else{
				hotelRoomDevice.setStatus(2);
			}
			hotelRoomDeviceService.status(hotelRoomDevice);
			j.setMsg("修改状态成功");
			return j;
		}	
	/**
	 * 删除酒店管理
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:hotelroomdevice:hotelRoomDevice:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(HotelRoomDevice hotelRoomDevice, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		hotelRoomDeviceService.delete(hotelRoomDevice);
		j.setMsg("删除酒店管理成功");
		return j;
	}
	
	/**
	 * 批量删除酒店管理
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:hotelroomdevice:hotelRoomDevice:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			hotelRoomDeviceService.delete(hotelRoomDeviceService.get(id));
		}
		j.setMsg("删除酒店管理成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:hotelroomdevice:hotelRoomDevice:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(HotelRoomDevice hotelRoomDevice, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "酒店管理"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<HotelRoomDevice> page = hotelRoomDeviceService.findPage(new Page<HotelRoomDevice>(request, response, -1), hotelRoomDevice);
    		new ExportExcel("酒店管理", HotelRoomDevice.class).setDataList(page.getList()).write(response, fileName).dispose();
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
	@RequiresPermissions("meiguotong:hotelroomdevice:hotelRoomDevice:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<HotelRoomDevice> list = ei.getDataList(HotelRoomDevice.class);
			for (HotelRoomDevice hotelRoomDevice : list){
				try{
					hotelRoomDeviceService.save(hotelRoomDevice);
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
		return "redirect:"+Global.getAdminPath()+"/meiguotong/hotelroomdevice/hotelRoomDevice/?repage";
    }
	
	/**
	 * 下载导入酒店管理数据模板
	 */
	@RequiresPermissions("meiguotong:hotelroomdevice:hotelRoomDevice:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "酒店管理数据导入模板.xlsx";
    		List<HotelRoomDevice> list = Lists.newArrayList(); 
    		new ExportExcel("酒店管理数据", HotelRoomDevice.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/hotelroomdevice/hotelRoomDevice/?repage";
    }
	/**
	 * 表单页面的图片上传
	 */
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
			String imgPath="common/"+CodeGenUtils.getYear()+"/"+CodeGenUtils.getMonth()+"/"+CodeGenUtils.getDay();

			if(multipartFile != null && multipartFile.getSize()>0) {
				String key =imgPath +"/"+CodeGenUtils.getPicId()+".jpg";
				String filePath=QiniuUtils.uploadFile(multipartFile.getInputStream(), key);
				if(filePath.equals("")){
					ajaxJson.setSuccess(false);
					ajaxJson.setMsg("图片上传失败");
				}else{
					ajaxJson.setSuccess(true);
					ajaxJson.setMsg("图片上传成功");
					filePath=QiniuUtils.QiniuUrl+ key;
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