package com.blogster.blog.services.imple;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.blogster.blog.config.AppConstans;
import com.blogster.blog.entities.Role;
import com.blogster.blog.entities.User;
import com.blogster.blog.exceptions.ResourceNotFoundException;
import com.blogster.blog.payloads.UserDto;
import com.blogster.blog.repositories.RoleRepo;
import com.blogster.blog.repositories.UserRepo;
import com.blogster.blog.services.UserService;

@Service
public class UserServiceImple implements UserService {

	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	private RoleRepo roleRepo;
	
	@Override
	public UserDto createUser(UserDto userDto) {
		
		User user = this.dtoTOUser(userDto);
		User savedUser = this.userRepo.save(user);
		return this.userTODto(savedUser);
		
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
		
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
		
		user.setName(userDto.getName());
		user.setPassword(userDto.getPassword());
		user.setAbout(userDto.getAbout());
		user.setEmail(userDto.getEmail());
		
		User updatedUser = this.userRepo.save(user);
		UserDto userDto1 = this.userTODto(updatedUser);
		return userDto1;
	}

	@Override
	public UserDto getUserById(Integer userId) {

		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
		
		return this.userTODto(user);
	}

	@Override
	public List<UserDto> getallUsers() {
		
		List<User> users = this.userRepo.findAll();
		
		List<UserDto> userDtos = users.stream().map(user -> this.userTODto(user)).collect(Collectors.toList());
		
		return userDtos;
	}

	@Override
	public void deleteUser(Integer userId) {
		
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
		
		this.userRepo.delete(user);

	}
	
	private User dtoTOUser(UserDto userDto) {
		
		
		User user = this.modelMapper.map(userDto, User.class);
		
//		user.setId(userDto.getId());
//		user.setName(userDto.getName());
//		user.setPassword(userDto.getPassword());
//		user.setAbout(userDto.getAbout());
//		user.setEmail(userDto.getEmail());
		
		return user;
	}
	
	public UserDto userTODto(User user) {
		UserDto userDto = this.modelMapper.map(user, UserDto.class);
		
		return userDto;
	}

	@Override
	public UserDto registerUser(UserDto userDto) {
		
		User user = this.modelMapper.map(userDto, User.class);
		
		// password encoded
		user.setPassword(this.encoder.encode(user.getPassword()));
		
		// setting Role
		Role role = this.roleRepo.findById(AppConstans.ROLE_USER).get();
		user.getRoles().add(role);
		
	    User newUser = this.userRepo.save(user);
		return this.modelMapper.map(newUser, UserDto.class);
	}

}
