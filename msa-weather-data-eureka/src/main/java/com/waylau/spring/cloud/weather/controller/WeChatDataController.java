package com.waylau.spring.cloud.weather.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.waylau.spring.cloud.weather.service.WeatherDataService;
import com.waylau.spring.cloud.weather.vo.WeatherResponse;

@RestController
@RequestMapping("/wechat")
public class WeChatDataController {

	@Autowired
	private WeatherDataService weatherDataService;

	@GetMapping("/desc/{cityName}")
	public WeatherResponse getWeatherByCityName(@PathVariable("cityName")String cityName) {
		return weatherDataService.getDataByCityName(cityName);
	}
}
