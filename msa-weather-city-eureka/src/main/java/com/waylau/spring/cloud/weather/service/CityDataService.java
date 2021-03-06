/**
 * 
 */
package com.waylau.spring.cloud.weather.service;

import java.util.List;

import com.waylau.spring.cloud.weather.vo.City;

/**
 * 城市数据服务
 * @author 张恒
 *
 */
public interface CityDataService {
	
	/**
	 * 获取城市列表
	 * @return
	 * @throws Exception
	 */
	List<City> listCity() throws Exception;
	
	/**
	 * 同步城市代码和城市名称的映射到redis
	 * @param cityList
	 */
	void saveCityMap(List<City> cityList);

}
