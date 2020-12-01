package com.waylau.spring.cloud.weather.service;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
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
import com.waylau.spring.cloud.weather.vo.WeatherResponse;

/**
 * WeatherDataService 实现.
 */
@Service
public class WeatherDataServiceImpl implements WeatherDataService {
	private static final String WEATHER_URI = "http://wthrcdn.etouch.cn/weather_mini?";
	private static final long TIME_OUT = 1800L;
	private final static Logger logger = LoggerFactory.getLogger(WeatherDataServiceImpl.class);

	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	
	@Override
	public WeatherResponse getDataByCityId(String cityId) {
		String uri = WEATHER_URI + "citykey=" + cityId;
		return this.doGetWeahter(uri);
	}

	@Override
	public WeatherResponse getDataByCityName(String cityName) {
		return this.doGetWeatherByCityName(cityName);
	}
	
	private WeatherResponse doGetWeahter(String uri) {
		String key = uri;
		WeatherResponse resp = null;
		String strBody = null;
		ObjectMapper mapper = new ObjectMapper();
		ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
		//增加逻辑, 如果redis中有, 就取redis中的数据
		if(stringRedisTemplate.hasKey(key)) {
			logger.info("redis has data" + key);
			strBody = ops.get(key);
		} else {
			//如果redis中没有, 就抛出异常
			logger.info("redis do not has data");
			throw new RuntimeException("redis中没有该城市数据!");

		}
		try {
			resp = mapper.readValue(strBody, WeatherResponse.class);
		} catch (IOException e) {
			logger.error("error!",e);
		}
		return resp;
	}
	
	/**
	 * 通过城市名称获取城市天气
	 * @param cityName
	 * @return
	 */
	private WeatherResponse doGetWeatherByCityName(String cityName) {
		String uri = WEATHER_URI + "city=" + cityName;
		Date date = new Date();
		DateFormat currDate = DateFormat.getDateInstance();
		String key = currDate + ":" + cityName;
		String strBody = null;
		WeatherResponse resp = null;
		ObjectMapper mapper = new ObjectMapper();
		ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
		
		//先去本地redis中取, 如果没有就直接请求uri
		//1.去redis中取
		if(stringRedisTemplate.hasKey(key)) {
			logger.info("redis has data" + key);
			strBody = ops.get(key);
		} else {
			//2.redis中没有请求uri
			ResponseEntity<String> respString = restTemplate.getForEntity(uri, String.class);
			if (respString.getStatusCodeValue() == 200) {
				strBody = respString.getBody();
				//3.将请求到的数据存入redis中, 提高别人查询的命中率
				ops.set(key, strBody, TIME_OUT,TimeUnit.SECONDS);
			} else {
				strBody = "";
				logger.info("redis not has data:" + key +" request fail");
				throw new RuntimeException("redis not has data:" + key +" request fail");
			}
		}
		try {
			resp = mapper.readValue(strBody, WeatherResponse.class);
		} catch (IOException e) {
			logger.error("redis data convert error!",e);
		}
		
		return resp;
	}

}
