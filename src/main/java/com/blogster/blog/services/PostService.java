package com.blogster.blog.services;

import java.util.List;

import com.blogster.blog.payloads.PostDto;
import com.blogster.blog.payloads.PostResponce;

public interface PostService {

	
	// create post
	
	PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);
	
	// update post
	
	PostDto updatePost(PostDto postDto, Integer postId);
	
	// delete post
	
	void deletePost(Integer postId);
	
	// get Single Post
	
	PostDto getPostById(Integer postId);
	
	// Get all post
	
	PostResponce getAllPost(Integer pageNumber, Integer pageSize, String sortby, String sortOrder);
	
	// Get all post by category
	
	List<PostDto> getAllPostByCategory(Integer categoryId);
	
	// Get All Post by user
	
	List<PostDto> getAllPostByUser(Integer userId);
	
	// Search Posts
	
	List<PostDto> searchPosts(String keyword);
	
	
	
}
