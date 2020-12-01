/**
 * 
 */
package com.waylau.spring.cloud.weather.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhtty
 *
 */
@RestController
public class HelloController {
	@RequestMapping("/hello")
	public String hello() {
		return "hello world";
	}
}
