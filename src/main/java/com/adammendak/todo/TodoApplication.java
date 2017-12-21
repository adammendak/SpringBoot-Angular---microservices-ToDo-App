package com.adammendak.todo;

import com.adammendak.todo.Bootstrap.Bootstrap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class TodoApplication {

	private Bootstrap bootstrap;

	public TodoApplication(Bootstrap bootstrap) {
		this.bootstrap = bootstrap;
	}

	public static void main(String[] args) {
		SpringApplication.run(TodoApplication.class, args);
	}

//	public void initAppData() {
//		try {
//			bootstrap.run();
//		} catch (Exception e) {
//			log.info("error {}",e.printStackTrace(););
//		}
//	}



}
