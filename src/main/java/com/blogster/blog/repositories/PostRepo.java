package com.blogster.blog.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.blogster.blog.entities.Category;
import com.blogster.blog.entities.Post;
import com.blogster.blog.entities.User;

public interface PostRepo extends JpaRepository<Post, Integer>{

	
	// Get all post of user
	
	List<Post> findByUser(User user);
	
	// Get all post by category
	
	List<Post> findByCategory(Category category);
	
	// GET POST BY TITLE
	
	List<Post> findByTitleContaining(String title);
}
