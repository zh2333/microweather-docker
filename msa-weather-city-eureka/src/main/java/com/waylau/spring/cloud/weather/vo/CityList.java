/**
 * 
 */
package com.waylau.spring.cloud.weather.vo;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * 用于映射整张xml表
 * @author 张恒
 *
 */
@XmlRootElement(name = "c")
@XmlAccessorType(XmlAccessType.FIELD)

public class CityList {
	
	@XmlElement(name = "d")
	private List<City> cityList;

	public List<City> getCityList() {
		return cityList;
	}

	public void setCityList(List<City> cityList) {
		this.cityList = cityList;
	}
	
	
}
