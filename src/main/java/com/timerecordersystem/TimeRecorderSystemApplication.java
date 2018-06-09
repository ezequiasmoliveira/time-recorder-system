package com.timerecordersystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

@EntityScan(basePackageClasses = { 
		TimeRecorderSystemApplication.class, 
		Jsr310JpaConverters.class 
		})
@SpringBootApplication
public class TimeRecorderSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(TimeRecorderSystemApplication.class, args);
	}
}
