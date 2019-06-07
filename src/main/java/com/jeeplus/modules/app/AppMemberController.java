package com.jeeplus.modules.app;

import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.modules.meiguotong.entity.collection.ProductCollection;
import com.jeeplus.modules.meiguotong.entity.comarticle.ComArticle;
import com.jeeplus.modules.meiguotong.entity.comcomment.ComComment;
import com.jeeplus.modules.meiguotong.entity.comprotocol.ComProtocol;
import com.jeeplus.modules.meiguotong.entity.compush.ComPushPeople;
import com.jeeplus.modules.meiguotong.entity.comtag.ComTag;
import com.jeeplus.modules.meiguotong.entity.conutry.Country;
import com.jeeplus.modules.meiguotong.entity.draft.ProductDraft;
import com.jeeplus.modules.meiguotong.entity.guide.Guide;
import com.jeeplus.modules.meiguotong.entity.insurance.OrderInsurance;
import com.jeeplus.modules.meiguotong.entity.order.*;
import com.jeeplus.modules.meiguotong.entity.ordermember.OrderMember;
import com.jeeplus.modules.meiguotong.service.collection.ProductCollectionService;
import com.jeeplus.modules.meiguotong.service.comarticle.ComArticleService;
import com.jeeplus.modules.meiguotong.service.comcomment.ComCommentService;
import com.jeeplus.modules.meiguotong.service.comprotocol.ComProtocolService;
import com.jeeplus.modules.meiguotong.service.compush.ComPushPeopleService;
import com.jeeplus.modules.meiguotong.service.comtag.ComTagService;
import com.jeeplus.modules.meiguotong.service.conutry.CountryService;
import com.jeeplus.modules.meiguotong.service.draft.ProductDraftService;
import com.jeeplus.modules.meiguotong.service.guide.GuideService;
import com.jeeplus.modules.meiguotong.service.insurance.OrderInsuranceService;
import com.jeeplus.modules.meiguotong.service.order.OrderRouteService;
import com.jeeplus.modules.meiguotong.service.order.OrderSysService;
import com.jeeplus.modules.meiguotong.service.ordermember.OrderMemberService;
import com.jeeplus.modules.sys.entity.member.Member;
import com.jeeplus.modules.sys.entity.member.MemberContacts;
import com.jeeplus.modules.sys.entity.member.MemberDiscount;
import com.jeeplus.modules.sys.entity.member.MemberInformation;
import com.jeeplus.modules.sys.entity.member.MemberTravel;
import com.jeeplus.modules.sys.entity.memberincome.MemberIncome;
import com.jeeplus.modules.sys.entity.score.SorceLog;
import com.jeeplus.modules.sys.mapper.member.MemberTravelMapper;
import com.jeeplus.modules.sys.service.member.MemberContactsService;
import com.jeeplus.modules.sys.service.member.MemberDiscountService;
import com.jeeplus.modules.sys.service.member.MemberInformationService;
import com.jeeplus.modules.sys.service.member.MemberService;
import com.jeeplus.modules.sys.service.memberincome.MemberIncomeService;
import com.jeeplus.modules.sys.service.score.SorceLogService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping(value = "${adminPath}/interface/member")
public class AppMemberController {

    @Autowired
    private MemberIncomeService memberIncomeService;
    @Autowired
    private SorceLogService sorceLogService;
    @Autowired
    private MemberInformationService memberInformationService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberDiscountService memberDiscountService;
    @Autowired
    private MemberContactsService memberContactsService;
    @Autowired
    private GuideService guideService;
    @Autowired
    private ComTagService comTagService;
    @Autowired
    private CountryService countryService;
    @Autowired
    private ProductDraftService productDraftService;
    @Autowired
    private ProductCollectionService productCollectionService;
    @Autowired
    private ComArticleService comArticleService;
    @Autowired
    private ComPushPeopleService comPushPeopleService;
    @Autowired
    private ComCommentService comCommentService;
    @Autowired
    private OrderSysService orderSysService;
    @Autowired
    private AppUtils appUtils;
    @Resource
    private OrderRouteService orderRouteService;
	@Autowired
	private OrderMemberService orderMemberService;

	@Autowired
	private OrderInsuranceService orderInsuranceService;
	@Autowired
	private MemberTravelMapper memberTravelMapper;
	@Autowired
	private ComProtocolService comProtocolService;
	
	

