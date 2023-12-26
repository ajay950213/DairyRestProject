package com.rest_api.dairy.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class DairySecurity {
	
//	@Bean
//	public UserDetailsManager configureDataSource(DataSource dataSource) {
//		
//		UserDetailsManager userDetailsManger = new JdbcUserDetailsManager(dataSource);
//		
//		return userDetailsManger;
//	}
	
	
	@Bean
	public InMemoryUserDetailsManager configureInMemoryUser() {
		
		UserDetails user1 = User.builder().username("Ajay").password("{noop}ajay12345").roles("ADMIN").build();
		UserDetails user2 = User.builder().username("Elon").password("{noop}elon12345").roles("MANAGER","EMPLOYEE").build();
		UserDetails user3 = User.builder().username("Mark").password("{noop}mark12345").roles("EMPLOYEE").build();
		
		InMemoryUserDetailsManager inMemoryUserDetails = new InMemoryUserDetailsManager(user1,user2,user3);
		
		return inMemoryUserDetails;
		
	}
	
	@Bean
	public SecurityFilterChain authorizeHttpRequests(HttpSecurity http) throws Exception {
		
		http
        .authorizeHttpRequests(
        		(authorize)->{
        			authorize
        			.requestMatchers(HttpMethod.DELETE, "/entries/**").hasRole("ADMIN")
        			.requestMatchers(HttpMethod.PUT, "/entries/**").hasRole("ADMIN")
        			.requestMatchers(HttpMethod.PATCH, "/entries/**").hasRole("MANAGER")
        			.anyRequest().authenticated();
        		
        		}
        		)
        .httpBasic()
        .and()
        .csrf()
        .disable();
		
        
		return http.build();
    
	}


	
}
