package com.waylau.spring.cloud.weather.service;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class WeatherDataCollectionServiceImpl implements WeatherDataCollectionService {

	private static final String WEATHER_URI = "https://tianqiapi.com/api?version=v6&appid=81228233&appsecret=NR6c1gue&cityid=";
	private static final String FORECAST_URI = "https://tianqiapi.com/api?version=v1&appid=81228233&appsecret=NR6c1gue&cityid=";
	private static final String PPREFIX = "city:weather:";//城市当天天气key
	private static final String FORECAST = "city:forecast:";
	public static final String CITY = "citylist";
	private static final long TIME_OUT = 1800L;
	
	private final static Logger logger = LoggerFactory.getLogger(WeatherDataCollectionServiceImpl.class);
	

	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private RedisTemplate redisTemplate;
	
	@Override
	public void syncDataByCityId(Integer cityCode) {
		String cityName = (String) redisTemplate.opsForHash().get(CITY, cityCode);
		this.saveWeatherData(cityCode, cityName);
		this.saveWeatherForecast(cityCode, cityName);
	}
	
	/**
	 * 同步实时天气到缓存中
	 * @param uri
	 */
	private void saveWeatherData(Integer cityCode, String cityName) {
		String key = PPREFIX + cityName;
		String uri = WEATHER_URI + cityCode;
		requestAndSave(key, uri);
		logger.info("data service: " + key + "Weather sync complete");

	}
	
	/**
	 * 同步天气预报到缓存中
	 * @param cityCode
	 * @param uri
	 */
	private void saveWeatherForecast (Integer cityCode, String cityName) {
		 String key = FORECAST + cityName;
		 String uri = FORECAST_URI + cityCode;
		 requestAndSave(key, uri);
		 logger.info("data service: " + key + "forecast sync complete");
	}
	
	/**
	 * 按照城市id请求天气api, 按照城市名称将当天城市天气以及预报天气存起来
	 * @param key
	 * @param uri
	 */
	private void requestAndSave(String key, String uri) {
		String strBody = null;
		ObjectMapper mapper = new ObjectMapper();
		ValueOperations<String, String> ops = redisTemplate.opsForValue();
		
		//将天气数据写入到redis, 覆盖或者新增
		ResponseEntity<String> respString = restTemplate.getForEntity(uri, String.class);
		if (respString.getStatusCodeValue() == 200) {
			strBody = respString.getBody();
		} else {
			logger.info("data service: request fail!");
		}
		//数据写入缓存
		ops.set(key, strBody, TIME_OUT,TimeUnit.SECONDS);
	}
}
