package com.jeeplus.modules.app;

import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.QiniuUtils;
import com.jeeplus.common.utils.UploadHelper;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.modules.base.task.SerializeUtil;
import com.jeeplus.modules.meiguotong.entity.car.CarInfo;
import com.jeeplus.modules.meiguotong.entity.collection.ProductCollection;
import com.jeeplus.modules.meiguotong.entity.comarticle.ComArticle;
import com.jeeplus.modules.meiguotong.entity.comcity.ComCity;
import com.jeeplus.modules.meiguotong.entity.comcomment.ComComment;
import com.jeeplus.modules.meiguotong.entity.comconsult.ComConsult;
import com.jeeplus.modules.meiguotong.entity.comcurrency.ComCurrency;
import com.jeeplus.modules.meiguotong.entity.comdig.ComDig;
import com.jeeplus.modules.meiguotong.entity.comlanguage.ComLanguage;
import com.jeeplus.modules.meiguotong.entity.comnavigation.ComNavigation;
import com.jeeplus.modules.meiguotong.entity.comprotocol.ComProtocol;
import com.jeeplus.modules.meiguotong.entity.comtag.ComTag;
import com.jeeplus.modules.meiguotong.entity.conutry.Country;
import com.jeeplus.modules.meiguotong.entity.guide.Guide;
import com.jeeplus.modules.meiguotong.entity.hotel.Hotel;
import com.jeeplus.modules.meiguotong.entity.hotel.HotelRoom;
import com.jeeplus.modules.meiguotong.entity.hotel.OrderHotel;
import com.jeeplus.modules.meiguotong.entity.insurance.Insurance;
import com.jeeplus.modules.meiguotong.entity.insurance.InsuranceRelationMod;
import com.jeeplus.modules.meiguotong.entity.insurance.OrderInsurance;
import com.jeeplus.modules.meiguotong.entity.liner_line.LinerLine;
import com.jeeplus.modules.meiguotong.entity.order.OrderRoute;
import com.jeeplus.modules.meiguotong.entity.order.OrderSys;
import com.jeeplus.modules.meiguotong.entity.ordercar.OrderCar;
import com.jeeplus.modules.meiguotong.entity.orderguide.OrderGuide;
import com.jeeplus.modules.meiguotong.entity.orderliner.OrderLiner;
import com.jeeplus.modules.meiguotong.entity.orderscenicspot.OrderScenicSpot;
import com.jeeplus.modules.meiguotong.entity.product.Route;
import com.jeeplus.modules.meiguotong.entity.scenicspot.ScenicSpot;
import com.jeeplus.modules.meiguotong.entity.settingtitle.SettingTitle;
import com.jeeplus.modules.meiguotong.entity.travel.OrderTravelBusiness;
import com.jeeplus.modules.meiguotong.service.car.CarInfoService;
import com.jeeplus.modules.meiguotong.service.collection.ProductCollectionService;
import com.jeeplus.modules.meiguotong.service.comarticle.ComArticleService;
import com.jeeplus.modules.meiguotong.service.comcity.ComCityService;
import com.jeeplus.modules.meiguotong.service.comcomment.ComCommentService;
import com.jeeplus.modules.meiguotong.service.comconsult.ComConsultService;
import com.jeeplus.modules.meiguotong.service.comcurrency.ComCurrencyService;
import com.jeeplus.modules.meiguotong.service.comdig.ComDigService;
import com.jeeplus.modules.meiguotong.service.comlanguage.ComLanguageService;
import com.jeeplus.modules.meiguotong.service.comnavigation.ComNavigationService;
import com.jeeplus.modules.meiguotong.service.comprotocol.ComProtocolService;
import com.jeeplus.modules.meiguotong.service.comtag.ComTagService;
import com.jeeplus.modules.meiguotong.service.conutry.CountryService;
import com.jeeplus.modules.meiguotong.service.guide.GuideService;
import com.jeeplus.modules.meiguotong.service.hotel.HotelRoomService;
import com.jeeplus.modules.meiguotong.service.hotel.HotelService;
import com.jeeplus.modules.meiguotong.service.hotel.OrderHotelService;
import com.jeeplus.modules.meiguotong.service.insurance.InsuranceRelationModService;
import com.jeeplus.modules.meiguotong.service.insurance.OrderInsuranceService;
import com.jeeplus.modules.meiguotong.service.liner.LinerService;
import com.jeeplus.modules.meiguotong.service.liner_line.LinerLineService;
import com.jeeplus.modules.meiguotong.service.order.OrderRouteService;
import com.jeeplus.modules.meiguotong.service.order.OrderSysService;
import com.jeeplus.modules.meiguotong.service.ordercar.OrderCarService;
import com.jeeplus.modules.meiguotong.service.orderguide.OrderGuideService;
import com.jeeplus.modules.meiguotong.service.orderliner.OrderLinerService;
import com.jeeplus.modules.meiguotong.service.orderscenicspot.OrderScenicSpotService;
import com.jeeplus.modules.meiguotong.service.product.RouteService;
import com.jeeplus.modules.meiguotong.service.productcar.ProductCarService;
import com.jeeplus.modules.meiguotong.service.scenicspot.ScenicSpotService;
import com.jeeplus.modules.meiguotong.service.settingtitle.SettingTitleService;
import com.jeeplus.modules.meiguotong.service.travel.OrderTravelBusinessService;
import com.jeeplus.modules.sys.entity.comAppstart.ComAppstart;
import com.jeeplus.modules.sys.entity.comFeedback.ComFeedback;
import com.jeeplus.modules.sys.entity.comPoster.ComPoster;
import com.jeeplus.modules.sys.entity.comVersion.ComVersion;
import com.jeeplus.modules.sys.entity.common.ComLoginLog;
import com.jeeplus.modules.sys.entity.income.Income;
import com.jeeplus.modules.sys.entity.member.Member;
import com.jeeplus.modules.sys.entity.member.MemberContacts;
import com.jeeplus.modules.sys.entity.member.MemberRefund;
import com.jeeplus.modules.sys.entity.memberincome.MemberIncome;
import com.jeeplus.modules.sys.service.MemberCollectionService;
import com.jeeplus.modules.sys.service.comAppstart.ComAppstartService;
import com.jeeplus.modules.sys.service.comFeedback.ComFeedbackService;
import com.jeeplus.modules.sys.service.comPoster.ComPosterService;
import com.jeeplus.modules.sys.service.common.ComLoginLogService;
import com.jeeplus.modules.sys.service.income.IncomeService;
import com.jeeplus.modules.sys.service.member.MemberContactsService;
import com.jeeplus.modules.sys.service.member.MemberRefundService;
import com.jeeplus.modules.sys.service.member.MemberService;
import com.jeeplus.modules.sys.service.memberincome.MemberIncomeService;
import com.jeeplus.modules.sys.utils.CodeGenUtils;
import com.jeeplus.modules.tools.utils.RandomUtils;
import net.oschina.j2cache.redis.client.RedisClient;
import net.oschina.j2cache.redis.support.RedisClientFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.misc.BASE64Decoder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = "${adminPath}/interface/common")
public class AppCommonController {

    @Autowired
    private MemberService memberService;
    @Autowired
    private ComFeedbackService comFeedbackService;
    @Autowired
    private ComPosterService comPosterService;
    @Autowired
    private ComAppstartService comAppstartService;
    @Autowired
    private CarInfoService carInfoService;
    @Autowired
    private MemberCollectionService memberCollectionService;
    @Autowired
    private ComLoginLogService comLoginLogService;
    @Autowired
    private ComCityService comCityService;
    @Autowired
    private AppUtils appUtils;
    @Autowired
    private ProductCollectionService productCollectionService;
    @Autowired
    private ProductCarService productCarService;
    @Autowired
    private RouteService routeService;
    @Autowired
    private MemberRefundService memberRefundService;
    @Autowired
    private InsuranceRelationModService insuranceRelationModService;
    @Autowired
    private ComDigService comDigService;
    @Autowired
    private SettingTitleService settingTitleService;
    @Autowired
    private ComNavigationService comNavigationService;
    @Autowired
    private ComTagService comTagService;
    @Autowired
    private ComCommentService comCommentService;
    @Autowired
    private ScenicSpotService scenicSpotService;
    @Autowired
    private HotelService hotelService;
    @Autowired
    private ComConsultService comConsultService;
    @Autowired
    private MemberContactsService memberContactsService;
    @Autowired
    private HotelRoomService hotelRoomService;
    @Autowired
    private GuideService guideService;
    @Autowired
    private LinerService linerService;
    @Autowired
    private ComProtocolService comProtocolService;
    @Autowired
    private ComLanguageService comLanguageService;
    @Autowired
    private ComCurrencyService comCurrencyService;
    @Resource
    private OrderSysService orderSysService;
    @Resource
    private OrderRouteService orderRouteService;
    @Resource
    private OrderInsuranceService orderInsuranceService;
    @Resource
    private IncomeService incomeService;
    @Resource
    private MemberIncomeService memberIncomeService;
    @Resource
    private CountryService countryService;
    @Resource
    private LinerLineService linerLineService;
    @Resource
    private OrderLinerService orderLinerService;
    @Resource
    private OrderScenicSpotService orderScenicSpotService;
    @Resource
    private OrderGuideService orderGuideService;
    @Resource
    private OrderCarService orderCarService;
    @Resource
    private OrderHotelService orderHotelService;
    @Resource
    private OrderTravelBusinessService orderTravelBusinessService;
    @Autowired
    private ComArticleService ComArticleService;
	@Autowired
	private RedisClientFactory redisClientFactory;

    /**
     * 上传图片接口
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "imgUpload", method = RequestMethod.POST)
    public AjaxJson imgUpload(HttpServletRequest request, HttpServletResponse response, Model model) {
        AjaxJson ajaxJson = new AjaxJson();
        // 图片文件进行base64编码进行传输
        String file = request.getParameter("file");

        if (StringUtils.isBlank(file)) {
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_2);
            ajaxJson.setMsg(AppErrorUtils.error_2_desc);
            return ajaxJson;
        }
        try {
            file = UploadHelper.replacePre(file);
            StringBuilder stringBuilder = new StringBuilder();
            // Base64解码
            byte[] bytes = new BASE64Decoder().decodeBuffer(file);
            for (int i = 0; i < bytes.length; ++i) {
                if (bytes[i] < 0) {// 调整异常数据
                    bytes[i] += 256;
                }
            }
            InputStream sbs = new ByteArrayInputStream(bytes);

            String modifyFileName = "";
            String year = DateUtils.getYear();
            String month = DateUtils.getMonth();
            String day = DateUtils.getDay();

            String pathUrl = "user/" + year + "/" + month + "/" + day + "/";
            modifyFileName = AppUtils.randomUUID() + UploadHelper.checkFormat(bytes);

            String string = QiniuUtils.uploadFile(sbs, pathUrl + modifyFileName);
            if (string.equals("")) {
                ajaxJson.setSuccess(false);
                ajaxJson.setErrorCode(AppErrorUtils.error_9);
                ajaxJson.setMsg(AppErrorUtils.error_9_desc);
                return ajaxJson;
            }
            String tupianUrl = QiniuUtils.QiniuUrl + pathUrl + modifyFileName;
            ajaxJson.getBody().put("imgUrl", tupianUrl);
            ajaxJson.setSuccess(true);
            ajaxJson.setErrorCode("0");
            ajaxJson.setMsg("");

        } catch (Exception e) {
            e.printStackTrace();
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_3);
            ajaxJson.setMsg(AppErrorUtils.error_3_desc);
        }

        return ajaxJson;
    }


    /**
     * 意见反馈接口
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "feeBack")
    public AjaxJson feeback(HttpServletRequest request, HttpServletResponse response, Model model) {
        AjaxJson ajaxJson = new AjaxJson();
        String type = request.getParameter("type");//1.游客 2.会员
        String uid = request.getParameter("uid");
        String time = request.getParameter("time");
        String key = request.getParameter("key");
        String content = request.getParameter("content");
        String mobile = request.getParameter("mobile"); //手机
        String email = request.getParameter("email");//邮箱
        String name = request.getParameter("name");//名字
        if (StringUtils.isBlank(content)) {
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_2);
            ajaxJson.setMsg(AppErrorUtils.error_2_desc);
            return ajaxJson;
        }

        try {
            ComFeedback comFeedback = new ComFeedback();
            if (Integer.valueOf(type) == 2) {
                Integer memberStatus = appUtils.keyIsTrue(uid, time, key);
                if (memberStatus != 0) {
                    ajaxJson.getBody().put("memberStatus", memberStatus);
                    ajaxJson.setSuccess(false);
                    ajaxJson.setErrorCode(AppErrorUtils.error_7);
                    ajaxJson.setMsg(AppErrorUtils.error_7_desc);
                    return ajaxJson;
                }
                Member member = memberService.get(uid);
                comFeedback.setMobile(member.getMobile());
                comFeedback.setMemberid(Integer.valueOf(uid));
                comFeedback.setEmail(member.getEmail());
                //			comFeedback.setMembername(member.getNickname());
//				comFeedback.setMobile(mobile);
//				comFeedback.setMemberid(Integer.valueOf(uid));
//				comFeedback.setEmail(email);
//				comFeedback.setMembername(name);
            }
            comFeedback.setContent(content);
            comFeedback.setIsDeal(0);
            comFeedback.setIsWay(Integer.valueOf(type));
            comFeedbackService.save(comFeedback);
            ajaxJson.setSuccess(true);
            ajaxJson.setErrorCode("0");
            ajaxJson.setMsg("提交反馈内容成功");
        } catch (Exception e) {
            e.printStackTrace();
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_3);
            ajaxJson.setMsg(AppErrorUtils.error_3_desc);
        }
        return ajaxJson;
    }

    /**
     * banner广告接口
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getbanner")
    public AjaxJson bannerPoster(HttpServletRequest request, HttpServletResponse response, Model model) {
        AjaxJson ajaxJson = new AjaxJson();
        String type = request.getParameter("type");//1.游客 2.会员
        String uid = request.getParameter("uid");
        String time = request.getParameter("time");
        String key = request.getParameter("key");
        String positionType = request.getParameter("postType");//1.首页 2.秒杀页
        if (Integer.valueOf(type) == 2) {
            Integer memberStatus = appUtils.keyIsTrue(uid, time, key);
            if (memberStatus != 0) {
                ajaxJson.getBody().put("memberStatus", memberStatus);
                ajaxJson.setSuccess(false);
                ajaxJson.setErrorCode(AppErrorUtils.error_7);
                ajaxJson.setMsg(AppErrorUtils.error_7_desc);
                return ajaxJson;
            }
        }

        if (StringUtils.isBlank(positionType)) {
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_2);
            ajaxJson.setMsg(AppErrorUtils.error_2_desc);
            return ajaxJson;
        }
        try {
            List<ComPoster> comPoster = comPosterService.findPosterByType(positionType);
            ajaxJson.getBody().put("comPosterList", comPoster);
            ajaxJson.setSuccess(true);
            ajaxJson.setErrorCode("0");
            ajaxJson.setMsg("提交反馈内容成功");
        } catch (Exception e) {
            e.printStackTrace();
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_3);
            ajaxJson.setMsg(AppErrorUtils.error_3_desc);
        }
        return ajaxJson;
    }

    /**
     * APP信息获取接口
     *
     * @param request
     * @param response
     * @param model
     * @return
     *//*
    @RequestMapping(value = "getProtocol")
	@ResponseBody
	public AjaxJson getProtocol(HttpServletRequest request, HttpServletResponse response, Model model) {
		AjaxJson ajaxJson = new AjaxJson();
		Integer type = Integer.parseInt(request.getParameter("type"));//1.游客 2.会员
		Integer protocolType = Integer.parseInt(request.getParameter("protocolType"));//1.用户协议，2.关于我们 3.企业介绍
		String uid = request.getParameter("uid");
		String time = request.getParameter("time");
		String key = request.getParameter("key");
		if (type == null || type.equals("")) {
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_2);
			ajaxJson.setMsg(AppErrorUtils.error_2_desc);
			return ajaxJson;
		}
		if (protocolType == null || protocolType.equals("")) {
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_2);
			ajaxJson.setMsg(AppErrorUtils.error_2_desc);
			return ajaxJson;
		}
		if (type == 2) {
			if (!AppUtils.keyIsTrue(uid, time, key)) {
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_7);
				ajaxJson.setMsg(AppErrorUtils.error_7_desc);
				return ajaxJson;
			}
		}

		try {
			ComProtocol comProtocol1=comProtocolService.findProtocolByType(protocolType);//查找APP帮助信息
			ajaxJson.getBody().put("comProtocol", comProtocol1);
			ajaxJson.setSuccess(true);
			ajaxJson.setErrorCode("0");
			ajaxJson.setMsg("APP信息获取成功");
			return ajaxJson;
		} catch (Exception e) {
			e.printStackTrace();
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_3);
			ajaxJson.setMsg(AppErrorUtils.error_3_desc);
			return ajaxJson;
		}
	}

	*/

