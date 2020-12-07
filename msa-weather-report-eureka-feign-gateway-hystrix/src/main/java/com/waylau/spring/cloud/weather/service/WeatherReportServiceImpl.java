package com.waylau.spring.cloud.weather.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.waylau.spring.cloud.weather.vo.Forecast;
import com.waylau.spring.cloud.weather.vo.HourWeather;
import com.waylau.spring.cloud.weather.vo.SimpleForecast;
import com.waylau.spring.cloud.weather.vo.SimpleWeather;
import com.waylau.spring.cloud.weather.vo.Weather;
import com.waylau.spring.cloud.weather.vo.WeatherResponse;

@Service
public class WeatherReportServiceImpl implements WeatherReportService {
	
	@Autowired
	private DataClient dataClient;
	
	
	@Override
	public WeatherResponse getDataByCityId(String cityId) {
		WeatherResponse resp = dataClient.getDataByCityId(cityId);
		return resp;
	}


	@Override
	public SimpleWeather getDataByCityName(String cityName) {
		SimpleWeather resp = dataClient.getDataByCityName(cityName);
		return resp;
	}


	@Override
	public SimpleForecast getForecast(String cityName) {
		SimpleForecast forecast = dataClient.getForecast(cityName);
		return forecast;
	}


	@Override
	public List<HourWeather> getHourWeather(String cityName) {
		List<HourWeather> hourWeathers = dataClient.getHourForecast(cityName);
		return hourWeathers;
	}

}
