package com.jeeplus.modules.app;

import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.modules.meiguotong.entity.insurance.Insurance;
import com.jeeplus.modules.meiguotong.entity.insurance.OrderInsurance;
import com.jeeplus.modules.meiguotong.entity.order.OrderRoute;
import com.jeeplus.modules.meiguotong.entity.order.OrderSys;
import com.jeeplus.modules.meiguotong.entity.ordermember.OrderMember;
import com.jeeplus.modules.meiguotong.entity.product.Route;
import com.jeeplus.modules.meiguotong.entity.product.RouteContent;
import com.jeeplus.modules.meiguotong.entity.product.RouteDate;
import com.jeeplus.modules.meiguotong.entity.scenicspot.ScenicSpot;
import com.jeeplus.modules.meiguotong.service.collection.ProductCollectionService;
import com.jeeplus.modules.meiguotong.service.comcity.ComCityService;
import com.jeeplus.modules.meiguotong.service.comcomment.ComCommentService;
import com.jeeplus.modules.meiguotong.service.comconsult.ComConsultService;
import com.jeeplus.modules.meiguotong.service.comtag.ComTagService;
import com.jeeplus.modules.meiguotong.service.insurance.InsuranceService;
import com.jeeplus.modules.meiguotong.service.order.OrderSysService;
import com.jeeplus.modules.meiguotong.service.product.RouteContentService;
import com.jeeplus.modules.meiguotong.service.product.RouteDateService;
import com.jeeplus.modules.meiguotong.service.product.RouteService;
import com.jeeplus.modules.meiguotong.service.productcar.ProductCarService;
import com.jeeplus.modules.meiguotong.service.scenicspot.ScenicSpotService;
import com.jeeplus.modules.sys.utils.CodeGenUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 路线规划
 */
@Controller
@RequestMapping(value = "${adminPath}/interface/route")
public class AppRouteController {
    @Autowired
    private ComCityService comCityService;
    @Autowired
    private ComTagService comTagService;
    @Autowired
    private AppUtils appUtils;
    @Autowired
    private ScenicSpotService scenicSpotService;
    @Autowired
    private RouteService routeService;
    @Autowired
    private ComCommentService comCommentService;
    @Autowired
    private RouteDateService routeDateService;
    @Autowired
    private RouteContentService routeContentService;
    @Autowired
    private ComConsultService comConsultService;
    @Autowired
    private ProductCollectionService productCollectionService;
    @Autowired
    private ProductCarService productCarService;
    @Autowired
    private InsuranceService insuranceService;
    @Autowired
    private OrderSysService orderSysService;