    /**
     * 启动接口
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "getStart")
    @ResponseBody
    public AjaxJson getStart(HttpServletRequest request, HttpServletResponse response, Model model) {
        AjaxJson ajaxJson = new AjaxJson();
        Integer type = Integer.parseInt(request.getParameter("type"));//1.游客 2.会员
        String version = request.getParameter("version");//当前安装版本号，为0则代表新安装
        Integer source = Integer.parseInt(request.getParameter("source"));//1.安卓，2.苹果
        String ip = request.getParameter("ip");//1.安卓，2.苹果
        String uid = request.getParameter("uid");
        String time = request.getParameter("time");
        String key = request.getParameter("key");
        if (type == null || type.equals("")) {
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_2);
            ajaxJson.setMsg(AppErrorUtils.error_2_desc);
            return ajaxJson;
        } else if (version == null || version.equals("")) {
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_2);
            ajaxJson.setMsg(AppErrorUtils.error_2_desc);
            return ajaxJson;
        } else if (source == null || source.equals("")) {
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_2);
            ajaxJson.setMsg(AppErrorUtils.error_2_desc);
            return ajaxJson;
        } else {//若都不为空
            if (type == 2) {
                Integer memberStatus = appUtils.keyIsTrue(uid, time, key);
                if (memberStatus != 0) {
                    ajaxJson.getBody().put("memberStatus", memberStatus);
                    ajaxJson.setSuccess(false);
                    ajaxJson.setErrorCode(AppErrorUtils.error_7);
                    ajaxJson.setMsg(AppErrorUtils.error_7_desc);
                    return ajaxJson;
                }
            }
            try {
                //查找版本号
                ComVersion comVersion = comAppstartService.findVersion(version, source);
                if (comVersion != null) {
                    ajaxJson.getBody().put("isUpdate", 2);//1.需要更新 2.不需要更新
                    ajaxJson.getBody().put("version_body", comVersion);
                } else {
                    ComVersion comVersion1 = comAppstartService.findNewVersion(source);
                    ajaxJson.getBody().put("isUpdate", 1);//1.需要更新 2.不需要更新
                    ajaxJson.getBody().put("version_body", comVersion1);
                }
                ComAppstart comAppstart = new ComAppstart();
                List<ComAppstart> comAppstart1 = comAppstartService.findList(comAppstart);
                ComLoginLog comLoginLog = new ComLoginLog();
                comLoginLog.setLoginIp(ip);
                comLoginLog.setLoginDate(new Date());
                comLoginLog.setLoginType(source);
                comLoginLog.setMemberid(Integer.valueOf(uid));
                if (StringUtils.isBlank(uid)) {
                    comLoginLog.setLoginWay(1);
                    comLoginLog.setIsLog(1);
                } else {
                    Member member = memberService.get(uid);
                    //						comLoginLog.setMembername(member.getNickname());
                    comLoginLog.setIsLog(2);
                    comLoginLog.setLoginWay(3);
                }
                comLoginLogService.save(comLoginLog);
                ajaxJson.getBody().put("appImg", comAppstart1);
                ajaxJson.setSuccess(true);
                ajaxJson.setErrorCode("0");
                ajaxJson.setMsg("启动接口获取成功");
                return ajaxJson;
            } catch (Exception e) {
                e.printStackTrace();
                ajaxJson.setSuccess(false);
                ajaxJson.setErrorCode(AppErrorUtils.error_3);
                ajaxJson.setMsg(AppErrorUtils.error_3_desc);
                return ajaxJson;
            }
        }
    }
    /**
     * 获取文章所有分类接口
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "allCategory")
    @ResponseBody
    public AjaxJson allCategory(HttpServletRequest request, HttpServletResponse response, Model model) {
        AjaxJson ajaxJson = new AjaxJson();
        String type = request.getParameter("type");      //1.游客 2.会员
        String uid = request.getParameter("uid");      //会员ID，登陆后返回
        String time = request.getParameter("time");        //时间，登陆后返回
        String key = request.getParameter("key");       //密钥，登陆后返回

        if (StringUtils.isBlank(type)) {
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_2);
            ajaxJson.setMsg("type传参为空");
            return ajaxJson;
        }
        if (Integer.parseInt(type) == 2) {
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
        }

        try {

            /*ajaxJson.getBody().put("body");*/
            ajaxJson.setSuccess(true);
            ajaxJson.setErrorCode("0");
            ajaxJson.setMsg("获取文章所有分类成功");
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
     * 获取文章详情接口
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "categoryContent")
    @ResponseBody
    public AjaxJson categoryContent(HttpServletRequest request, HttpServletResponse response, Model model) {
        AjaxJson ajaxJson = new AjaxJson();
        String type = request.getParameter("type");      //1.游客 2.会员
        String uid = request.getParameter("uid");      //会员ID，登陆后返回
        String time = request.getParameter("time");        //时间，登陆后返回
        String key = request.getParameter("key");       //密钥，登陆后返回
        String articleId = request.getParameter("articleId");       //文章ID

        if (StringUtils.isBlank(type)) {
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_2);
            ajaxJson.setMsg("type传参为空");
            return ajaxJson;
        }
        if (Integer.parseInt(type) == 2) {
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
        }
        if (StringUtils.isBlank(articleId)) {
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_2);
            ajaxJson.setMsg("articleId传参为空");
            return ajaxJson;
        }

        try {

	/*		ComArticle comArticle=comArticleService.getArtical(articleId);

			ajaxJson.getBody().put("body", comArticle);*/

            ajaxJson.setSuccess(true);
            ajaxJson.setErrorCode("0");
            ajaxJson.setMsg("获取文章详情成功");
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
     * 获取系统说明接口
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "getHelp")
    @ResponseBody
    public AjaxJson getHelp(HttpServletRequest request, HttpServletResponse response, Model model) {
        AjaxJson ajaxJson = new AjaxJson();
        String type = request.getParameter("type");      //1.游客 2.会员
        String uid = request.getParameter("uid");      //会员ID，登陆后返回
        String time = request.getParameter("time");        //时间，登陆后返回
        String key = request.getParameter("key");       //密钥，登陆后返回
        String wayType = request.getParameter("wayType");//1.关于我们 2.（根据实际项目定义中文值请求所需要的值即可）

        if (StringUtils.isBlank(type)) {
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_2);
            ajaxJson.setMsg("type传参为空");
            return ajaxJson;
        }
        if (Integer.parseInt(type) == 2) {
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
        }
        if (StringUtils.isBlank(wayType)) {
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_2);
            ajaxJson.setMsg("wayType传参为空");
            return ajaxJson;
        }

        try {
            /*ComProtocol comProtocol = comProtocolService.findHelp(wayType);*/

            /*ajaxJson.getBody().put("body", comProtocol);*/

            ajaxJson.setSuccess(true);
            ajaxJson.setErrorCode("0");
            ajaxJson.setMsg("获取系统说明成功");
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
     * 获取城市接口
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "getCityList")
    @ResponseBody
    public AjaxJson getCityList(HttpServletRequest request, HttpServletResponse response, Model model) {
        AjaxJson ajaxJson = new AjaxJson();

        String language = request.getParameter("languageid");//语言id
        String cityName = request.getParameter("cityName");//城市名称


        if (StringUtils.isBlank(language)) {
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_2);
            ajaxJson.setMsg("languageid传参为空");
            return ajaxJson;
        }
        try {
            ComCity comCity = new ComCity();
            comCity.setLanguageId(language);
            comCity.setCityName(cityName);
            List<ComCity> list = comCityService.getCityList(comCity);
            for (ComCity c : list){
                if(StringUtils.isNotBlank(c.getPhotoUrl())){
                    c.setPhotoUrl(c.getPhotoUrl().split(",")[0]);
                }
            }
            if (list != null && list.size() > 0) {
                ajaxJson.getBody().put("list", list);
            } else {
                ajaxJson.getBody().put("list", new ArrayList<>());
            }
            ajaxJson.setSuccess(true);
            ajaxJson.setErrorCode("0");
            ajaxJson.setMsg("获取城市集合成功");
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
     * 各类型标签属性接口
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "getLabel")
    @ResponseBody
    public AjaxJson getLabel(HttpServletRequest request, HttpServletResponse response, Model model) {

        AjaxJson ajaxJson = new AjaxJson();
        String type = request.getParameter("type");      //1.游客 2.会员
        String uid = request.getParameter("uid");      //会员ID，登陆后返回
        String time = request.getParameter("time");        //时间，登陆后返回
        String key = request.getParameter("key");       //密钥，登陆后返回
        String languageid = request.getParameter("languageid");       //语言id
        String routeType = request.getParameter("routeType");       //1  常规线路  2 当地参团  3 邮轮  4 景点 5 当地玩家  6 商务定制',
        if (StringUtils.isBlank(routeType)) {
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_2);
            ajaxJson.setMsg("routeType传参为空");
            return ajaxJson;
        }
        if (!StringUtils.isBlank(type) && Integer.parseInt(type) == 2) {
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
        }

        if (StringUtils.isBlank(languageid)) {
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_2);
            ajaxJson.setMsg("languageid传参为空");
            return ajaxJson;
        }

        try {
            ComTag comTag = new ComTag();
            comTag.setLanguageId(Integer.parseInt(languageid));
            int a = Integer.parseInt(routeType);
            comTag.setType(a);
            List<ComTag> list = new ArrayList<>();
            if (a == 1 || a == 2 || a == 3 || a == 4) {
                list = comTagService.getRouteLabel(comTag);
            } else {
                list = comTagService.getLabel(comTag);
            }
            if (list != null && list.size() > 0) {
                ajaxJson.getBody().put("list", list);
            } else {
                ajaxJson.getBody().put("list", new ArrayList<>());
            }
            ajaxJson.setSuccess(true);
            ajaxJson.setErrorCode("0");
            ajaxJson.setMsg("获取标签属性成功");
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
     * 添加/取消收藏接口（单个）
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "saveCollection")
    @ResponseBody
    public synchronized AjaxJson saveCollection(HttpServletRequest request, HttpServletResponse response, Model model) {

        AjaxJson ajaxJson = new AjaxJson();
        try {
            String type = request.getParameter("type");      //1.游客 2.会员
            String uid = request.getParameter("uid");      //会员ID，登陆后返回
            String time = request.getParameter("time");        //时间，登陆后返回
            String key = request.getParameter("key");       //密钥，登陆后返回
            String languageid = request.getParameter("languageid");       //语言id
            String typeid = request.getParameter("typeid");       //产品id
            String collectionType = request.getParameter("collectionType");       //1.常规路线2.当地参团3.当地玩家4.游轮5.景点门票


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
            if (StringUtils.isBlank(typeid)) {
                ajaxJson.setSuccess(false);
                ajaxJson.setErrorCode(AppErrorUtils.error_2);
                ajaxJson.setMsg("typeid传参为空");
                return ajaxJson;
            }
            if (StringUtils.isBlank(collectionType)) {
                ajaxJson.setSuccess(false);
                ajaxJson.setErrorCode(AppErrorUtils.error_2);
                ajaxJson.setMsg("collectionType传参为空");
                return ajaxJson;
            }

            ProductCollection productCollection = new ProductCollection();
            productCollection.setType(Integer.parseInt(collectionType));        // 1.常规路线2.当地参团3.当地玩家4.游轮5.景点
            productCollection.setTypeid(Integer.parseInt(typeid));        // 收藏产品id
            productCollection.setMemberid(Integer.parseInt(uid));        // 用户id
            productCollection.setLanguageid(Integer.parseInt(languageid));        // 语言id

            //判断是否收藏过
            int count = productCollectionService.judgeCollection(productCollection);
            if (count > 0) {//收藏过，取消收藏
            	productCollectionService.deleteCollection(productCollection);
                ajaxJson.setMsg("取消收藏成功");
            } else {//收藏
                productCollectionService.save(productCollection);
                ajaxJson.setMsg("添加收藏成功");
            }
            ajaxJson.setSuccess(true);
            ajaxJson.setErrorCode("0");
           
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
     * 退款说明接口
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "refundInfor")
    @ResponseBody
    public AjaxJson refundInfor(HttpServletRequest request, HttpServletResponse response, Model model) {

        AjaxJson ajaxJson = new AjaxJson();
        try {
            String type = request.getParameter("type");      //1.游客 2.会员
            String uid = request.getParameter("uid");      //会员ID，登陆后返回
            String time = request.getParameter("time");        //时间，登陆后返回
            String key = request.getParameter("key");       //密钥，登陆后返回
            String languageid = request.getParameter("languageid");       //语言id

            String productType = request.getParameter("productType");       //1.包车租车2.短程接送3.接送机4常规路线5.当地参团6.游轮7.景点门票9.酒店10.保险
            //11.当地玩家12.旅游定制-导游13.旅游定制-司兼导14包车/租车-导游15包车/租车-司兼导
            String productid = request.getParameter("productid");       //产品id（导游传导游id）


            if (StringUtils.isBlank(productType)) {
                ajaxJson.setSuccess(false);
                ajaxJson.setErrorCode(AppErrorUtils.error_2);
                ajaxJson.setMsg("productType传参为空");
                return ajaxJson;
            }
            if (StringUtils.isBlank(productid)) {
                ajaxJson.setSuccess(false);
                ajaxJson.setErrorCode(AppErrorUtils.error_2);
                ajaxJson.setMsg("productid传参为空");
                return ajaxJson;
            }
            if(!StringUtils.isBlank(type) && Integer.parseInt(type) == 2){
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

            }
            
            if (StringUtils.isBlank(languageid)) {
                ajaxJson.setSuccess(false);
                ajaxJson.setErrorCode(AppErrorUtils.error_2);
                ajaxJson.setMsg("languageid传参为空");
                return ajaxJson;
            }
            int agentid = 0;
            int useType = 0;
            Integer proType = Integer.parseInt(productType);
            if (proType == 1) {//1.包车租车
                useType = 2;
                CarInfo car = carInfoService.get(productid);
                agentid = car.getAgentid();
            } else if (proType == 2) {//2.短程接送
                useType = 2;
                CarInfo car = carInfoService.get(productid);
                agentid = car.getAgentid();
            } else if (proType == 3) {//3.接送机
                useType = 2;
                CarInfo car = carInfoService.get(productid);
                agentid = car.getAgentid();
            } else if (proType == 4 || proType == 5) {//4常规路线5参团
                useType = 2;
                //获取供应商
                Route route = routeService.get(productid);
                agentid = route.getAgentid();
            } else if (proType == 6) {//6.游轮
                useType = 2;
                LinerLine liner = linerLineService.get(productid);
                agentid = liner.getAgentid();
            } else if (proType == 7) {//7.景点门票
                useType = 2;
                ScenicSpot scenicSpot = scenicSpotService.get(productid);
                agentid = scenicSpot.getAgentid();
            } else if (proType == 9) {//9.酒店
                useType = 2;
                Hotel hotel = hotelService.get(productid);
                agentid = hotel.getAgentid();
            } else if (proType == 10) {//10.保险
                useType = 2;

            } else if (proType == 11) {//11.当地玩家
                useType = 1;
                //Guide guide = guideService.get(productid);
                agentid = Integer.parseInt(productid);
            } else if (proType == 12) {//12.旅游定制-导游
                useType = 1;
                //Guide guide = guideService.get(productid);
                agentid = Integer.parseInt(productid);
            } else if (proType == 13) {//13.旅游定制-司兼导
                useType = 1;
                //Guide guide = guideService.get(productid);
                agentid = Integer.parseInt(productid);
            } else if (proType == 14) {//14包车/租车-导游
                useType = 1;
                //Guide guide = guideService.get(productid);
                agentid = Integer.parseInt(productid);
            } else if (proType == 15) {//15包车/租车-司兼导
                useType = 1;
                //Guide guide = guideService.get(productid);
                agentid = Integer.parseInt(productid);
            }
            MemberRefund memberRefund = new MemberRefund();
            memberRefund.setType(useType);
            memberRefund.setTypeId(agentid);
            memberRefund.setRefundType(proType);
            List<MemberRefund> list = memberRefundService.getRefundInfor(memberRefund);
            if (list != null && list.size() > 0) {
                ajaxJson.getBody().put("list", list);
            } else {
                ajaxJson.getBody().put("list", new ArrayList<>());
            }
            ajaxJson.setSuccess(true);
            ajaxJson.setErrorCode("0");
            ajaxJson.setMsg("获取退款说明成功");
            return ajaxJson;
        } catch (Exception e) {
            e.printStackTrace();
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_3);
            ajaxJson.setMsg("获取退款说明失败");
            return ajaxJson;
        }
    }

    /**
     * 获取保险方案接口
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "getInsurance")
    @ResponseBody
    public AjaxJson getInsurance(HttpServletRequest request, HttpServletResponse response, Model model) {

        AjaxJson ajaxJson = new AjaxJson();
        try {
            String type = request.getParameter("type");      //1.游客 2.会员
            String uid = request.getParameter("uid");      //会员ID，登陆后返回
            String time = request.getParameter("time");        //时间，登陆后返回
            String key = request.getParameter("key");       //密钥，登陆后返回
            String languageid = request.getParameter("languageid");       //语言id

            String productType = request.getParameter("productType");       //1.包车租车2.短程接送3.接送机4常规路线5.当地参团6.游轮7.景点门票8.当地玩家9.旅游定制,

            if (StringUtils.isBlank(productType)) {
                ajaxJson.setSuccess(false);
                ajaxJson.setErrorCode(AppErrorUtils.error_2);
                ajaxJson.setMsg("productType传参为空");
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

            InsuranceRelationMod insuranceRelationMod = new InsuranceRelationMod();
            insuranceRelationMod.setLanguageId(Integer.parseInt(languageid));
            insuranceRelationMod.setType(Integer.parseInt(productType));

            List<Insurance> list = insuranceRelationModService.getInsurance(insuranceRelationMod);
            if (list != null && list.size() > 0) {
                ajaxJson.getBody().put("list", list);
            } else {
                ajaxJson.getBody().put("list", new ArrayList<>());
            }
            ajaxJson.setSuccess(true);
            ajaxJson.setErrorCode("0");
            ajaxJson.setMsg("获取保险方案成功");
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
     * 点赞接口
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "digComment")
    @ResponseBody
    public synchronized AjaxJson digComment(HttpServletRequest request, HttpServletResponse response, Model model) {

        AjaxJson ajaxJson = new AjaxJson();
        try {
            String type = request.getParameter("type");      //1.游客 2.会员
            String uid = request.getParameter("uid");      //会员ID，登陆后返回
            String time = request.getParameter("time");        //时间，登陆后返回
            String key = request.getParameter("key");       //密钥，登陆后返回
            String languageid = request.getParameter("languageid");       //语言id

            String typeId = request.getParameter("typeId");       //点赞id
            String digType = request.getParameter("digType");       //'点赞类型：1.评论点赞2.攻略点赞',

            if (StringUtils.isBlank(typeId)) {
                ajaxJson.setSuccess(false);
                ajaxJson.setErrorCode(AppErrorUtils.error_2);
                ajaxJson.setMsg("typeId传参为空");
                return ajaxJson;
            }
            if (StringUtils.isBlank(digType)) {
                ajaxJson.setSuccess(false);
                ajaxJson.setErrorCode(AppErrorUtils.error_2);
                ajaxJson.setMsg("digType传参为空");
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
            ComDig comDig = new ComDig();
            comDig.setMemberId(Integer.parseInt(uid));    // 点赞人id
            comDig.setType(Integer.parseInt(digType));    // 点赞类型：1.评论点赞2攻略点赞
            comDig.setTypeId(Integer.parseInt(typeId));    // 根据type 去区分 这个字段是主键id
            //判断是否点过赞
            int count = comDigService.getCount(comDig);
            if (count > 0) {//取消点赞
                comDigService.deleteDig(comDig);
                ajaxJson.getBody().put("code", 0);
            } else {
                comDigService.save(comDig);
                ajaxJson.getBody().put("code", 1);
            }
            ajaxJson.setSuccess(true);
            ajaxJson.setErrorCode("0");
            ajaxJson.setMsg("点赞成功");
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
     * 产品菜单接口
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "getProMenu")
    @ResponseBody
    public AjaxJson getProMenu(HttpServletRequest request, HttpServletResponse response, Model model) {

        AjaxJson ajaxJson = new AjaxJson();
        try {
            String type = request.getParameter("type");      //1.游客 2.会员
            String uid = request.getParameter("uid");      //会员ID，登陆后返回
            String time = request.getParameter("time");        //时间，登陆后返回
            String key = request.getParameter("key");       //密钥，登陆后返回
            String languageid = request.getParameter("languageid");       //语言id

            String proType = request.getParameter("proType");       //1.常规路线2.当地参团3.景点4.游轮

            String proid = request.getParameter("proid"); //产品id
            if (StringUtils.isBlank(proType)) {
                ajaxJson.setSuccess(false);
                ajaxJson.setErrorCode(AppErrorUtils.error_2);
                ajaxJson.setMsg("proType传参为空");
                return ajaxJson;
            }
            if (StringUtils.isBlank(proid)) {
                ajaxJson.setSuccess(false);
                ajaxJson.setErrorCode(AppErrorUtils.error_2);
                ajaxJson.setMsg("proid传参为空");
                return ajaxJson;
            }
            if (StringUtils.isNotBlank(type) && Integer.parseInt(type) == 2) {
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
            }
            if (StringUtils.isBlank(languageid)) {
                ajaxJson.setSuccess(false);
                ajaxJson.setErrorCode(AppErrorUtils.error_2);
                ajaxJson.setMsg("languageid传参为空");
                return ajaxJson;
            }
            SettingTitle settingTitle = new SettingTitle();
            settingTitle.setLanguageId(Integer.parseInt(languageid));
            settingTitle.setType(Integer.parseInt(proType));
            settingTitle.setProid(Integer.parseInt(proid));
            List<SettingTitle> list = settingTitleService.getProMenu(settingTitle);
            if (list != null && list.size() > 0) {
                ajaxJson.getBody().put("list", list);
            } else {
                ajaxJson.getBody().put("list", new ArrayList<>());
            }
            ajaxJson.setSuccess(true);
            ajaxJson.setErrorCode("0");
            ajaxJson.setMsg("获取产品菜单成功");
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
     * 根据语言获取热门目的地接口
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "getHotCity")
    @ResponseBody
    public AjaxJson getHotCity(HttpServletRequest request, HttpServletResponse response, Model model) {

        AjaxJson ajaxJson = new AjaxJson();
        RedisClient client=redisClientFactory.getResource();
        try {
            String type = request.getParameter("type");      //1.游客 2.会员
            String uid = request.getParameter("uid");      //会员ID，登陆后返回
            String time = request.getParameter("time");        //时间，登陆后返回
            String key = request.getParameter("key");       //密钥，登陆后返回
            String languageid = request.getParameter("languageid");       //语言id

            if (StringUtils.isNotBlank(type) && Integer.parseInt(type) == 2) {
                Integer memberStatus = appUtils.keyIsTrue(uid, time, key);
                if (memberStatus != 0) {
                    ajaxJson.getBody().put("memberStatus", memberStatus);
                    ajaxJson.setSuccess(false);
                    ajaxJson.setErrorCode(AppErrorUtils.error_7);
                    ajaxJson.setMsg(AppErrorUtils.error_7_desc);
                    return ajaxJson;
                }
            }
            if (StringUtils.isBlank(languageid)) {
                ajaxJson.setSuccess(false);
                ajaxJson.setErrorCode(AppErrorUtils.error_2);
                ajaxJson.setMsg("languageid传参为空");
                return ajaxJson;
            }
           
            //缓存取数据
            //List<ComNavigation> list = (List<ComNavigation>)CacheUtils.get("appHome", "hotCityList_"+languageid);
            byte[] in = client.get(("hotCityList_"+languageid).getBytes());
            List<ComNavigation> list = SerializeUtil.unserializeForList(in);
			
            if(list!=null&&list.size()>0){
            	 ajaxJson.getBody().put("list", list);
            }else{
            	ComNavigation comNavigation = new ComNavigation();
                comNavigation.setLanguageId(Integer.parseInt(languageid));
                List<ComNavigation> list1= comNavigationService.getHotCity(comNavigation);
                if (list1 != null && list1.size() > 0) {
                    ajaxJson.getBody().put("list", list1);
                } else {
                    ajaxJson.getBody().put("list", new ArrayList<>());
                }
            }
            
            ajaxJson.setSuccess(true);
            ajaxJson.setErrorCode("0");
            ajaxJson.setMsg("根据语言获取热门目的地成功");
            return ajaxJson;
        } catch (Exception e) {
            e.printStackTrace();
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_3);
            ajaxJson.setMsg(AppErrorUtils.error_3_desc);
            return ajaxJson;
        }finally{
        	try {
    			client.close();
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
        }
    }

    /**
     * 查询产品评论列表接口
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "selectComment")
    @ResponseBody
    public AjaxJson selectComment(HttpServletRequest request, HttpServletResponse response, Model model) {

        AjaxJson ajaxJson = new AjaxJson();
        try {
            String type = request.getParameter("type");      //1.游客 2.会员
            String uid = request.getParameter("uid");      //会员ID，登陆后返回
            String time = request.getParameter("time");        //时间，登陆后返回
            String key = request.getParameter("key");       //密钥，登陆后返回
            String languageid = request.getParameter("languageid");       //语言id
            String proType = request.getParameter("proType");       //产品类型
            String typeid = request.getParameter("typeid");       //产品id
            String pageNo = request.getParameter("pageNo");//页码


            if (!StringUtils.isBlank(type) && Integer.parseInt(type) == 2) {
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
            }
            if (StringUtils.isBlank(languageid)) {
                ajaxJson.setSuccess(false);
                ajaxJson.setErrorCode(AppErrorUtils.error_2);
                ajaxJson.setMsg("languageid传参为空");
                return ajaxJson;
            }
            if (StringUtils.isBlank(proType)) {
                ajaxJson.setSuccess(false);
                ajaxJson.setErrorCode(AppErrorUtils.error_2);
                ajaxJson.setMsg("proType传参为空");
                return ajaxJson;
            }
            if (StringUtils.isBlank(typeid)) {
                ajaxJson.setSuccess(false);
                ajaxJson.setErrorCode(AppErrorUtils.error_2);
                ajaxJson.setMsg("typeid传参为空");
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
			
            ComComment comComment = new ComComment();
            comComment.setLanguageId(Integer.parseInt(languageid));
            comComment.setType(Integer.parseInt(proType));
            comComment.setTypeId(Integer.parseInt(typeid));
            if (StringUtils.isNotBlank(uid)) {
                comComment.setMemberId(Integer.parseInt(uid));
            } else {
                comComment.setMemberId(0);
            }


            Page<ComComment> page = new Page<ComComment>();
            page.setPageNo(Integer.parseInt(pageNo));
            page.setPageSize1(pageSize);

            Page<ComComment> page1 = comCommentService.selectRouteComment(page, comComment);

            List<ComComment> list = page1.getList();
            if(list!=null&&list.size()>0){
    			for(ComComment a:list){
    				if(StringUtils.isBlank(a.getMemberPhoto())){
    					ComProtocol cp1 = new ComProtocol();
    	            	cp1.setLanguageid(Integer.parseInt(languageid));
    	            	ComProtocol cp = comProtocolService.getProtocol(cp1);
    	            	if(cp!=null&&cp.getDefaultPhoto()!=null){
    	            		a.setMemberPhoto(cp.getDefaultPhoto());
    	            	}
    				}
    			}
    		}
            int totalPage = page1.getTotalPage();
            if (Integer.parseInt(pageNo) > totalPage) {
                ajaxJson.getBody().put("list", new ArrayList<>());
            } else {
                ajaxJson.getBody().put("list", list);
            }
            ajaxJson.getBody().put("totalPage", totalPage);
            ajaxJson.setSuccess(true);
            ajaxJson.setErrorCode("0");
            ajaxJson.setMsg("获取产品评论成功");
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
     * 产品评论子评论列表接口
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "getChildComment")
    @ResponseBody
    public AjaxJson getChildComment(HttpServletRequest request, HttpServletResponse response, Model model) {

        AjaxJson ajaxJson = new AjaxJson();
        try {
            String type = request.getParameter("type");      //1.游客 2.会员
            String uid = request.getParameter("uid");      //会员ID，登陆后返回
            String time = request.getParameter("time");        //时间，登陆后返回
            String key = request.getParameter("key");       //密钥，登陆后返回
            String languageid = request.getParameter("languageid");       //语言id
            String commentid = request.getParameter("commentid");       //评论id
            String pageNo = request.getParameter("pageNo");//页码


            if (!StringUtils.isBlank(type) && Integer.parseInt(type) == 2) {
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
            }
            if (StringUtils.isBlank(languageid)) {
                ajaxJson.setSuccess(false);
                ajaxJson.setErrorCode(AppErrorUtils.error_2);
                ajaxJson.setMsg("languageid传参为空");
                return ajaxJson;
            }
            if (StringUtils.isBlank(commentid)) {
                ajaxJson.setSuccess(false);
                ajaxJson.setErrorCode(AppErrorUtils.error_2);
                ajaxJson.setMsg("commentid传参为空");
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
			
            ComComment comComment = new ComComment();
            comComment.setFatherId(Integer.parseInt(commentid));

            Page<ComComment> page = new Page<ComComment>();
            page.setPageNo(Integer.parseInt(pageNo));
            page.setPageSize1(pageSize);

            Page<ComComment> page1 = comCommentService.selectChildComment(page, comComment);

            List<ComComment> list = page1.getList();
            if(list!=null&&list.size()>0){
    			for(ComComment a:list){
    				if(StringUtils.isBlank(a.getMemberPhoto())){
    					ComProtocol cp1 = new ComProtocol();
    	            	cp1.setLanguageid(Integer.parseInt(languageid));
    	            	ComProtocol cp = comProtocolService.getProtocol(cp1);
    	            	if(cp!=null&&cp.getDefaultPhoto()!=null){
    	            		a.setMemberPhoto(cp.getDefaultPhoto());
    	            	}
    				}
    			}
    		}
            
            int totalPage = page1.getTotalPage();
            if (Integer.parseInt(pageNo) > totalPage) {
                ajaxJson.getBody().put("list", new ArrayList<>());
            } else {
                ajaxJson.getBody().put("list", list);
            }
            ajaxJson.getBody().put("totalPage", totalPage);
            ajaxJson.setSuccess(true);
            ajaxJson.setErrorCode("0");
            ajaxJson.setMsg("获取产品评论子评论成功");
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
     * 添加产品评论接口
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "addComment")
    @ResponseBody
    public AjaxJson addComment(HttpServletRequest request, HttpServletResponse response, Model model) {

        AjaxJson ajaxJson = new AjaxJson();
        try {
            String type = request.getParameter("type");      //1.游客 2.会员
            String uid = request.getParameter("uid");      //会员ID，登陆后返回
            String time = request.getParameter("time");        //时间，登陆后返回
            String key = request.getParameter("key");       //密钥，登陆后返回
            String languageid = request.getParameter("languageid");       //语言id
            //评论类型1.包车租车2.短程接送3.接送机4常规路线5.当地参团6.游轮7.景点门票8.当地玩家9.酒店10.保险11.旅游定制13.商务定制14.商务旅游定制15定制租车 16.攻略评论 17.城市评论',
            String contentType = request.getParameter("contentType"); 
            String typeId = request.getParameter("typeId");       //评论产品id
            String content = request.getParameter("content");//内容
            String level = request.getParameter("level");//评论星级
            String scenicSpot = request.getParameter("scenicSpot");//游玩的景点（城市评论存在）

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
            if (StringUtils.isBlank(contentType)) {
                ajaxJson.setSuccess(false);
                ajaxJson.setErrorCode(AppErrorUtils.error_2);
                ajaxJson.setMsg("contentType传参为空");
                return ajaxJson;
            }
            if (StringUtils.isBlank(content)) {
                ajaxJson.setSuccess(false);
                ajaxJson.setErrorCode(AppErrorUtils.error_2);
                ajaxJson.setMsg("content传参为空");
                return ajaxJson;
            }
            if (StringUtils.isBlank(typeId)) {
                ajaxJson.setSuccess(false);
                ajaxJson.setErrorCode(AppErrorUtils.error_2);
                ajaxJson.setMsg("typeId传参为空");
                return ajaxJson;
            }
            if (Integer.parseInt(contentType)!=16&&StringUtils.isBlank(level)) {
                ajaxJson.setSuccess(false);
                ajaxJson.setErrorCode(AppErrorUtils.error_2);
                ajaxJson.setMsg("level传参为空");
                return ajaxJson;
            }


            ComComment comComment = new ComComment();
            comComment.setFatherId(0);   //'父评论id',
            comComment.setContent(content);    //'评论内容',
            comComment.setMemberId(Integer.parseInt(uid));    //'评论人id',
            comComment.setStatus(1);    // '状态  1启用0禁用',
          //'订单类型：1.包车租车2.短程接送3.接送机4常规路线5.当地参团6.游轮7.景点门票8.当地玩家9.酒店10.保险11.旅游定制 13.攻略评论 14.城市评论15定制租车',
            if(Integer.parseInt(contentType)==11 || Integer.parseInt(contentType)==13 ||Integer.parseInt(contentType)==14){
            	comComment.setType(11); 
            	 comComment.setTypeId(Integer.parseInt(typeId));    //定制没有实际产品存orderSys子订单id
            }else if(Integer.parseInt(contentType)==16){
            	comComment.setType(13);
            	comComment.setTypeId(Integer.parseInt(typeId));    //产品id
            }else if(Integer.parseInt(contentType)==17){
            	comComment.setType(14);
            	comComment.setTypeId(Integer.parseInt(typeId));    //产品id
            }else{
            	 comComment.setType(Integer.parseInt(contentType));
            	 //订单评论 typeId为orderSys子订单id
            	 OrderSys os = orderSysService.get(typeId);
            	 if(os.getType()==1 || os.getType()==2 || os.getType()==3 || os.getType()==15 ){
            		 Integer carType;
            		 if(os.getType()==1 || os.getType()==2 || os.getType()==3){
            			 carType= os.getType();
            		 }else{
            			 carType = 4;
            		 } 
            		 OrderCar oc = orderCarService.findCarInfoByOrderSys2(carType, Integer.parseInt(typeId));
            		 comComment.setTypeId(Integer.parseInt(oc.getCarid()));    //产品id
            	 }else if(os.getType()==4 || os.getType()==5){
            		 OrderRoute route =orderRouteService.findRouteDetailByOrderSys2(Integer.parseInt(typeId));
                     comComment.setTypeId(route.getRouteid());    //产品id
            	 }else if(os.getType()==6){
            		 OrderLiner ol = orderLinerService.findLinerDetailByOrderSys2(Integer.parseInt(typeId));
                     comComment.setTypeId(ol.getLinerLineId());    //产品id 
            	 }else if(os.getType()==7){
            		 OrderScenicSpot oss = orderScenicSpotService.findScenicSpotDetailByOrderid(Integer.parseInt(typeId));
            		 comComment.setTypeId(oss.getSecnicSpotId());    //产品id
            	 }else if(os.getType()==8){
            		 OrderGuide og = orderGuideService.findGuideDetailByOrderSys2(Integer.parseInt(typeId));
                     comComment.setTypeId(og.getTypeid());    //产品id
            	 }else if(os.getType()==9){
            		 OrderHotel oh =orderHotelService.findhotelDetailByOrderSys2(Integer.parseInt(typeId));
                     comComment.setTypeId(oh.getHotelId());    //产品id
            	 }else if(os.getType()==10){
            		 OrderInsurance oi = orderInsuranceService.findInsuranceByOrderSys2(Integer.parseInt(typeId));
                     comComment.setTypeId(oi.getInsuranceid());    //产品id
            	 }
            }
            comComment.setLanguageId(Integer.parseInt(languageid));   // '语言id',
            if (StringUtils.isNotBlank(level)) {
            	 comComment.setLevel(Integer.parseInt(level));   //'评论星级',
            }
            comComment.setScenicSpot(scenicSpot);  //游玩景点（城市评论存在）
            comCommentService.save(comComment,typeId);

            ajaxJson.setSuccess(true);
            ajaxJson.setErrorCode("0");
            ajaxJson.setMsg("添加产品评论成功");
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
     * 添加产品评论子评论接口
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "addChildComment")
    @ResponseBody
    public AjaxJson addChildComment(HttpServletRequest request, HttpServletResponse response, Model model) {

        AjaxJson ajaxJson = new AjaxJson();
        try {
            String type = request.getParameter("type");      //1.游客 2.会员
            String uid = request.getParameter("uid");      //会员ID，登陆后返回
            String time = request.getParameter("time");        //时间，登陆后返回
            String key = request.getParameter("key");       //密钥，登陆后返回
            String languageid = request.getParameter("languageid");       //语言id
            String commentid = request.getParameter("commentid");       //评论id
            String content = request.getParameter("content");//内容

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
            if (StringUtils.isBlank(commentid)) {
                ajaxJson.setSuccess(false);
                ajaxJson.setErrorCode(AppErrorUtils.error_2);
                ajaxJson.setMsg("commentid传参为空");
                return ajaxJson;
            }
            if (StringUtils.isBlank(content)) {
                ajaxJson.setSuccess(false);
                ajaxJson.setErrorCode(AppErrorUtils.error_2);
                ajaxJson.setMsg("content传参为空");
                return ajaxJson;
            }

            ComComment comComment1 =  comCommentService.get(commentid);
            ComComment comComment = new ComComment();
            comComment.setFatherId(Integer.parseInt(commentid));
            comComment.setLanguageId(Integer.parseInt(languageid));
            comComment.setContent(content);
            comComment.setMemberId(Integer.parseInt(uid));
            comComment.setStatus(1);
            comComment.setType(comComment1.getType());
            comCommentService.save1(comComment);

            ajaxJson.setSuccess(true);
            ajaxJson.setErrorCode("0");
            ajaxJson.setMsg("添加产品评论子评论成功");
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
     * 根据城市获取景点接口
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "getScenicByCity")
    @ResponseBody
    public AjaxJson getScenicByCity(HttpServletRequest request, HttpServletResponse response, Model model) {

        AjaxJson ajaxJson = new AjaxJson();
        try {
            String type = request.getParameter("type");      //1.游客 2.会员
            String uid = request.getParameter("uid");      //会员ID，登陆后返回
            String time = request.getParameter("time");        //时间，登陆后返回
            String key = request.getParameter("key");       //密钥，登陆后返回
            String languageid = request.getParameter("languageid");       //语言id
            String cityid = request.getParameter("cityid");       //城市id


            if (!StringUtils.isBlank(type) && Integer.parseInt(type) == 2) {
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
            }
            if (StringUtils.isBlank(languageid)) {
                ajaxJson.setSuccess(false);
                ajaxJson.setErrorCode(AppErrorUtils.error_2);
                ajaxJson.setMsg("languageid传参为空");
                return ajaxJson;
            }
//            if (StringUtils.isBlank(cityid)) {
//                ajaxJson.setSuccess(false);
//                ajaxJson.setErrorCode(AppErrorUtils.error_2);
//                ajaxJson.setMsg("cityid传参为空");
//                return ajaxJson;
//            }
            ScenicSpot scenicSpot = new ScenicSpot();
            if(StringUtils.isNotBlank(cityid)){
                scenicSpot.setCityId(Integer.parseInt(cityid));
            }
            scenicSpot.setLanguageId(Integer.parseInt(languageid));
            List<ScenicSpot> list = scenicSpotService.getCityScenic(scenicSpot);
            if (list != null && list.size() > 0) {
                ajaxJson.getBody().put("list", list);
            } else {
                ajaxJson.getBody().put("list", new ArrayList<>());
            }
            ajaxJson.setSuccess(true);
            ajaxJson.setErrorCode("0");
            ajaxJson.setMsg("根据城市获取景点成功");
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
     * 根据语言获取酒店接口
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "getHotelByLanguage")
    @ResponseBody
    public AjaxJson getHotelByLanguage(HttpServletRequest request, HttpServletResponse response, Model model) {

        AjaxJson ajaxJson = new AjaxJson();
        try {
            String languageid = request.getParameter("languageid");       //语言id
            String name = request.getParameter("name");       //名称
            String pageNo = request.getParameter("pageNo");       //页码

            
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
            String pageSize1 = request.getParameter("pageSize");       //每页数
			Integer pageSize;
			if(StringUtils.isBlank(pageSize1)){
				pageSize = appUtils.getPageSize();
			}else{
				pageSize = Integer.parseInt(pageSize1);
			}
			
            Hotel hotel = new Hotel();
            hotel.setLanguageId(Integer.parseInt(languageid));
            hotel.setName(name);
            Page<Hotel> page = new Page<Hotel>();
            page.setPageNo(Integer.parseInt(pageNo));
            page.setPageSize1(pageSize);

            Page<Hotel> page1 = hotelService.getHotelByLanguage(page, hotel);

            List<Hotel> list = page1.getList();
            for(Hotel h : list){
                if(StringUtils.isNotBlank(h.getImgUrl())){
                    h.setImgUrl(h.getImgUrl().split(",")[0]);
                }
            }
            int totalPage = page1.getTotalPage();
            if (Integer.parseInt(pageNo) > totalPage) {
                ajaxJson.getBody().put("list", new ArrayList<>());
            } else {
                ajaxJson.getBody().put("list", list);
            }
            ajaxJson.getBody().put("totalPage", totalPage);
          
            ajaxJson.setSuccess(true);
            ajaxJson.setErrorCode("0");
            ajaxJson.setMsg("根据语言获取酒店成功");
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
     * 根据城市及搜索条件获取酒店接口
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "getHotelByCity")
    @ResponseBody
    public AjaxJson getHotelByCity(HttpServletRequest request, HttpServletResponse response, Model model) {

        AjaxJson ajaxJson = new AjaxJson();
        try {
            String type = request.getParameter("type");      //1.游客 2.会员
            String uid = request.getParameter("uid");      //会员ID，登陆后返回
            String time = request.getParameter("time");        //时间，登陆后返回
            String key = request.getParameter("key");       //密钥，登陆后返回
            String languageid = request.getParameter("languageid");       //语言id
            String cityid = request.getParameter("cityid");       //城市id
            String pageNo = request.getParameter("pageNo");       //页码
            String date = request.getParameter("date");       //日期

            String stars = request.getParameter("stars");       //酒店星级，多个用，隔开
            String oneNum = request.getParameter("oneNum");       //单人间
            String twoNum = request.getParameter("twoNum");       //双人间
            String threeNum = request.getParameter("threeNum");       //三人间
            String fourNum = request.getParameter("fourNum");       //四人间

            if (!StringUtils.isBlank(type) && Integer.parseInt(type) == 2) {
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
            }
            if (StringUtils.isBlank(languageid)) {
                ajaxJson.setSuccess(false);
                ajaxJson.setErrorCode(AppErrorUtils.error_2);
                ajaxJson.setMsg("languageid传参为空");
                return ajaxJson;
            }
            if (StringUtils.isBlank(cityid)) {
                ajaxJson.setSuccess(false);
                ajaxJson.setErrorCode(AppErrorUtils.error_2);
                ajaxJson.setMsg("cityid传参为空");
                return ajaxJson;
            }
            if (StringUtils.isBlank(date)) {
                ajaxJson.setSuccess(false);
                ajaxJson.setErrorCode(AppErrorUtils.error_2);
                ajaxJson.setMsg("date传参为空");
                return ajaxJson;
            }
            if (StringUtils.isBlank(pageNo)) {
                ajaxJson.setSuccess(false);
                ajaxJson.setErrorCode(AppErrorUtils.error_2);
                ajaxJson.setMsg("pageNo传参为空");
                return ajaxJson;
            }
            if (StringUtils.isBlank(oneNum)) {
                ajaxJson.setSuccess(false);
                ajaxJson.setErrorCode(AppErrorUtils.error_2);
                ajaxJson.setMsg("oneNum传参为空");
                return ajaxJson;
            }
            if (StringUtils.isBlank(twoNum)) {
                ajaxJson.setSuccess(false);
                ajaxJson.setErrorCode(AppErrorUtils.error_2);
                ajaxJson.setMsg("twoNum传参为空");
                return ajaxJson;
            }
            if (StringUtils.isBlank(threeNum)) {
                ajaxJson.setSuccess(false);
                ajaxJson.setErrorCode(AppErrorUtils.error_2);
                ajaxJson.setMsg("threeNum传参为空");
                return ajaxJson;
            }
            if (StringUtils.isBlank(fourNum)) {
                ajaxJson.setSuccess(false);
                ajaxJson.setErrorCode(AppErrorUtils.error_2);
                ajaxJson.setMsg("fourNum传参为空");
                return ajaxJson;
            }
            String pageSize1 = request.getParameter("pageSize");       //每页数
			Integer pageSize;
			if(StringUtils.isBlank(pageSize1)){
				pageSize = appUtils.getPageSize();
			}else{
				pageSize = Integer.parseInt(pageSize1);
			}
			
            Hotel hotel = new Hotel();
            hotel.setLanguageId(Integer.parseInt(languageid));
            hotel.setCityId(Integer.parseInt(cityid));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date1 = sdf.parse(date);
            hotel.setDate(date1);
            hotel.setStars(stars);
            hotel.setOneNum(Integer.parseInt(oneNum));
            hotel.setTwoNum(Integer.parseInt(twoNum));
            hotel.setThreeNum(Integer.parseInt(threeNum));
            hotel.setFourNum(Integer.parseInt(fourNum));
            Page<Hotel> page = new Page<Hotel>();
            page.setPageNo(Integer.parseInt(pageNo));
            page.setPageSize1(pageSize);

            Page<Hotel> page1 = hotelService.getHotelByCity(page, hotel);

            List<Hotel> list = page1.getList();
            int totalPage = page1.getTotalPage();
            if (Integer.parseInt(pageNo) > totalPage) {
                ajaxJson.getBody().put("list", new ArrayList<>());
            } else {
                ajaxJson.getBody().put("list", list);
            }
            ajaxJson.getBody().put("totalPage", totalPage);

            ajaxJson.setSuccess(true);
            ajaxJson.setErrorCode("0");
            ajaxJson.setMsg("根据城市及搜索条件获取酒店成功");
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
     * 获取酒店房间接口
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "getHotelRoom")
    @ResponseBody
    public AjaxJson getHotelRoom(HttpServletRequest request, HttpServletResponse response, Model model) {

        AjaxJson ajaxJson = new AjaxJson();
        try {
            String type = request.getParameter("type");      //1.游客 2.会员
            String uid = request.getParameter("uid");      //会员ID，登陆后返回
            String time = request.getParameter("time");        //时间，登陆后返回
            String key = request.getParameter("key");       //密钥，登陆后返回
            String languageid = request.getParameter("languageid");       //语言id
            String hotelid = request.getParameter("hotelid");       //城市id
            String date = request.getParameter("date");       //日期
            String pageNo = request.getParameter("pageNo");       //日期

            if (!StringUtils.isBlank(type) && Integer.parseInt(type) == 2) {
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
            }
            if (StringUtils.isBlank(languageid)) {
                ajaxJson.setSuccess(false);
                ajaxJson.setErrorCode(AppErrorUtils.error_2);
                ajaxJson.setMsg("languageid传参为空");
                return ajaxJson;
            }
            if (StringUtils.isBlank(hotelid)) {
                ajaxJson.setSuccess(false);
                ajaxJson.setErrorCode(AppErrorUtils.error_2);
                ajaxJson.setMsg("hotelid传参为空");
                return ajaxJson;
            }
            if (StringUtils.isBlank(date)) {
                ajaxJson.setSuccess(false);
                ajaxJson.setErrorCode(AppErrorUtils.error_2);
                ajaxJson.setMsg("date传参为空");
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
            
            Page<HotelRoom> page = new Page<HotelRoom>();
            HotelRoom hotelRoom = new HotelRoom();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date1 = sdf.parse(date);
            hotelRoom.setDate(date1);
            hotelRoom.setHotelId(Integer.parseInt(hotelid));
            page.setPageNo(Integer.parseInt(pageNo));
            page.setPageSize1(pageSize);
            Page<HotelRoom> hotelRoomPage = hotelRoomService.getHotelRoom(page, hotelRoom);
            List<HotelRoom> list = hotelRoomPage.getList();
            Integer totalPage = hotelRoomPage.getTotalPage();
            if (Integer.parseInt(pageNo) > totalPage) {
                ajaxJson.getBody().put("list", new ArrayList<>());
            } else {
                ajaxJson.getBody().put("list", list);
            }
            ajaxJson.getBody().put("totalPage", totalPage);

            ajaxJson.setSuccess(true);
            ajaxJson.setErrorCode("0");
            ajaxJson.setMsg("获取酒店房间成功");
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
     * 获取酒店房间详情接口
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "getHotelRoomDetails")
    @ResponseBody
    public AjaxJson getHotelRoomDetails(HttpServletRequest request, HttpServletResponse response, Model model) {

        AjaxJson ajaxJson = new AjaxJson();
        try {
            String type = request.getParameter("type");      //1.游客 2.会员
            String uid = request.getParameter("uid");      //会员ID，登陆后返回
            String time = request.getParameter("time");        //时间，登陆后返回
            String key = request.getParameter("key");       //密钥，登陆后返回
            String languageid = request.getParameter("languageid");       //语言id
            String hotelRoomid = request.getParameter("hotelRoomid");       //酒店房间id

            if (!StringUtils.isBlank(type) && Integer.parseInt(type) == 2) {
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
            }
            if (StringUtils.isBlank(languageid)) {
                ajaxJson.setSuccess(false);
                ajaxJson.setErrorCode(AppErrorUtils.error_2);
                ajaxJson.setMsg("languageid传参为空");
                return ajaxJson;
            }
            if (StringUtils.isBlank(hotelRoomid)) {
                ajaxJson.setSuccess(false);
                ajaxJson.setErrorCode(AppErrorUtils.error_2);
                ajaxJson.setMsg("hotelRoomid传参为空");
                return ajaxJson;
            }

            HotelRoom hotelRoom1 = new HotelRoom();
            hotelRoom1.setId(hotelRoomid);
            HotelRoom hotelRoom = hotelRoomService.getRoomDetails(hotelRoom1);

            ajaxJson.getBody().put("hotelRoom", hotelRoom);
            ajaxJson.setSuccess(true);
            ajaxJson.setErrorCode("0");
            ajaxJson.setMsg("获取酒店房间详情成功");
            return ajaxJson;
        } catch (Exception e) {
            e.printStackTrace();
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_3);
            ajaxJson.setMsg("获取酒店房间详情失败");
            return ajaxJson;
        }
    }

    /**
     * 根据城市及搜索条件获取定制汽车接口
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "getCarByCity")
    @ResponseBody
    public AjaxJson getCarByCity(HttpServletRequest request, HttpServletResponse response, Model model) {

        AjaxJson ajaxJson = new AjaxJson();
        try {
            String type = request.getParameter("type");      //1.游客 2.会员
            String uid = request.getParameter("uid");      //会员ID，登陆后返回
            String time = request.getParameter("time");        //时间，登陆后返回
            String key = request.getParameter("key");       //密钥，登陆后返回
            String languageid = request.getParameter("languageid");       //语言id
            String cityid = request.getParameter("cityid");       //城市id
            String pageNo = request.getParameter("pageNo");       //页码
            String carType = request.getParameter("carType");       //业务类型1.包车租车2.短程接送3.接送机4.定制
            String date = request.getParameter("startDate");       //日期
            String comfort = request.getParameter("comfort");       //车辆等级
            String agentid = request.getParameter("agentid");       //租车公司id
            String seatNum = request.getParameter("seatNum");       //座位数
            if (!StringUtils.isBlank(type) && Integer.parseInt(type) == 2) {
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
            }
            if (StringUtils.isBlank(languageid)) {
                ajaxJson.setSuccess(false);
                ajaxJson.setErrorCode(AppErrorUtils.error_2);
                ajaxJson.setMsg("languageid传参为空");
                return ajaxJson;
            }
            if (StringUtils.isBlank(cityid)) {
                ajaxJson.setSuccess(false);
                ajaxJson.setErrorCode(AppErrorUtils.error_2);
                ajaxJson.setMsg("cityid传参为空");
                return ajaxJson;
            }
            if (StringUtils.isBlank(date)) {
                ajaxJson.setSuccess(false);
                ajaxJson.setErrorCode(AppErrorUtils.error_2);
                ajaxJson.setMsg("date传参为空");
                return ajaxJson;
            }
            if (StringUtils.isBlank(pageNo)) {
                ajaxJson.setSuccess(false);
                ajaxJson.setErrorCode(AppErrorUtils.error_2);
                ajaxJson.setMsg("pageNo传参为空");
                return ajaxJson;
            }
            if (StringUtils.isBlank(carType)) {
                ajaxJson.setSuccess(false);
                ajaxJson.setErrorCode(AppErrorUtils.error_2);
                ajaxJson.setMsg("carType传参为空");
                return ajaxJson;
            }
            String pageSize1 = request.getParameter("pageSize");       //每页数
			Integer pageSize;
			if(StringUtils.isBlank(pageSize1)){
				pageSize = appUtils.getPageSize();
			}else{
				pageSize = Integer.parseInt(pageSize1);
			}
			
            CarInfo car = new CarInfo();
            car.setLanguage(Integer.parseInt(languageid));
            car.setCity(Integer.parseInt(cityid));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date1 = sdf.parse(date);
            car.setDate(date1);
            if (StringUtils.isNotBlank(comfort)) {
                car.setComfort(Integer.parseInt(comfort));
            }
            if (StringUtils.isNotBlank(seatNum)) {
                car.setSeatNum(Integer.parseInt(seatNum));
            }
            if (StringUtils.isNotBlank(agentid)) {
                car.setAgentid(Integer.parseInt(agentid));
            }
            car.setCarType(carType);
            Page<CarInfo> page = new Page<CarInfo>();
            page.setPageNo(Integer.parseInt(pageNo));
            page.setPageSize1(pageSize);

            Page<CarInfo> page1 = carInfoService.getCarByCity(page, car);

            List<CarInfo> list = page1.getList();
            int totalPage = page1.getTotalPage();
            if (Integer.parseInt(pageNo) > totalPage) {
                ajaxJson.getBody().put("list", new ArrayList<>());
            } else {
                ajaxJson.getBody().put("list", list);
            }
            ajaxJson.getBody().put("totalPage", totalPage);

            ajaxJson.setSuccess(true);
            ajaxJson.setErrorCode("0");
            ajaxJson.setMsg("根据城市及搜索条件获取定制汽车成功");
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
     * @method getCarByRent
     * @Description 包车租车查询车辆列表
     * @Author 彭善智
     * @Date 2019/3/7 15:00
     */
    @RequestMapping(value = "getCarByRent")
    @ResponseBody
    public AjaxJson getCarByRent(HttpServletRequest request, HttpServletResponse response, Model model) {

        AjaxJson ajaxJson = new AjaxJson();
        try {
            String type = request.getParameter("type");      //1.游客 2.会员
            String uid = request.getParameter("uid");      //会员ID，登陆后返回
            String time = request.getParameter("time");        //时间，登陆后返回
            String key = request.getParameter("key");       //密钥，登陆后返回
            String languageid = request.getParameter("languageid");       //语言id
            String cityid = request.getParameter("cityid");       //城市id
            String pageNo = request.getParameter("pageNo");       //页码
            String serviceids = request.getParameter("serviceids");       //包车租车标题id
            String comfort = request.getParameter("comfort");       //车辆等级
            String agentid = request.getParameter("agentid");       //租车公司id
            String seatNum = request.getParameter("seatNum");       //座位数
            if (!StringUtils.isBlank(type) && Integer.parseInt(type) == 2) {
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
            }
            if (StringUtils.isBlank(languageid)) {
                ajaxJson.setSuccess(false);
                ajaxJson.setErrorCode(AppErrorUtils.error_2);
                ajaxJson.setMsg("languageid传参为空");
                return ajaxJson;
            }
            if (StringUtils.isBlank(cityid)) {
                ajaxJson.setSuccess(false);
                ajaxJson.setErrorCode(AppErrorUtils.error_2);
                ajaxJson.setMsg("cityid传参为空");
                return ajaxJson;
            }
            if (StringUtils.isBlank(serviceids)) {
                ajaxJson.setSuccess(false);
                ajaxJson.setErrorCode(AppErrorUtils.error_2);
                ajaxJson.setMsg("serviceids传参为空");
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
			
            CarInfo car = new CarInfo();
            car.setLanguage(Integer.parseInt(languageid));
            car.setCity(Integer.parseInt(cityid));
            if (StringUtils.isNotBlank(comfort)) {
                car.setComfort(Integer.parseInt(comfort));
            }
            if (StringUtils.isNotBlank(seatNum)) {
                car.setSeatNum(Integer.parseInt(seatNum));
            }
            if (StringUtils.isNotBlank(agentid)) {
                car.setAgentid(Integer.parseInt(agentid));
            }
            car.setCarType("1");
            car.setServiceids(serviceids);
            Page<CarInfo> page = new Page<CarInfo>();
            page.setPageNo(Integer.parseInt(pageNo));
            page.setPageSize1(pageSize);

            Page<CarInfo> page1 = carInfoService.getCarByRent(page, car);

            List<CarInfo> list = page1.getList();
            int totalPage = page1.getTotalPage();
            if (Integer.parseInt(pageNo) > totalPage) {
                ajaxJson.getBody().put("list", new ArrayList<>());
            } else {
                ajaxJson.getBody().put("list", list);
            }
            ajaxJson.getBody().put("totalPage", totalPage);

            ajaxJson.setSuccess(true);
            ajaxJson.setErrorCode("0");
            ajaxJson.setMsg("根据城市及搜索条件获取定制汽车成功");
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
     * 用户咨询列表接口
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "getConsult")
    @ResponseBody
    public AjaxJson getConsult(HttpServletRequest request, HttpServletResponse response, Model model) {

        AjaxJson ajaxJson = new AjaxJson();
        try {
            String type = request.getParameter("type");      //1.游客 2.会员
            String uid = request.getParameter("uid");      //会员ID，登陆后返回
            String time = request.getParameter("time");        //时间，登陆后返回
            String key = request.getParameter("key");       //密钥，登陆后返回
            String languageid = request.getParameter("languageid");       //语言id
            String typeid = request.getParameter("typeid");       //id
            String proType = request.getParameter("proType");
            //1.包车租车2.短程接送3.接送机4常规路线5.当地参团6.游轮7.景点门票8.当地玩家9.酒店10.保险11.旅游定制12导游',
            String pageNo = request.getParameter("pageNo");//页码
            if (StringUtils.isBlank(pageNo)) {
                ajaxJson.setSuccess(false);
                ajaxJson.setErrorCode(AppErrorUtils.error_2);
                ajaxJson.setMsg("pageNo传参为空");
                return ajaxJson;
            }

            if (!StringUtils.isBlank(type) && Integer.parseInt(type) == 2) {
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
            }
            if (StringUtils.isBlank(languageid)) {
                ajaxJson.setSuccess(false);
                ajaxJson.setErrorCode(AppErrorUtils.error_2);
                ajaxJson.setMsg("languageid传参为空");
                return ajaxJson;
            }
            if (StringUtils.isBlank(typeid)) {
                ajaxJson.setSuccess(false);
                ajaxJson.setErrorCode(AppErrorUtils.error_2);
                ajaxJson.setMsg("typeid传参为空");
                return ajaxJson;
            }
            if (StringUtils.isBlank(proType)) {
                ajaxJson.setSuccess(false);
                ajaxJson.setErrorCode(AppErrorUtils.error_2);
                ajaxJson.setMsg("proType传参为空");
                return ajaxJson;
            }
            String pageSize1 = request.getParameter("pageSize");       //每页数
			Integer pageSize;
			if(StringUtils.isBlank(pageSize1)){
				pageSize = appUtils.getPageSize();
			}else{
				pageSize = Integer.parseInt(pageSize1);
			}
			
            ComConsult comConsult = new ComConsult();
            comConsult.setType(Integer.parseInt(proType));
            comConsult.setTypeId(Integer.parseInt(typeid));

            Page<ComConsult> page = new Page<ComConsult>();
            page.setPageNo(Integer.parseInt(pageNo));
            page.setPageSize1(pageSize);

            Page<ComConsult> page1 = comConsultService.getRouteConsult(page, comConsult);

            List<ComConsult> list = page1.getList();
            int totalPage = page1.getTotalPage();
            if (Integer.parseInt(pageNo) > totalPage) {
                ajaxJson.getBody().put("list", new ArrayList<>());
            } else {
                ajaxJson.getBody().put("list", list);
            }
            ajaxJson.getBody().put("totalPage", totalPage);


            ajaxJson.setSuccess(true);
            ajaxJson.setErrorCode("0");
            ajaxJson.setMsg("获取用户咨询列表成功");
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
     * 添加用户咨询接口
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "saveConsult")
    @ResponseBody
    public AjaxJson saveConsult(HttpServletRequest request, HttpServletResponse response, Model model) {

        AjaxJson ajaxJson = new AjaxJson();
        try {
            String type = request.getParameter("type");      //1.游客 2.会员
            String uid = request.getParameter("uid");      //会员ID，登陆后返回
            String time = request.getParameter("time");        //时间，登陆后返回
            String key = request.getParameter("key");       //密钥，登陆后返回
            String languageid = request.getParameter("languageid");       //语言id
            String typeid = request.getParameter("typeid");       //id
            String proType = request.getParameter("proType");
            //1.包车租车2.短程接送3.接送机4常规路线5.当地参团6.游轮7.景点门票8.当地玩家9.酒店10.保险11.旅游定制12导游',

            String content = request.getParameter("content");//咨询内容
            String name = request.getParameter("name");//姓名
            String mobile = request.getParameter("mobile");//电话
            if (StringUtils.isBlank(content)) {
                ajaxJson.setSuccess(false);
                ajaxJson.setErrorCode(AppErrorUtils.error_2);
                ajaxJson.setMsg("content传参为空");
                return ajaxJson;
            }
            if (StringUtils.isBlank(name)) {
                ajaxJson.setSuccess(false);
                ajaxJson.setErrorCode(AppErrorUtils.error_2);
                ajaxJson.setMsg("name传参为空");
                return ajaxJson;
            }

            if (StringUtils.isBlank(mobile)) {
                ajaxJson.setSuccess(false);
                ajaxJson.setErrorCode(AppErrorUtils.error_2);
                ajaxJson.setMsg("mobile传参为空");
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
            if (StringUtils.isBlank(typeid)) {
                ajaxJson.setSuccess(false);
                ajaxJson.setErrorCode(AppErrorUtils.error_2);
                ajaxJson.setMsg("typeid传参为空");
                return ajaxJson;
            }
            if (StringUtils.isBlank(proType)) {
                ajaxJson.setSuccess(false);
                ajaxJson.setErrorCode(AppErrorUtils.error_2);
                ajaxJson.setMsg("proType传参为空");
                return ajaxJson;
            }
            ComConsult comConsult = new ComConsult();
            comConsult.setType(Integer.parseInt(proType));
            comConsult.setTypeId(Integer.parseInt(typeid));
            comConsult.setContent(content);
            comConsult.setMemberid(Integer.parseInt(uid));
            comConsult.setStatus(1);        // 状态  1正常0禁用
            comConsult.setLanguageId(Integer.parseInt(languageid));        // 语言id
            comConsult.setName(name);        // 姓名
            comConsult.setMoblie(mobile);        // 电话

            comConsultService.save(comConsult);
            ajaxJson.setSuccess(true);
            ajaxJson.setErrorCode("0");
            ajaxJson.setMsg("添加用户咨询成功");
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
     * 获取常用联系人接口
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "getMemberContacts")
    @ResponseBody
    public AjaxJson getMemberContacts(HttpServletRequest request, HttpServletResponse response, Model model) {

        AjaxJson ajaxJson = new AjaxJson();
        try {
            String type = request.getParameter("type");      //1.游客 2.会员
            String uid = request.getParameter("uid");      //会员ID，登陆后返回
            String time = request.getParameter("time");        //时间，登陆后返回
            String key = request.getParameter("key");       //密钥，登陆后返回
            String languageid = request.getParameter("languageid");       //语言id


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

            MemberContacts memberContacts = new MemberContacts();
            memberContacts.setMemberid(Integer.parseInt(uid));        // 用户id

            List<MemberContacts> list = memberContactsService.getMemberContacts(memberContacts);

            if (list != null && list.size() > 0) {
                ajaxJson.getBody().put("list", list);
            } else {
                ajaxJson.getBody().put("list", new ArrayList<>());
            }

            ajaxJson.setSuccess(true);
            ajaxJson.setErrorCode("0");
            ajaxJson.setMsg("获取用户咨询列表成功");
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
     * 根据搜索条件获取导游列表接口
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "getGuideByCity")
    @ResponseBody
    public AjaxJson getGuideByCity(HttpServletRequest request, HttpServletResponse response, Model model) {

        AjaxJson ajaxJson = new AjaxJson();
        try {
            String type = request.getParameter("type");      //1.游客 2.会员
            String uid = request.getParameter("uid");      //会员ID，登陆后返回
            String time = request.getParameter("time");        //时间，登陆后返回
            String key = request.getParameter("key");       //密钥，登陆后返回
            String languageid = request.getParameter("languageid");       //语言id
            String cityid = request.getParameter("cityid");       //城市id
            String pageNo = request.getParameter("pageNo");       //页码
            String date = request.getParameter("startDate");       //日期
            //导游类型1.当地玩家2.定制旅游-导游3.定制旅游-司兼导4.包车/租车-导游5.包车/租车-司兼导
            String guideType = request.getParameter("guideType");
            String guideAge = request.getParameter("guideAge");       //“”不限 年龄段(1980)多个用，隔开
            String guideSex = request.getParameter("guideSex");       //“”不限  1.男2.女多个用，隔开
            String minPrice = request.getParameter("minPrice");       //最低价格
            String maxPrice = request.getParameter("maxPrice");       //最高价格
            String tagId = request.getParameter("tagId");       //“”不限  擅长多个，隔开


            if (!StringUtils.isBlank(type) && Integer.parseInt(type) == 2) {
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
            }
            if (StringUtils.isBlank(languageid)) {
                ajaxJson.setSuccess(false);
                ajaxJson.setErrorCode(AppErrorUtils.error_2);
                ajaxJson.setMsg("languageid传参为空");
                return ajaxJson;
            }
            if (StringUtils.isBlank(cityid)) {
                ajaxJson.setSuccess(false);
                ajaxJson.setErrorCode(AppErrorUtils.error_2);
                ajaxJson.setMsg("cityid传参为空");
                return ajaxJson;
            }
            if (StringUtils.isBlank(date)) {
                ajaxJson.setSuccess(false);
                ajaxJson.setErrorCode(AppErrorUtils.error_2);
                ajaxJson.setMsg("date传参为空");
                return ajaxJson;
            }
            if (StringUtils.isBlank(guideType)) {
                ajaxJson.setSuccess(false);
                ajaxJson.setErrorCode(AppErrorUtils.error_2);
                ajaxJson.setMsg("guideType传参为空");
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
			
            Guide guide = new Guide();
            guide.setCityid(Integer.parseInt(cityid));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date1 = sdf.parse(date);
            guide.setDate(date1);
            List<String> tagIds = new ArrayList<>();
            if (StringUtils.isNotBlank(tagId)) {
                String[] a = tagId.split(",");
                for (String b : a) {
                    tagIds.add(b);
                }
                guide.setTagIds(tagIds);
            }
            guide.setGuideType(guideType);
            guide.setGuideAge(guideAge);
            guide.setGuideSex(guideSex);
            guide.setMinPrice(minPrice);
            guide.setMaxPrice(maxPrice);

            Page<Guide> page = new Page<Guide>();
            page.setPageNo(Integer.parseInt(pageNo));
            page.setPageSize1(pageSize);

            Page<Guide> page1 = guideService.getGuideByCity(page, guide);

            List<Guide> list = page1.getList();
            int totalPage = page1.getTotalPage();
            if (Integer.parseInt(pageNo) > totalPage) {
                ajaxJson.getBody().put("list", new ArrayList<>());
            } else {
                ajaxJson.getBody().put("list", list);
            }
            ajaxJson.getBody().put("totalPage", totalPage);

            ajaxJson.setSuccess(true);
            ajaxJson.setErrorCode("0");
            ajaxJson.setMsg("根据搜索条件获取导游列表成功");
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
     * 根据语言获取基本参数
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "getProtocol")
    @ResponseBody
    public AjaxJson getProtocol(HttpServletRequest request, HttpServletResponse response, Model model) {

        AjaxJson ajaxJson = new AjaxJson();
        try {
            String languageid = request.getParameter("languageid");       //语言id

            if (StringUtils.isBlank(languageid)) {
                ajaxJson.setSuccess(false);
                ajaxJson.setErrorCode(AppErrorUtils.error_2);
                ajaxJson.setMsg("languageid传参为空");
                return ajaxJson;
            }

            ComProtocol comProtocol = new ComProtocol();
            comProtocol.setLanguageid(Integer.parseInt(languageid));

            ComProtocol comProtocol1 = comProtocolService.getProtocol(comProtocol);

            ajaxJson.getBody().put("comProtocol", comProtocol1);


            ajaxJson.setSuccess(true);
            ajaxJson.setErrorCode("0");
            ajaxJson.setMsg("获取基本参数成功");
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
     * 获取语言
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "getLanguage")
    @ResponseBody
    public AjaxJson getLanguage(HttpServletRequest request, HttpServletResponse response, Model model) {

        AjaxJson ajaxJson = new AjaxJson();
        try {

            List<ComLanguage> list = comLanguageService.getLanguage();

            if (list != null && list.size() > 0) {
                ajaxJson.getBody().put("list", list);
            } else {
                ajaxJson.getBody().put("list", new ArrayList<>());
            }

            ajaxJson.setSuccess(true);
            ajaxJson.setErrorCode("0");
            ajaxJson.setMsg("获取语言成功");
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
     * 获取货币
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "getCurrency")
    @ResponseBody
    public AjaxJson getCurrency(HttpServletRequest request, HttpServletResponse response, Model model) {
        AjaxJson ajaxJson = new AjaxJson();
        try {
            List<ComCurrency> list = comCurrencyService.getCurrency();
            if (list != null && list.size() > 0) {
                ajaxJson.getBody().put("list", list);
            } else {
                ajaxJson.getBody().put("list", new ArrayList<>());
            }

            ajaxJson.setSuccess(true);
            ajaxJson.setErrorCode("0");
            ajaxJson.setMsg("获取货币成功");
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
     * 获取城市详情
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "getCityDetails")
    @ResponseBody
    public AjaxJson getCityDetails(HttpServletRequest request, HttpServletResponse response, Model model) {

        AjaxJson ajaxJson = new AjaxJson();
        try {
            String cityid = request.getParameter("cityid");      //城市id
            if (StringUtils.isBlank(cityid)) {
                ajaxJson.setSuccess(false);
                ajaxJson.setErrorCode(AppErrorUtils.error_2);
                ajaxJson.setMsg("cityid传参为空");
                return ajaxJson;
            }
            ComCity city = comCityService.get(cityid);
            ajaxJson.getBody().put("cityInfo", city);
            ajaxJson.setSuccess(true);
            ajaxJson.setErrorCode("0");
            ajaxJson.setMsg("获取城市详情成功");
            return ajaxJson;
        } catch (Exception e) {
            e.printStackTrace();
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_46);
            ajaxJson.setMsg(AppErrorUtils.error_46_desc);
            return ajaxJson;
        }
    }


    /**
     * @Title: getNearbyCity
     * @Description: 获取附近城市列表
     * @author 彭善智
     * @Data 2018年11月13日上午10:36:44
     */
    @RequestMapping(value = "getNearbyCity")
    @ResponseBody
    public AjaxJson getNearbyCity(HttpServletRequest request, HttpServletResponse response, Model model) {

        AjaxJson ajaxJson = new AjaxJson();
        try {
            String cityid = request.getParameter("cityid");      //城市id
            if (StringUtils.isBlank(cityid)) {
                ajaxJson.setSuccess(false);
                ajaxJson.setErrorCode(AppErrorUtils.error_2);
                ajaxJson.setMsg("cityid传参为空");
                return ajaxJson;
            }
            List<ComCity> list = comCityService.getNearbyCity(cityid);
            ajaxJson.getBody().put("list", list);
            ajaxJson.setSuccess(true);
            ajaxJson.setErrorCode("0");
            ajaxJson.setMsg("获取附近城市成功");
            return ajaxJson;
        } catch (Exception e) {
            e.printStackTrace();
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_50);
            ajaxJson.setMsg(AppErrorUtils.error_50_desc);
            return ajaxJson;
        }
    }
    /**
     * 根据语言获取国家
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "getCountry")
    @ResponseBody
    public AjaxJson getCountry(HttpServletRequest request, HttpServletResponse response, Model model) {

        AjaxJson ajaxJson = new AjaxJson();
        try {
            String languageid = request.getParameter("languageid");      //城市id
            if (StringUtils.isBlank(languageid)) {
                ajaxJson.setSuccess(false);
                ajaxJson.setErrorCode(AppErrorUtils.error_2);
                ajaxJson.setMsg("languageid传参为空");
                return ajaxJson;
            }
            
            Country country = new Country();
            country.setLanguageId(Integer.parseInt(languageid));
            List<Country> list = countryService.getListByLanguage(country);
            ajaxJson.getBody().put("list", list);
            ajaxJson.setSuccess(true);
            ajaxJson.setErrorCode("0");
            ajaxJson.setMsg("获取国家列表成功");
            return ajaxJson;
        } catch (Exception e) {
            e.printStackTrace();
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_46);
            ajaxJson.setMsg(AppErrorUtils.error_46_desc);
            return ajaxJson;
        }
    }

    /**
     * @Title: getCitySpot
     * @Description: 获取城市景点
     * @author 彭善智
     * @Data 2018年11月13日上午11:49:25
     */
    @RequestMapping(value = "getCitySpot")
    @ResponseBody
    public AjaxJson getCitySpotitySpot(HttpServletRequest request, HttpServletResponse response, Model model) {

        AjaxJson ajaxJson = new AjaxJson();
        try {
            String cityid = request.getParameter("cityid");      //城市id
            if (StringUtils.isBlank(cityid)) {
                ajaxJson.setSuccess(false);
                ajaxJson.setErrorCode(AppErrorUtils.error_2);
                ajaxJson.setMsg("cityid传参为空");
                return ajaxJson;
            }
            ScenicSpot scenicSpot = new ScenicSpot();
            scenicSpot.setCityId(Integer.parseInt(cityid));
            List<ScenicSpot> list = scenicSpotService.getCitySpot(scenicSpot);
            ajaxJson.getBody().put("list", list);
            ajaxJson.setSuccess(true);
            ajaxJson.setErrorCode("0");
            ajaxJson.setMsg("获取城市景点成功");
        } catch (Exception e) {
            e.printStackTrace();
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_51);
            ajaxJson.setMsg(AppErrorUtils.error_51_desc);
        }
        return ajaxJson;
    }

    /**
     * @method: getComNavigation
     * @Description: 获取导航栏
     * @Author: 彭善智
     * @Date: 2019/1/4 14:33
     */
    @RequestMapping(value = "getComNavigation")
    @ResponseBody
    public AjaxJson getComNavigation(HttpServletRequest request, HttpServletResponse response, Model model) {

        AjaxJson ajaxJson = new AjaxJson();
        RedisClient client=redisClientFactory.getResource();
        try {
            String languageid = request.getParameter("languageid");       //语言id
            if (StringUtils.isBlank(languageid)) {
                ajaxJson.setSuccess(false);
                ajaxJson.setErrorCode(AppErrorUtils.error_2);
                ajaxJson.setMsg("languageid传参为空");
                return ajaxJson;
            }
            
            //缓存取数据
            //List<ComNavigation> list = (List<ComNavigation>)CacheUtils.get("appHome", "navList_"+languageid);
            byte[] in = client.get(("navList_"+languageid).getBytes());
            List<ComNavigation> list = SerializeUtil.unserializeForList(in);
			
            if(list!=null&&list.size()>0){
            	ajaxJson.getBody().put("list", list);
            }else{
              ComNavigation comNavigation = new ComNavigation();
              comNavigation.setLanguageId(Integer.parseInt(languageid));
              List<ComNavigation> list1 = comNavigationService.getComNavigation(comNavigation);
              ajaxJson.getBody().put("list", list1);
            }
            
            ajaxJson.setSuccess(true);
            ajaxJson.setErrorCode("0");
            ajaxJson.setMsg("获取首页导航栏成功");
        } catch (Exception e) {
            e.printStackTrace();
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_51);
            ajaxJson.setMsg(AppErrorUtils.error_51_desc);
        }finally{
        	try {
    			client.close();
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
        }
        return ajaxJson;
    }

    /**
     * 支付订单
     *
     * @param orderId   订单id
     * @param payType   支付类型 支付方式 1支付宝 2微信支付 3银联支付 4Paypal
     * @param orderType 订单类型 1.包车租车2.短程接送3.接送机4常规路线5.当地参团6.游轮7.景点门票8.当地玩家/导游9.酒店10.保险11.旅游定制13.商务定制14.商务旅游
     * @return
     */
    @RequestMapping(value = "payOrder")
    @ResponseBody
    public AjaxJson payOrder(String payOrderNo, Integer payType, Integer orderType) {
        AjaxJson ajaxJson = new AjaxJson();
        ajaxJson.setSuccess(false);
        ajaxJson.setErrorCode("1");
        ajaxJson.setMsg("支付失败");
        
        Date payDate=new Date();
        //流水号（模拟）正式支付修改
        String payNo= CodeGenUtils.getNowDate();
        //获取要支付的订单
        OrderSys a = new OrderSys();
        a.setPayOrderNo(payOrderNo);
        List<OrderSys> list = orderSysService.getListByPayOrderNo(a);
        if(list!=null&&list.size()>0){
        	for(OrderSys orderSys:list){
        		if (orderSys != null && orderSys.getStatus() != null && orderSys.getStatus() == 1) {
               	 OrderSys orderSys1 = new OrderSys();
                    orderSys1.setId(orderSys.getId());
                    orderSys1.setStatus(2);
                    orderSys1.setPayWay(payType);
                    orderSys1.setPayDate(payDate);
                    orderSys1.setPayNo(payNo);
                    Long i = orderSysService.updateObject(orderSys1);
                    if (i > 0) {
                   	//更新子单
                        OrderSys orderSys3 = new OrderSys();
                        orderSys3.setFatherid(Integer.valueOf(orderSys.getId()));
                        orderSys3.setStatus(2);
                        orderSys3.setPayWay(payType);
                        orderSys3.setPayDate(payDate);
                        orderSys3.setPayNo(payNo);
                        orderSysService.updateObject(orderSys3);
                        //保险子单变为待出行
                        OrderSys orderSys4 = new OrderSys();
                        orderSys4.setFatherid(Integer.valueOf(orderSys.getId()));
                        orderSys4.setStatus(3);
                        orderSys4.setType(10);
                        orderSysService.updateObject(orderSys4);
                        
                   	 if (orderType == 4 || orderType == 5) {
                            //更新路线订单
                            OrderRoute orderRoute = new OrderRoute();
                            orderRoute.setOrderSys1(Integer.valueOf(orderSys.getId()));
                            orderRoute.setStatus(2);
                            orderRoute.setPayWay(payType);
                            orderRoute.setPayDate(payDate);
                            orderRoute.setPayNo(payNo);
                            orderRouteService.updateObj2(orderRoute);
       		         }else if (orderType == 6) {//游轮
       		             //更新游轮订单
       		             OrderLiner orderLiner = new OrderLiner();
       		             orderLiner.setOrderSys1(Integer.valueOf(orderSys.getId()));
       		             orderLiner.setStatus(2);
       		             orderLiner.setPayWay(payType);
       		             orderLiner.setPayDate(payDate);
       		             orderLiner.setPayNo(payNo);
       		             orderLinerService.updateObject2(orderLiner);
       		         }else if (orderType == 7) {//景点
       		             //更新景点订单
       		             OrderScenicSpot orderScenicSpot = new OrderScenicSpot();
       		             orderScenicSpot.setOrderSys1(Integer.valueOf(orderSys.getId()));
       		             orderScenicSpot.setStatus(2);
       		             orderScenicSpot.setPayWay(payType);
       		             orderScenicSpot.setPayDate(payDate);
       		             orderScenicSpot.setPayIncome(payNo);
       		             orderScenicSpotService.updateObject2(orderScenicSpot);
       		         }else if (orderType == 8) {//当地玩家/导游
       		             //更新导游订单
       		             OrderGuide orderGuide = new OrderGuide();
       		             orderGuide.setOrderSys1(Integer.valueOf(orderSys.getId()));
       		             orderGuide.setStatus(2);
       		             orderGuide.setPayWay(payType);
       		             orderGuide.setPayDate(payDate);
       		             orderGuide.setPayNo(payNo);
       		             orderGuideService.updateObject2(orderGuide);
       		         }else if (orderType == 1 || orderType == 2 || orderType == 3) {//包车租车、短程接送、接送机
       		             //更新车辆订单
       		             OrderCar orderCar = new OrderCar();
       		             orderCar.setOrderSys1(Integer.valueOf(orderSys.getId()));
       		             orderCar.setStatus(2);
       		             orderCar.setPayWay(payType);
       		             orderCar.setPayDate(payDate);
       		             orderCar.setPayNo(payNo);
       		             orderCarService.updateObject2(orderCar);
       		             if(orderType == 1){
       		             	//更新导游订单
       		                 OrderGuide orderGuide = new OrderGuide();
       		                 orderGuide.setOrderSys1(Integer.valueOf(orderSys.getId()));
       		                 orderGuide.setStatus(2);
       		                 orderGuide.setPayWay(payType);
       		                 orderGuide.setPayDate(payDate);
       		                 orderGuide.setPayNo(payNo);
       		                 orderGuideService.updateObject2(orderGuide);
       		                 //更新酒店订单
       		                 OrderHotel orderHotel = new OrderHotel();
       		                 orderHotel.setOrderSys1(Integer.valueOf(orderSys.getId()));
       		                 orderHotel.setStatus(2);
       		                 orderHotel.setPayWay(payType);
       		                 orderHotel.setPayDate(payDate);
       		                 orderHotel.setPayNo(payNo);
       		                 orderHotelService.updateObject2(orderHotel);
       		             }
       		         }else if (orderType == 11 || orderType == 13 || orderType == 14) {//定制
       		             //更新定制订单
       		             OrderTravelBusiness orderTravelBusiness = new OrderTravelBusiness();
       		             orderTravelBusiness.setOrderSys1(Integer.valueOf(orderSys.getId()));
       		             orderTravelBusiness.setStatus(2);
       		             orderTravelBusiness.setPayWay(payType);
       		             orderTravelBusiness.setPayDate(payDate);
       		             orderTravelBusiness.setPayNo(payNo);
       		             orderTravelBusinessService.updateObject2(orderTravelBusiness);
       		             //更新车辆订单
       		             OrderCar orderCar = new OrderCar();
       		             orderCar.setOrderSys1(Integer.valueOf(orderSys.getId()));
       		             orderCar.setStatus(2);
       		             orderCar.setPayWay(payType);
       		             orderCar.setPayDate(payDate);
       		             orderCar.setPayNo(payNo);
       		             orderCarService.updateObject2(orderCar);
       		         	//更新导游订单
       		             OrderGuide orderGuide = new OrderGuide();
       		             orderGuide.setOrderSys1(Integer.valueOf(orderSys.getId()));
       		             orderGuide.setStatus(2);
       		             orderGuide.setPayWay(payType);
       		             orderGuide.setPayDate(payDate);
       		             orderGuide.setPayNo(payNo);
       		             orderGuideService.updateObject2(orderGuide);
       		             //更新酒店订单
       		             OrderHotel orderHotel = new OrderHotel();
       		             orderHotel.setOrderSys1(Integer.valueOf(orderSys.getId()));
       		             orderHotel.setStatus(2);
       		             orderHotel.setPayWay(payType);
       		             orderHotel.setPayDate(payDate);
       		             orderHotel.setPayNo(payNo);
       		             orderHotelService.updateObject2(orderHotel);
       		             //更新景点订单
       		             OrderScenicSpot orderScenicSpot = new OrderScenicSpot();
       		             orderScenicSpot.setOrderSys1(Integer.valueOf(orderSys.getId()));
       		             orderScenicSpot.setStatus(2);
       		             orderScenicSpot.setPayWay(payType);
       		             orderScenicSpot.setPayDate(payDate);
       		             orderScenicSpot.setPayIncome(payNo);
       		             orderScenicSpotService.updateObject2(orderScenicSpot);
       		         }
                        //更新保险
                        OrderInsurance orderInsurance = new OrderInsurance();
                        orderInsurance.setOrderSys1(Integer.valueOf(orderSys.getId()));
                        orderInsurance.setStatus(3);
                        orderInsurance.setPayWay(payType);
                        orderInsurance.setPayDate(payDate);
                        orderInsurance.setPayNo(payNo);
                        orderInsuranceService.updateObj2(orderInsurance);
                        
       	             //流水写入
       	             Income income = new Income();
       	             income.setIncome(1);
       	             income.setAgentid(orderSys.getAgentid());
       	             income.setIncomePrice(orderSys.getPrice());
       	             income.setOrderId(Integer.valueOf(orderSys.getId()));
       	             income.setPayType(payType);
       	             income.setType(orderType);
       	             income.setNo(RandomUtils.getOrderSn());
       	             income.setStatus(1);
       	             incomeService.save(income);
       	             //会员流水写入
       	             MemberIncome memberIncome = new MemberIncome();
       	             memberIncome.setAgentid(orderSys.getAgentid());
       	             memberIncome.setIncome(2);
       	             memberIncome.setIncomePrice(orderSys.getPrice());
       	             memberIncome.setMemberid(orderSys.getMemberid());
       	             memberIncome.setNo(RandomUtils.getOrderSn());
       	             memberIncome.setType(orderType);
       	             memberIncome.setPayType(payType);
       	             memberIncome.setOrderId(Integer.valueOf(orderSys.getId()));
       	             memberIncomeService.save(memberIncome);
       	             ajaxJson.setSuccess(true);
                        ajaxJson.setErrorCode("0");
                        ajaxJson.setMsg("支付成功");
                    }
               }
        	}
        }
        
        return ajaxJson;
    }
    
    /**
     * 新闻列表接口
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "findArticle")
    @ResponseBody
    public AjaxJson findArticle(HttpServletRequest request, HttpServletResponse response, Model model) {

        AjaxJson ajaxJson = new AjaxJson();
        try {
            String languageid = request.getParameter("languageid");       //语言id
            String pageNo = request.getParameter("pageNo");//页码


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

            String pageSize1 = request.getParameter("pageSize");       //每页数
			Integer pageSize;
			if(StringUtils.isBlank(pageSize1)){
				pageSize = appUtils.getPageSize();
			}else{
				pageSize = Integer.parseInt(pageSize1);
			}
			
            ComArticle comArticle=new ComArticle();
            comArticle.setLanguageId(Integer.parseInt(languageid));


            Page<ComArticle> page = new Page<ComArticle>();
            page.setPageNo(Integer.parseInt(pageNo));
            page.setPageSize1(pageSize);

            Page<ComArticle> page1 = ComArticleService.findListByLanguageid(page,comArticle);

            List<ComArticle> list = page1.getList();
            int totalPage = page1.getTotalPage();
            if (Integer.parseInt(pageNo) > totalPage) {
                ajaxJson.getBody().put("list", new ArrayList<>());
            } else {
                ajaxJson.getBody().put("list", list);
            }
            ajaxJson.getBody().put("topList", ComArticleService.findListByTop(comArticle));
            ajaxJson.getBody().put("totalPage", totalPage);
            ajaxJson.setSuccess(true);
            ajaxJson.setErrorCode("0");
            ajaxJson.setMsg("获取新闻列表成功");
            return ajaxJson;
        } catch (Exception e) {
            e.printStackTrace();
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_3);
            ajaxJson.setMsg(AppErrorUtils.error_3_desc);
            return ajaxJson;
        }
    }
    
    @RequestMapping(value = "findArticleDetail")
    @ResponseBody
    public AjaxJson findArticleDetail(HttpServletRequest request, HttpServletResponse response, Model model) {

        AjaxJson ajaxJson = new AjaxJson();
        try {
            String id = request.getParameter("id");       //id
            if (StringUtils.isBlank(id)) {
                ajaxJson.setSuccess(false);
                ajaxJson.setErrorCode(AppErrorUtils.error_2);
                ajaxJson.setMsg("languageid传参为空");
                return ajaxJson;
            }
            ajaxJson.getBody().put("comArticle", ComArticleService.get(id));
            ajaxJson.setSuccess(true);
            ajaxJson.setErrorCode("0");
            ajaxJson.setMsg("获取新闻详情成功");
            return ajaxJson;
        } catch (Exception e) {
            e.printStackTrace();
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_3);
            ajaxJson.setMsg(AppErrorUtils.error_3_desc);
            return ajaxJson;
        }
    }

    //内容类型详情
    @RequestMapping(value = "findArticleBycontentType")
    @ResponseBody
    public AjaxJson findArticleBycontentType(HttpServletRequest request, HttpServletResponse response, Model model) {

        AjaxJson ajaxJson = new AjaxJson();
        String languageid = request.getParameter("languageid");       //语言id
        String contentType = request.getParameter("contentType");       //内容类型1.用户协议2.合同
        if (StringUtils.isBlank(languageid) || StringUtils.isBlank(contentType)) {
        	ajaxJson.setSuccess(false);
        	ajaxJson.setErrorCode(AppErrorUtils.error_2);
        	ajaxJson.setMsg("languageid或contentType传参为空");
        	return ajaxJson;
        }
        try {
        	ComArticle comArticle=new ComArticle();
        	comArticle.setLanguageId(Integer.valueOf(languageid));
        	comArticle.setContentType(Integer.valueOf(contentType));
            ajaxJson.getBody().put("comArticle", ComArticleService.findListByContentType(comArticle));
            ajaxJson.setSuccess(true);
            ajaxJson.setErrorCode("0");
            ajaxJson.setMsg("获取协议、合同详情成功");
            return ajaxJson;
        } catch (Exception e) {
            e.printStackTrace();
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_3);
            ajaxJson.setMsg(AppErrorUtils.error_3_desc);
            return ajaxJson;
        }
    }
}