//package com.pcn.playing_ground.config;
//
//import java.util.Arrays;
//import java.util.Collections;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.CorsConfigurationSource;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//public class CorsConfig{
//	@Bean
//    public static CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOrigins(Collections.singletonList("http://localhost:8081"));
//        configuration.setAllowedMethods(Arrays.asList(AppConstants.GET, AppConstants.GET, AppConstants.PUT, AppConstants.DELETE, AppConstants.OPTIONS));
//        configuration.setAllowedHeaders(Arrays.asList(AppConstants.AUTHOR, AppConstants.CONTENT_TYPE, AppConstants.CACHE));
//        configuration.setAllowCredentials(true);
//        configuration.setMaxAge((long) 3600);
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }
//
////	 @Override
////	    public void addCorsMappings(CorsRegistry registry) {
////	        registry.addMapping("/**")
////	                .allowedOrigins("http://localhost:8081")  // Adjust as needed
////	                .allowedMethods("GET", "POST", "PUT", "DELETE")
////	                .allowedHeaders("Authorization", "Content-Type")
////	                .exposedHeaders("Authorization")
////	                .allowCredentials(true);
////	    }
//}
