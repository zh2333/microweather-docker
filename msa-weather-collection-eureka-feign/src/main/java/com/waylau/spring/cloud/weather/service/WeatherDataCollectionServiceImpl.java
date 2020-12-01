package com.waylau.spring.cloud.weather.service;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class WeatherDataCollectionServiceImpl implements WeatherDataCollectionService {

	private static final String WEATHER_URI = "http://wthrcdn.etouch.cn/weather_mini?";
	private static final long TIME_OUT = 1800L;
	
	private final static Logger logger = LoggerFactory.getLogger(WeatherDataCollectionServiceImpl.class);
	

	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	
	@Override
	public void syncDataByCityId(String cityId) {
		String uri = WEATHER_URI + "citykey=" + cityId;
		this.saveWeatherData(uri);
		
	}
	
	/**
	 * 将天气数据放到缓存中
	 * @param uri
	 */
	private void saveWeatherData(String uri) {
		String key = uri;
		String strBody = null;
		ObjectMapper mapper = new ObjectMapper();
		ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
		
		//将天气数据写入到redis, 覆盖或者新增
		ResponseEntity<String> respString = restTemplate.getForEntity(uri, String.class);
		if (respString.getStatusCodeValue() == 200) {
			strBody = respString.getBody();
		}
		
		
		//数据写入缓存
		ops.set(key, strBody, TIME_OUT,TimeUnit.SECONDS);
		
	}

}
