package com.example.imageprocessor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@SpringBootApplication
public class Application {

	@Value("${aws-accesskey}")
	private String accessKey;
	
	@Value("${aws-secretkey}")
	private String secretKey;
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	
	@Bean
	public AmazonS3 s3Client() {
	
		AWSCredentials credentials = new BasicAWSCredentials(accessKey,secretKey);
		
		AmazonS3 s3client = AmazonS3ClientBuilder
				  .standard()
				  .withCredentials(new AWSStaticCredentialsProvider(credentials))
				  .withRegion(Regions.US_WEST_1)
				  .build();
		return s3client;
	}
	
	
}
