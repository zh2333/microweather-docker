package com.waylau.spring.cloud.weather.vo;

import java.io.Serializable;
import java.util.List;

/**
 * 简化的实时每天天气
 * @author 张恒
 *
 */
public class SimpleWeather implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String city;//城市名称
	private String wea;//天气
	private String tem;//当前温度
	private String tem1;//今天最高温度
	private String tem2;//今天最低温度
	private String win;//风向
	private String win_speed;//风力
	private String air;//空气指数
	private String air_level;//建议
	
	public SimpleWeather(String city, String wea, String tem, String tem1, String tem2, String win, String win_speed,
			String air, String air_level) {
		super();
		this.city = city;
		this.wea = wea;
		this.tem = tem;
		this.tem1 = tem1;
		this.tem2 = tem2;
		this.win = win;
		this.win_speed = win_speed;
		this.air = air;
		this.air_level = air_level;
	}
	public SimpleWeather() {
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getWea() {
		return wea;
	}
	public void setWea(String wea) {
		this.wea = wea;
	}
	public String getTem() {
		return tem;
	}
	public void setTem(String tem) {
		this.tem = tem;
	}
	public String getTem1() {
		return tem1;
	}
	public void setTem1(String tem1) {
		this.tem1 = tem1;
	}
	public String getTem2() {
		return tem2;
	}
	public void setTem2(String tem2) {
		this.tem2 = tem2;
	}
	public String getWin() {
		return win;
	}
	public void setWin(String win) {
		this.win = win;
	}
	public String getWin_speed() {
		return win_speed;
	}
	public void setWin_speed(String win_speed) {
		this.win_speed = win_speed;
	}
	public String getAir() {
		return air;
	}
	public void setAir(String air) {
		this.air = air;
	}
	public String getAir_level() {
		return air_level;
	}
	public void setAir_level(String air_level) {
		this.air_level = air_level;
	}
	@Override
	public String toString() {
		return city + "," + wea + ", " + tem2 + "/" + tem1 + "°C, 现在" + tem + "°C.\n"+
				win + win_speed + ".pm2.5指数:" + air + ",空气" + air_level;
	}
}
