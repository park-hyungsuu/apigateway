package com.hyungsuu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@ComponentScan(basePackages = {"com.hyungsuu"})
@SpringBootApplication
@EnableDiscoveryClient 
public class ApigatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApigatewayApplication.class, args);
	}
	
	@Bean
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
	
		messageSource.setBasename("classpath:/message");
		messageSource.setDefaultEncoding("UTF-8");
		messageSource.setCacheSeconds(30);
		return messageSource;
	}

}
