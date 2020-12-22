package com.progressoft.task;

import com.progressoft.task.config.StorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class ProgressoftTaskApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProgressoftTaskApplication.class, args);
	}

}
