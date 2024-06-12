package com.blogster.blog.services;

import java.util.List;

import com.blogster.blog.payloads.UserDto;

public interface UserService {
	
	UserDto registerUser(UserDto userDto);

	UserDto createUser(UserDto userDto);
	
	UserDto updateUser(UserDto userDto, Integer userId);
	
	UserDto getUserById(Integer userId);
	
	List<UserDto> getallUsers();
	
	void deleteUser(Integer userId);
}
