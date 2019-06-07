/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.web;

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
import com.jeeplus.modules.sys.entity.MemberCollection;
import com.jeeplus.modules.sys.service.MemberCollectionService;

/**
 * 个人收藏Controller
 * @author xudemo
 * @version 2018-02-27
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/memberCollection")
public class MemberCollectionController extends BaseController {

	@Autowired
	private MemberCollectionService memberCollectionService;
	
	@ModelAttribute
	public MemberCollection get(@RequestParam(required=false) String id) {
		MemberCollection entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = memberCollectionService.get(id);
		}
		if (entity == null){
			entity = new MemberCollection();
		}
		return entity;
	}
	
	/**
	 * 个人收藏列表页面
	 */
	@RequiresPermissions("sys:memberCollection:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/sys/memberCollectionList";
	}
	
		/**
	 * 个人收藏列表数据
	 */
	@ResponseBody
	@RequiresPermissions("sys:memberCollection:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(MemberCollection memberCollection, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<MemberCollection> page = memberCollectionService.findPage(new Page<MemberCollection>(request, response), memberCollection); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑个人收藏表单页面
	 */
	@RequiresPermissions(value={"sys:memberCollection:view","sys:memberCollection:add","sys:memberCollection:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(MemberCollection memberCollection, Model model) {
		model.addAttribute("memberCollection", memberCollection);
		if(StringUtils.isBlank(memberCollection.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/sys/memberCollectionForm";
	}

	/**
	 * 保存个人收藏
	 */
	@RequiresPermissions(value={"sys:memberCollection:add","sys:memberCollection:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(MemberCollection memberCollection, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, memberCollection)){
			return form(memberCollection, model);
		}
		//新增或编辑表单保存
		memberCollectionService.save(memberCollection);//保存
		addMessage(redirectAttributes, "保存个人收藏成功");
		return "redirect:"+Global.getAdminPath()+"/sys/memberCollection/?repage";
	}
	
	/**
	 * 删除个人收藏
	 */
	@ResponseBody
	@RequiresPermissions("sys:memberCollection:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(MemberCollection memberCollection, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		memberCollectionService.delete(memberCollection);
		j.setMsg("删除个人收藏成功");
		return j;
	}
	
	/**
	 * 批量删除个人收藏
	 */
	@ResponseBody
	@RequiresPermissions("sys:memberCollection:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			memberCollectionService.delete(memberCollectionService.get(id));
		}
		j.setMsg("删除个人收藏成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("sys:memberCollection:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(MemberCollection memberCollection, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "个人收藏"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<MemberCollection> page = memberCollectionService.findPage(new Page<MemberCollection>(request, response, -1), memberCollection);
    		new ExportExcel("个人收藏", MemberCollection.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出个人收藏记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("sys:memberCollection:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<MemberCollection> list = ei.getDataList(MemberCollection.class);
			for (MemberCollection memberCollection : list){
				try{
					memberCollectionService.save(memberCollection);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条个人收藏记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条个人收藏记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入个人收藏失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/memberCollection/?repage";
    }
	
	/**
	 * 下载导入个人收藏数据模板
	 */
	@RequiresPermissions("sys:memberCollection:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "个人收藏数据导入模板.xlsx";
    		List<MemberCollection> list = Lists.newArrayList(); 
    		new ExportExcel("个人收藏数据", MemberCollection.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/memberCollection/?repage";
    }

}