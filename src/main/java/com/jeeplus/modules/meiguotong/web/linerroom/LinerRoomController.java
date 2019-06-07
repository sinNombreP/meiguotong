/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.web.linerroom;

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
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.jeeplus.modules.meiguotong.entity.linerroom.LinerRoom;
import com.jeeplus.modules.meiguotong.service.linerroom.LinerRoomService;
import com.jeeplus.modules.sys.utils.CodeGenUtils;

/**
 * 邮轮房间表Controller
 * @author cdq
 * @version 2018-08-14
 */
@Controller
@RequestMapping(value = "${adminPath}/meiguotong/linerroom/linerRoom")
public class LinerRoomController extends BaseController {

	@Autowired
	private LinerRoomService linerRoomService;
	
	@ModelAttribute
	public LinerRoom get(@RequestParam(required=false) String id) {
		LinerRoom entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = linerRoomService.get(id);
		}
		if (entity == null){
			entity = new LinerRoom();
		}
		return entity;
	}
	
	/**
	 * 邮轮房间表列表页面
	 */
	@RequiresPermissions("meiguotong:linerroom:linerRoom:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/meiguotong/linerroom/linerRoomList";
	}
	
		/**
	 * 邮轮房间表列表数据
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:linerroom:linerRoom:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(LinerRoom linerRoom, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<LinerRoom> page = linerRoomService.findPage(new Page<LinerRoom>(request, response), linerRoom); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑邮轮房间表表单页面
	 */
	@RequiresPermissions(value={"meiguotong:linerroom:linerRoom:view","meiguotong:linerroom:linerRoom:add","meiguotong:linerroom:linerRoom:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(LinerRoom linerRoom, Model model) {
		model.addAttribute("linerRoom", linerRoom);
		if(StringUtils.isBlank(linerRoom.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/meiguotong/linerroom/linerRoomForm";
	}

	/**
	 * 保存邮轮房间表
	 */
	@RequiresPermissions(value={"meiguotong:linerroom:linerRoom:add","meiguotong:linerroom:linerRoom:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(LinerRoom linerRoom, Model model, RedirectAttributes redirectAttributes,
			@RequestParam(value="imageFrontFile") MultipartFile imageFrontFile) throws Exception{
		if (!beanValidator(model, linerRoom)){
			return form(linerRoom, model);
		}
		//上传图片
		String path="meiguotong/"+CodeGenUtils.getYear()+"/"+CodeGenUtils.getMonth()+"/"+CodeGenUtils.getDay();
		String message="";
		try {
			if(imageFrontFile != null && imageFrontFile.getSize()>0) {
				String key = path +"-"+CodeGenUtils.getPicId()+".jpg";
				QiniuUtils.uploadFile(imageFrontFile.getInputStream(), key);
				linerRoom.setImgUrl(QiniuUtils.QiniuUrl+ key);
			}
		}catch (Exception e) {
			message = "上传图片失败";
			addMessage(model, message);
			return form(linerRoom, model);
		}
		//新增或编辑表单保存
		linerRoomService.save(linerRoom);//保存
		addMessage(redirectAttributes, "保存邮轮房间表成功");
		return "redirect:"+Global.getAdminPath()+"/meiguotong/linerroom/linerRoom/?repage";
	}
	
	/**
	 * 删除邮轮房间表
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:linerroom:linerRoom:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(LinerRoom linerRoom, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		linerRoomService.delete(linerRoom);
		j.setMsg("删除邮轮房间表成功");
		return j;
	}
	
	/**
	 * 批量删除邮轮房间表
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:linerroom:linerRoom:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			linerRoomService.delete(linerRoomService.get(id));
		}
		j.setMsg("删除邮轮房间表成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:linerroom:linerRoom:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(LinerRoom linerRoom, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "邮轮房间表"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<LinerRoom> page = linerRoomService.findPage(new Page<LinerRoom>(request, response, -1), linerRoom);
    		new ExportExcel("邮轮房间表", LinerRoom.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出邮轮房间表记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("meiguotong:linerroom:linerRoom:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<LinerRoom> list = ei.getDataList(LinerRoom.class);
			for (LinerRoom linerRoom : list){
				try{
					linerRoomService.save(linerRoom);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条邮轮房间表记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条邮轮房间表记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入邮轮房间表失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/linerroom/linerRoom/?repage";
    }
	
	/**
	 * 下载导入邮轮房间表数据模板
	 */
	@RequiresPermissions("meiguotong:linerroom:linerRoom:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "邮轮房间表数据导入模板.xlsx";
    		List<LinerRoom> list = Lists.newArrayList(); 
    		new ExportExcel("邮轮房间表数据", LinerRoom.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/linerroom/linerRoom/?repage";
    }

}