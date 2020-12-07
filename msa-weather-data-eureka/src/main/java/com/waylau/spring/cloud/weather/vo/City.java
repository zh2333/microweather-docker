package com.waylau.spring.cloud.weather.vo;

/**
 * 城市类
 * @author 张恒
 *
 */
public class City {
    String cityName;
    Integer cityCode;

    
    public City() {
		super();
	}

	public City(Integer cityCode, String cityName) {
        this.cityName = cityName;
        this.cityCode = cityCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Integer getCityCode() {
        return cityCode;
    }

    public void setCityCode(Integer cityCode) {
        this.cityCode = cityCode;
    }

    @Override
    public String toString() {
        return "[" +
                "cityName=" + cityName +
                ", cityCode='" + cityCode + '\'' +
                ']';
    }
}
