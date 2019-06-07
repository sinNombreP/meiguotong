/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.service.car;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.meiguotong.entity.car.CarBusiness;
import com.jeeplus.modules.meiguotong.entity.car.CarInfo;
import com.jeeplus.modules.meiguotong.entity.car.CarTimePrice;
import com.jeeplus.modules.meiguotong.mapper.car.CarBusinessMapper;
import com.jeeplus.modules.meiguotong.mapper.car.CarInfoMapper;
import com.jeeplus.modules.meiguotong.mapper.car.CarTimePriceMapper;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.utils.UserUtils;

/**
 * 租车管理Service
 * 
 * @author psz
 * @version 2018-08-31
 */
@Service
@Transactional(readOnly = true)
public class CarInfoService extends CrudService<CarInfoMapper, CarInfo> {

	@Autowired
	private CarBusinessMapper carBusinessMapper;
	@Autowired
	private CarTimePriceMapper carTimePriceMapper;

	public CarInfo get(String id) {
		return super.get(id);
	}

	public List<CarInfo> findList(CarInfo car) {
		return super.findList(car);
	}
	/**
	 * 根据城市及搜索条件获取定制汽车列表
	 * @param page
	 * @param car
	 * @return
	 */
	public Page<CarInfo> getCarByCity(Page<CarInfo> page, CarInfo car) {
		dataRuleFilter(car);
		car.setPage(page);
		page.setList(mapper.getCarByCity(car));
		return page;
	}
	/**
	 * 根据城市及搜索条件获取短程接送汽车列表
	 * @param page
	 * @param car
	 * @return
	 */
	public Page<CarInfo> getShortCarByCity(Page<CarInfo> page, CarInfo car) {
		dataRuleFilter(car);
		car.setPage(page);
		page.setList(mapper.getShortCarByCity(car));
		return page;
	}
	public Page<CarInfo> findPage(Page<CarInfo> page, CarInfo car) {
		return super.findPage(page, car);
	}

	@Transactional(readOnly = false)
	public void save(CarInfo car) {
		super.save(car);
	}

	@Transactional(readOnly = false)
	public void delete(CarInfo car) {
		super.delete(car);
	}