    /**
     * 根据城市获取途观景点接口
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
//        if (StringUtils.isBlank(cityid)) {
//            ajaxJson.setSuccess(false);
//            ajaxJson.setErrorCode(AppErrorUtils.error_2);
//            ajaxJson.setMsg("cityid传参为空");
//            return ajaxJson;
//        }
        try {
            ScenicSpot scenicSpot = new ScenicSpot();
            scenicSpot.setLanguageId(Integer.parseInt(languageid));
            if(StringUtils.isNotBlank(cityid))
            scenicSpot.setCityId(Integer.parseInt(cityid));
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
     * 常规路线搜索接口
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "selectRoute")
    @ResponseBody
    public AjaxJson selectRoute(HttpServletRequest request, HttpServletResponse response, Model model) {

        AjaxJson ajaxJson = new AjaxJson();
        String type = request.getParameter("type");      //1.游客 2.会员
        String uid = request.getParameter("uid");      //会员ID，登陆后返回
        String time = request.getParameter("time");        //时间，登陆后返回
        String key = request.getParameter("key");       //密钥，登陆后返回
        String languageid = request.getParameter("languageid");       //语言id
        String startCity = request.getParameter("startCity");       //出发城市id
        String endCity = request.getParameter("endCity");       //到达城市id
        String pageNo = request.getParameter("pageNo");//页码
        String date = request.getParameter("date");       //行程天数
        String day = request.getParameter("day");       //行程天数
        String minPrice = request.getParameter("minPrice");       //最小价格
        String maxPrice = request.getParameter("maxPrice");       //最大价格
        String scenic = request.getParameter("scenic");       //景点
        String labelAttrid = request.getParameter("labelAttrid");       //属性

        String orderByType = request.getParameter("orderByType");//排序方式1.销量2.价格降序3.价格升序

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
//		if (StringUtils.isBlank(cityid)) {
//			ajaxJson.setSuccess(false);
//			ajaxJson.setErrorCode(AppErrorUtils.error_2);
//			ajaxJson.setMsg("cityid传参为空");
//			return ajaxJson;
//		}
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
			
            Route route = new Route();
            if (!StringUtils.isBlank(date)) {
                JSONArray jsonArray = JSONArray.fromObject(date);
                List<Date> list = new ArrayList<>();
                for (int i = 0; i < jsonArray.size(); i++) {
                    //取出数组第i个元素
                    JSONObject jpro = jsonArray.getJSONObject(i);
                    //年
                    String year = jpro.getString("year");
                    //月
                    String month = jpro.getString("month");
                    //日
                    String days = jpro.getString("days");
                    String[] days1 = days.split(",");
                    String selectDate = year + "-" + month + "-";
                    for (String a : days1) {
                        selectDate = selectDate + a;
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        Date bt = sdf.parse(selectDate);
                        list.add(bt);
                        route.setDateLIst(list);
                    }
                }
            }

            route.setLanguage(Integer.parseInt(languageid));
            if (StringUtils.isNotBlank(startCity)) {
                route.setStartCity(Integer.parseInt(startCity));
            }
            if (StringUtils.isNotBlank(endCity)) {
                route.setEndCity(Integer.parseInt(endCity));
            }
            route.setType(1);
            route.setDay(day);
            route.setMinPrice(minPrice);
            route.setMaxPrice(maxPrice);

            List<String> scenicList = new ArrayList<>();
            List<String> attrList = new ArrayList<>();
            if (StringUtils.isNotBlank(scenic)) {
                String[] a = scenic.split(",");
                for (String b : a) {
                    scenicList.add(b);
                }
                route.setScenicList(scenicList);
            }
            if (StringUtils.isNotBlank(labelAttrid)) {
                String[] a = labelAttrid.split(",");
                for (String b : a) {
                    attrList.add(b);
                }
                route.setAttrList(attrList);
            }
            Page<Route> page = new Page<Route>();
            page.setPageNo(Integer.parseInt(pageNo));
            page.setPageSize1(pageSize);
            if (StringUtils.isNotBlank(orderByType)) {
                if (Integer.parseInt(orderByType) == 1) {//销量
                    page.setOrderBy("a.finish_num desc");
                } else if (Integer.parseInt(orderByType) == 2) {//价格降序
                    page.setOrderBy("a.price desc");
                } else if (Integer.parseInt(orderByType) == 3) {//价格升序
                    page.setOrderBy("a.price");
                } else if (Integer.parseInt(orderByType) == 4) {//评论降序
                    page.setOrderBy("a.star desc");
                }
            }
            Page<Route> page1 = routeService.findRoutePage(page, route);

            List<Route> routeList = page1.getList();
            int totalPage = page1.getTotalPage();
            if (Integer.parseInt(pageNo) > totalPage) {
                ajaxJson.getBody().put("list", new ArrayList<>());
            } else {
                ajaxJson.getBody().put("list", routeList);
            }
            ajaxJson.getBody().put("totalPage", totalPage);
            ajaxJson.setSuccess(true);
            ajaxJson.setErrorCode("0");
            ajaxJson.setMsg(" 常规路线搜索成功");
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
     * 常规路线详情接口
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "getRouteDetails")
    @ResponseBody
    public AjaxJson getRouteDetails(HttpServletRequest request, HttpServletResponse response, Model model) {

        AjaxJson ajaxJson = new AjaxJson();
        try {
            String type = request.getParameter("type");      //1.游客 2.会员
            String uid = request.getParameter("uid");      //会员ID，登陆后返回
            String time = request.getParameter("time");        //时间，登陆后返回
            String key = request.getParameter("key");       //密钥，登陆后返回
            String languageid = request.getParameter("languageid");       //语言id
            String routeid = request.getParameter("routeid");       //路线id

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
            if (StringUtils.isBlank(routeid)) {
                ajaxJson.setSuccess(false);
                ajaxJson.setErrorCode(AppErrorUtils.error_2);
                ajaxJson.setMsg("routeid传参为空");
                return ajaxJson;
            }

            Route route = new Route();
            route.setId(routeid);
            if (StringUtils.isNotBlank(uid)) {
                route.setMemberid(Integer.parseInt(uid));
            } else {
                route.setMemberid(0);
            }
            Route route1 = routeService.getRoute(route);
            route1.setRecommend(StringEscapeUtils.unescapeHtml(route1.getRecommend()));
//			//获取产品日期价格
//			RouteDate routeDate=new RouteDate();
//			
//			Calendar c = Calendar.getInstance();      
//			c.setTime(new Date());
//			c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天   
//			c.add(Calendar.MONTH, -1);
//			Date beginDate = c.getTime();
//			c.add(Calendar.MONTH, +3);
//			Date endDate = c.getTime();
//			
//			routeDate.setBeginDate(beginDate);
//			routeDate.setEndDate(endDate);
//			
//			Route route2 = routeService.get(routeid);
//			c.setTime(new Date());
//			c.add(Calendar.DAY_OF_MONTH, +route2.getAdvanceDay());
//			routeDate.setStartDate(c.getTime());
//			
//			routeDate.setRouteid(Integer.parseInt(routeid));
//			List<RouteDate> list = routeDateService.getDateList(routeDate);

            ajaxJson.getBody().put("route", route1);
//			if(list!=null&&list.size()>0){
//				ajaxJson.getBody().put("priceList", list);
//			}else{
//				ajaxJson.getBody().put("priceList", new ArrayList());
//			}
          //修改查看数量
            Route route2=new Route();
            route2.setId(routeid);
            routeService.changeLookNum(route2);
            ajaxJson.setSuccess(true);
            ajaxJson.setErrorCode("0");
            ajaxJson.setMsg("获取常规路线详情成功");
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
     * 常规路线日期价格接口
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "getRoutePriceDetails")
    @ResponseBody
    public AjaxJson getRoutePriceDetails(HttpServletRequest request, HttpServletResponse response, Model model) {

        AjaxJson ajaxJson = new AjaxJson();
        try {
            String type = request.getParameter("type");      //1.游客 2.会员
            String uid = request.getParameter("uid");      //会员ID，登陆后返回
            String time = request.getParameter("time");        //时间，登陆后返回
            String key = request.getParameter("key");       //密钥，登陆后返回
            String languageid = request.getParameter("languageid");       //语言id
            String routeid = request.getParameter("routeid");       //路线id
            String priceDate = request.getParameter("priceDate");       //日期

            if (StringUtils.isBlank(priceDate)) {
                ajaxJson.setSuccess(false);
                ajaxJson.setErrorCode(AppErrorUtils.error_2);
                ajaxJson.setMsg("priceDate传参为空");
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
            if (StringUtils.isBlank(routeid)) {
                ajaxJson.setSuccess(false);
                ajaxJson.setErrorCode(AppErrorUtils.error_2);
                ajaxJson.setMsg("routeid传参为空");
                return ajaxJson;
            }

            //获取产品日期价格
            RouteDate routeDate = new RouteDate();

            Calendar c = Calendar.getInstance();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
            Date bt = sdf.parse(priceDate);
            c.setTime(bt);
            c.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天
            c.add(Calendar.MONTH, -1);
            Date beginDate = c.getTime();
            c.add(Calendar.MONTH, +4);
            Date endDate = c.getTime();
            routeDate.setBeginDate(beginDate);
            routeDate.setEndDate(endDate);

            Route route = routeService.get(routeid);
            c.setTime(new Date());
            c.add(Calendar.DAY_OF_MONTH, +route.getAdvanceDay());

            routeDate.setStartDate(c.getTime());

            routeDate.setRouteid(Integer.parseInt(routeid));
            List<RouteDate> list = routeDateService.getDateList(routeDate);
            if (list != null && list.size() > 0) {
                ajaxJson.getBody().put("list", list);
            } else {
                ajaxJson.getBody().put("list", new ArrayList<>());
            }

            ajaxJson.setSuccess(true);
            ajaxJson.setErrorCode("0");
            ajaxJson.setMsg("获取常规路线日期价格详情成功");
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
     * 常规路线行程内容接口
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "getRouteContent")
    @ResponseBody
    public AjaxJson getRouteContent(HttpServletRequest request, HttpServletResponse response, Model model) {

        AjaxJson ajaxJson = new AjaxJson();
        try {
            String type = request.getParameter("type");      //1.游客 2.会员
            String uid = request.getParameter("uid");      //会员ID，登陆后返回
            String time = request.getParameter("time");        //时间，登陆后返回
            String key = request.getParameter("key");       //密钥，登陆后返回
            String languageid = request.getParameter("languageid");       //语言id
            String routeid = request.getParameter("routeid");       //路线id

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
            if (StringUtils.isBlank(routeid)) {
                ajaxJson.setSuccess(false);
                ajaxJson.setErrorCode(AppErrorUtils.error_2);
                ajaxJson.setMsg("routeid传参为空");
                return ajaxJson;
            }

            RouteContent routeContent = new RouteContent();
            routeContent.setRouteid(Integer.parseInt(routeid));
            List<RouteContent> list = routeContentService.getContentList(routeContent);

            if (list != null && list.size() > 0) {
                ajaxJson.getBody().put("list", list);
            } else {
                ajaxJson.getBody().put("list", new ArrayList<>());
            }

            ajaxJson.setSuccess(true);
            ajaxJson.setErrorCode("0");
            ajaxJson.setMsg("获取常规路线行程内容成功");
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
     * 常规路线添加订单接口
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "saveRouteOrder",method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson saveRouteOrder(HttpServletRequest request, HttpServletResponse response, Model model) {

        AjaxJson ajaxJson = new AjaxJson();
        ajaxJson=this.saveRouteOrder(request);
        if(ajaxJson!=null) {
        	return ajaxJson;
        }else {
        	 ajaxJson = new AjaxJson();
        }
        try {
//			String type = request.getParameter("type");      //1.游客 2.会员
            String uid = request.getParameter("uid");      //会员ID，登陆后返回
            String time = request.getParameter("time");        //时间，登陆后返回
            String key = request.getParameter("key");       //密钥，登陆后返回
            String languageid = request.getParameter("languageid");       //语言id
            String routeid = request.getParameter("routeid");       //路线id

            String contactsName = request.getParameter("contactsName");       //联系人姓名
            String contactsMobile = request.getParameter("contactsMobile");       //联系人电话
            String remark = request.getParameter("remark");       //备注
            String startDate = request.getParameter("startDate");       //开始时间
            String adultNum = request.getParameter("adultNum");       //大人
            String childNum = request.getParameter("childNum");       //小孩
            String oneNum = request.getParameter("oneNum");       //单人间
            String twoNum = request.getParameter("twoNum");       //双人间
            String threeNum = request.getParameter("threeNum");       //三人间
            String fourNum = request.getParameter("fourNum");       //四人间
            String arrangeNum = request.getParameter("arrangeNum");       //配房数量
            String insuranceid = request.getParameter("insuranceid");       //保险id
            /*出游人信息集合[{chineseName: "",englishName : "", certType: "",certNo : "",
            certValidDate: "", birthday: "", area: "", mobile: "",type : "" },{},{}]*/
            String orderMember = request.getParameter("orderMember");       //出游人信息

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date bt = sdf.parse(startDate);
            //查询余票
            RouteDate routeDate3=new RouteDate();
            routeDate3.setRouteid(Integer.valueOf(routeid));
            routeDate3.setPriceDate(bt);
            RouteDate routeDate2=routeDateService.findNumByDate(routeDate3);
            
