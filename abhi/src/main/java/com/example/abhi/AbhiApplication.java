package com.example.abhi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication()
//@ComponentScan(basePackages = {"com.example.abhi.dbconnect"})
public class AbhiApplication {

	public static void main(String[] args) {
		SpringApplication.run(AbhiApplication.class, args);
	}

}
