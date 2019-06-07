/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.web.sysUser;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

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
import com.jeeplus.common.config.Global;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.QiniuUtils;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.security.Digests;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.entity.member.MemberRefund;
import com.jeeplus.modules.sys.entity.sysUser.Role;
import com.jeeplus.modules.sys.entity.sysUser.SysUser;
import com.jeeplus.modules.sys.service.SystemService;
import com.jeeplus.modules.sys.service.member.MemberRefundService;
import com.jeeplus.modules.sys.service.member.SysUserTypeService;
import com.jeeplus.modules.sys.service.sysUser.SysUserService;
import com.jeeplus.modules.sys.utils.CodeGenUtils;
import com.jeeplus.modules.sys.utils.UserUtils;

/**
 * 后台账号管理Controller
 * @author xudemo
 * @version 2018-01-03
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/sysUser")
public class SysUserController extends BaseController {

	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private SysUserTypeService sysUserTypeService;
	@Autowired
	private MemberRefundService memberRefundService;
	
	@ModelAttribute
	public SysUser get(@RequestParam(required=false) String id) {
		SysUser entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = sysUserService.get(id);
		}
		if (entity == null){
			entity = new SysUser();
		}
		return entity;
	}
	

	/** 
	* @Title: list 
	* @Description: 后台账号列表页面
	* @author 彭善智
	* @date 2018年8月10日下午4:40:46
	*/ 
	@RequiresPermissions("sys:sysUser:list")
	@RequestMapping(value = {"list", ""})
	public String list(HttpServletRequest request) {
		return "modules/sys/sysUser/sysUserList";
	}


	/** 
	* @Title: list2 
	* @Description: 供应商管理列表页面
	* @author 彭善智
	* @date 2018年8月10日下午4:40:30
	*/ 
	@RequiresPermissions("sys:sysUser:list2")
	@RequestMapping(value = {"list2"})
	public String list2(HttpServletRequest request) {
		return "modules/sys/member/sysUserList";
	}
	
	
	/** 
	* @Title: data 
	* @Description: 后台账号数据子供应商数据
	* @author 彭善智
	* @date 2018年8月11日下午2:15:07
	*/ 
	@ResponseBody
	@RequiresPermissions("sys:sysUser:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(SysUser sysUser, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		sysUser.setUserType(user.getUserType());
		if(user.getUserType() == 2) {
			sysUser.setFatherid(UserUtils.getUser().getAgentid());
		}
		Page<SysUser> page = sysUserService.findPage(new Page<SysUser>(request, response), sysUser); 
		return getBootstrapData(page);
	}
	
	
	/** 
	* @Title: data2 
	* @Description: 供应商账号数据
	* @author 彭善智
	* @date 2018年8月11日下午2:15:22
	*/ 
	@ResponseBody
	@RequiresPermissions("sys:sysUser:list2")
	@RequestMapping(value = "data2")
	public Map<String, Object> data2(SysUser sysUser, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SysUser> page = sysUserService.findPage(new Page<SysUser>(request, response), sysUser); 
		return getBootstrapData(page);
	}
	
	

	/** 
	* @Title: form 
	* @Description: 查看，增加，编辑后台账号管理表单页面
	* @author 彭善智
	* @date 2018年8月11日下午2:16:13
	*/ 
	@RequestMapping(value = "form")
	public String form(SysUser sysUser, Model model) {
		model.addAttribute("sysUser", sysUser);
		if(StringUtils.isBlank(sysUser.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		//4.用户信息包括权限
		model.addAttribute("sysUser", sysUser);
		Role role = new Role();
		User user = UserUtils.getUser();
		if(user.getUserType()==1){
			role.setCreateById("1");
		}else{
			if(0 == user.getFatherid()){  //父供应商
				role.setCreateById(sysUserService.findByAgentId(user.getAgentid()).getId());
			}else{
				role.setCreateById(sysUserService.findByAgentId(user.getFatherid()).getId());
			}
		}
		role.setType(UserUtils.getUser().getUserType()==1?1:null);
		model.addAttribute("allRoles", systemService.findFatherRoleList(role));
		return "modules/sys/sysUser/sysUserForm";
	}

	
	/** 
	* @Title: form2 
	* @Description: 查看，增加，编辑供应商账号管理表单页面
	* @author 彭善智
	* @date 2018年8月11日下午2:16:34
	*/ 
	@RequestMapping(value = "form2")
	public String form2(SysUser sysUser, Model model) {
		model.addAttribute("sysUser", sysUser);
		model.addAttribute("sysUserTypeList", sysUserTypeService.findListByAgentid(sysUser));  //供应商类型list
		if(StringUtils.isBlank(sysUser.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}else {
			model.addAttribute("userList", sysUserService.findListByfathid(sysUser)); //供应商子账号
			MemberRefund memberRefund = new MemberRefund();
			memberRefund.setTypeId(sysUser.getAgentid());
			memberRefund.setType(2);
			model.addAttribute("memberRefundList", memberRefundService.findListByAgentid(memberRefund)); //供应商退款说明
		}
		//4.用户信息包括权限
		Role role = new Role();
		User user = UserUtils.getUser();
		if(user.getUserType()==1){
			role.setCreateById("1");
		}else{
			if(0 == user.getFatherid()){  //父供应商
				role.setCreateById(sysUserService.findByAgentId(user.getAgentid()).getId());
			}else{
				role.setCreateById(sysUserService.findByAgentId(user.getFatherid()).getId());
			}
		}
		role.setType(2);
		model.addAttribute("allRoles", systemService.findFatherRoleList(role));
		return "modules/sys/member/sysUserForm";
	}
	

	/** 
	* @Title: changeState 
	* @Description: 修改后台账号状态
	* @author 彭善智
	* @date 2018年8月10日下午12:00:13
	*/ 
	@ResponseBody
//	@RequiresPermissions("sys:sysUser:changeState")
	@RequestMapping(value = "changeState")
	public AjaxJson changeState(SysUser sysUser, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
			sysUserService.changeState(sysUser);
			j.setMsg("修改状态成功");
		} catch (Exception e) {
			j.setMsg("修改状态失败");
		}
		return j;
	}
	

	

	/** 
	* @Title: save 
	* @Description: 保存后台账号管理添加子供应商
	* @author 彭善智
	* @date 2018年8月11日下午2:18:21
	*/ 
	@ResponseBody
/*	@RequiresPermissions(value={"sys:sysUser:add","sys:sysUser:edit"},logical=Logical.OR)*/
	@RequestMapping(value = "save")
	public AjaxJson save(SysUser sysUser, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, sysUser)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		if (StringUtils.isNotBlank(sysUser.getNewPassword())) {
			sysUser.setPassword(SystemService.entryptPassword(sysUser.getNewPassword()));
		}else{
			sysUser.setPassword(sysUser.getPassword());
		}
		
		// 角色数据有效性验证，过滤不在授权内的角色
		List<Role> roleList = Lists.newArrayList();
		List<String> roleIdList = sysUser.getRoleIdList();
		for (Role r : systemService.findAllRole()){
			if (roleIdList.contains(r.getId())){
				roleList.add(r);
			}
		}
		sysUser.setRoleList(roleList);
		User user = UserUtils.getUser();
		sysUser.setUserType(user.getUserType());
		if(user.getUserType()==1){  //添加后台账号
			sysUser.setFatherid(0);
		}else{
			sysUser.setFatherid(user.getAgentid());
			sysUser.setAgentid(user.getAgentid());
		}
		sysUserService.save(sysUser);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存后台账号管理成功");
		return j;
	}
	
	
	/** 
	* @Title: addSupp 
	* @Description: 添加/更新供应商(已不用，用save3)
	* @author 彭善智
	* @date 2018年8月10日下午12:02:39
	*/ 
	@RequestMapping(value = "save2")
	public String save2(SysUser sysUser, Model model, RedirectAttributes redirectAttributes,
			HttpServletRequest request) throws Exception{
		if (!beanValidator(model, sysUser)){
			return form(sysUser, model);
		}

		if (StringUtils.isNotBlank(sysUser.getNewPassword())) {
			sysUser.setPassword(SystemService.entryptPassword(sysUser.getNewPassword()));
		}else{
			sysUser.setPassword(sysUser.getPassword());
		}
		if(StringUtils.isBlank(sysUser.getId())){//如果ID是空为添加
			sysUser.setAgentid(sysUserService.getMaxAgentid());
			sysUser.setUserType(2);  //2是供应商类型
			sysUser.setLoginFlag("1");
			sysUser.setFatherid(0);  //0 是父供应商
		}
		List<Role> roleList = Lists.newArrayList();
		List<String> roleIdList = sysUser.getRoleIdList();
		for (Role r : systemService.findAllRole()){
			if (roleIdList.contains(r.getId())){
				roleList.add(r);
			}
		}
		sysUser.setRoleList(roleList);
		//新增或编辑表单保存
		sysUserService.save(sysUser);//新建或者编辑保存
		addMessage(redirectAttributes, "保存供应商信息成功");
		return "redirect:"+Global.getAdminPath()+"/sys/sysUser/list2";
	}

	 /**
	   * @method: save3
	   * @Description:  添加/更新供应商
	   * @Author:   彭善智
	   * @Date:     2018/12/21 9:32
	   */
	@RequestMapping(value = "save3")
	public String save3(SysUser sysUser, Model model, RedirectAttributes redirectAttributes,
						HttpServletRequest request) throws Exception{
		if (!beanValidator(model, sysUser)){
			return form(sysUser, model);
		}

		if (StringUtils.isNotBlank(sysUser.getNewPassword())) {
			sysUser.setPassword(SystemService.entryptPassword(sysUser.getNewPassword()));
		}else{
			sysUser.setPassword(sysUser.getPassword());
		}
		if(StringUtils.isBlank(sysUser.getId())){//如果ID是空为添加
			sysUser.setAgentid(sysUserService.getMaxAgentid());
			sysUser.setUserType(2);  //2是供应商类型
			sysUser.setLoginFlag("1");
			sysUser.setFatherid(0);  //0 是父供应商
		}
		//新增或编辑表单保存
		sysUserService.updateAgent(sysUser);//新建或者编辑保存
		addMessage(redirectAttributes, "保存供应商信息成功");
		return "redirect:"+Global.getAdminPath()+"/sys/sysUser/list2";
	}


	/** 
	* @Title: checkName 
	* @Description: 验证角色名是否有效
	* @author 彭善智
	* @date 2018年8月10日上午11:54:54
	*/ 
	@ResponseBody
	@RequestMapping(value = "checkName")
	public String checkName(String oldName, String loginName) {
		if (loginName!=null && loginName.equals(oldName)) {
			return "true";
		} else if (loginName!=null && sysUserService.getSysUserByName(loginName) == null) {
			return "true";
		}
		return "false";
	}
	
	
	/** 
	* @Title: upload 
	* @Description: 上传图片
	* @author 彭善智
	* @date 2018年8月10日下午12:01:24
	*/ 
	@ResponseBody
	@RequestMapping(value = "uploadIMG")
	public void upload(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value="upfile") MultipartFile upfile)  
            throws Exception {  

        request.setCharacterEncoding("utf-8");  
        response.setContentType("text/html");  
        response.setCharacterEncoding("utf-8");  
        String path="common/"+CodeGenUtils.getYear()+"/"+CodeGenUtils.getMonth()+"/"+CodeGenUtils.getDay();
		String url ="";
		String result ="";
		//身份证正面
			try {
				if(upfile != null) {
					String key = path +"-"+CodeGenUtils.getPicId()+".jpg";
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
	* @Title: enable 
	* @Description: 启用后台账号
	* @author 彭善智
	* @date 2018年8月10日下午4:30:21
	*/ 
	@ResponseBody
/*	@RequiresPermissions("sys:sysUser:enable")*/
	@RequestMapping(value = "enable")
	public AjaxJson enable(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			sysUserService.enable(sysUserService.get(id));
		}
		j.setMsg("启用后台账号成功");
		return j;
	}

	/** 
	* @Title: disable 
	* @Description: 禁用后台账号
	* @author 彭善智
	* @date 2018年8月10日下午4:30:28
	*/ 
	@ResponseBody
/*	@RequiresPermissions("sys:sysUser:disable")*/
	@RequestMapping(value = "disable")
	public AjaxJson disable(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			sysUserService.disable(sysUserService.get(id));
		}
		j.setMsg("禁用后台账号成功");
		return j;
	}
	

	/** 
	* @Title: delete 
	* @Description: 删除后台账号管理
	* @author 彭善智
	* @date 2018年8月10日下午4:30:39
	*/ 
	@ResponseBody
/*	@RequiresPermissions("sys:sysUser:del")*/
	@RequestMapping(value = "delete")
	public AjaxJson delete(SysUser sysUser, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		sysUserService.deleteByLogic(sysUser);
		j.setMsg("删除后台账号管理成功");
		return j;
	}
	
	
	//TODO
	
	
	
	
//	@RequiresPermissions("sys:sysUser:list")
//	@RequestMapping(value = {"list", ""})
//	public String list(HttpServletRequest request) {
//		List<Role> list = systemService.findAllRole();
//		request.setAttribute("roles", list);
//		return "modules/sys/sysUser/sysUserList";
//	}
	
	/**
	 * 二审人员列表页面
	 */
//	@RequiresPermissions("sys:sysUser:list")
	@RequestMapping(value = {"listTwo"})
	public String listTwo(HttpServletRequest request) {
		return "modules/sys/income/sysUserList";
	}
	
	
	/**
	 * 二审人员列表数据
	 */
	@ResponseBody
	@RequestMapping(value = "listTwoData")
	public Map<String, Object> listTwoData(SysUser sysUser, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SysUser> page = sysUserService.findPage(new Page<SysUser>(request, response), sysUser); 
		return getBootstrapData(page);
	}
	
	/**
	 * 修改审核级别
	 */
	@ResponseBody
	@RequestMapping(value = "updateAudit")
	public AjaxJson updateAudit(SysUser sysUser, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
			sysUserService.updateAudit(sysUser);
			j.setMsg("修改审核级别成功");
		} catch (Exception e) {
			j.setMsg("修改审核级别失败");
		}
		return j;
	}
	
	/**
	 * 供应商管理列表页面
	 */
	@RequiresPermissions("sys:sysUser:supplierList")
	@RequestMapping(value = {"supplierList"})
	public String supplierList(HttpServletRequest request) {
		return "modules/sys/supplier/sysUserList";
	}
	

	
	
		/**
	 * 后台账号管理列表数据
	 */
