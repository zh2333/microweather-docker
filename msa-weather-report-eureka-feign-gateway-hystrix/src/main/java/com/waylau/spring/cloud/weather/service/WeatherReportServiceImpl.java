package com.waylau.spring.cloud.weather.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.waylau.spring.cloud.weather.vo.Forecast;
import com.waylau.spring.cloud.weather.vo.Weather;
import com.waylau.spring.cloud.weather.vo.WeatherResponse;

@Service
public class WeatherReportServiceImpl implements WeatherReportService {
	
	@Autowired
	private DataClient dataClient;
	
	
	@Override
	public Weather getDataByCityId(String cityId) {
		WeatherResponse resp = dataClient.getDataByCityId(cityId);
		
		Weather data = null;
		if(resp != null) {
			data = resp.getData();
		}
		
		return data;
	}


	@Override
	public Weather getDataByCityName(String cityName) {
		WeatherResponse resp = dataClient.getDataByCityName(cityName);
		Weather data = null;
		if(resp != null) {
			data = resp.getData();
		}
		return data;
	}

}
