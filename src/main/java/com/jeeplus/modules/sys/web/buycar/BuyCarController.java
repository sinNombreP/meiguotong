/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.web.buycar;

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
import com.jeeplus.modules.sys.entity.buycar.BuyCar;
import com.jeeplus.modules.sys.service.buycar.BuyCarService;

/**
 * 购物车Controller
 * @author dong
 * @version 2018-03-08
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/buycar/buyCar")
public class BuyCarController extends BaseController {

	@Autowired
	private BuyCarService buyCarService;
	
	@ModelAttribute
	public BuyCar get(@RequestParam(required=false) String id) {
		BuyCar entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = buyCarService.get(id);
		}
		if (entity == null){
			entity = new BuyCar();
		}
		return entity;
	}
	
	/**
	 * 购物车列表页面
	 */
	@RequiresPermissions("sys:buycar:buyCar:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/sys/buycar/buyCarList";
	}
	
		/**
	 * 购物车列表数据
	 */
	@ResponseBody
	@RequiresPermissions("sys:buycar:buyCar:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(BuyCar buyCar, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<BuyCar> page = buyCarService.findPage(new Page<BuyCar>(request, response), buyCar); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑购物车表单页面
	 */
	@RequiresPermissions(value={"sys:buycar:buyCar:view","sys:buycar:buyCar:add","sys:buycar:buyCar:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(BuyCar buyCar, Model model) {
		model.addAttribute("buyCar", buyCar);
		return "modules/sys/buycar/buyCarForm";
	}

	/**
	 * 保存购物车
	 */
	@ResponseBody
	@RequiresPermissions(value={"sys:buycar:buyCar:add","sys:buycar:buyCar:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(BuyCar buyCar, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, buyCar)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		buyCarService.save(buyCar);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存购物车成功");
		return j;
	}
	
	/**
	 * 删除购物车
	 */
	@ResponseBody
	@RequiresPermissions("sys:buycar:buyCar:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(BuyCar buyCar, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		buyCarService.delete(buyCar);
		j.setMsg("删除购物车成功");
		return j;
	}
	
	/**
	 * 批量删除购物车
	 */
	@ResponseBody
	@RequiresPermissions("sys:buycar:buyCar:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			buyCarService.delete(buyCarService.get(id));
		}
		j.setMsg("删除购物车成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("sys:buycar:buyCar:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(BuyCar buyCar, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "购物车"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<BuyCar> page = buyCarService.findPage(new Page<BuyCar>(request, response, -1), buyCar);
    		new ExportExcel("购物车", BuyCar.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出购物车记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("sys:buycar:buyCar:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<BuyCar> list = ei.getDataList(BuyCar.class);
			for (BuyCar buyCar : list){
				try{
					buyCarService.save(buyCar);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条购物车记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条购物车记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入购物车失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/buycar/buyCar/?repage";
    }
	
	/**
	 * 下载导入购物车数据模板
	 */
	@RequiresPermissions("sys:buycar:buyCar:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "购物车数据导入模板.xlsx";
    		List<BuyCar> list = Lists.newArrayList(); 
    		new ExportExcel("购物车数据", BuyCar.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/buycar/buyCar/?repage";
    }

}