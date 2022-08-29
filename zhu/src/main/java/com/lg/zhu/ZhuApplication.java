package com.lg.zhu;

import com.lg.zhu.push.Pusher;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableScheduling
public class ZhuApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZhuApplication.class, args);
	}

	// 定时
	@Scheduled(cron = "0 30 7 * * ?")
	public void goodMorning(){
		Pusher.push();
	}
}
