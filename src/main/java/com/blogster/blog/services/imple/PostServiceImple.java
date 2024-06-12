package com.blogster.blog.services.imple;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.blogster.blog.entities.Category;
import com.blogster.blog.entities.Post;
import com.blogster.blog.entities.User;
import com.blogster.blog.exceptions.ResourceNotFoundException;
import com.blogster.blog.payloads.PostDto;
import com.blogster.blog.payloads.PostResponce;
import com.blogster.blog.repositories.CategoryRepo;
import com.blogster.blog.repositories.PostRepo;
import com.blogster.blog.repositories.UserRepo;
import com.blogster.blog.services.PostService;

@Service
public class PostServiceImple implements PostService {

	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Override
	public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {
		
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "User Id", userId));
		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId));
		
		Post post = this.modelMapper.map(postDto, Post.class);
		post.setImageName("default.png");
		post.setAddedDate(new Date());
		post.setUser(user);
		post.setCategory(category);
		
		Post newPost = this.postRepo.save(post);
		
		return this.modelMapper.map(newPost, PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "Post Is", postId));
		
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setImageName(postDto.getImageName());
		
		Post updatedPost = this.postRepo.save(post);
		return this.modelMapper.map(updatedPost, PostDto.class);
	}

	@Override
	public void deletePost(Integer postId) {
		
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "Post Is", postId));
		this.postRepo.delete(post);

	}

	@Override
	public PostDto getPostById(Integer postId) {
		
		Post post = this.postRepo.findById(postId)
		       .orElseThrow(() -> new ResourceNotFoundException("Post", "Post Id", postId));
		
		PostDto postDto = this.modelMapper.map(post, PostDto.class);
		return postDto;
	}

	@Override
	public PostResponce getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
		
	
		Sort s = null;
		
		if (sortOrder.equalsIgnoreCase("asc")) {
			s = Sort.by(sortBy).ascending();
		}else {
			s = Sort.by(sortBy).descending();
		}
		Pageable p = PageRequest.of(pageNumber, pageSize, s);
		
		Page<Post> pagePost = this.postRepo.findAll(p);
		
		List<Post> posts = pagePost.getContent();
		
		
		//List<Post> posts =  this.postRepo.findAll();
		
		List<PostDto> postDtos = posts.stream().map((post) -> (this.modelMapper.map(post, PostDto.class))).collect(Collectors.toList());
		
		PostResponce postResponce = new PostResponce();
		
		postResponce.setContent(postDtos);
		postResponce.setPageNumber(pagePost.getNumber());
		postResponce.setPageSize(pagePost.getSize());
		postResponce.setTotalElements(pagePost.getTotalElements());
		postResponce.setTotalPage(pagePost.getTotalPages());
		postResponce.setLastPage(pagePost.isLast());
		
		return postResponce;
	}

	@Override
	public List<PostDto> getAllPostByCategory(Integer categoryId) {
		
		Category cat = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId));
		List<Post> posts = this.postRepo.findByCategory(cat);
		
		List<PostDto> postDtos =  posts.stream().map((post) -> (this.modelMapper.map(post, PostDto.class))).collect(Collectors.toList());
		return postDtos;
	}

	@Override
	public List<PostDto> getAllPostByUser(Integer userId) {
		
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "User Id", userId));
		
		 List<Post> posts = this.postRepo.findByUser(user);
		 
		 List<PostDto> postDtos = posts.stream().map((post) -> (this.modelMapper.map(post, PostDto.class))).collect(Collectors.toList());
		return postDtos;
	}

	@Override
	public List<PostDto> searchPosts(String keyword) {
		List<Post> posts = this.postRepo.findByTitleContaining(keyword);
		List<PostDto> postDtos = posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDtos;
	}

}
