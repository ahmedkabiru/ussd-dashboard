package com.hamsoft.restapi.controller;

import com.hamsoft.restapi.domain.Category;
import com.hamsoft.restapi.domain.Company;
import com.hamsoft.restapi.service.CategoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {


    private  CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public Iterable<Category> getAllCategory() {
        return categoryService.listAllCategories();
    }

}
