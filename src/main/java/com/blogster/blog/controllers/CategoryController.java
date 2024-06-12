package com.blogster.blog.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.blogster.blog.payloads.ApiResponse;
import com.blogster.blog.payloads.CategoryDto;
import com.blogster.blog.services.CategotyService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/categories")
public class CategoryController {

	@Autowired
	private CategotyService categotyService;
	
	// POST Request - create Category
	@PostMapping("/")
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto){
		
		CategoryDto createdCatDto = this.categotyService.createCategory(categoryDto);
		return new ResponseEntity<CategoryDto>(createdCatDto, HttpStatus.CREATED);	
	}
	
	// PUT Request -- update Category
	@PutMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable Integer categoryId){
		
		CategoryDto updatedCat = this.categotyService.updateCategory(categoryDto, categoryId);
		return ResponseEntity.ok(updatedCat);	
	}
	
	//DELETE Request -- delete category
	@DeleteMapping("/{categoryId}")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer categoryId){
		
		this.categotyService.deleteCategory(categoryId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Category Deleted Seccessfully ", true), HttpStatus.OK);
	}
	
	//GET Request 
	@GetMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> getSingleCategory(@PathVariable Integer categoryId){
		
		return ResponseEntity.ok(this.categotyService.getCategory(categoryId));
	}
	
	@GetMapping("/")
	public ResponseEntity<List<CategoryDto>> getAllCategories(){
		
		return ResponseEntity.ok(this.categotyService.getCategories());
	}
}
