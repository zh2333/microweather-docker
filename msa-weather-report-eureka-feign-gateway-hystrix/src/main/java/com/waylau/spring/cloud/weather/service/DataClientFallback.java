package com.waylau.spring.cloud.weather.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.waylau.spring.cloud.weather.vo.City;
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
		
		City city = new City();
		city.setCityId("101280601");
		city.setCityName("深圳");
		
		cityList.add(city);
		
		city = new City();
		city.setCityId("101280301");
		city.setCityName("惠州");
		
		cityList.add(city);
		return cityList;
	}

	@Override
	public WeatherResponse getDataByCityId(String cityId) {
		
		return null;
	}

	@Override
	public WeatherResponse getDataByCityName(String cityName) {
		// TODO Auto-generated method stub
		return null;
	}

}
