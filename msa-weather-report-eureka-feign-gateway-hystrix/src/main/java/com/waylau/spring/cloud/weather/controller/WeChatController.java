package com.waylau.spring.cloud.weather.controller;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.feign.FeignClientProperties.FeignClientConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.waylau.spring.cloud.weather.service.WeatherReportService;
import com.waylau.spring.cloud.weather.service.WechatService;
import com.waylau.spring.cloud.weather.vo.Forecast;
import com.waylau.spring.cloud.weather.vo.Weather;
import org.springframework.web.bind.annotation.RestController;

import util.MessageUtil;
import util.XMLUtil;
@RestController
public class WeChatController {
	public static final String TOKEN = "microweathertoken";

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
				//用户发送消息的内容
				String cityName = map.get("Content");				
				Weather weather = reportService.getDataByCityName(cityName);
				reply = wechatService.getWeatherDesc(weather);
				System.out.println("用户发送信息事件.."+reply);
			}
    	} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	return MessageUtil.setMessage(fromUser, toUser, 454245, "text", reply);
    }
    
    

}
