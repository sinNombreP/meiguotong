/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.web.guide;

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
import com.jeeplus.common.utils.CodeGen;
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
import com.jeeplus.modules.meiguotong.entity.comtag.ComTag;
import com.jeeplus.modules.meiguotong.entity.guide.Guide;
import com.jeeplus.modules.meiguotong.entity.order.OrderSys;
import com.jeeplus.modules.meiguotong.service.comtag.ComTagService;
import com.jeeplus.modules.meiguotong.service.guide.GuideService;
import com.jeeplus.modules.meiguotong.service.order.OrderSysService;
import com.jeeplus.modules.sys.entity.member.Member;
import com.jeeplus.modules.sys.entity.member.MemberInformation;
import com.jeeplus.modules.sys.entity.member.SysUserType;
import com.jeeplus.modules.sys.service.member.MemberInformationService;
import com.jeeplus.modules.sys.service.member.MemberService;
import com.jeeplus.modules.sys.utils.CodeGenUtils;

/**
 * 导游表Controller
 * @author cdq
 * @version 2018-08-20
 */
@Controller
@RequestMapping(value = "${adminPath}/meiguotong/guide/guide")
public class GuideController extends BaseController {

	@Autowired
	private GuideService guideService;
	@Autowired
	private ComTagService comTagService;
	@Autowired
	private MemberService memberService;
	@Autowired
	private MemberInformationService memberInformationService;
	@Autowired
	private OrderSysService orderSysService;
	@ModelAttribute
	public Guide get(@RequestParam(required=false) String id) {
		Guide entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = guideService.get(id);
		}
		if (entity == null){
			entity = new Guide();
		}
		return entity;
	}
	
	/**
	 * 导游表列表页面
	 */
	@RequiresPermissions("meiguotong:guide:guide:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/meiguotong/guide/guideList";
	}
	
		/**
	 * 导游表列表数据
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:guide:guide:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Guide guide, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Guide> page = guideService.findPage(new Page<Guide>(request, response), guide); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑导游表表单页面
	 */
	@RequiresPermissions(value={"meiguotong:guide:guide:view","meiguotong:guide:guide:add","meiguotong:guide:guide:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Guide guide, Model model) {
		
		//Member表的数据
		Member getMember=new Member();
		getMember.setId(guide.getMemberid());
		Member member=memberService.get(getMember);
		model.addAttribute("member", member);
		//MemberInformation表的数据
		MemberInformation getMemberInformation=new MemberInformation();
		getMemberInformation.setMemberid(guide.getMemberid());
		MemberInformation memberInformation =memberInformationService.findByMemberid(getMemberInformation);
		model.addAttribute("memberInformation", memberInformation);
		//当地玩家属性
		List<ComTag> comTagList=comTagService.getGuideTagList(guide);
		model.addAttribute("comTagList", comTagList);
		//抽成比例
/*		Guide guide1=guideService.findDiscount();
		model.addAttribute("guide1", guide1);*/
		model.addAttribute("guide", guide);
		if(StringUtils.isBlank(guide.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/meiguotong/guide/guideForm";
	}

	/**
	 * 保存导游表
	 */
	@RequiresPermissions(value={"meiguotong:guide:guide:add","meiguotong:guide:guide:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(Guide guide, Model model,RedirectAttributes redirectAttributes,
			@RequestParam(value="imageFrontFile1") MultipartFile imageFrontFile1) throws Exception{
		if (!beanValidator(model, guide)){
			return form(guide, model);
		}
		//当地玩家
	/*	ComTag comTag =new ComTag();
		comTag.setType(5);
		comTag.setContent(guide.getContent());		*/	
		//member表
		Member member= new Member();
		member.setId(guide.getMemberid());
		member.setMobile(guide.getMobile());
		member.setEmail(guide.getEmail());
		//memberInformation表
		MemberInformation memberInformation=new MemberInformation();
		memberInformation.setMemberid(guide.getMemberid());
		memberInformation.setPhoto(guide.getPhoto());
		memberInformation.setSex(Integer.parseInt(guide.getSex()));
		memberInformation.setRealName(guide.getRealName());
		//抽成比例
		SysUserType sysUserType=new SysUserType();
		sysUserType.setId(guide.getId());
		sysUserType.setDiscount(guide.getDiscount());
		//新增或编辑表单保存
		//上传图片
		String path="meiguotong/"+CodeGenUtils.getYear()+"/"+CodeGenUtils.getMonth()+"/"+CodeGenUtils.getDay();
		String message="";
		try {
			if(imageFrontFile1 != null && imageFrontFile1.getSize()>0) {
				String key1 = path +"-"+CodeGenUtils.getPicId()+".jpg";
				QiniuUtils.uploadFile(imageFrontFile1.getInputStream(), key1);
				memberInformation.setPhoto(QiniuUtils.QiniuUrl+ key1);
			}
		}catch (Exception e) {
			message = "上传图片失败";
			addMessage(model, message);
			return form(guide, model);
		}
		guideService.save(guide,null,member,memberInformation,sysUserType);//保存
		addMessage(redirectAttributes, "保存导游表成功");
		return "redirect:"+Global.getAdminPath()+"/meiguotong/guide/guide/?repage";
	}
	/**
	 * 修改状态
	 * @param linerLine
	 * @param redirectAttributes
	 * @return
	 */
	 @ResponseBody
		@RequestMapping(value = "type")
		public AjaxJson type(Guide guide, RedirectAttributes redirectAttributes) {
			AjaxJson j = new AjaxJson();
			try {
				guideService.status(guide);
				 j.setSuccess(true);
				 j.setMsg("更新状态成功");
			} catch (Exception e) {
				j.setSuccess(false);
				j.setMsg("更新状态失败");
			}
			return j;
		}
	   /**
	   *修改状态
	   */
		@ResponseBody
		@RequestMapping(value = "status")
		public AjaxJson status(Guide guide, RedirectAttributes redirectAttributes) {
			AjaxJson j = new AjaxJson();
			if(guide.getStatus()==2){
				guide.setStatus(1);
			}else{
				guide.setStatus(2);
			}
			guideService.status(guide);
			j.setMsg("修改状态成功");
			return j;
		}
	 /**
	  *  富文本图片上传
	  */
	 	@ResponseBody
	 	@RequestMapping(value = "uploadIMG")
	 	public void upload(HttpServletRequest request, HttpServletResponse response,
	 			@RequestParam(value="upfile") MultipartFile upfile)  
	             throws Exception {  

	         request.setCharacterEncoding("utf-8");  
	         response.setContentType("text/html");  
	         response.setCharacterEncoding("utf-8");  
	         String path="meiguotong/"+CodeGenUtils.getYear()+"/"+CodeGenUtils.getMonth()+"/"+CodeGenUtils.getDay();
	 		String url ="";
	 		String result ="";
	 		try {
	 			if(upfile != null) {
	 				String key = path +"-"+CodeGen.getPicId()+".jpg";
	 				QiniuUtils.uploadFile(upfile.getInputStream(), key);
	 				url = QiniuUtils.QiniuUrl+ key;
	 				result = "{ \"state\": \""  
	 					      + "SUCCESS" + "\", \"type\": \"" + "jpg" 
	 					      + "\", \"url\": \"" + url + "\"}";  
	 			}
	 		}catch (Exception e) {
	 			result = "{ \"state\": \""  
	 				      + "ERROR" + "\", \"type\": \"" + "jpg" 
	 				      + "\", \"url\": \"" + url + "\"}";  
	 		}
	   
	        result = result.replaceAll("\\\\", "\\\\");  
	        response.getWriter().print(result);  
	 } 
	/**
	 * 删除导游表
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:guide:guide:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(Guide guide, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		guideService.delete(guide);
		j.setMsg("删除导游表成功");
		return j;
	}
	
	/**
	 * 批量删除导游表
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:guide:guide:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			guideService.delete(guideService.get(id));
		}
		j.setMsg("删除导游表成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:guide:guide:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(Guide guide, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "导游表"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Guide> page = guideService.findPage(new Page<Guide>(request, response, -1), guide);
    		new ExportExcel("导游表", Guide.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出导游表记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("meiguotong:guide:guide:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Guide> list = ei.getDataList(Guide.class);
			for (Guide guide : list){
				try{
					guideService.save(guide);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条导游表记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条导游表记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入导游表失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/guide/guide/?repage";
    }
	
	/**
	 * 下载导入导游表数据模板
	 */
	@RequiresPermissions("meiguotong:guide:guide:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "导游表数据导入模板.xlsx";
    		List<Guide> list = Lists.newArrayList(); 
    		new ExportExcel("导游表数据", Guide.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/guide/guide/?repage";
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
			String imgPath="meiguotong/"+CodeGenUtils.getYear()+"/"+CodeGenUtils.getMonth()+"/"+CodeGenUtils.getDay();

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