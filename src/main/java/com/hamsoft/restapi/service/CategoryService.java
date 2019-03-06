package com.hamsoft.restapi.service;

import com.hamsoft.restapi.domain.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    List<Category> listAllCategories();

    Optional<Category> getCategoryById(Long id);

    Category saveCategory(Category category);

    void deleteCategory(Long id);
}