            int stock=Integer.valueOf(adultNum)+Integer.valueOf(childNum);
            if (routeDate2==null||(routeDate2.getStock()>0&&routeDate2.getStock()-routeDate2.getNum()<stock)) {
            	ajaxJson.setSuccess(false);
            	ajaxJson.setErrorCode("0");
                ajaxJson.setMsg("余票不足!");
                return ajaxJson;
			}
            //获取路线
            Route route = routeService.get(routeid);

            OrderSys orderSys = new OrderSys();
			orderSys.setPayOrderNo(CodeGenUtils.getNowDate());
            
            OrderRoute orderRoute = new OrderRoute();
            OrderInsurance orderInsurance = new OrderInsurance();
//订单状态1.待付款2.待确定，3.待出行，4.待评价 5.已完成6.取消订单7.申请退款8.退款中9退款成功10.退款失败
            orderSys.setStatus(1);
            orderSys.setCreateDate(new Date());
            orderSys.setType(4);        // 订单类型1.包车租车2.短程接送3.接送机4常规路线5.当地参团6.游轮7.景点门票
            //8.当地玩家9.酒店10.保险11.旅游定制12导游13.商务定制14.商务旅游',
            orderSys.setFatherid(0);        // 父id
            orderSys.setMemberid(Integer.parseInt(uid));      //下单人
            orderSys.setLanguageid(Integer.parseInt(languageid));    //语言id

            orderSys.setContactsName(contactsName);    //联系人姓名
            orderSys.setContactsMobile(contactsMobile);    //联系人电话
            orderSys.setRemark(remark);            //备注
            orderSys.setPeopleNum(Integer.parseInt(adultNum) + Integer.parseInt(childNum));
            orderSys.setTitle(route.getTitle());            //订单标题
            orderSys.setAdultNum(Integer.parseInt(adultNum));        // 大人数量
            orderSys.setChildNum(Integer.parseInt(childNum));        // 孩子数量
            orderSys.setBeginDate(bt);       //开始时间
            orderSys.setOneNum(Integer.parseInt(oneNum));        // 单人间数量
            orderSys.setTwoNum(Integer.parseInt(twoNum));        // 双人间数量
            orderSys.setThreeNum(Integer.parseInt(threeNum));        // 三人间数量
            orderSys.setFourNum(Integer.parseInt(fourNum));        // 四人间数量
            orderSys.setArrangeNum(Integer.parseInt(arrangeNum));        // 配房数量
            orderSys.setAgentid(route.getAgentid());
            orderSys.setStartCity(route.getStartCityContent());
            String image=route.getCarImg();
            if(image!=null) {
            	image=image.split(",")[0];
            }
            orderSys.setImage(image);
            
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd");
            String bt1 = sdf1.format(new Date());
            String orderNo = "CG-" + route.getAgentid() + "-" + bt1 + "[]" + "-" + route.getId();
            orderRoute.setOrderNo(orderNo);  //订单号,后面替换[]
            orderRoute.setAgentid(route.getAgentid());        // 供应商ID
            orderRoute.setRouteid(Integer.parseInt(routeid));        // 常规路线/参团id
            orderRoute.setMemberid(Integer.parseInt(uid));        // 会员ID
            orderRoute.setDay(route.getDayNum()); //订单天数
            orderRoute.setStartCity(route.getEndCity());        // 出发城市
            orderRoute.setStartDate(bt);        // 出发时间
            orderRoute.setAdultNum(Integer.parseInt(adultNum));        // 大人数量
            orderRoute.setChildNum(Integer.parseInt(childNum));        // 孩子数量
            orderRoute.setOneNum(Integer.parseInt(oneNum));        // 单人间数量
            orderRoute.setTwoNum(Integer.parseInt(twoNum));        // 双人间数量
            orderRoute.setThreeNum(Integer.parseInt(threeNum));        // 三人间数量
            orderRoute.setFourNum(Integer.parseInt(fourNum));        // 四人间数量
            orderRoute.setArrangeNum(Integer.parseInt(arrangeNum));        // 配房数量
            //获取出发时间的价格
            RouteDate routeDate = new RouteDate();
            routeDate.setRouteid(Integer.parseInt(routeid));
            routeDate.setPriceDate(bt);
            Map map=new HashMap();
            map.put("routeid", routeid);
            map.put("filter_priceDate", this.date2String(bt));
          List<RouteDate>  routeDates = routeDateService.findListByPage(map);
          routeDate= routeDates.get(0);
            orderRoute.setContactsName(contactsName);        // 联系人姓名
            orderRoute.setContactsMobile(contactsMobile);        // 联系人电话
            orderRoute.setRemark(remark);        // 备注
            orderRoute.setStatus(1);        // 订单状态1.待付款2.待确定，3.待出行，4.待评价 5.已完成6.取消订单7.申请退款8退款通过9.退款不通过
            orderRoute.setLanguageId(Integer.parseInt(languageid));        // 语言
            orderRoute.setTitle(route.getTitle());        // 标题
            orderRoute.setSubtitle(route.getSubtitle());        // 副标题
            orderRoute.setInfor(route.getInfor());        // 产品详情
            orderRoute.setNo(route.getNo());            //产品编号
            orderRoute.setCompanyName(route.getCompanyName());//供应商名称
            orderRoute.setType(1);        // 1常规路线  2参团
            orderRoute.setName(route.getTitle());//订单标题


