/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.service.collection;

import java.util.Date;
import java.util.List;

import javax.enterprise.inject.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.meiguotong.entity.collection.ProductCollection;
import com.jeeplus.modules.meiguotong.entity.guide.Guide;
import com.jeeplus.modules.meiguotong.entity.liner_line.LinerLine;
import com.jeeplus.modules.meiguotong.entity.product.Route;
import com.jeeplus.modules.meiguotong.entity.productcar.ProductCar;
import com.jeeplus.modules.meiguotong.entity.scenicspot.ScenicSpot;
import com.jeeplus.modules.meiguotong.mapper.collection.ProductCollectionMapper;
import com.jeeplus.modules.meiguotong.mapper.productcar.ProductCarMapper;
import com.jeeplus.modules.meiguotong.service.guide.GuideService;
import com.jeeplus.modules.meiguotong.service.liner_line.LinerLineService;
import com.jeeplus.modules.meiguotong.service.product.RouteService;
import com.jeeplus.modules.meiguotong.service.scenicspot.ScenicSpotService;

/**
 * 我的收藏Service
 * @author dong
 * @version 2018-09-14
 */
@Service
@Transactional(readOnly = true)
public class ProductCollectionService extends CrudService<ProductCollectionMapper, ProductCollection> {
	
	@Autowired
	private ProductCarMapper productCarMapper;
	@Autowired
	private RouteService routeService;
	@Autowired
	private GuideService guideService;
	@Autowired
	private ScenicSpotService scenicSpotService;
	@Autowired
	private LinerLineService linerLineService;
	
	public ProductCollection get(String id) {
		return super.get(id);
	}
	
	public List<ProductCollection> findList(ProductCollection productCollection) {
		return super.findList(productCollection);
	}
	/**
	 * 获取我的路线/路线收藏列表接口
	 * @param page
	 * @param productCollection
	 * @return
	 */
	public Page<ProductCollection> myCollectionRoute(Page<ProductCollection> page, ProductCollection productCollection) {
		dataRuleFilter(productCollection);
		productCollection.setPage(page);
		page.setList(mapper.myCollectionRoute(productCollection));
		return page;
	}
	/**
	 * 获取我的玩家收藏列表接口
	 * @param page
	 * @param productCollection
	 * @return
	 */
	public Page<ProductCollection> myCollectionGuide(Page<ProductCollection> page, ProductCollection productCollection) {
		dataRuleFilter(productCollection);
		productCollection.setPage(page);
		page.setList(mapper.myCollectionGuide(productCollection));
		return page;
	}
	/**
	 * 获取我的游轮收藏列表接口
	 * @param page
	 * @param productCollection
	 * @return
	 */
	public Page<ProductCollection> myCollectionLiner(Page<ProductCollection> page, ProductCollection productCollection) {
		dataRuleFilter(productCollection);
		productCollection.setPage(page);
		page.setList(mapper.myCollectionLiner(productCollection));
		return page;
	}
	/**
	 * 获取我的收藏景点列表接口
	 * @param page
	 * @param productCollection
	 * @return
	 */
	public Page<ProductCollection> myCollectionScenic(Page<ProductCollection> page, ProductCollection productCollection) {
		dataRuleFilter(productCollection);
		productCollection.setPage(page);
		page.setList(mapper.myCollectionScenic(productCollection));
		return page;
	}
	public Page<ProductCollection> findPage(Page<ProductCollection> page, ProductCollection productCollection) {
		return super.findPage(page, productCollection);
	}
	
