package com.blogster.blog.services;

import java.util.List;

import com.blogster.blog.payloads.CategoryDto;

public interface CategotyService {

	CategoryDto createCategory(CategoryDto categoryDto);
	
	CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId);
	
	CategoryDto getCategory(Integer categoryId);
	
	List<CategoryDto> getCategories();
	
	void deleteCategory(Integer id);
	
	
	
}
