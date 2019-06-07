/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.service.car;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.meiguotong.entity.car.CarBusiness;
import com.jeeplus.modules.meiguotong.entity.car.CarInfo;
import com.jeeplus.modules.meiguotong.mapper.car.CarBusinessMapper;
import com.jeeplus.modules.meiguotong.mapper.car.CarTimePriceMapper;

/**
 * 汽车业务表Service
 * @author psz
 * @version 2018-08-31
 */
@Service
@Transactional(readOnly = true)
public class CarBusinessService extends CrudService<CarBusinessMapper, CarBusiness> {
	
	@Autowired
	private CarTimePriceMapper carTimePriceMapper;

	public CarBusiness get(String id) {
		return super.get(id);
	}
	
	public List<CarBusiness> findList(CarBusiness carBusiness) {
		return super.findList(carBusiness);
	}
	
	public Page<CarBusiness> findPage(Page<CarBusiness> page, CarBusiness carBusiness) {
		return super.findPage(page, carBusiness);
	}
	
	@Transactional(readOnly = false)
	public void save(CarBusiness carBusiness) {
		super.save(carBusiness);
	}
	
	@Transactional(readOnly = false)
	public void delete(CarBusiness carBusiness) {
		super.delete(carBusiness);
	}

	/** 
	* @Title: findListByCarid 
	* @Description: 获取汽车业务
	* @author 彭善智
	* @date 2018年9月3日下午4:50:52
	*/ 
	public List<CarBusiness> findListByCarid(CarInfo car) {
		List<CarBusiness> list = new ArrayList<CarBusiness>();
		for(int i = 1; i<6; i++) {
			CarBusiness carBusiness = new CarBusiness();
			carBusiness.setBusinessType(i);
			if(StringUtils.isNotBlank(car.getId())) {
				carBusiness.setCarid(Integer.parseInt(car.getId()));
				carBusiness = mapper.getDataByCarid(carBusiness);
				if(i == 1) {
					carBusiness.setCarTimePriceList(carTimePriceMapper.getDataByCarBusinessid(carBusiness));
				}
			}
			list.add(carBusiness);
		}
		return list;
	}
	
	
	public static void main(String[] args) {
		SimpleDateFormat sdf=new SimpleDateFormat("HH:mm:ss");
		try {
			Date date=sdf.parse("15:30:22");
			System.out.println(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
}