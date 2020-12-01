package com.waylau.spring.cloud.weather.service;

/**
 * 天气数据采集微服务接口
 * @author 张恒
 *
 */
public interface WeatherDataCollectionService {
	
	/**
	 * 通过城市id同步天气
	 * @param cityId
	 */
	void syncDataByCityId(String cityId);

}
