package com.waylau.spring.cloud.weather.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.waylau.spring.cloud.weather.vo.City;
import com.waylau.spring.cloud.weather.vo.HourWeather;
import com.waylau.spring.cloud.weather.vo.SimpleForecast;
import com.waylau.spring.cloud.weather.vo.SimpleWeather;
import com.waylau.spring.cloud.weather.vo.WeatherResponse;

/**
 * hystrix回调类, 当report微服务依赖的微服务出现错误时会回调这个类中相应的方法
 * @author 张恒
 *
 */
@Component
public class DataClientFallback implements DataClient {

	@Override
	public List<City> listCity() throws Exception {
		List<City> cityList = null;
		cityList = new ArrayList<>();
		
		City city = new City(101280601, "深圳");
		cityList.add(city);
		
		city = new City(101280301, "惠州");		
		cityList.add(city);
		return cityList;
	}

	@Override
	public WeatherResponse getDataByCityId(String cityId) {
		//TODO
		return null;
	}

	@Override
	public SimpleWeather getDataByCityName(String cityName) {
		// TODO 
		return null;
	}

	@Override
	public List<HourWeather> getHourForecast(String cityName) {
		// TODO
		return null;
	}

	@Override
	public SimpleForecast getForecast(String cityName) {
		// TODO 
		return null;
	}

//	@Override
//	public void pushRegister(String cityName, String userId) {
//		// TODO Auto-generated method stub
//		
//	}

}
