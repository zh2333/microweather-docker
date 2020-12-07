package com.waylau.spring.cloud.weather.vo;

import java.io.Serializable;

/**
 * 响应页面的请求
 */
public class WeatherResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	private SimpleWeather data;//当天天气
	private SimpleForecast forecast;//每日天气预报
	
	
	public WeatherResponse(SimpleWeather data, SimpleForecast forecast) {
		super();
		this.data = data;
		this.forecast = forecast;
	}
	public SimpleWeather getData() {
		return data;
	}
	public void setData(SimpleWeather data) {
		this.data = data;
	}
	public SimpleForecast getForecast() {
		return forecast;
	}
	public void setForecast(SimpleForecast forecast) {
		this.forecast = forecast;
	}
}
