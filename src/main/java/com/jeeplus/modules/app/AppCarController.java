package com.jeeplus.modules.app;

import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.modules.meiguotong.entity.car.CarInfo;
import com.jeeplus.modules.meiguotong.entity.car.CarService;
import com.jeeplus.modules.meiguotong.entity.car.CarTimePrice;
import com.jeeplus.modules.meiguotong.entity.guide.Guide;
import com.jeeplus.modules.meiguotong.entity.guide.GuideDate;
import com.jeeplus.modules.meiguotong.entity.hotel.Hotel;
import com.jeeplus.modules.meiguotong.entity.hotel.HotelRoom;
import com.jeeplus.modules.meiguotong.entity.hotel.HotelRoomDate;
import com.jeeplus.modules.meiguotong.entity.hotel.OrderHotel;
import com.jeeplus.modules.meiguotong.entity.insurance.Insurance;
import com.jeeplus.modules.meiguotong.entity.insurance.OrderInsurance;
import com.jeeplus.modules.meiguotong.entity.order.OrderSys;
import com.jeeplus.modules.meiguotong.entity.ordercar.OrderCar;
import com.jeeplus.modules.meiguotong.entity.ordercar.OrderCarDate;
import com.jeeplus.modules.meiguotong.entity.orderguide.OrderGuide;
import com.jeeplus.modules.meiguotong.entity.orderhotelroom.OrderHotelRoom;
import com.jeeplus.modules.meiguotong.entity.ordermember.OrderMember;
import com.jeeplus.modules.meiguotong.service.car.CarInfoService;
import com.jeeplus.modules.meiguotong.service.car.CarServiceService;
import com.jeeplus.modules.meiguotong.service.car.CarTimePriceService;
import com.jeeplus.modules.meiguotong.service.guide.GuideDateService;
import com.jeeplus.modules.meiguotong.service.guide.GuideService;
import com.jeeplus.modules.meiguotong.service.hotel.HotelRoomDateService;
import com.jeeplus.modules.meiguotong.service.hotel.HotelRoomService;
import com.jeeplus.modules.meiguotong.service.hotel.HotelService;
import com.jeeplus.modules.meiguotong.service.insurance.InsuranceService;
import com.jeeplus.modules.meiguotong.service.order.OrderSysService;
import com.jeeplus.modules.meiguotong.service.scenicspot.ScenicSpotService;
import com.jeeplus.modules.meiguotong.service.travel.TravelCustomizationService;
import com.jeeplus.modules.meiguotong.service.travel.TravelDetailsService;
import com.jeeplus.modules.sys.utils.CodeGenUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