	/**
	 * @Title: addUpdate
	 * @Description: 保存更新租车
	 * @author 彭善智
	 * @date 2018年9月4日下午4:34:15
	 */
	@Transactional(readOnly = false)
	public void addUpdate(CarInfo car) {
		try {
			if (car.getIsNewRecord()){
				car.preInsert();
				mapper.insert(car);
				car.setCarNo("VE-"+car.getAgentid()+"-"+car.getSeatNum()+"-"+car.getId());
				mapper.updateNo(car);
			}else{
				User user = UserUtils.getUser();
				if(user!=null&&user.getAgentid()!=null){
					car.setStatus(1);
				}
				car.preUpdate();
				 mapper.update(car);
			}
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
			if (car.getCarBusinessList() != null && car.getCarBusinessList().size() > 0) {
				for (CarBusiness carBusiness : car.getCarBusinessList()) {
					carBusiness.setCarid(Integer.parseInt(car.getId()));
					carBusiness.setLanguage(car.getLanguage());
					if (carBusiness.getStatus() == null) {
						carBusiness.setStatus(2);
					}
					carTimePriceMapper.deleteByCarid(carBusiness.getId());
					if (carBusiness.getBusinessType() == 1) {
						if (carBusiness.getIsNewRecord()) {
							carBusiness.preInsert();
							carBusinessMapper.insert(carBusiness);
						} else {
							carBusiness.preUpdate();
							carBusinessMapper.update(carBusiness);
						}
						if (carBusiness.getCarTimePriceList() != null && carBusiness.getCarTimePriceList().size() > 0) {
							for (CarTimePrice carTimePrice : carBusiness.getCarTimePriceList()) {
								carTimePrice.setType(1);
								carTimePrice.setLanguage(car.getLanguage());
								carTimePrice.setBusinessid(Integer.parseInt(carBusiness.getId()));
								if (carTimePrice.getRange() != null && carTimePrice.getRange() != 3) {
									carTimePrice.setRadius(null);
								}
								carTimePriceMapper.insert(carTimePrice);
							}
						}
					} else {
						String weekDate = "";
						if (carBusiness.getWeekList() != null && carBusiness.getWeekList().length > 0) {
							for (int i = 0; i < carBusiness.getWeekList().length; i++) {
								weekDate += carBusiness.getWeekList()[i] + ",";
							}
							weekDate = weekDate.length() > 0 ? weekDate.substring(0, weekDate.lastIndexOf(",")) : "";
						}
						String dayDate = "";
						if (carBusiness.getDayList() != null && carBusiness.getDayList().length > 0) {
							for (int i = 0; i < carBusiness.getDayList().length; i++) {
								dayDate += carBusiness.getDayList()[i] + ",";
							}
							dayDate = dayDate.length() > 0 ? dayDate.substring(0, dayDate.lastIndexOf(",")) : "";
						}
						String hourDate = "";
						if (carBusiness.getHourList() != null && carBusiness.getHourList().length > 0) {
							for (int i = 0; i < carBusiness.getHourList().length; i++) {
								hourDate += carBusiness.getHourList()[i] + ",";
								if (carBusiness.getHourList()[i].equals("-1")) {
									hourDate = "-1";
									break;
								}
							}
							if (!hourDate.equals("-1")) {
								hourDate = hourDate.length() > 0 ? hourDate.substring(0, hourDate.lastIndexOf(","))
										: "";
								if (StringUtils.isNotBlank(hourDate) && (hourDate.split(",").length & 1) != 0) { // 奇数
									hourDate = hourDate.substring(0, hourDate.lastIndexOf(","));
								}
							}
						}
						if (carBusiness.getDateType() != null) {
							carBusiness.setHourDate(hourDate);
							if (carBusiness.getDateType() == 2) {
								carBusiness.setWeekDate(weekDate);
							} else if (carBusiness.getDateType() == 3) {
								carBusiness.setDayDate(dayDate);
							}
						}
						if (carBusiness.getIsNewRecord()) {
							carBusiness.preInsert();
							carBusinessMapper.insert(carBusiness);
						} else {
							carBusiness.preUpdate();
							carBusinessMapper.update(carBusiness);
						}
						if(("-1").equals(carBusiness.getHourDate())){
							carBusiness.setHourDate("");
							hourDate = "";
						}
						CarTimePrice carTimePrice = new CarTimePrice();
						carTimePrice.setType(1);
						carTimePrice.setLanguage(car.getLanguage());
						carTimePrice.setBusinessid(Integer.parseInt(carBusiness.getId()));
						carTimePrice.setPrice(carBusiness.getPrice());
						carTimePrice.setStartPrice(carBusiness.getStartPrice());
						carTimePrice.setHeadPrice(carBusiness.getHeadPrice());
						carTimePrice.setFoodPrice(carBusiness.getFoodPrice());
						carTimePrice.setEmptyPrice(carBusiness.getEmptyPrice());
						if (carBusiness.getBeginDate() != null && carBusiness.getEndDate() != null) {
							Calendar cal = Calendar.getInstance(); // 获得一个日历
							Integer day = null;
							Integer week = null;
							for (long k = carBusiness.getBeginDate().getTime(); k < carBusiness.getEndDate()
									.getTime(); k += 86400000) {
								cal.setTimeInMillis(k);
								carTimePrice.setBusiDate(new Date(k));
								if (carBusiness.getDateType() == 1) { // 1所有星期 2按星期 3按号数
									if (StringUtils.isNotBlank(hourDate)) {
										String hourDateList[] = hourDate.split(",");
										for (int d = 0; d < hourDateList.length; d += 2) {
											carTimePrice.setBeginTime(sdf.parse(hourDateList[d]));
											carTimePrice.setEndTime(sdf.parse(hourDateList[d + 1]));
											carTimePriceMapper.insert(carTimePrice);
										}
									} else {
										carTimePrice.setBeginTime(sdf.parse("00:00"));
										carTimePrice.setEndTime(sdf.parse("23:59"));
										carTimePriceMapper.insert(carTimePrice);
									}
								} else if (carBusiness.getDateType() == 2 && carBusiness.getWeekList() != null
										&& carBusiness.getWeekList().length > 0) {
									for (int i = 0; i < carBusiness.getWeekList().length; i++) {
										day = Integer.parseInt(carBusiness.getWeekList()[i]);
										if (cal.get(Calendar.DAY_OF_WEEK) == day) {
											if (StringUtils.isNotBlank(hourDate)) {
												String hourDateList[] = hourDate.split(",");
												for (int d = 0; d < hourDateList.length; d += 2) {
													carTimePrice.setBeginTime(sdf.parse(hourDateList[d]));
													carTimePrice.setEndTime(sdf.parse(hourDateList[d + 1]));
													carTimePriceMapper.insert(carTimePrice);
												}
											} else {
												carTimePrice.setBeginTime(sdf.parse("00:00"));
												carTimePrice.setEndTime(sdf.parse("23:59"));
												carTimePriceMapper.insert(carTimePrice);
											}
										}
									}
								} else if (carBusiness.getDateType() == 3 && carBusiness.getDayList() != null
										&& carBusiness.getDayList().length > 0) {
									for (int i = 0; i < carBusiness.getDayList().length; i++) {
										week = Integer.parseInt(carBusiness.getDayList()[i]);
										if (cal.get(Calendar.DAY_OF_MONTH) == week) {
											if (StringUtils.isNotBlank(hourDate)) {
												String[] hourDateList = hourDate.split(",");
												for (int d = 0; d < hourDateList.length; d += 2) {
													carTimePrice.setBeginTime(sdf.parse(hourDateList[d]));
													carTimePrice.setEndTime(sdf.parse(hourDateList[d + 1]));
												}
												carTimePriceMapper.insert(carTimePrice);
											} else {
												carTimePrice.setBeginTime(sdf.parse("00:00"));
												carTimePrice.setEndTime(sdf.parse("23:59"));
												carTimePriceMapper.insert(carTimePrice);
											}
										}
									}
								}
							}
						}

						carTimePrice.setBusiDate(null);
						carTimePrice.setType(0);
						carTimePriceMapper.insert(carTimePrice);
					}
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
	  }
	}

	public static boolean isOdd(int a) {
		if ((a & 1) != 0) { // 是奇数
			return true;
		}
		return false;
	}

	public static void main(String[] args) {
		System.out.println(isOdd(1));
	}

	/**
	* @method getCarByRent
	* @Description 包车租车查询车辆列表
	* @Author 彭善智
	* @Date 2019/3/7 15:11
	*/
	public Page<CarInfo> getCarByRent(Page<CarInfo> page, CarInfo car) {
		dataRuleFilter(car);
		car.setPage(page);
		page.setList(mapper.getCarByRent(car));
		return page;
	}
}