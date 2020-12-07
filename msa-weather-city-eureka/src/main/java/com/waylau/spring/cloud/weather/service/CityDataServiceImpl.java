/**
 * 
 */
package com.waylau.spring.cloud.weather.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.waylau.spring.cloud.constparamter.Const;
import com.waylau.spring.cloud.weather.util.JsonBuilder;
import com.waylau.spring.cloud.weather.vo.City;

/**
 * @author 张恒
 *
 */
@Service
public class CityDataServiceImpl implements CityDataService {
	private final static Logger logger = LoggerFactory.getLogger(CityDataServiceImpl.class);
	
	@Autowired
	private RedisTemplate redisTemplate;
	
	@Override
	public List<City> listCity() throws Exception {
		logger.info("parse json..........");
		//将xml转为java对象
		List<City> cityList = (List<City>) JsonBuilder.parseJsonToList(City.class, "citylist.json");
		return cityList;
	}
	
	/**
	 * 将城市和城市名称存入redis
	 */
	public void saveCityMap(List<City> cityList) {
		Map<String, String> cityMap = new HashMap();//城市名称和城市代码的映射
		
		for (City city:cityList) {
			cityMap.put(String.valueOf(city.getCityCode()), city.getCityName());
		}
		redisTemplate.opsForHash().putAll(Const.CITY, cityMap);
	}

}
