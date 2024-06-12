package com.blogster.blog;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.blogster.blog.config.AppConstans;
import com.blogster.blog.entities.Role;
import com.blogster.blog.repositories.RoleRepo;


@SpringBootApplication
public class BlogAppApisApplication implements CommandLineRunner {

	@Autowired
	private RoleRepo roleRepo;
	
	public static void main(String[] args) {
		SpringApplication.run(BlogAppApisApplication.class, args);
	}
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Override
	public void run(String... args) throws Exception {
		
		try {

			
			Role role1 = new Role();
			role1.setId(AppConstans.ROLE_ADMIN);
			role1.setName("ROLE_ADMIN");
			

			Role role2 = new Role();
			role2.setId(AppConstans.ROLE_USER);
			role2.setName("ROLE_USER");
			
		    List<Role> roles = List.of(role1, role2);
		    List<Role> result = this.roleRepo.saveAll(roles);
		    
		    result.forEach(r -> {
		    	System.out.println(r.getName());
		    });
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
