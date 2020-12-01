package com.waylau.spring.cloud.weather.service;

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
    public String getWeatherDesc(Weather weather);
    
    /**
     * 响应用户关注公众号事件
     * @return
     */
    public String responseFocus(String event);
}
