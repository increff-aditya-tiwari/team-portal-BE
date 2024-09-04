package com.increff.teamer;


import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.client.RestTemplate;
import com.increff.teamer.util.WebSocketHandler;

@SpringBootApplication
public class TeamerApplication {

	public static void main(String[] args) {

		SpringApplication.run(TeamerApplication.class, args);
		System.out.println("server started");
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Bean
	public WebSocketHandler notificationWebSocketHandler(){
		return new WebSocketHandler();
	}

}
