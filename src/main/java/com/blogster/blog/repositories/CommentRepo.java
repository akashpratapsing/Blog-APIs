package com.blogster.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogster.blog.entities.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer> {

}
