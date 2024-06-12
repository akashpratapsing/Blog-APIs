package com.blogster.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blogster.blog.exceptions.ApiException;
import com.blogster.blog.payloads.JwtAuthRequest;
import com.blogster.blog.payloads.JwtAuthResponse;
import com.blogster.blog.payloads.UserDto;
import com.blogster.blog.security.JwtTokenHelper;
import com.blogster.blog.services.UserService;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
	
	@Autowired
	private JwtTokenHelper jwtTokenHelper;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> createToken (
		@RequestBody JwtAuthRequest request
			){
		
		this.authenticate(request.getUsername(), request.getPassword());
		
		UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getUsername());
		String token = this.jwtTokenHelper.generateToken(userDetails);
		
		JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
		jwtAuthResponse.setToken(token);
		
		return new ResponseEntity<JwtAuthResponse>(jwtAuthResponse, HttpStatus.OK);
		
		
	}
	
	@PostMapping("/signup")
	public ResponseEntity<UserDto> registerUser(@RequestBody UserDto userDto){
		
		UserDto newUser = this.userService.registerUser(userDto);
		
		return new ResponseEntity<UserDto>(newUser, HttpStatus.CREATED);
	}

	private void authenticate(String username, String password) {
		
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
		
		try {
			this.authenticationManager.authenticate(token);
		} catch (BadCredentialsException e) {
			
			throw new ApiException("Invalid username password");
		}
		
	}
	


}
