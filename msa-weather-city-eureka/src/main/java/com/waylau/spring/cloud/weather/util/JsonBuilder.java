package com.waylau.spring.cloud.weather.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import com.alibaba.fastjson.JSON;

/**
 * parse json string
 * @author 张恒
 *
 */
public class JsonBuilder {
	/**
	 * 从给定的类型解析json为list
	 * @param clazz 
	 * @param file
	 * @return
	 */
	public static Object parseJsonToList(Class clazz, String file) {
		Resource resource = new ClassPathResource(file);
        InputStream inputStream;
        List<Class> list = null;
		try {
			inputStream = resource.getInputStream();
			int length = inputStream.available();
			byte bytes[] = new byte[length];
	        inputStream.read(bytes);
	        inputStream.close();
	        String str =new String(bytes, StandardCharsets.UTF_8);
	        list = JSON.parseArray(str, clazz);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		return list;
	}
}
