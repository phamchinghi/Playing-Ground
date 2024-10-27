package com.pcn.playing_ground.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

//import com.pcn.playing_ground.security.jwt.JwtAuthenticationFilter;
//import com.pcn.playing_ground.security.jwt.JwtTokenProvider;
import com.pcn.playing_ground.service.impl.UserDetailsServiceImpl;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {
	
//	@Autowired
//    private JwtAuthenticationFilter jwtAuthenticationFilter;
//	@Autowired
//    private JwtTokenProvider jwtTokenProvider;
	
	@Autowired
	private UserDetailsServiceImpl userDetailServiceImpl;

	@Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
            .authorizeHttpRequests((requests) -> requests
                .requestMatchers("/home", "/register", "/register/save", AppConstants.GET, AppConstants.CSS, AppConstants.JS, AppConstants.IMG, AppConstants.SCSS,AppConstants.VENDOR).permitAll()
//                .requestMatchers("/api/auth/**").permitAll()
                .anyRequest().authenticated()
            )
            .formLogin((form) -> form
        		.loginPage("/login")
                .loginProcessingUrl("/perform_login")
                .defaultSuccessUrl("/home", true)
                .failureUrl("/register?error=true")
                .permitAll()
            )
            .logout(logout -> logout
            		.logoutSuccessUrl("/login?logout")
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID")
                    .permitAll()
            )
            .userDetailsService(userDetailServiceImpl);
        
        return http.build();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration, HttpSecurity http) throws Exception {
    	AuthenticationManagerBuilder authenticationManagerBuilder = 
                http.getSharedObject(AuthenticationManagerBuilder.class);
            authenticationManagerBuilder.userDetailsService(userDetailServiceImpl)
                .passwordEncoder(passwordEncoder());
            return authenticationManagerBuilder.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}