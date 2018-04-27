package com.spring.test.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.spring.csvfactory.CsvFactory;
import com.spring.csvfactory.CsvFactoryImpl;

@Configuration
public class AppConfigForTest {
	
	@Bean
	public CsvFactory getCsvFactory() {
		return new CsvFactoryImpl();
	}
}
