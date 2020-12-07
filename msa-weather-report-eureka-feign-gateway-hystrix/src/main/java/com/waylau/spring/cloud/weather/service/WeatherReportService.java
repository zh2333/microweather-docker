package com.waylau.spring.cloud.weather.service;

import java.util.List;

import com.waylau.spring.cloud.weather.vo.HourWeather;
import com.waylau.spring.cloud.weather.vo.SimpleForecast;
import com.waylau.spring.cloud.weather.vo.SimpleWeather;
import com.waylau.spring.cloud.weather.vo.Weather;
import com.waylau.spring.cloud.weather.vo.WeatherResponse;

/**
 * 天气预报服务接口
 * @author 张恒
 *
 */
public interface WeatherReportService {
	
	/*根据城市id获取天气信息*/
	WeatherResponse getDataByCityId(String cityId);
	
	/*根据城市名称获取天气*/
	SimpleWeather getDataByCityName(String cityName);
	
	/**
	 * 获取每天天气预报
	 * @param cityName
	 * @return
	 */
	SimpleForecast getForecast(String cityName);
	
	/**
	 * 获取每小时天气预报
	 * @param cityName
	 * @return
	 */
	List<HourWeather> getHourWeather(String cityName);
	
	

}
