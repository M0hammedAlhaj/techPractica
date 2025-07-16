package com.spring.techpractica.mengmentData;


import com.spring.techpractica.model.entity.techSkills.Category;
import com.spring.techpractica.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CategoryManagementData {

    private final CategoryRepository categoryRepository;


    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Optional<Category> findCategoryByCategoryName(String categoryName) {
        return categoryRepository.findById(categoryName);
    }

    public Category getCategoryByCategoryName(String categoryName) {
        return categoryRepository.findById(categoryName)
                .orElseThrow(() -> new IllegalArgumentException("Field not found"));
    }

    public List<Category> getCategoriesByCategoriesName(List<String> categoriesName) {
        return categoryRepository.findAllById(categoriesName);
    }
}
