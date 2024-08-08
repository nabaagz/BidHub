package com.BidHub.ForwardBidding;

 
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


//@SpringBootApplication
@ComponentScan(basePackages = {"com.BidHub.ForwardBidding"})
public class BidhubApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(BidhubApplication.class, args);
	}
}
