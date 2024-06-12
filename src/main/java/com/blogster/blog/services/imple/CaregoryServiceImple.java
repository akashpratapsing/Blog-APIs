package com.blogster.blog.services.imple;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blogster.blog.entities.Category;
import com.blogster.blog.exceptions.ResourceNotFoundException;
import com.blogster.blog.payloads.CategoryDto;
import com.blogster.blog.repositories.CategoryRepo;
import com.blogster.blog.services.CategotyService;

@Service
public class CaregoryServiceImple implements CategotyService {

	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		
		Category cat = this.modelMapper.map(categoryDto, Category.class);
		Category addcat = this.categoryRepo.save(cat);
		
		return this.modelMapper.map(addcat, CategoryDto.class);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
		
		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "CategoryId", categoryId));
		
		
		category.setCategoryTitle(categoryDto.getCategoryTitle());
		category.setCategoryDescription(categoryDto.getCategoryDescription());
		
		Category updateCat = this.categoryRepo.save(category);
		
		return this.modelMapper.map(updateCat, CategoryDto.class);
	}

	@Override
	public CategoryDto getCategory(Integer categoryId) {
		
		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "CategoryId", categoryId));
		
		return this.modelMapper.map(category, CategoryDto.class);
	}

	@Override
	public List<CategoryDto> getCategories() {
		
		List<Category> categories = this.categoryRepo.findAll();
		
		List<CategoryDto> catDtos = categories.stream()
				.map(category -> this.modelMapper.map(category, CategoryDto.class))
				.collect(Collectors.toList());
		return catDtos;
	}

	@Override
	public void deleteCategory(Integer categoryId) {
		
		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "CategoryId", categoryId));
		
		this.categoryRepo.delete(category);
		
	}

}
