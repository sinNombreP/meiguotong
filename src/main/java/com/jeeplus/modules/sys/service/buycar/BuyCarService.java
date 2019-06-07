/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.service.buycar;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.sys.entity.buycar.BuyCar;
import com.jeeplus.modules.sys.mapper.buycar.BuyCarMapper;

/**
 * 购物车Service
 * @author dong
 * @version 2018-03-08
 */
@Service
@Transactional(readOnly = true)
public class BuyCarService extends CrudService<BuyCarMapper, BuyCar> {

	public BuyCar get(String id) {
		return super.get(id);
	}
	
	public List<BuyCar> findList(BuyCar buyCar) {
		return super.findList(buyCar);
	}
	
	public Page<BuyCar> findPage(Page<BuyCar> page, BuyCar buyCar) {
		return super.findPage(page, buyCar);
	}
	
	@Transactional(readOnly = false)
	public void save(BuyCar buyCar) {
		super.save(buyCar);
	}
	
	@Transactional(readOnly = false)
	public void delete(BuyCar buyCar) {
		super.delete(buyCar);
	}
	
}