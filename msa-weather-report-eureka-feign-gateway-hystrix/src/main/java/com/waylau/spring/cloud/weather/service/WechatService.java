package com.waylau.spring.cloud.weather.service;

import java.util.List;

import com.waylau.spring.cloud.weather.vo.HourWeather;
import com.waylau.spring.cloud.weather.vo.SimpleForecast;
import com.waylau.spring.cloud.weather.vo.SimpleWeather;
import com.waylau.spring.cloud.weather.vo.Weather;

public interface WechatService {
    /**
     * 校验请求是否是来自于微型服务器
     * @param signature
     * @param token
     * @param timestamp
     * @param nonce
     * @return
     */
    public boolean check(String signature, String token, String timestamp, String nonce );
    
    /**
     * 根据城市名获取今天城市的天气概述
     * @param weather
     * @return
     */
    public String getWeatherDesc(SimpleWeather weather);
    
    /**
     * 响应用户关注公众号事件
     * @return
     */
    public String responseFocus(String event);
    
    /**
     * 获取小时天气预报描述
     * @return
     */
    public String getHourForecastDesc(List<HourWeather> weathers);
    
    /**
     * 获取每天天气预报
     * @param weathers
     * @return
     */
    public String getDayForecastDesc(SimpleForecast dayForecasts);
    
    /**
     * 给每个查询天气的用户注册天气推送服务
     * @param userId
     * @param cityName
     * @return
     */
    //public void pushRegister(String cityName, String userId);
}
