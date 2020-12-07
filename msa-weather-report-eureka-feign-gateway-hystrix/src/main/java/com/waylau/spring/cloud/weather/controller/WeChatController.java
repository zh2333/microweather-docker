package com.waylau.spring.cloud.weather.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.feign.FeignClientProperties.FeignClientConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.waylau.spring.cloud.weather.service.WeatherReportService;
import com.waylau.spring.cloud.weather.service.WechatService;
import com.waylau.spring.cloud.weather.vo.Forecast;
import com.waylau.spring.cloud.weather.vo.HourWeather;
import com.waylau.spring.cloud.weather.vo.SimpleForecast;
import com.waylau.spring.cloud.weather.vo.SimpleWeather;
import com.waylau.spring.cloud.weather.vo.Weather;
import org.springframework.web.bind.annotation.RestController;

import util.MessageUtil;
import util.StringUtil;
import util.XMLUtil;
@RestController
public class WeChatController {
	public static final String TOKEN = "microweathertoken";
	private static final String HOURFROECAST = "小时预报";
	private static final String DAYFORECAST = "天气预报";

    @Autowired
    private WechatService wechatService;
        
    @Autowired
    private WeatherReportService reportService;

    @GetMapping(value = "/wechat")
    public String check(HttpServletRequest request) {
        String sign = request.getParameter("signature");
        String time = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");

        boolean isAuthed = wechatService.check(sign, TOKEN, time, nonce);

        if (isAuthed) {
            System.out.println("接入成功");
            return echostr;
        } else {
            System.out.println("接入失败");
            return "";
        }
    }
    
    @PostMapping(value = "/wechat", produces="application/xml;charset=utf-8")
    public String responseWeChat(HttpServletRequest request) {
    	String msgType = "text";
    	String fromUser = "";
    	String toUser = "";
    	String reply = "";
    	Long currTime = new Date().getTime(); // 当前时间戳
    	//1.解析XML文档, 判断事件类型
    	try {
			Map<String, String> map = XMLUtil.getMap(request.getInputStream());
			msgType = map.get("MsgType");
			fromUser = map.get("FromUserName");
		    toUser = map.get("ToUserName");
						
			if (msgType.equals("event")) {
				String event = map.get("Event");
				reply = wechatService.responseFocus(event);
				System.out.println("关注事件.."+reply);
			} else if (msgType.equals("text")) {//如果是用户发送的消息, 默认进行查询天气的行为
				String content = map.get("Content");
				//用户发送消息的内容
				if (StringUtil.matchSubStr(content, HOURFROECAST)) {//查询的是小时天气预报
					System.out.println("小时天气.."+reply);
					String cityName = content.replaceAll(HOURFROECAST, "");
					List<HourWeather> hourWeathers = reportService.getHourWeather(cityName);
					reply = wechatService.getHourForecastDesc(hourWeathers);
				} else if (StringUtil.matchSubStr(content, DAYFORECAST)) {//查询一个星期的天气预报
					System.out.println("天气预报.."+reply);
					String cityName = content.replaceAll(DAYFORECAST, "");
					SimpleForecast dayForecasts = reportService.getForecast(cityName);
					reply = wechatService.getDayForecastDesc(dayForecasts);
				} else {//输入的城市名称查询
					SimpleWeather weather = reportService.getDataByCityName(content);
					reply = wechatService.getWeatherDesc(weather);
					System.out.println("城市天气.."+reply);
				}
			}
    	} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	return MessageUtil.setMessage(fromUser, toUser, currTime, "text", reply);
    }
    
    

}
