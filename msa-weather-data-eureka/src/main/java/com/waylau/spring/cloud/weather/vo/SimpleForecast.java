package com.waylau.spring.cloud.weather.vo;

import java.io.Serializable;
import java.util.List;

/**
 * 简化部分字段的预报天气实体类
 * @author 张恒
 *
 */
public class SimpleForecast implements Serializable {
	private static final long serialVersionUID = 2L;

	private String city;//城市名称
	private List<SimpleDayWeather> data;//每日天气预报
	
	
	public SimpleForecast() {
		super();
	}
	public SimpleForecast(String city, List<SimpleDayWeather> data) {
		super();
		this.city = city;
		this.data = data;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public List<SimpleDayWeather> getData() {
		return data;
	}
	public void setData(List<SimpleDayWeather> data) {
		this.data = data;
	}
	@Override
	public String toString() {
		return "SimpleForecast [city=" + city + ", data=" + data.toString() + "]";
	}
	
}
