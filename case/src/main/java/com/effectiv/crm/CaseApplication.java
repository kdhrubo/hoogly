package com.effectiv.crm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@SpringBootApplication
@EnableSpringDataWebSupport
public class CaseApplication {

	public static void main(String[] args) {
		SpringApplication.run(CaseApplication.class, args);
	}
}
