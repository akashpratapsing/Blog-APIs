package com.blogster.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.blogster.blog.security.CustomUserDetailService;
import com.blogster.blog.security.JwtAuthenticationEntryPoint;
import com.blogster.blog.security.JwtAuthenticationFilter;


@Configuration
@EnableWebSecurity
@EnableWebMvc
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
	
	public static final String[] PUBLIC_URLS = {
			"/api/v1/auth/**",
			"/v3/api-docs",
			"/v2/api-docs",
			"/swagger-resources/**",
			"/swagger-ui/**",
			"/webjars/**"
			
	};
	
	@Autowired
	private CustomUserDetailService customUserDetailService;
	
	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	
	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

		httpSecurity
		.csrf((csrf) -> csrf
		.disable())
		.authorizeHttpRequests(authrize -> authrize
		.requestMatchers(PUBLIC_URLS).permitAll()
		.requestMatchers(HttpMethod.GET).permitAll()
		.anyRequest()
		.authenticated())
		.exceptionHandling(e -> e
		.authenticationEntryPoint(this.jwtAuthenticationEntryPoint))
		.sessionManagement(session -> session
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		.authenticationProvider(authenticationProvider())
		.addFilterBefore(this.jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);;
		
		
		
		
		
		
		
		
		
//		.httpBasic(Customizer.withDefaults()).userDetailsService(customUserDetailService);

		return httpSecurity.build();

	}
	
//	 @Bean
//	    public AuthenticationManager authenticationManager() throws Exception {
//	        return authentication -> {
//	            UserDetails user = customUserDetailService.loadUserByUsername(authentication.getName());
//	            if (user.getUsername().equals(authentication.getName()) && user.getPassword().equals(authentication.getCredentials())) {
//	                return new UsernamePasswordAuthenticationToken(
//	                        user.getUsername(),
//	                        user.getPassword(),
//	                        user.getAuthorities()
//	                );
//	            } else {
//	                throw new BadCredentialsException("Authentication failed");
//	            }
//	        };
//	    }
	
	
	  @Bean
	    public AuthenticationProvider authenticationProvider() {
	        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
	        authenticationProvider.setUserDetailsService(customUserDetailService);
	        authenticationProvider.setPasswordEncoder(passwordEncoder());
	        return authenticationProvider;
	    }
	  
	  
	@Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
	
	@Bean
	public PasswordEncoder passwordEncoder() {	
		return new BCryptPasswordEncoder();
	}

}
