/**
 * 
 */
package com.waylau.spring.cloud.weather.job;

import java.util.ArrayList;
import java.util.List;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.waylau.spring.cloud.weather.service.CityClient;
import com.waylau.spring.cloud.weather.service.WeatherDataCollectionService;
import com.waylau.spring.cloud.weather.service.WeatherDataCollectionServiceImpl;
import com.waylau.spring.cloud.weather.vo.City;

/**
 * 定时任务类
 * @author 张恒
 *
 */
public class WeatherDataSyncJob extends QuartzJobBean {
	
	private final static Logger logger = LoggerFactory.getLogger(WeatherDataCollectionServiceImpl.class);
	

	@Autowired
	private CityClient cityClient;
	
	@Autowired
	private WeatherDataCollectionService weatherDataCollectionService;

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		logger.info("weather Data Sync Job.Start!");
		//获取城市ID列表
		List<City> cityList = null;
		try {
			cityList = cityClient.listCity();
		} catch (Exception e) {
			logger.error("get cities error!", e);
		}
//		//遍历城市ID列表获取天气
//		for(City city: cityList) {
//			Integer cityId = city.getCityCode();
//			String cityName = city.getCityName();
//			logger.info("sync weather data of: " + cityName);
//			
//			
//		}
		Integer cityId = 101210101;//TODO 暂时只同步一个城市用于测试
		String cityName = "杭州";
		logger.info("sync weather data of: " + cityName);
		weatherDataCollectionService.syncDataByCityId(cityId);
		logger.info("weather Data Sync Job.End!");
	}

}
