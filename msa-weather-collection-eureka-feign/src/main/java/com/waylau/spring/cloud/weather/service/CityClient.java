package com.waylau.spring.cloud.weather.service;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import com.waylau.spring.cloud.weather.vo.City;

@FeignClient("zuul-service")
public interface CityClient {
	
	@GetMapping("/city/cities")
	List<City> listCity() throws Exception;

}
