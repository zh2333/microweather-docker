package com.waylau.spring.cloud.weather.service;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.waylau.spring.cloud.weather.vo.DayWeather;
import com.waylau.spring.cloud.weather.vo.Forecast;
import com.waylau.spring.cloud.weather.vo.HourWeather;
import com.waylau.spring.cloud.weather.vo.SimpleDayWeather;
import com.waylau.spring.cloud.weather.vo.SimpleForecast;
import com.waylau.spring.cloud.weather.vo.SimpleWeather;
import com.waylau.spring.cloud.weather.vo.Weather;
import com.waylau.spring.cloud.weather.vo.WeatherResponse;

/**
 * WeatherDataService 实现.
 */
@Service
public class WeatherDataServiceImpl implements WeatherDataService {
	private static final String WEATHER_URI = "https://tianqiapi.com/api?version=v6&appid=81228233&appsecret=NR6c1gue&city=";
	private static final String FORECAST_URI = "https://tianqiapi.com/api?version=v1&appid=81228233&appsecret=NR6c1gue&city=";
	private static final long TIME_OUT = 1800L;
	public static final String CITY = "citylist";
	private static final String PPREFIX = "city:weather:";//城市当天天气key
	private static final String FORECAST = "city:forecast:";//城市预报天气key
	private final static Logger logger = LoggerFactory.getLogger(WeatherDataServiceImpl.class);

	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private RedisTemplate redisTemplate;
	
	@Override
	public SimpleWeather getDataByCityId(String cityCode) {
		String cityName = (String) redisTemplate.opsForHash().get(CITY, cityCode);
		return this.doGetWeahter(cityName);
	}

	@Override
	public SimpleWeather getDataByCityName(String cityName) {
		return this.doGetWeahter(cityName);
	}
	
	private SimpleWeather doGetWeahter(String cityName) {
		String key = PPREFIX + cityName;
		Weather oriWeather = null;
		String strBody = null;
		ObjectMapper mapper = new ObjectMapper();
		ValueOperations<String, String> ops = redisTemplate.opsForValue();
		//1.从redis中取出当天原始天气信息
		if(redisTemplate.hasKey(key)) {
			logger.info("doGetWeahter:redis has weather of today" + key);
			strBody = ops.get(key);
		} else {//如果没有该城市信息,则去请求uri
			strBody = requestWithinRedis(cityName, WEATHER_URI, key);
		}
		try {
			oriWeather = mapper.readValue(strBody, Weather.class);
			return oriWeather.getSimpleWeather();
		} catch (IOException e) {
			logger.error("strBody parse error , null or type error!",e);
		}
		return null;
	}

	@Override
	public WeatherResponse getWeatherAndForecast(Integer cityCode) {
		String cityName = (String) redisTemplate.opsForHash().get(CITY, cityCode);
		String weatherKey = PPREFIX + cityName;
		String forecastKey = FORECAST + cityName;
		Weather oriWeather = null;
		SimpleWeather simpleWeather = null;
		SimpleForecast simpleyForecast = null;
		String weatherBody = null;
		String forecastBody = null;
		Forecast forecast = null;
		ObjectMapper mapper = new ObjectMapper();
		ValueOperations<String, String> ops = redisTemplate.opsForValue();
		try {
			if (redisTemplate.hasKey(weatherKey) && redisTemplate.hasKey(forecastKey)) {
				logger.info("redis has data" + weatherKey);
				weatherBody = ops.get(weatherKey);
				forecastBody = ops.get(forecastKey);
			} else {
				//如果redis中没有, 请求uri
				weatherBody = requestWithinRedis(cityName, WEATHER_URI, weatherKey);
				forecastBody = requestWithinRedis(cityName, FORECAST_URI, forecastKey);
				logger.info("getWeatherAndForecast:redis do not has data:" + weatherKey + " and " + forecastKey + "requesting...");
			}
			oriWeather = mapper.readValue(weatherBody, Weather.class);
			forecast = mapper.readValue(forecastBody, Forecast.class);
			simpleWeather = oriWeather.getSimpleWeather();
			simpleyForecast = forecast.getSimpleForecast();
			WeatherResponse resp = new WeatherResponse(simpleWeather, simpleyForecast);
			return resp;
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("getWeatherAndForecast: mapper readvalue error!");
		}
		return null;
	}

	@Override
	public SimpleForecast getForecast(String cityName) {
		String forecastKey = FORECAST + cityName;
		SimpleForecast simpleyForecast = null;
		Forecast forecast = null;
		String forecastBody = null;
		ObjectMapper mapper = new ObjectMapper();
		ValueOperations<String, String> ops = redisTemplate.opsForValue();
		try {
			if (redisTemplate.hasKey(forecastKey)) {
				forecastBody = ops.get(forecastKey);
				//logger.info("getForecast: redis forecastBody" + forecastBody);
			} else {
				//如果redis中没有, 请求uri
				logger.info("getForecast:redis do not has data, request uri:" + FORECAST_URI + cityName);
				forecastBody = requestWithinRedis(cityName, FORECAST_URI, forecastKey);
				//logger.info("getForecast: request forecastBody" + forecastBody);
			}
			forecast = mapper.readValue(forecastBody, Forecast.class);
			simpleyForecast = forecast.getSimpleForecast();
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("getForecast: data service: mapper readvalue error!");
		}
		//logger.info("simpleForecast|" + simpleyForecast.toString());
		return simpleyForecast;
	}

	@Override
	public List<HourWeather> getHourForecast(String cityName) {
		String forecastKey = FORECAST + cityName;
		List<HourWeather> simpleyForecast = null;
		Forecast forecast = null;
		List<HourWeather> hours = null;
		String forecastBody = null;
		ObjectMapper mapper = new ObjectMapper();
		ValueOperations<String, String> ops = redisTemplate.opsForValue();
		try {
			if (redisTemplate.hasKey(forecastKey)) {
				forecastBody = ops.get(forecastKey);
			} else {
				//如果redis中没有, 请求uri
				forecastBody = requestWithinRedis(cityName, FORECAST_URI, forecastKey);
				logger.info("getHourForecast:redis do not has data, request uri:" + FORECAST_URI + cityName);
			}
			forecast = mapper.readValue(forecastBody, Forecast.class);
			hours = forecast.getData().get(0).getHours();
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("data service getHourForecast: mapper readvalue error!");
		}
		return hours;
	}
	/**
	 * 在redis中没有数据的情况下, 去请求uri解析, 并将请求结果写入到redis中
	 * @param cityName 城市名称
	 * @param uriPrefix 请求前缀
	 * @return
	 */
	private String requestWithinRedis(String cityName, String uriPrefix, String key) {
		String strBody = null;
		String tmpUri = uriPrefix + cityName;
		ValueOperations<String, String> ops = redisTemplate.opsForValue();
		ResponseEntity<String> respString = restTemplate.getForEntity(tmpUri, String.class);
		logger.info("requestWithinRedis: do not has weather of "+ cityName +" today , request uri:" + tmpUri);
		if (respString.getStatusCodeValue() == 200) {
			strBody = respString.getBody();
			//数据写入缓存
			ops.set(key, strBody, TIME_OUT,TimeUnit.SECONDS);
		} else {
			logger.info("data service: redis do not has data of " + key + "and request fail:" + uriPrefix + cityName);
		}
		logger.info("strBody:" + strBody);
		return strBody;
	}
}
