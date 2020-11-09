package com.honestpeak.datasource;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MyJob {
	@Scheduled(cron="0/1 * * * * ?")//每5秒执行一次
	public void MyJob(){
		Date date = new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time =sdf.format(date);
		System.out.println("当前的测试时间是："+time);
	}
}
