package com.waylau.spring.cloud.weather.service.impl;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import org.springframework.stereotype.Service;

import com.waylau.spring.cloud.weather.service.WechatService;
import com.waylau.spring.cloud.weather.vo.Forecast;
import com.waylau.spring.cloud.weather.vo.Weather;

@Service
public class WechatServiceImpl implements WechatService {
    @Override
    public boolean check(String signature, String token, String timestamp, String nonce) {

        /**
         * 1）将token、timestamp、nonce三个参数进行字典序排序
         * 2）将三个参数字符串拼接成一个字符串进行sha1加密
         * 3）开发者获得加密后的字符串可与signature对比，标识该请求来源于微信
         */
        String[] params = new String[]{token, timestamp, nonce};
        Arrays.sort(params);//将参数按照字典序排序
        String mySigStr = params[0] + params[1] + params[2];
        String mySig = sha1(mySigStr);
        return mySig.equals(signature);
    }

    /**
     * 加密
     * @param mySigStr
     * @return
     * @throws NoSuchAlgorithmException
     */
    private String sha1(String mySigStr)  {

        MessageDigest sha1 = null;
        StringBuilder sb = new StringBuilder();

        try {
            sha1 = MessageDigest.getInstance("sha1");
            byte[] digest = sha1.digest(mySigStr.getBytes());
            char[] chars = {'0', '1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};
            for (byte b: digest) {
                sb.append(chars[(b >> 4) & 15]);
                sb.append(chars[b & 15]);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    /**
     * 获取指定城市今天的天气概述
     */
	@Override
	public String getWeatherDesc(Weather weather) {
		String desc = "";
		if (weather != null) {
			Forecast today = weather.getForecast().get(0);
			String city = weather.getCity();
			String maxwendu = today.getHigh();
			String lowwendu = today.getLow();
			String fengx = today.getFengxiang();
			String fengli = today.getFengli();
			String ganmao = weather.getGanmao();
			String type = today.getType();
			desc = city + "今天"+ type +"天.最高温度 " + maxwendu + "度, " + "最低温度" + lowwendu + "度." + fengx + fengli + ".\n" + ganmao; 
		} else {
			desc = "暂无当前城市天气数据";
		}
		return desc;
	}

	/**
	 * 响应用户关注公众号事件
	 */
	@Override
	public String responseFocus(String event) {
		String reply = "";
		if (event.equals("subscribe")) {//如果是关注事件
			reply = "欢迎关注zhtty的天气管家!更多功能敬请期待~";
		}
		return reply;
	}
    
}

