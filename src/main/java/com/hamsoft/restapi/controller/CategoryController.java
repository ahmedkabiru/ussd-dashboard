package com.hamsoft.restapi.controller;

import com.hamsoft.restapi.domain.Category;
import com.hamsoft.restapi.exception.ResourceNotFoundException;
import com.hamsoft.restapi.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(APIName.CATEGORIES)
@RequiredArgsConstructor
public class CategoryController {

    private  final CategoryService categoryService;

    @GetMapping
    public List<Category> getAllCategory() {
        return categoryService.listAllCategories();
    }


    @PostMapping
    public Category createCategory(@Valid  @RequestBody Category category){
        return  categoryService.saveCategory(category);
    }

    @GetMapping("{id}")
    public Category getCategory(@PathVariable(value = "id") Long categoryId ){

        return categoryService.getCategoryById(categoryId)
                .orElseThrow(()->new ResourceNotFoundException("Category","id",categoryId));
    }

    @PutMapping("{id}")
    public  Category updateCategory(@PathVariable(value = "id") Long categoryId , @Valid @RequestBody Category categoryRequest){
        return  categoryService.getCategoryById(categoryId).map(
                category -> {
                    category.setName(categoryRequest.getName());
                    return  categoryService.saveCategory(category);
                }

        ).orElseThrow(()->new ResourceNotFoundException("Category","id",categoryId));

    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deleteCategory(@PathVariable(value = "id") Long categoryId) {
        return categoryService.getCategoryById(categoryId).map(category -> {
            categoryService.deleteCategory(category.getId());
            return ResponseEntity.ok().build();
        }).orElseThrow(()->new ResourceNotFoundException("Category","id",categoryId));
    }

}
