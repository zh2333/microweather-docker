package com.waylau.spring.cloud.weather.vo;

import java.util.List;

/**
 * 城市列表类
 * @author 张恒
 *
 */
public class CityList {
    public List<City> cityList;

    public CityList(List<City> cityList) {
        this.cityList = cityList;
    }

    public List<City> getCityList() {
        return cityList;
    }

    public void setCityList(List<City> cityList) {
        this.cityList = cityList;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (City city:cityList) {
            str.append(city.toString());
        }
        return str.toString();
    }
}
