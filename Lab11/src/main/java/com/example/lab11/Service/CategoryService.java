package com.example.lab11.Service;


import com.example.lab11.Api.ApiException;
import com.example.lab11.Model.Category;

import com.example.lab11.Repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public List<Category> getAllCategory() {
        return categoryRepository.findAll();
    }

    public void addCategory(Category category) {
        if (category == null) {
            throw new ApiException("category not found");
        }
        categoryRepository.save(category);
    }

    public void updateCategory(Integer id, Category category) {
        Category oldCategory = categoryRepository.findCategoryByCategoryId(id);
        if (oldCategory == null) {
            throw new ApiException("category not found");
        }
        oldCategory.setName(category.getName());
        categoryRepository.save(oldCategory);
    }

    public void deleteCategory(Integer id) {
        Category category = categoryRepository.findCategoryByCategoryId(id);
        if (category == null) {
            throw new ApiException("category not found");
        }
        categoryRepository.delete(category);
    }
}

