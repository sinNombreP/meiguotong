package com.jeeplus.modules.meiguotong.entity.comcity;

import java.util.List;

/**
 * 容器类
 * @author Administrator
 *
 */
public class ComCityModel {
   private List<ComCity2>  comCity;
   public ComCityModel(List<ComCity2> comCity){
   	super();
   	this.comCity=comCity;
   }
   public ComCityModel(){
   	super();
   }
public List<ComCity2> getComCity() {
	return comCity;
}

public void setComCity(List<ComCity2> comCity) {
	this.comCity = comCity;
}
   
}
