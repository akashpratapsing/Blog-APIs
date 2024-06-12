package com.blogster.blog.services.imple;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blogster.blog.entities.Comment;
import com.blogster.blog.entities.Post;
import com.blogster.blog.exceptions.ResourceNotFoundException;
import com.blogster.blog.payloads.CommentDto;
import com.blogster.blog.repositories.CommentRepo;
import com.blogster.blog.repositories.PostRepo;
import com.blogster.blog.services.CommentService;

@Service
public class CommentServiceImple implements CommentService {

	@Autowired
	private CommentRepo commentRepo;
	
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
    private ModelMapper modelMapper;
	@Override
	public CommentDto createComment(CommentDto commentDto, Integer postId) {
		
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "Post Id", postId));
		
		Comment comment = this.modelMapper.map(commentDto, Comment.class);
		
		comment.setContent(commentDto.getContent());
		comment.setPost(post);
		
		Comment savedComment = this.commentRepo.save(comment);
		
		return this.modelMapper.map(savedComment, CommentDto.class);
	}

	@Override
	public void deleteComment(Integer commentId) {
		
		Comment comment = this.commentRepo.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "Comment Id", commentId));
		
		this.commentRepo.delete(comment);

	}

}
