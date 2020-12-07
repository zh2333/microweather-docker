package com.waylau.spring.cloud.weather.service;

import java.util.List;

import com.waylau.spring.cloud.weather.vo.HourWeather;
import com.waylau.spring.cloud.weather.vo.SimpleForecast;
import com.waylau.spring.cloud.weather.vo.SimpleWeather;
import com.waylau.spring.cloud.weather.vo.WeatherResponse;

/**
 * Weather Data Service.
 * 
 * @since 1.0.0 2017年11月22日
 * @author <a href="https://waylau.com">Way Lau</a> 
 */
public interface WeatherDataService {
	/**
	 * 根据城市ID查询每日天气数据
	 * 
	 * @param cityId
	 * @return
	 */
	SimpleWeather getDataByCityId(String cityId);

	/**
	 * 根据城市名称查询每日天气数据(响应公众号请求)
	 * 
	 * @param cityId
	 * @return
	 */
	SimpleWeather getDataByCityName(String cityName);
	
	/**
	 * 获取当天天气以及后面几天的预报天气(响应页面请求)
	 * @param cityName
	 * @return
	 */
	WeatherResponse getWeatherAndForecast(Integer cityCode);
	
	/**
	 * 获取后面几天的天气预报(响应微信公众号请求)
	 * @param cityCode
	 * @return
	 */
	SimpleForecast getForecast(String cityName);
	
	/**
	 * 获取接下来几个小时的天气预报(响应微信公众号请求)
	 * @return
	 */
	List<HourWeather> getHourForecast(String cityName);
	
	
}
