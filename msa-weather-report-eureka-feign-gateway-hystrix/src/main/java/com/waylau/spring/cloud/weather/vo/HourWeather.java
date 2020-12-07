package com.waylau.spring.cloud.weather.vo;

import java.io.Serializable;

/**
 * 小时天气预报实体类
 * @author 张恒
 *
 */
public class HourWeather implements Serializable {
	private static final long serialVersionUID = 3L;
	private String day;
	private String wea;
	private String tem;
	private String win;
	private String win_speed;

	
	public HourWeather() {
		super();
	}
	public HourWeather(String day, String wea, String tem, String win, String win_speed) {
		super();
		this.day = day;
		this.wea = wea;
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
	public String getTem() {
		return tem;
	}
	public void setTem(String tem) {
		this.tem = tem;
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
	@Override
	public String toString() {
		return day + ":" + wea + ", " + tem + "°C." + win + win_speed
				+ "\n";
	}
	
	
}
