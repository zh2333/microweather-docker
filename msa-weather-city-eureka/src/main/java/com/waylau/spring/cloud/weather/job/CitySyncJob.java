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

import com.waylau.spring.cloud.constparamter.Const;
import com.waylau.spring.cloud.weather.service.CityDataService;
import com.waylau.spring.cloud.weather.service.CityDataServiceImpl;
import com.waylau.spring.cloud.weather.util.JsonBuilder;
import com.waylau.spring.cloud.weather.vo.City;

/**
 * 定时任务类
 * @author 张恒
 *
 */
public class CitySyncJob extends QuartzJobBean {
	
	private final static Logger logger = LoggerFactory.getLogger(CityDataServiceImpl.class);
	
	@Autowired
	private CityDataService cityDataService;

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		logger.info("city Data Sync Job Start......");
		//获取城市列表
		List<City> cityList = (List<City>) JsonBuilder.parseJsonToList(City.class, Const.JSONFILE);
		//遍历城市ID列表获取天气
		for(City city: cityList) {
			Integer cityCode = city.getCityCode();
			String cityName = city.getCityName();
			logger.info("sync city data **** " + cityCode + ":" + cityName + " ****");
			cityDataService.saveCityMap(cityList);
		}
		logger.info("city Data Sync Job End......");
	}

}