//	@ResponseBody
//	@RequiresPermissions("sys:sysUser:list")
//	@RequestMapping(value = "data")
//	public Map<String, Object> data(SysUser sysUser, HttpServletRequest request, HttpServletResponse response, Model model) {
//		Page<SysUser> page = sysUserService.findPage(new Page<SysUser>(request, response), sysUser); 
//		return getBootstrapData(page);
//	}


	/**
	 * 修改审核级别页面
	 */
	@RequestMapping(value = "sysUserForm")
	public String sysUserForm(SysUser sysUser, Model model) {
		model.addAttribute("sysUser", sysUser);
		return "modules/sys/income/sysUserForm";
	}
	
	/**
	 * 查看代理商管理表单页面
	 */
/*	@RequestMapping(value = "form2")
	public String form2(SysUser sysUser, Model model) {
		model.addAttribute("sysUser", sysUser);
		return "modules/sys/supplier/sysUserForm";
	}*/
	
	
	/**
	 * 保存后台账号管理
	 */
	@ResponseBody
	@RequestMapping(value = "saveAudit")
	public AjaxJson saveAudit(SysUser sysUser, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, sysUser)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		if (StringUtils.isNotBlank(sysUser.getNewPassword())) {
			String password = Digests.string2MD5(sysUser.getNewPassword());
			sysUser.setPassword(password);
		}else{
			sysUser.setPassword(sysUser.getPassword());
		}
		
		sysUserService.save(sysUser);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存后台账号管理成功");
		return j;
	}

	
	/**
	 * 批量删除后台账号管理
	 */
	@ResponseBody
	@RequiresPermissions("sys:sysUser:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			sysUserService.deleteByLogic(sysUserService.get(id));
		}
		j.setMsg("删除后台账号管理成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("sys:sysUser:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(SysUser sysUser, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "后台账号管理"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<SysUser> page = sysUserService.findPage(new Page<SysUser>(request, response, -1), sysUser);
    		new ExportExcel("后台账号管理", SysUser.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出后台账号管理记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("sys:sysUser:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<SysUser> list = ei.getDataList(SysUser.class);
			for (SysUser sysUser : list){
				try{
					sysUserService.save(sysUser);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条后台账号管理记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条后台账号管理记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入后台账号管理失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/sysUser/?repage";
    }
	
	/**
	 * 下载导入后台账号管理数据模板
	 */
	@RequiresPermissions("sys:sysUser:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "后台账号管理数据导入模板.xlsx";
    		List<SysUser> list = Lists.newArrayList(); 
    		new ExportExcel("后台账号管理数据", SysUser.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/sysUser/?repage";
    }

}