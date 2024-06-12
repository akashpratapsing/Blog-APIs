package com.blogster.blog.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {

	private int id;
	
	@NotEmpty
	@Size(min = 4,max = 84,  message= "username must contain 4 characters and upto 84 characters")
	private String name;
	
	@Email
	private String email;
	
	@NotEmpty
	@Size(min = 4, max = 10, message = "Password must be of minimum 4 characters and upto 10 characters")
	private String password;
	
	@NotNull
	private String about;
}
