/**
 * 
 */
package com.waylau.spring.cloud.weather.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.waylau.spring.cloud.weather.util.XmlBuilder;
import com.waylau.spring.cloud.weather.vo.City;
import com.waylau.spring.cloud.weather.vo.CityList;

/**
 * @author 张恒
 *
 */
@Service
public class CityDataServiceImpl implements CityDataService {
	private final static Logger logger = LoggerFactory.getLogger(CityDataServiceImpl.class);
	@Override
	public List<City> listCity() throws Exception {
		logger.info("parse xml..........");
		//读取xml文件
		Resource resource = new ClassPathResource("citylist.xml");
		BufferedReader bf = new BufferedReader(new InputStreamReader(resource.getInputStream(), "utf-8"));
		StringBuffer buffer = new StringBuffer();
		String line = "";
		while((line = bf.readLine()) != null) {
			buffer.append(line);
		}
		if(bf != null) {
			bf.close();
		}
		//将xml转为java对象
		CityList cityList = (CityList)XmlBuilder.xmlStrToObject(CityList.class, buffer.toString());
		return cityList.getCityList();
	}

}
