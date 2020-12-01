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
	@RequestMapping(value = "/data/wechat/desc/{cityName}", method = RequestMethod.GET)
	WeatherResponse getDataByCityName(@PathVariable("cityName")String cityName);

}
