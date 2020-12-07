/**
 * 
 */
package com.waylau.spring.cloud.weather.config;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.ScheduleBuilder;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.waylau.spring.cloud.weather.job.CitySyncJob;

/**
 * Quartz Configuration
 * @author 张恒
 *
 */
@Configuration
public class QuartzConfiguration {
	private static final int TIME = 120;//城市数据映射工作频率
	
	//JobDetail  定义一个job
	@Bean
	public JobDetail CitySyncJobDetail() {
		return JobBuilder.newJob(CitySyncJob.class).withIdentity("weatherDataSyncJob").storeDurably().build(); 
	}
	
	//Trigger 定义脚本在什么状态下被触发
	@Bean
	public Trigger weatherDataSyncTrigger() {
		ScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(TIME).repeatForever();
		return TriggerBuilder.newTrigger().forJob(CitySyncJobDetail()).withIdentity("citySyncTrigger").withSchedule(scheduleBuilder).build();
	}

}
