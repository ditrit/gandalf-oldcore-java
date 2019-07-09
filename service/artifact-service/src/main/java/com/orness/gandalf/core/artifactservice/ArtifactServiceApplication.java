package com.orness.gandalf.core.artifactservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@Configuration
@EnableAutoConfiguration
@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication
public class ArtifactServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ArtifactServiceApplication.class, args);
	}

	@Bean
	public MultipartResolver multipartResolver() {
		CommonsMultipartResolver multipartResolver =  new CommonsMultipartResolver();
		multipartResolver.setMaxUploadSize(1024000);
		return multipartResolver;
	}
}
