package com.blogster.blog;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class PasswordEncoderValue {
	
	@Autowired
	private PasswordEncoder encoder;
	
	
	@Test
	public void encoderTest() {
		
		System.out.println(this.encoder.encode("abc"));
	}

}
