package com.waylau.spring.cloud.weather.vo;

import java.io.Serializable;
import java.util.List;

/**
 * 原始预报信息
 */
public class Forecast implements Serializable {

	private static final long serialVersionUID = 1L;
	private String cityid;
	private String update_time;
	private String city;
	private String cityEn;
	private String country;
	private String countryEn;
	private List<DayWeather> data;
	public Forecast(String cityid, String update_time, String city, String cityEn, String country,String countryEn,
			List<DayWeather> data) {
		super();
		this.cityid = cityid;
		this.update_time = update_time;
		this.city = city;
		this.cityEn = cityEn;
		this.country = country;
		this.data = data;
	}
	
	public Forecast() {
		super();
	}

	public String getCityid() {
		return cityid;
	}
	public void setCityid(String cityid) {
		this.cityid = cityid;
	}
	public String getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCityEn() {
		return cityEn;
	}
	public void setCityEn(String cityEn) {
		this.cityEn = cityEn;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public List<DayWeather> getData() {
		return data;
	}
	public void setData(List<DayWeather> data) {
		this.data = data;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
	public String getCountryEn() {
		return countryEn;
	}

	public void setCountryEn(String countryEn) {
		this.countryEn = countryEn;
	}

	@Override
	public String toString() {
		return "Forecast [cityid=" + cityid + ", update_time=" + update_time + ", city=" + city + ", cityEn=" + cityEn
				+ ", country=" + country + ", data=" + data.toString() + "]";
	}
	
	/**
	 * 获取简化版的每日天气预报, 简化部分字段
	 * @return
	 */
	public SimpleForecast getSimpleForecast() {
		List<SimpleDayWeather> simpleData = null;
		for (DayWeather dayWeather: data) {
			simpleData.add(dayWeather.getSimpleDayWeather());
		}
		return new SimpleForecast(city, simpleData);
	}
	
}
