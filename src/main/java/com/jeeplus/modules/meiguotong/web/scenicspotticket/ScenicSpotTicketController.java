/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.web.scenicspotticket;

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
import com.jeeplus.modules.meiguotong.entity.scenicspotticket.ScenicSpotTicket;
import com.jeeplus.modules.meiguotong.service.scenicspotticket.ScenicSpotTicketService;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.utils.UserUtils;

/**
 * 景点门票表Controller
 * @author cdq
 * @version 2018-08-16
 */
@Controller
@RequestMapping(value = "${adminPath}/meiguotong/scenicspotticket/scenicSpotTicket")
public class ScenicSpotTicketController extends BaseController {

	@Autowired
	private ScenicSpotTicketService scenicSpotTicketService;
	
	@ModelAttribute
	public ScenicSpotTicket get(@RequestParam(required=false) String id) {
		ScenicSpotTicket entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = scenicSpotTicketService.get(id);
		}
		if (entity == null){
			entity = new ScenicSpotTicket();
		}
		return entity;
	}
	
	/**
	 * 景点门票表列表页面
	 */
	@RequiresPermissions("meiguotong:scenicspotticket:scenicSpotTicket:list")
	@RequestMapping(value = {"list", ""})
	public String list(Integer scenicid ,ScenicSpotTicket scenicSpotTicket,Model model) {
		model.addAttribute("scenicid", scenicid);
		User user = UserUtils.getUser();
		if (StringUtils.isNotBlank(user.getId())){
			scenicSpotTicket.setLoginAgentid(user.getAgentid());
		}
		if(scenicSpotTicket.getLoginAgentid()!=null){//供应商登录为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/meiguotong/scenicspotticket/scenicSpotTicketList";
	}
	
		/**
	 * 景点门票表列表数据
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:scenicspotticket:scenicSpotTicket:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(ScenicSpotTicket scenicSpotTicket, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ScenicSpotTicket> page = scenicSpotTicketService.findPage(new Page<ScenicSpotTicket>(request, response), scenicSpotTicket); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑景点门票表表单页面
	 */
	@RequiresPermissions(value={"meiguotong:scenicspotticket:scenicSpotTicket:view","meiguotong:scenicspotticket:scenicSpotTicket:add","meiguotong:scenicspotticket:scenicSpotTicket:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(ScenicSpotTicket scenicSpotTicket, Integer scenicid,Model model) {
		model.addAttribute("scenicid", scenicid);
		model.addAttribute("scenicSpotTicket", scenicSpotTicket);
		if(StringUtils.isBlank(scenicSpotTicket.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/meiguotong/scenicspotticket/scenicSpotTicketForm";
	}

	/**
	 * 保存景点门票表
	 */
	@ResponseBody
	@RequiresPermissions(value={"meiguotong:scenicspotticket:scenicSpotTicket:add","meiguotong:scenicspotticket:scenicSpotTicket:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(ScenicSpotTicket scenicSpotTicket, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, scenicSpotTicket)){
			j.setSuccess(false);
			j.setMsg("非法参数");
			return j;
		}
		//新增或者编辑表单保存
		scenicSpotTicketService.save(scenicSpotTicket);//保存
		j.setSuccess(true);
		j.setMsg("保存成功");
		return j;	
	}
	  /**
	   *修改状态
	   */
		@ResponseBody
		@RequestMapping(value = "status")
		public AjaxJson status(ScenicSpotTicket scenicSpotTicket, RedirectAttributes redirectAttributes) {
			AjaxJson j = new AjaxJson();
			if(scenicSpotTicket.getStatus()==0){
				scenicSpotTicket.setStatus(1);
			}else{
				scenicSpotTicket.setStatus(0);
			}
			scenicSpotTicketService.status(scenicSpotTicket);
			if(scenicSpotTicket.getStatus()==0){
				j.setMsg("修改状态成功");
			}else{
				j.setMsg("修改状态成功");
			}
			return j;
		}
	/**
	 * 删除景点门票表
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:scenicspotticket:scenicSpotTicket:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(ScenicSpotTicket scenicSpotTicket, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		scenicSpotTicketService.delete(scenicSpotTicket);
		j.setMsg("删除景点门票表成功");
		return j;
	}
	
	/**
	 * 批量删除景点门票表
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:scenicspotticket:scenicSpotTicket:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			scenicSpotTicketService.delete(scenicSpotTicketService.get(id));
		}
		j.setMsg("删除景点门票表成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:scenicspotticket:scenicSpotTicket:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(ScenicSpotTicket scenicSpotTicket, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "景点门票表"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<ScenicSpotTicket> page = scenicSpotTicketService.findPage(new Page<ScenicSpotTicket>(request, response, -1), scenicSpotTicket);
    		new ExportExcel("景点门票表", ScenicSpotTicket.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出景点门票表记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("meiguotong:scenicspotticket:scenicSpotTicket:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<ScenicSpotTicket> list = ei.getDataList(ScenicSpotTicket.class);
			for (ScenicSpotTicket scenicSpotTicket : list){
				try{
					scenicSpotTicketService.save(scenicSpotTicket);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条景点门票表记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条景点门票表记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入景点门票表失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/scenicspotticket/scenicSpotTicket/?repage";
    }
	
	/**
	 * 下载导入景点门票表数据模板
	 */
	@RequiresPermissions("meiguotong:scenicspotticket:scenicSpotTicket:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "景点门票表数据导入模板.xlsx";
    		List<ScenicSpotTicket> list = Lists.newArrayList(); 
    		new ExportExcel("景点门票表数据", ScenicSpotTicket.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/scenicspotticket/scenicSpotTicket/?repage";
    }

}