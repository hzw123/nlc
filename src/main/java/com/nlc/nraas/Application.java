package com.nlc.nraas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

@SpringBootApplication
public class Application {
	
	@Bean
	public MethodValidationPostProcessor methodValidationPostProcessor() {  
	  return new MethodValidationPostProcessor();
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}