    /**
     * 帐户流水接口
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "memberIncome")
    @ResponseBody
    public AjaxJson memberIncome(HttpServletRequest request, HttpServletResponse response, Model model) {
        AjaxJson ajaxJson = new AjaxJson();
        String type = request.getParameter("type");      //1.游客 2.会员
        String uid = request.getParameter("uid");      //会员ID，登陆后返回
        String time = request.getParameter("time");        //时间，登陆后返回
        String key = request.getParameter("key");       //密钥，登陆后返回
        String pageNo = request.getParameter("pageNo");//页码

        if (StringUtils.isBlank(type)) {
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_2);
            ajaxJson.setMsg("type传参为空");
            return ajaxJson;
        }
        if (Integer.parseInt(type) != 2) {
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_2);
            ajaxJson.setMsg("type传参错误");
            return ajaxJson;
        }
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
        if (StringUtils.isBlank(pageNo)) {
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_2);
            ajaxJson.setMsg("pageNo传参为空");
            return ajaxJson;
        }

        try {
        	String pageSize1 = request.getParameter("pageSize");       //每页数
			Integer pageSize;
			if(StringUtils.isBlank(pageSize1)){
				pageSize = appUtils.getPageSize();
			}else{
				pageSize = Integer.parseInt(pageSize1);
			}
			
            MemberIncome memberIncome = new MemberIncome();
            memberIncome.setMemberid(Integer.parseInt(uid));
            Page<MemberIncome> page = new Page<MemberIncome>();
            page.setPageNo(Integer.parseInt(pageNo));
            page.setPageSize1(pageSize);

            Page<MemberIncome> page1 = memberIncomeService.findMemberIncomePage(page, memberIncome);

            List<MemberIncome> memberIncomeList = page1.getList();

            int totalPage = page1.getTotalPage();
            if (Integer.parseInt(pageNo) > totalPage) {
                ajaxJson.getBody().put("body_list", null);
            } else {
                ajaxJson.getBody().put("body_list", memberIncomeList);
            }
            ajaxJson.getBody().put("body_total", totalPage);

            ajaxJson.setSuccess(true);
            ajaxJson.setErrorCode("0");
            ajaxJson.setMsg("获取帐户流水成功");
            return ajaxJson;
        } catch (Exception e) {
            e.printStackTrace();
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_3);
            ajaxJson.setMsg(AppErrorUtils.error_3_desc);
            return ajaxJson;
        }
    }

    /**
     * 积分流水接口
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "scoreIncome")
    @ResponseBody
    public AjaxJson scoreIncome(HttpServletRequest request, HttpServletResponse response, Model model) {
        AjaxJson ajaxJson = new AjaxJson();
        String type = request.getParameter("type");      //1.游客 2.会员
        String uid = request.getParameter("uid");      //会员ID，登陆后返回
        String time = request.getParameter("time");        //时间，登陆后返回
        String key = request.getParameter("key");       //密钥，登陆后返回
        String pageNo = request.getParameter("pageNo");//页码

        if (StringUtils.isBlank(type)) {
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_2);
            ajaxJson.setMsg("type传参为空");
            return ajaxJson;
        }
        if (Integer.parseInt(type) != 2) {
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_2);
            ajaxJson.setMsg("type传参错误");
            return ajaxJson;
        }
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
        if (StringUtils.isBlank(pageNo)) {
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_2);
            ajaxJson.setMsg("pageNo传参为空");
            return ajaxJson;
        }

        try {
        	String pageSize1 = request.getParameter("pageSize");       //每页数
			Integer pageSize;
			if(StringUtils.isBlank(pageSize1)){
				pageSize = appUtils.getPageSize();
			}else{
				pageSize = Integer.parseInt(pageSize1);
			}
			
            SorceLog sorceLog = new SorceLog();
            sorceLog.setMemberId(Integer.parseInt(uid));
            Page<SorceLog> page = new Page<SorceLog>();
            page.setPageNo(Integer.parseInt(pageNo));
            page.setPageSize1(pageSize);

            Page<SorceLog> page1 = sorceLogService.findSorceLogPage(page, sorceLog);

            List<SorceLog> sorceLogList = page1.getList();

            int totalPage = page1.getTotalPage();
            if (Integer.parseInt(pageNo) > totalPage) {
                ajaxJson.getBody().put("body_list", null);
            } else {
                ajaxJson.getBody().put("body_list", sorceLogList);
            }
            ajaxJson.getBody().put("body_total", totalPage);

            ajaxJson.setSuccess(true);
            ajaxJson.setErrorCode("0");
            ajaxJson.setMsg("获取积分流水成功");
            return ajaxJson;
        } catch (Exception e) {
            e.printStackTrace();
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_3);
            ajaxJson.setMsg(AppErrorUtils.error_3_desc);
            return ajaxJson;
        }
    }

    /**
     * 获取个人信息
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "myInfor")
    @ResponseBody
    public AjaxJson myInfor(HttpServletRequest request, HttpServletResponse response, Model model) {
        AjaxJson ajaxJson = new AjaxJson();
        String uid = request.getParameter("uid");
        String time = request.getParameter("time");
        String key = request.getParameter("key");
        String languageid = request.getParameter("languageid");
        if (StringUtils.isBlank(languageid)) {
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_2);
            ajaxJson.setMsg("languageid传参为空");
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
        try {
            MemberInformation member = memberService.myInfor(uid);
            if(StringUtils.isBlank(member.getPhoto())){
            	ComProtocol cp1 = new ComProtocol();
            	cp1.setLanguageid(Integer.parseInt(languageid));
            	ComProtocol cp = comProtocolService.getProtocol(cp1);
            	if(cp!=null&&cp.getDefaultPhoto()!=null){
            		member.setPhoto(cp.getDefaultPhoto());
            	}
            }
            ajaxJson.getBody().put("member", member);
            ajaxJson.setSuccess(true);
            ajaxJson.setErrorCode("0");
            ajaxJson.setMsg("查询个人信息成功");
            return ajaxJson;
        } catch (Exception e) {
            e.printStackTrace();
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_3);
            ajaxJson.setMsg(AppErrorUtils.error_3_desc);
            return ajaxJson;
        }
    }
    /**
     * 修改个人信息（普通用户）
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "updateMy")
    @ResponseBody
    public AjaxJson updateMy(HttpServletRequest request, HttpServletResponse response, Model model) {
        AjaxJson ajaxJson = new AjaxJson();
        String uid = request.getParameter("uid");
        String time = request.getParameter("time");
        String key = request.getParameter("key");
        String memberType = request.getParameter("memberType");
        String photo = request.getParameter("photo");       //澶村儚锛堟櫘閫氫細鍛橈級锛堟梾琛岀ぞ锛�
        String nickName = request.getParameter("nickName");    //鏄电О锛堟櫘閫氫細鍛橈級
        String phone = request.getParameter("phone");        //鑱旂郴鏂瑰紡锛堟櫘閫氫細鍛橈級锛堟梾琛岀ぞ锛�
        String countryid = request.getParameter("countryid");            //鍥藉id锛堟櫘閫氫細鍛橈級锛堟梾琛岀ぞ锛�
        String cityid = request.getParameter("cityid");            //鍩庡競id锛堟櫘閫氫細鍛橈級锛堟梾琛岀ぞ锛�
        String address = request.getParameter("address");    //璇︾粏鍦板潃锛堟櫘閫氫細鍛橈級锛堟梾琛岀ぞ锛�
        String birthday = request.getParameter("birthday");    //鐢熸棩锛堟櫘閫氫細鍛橈級
        String sex = request.getParameter("sex");            //鎬у埆锛堟櫘閫氫細鍛橈級


        String legalPerson = request.getParameter("legalPerson");            //娉曚汉浠ｈ〃锛堟梾琛岀ぞ锛�
        String companyName = request.getParameter("companyName");            //鍏徃鍚嶇О锛堟梾琛岀ぞ锛�
        String companyLogo = request.getParameter("companyLogo");            //鍏徃logo锛堟梾琛岀ぞ锛�
        String companyImg = request.getParameter("companyImg");            //钀ヤ笟鎵х収锛堟梾琛岀ぞ锛�
        String cardsImg = request.getParameter("cardsImg");            //娉曚汉韬唤璇侊紙鏃呰绀撅級
        String companyContent = request.getParameter("companyContent");            //鍏徃绠�浠嬶紙鏃呰绀撅級

        Integer memberStatus = appUtils.keyIsTrue(uid, time, key);
        if (memberStatus != 0) {
            ajaxJson.getBody().put("memberStatus", memberStatus);
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_7);
            ajaxJson.setMsg(AppErrorUtils.error_7_desc);
            return ajaxJson;
        }
        if (StringUtils.isBlank(memberType)) {
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_2);
            ajaxJson.setMsg("memberType传参为空");
            return ajaxJson;
        }
        if (Integer.parseInt(memberType) != 1 && Integer.parseInt(memberType) != 2) {
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_2);
            ajaxJson.setMsg("memberType传参错误");
            return ajaxJson;
        }
        if (Integer.parseInt(memberType) == 1) {
            if (StringUtils.isBlank(photo)) {
                ajaxJson.setSuccess(false);
                ajaxJson.setErrorCode(AppErrorUtils.error_2);
                ajaxJson.setMsg("photo传参为空");
                return ajaxJson;
            }
            if (StringUtils.isBlank(nickName)) {
                ajaxJson.setSuccess(false);
                ajaxJson.setErrorCode(AppErrorUtils.error_2);
                ajaxJson.setMsg("nickName传参为空");
                return ajaxJson;
            }
            if (StringUtils.isBlank(phone)) {
                ajaxJson.setSuccess(false);
                ajaxJson.setErrorCode(AppErrorUtils.error_2);
                ajaxJson.setMsg("mobile传参为空");
                return ajaxJson;
            }
            if (StringUtils.isBlank(countryid)) {
                ajaxJson.setSuccess(false);
                ajaxJson.setErrorCode(AppErrorUtils.error_2);
                ajaxJson.setMsg("countryid传参为空");
                return ajaxJson;
            }
            if (StringUtils.isBlank(cityid)) {
                ajaxJson.setSuccess(false);
                ajaxJson.setErrorCode(AppErrorUtils.error_2);
                ajaxJson.setMsg("cityid传参为空");
                return ajaxJson;
            }
            if (StringUtils.isBlank(birthday)) {
                ajaxJson.setSuccess(false);
                ajaxJson.setErrorCode(AppErrorUtils.error_2);
                ajaxJson.setMsg("birthday传参为空");
                return ajaxJson;
            }
            if (StringUtils.isBlank(address)) {
                ajaxJson.setSuccess(false);
                ajaxJson.setErrorCode(AppErrorUtils.error_2);
                ajaxJson.setMsg("address传参为空");
                return ajaxJson;
            }
            if (StringUtils.isBlank(sex)) {
                ajaxJson.setSuccess(false);
                ajaxJson.setErrorCode(AppErrorUtils.error_2);
                ajaxJson.setMsg("sex传参为空");
                return ajaxJson;
            }
        }
        if (Integer.parseInt(memberType) == 2) {
            if (StringUtils.isBlank(photo)) {
                ajaxJson.setSuccess(false);
                ajaxJson.setErrorCode(AppErrorUtils.error_2);
                ajaxJson.setMsg("photo传参为空");
                return ajaxJson;
            }
            if (StringUtils.isBlank(phone)) {
                ajaxJson.setSuccess(false);
                ajaxJson.setErrorCode(AppErrorUtils.error_2);
                ajaxJson.setMsg("mobile传参为空");
                return ajaxJson;
            }
            if (StringUtils.isBlank(countryid)) {
                ajaxJson.setSuccess(false);
                ajaxJson.setErrorCode(AppErrorUtils.error_2);
                ajaxJson.setMsg("countryid传参为空");
                return ajaxJson;
            }
            if (StringUtils.isBlank(cityid)) {
                ajaxJson.setSuccess(false);
                ajaxJson.setErrorCode(AppErrorUtils.error_2);
                ajaxJson.setMsg("cityid传参为空");
                return ajaxJson;
            }
            if (StringUtils.isBlank(address)) {
                ajaxJson.setSuccess(false);
                ajaxJson.setErrorCode(AppErrorUtils.error_2);
                ajaxJson.setMsg("address传参为空");
                return ajaxJson;
            }
            if (StringUtils.isBlank(legalPerson)) {
                ajaxJson.setSuccess(false);
                ajaxJson.setErrorCode(AppErrorUtils.error_2);
                ajaxJson.setMsg("legalPerson传参为空");
                return ajaxJson;
            }
            if (StringUtils.isBlank(companyName)) {
                ajaxJson.setSuccess(false);
                ajaxJson.setErrorCode(AppErrorUtils.error_2);
                ajaxJson.setMsg("companyName传参为空");
                return ajaxJson;
            }
            if (StringUtils.isBlank(companyLogo)) {
                ajaxJson.setSuccess(false);
                ajaxJson.setErrorCode(AppErrorUtils.error_2);
                ajaxJson.setMsg("companyLogo传参为空");
                return ajaxJson;
            }
            if (StringUtils.isBlank(companyImg)) {
                ajaxJson.setSuccess(false);
                ajaxJson.setErrorCode(AppErrorUtils.error_2);
                ajaxJson.setMsg("companyImg传参为空");
                return ajaxJson;
            }
            if (StringUtils.isBlank(cardsImg)) {
                ajaxJson.setSuccess(false);
                ajaxJson.setErrorCode(AppErrorUtils.error_2);
                ajaxJson.setMsg("cardsImg传参为空");
                return ajaxJson;
            }
            if (StringUtils.isBlank(companyContent)) {
                ajaxJson.setSuccess(false);
                ajaxJson.setErrorCode(AppErrorUtils.error_2);
                ajaxJson.setMsg("companyContent传参为空");
                return ajaxJson;
            }
        }

        try {
            if (Integer.parseInt(memberType) == 1) {
                MemberInformation member = new MemberInformation();
                member.setMemberid(uid);
                member.setPhoto(photo);
                member.setNickName(nickName);
                member.setPhone(phone);
                member.setCountryid(countryid);
                member.setCityid(cityid);
                member.setAddress(address);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date birthday1 = sdf.parse(birthday);
                member.setBirthday(birthday1);
                member.setSex(Integer.parseInt(sex));
                memberInformationService.updateMember(member);
            } else {
                MemberInformation member = new MemberInformation();
                member.setPhone(phone);
                member.setCountryid(countryid);
                member.setCityid(cityid);
                member.setAddress(address);
                member.setPhoto(photo);
                member.setMemberid(uid);
                member.setLegalPerson(legalPerson);
                member.setCompanyName(companyName);
                member.setCompanyLogo(companyLogo);
                member.setCompanyImg(companyImg);
                member.setCardsImg(cardsImg);
                member.setCompanyContent(companyContent);
                memberInformationService.updateMemberTravel(member);
            }

            ajaxJson.setSuccess(true);
            ajaxJson.setErrorCode("0");
            ajaxJson.setMsg("修改个人信息成功");
            return ajaxJson;
        } catch (Exception e) {
            e.printStackTrace();
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_3);
            ajaxJson.setMsg(AppErrorUtils.error_3_desc);
            return ajaxJson;
        }
    }
    /**
     * 修改旅行社信息
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "updateMyTravel")
    @ResponseBody
    public AjaxJson updateMyTravel(HttpServletRequest request, HttpServletResponse response, Model model) {
        AjaxJson ajaxJson = new AjaxJson();
        String uid = request.getParameter("uid");
        String time = request.getParameter("time");
        String key = request.getParameter("key");
        String companyType = request.getParameter("companyType");//1.个人2.公司
        String mobile = request.getParameter("mobile");        //联系方式（旅行社）
        String countryid = request.getParameter("countryid");            //国家id（旅行社）
        String cityid = request.getParameter("cityid");            //城市id（旅行社）
        String address = request.getParameter("address");    //详细地址（旅行社）


        String legalPerson = request.getParameter("legalPerson");            //法人代表（旅行社）（个人姓名）
        String companyName = request.getParameter("companyName");            //公司名称（旅行社）（个人昵称）
        String companyLogo = request.getParameter("companyLogo");            //公司logo（旅行社）（个人头像）
        String companyImg = request.getParameter("companyImg");            //营业执照（旅行社）
        String cardsImg = request.getParameter("cardsImg");            //法人身份证（旅行社）
        String companyContent = request.getParameter("companyContent");            //公司简介（旅行社）

        Integer memberStatus = appUtils.keyIsTrue(uid, time, key);
        if (memberStatus != 0) {
            ajaxJson.getBody().put("memberStatus", memberStatus);
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_7);
            ajaxJson.setMsg(AppErrorUtils.error_7_desc);
            return ajaxJson;
        }
        if (StringUtils.isBlank(companyType)) {
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_2);
            ajaxJson.setMsg("memberType传参为空");
            return ajaxJson;
        }
        if (Integer.parseInt(companyType) != 1 && Integer.parseInt(companyType) != 2) {
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_2);
            ajaxJson.setMsg("memberType传参错误");
            return ajaxJson;
        }
        if (StringUtils.isBlank(legalPerson)) {
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_2);
            ajaxJson.setMsg("legalPerson传参为空");
            return ajaxJson;
        }
        if (StringUtils.isBlank(companyName)) {
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_2);
            ajaxJson.setMsg("companyName传参为空");
            return ajaxJson;
        }
        if (StringUtils.isBlank(companyLogo)) {
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_2);
            ajaxJson.setMsg("companyLogo传参为空");
            return ajaxJson;
        }
           if(Integer.parseInt(companyType)== 1){
               if (StringUtils.isBlank(mobile)) {
                   ajaxJson.setSuccess(false);
                   ajaxJson.setErrorCode(AppErrorUtils.error_2);
                   ajaxJson.setMsg("mobile传参为空");
                   return ajaxJson;
               }
               if (StringUtils.isBlank(countryid)) {
                   ajaxJson.setSuccess(false);
                   ajaxJson.setErrorCode(AppErrorUtils.error_2);
                   ajaxJson.setMsg("countryid传参为空");
                   return ajaxJson;
               }
               if (StringUtils.isBlank(cityid)) {
                   ajaxJson.setSuccess(false);
                   ajaxJson.setErrorCode(AppErrorUtils.error_2);
                   ajaxJson.setMsg("cityid传参为空");
                   return ajaxJson;
               }
           }else {
               if (StringUtils.isBlank(companyImg)) {
                   ajaxJson.setSuccess(false);
                   ajaxJson.setErrorCode(AppErrorUtils.error_2);
                   ajaxJson.setMsg("companyImg传参为空");
                   return ajaxJson;
               }
               if (StringUtils.isBlank(cardsImg)) {
                   ajaxJson.setSuccess(false);
                   ajaxJson.setErrorCode(AppErrorUtils.error_2);
                   ajaxJson.setMsg("cardsImg传参为空");
                   return ajaxJson;
               }
           }

           

        try {
            Member member = new Member(); 
            if(StringUtils.isNotBlank(mobile)){
            	Member member1 = memberService.get(uid);
                if(mobile.equals(member1.getMobile())){
                	 member.setMobile(mobile);
                }else{
                	member.setMobile(mobile);
                	if(memberService.ifRegister(member) != null){
                		 ajaxJson.setSuccess(false);
                         ajaxJson.setErrorCode(AppErrorUtils.error_1);
                         ajaxJson.setMsg(AppErrorUtils.error_1_desc);
                         return ajaxJson;
                	}
                }
            }
            
                MemberTravel memberTravel = new MemberTravel();
                memberTravel.setCompanyType(Integer.parseInt(companyType));
                memberTravel.setCountryid(countryid);
                memberTravel.setCityid(cityid);
                memberTravel.setAddress(address);
                memberTravel.setId(uid);
                memberTravel.setMemberid(Integer.parseInt(uid));
                memberTravel.setLegalPerson(legalPerson);
                memberTravel.setCompanyName(companyName);
                memberTravel.setCompanyLogo(companyLogo);
                memberTravel.setCompanyImg(companyImg);
                memberTravel.setCardsImg(cardsImg);
                memberTravel.setCompanyContent(companyContent);
               
                memberService.updateTravel(member,memberTravel);

            ajaxJson.setSuccess(true);
            ajaxJson.setErrorCode("0");
            ajaxJson.setMsg("修改旅行社信息成功");
            return ajaxJson;
        } catch (Exception e) {
            e.printStackTrace();
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_3);
            ajaxJson.setMsg(AppErrorUtils.error_3_desc);
            return ajaxJson;
        }
    }

    /**
     * 获取公司子账号信息
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "companyInfor")
    @ResponseBody
    public AjaxJson companyInfor(HttpServletRequest request, HttpServletResponse response, Model model) {
        AjaxJson ajaxJson = new AjaxJson();
        String uid = request.getParameter("uid");
        String time = request.getParameter("time");
        String key = request.getParameter("key");
        Integer memberStatus = appUtils.keyIsTrue(uid, time, key);
        if (memberStatus != 0) {
            ajaxJson.getBody().put("memberStatus", memberStatus);
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_7);
            ajaxJson.setMsg(AppErrorUtils.error_7_desc);
            return ajaxJson;
        }
        try {
            //获取账号优惠信息
            List<MemberDiscount> memberDiscountList = memberDiscountService.getDiscountInfo(uid);
            //获取子账号
            List<Member> memberList = memberService.getChildMember(uid);
            if (memberList != null && memberList.size() > 0) {
                ajaxJson.getBody().put("memberList", memberList);
            } else {
                ajaxJson.getBody().put("memberList", new ArrayList<>());
            }
            if (memberDiscountList != null && memberDiscountList.size() > 0) {
                ajaxJson.getBody().put("memberDiscountList", memberDiscountList);
            } else {
                ajaxJson.getBody().put("memberDiscountList", new ArrayList<>());
            }
            ajaxJson.setSuccess(true);
            ajaxJson.setErrorCode("0");
            ajaxJson.setMsg("获取公司子账号信息成功");
            return ajaxJson;
        } catch (Exception e) {
            e.printStackTrace();
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_3);
            ajaxJson.setMsg(AppErrorUtils.error_3_desc);
            return ajaxJson;
        }
    }

    /**
     * 公司子账号信息修改
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "updateCompanyInfor")
    @ResponseBody
    public AjaxJson updateCompanyInfor(HttpServletRequest request, HttpServletResponse response, Model model) {
        AjaxJson ajaxJson = new AjaxJson();
        String uid = request.getParameter("uid");
        String time = request.getParameter("time");
        String key = request.getParameter("key");
        //[{memberid:"",mobile:"",password:"",viewPassword:""},{},{}]
        String child = request.getParameter("child");

        Integer memberStatus = appUtils.keyIsTrue(uid, time, key);
        if (memberStatus != 0) {
            ajaxJson.getBody().put("memberStatus", memberStatus);
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_7);
            ajaxJson.setMsg(AppErrorUtils.error_7_desc);
            return ajaxJson;
        }
        if (StringUtils.isBlank(child)) {
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_2);
            ajaxJson.setMsg("child传参为空");
            return ajaxJson;
        }
        try {
            JSONArray jsonArray = JSONArray.fromObject(child);
            List<Member> list = new ArrayList<>();
            for (int i = 0; i < jsonArray.size(); i++) {
                Member member = new Member();
                //取出数组第i个元素
                JSONObject jpro = jsonArray.getJSONObject(i);
                String memberid = jpro.getString("memberid");
                String mobile = jpro.getString("mobile");
                String viewPassword = jpro.getString("viewPassword");
                if (!StringUtils.isBlank(memberid)) {
                    member.setMemberid(Integer.parseInt(memberid));
                } else {
                    String password = jpro.getString("password");
                    member.setPassword(password);
                }
                member.setMobile(mobile);
                member.setViewPassword(viewPassword);
                list.add(member);
            }
            memberService.updateCompanyInfor(list, uid);

            ajaxJson.setSuccess(true);
            ajaxJson.setErrorCode("0");
            ajaxJson.setMsg("获取公司子账号信息成功");
            return ajaxJson;
        } catch (Exception e) {
            e.printStackTrace();
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_3);
            ajaxJson.setMsg(AppErrorUtils.error_3_desc);
            return ajaxJson;
        }
    }

    /**
     * 验证公司子账号是否存在接口
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "validateMember")
    @ResponseBody
    public AjaxJson validateMember(HttpServletRequest request, HttpServletResponse response, Model model) {
        AjaxJson ajaxJson = new AjaxJson();
        try {
            String uid = request.getParameter("uid");
            String time = request.getParameter("time");
            String key = request.getParameter("key");
            String name = request.getParameter("name");

            Integer memberStatus = appUtils.keyIsTrue(uid, time, key);
            if (memberStatus != 0) {
                ajaxJson.getBody().put("memberStatus", memberStatus);
                ajaxJson.setSuccess(false);
                ajaxJson.setErrorCode(AppErrorUtils.error_7);
                ajaxJson.setMsg(AppErrorUtils.error_7_desc);
                return ajaxJson;
            }
            if (StringUtils.isBlank(name)) {
                ajaxJson.setSuccess(false);
                ajaxJson.setErrorCode(AppErrorUtils.error_2);
                ajaxJson.setMsg("name传参为空");
                return ajaxJson;
            }

            Integer a = memberService.getValidateCount(name);
            if (a > 0) {
                ajaxJson.getBody().put("flag", false);
                ajaxJson.setSuccess(false);
                ajaxJson.setErrorCode("-1");
                ajaxJson.setMsg("账号重复请重新添加");
                return ajaxJson;
            }
            ajaxJson.getBody().put("flag", true);
            ajaxJson.setSuccess(true);
            ajaxJson.setErrorCode("0");
            ajaxJson.setMsg("账号可用");
            return ajaxJson;
        } catch (Exception e) {
            e.printStackTrace();
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_3);
            ajaxJson.setMsg(AppErrorUtils.error_3_desc);
            return ajaxJson;
        }
    }

    /**
     * 常用联系人信息列表接口
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "getMemberContact")
    @ResponseBody
    public AjaxJson getMemberContact(HttpServletRequest request, HttpServletResponse response, Model model) {
        AjaxJson ajaxJson = new AjaxJson();
        try {
            String uid = request.getParameter("uid");
            String time = request.getParameter("time");
            String key = request.getParameter("key");

            String pageNo = request.getParameter("pageNo");       //页码

            Integer memberStatus = appUtils.keyIsTrue(uid, time, key);
            if (memberStatus != 0) {
                ajaxJson.getBody().put("memberStatus", memberStatus);
                ajaxJson.setSuccess(false);
                ajaxJson.setErrorCode(AppErrorUtils.error_7);
                ajaxJson.setMsg(AppErrorUtils.error_7_desc);
                return ajaxJson;
            }
            if (StringUtils.isBlank(pageNo)) {
                ajaxJson.setSuccess(false);
                ajaxJson.setErrorCode(AppErrorUtils.error_2);
                ajaxJson.setMsg("pageNo传参为空");
                return ajaxJson;
            }

            String pageSize1 = request.getParameter("pageSize");       //每页数
			Integer pageSize;
			if(StringUtils.isBlank(pageSize1)){
				pageSize = appUtils.getPageSize();
			}else{
				pageSize = Integer.parseInt(pageSize1);
			}
			
            Page<MemberContacts> page = new Page<MemberContacts>();
            page.setPageNo(Integer.parseInt(pageNo));
            page.setPageSize1(pageSize);

            MemberContacts memberContacts = new MemberContacts();
            memberContacts.setMemberid(Integer.parseInt(uid));
            //获取常用联系人信息列表
            Page<MemberContacts> page1 = memberContactsService.getContactMember(page, memberContacts);

            List<MemberContacts> memberContactsList = page1.getList();
            int totalPage = page1.getTotalPage();

            if (Integer.parseInt(pageNo) > totalPage) {
                ajaxJson.getBody().put("memberContactsList", new ArrayList<>());
            } else {
                ajaxJson.getBody().put("memberContactsList", memberContactsList);
            }
            ajaxJson.getBody().put("totalPage", totalPage);
            ajaxJson.setSuccess(true);
            ajaxJson.setErrorCode("0");
            ajaxJson.setMsg("获取常用联系人信息列表成功");
            return ajaxJson;
        } catch (Exception e) {
            e.printStackTrace();
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_3);
            ajaxJson.setMsg(AppErrorUtils.error_3_desc);
            return ajaxJson;
        }
    }

    /**
     * 添加修改常用联系人接口
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "addMemberContact")
    @ResponseBody
    public AjaxJson addMemberContact(HttpServletRequest request, HttpServletResponse response, Model model) {
        AjaxJson ajaxJson = new AjaxJson();
        String uid = request.getParameter("uid");
        String time = request.getParameter("time");
        String key = request.getParameter("key");
        String contactid = request.getParameter("contactid");    //联系人id
        String chineseName = request.getParameter("chineseName");    //中文姓名
        String englishName = request.getParameter("englishName");        //英文姓名
        String certType = request.getParameter("certType");        //证件类型1.身份证2.护照
        String certNo = request.getParameter("certNo");    //证件号码
        String certValidDate = request.getParameter("certValidDate");        //张建过期时间
        String birthday = request.getParameter("birthday");        //出生日期
        String area = request.getParameter("area");    //区号
        String mobile = request.getParameter("mobile");        //电话号码

        Integer memberStatus = appUtils.keyIsTrue(uid, time, key);
        if (memberStatus != 0) {
            ajaxJson.getBody().put("memberStatus", memberStatus);
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_7);
            ajaxJson.setMsg(AppErrorUtils.error_7_desc);
            return ajaxJson;
        }
        if (StringUtils.isBlank(chineseName)) {
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_2);
            ajaxJson.setMsg("chineseName传参为空");
            return ajaxJson;
        }
        if (StringUtils.isBlank(englishName)) {
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_2);
            ajaxJson.setMsg("englishName传参为空");
            return ajaxJson;
        }
        if (StringUtils.isBlank(certType)) {
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_2);
            ajaxJson.setMsg("certType传参为空");
            return ajaxJson;
        }
        if (StringUtils.isBlank(certNo)) {
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_2);
            ajaxJson.setMsg("certNo传参为空");
            return ajaxJson;
        }
        if (StringUtils.isBlank(certValidDate)) {
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_2);
            ajaxJson.setMsg("certValidDate传参为空");
            return ajaxJson;
        }
        if (StringUtils.isBlank(birthday)) {
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_2);
            ajaxJson.setMsg("birthday传参为空");
            return ajaxJson;
        }
        if (StringUtils.isBlank(area)) {
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_2);
            ajaxJson.setMsg("area传参为空");
            return ajaxJson;
        }
        if (StringUtils.isBlank(mobile)) {
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_2);
            ajaxJson.setMsg("mobile传参为空");
            return ajaxJson;
        }
        try {
            MemberContacts memberContacts = new MemberContacts();
            memberContacts.setId(contactid);
            memberContacts.setMemberid(Integer.parseInt(uid));
            memberContacts.setChineseName(chineseName);
            memberContacts.setEnglishName(englishName);
            memberContacts.setCertType(Integer.parseInt(certType));
            memberContacts.setCertNo(certNo);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date birthday1 = sdf.parse(birthday);
            Date certValidDate1 = sdf.parse(certValidDate);
            memberContacts.setCertValidDate(certValidDate1);
            memberContacts.setBirthday(birthday1);
            memberContacts.setArea(area);
            memberContacts.setMobile(mobile);

            memberContactsService.save(memberContacts);

            ajaxJson.setSuccess(true);
            ajaxJson.setErrorCode("0");
            ajaxJson.setMsg("添加/修改联系人成功");
            return ajaxJson;
        } catch (Exception e) {
            e.printStackTrace();
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_3);
            ajaxJson.setMsg(AppErrorUtils.error_3_desc);
            return ajaxJson;
        }
    }

    /**
     * 删除常用联系人接口
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "deleteMemberContact")
    @ResponseBody
    public AjaxJson deleteMemberContact(HttpServletRequest request, HttpServletResponse response, Model model) {
        AjaxJson ajaxJson = new AjaxJson();
        String uid = request.getParameter("uid");
        String time = request.getParameter("time");
        String key = request.getParameter("key");
        String contactid = request.getParameter("contactid");    //联系人id

        Integer memberStatus = appUtils.keyIsTrue(uid, time, key);
        if (memberStatus != 0) {
            ajaxJson.getBody().put("memberStatus", memberStatus);
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_7);
            ajaxJson.setMsg(AppErrorUtils.error_7_desc);
            return ajaxJson;
        }
        if (StringUtils.isBlank(contactid)) {
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_2);
            ajaxJson.setMsg("contactid传参为空");
            return ajaxJson;
        }

        try {
            MemberContacts memberContacts = new MemberContacts();
            memberContacts.setId(contactid);

            memberContactsService.delete(memberContacts);

            ajaxJson.setSuccess(true);
            ajaxJson.setErrorCode("0");
            ajaxJson.setMsg("删除常用联系人成功");
            return ajaxJson;
        } catch (Exception e) {
            e.printStackTrace();
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_3);
            ajaxJson.setMsg(AppErrorUtils.error_3_desc);
            return ajaxJson;
        }
    }

    /**
     * 提交玩家认证接口
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "addGuide")
    @ResponseBody
    public AjaxJson addGuide(HttpServletRequest request, HttpServletResponse response, Model model) {
        AjaxJson ajaxJson = new AjaxJson();
        String uid = request.getParameter("uid");
        String time = request.getParameter("time");
        String key = request.getParameter("key");
        String guideid = request.getParameter("guideid");        //导游id
        String realName = request.getParameter("realName");        //真实姓名
        String codeType = request.getParameter("codeType");        //证件类型
        String code = request.getParameter("code");        //证件号码
        String countryid = request.getParameter("countryid");    //国家id
        String cityid = request.getParameter("cityid");        //城市id
        String address = request.getParameter("address");    //详细地址
        String img = request.getParameter("img");    //介绍图片
        String tagId = request.getParameter("tagId");        //擅长属性id 多个用，隔开
        String photoUrl = request.getParameter("photoUrl");    //证件图片，多个用，隔开
        String introduction = request.getParameter("introduction");        //简介
        String deltails = request.getParameter("deltails");        //详情

        Integer memberStatus = appUtils.keyIsTrue(uid, time, key);
        if (memberStatus != 0) {
            ajaxJson.getBody().put("memberStatus", memberStatus);
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_7);
            ajaxJson.setMsg(AppErrorUtils.error_7_desc);
            return ajaxJson;
        }
        if (StringUtils.isBlank(realName)) {
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_2);
            ajaxJson.setMsg("realName传参为空");
            return ajaxJson;
        }
        if (StringUtils.isBlank(codeType)) {
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_2);
            ajaxJson.setMsg("codeType传参为空");
            return ajaxJson;
        }
        if (StringUtils.isBlank(code)) {
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_2);
            ajaxJson.setMsg("code传参为空");
            return ajaxJson;
        }
        if (StringUtils.isBlank(countryid)) {
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_2);
            ajaxJson.setMsg("countryid传参为空");
            return ajaxJson;
        }
        if (StringUtils.isBlank(cityid)) {
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_2);
            ajaxJson.setMsg("cityid传参为空");
            return ajaxJson;
        }
        if (StringUtils.isBlank(address)) {
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_2);
            ajaxJson.setMsg("address传参为空");
            return ajaxJson;
        }
        if (StringUtils.isBlank(tagId)) {
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_2);
            ajaxJson.setMsg("tagId传参为空");
            return ajaxJson;
        }
        if (StringUtils.isBlank(img)) {
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_2);
            ajaxJson.setMsg("img传参为空");
            return ajaxJson;
        }
        if (StringUtils.isBlank(photoUrl)) {
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_2);
            ajaxJson.setMsg("photoUrl传参为空");
            return ajaxJson;
        }
        if (StringUtils.isBlank(introduction)) {
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_2);
            ajaxJson.setMsg("introduction传参为空");
            return ajaxJson;
        }
        if (StringUtils.isBlank(deltails)) {
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_2);
            ajaxJson.setMsg("deltails传参为空");
            return ajaxJson;
        }
        try {
            Guide guide = new Guide();
            if (StringUtils.isNotBlank(guideid)) {
            	guide = guideService.get(guideid);
            }else{
            	guide.setStatus(1);
            }
            guide.setMemberid(uid);
            guide.setRealName(realName);
            guide.setCodeType(Integer.parseInt(codeType));
            guide.setCode(code);
            guide.setImg(img);
            guide.setCountryid(Integer.parseInt(countryid));
            guide.setCityid(Integer.parseInt(cityid));
            guide.setAddress(address);
            guide.setTagId(tagId);
            guide.setPhotoUrl(photoUrl);
            guide.setIntroduction(introduction);
            guide.setDeltails(deltails);
            MemberInformation member = memberInformationService.getMember(uid);
            guide.setSex(member.getSex().toString());
            guide.setType(0);
            
            
            guideService.save(guide);

            ajaxJson.setSuccess(true);
            ajaxJson.setErrorCode("0");
            ajaxJson.setMsg("提交玩家认证成功");
            return ajaxJson;
        } catch (Exception e) {
            e.printStackTrace();
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_3);
            ajaxJson.setMsg(AppErrorUtils.error_3_desc);
            return ajaxJson;
        }
    }

    /**
     * 获取玩家认证信息接口
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "getGuideInfo")
    @ResponseBody
    public AjaxJson getGuideInfo(HttpServletRequest request, HttpServletResponse response, Model model) {
        AjaxJson ajaxJson = new AjaxJson();
        String uid = request.getParameter("uid");
        String time = request.getParameter("time");
        String key = request.getParameter("key");

        Integer memberStatus = appUtils.keyIsTrue(uid, time, key);
        if (memberStatus != 0) {
            ajaxJson.getBody().put("memberStatus", memberStatus);
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_7);
            ajaxJson.setMsg(AppErrorUtils.error_7_desc);
            return ajaxJson;
        }

        try {
            Guide guide = guideService.getGuide(uid);
            ajaxJson.getBody().put("guide", guide);
            ajaxJson.setSuccess(true);
            ajaxJson.setErrorCode("0");
            ajaxJson.setMsg("获取玩家认证信息成功");
            return ajaxJson;
        } catch (Exception e) {
            e.printStackTrace();
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_3);
            ajaxJson.setMsg(AppErrorUtils.error_3_desc);
            return ajaxJson;
        }
    }

    /**
     * 获取玩家擅长属性接口
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "getGuideLabel")
    @ResponseBody
    public AjaxJson getGuideLabel(HttpServletRequest request, HttpServletResponse response, Model model) {
        AjaxJson ajaxJson = new AjaxJson();
        String languageid = request.getParameter("languageid");

        if (StringUtils.isBlank(languageid)) {
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_2);
            ajaxJson.setMsg("languageid传参为空");
            return ajaxJson;
        }
        try {
            List<ComTag> comTagList = comTagService.getGuideLabel(Integer.parseInt(languageid));
            if (comTagList != null && comTagList.size() > 0) {
                ajaxJson.getBody().put("comTagList", comTagList);
            } else {
                ajaxJson.getBody().put("comTagList", new ArrayList<>());
            }
            ajaxJson.setSuccess(true);
            ajaxJson.setErrorCode("0");
            ajaxJson.setMsg("获取玩家擅长属性成功");
            return ajaxJson;
        } catch (Exception e) {
            e.printStackTrace();
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_3);
            ajaxJson.setMsg(AppErrorUtils.error_3_desc);
            return ajaxJson;
        }
    }

    /**
     * 根据语言获取国家城市接口
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "getCountryCity")
    @ResponseBody
    public AjaxJson getCountryCity(HttpServletRequest request, HttpServletResponse response, Model model) {
        AjaxJson ajaxJson = new AjaxJson();
        String uid = request.getParameter("uid");
        String time = request.getParameter("time");
        String key = request.getParameter("key");
        String languageid = request.getParameter("languageid");
        Integer memberStatus = appUtils.keyIsTrue(uid, time, key);
        if (memberStatus != 0) {
            ajaxJson.getBody().put("memberStatus", memberStatus);
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_7);
            ajaxJson.setMsg(AppErrorUtils.error_7_desc);
            return ajaxJson;
        }
        if (StringUtils.isBlank(languageid)) {
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_2);
            ajaxJson.setMsg("languageid传参为空");
            return ajaxJson;
        }
        try {
            Country country = new Country();
            country.setLanguageId(Integer.parseInt(languageid));
            List<Country> countryList = countryService.getCountry(country);
            if (countryList != null && countryList.size() > 0) {
                ajaxJson.getBody().put("countryList", countryList);
            } else {
                ajaxJson.getBody().put("countryList", new ArrayList<>());
            }
            ajaxJson.setSuccess(true);
            ajaxJson.setErrorCode("0");
            ajaxJson.setMsg("根据语言获取国家城市成功");
            return ajaxJson;
        } catch (Exception e) {
            e.printStackTrace();
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_3);
            ajaxJson.setMsg(AppErrorUtils.error_3_desc);
            return ajaxJson;
        }
    }

    /**
     * 获取我的草稿列表接口
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "myDraft")
    @ResponseBody
    public AjaxJson myDraft(HttpServletRequest request, HttpServletResponse response, Model model) {

        AjaxJson ajaxJson = new AjaxJson();
        String type = request.getParameter("type");      //1.游客 2.会员
        String uid = request.getParameter("uid");      //会员ID，登陆后返回
        String time = request.getParameter("time");        //时间，登陆后返回
        String key = request.getParameter("key");       //密钥，登陆后返回
        String languageid = request.getParameter("languageid");       //语言id
        String pageNo = request.getParameter("pageNo");       //页码

        if (StringUtils.isBlank(type)) {
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_2);
            ajaxJson.setMsg("type传参为空");
            return ajaxJson;
        }
        if (Integer.parseInt(type) != 2) {
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_2);
            ajaxJson.setMsg("type传参错误");
            return ajaxJson;
        }
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
        if (StringUtils.isBlank(languageid)) {
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_2);
            ajaxJson.setMsg("languageid传参为空");
            return ajaxJson;
        }
        if (StringUtils.isBlank(pageNo)) {
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_2);
            ajaxJson.setMsg("pageNo传参为空");
            return ajaxJson;
        }
        try {
        	String pageSize1 = request.getParameter("pageSize");       //每页数
			Integer pageSize;
			if(StringUtils.isBlank(pageSize1)){
				pageSize = appUtils.getPageSize();
			}else{
				pageSize = Integer.parseInt(pageSize1);
			}
			
            Page<ProductDraft> page = new Page<ProductDraft>();
            page.setPageNo(Integer.parseInt(pageNo));
            page.setPageSize1(pageSize);

            ProductDraft productDraft = new ProductDraft();
            productDraft.setMemberid(Integer.parseInt(uid));
            productDraft.setLanguageid(Integer.parseInt(languageid));
            Page<ProductDraft> page1 = productDraftService.myDraft(page, productDraft);

            List<ProductDraft> list = page1.getList();
            int totalPage = page1.getTotalPage();
            ajaxJson.getBody().put("totalPage", totalPage);
            if (Integer.parseInt(pageNo) > totalPage) {
                ajaxJson.getBody().put("list", new ArrayList<>());
            } else {
                ajaxJson.getBody().put("list", list);
            }
            ajaxJson.setSuccess(true);
            ajaxJson.setErrorCode("0");
            ajaxJson.setMsg("获取我的草稿列表成功");
            return ajaxJson;
        } catch (Exception e) {
            e.printStackTrace();
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_3);
            ajaxJson.setMsg(AppErrorUtils.error_3_desc);
            return ajaxJson;
        }
    }

    /**
     * 删除我的草稿接口
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "deleteDraft")
    @ResponseBody
    public AjaxJson deleteDraft(HttpServletRequest request, HttpServletResponse response, Model model) {

        AjaxJson ajaxJson = new AjaxJson();
        String type = request.getParameter("type");      //1.游客 2.会员
        String uid = request.getParameter("uid");      //会员ID，登陆后返回
        String time = request.getParameter("time");        //时间，登陆后返回
        String key = request.getParameter("key");       //密钥，登陆后返回
        String ids = request.getParameter("ids");       //草稿id(可以传多个，逗号分隔)


        if (StringUtils.isBlank(type)) {
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_2);
            ajaxJson.setMsg("type传参为空");
            return ajaxJson;
        }
        if (Integer.parseInt(type) != 2) {
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_2);
            ajaxJson.setMsg("type传参错误");
            return ajaxJson;
        }
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
        if (StringUtils.isBlank(ids)) {
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_2);
            ajaxJson.setMsg("ids传参为空");
            return ajaxJson;
        }
        try {
            ProductDraft productDraft = new ProductDraft();
            productDraft.setIds(ids);
            productDraftService.deleteMyDraft(productDraft);

            ajaxJson.setSuccess(true);
            ajaxJson.setErrorCode("0");
            ajaxJson.setMsg("删除我的草稿成功");
            return ajaxJson;
        } catch (Exception e) {
            e.printStackTrace();
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_3);
            ajaxJson.setMsg(AppErrorUtils.error_3_desc);
            return ajaxJson;
        }
    }

    /**
     * 获取我的收藏列表接口
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "myCollection")
    @ResponseBody
    public AjaxJson myCollection(HttpServletRequest request, HttpServletResponse response, Model model) {

        AjaxJson ajaxJson = new AjaxJson();
        String type = request.getParameter("type");      //1.游客 2.会员
        String uid = request.getParameter("uid");      //会员ID，登陆后返回
        String time = request.getParameter("time");        //时间，登陆后返回
        String key = request.getParameter("key");       //密钥，登陆后返回
        String languageid = request.getParameter("languageid");       //语言id
        String collectionType = request.getParameter("collectionType");//收藏类型

        String pageNo = request.getParameter("pageNo");//页码

        if (StringUtils.isBlank(type)) {
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_2);
            ajaxJson.setMsg("type传参为空");
            return ajaxJson;
        }
        if (Integer.parseInt(type) != 2) {
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_2);
            ajaxJson.setMsg("type传参错误");
            return ajaxJson;
        }
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
        if (StringUtils.isBlank(languageid)) {
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_2);
            ajaxJson.setMsg("languageid传参为空");
            return ajaxJson;
        }
        if (StringUtils.isBlank(collectionType)) {
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_2);
            ajaxJson.setMsg("collectionType传参为空");
            return ajaxJson;
        }
        if (StringUtils.isBlank(pageNo)) {
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_2);
            ajaxJson.setMsg("pageNo传参为空");
            return ajaxJson;
        }
        try {
        	String pageSize1 = request.getParameter("pageSize");       //每页数
			Integer pageSize;
			if(StringUtils.isBlank(pageSize1)){
				pageSize = appUtils.getPageSize();
			}else{
				pageSize = Integer.parseInt(pageSize1);
			}
			
            Page<ProductCollection> page = new Page<ProductCollection>();
            page.setPageNo(Integer.parseInt(pageNo));
            page.setPageSize1(pageSize);

            ProductCollection productCollection = new ProductCollection();
            productCollection.setMemberid(Integer.parseInt(uid));
            productCollection.setLanguageid(Integer.parseInt(languageid));
            productCollection.setType(Integer.parseInt(collectionType));
            Page<ProductCollection> page1 = new Page<ProductCollection>();
            if (Integer.parseInt(collectionType) == 1) {//路线收藏
                productCollection.setType(1);
                page1 = productCollectionService.myCollectionRoute(page, productCollection);
            } else if (Integer.parseInt(collectionType) == 2) {//参团收藏
                productCollection.setType(2);
                page1 = productCollectionService.myCollectionRoute(page, productCollection);
            } else if (Integer.parseInt(collectionType) == 3) {//导游收藏
                page1 = productCollectionService.myCollectionGuide(page, productCollection);
            } else if (Integer.parseInt(collectionType) == 4) {//邮轮收藏
                page1 = productCollectionService.myCollectionLiner(page, productCollection);
            } else if (Integer.parseInt(collectionType) == 5) {//景点收藏
                page1 = productCollectionService.myCollectionScenic(page, productCollection);
            }

            List<ProductCollection> list = page1.getList();
            int totalPage = page1.getTotalPage();
            ajaxJson.getBody().put("totalPage", totalPage);
            if (Integer.parseInt(pageNo) > totalPage) {
                ajaxJson.getBody().put("list", new ArrayList<>());
            } else {
                ajaxJson.getBody().put("list", list);
            }

//				Page<ProductCollection> pageRoute1 = productCollectionService.myCollectionRoute1(page, productCollection);
//				List<ProductCollection> listRoute1 = pageRoute1.getList();
//
//				Page<ProductCollection> pageRoute2 = productCollectionService.myCollectionRoute2(page,productCollection);
//				List<ProductCollection> listRoute2 = pageRoute2.getList();
//
//				Page<ProductCollection> pageGuide = productCollectionService.myCollectionGuide(page,productCollection);
//				List<ProductCollection> listGuide = pageGuide.getList();
//
//				Page<ProductCollection> pageLiner = productCollectionService.myCollectionLiner(page,productCollection);
//				List<ProductCollection> listLiner = pageLiner.getList();
//
//				Page<ProductCollection> pageScenic = productCollectionService.myCollectionScenic(page,productCollection);
//				List<ProductCollection> listScenic = pageScenic.getList();
//
//				if(listRoute1!=null&&listRoute1.size()>0){
//					ajaxJson.getBody().put("listRoute1", listRoute1);
//				}else{
//					ajaxJson.getBody().put("listRoute1", new ArrayList());
//				}
//				if(listRoute2!=null&&listRoute2.size()>0){
//					ajaxJson.getBody().put("listRoute2", listRoute2);
//				}else{
//					ajaxJson.getBody().put("listRoute2", new ArrayList());
//				}
//				if(listGuide!=null&&listGuide.size()>0){
//					ajaxJson.getBody().put("listGuide", listGuide);
//				}else{
//					ajaxJson.getBody().put("listGuide", new ArrayList());
//				}
//				if(listLiner!=null&&listLiner.size()>0){
//					ajaxJson.getBody().put("listLiner", listLiner);
//				}else{
//					ajaxJson.getBody().put("listLiner", new ArrayList());
//				}
//				if(listScenic!=null&&listScenic.size()>0){
//					ajaxJson.getBody().put("listScenic", listScenic);
//				}else{
//					ajaxJson.getBody().put("listScenic", new ArrayList());
//				}
            ajaxJson.setSuccess(true);
            ajaxJson.setErrorCode("0");
            ajaxJson.setMsg("获取我的收藏列表成功");
            return ajaxJson;
        } catch (Exception e) {
            e.printStackTrace();
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_3);
            ajaxJson.setMsg(AppErrorUtils.error_3_desc);
            return ajaxJson;
        }
    }

    /**
     * 取消收藏接口（多个取消）
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "deleteCollection")
    @ResponseBody
    public AjaxJson deleteCollection(HttpServletRequest request, HttpServletResponse response, Model model) {

        AjaxJson ajaxJson = new AjaxJson();
        String type = request.getParameter("type");      //1.游客 2.会员
        String uid = request.getParameter("uid");      //会员ID，登陆后返回
        String time = request.getParameter("time");        //时间，登陆后返回
        String key = request.getParameter("key");       //密钥，登陆后返回
        String collectionids = request.getParameter("collectionids");       //


        if (StringUtils.isBlank(type)) {
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_2);
            ajaxJson.setMsg("type传参为空");
            return ajaxJson;
        }
        if (Integer.parseInt(type) != 2) {
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_2);
            ajaxJson.setMsg("type传参错误");
            return ajaxJson;
        }
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
        if (StringUtils.isBlank(collectionids)) {
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_2);
            ajaxJson.setMsg("collectionids传参为空");
            return ajaxJson;
        }
        try {
            String[] ids = collectionids.split(",");
            productCollectionService.deleteCollection(ids);
            ajaxJson.setSuccess(true);
            ajaxJson.setErrorCode("0");
            ajaxJson.setMsg("取消收藏成功");
            return ajaxJson;
        } catch (Exception e) {
            e.printStackTrace();
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_3);
            ajaxJson.setMsg(AppErrorUtils.error_3_desc);
            return ajaxJson;
        }
    }

    /**
     * 收藏加入购物车接口
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "addCollectionToCar")
    @ResponseBody
    public AjaxJson addCollectionToCar(HttpServletRequest request, HttpServletResponse response, Model model) {

        AjaxJson ajaxJson = new AjaxJson();
        String type = request.getParameter("type");      //1.游客 2.会员
        String uid = request.getParameter("uid");      //会员ID，登陆后返回
        String time = request.getParameter("time");        //时间，登陆后返回
        String key = request.getParameter("key");       //密钥，登陆后返回
        String collectionids = request.getParameter("collectionids");       //语言id


        if (StringUtils.isBlank(type)) {
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_2);
            ajaxJson.setMsg("type传参为空");
            return ajaxJson;
        }
        if (Integer.parseInt(type) != 2) {
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_2);
            ajaxJson.setMsg("type传参错误");
            return ajaxJson;
        }
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
        if (StringUtils.isBlank(collectionids)) {
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_2);
            ajaxJson.setMsg("collectionids传参为空");
            return ajaxJson;
        }
        try {
            String[] ids = collectionids.split(",");
            productCollectionService.addCollectionToCar(ids);
            ajaxJson.setSuccess(true);
            ajaxJson.setErrorCode("0");
            ajaxJson.setMsg("收藏加入购物车成功");
            return ajaxJson;
        } catch (Exception e) {
            e.printStackTrace();
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_3);
            ajaxJson.setMsg(AppErrorUtils.error_3_desc);
            return ajaxJson;
        }
    }

    /**
     * 获取关于我们信息接口
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "getArticle")
    @ResponseBody
    public AjaxJson getArticle(HttpServletRequest request, HttpServletResponse response, Model model) {

        AjaxJson ajaxJson = new AjaxJson();
        String languageid = request.getParameter("languageid");       //语言id
        if (StringUtils.isBlank(languageid)) {
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_2);
            ajaxJson.setMsg("languageid传参为空");
            return ajaxJson;
        }
        try {
            ComArticle comArticle = new ComArticle();
            comArticle.setLanguageId(Integer.parseInt(languageid));
            List<ComArticle> list = new ArrayList<>();
            List<ComArticle> list2 = comArticleService.getArticle(comArticle);
            if (list2 != null && list2.size() > 0) {
                list = list2;
            }
            ajaxJson.getBody().put("list", list);
            ajaxJson.setSuccess(true);
            ajaxJson.setErrorCode("0");
            ajaxJson.setMsg("获取关于我们信息成功");
            return ajaxJson;
        } catch (Exception e) {
            e.printStackTrace();
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_3);
            ajaxJson.setMsg(AppErrorUtils.error_3_desc);
            return ajaxJson;
        }
    }

    /**
     * 获取消息中心系统消息接口
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "getCompush")
    @ResponseBody
    public AjaxJson getCompush(HttpServletRequest request, HttpServletResponse response, Model model) {

        AjaxJson ajaxJson = new AjaxJson();
        String type = request.getParameter("type");      //1.游客 2.会员
        String uid = request.getParameter("uid");      //会员ID，登陆后返回
        String time = request.getParameter("time");        //时间，登陆后返回
        String key = request.getParameter("key");       //密钥，登陆后返回
        String pageNo = request.getParameter("pageNo");//页码
        if (StringUtils.isBlank(type)) {
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_2);
            ajaxJson.setMsg("type传参为空");
            return ajaxJson;
        }
        if (Integer.parseInt(type) != 2) {
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_2);
            ajaxJson.setMsg("type传参错误");
            return ajaxJson;
        }
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
        if (StringUtils.isBlank(pageNo)) {
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_2);
            ajaxJson.setMsg("pageNo传参为空");
            return ajaxJson;
        }
        try {
        	String pageSize1 = request.getParameter("pageSize");       //每页数
			Integer pageSize;
			if(StringUtils.isBlank(pageSize1)){
				pageSize = appUtils.getPageSize();
			}else{
				pageSize = Integer.parseInt(pageSize1);
			}
			
            Page<ComPushPeople> page = new Page<ComPushPeople>();
            page.setPageNo(Integer.parseInt(pageNo));
            page.setPageSize1(pageSize);
            ComPushPeople comPushPeople = new ComPushPeople();
            comPushPeople.setMemberid(Integer.parseInt(uid));
            Page<ComPushPeople> page1 = comPushPeopleService.getCompush(page, comPushPeople);

            List<ComPushPeople> list = page1.getList();
            int totalPage = page1.getTotalPage();
            ajaxJson.getBody().put("totalPage", totalPage);
            if (Integer.parseInt(pageNo) > totalPage) {
                ajaxJson.getBody().put("list", new ArrayList<>());
            } else {
                ajaxJson.getBody().put("list", list);
            }
            ajaxJson.setSuccess(true);
            ajaxJson.setErrorCode("0");
            ajaxJson.setMsg("获取消息中心系统消息成功");
            return ajaxJson;
        } catch (Exception e) {
            e.printStackTrace();
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_3);
            ajaxJson.setMsg(AppErrorUtils.error_3_desc);
            return ajaxJson;
        }
    }

    /**
     * 获取消息中心评论消息接口
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "getComment")
    @ResponseBody
    public AjaxJson getComment(HttpServletRequest request, HttpServletResponse response, Model model) {

        AjaxJson ajaxJson = new AjaxJson();
        String type = request.getParameter("type");      //1.游客 2.会员
        String uid = request.getParameter("uid");      //会员ID，登陆后返回
        String time = request.getParameter("time");        //时间，登陆后返回
        String key = request.getParameter("key");       //密钥，登陆后返回
        String pageNo = request.getParameter("pageNo");//页码
        if (StringUtils.isBlank(type)) {
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_2);
            ajaxJson.setMsg("type传参为空");
            return ajaxJson;
        }
        if (Integer.parseInt(type) != 2) {
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_2);
            ajaxJson.setMsg("type传参错误");
            return ajaxJson;
        }
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
        if (StringUtils.isBlank(pageNo)) {
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_2);
            ajaxJson.setMsg("pageNo传参为空");
            return ajaxJson;
        }
        try {
        	String pageSize1 = request.getParameter("pageSize");       //每页数
			Integer pageSize;
			if(StringUtils.isBlank(pageSize1)){
				pageSize = appUtils.getPageSize();
			}else{
				pageSize = Integer.parseInt(pageSize1);
			}
			
            Page<ComComment> page = new Page<ComComment>();
            page.setPageNo(Integer.parseInt(pageNo));
            page.setPageSize1(pageSize);
            ComComment comComment = new ComComment();
            comComment.setMemberId(Integer.parseInt(uid));
            Page<ComComment> page1 = comCommentService.getComment(page, comComment);

            List<ComComment> list = page1.getList();
            int totalPage = page1.getTotalPage();
            ajaxJson.getBody().put("totalPage", totalPage);
            if (Integer.parseInt(pageNo) > totalPage) {
                ajaxJson.getBody().put("list", new ArrayList<>());
            } else {
                ajaxJson.getBody().put("list", list);
            }
            ajaxJson.setSuccess(true);
            ajaxJson.setErrorCode("0");
            ajaxJson.setMsg("获取消息中心评论消息成功");
            return ajaxJson;
        } catch (Exception e) {
            e.printStackTrace();
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_3);
            ajaxJson.setMsg(AppErrorUtils.error_3_desc);
            return ajaxJson;
        }
    }

    /**
     * 获取我的订单接口
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "myOrder")
    @ResponseBody
    public AjaxJson myOrder(HttpServletRequest request, HttpServletResponse response, Model model) {

        AjaxJson ajaxJson = new AjaxJson();
        String uid = request.getParameter("uid");      //会员ID，登陆后返回
        String languageid = request.getParameter("languageid");       //密钥，登陆后返回
        String pageNo = request.getParameter("pageNo");//页码
        String status = request.getParameter("status");      //订单状态 0标识全部 1.待付款2.待确定，3.待出行，4.待评价 
        ajaxJson = this.checkMyOrder(request);
        if (ajaxJson != null) {
            return ajaxJson;
        } else {
            ajaxJson = new AjaxJson();
        }
        try {
        	String pageSize1 = request.getParameter("pageSize");       //每页数
			Integer pageSize;
			if(StringUtils.isBlank(pageSize1)){
				pageSize = appUtils.getPageSize();
			}else{
				pageSize = Integer.parseInt(pageSize1);
			}
			
            Page<OrderSys> page = new Page<OrderSys>();
            page.setPageNo(Integer.parseInt(pageNo));
            page.setPageSize1(pageSize);
            OrderSys orderSys = new OrderSys();
            orderSys.setMemberid(Integer.parseInt(uid));
            orderSys.setLanguageid(Integer.parseInt(languageid));
            if(StringUtils.isNotBlank(status)){
                orderSys.setStatus(Integer.parseInt(status));
            }

            Page<OrderSys> page1 = orderSysService.myOrder(page, orderSys);

            List<OrderSys> list = page1.getList();
            int totalPage = page1.getTotalPage();
            ajaxJson.getBody().put("totalPage", totalPage);
            if (Integer.parseInt(pageNo) > totalPage) {
                ajaxJson.getBody().put("list", new ArrayList<>());
            }
            if (list != null && list.size() > 0) {
                List<Order> list1 = new ArrayList<>();
                List<Integer> fatherids = new ArrayList<>();
                List<Integer> ids = new ArrayList<>();
                for (OrderSys a : list) {
                    fatherids.add(Integer.parseInt(a.getId()));
                }

                Map map = new HashMap();
                map.put("fatheridIns", fatherids);
                List<OrderSys> orderSysFathers = orderSysService.findListByPage(map);
                for (OrderSys a : list) {
                    Order order = new Order();
                	BeanUtils.copyProperties(a, order);
                    order.setCreateDateString(this.date2StringTime(a.getCreateDate()));
                    order.setType(a.getType());
                    order.setId(Integer.parseInt(a.getId()));
                    order.setStatus(a.getStatus());
                    List<OrderSys> list2 = this.getSubOrderSys(orderSysFathers, Integer.parseInt(a.getId()));

                    order.setOrderSysList(list2);
                    list1.add(order);
                }
                ajaxJson.getBody().put("list", list1);
            } else {
                ajaxJson.getBody().put("list", new ArrayList<>());
            }
            ajaxJson.setSuccess(true);
            ajaxJson.setErrorCode("0");
            ajaxJson.setMsg("获取我的订单成功");
            return ajaxJson;
        } catch (Exception e) {
            e.printStackTrace();
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_3);
            ajaxJson.setMsg(AppErrorUtils.error_3_desc);
            return ajaxJson;
        }


    }
    /**
     * 获取我的售后订单接口
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "myOrderAfter")
    @ResponseBody
    public AjaxJson myOrderAfter(HttpServletRequest request, HttpServletResponse response, Model model) {

        AjaxJson ajaxJson = new AjaxJson();
        String uid = request.getParameter("uid");      //会员ID，登陆后返回
        String languageid = request.getParameter("languageid");       //密钥，登陆后返回
        String pageNo = request.getParameter("pageNo");//页码
       
        ajaxJson = this.checkMyOrder(request);
        if (ajaxJson != null) {
            return ajaxJson;
        } else {
            ajaxJson = new AjaxJson();
        }
        try {
        	String pageSize1 = request.getParameter("pageSize");       //每页数
			Integer pageSize;
			if(StringUtils.isBlank(pageSize1)){
				pageSize = appUtils.getPageSize();
			}else{
				pageSize = Integer.parseInt(pageSize1);
			}
			
            Page<OrderSys> page = new Page<OrderSys>();
            page.setPageNo(Integer.parseInt(pageNo));
            page.setPageSize1(pageSize);
            OrderSys orderSys = new OrderSys();
            orderSys.setMemberid(Integer.parseInt(uid));
            orderSys.setLanguageid(Integer.parseInt(languageid));
            Page<OrderSys> page1 = orderSysService.myOrderAfter(page, orderSys);

            List<OrderSys> list = page1.getList();
            int totalPage = page1.getTotalPage();
            ajaxJson.getBody().put("totalPage", totalPage);
            if (Integer.parseInt(pageNo) > totalPage) {
                ajaxJson.getBody().put("list", new ArrayList<>());
            }
            if (list != null && list.size() > 0) {
                List<Order> list1 = new ArrayList<>();
                List<Integer> fatherids = new ArrayList<>();
                List<Integer> ids = new ArrayList<>();
                for (OrderSys a : list) {
                    fatherids.add(Integer.parseInt(a.getId()));
                }

                Map map = new HashMap();
                map.put("fatheridIns", fatherids);
                List<OrderSys> orderSysFathers = orderSysService.findListByPage(map);
                for (OrderSys a : list) {
                    Order order = new Order();
                	BeanUtils.copyProperties(a, order);
                    order.setCreateDateString(this.date2StringTime(a.getCreateDate()));
                    order.setType(a.getType());
                    order.setId(Integer.parseInt(a.getId()));
                    order.setStatus(a.getStatus());
                    List<OrderSys> list2 = this.getSubOrderSys(orderSysFathers, Integer.parseInt(a.getId()));

                    order.setOrderSysList(list2);
                    list1.add(order);
                }
                ajaxJson.getBody().put("list", list1);
            } else {
                ajaxJson.getBody().put("list", new ArrayList<>());
            }
            ajaxJson.setSuccess(true);
            ajaxJson.setErrorCode("0");
            ajaxJson.setMsg("获取我的售后订单成功");
            return ajaxJson;
        } catch (Exception e) {
            e.printStackTrace();
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_3);
            ajaxJson.setMsg(AppErrorUtils.error_3_desc);
            return ajaxJson;
        }


    }
    /**
     * @Title: cancelOrder
     * @Description: 取消订单
     */
    @ResponseBody
    @RequestMapping(value = "cancelOrder")
    public AjaxJson cancelOrder(Integer orderId, HttpServletRequest request) {
    	AjaxJson ajaxJson =new AjaxJson();
		String uid = request.getParameter("uid");      //会员ID，登陆后返回
		String time = request.getParameter("time");		//时间，登陆后返回
		String key = request.getParameter("key");       //密钥，登陆后返回
		
		Integer memberStatus = appUtils.keyIsTrue(uid, time, key);
		if (memberStatus != 0) {
			ajaxJson.getBody().put("memberStatus", memberStatus);
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_7);
			ajaxJson.setMsg(AppErrorUtils.error_7_desc);
			return ajaxJson;
		}
		