            if (StringUtils.isNotBlank(insuranceid)) {
                //获取保险信息
                Insurance insurance = insuranceService.get(insuranceid);

                orderInsurance.setName(insurance.getName());        // 保险名称
                orderInsurance.setInsuranceid(Integer.parseInt(insuranceid));        // 保险ID
                orderInsurance.setAgentid(insurance.getAgentid());        // 供应商ID

                String orderNo1 = "BX-" + insurance.getAgentid() + "-" + bt1 + "[]" + "-" + insurance.getId();
                orderInsurance.setOrderNo(orderNo1);        // 订单号，后面替换[]
                orderInsurance.setMemberId(Integer.parseInt(uid));        // 下单人id
                //保险费*(成人数+儿童数)
                int num = Integer.parseInt(adultNum) + Integer.parseInt(childNum);
                BigDecimal price=new BigDecimal(num).multiply(insurance.getPrice()).setScale(2,BigDecimal.ROUND_HALF_UP);
                orderInsurance.setPrice(price);        // 订单价格
                orderInsurance.setPrice2(insurance.getPrice());   
                orderInsurance.setStatus(1);        // 订单状态  1 待付款 2 待确认 3 待出行 4 待点评 5 已完成 6 已取消 7 申请退款 8 退款成功 9 退款失败
                orderInsurance.setContacts(contactsName);        // 联系人
                orderInsurance.setContactMobile(contactsMobile);        // 联系电话
                orderInsurance.setContactRemark(remark);        // 备注
                orderInsurance.setLanguageId(languageid);        // 语言id
                orderInsurance.setNum(num);        // 预定数量
            }
            JSONArray jsonArray = JSONArray.fromObject(orderMember);
            List<OrderMember> orderMemberList = new ArrayList<>();
            for (int i = 0; i < jsonArray.size(); i++) {
                OrderMember orderMember1 = new OrderMember();
                //取出数组第i个元素
                JSONObject jpro = jsonArray.getJSONObject(i);
                String chineseName = jpro.getString("chineseName");
                String englishName = jpro.getString("englishName");
                String certType = jpro.getString("certType");
                String certNo = jpro.getString("certNo");
                String certValidDate = jpro.getString("certValidDate");
                String birthday = jpro.getString("birthday");
                String area = jpro.getString("area");
                String mobile = jpro.getString("mobile");
                String orderMemberType = jpro.getString("type");
                orderMember1.setIsNewRecord(true);

                orderMember1.setChineseName(chineseName);        // 中文姓名
                orderMember1.setEnglishName(englishName);        // 英文姓名
                orderMember1.setCertType(Integer.parseInt(certType));        // 证件类型：1.身份证 2.护照 3.本地ID',
                orderMember1.setCertNo(certNo);        // 证件号
                Date certValidDate1 = sdf.parse(certValidDate);
                orderMember1.setCertValidDate(certValidDate1);        // 有效期
                Date birthday1 = sdf.parse(birthday);
                orderMember1.setBirthday(birthday1);        // 出生年月
                orderMember1.setArea(area);        // 区号
                orderMember1.setMobile(mobile);        // 手机号
                orderMember1.setLanguageId(Integer.parseInt(languageid));        // 语言id
                orderMember1.setType(2);        //1.租车2.常规路线3.当地参团4.游轮5.景点门票6.当地玩家7.酒店8.保险9.定制10导游'
//				    orderMember1.setTypeId(typeId);		// 根据type 对应不同表的 orderid(orderSys)
                if (StringUtils.isNotBlank(orderMemberType)) {
                    orderMember1.setSaveType(Boolean.parseBoolean(orderMemberType));
                }
                orderMemberList.add(orderMember1);
            }
            //保险价格
            BigDecimal insurancePrice = BigDecimal.ZERO;
            if (orderInsurance.getPrice() != null) {
                insurancePrice = orderInsurance.getPrice();
            }
            //路线价格*(成人数+儿童数)+(单房成本+单房利润)*单人间数量+(双房成本+双房利润）*双人间数量+(三房成本+三房利润)*三人间数量
            // +(四房成本+四房利润)*四人间数量+(配房成本+配房利润)*配房数量+保险费*(成人数+儿童数)
             //路线
            //BigDecimal routePrice = route.getPrice().multiply(new BigDecimal(adultNum).add(new BigDecimal(childNum))).setScale(2, BigDecimal.ROUND_HALF_UP);
            BigDecimal routePrice =new BigDecimal("0");
            //单人房
            BigDecimal oneRoomPrice = (routeDate.getOneCost().add(routeDate.getOneProfit())).multiply(new BigDecimal(oneNum)).setScale(2, BigDecimal.ROUND_HALF_UP);
            //双人房
            BigDecimal twoRoomPrice = (routeDate.getTwoCost().add(routeDate.getTwoProfit())).multiply(new BigDecimal(twoNum)).setScale(2, BigDecimal.ROUND_HALF_UP);
            //三人房
            BigDecimal threeRoomPrice = (routeDate.getThreeCost().add(routeDate.getThreeProfit())).multiply(new BigDecimal(threeNum)).setScale(2, BigDecimal.ROUND_HALF_UP);
            //四人房
            BigDecimal fourRoomPrice = (routeDate.getFourCost().add(routeDate.getFourProfit())).multiply(new BigDecimal(fourNum)).setScale(2, BigDecimal.ROUND_HALF_UP);
            //配房价格
            BigDecimal arrangeRoomPrice = (routeDate.getArrangeCost().add(routeDate.getArrangeProfit())).multiply(new BigDecimal(arrangeNum)).setScale(2, BigDecimal.ROUND_HALF_UP);
            //价格=路线价格+房间价格
            BigDecimal routeTotalPrice = routePrice.add(oneRoomPrice).add(twoRoomPrice).add(threeRoomPrice).add(fourRoomPrice).add(arrangeRoomPrice).setScale(2, BigDecimal.ROUND_HALF_UP);
            orderRoute.setPrice(routeTotalPrice);
            //总价格=路线价格+房间价格+保险价格
            BigDecimal totalPrice = routeTotalPrice.add(insurancePrice).setScale(2, BigDecimal.ROUND_HALF_UP);
            orderSys.setPrice(totalPrice);//主订单总价
          
            orderSys.setPrice1(routeDate.getOneCost().add(routeDate.getOneProfit()));
            orderSys.setPrice2(routeDate.getTwoCost().add(routeDate.getTwoProfit()));
            orderSys.setPrice3(routeDate.getThreeCost().add(routeDate.getThreeProfit()));
            orderSys.setPrice4(routeDate.getFourCost().add(routeDate.getFourProfit()));
            orderSys.setPricea(routeDate.getArrangeCost().add(routeDate.getArrangeProfit()));
            
