package com.waylau.spring.cloud.weather.service;

import com.waylau.spring.cloud.weather.vo.Weather;

/**
 * 天气预报服务接口
 * @author 张恒
 *
 */
public interface WeatherReportService {
	
	/*根据城市id获取天气信息*/
	Weather getDataByCityId(String cityId);
	
	/*根据城市名称获取天气*/
	Weather getDataByCityName(String cityName);

}
