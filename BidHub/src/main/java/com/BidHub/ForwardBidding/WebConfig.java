package com.BidHub.ForwardBidding;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer{
	
	public void addCorsMappings(CorsRegistry reg)
    {
        reg.addMapping("/**")
                .allowedMethods("*")
                .allowedOrigins("*")
                .allowedHeaders("*")
                .allowCredentials(false)
                .maxAge(-1);
    }
	

}
