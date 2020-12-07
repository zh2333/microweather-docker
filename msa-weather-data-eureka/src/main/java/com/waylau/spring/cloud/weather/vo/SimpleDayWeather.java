package com.waylau.spring.cloud.weather.vo;

import java.util.List;

/**
 * 简化部分字段的每日天气预报
 * @author 张恒
 *
 */
public class SimpleDayWeather {
	private static final long serialVersionUID = 1L;
	private String day;
	private String wea;
	private String wea_img;
	private String air;
	private String air_level;
	private String tem1;
	private String tem2;
	private String tem;
	private List<String> win;
	private String win_speed;
	
	
	public SimpleDayWeather() {
		super();
	}
	public SimpleDayWeather(String day, String wea, String wea_img, String air, String air_level,
			String tem1, String tem2, String tem, List<String> win, String win_speed) {
		super();
		this.day = day;
		this.wea = wea;
		this.wea_img = wea_img;
		this.air = air;
		this.air_level = air_level;
		this.tem1 = tem1;
		this.tem2 = tem2;
		this.tem = tem;
		this.win = win;
		this.win_speed = win_speed;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public String getWea() {
		return wea;
	}
	public void setWea(String wea) {
		this.wea = wea;
	}
	public String getWea_img() {
		return wea_img;
	}
	public void setWea_img(String wea_img) {
		this.wea_img = wea_img;
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
	public String getTem() {
		return tem;
	}
	public void setTem(String tem) {
		this.tem = tem;
	}
	public List<String> getWin() {
		return win;
	}
	public void setWin(List<String> win) {
		this.win = win;
	}
	public String getWin_speed() {
		return win_speed;
	}
	public void setWin_speed(String win_speed) {
		this.win_speed = win_speed;
	}
	@Override
	public String toString() {
		return day + ": " + wea  + tem1 + "/" + tem2 + "°C." + win.get(0)
				+ win_speed + "\n";
	}
}
