package com.waylau.spring.cloud.weather.service;

import java.util.List;

import com.waylau.spring.cloud.weather.vo.City;

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
	void syncDataByCityId(Integer cityId);

}