            orderRoute.setPrice1(routeDate.getOneCost().add(routeDate.getOneProfit()));
            orderRoute.setPrice2(routeDate.getTwoCost().add(routeDate.getTwoProfit()));
            orderRoute.setPrice3(routeDate.getThreeCost().add(routeDate.getThreeProfit()));
            orderRoute.setPrice4(routeDate.getFourCost().add(routeDate.getFourProfit()));
            orderRoute.setPricea(routeDate.getArrangeCost().add(routeDate.getArrangeProfit()));
            
            
            String orderid = orderSysService.saveRouteOrder(orderSys, orderRoute, orderInsurance, orderMemberList);

			ajaxJson.getBody().put("payOrderNo", orderSys.getPayOrderNo());
            ajaxJson.setSuccess(true);
            ajaxJson.setErrorCode("0");
            ajaxJson.setMsg("常规路线添加订单成功");
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
     * 当地参团搜索接口
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "selectCityRoute")
    @ResponseBody
    public AjaxJson selectCityRoute(HttpServletRequest request, HttpServletResponse response, Model model) {

        AjaxJson ajaxJson = new AjaxJson();
        String type = request.getParameter("type");      //1.游客 2.会员
        String uid = request.getParameter("uid");      //会员ID，登陆后返回
        String time = request.getParameter("time");        //时间，登陆后返回
        String key = request.getParameter("key");       //密钥，登陆后返回
        String languageid = request.getParameter("languageid");       //语言id
        String cityid = request.getParameter("cityid");       //城市id
        String pageNo = request.getParameter("pageNo");//页码
        String date = request.getParameter("date");       //[{year:”2018”,month:”05”,days:”05,06”},{}]
        String day = request.getParameter("day");       //行程天数
        String minPrice = request.getParameter("minPrice");       //最小价格
        String maxPrice = request.getParameter("maxPrice");       //最大价格
        String labelAttrid = request.getParameter("labelAttrid");       //属性

        String orderByType = request.getParameter("orderByType");//排序方式1.销量2.价格降序3.价格升序

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
//		if (StringUtils.isBlank(cityid)) {
//			ajaxJson.setSuccess(false);
//			ajaxJson.setErrorCode(AppErrorUtils.error_2);
//			ajaxJson.setMsg("cityid传参为空");
//			return ajaxJson;
//		}
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
			
            Route route = new Route();
            if (!StringUtils.isBlank(date)) {
                JSONArray jsonArray = JSONArray.fromObject(date);
                List<Date> list = new ArrayList<>();
                for (int i = 0; i < jsonArray.size(); i++) {
                    //取出数组第i个元素
                    JSONObject jpro = jsonArray.getJSONObject(i);
                    //年
                    String year = jpro.getString("year");
                    //月
                    String month = jpro.getString("month");
                    //日
                    String days = jpro.getString("days");
                    String[] days1 = days.split(",");
                    String selectDate = year + "-" + month + "-";
                    for (String a : days1) {
                        selectDate = selectDate + a;
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        Date bt = sdf.parse(selectDate);
                        list.add(bt);
                        route.setDateLIst(list);
                    }
                }
            }

            route.setLanguage(Integer.parseInt(languageid));
            if (StringUtils.isNotBlank(cityid)) {
                route.setEndCity(Integer.parseInt(cityid));
            }
            route.setType(2);
            route.setDay(day);
            route.setMinPrice(minPrice);
            route.setMaxPrice(maxPrice);

            List<String> attrList = new ArrayList<>();
            if (StringUtils.isNotBlank(labelAttrid)) {
                String[] a = labelAttrid.split(",");
                for (String b : a) {
                    attrList.add(b);
                }
                route.setAttrList(attrList);
            }


            Page<Route> page = new Page<Route>();
            page.setPageNo(Integer.parseInt(pageNo));
            page.setPageSize1(pageSize);
            if (StringUtils.isNotBlank(orderByType)) {
                if (Integer.parseInt(orderByType) == 1) {//销量
                    page.setOrderBy("a.finish_num desc");
                } else if (Integer.parseInt(orderByType) == 2) {//价格降序
                    page.setOrderBy("a.price desc");
                } else if (Integer.parseInt(orderByType) == 3) {//价格升序
                    page.setOrderBy("a.price");
                } else if (Integer.parseInt(orderByType) == 4) {//价格升序
                    page.setOrderBy("a.star desc");
                }
            }
            Page<Route> page1 = routeService.findCityRoutePage(page, route);

            List<Route> routeList = page1.getList();
            int totalPage = page1.getTotalPage();
            if (Integer.parseInt(pageNo) > totalPage) {
                ajaxJson.getBody().put("list", new ArrayList<>());
            } else {
                ajaxJson.getBody().put("list", routeList);
            }
            ajaxJson.getBody().put("totalPage", totalPage);
            ajaxJson.setSuccess(true);
            ajaxJson.setErrorCode("0");
            ajaxJson.setMsg("当地参团搜索成功");
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
     * 当地参团详情接口
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "getCityRouteDetails")
    @ResponseBody
    public AjaxJson getCityRouteDetails(HttpServletRequest request, HttpServletResponse response, Model model) {

        AjaxJson ajaxJson = new AjaxJson();
        try {
            String type = request.getParameter("type");      //1.游客 2.会员
            String uid = request.getParameter("uid");      //会员ID，登陆后返回
            String time = request.getParameter("time");        //时间，登陆后返回
            String key = request.getParameter("key");       //密钥，登陆后返回
            String languageid = request.getParameter("languageid");       //语言id
            String routeid = request.getParameter("routeid");       //路线id


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
            if (StringUtils.isBlank(routeid)) {
                ajaxJson.setSuccess(false);
                ajaxJson.setErrorCode(AppErrorUtils.error_2);
                ajaxJson.setMsg("routeid传参为空");
                return ajaxJson;
            }

            Route route = new Route();
            route.setId(routeid);
            if (StringUtils.isNotBlank(uid)) {
                route.setMemberid(Integer.parseInt(uid));
            } else {
                route.setMemberid(0);
            }
            Route route1 = routeService.getCityRoute(route);
            route1.setRecommend(StringEscapeUtils.unescapeHtml(route1.getRecommend()));
//			//获取产品日期价格
//			RouteDate routeDate=new RouteDate();
//			
//			Calendar c = Calendar.getInstance();      
//			c.setTime(new Date());
//			c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天   
//			c.add(Calendar.MONTH, -1);
//			Date beginDate = c.getTime();
//			c.add(Calendar.MONTH, +3);
//			Date endDate = c.getTime();
//			
//			routeDate.setBeginDate(beginDate);
//			routeDate.setEndDate(endDate);
//			routeDate.setRouteid(Integer.parseInt(routeid));
//			List<RouteDate> list = routeDateService.getDateList(routeDate);

          //修改查看数量
            Route route2=new Route();
            route2.setId(routeid);
            routeService.changeLookNum(route2);
            
            ajaxJson.getBody().put("route", route1);
//			if(list!=null&&list.size()>0){
//				ajaxJson.getBody().put("priceList", list);
//			}else{
//				ajaxJson.getBody().put("priceList", new ArrayList());
//			}

            ajaxJson.setSuccess(true);
            ajaxJson.setErrorCode("0");
            ajaxJson.setMsg("获取当地参团详情成功");
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
     * 当地参团添加订单接口
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "saveCityRouteOrder")
    @ResponseBody
    public AjaxJson saveCityRouteOrder(HttpServletRequest request, HttpServletResponse response, Model model) {

        AjaxJson ajaxJson = new AjaxJson();
        ajaxJson=this.checkSaveCityRouteOrder(request);
        if(ajaxJson!=null) {
        	return ajaxJson;
        }else {
        	 ajaxJson = new AjaxJson();
        }
        try {
//			String type = request.getParameter("type");      //1.游客 2.会员
            String uid = request.getParameter("uid");      //会员ID，登陆后返回
            String time = request.getParameter("time");        //时间，登陆后返回
            String key = request.getParameter("key");       //密钥，登陆后返回
            String languageid = request.getParameter("languageid");       //语言id
            String routeid = request.getParameter("routeid");       //路线id

            String contactsName = request.getParameter("contactsName");       //联系人姓名
            String contactsMobile = request.getParameter("contactsMobile");       //联系人电话
            String remark = request.getParameter("remark");       //备注
            String startDate = request.getParameter("startDate");       //开始时间
            String adultNum = request.getParameter("adultNum");       //大人
            String childNum = request.getParameter("childNum");       //小孩
            String oneNum = request.getParameter("oneNum");       //单人间
            String twoNum = request.getParameter("twoNum");       //双人间
            String threeNum = request.getParameter("threeNum");       //三人间
            String fourNum = request.getParameter("fourNum");       //四人间
            String arrangeNum = request.getParameter("arrangeNum");       //配房数量
            String insuranceid = request.getParameter("insuranceid");       //保险id
            /*出游人信息集合[{chineseName: "",englishName : "", certType: "",certNo : "",
            certValidDate: "", birthday: "", area: "", mobile: "",type : "" },{},{}]*/
            String orderMember = request.getParameter("orderMember");       //出游人信息
            
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date bt = sdf.parse(startDate);
            
            //查询余票
            RouteDate routeDate3=new RouteDate();
            routeDate3.setRouteid(Integer.valueOf(routeid));
            routeDate3.setPriceDate(bt);
            RouteDate routeDate2=routeDateService.findNumByDate(routeDate3);
            
            int stock=Integer.valueOf(adultNum)+Integer.valueOf(childNum);
            if (routeDate2==null||(routeDate2.getStock()>0&&routeDate2.getStock()-routeDate2.getNum()<stock)) {
            	ajaxJson.setSuccess(false);
            	ajaxJson.setErrorCode("0");
                ajaxJson.setMsg("余票不足!");
                return ajaxJson;
			}
            
            //获取参团
            Route route = routeService.get(routeid);

            OrderSys orderSys = new OrderSys();
			orderSys.setPayOrderNo(CodeGenUtils.getNowDate());
            OrderRoute orderRoute = new OrderRoute();
            OrderInsurance orderInsurance = new OrderInsurance();
            orderSys.setStatus(1);
            orderSys.setType(5);// 订单类型1.包车租车2.短程接送3.接送机4常规路线5.当地参团6.游轮7.景点门票
            //8.当地玩家9.酒店10.保险11.旅游定制12导游13.商务定制14.商务旅游',
            orderSys.setFatherid(0);        // 父id
            orderSys.setMemberid(Integer.parseInt(uid));      //下单人
            orderSys.setLanguageid(Integer.parseInt(languageid));    //语言id

            orderSys.setContactsName(contactsName);    //联系人姓名
            orderSys.setContactsMobile(contactsMobile);    //联系人电话
            orderSys.setRemark(remark);            //备注
            orderSys.setPeopleNum(Integer.parseInt(adultNum) + Integer.parseInt(childNum));
            orderSys.setTitle(route.getTitle());            //订单标题
            orderSys.setAdultNum(Integer.parseInt(adultNum));        // 大人数量
            orderSys.setChildNum(Integer.parseInt(childNum));        // 孩子数量
           
            orderSys.setBeginDate(bt);       //开始时间
            orderSys.setOneNum(Integer.parseInt(oneNum));        // 单人间数量
            orderSys.setTwoNum(Integer.parseInt(twoNum));        // 双人间数量
            orderSys.setThreeNum(Integer.parseInt(threeNum));        // 三人间数量
            orderSys.setFourNum(Integer.parseInt(fourNum));        // 四人间数量
            orderSys.setArrangeNum(Integer.parseInt(arrangeNum));        // 配房数量
            orderSys.setAgentid(route.getAgentid());
            orderSys.setEndCity(route.getEndCityContent());
            
            String image=route.getCarImg();
            if(image!=null) {
            	image=image.split(",")[0];
            }
            orderSys.setImage(image);

            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd");
            String bt1 = sdf1.format(new Date());
            String orderNo = "CG-" + route.getAgentid() + "-" + bt1 + "[]" + "-" + route.getId();
            orderRoute.setOrderNo(orderNo);  //订单号,后面替换[]
            orderRoute.setAgentid(route.getAgentid());        // 供应商ID
            orderRoute.setRouteid(Integer.parseInt(routeid));        // 常规路线/参团id
            orderRoute.setMemberid(Integer.parseInt(uid));        // 会员ID
            orderRoute.setDay(route.getDayNum()); //订单天数
            orderRoute.setStartCity(route.getEndCity());        // 出发城市
            orderRoute.setStartDate(bt);        // 出发时间
            orderRoute.setAdultNum(Integer.parseInt(adultNum));        // 大人数量
            orderRoute.setChildNum(Integer.parseInt(childNum));        // 孩子数量
            orderRoute.setOneNum(Integer.parseInt(oneNum));        // 单人间数量
            orderRoute.setTwoNum(Integer.parseInt(twoNum));        // 双人间数量
            orderRoute.setThreeNum(Integer.parseInt(threeNum));        // 三人间数量
            orderRoute.setFourNum(Integer.parseInt(fourNum));        // 四人间数量
            orderRoute.setArrangeNum(Integer.parseInt(arrangeNum));        // 配房数量
            //获取出发时间的价格
            RouteDate routeDate = new RouteDate();
            routeDate.setRouteid(Integer.parseInt(routeid));
            routeDate.setPriceDate(bt);
            routeDate = routeDateService.getRouteDate(routeDate);
            orderRoute.setContactsName(contactsName);        // 联系人姓名
            orderRoute.setContactsMobile(contactsMobile);        // 联系人电话
            orderRoute.setRemark(remark);        // 备注
            orderRoute.setStatus(1);        // 订单状态1.待付款2.待确定，3.待出行，4.待评价 5.已完成6.取消订单7.申请退款8退款通过9.退款不通过
            orderRoute.setLanguageId(Integer.parseInt(languageid));        // 语言
            orderRoute.setTitle(route.getTitle());        // 标题
            orderRoute.setSubtitle(route.getSubtitle());        // 副标题
            orderRoute.setInfor(route.getInfor());        // 产品详情
            orderRoute.setNo(route.getNo());            //产品编号
            orderRoute.setCompanyName(route.getCompanyName());//供应商名称
            orderRoute.setType(2);        // 1常规路线  2参团
            orderRoute.setName(route.getTitle());//订单标题


            if (StringUtils.isNotBlank(insuranceid)) {
                //获取保险信息
                Insurance insurance = insuranceService.get(insuranceid);

                orderInsurance.setName(insurance.getName());        // 保险名称
                orderInsurance.setInsuranceid(Integer.parseInt(insuranceid));        // 保险ID
                orderInsurance.setAgentid(insurance.getAgentid());        // 供应商ID

                String orderNo1 = "BX-" + insurance.getAgentid() + "-" + bt1 + "[]" + "-" + insurance.getId();
                orderInsurance.setOrderNo(orderNo1);        // 订单号，后面替换[]

                orderInsurance.setMemberId(Integer.parseInt(uid));        // 下单人id
                //保险费*(成人数+儿童数)
                int num = Integer.parseInt(adultNum) + Integer.parseInt(childNum);
                BigDecimal price = new BigDecimal(num).multiply(insurance.getPrice()).setScale(2, BigDecimal.ROUND_HALF_UP);
                orderInsurance.setPrice(price);
                orderInsurance.setStatus(1);        // 订单状态  1 待付款 2 待确认 3 待出行 4 待点评 5 已完成 6 已取消 7 申请退款 8 退款成功 9 退款失败
                orderInsurance.setContacts(contactsName);        // 联系人
                orderInsurance.setContactMobile(contactsMobile);        // 联系电话
                orderInsurance.setContactRemark(remark);        // 备注
                orderInsurance.setLanguageId(languageid);        // 语言id
                orderInsurance.setNum(num);        // 预定数量
            }

            JSONArray jsonArray = JSONArray.fromObject(orderMember);
            List<OrderMember> orderMemberList = new ArrayList<>();
            for (int i = 0; i < jsonArray.size(); i++) {
                OrderMember orderMember1 = new OrderMember();
                //取出数组第i个元素
                JSONObject jpro = jsonArray.getJSONObject(i);
                String chineseName = jpro.getString("chineseName");
                String englishName = jpro.getString("englishName");
                String certType = jpro.getString("certType");
                String certNo = jpro.getString("certNo");
                String certValidDate = jpro.getString("certValidDate");
                String birthday = jpro.getString("birthday");
                String area = jpro.getString("area");
                String mobile = jpro.getString("mobile");
                String orderMemberType = jpro.getString("type");

                orderMember1.setChineseName(chineseName);        // 中文姓名
                orderMember1.setEnglishName(englishName);        // 英文姓名
                orderMember1.setCertType(Integer.parseInt(certType));        // 证件类型：1.身份证 2.护照 3.本地ID',
                orderMember1.setCertNo(certNo);        // 证件号
                Date certValidDate1 = sdf.parse(certValidDate);
                orderMember1.setCertValidDate(certValidDate1);        // 有效期
                Date birthday1 = sdf.parse(birthday);
                orderMember1.setBirthday(birthday1);        // 出生年月
                orderMember1.setArea(area);        // 区号
                orderMember1.setMobile(mobile);        // 手机号
                orderMember1.setLanguageId(Integer.parseInt(languageid));        // 语言id
                orderMember1.setType(3);        // 1.租车2.常规路线3.当地参团4.游轮5.景点门票6.当地玩家7.酒店8.保险9.定制10导游'
//				    orderMember1.setTypeId(typeId);		// 根据type 对应不同表的 orderid(orderSys)
                if (StringUtils.isNotBlank(orderMemberType)) {
                    orderMember1.setSaveType(Boolean.parseBoolean(orderMemberType));
                }
                orderMemberList.add(orderMember1);
            }
            BigDecimal insurancePrice = BigDecimal.ZERO;
            if (orderInsurance.getPrice() != null) {
                insurancePrice = orderInsurance.getPrice();
            }
            //路线价格*(成人数+儿童数)+(单房成本+单房利润)*单人间数量+(双房成本+双房利润）*双人间数量+(三房成本+三房利润)*三人间数量
            // +(四房成本+四房利润)*四人间数量+(配房成本+配房利润)*配房数量+保险费*(成人数+儿童数)
            //路线
            //BigDecimal routePrice = route.getPrice().multiply(new BigDecimal(adultNum).add(new BigDecimal(childNum))).setScale(2, BigDecimal.ROUND_HALF_UP);
            BigDecimal routePrice =new BigDecimal("0");
            //单人房
            BigDecimal oneRoomPrice = (routeDate.getOneCost()).multiply(new BigDecimal(oneNum)).setScale(2, BigDecimal.ROUND_HALF_UP);
            //双人房
            BigDecimal twoRoomPrice = (routeDate.getTwoCost()).multiply(new BigDecimal(twoNum)).setScale(2, BigDecimal.ROUND_HALF_UP);
            //三人房
            BigDecimal threeRoomPrice = (routeDate.getThreeCost()).multiply(new BigDecimal(threeNum)).setScale(2, BigDecimal.ROUND_HALF_UP);
            //四人房
            BigDecimal fourRoomPrice = (routeDate.getFourCost()).setScale(2, BigDecimal.ROUND_HALF_UP);
            //配房价格
            BigDecimal arrangeRoomPrice = (routeDate.getArrangeCost()).multiply(new BigDecimal(arrangeNum)).setScale(2, BigDecimal.ROUND_HALF_UP);
            //价格=路线价格+房间价格
            BigDecimal routeTotalPrice = routePrice.add(oneRoomPrice).add(oneRoomPrice).add(twoRoomPrice).add(threeRoomPrice).add(fourRoomPrice).add(arrangeRoomPrice).setScale(2, BigDecimal.ROUND_HALF_UP);
            orderRoute.setPrice(routeTotalPrice);
            //总价格=路线价格+房间价格+保险价格
            BigDecimal totalPrice = routeTotalPrice.add(insurancePrice).setScale(2, BigDecimal.ROUND_HALF_UP);
            orderSys.setPrice(totalPrice);//主订单总价
            
            orderSys.setPrice1(routeDate.getOneCost());
            orderSys.setPrice2(routeDate.getTwoCost());
            orderSys.setPrice3(routeDate.getThreeCost());
            orderSys.setPrice4(routeDate.getFourCost());
            orderSys.setPricea(routeDate.getArrangeCost());
            
            orderRoute.setPrice1(routeDate.getOneCost());
            orderRoute.setPrice2(routeDate.getTwoCost());
            orderRoute.setPrice3(routeDate.getThreeCost());
            orderRoute.setPrice4(routeDate.getFourCost());
            orderRoute.setPricea(routeDate.getArrangeCost());
            

            String orderid = orderSysService.saveRouteOrder(orderSys, orderRoute, orderInsurance, orderMemberList);

			ajaxJson.getBody().put("payOrderNo", orderSys.getPayOrderNo());
            ajaxJson.setSuccess(true);
            ajaxJson.setErrorCode("0");
            ajaxJson.setMsg("当地参团添加订单成功");
            return ajaxJson;
        } catch (Exception e) {
            e.printStackTrace();
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_3);
            ajaxJson.setMsg(AppErrorUtils.error_3_desc);
            return ajaxJson;
        }
    }
    
    
    
    
    private AjaxJson saveRouteOrder(HttpServletRequest request) {
    	String uid = request.getParameter("uid");      //会员ID，登陆后返回
        String time = request.getParameter("time");        //时间，登陆后返回
        String key = request.getParameter("key");       //密钥，登陆后返回
        String languageid = request.getParameter("languageid");       //语言id
        String routeid = request.getParameter("routeid");       //路线id

        String contactsName = request.getParameter("contactsName");       //联系人姓名
        String contactsMobile = request.getParameter("contactsMobile");       //联系人电话
        String remark = request.getParameter("remark");       //备注
        String startDate = request.getParameter("startDate");       //开始时间
        String adultNum = request.getParameter("adultNum");       //大人
        String childNum = request.getParameter("childNum");       //小孩
        String oneNum = request.getParameter("oneNum");       //单人间
        String twoNum = request.getParameter("twoNum");       //双人间
        String threeNum = request.getParameter("threeNum");       //三人间
        String fourNum = request.getParameter("fourNum");       //四人间
        String arrangeNum = request.getParameter("arrangeNum");       //配房数量
        String insuranceid = request.getParameter("insuranceid");       //保险id
        /*出游人信息集合[{chineseName: "",englishName : "", certType: "",certNo : "",
        certValidDate: "", birthday: "", area: "", mobile: "",type : "" },{},{}]*/
        String orderMember = request.getParameter("orderMember");       //出游人信息
        AjaxJson ajaxJson = new AjaxJson();
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
        if (StringUtils.isBlank(routeid)) {
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_2);
            ajaxJson.setMsg("routeid传参为空");
            return ajaxJson;
        }
        if (StringUtils.isBlank(contactsName)) {
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_2);
            ajaxJson.setMsg("contactsName传参为空");
            return ajaxJson;
        }
        if (StringUtils.isBlank(contactsMobile)) {
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_2);
            ajaxJson.setMsg("contactsMobile传参为空");
            return ajaxJson;
        }
