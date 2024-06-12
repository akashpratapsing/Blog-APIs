package com.blogster.blog.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.blogster.blog.config.AppConstans;
import com.blogster.blog.payloads.ApiResponse;
import com.blogster.blog.payloads.PostDto;
import com.blogster.blog.payloads.PostResponce;
import com.blogster.blog.services.FileService;
import com.blogster.blog.services.PostService;

import jakarta.servlet.http.HttpServletResponse;


@RestController
@RequestMapping("/api/")
public class PostController {

	@Autowired
	private PostService postService;
	
	@Autowired
	private FileService fileService;
	
	@Value("${project.image}")
	private String path;
	//create
	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, 
			@PathVariable Integer userId,
			@PathVariable Integer categoryId){
		
	PostDto newPost = this.postService.createPost(postDto, userId, categoryId);
	
		return new ResponseEntity<PostDto>(newPost, HttpStatus.CREATED);
	}
	
	// Get POST by user
	
		@GetMapping("/user/{userId}/posts")
		public ResponseEntity<List<PostDto>> getPostByUser(@PathVariable Integer userId){
			
			List<PostDto> postDtos = this.postService.getAllPostByUser(userId);
			
			return new ResponseEntity<List<PostDto>>(postDtos, HttpStatus.OK);
		}
		
		// Update Post
		@PutMapping("posts/{postId}")
		public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto,
				@PathVariable Integer postId)
		{
			PostDto updatedPost = this.postService.updatePost(postDto, postId);
			return ResponseEntity.ok(updatedPost);
		}
		
		// Get POST by Category
		
		@GetMapping("/category/{categoryId}/posts")
		public ResponseEntity<List<PostDto>> getPostByCategory(@PathVariable Integer categoryId){
			
			List<PostDto> postDtos = this.postService.getAllPostByCategory(categoryId);
			
			return new ResponseEntity<List<PostDto>>(postDtos, HttpStatus.OK);
		}
		
	// get post By Post ID
		@GetMapping("/posts/{postId}")
		public ResponseEntity<PostDto> getSinglePost(@PathVariable Integer postId){
			
			return ResponseEntity.ok(this.postService.getPostById(postId));	
		}
		
	// Get all post 
		@GetMapping("/posts")	
		public ResponseEntity<PostResponce> getAllPosts(
				@RequestParam(value = "pageNumber", defaultValue = AppConstans.PAGE_NUMBER, required = false) Integer pageNumber,
				@RequestParam(value = "pageSize", defaultValue = AppConstans.PAGE_SIZE, required = false) Integer pageSize,
				@RequestParam(value = "sortBy", defaultValue = AppConstans.SORT_BY, required = false) String sortBy,
				@RequestParam(value = "sortOrder", defaultValue = AppConstans.SORT_ORDER, required = false) String sortOrder
				)
		{
			
			PostResponce postResponce = this.postService.getAllPost(pageNumber, pageSize, sortBy, sortOrder);
			
			return new ResponseEntity<PostResponce>(postResponce, HttpStatus.OK);
		}
		
	// delete post by id
		@DeleteMapping("/posts/{postId}")
		public ResponseEntity<ApiResponse> deletePost(@PathVariable Integer postId){
			
			this.postService.deletePost(postId);
			
			return new ResponseEntity<ApiResponse>(new ApiResponse("Post Deleted Successfully ", true), HttpStatus.OK);
		}
		
	// Search post by title
		@GetMapping("/posts/search/{Keywords}")
		public ResponseEntity<List<PostDto>> searchByTitle(@PathVariable("Keywords") String keywords){
			
			List<PostDto> posts = this.postService.searchPosts(keywords);
			
			return new ResponseEntity<List<PostDto>>(posts, HttpStatus.OK);
		}
		
	// post upload image
		@PostMapping("/posts/image/upload/{postId}")
		public ResponseEntity<PostDto> uploadPostImage(
				@RequestParam("image") MultipartFile image,
				@PathVariable Integer postId) throws IOException{
			
			PostDto postDto = this.postService.getPostById(postId);
			
			String fileNmae = this.fileService.uploadImage(path, image);
			postDto.setImageName(fileNmae);
			PostDto updatedPost = this.postService.updatePost(postDto, postId);
			
			return new ResponseEntity<PostDto>(updatedPost, HttpStatus.OK);
			
		}
		
		
	//Method to serve file
	    @GetMapping(value = "/post/images/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
	    public void downloadImage(
	            @PathVariable("imageName") String imageName,
	            HttpServletResponse response
	    ) throws IOException {
	        InputStream in =  this.fileService.getResource(path,imageName);
	        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
	        StreamUtils.copy(in, response.getOutputStream());

	    }
}
