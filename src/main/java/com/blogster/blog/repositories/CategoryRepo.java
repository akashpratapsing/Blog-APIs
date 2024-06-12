package com.blogster.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogster.blog.entities.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer> {

}
