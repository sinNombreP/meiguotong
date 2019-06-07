package com.jeeplus.modules.app;

import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.modules.meiguotong.entity.guide.Guide;
import com.jeeplus.modules.meiguotong.entity.guideroute.GuideRoute;
import com.jeeplus.modules.meiguotong.entity.insurance.Insurance;
import com.jeeplus.modules.meiguotong.entity.insurance.InsuranceRelationMod;
import com.jeeplus.modules.meiguotong.entity.liner_line.LinerLine;
import com.jeeplus.modules.meiguotong.entity.product.Route;
import com.jeeplus.modules.meiguotong.entity.productcar.ProductCar;
import com.jeeplus.modules.meiguotong.entity.scenicspotticket.ScenicSpotTicket;
import com.jeeplus.modules.meiguotong.service.guide.GuideService;
import com.jeeplus.modules.meiguotong.service.guideroute.GuideRouteService;
import com.jeeplus.modules.meiguotong.service.insurance.InsuranceRelationModService;
import com.jeeplus.modules.meiguotong.service.liner_line.LinerLineService;
import com.jeeplus.modules.meiguotong.service.linerroom.LinerRoomService;
import com.jeeplus.modules.meiguotong.service.product.RouteService;
import com.jeeplus.modules.meiguotong.service.productcar.ProductCarService;
import com.jeeplus.modules.meiguotong.service.scenicspotticket.ScenicSpotTicketService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Scanner;

/**
 * 购物车接口
 */
