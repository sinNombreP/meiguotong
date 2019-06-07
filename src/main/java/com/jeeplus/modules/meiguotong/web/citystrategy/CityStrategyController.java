/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.web.citystrategy;

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
import com.jeeplus.modules.meiguotong.entity.comlanguage.ComLanguage;
import com.jeeplus.modules.meiguotong.service.citystrategy.CityStrategyService;
import com.jeeplus.modules.meiguotong.service.comlanguage.ComLanguageService;


/**
 * 城市攻略表Controller
 * @author cdq
 * @version 2018-08-01
 */
@Controller
@RequestMapping(value = "${adminPath}/meiguotong/citystrategy/cityStrategy")
public class CityStrategyController extends BaseController {

	@Autowired
	private CityStrategyService cityStrategyService;
	@Autowired
	private ComLanguageService comLanguageService;
	
	@ModelAttribute
	public CityStrategy get(@RequestParam(required=false) String id) {
		CityStrategy entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = cityStrategyService.get(id);
		}
		if (entity == null){
			entity = new CityStrategy();
		}
		return entity;
	}
	
	/**
	 * 城市攻略表列表页面
	 */
	@RequiresPermissions("meiguotong:citystrategy:cityStrategy:list")
	@RequestMapping(value = {"list", ""})
	public String list(Integer cityId,Model model) {
		model.addAttribute("cityId", cityId);
		List<ComLanguage> comLanguageList=comLanguageService.findLanguage();
		model.addAttribute("comLanguageList", comLanguageList);
		return "modules/meiguotong/citystrategy/cityStrategyList";
	}
	
		/**
	 * 城市攻略表列表数据
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:citystrategy:cityStrategy:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(CityStrategy cityStrategy, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CityStrategy> page = cityStrategyService.findPage(new Page<CityStrategy>(request, response), cityStrategy); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑城市攻略表表单页面
	 */
	@RequiresPermissions(value={"meiguotong:citystrategy:cityStrategy:view","meiguotong:citystrategy:cityStrategy:add","meiguotong:citystrategy:cityStrategy:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(CityStrategy cityStrategy, Model model) {
		model.addAttribute("cityStrategy", cityStrategy);
		return "modules/meiguotong/citystrategy/cityStrategyForm";
	}

	/**
	 * 保存城市攻略表
	 */
	@ResponseBody
	@RequiresPermissions(value={"meiguotong:citystrategy:cityStrategy:add","meiguotong:citystrategy:cityStrategy:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(CityStrategy cityStrategy, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, cityStrategy)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		cityStrategyService.save(cityStrategy);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存城市攻略表成功");
		return j;
	}
	   /**
	   *更改状态
	   */
		@ResponseBody
		@RequestMapping(value = "status")
		public AjaxJson status(CityStrategy cityStrategy, RedirectAttributes redirectAttributes) {
			AjaxJson j = new AjaxJson();
			if(cityStrategy.getStatus()==0){
				cityStrategy.setStatus(1);
			}else{
				cityStrategy.setStatus(0);
			}
			cityStrategyService.status(cityStrategy);
			if(cityStrategy.getStatus()==0){
				j.setMsg("更改成功");
			}else{
				j.setMsg("更改成功");
			}
			return j;
		}
		   /**
		   *设置精华状态
		   */
			@ResponseBody
			@RequestMapping(value = "isEssence")
			public AjaxJson isEssence(CityStrategy cityStrategy, RedirectAttributes redirectAttributes) {
				AjaxJson j = new AjaxJson();
				if(cityStrategy.getIsEssence()==0){
					cityStrategy.setIsEssence(1);
				}else{
					cityStrategy.setIsEssence(0);
				}
				cityStrategyService.isEssence(cityStrategy);
				if(cityStrategy.getIsEssence()==0){
					j.setMsg("更改成功");
				}else{
					j.setMsg("更改成功");
				}
				return j;
			}
	/**
	 * 删除城市攻略表
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:citystrategy:cityStrategy:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(CityStrategy cityStrategy, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		cityStrategyService.delete(cityStrategy);
		j.setMsg("删除城市攻略表成功");
		return j;
	}
	
	/**
	 * 批量删除城市攻略表
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:citystrategy:cityStrategy:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			cityStrategyService.delete(cityStrategyService.get(id));
		}
		j.setMsg("删除城市攻略表成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:citystrategy:cityStrategy:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(CityStrategy cityStrategy, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "城市攻略表"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<CityStrategy> page = cityStrategyService.findPage(new Page<CityStrategy>(request, response, -1), cityStrategy);
    		new ExportExcel("城市攻略表", CityStrategy.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出城市攻略表记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("meiguotong:citystrategy:cityStrategy:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<CityStrategy> list = ei.getDataList(CityStrategy.class);
			for (CityStrategy cityStrategy : list){
				try{
					cityStrategyService.save(cityStrategy);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条城市攻略表记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条城市攻略表记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入城市攻略表失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/citystrategy/cityStrategy/?repage";
    }
	
	/**
	 * 下载导入城市攻略表数据模板
	 */
	@RequiresPermissions("meiguotong:citystrategy:cityStrategy:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "城市攻略表数据导入模板.xlsx";
    		List<CityStrategy> list = Lists.newArrayList(); 
    		new ExportExcel("城市攻略表数据", CityStrategy.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/citystrategy/cityStrategy/?repage";
    }

}