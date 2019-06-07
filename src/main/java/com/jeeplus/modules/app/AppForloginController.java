package com.jeeplus.modules.app;

import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.common.sms.SMSUtil;
import com.jeeplus.common.utils.EmailUtils;
import com.jeeplus.modules.sys.entity.common.ComLoginLog;
import com.jeeplus.modules.sys.entity.common.ComSmsLog;
import com.jeeplus.modules.sys.entity.member.Member;
import com.jeeplus.modules.sys.entity.member.MemberInformation;
import com.jeeplus.modules.sys.entity.member.MemberTravel;
import com.jeeplus.modules.sys.service.common.ComLoginLogService;
import com.jeeplus.modules.sys.service.common.ComSmsLogService;
import com.jeeplus.modules.sys.service.common.ComTripartiteLoginService;
import com.jeeplus.modules.sys.service.member.MemberInformationService;
import com.jeeplus.modules.sys.service.member.MemberService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;


@Controller
@RequestMapping(value = "${adminPath}/interface/forlogin")
public class AppForloginController {
	
	@Autowired
	private MemberService memberService;
	@Autowired
	private MemberInformationService memberInformationService;
	@Autowired
	private ComSmsLogService comSmsLogService;
	@Autowired
	private ComTripartiteLoginService comTripartiteLoginService;
	@Autowired
	private ComLoginLogService comLoginLogService;
	@Autowired
	private AppUtils appUtils;
	/**
	 * 登陆接口
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "login")
	@ResponseBody
	public AjaxJson login(HttpServletRequest request, HttpServletResponse response, Model model) {
		AjaxJson ajaxJson = new AjaxJson();
		String mobile = request.getParameter("phone"); // 手机号/邮箱
		String password = request.getParameter("passWord"); // 密码
		String devid = request.getParameter("devid"); // 设备号
		String loginX = request.getParameter("loginX"); // 经度
		String loginY = request.getParameter("loginY"); // 纬度
		String loginIp=request.getParameter("loginIp");//登陆IP
		if (StringUtils.isBlank(mobile) || StringUtils.isBlank(password) ) {
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_2);
			ajaxJson.setMsg(AppErrorUtils.error_2_desc);
			return ajaxJson;
		}
		try {
				Member member = new Member();
				member.setPassword(password);
				member.setMobile(mobile);
				if(memberService.ifRegister(member) == null){
					ajaxJson.setSuccess(false);
					ajaxJson.setErrorCode(AppErrorUtils.error_5);
					ajaxJson.setMsg("你的账号尚未注册");
					return ajaxJson;
				}
				Member member2  = memberService.login(member);
				if(member2 == null){
					ajaxJson.setSuccess(false);
					ajaxJson.setErrorCode(AppErrorUtils.error_5);
					ajaxJson.setMsg(AppErrorUtils.error_5_desc);
					return ajaxJson;
				}else if (member2.getType() == 2 && member2.getAudit()!=3) {
					ajaxJson.setSuccess(false);
					ajaxJson.setErrorCode(AppErrorUtils.error_55);
					ajaxJson.setMsg(AppErrorUtils.error_55_desc);
					return ajaxJson;
				}else if (member2.getStatus() == 2) {
					ajaxJson.setSuccess(false);
					ajaxJson.setErrorCode(AppErrorUtils.error_26);
					ajaxJson.setMsg(AppErrorUtils.error_26_desc);
					return ajaxJson;
				} else {
					member2.setLoginx(loginX);
					member2.setLoginy(loginY);
					member2.setLoginDeyid(devid);
					member2.setLastloginDate(new Date());
					memberService.save(member2);
					ComLoginLog comLoginLog=new ComLoginLog();
					comLoginLog.setLoginDate(new Date());
					comLoginLog.setLoginIp(loginIp);
					comLoginLog.setLoginDate(new Date());
					comLoginLog.setLoginType(Integer.valueOf(member2.getCreateType()));
					comLoginLog.setMemberid(Integer.valueOf(member2.getId()));
					comLoginLog.setLoginWay(1);//1.会员登录2.三方登陆3.游客登陆
					comLoginLog.setIsLog(1);//1.会员 2.游客'
					comLoginLogService.save(comLoginLog);//修改登录信息
					String nowTime = AppUtils.getNowTime();
					ajaxJson.setSuccess(true);
					ajaxJson.setErrorCode("0");
					ajaxJson.setMsg("登陆成功");
					String uid = member2.getFatherId() != 0 ?member2.getFatherId().toString():member2.getId();
					ajaxJson.getBody().put("member", memberService.myInfor(uid));
					ajaxJson.getBody().put("uid", uid);
					ajaxJson.getBody().put("time", nowTime);
					ajaxJson.getBody().put("key", AppUtils.getKeyByUidAndTime(uid, nowTime));
					return ajaxJson;
				}
		} catch (Exception e) {
			e.printStackTrace();
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_10);
			ajaxJson.setMsg(AppErrorUtils.error_10_desc);
			return ajaxJson;
		}
	}	
	/**
	 * 注册接口
	 */
	@RequestMapping(value = "register")
	@ResponseBody
	public AjaxJson register(HttpServletRequest request, HttpServletResponse response, Model model) {
		String mobile = request.getParameter("phone"); // 电话号码
		String password = request.getParameter("passWord"); // 密码
		String smsCode = request.getParameter("smsCode"); // 验证码
		String version =request.getParameter("version");  //1.普通用户 2.旅行社
		String category = request.getParameter("category"); // 1.注册2绑定邮箱 3忘记密码
		String area = request.getParameter("area"); // 区号
		String email = request.getParameter("email");//邮箱
		String createType = request.getParameter("createType"); // 1.手机注册 2.邮箱注册
		String source = request.getParameter("source"); // 验证码 1安卓 2苹果 3 网页
		
		AjaxJson ajaxJson = new AjaxJson();
		if (StringUtils.isBlank(password) || StringUtils.isBlank(smsCode)
		 || StringUtils.isBlank(version) 	|| StringUtils.isBlank(category)  || StringUtils.isBlank(createType)
		 ) {
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_2);
			ajaxJson.setMsg(AppErrorUtils.error_2_desc);
			return ajaxJson;
		}
		if(Integer.parseInt(createType)  == 1 && (StringUtils.isBlank(mobile) || StringUtils.isBlank(area))){
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_2);
            ajaxJson.setMsg(AppErrorUtils.error_2_desc);
            return ajaxJson;
        }
		if(Integer.parseInt(createType)  == 2 && StringUtils.isBlank(email)){
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_2);
            ajaxJson.setMsg(AppErrorUtils.error_2_desc);
            return ajaxJson;
        }
		try {
			ComSmsLog comSmsLog=new ComSmsLog();
			MemberInformation memberInformation = new MemberInformation();
			Member member = new Member();
			 //手机号注册
			if(Integer.valueOf(createType)==1){ 
			member.setCreateType(Integer.parseInt(createType));
			member.setPassword(password);
			member.setMobile(mobile);
			member.setFatherId(0);
			member.setCreateType(1);
			member.setType(Integer.parseInt(version));
			member.setCode(area);
			member.setCreateDate(new Date());
			comSmsLog.setPhone(mobile);
			comSmsLog.setType(Integer.parseInt(category));
			comSmsLog.setSource(Integer.parseInt(source));
			ComSmsLog smsLog = comSmsLogService.findCodeByMobile(comSmsLog);
			if(smsLog!=null){ 
				String code = smsLog.getCode();
				long d = new Date().getTime();// 当前时间
				long from = smsLog.getCreateDate().getTime(); // 创建时间
				long time = (d - from) / (1000 * 60 );
					if (code != null && code.equals(smsCode)) {
						if (memberService.ifRegister(member) == null) {// 未注册
							if (time <= 5) {
								memberService.register(member,memberInformation);								
								//注册环信
							/*	TalkDataService service = new TalkDataServiceImpl(new TalkHttpServiceImplApache());
								String write = JsonTool.write(service.userSave(mobile, "123456", nickname));
								System.out.println(write);*/							
								ajaxJson.setMsg("注册成功");
								ajaxJson.setSuccess(true);
								ajaxJson.setErrorCode("0");
								return ajaxJson;
							} 
							else {
								ajaxJson.setMsg("验证码已失效");
							}
						} else {
							ajaxJson.setMsg("此用户已被注册");
						}
					} 	else {
						ajaxJson.setMsg("验证码错误");
					}
				}else{
					ajaxJson.setMsg("你未发送验证码");
				}
             
			//邮箱注册
			}else if(Integer.valueOf(createType)==2){ 
				member.setPassword(password);
				member.setCreateType(Integer.parseInt(createType));
				member.setType(Integer.parseInt(version));
				member.setEmail(email);
				member.setCreateType(2);
				member.setFatherId(0);
				member.setCreateDate(new Date());
				comSmsLog.setEmail(email);
				comSmsLog.setType(Integer.parseInt(category));
				comSmsLog.setSource(Integer.parseInt(source));
				ComSmsLog smsLog = comSmsLogService.findCodeByEmail(comSmsLog);
				if(smsLog!=null){ 
					String code = smsLog.getCode();
					long d = new Date().getTime();// 当前时间
					long from = smsLog.getCreateDate().getTime(); // 创建时间
					long time = (d - from) / (1000 * 60 );
						if (code != null && code.equals(smsCode)) {
                            member.setMobile(email);
							if (memberService.ifRegister(member) == null) {// 未注册
                                member.setMobile(null);
								if (time <= 5) {
									memberService.register(member,memberInformation);								
									//注册环信
								/*	TalkDataService service = new TalkDataServiceImpl(new TalkHttpServiceImplApache());
									String write = JsonTool.write(service.userSave(mobile, "123456", nickname));
									System.out.println(write);*/							
									ajaxJson.setMsg("注册成功");
									ajaxJson.setSuccess(true);
									ajaxJson.setErrorCode("0");
									return ajaxJson;
								} 
								else {
									ajaxJson.setMsg("验证码已失效");
								}
							} else {
								ajaxJson.setMsg("此用户已被注册");
							}
						} 	else {
							ajaxJson.setMsg("验证码错误");
						}
					}else{
						ajaxJson.setMsg("你未发送验证码");
					}				
			}
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode("-1");
			return ajaxJson;
		} catch (Exception e) {
			e.printStackTrace();
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_10);
			ajaxJson.setMsg(AppErrorUtils.error_10_desc);
			return ajaxJson;
		}
	}
	/**
	 * 修改密码接口
	 */
	@RequestMapping(value = "updatePassword")
	@ResponseBody   
	public AjaxJson updatePassword(HttpServletRequest request, HttpServletResponse response, Model model) {
		String mobile = request.getParameter("mobile"); // 电话号码-邮箱
		String newPassword = request.getParameter("newPassword"); // 新密🐎
		String smsCode = request.getParameter("smsCode"); // 验证码
	//	String version =request.getParameter("version");  //1.普通用户 2.旅行社
		String category = request.getParameter("category"); // // 1.注册2绑定邮箱 3忘记密码

//        String area = request.getParameter("area"); // 区号
//		String email=request.getParameter("email");//邮箱
//		String  registerType=request.getParameter("registerType"); //1 手机号  2.邮箱

		String source = request.getParameter("source"); // 1安卓 2苹果 3 网页
		
		AjaxJson ajaxJson = new AjaxJson();
		if (StringUtils.isBlank(mobile) || StringUtils.isBlank(newPassword) || StringUtils.isBlank(smsCode)
		|| StringUtils.isBlank(category)) {
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_2);
			ajaxJson.setMsg(AppErrorUtils.error_2_desc);
			return ajaxJson;
		}
        Member member1 = new Member();
        member1.setMobile(mobile);
        member1 = memberService.ifRegister(member1);
        if(member1 == null){
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode("-1");
            ajaxJson.setMsg("此账号暂未注册");
            return ajaxJson;
        }
        mobile = member1.getMobile();
        String email = member1.getEmail();
		try {
			ComSmsLog comSmsLog=new ComSmsLog();
			
			Member member = new Member();
            member.setId(member1.getId());
			member.setPassword(newPassword);
			
			ComSmsLog smsLog;
			comSmsLog.setType(Integer.parseInt(category));
			comSmsLog.setSource(Integer.parseInt(source));
			if(member1.getCreateType()==1){
				comSmsLog.setPhone(mobile);
				smsLog = comSmsLogService.findCodeByMobile(comSmsLog);
			}else{
				comSmsLog.setEmail(email);
				smsLog = comSmsLogService.findCodeByEmail(comSmsLog);
			}
			if(smsLog!=null){ 
				String code = smsLog.getCode();
				long d = new Date().getTime();// 当前时间
				long from = smsLog.getCreateDate().getTime(); // 创建时间
				long time = (d - from) / (1000 * 60 );
					if (code != null && code.equals(smsCode)) {
							if (time <= 5) {
								memberService.updatePassword(member);
								ajaxJson.setMsg("修改密码成功");
								ajaxJson.setSuccess(true);
								ajaxJson.setErrorCode("0");
								return ajaxJson;
							} 
							else {
								ajaxJson.setMsg("验证码已失效");
							}

					} 	else {
						ajaxJson.setMsg("验证码错误");
					}
				}else{
					ajaxJson.setMsg("你未发送验证码");
				}
             
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode("-1");
			return ajaxJson;
		} catch (Exception e) {
			e.printStackTrace();
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_10);
			ajaxJson.setMsg(AppErrorUtils.error_10_desc);
			return ajaxJson;
		}
	}

	/**
	 *  商家注册
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "businessRegister")
	@ResponseBody
	public AjaxJson businessRegister(HttpServletRequest request, HttpServletResponse response, Model model) {
		String accountNumbe = request.getParameter("accountNumbe"); // 登录账号
		String password = request.getParameter("password"); // 密码
		String passwordCheck = request.getParameter("passwordCheck"); // 确认密码
		String smsCode = request.getParameter("smsCode"); // 验证码
//		String version =request.getParameter("version");  //1.普通用户 2.旅行社
//		String category = request.getParameter("category"); // 1.注册验证码 2.忘记密码 3.验证原手机 4.绑定新手机 5.新帐号绑定手机 6.已有帐号绑定手机',
//		String area = request.getParameter("area"); // 区号
//		String email = request.getParameter("email");//邮箱
		String registerType = request.getParameter("registerType"); // 1.手机注册 2.邮箱注册
		String source = request.getParameter("source"); // 验证码 1安卓 2苹果 3 网页
		String companyType = request.getParameter("companyType");//公司类型 1.个人 2 公司
		String cardsImg = request.getParameter("cardsImg");//身份证照片
		String   content = request.getParameter("content");//公司简介或者是个人详情

		String nickName = request.getParameter("nickName");//昵称
		String  realName = request.getParameter("realName");//真实姓名
		String  photo = request.getParameter("photo");//个人照片


		String  companyName = request.getParameter("companyName");//公司名称
		String  legalPerson = request.getParameter("legalPerson");//法人代表
		String   companyLogo = request.getParameter("companyLogo");//公司Logo
		String  companyImg = request.getParameter("companyImg");//公司营业执照

		AjaxJson ajaxJson = new AjaxJson();
		if (StringUtils.isBlank(accountNumbe) || StringUtils.isBlank(password) || StringUtils.isBlank(smsCode)
		 || StringUtils.isBlank(registerType)  || StringUtils.isBlank(cardsImg) || StringUtils.isBlank(content)
		 || StringUtils.isBlank(companyType)) {
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_2);
			ajaxJson.setMsg(AppErrorUtils.error_2_desc);
			return ajaxJson;
		}
		if(Integer.parseInt(companyType) == 1){
			if(StringUtils.isBlank(nickName) || StringUtils.isBlank(realName) || StringUtils.isBlank(photo) ){
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_2);
				ajaxJson.setMsg(AppErrorUtils.error_2_desc);
				return ajaxJson;
			}
		}
		if(Integer.parseInt(companyType) == 2){
			if(StringUtils.isBlank(companyName) || StringUtils.isBlank(legalPerson)
					|| StringUtils.isBlank(companyLogo)  || StringUtils.isBlank(companyImg)){
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_2);
				ajaxJson.setMsg(AppErrorUtils.error_2_desc);
				return ajaxJson;
			}
		}
		try {
			ComSmsLog comSmsLog=new ComSmsLog();
			MemberInformation memberInformation = new MemberInformation();
			Member member = new Member();
			MemberTravel  memberTravel=new MemberTravel();
			if(Integer.parseInt(companyType)==1){//个人注册
				memberTravel.setCompanyName(nickName);//昵称
				memberTravel.setLegalPerson(realName);  //姓名
				memberTravel.setCompanyLogo(photo);//头像
			}else{
				memberTravel.setCompanyName(companyName);//公司名称
				memberTravel.setLegalPerson(legalPerson);  //法人代表
				memberTravel.setCompanyLogo(companyLogo);//公司LOGO
			}
			memberTravel.setCardsImg(cardsImg);
			memberTravel.setCompanyType(Integer.parseInt(companyType));
			memberTravel.setCompanyContent(content);
			memberTravel.setCompanyImg(companyImg);//营业执照
			 //手机号注册
			if(Integer.valueOf(registerType)==1){ 
			member.setPassword(password);
			member.setMobile(accountNumbe);
			member.setCreateType(1);//注册类型 1.手机号  2.邮箱
			member.setType(2);
			member.setStatus(1);
			member.setAudit(2);
			member.setFatherId(0);
			member.setCreateDate(new Date());
//			member.setCode("86");
			comSmsLog.setPhone(accountNumbe);
			comSmsLog.setType(1);
			comSmsLog.setSource(Integer.parseInt(source));
			ComSmsLog smsLog = comSmsLogService.findCodeByMobile(comSmsLog);
			if(smsLog!=null){ 
				String code = smsLog.getCode();
				long d = new Date().getTime();// 当前时间
				long from = smsLog.getCreateDate().getTime(); // 创建时间
				long time = (d - from) / (1000 * 60 );
					if (code != null && code.equals(smsCode)) {
						if (memberService.ifRegister(member) == null) {// 未注册
							if (time <= 5) {
						           if(Integer.valueOf(companyType)==1){
						       memberService.BusinessRegister(member,memberInformation,memberTravel);														        	   
						        	   ajaxJson.setMsg("注册成功");
						        	   ajaxJson.setSuccess(true);
						        	   ajaxJson.setErrorCode("0");
						        	   return ajaxJson;
						           }else if(Integer.valueOf(companyType)==2){

						      memberService.BusinessRegister2(member,memberTravel);	
						        	   ajaxJson.setMsg("注册成功");
						        	   ajaxJson.setSuccess(true);
						        	   ajaxJson.setErrorCode("0");
						        	   return ajaxJson;
						           }
							} 
							else {
								ajaxJson.setMsg("验证码已失效");
							}
						} else {
							ajaxJson.setMsg("此用户已被注册");
						}
					} 	else {
						ajaxJson.setMsg("验证码错误");
					}
				}else{
					ajaxJson.setMsg("你未发送验证码");
				}
             
			//邮箱注册
			}else if(Integer.valueOf(registerType)==2){ 
				member.setPassword(password);
				member.setType(2);
				member.setEmail(accountNumbe);
				member.setCreateType(2);

				member.setStatus(1);
				member.setAudit(2);
				member.setCreateDate(new Date());
				member.setFatherId(0);
				comSmsLog.setEmail(accountNumbe);
				comSmsLog.setType(1);
				comSmsLog.setSource(Integer.parseInt(source));
				ComSmsLog smsLog = comSmsLogService.findCodeByEmail(comSmsLog);
				if(smsLog!=null){ 
					String code = smsLog.getCode();
					long d = new Date().getTime();// 当前时间
					long from = smsLog.getCreateDate().getTime(); // 创建时间
					long time = (d - from) / (1000 * 60 );
						if (code != null && code.equals(smsCode)) {
							if (memberService.ifRegister(member) == null) {// 未注册
								if (time <= 5) {
							           if(Integer.valueOf(companyType)==1){
									       memberService.BusinessRegister(member,memberInformation,memberTravel);														        	   
									        	   ajaxJson.setMsg("注册成功");
									        	   ajaxJson.setSuccess(true);
									        	   ajaxJson.setErrorCode("0");
									        	   return ajaxJson;
									           }else if(Integer.valueOf(companyType)==2){
									      memberService.BusinessRegister2(member,memberTravel);	
									        	   ajaxJson.setMsg("注册成功");
									        	   ajaxJson.setSuccess(true);
									        	   ajaxJson.setErrorCode("0");
									        	   return ajaxJson;
									           }
								} 
								else {
									ajaxJson.setMsg("验证码已失效");
								}
							} else {
								ajaxJson.setMsg("此用户已被注册");
							}
						} 	else {
							ajaxJson.setMsg("验证码错误");
						}
					}else{
						ajaxJson.setMsg("你未发送验证码");
					}				
			}
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode("-1");
			return ajaxJson;
		} catch (Exception e) {
			e.printStackTrace();
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_10);
			ajaxJson.setMsg(AppErrorUtils.error_10_desc);
			return ajaxJson;
		}
	}

	
	/**
	 * 发送验证码接口
	 */
	@RequestMapping(value = "sendSms")
	@ResponseBody
	public AjaxJson sendSms(HttpServletRequest request, HttpServletResponse response, Model model) {
		AjaxJson ajaxJson = new AjaxJson();
		String mobile = request.getParameter("mobile"); // 手机号
		String category = request.getParameter("category"); //1.注册2绑定邮箱 3忘记密码
		String sendType =request.getParameter("sendType"); // 1.手机发送验证码 2.邮箱发送验证码
		String email = request.getParameter("email"); // 邮箱
		String area = request.getParameter("area"); // 区号
		String source = request.getParameter("source"); //1.网页端2.手机端
		
		if (StringUtils.isBlank(category) || StringUtils.isBlank(source)) {
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_2);
			ajaxJson.setMsg(AppErrorUtils.error_2_desc);
			return ajaxJson;
		}

		if(Integer.parseInt(category) != 3) {
		    if(StringUtils.isBlank(sendType)){
                ajaxJson.setSuccess(false);
                ajaxJson.setErrorCode(AppErrorUtils.error_2);
                ajaxJson.setMsg(AppErrorUtils.error_2_desc);
                return ajaxJson;
            }
            if (Integer.parseInt(sendType) == 1) {
                if (StringUtils.isBlank(mobile)) {
                    ajaxJson.setSuccess(false);
                    ajaxJson.setErrorCode(AppErrorUtils.error_2);
                    ajaxJson.setMsg(AppErrorUtils.error_2_desc);
                    return ajaxJson;
                }
            }

            if (Integer.parseInt(sendType) == 2 && StringUtils.isBlank(email)) {
                ajaxJson.setSuccess(false);
                ajaxJson.setErrorCode(AppErrorUtils.error_2);
                ajaxJson.setMsg(AppErrorUtils.error_2_desc);
                return ajaxJson;
            }
        }else{
            if (StringUtils.isBlank(mobile)) {
                ajaxJson.setSuccess(false);
                ajaxJson.setErrorCode(AppErrorUtils.error_2);
                ajaxJson.setMsg(AppErrorUtils.error_2_desc);
                return ajaxJson;
            }
            Member member = new Member();
            member.setMobile(mobile);
            member = memberService.ifRegister(member);
            if(member == null){
                ajaxJson.setSuccess(false);
                ajaxJson.setErrorCode("-1");
                ajaxJson.setMsg("此账号暂未注册");
                return ajaxJson;
            }
            mobile = member.getMobile();
            email = member.getEmail();
            area = member.getCode();
            sendType = member.getCreateType().toString();
        }
		try {
			ComSmsLog smsLog = new ComSmsLog();
		//	Integer verificationCode = Integer.valueOf(AppUtils.createVerificationCode());// 生成六位数验证码
			Integer verificationCode = 123456;// 生成六位数验证码
			smsLog.setCode(verificationCode.toString()); // 设置验证码
			smsLog.setType(Integer.valueOf(category));
			String message="";
			switch (Integer.valueOf(category)) {
			case 1: // 注册验证码
				smsLog.setContent("欢迎使用，您的验证码为:" + verificationCode+"，请妥善保管");
				message="欢迎使用，您的验证码为:" + verificationCode+"，请妥善保管";
				break;
			case 2: // 绑定邮箱
				smsLog.setContent("欢迎使用，您的验证码为:" + verificationCode+"，请妥善保管");
				message="欢迎使用，您的验证码为:" + verificationCode+"，请妥善保管";
				break;
			case 3: // 忘记密码
				smsLog.setContent("欢迎使用，您的验证码为:" + verificationCode+"，请妥善保管");
				message="欢迎使用，您的验证码为:" + verificationCode+"，请妥善保管";
				break;
			default:
				break;
			}
			smsLog.setSource(Integer.valueOf(source));
			smsLog.setPhone(mobile);
			smsLog.setArea(area);
			smsLog.setEmail(email);
			smsLog.setSendType(Integer.parseInt(sendType));
			comSmsLogService.save(smsLog);
			if(Integer.valueOf(sendType)==1){
				try {
					String phone="+"+area+mobile;
					String result =SMSUtil.send(phone, message);
				//	SMSEntity rs = (SMSEntity)JsonUtils.getInstanceByJson(SMSEntity.class, result);
//					if (!"0".equals(rs.getResult())) {
//						ajaxJson.setSuccess(false);
//						ajaxJson.setErrorCode("2");
//						ajaxJson.setMsg("短信发送失败，错误代码"+rs.getResult()+"，请联系管理员。");
//						return ajaxJson;
//					}else{
						ajaxJson.setSuccess(true);
						ajaxJson.setErrorCode("-1");
						ajaxJson.setMsg("短信发送成功!"+result);
						request.getSession().getServletContext().setAttribute(phone, message);
						return ajaxJson;
				//	}
				} catch (IOException e) {
					ajaxJson.setSuccess(false);
					ajaxJson.setErrorCode("3");
					ajaxJson.setMsg("因未知原因导致短信发送失败，请联系管理员。");
					return ajaxJson;
				}
			}else{
				try {
					Integer res = EmailUtils.sendEmail(email, "你有一个新的验证码，请查看", message);
					if ("1".equals(res)) {
						ajaxJson.setSuccess(false);
						ajaxJson.setErrorCode("-1");
						ajaxJson.setMsg("邮箱发送失败，错误代码"+res+"，请联系管理员。");
						return ajaxJson;
					}else{
						ajaxJson.setSuccess(true);
						ajaxJson.setErrorCode("0");
						ajaxJson.setMsg("邮箱发送成功!"+res);
						request.getSession().getServletContext().setAttribute(email, message);
						return ajaxJson;
					}
				} catch (Exception e) {
					ajaxJson.setSuccess(false);
					ajaxJson.setErrorCode("-1");
					ajaxJson.setMsg("因未知原因导致邮箱发送失败，请联系管理员。");
					return ajaxJson;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_10);
			ajaxJson.setMsg(AppErrorUtils.error_10_desc);
			return ajaxJson;
		}
	}
	
	
	/**
	 * 验证手机号码是否注册
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "checkRegister")
	@ResponseBody
	public AjaxJson ifMobile(HttpServletRequest request, HttpServletResponse response, Model model) {
		String mobile = request.getParameter("mobile"); // 登陆名
		AjaxJson ajaxJson = new AjaxJson();
		if (StringUtils.isBlank(mobile)) {
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_2);
			ajaxJson.setMsg(AppErrorUtils.error_2_desc);
			return ajaxJson;
		}
        try {
            Member member = new Member();
            member.setMobile(mobile);
            ajaxJson.getBody().put("ifRegister",memberService.ifRegister(member));
            ajaxJson.setSuccess(true);
            ajaxJson.setErrorCode("0");
            ajaxJson.setMsg("");
            return ajaxJson;
        } catch (Exception e) {
            e.printStackTrace();
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_10);
            ajaxJson.setMsg(AppErrorUtils.error_10_desc);
            return ajaxJson;
        }

	}


//
//	
//	
//	/**
//	 * 手机号码找回密码
//	 * @param request
//	 * @param response
//	 * @param model
//	 * @return
//	 */
//	@RequestMapping(value = "passFind")
//	@ResponseBody
//	public AjaxJson mobileFind(HttpServletRequest request, HttpServletResponse response, Model model) {
//		String mobile = request.getParameter("mobile"); // 登陆名
//		String findType = request.getParameter("findType"); // 1.电话，2.邮箱
//		String password = request.getParameter("password"); // 新的密码
//		String area = request.getParameter("area"); // 登陆名
//		String smsCode = request.getParameter("smsCode"); // 
//		String email = request.getParameter("email"); // 邮箱
//		AjaxJson ajaxJson = new AjaxJson();
//		if (StringUtils.isBlank(password) || StringUtils.isBlank(smsCode) || StringUtils.isBlank(findType)) {
//			ajaxJson.setSuccess(false);
//			ajaxJson.setErrorCode(AppErrorUtils.error_2);
//			ajaxJson.setMsg(AppErrorUtils.error_2_desc);
//			return ajaxJson;
//		}else{
//				try {
//					Member member = new Member();
//					ComSmsLog comSmsLog=new ComSmsLog();
//					if(Integer.valueOf(findType)==1){
//						comSmsLog.setIsWay(1);
//						comSmsLog.setPhone(mobile);
//						member.setMobile(mobile);
//						member.setType(1);
//					}else{
//						comSmsLog.setIsWay(2);
//						comSmsLog.setEmail(email);
//						member.setType(2);
//						member.setEmail(email);
//					}
//					comSmsLog.setArea(area);
//					comSmsLog.setType(2);//找回密码
//					member.setAre(area);
//					member.setMemberPassword(password);
//					ComSmsLog smsLog = comSmsLogService.findCodeByMobile(comSmsLog);
//					String code = smsLog.getCode();
//					long d = new Date().getTime();// 当前时间
//					long from = smsLog.getCreateDate().getTime(); // 创建时间
//					long time = (d - from) / (1000 * 60 * 60);
//					if (time <= 1) {
//						if (code != null && code.equals(smsCode)) {
//							Boolean ifRegister = memberService.IfRegister(member);// 判断是否注册
//							if (ifRegister == false) {// 未注册
//								ajaxJson.setErrorCode("-1");
//								ajaxJson.setMsg("此账号未注册");
//								ajaxJson.setSuccess(false);
//								return ajaxJson;
//							} else {//若注册
//								memberService.updateMemberPasswordByMember(member);//修改密码
//								ajaxJson.setSuccess(true);
//							}
//						} else {
//							ajaxJson.getBody().put("message", "验证码错误");
//							ajaxJson.setSuccess(false);
//							ajaxJson.setErrorCode("-1");
//							return ajaxJson;
//						}
//					} else {
//						ajaxJson.getBody().put("message", "验证码已失效");
//						ajaxJson.setSuccess(false);
//						ajaxJson.setErrorCode("-1");
//						return ajaxJson;
//					}
//					ajaxJson.setErrorCode("0");
//					ajaxJson.setMsg("修改密码成功");
//					return ajaxJson;
//				} catch (Exception e) {
//					e.printStackTrace();
//					ajaxJson.setSuccess(false);
//					ajaxJson.setErrorCode(AppErrorUtils.error_10);
//					ajaxJson.setMsg(AppErrorUtils.error_10_desc);
//					return ajaxJson;
//				}
//		}
//	}
//	
//	/**
//	 * 三方登录接口
//	 */
//	@RequestMapping(value = "threeLogin")
//	@ResponseBody
//	public AjaxJson threeLogin(HttpServletRequest request, HttpServletResponse response, Model model) {
//		String type = request.getParameter("loginType"); // 三方登录类型
//		String typeId = request.getParameter("typeId"); // 开放api
//		String loginPhoto = request.getParameter("loginPhoto"); // 头像
//		String loginName = request.getParameter("loginName"); // 姓名
//		String loginIp = request.getParameter("loginIp"); // 登陆IP
//		String loginX = request.getParameter("loginX"); // 登陆X位置
//		String loginY = request.getParameter("loginY"); // 登陆Y位置
//		String createType = request.getParameter("createType"); // 登陆类型用于注册
//		AjaxJson ajaxJson = new AjaxJson();
//		if (type == null || type.equals("")) {
//			ajaxJson.setSuccess(false);
//			ajaxJson.setErrorCode(AppErrorUtils.error_2);
//			ajaxJson.setMsg(AppErrorUtils.error_2_desc);
//			return ajaxJson;
//		}
//		if (loginPhoto == null || loginPhoto.equals("") || loginName == null || loginName.equals("")) {
//			ajaxJson.setSuccess(false);
//			ajaxJson.setErrorCode(AppErrorUtils.error_2);
//			ajaxJson.setMsg(AppErrorUtils.error_2_desc);
//			return ajaxJson;
//		}
//		if (loginIp == null || loginIp.equals("")) {
//			ajaxJson.setSuccess(false);
//			ajaxJson.setErrorCode(AppErrorUtils.error_2);
//			ajaxJson.setMsg(AppErrorUtils.error_2_desc);
//			return ajaxJson;
//		}
//		if (typeId == null || typeId.equals("")) {
//			ajaxJson.setSuccess(false);
//			ajaxJson.setErrorCode(AppErrorUtils.error_2);
//			ajaxJson.setMsg(AppErrorUtils.error_2_desc);
//			return ajaxJson;
//		}
//		if (createType == null || createType.equals("")) {
//			ajaxJson.setSuccess(false);
//			ajaxJson.setErrorCode(AppErrorUtils.error_2);
//			ajaxJson.setMsg(AppErrorUtils.error_2_desc);
//			return ajaxJson;
//		}
//		try {
//			ComTripartiteLogin comTripartiteLogin = new ComTripartiteLogin();
//			comTripartiteLogin.setType(type);
//			comTripartiteLogin.setTypeId(typeId);
//			comTripartiteLogin.setLoginDate(new Date());
//			comTripartiteLogin.setLoginIp(loginIp);
//			ComTripartiteLogin cLogin = comTripartiteLoginService.findAPI(comTripartiteLogin);
//			String nowTime = AppUtils.getNowTime();
//			if(cLogin!=null&&!cLogin.equals("")){
//				Member member = new Member();
//				if (loginX == null || loginX.equals("") || loginY == null || loginY.equals("")) {
//					member.setLastloginIp(loginIp);
//					member.setMemberid(cLogin.getMemberid());
//				}else{
//					member.setLoginX(loginX);
//					member.setLoginY(loginY);
//					member.setLastloginIp(loginIp);
//					member.setMemberid(cLogin.getMemberid());
//				}
//				ComLoginLog comLoginLog=new ComLoginLog();
//				comLoginLog.setLoginIp(loginIp);
//				comLoginLog.setLoginDate(new Date());
//				comLoginLog.setLoginType(Integer.valueOf(createType));
//				comLoginLog.setMemberid(cLogin.getMemberid());
//				comLoginLog.setLoginWay(2);//1.会员登录2.三方登陆3.游客登陆',
//				comLoginLog.setMembername(member.getNickname());
//				memberService.updateLogin(member,comLoginLog);
//				if(memberService.get(String.valueOf(cLogin.getMemberid())).getStatus()==2){
//					ajaxJson.setSuccess(false);
//					ajaxJson.setErrorCode(AppErrorUtils.error_26);
//					ajaxJson.setMsg(AppErrorUtils.error_26_desc);
//					return ajaxJson;
//				}
//				if(cLogin.getIsBang()==1){
//					ajaxJson.getBody().put("type", 1);
//				}else{
//					ajaxJson.getBody().put("type", 2);
//				}
//				ajaxJson.setSuccess(true);
//				ajaxJson.setErrorCode("0");
//				ajaxJson.setMsg("");
//				ajaxJson.getBody().put("uid", cLogin.getMemberid());
//				ajaxJson.getBody().put("time", nowTime);
//				ajaxJson.getBody().put("key",
//						AppUtils.getKeyByUidAndTime(String.valueOf(cLogin.getMemberid()), nowTime));
//				
//			}else {
//				Member member = new Member();
//				member.setCreateDate(new Date());
//				member.setCreateType(Integer.valueOf(createType));
//				member.setAudit(2);
//				if (loginX == null || loginX.equals("") || loginY == null || loginY.equals("")) {
//					member.setLastloginIp(loginIp);
//				}else{
//					member.setLoginX(loginX);
//					member.setLoginY(loginY);
//					member.setLastloginIp(loginIp);
//				}
//				if(type.equals("1")){
//					member.setWeixinId(typeId);
//				}else if(type.equals("2")){
//					member.setQqId(typeId);
//				}else if(type.equals("3")){
//					member.setWeiboId(typeId);
//				}else if(type.equals("4")){
//					member.setFacebookId(typeId);
//				}
//				ComLoginLog comLoginLog=new ComLoginLog();
//				comLoginLog.setLoginIp(loginIp);
//				comLoginLog.setLoginDate(new Date());
//				comLoginLog.setLoginType(Integer.valueOf(createType));
//				comLoginLog.setLoginWay(2);//1.会员登录2.三方登陆3.游客登陆',
//				comLoginLog.setIsLog(2);//1.会员登录2.三方登陆3.游客登陆',
//				comLoginLog.setMembername(loginName);
//				MemberInformation memberInformation = new MemberInformation();
//				memberInformation.setNickname(loginName);
//				memberInformation.setImg(loginPhoto);
//				comTripartiteLogin.setIsBang(2);
//				memberService.threeLogin(member,memberInformation,comTripartiteLogin,comLoginLog);
//				ajaxJson.getBody().put("type", 2);
//				ajaxJson.setErrorCode("0");
//				ajaxJson.setMsg("保存成功");
//				ajaxJson.getBody().put("uid", member.getId());
//				ajaxJson.getBody().put("time", nowTime);
//				ajaxJson.getBody().put("key",
//						AppUtils.getKeyByUidAndTime(String.valueOf(member.getId()), nowTime));
//			}
//			return ajaxJson;
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			ajaxJson.setSuccess(false);
//			ajaxJson.setErrorCode(AppErrorUtils.error_10);
//			ajaxJson.setMsg(AppErrorUtils.error_10_desc);
//			return ajaxJson;
//		}
//	}
//	
//	/**
//	 * 三方登录绑定手机
//	 */
//	@RequestMapping(value = "bindNew")
//	@ResponseBody
//	public AjaxJson verMobile(HttpServletRequest request, HttpServletResponse response, Model model) {
//		AjaxJson ajaxJson = new AjaxJson();
//		String type = request.getParameter("type");      //1.游客 2.会员
//		String uid = request.getParameter("uid");      //会员ID，登陆后返回
//		String time = request.getParameter("time");		//时间，登陆后返回
//		String key = request.getParameter("key");       //密钥，登陆后返回
//		String mobile = request.getParameter("mobile");
//		String smsCode = request.getParameter("smsCode");
//		String area = request.getParameter("area");
//		String password = request.getParameter("password");
//		if (StringUtils.isBlank(type)) {
//			ajaxJson.setSuccess(false);
//			ajaxJson.setErrorCode(AppErrorUtils.error_2);
//			ajaxJson.setMsg("type传参为空");
//			return ajaxJson;
//		}
//		if (StringUtils.isBlank(uid)) {
//			ajaxJson.setSuccess(false);
//			ajaxJson.setErrorCode(AppErrorUtils.error_2);
//			ajaxJson.setMsg("uid传参为空");
//			return ajaxJson;
//		}
//		if (StringUtils.isBlank(time)) {
//			ajaxJson.setSuccess(false);
//			ajaxJson.setErrorCode(AppErrorUtils.error_2);
//			ajaxJson.setMsg("time传参为空");
//			return ajaxJson;
//		}
//		if (StringUtils.isBlank(key)) {
//			ajaxJson.setSuccess(false);
//			ajaxJson.setErrorCode(AppErrorUtils.error_2);
//			ajaxJson.setMsg("key传参为空");
//			return ajaxJson;
//		}
//		if (password == null || password.equals("")) {
//			ajaxJson.setSuccess(false);
//			ajaxJson.setErrorCode("-1");
//			ajaxJson.setMsg("密码为空");
//			return ajaxJson;
//		}
//		if (mobile == null || mobile.equals("")) {
//			ajaxJson.setSuccess(false);
//			ajaxJson.setErrorCode("-1");
//			ajaxJson.setMsg("手机号为空");
//			return ajaxJson;
//		}
//		if (smsCode == null || smsCode.equals("")) {
//			ajaxJson.setSuccess(false);
//			ajaxJson.setErrorCode("-1");
//			ajaxJson.setMsg("验证码为空");
//			return ajaxJson;
//		}
//		try {
//			Member member = new Member();
//			member.setId(uid);
//			if(area==null||area.equals("")){
//				member.setAre(null);
//			}else{
//				member.setAre(area);
//			}
//			member.setMobile(mobile);
//			member.setMemberPassword(password);
//			ComSmsLog comSmsLog = new ComSmsLog();
//			comSmsLog.setIsWay(1);
//			comSmsLog.setArea(area);
//			comSmsLog.setPhone(mobile);
//			comSmsLog.setType(5);
//			ComSmsLog smsLog = comSmsLogService.findCodeByMobile(comSmsLog);
//			String code = smsLog.getCode();
//			long d = new Date().getTime();					//当前时间
//			long from = smsLog.getCreateDate().getTime(); 	//创建时间
//			long time2 = (d - from) / (1000 * 60 * 60);
//			if (time2 <= 1) {
//				if (code != null && code.equals(smsCode)) {
//					memberService.updateNewMobile(member);
//					ComTripartiteLogin comTripartiteLogin = new ComTripartiteLogin();
//					comTripartiteLogin.setMemberid(Integer.valueOf(uid));
//					List<ComTripartiteLogin> cList = comTripartiteLoginService.findByMember(comTripartiteLogin);
//					for(ComTripartiteLogin cLogin:cList){
//						comTripartiteLoginService.updateBang(cLogin);
//					}
//				} else {
//					ajaxJson.setSuccess(false);
//					ajaxJson.setErrorCode(AppErrorUtils.error_28);
//					ajaxJson.setMsg(AppErrorUtils.error_28_desc);
//					return ajaxJson;
//				}
//			} else {
//				ajaxJson.setSuccess(false);
//				ajaxJson.setErrorCode(AppErrorUtils.error_29);
//				ajaxJson.setMsg(AppErrorUtils.error_29_desc);
//				return ajaxJson;
//			}
//			ajaxJson.setSuccess(true);
//			ajaxJson.setErrorCode("0");
//			ajaxJson.setMsg("注册成功");
//			return ajaxJson;
//		} catch (Exception e) {
//			e.printStackTrace();
//			ajaxJson.setSuccess(false);
//			ajaxJson.setErrorCode(AppErrorUtils.error_3);
//			ajaxJson.setMsg(AppErrorUtils.error_3_desc);
//			return ajaxJson;
//		}
	/**
	 * 邮箱验证
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "checkEmail")
	@ResponseBody
	public AjaxJson checkEmail(HttpServletRequest request, HttpServletResponse response, Model model) {
		AjaxJson ajaxJson = new AjaxJson();
		String type = request.getParameter("type"); //1.普通会员  2.旅行社
		String uid = request.getParameter("uid");      //会员ID，登陆后返回
		String time = request.getParameter("time");		//时间，登陆后返回
		String key = request.getParameter("key");       //密钥，登陆后返回
		String code = request.getParameter("code"); //验证码
		String email = request.getParameter("email");//邮箱
		if (StringUtils.isBlank(uid)) {
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_2);
			ajaxJson.setMsg("uid传参为空");
			return ajaxJson;
		}
		if (StringUtils.isBlank(time)) {
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_2);
			ajaxJson.setMsg("time传参为空");
			return ajaxJson;
		}
		if (StringUtils.isBlank(key)) {
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_2);
			ajaxJson.setMsg("key传参为空");
			return ajaxJson;
		}
		Integer memberStatus = appUtils.keyIsTrue(uid, time, key);
		if (memberStatus != 0) {
			ajaxJson.getBody().put("memberStatus", memberStatus);
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_7);
			ajaxJson.setMsg(AppErrorUtils.error_7_desc);
			return ajaxJson;
		}
		if (StringUtils.isBlank(email)) {
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_2);
			ajaxJson.setMsg(AppErrorUtils.error_2_desc);
			return ajaxJson;
		}
		if (StringUtils.isBlank(code)) {
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_2);
			ajaxJson.setMsg(AppErrorUtils.error_2_desc);
			return ajaxJson;
		}
		try {
			ComSmsLog comSmsLog=new ComSmsLog();
			comSmsLog.setEmail(email);
			comSmsLog.setCode(code);
			
			ComSmsLog smsLog = comSmsLogService.checkEmail(comSmsLog);
			if(smsLog!=null){ 
				long d = new Date().getTime();// 当前时间
				long from = smsLog.getCreateDate().getTime(); // 创建时间
				long time1 = (d - from) / (1000 * 60 );
				if (time1 <= 5) {
					Member meber = memberService.get(uid);
					meber.setEmail(email);
					memberService.save(meber);
				   ajaxJson.setMsg("验证成功");
	        	   ajaxJson.setSuccess(true);
	        	   ajaxJson.setErrorCode("0");
	        	   return ajaxJson;
				}else {
					ajaxJson.setMsg("验证码已失效");
				}
			}else{
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode("-1");
				ajaxJson.setMsg("验证码错误");
			}
			return ajaxJson;
		} catch (Exception e) {
			e.printStackTrace();
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_10);
			ajaxJson.setMsg(AppErrorUtils.error_10_desc);
			return ajaxJson;
		}
	}	
}




