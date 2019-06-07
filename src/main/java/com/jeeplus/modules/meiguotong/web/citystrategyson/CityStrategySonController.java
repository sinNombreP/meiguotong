/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.web.citystrategyson;

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
import com.jeeplus.modules.meiguotong.entity.citystrategyson.CityStrategySon;
import com.jeeplus.modules.meiguotong.service.citystrategyson.CityStrategySonService;

/**
 * 攻略子类表Controller
 * @author cdq
 * @version 2018-08-01
 */
@Controller
@RequestMapping(value = "${adminPath}/meiguotong/citystrategyson/cityStrategySon")
public class CityStrategySonController extends BaseController {

	@Autowired
	private CityStrategySonService cityStrategySonService;
	
	@ModelAttribute
	public CityStrategySon get(@RequestParam(required=false) String id) {
		CityStrategySon entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = cityStrategySonService.get(id);
		}
		if (entity == null){
			entity = new CityStrategySon();
		}
		return entity;
	}
	
	/**
	 * 攻略子类表列表页面
	 */
	@RequiresPermissions("meiguotong:citystrategyson:cityStrategySon:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/meiguotong/citystrategyson/cityStrategySonList";
	}
	
		/**
	 * 攻略子类表列表数据
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:citystrategyson:cityStrategySon:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(CityStrategySon cityStrategySon, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CityStrategySon> page = cityStrategySonService.findPage(new Page<CityStrategySon>(request, response), cityStrategySon); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑攻略子类表表单页面
	 */
	@RequiresPermissions(value={"meiguotong:citystrategyson:cityStrategySon:view","meiguotong:citystrategyson:cityStrategySon:add","meiguotong:citystrategyson:cityStrategySon:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(CityStrategySon cityStrategySon, Model model) {
		model.addAttribute("cityStrategySon", cityStrategySon);
		return "modules/meiguotong/citystrategyson/cityStrategySonForm";
	}

	/**
	 * 保存攻略子类表
	 */
	@ResponseBody
	@RequiresPermissions(value={"meiguotong:citystrategyson:cityStrategySon:add","meiguotong:citystrategyson:cityStrategySon:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(CityStrategySon cityStrategySon, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, cityStrategySon)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		cityStrategySonService.save(cityStrategySon);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存攻略子类表成功");
		return j;
	}
	
	/**
	 * 删除攻略子类表
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:citystrategyson:cityStrategySon:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(CityStrategySon cityStrategySon, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		cityStrategySonService.delete(cityStrategySon);
		j.setMsg("删除攻略子类表成功");
		return j;
	}
	
	/**
	 * 批量删除攻略子类表
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:citystrategyson:cityStrategySon:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			cityStrategySonService.delete(cityStrategySonService.get(id));
		}
		j.setMsg("删除攻略子类表成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:citystrategyson:cityStrategySon:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(CityStrategySon cityStrategySon, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "攻略子类表"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<CityStrategySon> page = cityStrategySonService.findPage(new Page<CityStrategySon>(request, response, -1), cityStrategySon);
    		new ExportExcel("攻略子类表", CityStrategySon.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出攻略子类表记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("meiguotong:citystrategyson:cityStrategySon:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<CityStrategySon> list = ei.getDataList(CityStrategySon.class);
			for (CityStrategySon cityStrategySon : list){
				try{
					cityStrategySonService.save(cityStrategySon);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条攻略子类表记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条攻略子类表记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入攻略子类表失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/citystrategyson/cityStrategySon/?repage";
    }
	
	/**
	 * 下载导入攻略子类表数据模板
	 */
	@RequiresPermissions("meiguotong:citystrategyson:cityStrategySon:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "攻略子类表数据导入模板.xlsx";
    		List<CityStrategySon> list = Lists.newArrayList(); 
    		new ExportExcel("攻略子类表数据", CityStrategySon.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/citystrategyson/cityStrategySon/?repage";
    }

}