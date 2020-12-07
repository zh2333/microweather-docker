package com.waylau.spring.cloud.weather.service;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.waylau.spring.cloud.weather.vo.City;
import com.waylau.spring.cloud.weather.vo.HourWeather;
import com.waylau.spring.cloud.weather.vo.SimpleForecast;
import com.waylau.spring.cloud.weather.vo.SimpleWeather;
import com.waylau.spring.cloud.weather.vo.WeatherResponse;

/**
 * Data Client
 * @author 张恒
 *
 */
//请求转发到zuul微服务网关
@FeignClient(name="zuul-service", fallback=DataClientFallback.class)
public interface DataClient {
	
	/**
	 * 获取城市列表
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/city/cities")
	List<City> listCity() throws Exception;
	
	
	/**
	 * 根据城市id获取城市天气信息
	 * 
	 */
	@GetMapping(value = "/data/weather/cityId/{cityId}")
	WeatherResponse getDataByCityId(@PathVariable("cityId") String cityId);
	
	/**
	 * 调用data-service, 获取今日天气, 参数设置在URL中
	 * @param cityName
	 * @return
	 */
	@RequestMapping(value = "/data/wechat/today/{cityName}", method = RequestMethod.GET)
	SimpleWeather getDataByCityName(@PathVariable("cityName")String cityName);
	
	/**
	 * 获取后面几天的天气预报(响应微信公众号请求)
	 * @param cityCode
	 * @return
	 */
	@RequestMapping(value = "/data/wechat/dayforecast/{cityName}", method = RequestMethod.GET)
	SimpleForecast getForecast(@PathVariable("cityName")String cityName);
	/**
	 * 获取接下来几个小时的天气预报(响应微信公众号请求)
	 * @return
	 */
	@RequestMapping(value = "/data/wechat/hourcast/{cityName}", method = RequestMethod.GET)
	List<HourWeather> getHourForecast(@PathVariable("cityName")String cityName);
	/**
	 * 为查询过某个城市天气的用户注册天气推送服务(推送每天的天气).相当于某个用户订阅了某个城市的天气预报
	 */
//	@RequestMapping(value = "/push/wechat/{cityName}/{userId}", method = RequestMethod.GET)
//	void pushRegister(@PathVariable("cityName")String cityName, @PathVariable("userId")String userId);
	
}
