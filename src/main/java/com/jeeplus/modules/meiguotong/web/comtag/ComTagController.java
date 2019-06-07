/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.web.comtag;

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
import com.jeeplus.modules.meiguotong.entity.comlanguage.ComLanguage;
import com.jeeplus.modules.meiguotong.entity.comtag.ComTag;
import com.jeeplus.modules.meiguotong.service.comlanguage.ComLanguageService;
import com.jeeplus.modules.meiguotong.service.comtag.ComTagService;

/**
 * 各项管理Controller
 * 
 * @author cdq
 * @version 2018-07-31
 */
@Controller
@RequestMapping(value = "${adminPath}/meiguotong/comtag/comTag")
public class ComTagController extends BaseController {

	@Autowired
	private ComTagService comTagService;
	@Autowired
	private ComLanguageService comLanguageService;

	@ModelAttribute
	public ComTag get(@RequestParam(required = false) String id) {
		ComTag entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = comTagService.get(id);
		}
		if (entity == null) {
			entity = new ComTag();
		}
		return entity;
	}

	/**
	 * @Title: getDate
	 * @Description: 获取标签内容
	 * @author 彭善智
	 * @date 2018年8月15日上午10:21:38
	 */
	@ResponseBody
	@RequestMapping(value = "getDate")
	public AjaxJson getDate(ComTag comTag, HttpServletRequest request, HttpServletResponse response,
			Model model) {
		AjaxJson j = new AjaxJson();
		try {
			Page<ComTag> page = comTagService.getDate(new Page<ComTag>(request, response,-1), comTag);
			j.getBody().put("comTagList", page.getList());
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("获取标签内容失败");
		}

		return j;
	}

	/**
	 * 常规路线、当地参团、游轮、景点标签管理列表页面
	 */
	@RequiresPermissions("meiguotong:comtag:comTag:list")
	@RequestMapping(value = { "list", "" })
	public String list(Integer type,Model model) {
		model.addAttribute("type", type);
		return "modules/meiguotong/comtag/comTagList";
	}
	/**
	 * 常规路线、当地参团、游轮、景点标签管理列表数据
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:comtag:comTag:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(ComTag comTag, HttpServletRequest request, HttpServletResponse response,
			Model model) {
		Page<ComTag> page = comTagService.findPage(new Page<ComTag>(request, response), comTag);
		return getBootstrapData(page);
	}

	/**
	 * 当地玩家，商务定制列表页面
	 */
	@RequiresPermissions("meiguotong:comtag:comTag:list")
	@RequestMapping(value = { "Player" })
	public String Player(Integer type,Model model) {
		model.addAttribute("type", type);
		return "modules/meiguotong/comtag/comTagPlayerList";
	}

	/**
	 * 当地玩家，商务定制管理列表数据
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:comtag:comTag:list")
	@RequestMapping(value = "PlayerDate")
	public Map<String, Object> PlayerDate(ComTag comTag, HttpServletRequest request, HttpServletResponse response,
			Model model) {
		Page<ComTag> page = comTagService.PlayerList(new Page<ComTag>(request, response), comTag);
		return getBootstrapData(page);
	}


	
	/**
	 * 常规路线、当地参团、游轮、景点标签管理表单添加页面
	 */
	@RequiresPermissions(value = {"meiguotong:comtag:comTag:add"}, logical = Logical.OR)
	@RequestMapping(value = "form")
	public String form(ComTag comTag, Model model) {
		List<ComLanguage> comLanguageList = comLanguageService.findLanguage();
		model.addAttribute("comLanguageList", comLanguageList);
		model.addAttribute("comTag", comTag);
		return "modules/meiguotong/comtag/comTagForm";
	}


	/**
	 * 当地玩家，商务定制增加管理表单页面
	 */
	@RequiresPermissions(value = { "meiguotong:comtag:comTag:view", "meiguotong:comtag:comTag:add",
			"meiguotong:comtag:comTag:edit" }, logical = Logical.OR)
	@RequestMapping(value = "PlayerForm")
	public String PlayerForm(ComTag comTag, Model model) {
		List<ComLanguage> comLanguageList = comLanguageService.findLanguage();
		model.addAttribute("comLanguageList", comLanguageList);
		model.addAttribute("comTag", comTag);
		return "modules/meiguotong/comtag/comTagPlayerForm";
	}

	
	/**
	 * 各个编辑表单页面
	 */
	@RequiresPermissions(value = { "meiguotong:comtag:comTag:view", "meiguotong:comtag:comTag:add",
			"meiguotong:comtag:comTag:edit" }, logical = Logical.OR)
	@RequestMapping(value = "EditForm")
	public String EditForm(ComTag comTag, Model model) {
		List<ComLanguage> comLanguageList = comLanguageService.findLanguage();
		model.addAttribute("comLanguageList", comLanguageList);
		model.addAttribute("comTag", comTag);
		return "modules/meiguotong/comtag/comTagEditForm";
	}
	
	/**
	 * 保存各项管理
	 */
	@ResponseBody
	@RequiresPermissions(value = { "meiguotong:comtag:comTag:add",
			"meiguotong:comtag:comTag:edit" }, logical = Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(ComTag comTag, Model model, RedirectAttributes redirectAttributes) throws Exception {
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, comTag)) {
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		comTagService.save(comTag);// 新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存各项管理成功");
		return j;
	}

	/**
	 * 修改状态
	 */
	@ResponseBody
	@RequestMapping(value = "status")
	public AjaxJson status(ComTag comTag, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		if (comTag.getStatus() == 2) {
			comTag.setStatus(1);
		} else {
			comTag.setStatus(2);
		}
		comTagService.status(comTag);
		j.setMsg("修改状态成功");
		return j;
	}

	/**
	 * 删除各项管理
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:comtag:comTag:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(ComTag comTag, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		comTagService.delete(comTag);
		j.setMsg("删除各项管理成功");
		return j;
	}

	/**
	 * 批量删除各项管理
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:comtag:comTag:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] = ids.split(",");
		for (String id : idArray) {
			comTagService.delete(comTagService.get(id));
		}
		j.setMsg("删除各项管理成功");
		return j;
	}
	/**
	 * 根据语言获取标签
	 */
	@ResponseBody
	@RequestMapping(value = "getLabelByLanguage")
	public AjaxJson getLabelByLanguage(ComTag comTag, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		List<ComTag> list = comTagService.getLabelByLanguage(comTag);
		if(list!=null){
			j.getBody().put("list", list);
		}else{
			j.getBody().put("list", new ArrayList<ComTag>());
		}
		j.setMsg("根据语言获取标签成功");
		return j;
	}
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:comtag:comTag:export")
	@RequestMapping(value = "export", method = RequestMethod.POST)
	public AjaxJson exportFile(ComTag comTag, HttpServletRequest request, HttpServletResponse response,
			RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
			String fileName = "各项管理" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
			Page<ComTag> page = comTagService.findPage(new Page<ComTag>(request, response, -1), comTag);
			new ExportExcel("各项管理", ComTag.class).setDataList(page.getList()).write(response, fileName).dispose();
			j.setSuccess(true);
			j.setMsg("导出成功！");
			return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出各项管理记录失败！失败信息：" + e.getMessage());
		}
		return j;
	}

	/**
	 * 导入Excel数据
	 * 
	 */
	@RequiresPermissions("meiguotong:comtag:comTag:import")
	@RequestMapping(value = "import", method = RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<ComTag> list = ei.getDataList(ComTag.class);
			for (ComTag comTag : list) {
				try {
					comTagService.save(comTag);
					successNum++;
				} catch (ConstraintViolationException ex) {
					failureNum++;
				} catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum > 0) {
				failureMsg.insert(0, "，失败 " + failureNum + " 条各项管理记录。");
			}
			addMessage(redirectAttributes, "已成功导入 " + successNum + " 条各项管理记录" + failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入各项管理失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + Global.getAdminPath() + "/meiguotong/comtag/comTag/?repage";
	}

	/**
	 * 下载导入各项管理数据模板
	 */
	@RequiresPermissions("meiguotong:comtag:comTag:import")
	@RequestMapping(value = "import/template")
	public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
			String fileName = "各项管理数据导入模板.xlsx";
			List<ComTag> list = Lists.newArrayList();
			new ExportExcel("各项管理数据", ComTag.class, 1).setDataList(list).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + Global.getAdminPath() + "/meiguotong/comtag/comTag/?repage";
	}

}