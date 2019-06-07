/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.web.comcomment;

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
import com.jeeplus.modules.meiguotong.entity.citystrategy.CityStrategy;
import com.jeeplus.modules.meiguotong.entity.comcomment.ComComment;
import com.jeeplus.modules.meiguotong.entity.comlanguage.ComLanguage;
import com.jeeplus.modules.meiguotong.service.comcomment.ComCommentService;
import com.jeeplus.modules.meiguotong.service.comlanguage.ComLanguageService;

/**
 * 评论表Controller
 * 
 * @author cdq
 * @version 2018-08-01
 */
@Controller
@RequestMapping(value = "${adminPath}/meiguotong/comcomment/comComment")
public class ComCommentController extends BaseController {

	@Autowired
	private ComCommentService comCommentService;
	@Autowired
	private ComLanguageService comLanguageService;

	@ModelAttribute
	public ComComment get(@RequestParam(required = false) String id) {
		ComComment entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = comCommentService.get(id);
		}
		if (entity == null) {
			entity = new ComComment();
		}
		return entity;
	}

	/**
	 * 评论表列表页面
	 */
	@RequiresPermissions("meiguotong:comcomment:comComment:list")
	@RequestMapping(value = { "list", "" })
	public String list(Integer fatherId, Model model) {
		model.addAttribute("fatherId", fatherId);
		return "modules/meiguotong/comcomment/comCommentList";
	}

	/**
	 * 评论表列表数据
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:comcomment:comComment:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(ComComment comComment, HttpServletRequest request, HttpServletResponse response,
			Model model) {
		Page<ComComment> page = comCommentService.findPage(new Page<ComComment>(request, response), comComment);
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑评论表表单页面
	 */
	@RequiresPermissions(value = { "meiguotong:comcomment:comComment:view", "meiguotong:comcomment:comComment:add",
			"meiguotong:comcomment:comComment:edit" }, logical = Logical.OR)
	@RequestMapping(value = "form")
	public String form(ComComment comComment, Model model) {
		model.addAttribute("comComment", comComment);
		return "modules/meiguotong/comcomment/comCommentForm";
	}
	/**
	 * 攻略评论表列表页面
	 */
	@RequestMapping(value = { "strategyComment" })
	public String childComment(ComComment comComment, Model model) {
		// 语言
		List<ComLanguage> comLanguageList = comLanguageService.findList(new ComLanguage());
		model.addAttribute("comLanguageList", comLanguageList);
		model.addAttribute("comComment", comComment);
		return "modules/meiguotong/comcomment/strategyCommentList";
	}
	/**
	 * 攻略评论表列表数据
	 */
	@ResponseBody
	@RequestMapping(value = "strategyCommentData")
	public Map<String, Object> strategyCommentData(ComComment comComment, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Page<ComComment> page = comCommentService.getCommentDate(new Page<ComComment>(request, response), comComment);
		return getBootstrapData(page);
	}
	/**
	 * @Title: goComment
	 * @Description: 评论列表表单页面
	 * @author 彭善智
	 * @date 2018年8月16日上午9:55:57
	 */
	@RequestMapping(value = { "goComment" })
	public String goComment(ComComment comComment, Model model) {
		// 语言
		List<ComLanguage> comLanguageList = comLanguageService.findList(new ComLanguage());
		model.addAttribute("comLanguageList", comLanguageList);
		model.addAttribute("comComment", comComment);
		return "modules/meiguotong/product/comCommentList";
	}

	/**
	 * @Title: getCommentDate
	 * @Description: 评论列表数据
	 * @author 彭善智
	 * @date 2018年8月16日上午9:59:21
	 */
	@ResponseBody
	@RequestMapping(value = "getCommentDate")
	public Map<String, Object> getCommentDate(ComComment comComment, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Page<ComComment> page = comCommentService.getCommentDate(new Page<ComComment>(request, response), comComment);
		return getBootstrapData(page);
	}

	/**
	 * @Title: updateStatus
	 * @Description: 启用禁用
	 * @author 彭善智
	 * @date 2018年8月16日下午2:49:00
	 */
	@ResponseBody
	@RequestMapping(value = "updateStatus")
	public AjaxJson updateStatus(ComComment comComment, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		comCommentService.updateStatus(comComment);
		j.setMsg("更新状态成功");
		return j;
	}

	/**
	 * 保存评论表
	 */
	@ResponseBody
	@RequiresPermissions(value = { "meiguotong:comcomment:comComment:add",
			"meiguotong:comcomment:comComment:edit" }, logical = Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(ComComment comComment, Model model, RedirectAttributes redirectAttributes) throws Exception {
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, comComment)) {
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		comCommentService.save(comComment);// 新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存评论表成功");
		return j;
	}

	/**
	 * 更改状态
	 */
	@ResponseBody
	@RequestMapping(value = "status")
	public AjaxJson status(ComComment comComment, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		if (comComment.getStatus() == 0) {
			comComment.setStatus(1);
		} else {
			comComment.setStatus(0);
		}
		comCommentService.status(comComment);
		if (comComment.getStatus() == 0) {
			j.setMsg("更改成功");
		} else {
			j.setMsg("更改成功");
		}
		return j;
	}

	/**
	 * 删除评论表
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:comcomment:comComment:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(ComComment comComment, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		comCommentService.delete(comComment);
		j.setMsg("删除评论表成功");
		return j;
	}

	/**
	 * 批量删除评论表
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:comcomment:comComment:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] = ids.split(",");
		for (String id : idArray) {
			comCommentService.delete(comCommentService.get(id));
		}
		j.setMsg("删除评论表成功");
		return j;
	}

	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:comcomment:comComment:export")
	@RequestMapping(value = "export", method = RequestMethod.POST)
	public AjaxJson exportFile(ComComment comComment, HttpServletRequest request, HttpServletResponse response,
			RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
			String fileName = "评论表" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
			Page<ComComment> page = comCommentService.findPage(new Page<ComComment>(request, response, -1), comComment);
			new ExportExcel("评论表", ComComment.class).setDataList(page.getList()).write(response, fileName).dispose();
			j.setSuccess(true);
			j.setMsg("导出成功！");
			return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出评论表记录失败！失败信息：" + e.getMessage());
		}
		return j;
	}

	/**
	 * 导入Excel数据
	 * 
	 */
	@RequiresPermissions("meiguotong:comcomment:comComment:import")
	@RequestMapping(value = "import", method = RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<ComComment> list = ei.getDataList(ComComment.class);
			for (ComComment comComment : list) {
				try {
					comCommentService.save(comComment);
					successNum++;
				} catch (ConstraintViolationException ex) {
					failureNum++;
				} catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum > 0) {
				failureMsg.insert(0, "，失败 " + failureNum + " 条评论表记录。");
			}
			addMessage(redirectAttributes, "已成功导入 " + successNum + " 条评论表记录" + failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入评论表失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + Global.getAdminPath() + "/meiguotong/comcomment/comComment/?repage";
	}

	/**
	 * 下载导入评论表数据模板
	 */
	@RequiresPermissions("meiguotong:comcomment:comComment:import")
	@RequestMapping(value = "import/template")
	public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
			String fileName = "评论表数据导入模板.xlsx";
			List<ComComment> list = Lists.newArrayList();
			new ExportExcel("评论表数据", ComComment.class, 1).setDataList(list).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + Global.getAdminPath() + "/meiguotong/comcomment/comComment/?repage";
	}
	

	/**
	 * 城市评论表列表数据
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:comcomment:comComment:list")
	@RequestMapping(value = "cityCommentDate")
	public Map<String, Object> cityCommentDate(ComComment comComment, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Page<ComComment> page = comCommentService.cityComment(new Page<ComComment>(request, response), comComment);
		return getBootstrapData(page);
	}

	/**
	 * 邮轮评论表列表数据
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:comcomment:comComment:list")
	@RequestMapping(value = "CruiseCommentDate")
	public Map<String, Object> CruiseCommentDate(ComComment comComment, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Page<ComComment> page = comCommentService.CruiseComment(new Page<ComComment>(request, response), comComment);
		return getBootstrapData(page);
	}

	/**
	 * 邮轮子类评论表列表数据
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:comcomment:comComment:list")
	@RequestMapping(value = "CruiseChildCommentDate")
	public Map<String, Object> CruiseChildCommentDate(ComComment comComment, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Page<ComComment> page = comCommentService.CruiseChildComment(new Page<ComComment>(request, response),
				comComment);
		return getBootstrapData(page);
	}

	/**
	 * 景点表列表数据
	 */
	@ResponseBody
	// @RequiresPermissions("meiguotong:comcomment:comComment:list")
	@RequestMapping(value = "SceniceCommentData")
	public Map<String, Object> SceniceCommentData(ComComment comComment, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Page<ComComment> page = comCommentService.SceniceComment(new Page<ComComment>(request, response), comComment);
		return getBootstrapData(page);
	}

	/**
	 * 当地玩家表列表数据
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:comcomment:comComment:list")
	@RequestMapping(value = "playerCommentData")
	public Map<String, Object> playerCommentData(ComComment comComment, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Page<ComComment> page = comCommentService.playerComment(new Page<ComComment>(request, response), comComment);
		return getBootstrapData(page);
	}

	/**
	 * 当地玩家表列表页面
	 */
	@RequiresPermissions("meiguotong:comcomment:comComment:list")
	@RequestMapping(value = { "playerCommet" })
	public String playerCommet(Integer typeId, Model model) {
		model.addAttribute("typeId", typeId);
		return "modules/meiguotong/comcomment/playerCommentList";
	}

	

	/**
	 * 城市评论表列表页面
	 */
	@RequiresPermissions("meiguotong:comcomment:comComment:list")
	@RequestMapping(value = { "cityComment" })
	public String cityComment(Integer typeId, Model model) {
		model.addAttribute("typeId", typeId);
		return "modules/meiguotong/comcomment/cityCommentList";
	}

	/**
	 * 邮轮评论表列表页面
	 */
	@RequiresPermissions("meiguotong:comcomment:comComment:list")
	@RequestMapping(value = { "CruiseComment" })
	public String CruiseComment() {
		return "modules/meiguotong/comcomment/cruiseCommentList";
	}

	/**
	 * 城市子类评论表列表页面
	 */
	@RequiresPermissions("meiguotong:comcomment:comComment:list")
	@RequestMapping(value = { "CruiseChildComment" })
	public String CruiseChildComment(Integer typeId, Model model) {
		model.addAttribute("typeId", typeId);
		return "modules/meiguotong/comcomment/cruiseChildCommentList";
	}

	/**
	 * 景点评论表列表页面
	 */
	// @RequiresPermissions("meiguotong:comcomment:comComment:list")
	@RequestMapping(value = { "sceniceComment" })
	public String sceniceComment(Integer typeId, Model model) {
		model.addAttribute("typeId", typeId);
		return "modules/meiguotong/comcomment/sceniceCommentList";
	}
}