@Controller
@RequestMapping(value = "${adminPath}/interface/car")
public class AppCarController {
	@Autowired
	private AppUtils appUtils;
	@Autowired
	private CarServiceService carServiceService;
	@Autowired
	private ScenicSpotService scenicSpotService;
	@Autowired
	private TravelCustomizationService travelCustomizationService;
	@Autowired
	private TravelDetailsService travelDetailsService;
	@Autowired
	private InsuranceService insuranceService;
	@Autowired
	private OrderSysService orderSysService;
	@Autowired
	private GuideDateService guideDateService;
	@Autowired
	private CarInfoService carInfoService;
	@Autowired
	private CarTimePriceService carTimePriceServcie;
	@Autowired
	private HotelRoomService hotelRoomService;
	@Autowired
	private HotelRoomDateService hotelRoomDateService;
	@Autowired
	private HotelService hotelService;
	@Autowired
	private GuideService guideService;
	/**
	 * 获取车辆业务类型接口
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "selectCarService")
	@ResponseBody
	public AjaxJson selectCarService(HttpServletRequest request, HttpServletResponse response, Model model) {
		
		AjaxJson ajaxJson = new AjaxJson();
		try {
			String type = request.getParameter("type");      //1.游客 2.会员
			String uid = request.getParameter("uid");      //会员ID，登陆后返回
			String time = request.getParameter("time");		//时间，登陆后返回
			String key = request.getParameter("key");       //密钥，登陆后返回
			String languageid = request.getParameter("languageid");       //语言id
			
			if(!StringUtils.isBlank(type)&&Integer.parseInt(type)==2){
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
			
			CarService carService = new CarService();
			carService.setLanguage(Integer.parseInt(languageid));
			
			List<CarService> list=carServiceService.selectCarService(carService);
			
			if(list!=null&&list.size()>0){
				ajaxJson.getBody().put("list", list);
			}else{
				ajaxJson.getBody().put("list", new ArrayList<>());
			}
			ajaxJson.setSuccess(true);
			ajaxJson.setErrorCode("0");
			ajaxJson.setMsg("获取车辆业务类型成功");  
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
	 * 包车租车生成订单接口
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "saveCarOrder")
	@ResponseBody
	public AjaxJson saveCarOrder(HttpServletRequest request, HttpServletResponse response, Model model) {
	
		AjaxJson ajaxJson = new AjaxJson();
		try {
			String uid = request.getParameter("uid");      //会员ID，登陆后返回
			String time = request.getParameter("time");		//时间，登陆后返回
			String key = request.getParameter("key");       //密钥，登陆后返回
			String languageid = request.getParameter("languageid");       //语言id

			String contactsName = request.getParameter("contactsName");       //联系人姓名
			String contactsMobile = request.getParameter("contactsMobile");       //联系人电话
			String remark = request.getParameter("remark");       //备注
			String startCity = request.getParameter("startCity");  //出发城市
			String startAddress = request.getParameter("startAddress");  //出发详细地址
			String startDate = request.getParameter("startDate");       //上车时间
			String endDate = request.getParameter("endDate");       //结束时间
			String dayNum = request.getParameter("dayNum");       //天数
			
			String adultNum = request.getParameter("adultNum");       //大人
			String childNum = request.getParameter("childNum");       //小孩
			String bagNum = request.getParameter("bagNum");  //包裹
			String carInfor = request.getParameter("carInfor");  //汽车信息
			

			String guideid = request.getParameter("guideid");       //导游id
			String guideType = request.getParameter("guideType");       //导游类型
			String travelInfor = request.getParameter("travelInfor");       //行程信息
			
			String insuranceid = request.getParameter("insuranceid");       //保险id
			/*出游人信息集合[{chineseName: "",englishName : "", certType: "",certNo : "", 
			certValidDate: "", birthday: "", area: "", mobile: "",type : "" },{},{}]*/
			String orderMember = request.getParameter("orderMember");       //出游人信息
			
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
			if (StringUtils.isBlank(orderMember)) {
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_2);
				ajaxJson.setMsg("orderMember传参为空");
				return ajaxJson;
			}
			if (StringUtils.isBlank(carInfor)) {
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_2);
				ajaxJson.setMsg("carInfor传参为空");
				return ajaxJson;
			}
			if (StringUtils.isBlank(bagNum)) {
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_2);
				ajaxJson.setMsg("bagNum传参为空");
				return ajaxJson;
			}
			if (StringUtils.isBlank(dayNum)) {
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_2);
				ajaxJson.setMsg("dayNum传参为空");
				return ajaxJson;
			}
		
			if (StringUtils.isBlank(startAddress)) {
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_2);
				ajaxJson.setMsg("startAddress传参为空");
				return ajaxJson;
			}
			if (StringUtils.isBlank(startCity)) {
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_2);
				ajaxJson.setMsg("startCity传参为空");
				return ajaxJson;
			}
			
			String orderTitle = "";//订单标题
			
			OrderSys orderSys = new OrderSys();
			orderSys.setPayOrderNo(CodeGenUtils.getNowDate());
			OrderInsurance orderInsurance = new OrderInsurance();
			
			orderSys.setType(1);		// 订单类型1.包车租车2.短程接送3.接送机4常规路线5.当地参团6.游轮7.景点门票
										//8.当地玩家9.酒店10.保险11.旅游定制12导游13.商务定制14.商务旅游',
			orderSys.setFatherid(0);		// 父id
			orderSys.setMemberid(Integer.parseInt(uid));      //下单人
			orderSys.setLanguageid(Integer.parseInt(languageid));    //语言id

			orderSys.setContactsName(contactsName);	//联系人姓名
			orderSys.setContactsMobile(contactsMobile);	//联系人电话
			orderSys.setRemark(remark);			//备注
			orderSys.setPeopleNum(Integer.parseInt(adultNum)+Integer.parseInt(childNum));

			orderSys.setTitle(orderTitle);			//订单标题
			orderSys.setAdultNum(Integer.parseInt(adultNum));		// 大人数量
			orderSys.setChildNum(Integer.parseInt(childNum));		// 孩子数量
			orderSys.setBagNum(Integer.parseInt(bagNum));		// 包裹数量
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); 
			Date bt=sdf.parse(startDate); 
			Date et=sdf.parse(endDate); 
			orderSys.setBeginDate(bt);       //开始时间
			orderSys.setEndDate(et);       //开始时间
			orderSys.setStatus(1);
			
			SimpleDateFormat sdf1=new SimpleDateFormat("yyyyMMdd"); 
			String bt1=sdf1.format(new Date()); 
			//订单总价
			BigDecimal orderPrice = BigDecimal.ZERO;
			//车辆订单
			List<OrderCar> orderCarList= new ArrayList<>();
			if(StringUtils.isNotBlank(carInfor)){//是否选择车辆
				 JSONArray jsonArray = JSONArray.fromObject(carInfor); 
				 for(int i=0;i<jsonArray.size();i++){
					 OrderCar orderCar1 = new OrderCar();
					 //取出数组第i个元素 
					 JSONObject jpro = jsonArray.getJSONObject(i);
					 String carid = jpro.getString("carid"); 
					 String carType = jpro.getString("carType");  
					 String dayDate = jpro.getString("dayDate");
					 String dayRange = jpro.getString("dayRange");
					 //获取车辆信息
					 CarInfo car = carInfoService.get(carid);
					 String orderNo3 = "VE-"+car.getAgentid()+"-"+bt1+"[]";
					 orderCar1.setOrderNo(orderNo3); //'订单号',后面替换[]
					 orderCar1.setType(1); //'订单类型1.包车租车2.短程接送3.接送机4.定制'
					 orderCar1.setName(car.getCarName()); //'订单标题'
					 orderCar1.setAdultNum(Integer.parseInt(adultNum)); //'大人数量'
					 orderCar1.setChildNum(Integer.parseInt(childNum)); //'孩子数量'
					 orderCar1.setBagNum(Integer.parseInt(bagNum)); //'包裹数量'
					 orderCar1.setStartCity(startCity); //'出发城市'
					 orderCar1.setStartAddress(startAddress); //'出发详细地址'
					 orderCar1.setBeginDate(bt); //'上车时间'
					 orderCar1.setEndDate(et);  //结束时间
					 orderCar1.setContactsName(contactsName); //'联系人姓名'
					 orderCar1.setContactsMobile(contactsMobile); //'联系人电话'
					 orderCar1.setRemark(remark); //'备注'
					 orderCar1.setCarTime(dayDate); //行程行驶时间
					 orderCar1.setCarDistance(dayRange);//行程行驶距离
					 orderCar1.setAgentid(car.getAgentid());
					 //查询汽车价格
					 CarTimePrice carTimePrice = new CarTimePrice();
					 carTimePrice.setCarid(carid);
					 carTimePrice.setBusinessType(carType);
					 //carTimePrice.setBusiDate(bt);
					 CarTimePrice carTimePrice1 = carTimePriceServcie.getPrice(carTimePrice);
					 BigDecimal carPrice = carTimePrice1.getEmptyPrice()
							 .add(carTimePrice1.getPrice())
							 .add(carTimePrice1.getFoodPrice())
							 .add(carTimePrice1.getHeadPrice())
							 .add(carTimePrice1.getStartPrice())
							 .setScale(2,BigDecimal.ROUND_HALF_UP);
					 orderCar1.setPrice(carPrice); //'金额'
					 orderPrice.add(carPrice);
					 orderCar1.setCarid(carid); //'汽车id'
					 orderCar1.setCarName(car.getCarName()); //'汽车名称'
					 orderCar1.setStatus(1); //'订单状态1.待付款2.待确定，3.待出行，4.待评价 5.已完成6.取消订单7.申请退款8退款通过9.退款不通过'
					 orderCar1.setLanguageId(Integer.parseInt(languageid)); //'语言'
					 orderCar1.setMemberId(uid);  //'下单人ID'
					 orderCar1.setUseDate(bt); //'使用时间'
					 orderCar1.setSeatNum(car.getSeatNum());//汽车座位数
					 orderCar1.setComfort(car.getComfort().toString());//汽车舒适度
					 orderCar1.setCarBagNum(car.getBagNum());//汽车包裹容量
					 
					 orderCarList.add(orderCar1);
				 }
			}
			
			
			//包车租车订单详情
			List<OrderCarDate> orderCarDateList= new ArrayList<>();
			//酒店订单
			List<OrderHotel> orderHotelList= new ArrayList<>();
			SimpleDateFormat sdf2=new SimpleDateFormat("yyyy-MM-dd"); 
			SimpleDateFormat sdf3=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
			if(StringUtils.isNotBlank(travelInfor)){
				JSONArray jsonArray = JSONArray.fromObject(travelInfor); 
				 for(int i=0;i<jsonArray.size();i++){
					 //取出数组第i个元素 
					 JSONObject jpro = jsonArray.getJSONObject(i);
					 String date = jpro.getString("date"); //日期
					 String day = jpro.getString("day"); //第几天
					 String cityid = jpro.getString("cityid"); //城市id
					 String cityName = jpro.getString("cityName"); //城市名称
					 String endCityid = jpro.getString("endCityid"); //结束城市id
					 String endCityName = jpro.getString("endCityName"); //结束城市名称
					 String range = jpro.getString("range"); //行程类型1.接机2.本地城市3.附近城市',
					 String serviceid = jpro.getString("serviceid"); //业务id
					 String serviceTitle = jpro.getString("serviceTitle"); //业务标题
					 String airNo = jpro.getString("airNo"); //航班号
					 String hotelInforDetails = jpro.getString("hotelInforDetails"); //酒店信息
					 if (StringUtils.isBlank(date)||StringUtils.isBlank(day)||
							 StringUtils.isBlank(cityid)||StringUtils.isBlank(cityName)|| 
							 StringUtils.isBlank(endCityid)||StringUtils.isBlank(endCityName)) {
						ajaxJson.setSuccess(false);
						ajaxJson.setErrorCode(AppErrorUtils.error_2);
						ajaxJson.setMsg("行程信息不完善");
						return ajaxJson;
					}
					 OrderCarDate orderCarDate = new OrderCarDate();
					 //orderCarDate.setOrderid(orderid); //'订单id（orderSys主订单id）',
					 orderCarDate.setRange(Integer.parseInt(range));//'行程类型1.接机2.本地城市3.附近城市'',',
					 orderCarDate.setServiceid(Integer.parseInt(serviceid));//'业务id',
					 orderCarDate.setTitle(serviceTitle);//'业务名称',
					 orderCarDate.setCityid(Integer.parseInt(cityid));//'城市id',
					 orderCarDate.setCity(cityName);//'城市名称',
					 orderCarDate.setEndCityid(Integer.parseInt(endCityid));//'到达城市id',
					 orderCarDate.setEndCity(endCityName);// '到达城市名称',
					 orderCarDate.setAirNum(airNo);//'航班号',
					 try {
						 String sendAirport = jpro.getString("sendAirport"); //出发机场
						 String sendDate = jpro.getString("sendDate"); //出发时间
						 String reachAirport = jpro.getString("reachAirport"); //到达机场
						 String reachDate = jpro.getString("reachDate"); //到达时间
						 orderCarDate.setSendAirport(sendAirport);//'飞机起飞机场',
						 orderCarDate.setReachAirport(reachAirport);// '到达机场',
						 orderCarDate.setSendDate(sdf2.parse(sendDate));//'飞机起飞时间',
						 orderCarDate.setReachDate(sdf2.parse(reachDate));//'到达时间',
					 } catch (Exception e) {
					 }
					 orderCarDate.setDay(Integer.parseInt(day));//'第几天',
					 try {
						 orderCarDate.setDayTime(sdf3.parse(date));//'日期',
					 } catch (Exception e) {
					 }
					 
					 orderCarDateList.add(orderCarDate);
					 //酒店
					 List<OrderHotel> orderHotelList1= new ArrayList<>();
					 if(StringUtils.isNotBlank(hotelInforDetails)){//是否预订酒店
							JSONArray jsonArrayHotel = JSONArray.fromObject(hotelInforDetails); 
							 for(int j=0;j<jsonArrayHotel.size();j++){
								 OrderHotel orderHotel = new OrderHotel();
								 //取出数组第i个元素 
								 JSONObject jproHotel = jsonArrayHotel.getJSONObject(j);
								 String hotelid = jproHotel.getString("hotelid"); //酒店id
								 String roomid = jproHotel.getString("roomid"); //房间id
								 String num = jproHotel.getString("num"); //房间数量
								 
								 if (StringUtils.isBlank(hotelid)||StringUtils.isBlank(roomid)||
										 StringUtils.isBlank(num)) {
										ajaxJson.setSuccess(false);
										ajaxJson.setErrorCode(AppErrorUtils.error_2);
										ajaxJson.setMsg("酒店信息不完善");
										return ajaxJson;
									}
								 //获取酒店信息
								 Hotel hotel = hotelService.get(hotelid);
								 orderHotel.setName(hotel.getName()); //'订单标题'
								 orderHotel.setHotelId(Integer.parseInt(hotelid)); //'酒店id'
								 String orderNo4 = "HT-"+hotel.getAgentid()+"-"+bt1+"[]"+"-"+hotel.getId();
								 orderHotel.setOrderNo(orderNo4); //'订单号',后面替换[]
								 orderHotel.setProductName(hotel.getName()); //'酒店名称'
								 orderHotel.setMemberId(Integer.parseInt(uid)); //'下单人id'
								 orderHotel.setStatus(1); //'订单状态  1 待付款 2 待确认 3 待出行 4 待点评 5 已完成 6 已取消 7 申请退款 8 退款成功 9 退款失败'
								 orderHotel.setContacts(contactsName); //'联系人'
								 orderHotel.setContactMobile(contactsMobile); //'联系电话'
								 orderHotel.setContactRemark(remark); //'备注'
								 orderHotel.setLanguageId(Integer.parseInt(languageid)); //'语言id'
								 Date useDate=sdf.parse(date);
								 orderHotel.setStartDate(useDate);  //'使用时间'
								 orderHotel.setAgentid(hotel.getAgentid());  //'供应商ID'
								 //订单价格
								 BigDecimal orderHotelPrice = BigDecimal.ZERO;
								 //酒店房间集合
								 List<OrderHotelRoom> orderHotelRoomList= new ArrayList<>();
								 
								 OrderHotelRoom orderHotelRoom = new OrderHotelRoom();
								 //获取酒店房间信息
								 HotelRoom hotelRoom = hotelRoomService.get(roomid);
			//					 orderHotelRoom.setOrderHotelId(orderHotelId); //'酒店订单id'
								 orderHotelRoom.setRoomId(Integer.parseInt(roomid)); //'房间id'
								 orderHotelRoom.setRoomName(hotelRoom.getRoomName()); //'房间名称'
								 orderHotelRoom.setRoomDate(useDate); //'日期'
								 orderHotelRoom.setRoomDay(Integer.parseInt(day)); //'行程第几天'
								 //获取酒店房间价格库存
								 HotelRoomDate hotelRoomDate = new HotelRoomDate(); 
								 hotelRoomDate.setPriceDate(useDate);
								 hotelRoomDate.setHotelRoomId(Integer.parseInt(roomid));
								 HotelRoomDate hotelRoomDate1 = hotelRoomDateService.getHotelRoomDate(hotelRoomDate);
//								 if(hotelRoomDate1.getStockNum()<Integer.parseInt(num)){
//									 ajaxJson.setSuccess(false);
//										ajaxJson.setErrorCode(AppErrorUtils.error_39);
//										ajaxJson.setMsg(AppErrorUtils.error_39_desc);
//										return ajaxJson;
//								 }
								 BigDecimal roomPrice = hotelRoomDate1.getPrice();
								 //计算订单价格
								 orderHotelPrice = roomPrice.add(orderHotelPrice).multiply(new BigDecimal(num)).setScale(2, BigDecimal.ROUND_HALF_UP); 
								 orderHotelRoom.setPrice(roomPrice); //'价格'
								 orderHotelRoom.setNum(Integer.parseInt(num)); //房间数量
								 orderHotelRoom.setHotelId(hotelRoom.getHotelId());//'酒店id'
								 orderHotelRoom.setName(hotelRoom.getName());//'酒店名称'
								 orderHotelRoom.setPeople(hotelRoom.getPeople());//'最大可入住人数'
								 orderHotelRoom.setImgUrl(hotelRoom.getImgUrl());//'酒店房间照片'
								 orderHotelRoom.setContent(hotelRoom.getContent()); //'详情介绍'
								 orderHotelRoom.setLanguageId(Integer.parseInt(languageid));//'语言id'
								 orderHotelRoom.setAgentid(hotelRoom.getAgentid());  //'供应商ID'
								 
								 orderHotelRoomList.add(orderHotelRoom);
								 
								 orderHotel.setPrice(orderHotelPrice); //'订单价格'
								 orderHotel.setOrderHotelRoomList(orderHotelRoomList);
								 orderHotelList1.add(orderHotel);
							 }
							 
							 HashMap<Integer,OrderHotel> tempMap = new HashMap<Integer,OrderHotel>(); //去掉重复的key 
							 for(OrderHotel orderHotel : orderHotelList1){ 
								 int temp = orderHotel.getHotelId(); 
								 //containsKey(Object key)该方法判断Map集合中是否包含指定的键名，如果包含返回true，不包含返回false 
								 //containsValue(Object value)该方法判断Map集合中是否包含指定的键值，如果包含返回true，不包含返回false 
								 if(tempMap.containsKey(temp)){ 
									 OrderHotel newOrderHotel = tempMap.get(temp); 
									 newOrderHotel.getOrderHotelRoomList().addAll(orderHotel.getOrderHotelRoomList());
									 //HashMap不允许key重复，当有key重复时，前面key对应的value值会被覆盖 
									 tempMap.put(temp,newOrderHotel ); 
								}else{ 
									tempMap.put(temp,orderHotel ); 
								} 
							}
							 //去除重复key的list 
							 for(Integer temp:tempMap.keySet()){ 
								 orderHotelList.add(tempMap.get(temp)); 
							}
						}
				 }
			}
			OrderGuide orderGuide = new OrderGuide();
			//导游订单
			if(StringUtils.isNotBlank(guideid)){//是否选择导游
				if (StringUtils.isBlank(guideType)) {
					ajaxJson.setSuccess(false);
					ajaxJson.setErrorCode(AppErrorUtils.error_2);
					ajaxJson.setMsg("guideType传参为空");
					return ajaxJson;
				}
				//获取导游信息
				Guide guide = guideService.get(guideid);
				String orderNo2 = "DY-"+guide.getId()+"-"+bt1+"[]";
				orderGuide.setOrderNo(orderNo2); //'订单号',后面替换[]
				orderGuide.setStatus(1); //'订单状态1.待付款2.待确定，3.待出行，4.待评价 5.已完成6.取消订单7.申请退款8退款通过9.退款不通过',
				orderGuide.setName("DY-"+guide.getRealName()); //'订单标题',
				orderGuide.setCreateDate(new Date());
				orderGuide.setLanguageid(Integer.parseInt(languageid));
				//获取导游价格
				GuideDate guideDate = new GuideDate();
				guideDate.setGuideid(Integer.parseInt(guideid));
				guideDate.setType(Integer.parseInt(guideType));
				guideDate.setPriceDate(bt);
				GuideDate guideDate1 = guideDateService.getGuideDate(guideDate);

				orderGuide.setPrice(guideDate1.getPrice().multiply(new BigDecimal(dayNum))); //'金额',
				orderGuide.setBeginDate(bt); //'开始时间',
				orderGuide.setEndDate(et); //'结束时间',
				orderGuide.setType(1); //'1.包车租车 2.当地玩家 3.酒店 4.保险 5.定制',
				orderGuide.setTypeid(Integer.parseInt(guideid)); //'对应的导游ID',
				orderGuide.setContactsName(contactsName); //'联系人姓名',
				orderGuide.setContactsMobile(contactsMobile); //'联系人电话',
				orderGuide.setRemark(remark); //'备注',
				orderGuide.setUseDate(bt); //'使用时间',
				orderGuide.setAdultNum(adultNum);  //'大人数量',
				orderGuide.setChildNum(childNum); //'孩子数量',
				orderGuide.setEndCity(startCity); //'目的地',
				orderGuide.setMemberId(uid); //'下单人Id',
			}
			if(StringUtils.isNotBlank(insuranceid)){
				//获取保险信息
				Insurance insurance = insuranceService.get(insuranceid);
				
				orderInsurance.setName(insurance.getName());		// 保险名称
				orderInsurance.setInsuranceid(Integer.parseInt(insuranceid));		// 保险ID
				orderInsurance.setAgentid(insurance.getAgentid());		// 供应商ID
				
				String orderNo1 = "BX-"+insurance.getAgentid()+"-"+bt1+"[]"+"-"+insurance.getId();
				orderInsurance.setOrderNo(orderNo1);		// 订单号，后面替换[]
				
				orderInsurance.setMemberId(Integer.parseInt(uid));		// 下单人id
				//保险费*(成人数+儿童数)
				int num = Integer.parseInt(adultNum) + Integer.parseInt(childNum);
				BigDecimal price = new BigDecimal(num).multiply(insurance.getPrice()).setScale(2,BigDecimal.ROUND_HALF_UP);
				orderInsurance.setPrice(price);		// 订单价格
				orderInsurance.setStatus(1);		// 订单状态  1 待付款 2 待确认 3 待出行 4 待点评 5 已完成 6 已取消 7 申请退款 8 退款成功 9 退款失败
				orderInsurance.setContacts(contactsName);		// 联系人
				orderInsurance.setContactMobile(contactsMobile);		// 联系电话
				orderInsurance.setContactRemark(remark);		// 备注
				orderInsurance.setLanguageId(languageid);		// 语言id
				orderInsurance.setNum(Integer.parseInt(adultNum)+Integer.parseInt(childNum));		// 预定数量
			}
		
			JSONArray jsonArray = JSONArray.fromObject(orderMember); 
			List<OrderMember> orderMemberList= new ArrayList<>();
			for(int i=0;i<jsonArray.size();i++){
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
				    
				    orderMember1.setChineseName(chineseName);		// 中文姓名
				    orderMember1.setEnglishName(englishName);		// 英文姓名
				    orderMember1.setCertType(Integer.parseInt(certType));		// 证件类型：1.身份证 2.护照 3.本地ID',
				    orderMember1.setCertNo(certNo);		// 证件号
				    Date certValidDate1=sdf.parse(certValidDate); 
				    orderMember1.setCertValidDate(certValidDate1);		// 有效期
				    Date birthday1=sdf.parse(birthday); 
				    orderMember1.setBirthday(birthday1);		// 出生年月
				    orderMember1.setArea(area);		// 区号
				    orderMember1.setMobile(mobile);		// 手机号
				    orderMember1.setLanguageId(Integer.parseInt(languageid));		// 语言id
				    orderMember1.setType(1);		// 1.租车2.常规路线3.当地参团4.游轮5.景点门票6.当地玩家7.酒店8.保险9.定制10导游'
//				    orderMember1.setTypeId(typeId);		// 根据type 对应不同表的 orderid(orderSys)
				    if (StringUtils.isNotBlank(orderMemberType)) {
				    	orderMember1.setSaveType(Boolean.parseBoolean(orderMemberType));
				    }
				    orderMemberList.add(orderMember1);
			}

			BigDecimal insurancePrice = BigDecimal.ZERO;
			if(orderInsurance.getPrice()!=null){
				insurancePrice = orderInsurance.getPrice();
			}
			orderPrice.add(insurancePrice);
			orderSys.setPrice(orderPrice);//主订单总价
			
			String orderid = orderSysService.saveCarOrderOne(orderSys,orderCarList,orderInsurance,orderMemberList,
					orderCarDateList,orderHotelList,orderGuide);
			ajaxJson.getBody().put("payOrderNo", orderSys.getPayOrderNo());
			
			ajaxJson.setSuccess(true);
			ajaxJson.setErrorCode("0");
			ajaxJson.setMsg("包车租车车辆生成订单成功");  
			return ajaxJson;
		} catch (Exception e) {
			e.printStackTrace();
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_60);
			ajaxJson.setMsg(AppErrorUtils.error_60_desc);
			return ajaxJson;
		}
	}
	
	/**
	 * 短程接送车辆搜索接口
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "selectShortCar")
	@ResponseBody
	public AjaxJson selectShortCar(HttpServletRequest request, HttpServletResponse response, Model model) {
		
		AjaxJson ajaxJson = new AjaxJson();
		try {
			String type = request.getParameter("type");      //1.游客 2.会员
			String uid = request.getParameter("uid");      //会员ID，登陆后返回
			String time = request.getParameter("time");		//时间，登陆后返回
			String key = request.getParameter("key");       //密钥，登陆后返回
			String languageid = request.getParameter("languageid");       //语言id
			String startCity = request.getParameter("startCity");       //出发城市id
			String startDate = request.getParameter("startDate");       //开始时间
			String carType = request.getParameter("carType");       //业务类型1.包车租车2.短程接送3.接送机4.定制
			String comfort = request.getParameter("comfort");       //车辆等级1.2.3.4.
			String agentid = request.getParameter("agentid");       //租车公司id
			String seatNum = request.getParameter("seatNum");       //座位数
			String pageNo = request.getParameter("pageNo");       //页码
			
			if(!StringUtils.isBlank(type)&&Integer.parseInt(type)==2){
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
			
			if (StringUtils.isBlank(startCity)) {
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_2);
				ajaxJson.setMsg("startCity传参为空");
				return ajaxJson;
			}
			if (StringUtils.isBlank(startDate)) {
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_2);
				ajaxJson.setMsg("startDate传参为空");
				return ajaxJson;
			}
			if (StringUtils.isBlank(carType)) {
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_2);
				ajaxJson.setMsg("carType传参为空");
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
			car.setCity(Integer.parseInt(startCity));
//			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			  Date date1=sdf.parse(startDate);
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
			
			Page<CarInfo> page=new Page<CarInfo>();
			page.setPageNo(Integer.parseInt(pageNo));
			page.setPageSize1(pageSize);
			
			Page<CarInfo> page1 = carInfoService.getShortCarByCity(page, car);
		
			List<CarInfo> list = page1.getList();
			int totalPage = page1.getTotalPage();
			if(Integer.parseInt(pageNo) > totalPage){
				ajaxJson.getBody().put("list", new ArrayList<>());
			}else{
				ajaxJson.getBody().put("list", list);
			}
			ajaxJson.getBody().put("totalPage", totalPage);
			
			ajaxJson.setSuccess(true);
			ajaxJson.setErrorCode("0");
			ajaxJson.setMsg("短程接送车辆搜索成功");  
			return ajaxJson;
		} catch (Exception e) {
			e.printStackTrace();
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_41);
			ajaxJson.setMsg(AppErrorUtils.error_41_desc);
			return ajaxJson;
		}
	}
	/**
	 * 短程接送车辆生成订单接口
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "saveShortCarOrder")
	@ResponseBody
	public AjaxJson saveShortCarOrder(HttpServletRequest request, HttpServletResponse response, Model model) {
	
		AjaxJson ajaxJson = new AjaxJson();
		try {
			String uid = request.getParameter("uid");      //会员ID，登陆后返回
			String time = request.getParameter("time");		//时间，登陆后返回
			String key = request.getParameter("key");       //密钥，登陆后返回
			String languageid = request.getParameter("languageid");       //语言id

			String contactsName = request.getParameter("contactsName");       //联系人姓名
			String contactsMobile = request.getParameter("contactsMobile");       //联系人电话
			String remark = request.getParameter("remark");       //备注
			String orderTitle = request.getParameter("orderTitle");       //行程说明
			String startDate = request.getParameter("startDate");       //上车时间
			String startCity = request.getParameter("startCity");  //出发城市
			String startAddress = request.getParameter("startAddress");  //出发详细地址
			String endCity = request.getParameter("endCity");  //到达城市
			String endAddress = request.getParameter("endAddress");  //到达详细地址
			String carTime = request.getParameter("carTime");  //消耗时间
			String distance = request.getParameter("distance");  //行驶路程
			String adultNum = request.getParameter("adultNum");       //大人
			String childNum = request.getParameter("childNum");       //小孩
			String bagNum = request.getParameter("bagNum");  //包裹
			String carInfor = request.getParameter("carInfor");  //汽车信息
			String insuranceid = request.getParameter("insuranceid");       //保险id
			/*出游人信息集合[{chineseName: "",englishName : "", certType: "",certNo : "", 
			certValidDate: "", birthday: "", area: "", mobile: "",type : "" },{},{}]*/
			String orderMember = request.getParameter("orderMember");       //出游人信息
			
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
			if (StringUtils.isBlank(orderMember)) {
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_2);
				ajaxJson.setMsg("orderMember传参为空");
				return ajaxJson;
			}
			if (StringUtils.isBlank(carInfor)) {
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_2);
				ajaxJson.setMsg("carInfor传参为空");
				return ajaxJson;
			}
			if (StringUtils.isBlank(bagNum)) {
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_2);
				ajaxJson.setMsg("bagNum传参为空");
				return ajaxJson;
			}
			if (StringUtils.isBlank(distance)) {
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_2);
				ajaxJson.setMsg("distance传参为空");
				return ajaxJson;
			}
			if (StringUtils.isBlank(orderTitle)) {
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_2);
				ajaxJson.setMsg("orderTitle传参为空");
				return ajaxJson;
			}
			if (StringUtils.isBlank(carTime)) {
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_2);
				ajaxJson.setMsg("carTime传参为空");
				return ajaxJson;
			}
			if (StringUtils.isBlank(endAddress)) {
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_2);
				ajaxJson.setMsg("endAddress传参为空");
				return ajaxJson;
			}
			if (StringUtils.isBlank(endCity)) {
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_2);
				ajaxJson.setMsg("endCity传参为空");
				return ajaxJson;
			}
			if (StringUtils.isBlank(startAddress)) {
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_2);
				ajaxJson.setMsg("startAddress传参为空");
				return ajaxJson;
			}
			if (StringUtils.isBlank(startCity)) {
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_2);
				ajaxJson.setMsg("startCity传参为空");
				return ajaxJson;
			}
		
			OrderSys orderSys = new OrderSys();
			orderSys.setPayOrderNo(CodeGenUtils.getNowDate());
			OrderInsurance orderInsurance = new OrderInsurance();
			
			orderSys.setType(2);// 订单类型1.包车租车2.短程接送3.接送机4常规路线5.当地参团6.游轮7.景点门票
									//8.当地玩家9.酒店10.保险11.旅游定制12导游13.商务定制14.商务旅游',
			orderSys.setFatherid(0);		// 父id
			orderSys.setMemberid(Integer.parseInt(uid));      //下单人
			orderSys.setLanguageid(Integer.parseInt(languageid));    //语言id

			orderSys.setContactsName(contactsName);	//联系人姓名
			orderSys.setContactsMobile(contactsMobile);	//联系人电话
			orderSys.setRemark(remark);			//备注
			orderSys.setPeopleNum(Integer.parseInt(adultNum)+Integer.parseInt(childNum));

			orderSys.setTitle(orderTitle);			//订单标题
			orderSys.setAdultNum(Integer.parseInt(adultNum));		// 大人数量
			orderSys.setChildNum(Integer.parseInt(childNum));		// 孩子数量
			orderSys.setBagNum(Integer.parseInt(bagNum));		// 包裹数量
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); 
			Date bt=sdf.parse(startDate); 
			orderSys.setBeginDate(bt);       //开始时间
			
			
			SimpleDateFormat sdf1=new SimpleDateFormat("yyyyMMdd"); 
			String bt1=sdf1.format(new Date()); 
			//订单总价
			BigDecimal orderPrice = BigDecimal.ZERO;
			//车辆订单
			List<OrderCar> orderCarList= new ArrayList<>();
			if(StringUtils.isNotBlank(carInfor)){//是否选择车辆
				 JSONArray jsonArray = JSONArray.fromObject(carInfor); 
				 for(int i=0;i<jsonArray.size();i++){
					 OrderCar orderCar1 = new OrderCar();
					 //取出数组第i个元素 
					 JSONObject jpro = jsonArray.getJSONObject(i);
					 String carid = jpro.getString("carid"); 
					 String carType = jpro.getString("carType"); 
					 //获取车辆信息
					 CarInfo car = carInfoService.get(carid);
					 String orderNo3 = "VE-"+car.getAgentid()+"-"+bt1+"[]";
					 orderCar1.setOrderNo(orderNo3); //'订单号',后面替换[]
					 orderCar1.setType(2); //'订单类型1.包车租车2.短程接送3.接送机4.定制'
					 orderCar1.setName(orderTitle); //'订单标题'
					 orderCar1.setAdultNum(Integer.parseInt(adultNum)); //'大人数量'
					 orderCar1.setChildNum(Integer.parseInt(childNum)); //'孩子数量'
					 orderCar1.setBagNum(Integer.parseInt(bagNum)); //'包裹数量'
					 orderCar1.setStartCity(startCity); //'出发城市'
					 orderCar1.setStartAddress(startAddress); //'出发详细地址'
					 orderCar1.setEndCity(endCity); //'到达城市'
					 orderCar1.setEndAddress(endAddress); //'到达详细地址'
					 orderCar1.setBeginDate(bt); //'上车时间'
					 orderCar1.setContactsName(contactsName); //'联系人姓名'
					 orderCar1.setContactsMobile(contactsMobile); //'联系人电话'
					 orderCar1.setRemark(remark); //'备注'
					 
					 //查询汽车价格
					 CarTimePrice carTimePrice = new CarTimePrice();
					 carTimePrice.setCarid(carid);
					 carTimePrice.setBusinessType(carType);
					 carTimePrice.setBusiDate(bt);
					 CarTimePrice carTimePrice1 = carTimePriceServcie.getPrice(carTimePrice);
					 BigDecimal carPrice = carTimePrice1.getEmptyPrice()
							 .add(carTimePrice1.getPrice())
							 .add(carTimePrice1.getFoodPrice())
							 .add(carTimePrice1.getHeadPrice())
							 .add(carTimePrice1.getStartPrice())
							 .setScale(2,BigDecimal.ROUND_HALF_UP);
					 orderCar1.setPrice(carPrice); //'金额'
					 orderPrice.add(carPrice);
					 orderCar1.setCarid(carid); //'汽车id'
					 orderCar1.setCarName(car.getCarName()); //'汽车名称'
					 orderCar1.setStatus(1); //'订单状态1.待付款2.待确定，3.待出行，4.待评价 5.已完成6.取消订单7.申请退款8退款通过9.退款不通过'
					 orderCar1.setLanguageId(Integer.parseInt(languageid)); //'语言'
					 orderCar1.setMemberId(uid);  //'下单人ID'
					 orderCar1.setUseDate(bt); //'使用时间'
					 orderCar1.setCarTime(carTime); //行程行驶时间
					 orderCar1.setCarDistance(distance);//行程行驶距离
					 orderCar1.setSeatNum(car.getSeatNum());//汽车座位数
					 orderCar1.setComfort(car.getComfort().toString());//汽车舒适度
					 orderCar1.setCarBagNum(car.getBagNum());//汽车包裹容量
					 
					 orderCarList.add(orderCar1);
				 }
			}
			
			if(StringUtils.isNotBlank(insuranceid)){
				//获取保险信息
				Insurance insurance = insuranceService.get(insuranceid);
				
				orderInsurance.setName(insurance.getName());		// 保险名称
				orderInsurance.setInsuranceid(Integer.parseInt(insuranceid));		// 保险ID
				orderInsurance.setAgentid(insurance.getAgentid());		// 供应商ID
				
				String orderNo1 = "BX-"+insurance.getAgentid()+"-"+bt1+"[]"+"-"+insurance.getId();
				orderInsurance.setOrderNo(orderNo1);		// 订单号，后面替换[]
				
				orderInsurance.setMemberId(Integer.parseInt(uid));		// 下单人id
				BigDecimal orderInsuranceprice = new BigDecimal(Integer.parseInt(adultNum)+Integer.parseInt(childNum))
						.multiply(insurance.getPrice())
						.setScale(2,BigDecimal.ROUND_HALF_UP);
				orderInsurance.setPrice((orderInsuranceprice));		// 订单价格
				orderInsurance.setStatus(1);		// 订单状态  1 待付款 2 待确认 3 待出行 4 待点评 5 已完成 6 已取消 7 申请退款 8 退款成功 9 退款失败
				orderInsurance.setContacts(contactsName);		// 联系人
				orderInsurance.setContactMobile(contactsMobile);		// 联系电话
				orderInsurance.setContactRemark(remark);		// 备注
				orderInsurance.setLanguageId(languageid);		// 语言id
				orderInsurance.setNum(Integer.parseInt(adultNum)+Integer.parseInt(childNum));		// 预定数量
			}
		
			JSONArray jsonArray = JSONArray.fromObject(orderMember); 
			List<OrderMember> orderMemberList= new ArrayList<>();
			for(int i=0;i<jsonArray.size();i++){
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
				    
				    orderMember1.setChineseName(chineseName);		// 中文姓名
				    orderMember1.setEnglishName(englishName);		// 英文姓名
				    orderMember1.setCertType(Integer.parseInt(certType));		// 证件类型：1.身份证 2.护照 3.本地ID',
				    orderMember1.setCertNo(certNo);		// 证件号
				    Date certValidDate1=sdf.parse(certValidDate); 
				    orderMember1.setCertValidDate(certValidDate1);		// 有效期
				    Date birthday1=sdf.parse(birthday); 
				    orderMember1.setBirthday(birthday1);		// 出生年月
				    orderMember1.setArea(area);		// 区号
				    orderMember1.setMobile(mobile);		// 手机号
				    orderMember1.setLanguageId(Integer.parseInt(languageid));		// 语言id
				    orderMember1.setType(1);		// 1.租车2.常规路线3.当地参团4.游轮5.景点门票6.当地玩家7.酒店8.保险9.定制10导游'
//				    orderMember1.setTypeId(typeId);		// 根据type 对应不同表的 orderid(orderSys)
				    if (StringUtils.isNotBlank(orderMemberType)) {
				    	orderMember1.setSaveType(Boolean.parseBoolean(orderMemberType));
				    }
				    orderMemberList.add(orderMember1);
			}
			BigDecimal insurancePrice = BigDecimal.ZERO;
			if(orderInsurance.getPrice()!=null){
				insurancePrice = orderInsurance.getPrice();
			}
			orderPrice.add(insurancePrice);
			orderSys.setPrice(orderPrice);//主订单总价
			
			String orderid = orderSysService.saveCarOrder(orderSys,orderCarList,orderInsurance,orderMemberList);

			ajaxJson.getBody().put("payOrderNo", orderSys.getPayOrderNo());
			ajaxJson.setSuccess(true);
			ajaxJson.setErrorCode("0");
			ajaxJson.setMsg("短程接送车辆生成订单成功");  
			return ajaxJson;
		} catch (Exception e) {
			e.printStackTrace();
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_42);
			ajaxJson.setMsg(AppErrorUtils.error_42_desc);
			return ajaxJson;
		}
	}
	/**
	 * 接送机车辆搜索接口
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "selectAirportCar")
	@ResponseBody
	public AjaxJson selectAirportCar(HttpServletRequest request, HttpServletResponse response, Model model) {
		
		AjaxJson ajaxJson = new AjaxJson();
		try {
			String type = request.getParameter("type");      //1.游客 2.会员
			String uid = request.getParameter("uid");      //会员ID，登陆后返回
			String time = request.getParameter("time");		//时间，登陆后返回
			String key = request.getParameter("key");       //密钥，登陆后返回
			String languageid = request.getParameter("languageid");       //语言id
			
			String startCity = request.getParameter("startCity");       //出发城市id
			String startDate = request.getParameter("startDate");       //开始时间
			String carType = request.getParameter("carType");       //业务类型1.包车租车2.短程接送3.接送机4.定制
			String comfort = request.getParameter("comfort");       //车辆等级1.2.3.4.
			String agentid = request.getParameter("agentid");       //租车公司id
			String seatNum = request.getParameter("seatNum");       //座位数
			String pageNo = request.getParameter("pageNo");       //页码
			
			if(!StringUtils.isBlank(type)&&Integer.parseInt(type)==2){
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
			
			if (StringUtils.isBlank(startCity)) {
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_2);
				ajaxJson.setMsg("startCity传参为空");
				return ajaxJson;
			}
			if (StringUtils.isBlank(startDate)) {
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_2);
				ajaxJson.setMsg("startDate传参为空");
				return ajaxJson;
			}
			if (StringUtils.isBlank(carType)) {
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_2);
				ajaxJson.setMsg("carType传参为空");
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
			car.setCity(Integer.parseInt(startCity));
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
			  Date date1=sdf.parse(startDate); 
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
			
			Page<CarInfo> page=new Page<CarInfo>();
			page.setPageNo(Integer.parseInt(pageNo));
			page.setPageSize1(pageSize);
			
			Page<CarInfo> page1 = carInfoService.getShortCarByCity(page, car);
		
			List<CarInfo> list=page1.getList();
			int totalPage=page1.getTotalPage();
			if(Integer.parseInt(pageNo)>totalPage){
				ajaxJson.getBody().put("list", new ArrayList<>());
			}else{
				ajaxJson.getBody().put("list", list);
			}
			ajaxJson.getBody().put("totalPage", totalPage);
			
			ajaxJson.setSuccess(true);
			ajaxJson.setErrorCode("0");
			ajaxJson.setMsg("接送机车辆搜索成功");  
			return ajaxJson;
		} catch (Exception e) {
			e.printStackTrace();
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_43);
			ajaxJson.setMsg(AppErrorUtils.error_43_desc);
			return ajaxJson;
		}
	}
	/**
	 * 接送机车辆生成订单接口
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "saveAirportCarOrder")
	@ResponseBody
	public AjaxJson saveAirportCarOrder(HttpServletRequest request, HttpServletResponse response, Model model) {
	
		AjaxJson ajaxJson = new AjaxJson();
		try {
			String uid = request.getParameter("uid");      //会员ID，登陆后返回
			String time = request.getParameter("time");		//时间，登陆后返回
			String key = request.getParameter("key");       //密钥，登陆后返回
			String languageid = request.getParameter("languageid");       //语言id
			String orderType = request.getParameter("orderType");  //1.接机2.送机
			String airNo = request.getParameter("airNo"); 			//航班号
			String sendAirport = request.getParameter("sendAirport"); //起飞机场
			String sendDate = request.getParameter("sendDate");       //起飞时间
			String reachAirport = request.getParameter("reachAirport");// 到达机场
			String reachDate = request.getParameter("reachDate");      //到达时间
			String contactsName = request.getParameter("contactsName");       //联系人姓名
			String contactsMobile = request.getParameter("contactsMobile");       //联系人电话
			String remark = request.getParameter("remark");       //备注
			String orderTitle = request.getParameter("orderTitle");       //行程说明
			String startDate = request.getParameter("startDate");       //上车时间
			String startCity = request.getParameter("startCity");  //出发城市
			String startAddress = request.getParameter("startAddress");  //出发详细地址
			String carTime = request.getParameter("carTime");  //消耗时间
			String distance = request.getParameter("distance");  //行驶路程
			String adultNum = request.getParameter("adultNum");       //大人
			String childNum = request.getParameter("childNum");       //小孩
			String bagNum = request.getParameter("bagNum");  //包裹
			String carInfor = request.getParameter("carInfor");  //汽车信息
			String insuranceid = request.getParameter("insuranceid");       //保险id
			/*出游人信息集合[{chineseName: "",englishName : "", certType: "",certNo : "", 
			certValidDate: "", birthday: "", area: "", mobile: "",type : "" },{},{}]*/
			String orderMember = request.getParameter("orderMember");       //出游人信息
			
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
			if (StringUtils.isBlank(orderTitle)) {
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_2);
				ajaxJson.setMsg("orderTitle传参为空");
				return ajaxJson;
			}
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
			if (StringUtils.isBlank(orderMember)) {
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_2);
				ajaxJson.setMsg("orderMember传参为空");
				return ajaxJson;
			}
			if (StringUtils.isBlank(carInfor)) {
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_2);
				ajaxJson.setMsg("carInfor传参为空");
				return ajaxJson;
			}
			if (StringUtils.isBlank(bagNum)) {
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_2);
				ajaxJson.setMsg("bagNum传参为空");
				return ajaxJson;
			}
			if (StringUtils.isBlank(distance)) {
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_2);
				ajaxJson.setMsg("distance传参为空");
				return ajaxJson;
			}
			if (StringUtils.isBlank(carTime)) {
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_2);
				ajaxJson.setMsg("carTime传参为空");
				return ajaxJson;
			}
			if (StringUtils.isBlank(startAddress)) {
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_2);
				ajaxJson.setMsg("startAddress传参为空");
				return ajaxJson;
			}
			if (StringUtils.isBlank(startCity)) {
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_2);
				ajaxJson.setMsg("startCity传参为空");
				return ajaxJson;
			}
			if (StringUtils.isBlank(orderType)) {
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_2);
				ajaxJson.setMsg("orderType传参为空");
				return ajaxJson;
			}
			if (StringUtils.isBlank(airNo)) {
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_2);
				ajaxJson.setMsg("airNo传参为空");
				return ajaxJson;
			}
			if (StringUtils.isBlank(sendAirport)) {
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_2);
				ajaxJson.setMsg("sendAirport传参为空");
				return ajaxJson;
			}
			if (StringUtils.isBlank(sendDate)) {
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_2);
				ajaxJson.setMsg("sendDate传参为空");
				return ajaxJson;
			}
			if (StringUtils.isBlank(reachAirport)) {
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_2);
				ajaxJson.setMsg("reachAirport传参为空");
				return ajaxJson;
			}
			if (StringUtils.isBlank(reachDate)) {
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_2);
				ajaxJson.setMsg("uidreachDate参为空");
				return ajaxJson;
			}
			OrderSys orderSys = new OrderSys();
			orderSys.setPayOrderNo(CodeGenUtils.getNowDate());
			OrderInsurance orderInsurance = new OrderInsurance();
			
			orderSys.setType(3);// 订单类型1.包车租车2.短程接送3.接送机4常规路线5.当地参团6.游轮7.景点门票
							//8.当地玩家9.酒店10.保险11.旅游定制12导游13.商务定制14.商务旅游',
			orderSys.setFatherid(0);		// 父id
			orderSys.setMemberid(Integer.parseInt(uid));      //下单人
			orderSys.setLanguageid(Integer.parseInt(languageid));    //语言id

			orderSys.setContactsName(contactsName);	//联系人姓名
			orderSys.setContactsMobile(contactsMobile);	//联系人电话
			orderSys.setRemark(remark);			//备注
			orderSys.setPeopleNum(Integer.parseInt(adultNum)+Integer.parseInt(childNum));
			orderSys.setTitle(orderTitle);			//订单标题
			orderSys.setAdultNum(Integer.parseInt(adultNum));		// 大人数量
			orderSys.setChildNum(Integer.parseInt(childNum));		// 孩子数量
			orderSys.setBagNum(Integer.parseInt(bagNum));		// 包裹数量
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); 
			Date bt=sdf.parse(startDate); 
			orderSys.setBeginDate(bt);       //开始时间
			
			
			SimpleDateFormat sdf1=new SimpleDateFormat("yyyyMMdd"); 
			String bt1=sdf1.format(new Date()); 
			//订单总价
			BigDecimal orderPrice = BigDecimal.ZERO;
			//车辆订单
			List<OrderCar> OrderCarList= new ArrayList<>();
			if(StringUtils.isNotBlank(carInfor)){//是否选择车辆
				 JSONArray jsonArray = JSONArray.fromObject(carInfor); 
				 for(int i=0;i<jsonArray.size();i++){
					 OrderCar orderCar1 = new OrderCar();
					 //取出数组第i个元素 
					 JSONObject jpro = jsonArray.getJSONObject(i);
					 String carid = jpro.getString("carid"); 
					 String carType = jpro.getString("carType"); 
					 //获取车辆信息
					 CarInfo car = carInfoService.get(carid);
					 String orderNo3 = "VE-"+car.getAgentid()+"-"+bt1+"[]";
					 orderCar1.setOrderNo(orderNo3); //'订单号',后面替换[]
					 orderCar1.setType(3); //'订单类型1.包车租车2.短程接送3.接送机4.定制'
					 orderCar1.setName(orderTitle); //'订单标题'
					 orderCar1.setAdultNum(Integer.parseInt(adultNum)); //'大人数量'
					 orderCar1.setChildNum(Integer.parseInt(childNum)); //'孩子数量'
					 orderCar1.setBagNum(Integer.parseInt(bagNum)); //'包裹数量'
					 orderCar1.setBeginDate(bt); //'上车时间'
					 orderCar1.setAirType(orderType); //'接送机1.接机2.送机'
					 if(orderType.equals("1")){//接机
						 orderCar1.setEndCity(startCity); //'到达城市'
						 orderCar1.setEndAddress(startAddress); //'到达详细地址'
					 }else if(orderType.equals("2")){//送机
						 orderCar1.setStartCity(startCity); //'出发城市'
						 orderCar1.setStartAddress(startAddress); //'出发详细地址'
					 }
					 orderCar1.setAirNo(airNo); //'航班号'
					 orderCar1.setSendAirport(sendAirport); //'飞机起飞机场'
					 Date sd=sdf.parse(sendDate); 
					 orderCar1.setSendDate(sd); //'飞机起飞时间'
					 orderCar1.setReachAirport(reachAirport); //'到达机场'
					 Date rd=sdf.parse(reachDate); 
					 orderCar1.setReachDate(rd); //'到达时间'
					 orderCar1.setContactsName(contactsName); //'联系人姓名'
					 orderCar1.setContactsMobile(contactsMobile); //'联系人电话'
					 orderCar1.setRemark(remark); //'备注'
					 
					 //查询汽车价格
					 CarTimePrice carTimePrice = new CarTimePrice();
					 carTimePrice.setCarid(carid);
					 carTimePrice.setBusinessType(carType);
					 carTimePrice.setBusiDate(bt);
					 CarTimePrice carTimePrice1 = carTimePriceServcie.getPrice(carTimePrice);
					 BigDecimal carPrice =carTimePrice1.getPrice()
							 .add(carTimePrice1.getFoodPrice())
							 .add(carTimePrice1.getHeadPrice())
							 .add(carTimePrice1.getStartPrice())
							 .setScale(2,BigDecimal.ROUND_HALF_UP);
					 orderCar1.setPrice(carPrice); //'金额'
					 orderPrice.add(carPrice);
					 orderCar1.setCarid(carid); //'汽车id'
					 orderCar1.setCarName(car.getCarName()); //'汽车名称'
					 orderCar1.setStatus(1); //'订单状态1.待付款2.待确定，3.待出行，4.待评价 5.已完成6.取消订单7.申请退款8退款通过9.退款不通过'
					 orderCar1.setLanguageId(Integer.parseInt(languageid)); //'语言'
					 orderCar1.setMemberId(uid);  //'下单人ID'
					 orderCar1.setUseDate(bt); //'使用时间'
					 orderCar1.setCarTime(carTime); //行程行驶时间
					 orderCar1.setCarDistance(distance);//行程行驶距离
					 orderCar1.setSeatNum(car.getSeatNum());//汽车座位数
					 orderCar1.setComfort(car.getComfort().toString());//汽车舒适度
					 orderCar1.setCarBagNum(car.getBagNum());//汽车包裹容量
					 
					 OrderCarList.add(orderCar1);
				 }
			}
			
			if(StringUtils.isNotBlank(insuranceid)){
				//获取保险信息
				Insurance insurance = insuranceService.get(insuranceid);
				
				orderInsurance.setName(insurance.getName());		// 保险名称
				orderInsurance.setInsuranceid(Integer.parseInt(insuranceid));		// 保险ID
				orderInsurance.setAgentid(insurance.getAgentid());		// 供应商ID
				
				String orderNo1 = "BX-"+insurance.getAgentid()+"-"+bt1+"[]"+"-"+insurance.getId();
				orderInsurance.setOrderNo(orderNo1);		// 订单号，后面替换[]
				
				orderInsurance.setMemberId(Integer.parseInt(uid));		// 下单人id
				BigDecimal orderInsuranceprice = new BigDecimal(Integer.parseInt(adultNum)+Integer.parseInt(childNum))
						.multiply(insurance.getPrice())
						.setScale(2,BigDecimal.ROUND_HALF_UP);
				orderInsurance.setPrice((orderInsuranceprice));		// 订单价格
				orderInsurance.setStatus(1);		// 订单状态  1 待付款 2 待确认 3 待出行 4 待点评 5 已完成 6 已取消 7 申请退款 8 退款成功 9 退款失败
				orderInsurance.setContacts(contactsName);		// 联系人
				orderInsurance.setContactMobile(contactsMobile);		// 联系电话
				orderInsurance.setContactRemark(remark);		// 备注
				orderInsurance.setLanguageId(languageid);		// 语言id
				orderInsurance.setNum(Integer.parseInt(adultNum)+Integer.parseInt(childNum));		// 预定数量
			}
		
			JSONArray jsonArray = JSONArray.fromObject(orderMember); 
			List<OrderMember> orderMemberList= new ArrayList<>();
			for(int i=0;i<jsonArray.size();i++){
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
				    
				    orderMember1.setChineseName(chineseName);		// 中文姓名
				    orderMember1.setEnglishName(englishName);		// 英文姓名
				    orderMember1.setCertType(Integer.parseInt(certType));		// 证件类型：1.身份证 2.护照 3.本地ID',
				    orderMember1.setCertNo(certNo);		// 证件号
				    Date certValidDate1=sdf.parse(certValidDate); 
				    orderMember1.setCertValidDate(certValidDate1);		// 有效期
				    Date birthday1=sdf.parse(birthday); 
				    orderMember1.setBirthday(birthday1);		// 出生年月
				    orderMember1.setArea(area);		// 区号
				    orderMember1.setMobile(mobile);		// 手机号
				    orderMember1.setLanguageId(Integer.parseInt(languageid));		// 语言id
				    orderMember1.setType(1);		// 1.租车2.常规路线3.当地参团4.游轮5.景点门票6.当地玩家7.酒店8.保险9.定制10导游'
//				    orderMember1.setTypeId(typeId);		// 根据type 对应不同表的 orderid(orderSys)
				    if (StringUtils.isNotBlank(orderMemberType)) {
				    	orderMember1.setSaveType(Boolean.parseBoolean(orderMemberType));
				    }
				    orderMemberList.add(orderMember1);
			}
			BigDecimal insurancePrice = BigDecimal.ZERO;
			if(orderInsurance.getPrice()!=null){
				insurancePrice = orderInsurance.getPrice();
			}
			orderPrice.add(insurancePrice);
			orderSys.setPrice(orderPrice);//主订单总价
			
			String orderid = orderSysService.saveCarOrder(orderSys,OrderCarList,orderInsurance,orderMemberList);

			ajaxJson.getBody().put("payOrderNo", orderSys.getPayOrderNo());
			ajaxJson.setSuccess(true);
			ajaxJson.setErrorCode("0");
			ajaxJson.setMsg("接送机车辆生成订单成功");  
			return ajaxJson;
		} catch (Exception e) {
			e.printStackTrace();
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_44);
			ajaxJson.setMsg(AppErrorUtils.error_44_desc);
			return ajaxJson;
		}
	}
}
	
