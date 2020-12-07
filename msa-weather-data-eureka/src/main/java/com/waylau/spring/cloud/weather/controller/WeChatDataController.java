package com.waylau.spring.cloud.weather.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.waylau.spring.cloud.weather.service.WeatherDataService;
import com.waylau.spring.cloud.weather.service.WeatherDataServiceImpl;
import com.waylau.spring.cloud.weather.vo.HourWeather;
import com.waylau.spring.cloud.weather.vo.SimpleForecast;
import com.waylau.spring.cloud.weather.vo.SimpleWeather;
import com.waylau.spring.cloud.weather.vo.WeatherResponse;
/**
 * 公众号天气控制器.通过名称查询
 * @author 张恒
 *
 */
@RestController
@RequestMapping("/wechat")
public class WeChatDataController {
	private final static Logger logger = LoggerFactory.getLogger(WeChatDataController.class);
	@Autowired
	private WeatherDataService weatherDataService;

	@GetMapping("/today/{cityName}")
	public SimpleWeather getWeatherByCityName(@PathVariable("cityName")String cityName) {
		return weatherDataService.getDataByCityName(cityName);
	}
	
	@GetMapping(value = "/dayforecast/{cityName}")
	public SimpleForecast getDayForecast(@PathVariable("cityName")String cityName) {
		SimpleForecast forecast = weatherDataService.getForecast(cityName);
		logger.info(forecast.toString());
		return forecast;
	}
	
	@GetMapping(value = "/hourcast/{cityName}")
	public List<HourWeather> getHourForecast(@PathVariable("cityName")String cityName) {
		return weatherDataService.getHourForecast(cityName);
	}
}
