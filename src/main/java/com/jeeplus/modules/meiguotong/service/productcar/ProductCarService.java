/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.service.productcar;

import java.text.ParseException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.app.AppErrorUtils;
import com.jeeplus.modules.meiguotong.entity.productcar.ProductCar;
import com.jeeplus.modules.meiguotong.entity.scenicspotticket.ScenicSpotTicket;
import com.jeeplus.modules.meiguotong.mapper.productcar.ProductCarMapper;
import com.jeeplus.modules.sys.utils.CodeGenUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 购物车Service
 * @author dong
 * @version 2018-09-17
 */
@Service
@Transactional(readOnly = true)
public class ProductCarService extends CrudService<ProductCarMapper, ProductCar> {
	@Autowired
	private ProductCarSaveService productCarSaveService;
	public ProductCar get(String id) {
		return super.get(id);
	}
	
	public List<ProductCar> findList(ProductCar productCar) {
		return super.findList(productCar);
	}
	
	public Page<ProductCar> findPage(Page<ProductCar> page, ProductCar productCar) {
		return super.findPage(page, productCar);
	}
	
	@Transactional(readOnly = false)
	public void save(ProductCar productCar) {
		super.save(productCar);
	}
	
	@Transactional(readOnly = false)
	public void delete(ProductCar productCar) {
		super.delete(productCar);
	}
	/**
	 * 购物车删除（多个id）
	 * @param ids
	 */
	@Transactional(readOnly = false)
	public void deleteCar(String ids) {
		String[] carIds = ids.split(",");
		mapper.deleteCar(carIds);
	}
	
	//根据用户ID查询购物列表
	
	public Page<ProductCar> findproductCarByMemberId(Page<ProductCar> page, ProductCar productCar) {
		dataRuleFilter(productCar);
		productCar.setPage(page);
		page.setList(mapper.findproductCarListByMemberId(productCar));
		return page;
	}
	public List<ProductCar> findproductCarByMemberId(Integer id,String languageid){
		return mapper.findproductCarByMemberId(id, languageid);
	};
	
	//查询购物车各类型订单数量
	public List<ProductCar> findNumGroupByType(Integer id,String languageid){
		return mapper.findNumGroupByType(id,languageid);
	};
	
	
	//根据ID查询购物车信息
	public List<ProductCar> findProductCarById(String id){
		return mapper.findProductCarById(id);
	};
	
	/**
	 * 购物车下单
	 * @param uid
	 * @param languageid
	 * @param contactsName
	 * @param contactsMobile
	 * @param remark
	 * @param carOrder
	 * @return
	 * @throws ParseException 
	 */
	@Transactional(readOnly = false)
	public AjaxJson saveOrderByCar(String uid,String languageid,String contactsName,
						String contactsMobile,String remark,String carOrder) throws ParseException{
							
		AjaxJson ajaxJson = new AjaxJson();
		JSONArray jsonArray = JSONArray.fromObject(carOrder); 
		for(int i=0;i<jsonArray.size();i++){
			 //取出数组第i个元素 
			 JSONObject jpro = jsonArray.getJSONObject(i);
			 String type = jpro.getString("type"); //日期
			 if (StringUtils.isBlank(type)) {
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_57);
				ajaxJson.setMsg(AppErrorUtils.error_57_desc);
				return ajaxJson;
			}
			 //数据验证
			 if(Integer.parseInt(type)==1){//常规路线
				 ajaxJson = productCarSaveService.checkRoute1(jpro);
			 }else if(Integer.parseInt(type)==2){//当地参团
				 ajaxJson = productCarSaveService.checkRoute2(jpro);
			 }else if(Integer.parseInt(type)==3){//当地玩家
				 ajaxJson = productCarSaveService.checkGuide(jpro);
			 }else if(Integer.parseInt(type)==4){//游轮
				 ajaxJson = productCarSaveService.checkLinerLine(jpro);
			 }else if(Integer.parseInt(type)==5){//景点门票
				 ajaxJson = productCarSaveService.checkScenicSpot(jpro);
			 }else if(Integer.parseInt(type)==6){//导游路线
				 ajaxJson = productCarSaveService.checkGuideRoute(jpro);
			 }
			 if (!ajaxJson.isSuccess()) {
				return ajaxJson;
			}
		}
		String payOrderNo = CodeGenUtils.getNowDate();
		 for(int i=0;i<jsonArray.size();i++){
			 //取出数组第i个元素 
			 JSONObject jpro = jsonArray.getJSONObject(i);
			 String type = jpro.getString("type"); //日期
			 //生成订单
			 if(Integer.parseInt(type)==1){//常规路线
				 productCarSaveService.saveRoute1
						 (uid,languageid,contactsName,contactsMobile,remark,jpro,payOrderNo);
			 }else if(Integer.parseInt(type)==2){//当地参团
				 productCarSaveService.saveRoute2
						 (uid,languageid,contactsName,contactsMobile,remark,jpro,payOrderNo);
			 }else if(Integer.parseInt(type)==3){//当地玩家
				 productCarSaveService.saveGuide
						 (uid,languageid,contactsName,contactsMobile,remark,jpro,payOrderNo);
			 }else if(Integer.parseInt(type)==4){//游轮
				 productCarSaveService.saveLinerLine
						 (uid,languageid,contactsName,contactsMobile,remark,jpro,payOrderNo);
			 }else if(Integer.parseInt(type)==5){//景点门票
				 productCarSaveService.saveScenicSpot
						 (uid,languageid,contactsName,contactsMobile,remark,jpro,payOrderNo);
			 }else if(Integer.parseInt(type)==6){//导游路线
				 productCarSaveService.saveGuideRoute
						 (uid,languageid,contactsName,contactsMobile,remark,jpro,payOrderNo);
			 }
		 }
		 for(int i=0;i<jsonArray.size();i++){
			 //取出数组第i个元素 
			 JSONObject jpro = jsonArray.getJSONObject(i);
			 String id = jpro.getString("id"); //购物车id
			//删除购物车
			 ProductCar productCar = new ProductCar();
			 productCar.setId(id);
			 mapper.delete(productCar);
		 }
		ajaxJson.getBody().put("payOrderNo", payOrderNo);
		ajaxJson.setSuccess(true);
		ajaxJson.setErrorCode(AppErrorUtils.error_59);
		ajaxJson.setMsg(AppErrorUtils.error_59_desc);
		return	ajaxJson;		
	}
	
}