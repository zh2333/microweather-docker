package com.waylau.spring.cloud.weather.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.waylau.spring.cloud.weather.service.WeatherDataService;
import com.waylau.spring.cloud.weather.vo.SimpleWeather;
import com.waylau.spring.cloud.weather.vo.WeatherResponse;
/**
 * Weather Controller.
 * 网页天气控制器.通过城市id查询
 */
@RestController
@RequestMapping("/weather")
public class WeatherController {
	@Autowired
	private WeatherDataService weatherDataService;
	
	@GetMapping("/cityId/{cityId}")
	public WeatherResponse getWeatherByCityId(@PathVariable("cityId") Integer cityId) {
		return weatherDataService.getWeatherAndForecast(cityId);
	}
}
