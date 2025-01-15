package com.pcn.playing_ground.config;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer{
//	@Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOrigins(Collections.singletonList("*"));
//        configuration.setAllowedMethods(Arrays.asList(AppConstants.API_GET, AppConstants.API_GET, AppConstants.API_PUT, AppConstants.API_DEL, AppConstants.API_OPT));
//        configuration.setAllowedHeaders(Arrays.asList(AppConstants.AUTHOR, AppConstants.CONTENT_TYPE, AppConstants.X_AUTH_TOKEN));
//        configuration.setExposedHeaders(Collections.singletonList(AppConstants.X_AUTH_TOKEN));
//        configuration.setAllowCredentials(true);
//        configuration.setMaxAge((long) 3600);
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }
	
	 @Override
	    public void addCorsMappings(CorsRegistry registry) {
	        registry.addMapping("/**")
	                .allowedOrigins("http://localhost:8081")  // Adjust as needed
	                .allowedMethods("GET", "POST", "PUT", "DELETE")
	                .allowedHeaders("Authorization", "Content-Type")
	                .exposedHeaders("Authorization")
	                .allowCredentials(true);
	    }
}