@Controller
@RequestMapping(value = "${adminPath}/interface/buyCar")
public class AppBuyCarController {
    @Autowired
    private AppUtils appUtils;
    @Autowired
    private RouteService routeService;
    @Autowired
    private ProductCarService productCarService;
    @Autowired
    private LinerLineService linerLineService;
    @Autowired
    private ScenicSpotTicketService scenicSpotTicketService;
    @Autowired
    private  GuideService guideService;
    @Autowired
    private GuideRouteService guideRouteService;
    @Autowired
    private LinerRoomService linerRoomService;
    @Autowired
    private InsuranceRelationModService insuranceRelationModService;
    /**
     * 添加购物车接口
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "saveCar")
    @ResponseBody
    public AjaxJson saveCar(HttpServletRequest request, HttpServletResponse response, Model model) {

        AjaxJson ajaxJson = new AjaxJson();
        try {
            String type = request.getParameter("type");      //1.游客 2.会员
            String uid = request.getParameter("uid");      //会员ID，登陆后返回
            String time = request.getParameter("time");        //时间，登陆后返回
            String key = request.getParameter("key");       //密钥，登陆后返回
            String languageid = request.getParameter("languageid");       //语言id
            String typeid = request.getParameter("typeid");       //产品id
            String carType = request.getParameter("carType");       //1.常规路线2.当地参团3.当地玩家4.游轮5.景点门票6.导游路线

            String beginDate = request.getParameter("beginDate");       //出发时间
            String endDate = request.getParameter("endDate");       //出发时间
            String adultNum = request.getParameter("adultNum");       //大人数量
            String childNum = request.getParameter("childNum");       //小孩数量
            String oneNum = request.getParameter("oneNum");       //单人间
            String twoNum = request.getParameter("twoNum");       //双人间
            String threeNum = request.getParameter("threeNum");       //三人间
            String fourNum = request.getParameter("fourNum");       //四人间
            String arrangeNum = request.getParameter("arrangeNum");   //配房
            String price = request.getParameter("price");       //价格
            String roomids = request.getParameter("roomids");       //游轮房间


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
            if (StringUtils.isBlank(carType)) {
                ajaxJson.setSuccess(false);
                ajaxJson.setErrorCode(AppErrorUtils.error_2);
                ajaxJson.setMsg("carType传参为空");
                return ajaxJson;
            }
            if (StringUtils.isBlank(price)) {
                ajaxJson.setSuccess(false);
                ajaxJson.setErrorCode(AppErrorUtils.error_2);
                ajaxJson.setMsg("price传参为空");
                return ajaxJson;
            }
            if(Integer.parseInt(carType)==1 || Integer.parseInt(carType)==2 ||
            		Integer.parseInt(carType)==3 || Integer.parseInt(carType)==5 ||
            		Integer.parseInt(carType)==6){
            	
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
            }
            if(Integer.parseInt(carType)==1 || Integer.parseInt(carType)==2){
            	
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
            }
            if(Integer.parseInt(carType)==3){
            	if (StringUtils.isBlank(endDate)) {
                    ajaxJson.setSuccess(false);
                    ajaxJson.setErrorCode(AppErrorUtils.error_2);
                    ajaxJson.setMsg("endDate传参为空");
                    return ajaxJson;
                }
            }
            if(Integer.parseInt(carType)==4){
            	if (StringUtils.isBlank(roomids)) {
                    ajaxJson.setSuccess(false);
                    ajaxJson.setErrorCode(AppErrorUtils.error_2);
                    ajaxJson.setMsg("roomids传参为空");
                    return ajaxJson;
                }
            }
            ProductCar productCar = new ProductCar();
            productCar.setMemberid(Integer.parseInt(uid));        // 用户id
            productCar.setType(Integer.parseInt(carType));        // 1.常规路线2.当地参团3.当地玩家4.游轮5.景点门票
            productCar.setTypeid(Integer.parseInt(typeid));        // 产品id
            productCar.setLanguageid(Integer.parseInt(languageid));        // 语言id
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            if (StringUtils.isNotBlank(beginDate)) {
                Date bt = sdf.parse(beginDate);
                productCar.setBeginDate(bt);        // 出发时间
            }
            if (StringUtils.isNotBlank(endDate)) {
                Date et = sdf.parse(endDate);
                productCar.setEndDate(et);        // 结束时间
            }
            if (StringUtils.isNotBlank(adultNum)) {
                productCar.setAdultNum(Integer.parseInt(adultNum));        // 大人数量
            }else{
            	 productCar.setAdultNum(0);        // 大人数量
            }
            if (StringUtils.isNotBlank(childNum)) {
                productCar.setChildNum(Integer.parseInt(childNum));        // 孩子数量
            }else{
                productCar.setChildNum(0);        // 孩子数量
            }
            if (StringUtils.isNotBlank(oneNum)) {
                productCar.setOneNum(Integer.parseInt(oneNum));        // 单人间数量
            }
            if (StringUtils.isNotBlank(twoNum)) {
                productCar.setTwoNum(Integer.parseInt(twoNum));        // 双人间数量
            }
            if (StringUtils.isNotBlank(threeNum)) {
                productCar.setThreeNum(Integer.parseInt(threeNum));        // 三人间数量
            }
            if (StringUtils.isNotBlank(fourNum)) {
                productCar.setFourNum(Integer.parseInt(fourNum));        // 四人间数量
            }
            if (StringUtils.isNotBlank(arrangeNum)) {
                productCar.setArrangeNum(Integer.parseInt(arrangeNum));        // 配房数量
            }
            if (StringUtils.isNotBlank(price)) {
                productCar.setPrice(new BigDecimal(price));        // 价格
            }
            if (StringUtils.isNotBlank(roomids)) {
                productCar.setRoomids(roomids);      // 游轮房间id
            }
            
            productCarService.save(productCar);
            ajaxJson.setSuccess(true);
            ajaxJson.setErrorCode("0");
            ajaxJson.setMsg("添加购物车成功");
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
     * 购物车删除
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "deleteCar")
    @ResponseBody
    public AjaxJson deleteCar(HttpServletRequest request, HttpServletResponse response, Model model) {

        AjaxJson ajaxJson = new AjaxJson();
        String type = request.getParameter("type");      //1.游客 2.会员
        String uid = request.getParameter("uid");      //会员ID，登陆后返回
        String time = request.getParameter("time");        //时间，登陆后返回
        String key = request.getParameter("key");       //密钥，登陆后返回
        String languageid = request.getParameter("languageid");       //语言id
        String carids = request.getParameter("carids");       //购物车id
        
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
        if (StringUtils.isBlank(carids)) {
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_2);
            ajaxJson.setMsg("carids传参为空");
            return ajaxJson;
        }
        
        try {
        	productCarService.deleteCar(carids);
            ajaxJson.setSuccess(true);
            ajaxJson.setErrorCode("0");
            ajaxJson.setMsg("购物车删除成功");
            return ajaxJson;
        } catch (Exception e) {
            e.printStackTrace();
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_3);
            ajaxJson.setMsg(AppErrorUtils.error_3_desc);
            return ajaxJson;
        }
    }
  //购物车类型数量
    @ResponseBody
    @RequestMapping(value="productCarNum")
    public AjaxJson productCarNum(HttpServletRequest request, HttpServletResponse response, Model model){
    	 AjaxJson ajaxJson = new AjaxJson();
         String uid = request.getParameter("uid");      //会员ID，登陆后返回
         String time = request.getParameter("time");        //时间，登陆后返回
         String key = request.getParameter("key");       //密钥，登陆后返回
         String languageid = request.getParameter("languageid");       //密钥，登陆后返回
         
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
        	List<ProductCar> productNum=productCarService.findNumGroupByType(Integer.valueOf(uid),languageid);
             ajaxJson.setMsg("获取购物车数量成功");
			ajaxJson.getBody().put("productNum", productNum);
            ajaxJson.setSuccess(true);
            ajaxJson.setErrorCode("0");
		} catch (Exception e) {
			e.printStackTrace();
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_3);
            ajaxJson.setMsg(AppErrorUtils.error_3_desc);
            return ajaxJson;
		}
         return ajaxJson;
    }
  //购物车列表
    @ResponseBody
    @RequestMapping(value="productCar")
    public AjaxJson productCar(HttpServletRequest request, HttpServletResponse response, Model model){
    	 AjaxJson ajaxJson = new AjaxJson();
         String uid = request.getParameter("uid");      //会员ID，登陆后返回
         String time = request.getParameter("time");        //时间，登陆后返回
         String key = request.getParameter("key");       //密钥，登陆后返回
         String carType=request.getParameter("carType");//类型
         String pageNo = request.getParameter("pageNo");       //页码
         String languageid = request.getParameter("languageid");       //语言
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
        	 String pageSize1 = request.getParameter("pageSize");       //每页数
 			Integer pageSize;
 			if(StringUtils.isBlank(pageSize1)){
 				pageSize = appUtils.getPageSize();
 			}else{
 				pageSize = Integer.parseInt(pageSize1);
 			}
 			
 			Page<ProductCar> page=new Page<ProductCar>();
			page.setPageNo(Integer.parseInt(pageNo));
			page.setPageSize1(pageSize);
			ProductCar productCar=new ProductCar();
			productCar.setMemberid(Integer.valueOf(uid));
			productCar.setType(Integer.valueOf(carType));
			productCar.setLanguageid(Integer.parseInt(languageid));
			Page<ProductCar> page1 = productCarService.findproductCarByMemberId(page, productCar);
			
			List<ProductCar> list = page1.getList();
			for (ProductCar p:list) {
				//1.常规路线2.当地参团3.当地玩家4.游轮5.景点门票6.导游路线
				if (p.getType()==1 || p.getType()==2) {
					Route route=routeService.findRouteStockByDate(p.getTypeid(), p.getBeginDate());
					if (checkDate(route.getAdvanceDay(),p.getBeginDate())) {//提前预订天数
						p.setStatus(0);
					}
					if (route ==null || route.getStock()==null) {//禁卖
						p.setStatus(0);
					}
					if (route.getStock()>0 && (route.getStock()-route.getNum()<p.getChildNum()+p.getAdultNum())) {//票数不足
						p.setStatus(0);
					}
				}else if (p.getType()==3) {
					
				}else if (p.getType()==4) {
					LinerLine linerLine=linerLineService.get(p.getTypeid().toString());
					if (checkDate(linerLine.getAdvanceNum(),p.getBeginDate())) {//提前预订天数
						p.setStatus(0);
					}
				}else if (p.getType()==5) {
					ScenicSpotTicket scenicSpot=scenicSpotTicketService.checkDateByScenicid(p.getTypeid());
					if (checkDate(0,scenicSpot.getValidityDate())) {//有效期
						p.setStatus(0);
					}
					if (checkDate(scenicSpot.getRule(),p.getBeginDate())) {//提前预订天数
						p.setStatus(0);
					}
				}else if (p.getType()==6) {
					
				}
				if(StringUtils.isNotBlank(p.getImg())){
				    p.setImg(p.getImg().split(",")[0]);
                }
			}
			int totalPage = page1.getTotalPage();
			if(Integer.parseInt(pageNo) > totalPage){
				ajaxJson.getBody().put("productCars", new ArrayList<>());
			}else{
				ajaxJson.getBody().put("productCars", list);
			}
			
			ajaxJson.getBody().put("totalPage", totalPage);
            ajaxJson.setSuccess(true);
            ajaxJson.setErrorCode("0");
            ajaxJson.setMsg("获取购物车列表成功");
		} catch (Exception e) {
			e.printStackTrace();
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_3);
            ajaxJson.setMsg(AppErrorUtils.error_3_desc);
            return ajaxJson;
		}
         return ajaxJson;
    }
    //清空失效产品
    @ResponseBody
    @RequestMapping(value="cancleTimeOuteProductCar")
    public AjaxJson cancleTimeOuteProductCar(HttpServletRequest request, HttpServletResponse response, Model model){
    	 AjaxJson ajaxJson = new AjaxJson();
         String uid = request.getParameter("uid");      //会员ID，登陆后返回
         String time = request.getParameter("time");        //时间，登陆后返回
         String key = request.getParameter("key");       //密钥，登陆后返回
         String languageid = request.getParameter("languageid");       //语言
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
			List<ProductCar> productCars=productCarService.findproductCarByMemberId(Integer.valueOf(uid),languageid);
			for (ProductCar p:productCars) {
				//1.常规路线2.当地参团3.当地玩家4.游轮5.景点门票6.导游路线
				if (p.getType()==1 || p.getType()==2) {
					Route route=routeService.findRouteStockByDate(p.getTypeid(), p.getBeginDate());
					if (checkDate(route.getAdvanceDay(),p.getBeginDate())) {
						productCarService.deleteCar(p.getId());
					}
					if (route ==null || route.getStock()==null) {
						productCarService.deleteCar(p.getId());
					}
					if (route.getStock()>0 && (route.getStock()-route.getNum()<p.getChildNum()+p.getAdultNum())) {
						productCarService.deleteCar(p.getId());
					}
				}else if (p.getType()==3) {
					
				}else if (p.getType()==4) {
					LinerLine linerLine=linerLineService.get(p.getTypeid().toString());
					if (checkDate(linerLine.getAdvanceNum(),p.getBeginDate())) {
						productCarService.deleteCar(p.getId());
					}
				}else if (p.getType()==5) {
					ScenicSpotTicket scenicSpot=scenicSpotTicketService.checkDateByScenicid(p.getTypeid());
					
					if (checkDate(0,scenicSpot.getValidityDate())) {
						productCarService.deleteCar(p.getId());
					}
					if (checkDate(scenicSpot.getRule(),p.getBeginDate())) {
						productCarService.deleteCar(p.getId());
					}
				}else if (p.getType()==6) {
					
				}
			}
            ajaxJson.setSuccess(true);
            ajaxJson.setErrorCode("0");
            ajaxJson.setMsg("清空失效宝贝成功");
		} catch (Exception e) {
			e.printStackTrace();
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_3);
            ajaxJson.setMsg(AppErrorUtils.error_3_desc);
            return ajaxJson;
		}
         return ajaxJson;
    }
    
  //购物车订单详情
    @ResponseBody
    @RequestMapping(value="productCarDetail")
    public AjaxJson productCarDetail(HttpServletRequest request, HttpServletResponse response, Model model){
    	 AjaxJson ajaxJson = new AjaxJson();
         String uid = request.getParameter("uid");      //会员ID，登陆后返回
         String time = request.getParameter("time");        //时间，登陆后返回
         String key = request.getParameter("key");       //密钥，登陆后返回
         String id = request.getParameter("id");       //购物车id
         String languageid = request.getParameter("languageid");       //购物车id
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
			List<ProductCar> productCars=productCarService.findProductCarById(id);//购物车列表信息
			if (productCars !=null && productCars.size()>0) {
				for( ProductCar p:productCars){
					//1.包车租车2.短程接送3.接送机4常规路线5.当地参团6.游轮7.景点门票8.当地玩家9.旅游定制,
					Integer type = 0;
					if (p.getType()==1 || p.getType()==2) {
						Route route=routeService.get(p.getTypeid().toString());//常规路线，当地参团
						if (p.getType()==1) {
							type = 4;
						}
						if (p.getType()==2) {
							type = 5;
						}
						p.setRoute(route);
						p.setCityid(route.getCityid());
						p.setCityName(route.getCityName());
					}else if (p.getType()==3) {
						Guide guide=guideService.productCar_findGuide(p.getTypeid());//导游
						type = 8;
						p.setGuide(guide);
                        p.setCityid(guide.getCityid());
                        p.setCityName(guide.getCityName());
					}else if (p.getType()==4) {
						LinerLine linerLine=linerLineService.get(p.getTypeid().toString());//邮轮
						linerLine.setLinerRoomList(linerRoomService.productCar_findLinerRoom(p.getRoomids()));//邮轮房间
						type = 6;
						p.setLinerLine(linerLine);
                        p.setCityid(linerLine.getCityid());
                        p.setCityName(linerLine.getCityName());
					}else if (p.getType()==5) {
						ScenicSpotTicket scenicSpotTicket=scenicSpotTicketService.productCar_findScenice(p.getTypeid());//景点
						type = 7;
						p.setScenicSpotTicket(scenicSpotTicket);
                        p.setCityid(scenicSpotTicket.getCityid());
                        p.setCityName(scenicSpotTicket.getCityName());
					}else if (p.getType()==6) {
						GuideRoute guideRoute=guideRouteService.productCar_findGuideRoute(p.getTypeid());//导游路线
						type = 8;
						p.setGuideRoute(guideRoute);
                        p.setCityid(guideRoute.getCityid());
                        p.setCityName(guideRoute.getCityName());
					}
					
					InsuranceRelationMod insuranceRelationMod = new InsuranceRelationMod();
		            insuranceRelationMod.setLanguageId(Integer.parseInt(languageid));
		            insuranceRelationMod.setType(type);
		            List<Insurance> list = insuranceRelationModService.getInsurance(insuranceRelationMod);
		            p.setInsuranceList(list);
				}
			}
			ajaxJson.getBody().put("productCars",productCars);
            ajaxJson.setSuccess(true);
            ajaxJson.setErrorCode("0");
            ajaxJson.setMsg("获取购物车详情成功");
		} catch (Exception e) {
			e.printStackTrace();
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_3);
            ajaxJson.setMsg(AppErrorUtils.error_3_desc);
            return ajaxJson;
		}
         return ajaxJson;
    }
   
    public boolean checkDate(int day,Date beginDate) throws ParseException{
    	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
    	Calendar calendar=Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DAY_OF_YEAR, Integer.valueOf(day));
		Date date=calendar.getTime();
		String date1=sdf.format(beginDate);
		String date2=sdf.format(date);
		Date date3=sdf.parse(date1);
		Date date4=sdf.parse(date2);
		return date3.before(date4);//date3小于date4返回TRUE，当date3大于等于date4时，返回false
    }
    
    
    /**
     * 购物车下单
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "saveOrderByCar")
    @ResponseBody
    public AjaxJson saveOrderByCar(HttpServletRequest request, HttpServletResponse response, Model model) {

        AjaxJson ajaxJson = new AjaxJson();
        String type = request.getParameter("type");      //1.游客 2.会员
        String uid = request.getParameter("uid");      //会员ID，登陆后返回
        String time = request.getParameter("time");        //时间，登陆后返回
        String key = request.getParameter("key");       //密钥，登陆后返回
        String languageid = request.getParameter("languageid");       //语言id
        String contactsName = request.getParameter("contactsName");       //联系人姓名
        String contactsMobile = request.getParameter("contactsMobile");       //联系人电话
        String remark = request.getParameter("remark");       //备注
        String carOrder = request.getParameter("carOrder");       //订单详细信息

       
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
        if (StringUtils.isBlank(carOrder) || "[]".equals(carOrder)) {
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_2);
            ajaxJson.setMsg("carOrder传参为空");
            return ajaxJson;
        }
        
        try {
        	
        	ajaxJson=productCarService.saveOrderByCar(uid,languageid,contactsName,contactsMobile,remark,carOrder);
        	
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