		 ajaxJson = orderSysService.cancelOrder(orderId);
		return ajaxJson;
    }
    /**
     * @Title: refundOrder
     * @Description: 订单退款  
     */
    @ResponseBody
    @RequestMapping(value = "refundOrder")
    public AjaxJson refundOrder(Integer orderId, HttpServletRequest request) {
    	AjaxJson ajaxJson =new AjaxJson();
		String uid = request.getParameter("uid");      //会员ID，登陆后返回
		String time = request.getParameter("time");		//时间，登陆后返回
		String key = request.getParameter("key");       //密钥，登陆后返回
		
		Integer memberStatus = appUtils.keyIsTrue(uid, time, key);
		if (memberStatus != 0) {
			ajaxJson.getBody().put("memberStatus", memberStatus);
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_7);
			ajaxJson.setMsg(AppErrorUtils.error_7_desc);
			return ajaxJson;
		}
		
		 ajaxJson = orderSysService.refundOrder(orderId);
		return ajaxJson;
    }
	
	/** 
	* @Title: 订单详情
	* @Description:  订单详情
	*/ 
	@ResponseBody
	@RequestMapping(value = "orderDetail")
	public AjaxJson orderDetail(Integer orderId,HttpServletRequest request) {
    	AjaxJson ajaxJson =new AjaxJson();

		OrderDetail orderDetail=new OrderDetail();
		 Map paramMap=new HashMap();
		 paramMap.put("id", orderId);
		 OrderSys   orderSys= orderSysService.findObj(paramMap);
		 BeanUtils.copyProperties(orderSys, orderDetail);
		 orderDetail.setCreateDateString(this.date2StringTime(orderSys.getCreateDate()));

		 
		//订单信息
		 if(orderSys.getType()==4) {
			 paramMap=new HashMap();
			 paramMap.put("orderSys1", orderId);
			 OrderRoute orderRoute=orderRouteService.findObj(paramMap);
			 orderDetail.setOrderRoute(orderRoute);
		 }
		 //联系人信息
		 List<OrderMemberApp> orderMemberApps=new ArrayList<>();
		 OrderMember orderMember=new OrderMember();
		 orderMember.setTypeId(orderId);
		 List<OrderMember> orderMembers= orderMemberService.findListByTyid2(orderMember);
		 if(orderMembers!=null) {
			 for (OrderMember orderMember2 : orderMembers) {
				 OrderMemberApp orderMemberApp=new OrderMemberApp();
			      BeanUtils.copyProperties(orderMember2, orderMemberApp);
			      orderMemberApps.add(orderMemberApp);
			}
		 }
		 orderDetail.setOrderMemberApps(orderMemberApps);
		 //保险信息
		 paramMap=new HashMap();
		 paramMap.put("orderSys1", orderId);
		 OrderInsurance orderInsurance=orderInsuranceService.findObj(paramMap);
		 OrderInsuranceApp orderInsuranceApp=new OrderInsuranceApp();
		 if(orderInsurance!=null) {
		      BeanUtils.copyProperties(orderInsurance, orderInsuranceApp);
		 }
		 orderDetail.setOrderInsuranceApp(orderInsuranceApp);
	 ajaxJson.getBody().put("orderDetail", orderDetail);
    
    ajaxJson.setSuccess(true);
    ajaxJson.setErrorCode("0");
    ajaxJson.setMsg("获取我的订单成功");
    return ajaxJson;
	}
	
	


    private List<OrderSys> getSubOrderSys(List<OrderSys> orderSyss, Integer pId) {

        List<OrderSys> subs = new ArrayList<>();
        for (Iterator iterator = orderSyss.iterator(); iterator.hasNext(); ) {
            OrderSys orderSys = (OrderSys) iterator.next();
            System.err.println("(orderSys.getFatherid()=" + orderSys.getFatherid() + "  " + pId);
            if (orderSys.getType().equals(10)) {
                continue;
            }
            if (orderSys.getFatherid().equals(pId)) {
                subs.add(orderSys);
            }
        }
        return subs;
    }

    private List<OrderSys> getOrderSys(List<OrderSys> orderSyss, String orderId) {

        List<OrderSys> subs = new ArrayList<>();
        for (Iterator iterator = orderSyss.iterator(); iterator.hasNext(); ) {
            OrderSys orderSys = (OrderSys) iterator.next();
            if (orderSys.getId().equals(orderId)) {
                subs.add(orderSys);
            }
        }
        return subs;
    }


    private AjaxJson checkMyOrder(HttpServletRequest request) {

        String type = request.getParameter("type");      //1.游客 2.会员
        String uid = request.getParameter("uid");      //会员ID，登陆后返回
        String time = request.getParameter("time");        //时间，登陆后返回
        String key = request.getParameter("key");       //密钥，登陆后返回
        String languageid = request.getParameter("languageid");       //密钥，登陆后返回
        String pageNo = request.getParameter("pageNo");//页码
        AjaxJson ajaxJson = new AjaxJson();
        if (StringUtils.isBlank(type)) {
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_2);
            ajaxJson.setMsg("type传参为空");
            return ajaxJson;
        }
        if (Integer.parseInt(type) != 2) {
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_2);
            ajaxJson.setMsg("type传参错误");
            return ajaxJson;
        }
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
        if (StringUtils.isBlank(languageid)) {
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_2);
            ajaxJson.setMsg("languageid传参为空");
            return ajaxJson;
        }
        if (StringUtils.isBlank(pageNo)) {
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_2);
            ajaxJson.setMsg("pageNo传参为空");
            return ajaxJson;
        }

        return null;
    }

    public String date2StringTime(Date date) {
        DateFormat format1 = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format1.format(date);

    }

    /**
     * @Title: orderComment
     * @Description: 评论订单
     */
    @ResponseBody
    @RequestMapping(value = "orderComment")
    public AjaxJson orderComment(String commentJson, HttpServletRequest request) {
        String type = request.getParameter("type");      //1.游客 2.会员
        String uid = request.getParameter("uid");      //会员ID，登陆后返回
        String time = request.getParameter("time");        //时间，登陆后返回
        String key = request.getParameter("key");       //密钥，登陆后返回
        String languageid = request.getParameter("languageid");       //密钥，登陆后返回
        AjaxJson ajaxJson = new AjaxJson();
        if (StringUtils.isBlank(type)) {
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_2);
            ajaxJson.setMsg("type传参为空");
            return ajaxJson;
        }
        if (Integer.parseInt(type) != 2) {
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_2);
            ajaxJson.setMsg("type传参错误");
            return ajaxJson;
        }
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
        if (StringUtils.isBlank(languageid)) {
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_2);
            ajaxJson.setMsg("languageid传参为空");
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
        JSONObject jsonObject = JSONObject.fromObject(commentJson);
        String orderSysId = jsonObject.getString("orderSysId");
        JSONArray jsonArray = jsonObject.getJSONArray("commentList");
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject object = jsonArray.getJSONObject(i);
            String content = object.getString("content");
            String commentType = object.getString("type");
            String typeId = object.getString("typeId");
            String level = object.getString("level");
            ComComment comComment = new ComComment();
            comComment.setContent(content);
            comComment.setLanguageId(Integer.valueOf(languageid));
            comComment.setStatus(1);
            comComment.setType(Integer.valueOf(commentType));
            comComment.setTypeId(Integer.valueOf(typeId));
            comComment.setLevel(Integer.valueOf(level));
            comComment.setMemberId(Integer.valueOf(uid));
            comCommentService.save(comComment);

        }
        //常规路线
        OrderSys orderSys1 = new OrderSys();
        orderSys1.setId(orderSysId);
        orderSys1.setStatus(5);
        orderSysService.updateObject(orderSys1);
        ajaxJson.setSuccess(true);
        ajaxJson.setErrorCode("0");
        ajaxJson.setMsg("评论成功");
        return ajaxJson;
    }
}