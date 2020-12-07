package com.waylau.spring.cloud.weather.service.impl;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.waylau.spring.cloud.weather.service.DataClient;
import com.waylau.spring.cloud.weather.service.WechatService;
import com.waylau.spring.cloud.weather.vo.Forecast;
import com.waylau.spring.cloud.weather.vo.HourWeather;
import com.waylau.spring.cloud.weather.vo.SimpleDayWeather;
import com.waylau.spring.cloud.weather.vo.SimpleForecast;
import com.waylau.spring.cloud.weather.vo.SimpleWeather;
import com.waylau.spring.cloud.weather.vo.Weather;

@Service
public class WechatServiceImpl implements WechatService {
	private final static Logger logger = LoggerFactory.getLogger(WechatServiceImpl.class);
	@Autowired
	private DataClient dataClient;
	
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
	public String getWeatherDesc(SimpleWeather weather) {
		String desc = "";
		if (weather != null) {
			desc = weather.toString();
		} else {
			desc = "请检查输入的城市名称(不携带区)";
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
			reply = "欢迎关注zhtty的天气管家!\n" + "1.输入城市名称获取城市天气\n"
					+ "2.输入 城市名称 +天气预报 获取未来一周的天气\n"
					+ "3.输入 城市名称+小时预报 获取今天未来几小时的天气\n";
		}
		return reply;
	}

	@Override
	public String getDayForecastDesc(SimpleForecast dayForecasts) {
		StringBuilder desc = new StringBuilder();
		logger.info(dayForecasts.toString());
		if (dayForecasts != null) {
			String cityName = dayForecasts.getCity();
			List<SimpleDayWeather> weathers = dayForecasts.getData();
			desc.append(cityName + "未来一周的天气:\n");
			for (SimpleDayWeather dayWeather : weathers) {
				desc.append(dayWeather.toString());
			}
		} else {
			desc = desc.append("请检查输入的城市名称(不携带区)");
		}
		return desc.toString();
	}

	@Override
	public String getHourForecastDesc(List<HourWeather> hourForecasts) {
		StringBuilder desc = new StringBuilder();
		if (hourForecasts != null) {
			desc.append("接下来几小时的天气:\n");
			for (HourWeather hourWeather : hourForecasts) {
				desc.append(hourWeather);
			}
		} else {
			desc = desc.append("请检查输入的城市名称(不携带区)");
		}
		return desc.toString();
	}

//	public void pushRegister(String user, String cityName) {
//		dataClient.pushRegister(cityName, user);
//	}
    
}

