package com.waylau.spring.cloud.weather.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.waylau.spring.cloud.weather.service.DataClient;
import com.waylau.spring.cloud.weather.service.WeatherReportService;
import com.waylau.spring.cloud.weather.vo.City;
/**
 * Weather Controller.
 */
@RestController
@RequestMapping("/report")
public class WeatherReportController {
	
	private final static Logger logger = LoggerFactory.getLogger(WeatherReportController.class);

	@Autowired
	private DataClient dataClient;

	@Autowired
	private WeatherReportService weatherReportService;

	
	@GetMapping("/cityId/{cityId}")
	public ModelAndView getWeatherByCityId(@PathVariable("cityId") String cityId, Model model) throws Exception{
		List<City> cityList = null;
		try {
			
			cityList = dataClient.listCity();
		} catch (Exception e) {
			logger.error("get cities error!", e);
		}
		//绑定模型信息
		model.addAttribute("title","天气预报微服务");
		model.addAttribute("cityId", cityId);
		model.addAttribute("cityList", cityList);
		model.addAttribute("report", weatherReportService.getDataByCityId(cityId));
		return new ModelAndView("weather/report", "reportModel", model);
	}
}


