package com.waylau.spring.cloud.weather.vo;

import java.io.Serializable;

/**
 * 运动指数等
 * @author 张恒
 *
 */
public class Index implements Serializable {
	private static final long serialVersionUID = 1L;
	private String title;
	private String level;
	private String desc;
	
	
	public Index() {
		super();
	}
	public Index(String title, String level, String desc) {
		super();
		this.title = title;
		this.level = level;
		this.desc = desc;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
}