//		if (StringUtils.isBlank(remark)) {
//			ajaxJson.setSuccess(false);
//			ajaxJson.setErrorCode(AppErrorUtils.error_2);
//			ajaxJson.setMsg("remark传参为空");
//			return ajaxJson;
//		}
        if (StringUtils.isBlank(startDate)) {
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_2);
            ajaxJson.setMsg("startDate传参为空");
            return ajaxJson;
        }
        if (StringUtils.isBlank(adultNum)) {
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_2);
            ajaxJson.setMsg("adultNum传参为空");
            return ajaxJson;
        }
        if (StringUtils.isBlank(childNum)) {
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_2);
            ajaxJson.setMsg("childNum传参为空");
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
        if (StringUtils.isBlank(arrangeNum)) {
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_2);
            ajaxJson.setMsg("arrangeNum传参为空");
            return ajaxJson;
        }
//		if (StringUtils.isBlank(insuranceid)) {
//			ajaxJson.setSuccess(false);
//			ajaxJson.setErrorCode(AppErrorUtils.error_2);
//			ajaxJson.setMsg("insuranceid传参为空");
//			return ajaxJson;
//		}
        if (StringUtils.isBlank(orderMember)) {
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_2);
            ajaxJson.setMsg("orderMember传参为空");
            return ajaxJson;
        }
        
        return null;
    }
    
    private AjaxJson checkSaveCityRouteOrder(HttpServletRequest request) {
    	 String uid = request.getParameter("uid");      //会员ID，登陆后返回
         String time = request.getParameter("time");        //时间，登陆后返回
         String key = request.getParameter("key");       //密钥，登陆后返回
         String languageid = request.getParameter("languageid");       //语言id
         String routeid = request.getParameter("routeid");       //路线id

         String contactsName = request.getParameter("contactsName");       //联系人姓名
         String contactsMobile = request.getParameter("contactsMobile");       //联系人电话
         String remark = request.getParameter("remark");       //备注
         String startDate = request.getParameter("startDate");       //开始时间
         String adultNum = request.getParameter("adultNum");       //大人
         String childNum = request.getParameter("childNum");       //小孩
         String oneNum = request.getParameter("oneNum");       //单人间
         String twoNum = request.getParameter("twoNum");       //双人间
         String threeNum = request.getParameter("threeNum");       //三人间
         String fourNum = request.getParameter("fourNum");       //四人间
         String arrangeNum = request.getParameter("arrangeNum");       //配房数量
         String insuranceid = request.getParameter("insuranceid");       //保险id
         /*出游人信息集合[{chineseName: "",englishName : "", certType: "",certNo : "",
         certValidDate: "", birthday: "", area: "", mobile: "",type : "" },{},{}]*/
         String orderMember = request.getParameter("orderMember");       //出游人信息
    	
    	
        AjaxJson ajaxJson = new AjaxJson();
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
        if (StringUtils.isBlank(routeid)) {
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_2);
            ajaxJson.setMsg("routeid传参为空");
            return ajaxJson;
        }
        if (StringUtils.isBlank(contactsName)) {
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_2);
            ajaxJson.setMsg("contactsName传参为空");
            return ajaxJson;
        }
        if (StringUtils.isBlank(contactsMobile)) {
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_2);
            ajaxJson.setMsg("contactsMobile传参为空");
            return ajaxJson;
        }
//		if (StringUtils.isBlank(remark)) {
//			ajaxJson.setSuccess(false);
//			ajaxJson.setErrorCode(AppErrorUtils.error_2);
//			ajaxJson.setMsg("remark传参为空");
//			return ajaxJson;
//		}
        if (StringUtils.isBlank(startDate)) {
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_2);
            ajaxJson.setMsg("startDate传参为空");
            return ajaxJson;
        }
        if (StringUtils.isBlank(adultNum)) {
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_2);
            ajaxJson.setMsg("adultNum传参为空");
            return ajaxJson;
        }
        if (StringUtils.isBlank(childNum)) {
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_2);
            ajaxJson.setMsg("childNum传参为空");
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
        if (StringUtils.isBlank(arrangeNum)) {
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_2);
            ajaxJson.setMsg("arrangeNum传参为空");
            return ajaxJson;
        }


        if (StringUtils.isBlank(orderMember)) {
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_2);
            ajaxJson.setMsg("orderMember传参为空");
            return ajaxJson;
        }
        
        return null;
    }
    
	
	public  String date2String(Date date) {

		DateFormat format1 = new java.text.SimpleDateFormat("yyyy-MM-dd");
		return format1.format(date);

	}
}

