package com.ks.dbHost;

import com.ks.dbHost.service.ProcessPerson;
import com.ks.dbHost.service.RedisPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DbhostApplication {

	@Autowired
	private ProcessPerson processPerson;

	public static void main(String[] args) {
		SpringApplication.run(DbhostApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner (ApplicationContext context){
		return args ->{
		};
	}

}