	@Transactional(readOnly = false)
	public void save(ProductCollection productCollection) {
		super.save(productCollection);
		int type=productCollection.getType();
		//修改产品收藏数量
		if (type==1 || type==2) {
			Route route =new Route();
			route.setCollectionNum(1);
			route.setId(productCollection.getTypeid().toString());
			routeService.changecollectionNum(route);
		}else if (type==3) {
			Guide guide =new Guide();
			guide.setCollectionNum(1);
			guide.setId(productCollection.getTypeid().toString());
			guideService.changecollectionNum(guide);
		}else if (type==4) {
			LinerLine linerLine=new LinerLine();
			linerLine.setCollectionNum(1);
			linerLine.setId(productCollection.getTypeid().toString());
			linerLineService.changecollectionNum(linerLine);
		}else if (type==5) {
			ScenicSpot scenicSpot=new ScenicSpot();
			scenicSpot.setCollectionNum(1);
			scenicSpot.setId(productCollection.getTypeid().toString());
			scenicSpotService.changecollectionNum(scenicSpot);
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(ProductCollection productCollection) {
		super.delete(productCollection);
	}
	/**
	 * 判断是否被收藏过
	 * @param productCollection
	 * @return
	 */
	public Integer judgeCollection(ProductCollection productCollection) {
		return mapper.judgeCollection(productCollection);
	}
	
	/**
	 * 取消收藏（单个）
	 * @param ids
	 */
	@Transactional(readOnly = false)
	public void deleteCollection(ProductCollection productCollection) {
		mapper.deleteCollection(productCollection);
		int type=productCollection.getType();
		//修改产品收藏数量
		if (type==1 || type==2) {
			Route route =new Route();
			route.setCollectionNum(-1);
			route.setId(productCollection.getTypeid().toString());
			routeService.changecollectionNum(route);
		}else if (type==3) {
			Guide guide =new Guide();
			guide.setCollectionNum(-1);
			guide.setId(productCollection.getTypeid().toString());
			guideService.changecollectionNum(guide);
		}else if (type==4) {
			LinerLine linerLine=new LinerLine();
			linerLine.setCollectionNum(-1);
			linerLine.setId(productCollection.getTypeid().toString());
			linerLineService.changecollectionNum(linerLine);
		}else if (type==5) {
			ScenicSpot scenicSpot=new ScenicSpot();
			scenicSpot.setCollectionNum(-1);
			scenicSpot.setId(productCollection.getTypeid().toString());
			scenicSpotService.changecollectionNum(scenicSpot);
		}
	}
	/**
	 * 取消收藏（多个一起取消）
	 * @param ids
	 */
	@Transactional(readOnly = false)
	public void deleteCollection(String[] ids) {
		for(String a:ids){
			ProductCollection collection = mapper.get(a);
			collection.setId(a);
			mapper.delete(collection);
			int type=collection.getType();
			//修改产品收藏数量
			if (type==1 || type==2) {
				Route route =new Route();
				route.setCollectionNum(-1);
				route.setId(collection.getTypeid().toString());
				routeService.changecollectionNum(route);
			}else if (type==3) {
				Guide guide =new Guide();
				guide.setCollectionNum(-1);
				guide.setId(collection.getTypeid().toString());
				guideService.changecollectionNum(guide);
			}else if (type==4) {
				LinerLine linerLine=new LinerLine();
				linerLine.setCollectionNum(-1);
				linerLine.setId(collection.getTypeid().toString());
				linerLineService.changecollectionNum(linerLine);
			}else if (type==5) {
				ScenicSpot scenicSpot=new ScenicSpot();
				scenicSpot.setCollectionNum(-1);
				scenicSpot.setId(collection.getTypeid().toString());
				scenicSpotService.changecollectionNum(scenicSpot);
			}
		}
	}
	/**
	 * 收藏加入购物车
	 * @param ids
	 */
	@Transactional(readOnly = false)
	public void addCollectionToCar(String[] ids) {
		for(String a:ids){
			ProductCollection collection = mapper.get(a);
			ProductCar car = new ProductCar();
			car.setMemberid(collection.getMemberid());
			car.setType(collection.getType());
			car.setTypeid(collection.getTypeid());
			car.setLanguageid(collection.getLanguageid());
			car.setCreateDate(new Date());
			productCarMapper.insert(car);
		}
	}
}