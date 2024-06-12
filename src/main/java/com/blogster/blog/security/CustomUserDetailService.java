package com.blogster.blog.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.blogster.blog.entities.User;
import com.blogster.blog.exceptions.ResourceNotFoundException;
import com.blogster.blog.repositories.UserRepo;

@Service
public class CustomUserDetailService implements UserDetailsService{

	@Autowired
	private UserRepo userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		// Loading user by username
		User user = this.userRepo.findByEmail(username).orElseThrow(() -> new ResourceNotFoundException("User ", "email ", username));
		
		return user;
	}

}
